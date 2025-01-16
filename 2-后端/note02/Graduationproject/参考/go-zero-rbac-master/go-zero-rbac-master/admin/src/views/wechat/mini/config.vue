<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：config.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月12日 20:06:05
  - # 上次修改时间：2021年07月12日 00:13:57
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div class="container">
		<div class="title">小程序配置</div>
		<cl-form class="wechat-config" ref="form" inner />
	</div>
</template>

<script>
export default {
	name: "mini",
	mounted: function() {
		this.$refs.form.create({
			items: [
				{
					label: {
						text: "Appid",
						icon: "el-icon-question",
						tip: "小程序appid"
					},
					prop: "appId",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入小程序appid"
						}
					}
				},
				{
					label: {
						text: "Secret",
						icon: "el-icon-question",
						tip: "小程序密钥"
					},
					prop: "appSecret",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入小程序appid"
						}
					}
				}
			],
			op: {
				saveButtonText: "保存",
				buttons: ["save"]
			},
			on: {
				submit: (data, { close, done }) => {
					this.$service.wechat.config
						.update({
							type: 2,
							data
						})
						.then(res => {
							this.$message.success("保存成功");
						});
					close();
				}
			}
		});

		this.onLoad();
	},
	methods: {
		onLoad() {
			this.$service.wechat.config.info({ type: 2 }).then(res => {
				this.$refs.form.reBindForm(JSON.parse(res));
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.container {
	background: #ffffff;
	height: 100%;
	padding: 20px;
	box-sizing: border-box;
	border-radius: 5px;
	box-shadow: 0 1px 5px 0 #0000000d;
	.title {
		color: #000;
		margin-bottom: 30px;
		font-size: 15px;
	}
	.wechat-config {
		width: 50%;
	}
}
</style>
