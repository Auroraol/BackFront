<template>
	<div>
		<Student name="李四" sex="女" :age="18"/>
		<!-- <MyList :todos="todos" :checkTodo="checkTodo" :deleteTodo="deleteTodo"/> -->
	</div>
</template>

<script>
	import Student from './components/Student'

	export default {
		name:'App',
		components:{Student}
	}
</script>
