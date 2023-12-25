<template>
	<div>
		<button @click="isShow = !isShow">显示/隐藏</button>
		// 动画
		<transition name="xxx" appear>       //:appear="ture" 可以简写  表示默认实行
			<h1 v-show="isShow">你好啊！</h1>
		</transition>
	</div>
</template>

<script>
	export default {
		name:'Test',
		data() {
			return {
				isShow:true
			}
		},
	}
</script>

<style scoped>
	h1{
		background-color: orange;
	}

	/* 进入 */
	.xxx-enter-active{
		animation: atguigu 0.5s linear;
	}

	/* 离开 */
	.xxx-leave-active{
		animation: atguigu 0.5s linear reverse;
	}

	 /*动画  */
	@keyframes atguigu {
		from{
			transform: translateX(-100%);
		}
		to{
			transform: translateX(0px);
		}
	}
</style>