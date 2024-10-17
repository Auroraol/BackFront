// 消息模板对象
const MSG_TPL = {
    text: {
        Content: 0 // 文本内容
    },
    image: {
        Image: {
            MediaId: 0 // 图像的媒体 ID
        }
    },
    voice: {
        Voice: {
            MediaId: 0 // 语音的媒体 ID
        }
    },
    video: {
        Video: {
            MediaId: 0,     // 视频的媒体 ID
            Title: 1,       // 视频标题
            Description: 2   // 视频描述
        }
    },
    music: {
        Music: {
            ThumbMediaId: 0, // 缩略图的媒体 ID
            HQMusicUrl: 1,    // 高质量音乐的 URL
            MusicUrl: 2,      // 音乐的 URL
            Title: 3,         // 音乐标题
            Description: 4     // 音乐描述
        }
    },
    news: {
        ArticleCount: 1, // 文章数量
        Articles: 0      // 文章集合
    }
};

// 导出 MSG_TPL 对象
export default MSG_TPL;