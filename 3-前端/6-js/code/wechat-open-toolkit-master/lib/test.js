import { wechat_shop_app } from "@/utils/const"
import { updateAuthInfo } from '@/utils/postgrest'
import { isEmpty, isNotEmpty } from '@/wechat_shop/utils/isEmpty'
import { parseXMLSync, buildXMLSync } from "@/wechat_shop/utils/xml"

import Component from "@/wechat_shop/service/component"
import Authorizer from "@/wechat_shop/service/authorizer"
import WechatResponder from '@/wechat_shop/utils/wechatResponder'
import MSG_TPL from "@/wechat_shop/utils/msg_tpl"

const WechatEncrypt = require('wechat-encrypt')
const EventEmitter = require('events')

// 事件列表
const EVENT_COMPONENT_VERIFY_TICKET = 'component_verify_ticket'  // 当微信服务器向第三方服务器推送 ticket 时触发[推送]
const EVENT_AUTHORIZED = 'authorized'                            // 授权成功[推送]
const EVENT_UPDATE_AUTHORIZED = 'updateauthorized'               // 授权更新[推送]
const EVENT_UNAUTHORIZED = 'unauthorized'                        // 授权取消[推送]
const EVENT_COMPONENT_ACCESS_TOKEN = 'component_access_token'    // 当startComponentAccessTokenTimer()函数触发, 在component_access_token需要刷新时触发[触发器刷新] 
const EVENT_AUTHORIZER_ACCESS_TOKEN = 'authorizer_access_token'  // 当startAuthorizerAccessTokenTimer()函数触发, 在access token需要刷新时触发[触发器刷新] 
const EVENT_ERROR = 'error' // 错误

// 全网发布自动化测试的账号 
// 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/operation/thirdparty/releases_instructions.html
const AUTO_TEST_MP_APPID = 'wx570bc396a51b8ff8' // 测试公众号APPID
const AUTO_TEST_MP_NAME = 'gh_3c884a361561' // 测试公众号名称
const AUTO_TEST_MINI_PROGRAM_APPID = 'wxd101a85aa106f53e' // 测试小程序APPID
const AUTO_TEST_MINI_PROGRAM_NAME = 'gh_8dad206e9538' // 测试小程序名称

const AUTO_TEST_TEXT_CONTENT = 'TESTCOMPONENT_MSG_TYPE_TEXT'
const AUTO_TEST_REPLY_TEXT = 'TESTCOMPONENT_MSG_TYPE_TEXT_callback'

class Toolkit extends EventEmitter {
  componentMap = {} // 第三方平台列表
  static instance = null;

  // 初始化
  constructor(options) {
    super(); // 调用父类构造函数

    // 确保只有一个实例
    if (Toolkit.instance) {
      return Toolkit.instance;
    }
    Toolkit.instance = this;

    // 初始化
    const { list } = options;
    this.componentMap = {};
    list.forEach(({ componentAppId, componentAppSecret, token, encodingAESKey }) => {
      this.componentMap[componentAppId] = { componentAppId, componentAppSecret, token, encodingAESKey };
    });

    // 注册事件监听器
    this.on(EVENT_COMPONENT_VERIFY_TICKET, this.#onReceiveComponentVerifyTicket);
    this.on(EVENT_COMPONENT_ACCESS_TOKEN, this.#onRefreshComponentAccessToken);
    this.on(EVENT_AUTHORIZER_ACCESS_TOKEN, this.#onRefreshAuthorizerAccessToken);
    this.on(EVENT_AUTHORIZED, this.#onAuthorized);
    this.on(EVENT_UPDATE_AUTHORIZED, this.#updateauthorized);
    this.on(EVENT_UNAUTHORIZED, this.#onUnauthorized);
  }

  // 当接收到 component_verify_ticket 时触发
  async #onReceiveComponentVerifyTicket(data) {
    try {
      console.log('接收到的组件验证票据:', data);
      await Component.saveComponentVerifyTicket(data.ComponentVerifyTicket);
    } catch (error) {
      throw error;
    }
  }

  // 当第三方平台component_access_token 刷新时触发
  async #onRefreshComponentAccessToken() {
    try {
      console.log('刷新第三方平台component_access_token');
      // 刷新componentAccessToken
      await Component.saveOrRefreshComponentAccessToken()
    } catch (error) {
      throw error;
    }
  }

  // 当授权方access token 刷新时触发
  async #onRefreshAuthorizerAccessToken(authInfo: any) {
    try {
      console.log('刷新 授权方access token');
      const componentAccessToken = await Component.readComponentAccessToken()
      // 发起获取刷新access token的post请求
      const data = await Authorizer.refreshAuthorizerAccessToken(componentAccessToken, authInfo.account, authInfo.shop_id, authInfo.refresh_token)
      if (isEmpty(data)) {
        console.log("发起刷新token请求失败")
        return
      }

      const now = Date.now()
      const et = Math.floor(now / 1000) + data.expires_in
      authInfo.access_token = data.authorizer_access_token
      authInfo.refresh_token = data.authorizer_refresh_token
      authInfo.expired_time = et
      authInfo.refresh_expired_time = et
      authInfo.extra_info = componentAccessToken
      let result = await updateAuthInfo(authInfo)
      console.log("update result", result)
    } catch (error) {
      throw error;
    }
  }

  // 当有新的授权方授权给第三方平台时触发 TODO
  async #onAuthorized(data) {
    try {
      /**
        <xml>
          <AppId>第三方平台appid</AppId>
          <CreateTime>1413192760</CreateTime>
          <InfoType>authorized</InfoType>
          <AuthorizerAppid>公众号appid</AuthorizerAppid>
          <AuthorizationCode>授权码</AuthorizationCode>
          <AuthorizationCodeExpiredTime>过期时间</AuthorizationCodeExpiredTime>
          <PreAuthCode>预授权码</PreAuthCode>
        <xml>
      */
      console.log("当有新的授权方授权给第三方平台时触发")
      const { AppId, AuthorizationCode } = data
      const componentAccessToken = await Component.readComponentAccessToken()  // 从云数据库中读取应用token
      await Authorizer.getAuthorizerAccessToken(AppId, AuthorizationCode, componentAccessToken)
    } catch (error) {
      throw error;
    }
  }

  // 当更新授权方授权给第三方平台时触发
  async #updateauthorized(data) {
    try {
      /**
       <xml>
        <AppId>第三方平台appid</AppId>
        <CreateTime>1413192760</CreateTime>
        <InfoType>updateauthorized</InfoType>
        <AuthorizerAppid>公众号appid</AuthorizerAppid>
        <AuthorizationCode>授权码</AuthorizationCode>
        <AuthorizationCodeExpiredTime>过期时间</AuthorizationCodeExpiredTime>
        <PreAuthCode>预授权码</PreAuthCode>
      <xml>
      */
      console.log("当更新授权方授权给第三方平台时触发")
      const { AppId, AuthorizationCode } = data
      const componentAccessToken = await Component.readComponentAccessToken()  // 从云数据库中读取应用token
      await Authorizer.getAuthorizerAccessToken(AppId, AuthorizationCode, componentAccessToken)
    } catch (error) {
      throw error;
    }
  }

  // 当授权方取消授权时触发
  async #onUnauthorized(data) {
    console.log("当授权方取消授权时触发")
  }

  /**
   * 定时刷新 第三方平台access token(2小时)
   */
  async startComponentAccessTokenTimer() {
    this.emit(EVENT_COMPONENT_ACCESS_TOKEN) // 触发第三方平台access token更新事件
  }

  /**
   * 定时刷新 授权方access token(2小时)
   * @param {object} authInfo 认证信息
   */
  async startAuthorizerAccessTokenTimer(authInfo: any) {
    this.emit(EVENT_AUTHORIZER_ACCESS_TOKEN, authInfo) // 触发授权方access token更新事件
  }

  // 
  /**
   * 返回第三方平台授权事件的中间件, [授权事件接收配置] /callback
   * @param {*} ctx 
   * @document 文档: 
   */
  async events(ctx: FunctionContext) {
    try {
      const { msg_signature, timestamp, nonce } = ctx.query;
      // console.log("参数", ctx.query)
      let xmlStr = ctx.body.xml
      // console.log(xmlStr)
      /**
      {
        appid: [ 'wx7c3ea0da7c15b94f' ],
        encrypt: [
          '6+IrACWACSB2remoy9IY9DyYRo8zR+1KpNGx4GVD76LUdsGHIkBYkBdJlwhtn8U/L/FOYaSoKAYhXI8u4mJKNG/nloy2pHxaZ3cTazJlpvUAoqj0dsDhkANCcxtVbA4vM7EKzicA/SE6jdIswKTmVa5DWJAF3TP2m5AjIkXDRYVDCX1+Qw+skf5IUJI1qGZox44T+4zE88rlaadXuybPaXIv0eEepleLTyjq7lZB/XvqdOkqluHNaLfkn9f/BHYrr/2MDAHXkZnrn7Pw1J7w+z+Zw9OajfI0dxnoSyggvbLS6YXALRBbTWC4DPfi7QbJ9AIrcWgjVnnjWZONQED6rP1aLbufMbTJQQnuTrf9sPoFV8lplgLfA7DVWsFpZbKtvY9rWibRnaXGpPK1z+55BFqHLASTzD02eZEC858Pj7w5XwgRPdJJF3RMdgvgJmD4UiV+xOykqmePVG+wpQQOAQ=='
        ]
      }
      */
      const AppId = xmlStr.appid[0]
      const Encrypt = xmlStr.encrypt[0]
      // 读取第三方平台配置
      let { encodingAESKey, token } = this.componentMap[AppId]
      //  签名校验
      let wechatEncrypt = new WechatEncrypt({ appId: AppId, encodingAESKey, token })
      let signature = wechatEncrypt.genSign({ timestamp, nonce, encrypt: Encrypt })
      if (signature === msg_signature) {
        let str = wechatEncrypt.decode(Encrypt)
        let xml: any = await parseXMLSync(str)  // 解析XML数据成JS对象
        /**
         * {
            AppId: 'wx7c3ea0da7c15b94f',
            CreateTime: '1729149222',
            InfoType: 'component_verify_ticket',
            ComponentVerifyTicket: 'ticket@@@jqiL5oDEy9vu8sI5hZ92-FqtJMkna3o7ABiGp1rKJngMEBMFi-BCw8QzvuGOcczs7WKPfafOQIOiBw5w3nlDKg'
         * }
        */
        let { InfoType } = xml
        console.log("解析XML数据成JS对象: ", xml)
        this.emit(InfoType, xml) // 触发相应事件
        ctx.response.send('success')
      } else {
        console.log('签名校验失败')
      }
    } catch (error) {
      const err = {
        Message: "events函数错误",
        Error: error
      }
      this.emit(EVENT_ERROR, err)
    }
  }

  /**
   * 返回授权方消息处理的中间件,  [消息与事件接收配置] /{APPID}/callback  
   * 代理: engine.xiaoduoai.com/wechat_shop/init/$APPID$/callback
   * @param {string} componentAppId 第三方平台APPID
   * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/Before_Develop/message_push.html
   */
  async message(appId: string, ctx: FunctionContext) {
    const res = ctx.response
    try {
      const { msg_signature, timestamp, nonce } = ctx.query;
      let xmlStr = ctx.body.xml
      const Encrypt = xmlStr.encrypt[0]
      console.log(Encrypt)
      // 读取第三方平台配置
      let { encodingAESKey, token } = this.componentMap[wechat_shop_app.appid]
      //  签名校验
      console.log(appId, encodingAESKey, token, ctx.query)
      let wechatEncrypt = new WechatEncrypt({ appId, encodingAESKey, token })
      let signature = wechatEncrypt.genSign({ timestamp, nonce, encrypt: Encrypt })
      console.log(signature, msg_signature)
      if (signature === msg_signature) {
        let str = wechatEncrypt.decode(Encrypt)
        let xml: any = await parseXMLSync(str)  // 解析XML数据成JS对象
        console.log("解析XML数据成JS对象: ", xml)
        let { FromUserName, ToUserName } = xml

        /* 回复图文消息 */
        const responder = new WechatResponder(wechat_shop_app.appid, encodingAESKey, token);
        const replies = responder.reply(MSG_TPL, FromUserName, ToUserName);
        console.log("回复图文消息", replies);
        res.send(replies);
      } else {
        console.warn('签名校验失败')
        res.send();
      }
    } catch (error) {
      res.send('success') // 当发生错误时，正常响应微信服务器
      const err = {
        Message: "message函数错误",
        Error: error
      }
      this.emit(EVENT_ERROR, err)
    }
  }

  // 返回全网发布测试的中间件
  async autoTest(ctx: FunctionContext) {
    const res: any = ctx.response
    const req: any = ctx.request
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
          const componentAccessToken = await Component.readComponentAccessToken()  // 从云数据库中读取应用token
          // TODO
          let authorizer_access_token = await Authorizer.getAuthorizerAccessToken(wechat_shop_app.appid, componentAccessToken, strList[1])
          let content = `${strList[1]}_from_api`
          let ret = await Authorizer.send(authorizer_access_token, FromUserName, 'text', { content })
          console.log(`\n>>> 测试用例：主动发送客服消息；状态：已发送；响应结果：${JSON.stringify(ret)}；发送内容：${content} <<<\n`)
        }
      } else {
        console.log("检测到非测试消息")
      }
    } catch (error) {
      const err = {
        Message: "返回全网发布测试的中间件错误",
        Error: error
      }
      this.emit(EVENT_ERROR, err)
    }
  }

  /**
   * 返回第三方授权处理的中间件，返回授权URL
   * 
   * @param {string} componentAppId - 第三方平台的应用 ID。
   * @param {string} redirectUrl - 授权成功后的回调地址。
   * @param {number} [authType=Component.AUTH_TYPE_BOTH] - 第三方平台授权类型，默认值为 `Component.AUTH_TYPE_BOTH`。
   *  - 1: 授权方手机端只展示公众号列表
   *  - 2: 授权方手机端只展示小程序列表
   *  - 3: 授权方手机端展示公众号和小程序列表
   *  - 4: 表示小程序推客账号
   *  - 5: 表示视频号账号
   *  - 6: 表示全部，即公众号、小程序、视频号都展示
   * @param {number} [pageStyle=Component.PAGE_STYLE_PC] - 第三方平台授权页样式，默认值为 `Component.PAGE_STYLE_PC`。
   *  - 1: 适用于PC的页面样式
   *  - 2: 适用于移动设备的页面样式
   * @returns {Promise<string>} 返回一个 Promise，解析为授权 URL。
   */
  async auth(componentAppId, redirectUrl, authType = Component.AUTH_TYPE_ALL, pageStyle = Component.PAGE_STYLE_PC) {
    // console.log("componentAccessToken", componentAccessToken)/
    try {
      const componentAccessToken = await Component.readComponentAccessToken()
      console.log("componentAccessToken:", componentAccessToken)
      // 获取预授权码
      const pre_auth_code = await Component.getPreAuthCode(componentAppId, componentAccessToken)
      console.log("预授权码:", pre_auth_code)
      if (isEmpty(pre_auth_code)) {
        return this.emit(EVENT_ERROR, "auth函数, 获取预授权码错误")
      }
      // 获取第三方平台授权URL
      const url = Component.getAuthorizationUrl(componentAppId, pre_auth_code, redirectUrl, authType, pageStyle)
      // console.log(url)
      return url
    } catch (error) {
      const err = {
        Message: "auth函数错误",
        Error: error
      }
      this.emit(EVENT_ERROR, err)
    }
  }

  // // 返回授权方网页授权的中间件
  // oauth(componentAppId, authorizerAppId, redirectUrl, scope = Authorizer.OAUTH_TYPE_BASE, state = '') {
  //   return (req, res) => {
  //     // 获取授权方网页授权URL
  //     let url = Authorizer.getOAuthUrl(componentAppId, authorizerAppId, redirectUrl, scope, state)
  //     res.statusCode = HTTP_STATUS_CODE_REDIRECT
  //     res.setHeader('Location', url)
  //     res.end()
  //   }
  // }
}

// 将 Component 和 Authorizer 的方法添加到 Toolkit 的原型上
Object.assign(Toolkit.prototype, Component);
Object.assign(Toolkit.prototype, Authorizer);
Object.assign(Toolkit.prototype, {
  EVENT_COMPONENT_VERIFY_TICKET, EVENT_AUTHORIZED, EVENT_UPDATE_AUTHORIZED, EVENT_UNAUTHORIZED,
  EVENT_COMPONENT_ACCESS_TOKEN, EVENT_AUTHORIZER_ACCESS_TOKEN
});

let list = [
  {
    componentAppId: wechat_shop_app.appid,
    componentAppSecret: wechat_shop_app.appsecret,
    token: wechat_shop_app.token,
    encodingAESKey: wechat_shop_app.encodingAESKey
  }
]

const toolkitInstance = new Toolkit({ list }); // 创建唯一实例
export default toolkitInstance; // 导出实例