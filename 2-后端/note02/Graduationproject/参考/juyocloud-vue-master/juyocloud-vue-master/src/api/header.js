import axios from '@/http/request'

// 重置密码
export const resetPasswordReq = (data) => {
	return axios({
		url: '/site/reset-password',
		method: 'get',
		data,
	})
}

// 获取已启用的存储源列表
export const getSourceListReq = (data) => {
	return axios({
		url: '/storage/list',
		method: 'get',
		data,
	})
}
