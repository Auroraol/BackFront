<template>
	<h2>当前求和为：{{sum}}</h2>
	<button @click="sum++">点我+1</button>
	<hr>
	<h2>当前点击时鼠标的坐标为：x：{{point.x}}，y：{{point.y}}</h2>
</template>

<script>
	import {ref} from 'vue'
	import usePoint from '../hooks/usePoint' //引入组件
	export default {
		name: 'Demo',
		setup(){
			//数据
			let sum = ref(0)
			let point = usePoint()   //usePoint() 公用方法
			

			//返回一个对象（常用）
			return {sum,point}
		}
	}
</script>

