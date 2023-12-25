<template>
	<button @click="isShowDemo = !isShowDemo">切换隐藏/显示</button>
	<Demo v-if="isShowDemo"/>
	<hr>
	<Test/>
</template>

<script>
	import {ref} from 'vue'
	import Demo from './components/Demo'   //注入组件
	import Test from './components/Test'   //注入组件
	export default {
		name: 'App',
		components:{Demo,Test},
		setup() {
			let isShowDemo = ref(true)
			return {isShowDemo}
		}
	}
</script>

