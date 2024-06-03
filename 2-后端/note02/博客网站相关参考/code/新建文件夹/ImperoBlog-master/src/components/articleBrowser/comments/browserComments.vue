<template>
    <div class="browserComment-container">
        <div class="addComment">
            <!-- 标题 -->
            <div class="title">
                <p>写个留言吧</p>
                <span></span>
            </div>
            <!-- 内容 -->
            <div class="commentText">
                <!-- 头像 -->
                <div class="headimg">
                    <img :src="commentHeadImg" alt="head">
                </div>
                <!-- input -->
                <el-input v-model="commentTextInfo" maxlength="600" placeholder="输入一条留言吧" show-word-limit type="textarea"
                    class="textInputArea" :autosize="{ minRows: 4, maxRows: 6 }" resize="none" />
            </div>
            <!-- 发布 -->
            <div class="commentsend">
                <BtnSend class="btnSend" @click="addComment" v-if="ifAddComment">
                    <template v-slot>
                        点击发布
                    </template>
                </BtnSend>
                <BtnSend class="btnSend" v-else>
                    <template v-slot>
                        发布中..
                    </template>
                </BtnSend>
            </div>
        </div>
        <!-- 更新评论列表dom -->
        <commentsList :key="pinia.articleCommentsAdd"></commentsList>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import commentsList from "@/components/articleBrowser/comments/commentsList.vue";
import useAxios from "@/hooks/axios/axios";
import { useStore } from "@/store/count";
import BtnSend from "@/components/UIVerse/btn-send.vue";
import { ElMessage } from 'element-plus'

const pinia = useStore()

type Props = {
    articleId: string,   //文章id
}
const props = defineProps<Props>()

let commentHeadImg = ref()  //头像地址
const token = localStorage.getItem('userAccount')  //获取token
if (!token) {  //如果没有登录展示默认头像
    const { data: res } = await useAxios.get('/getdefaulthead')
    commentHeadImg.value = res.data
} else {
    const userAccount = JSON.parse(window.atob(token))
    const { data: res } = await useAxios.get('/userinfo', {
        params: {
            account: userAccount.account
        }
    })
    commentHeadImg.value = res.data.headImg
}

//输入内容区域
const commentTextInfo = ref('')
const ifAddComment = ref(true)

const addComment = async () => {
    if (!token) {
        //没有登录不能发布评论
        ElMessage({
            message: '登录后发布评论',
            type: 'warning',
        })
    } else {
        if (commentTextInfo.value === '') {
            ElMessage({
                message: '不能发布空评论',
                type: 'error',
            })
        } else {
            ifAddComment.value = !ifAddComment.value
            const userAccount = JSON.parse(window.atob(token))
            const { data: res } = await useAxios.get('/articleaddcomment', {
                params: {
                    articleId: props.articleId,
                    account: userAccount.account,
                    content: commentTextInfo.value
                }
            })
            if (res) {
                if (res.status === 0) {
                    ElMessage({
                        message: '发布成功',
                        type: 'success',
                    })
                    commentTextInfo.value = ''
                    ifAddComment.value = !ifAddComment.value
                    pinia.articleCommentsAdd += 1   //更新pinia刷新dom
                } else {
                    ElMessage({
                        message: '发送失败',
                        type: 'error',
                    })
                    ifAddComment.value = !ifAddComment.value
                }
            } else {
                ElMessage({
                    message: '操作出现错误',
                    type: 'error',
                })
                ifAddComment.value = !ifAddComment.value
            }
        }


    }
}


</script>

<style scoped lang="less">
.browserComment-container {
    width: 100%;
    background-color: rgb(255, 255, 255);
    margin: 0 auto;
    margin-top: 1rem;
    border-radius: .5rem;
    box-sizing: border-box;
    padding: 1rem;

    .addComment {
        width: 100%;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
        padding: 1rem;
        border-bottom: .2rem solid var(--gray-light-border);

        .title {
            font-size: 1.4rem;
            font-weight: 700;

            span {
                margin-top: .5rem;
                display: block;
                width: 4rem;
                height: .5rem;
                background-image: linear-gradient(to left, var(--gradient-start-one), var(--gradient-end-one));
                border-radius: 1rem;
            }
        }
    }

    .commentText {
        display: flex;
        width: 100%;
        margin: 1rem 0;

        .headimg {
            width: 5rem;
            height: 5rem;
            border-radius: 50%;
            overflow: hidden;
            background-color: rgb(231, 231, 231);
            margin-right: 1rem;

            img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
        }

        .textInputArea {
            flex: 1;
        }
    }

    .commentsend {
        display: flex;
        justify-content: flex-end;

        .btnSend {
            // width: 10rem;
            height: 3rem;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
        }
    }
}
</style>