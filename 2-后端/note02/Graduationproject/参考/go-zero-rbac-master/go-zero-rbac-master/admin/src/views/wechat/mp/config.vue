<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：config.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月16日 00:54:39
  - # 上次修改时间：2021年07月14日 17:54:08
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div class="container">
		<div class="title">公众号配置</div>
		<cl-form class="wechat-config" ref="form" inner>
			<template #slot-url="{ scope }">
				<el-button class="copy" v-copy="scope.url">{{ scope.url }}</el-button>
			</template>
		</cl-form>
	</div>
</template>

<script>
export default {
	name: "crud-form-inner",
	mounted: function() {
		this.$refs.form.create({
			items: [
				{
					label: {
						text: "Appid",
						icon: "el-icon-question",
						tip: "公众号id"
					},
					prop: "appId",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入微信appid"
						}
					}
				},
				{
					label: {
						text: "Secret",
						icon: "el-icon-question",
						tip: "公众号密钥"
					},
					prop: "appSecret",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入微信appSecret"
						}
					}
				},
				{
					label: {
						text: "URL",
						icon: "el-icon-question",
						tip: "微信公众号对接url，http或https自行增加"
					},
					prop: "url",
					component: {
						name: "slot-url"
					}
				},
				{
					label: {
						text: "微信密钥",
						icon: "el-icon-question",
						tip: "EncodedAESKey，用于消息解码"
					},
					prop: "EncodingAESKey",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入微信token"
						}
					}
				},
				{
					label: {
						text: "Token",
						icon: "el-icon-question",
						tip: "公众号token，用于对接微信消息"
					},
					prop: "Token",
					component: {
						name: "el-input",
						attrs: {
							placeholder: "请输入微信token"
						}
					},
					append: ({ h }) => {
						return h(
							"el-button",
							{
								on: {
									click: this.gen
								}
							},
							"生成"
						);
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
							type: 1,
							data: {
								appId: data.appId,
								appSecret: data.appSecret,
								Token: data.Token,
								EncodingAESKey: data.EncodingAESKey
							}
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
			this.$service.wechat.config.info({ type: 1 }).then(res => {
				this.$refs.form.reBindForm(JSON.parse(res));
			});
		},

		copy(args) {
			console.log(args);
		},

		// 生成32位token
		gen() {
			let arr = [
				"0",
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"a",
				"b",
				"c",
				"d",
				"e",
				"f",
				"g",
				"h",
				"i",
				"j",
				"k",
				"l",
				"m",
				"n",
				"o",
				"p",
				"q",
				"r",
				"s",
				"t",
				"u",
				"v",
				"w",
				"x",
				"y",
				"z",
				"A",
				"B",
				"C",
				"D",
				"E",
				"F",
				"G",
				"H",
				"I",
				"J",
				"K",
				"L",
				"M",
				"N",
				"O",
				"P",
				"Q",
				"R",
				"S",
				"T",
				"U",
				"V",
				"W",
				"X",
				"Y",
				"Z"
			];
			let pos;
			let accessToken = "";
			for (var i = 0; i < 32; i++) {
				pos = Math.round(Math.random() * (arr.length - 1));
				accessToken += arr[pos];
			}
			this.$refs.form.setForm("Token", accessToken);
		}
	}
};
</script>
<style lang="scss" scoped>
.container {
	.wechat-config {
		width: 60%;
	}
	.copy {
		width: 100%;
		text-align: left;
	}
}
</style>
