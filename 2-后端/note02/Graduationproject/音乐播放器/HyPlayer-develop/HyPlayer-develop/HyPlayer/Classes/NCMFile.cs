﻿#region

using HyPlayer.HyPlayControl;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using Windows.Storage;
using File = TagLib.File;

#endregion

namespace HyPlayer.Classes;

internal static class NCMFile
{
    private static readonly byte[] _flag = new byte[8] { 0x43, 0x54, 0x45, 0x4e, 0x46, 0x44, 0x41, 0x4d };

    private static byte[] _id3Flag = new byte[3] { 0x49, 0x44, 0x33 };
    private static byte[] _pngFlag = new byte[8] { 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };

    private static readonly byte[] _coreBoxKey = new byte[16]
        { 0x68, 0x7A, 0x48, 0x52, 0x41, 0x6D, 0x73, 0x6F, 0x35, 0x6B, 0x49, 0x6E, 0x62, 0x61, 0x78, 0x57 };

    private static readonly byte[] _modifyBoxKey = new byte[16]
        { 0x23, 0x31, 0x34, 0x6C, 0x6A, 0x6B, 0x5F, 0x21, 0x5C, 0x5D, 0x26, 0x30, 0x55, 0x3C, 0x27, 0x28 };

    private static byte[] _keyBox;

    // 此处代码参考 https://github.com/anonymous5l/ncmdump-gui/blob/master/DesktopTool/NeteaseCrypto.cs
    public static async Task<bool> DecryptNCMFileAsync(StorageFile file, StorageFile destinationFile)
    {
        var stream = await file.OpenStreamForReadAsync();
        if (!IsCorrectNCMFile(stream)) return false;
        var Info = GetNCMMusicInfo(stream);
        var encStream = GetEncryptedStream(stream);
        encStream.CopyTo(await destinationFile.OpenStreamForWriteAsync());
        var tagFile = File.Create(new UwpStorageFileAbstraction(destinationFile));
        The163KeyHelper.TrySetMusicInfo(tagFile.Tag, Info);
        return true;
    }

    public static Stream GetCoverStream(Stream stream)
    {
        stream.Seek(9, SeekOrigin.Current);
        return new MemoryStream(ReadChunk(stream));
    }
    public static byte[] GetCoverByteArray(Stream stream)
    {
        stream.Seek(9, SeekOrigin.Current);
        return ReadChunk(stream);
    }

    public static Stream GetEncryptedStream(Stream sourceStream)
    {
        var n = 0x8000;
        double totalLen = sourceStream.Length - sourceStream.Position;

        //char[] ignore = Path.GetInvalidFileNameChars();
        //var convertName = Name;

        //foreach (var i in ignore)
        //{
        //    convertName = convertName.Replace(i.ToString(), "");
        //}

        MemoryStream stream = null;

        while (n > 1)
        {
            var chunk = new byte[n];
            n = sourceStream.Read(chunk, 0, n);

            for (var i = 0; i < n; i++)
            {
                var j = (i + 1) & 0xff;
                chunk[i] ^= _keyBox[(_keyBox[j] + _keyBox[(_keyBox[j] + j) & 0xff]) & 0xff];
            }

            if (stream == null) stream = new MemoryStream();

            if (stream != null)
                stream.Write(chunk, 0, n);
            else
                break;
        }

        return stream;
    }

    private static bool byteCompare(byte[] src, byte[] dst)
    {
        if (src.Length > dst.Length)
            return false;

        for (var i = 0; i < src.Length; i++)
            if (dst[i] != src[i])
                return false;

        return true;
    }


    public static The163KeyClass GetNCMMusicInfo(Stream stream)
    {
        The163KeyClass keys = default;
        stream.Seek(2, SeekOrigin.Current);
        var coreKeyChunk = ReadChunk(stream);
        for (var i = 0; i < coreKeyChunk.Length; i++) coreKeyChunk[i] ^= 0x64;

        var ckcLen = AesDecrypt(coreKeyChunk, _coreBoxKey);

        var finalKey = new byte[ckcLen - 17];
        Array.Copy(coreKeyChunk, 17, finalKey, 0, finalKey.Length);
        _keyBox = new byte[256];
        for (var i = 0; i < _keyBox.Length; i++) _keyBox[i] = (byte)i;

        byte swap = 0;
        byte c = 0;
        byte last_byte = 0;
        byte key_offset = 0;

        for (var i = 0; i < _keyBox.Length; i++)
        {
            swap = _keyBox[i];
            c = (byte)((swap + last_byte + finalKey[key_offset++]) & 0xff);
            if (key_offset >= finalKey.Length) key_offset = 0;
            _keyBox[i] = _keyBox[c];
            _keyBox[c] = swap;
            last_byte = c;
        }

        var dontModifyChunk = ReadChunk(stream);

        if (dontModifyChunk != null)
        {
            var startIndex = 0;
            for (var i = 0; i < dontModifyChunk.Length; i++)
            {
                dontModifyChunk[i] ^= 0x63;
                if (dontModifyChunk[i] == 58 && startIndex == 0) startIndex = i + 1;
            }

            var dontModifyDecryptChunk = Convert.FromBase64String(Encoding.UTF8.GetString(dontModifyChunk,
                startIndex, dontModifyChunk.Length - startIndex));
            var mdcLen = AesDecrypt(dontModifyDecryptChunk, _modifyBoxKey);

            // skip `music:`
            using (var reader = new MemoryStream(dontModifyDecryptChunk, 6, mdcLen - 6))
            {
                var infoStr = Encoding.UTF8.GetString(reader.ToArray());
                var obj = JsonConvert.DeserializeObject<JObject>(infoStr);
                keys = new The163KeyClass
                {
                    albumId = obj["albumId"].ToObject<ulong>(),
                    album = obj["album"].ToString(),
                    musicId = long.Parse(Regex.Match(obj["musicId"].ToString(), "\\d*$").Value),
                    musicName = obj["musicName"].ToString(),
                    duration = obj["duration"].ToObject<int>(),
                    bitrate = obj["bitrate"].ToObject<int>(),
                    albumPic = obj["albumPic"].ToString(),
                    format = obj["format"].ToString(),
                    artist = obj["artist"].ToObject<List<List<object>>>()
                };
                obj.RemoveAll();
            }
        }

        return keys;
    }

    public static bool IsCorrectNCMFile(Stream stream)
    {
        var buffer = new byte[8];
        stream.Seek(0, SeekOrigin.Begin);
        stream.Read(buffer, 0, buffer.Length);
        return buffer.SequenceEqual(_flag);
    }


    private static int AesDecrypt(byte[] data, byte[] key)
    {
        var aes = Aes.Create();
        aes.Mode = CipherMode.ECB;
        aes.Key = key;
        aes.Padding = PaddingMode.PKCS7;

        using (var stream = new MemoryStream(data))
        {
            using (var cs = new CryptoStream(stream, aes.CreateDecryptor(), CryptoStreamMode.Read))
            {
                return cs.Read(data, 0, data.Length);
            }
        }
    }

    private static byte[] ReadChunk(Stream fs)
    {
        var len = fs.ReadUInt32();
        if (len > 0)
        {
            var chunk = new byte[len];
            // unsafe
            fs.Read(chunk, 0, (int)len);
            return chunk;
        }

        return null;
    }

    public static uint ReadUInt32(this Stream fs)
    {
        var raw = new byte[4];
        var ret = fs.Read(raw, 0, raw.Length);

        if (ret != raw.Length) throw new IOException("out of stream");

        return BitConverter.ToUInt32(raw, 0);
    }
}