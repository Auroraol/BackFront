<template>
	<div class="col-xs-offset-2 col-xs-8">
		<div class="page-header">
			<h2>Vue Router Demo</h2>
			<!-- 不借助```<router-link> ```实现路由跳转 -->
			<button @click="back">后退</button>
			<button @click="forward">前进</button>
			<button @click="test">测试一下go</button>
		</div>
	</div>
</template>

<script>
	export default {
		name:'Banner',
		methods: {
			//后退
			back(){
				this.$router.back()
				// console.log(this.$router)
			},
			//前进
			forward(){
				this.$router.forward()
			},
			test(){
				// 往前走三步
				this.$router.go(3)
			}
		},
	}
</script>