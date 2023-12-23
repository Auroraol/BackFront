<template>
	<input type="text" v-model="keyWord">
	<h3>{{keyWord}}</h3>
</template>
/*
作用：创建一个自定义的 ref，并对其依赖项跟踪和更新触发进行显式控制。
*/
<script>
	import {ref,customRef} from 'vue'
	export default {
		name: 'App',
		setup() {
			//1. 自定义一个ref——名为：myRef
			function myRef(value,delay){
				let timer
				// 2. customRef api
				return customRef((track,trigger)=>{
					return {
						// 读
						get(){
							console.log(`有人从myRef这个容器中读取数据了，我把${value}给他了`)
							track() //通知Vue追踪value的变化（提前和get商量一下，让他认为这个value是有用的）
							return value
						},
						// 改
						set(newValue){
							console.log(`有人把myRef这个容器中数据改为了：${newValue}`)
							// 改了重新调用读
							clearTimeout(timer)
							timer = setTimeout(()=>{
								value = newValue
								trigger() //通知Vue去重新解析模板,-->相当于去调用get()
							},delay)
						},
					}
				})
			}

			// let keyWord = ref('hello') //使用Vue提供的ref
			let keyWord = myRef('hello',500) //使用程序员自定义的ref
			
			return {keyWord}
		}
	}
</script>

