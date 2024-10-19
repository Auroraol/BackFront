/** 处理授权方相关函数 */
import { wechat_shop_app } from "@/utils/const"
import { isEmpty, isNotEmpty } from '@/wechat_shop/utils/isEmpty'

const querystring = require('querystring')
const axios = require('axios');

// 授权方网页授权类型
const OAUTH_TYPE_BASE = 'snsapi_base' // 基本授权可以得到用户openId
const OAUTH_TYPE_USERINFO = 'snsapi_userinfo' // 用户信息授权可以得到用户openId、unionId、头像和昵称

/**
 * 获取授权信息
 * 从授权回调中获得auth_code授权码，来获取授权方的授权信息
 * @param {string} componentAppId 第三方平台APPID
 * @param {string} componentAccessToken 第三方平台access token
 * @param {string} authorizationCode 授权码
 * @document 文档:https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/ThirdParty/token/authorization_info.html
 */
async function getAuthorizerAccessToken(componentAppid: string, authorizationCode: string, componentAccessToken: string) {
  try {
    let url = wechat_shop_app.getAuthorizerAccessTokenByAuthorizationCodeUrl;
    const query = { component_access_token: componentAccessToken }
    url += '?' + querystring.stringify(query)

    const body = {
      component_appid: componentAppid,
      authorization_code: authorizationCode,
    };

    const { data } = await axios.post(url, body);
    console.log("获取授权信息",data)
    /**
    {
      authorization_info: {
        authorizer_appid: 'wx505c82c29ab5addf',
        authorizer_access_token: '85_uhfFVeg9e5qTx4ckgXAVSu7wUW8zA8zZiJgxm6h_pap9t-Bd1furXHpIqU0t0XcydsS14jssPROOFsbcJ66S1NSvDrFehEZHb-xWWu-O7pK6iwNKJHY26Nsi-Y5sbVVYkRDlVW5zARBy_23VZECcAMDFKI',
        expires_in: 7200,
        authorizer_refresh_token: 'refreshtoken@@@Eftg_BLmoPNDy_8CbQT76QRcLZqE93Ra9iZekDuncT4',
        func_info: [
          { funcscope_category: { id: 17 } },
          {
            funcscope_category: { id: 18 },
            confirm_info: { need_confirm: 0, already_confirm: 0, can_confirm: 0 }
          },
          {
            funcscope_category: { id: 30 },
            confirm_info: { need_confirm: 0, already_confirm: 0, can_confirm: 0 }
          },
          {
            funcscope_category: { id: 49 },
            confirm_info: { need_confirm: 0, already_confirm: 0, can_confirm: 0 }
          }
        ]
      }
    }
    */
    // 保存授权信息 
    await saveAuthorizationInformation(data, componentAppid, componentAccessToken)
  } catch (error) {
    console.error('登录验证 失败:', error);
    throw error;
  }
}

/**
 * 获取/刷新接口调用令牌
 * @param {string} componentAccessToken 第三方平台component_access_token
 * @param {string} componentAppid 第三方平台appid
 * @param {string} authorizerAppid 授权方appid
 * @param {string} authorizerRefreshToken 授权方刷新令牌
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/ThirdParty/token/api_authorizer_token.html
 */
async function refreshAuthorizerAccessToken(componentAccessToken: string, componentAppid: number, authorizerAppid: number, authorizerRefreshToken: string) {
  try {
    let url = wechat_shop_app.refreshAuthorizerAccessTokenUrl;
    const query = { component_access_token: componentAccessToken }
    url += '?' + querystring.stringify(query)

    const body = {
      component_appid: componentAppid,
      authorizer_appid: authorizerAppid,
      authorizer_refresh_token: authorizerRefreshToken
    };
    const { data } = await axios.post(url, body);

    console.log(data)
    return data;
  } catch (error) {
    throw error;
  }
}

/**
 * 获取授权账号详情
 * @param {string} componentAppId 第三方平台APPID
 * @param {string} componentAccessToken 第三方平台 access token
 * @param {string} authorizerAppId 授权方APPID
 */
async function getAuthorizerInfo(componentAppId, componentAccessToken, authorizerAppId) {
  try {
    let url = 'https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info'
    let query = { component_access_token: componentAccessToken }
    let body = { component_appid: componentAppId, authorizer_appid: authorizerAppId }
    url += '?' + querystring.stringify(query)

    const { data } = await axios.post(url, body)
    console.log("获取授权账号详情", data)
    return data
  } catch (error) {
    throw error;
  }
}

/**
 * 发送客服消息
 * @param {string} authorizerAccessToken 授权方access token
 * @param {string} openId 微信用户openId
 * @param {string} type 消息类型
 * @param {Object} content 消息主体
 */
async function send(authorizerAccessToken, openId, type, content) {
  try {
    let url = 'https://api.weixin.qq.com/cgi-bin/message/custom/send'
    let query = {
        access_token: authorizerAccessToken
    }
    let body = {
        touser: openId,
        msgtype: type,
        [type]: content
    }
    url += '?' + querystring.stringify(query)

    const { data } = await axios.post(url, body)
    console.log("发送客服消息", data)
    return data
  } catch (error) {
    throw error;
  }
}

// 保存授权信息
async function saveAuthorizationInformation(data: any, componentAppid: string, componentAccessToken: string) {
  try {
    const { authorizer_appid, authorizer_access_token, expires_in, authorizer_refresh_token } = data
    // 使用postgrest存储
    //授权信息，查看appAuthToken是否存在，存在则更新，不存在则添加
    const now = Date.now()
    const et = Math.floor(now / 1000) + expires_in

    const authInfo = {
      // unit_id: extraInfo.unit_id, //state.unit_id
      // channel_id: extraInfo.channel_id,
      shop_id: authorizer_appid,  // 授权方 appid
      channel_type: wechat_shop_app.channel_type.toString(),
      access_token: authorizer_access_token,    //接口调用令牌（在授权的公众号/小程序具备 API 权限时，才有此返回值）
      expired_time: et,   //正常情况下为7200秒（2小时），有效期内重复获取返回相同结果
      refresh_token: authorizer_refresh_token,  //刷新令牌
      refresh_expired_time: 4068115200,         //access_token有效时间，设置为2099年
      country: "", // 地区
      account: componentAppid, //第三方平台appid
      extra_info: componentAccessToken // 第三方应用令牌
    }
    console.log("authInfo=", authInfo)
    // //查看数据库是否有该企业的消息
    // let authInfoExist = await getAuthInfo(0, authInfo.shop_id)
    // console.log("authInfoExist: ", authInfoExist)
    // if (authInfoExist) {  //存在则更新信息
    //   // authInfoExist.unit_id = authInfo.unit_id
    //   // authInfoExist.channel_id = authInfo.channel_id
    //   authInfoExist.shop_id = authInfo.shop_id
    //   authInfoExist.access_token = authInfo.access_token
    //   authInfoExist.expired_time = authInfo.expired_time
    //   authInfoExist.refresh_token = authInfo.refresh_token
    //   authInfoExist.refresh_expired_time = authInfo.refresh_expired_time
    //   authInfoExist.country = authInfo.country
    //   authInfoExist.extra_info = authInfo.extra_info
    //   let result = await updateAuthInfo(authInfoExist)
    //   console.log("update result", result)
    // } else {
    //   //不存在，添加企业授权信息
    //   let result = await addAuthInfo(authInfo)
    //   console.log("insert result", result)
    // }

    // // 调 cvd 接口，把认证信息返回给 cvd
    // let cvdAuthCallbackReq = await {
    //   // "uid": extraInfo.uid,      //state.uid
    //   "channel_type": wechat_shop_app.channel_type,
    //   // "extra_info": state,

    //   "seller_info": {
    //     "seller_id": authInfo.shop_id.toString(),
    //     "name": "TODO"   // TODO
    //   }
    // }

    // // 线上还是测试
    // var url = cvd.test_host + cvd.auth_callback_path
    // // var url = cvd.host + cvd.auth_callback_path

    // console.log("cvd request: ", cvdAuthCallbackReq)
    // console.log("cvd request url:", url)

    // const cvdRes = await fetch(url, {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json'
    //   },
    //   body: JSON.stringify(cvdAuthCallbackReq)
    // })

    // let cvdResObj = await cvdRes.json()

    // console.log("cvdResObj: ", cvdResObj)
    // if (!cvdResObj.err_code) {
    //   console.log(`request cvd failed, ${cvdResObj.msg}`)
    // }
    //保存完成之后，返回回调成功信息，否则返回失败信息
  } catch (error) {
    throw error;
  }
}

// 默认导出一个对象
export default {
  getAuthorizerAccessToken,
  refreshAuthorizerAccessToken,
  send
};


