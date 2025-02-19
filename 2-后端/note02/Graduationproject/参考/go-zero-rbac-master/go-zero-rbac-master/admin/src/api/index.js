/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：index.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月18日 00:36:12
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import Common from "./common";
import SysUser from "./system/user";
import SysMenu from "./system/menu";
import SysRole from "./system/role";
import SysDept from "./system/dept";
import SysParam from "./system/param";
import SysLog from "./system/log";
import PluginInfo from "./plugin/info";
import GoodsCategory from "./goods/category";
import Goods from "./goods/goods";
import GoodsAttr from "./goods/attr";
import WechatConfig from "@/api/wechat/config";
import WechatMenu from "@/api/wechat/menu";
import WechatReply from "@/api/wechat/replay";
import Material from "@/api/wechat/material/index";
import WechatKeyReply from "@/api/wechat/reply/index";

export default {
	common: new Common(),
	system: {
		user: new SysUser(),
		menu: new SysMenu(),
		role: new SysRole(),
		dept: new SysDept(),
		param: new SysParam(),
		log: new SysLog()
	},
	wechat: {
		config: new WechatConfig(),
		menu: new WechatMenu(),
		material: new Material(),
		reply: new WechatReply(),
		keyReply: new WechatKeyReply()
	},
	plugin: {
		info: new PluginInfo()
	},
	goods: {
		category: new GoodsCategory(),
		goods: new Goods(),
		attr: new GoodsAttr()
	}
};
