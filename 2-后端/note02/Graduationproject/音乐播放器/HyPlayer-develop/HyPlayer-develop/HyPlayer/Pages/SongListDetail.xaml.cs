﻿#region

using HyPlayer.Classes;
using HyPlayer.HyPlayControl;
using NeteaseCloudMusicApi;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading;
using System.Threading.Tasks;
using Windows.ApplicationModel.DataTransfer;
using Windows.Graphics.Imaging;
using Windows.Storage.Streams;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Buffer = Windows.Storage.Streams.Buffer;

#endregion

// https://go.microsoft.com/fwlink/?LinkId=234238 上介绍了“空白页”项模板

namespace HyPlayer.Pages;

/// <summary>
///     可用于自身或导航至 Frame 内部的空白页。
/// </summary>
public sealed partial class SongListDetail : Page, IDisposable
{
    public static readonly DependencyProperty IsLoadingProperty = DependencyProperty.Register(
        "IsLoading", typeof(bool), typeof(SongListDetail), new PropertyMetadata(true));

    private int page;
    public NCPlayList playList;
    public ObservableCollection<NCSong> Songs;
    private bool disposedValue = false;
    private DataTransferManager _dataTransferManager = DataTransferManager.GetForCurrentView();
    private Task _songListLoaderTask;
    private Task _songListColorLoaderTask;
    private CancellationTokenSource _cancellationTokenSource = new CancellationTokenSource();
    private CancellationToken _cancellationToken;

    public SongListDetail()
    {
        InitializeComponent();
        Songs = new ObservableCollection<NCSong>();
        _dataTransferManager.DataRequested += DataTransferManagerOnDataRequested;
        _cancellationToken = _cancellationTokenSource.Token;
    }

    private void DataTransferManagerOnDataRequested(DataTransferManager sender, DataRequestedEventArgs args)
    {
        var dp = new DataPackage();
        dp.Properties.Title = playList.name;
        dp.SetWebLink(new Uri("https://music.163.com/#/playlist?id=" +
                              playList.plid));
        var request = args.Request;
        request.Data = dp;
    }

    public bool IsLoading
    {
        get => (bool)GetValue(IsLoadingProperty);
        set => SetValue(IsLoadingProperty, value);
    }

    public async Task LoadAlbumImage()
    {
        _cancellationToken.ThrowIfCancellationRequested();
        using var result = await Common.HttpClient.GetAsync(new Uri(playList.cover + "?param=" + StaticSource.PICSIZE_SONGLIST_DETAIL_COVER));
        if (result.IsSuccessStatusCode)
        {
            using var stream = new InMemoryRandomAccessStream();
            await result.Content.WriteToStreamAsync(stream);
            _cancellationToken.ThrowIfCancellationRequested();
            Color imageMainColor = await ColorExtractor.ExtractColorFromStream(stream);
            if (AlbumColor != null)
            {
                _ = Common.Invoke(() => AlbumColor.Color = imageMainColor);
            }
        }
    }

    public void LoadSongListDetail()
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        if (Common.Setting.noImage)
        {
            AlbumImageSource = null;
        }
        else
        {
            AlbumImageSource.UriSource = new Uri(playList.cover + "?param=" + StaticSource.PICSIZE_SONGLIST_DETAIL_COVER);
            _songListColorLoaderTask = LoadAlbumImage();
        }

        TextBoxPLName.Text = playList.name;
        DescriptionTextBlock.Text = playList.desc;
        TextBoxAuthor.Content = playList.creater.name;
        ButtonLike.Tag = playList.subscribed;
        UpdateLikeBtnStyle();
        if (playList.updateTime.Year != 0001)
            TextBoxUpdateTime.Text = $"{DateConverter.FriendFormat(playList.updateTime)}更新";
    }
    public void UpdateLikeBtnStyle()
    {
        if ((bool)ButtonLike.Tag == true)
        {
            LikedIcon.Glyph = "\uE10B";
            LikeBtnText.Text = "已收藏";
        }
        else
        {
            LikedIcon.Glyph = "\uE0B4";
            LikeBtnText.Text = "收藏";
        }

    }


    public async Task LoadSongListItem()
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        IsLoading = true;
        if (playList.plid != "-666")
        {
            await LoadPlayListItems();
        }
        else
        {
            ButtonIntel.Visibility = Visibility.Collapsed;
            await LoadDailyRcmdItems();
        }

        IsLoading = false;
    }

    private async Task LoadDailyRcmdItems()
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        SongsList.ListSource = "content";
        try
        {
            _cancellationToken.ThrowIfCancellationRequested();
            var json = await Common.ncapi?.RequestAsync(CloudMusicApiProviders.RecommendSongs);
            if (json["data"]["dailySongs"][0]["alg"].ToString() == "birthDaySong")
            {
                // 诶呀,没想到还过生了,吼吼
                DescriptionTextBlock.Text = "生日快乐~ 今天也要开心哦!";
                DescriptionTextBlock.Foreground = new SolidColorBrush(Color.FromArgb(255, 255, 0, 0));
                DescriptionTextBlock.FontSize = 25;
            }

            var idx = 0;
            foreach (var song in json["data"]["dailySongs"])
            {
                _cancellationToken.ThrowIfCancellationRequested();
                var ncSong = NCSong.CreateFromJson(song);
                ncSong.IsAvailable = true;
                ncSong.Order = idx++;
                Songs.Add(ncSong);
            }
            json.RemoveAll();
        }
        catch (Exception ex)
        {
            if (ex.GetType() != typeof(TaskCanceledException) && ex.GetType() != typeof(OperationCanceledException))
                Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
        }
    }

    private async Task LoadPlayListItems()
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        try
        {
            _cancellationToken.ThrowIfCancellationRequested();
            var json = await Common.ncapi?.RequestAsync(CloudMusicApiProviders.PlaylistDetail,
                new Dictionary<string, object> { { "id", playList.plid } });
            if (json["code"].ToString() == "405")
            {
                treashold = ++cooldownTime * 10;
                page--;
                throw new Exception($"渐进加载速度过于快, 将在 {cooldownTime * 10} 秒后尝试继续加载, 正在清洗请求");
            }
            if (!json["playlist"]["trackIds"].HasValues) return;
            var trackIds = json["playlist"]["trackIds"].Select(t => (string)t["id"]).Skip(page * 500)
                .Take(500)
                .ToArray();

            NextPage.Visibility = trackIds.Length >= 500 ? Visibility.Visible : Visibility.Collapsed;


            if (json["playlist"]["specialType"].ToString() == "5" &&
                json["playlist"]["userId"].ToString() == Common.LoginedUser?.id)
            {
                ButtonIntel.Visibility = Visibility.Visible;
                SongsList.IsMySongList = true;
            }

            try
            {
                json = await Common.ncapi?.RequestAsync(CloudMusicApiProviders.SongDetail,
                    new Dictionary<string, object> { ["ids"] = string.Join(",", trackIds) });
                var idx = page * 500;
                var i = 0;
                foreach (var jToken in json["songs"])
                {
                    _cancellationToken.ThrowIfCancellationRequested();
                    var song = (JObject)jToken;

                    var ncSong = NCSong.CreateFromJson(song);
                    ncSong.IsAvailable =
                        json["privileges"].ToList()[i++]["st"].ToString() == "0";
                    ncSong.Order = idx++;
                    Songs.Add(ncSong);
                }
                json.RemoveAll();
            }

            catch (Exception ex)
            {
                if (ex.GetType() != typeof(TaskCanceledException) && ex.GetType() != typeof(OperationCanceledException))
                    Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
            }
        }
        catch (Exception ex)
        {
            if (ex.GetType() != typeof(TaskCanceledException) && ex.GetType() != typeof(OperationCanceledException))
                Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
        }
    }

    protected override async void OnNavigatedFrom(NavigationEventArgs e)
    {
        base.OnNavigatedFrom(e);
        if (_songListLoaderTask != null && !_songListLoaderTask.IsCompleted)
        {
            try
            {
                _cancellationTokenSource.Cancel();
                await _songListLoaderTask;
            }
            catch
            {
            }
        }
        if (_songListColorLoaderTask != null && !_songListColorLoaderTask.IsCompleted)
        {
            try
            {
                _cancellationTokenSource.Cancel();
                await _songListColorLoaderTask;
            }
            catch
            {
            }
        }
        Dispose();
    }

    protected override async void OnNavigatedTo(NavigationEventArgs e)
    {
        base.OnNavigatedTo(e);

        if (e.Parameter != null)
        {
            if (e.Parameter is NCPlayList)
            {
                playList = (NCPlayList)e.Parameter;
            }
            else
            {
                var pid = e.Parameter.ToString();

                try
                {
                    var json = await Common.ncapi?.RequestAsync(
                        CloudMusicApiProviders.PlaylistDetail,
                        new Dictionary<string, object> { { "id", pid } });
                    if (json["code"].ToString() != "200")
                        throw new Exception(json["message"]?.ToString());
                    playList = NCPlayList.CreateFromJson(json["playlist"]);
                    json.RemoveAll();
                }
                catch (Exception ex)
                {
                    Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
                }
            }
        }

        SongsList.ListSource = "pl" + playList?.plid;
        LoadSongListDetail();
        _songListLoaderTask = LoadSongListItem();
        if (Common.Setting.greedlyLoadPlayContainerItems)
            HyPlayList.OnTimerTicked += GreedlyLoad;
    }

    int treashold = 3;
    int cooldownTime = 0;

    private void GreedlyLoad()
    {
        _ = Common.Invoke(() =>
        {
            if (treashold > 10)
            {
                treashold--;
                return;
            }
            if (Songs.Count > 0 && NextPage.Visibility == Visibility.Visible && treashold-- <= 0 && !disposedValue)
            {
                NextPage_OnClickPage_OnClick(null, null);
                treashold = 3;
            }
            else if (Songs.Count > 0 && NextPage.Visibility == Visibility.Collapsed || disposedValue)
            {
                HyPlayList.OnTimerTicked -= GreedlyLoad;
            }
        });
    }


    private async void ButtonPlayAll_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        if (playList.plid != "-666")
        {
            HyPlayList.RemoveAllSong();
            await HyPlayList.AppendPlayList(playList.plid);
            HyPlayList.PlaySourceId = playList.plid;
            HyPlayList.NowPlaying = -1;
            HyPlayList.SongMoveNext();
        }
        else
        {
            HyPlayList.AppendNcSongs(Songs.ToList());
            HyPlayList.PlaySourceId = playList.plid;
            HyPlayList.NowPlaying = -1;
            HyPlayList.SongMoveNext();
        }
    }


    private void NextPage_OnClickPage_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        page++;
        _songListLoaderTask = LoadSongListItem();
    }

    private void ButtonComment_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        Common.NavigatePage(typeof(Comments), "pl" + playList.plid);
    }

    private async void ButtonHeartBeat_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        HyPlayList.RemoveAllSong();
        try
        {
            var jsona = await Common.ncapi?.RequestAsync(
                CloudMusicApiProviders.PlaymodeIntelligenceList,
                new Dictionary<string, object>
                    { { "pid", playList.plid }, { "id", Songs[0].sid } /*, { "sid", Songs[0].sid }*/ });
            var IntSongs = new List<NCSong>
            {
                Songs[new Random().Next(0, Songs.Count)]
            };
            foreach (var token in jsona["data"])
                try
                {
                    if (token["songInfo"] != null)
                    {
                        var ncSong = NCSong.CreateFromJson(token["songInfo"]);
                        IntSongs.Add(ncSong);
                    }
                }
                catch
                {
                    //ignore
                }

            try
            {
                HyPlayList.AppendNcSongs(IntSongs);
                HyPlayList.PlaySourceId = playList.plid;
                HyPlayList.SongMoveTo(0);
            }
            catch (Exception ex)
            {
                Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
            }
            jsona.RemoveAll();
            IntSongs.Clear();
        }
        catch (Exception ex)
        {
            Common.AddToTeachingTipLists(ex.Message, (ex.InnerException ?? new Exception()).Message);
        }
    }

    private void ButtonDownloadAll_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        DownloadManager.AddDownload(Songs.ToList());
    }

    private void LikeBtnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        _ = Common.ncapi?.RequestAsync(CloudMusicApiProviders.PlaylistSubscribe,
            new Dictionary<string, object> { { "id", playList.plid }, { "t", playList.subscribed ? "0" : "1" } });
        playList.subscribed = !playList.subscribed;
        ButtonLike.Tag = playList.subscribed;
        UpdateLikeBtnStyle();
    }

    private void TextBoxAuthor_Tapped(object sender, RoutedEventArgs routedEventArgs)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        Common.NavigatePage(typeof(Me), playList.creater.id);
    }

    private void BtnShare_Clicked(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        DataTransferManager.ShowShareUI();
    }


    private async void BtnAddAll_Clicked(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        if (playList.plid != "-666")
            await HyPlayList.AppendPlayList(playList.plid);
        else
            HyPlayList.AppendNcSongs(Songs.ToList());
    }

    private void Dispose(bool disposing)
    {
        if (!disposedValue)
        {
            if (disposing)
            {
                Songs.Clear();
                playList = null;
                AlbumImageSource = null;
                _cancellationTokenSource.Dispose();
            }
            SongsList.Dispose();
            _dataTransferManager.DataRequested -= DataTransferManagerOnDataRequested;
            disposedValue = true;
        }
    }

    ~SongListDetail()
    {
        Dispose(disposing: false);
    }

    public void Dispose()
    {
        // 不要更改此代码。请将清理代码放入“Dispose(bool disposing)”方法中
        Dispose(disposing: true);
        GC.SuppressFinalize(this);
    }

    private void BtnComment_OnClick(object sender, RoutedEventArgs e)
    {
        if (disposedValue) throw new ObjectDisposedException(nameof(SongListDetail));
        Common.NavigatePage(typeof(Comments), "pl" + playList.plid);
    }
}