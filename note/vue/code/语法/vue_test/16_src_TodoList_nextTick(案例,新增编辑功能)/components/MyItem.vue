<template>
	<li>
		<label>
			<input type="checkbox" :checked="todo.done" @change="handleCheck(todo.id)"/>
			<!-- 如下代码也能实现功能，但是不太推荐，因为有点违反原则，因为修改了props -->
			<!-- <input type="checkbox" v-model="todo.done"/> -->
			<span v-show="!todo.isEdit">{{todo.title}}</span>   //  v-show条件语句
			<!-- 有isEdit就显示的编辑框 $event传递该事件 -->
			<input 
				type="text" 
				v-show="todo.isEdit" 
				:value="todo.title" 
				@blur="handleBlur(todo,$event)"    
				ref="inputTitle"
			>
		</label>
		<button class="btn btn-danger" @click="handleDelete(todo.id)">删除</button>
		<button v-show="!todo.isEdit" class="btn btn-edit" @click="handleEdit(todo)">编辑</button>  //绑定事件@click="handleEdit(todo)
	</li>
</template>

<script>
	import pubsub from 'pubsub-js'
	export default {
		name:'MyItem',
		//声明接收todo
		props:['todo'],
		methods: {
			//勾选or取消勾选
			handleCheck(id){
				//通知App组件将对应的todo对象的done值取反
				// this.checkTodo(id)
				this.$bus.$emit('checkTodo',id)
			},
			//删除
			handleDelete(id){
				if(confirm('确定删除吗？')){
					//通知App组件将对应的todo对象删除
					// this.deleteTodo(id)
					// this.$bus.$emit('deleteTodo',id)
					pubsub.publish('deleteTodo',id)             // 发布
				}
			},
			//编辑
			handleEdit(todo){
				// tode对象有isedit属性就修改其他就追加
				if(todo.hasOwnProperty('isEdit')){
					todo.isEdit = true
				}else{
					// console.log('@')
					this.$set(todo,'isEdit',true)          //给对象添加一个isEdit属性
				}
				// 官方api $nextTick在节点更新往后调用
				this.$nextTick(function(){
					this.$refs.inputTitle.focus()        // 焦点定位
				})
			},
			//失去焦点回调（真正执行修改逻辑） //@blur=去焦点事件  //当改变数据后，要基于更新后的新DOM进行某些操作时，要在nextTick所指定的回调函数中执行。
			handleBlur(todo,e){
				todo.isEdit = false
				if(!e.target.value.trim()) return alert('输入不能为空！')
				this.$bus.$emit('updateTodo',todo.id,e.target.value)
			}
		},
	}
</script>

<style scoped>
	/*item*/
	li {
		list-style: none;
		height: 36px;
		line-height: 36px;
		padding: 0 5px;
		border-bottom: 1px solid #ddd;
	}

	li label {
		float: left;
		cursor: pointer;
	}

	li label li input {
		vertical-align: middle;
		margin-right: 6px;
		position: relative;
		top: -1px;
	}

	li button {
		float: right;
		display: none;
		margin-top: 3px;
	}

	li:before {
		content: initial;
	}

	li:last-child {
		border-bottom: none;
	}

	li:hover{
		background-color: #ddd;
	}
	
	li:hover button{
		display: block;
	}
</style>