const fs = require('fs')
const EventEmitter = require('events') //实现事件驱动编程
const urlParser = require('url')
const xml2js = require('xml2js')
const WechatEncrypt = require('wechat-encrypt')
const { buildObject } = require('./util')
const Component = require('./component')
const Authorizer = require('./authorizer')
const { getBody } = require('./network')
const MSG_TPL = require('./msg_tpl')

const HTML_TPL = fs.readFileSync(__dirname + '/jump.html', 'utf8') // html模板

const HTTP_STATUS_CODE_REDIRECT = 302 // HTTP状态码，重定向

const DELAY_UPPER_LIMIT = Math.pow(2, 31) - 1 // setTimeout 的延时上限值
const REFRESH_INTERVAL = 1000 * 60 * 110 // 刷新间隔，单位：毫秒。1小时50分钟
const RETRY_TIMEOUT = 1000 // 重试的超时时间，单位：毫秒。
const TIPS_TIMEOUT = 1000 * 5 // 超时则提示 component_verity_ticket 未就绪，单位：毫秒
const WARN_TIPS = '第三方平台服务将暂时不可用【原因：未收到微信服务器推送 component_verify_ticket】'

// 事件列表
const EVENT_COMPONENT_VERIFY_TICKET = 'component_verify_ticket'  // 当微信服务器向第三方服务器推送 ticket 时触发[推送]
const EVENT_COMPONENT_ACCESS_TOKEN = 'component_access_token'    // 当component_access_token需要刷新时触发[触发器刷新]
const EVENT_AUTHORIZED = 'authorized'                           // 授权成功[推送]
const EVENT_UPDATE_AUTHORIZED = 'updateauthorized'              // 授权更新[推送]
const EVENT_UNAUTHORIZED = 'unauthorized'                       // 授权取消[推送]
const EVENT_AUTHORIZER_ACCESS_TOKEN = 'authorizer_access_token' // 当access token需要刷新时触发[触发器刷新]
const EVENT_AUTHORIZER_JSAPI_TICKET = 'authorizer_jsapi_ticket' // 当授权方 Js Api Ticket 更新时触发

// 全网发布自动化测试的账号
const AUTO_TEST_MP_APPID = 'wx570bc396a51b8ff8' // 测试公众号APPID
const AUTO_TEST_MP_NAME = 'gh_3c884a361561' // 测试公众号名称
const AUTO_TEST_MINI_PROGRAM_APPID = 'wxd101a85aa106f53e' // 测试小程序APPID
const AUTO_TEST_MINI_PROGRAM_NAME = 'gh_8dad206e9538' // 测试小程序名称

const AUTO_TEST_TEXT_CONTENT = 'TESTCOMPONENT_MSG_TYPE_TEXT'
const AUTO_TEST_REPLY_TEXT = 'TESTCOMPONENT_MSG_TYPE_TEXT_callback'

const xmlParser = new xml2js.Parser({
    explicitRoot: false,
    explicitArray: false
})

// 解板XML数据
function parseXMLSync(str) {
    return new Promise(function (resolve, reject) {
        xmlParser.parseString(str, function (err, result) {
            if (err) {
                reject(err)
            } else {
                resolve(result)
            }
        })
    })
}

/**
 * componentMap 的字段列表
 * {
 *     [componentAppId]: {
 *          componentAppId 第三方平台APPID
 *          componentAppSecret 第三方平台app secret
 *          token 消息校验token
 *          encodingAESKey 消息加解密Key
 *          timer 刷新 component_access_token 的定时器ID
 *          fetchTimer 分页获取全部已授权账号的定时器
 *          retryTimes 重试次数
 *          fetchRetryTimes 分页获取的重试次数
 *          offset 分页获取的偏移值
 *          ComponentVerifyTicket 第三方平台 verify ticket
 *          component_access_token 第三方平台 access token
 *     },
 *     [componentAppId/authorizerAppId]: {
 *          authorizer_appid 授权方APPID
 *          authorizer_access_token 授权方access token
 *          authorizer_refresh_token 授权方 refresh token
 *          refresh_token 授权方 refresh token
 *          ticket 授权方 Js Api Ticket
 *          tokenTimer 刷新 access token 的定时器ID
 *          ticketTimer 刷新 JsApi Ticket 的定时器ID
 *          retryTokenTimes 重试 token 的次数
 *          retryTicketTimes 重试 ticket 的次数
 *     }
 * }
 */
let componentMap = {} // 第三方平台列表

// 使用 ES6 类语法定义 Toolkit
class Toolkit extends EventEmitter {
    constructor(options) {
        super(); // 调用父类构造函数
        const { list } = options;
        // 初始化 componentMap
        this.componentMap = {};
        list.forEach(({ componentAppId, componentAppSecret, token, encodingAESKey }) => {
            this.componentMap[componentAppId] = { componentAppId, componentAppSecret, token, encodingAESKey };
        });

        // 注册事件监听器
        this.on(EVENT_COMPONENT_VERIFY_TICKET, this.onReceiveComponentVerifyTicket);
        this.on(EVENT_COMPONENT_ACCESS_TOKEN, this.onRefreshComponentAccessToken);
        this.on(EVENT_AUTHORIZER_ACCESS_TOKEN, this.onRefreshAuthorizerAccessToken);
        this.on(EVENT_AUTHORIZED, this.onAuthorized);
        this.on(EVENT_UNAUTHORIZED, this.onUnauthorized);
        this.on(EVENT_AUTHORIZER_JSAPI_TICKET, this.onRefreshAuthorizerJsApiTicket);
    }

    // 当接收到 component_verify_ticket 时触发
    async onReceiveComponentVerifyTicket(data) { 
        //微信服务器会向其 ”授权事件接收URL” 每隔 10 分钟以 POST 的方式推送 component_verify_ticket，
        /**
        <xml>
            <AppId>some_appid</AppId>
            <CreateTime>1413192605</CreateTime>
            <InfoType>component_verify_ticket</InfoType>
            <ComponentVerifyTicket>some_verify_ticket</ComponentVerifyTicket>
        </xml>
        */
        await saveComponentVerifyTicket(data.ComponentVerifyTicket);
    }

    // 当 component_access_token 刷新时触发
    async onRefreshComponentAccessToken(data) {

    }
    
    /**
     * 当授权方access token更新时触发 
    */
    async onRefreshAuthorizerAccessToken (data) {
    
    }

    // 当有新的授权方授权给第三方平台时触发
     async onAuthorized (data) {
        // const { AppId, AuthorizationCode } = data
        // const componentAccessToken = await readComponentAccessToken()  // 从云数据库中读取应用token
        // await getAuthorizerAccessToken(AppId, AuthorizationCode, componentAccessToken)


        // let { AppId, AuthorizerAppid, AuthorizationCode } = data
        // let { component_access_token } = componentMap[AppId]
        // componentMap[`${AppId}/${AuthorizerAppid}`] = data // 存储数据

        // try {
        //     // 获取授权方的access token
        //     let { authorization_info } = await Authorizer.getAccessToken(AppId, component_access_token, AuthorizationCode)
        //     Object.assign(authorization_info, { AppId }) // 混合数据
            
        //     this.emit(EVENT_AUTHORIZER_ACCESS_TOKEN, authorization_info) // 触发授权方access token更新事件
        //     // 启动定时刷新授权方access token的功能
        //     componentMap[`${AppId}/${AuthorizerAppid}`].tokenTimer = setTimeout(this.startAuthorizerAccessTokenTimer.bind(this, AppId, AuthorizerAppid), REFRESH_INTERVAL)
        // } catch(err) {
        //     this.emit('error', err)
        // }
    }

    // 当授权方取消授权时触发
    onUnauthorized(data) {
   
    }

    // 当授权方 Js Api Ticket 更新时触发
    onRefreshAuthorizerJsApiTicket (data) {
     
    }

    /**
     * 获取第三方平台下已授权的全部授权方账号
     * @param {string} componentAppId 第三方平台APPID
     */
     async startFetchAuthorizerListTimer (componentAppId) {
        let { component_access_token, fetchRetryTimes = 0, offset = 0 } = componentMap[componentAppId]

        try {
            // 获取第三方平台下已授权的授权方列表
            let ret = await Component.getAuthorizerList(componentAppId, component_access_token, offset)
            let { list, total_count } = ret
            if (offset >= total_count) return

            list.forEach(item => componentMap[`${componentAppId}/${item.authorizer_appid}`] = item)
            // 定时刷新授权方的access token
            list.forEach(item => this.startAuthorizerAccessTokenTimer(componentAppId, item.authorizer_appid))

            timeout = 0 // 如果成功，则立即获取下一页
            componentMap[componentAppId].offset = offset + list.length // 更新偏移值
            componentMap[componentAppId].fetchRetryTimes = 0 // 如果成功调用，则重试次数和间隔时长回到初始值
        } catch (err) {
            this.emit('error', err)
            timeout = Math.min(RETRY_TIMEOUT * Math.pow(2, fetchRetryTimes), DELAY_UPPER_LIMIT) // 重试的间隔时长按指数级增长，且不大于 setTimeout 的上限值
            componentMap[componentAppId].fetchRetryTimes = fetchRetryTimes + 1 // 各个第三方平台分别存储重试次数，间隔时长不互相影响
        }

        clearTimeout(componentMap[componentAppId].fetchTimer) // 清理旧的定时器
        componentMap[componentAppId].fetchTimer = setTimeout(this.startFetchAuthorizerListTimer.bind(this, componentAppId), timeout)
    }

    /**
     * 定时刷新第三方平台 access token
     * @param {string} componentAppId 第三方平台APPID
     */
     async startComponentAccessTokenTimer (componentAppId) {
        let { componentAppSecret, ComponentVerifyTicket, retryTimes = 0 } = componentMap[componentAppId]
        let timeout = 0

        try {
            // 获取第三方平台 access token
            let ret = await Component.getComponentAccessToken(componentAppId, componentAppSecret, ComponentVerifyTicket)
            Object.assign(ret, { componentAppId })
            this.emit(EVENT_COMPONENT_ACCESS_TOKEN, ret) // 触发第三方平台access token更新事件
            timeout = REFRESH_INTERVAL
            componentMap[componentAppId].retryTimes = 0 // 如果成功调用，则重试次数和间隔时长回到初始值
        } catch(err) {
            this.emit('error', err)
            timeout = Math.min(RETRY_TIMEOUT * Math.pow(2, retryTimes), DELAY_UPPER_LIMIT) // 重试的间隔时长按指数级增长，且不大于 setTimeout 的上限值
            componentMap[componentAppId].retryTimes = retryTimes + 1 // 各个第三方平台分别存储重试次数，间隔时长不互相影响
        }

        clearTimeout(componentMap[componentAppId].timer) // 清理旧的定时器
        componentMap[componentAppId].timer = setTimeout(this.startComponentAccessTokenTimer.bind(this, componentAppId), timeout)
    }

    /**
     * 定时刷新授权方 access token
     * @param {string} componentAppId 第三方平台APPID
     * @param {string} authorizerAppId 授权方APPID
     */
     async startAuthorizerAccessTokenTimer (componentAppId, authorizerAppId) {
        let { component_access_token } = componentMap[componentAppId]
        let { authorizer_refresh_token, refresh_token, retryTokenTimes = 0 } = componentMap[`${componentAppId}/${authorizerAppId}`]
        let timeout = 0

        try {
            // 刷新授权方的access token
            let ret = await Authorizer.refreshAccessToken(componentAppId, component_access_token, authorizerAppId, authorizer_refresh_token || refresh_token)
            Object.assign(ret, { AppId: componentAppId, authorizer_appid: authorizerAppId })
            this.emit(EVENT_AUTHORIZER_ACCESS_TOKEN, ret) // 触发授权方access token更新事件
            timeout = REFRESH_INTERVAL
            componentMap[`${componentAppId}/${authorizerAppId}`].retryTokenTimes = 0 // 如果成功调用，则重试次数和间隔时长回到初始值
        } catch(err) {
            this.emit('error', err)
            timeout = Math.min(RETRY_TIMEOUT * Math.pow(2, retryTokenTimes), DELAY_UPPER_LIMIT) // 重试的间隔时长按指数级增长，且不大于 setTimeout 的上限值
            componentMap[`${componentAppId}/${authorizerAppId}`].retryTokenTimes = retryTokenTimes + 1 // 各个授权方分别存储重试次数，间隔时长不互相影响
        }

        clearTimeout(componentMap[`${componentAppId}/${authorizerAppId}`].tokenTimer) // 清理旧的定时器
        componentMap[`${componentAppId}/${authorizerAppId}`].tokenTimer = setTimeout(this.startAuthorizerAccessTokenTimer.bind(this, componentAppId, authorizerAppId), timeout)
    }

    /**
     * 定时刷新授权方 Js Api Ticket
     * @param {string} componentAppId 第三方平台APPID
     * @param {string} authorizerAppId 授权方APPID
     */
     async startAuthorizerJsApiTicketTimer (componentAppId, authorizerAppId) {
        let { authorizer_access_token, retryTicketTimes = 0 } = componentMap[`${componentAppId}/${authorizerAppId}`]
        let timeout = 0

        try {
            let ret = await Authorizer.getJsApiTicket(authorizer_access_token)
            Object.assign(ret, { componentAppId, authorizerAppId })
            this.emit(EVENT_AUTHORIZER_JSAPI_TICKET, ret)
            timeout = REFRESH_INTERVAL
            componentMap[`${componentAppId}/${authorizerAppId}`].retryTicketTimes = 0 // 如果成功调用，则重试次数和间隔时长回到初始值
        } catch(err) {
            this.emit('error', err)
            timeout = Math.min(RETRY_TIMEOUT * Math.pow(2, retryTicketTimes), DELAY_UPPER_LIMIT) // 重试的间隔时长按指数级增长，且不大于 setTimeout 的上限值
            componentMap[`${componentAppId}/${authorizerAppId}`].retryTicketTimes = retryTicketTimes + 1 // 各个授权方分别存储重试次数，间隔时长不互相影响
        }

        clearTimeout(componentMap[`${componentAppId}/${authorizerAppId}`].ticketTimer) // 清理旧的定时器
        componentMap[`${componentAppId}/${authorizerAppId}`].ticketTimer = setTimeout(this.startAuthorizerJsApiTicketTimer.bind(this, componentAppId, authorizerAppId), timeout)
    }

    // 返回第三方平台授权事件的中间件, [授权事件接收配置] /callback
    //https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/Before_Develop/component_verify_ticket.html
    async events (query, body) {
        try {
            let bodyRaw = body.toString() // 转换成字符串
            let xml = await parseXMLSync(bodyRaw) // 解析XML数据成JS对象
            const { msg_signature, timestamp, nonce } = query;
            /**
            <xml>
                <AppId><![CDATA[wx5a062835463d1c61]]></AppId>
                <Encrypt><![CDATA[3KAGFTaD32rFYsXMmjK/6Hk7nfJzrgIqQs4tqlFA5ORsuMhFvMM9P0cUKEoW9v+2oJztVFRI/OV09szzN7QqqxgapTGLYcyKnbViqs+uSnVkwc5YBM4YkAKGU2Yyp89/6tXU0FloSfUvM8bHZteed44Wo3B3diuhA3JIOK/viBfdfgODTZCe/q/JFt7yYc2Cr3ojtmR1n2M+PdQTfs3bf7tLlR4yURD5kXYrJkZPK7giNzE+uEgn52SUAAhLnAc68hWm0rnPhvPgVARSqL3sHK5xtxihoXcX0h15j8Al3KbZ3IqIdM/SIJrB0mnDx8hOnoI8UaBmChVWS8iz/Ygp/lr8mT37WK0bVeFKm2PkNBG91/icsxOacEJCuXdj/yD+rthlB2OQbmbdFPXw/8QPCNjHdj2RmEL78flwrRQeoNOHYvmMBtoU9jm0WAix6db8siyCA2CNFsC/QxD84p0N/Q==]]></Encrypt>
            </xml>
            */
            let { AppId, Encrypt } = xml
            // 读取第三方平台配置
            let { encodingAESKey, token } = componentMap[AppId]
            //  签名校验
            let wechatEncrypt = new WechatEncrypt({ appId: AppId, encodingAESKey, token })
            let signature = wechatEncrypt.genSign({ timestamp, nonce, encrypt: Encrypt }) 
            if (signature === msg_signature ) {
                let str = wechatEncrypt.decode(Encrypt) 
                let xml = await parseXMLSync(str)  // 解析XML数据成JS对象
                /**
                <xml>
                    <AppId>some_appid</AppId>
                    <CreateTime>1413192605</CreateTime>
                    <InfoType>component_verify_ticket</InfoType>
                    <ComponentVerifyTicket>some_verify_ticket</ComponentVerifyTicket>
                </xml>
                    */
                let { InfoType } = xml
                this.emit(InfoType, xml) // 触发相应事件
                res.end('success')
            } else {
                console.warn('签名校验失败')
            }
        } catch(err) {
            this.emit('error', err)
        }
    }

    /**
     * 返回授权方消息处理的中间件,  [消息与事件接收配置] /{APPID}/callback
     * @param {string} componentAppId 第三方平台APPID
     */
    async message (componentAppId, body) {
        try {
            let bodyRaw = body.toString() // 转换成字符串
            let xml = await parseXMLSync(bodyRaw) // 解析XML数据成JS对象
            const { timestamp, nonce, msg_signature } = query;
            let { Encrypt } = xml
            // 读取第三方平台配置
            let { encodingAESKey, token } = componentMap[componentAppId]
            //  签名校验
            let wechatEncrypt = new WechatEncrypt({ appId: componentAppId, encodingAESKey, token })
            let signature = wechatEncrypt.genSign({ timestamp, nonce, encrypt: Encrypt }) // 生成签名
            if (signature === msg_signature) {
                let str = wechatEncrypt.decode(Encrypt)
                let xml = await parseXMLSync(str) // 解析XML数据成JS对象
                let { FromUserName, ToUserName } = xml
                req.wechat = xml
                Object.entries(MSG_TPL).forEach(([key, val]) => {
                    res[key] = this.genReplyFunc(componentAppId, encodingAESKey, token, ToUserName, FromUserName, key, JSON.stringify(val))
                })
                /* 回复图文消息 [[ */
                let news = res.news
                res.news = (list) => news.call(res, { item: list }, list.length)
                /* 回复图文消息 ]] */
                next && next()
            } else {
                console.warn('签名校验失败')
                res.end()
            }
        } catch(err) {
            !res.finished && res.end('success') // 当发生错误时，正常响应微信服务器
            this.emit('error', err) // 如果有错误，触发错误事件
        }
    }

    // 返回全网发布测试的中间件
    async autoTest (componentAppId, query) {
        let { Content = '', FromUserName, ToUserName } = req.wechat
        let strList = null

        try {
            // 如果接收消息的授权方是测试公众号或测试小程序，则执行预设的测试用例
            if ([AUTO_TEST_MP_NAME, AUTO_TEST_MINI_PROGRAM_NAME].includes(ToUserName)) {
                console.log('\n\n\n>>> 检测到全网发布测试 <<<\n\n\n')
                console.log('打印消息主体:')
                console.log(req.wechat)
                if (Content === AUTO_TEST_TEXT_CONTENT) {
                    res.text(AUTO_TEST_REPLY_TEXT)
                    console.log(`\n>>> 测试用例：被动回复消息；状态：已回复；回复内容：${AUTO_TEST_REPLY_TEXT} <<<\n`)
                } else if ((strList = Content.split(':'))[0] === 'QUERY_AUTH_CODE') {
                    res.end('')
                    let { component_access_token } = componentMap[`${componentAppId}`]
                    let { authorization_info: { authorizer_access_token } } = await Authorizer.getAccessToken(componentAppId, component_access_token, strList[1])
                    let content = `${strList[1]}_from_api`
                    let ret = await Authorizer.send(authorizer_access_token, FromUserName, 'text', { content })
                    console.log(`\n>>> 测试用例：主动发送客服消息；状态：已发送；响应结果：${JSON.stringify(ret)}；发送内容：${content} <<<\n`)
                }
            } else {
                next && next()
            }
        } catch(err) {
            this.emit('error', err)
        }
    }

    /**
     * 返回第三方授权处理的中间件, 返回授权URL
     * @param {*} componentAppId 第三方平台APPID
     * @param {*} redirectUrl 回调地址
     * @param {*} authType 第三方平台授权类型
     * @param {*} pageStyle 第三方平台授权页样式
     * @returns 
     */
    async auth (componentAppId, redirectUrl, authType = Component.AUTH_TYPE_BOTH, pageStyle = Component.PAGE_STYLE_PC) {
        let { component_access_token } = componentMap[componentAppId]
        try {
            // 获取预授权码
            let { pre_auth_code } = await Component.getPreAuthCode(componentAppId, component_access_token)
            // 获取第三方平台授权URL
            let url = Component.getAuthorizationUrl(componentAppId, pre_auth_code, redirectUrl, authType, pageStyle)
            res.end(HTML_TPL.replace('{url}', url))
        } catch(err) {
            this.emit('error', err)
            res.statusCode = 500
            res.end(err.stack)
        }
    }

    // 返回授权方网页授权的中间件
    oauth (componentAppId, authorizerAppId, redirectUrl, scope = Authorizer.OAUTH_TYPE_BASE, state = '') {
        return (req, res) => {
            // 获取授权方网页授权URL
            let url = Authorizer.getOAuthUrl(componentAppId, authorizerAppId, redirectUrl, scope, state)
            res.statusCode = HTTP_STATUS_CODE_REDIRECT
            res.setHeader('Location', url)
            res.end()
        }
    }

    /**
     * 生成被动回复消息的函数
     * @param {string} componentAppId 第三方平台APPID
     * @param {string} encodingAESKey 消息加解密Key
     * @param {string} token 消息加密token
     * @param {string} fromUserName 消息发送者
     * @param {string} toUserName 消息接收者
     * @param {string} type 消息类型
     * @param {string} tpl 消息模板
     */
    #genReplyFunc(componentAppId, encodingAESKey, token, fromUserName, toUserName, type, tpl) {
            let args = Array.prototype.slice.call(arguments)
            let timestamp = parseInt(Date.now() / 1000).toString()
            let data = { ToUserName: toUserName, FromUserName: fromUserName, CreateTime: timestamp, MsgType: type }
            let json = JSON.parse(tpl, (key, val) => key && typeof val === 'number' ? args[val] : val) // 为JSON模板填充数据
            Object.assign(data, json) // 混合数据

            let wechatEncrypt = new WechatEncrypt({ appId: componentAppId, encodingAESKey, token })
            let xml = buildObject(data) // js 对象转 xml 字符串

            let Encrypt = wechatEncrypt.encode(xml) // 加密内容
            let TimeStamp = Date.now() // 时间戳
            let Nonce = Math.random().toString(36).slice(2, 18) // 随机字符串
            let MsgSignature = wechatEncrypt.genSign({ timestamp: TimeStamp, nonce: Nonce, encrypt: Encrypt }) // 签名

            xml = buildObject({ Encrypt, TimeStamp, Nonce, MsgSignature })
            this.end(xml)
    }

}



Object.assign(Toolkit, {
    EVENT_COMPONENT_VERIFY_TICKET, EVENT_AUTHORIZED, EVENT_UPDATE_AUTHORIZED, EVENT_UNAUTHORIZED,
    EVENT_COMPONENT_ACCESS_TOKEN, EVENT_AUTHORIZER_ACCESS_TOKEN, EVENT_AUTHORIZER_JSAPI_TICKET
}, Component, Authorizer)

module.exports = Toolkit


// @GetMapping("/auth/callback")
//     public void authCallBack(HttpServletRequest request, HttpServletResponse response) {
//         log.info("接收到授权回调请求");
//         vxService.authCallBack(request, response);
//     }