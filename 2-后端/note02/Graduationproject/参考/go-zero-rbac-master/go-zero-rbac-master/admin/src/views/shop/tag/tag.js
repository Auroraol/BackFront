/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：tag.js  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月18日 00:36:58
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

import "./tag.scss";

export default {
	name: "GoodsTag",
	components: {},
	data() {
		return {
			isCreateTag: false, // 是否点击创建新的标签
			tagName: "", // 分组名称
			visible: {
				// 修改名称
			},
			// table数据列表
			list: [
				{
					categoryName: "底妆",
					createdTime: "2019-08-21 23:20:19",
					goodsCount: 2
				},
				{
					categoryName: "妆前乳",
					createdTime: "2019-08-24 13:21:32",
					goodsCount: 20
				}
			],
			// EVue table option 配置 http://evue.top/#/table/doc
			option: {
				menuDeleteOption: {
					type: "text"
				},
				menuEditOption: {
					type: "text"
				},
				isPagination: true,
				paginationTotal: 2,
				paginationCurrent: 1,
				paginationSize: 20,
				isMenuEdit: false,
				isMenuDelete: false,
				column: [
					{
						label: "分组名称",
						prop: "categoryName",
						slotName: "categoryName"
					},
					{
						label: "商品数",
						prop: "goodsCount"
					},
					{
						label: "创建时间",
						prop: "createdTime"
					}
				]
			}
		};
	},
	computed: {},
	methods: {
		/** 跳转编辑页面*/
		goCreatePage() {
			this.$router.push({
				name: "featureCreate"
			});
		},
		/**
		 * 开始新增
		 * item 编辑模式下当条的数据
		 * type Boolean 是否点击了确定
		 * $isEdit 编辑模式
		 * */
		startCreate(item, type, $isEdit = false) {
			if (!this.tagName && type) {
				this.$message.error("请输入名称");
				return;
			}
			if ($isEdit) {
				// 编辑模式
				this.visible[item.index] = !this.visible[item.index] || false;
			} else {
				this.isCreateTag = false;
			}
			if (!type) return;
			if ($isEdit) {
				// 编辑模式
				item.data.categoryName = this.tagName;
			} else {
				// 新增模式
				this.list.push({
					categoryName: this.tagName,
					createdTime: "2019-08-21 23:20:19",
					goodsCount: 0
				});
			}

			this.tagName = "";
			this.option.paginationTotal = this.list.length;
		},
		// 删除
		del(item) {
			this.$confirm("确定要删除吗?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning"
			})
				.then(() => {
					this.list.splice(item.index, 1);
				})
				.catch(() => {});
		},
		/**
		 * 编辑模式下popover组件显示事件
		 * 主要为了复制 tagName
		 * */
		popEditShow(item) {
			this.tagName = item.data.categoryName;
		}
	}
};
