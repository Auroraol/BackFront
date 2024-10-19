import cloud from '@lafjs/cloud'
import { succRes, failRes } from '@/utils/internal_response'
// import { loginCheck, getUserValidityInfo } from '@/wechat_shop/utils/service'
const axios = require('axios');
import { readComponentAccessToken, saveOrRefreshComponentAccessTokenDb, getComponentAccessToken } from "@/wechat_shop/utils/wxContent"

import { wechat_shop_app } from "@/utils/const"
import toolkit from '@/wechat_shop/service/toolkit';
// 第三方授权回调  // 实际没走这个
export default async function (ctx: FunctionContext) {
  try {
    console.log("第三方授权回调 ")
    // { auth_code: 'queryauthcode@@@jkpZb8igjNgz8KF9sqT9NFEw2G0gGnvtUPWDdnhs9_5SYiJxdojlnmjNzqg1A0Z2-4pazYnLb5T9KYc4LZgjvQ', expires_in: '3600' }

    // const { code } = ctx.query;
    // // 登录验证
    // const data = await loginCheck(code);  // TODO
    // const { appid, service_id, buyer_type, openid } = data

    // // 获取服务有效期
    // getUserValidityInfo(appid, service_id, buyer_type, openid)  // TODO
    // toolkit.getAuthorizerAccessToken(wechat_shop_app.appid, , )

    // const { AppId, AuthorizationCode } = data
    // const componentAccessToken = await readComponentAccessToken()  // 从云数据库中读取应用token
    // await Authorizer.getAuthorizerAccessToken(AppId, AuthorizationCode, componentAccessToken)
    const { auth_code } = ctx.query
    const componentAccessToken = await readComponentAccessToken()  // 从云数据库中读取应用token
    // await Authorizer.getAuthorizerAccessToken(AppId, auth_code, componentAccessToken)
    return succRes("success")
  } catch (e) {
    console.info("微信小店授权回调失败")
    return failRes(e.message)
  }
}