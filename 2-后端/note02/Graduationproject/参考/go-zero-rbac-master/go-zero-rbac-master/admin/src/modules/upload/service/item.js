/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：item.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月16日 00:54:39
 * # 上次修改时间：2021年07月15日 23:23:09
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import { BaseService, Service, Permission } from "@/common/cl-admin";

@Service("app/item")
class SpaceInfo extends BaseService {
	/**
	 * 文件上传，如果模式是 cloud，返回对应参数
	 *
	 * @returns
	 * @memberof CommonService
	 */
	@Permission("upload")
	upload(params) {
		return this.request({
			url: "/upload",
			method: "POST",
			params
		});
	}
}

export default SpaceInfo;
