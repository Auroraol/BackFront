<template>
    <div class="container">
        <div class="middle" :key="messageBoxkey">
            <div class="title">
                <div>留言版</div>
                <div>这是一个留言板，欢迎留言</div>
            </div>
            <div class="writeMessage">
                <div class="userHead">
                    <img :src="messageHeadImg" alt="default">
                </div>
                <el-input @input="saveEditor" v-model="writeMessage" :autosize="{ minRows: 3, maxRows: 6 }" type="textarea"
                    placeholder="写下一条留言吧" class="userWrite" resize="none" maxlength="120" show-word-limit />
                <div class="sendBtn" @click="sendMessage">
                    <span>发布</span>
                </div>
            </div>
            <Suspense v-for="item in messageList" :key="item.id">
                <template #default>
                    <leaveMessage :item="item">
                    </leaveMessage>
                </template>
                <template #fallback>
                    <div class="window">
                        <Loading class="winLoad"></Loading>
                    </div>
                </template>
            </Suspense>
        </div>

        <div class="rightBox">
            <Suspense>
                <template #default>
                    <Right class="right"></Right>
                </template>
                <template #fallback>
                    <div class="window">
                        <Loading class="winLoad"></Loading>
                    </div>
                </template>
            </Suspense>
        </div>

    </div>
</template>

<script setup lang="ts">
import { ref, defineAsyncComponent, watchEffect, onMounted } from 'vue'
import useAxios from "@/hooks/axios/axios";
import { useStore } from '@/store/count'
import { ElMessage } from 'element-plus'
import Loading from "@/components/loading/loading2.vue";
import gsap from 'gsap'
import { debonce } from '@/hooks/debonceAndThrottle/debonceAndThrottle'

const leaveMessage = defineAsyncComponent(() => import('@/components/leaveMessage/leaveMessage.vue'))
const Right = defineAsyncComponent(() => import('@/components/rightToolsBox/ArticleRight.vue'))

const writeMessage = ref('')  //写下的留言
const pinia = useStore()

onMounted(() => {
    gsap.from('.container', {
        duration: 0.5,
        opacity: 0.2,
        x: -100
    })
})

// 如果有草稿就同步一下草稿
const leaveMessageDraftSession = sessionStorage.getItem('leaveMessageDraft')
if (leaveMessageDraftSession) {
    writeMessage.value = leaveMessageDraftSession
}

//保存草稿（防抖,避免一直触发）
const saveEditor = debonce(() => {
    const draft = writeMessage.value
    sessionStorage.setItem('leaveMessageDraft', draft)
}, 500)

let messageHeadImg = ref()
//检查是否登录
const session = localStorage.getItem('userAccount')  //获取session

if (!session) {   //如果没有登录选择默认头像展示
    const { data: res } = await useAxios.get('/getdefaulthead')
    messageHeadImg.value = res.data

} else {   //登录了展示用户自己的头像
    const userAccount = JSON.parse(window.atob(session))
    const { data: res } = await useAxios.get('/userinfo', {
        params: {
            account: userAccount.account
        }
    })
    messageHeadImg.value = res.data.headImg
}

//请求留言列表
const { data: res } = await useAxios.get('/getmessages')
const messageList = ref(res.data.reverse())

let messageBoxkey = ref(0)

//发表留言
const sendMessage = async () => {
    if (!session) {
        alert('没有登录，先登录吧')
    } else {
        if (writeMessage.value === '') {
            ElMessage({
                message: '不能发布空白的留言',
                type: 'warning',
            })
        } else {
            const userAccount = JSON.parse(window.atob(session))
            const { data: res } = await useAxios.get('/uploadmessage', {
                params: {
                    account: userAccount.account,
                    content: writeMessage.value
                }
            })
            if (res.status === 0) {
                ElMessage({
                    message: '发布成功',
                    type: 'success',
                })
                const { data: res } = await useAxios.get('/getmessages')
                messageList.value = res.data.reverse()
                messageBoxkey.value += 1
                writeMessage.value = ''
            } else {
                ElMessage.error('发布失败')
                writeMessage.value = ''
            }
        }

    }
}

//如果监听到删除留言操作重新渲染dom
watchEffect(async () => {
    if (pinia.ifDelMessage) {
        const { data: res } = await useAxios.get('/getmessages')
        messageList.value = res.data.reverse()
        messageBoxkey.value += 1
    }
})

</script>

<style scoped lang="less">
.container {
    width: 75%;
    margin: 0 auto;
    display: flex;
    box-sizing: border-box;

    .middle {
        box-sizing: border-box;
        min-height: 90rem;
        max-height: 125rem;
        overflow-y: scroll;
        flex: 1;
        padding: 1rem 2rem;
        background-color: var(--white-background-color);
        border-radius: .4rem;

        .title {
            height: 8rem;
            margin-top: 1rem;
            border-radius: 1rem;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

            :nth-child(1) {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            :nth-child(2) {
                font-size: 1.2rem;
            }
        }

        .writeMessage {
            width: 100%;
            display: flex;
            align-items: center;
            box-sizing: border-box;
            padding: 1rem 2rem;

            .userHead {
                width: 6rem;
                height: 6rem;
                border-radius: 50%;
                margin-right: 1rem;
                overflow: hidden;

                img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
            }

            .userWrite {
                flex: 1;
                background-color: rgb(173, 245, 229);
            }

            .sendBtn {
                width: 6rem;
                height: 6rem;
                background-color: var(--box-shadow);
                margin-left: 1rem;
                border-radius: .5rem;
                display: flex;
                justify-content: center;
                align-items: center;
                color: #fff;
                font-size: 1.2rem;
                font-weight: 500;

                &:hover {
                    cursor: pointer;
                    background-color: var(--special-font-color);
                }
            }
        }
    }

    .right {
        margin-left: .5rem;
        // padding: 2rem;
        // background-color: var(--backgeound-color);
        // border: .1rem solid var(--gray-sahdow);
        // border-radius: .5rem;
    }
}

@media only screen and (max-width: 1200px) {
    .right {
        display: none;
    }
}

@media only screen and (max-width: 1200px) {
    .container {
        width: 100%;
        border: none;

        .rightBox {
            display: none;
        }
    }

}
</style>