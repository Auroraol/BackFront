<!--
  - # 此文件版权属于ASO.DESIGN
  - # 文件：file-item.vue  项目：aso-zero
  - # 作用：
  - # 当前修改时间：2021年07月18日 01:05:05
  - # 上次修改时间：2021年07月15日 20:53:52
  - # 作者：thunur
  - # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
  - # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
  - # 如果您还没获得授权请联系我们 thunur@qq.com
  - # Copyright (c) 2021 aso.design
  -->

<template>
	<div
		class="cl-material-item is-image"
		:class="[`is-image`]"
		@click.stop.prevent="select"
		@contextmenu.stop.prevent="openContextMenu"
	>
		<!-- 图片 -->
		<template>
			<div class="tag" v-if="value.type">多图文</div>
			<div class="cl-material-item__box">
				<el-image fit="cover" :src="value.thumb" lazy>
					<div slot="placeholder" class="image-slot">
						<i class="el-icon-loading"></i>
					</div>
					<div slot="error" class="image-slot error">
						<i class="el-icon-picture-outline"></i>
					</div>
				</el-image>
				<div class="title">{{ value.title }}</div>
			</div>
		</template>
	</div>
</template>

<script>
export default {
	name: "cl-material-space-item",

	props: {
		value: Object
	},

	inject: ["material"],

	computed: {
		type() {
			return this.value.type || "";
		}
	},

	methods: {
		select() {
			this.$emit("select", this.value);
		},

		remove() {
			this.$emit("remove", this.value);
		},

		openContextMenu(e) {
			this.$crud.openContextMenu(e, {
				list: [
					{
						label: "选中",
						"suffix-icon": "el-icon-check",
						callback: (_, done) => {
							this.select();
							done();
						}
					},
					{
						label: "删除",
						"suffix-icon": "el-icon-delete",
						callback: (_, done) => {
							this.remove();
							done();
						}
					}
				]
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.cl-material-item {
	height: 160px;
	width: 160px;
	max-width: calc(50% - 10px);
	cursor: pointer;
	position: relative;
	border-radius: 3px;
	box-sizing: border-box;
	border: 1px solid #eee;
	margin: 5px 10px 5px 0;
	&__box {
		position: relative;
		overflow: hidden;
		width: 100%;
		height: 100%;
		.el-image {
			width: 100%;
			height: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
		}
		.error {
			color: $color-info;
			font-size: 30px;
		}
		&__mask {
			position: absolute;
			left: 0;
			top: 0;
			height: 100%;
			width: 100%;
			background-color: rgba(0, 0, 0, 0.3);
			span {
				position: absolute;
				right: 10px;
				top: 10px;
				background-color: #67c23a;
				color: #fff;
				display: inline-block;
				height: 20px;
				width: 20px;
				text-align: center;
				line-height: 20px;
				border-radius: 20px;
			}
		}
		.title {
			position: absolute;
			bottom: 0;
			background: #00000059;
			padding: 5px;
			width: 100%;
			font-size: 12px;
			color: $color-white;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
	}
	.tag {
		position: absolute;
		top: 4px;
		padding: 2px 5px;
		background: $primary;
		box-shadow: -1px 2px 4px #00000059;
		color: $color-white;
		font-size: 12px;
		border-radius: 5px 0 0 5px;
		right: 0;
		z-index: 1;
		&:before,
		&:after {
			content: "";
			position: absolute;
		}
		&:before {
			width: 7px;
			height: 100%;
			padding: 0 0 7px;
			top: 0;
			right: -7px;
			background: inherit;
			border-radius: 0 5px 5px 0;
		}
		&:after {
			width: 5px;
			height: 5px;
			background: #00000059;
			bottom: -5px;
			right: -5px;
			border-radius: 0 5px 5px 0;
		}
	}
}
.scroller1 {
	overflow: auto;
}
</style>
