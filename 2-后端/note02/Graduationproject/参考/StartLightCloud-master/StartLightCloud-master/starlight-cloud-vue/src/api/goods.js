import request from "./index.js";


// 获取全部商品数据
export function getAllGoodsInfoAPI(data) {
    return request({
        url: '/goods/getAllGoodsInfo',
        method: 'get',
        params: data
    })
}

// 购买商品(SCoin)
export function buyGoodsBySCoinAPI(data) {
    return request({
        url: '/goods/buyGoodsBySCoin',
        method: 'post',
        params: data
    })
}

// 购买商品(CNY)
export function buyGoodsByCNYAPI(data) {
    return request({
        url: '/goods/buyGoodsByCNY',
        method: 'post',
        params: data
    })
}