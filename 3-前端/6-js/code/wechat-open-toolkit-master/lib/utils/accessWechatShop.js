// 购买之后，用户点击“去使用”，会跳转到服务商在创建服务时填写的Url地址，并在path上带上参数code（如果支持移动端，那么会在跳转的小程序Path上带上code参数），
//服务商可依据此code调用【登录验证】接口换取订单信息。
//服务平台提供了【获取服务用户有效期列表】、【获取服务用户有效期】、【获取用户已支付订单列表】三个接口供服务商使用，
// 这三个接口需要使用【服务市场企业主页的access token】。

/**
 * 获取用户已支付订单列表
 * @param {*} componentAccessToken  对于普通业务，access_token为第三方平台component_access_token 对于设计工具类业务，access_token为服务平台的企业主页的access_token
 * @param {*} serviceId 服务id，64位无符号整数，不填或0代表拉取全部服务
 * @param {*} appid 小程序appid，当buyer_type为1时必填
 * @param {*} offset 
 * @param {*} limit 
 * @param {*} openid 用户在服务平台的openid，当buyer_type为2时必填
 * @param {*} buyerType 买家类型，1是小程序，2是微信用户，默认为1
 * @returns 
 * @document 文档: https://developers.weixin.qq.com/doc/oplatform/service_market/interface/value_added/servicemarket_get_paid_order_list.html
 */
async function getPaidOrderList(componentAccessToken: string, serviceId: number,
   appid: string, offset: number, limit: number, openid: string, buyerType: number) {
    try {
      let url = "https://api.weixin.qq.com/wxa/servicemarket/get_paid_order_list";
      const query = { access_token: componentAccessToken }
      url += '?' + querystring.stringify(query)
  
      const body = {
        appid: appid,
        service_id: serviceId,
        offset: offset,
        limit: limit,
        openid: openid,
        buyer_type: buyerType
      };
      const { data } = await axios.post(url, body);
  
      console.log("获取用户已支付订单列表", data)
      return data;
    } catch (error) {
      throw error;
    }
}
