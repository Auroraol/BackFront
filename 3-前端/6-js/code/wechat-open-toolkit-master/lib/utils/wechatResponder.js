const WechatEncrypt = require('wechat-encrypt')

class WechatResponder {
    constructor(componentAppId, encodingAESKey, token) {
        this.componentAppId = componentAppId;
        this.encodingAESKey = encodingAESKey;
        this.token = token;
    }

    // 生成回复消息的函数
    genReplyFunc(fromUserName, toUserName, type, tpl) {
        let args = Array.prototype.slice.call(arguments);
        let timestamp = parseInt(Date.now() / 1000).toString();
        let data = {
            ToUserName: toUserName,
            FromUserName: fromUserName,
            CreateTime: timestamp,
            MsgType: type
        };

        let json = JSON.parse(tpl, (key, val) => key && typeof val === 'number' ? args[val] : val); // 填充数据
        Object.assign(data, json); // 混合数据

        let wechatEncrypt = new WechatEncrypt({ appId: this.componentAppId, encodingAESKey: this.encodingAESKey, token: this.token });
        let xml = buildXMLSync(data); // js 对象转 xml 字符串

        let Encrypt = wechatEncrypt.encode(xml); // 加密内容
        let TimeStamp = Date.now(); // 时间戳
        let Nonce = Math.random().toString(36).slice(2, 18); // 随机字符串
        let MsgSignature = wechatEncrypt.genSign({ timestamp: TimeStamp, nonce: Nonce, encrypt: Encrypt }); // 签名

        xml = buildXMLSync({ Encrypt, TimeStamp, Nonce, MsgSignature });
        return xml; // 返回生成的 XML
    }

    /**
     * 回复
     * @param {*} msgTemplates 消息模版
     * @param {*} fromUserName 
     * @param {*} toUserName 
     * @returns 
     */
    reply(msgTemplates, fromUserName, toUserName) {
        let replies = {};
        Object.entries(msgTemplates).forEach(([key, val]) => {
            replies[key] = this.genReplyFunc(fromUserName, toUserName, key, JSON.stringify(val));
        });

        // 回复图文消息的处理逻辑
        let news = replies.news;
        replies.news = (list) => news.call(replies, { item: list }, list.length);
        
        return replies; // 返回所有回复
    }
}