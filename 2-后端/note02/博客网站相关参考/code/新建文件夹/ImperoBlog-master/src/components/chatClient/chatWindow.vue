<template>
    <div class="chatWindow-container">
        <el-divider />
        <!-- 聊天窗口 -->
        <div class="chatMain" ref="chatMainWindow">
            <!-- 消息盒子 -->
            <div class="chatLogBox" v-for="item in chatLogBox.logInfo" :key="item.id">
                <!-- 头像 -->
                <img style="cursor: pointer;" :src="item.head" alt="" @click="goPersonalCenter(item.account)">
                <!-- 右侧 -->
                <div class="right">
                    <!-- 昵称和时间 -->
                    <div class="nameAndTme">
                        <span>{{ item.nickName }}</span>
                        <span>{{ item.time }}</span>
                    </div>
                    <!-- 内容 -->
                    <div class="chatContent" style="white-space: pre-line;">
                        {{ item.content }}
                    </div>
                </div>
            </div>
        </div>
        <!-- 输入窗口 -->
        <div class="iptChat">
            <el-input v-model="chatLogIpt" :autosize="{ minRows: 2, maxRows: 6 }"
                :input-style="{ borderRadius: '1rem', flex: '1' }" resize="none" :maxlength="1000" :show-word-limit="true"
                type="textarea" placeholder="发送消息" @keydown="sendKeyDown" />
            <button @click="sendChatLog">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-fasong"></use>
                </svg>
                <span style="margin-left: .3rem;">发送</span>
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, toRaw, reactive, onMounted } from 'vue'
import useAxios from '@/hooks/axios/axios'
import { ElMessage } from 'element-plus'
import { ChatLog, ChatLogBox } from '@/hooks/Types/types'
import { useStore } from '@/store/count'
import { socket } from '@/hooks/socket/socket'
import { goToPersonalCenterHook } from "@/hooks/goToPersonalCenter/goToPersonalCenter";
import { useRoute } from 'vue-router'
const route = useRoute()
const pinia = useStore()

type Props = {
    logInfo: Array<ChatLog>
}
const props = defineProps<Props>()
const chatLogIpt = ref('') //输入的聊天内容
const logInfoProps = toRaw(props.logInfo)
const chatMainWindow = ref() //获取聊天视窗的dom，自动滚动
let chatLogBox = reactive({
    logInfo: new Array<ChatLogBox>
});

onMounted(() => {
    //把聊天窗口滚动到最底部
    const chatDom = chatMainWindow.value as HTMLElement
    const observer = new MutationObserver(mutationsList => {
        // 滚动到最底部
        chatDom.scrollTop = chatDom.scrollHeight;
    });
    observer.observe(chatDom, { childList: true })
})

//定义新消息盒子
const newChatLogInfo = async (newLog: ChatLog) => {
    const { data: res } = await useAxios.get('/userinfo', { params: { account: newLog.account } })
    const info: ChatLogBox = {
        id: newLog.id,
        account: newLog.account,
        head: res.data.headImg,
        nickName: res.data.nickName,
        time: newLog.time,
        content: newLog.content
    }
    return info
}

//拿到props传来的消息记录，然后请求对应的用户进行补全，最后更新要渲染的消息记录的数组
const shiftPropsLogInfo = async () => {
    for (let i = 0; i < logInfoProps.length; i++) {
        chatLogBox.logInfo.push(await newChatLogInfo(logInfoProps[i]))
    }
}
shiftPropsLogInfo()

//socket广播更新记录
const socketUploadLog = async (newLog: any) => {
    socket.emit('uploadLocalLog', [herfRoomName(), await newChatLogInfo(newLog)])
}

socket.on('uploadLocalLog', (data, cb) => {
    if (herfRoomName() === data[0]) chatLogBox.logInfo.push(data[1])
})

//清空输入框
const clearChatLog = () => {
    chatLogIpt.value = ''
}

//从url获取当前房间名字
const herfRoomName = () => {
    const roomName = route.query.name  //房间名字
    return roomName
}

//发送消息
const sendChatLog = async () => {
    if (chatLogIpt.value === '') {
        ElMessage({
            message: '不能发送空消息',
            type: 'warning',
        })
        return
    }
    const roomName = herfRoomName()
    const account = JSON.parse(window.atob(localStorage.getItem('userAccount')!))
    const { data: getRoom } = await useAxios.get('/getchatlog', { params: { name: roomName } })
    const roomArr = getRoom.data
    let newId;
    if (!roomArr) return ElMessage.error(`你发送消息的房间 ${herfRoomName()} 已经被删除`)
    if (roomArr.length === 0) {
        newId = 0
    } else {
        newId = Number(roomArr[roomArr.length - 1].id) + 1
    }
    //更新数据库的聊天记录
    const newLog = {
        id: newId,
        roomName: roomName,
        account: account.account,
        content: chatLogIpt.value,
    }
    const { data: res } = await useAxios.post('/setnewlog', newLog)

    switch (res.status) {
        case 0:
            ElMessage({
                message: '发送成功',
                type: 'success',
            })
            break
        case 1:
            ElMessage.error('房间不存在')
            break
    }

    //socket广播给所有用户更新本地记录，不需要重新请求渲染消息记录
    socketUploadLog(newLog)

    clearChatLog()
}

//键盘enter发送消息
const sendKeyDown = (e: any) => {
    if (e.key === 'Enter') {
        e.preventDefault();
    }
    if (e.key === 'Enter' && !e.shiftKey) {
        sendChatLog()
    }
}

const goPersonalCenter = (account: any) => {
    goToPersonalCenterHook(account)
}

</script>

<style scoped lang="less">
.chatWindow-container {
    box-sizing: border-box;
    height: 100%;
    padding: .5rem;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .chatMain {
        flex: 1;
        overflow-y: auto;
        scroll-behavior: smooth;

        .chatLogBox {
            display: flex;
            align-items: center;
            box-sizing: border-box;
            padding: 1rem;
            border-radius: 1rem;
            transition: all .3s;

            &:hover {
                background-color: var(--chat-gray-back);
            }

            img {
                align-self: flex-start;
                width: 4rem;
                border-radius: 50%;
            }

            .right {
                margin-left: 1rem;

                .nameAndTme {
                    :nth-child(1) {
                        font-weight: 600;
                        font-size: 1.3rem;
                        color: red;
                    }

                    :nth-child(2) {
                        color: var(--font-gray-color);
                        margin-left: .5rem;
                    }
                }

                .chatContent {
                    font-size: 1.1rem;
                }
            }
        }
    }

    .iptChat {
        display: flex;
        align-items: center;

        button {
            box-sizing: border-box;
            height: 3rem;
            border-radius: 1rem;
            margin-left: .5rem;
            background-color: var(--chat-gray-back);
            color: #fff;
            transition: all .3s;
            padding: 0 .8rem;

            &:hover {
                cursor: pointer;
                background-color: var(--special-font-color);
            }
        }
    }
}

@media screen and (max-width: 800px) {
    .el-divider--horizontal {
        display: none;
    }
}
</style>