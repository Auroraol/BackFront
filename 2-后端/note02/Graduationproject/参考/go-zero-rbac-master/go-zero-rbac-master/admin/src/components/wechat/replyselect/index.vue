<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：index.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月20日 23:41:30
  - # 上次修改时间：2021年07月20日 00:39:32
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<!-- 回复类型 -->
	<div class="reply-controls">
		<div class="reply-controls__keywords_reply" v-if="type === 'picmsg' && list.length > 0">
			<div class="reply-controls__keywords_reply__items has-thumb">
				<div class="bd">
					<div class="title">
						{{ list[0].title }}
					</div>
				</div>
				<div class="hd">
					<div class="thumb">
						<el-image fit="cover" :src="list[0].cover"></el-image>
					</div>
				</div>
			</div>
			<div class="reply-controls__keywords_reply__delete">
				<el-tooltip effect="dark" content="删除" placement="bottom">
					<el-button
						@click="list = []"
						class="reply-controls__keywords_reply__delete__button"
					>
						<icon-svg name="icon-delete" :size="18"></icon-svg>
					</el-button>
				</el-tooltip>
			</div>
		</div>
		<div class="reply-controls__keywords_text" v-else-if="type === 'text' && text.length > 0">
			{{ text }}
		</div>
		<div class="reply-controls__keywords_pic" v-else-if="type === 'pic' && url.length > 0">
			<div class="reply-controls__keywords_pic__area">
				<el-image fit="cover" :src="url" lazy />
			</div>
			<a href="javascript:" @click="url = ''" class="reply-controls__keywords_pic__delete"
				><svg width="17" height="13" viewBox="0 0 17 13" xmlns="http://www.w3.org/2000/svg">
					<path
						d="M4.05 11H16a1 1 0 0 0 0-2H4.015l-.017-.988a.5.5 0 0 0-.811-.383L.565 9.711a.5.5 0 0 0 .014.793l2.693 1.989a.5.5 0 0 0 .797-.411L4.049 11zm9.02-9H2a1 1 0 1 0 0 2h11.035l-.017 1.002a.5.5 0 0 0 .794.413l2.684-1.953a.5.5 0 0 0 .014-.798L13.895.618a.5.5 0 0 0-.808.386L13.07 2z"
					></path>
				</svg>
				删除
			</a>
		</div>
		<div class="reply-controls__reply_area">
			<ul class="reply-controls__reply_area__items">
				<li
					class="sender__tab"
					@click="openMaterial"
					:class="type === 'picmsg' ? 'active' : ''"
				>
					<icon-svg name="icon-pic-msg" :size="14" />
					图文(回复一条)
					<cl-material-space ref="material" @confirm="onPicMsgConfirm" :button="false" />
				</li>
				<li
					class="sender__tab"
					@click="
						visible = true;
						type = 'text';
					"
					:class="type === 'text' ? 'active' : ''"
				>
					<icon-svg name="icon-txt" :size="14" />
					文字
				</li>
				<!--				<li class="sender__tab" :class="type === 'pic' ? 'active' : ''" @click="openSpace">-->
				<!--					<icon-svg name="icon-thumb" :size="14" />-->
				<!--					图片-->
				<!--					<cl-upload-space-->
				<!--						:showButton="false"-->
				<!--						:limit="1"-->
				<!--						:rename="true"-->
				<!--						:detail-data="true"-->
				<!--						ref="space"-->
				<!--						@confirm="onFileConfirm"-->
				<!--					/>-->
				<!--				</li>-->
				<!--				<li-->
				<!--					class="sender__tab"-->
				<!--					:class="type === 'audio' ? 'active' : ''"-->
				<!--					@click="openSpace"-->
				<!--				>-->
				<!--					<icon-svg name="icon-audio" :size="14" />-->
				<!--					音频-->
				<!--					<cl-upload-space-->
				<!--						:showButton="false"-->
				<!--						:limit="1"-->
				<!--						:rename="true"-->
				<!--						:detail-data="true"-->
				<!--						ref="space"-->
				<!--						@confirm="onFileConfirm"-->
				<!--					/>-->
				<!--				</li>-->
				<!--				<li-->
				<!--					class="sender__tab"-->
				<!--					:class="type === 'video' ? 'active' : ''"-->
				<!--					@click="openSpace"-->
				<!--				>-->
				<!--					<icon-svg name="icon-video" :size="14" />-->
				<!--					视频-->
				<!--					<cl-upload-space-->
				<!--						:showButton="false"-->
				<!--						:limit="1"-->
				<!--						:rename="true"-->
				<!--						:detail-data="true"-->
				<!--						ref="space"-->
				<!--						@confirm="onFileConfirm"-->
				<!--					/>-->
				<!--				</li>-->
			</ul>
		</div>
		<!-- 文本回复 -->
		<el-dialog title="添加回复文字" :visible.sync="visible" width="800px" append-to-body>
			<el-input
				type="textarea"
				:rows="10"
				placeholder="请输入内容"
				v-model="text"
				maxlength="300"
				show-word-limit
			>
			</el-input>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="visible = false">保 存</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "wechat-key-word-select",
	props: {
		value: {
			type: Number,
			default: 0
		},
		type: {
			type: String
		},
		url: {
			type: String
		},
		text: {
			type: String
		}
	},
	data() {
		return {
			list: [],
			visible: false
		};
	},
	watch: {
		value: {
			handler(val) {
				this.$emit("changeMediaId", val);
				this.onPicMsgConfirm(val);
			}
		},
		type: {
			handler(val) {
				this.$emit("changeType", val);
			}
		},
		text: {
			handler(val) {
				this.$emit("changeText", val);
			}
		},
		url: {
			handler(val) {
				this.$emit("changeUrl", val);
			}
		}
	},
	methods: {
		openMaterial() {
			this.$refs.material.open();
		},
		openSpace() {
			this.$refs.space.open();
		},
		onPicMsgConfirm(id) {
			this.$emit("changeMediaId", id);
			this.$emit("changeType", "picmsg");
			this.$service.wechat.material
				.list({ id })
				.then(res => {
					this.list = res;
				})
				.catch(err => {
					this.$message.error(err);
				});
		},
		// 选择文件、图片、音频、视频回调
		onFileConfirm(val) {
			console.log(val);
			switch (val[0].type) {
				case "image/jpeg":
					this.$emit("changeType", "pic");
					break;
			}
			this.$emit("changeUrl", val[0].url);
		}
	}
};
</script>

<style lang="scss" scoped>
.reply-controls {
	&__keywords_reply {
		width: 306px;
		display: inline-block;
		border: 1px solid #e7e7eb;
		background-color: $color-white;
		position: relative;
		&__items {
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
			&.none {
				transition: all ease 0.3s;
				max-height: 0;
				padding: 0;
				overflow: hidden;
			}
		}
		&__delete {
			position: absolute;
			left: 100%;
			top: 0;
			bottom: 0;
			opacity: 0;
			visibility: hidden;
			-webkit-transition: opacity 0.3s;
			transition: opacity 0.3s;
			white-space: nowrap;
			&__button {
				margin-left: 10px;
				display: inline-block;
				box-shadow: 0 0 12px 0 rgba(0, 0, 0, 0.2);
				border-radius: 100%;
				height: 48px;
				text-align: center;
				width: 48px;
			}
		}
	}
	&__reply_area {
		&__items {
			background-color: #f6f7f8;
			> .sender__tab {
				padding: 0 15px;
				display: inline-block;
				cursor: pointer;
				svg {
					margin-right: 5px;
				}
				&:hover,
				&.active {
					color: $color-success;
					fill: $color-success;
				}
			}
		}
	}
	&__keywords_pic {
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
.reply-controls__keywords_reply:hover {
	.reply-controls__keywords_reply__delete {
		opacity: 1;
		visibility: visible;
	}
	> .none {
		max-height: 48px;
		padding: 15px;
	}
}
</style>
