/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：base.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月17日 12:04:05
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import request from "@/service/request";
import { baseUrl, isDev } from "@/config/env";

export default class BaseService {
	constructor() {
		const crud = {
			page: "page",
			list: "list",
			info: "info",
			add: "add",
			delete: "delete",
			update: "update"
		};

		if (!this.permission) this.permission = {};

		for (let i in crud) {
			if (this.namespace) {
				this.permission[i] = this.namespace.replace(/\//g, ":") + ":" + crud[i];
			} else {
				this.permission[i] = crud[i];
			}
		}
	}

	request(options = {}) {
		if (!options.params) options.params = {};

		let ns = "";

		// 是否 mock 模式
		if (!this.mock) {
			if (isDev) {
				ns = this.proxy || baseUrl;
			} else {
				ns = this.proxy ? this.url : baseUrl;
			}
		}

		// 拼接前缀
		if (this.namespace) {
			ns += "/" + this.namespace;
		}

		// 处理 http
		if (options.url.indexOf("http") !== 0) {
			options.url = ns + options.url;
		}

		return request(options);
	}

	list(params) {
		return this.request({
			url: "/list",
			method: "GET",
			params
		});
	}

	page(data) {
		return this.request({
			url: "/page",
			method: "POST",
			data
		});
	}

	info(params) {
		return this.request({
			url: "/info",
			params
		});
	}

	update(data) {
		return this.request({
			url: "/update",
			method: "PUT",
			data
		});
	}

	delete(data) {
		return this.request({
			url: "/delete",
			method: "DELETE",
			data
		});
	}

	add(data) {
		return this.request({
			url: "/add",
			method: "POST",
			data
		});
	}
}
