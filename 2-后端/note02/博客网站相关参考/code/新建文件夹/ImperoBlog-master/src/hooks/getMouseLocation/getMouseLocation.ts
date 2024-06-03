//获取鼠标位置的hook
import { onMounted } from 'vue'


type MouseLocation = {
    x: number
    y: number
}

const mouseLocation: MouseLocation = {
    x: 0,
    y: 0
}

const getMouse = () => {
    document.addEventListener('mousemove', (e) => {
        const mouseX = e.pageX
        const mouseY = e.pageY
        mouseLocation.x = mouseX
        mouseLocation.y = mouseY
    })
    return mouseLocation
}

export default getMouse