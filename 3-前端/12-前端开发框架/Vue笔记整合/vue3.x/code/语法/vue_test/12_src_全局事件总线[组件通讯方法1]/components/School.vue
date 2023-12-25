<template>
	<div class="school">
		<h2>学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
	</div>
</template>

<script>
	export default {
		name:'School',
		data() {
			return {
				name:'尚硅谷',
				address:'北京',
			}
		},

		//在mounted使用事件总线
		mounted() {

			// console.log('School',this)    
			this.$bus.$on('hello',(data)=>{       // "接受信号"
				console.log('我是School组件，收到了数据',data)
			})
		},
		beforeDestroy() {
			this.$bus.$off('hello')   //$off去解绑<span style="color:red">当前组件所用到的</span>事件
		},
	}
</script>

<style scoped>
	.school{
		background-color: skyblue;
		padding: 5px;
	}
</style>