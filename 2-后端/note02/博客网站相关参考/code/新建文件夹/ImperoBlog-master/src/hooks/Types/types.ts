//数据库中直接请求过来的对应房间的消息记录的chatLog类型
type ChatLog = {
    id: number,
    account: string
    time: string
    content: string
}

//要渲染在客户端上的消息记录的类型，要用ChatLog的account请求对应的用户拿到信息
type ChatLogBox = {
    id: number
    account: string
    head: string
    nickName: string
    time: string
    content: string
}

// 留言Message类型
type Message = {
    _id: Object
    id: number
    account: string
    time: string
    content: string
}

// 上传文章
type ArticleWrite = {
    articleTitle: string
    articleText: string
    articleTags: Array<string>
    author: string,
}

//请求过来的文章的类型
type Article = {
    author: string
    collections: Array<string>
    comments: Array<Message>
    content: string
    cover: string
    coverName: string
    id: number
    lastUpdataTime: string
    mdImgName: string
    parise: Array<string>
    tag: Array<string>
    time: string
    title: string
}



export type {
    ChatLog,
    ChatLogBox,
    Message,
    ArticleWrite,
    Article
}