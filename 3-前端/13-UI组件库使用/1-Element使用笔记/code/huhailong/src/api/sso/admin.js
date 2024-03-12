import {get, post} from '../../util/http'

export const getUserVOList = param => get('auth/admin/user/getVOList', param);

export const getRoleList = param => get('auth/admin/role/list', param);

export const setUserVO = param => post('auth/admin/user/setUserVO', param);

export const lockUser = param => post('auth/admin/user/lockUser', param);

export const unLockUser = param => post('auth/admin/user/unLockUser', param);

export const disenableUser = param => post('auth/admin/user/disableUser', param);

export const enableUser = param => post('auth/admin/user/enableUser', param);

export const delRole = param => post('auth/admin/role/del', param);

export const setRole = param => post('auth/admin/role/set', param);

export const addRole = param => post('auth/admin/role/add', param);

export const getApiUrlList = param => post('auth/admin/apiUrl/list', param);

export const delApiUrl = param => post('auth/admin/apiUrl/del', param);

export const setApiUrl = param => post('auth/admin/apiUrl/set', param);

export const addApiUrl = param => post('auth/admin/apiUrl/add', param);

export const addDevLog = param => post('auth/admin/devLog/add', param);

export const delDevLog = param => post('auth/admin/devLog/del', param);

export const getDevLogList = param => get('auth/admin/devLog/list', param);

export const getSysLogList = param => post('auth/admin/sysLog/list', param);