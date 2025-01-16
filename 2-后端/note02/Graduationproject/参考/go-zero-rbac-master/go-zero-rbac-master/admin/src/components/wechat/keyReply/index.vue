<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：index.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月20日 23:41:30
  - # 上次修改时间：2021年07月20日 00:37:11
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
			<cl-table v-bind="table" :auto-height="false">
				<template #column-match="{ scope }">
					<el-tag
						size="mini"
						effect="dark"
						v-if="!scope.row.match"
						style="margin-left: 10px"
					>
						半匹配
					</el-tag>
					<el-tag
						size="mini"
						effect="dark"
						type="success"
						v-else
						style="margin-left: 10px"
					>
						全匹配
					</el-tag>
				</template>
			</cl-table>
		</el-row>

		<el-row type="flex">
			<cl-flex1 />
			<cl-pagination />
		</el-row>

		<cl-upsert ref="upsert" v-bind="upsert"></cl-upsert>
	</cl-crud>
</template>

<script>
export default {
	name: "wechat-key-reply",
	data() {
		return {
			upsert: {
				props: {
					width: "800px"
				},
				items: [
					{
						prop: "name",
						label: "规则名称",
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
						prop: "match",
						label: "匹配方式",
						span: 12,
						value: false,
						component: {
							name: "el-select",
							options: [
								{
									value: true,
									label: "全匹配"
								},
								{
									value: false,
									label: "半匹配"
								}
							]
						},
						rules: {
							required: true,
							message: "关键词不能为空且最多30个字"
						}
					},
					{
						prop: "key",
						label: "关键词",
						span: 12,
						component: {
							name: "el-input",
							attrs: {
								placeholder: "请输入关键词"
							}
						},
						rules: {
							required: true,
							message: "关键词不能为空且最多30个字"
						}
					},
					{
						prop: "mediaId",
						label: "回复内容",
						value: null,
						component: ({ h, scope }) => {
							return h("wechat-key-word-select", {
								props: {
									value: scope.mediaId,
									type: scope.type,
									url: scope.url,
									text: scope.text
								},
								attrs: {
									placeholder: "请填写内容"
								},
								on: {
									changeMediaId: val => {
										this.updateMediaId(val);
									},
									changeType: val => {
										this.updateType(val);
									},
									changeText: val => {
										this.updateText(val);
									},
									changeUrl: val => {
										this.updateUrl(val);
									}
								}
							});
						}
					},
					{
						prop: "type",
						component: {
							name: "el-input",
							style: {
								display: "none"
							}
						}
					},
					{
						prop: "text",
						component: {
							name: "el-input",
							style: {
								display: "none"
							}
						}
					},
					{
						prop: "url",
						component: {
							name: "el-input",
							style: {
								display: "none"
							}
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
						label: "规则名称",
						align: "center",
						"min-width": 150
					},
					{
						prop: "key",
						label: "关键词",
						align: "center",
						"min-width": 120
					},
					{
						prop: "match",
						label: "匹配类型",
						align: "center",
						"min-width": 120
					},
					{
						prop: "type",
						label: "回复类型",
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
			ctx.service(this.$service.wechat.keyReply).done();

			app.refresh();
		},
		updateMediaId(val) {
			this.$refs["upsert"].setForm("mediaId", val);
		},
		updateType(val) {
			this.$refs["upsert"].setForm("type", val);
		},
		updateText(val) {
			this.$refs["upsert"].setForm("text", val);
		},
		updateUrl(val) {
			console.log(val);
			this.$refs["upsert"].setForm("url", val);
		}
	}
};
</script>
<style lang="scss" scoped>
.el-form .el-row .el-col:nth-last-child(1) {
	display: none;
}
</style>
