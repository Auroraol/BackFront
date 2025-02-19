import request from "./index.js";


// 用户登录
export function userLoginAPI(data) {
    return request({
        url: '/user/login',
        method: 'post',
        params: data
    })
}

// 用户注册
export function userRegisterAPI(data) {
    return request({
        url: '/user/register',
        method: 'post',
        params: data
    })
}

// 获取用户信息
export function getUserInfoAPI(data) {
    return request({
        url: '/user/getUserInfo',
        method: 'get',
        params: data
    })
}

// 获取用户头像
export function getProfileAPI(data) {
    return request({
        url: '/user/getProfile',
        method: 'get',
        params: data
    })
}

// 获取邮箱注册验证码请求
export function getRegisterVerifyCodeAPI(data) {
    return request({
        url: '/user/getRegisterVerifyCode',
        method: 'get',
        params: data
    })
}