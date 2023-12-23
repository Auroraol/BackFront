import {reactive,onMounted,onBeforeUnmount} from 'vue'  // 引入
export default function (){
	//实现鼠标“打点”相关的数据
	let point = reactive({
		x:0,
		y:0
	})

	//实现鼠标“打点”相关的方法
	function savePoint(event){
		point.x = event.pageX
		point.y = event.pageY
		console.log(event.pageX,event.pageY)
	}

	//实现鼠标“打点”相关的生命周期钩子
	onMounted(()=>{
		window.addEventListener('click',savePoint)   //增加事件,调用函数savePoint
	})

	onBeforeUnmount(()=>{
		window.removeEventListener('click',savePoint) //移除事件
	})

	return point
}
