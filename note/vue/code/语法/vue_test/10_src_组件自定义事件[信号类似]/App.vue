<template>
	<div class="app">
		<h1>{{msg}}，学生姓名是:{{studentName}}</h1>

		<!-- 通过父组件给子组件传递函数类型的props实现：子给父传递数据 -->
		<!-- <School :getSchoolName="getSchoolName"/> -->

		<!-- 通过父组件给子组件绑定一个自定义事件实现：子给父传递数据（第一种写法，使用@或v-on） 推荐-->)
		<Student @atguigu="getStudentName" @demo="m1"/>

		<!-- 通过父组件给子组件绑定一个自定义事件实现：子给父传递数据（第二种写法，使用ref） -->
		<!-- <Student ref="student" @click.native="show"/> -->
	</div>
</template>

/*

父类:
	@自定义事件="事件函数"
	事件函数(参数){}


子类
	this.$emit(自定义事件, 参数)   //触发事件   处理事件 ---> 子类调用事件函数  ---> "发起信号"
	this.$off('atguigu')      // 解绑一个事件
	this.$off(['atguigu','demo']) //解绑多个自事件 //注意是放在数组里面
	this.$destroy()               //销毁了当前组件的实例, 销毁后所有Student实例的自定义事件全都不奏效。
*/

<script>
	import Student from './components/Student'
	import School from './components/School'

	export default {
		name:'App',
		components:{School,Student},
		data() {
			return {
				msg:'你好啊！',
				studentName:''
			}
		},
		methods: {
			getSchoolName(name){
				console.log('App收到了学校名：',name)
			},
			// 事件处理函数             -----------------------
			getStudentName(name,...params){
				console.log('App收到了学生名：',name,params)
				this.studentName = name
			},
			m1(){
				console.log('demo事件被触发了！')
			},
			show(){
				alert(123)
			}
		},
		mounted() {
			this.$refs.student.$on('atguigu',this.getStudentName) //绑定自定义事件
			// this.$refs.student.$once('atguigu',this.getStudentName) //绑定自定义事件（一次性）
		},
	}
</script>

<style scoped>
	.app{
		background-color: gray;
		padding: 5px;
	}
</style>
