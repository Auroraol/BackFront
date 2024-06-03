<template>
    <div class="chat-container">
        <!-- 左侧公告和房间 -->
        <!-- 分割线 -->
        <div class="roomsListBox">
            <!-- 房间列表标题 -->
            <div
                style="display: flex;justify-content: space-between;align-items: center;box-sizing: border-box;padding: 0 1rem;">
                <span style="font-size: 1.2rem;">房间列表</span>
                <span style="font-weight: 700;font-size: 1.5rem;cursor: pointer;" @click="addRoom">+</span>
            </div>
            <div v-if="addNewRoom" class="addRoomBox">
                <el-input v-model="inputNewRoom" size="small" maxlength="8" minlength="1"
                    :input-style="{ marginBottom: '.2rem' }" placeholder="添加房间" :show-word-limit="true" />
                <button
                    style="background-color: #fff; padding:  .1rem .8rem;cursor: pointer;margin-right: 1rem;border-radius: 1rem;"
                    @click="cancleAddRoom">取消</button>
                <button
                    style="background-color: var(--special-font-color);color: #fff;padding:.1rem .8rem;cursor: pointer;border-radius: 1rem;"
                    @click="setNewRoom">确定</button>
            </div>
            <!-- 房间列表 -->
            <div class="roomsList">
                <router-link :class="`roomsItem ${route.query.name === item.name ? 'routerLinkActive' : ''}`"
                    v-for="item in allRooms" :key="item.id" :to="{ path: '/chat', query: { name: item.name } }"
                    @click="clickRoomLog(item.name)">
                    <span>{{ item.name }}</span>
                    <el-popconfirm title="确定要删除房间？" @confirm="delRoom(item.name)">
                        <template #reference>
                            <button class="delBtn">
                                <svg class="icon" aria-hidden="true">
                                    <use xlink:href="#icon-shanchu"></use>
                                </svg>
                            </button>
                        </template>
                    </el-popconfirm>
                </router-link>
            </div>
        </div>
        <!-- 中间聊天区域 -->
        <div class="main">
            <ChatWindow :key="uploadChatWindow" :logInfo="chatLogInfo"></ChatWindow>
        </div>
        <!-- 右侧在线列表 -->
        <div class="onlineList">
            <p>在线 - {{ onlineList.length }}</p>
            <div class="userList" v-for="item in onlineList" :key="item.id" @click="goPersonalCenter(item)">
                <img style="width: 3.5rem;border-radius: 50%;margin: .2rem;margin-right: .5rem;" :src="item.headImg" alt="">
                <span style="font-size: 1.2rem;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{
                    item.nickName }} <span v-if="item.root" style="color: red;">*</span>
                </span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onBeforeUnmount } from 'vue'
import { socket } from '@/hooks/socket/socket';
import { useStore } from '@/store/count';
import useAxios from '@/hooks/axios/axios';
import { ElMessage } from 'element-plus'
import ChatWindow from '@/components/chatClient/chatWindow.vue'
import { ChatLog } from '@/hooks/Types/types'
import { goToPersonalCenterHook } from "@/hooks/goToPersonalCenter/goToPersonalCenter";
import { useRoute, useRouter } from 'vue-router';
const pinia = useStore()
const router = useRouter()
const route = useRoute()

let allRooms = ref();
let onlineList = ref<any>([]) //在线列表
const inputNewRoom = ref('')  //添加的新房间的名字
const addNewRoom = ref(false)  //添加房间
const uploadChatWindow = ref(0)  //刷新子组件
let chatLogInfo = ref(new Array<ChatLog>); //消息记录，传给子组件


const userAccount = localStorage.getItem('userAccount')
if (userAccount === null) {
    alert('还没有登录,去登录吧')
    router.replace('/login')
}

socket.connect()  //连接socket服务器,登陆者账号也被发送到后端

//组件销毁要退出连接
onBeforeUnmount(() => {
    socket.disconnect()
})


//进入页面主动获取列表
socket.emit('getUsers', 'getOnlineList', (res: any) => {
    onlineList.value = res
})


// 有人登录退出被动获取当前在线列表
socket.on('loginUsers', (data) => {
    onlineList.value = data
})
///房间发送变化
//向后端发送emit，然后向所有前端广播，所有前端都更新房间列表
const sendChangeRoom = () => {
    socket.emit('roomsChange', 'roomsChange')
}
socket.on('roomsChange', () => {
    getAllRooms()
})
//进入页面获取房间列表
const getAllRooms = async () => {
    const { data: res } = await useAxios.get('/getallroomsname')
    allRooms.value = res.data
    getRoomLog('默认房间')  //刚进入页面默认请求默认房间的消息记录
}
getAllRooms()

//请求对应的房间消息记录
const getRoomLog = async (name: string) => {
    const { data: res } = await useAxios.get('/getchatlog', { params: { name: name } })
    if (res.status === 1) return alert('没有对应房间')
    chatLogInfo.value = res.data
    uploadChatWindow.value++
}

//点击房间名字要请求对应的消息记录
const clickRoomLog = (name: string) => {
    //如果点击的房间就是现在所在的房间就不请求
    const nowUrl = decodeURI(window.location.href).split('/')
    const nowRoom = nowUrl[nowUrl.length - 1]
    if (name === nowRoom) return
    //否则请求
    getRoomLog(name)
}


//添加房间
const addRoom = () => {
    addNewRoom.value = true
}

//取消添加房间
const cancleAddRoom = () => {
    addNewRoom.value = false
}

//确定添加房间
const setNewRoom = async () => {
    if (inputNewRoom.value === '') return alert('房间名不能为空')
    const { data: res } = await useAxios.get('/setnewchatroom', { params: { name: inputNewRoom.value } })
    if (res.status === 1) {
        ElMessage({
            message: '房间名已存在或者房间数量已达上限',
            type: 'warning',
        })
        inputNewRoom.value = ''
    }
    if (res.status === 0) {
        ElMessage({
            message: '添加成功',
            type: 'success',
        })
        cancleAddRoom()
        sendChangeRoom()
        inputNewRoom.value = ''
    }
}


//删除房间
const delRoom = async (name: string) => {

    const { data: res } = await useAxios.get('/delroom', { params: { name: name } })
    switch (res.status) {
        case 0:
            ElMessage({
                message: '删除成功',
                type: 'success',
            })
            sendChangeRoom()
            break;
        case 1:
            ElMessage.error('删除失败')
            break;
        case 2:
            ElMessage({
                message: '默认房间不允许删除',
                type: 'warning',
            })
            break;
    }
}

//点击头像去个人空间
const goPersonalCenter = (item: any) => {
    goToPersonalCenterHook(item.account)
}

</script>

<style scoped lang="less">
.chat-container {
    box-sizing: border-box;
    padding: .5rem;
    width: 75%;
    height: 84vh;
    margin: 0 auto;
    background-color: #2f3235;
    color: #fff;
    display: grid;
    grid-template-columns: 15rem 1fr 16rem;
    border-radius: 1rem;


    .roomsListBox {
        display: flex;
        flex-direction: column;

        .roomsList {
            display: flex;
            flex-direction: column;
            position: relative;
            overflow-y: scroll;

            .roomsItem {
                box-sizing: border-box;
                padding: .6rem;
                margin: .2rem 0;
                border-radius: .5rem;
                transition: all .3s;
                color: #fff;
                position: relative;

                &:hover {
                    cursor: pointer;
                    background-color: var(--chat-gray-back);
                }

                .delBtn {
                    position: absolute;
                    top: 50%;
                    right: 5%;
                    transform: translateY(-50%);
                    color: #fff;
                    background-color: transparent;

                    &:hover {
                        cursor: pointer;
                        color: red;
                    }
                }
            }

        }
    }


    .main {
        overflow-y: scroll;
        height: 100%;
    }

    .onlineList {
        overflow-y: auto;
        box-sizing: border-box;
        padding: .5rem;
        border-left: 1px solid var(--chat-gray-back);

        .userList {
            display: flex;
            align-items: center;
            transition: all .3s;

            &:hover {
                cursor: pointer;
                color: var(--special-font-color);
            }
        }
    }
}

.routerLinkActive {
    background-color: var(--chat-gray-back);
}

@media screen and (max-width: 1050px) {
    .chat-container {
        width: 100%;
    }
}

@media screen and (max-width: 800px) {
    .chat-container {
        grid-template-columns: none;
        grid-template-rows: 10rem 1fr;

        .roomsListBox {
            overflow-y: scroll;
        }

        .onlineList {
            display: none;
        }
    }

}
</style>