import cloud from '@lafjs/cloud'
import { getComponentAccessToken } from "@/wechat_shop/utils/wxContent"
// import { getPreAuthCode, getAuthorizationUrl } from "@/wechat_shop/utils/component"
import { wechat_shop_app } from "@/utils/const"
import { succRes, failRes } from '@/utils/internal_response'
import Component from "@/wechat_shop/service/component"
import toolkit from '@/wechat_shop/service/toolkit';

// 测试
export default async function (ctx: FunctionContext) {
  // ctx.response.status(200).send('EVENT RECEIVED')
  try {
    const res = ctx.response

    let extraInfo = "{\"unit_id\":12312,\"uid\":3222}"

    // 生成第三方授权 URL
    let url = await toolkit.auth(wechat_shop_app.appid, wechat_shop_app.authCallbackUrl);
    url += '& state' + encodeURIComponent(extraInfo)
    console.info(url);

    // 不能直接将授权url复制到浏览器然后打开
    const html = `
      <!DOCTYPE html>
      <html lang="zh">
      <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>授权页面</title>
          <style>
              .center {
                  text-align: center;
                  margin: 20px;
              }
          </style>
      </head>
      <body>
      <div class="center">
          <h2>点击下面的链接进行授权</h2>
          <a href="${url}">授权</a>
      </div>
      </body>
      </html>
    `;

    res.send(html); // 直接发送 HTML
  } catch (err) {
    // 错误处理并渲染错误信息
    console.error('Error:', err);
    ctx.body = failRes(err.message); // 返回错误信息
  }
}

/**
 * 生成第三方应用授权链接
*/
export async function genWechatShopUrl(extraInfo: any, ctx: FunctionContext) {
  try {
    const res = ctx.response
    // 生成授权 URL
    let url = await toolkit.auth(wechat_shop_app.appid, wechat_shop_app.authCallbackUrl);
    url += '& state' + encodeURIComponent(extraInfo)
    console.info(url);

    // 不能直接将授权url复制到浏览器然后打开
    const html = `
      <!DOCTYPE html>
      <html lang="zh">
      <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>授权页面</title>
          <style>
              .center {
                  text-align: center;
                  margin: 20px;
              }
          </style>
      </head>
      <body>
      <div class="center">
          <h2>点击下面的链接进行授权</h2>
          <a href="${url}">授权</a>
      </div>
      </body>
      </html>
    `;

    res.send(html); // 直接发送 HTML
    return url
  } catch (err) {
    // 错误处理并渲染错误信息
    console.error('Error:', err);
  }
}