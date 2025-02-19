/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：common.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 23:22:14
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import { BaseService, Service } from "@/common/cl-admin";
import md5 from "js-md5";

@Service("sys/common")
class Common extends BaseService {
	/**
	 * 用户登录
	 *
	 * @param {*} { username, password, captchaId, verifyCode }
	 * @returns
	 * @memberof CommonService
	 */
	userLogin({ username, password, captchaId, verifyCode }) {
		password = md5(password);
		return this.request({
			url: "/login",
			method: "POST",
			data: {
				username,
				password,
				captchaId,
				verifyCode
			}
		});
	}

	/**
	 * 图片验证码 svg
	 *
	 * @param {*} { height, width }
	 * @returns
	 * @memberof CommonService
	 */
	captcha({ height, width }) {
		return this.request({
			url: "/captcha",
			params: {
				height,
				width
			}
		});
	}

	/**
	 * 用户退出
	 */
	userLogout() {
		return this.request({
			url: "/logout",
			method: "POST"
		});
	}

	/**
	 * 文件上传模式
	 */
	uploadMode() {
		return this.request({
			url: "/uploadMode"
		});
	}

	/**
	 * 用户信息
	 *
	 * @returns
	 * @memberof CommonService
	 */
	userInfo() {
		return this.request({
			url: "/person"
		});
	}

	/**
	 * 用户信息修改
	 *
	 * @param {*} params
	 * @returns
	 * @memberof CommonService
	 */
	userUpdate(params) {
		return this.request({
			url: "/personUpdate",
			method: "POST",
			data: {
				...params
			}
		});
	}

	/**
	 * 权限信息
	 *
	 * @returns
	 * @memberof CommonService
	 */
	permMenu() {
		return this.request({
			url: "/menus"
		});
	}
}

export default Common;
