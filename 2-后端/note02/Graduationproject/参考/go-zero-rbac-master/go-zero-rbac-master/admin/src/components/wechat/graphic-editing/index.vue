<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：index.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月18日 00:05:51
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div>
		<div v-if="type === 'btn'">
			<el-button
				type="primary"
				size="mini"
				icon="el-icon-document-add"
				style="margin: 0 10px;"
				@click="open"
			>
				新建图文
			</el-button>
		</div>
		<div v-else>
			<div class="grid-content bg-purple" @click="open">
				<i class="el-icon-edit-outline"></i>
				<strong>新建图文</strong>
			</div>
		</div>
		<cl-dialog
			title="创建图文"
			height="100%"
			width="100%"
			class="create"
			keep-alive
			:visible.sync="visible"
			v-if="visible"
			:show="visible"
			:props="{
				'close-on-click-modal': false,
				'append-to-body': true,
				customClass: 'dialog-media-create'
			}"
			:controls="['cl-flex1', 'close']"
		>
			<div class="left">
				<div
					class="switch-open"
					v-if="!sideMenu.visible"
					@click="sideMenu.visible = true"
				></div>
				<div
					class="sideMenu"
					:class="{
						'is-position': browser.isMd,
						'is-show': sideMenu.visible
					}"
				>
					<div
						class="switch-close"
						v-if="sideMenu.visible"
						@click="sideMenu.visible = false"
					></div>

					<div class="articleList">
						<div class="appmsg-side__wrapper">
							<div class="web-title">
								公众号名称
							</div>
							<div
								class="item"
								v-for="(item, index) in media"
								:key="index"
								@click="select(index)"
								:class="{
									'has-thumb': index === 0,
									current: cur === index
								}"
							>
								<div class="container">
									<div class="bd">
										<div class="title">{{ item.title }}</div>
									</div>
									<div class="hd">
										<div
											class="thumb"
											:style="'background-image: url(' + item.cover + ')'"
										></div>
									</div>
								</div>
							</div>
							<div class="add-item">
								<div class="add-wrap" v-if="showAddBtn" @click="addItem">
									<i class="el-icon-plus"></i>
									新建消息
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div
				class="right"
				:class="{
					'md-layout': browser.isMd
				}"
			>
				<div class="edit-box">
					<div class="editor-pannel">
						<div class="editor-area">
							<el-input
								v-model="media[cur].title"
								class="title"
								maxlength="64"
								show-word-limit
								placeholder="请输入标题"
							/>
							<el-input
								v-model="media[cur].author"
								class="author"
								maxlength="10"
								show-word-limit
								placeholder="请输入作者"
							/>
							<cl-editor-quill
								v-model="media[cur].content"
								@load="onLoad"
								@count="count"
							></cl-editor-quill>
							<div class="article-setting-area">
								<div class="setting-group-title">封面和摘要</div>
								<div class="setting-group-content">
									<div class="setting-group-cover">
										<div class="select-cover-btn" v-if="!media[cur].cover">
											<div class="select-icon" @click="uploadHandler">
												<el-tooltip
													class="item"
													effect="dark"
													content="从图库选择"
													placement="top"
												>
													<i class="el-icon-plus"></i>
												</el-tooltip>
											</div>
										</div>
										<div
											class="setting-group-cover-preview"
											:style="
												'background-image: url(' + media[cur].cover + ')'
											"
											v-if="media[cur].cover"
										></div>
										<div
											class="setting-group-cover-tips"
											v-if="media[cur].cover"
										>
											<div class="setting-group-cover-tips-area">
												<div class="tips-button" @click="uploadHandler">
													<i class="el-icon-edit-outline"></i>
												</div>
											</div>
										</div>
									</div>
									<div class="setting-group-abstract">
										<el-input
											class="textarea-box"
											type="textarea"
											:rows="4"
											placeholder="请输入内容"
											maxlength="120"
											resize="none"
											show-word-limit
											v-model="media[cur].summary"
										>
										</el-input>
									</div>
								</div>
							</div>
						</div>
						<!-- 底部按钮 -->
						<div class="bottom-main">
							<div class="button-area">
								<div class="tool-bar">
									<div class="tool-bar-extend-left">
										<div class="flod-tips">正文字数：{{ wordCount }}</div>
									</div>
									<div class="tool-bar-extend-right">
										<el-button
											size="small"
											type="primary"
											:loading="loading"
											@click="submit"
										>
											保存
										</el-button>
										<el-button size="small">预览</el-button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<cl-upload-space
				ref="upload-space"
				:show-button="false"
				:limit="1"
				@confirm="onFileConfirm"
			>
			</cl-upload-space>
		</cl-dialog>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
import Quill from "quill";

export default {
	name: "cl-graphic-editing",
	props: ["type"],
	data() {
		return {
			showAddBtn: true,
			cur: 0,
			wordCount: 0,
			visible: false,
			loading: false,
			media: [
				{
					content: "",
					title: "",
					author: "",
					summary: "",
					cover: ""
				}
			],
			sideMenu: {
				visible: true
			}
		};
	},
	computed: {
		...mapGetters(["browser"])
	},
	watch: {
		"browser.isMd": {
			immediate: true,
			handler(val) {
				this.sideMenu.visible = !val;
			}
		}
	},
	methods: {
		open() {
			this.visible = true;
		},
		close() {
			// 重设相关数据
			this.cur = 0;
			this.media = [
				{
					content: "",
					title: "",
					author: "",
					summary: "",
					cover: ""
				}
			];
			this.visible = false;
		},
		onLoad(ref) {},
		count(val) {
			this.wordCount = val;
		},
		select(index) {
			this.cur = index;
		},
		addItem() {
			if (this.media.length >= 8) this.showAddBtn = false;
			this.media.push({
				content: "",
				title: "",
				author: "",
				cover: ""
			});
		},
		uploadHandler() {
			this.$refs["upload-space"].open();
		},

		onFileConfirm(files) {
			console.log(files);
			this.media[this.cur].cover = files;
		},
		submit() {
			this.loading = true;
			this.$service.wechat.material
				.add({
					title: this.media[this.cur].title,
					thumb: this.media[this.cur].cover,
					data: this.media
				})
				.then(() => {
					this.$message({
						message: "保存成功",
						type: "success"
					});
					this.close();
					this.loading = false;
				})
				.catch(err => {
					console.log(err);
				});
			this.loading = false;
		}
	}
};
</script>
<style lang="scss">
.switch-open {
	position: fixed;
	left: 0;
	top: 122px;
	background: $color-white;
	box-shadow: 0 1px 4px 0 #00000033;
	border-radius: 0 100px 100px 0;
	width: 52px;
	height: 32px;
	line-height: 32px;
	border: 0;
	text-align: right;
	cursor: pointer;
}
.switch-open:before {
	content: " ";
	background-image: url("data:image/svg+xml,%3Csvg width='21' height='21' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Cdefs%3E%3Cpath d='M0 124h32.5c10.77 0 19.5 8.73 19.5 19.5S43.27 163 32.5 163H0v-39z' id='b'/%3E%3Cfilter x='-12.5%25' y='-14.1%25' width='125%25' height='133.3%25' filterUnits='objectBoundingBox' id='a'%3E%3CfeOffset dy='1' in='SourceAlpha' result='shadowOffsetOuter1'/%3E%3CfeGaussianBlur stdDeviation='2' in='shadowOffsetOuter1' result='shadowBlurOuter1'/%3E%3CfeColorMatrix values='0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.2 0' in='shadowBlurOuter1'/%3E%3C/filter%3E%3C/defs%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cpath fill='%23F5F5F5' d='M-17-133h772V891H-17z'/%3E%3Cpath d='M-16-31h771v899H-16z' fill='%23FFF'/%3E%3Cg transform='translate(-17 -133)'%3E%3Cuse fill='%23000' filter='url(%23a)' xlink:href='%23b'/%3E%3Cuse fill='%23FFF' xlink:href='%23b'/%3E%3C/g%3E%3Cpath d='M10.56 20.714C4.952 20.714.406 16.168.406 10.56S4.952.406 10.56.406 20.714 4.952 20.714 10.56 16.168 20.714 10.56 20.714zm0-1.219a8.935 8.935 0 1 0 0-17.87 8.935 8.935 0 0 0 0 17.87zM6.424 9.68h8.272c.146 0 .264.118.264.264v.704a.264.264 0 0 1-.264.264H6.424a.264.264 0 0 1-.264-.264v-.704c0-.146.118-.264.264-.264zm0-2.88h8.272c.146 0 .264.118.264.264v.704a.264.264 0 0 1-.264.264H6.424a.264.264 0 0 1-.264-.264v-.704c0-.146.118-.264.264-.264zm0 5.6h8.272c.146 0 .264.118.264.264v.704a.264.264 0 0 1-.264.264H6.424a.264.264 0 0 1-.264-.264v-.704c0-.146.118-.264.264-.264z' fill='%234A4A51'/%3E%3C/g%3E%3C/svg%3E");
	width: 21px;
	height: 21px;
	margin-right: 6px;
	margin-top: -3px;
	display: inline-block;
	vertical-align: middle;
}
.sideMenu {
	position: fixed;
	left: 0;
	top: 0;
	bottom: 0;
	height: 100%;
	z-index: 0;
	overflow-y: auto;
	width: 0;
	padding-top: 105px;
	-webkit-transition: width 0.2s ease-in-out;
	transition: width 0.2s ease-in-out;
	.switch-close {
		width: 32px;
		height: 32px;
		line-height: 32px;
		position: absolute;
		left: 30px;
		top: 15px;
		border: 1px solid #e3e4e5;
		text-align: center;
		border-radius: 50%;
		cursor: pointer;
	}
	.switch-close:hover {
		background-color: $color-white;
	}
	.switch-close:before {
		content: " ";
		background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='20' height='20' viewBox='0 0 20 20'%3E %3Cpath fill='%234C4D4E' fill-rule='evenodd' d='M12.083 2.34l.884.884L6.191 10l6.776 6.776-.884.884-7.07-7.07a.833.833 0 0 1 0-1.18l7.07-7.07z'/%3E%3C/svg%3E");
		width: 20px;
		height: 20px;
		display: inline-block;
		vertical-align: middle;
		margin-top: -3px;
	}

	&.is-show {
		width: 370px;
	}
	&.is-position {
		height: 100%;
		z-index: 3000;
		box-shadow: 0 0 20px 0 #cecece;
		background-color: #f5f5f5;
		&.is-show {
			width: 370px;
		}
	}
	.articleList {
		overflow: auto;
		border-right-width: 0;
		padding: 0 30px 0;
		position: relative;
		.appmsg-side__wrapper {
			background-color: #ffffff;
			box-shadow: 0 0 8px 0 #e5e5e580;
			margin: 0;
			width: 310px;
			border-radius: 5px;
			overflow: hidden;
			.web-title {
				background-color: #fff;
				padding: 10px 15px;
				display: flex;
				align-items: center;
			}
			.add-item {
				line-height: 70px;
				position: relative;
				margin-bottom: 0;
				border-radius: 0 0 5px 5px;
				text-align: center;

				color: #9a9a9a;
				.add-wrap {
					display: inline-block;
					vertical-align: middle;
					font-size: 14px;
					position: relative;
					cursor: pointer;
				}
			}
			.item {
				cursor: pointer;
				&.has-thumb {
					.container {
						padding: 42.55% 0 0;
						position: relative;
						overflow: hidden;
						display: block;
						border-radius: 0;
						box-shadow: none;
						.thumb {
							position: absolute;
							top: 0;
							bottom: 0;
							left: 0;
							right: 0;
							width: 100% !important;
							height: 100% !important;
							background-size: cover;
							background-position: 50% 50%;
							background-repeat: no-repeat;
							background-color: #f6f8f9;
						}
						.thumb:before {
							content: " ";
							position: absolute;
							left: 0;
							right: 0;
							top: 0;
							bottom: 0;
							background-color: rgba(0, 0, 0, 0.3);
						}
						.title {
							position: absolute;
							left: 15px;
							right: 15px;
							bottom: 15px;
							overflow: hidden;
							text-overflow: ellipsis;
							color: #fff;
							font-weight: 400;
							z-index: 1;
							white-space: nowrap;
							text-overflow: ellipsis;
						}
					}
				}
				.container {
					width: auto;
					padding: 12px;
					position: relative;
					overflow: hidden;
					.hd,
					.bd {
						display: table-cell;
						vertical-align: middle;
						word-wrap: break-word;
						word-break: break-all;
					}
					.bd {
						width: 2000px;
					}
					.hd {
						.thumb {
							width: 48px;
							height: 48px;
							background-size: cover;
							background-position: 50% 50%;
							background-repeat: no-repeat;
							background-color: #f6f8f9;
						}
					}
				}
				.container:before {
					content: " ";
					width: calc(100% - 24px);
					position: absolute;
					bottom: 0;
					background-color: #e7e7eb;
					height: 1px;
				}
				&.current {
					border: 2px solid #07c160;
				}
			}
		}
	}
}
.create {
	font-size: 1000px;
	.el-dialog__header {
		background-color: $color-white;
		position: fixed;
		width: 100%;
		z-index: 1;
	}
	.dialog-media-create {
		width: 100% !important;
		height: 100% !important;
		margin: 0 !important;
		overflow: auto !important;
		background-color: #f5f5f5;
		.el-dialog__header {
			cursor: auto !important;
		}
	}
}
.bg-purple:hover {
	background: #f6f8f9;
}
.grid-content {
	border-radius: 4px;
	text-align: center;
	width: 130px;
	transition: all 0.3s;
	padding-top: 28px;
	padding-bottom: 34px;
	color: #9a9a9a;
}
.grid-content i {
	font-size: 40px;
	margin-bottom: 15px;
}
.grid-content strong {
	font-weight: normal;
	display: block;
}

.right {
	margin-left: 180px;
	&.md-layout {
		margin-left: 0;
	}
	.ql-editor.ql-blank::before {
		color: #c0c4cb;
	}
	.cl-editor-quill {
		.ql-toolbar {
			position: fixed !important;
			top: 0;
			left: 0;
			background: #fafafa;
			margin: 45px 0 0 0;
			overflow: visible;
			text-align: center;
			border: none !important;
			width: 100%;
			z-index: 1;
			.ql-formats {
				text-align: left;
			}
		}
		.editor {
			border: 0 !important;
			.ql-editor {
				min-height: 400px;
			}
			.ql-editor.ql-blank::before {
				font-style: normal;
			}
		}
	}

	.edit-box {
		.editor-pannel {
			margin: 120px auto 0 auto;
			width: 768px;
			background-color: $color-white;
			border-radius: 4px 4px 0 0;
			box-shadow: 0 1px 5px 0 #0000000d;
			.editor-area {
				padding: 50px 95px 72px 95px;
				.title {
					input {
						color: #353535;
						border: 0 !important;
						height: 50px;
						font-size: 24px;
						font-weight: 500;
					}
				}
				.author {
					input {
						color: #353535;
						border: 0 !important;
					}
				}
				.article-setting-area {
					padding-top: 20px;
					border-top: 1px solid #ebebeb;
					margin-bottom: 50px;
					.setting-group-title {
						font-size: 16px;
						line-height: 20px;
						margin-bottom: 20px;
					}
					.setting-group-content {
						display: table;

						.setting-group-cover {
							position: relative;
							float: left;
							margin-right: 10px;
							display: table-cell;
							.select-cover-btn {
								display: block;
								width: 211.5px;
								height: 106px;
								box-sizing: border-box;
								border: 2px dashed #ebebeb;
								text-align: center;
								padding-top: 16px;
								.select-icon {
									position: relative;
									top: 12px;
									vertical-align: middle;
									display: inline-block;
									font-size: 30px;
									cursor: pointer;
								}
							}
							&:hover {
								.setting-group-cover-tips {
									display: block;
								}
							}
							.setting-group-cover-tips {
								display: none;
								position: absolute;
								left: 0;
								top: 0;
								bottom: 0;
								right: 0;
								.setting-group-cover-tips-area {
									display: flex;
									align-items: center;
									justify-content: center;
									width: 100%;
									height: 100%;
									.tips-button {
										border: 1px solid #e3e4e5;
										background: $color-white;
										cursor: pointer;
										color: #353535;
										border-radius: 50%;
										padding: 5px;
										&:hover {
											background-color: #f6f7f8;
										}
										i {
											font-size: 18px;
										}
									}
								}
							}
							.setting-group-cover-preview {
								display: block;
								width: 211px;
								height: 90px;
								text-align: center;
								padding-top: 16px;
								transition: all 0.1s;
								background-size: cover;
								background-repeat: no-repeat;
								background-position: center center;
							}
						}
						.setting-group-abstract {
							display: table-cell;
							vertical-align: middle;
							width: 100%;
							.textarea-box {
								textarea {
									padding: 4px 0 4px 10px;
									height: 106px;
								}
							}
						}
					}
					.group-thumb {
						margin-bottom: 50px;
					}
				}
			}
			.bottom-main {
				position: fixed;
				bottom: 0;
				z-index: 2;
				background-color: $color-white;
				border-top: 1px solid #d9dadc;
				z-index: 118;
				border-top-width: 0;
				box-shadow: 0 -1px 4px 0 #0000000d;
				.button-area {
					width: 768px;
					margin-left: auto;
					margin-right: auto;
					.tool-bar {
						padding: 20px 60px 15px 30px;
						overflow: hidden;
						margin-top: 0;
						.tool-bar-extend-left {
							float: left;
							vertical-align: middle;
							.flod-tips {
								font-size: 14px;
								position: relative;
								color: $color-info;
								margin-top: 5px;
							}
						}
						.tool-bar-extend-right {
							float: right;
						}
					}
				}
			}
		}
	}
}
@media screen and (min-width: 1600px) {
	.right {
		margin-left: 80px;
	}
}
.dialog-media-create .el-dialog__body {
	padding: 0 !important;
}
.create .cl-dialog__controls button {
	margin: 0 25px !important;
}
</style>
<style scoped>
.show {
	display: block !important;
}
</style>
