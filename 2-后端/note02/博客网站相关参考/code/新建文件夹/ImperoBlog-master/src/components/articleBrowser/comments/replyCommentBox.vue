<template>
    <div class="relpyCommentBox-container">
        <!-- 头像和输入框 -->
        <div class="headAndIpt">
            <!-- 头像 -->
            <div class="headBox">
                <img :src="userInfo.headImg" alt="">
            </div>
            <!-- 输入框 -->
            <el-input v-model="commentTextInfo.replyContent" maxlength="200" placeholder="输入回复信息" show-word-limit
                type="textarea" class="textInputArea" :autosize="{ minRows: 2, maxRows: 4 }" resize="none" />
        </div>
        <!-- 取消和发送按钮 -->
        <div class="btns">
            <el-button @click="cancelReplyComment">取消</el-button>
            <el-button type="primary" @click="relpyComment">确定</el-button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, inject } from "vue";
import useAxios from '@/hooks/axios/axios';
import { useStore } from "@/store/count";
import { ElMessage } from 'element-plus'

const pinia = useStore()
const emit = defineEmits(['cancelReplyComment'])
type Props = {
    commentId: number  //评论id
    replyAccount: string  //回复的谁
}
const props = defineProps<Props>()  //收到当前回复的评论的id

const token = localStorage.getItem('userAccount')
const tokenInfo = JSON.parse(window.atob(token!))
// 请求token的信息
const { data: res } = await useAxios.get('/userinfo', {
    params: {
        account: tokenInfo.account
    }
})
const userInfo = res.data

type Comments = {
    id: number,
    account: string,
    time: string,
    content: string
}
type Inject = {
    id: string,
    comments: Array<Comments>
}
const commentsInject: Inject = inject('comments')!  //拿到祖组件依赖注入传过来的数据(文章id和评论列表)

const commentTextInfo = reactive({
    articleId: commentsInject.id,  //文章id
    commentId: props.commentId,   //评论id 
    account: userInfo.account,   //自己的信息
    replyAccount: props.replyAccount,   //回复的谁
    replyContent: '',  //回复的内容
})
const cancelReplyComment = () => {  //emit子传父
    emit('cancelReplyComment', false)
}
const relpyComment = async () => {
    if (commentTextInfo.replyContent === '') {
        ElMessage({
            message: '不能发表空回复',
            type: 'warning',
        })
        return
    } else {
        const { data: res } = await useAxios.post('/replycomment', commentTextInfo)
        if (res.status === 0) {
            ElMessage({
                message: '回复成功',
                type: 'success',
            })
            commentTextInfo.replyContent = ''
            cancelReplyComment()
            pinia.articleCommentsAdd += 1
        } else {
            ElMessage({
                message: '出现错误',
                type: 'error',
            })
        }
    }
}

</script>

<style scoped lang="less">
.relpyCommentBox-container {
    display: flex;
    flex-direction: column;

    .headAndIpt {
        display: flex;

        .headBox {
            width: 5rem;

            img {
                width: 3.5rem;
                height: 3.5rem;
                border-radius: 50%;
            }
        }
    }

    .btns {
        margin-top: .5rem;
        display: flex;
        justify-content: flex-end;
    }
}
</style>