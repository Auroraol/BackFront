<template>
	<div class="son">
		<h3>我是Son组件（孙），{{car.name}}--{{car.price}}</h3>
	</div>
</template>

<script>
	import {inject} from 'vue'
	export default {
		name:'Son',
		setup(){
			// 后代组件有一个 `inject` 选项来开始使用这些数据
			let car = inject('car')
			return {car}
		}
	}
</script>

<style>
	.son{
		background-color: orange;
		padding: 10px;
	}
</style>