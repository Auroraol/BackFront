<template>
	<h1>一个人的信息</h1>
	<h2>姓名：{{name}}</h2>
	<h2>年龄：{{age}}</h2>
	<h3>工作种类：{{job.type}}</h3>
	<h3>工作薪水：{{job.salary}}</h3>
	<button @click="changeInfo">修改人的信息</button>
</template>

/*
- 作用: 定义一个基本类型的响应式的数据
- 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象(reference对象, 简称ref对象)</strong>。
- JS中操作数据: xxx.value
- 模板中读取数据: 不需要.value, 直接: <div>{{xxx}}</div>
 */
<script>
	import {ref} from 'vue'        // 引入
	export default {
		name: 'App',
		setup(){
			//数据
			let name = ref('张三')   //ref定义一个响应式的数据(动态)
			let age = ref(18)
			// 对象最好是使用reactive函数
			let job = ref({
				type:'前端工程师',
				salary:'30K'
			})

			//方法
			function changeInfo(){
				// name.value = '李四'
				// age.value = 48
				console.log(job.value)
				// job.value.type = 'UI设计师'  //不能写成job.type.value
				// job.value.salary = '60K'
				// console.log(name,age)
			}

			//返回一个对象（常用）
			return {
				name,
				age,
				job,
				changeInfo
			}
		}
	}
</script>

