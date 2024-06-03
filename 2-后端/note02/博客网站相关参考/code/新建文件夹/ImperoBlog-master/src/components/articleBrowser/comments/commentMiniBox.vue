<template>
    <div class="commentMiniBox-container">
        <!-- 头像 -->
        <div class="headBox">
            <img :src="userInfo.headImg" alt="head" @click="goToPersonalCenter(userInfo)">
        </div>
        <!-- 内容 -->
        <div class="bodyBox">
            <!-- 标题 + 内容 -->
            <div class="title">
                <span style="cursor: pointer; font-weight: 600;" @click="goToPersonalCenter(userInfo)">{{
                    userInfo.nickName
                }}</span>
                <span>&nbsp;&nbsp;回复&nbsp;</span>
                <span class="replyName" @click="goToPersonalCenter(replyInfo)">@{{ replyInfo.nickName }}： </span>
                <p>{{ reply.content }}</p>
            </div>
            <!-- 时间 + 回复、删除 -->
            <div class="replyTime">
                <p>{{ reply.time }}</p>
                <span class="replyComment" @click="replyComment">回复</span>
            </div>
            <!-- 回复评论的盒子 -->
            <ReplyCommentBox v-if="ifReplyComment" @cancelReplyComment="cancelReplyComment" :commentId="props.commentId"
                :replyAccount="userInfo.account">
            </ReplyCommentBox>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import useAxios from "@/hooks/axios/axios";
import { useStore } from "@/store/count";
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus'
import ReplyCommentBox from "@/components/articleBrowser/comments/replyCommentBox.vue";

const pinia = useStore()
const router = useRouter()

type Reply = {
    id: number,
    replyTo: string,
    account: string,
    content: string,
    time: string
}
type Props = {
    reply: Reply,
    commentId: number
}
const props = defineProps<Props>()
const reply = props.reply

//请求对应的用户的信息(这个box属于谁)
const { data: res } = await useAxios.get('/userinfo', {
    params: {
        account: reply.account
    }
})
const userInfo = res.data

//请求回复(@) 的人的信息
const { data: replyUser } = await useAxios.get('/userinfo', {
    params: {
        account: reply.replyTo
    }
})
const replyInfo = replyUser.data

const token = localStorage.getItem('userAccount')
const goToPersonalCenter = (info: any) => {
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        if (tokenInfo.account === info.account) {   //如果点击的是自己的留言的头像，就跳转到自己的个人中心
            router.push('/personalcenter')
        } else {    //不是就正常跳转到别人的个人中心
            router.push({
                path: '/otherspersonalcenter',
                query: {
                    account: info.account
                }
            })
        }
    } else {    //未登录，直接跳转到别人的个人中心
        router.push({
            path: '/otherspersonalcenter',
            query: {
                account: info.account
            }
        })
    }
}


const ifReplyComment = ref(false)
const replyComment = () => {
    if (token) {
        ifReplyComment.value = !ifReplyComment.value
    } else {
        ElMessage({
            message: '登录后才能回复',
            type: 'warning',
        })
    }
}

const cancelReplyComment = (val: boolean) => {  //取消回复评论的组件（子传父false）
    ifReplyComment.value = val
}

</script>

<style scoped lang="less">
.commentMiniBox-container {
    display: flex;
    padding: 1rem 0;

    .headBox {
        width: 3.5rem;

        img {
            width: 2.5rem;
            height: 2.5rem;
            border-radius: 50%;
            background-color: rgb(228, 228, 228);
            object-fit: cover;
            cursor: pointer;
        }
    }

    .bodyBox {
        flex: 1;

        .title {
            span {
                font-size: 1.1rem;
                font-weight: 550;
            }

            p {
                display: inline;
            }

            .replyName {
                color: var(--special-font-color);
                cursor: pointer;
            }
        }

        .replyTime {
            color: var(--font-gray-color);
            display: flex;
            justify-content: flex-start;
            margin-bottom: 1rem;

            span {
                margin-left: 1rem;
            }

            p {
                margin-right: 1rem;
            }

            .delBtn {
                display: none;
                color: rgb(255, 88, 88);
                cursor: pointer;

            }

            .replyComment {
                color: var(--special-font-color);
                cursor: pointer;
            }
        }
    }
}
</style>