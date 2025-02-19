<template>
	<div class="cl-goods-category" v-loading="loading">
		<el-input placeholder="输入关键字进行过滤" v-model="filterText"> </el-input>

		<el-tree
			class="filter-tree"
			:data="list"
			:props="defaultProps"
			default-expand-all
			:filter-node-method="filterNode"
			@node-click="handleNodeClick"
			ref="tree"
		>
		</el-tree>
	</div>
</template>

<script>
export default {
	name: "cl-goods-category",
	inject: ["form"],
	data() {
		return {
			loading: false,
			filterText: "",
			list: [],
			defaultProps: {
				children: "children",
				label: "label"
			}
		};
	},
	watch: {
		filterText(val) {
			this.$refs.tree.filter(val);
		}
	},
	mounted() {
		this.refresh();
	},
	methods: {
		refresh() {
			this.$service.goods.category
				.list()
				.then(res => {
					this.list = res;
				})
				.catch(err => {
					this.$message.error(err);
				});
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.label.indexOf(value) !== -1;
		},
		handleNodeClick(data) {
			console.log(data);
		}
	}
};
</script>

<style scoped></style>
