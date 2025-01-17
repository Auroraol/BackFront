# Redis库的不同数据表说明

Redis库运用于系统一级缓存来协助服务器快速响应并处理数据

## 0号表
用于对应网易歌曲id和歌曲的播放地址
格式如下:

包括热门歌单, 热门歌曲, 热门排行榜
都是整理成id-play_url 方式
Key-Value 类型(Str)

* Key : NEM123456
* Value: http://music.163.com/song/media/outer/url?id=123456.mp3

## 1号表
1号库用于维护用户的热门歌单的地址、歌单封面、歌单名称
类型为:
    image_url:
    title:    
    song_list_url:

## 2号表
2号表用于维护虾米音乐的歌曲id和播放地址
格式如下:

包括热门歌单, 热门歌曲, 热门排行榜
都是整理成id-play_url 方式
Key-Value 类型(Str)

形式同0号表相似

## 3号表(计划中)
3号表用于维护登录用户的登录状态(cookies), 实现快速登录.

## 4号表用于维护用户密码的随机盐值

格式如下:

参数        是否必须  描述

user_id   是       用户名

Salt      是       盐值

形成Key-Value类型储存，持久化储存将后续使用MySQL实现

## 5号表用于维护代理ip
    
    计划中

## 6号表用于维护用户的Cookies-token信息

格式如下:

参数        是否必须  描述

user_id    是       用户名

token      是       token值

该表内数据具有一定的时效性

## 7号表用于维护受到控制的token列表

处于该列表的token是于一分钟内产生了请求，如果一分钟内请求数量高达30次，该token会被标注为注意token，
当大于40次每分钟时自动失效token，并且封禁ip地址不允许请求get_token10分钟，记录ip地址3小时时效，如果再犯则自动封禁ip地址一天

格式如下:

参数        是否必须  描述

num        是       该token一分钟内被请求次数

token      是       token值

该表内数据具有一定的时效性



