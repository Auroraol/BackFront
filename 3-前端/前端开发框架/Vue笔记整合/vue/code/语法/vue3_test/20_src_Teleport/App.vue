<template>
	<div class="app">
		<h3>我是App组件</h3>
		<Child/>
	</div>
</template>
/*
- 在Vue2中: 组件必须有一个根标签
- 在Vue3中: 组件可以没有根标签, 内部会将多个标签包含在一个Fragment虚拟元素中
- 好处: 减少标签层级, 减小内存占用
*/
<script>
	import Child from './components/Child'
	export default {
		name:'App',
		components:{Child},
	}
</script>

<style>
	.app{
		background-color: gray;
		padding: 10px;
	}
</style>