import request from "./index.js";


// 获取全部商品数据
export function getUserOrderListAPI(data) {
    return request({
        url: '/order/getUserOrderList',
        method: 'get',
        params: data
    })
}

// 取消订单
export function cancelOrderAPI(data) {
    return request({
        url: '/order/cancelOrder',
        method: 'post',
        params: data
    })
}

// 获取订单支付链接
export function getOrderPayUrlFromRedisAPI(data) {
    return request({
        url: '/order/getOrderPayUrlFromRedis',
        method: 'get',
        params: data
    })
}

