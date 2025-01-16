/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：request.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月18日 00:36:58
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import axios from "axios";
import { Message } from "element-ui";
import store from "@/store";
import { isDev } from "@/config/env";
import { storage, href } from "@/common/cl-admin/utils";
import NProgress from "nprogress";
import "nprogress/nprogress.css";

axios.defaults.timeout = 30000;
axios.defaults.withCredentials = true;

NProgress.configure({
	showSpinner: false
});

// 忽略规则
const ignore = {
	NProgress: ["/sys/info/record"],
	token: ["/login", "/captcha"]
};

// 请求队列
let requests = [];

// Token 是否刷新中
let isRefreshing = false;

// Request
axios.interceptors.request.use(
	config => {
		const token = store.getters.token || "";

		if (config.url) {
			if (!ignore.token.some(e => config.url.includes(e))) {
				config.headers["Authorization"] = token;
			}

			if (!ignore.NProgress.some(e => config.url.includes(e))) {
				NProgress.start();
			}
		}

		// 请求信息
		if (isDev) {
			console.group(config.url);
			console.log("method:", config.method);
			console.log("token:", token);
			console.table("data:", config.method == "get" ? config.params : config.data);
			console.groupEnd();
		}
		return config;
	},
	error => {
		return Promise.reject(error);
	}
);

// Response
axios.interceptors.response.use(
	res => {
		NProgress.done();
		if (res.headers["new-token"]) {
			let tokenObj = {
				token: res.headers["new-token"]
			};
			store.commit("SET_TOKEN", tokenObj);
		}
		const { code, data, msg } = res.data;

		if (!res.data) {
			return res;
		}

		switch (code) {
			case 200:
				return data;
			default:
				return Promise.reject(msg);
		}
	},
	async error => {
		NProgress.done();

		if (error.response) {
			const { status, config, data } = error.response;

			switch (status) {
				case 401:
					Message.error("账号在别处登录或登录已失效，请重新登录");
					await store.dispatch("userRemove");
					setTimeout(() => {
						href("/login");
					}, 3000);
					break;

				case 403:
					if (isDev) {
						Message.error(`${config.url} 无权限访问！！`);
					} else {
						href("/403");
					}
					break;

				case 404:
					break;

				case 500:
					if (!isDev) {
						href("/500");
					}
					break;

				case 502:
					if (isDev) {
						Message.error(`${config.url} 服务异常！！`);
					} else {
						href("/502");
					}
					break;
			}
		}

		return Promise.reject(error.response.data.msg);
	}
);

export default axios;
