<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：index.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月20日 23:41:30
  - # 上次修改时间：2021年07月19日 21:37:40
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div class="cl-upload-space__wrap" v-show="button">
		<div class="grid-content bg-purple" @click="open">
			<i class="el-icon-folder"></i>
			<strong>从素材库选择</strong>
		</div>

		<!-- 弹框 -->
		<cl-dialog
			title="素材库"
			height="630px"
			width="1000px"
			keep-alive
			:visible.sync="visible"
			:props="{
				'close-on-click-modal': false,
				'append-to-body': true,
				customClass: 'dialog-upload-space'
			}"
			:controls="['cl-flex1', 'fullscreen', 'close']"
		>
			<div class="cl-upload-space">
				<!-- 内容 -->
				<div class="cl-upload-space__content">
					<!-- 操作栏 -->
					<div class="cl-upload-space__header scroller1">
						<el-button size="mini" icon="el-icon-refresh" @click="refresh()">
							刷新
						</el-button>
						<cl-graphic-editing :type="'btn'"></cl-graphic-editing>
					</div>

					<!-- 文件区域 -->
					<div
						class="cl-upload-space__file scroller1"
						v-loading="loading"
						element-loading-text="拼命加载中"
					>
						<!-- 素材列表 -->
						<template v-if="list.length > 0">
							<div class="cl-upload-space__file-list">
								<file-item
									v-for="item in list"
									:key="item.media_id"
									:value="item"
									:element-loading-text="item.progress"
									v-loading="item.loading"
									@select="select"
									@remove="remove"
								></file-item>
							</div>
						</template>

						<!-- 空态 -->
						<div class="cl-upload-space__file-empty" v-else>
							无数据
						</div>
					</div>

					<!-- 分页 -->
					<div class="cl-upload-space__footer">
						<el-pagination
							background
							:page-size="pagination.size"
							:current-page="pagination.page"
							:total="pagination.total"
							@current-change="
								page => {
									refresh({ page });
								}
							"
						></el-pagination>
					</div>
				</div>
			</div>
		</cl-dialog>
	</div>
</template>

<script>
import FileItem from "./file-item";
import { mapGetters } from "vuex";

export default {
	name: "cl-material-space",

	props: {
		button: {
			type: Boolean,
			default: true
		}
	},

	components: {
		FileItem
	},

	provide() {
		return {
			material: this
		};
	},

	data() {
		return {
			visible: false,
			loading: false,
			category: {
				id: null,
				visible: true
			},
			list: [],
			pagination: {
				page: 1,
				size: 12,
				total: 0
			}
		};
	},

	computed: {
		...mapGetters(["browser"])
	},

	methods: {
		open() {
			this.refresh();
			this.visible = true;
		},

		close() {
			this.visible = false;
		},

		// 刷新资源文件
		refresh(params) {
			this.loading = true;

			this.$service.wechat.material
				.page({
					...this.pagination,
					...params,
					type: this.accept
				})
				.then(res => {
					this.pagination = res.pagination;
					this.list = res.list;
				})
				.done(() => {
					this.loading = false;
				});
		},

		// 选择
		select(item) {
			this.$emit("input", item.id);
			this.$emit("confirm", this.detailData ? item : item.id);
			this.close();
		},

		// 删除选中
		remove(...selection) {
			// 已选文件 id
			const ids = selection.map(e => e.id);
			this.$confirm("此操作将删除文件, 是否继续?", "提示", {
				type: "warning"
			})
				.then(() => {
					// 删除请求
					this.$service.wechat.material
						.delete({
							ids
						})
						.then(res => {
							this.$message.success("删除成功");
						})
						.catch(err => {
							this.$message.error(err);
						});
				})
				.catch(() => null);
		}
	}
};
</script>

<style lang="scss">
.dialog-upload-space {
	.el-dialog {
		&__body {
			padding: 0 !important;
		}
	}
}
</style>

<style lang="scss" scoped>
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
	font-size: 14px;
	i {
		font-size: 40px;
		margin-bottom: 15px;
	}
	strong {
		font-weight: normal;
		display: block;
	}
}
.cl-upload-space {
	display: flex;
	height: 100%;
	box-sizing: border-box;
	background-color: #f7f7f7;
	padding: 5px;

	&__content {
		flex: 1;
		max-width: 100%;
		padding: 0 10px;
		box-sizing: border-box;
		background-color: $color-white;
		border-radius: 5px;
	}

	&__header {
		display: flex;
		align-items: center;
		height: 50px;
		overflow: auto hidden;
	}

	&__file {
		height: calc(100% - 100px);
		position: relative;

		&-list {
			display: flex;
			flex-wrap: wrap;
		}

		&-empty {
			display: flex;
			align-items: center;
			justify-content: center;
			position: absolute;
			top: calc(50% - 90px);
			left: calc(50% - 160px);

			/deep/.cl-upload {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				border-radius: 6px;
				cursor: pointer;

				.el-upload-dragger {
					height: 180px;
					width: 320px;
				}

				i {
					font-size: 67px;
					color: #c0c4cc;
				}
			}
		}
	}

	&__footer {
		padding: 9px 0;
	}
}
</style>
