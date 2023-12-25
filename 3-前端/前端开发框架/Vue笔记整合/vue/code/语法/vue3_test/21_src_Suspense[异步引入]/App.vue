<template>
	<div class="app">
		<h3>我是App组件</h3>
		<!--2. 格式不变:  使用```Suspense```包裹组件，并配置好```default``` 与 ```fallback``` -->
		<Suspense>
			<template v-slot:default>
				<Child/>
			</template>
			<template v-slot:fallback>
				<h3>稍等，加载中...</h3>
			</template>
		</Suspense>
	</div>
</template>
/*
1. 等待异步组件时渲染一些额外内容，让应用有更好的用户体验
*/ 
<script>
	// import Child from './components/Child'//静态引入
	// 异步引入组件
	import {defineAsyncComponent} from 'vue' 
	const Child = defineAsyncComponent(()=>import('./components/Child')) //异步引入
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