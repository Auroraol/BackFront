package encryption

import (
	"bytes"
	"crypto/aes"
	"crypto/cipher"
	"crypto/md5"
	"encoding/base64"
	"encoding/hex"
)

// EncryptData 先md5然后aes加密
func EncryptData(plainText, k string) string {
	v := MD5V(plainText)
	return AESEncrypt(v, k)
}

// AESDecrypt 解密
func AESDecrypt(decrypted, key []byte) []byte {
	block, _ := aes.NewCipher(key)
	blockSize := block.BlockSize()
	blockMode := cipher.NewCBCDecrypter(block, key[:blockSize])
	origData := make([]byte, len(decrypted))
	blockMode.CryptBlocks(origData, decrypted)
	origData = PKCS7UnPadding(origData)
	return origData
}

// PKCS7UnPadding 去补码
func PKCS7UnPadding(origData []byte) []byte {
	length := len(origData)
	padding := int(origData[length-1])
	return origData[:length-padding]
}

// AESEncrypt 加密
func AESEncrypt(data, k string) string {
	origData := []byte(data)
	key := []byte(k)
	//获取block块
	block, _ := aes.NewCipher(key)
	//补码
	origData = PKCS7Padding(origData, block.BlockSize())
	//加密模式
	blockMode := cipher.NewCBCEncrypter(block, key[:block.BlockSize()])
	decrypted := make([]byte, len(origData))
	blockMode.CryptBlocks(decrypted, origData)
	return base64.StdEncoding.EncodeToString(decrypted)
}

// PKCS7Padding 补码
func PKCS7Padding(origData []byte, blockSize int) []byte {
	//计算需要补几位数
	padding := blockSize - len(origData)%blockSize
	//在切片后面追加char数量的byte(char)
	plaintext := bytes.Repeat([]byte{byte(padding)}, padding)
	return append(origData, plaintext...)
}

// md5加密
func MD5V(str string) string {
	h := md5.New()
	h.Write([]byte(str))
	return hex.EncodeToString(h.Sum(nil))
}
