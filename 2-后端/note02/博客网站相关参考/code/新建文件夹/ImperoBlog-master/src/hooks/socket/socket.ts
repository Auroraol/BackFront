import { io } from 'socket.io-client'
import { ElMessage } from 'element-plus'
import { useStore } from "@/store/count"
const pinia = useStore()

//连接服务器
const socketHttpUrl = pinia.socketUrl
const socket = io(socketHttpUrl, ({
    autoConnect: false,
    auth: {
        userInfo: window.atob(localStorage.getItem('userAccount')!)
    }
}))
//监听器，啥都监听
socket.onAny((event, ...args) => {
    // console.log(event, args);
});

socket.on('connect', () => { }) //连接socket
socket.io.on('error', (error) => {
    ElMessage.error('网络连接错误' + error)
})
socket.io.on("reconnect", (attempt) => {
    ElMessage({
        message: '重新连接成功',
        type: 'success',
    })
});


export {
    socket
}

