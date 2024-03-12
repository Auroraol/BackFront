import {form, get, post} from '../../util/http'

export const login = param => form('auth/login', param);

export const logout = param => post('auth/logout', param);

export const test = param => get('auth/test/test', param);

export const addUser = param => post('auth/user/add', param);

export const setPassword = param => post('auth/user/setPassword', param);

export const getCaptcha = param => get('auth/api/captcha',param);

export const enableUserAgain =param => get('auth/api/enableUserAgain', param);

export const findPassword = param => get('auth/api/findPassword', param);

export const checkFindPasswordCode = param => get('auth/api/checkFindPasswordCode', param);

export const getUserInfo = param => get('auth/userInfo/getUserInfo',param);

export const setUserInfo = param => post('auth/userInfo/set', param);