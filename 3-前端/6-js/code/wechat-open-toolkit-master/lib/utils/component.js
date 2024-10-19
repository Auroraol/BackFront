import cloud from '@lafjs/cloud'
import { isEmpty, isNotEmpty } from '@/wechat_shop/utils/isEmpty'
import { wechat_shop_app } from "@/utils/const"

const querystring = require('querystring')
const axios = require('axios');

const db = cloud.database();

// 第三方平台授权类型列表
const AUTH_TYPE_MP = 1;               // 授权方手机端只展示公众号列表
const AUTH_TYPE_MINI_PROGRAM = 2;     // 授权方手机端只展示小程序列表
const AUTH_TYPE_BOTH = 3;             // 授权方手机端展示公众号和小程序列表
const AUTH_TYPE_MINI_PROGRAM_AGENT = 4; // 授权方手机端展示小程序推客账号
const AUTH_TYPE_VIDEO_ACCOUNT = 5;     // 授权方手机端展示视频号账号
const AUTH_TYPE_ALL = 6;               // 授权方手机端展示全部（公众号、小程序、视频号）


// 第三方平台授权页样式
const PAGE_STYLE_PC = 1             // 适用于PC的页面样式
const PAGE_STYLE_MOBILE = 2         // 适用于移动设备的页面样式
const PAGE_SIZE = 20                // 获取第三方平台已授权账号列表的分页大小


/**
 * 读取 SuiteTicket
 */
async function readComponentVerifyTicket(): Promise<string | null> {
  try {
    const res = await db.collection('component_verify_ticket').get();
    if (isNotEmpty(res.data)) {
      return res.data.at(0).component_verify_ticket
    }
    return null;
  } catch (error) {
    console.error('读取 component_verify_ticket 失败:', error);
    return null;
  }
}

/**
 * 保存 component_verify_ticket 有效时间为12小时
 */
async function saveComponentVerifyTicket(componentVerifyTicket: string): Promise<boolean> {
  try {
    let docId = '-1'; // 特定文档ID
    // 更新指定文档
    await db.collection('component_verify_ticket').doc(docId).set({ component_verify_ticket: componentVerifyTicket });
    console.info('保存 component_verify_ticket :', componentVerifyTicket);
    return true;
  } catch (error) {
    console.error('保存 component_verify_ticket 失败:', error);
    return false;
  }
}

/**
 * 通过数据库保存/刷新 component_access_token
 * 需要设置定时器每隔两小时触发
 */
async function saveOrRefreshComponentAccessToken(): Promise<boolean> {
  try {
    let docId = '-1'; // 特定文档ID
    const componentAccessToken = await getComponentAccessToken()  // 获得第三方应用令牌
    // 更新指定文档
    await db.collection('component_access_token').doc(docId).set({ component_access_token: componentAccessToken });
    return true;
  } catch (error) {
    console.error('保存 component_access_token 失败:', error);
    return false;
  }
}

/**
 * 通过数据库读取 component_access_token 
 */
async function readComponentAccessTokenByDb(): Promise<string | null> {
  try {
    const res = await db.collection('component_access_token').get();
    if (isNotEmpty(res.data)) {
      return res.data.at(0).component_access_token
    }
    return null;
  } catch (error) {
    console.error('读取 component_access_token 失败:', error);
    return null;
  }
}

/** 
 *获取 第三方应用令牌（component_access_token）函数  有效时间为2小时
 * @param {string} 
 * @param {string} 
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/ThirdParty/token/component_access_token.html
*/
async function getComponentAccessToken() {
  try {
    const url = wechat_shop_app.componentAccessTokenUrl;
    const component_verify_ticket = await readComponentVerifyTicket();
    if (isEmpty(component_verify_ticket)) {
      console.log("component_access_token 不存在")
      return
    }
    const body = {
      component_appid: wechat_shop_app.appid,
      component_appsecret: wechat_shop_app.appsecret,
      component_verify_ticket: component_verify_ticket
    };
    const { data } = await axios.post(url, body);
    return data.component_access_token;
  } catch (error) {
    throw error;
  }
};

/**
 * 读取 component_access_token, 没有会调用获取第三方应用令牌api, 保存component_access_token到云数据库
 */
async function readComponentAccessToken() {
  try {
    let componentAccessToken
    componentAccessToken = await readComponentAccessTokenByDb()  // 从云数据库中读取应用token
    if (isEmpty(componentAccessToken)) {
      const ok = await saveOrRefreshComponentAccessToken()
      if (!ok) {
        console.log('更新令牌失败');
        return;
      }
      componentAccessToken = await readComponentAccessTokenByDb()
    }
    return componentAccessToken;
  } catch (error) {
    console.error('读取 component_access_token 失败:', error);
  }
}

/**
 * 获取第三方平台预授权码
 * @param {string} componentAppId 第三方平台APPID
 * @param {string} componentAccessToken 第三方平台access token
 * @return pre_auth_code 预授权码
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/ThirdParty/token/pre_auth_code.html
 */
async function getPreAuthCode(componentAppId, componentAccessToken) {
  try {
    let url = wechat_shop_app.getPreAuthCodeUrl
    let query = { component_access_token: componentAccessToken }
    url += '?' + querystring.stringify(query)

    const body = {
      component_appid: componentAppId
    };
    const { data } = await axios.post(url, body);
    console.info(data)
    if (isEmpty(data.pre_auth_code)) {
      return ''
    }
    return data.pre_auth_code
  } catch (error) {
    console.error('登录验证 失败:', error);
    throw error;
  }
}

/**
 * 获取第三方平台授权URL
 * @param {string} componentAppId 第三方平台APPID
 * @param {string} preAuthCode 第三方平台预授权码
 * @param {string} redirectUrl 授权后的重定向地址
 * @param {number|string} authType 授权类型；bizAppId 指定授权方APPID - 1 表示手机端仅展示公众号；2 表示仅展示小程序，3 表示公众号和小程序都展示。- 4表示小程序推客账号；- 5表示视频号账号；- 6表示全部，即公众号、小程序、视频号都展示
 * @param {number} pageStyle 页面风格
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/Before_Develop/Authorization_Process_Technical_Description.html
 */
function getAuthorizationUrl(componentAppId, preAuthCode, redirectUrl, authType, pageStyle) {
  let url = {
    [PAGE_STYLE_PC]: 'https://mp.weixin.qq.com/cgi-bin/componentloginpage',
    [PAGE_STYLE_MOBILE]: 'https://mp.weixin.qq.com/safe/bindcomponent'
  }[pageStyle]
  console.log(componentAppId)
  let query = { component_appid: componentAppId, pre_auth_code: preAuthCode, redirect_uri: redirectUrl }
  console.log(query)
  if (typeof authType === 'number') {
    Object.assign(query, { auth_type: authType })
  } else if (typeof authType === 'string') {
    Object.assign(query, { biz_appid: authType })
  }

  if (pageStyle === PAGE_STYLE_MOBILE) {
    Object.assign(query, { action: 'bindcomponent', no_scan: 1 })
    url += '?' + querystring.stringify(query) + '#wechat_redirect'
  } else if (pageStyle === PAGE_STYLE_PC) {
    url += '?' + querystring.stringify(query)
  }
  // console.log(url)
  return url
}

/**
 * 获取第三方平台的授权方列表 (没有使用)
 * @param {string} componentAppId 第三方平台APPID
 * @param {string} componentAccessToken 第三方平台access token
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/2.0/api/ThirdParty/Account_Authorization/api_get_authorizer_list.html#%E8%AF%B7%E6%B1%82%E5%9C%B0%E5%9D%80
 */
async function getAuthorizerList(componentAppId, componentAccessToken, offset) {
  let url = 'https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list'
  let query = { component_access_token: componentAccessToken }
  let body = { component_appid: componentAppId, offset, count: PAGE_SIZE }
  url += '?' + querystring.stringify(query)

  const { data } = await axios.post(url, body);
  return data
}

// 默认导出一个对象
export default {
  saveComponentVerifyTicket,
  saveOrRefreshComponentAccessToken,
  readComponentAccessToken,
  getPreAuthCode,
  getAuthorizationUrl,

  AUTH_TYPE_MP,
  AUTH_TYPE_MINI_PROGRAM,
  AUTH_TYPE_BOTH,
  AUTH_TYPE_MINI_PROGRAM_AGENT,
  AUTH_TYPE_VIDEO_ACCOUNT,
  AUTH_TYPE_ALL,
  PAGE_STYLE_PC,
  PAGE_STYLE_MOBILE,
  PAGE_SIZE
};
