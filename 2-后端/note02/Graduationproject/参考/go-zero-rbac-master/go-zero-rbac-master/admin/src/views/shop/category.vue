<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：category.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月18日 00:36:58
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<cl-crud @load="onLoad">
		<el-row type="flex">
			<cl-refresh-btn />
			<cl-add-btn />
			<cl-multi-delete-btn />
			<cl-flex1 />
			<cl-search-key />
		</el-row>

		<el-row>
			<cl-table ref="table" v-bind="table"></cl-table>
		</el-row>

		<el-row type="flex">
			<cl-flex1 />
			<cl-pagination />
		</el-row>

		<cl-upsert v-model="form" v-bind="upsert"></cl-upsert>
	</cl-crud>
</template>

<script>
export default {
	name: "category",
	data() {
		return {
			form: {
				relevance: 1
			},
			upsert: {
				props: {
					width: "800px"
				},
				items: [
					{
						prop: "category_name",
						label: "分类名称",
						span: 12,
						component: {
							name: "el-input",
							attrs: {
								placeholder: "请填写名称"
							}
						},
						rules: {
							required: true,
							message: "名称不能为空"
						}
					},
					{
						label: "上级分类",
						prop: "departmentIdList",
						value: [],
						component: {
							name: "cl-goods-category"
						}
					}
				]
			},
			table: {
				props: {
					"default-sort": {
						prop: "createTime",
						order: "descending"
					}
				},
				columns: [
					{
						type: "selection",
						align: "center",
						width: "60"
					},
					{
						prop: "name",
						label: "名称",
						align: "center",
						"min-width": 150
					},
					{
						prop: "label",
						label: "标识",
						align: "center",
						"min-width": 120
					},
					{
						prop: "remark",
						label: "备注",
						align: "center",
						"show-overflow-tooltips": true,
						"min-width": 150
					},
					{
						prop: "createTime",
						label: "创建时间",
						align: "center",
						sortable: "custom",
						"min-width": 150
					},
					{
						prop: "updateTime",
						label: "更新时间",
						align: "center",
						sortable: "custom",
						"min-width": 150
					},
					{
						label: "操作",
						align: "center",
						type: "op"
					}
				]
			}
		};
	},

	methods: {
		onLoad({ ctx, app }) {
			ctx.service(this.$service.goods.category).done();
			app.refresh();
		}
	}
};
</script>

<style scoped></style>
