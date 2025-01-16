<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：reply.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月18日 01:04:29
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div class="container">
		<div class="title">{{ title }}</div>
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<el-tab-pane label="关键字回复" name="keyword">
				<wechat-key-reply />
			</el-tab-pane>
			<el-tab-pane class="container-auto_reply" label="默认回复" name="autoReply">
				<el-tabs v-model="data.autoReply.active" type="border-card">
					<el-tab-pane name="text">
						<span slot="label"><i class="el-icon-chat-dot-square"></i> 文字</span>
						<el-input
							type="textarea"
							:rows="2"
							placeholder="请输入内容"
							v-model="data.autoReply.text"
						>
						</el-input>
					</el-tab-pane>
					<el-tab-pane name="picmsg">
						<span slot="label"><i class="el-icon-chat-line-square"></i> 图文(回复一条)</span>
						<!-- 图文 -->
						<div
							class="container-auto_reply__picmsg__area"
							v-if="data.autoReply.media.length > 0"
						>
							<div class="container-auto_reply__picmsg__area__list">
								<div
									class="container-auto_reply__picmsg__area__list__item has-thumb"
								>
									<div class="bd">
										<div class="title">
											{{ data.autoReply.media[0].title }}
										</div>
									</div>
									<div class="hd">
										<div class="thumb">
											<el-image fit="cover" :src="data.autoReply.media[0].cover"></el-image>
										</div>
									</div>
								</div>
							</div>
							<a
								href="javascript:"
								@click="data.autoReply.media = []"
								class="container-auto_reply__picmsg__area__delete"
							>
								删除
							</a>
						</div>
						<el-row v-else>
							<el-col :span="4">
								<cl-material-space @confirm="onPicMsgConfirm" />
							</el-col>
							<el-col :span="4">
								<cl-graphic-editing />
							</el-col>
						</el-row>
						<!-- 图文结束 -->
					</el-tab-pane>
<!--					<el-tab-pane name="pic">-->
<!--						<span slot="label"><i class="el-icon-picture-outline"></i> 图片</span>-->
<!--						<div class="container-auto_reply__pic" v-if="data.autoReply.url">-->
<!--							<div class="container-menu__edit__pic__area">-->
<!--								<el-image fit="cover" :src="data.autoReply.url" lazy />-->
<!--							</div>-->
<!--							<a-->
<!--								href="javascript:"-->
<!--								@click="data.autoReply.url = ''"-->
<!--								class="container-auto_reply__pic__delete"-->
<!--								><svg-->
<!--									width="17"-->
<!--									height="13"-->
<!--									viewBox="0 0 17 13"-->
<!--									xmlns="http://www.w3.org/2000/svg"-->
<!--								>-->
<!--									<path-->
<!--										d="M4.05 11H16a1 1 0 0 0 0-2H4.015l-.017-.988a.5.5 0 0 0-.811-.383L.565 9.711a.5.5 0 0 0 .014.793l2.693 1.989a.5.5 0 0 0 .797-.411L4.049 11zm9.02-9H2a1 1 0 1 0 0 2h11.035l-.017 1.002a.5.5 0 0 0 .794.413l2.684-1.953a.5.5 0 0 0 .014-.798L13.895.618a.5.5 0 0 0-.808.386L13.07 2z"-->
<!--									></path>-->
<!--								</svg>-->
<!--								删除-->
<!--							</a>-->
<!--						</div>-->
<!--						<el-row :gutter="20" v-else>-->
<!--							<el-col :span="6">-->
<!--								<cl-upload-space-->
<!--									:showMaterial="true"-->
<!--									:limit="1"-->
<!--									:rename="true"-->
<!--									:detail-data="true"-->
<!--									@confirm="onFileConfirm"-->
<!--								/>-->
<!--							</el-col>-->
<!--						</el-row>-->
<!--					</el-tab-pane>-->
				</el-tabs>
			</el-tab-pane>
			<el-tab-pane class="container-attention" label="被关注回复" name="attention">
				<el-tabs v-model="data.attention.active" type="border-card">
					<el-tab-pane name="text">
						<span slot="label"><i class="el-icon-chat-dot-square"></i> 文字</span>
						<el-input
							type="textarea"
							:rows="2"
							placeholder="请输入内容"
							v-model="data.attention.text"
						>
						</el-input>
					</el-tab-pane>
					<el-tab-pane name="picmsg">
						<span slot="label"><i class="el-icon-chat-line-square"></i> 图文</span>
						<!-- 图文 -->
						<div
							class="container-attention__picmsg__area"
							v-if="data.attention.media.length > 0"
						>
							<div class="container-attention__picmsg__area__list">
								<div
									class="container-attention__picmsg__area__list__item"
									v-for="(item, index) in data.attention.media"
									:key="index"
									:class="{ 'has-thumb': index === 0 }"
								>
									<div class="bd">
										<div class="title">
											{{ item.title }}
										</div>
									</div>
									<div class="hd">
										<div class="thumb">
											<el-image fit="cover" :src="item.cover"></el-image>
										</div>
									</div>
								</div>
							</div>
							<a
								href="javascript:"
								@click="data.attention.media = []"
								class="container-attention__picmsg__area__delete"
							>
								删除
							</a>
						</div>
						<el-row v-else>
							<el-col :span="4">
								<cl-material-space @confirm="onPicMsgConfirm" />
							</el-col>
							<el-col :span="4">
								<cl-graphic-editing />
							</el-col>
						</el-row>
						<!-- 图文结束 -->
					</el-tab-pane>
<!--					<el-tab-pane name="pic">-->
<!--						<span slot="label"><i class="el-icon-picture-outline"></i> 图片</span>-->
<!--						<div class="container-attention__pic" v-if="data.attention.url">-->
<!--							<div class="container-attention__edit__pic__area">-->
<!--								<el-image fit="cover" :src="data.attention.url" lazy />-->
<!--							</div>-->
<!--							<a-->
<!--								href="javascript:"-->
<!--								@click="data.attention.url = ''"-->
<!--								class="container-attention__pic__delete"-->
<!--								><svg-->
<!--									width="17"-->
<!--									height="13"-->
<!--									viewBox="0 0 17 13"-->
<!--									xmlns="http://www.w3.org/2000/svg"-->
<!--								>-->
<!--									<path-->
<!--										d="M4.05 11H16a1 1 0 0 0 0-2H4.015l-.017-.988a.5.5 0 0 0-.811-.383L.565 9.711a.5.5 0 0 0 .014.793l2.693 1.989a.5.5 0 0 0 .797-.411L4.049 11zm9.02-9H2a1 1 0 1 0 0 2h11.035l-.017 1.002a.5.5 0 0 0 .794.413l2.684-1.953a.5.5 0 0 0 .014-.798L13.895.618a.5.5 0 0 0-.808.386L13.07 2z"-->
<!--									></path>-->
<!--								</svg>-->
<!--								删除-->
<!--							</a>-->
<!--						</div>-->
<!--						<el-row :gutter="20" v-else>-->
<!--							<el-col :span="6">-->
<!--								<cl-upload-space-->
<!--									:showMaterial="true"-->
<!--									:limit="1"-->
<!--									:rename="true"-->
<!--									:detail-data="true"-->
<!--									@confirm="onFileConfirm"-->
<!--								/>-->
<!--							</el-col>-->
<!--						</el-row>-->
<!--					</el-tab-pane>-->
				</el-tabs>
			</el-tab-pane>
		</el-tabs>
		<el-button
			v-if="curIndex !== '0'"
			class="container-submit"
			type="primary"
			size="mini"
			@click="submit"
			>保存</el-button
		>
	</div>
</template>

<script>
export default {
	name: "wechat-reply",
	data() {
		return {
			activeName: "attention",
			title: "被关注回复",
			curIndex: "2",
			loading: false,
			data: {
				autoReply: {
					active: "text",
					text: "",
					mediaId: 0,
					url: "",
					media: []
				},
				attention: {
					active: "text",
					text: "",
					mediaId: 0,
					url: "",
					media: []
				}
			}
		};
	},
	mounted() {
		this.refresh();
	},
	methods: {
		refresh() {
			this.$service.wechat.config
				.info({ type: 4 })
				.then(res => {
					this.data = JSON.parse(res);
				})
				.catch(err => {
					this.$message.error(err.msg);
				});
		},
		handleClick(tab) {
			this.title = tab.label;
			this.curIndex = tab.index;
		},
		// 自动回复选择图文回调
		onPicMsgConfirm(id) {
			switch (this.curIndex) {
				case "1":
					this.data.autoReply.media = [];
					break;
				case "2":
					this.data.attention.media = [];
					break;
			}
			this.$service.wechat.material
				.list({ id })
				.then(res => {
					switch (this.curIndex) {
						case "1":
							this.data.autoReply.media = res;
							this.data.autoReply.mediaId = id;
							break;
						case "2":
							this.data.attention.media = res;
							this.data.attention.mediaId = id;
							break;
					}
				})
				.catch(err => {
					this.$message.error(err);
				});
		},
		// 选择文件、图片、音频、视频回调
		onFileConfirm(val) {
			switch (this.curIndex) {
				case "1":
					this.data.autoReply.url = val[0].url;
					break;
				case "2":
					this.data.attention.url = val[0].url;
					break;
			}
		},
		// 提交
		submit() {
			this.loading = true;
			// 清空多余内容
			switch (this.data.autoReply.active) {
				case "text":
					this.data.autoReply.media = [];
					this.data.autoReply.url = "";
					break;
				case "picmsg":
					this.data.autoReply.text = "";
					this.data.autoReply.url = "";
					break;
				case "pic":
					this.data.autoReply.media = [];
					this.data.autoReply.text = "";
					break;
			}
			switch (this.data.attention.active) {
				case "text":
					this.data.attention.media = [];
					this.data.attention.url = "";
					break;
				case "picmsg":
					this.data.attention.text = "";
					this.data.attention.url = "";
					break;
				case "pic":
					this.data.attention.media = [];
					this.data.attention.text = "";
					break;
			}
			this.$service.wechat.config
				.reply({
					type: 4,
					data: this.data
				})
				.then(res => {
					this.$message.success("保存成功");
					this.refresh();
				})
				.catch(err => {
					this.$message.error(err.msg);
				});
			this.loading = false;
		}
	}
};
</script>

<style lang="scss" scoped>
.el-tabs {
	box-shadow: none;
}
.container {
	&-attention,
	&-auto_reply {
		// 已选择图文样式
		&__picmsg__area {
			color: #9a9a9a;
			overflow: hidden;

			&__list {
				width: 306px;
				display: inline-block;
				border: 1px solid #e7e7eb;
				background-color: $color-white;

				&__item {
					position: relative;
					padding: 15px;

					.bd,
					.hd {
						display: table-cell;
						vertical-align: middle;
						word-wrap: break-word;
						word-break: break-all;
					}

					.bd {
						width: 2000px;
						color: #353535;
					}

					.hd {
						.thumb {
							width: 48px;
							height: 48px;
							display: block;
							position: relative;

							.el-image {
								height: 100% !important;
							}
						}
					}

					&.has-thumb {
						padding-top: 56.25%;

						.bd {
							.title {
								position: absolute;
								left: 15px;
								right: 15px;
								bottom: 15px;
								overflow: hidden;
								text-overflow: ellipsis;
								display: -webkit-box;
								-webkit-box-orient: vertical;
								-webkit-line-clamp: 2;
								color: $color-white;
								font-weight: 400;
								z-index: 1;
							}
						}

						.hd {
							.thumb {
								position: absolute;
								top: 0;
								bottom: 0;
								left: 0;
								right: 0;
								width: auto;
								height: auto;
								background-size: cover;
								background-position: 50% 50%;
								background-repeat: no-repeat;
								background-color: #f6f8f9;
							}
						}
					}
				}
			}

			&__delete {
				display: inline-block;
				vertical-align: bottom;
				padding-left: 10px;
				margin-bottom: 5px;
				color: #576b95;
			}
		}
		&__pic {
			width: 320px;
			position: relative;
			&__delete {
				svg {
					fill: $color-white;
					margin-right: 10px;
					vertical-align: middle;
					margin-top: -2px;
				}

				position: absolute;
				top: 20px;
				right: 20px;
				background-color: rgba(0, 0, 0, 0.5);
				border: 0;
				padding: 6px 12px;
				border-radius: 4px;
				color: $color-white;

				&:hover {
					background-color: rgba(47, 47, 47, 0.5);
				}
			}
		}
	}
	&-submit {
		margin-top: 30px;
	}
}
</style>
