# 音乐湖 PC 端 ![Build Status](https://github.com/sunzongzheng/music/workflows/Build/release/badge.svg)

# 由于收到网易云音乐和 QQ 音乐的警告函，登录/云歌单服务已关闭，可继续使用离线歌单，有服务器资源可自行搭建云歌单，所有代码均已开源。打包介绍见[build.md](build.md)

## 简介

-   歌曲 Api 涵盖网易云、QQ 音乐、虾米
-   界面仿 QQ 音乐
-   Mac > Windows > Linux 都会逐步适配
-   安卓客户端详见[caiyonglong/MusicLake](https://github.com/caiyonglong/MusicLake)
-   登录、收藏、播放的流程基本没问题，可作为日用上班挂后台听歌程序
-   精力有限，需求不饱和都会逐步完善，有兴趣可赏个 star 静等完善与 bug 修复，期望的功能也可提 issues
-   [代码架构图](screenshot/code-architecture.png)

## Feature

> 目前大家提的功能需求已全部解决！
> 如果此程序能满足你 70%的日用需求，剩下的 30%欢迎以[issues](https://github.com/sunzongzheng/music/issues)的形式告诉我

-   搜索 / 精选 / 榜单 / 歌手详情 / 专辑详情 / 歌曲评论 / 网易云 MV
-   支持 QQ、微博 授权登录，云歌单
-   支持一键导入网易云、QQ 音乐、虾米歌单
-   支持离线歌单
-   歌词翻译
-   下载（不能下载被云平台限制的歌曲，如会员限制）
-   Mac 状态栏歌词 / TouchBar 播放控制 / 桌面歌词
-   快键键 / 全局快捷键
-   社会化分享：QQ、微博、微信等

## 关联项目

-   [Android 客户端](https://github.com/caiyonglong/MusicLake)
-   [音乐解析 Api](https://github.com/sunzongzheng/musicApi)
-   [云歌单 Api](https://github.com/sunzongzheng/player-be)
-   [Mac 免签名自动更新](https://github.com/sunzongzheng/electron-updater)

## 预览图

-   精选
    ![](screenshot/1.png)
-   排行
    ![](screenshot/rank.png)
-   QQ 音乐歌手列表
    ![](screenshot/2.png)
-   快速导入歌单
    ![](screenshot/3.png)
-   全屏歌词
    ![](screenshot/4.png)
-   歌曲评论页
    ![](screenshot/5.png)
-   Mac 顶部状态栏歌词 + 桌面歌词
    ![](screenshot/6.png)
-   社交分享
    ![](screenshot/7.png)

## :moneybag: 捐赠

捐赠会有两个用途

-   请允许我买杯咖啡 :coffee:
-   服务器升级与开多台负载均衡
-   微信:

![微信](screenshot/donate-wechat.jpg)

-   支付宝:

![支付宝](screenshot/donate-alipay.png)
