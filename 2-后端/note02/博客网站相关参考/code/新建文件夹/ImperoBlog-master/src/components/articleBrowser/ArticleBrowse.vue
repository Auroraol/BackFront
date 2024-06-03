<template>
    <Transition @enter="gsapEnterCover" :css="false">
        <div class="coverImg" v-if="ifCover">
            <img :src="browseArticle.cover" alt="">
            <div class="mask">
                <div class="title">{{ browseArticle.title }}</div>
                <div class="author" @click="goToPersonalCenter">
                    <div class="head">
                        <img :src="authorInfo.headImg" alt="head">
                    </div>
                    <div class="nickName">{{ authorInfo.nickName }}</div>
                </div>
                <div class="time">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-rili"></use>
                    </svg>
                    <span>{{ browseArticle.time }}</span>
                </div>
            </div>
        </div>
    </Transition>

    <div class="articleMd">
        <div class="contaier">
            <div class="mdTextTop">
                <div class="editor">
                    <button class="option" @click="expandEdiorBox">编辑</button>
                    <div class="ediorBox">
                        <button @click="topArticle">置顶</button>
                        <button @click="openEditor">编辑</button>
                        <button @click="openDel">删除</button>
                    </div>
                    <Teleport to="body">
                        <el-dialog v-model="dialogFormVisible" title="删除文章">
                            <div style="margin-bottom: 2rem ;">如果要删除此文章，请输入以下验证码数字：{{ randomNum }}</div>
                            <el-input v-model="code" placeholder="Please input" />
                            <template #footer>
                                <span class="dialog-footer">
                                    <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
                                    <el-button @click="trueDel">确定</el-button>
                                </span>
                            </template>
                        </el-dialog>
                    </Teleport>
                </div>
                <!-- 文章内容 -->
                <div class="articleIndex">
                    <md-editor :showCodeRowNumber="true" editor-id="articleBrowse" v-model="browseArticle.content"
                        preview-only class="mdText" />
                </div>
            </div>

            <!-- 点赞收藏数量显示组件 -->
            <BrowserBottom :articleInfo="browseArticle"></BrowserBottom>

            <!-- 评论区组件 -->
            <Suspense>
                <template #default>
                    <browserComment :articleId="(articleId as string)"></browserComment>
                </template>

                <template #fallback>
                    <Loading2></Loading2>
                </template>
            </Suspense>
        </div>
        <div class="catalog">
            <div class="cataSide" ref="sticky">
                <div class="titleTop">文章目录</div>
                <md-catalog editor-id="articleBrowse" class="browserCatalog" :scrollElement="scrollElement" />
                <BrowserSide class="browserSide" :id="(articleId as string)"></BrowserSide>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineAsyncComponent, provide } from 'vue';
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { useRoute, useRouter } from "vue-router";
import useAxios from "@/hooks/axios/axios";
import gsap from 'gsap'
//用pinia拼接图片地址
import { useStore } from "@/store/count";
import { ElMessage } from 'element-plus'
import BrowserSide from '@/components/articleBrowser/browserSide.vue';
import BrowserBottom from '@/components/articleBrowser/browserBottom.vue';
import Loading2 from "@/components/loading/loading2.vue";
const browserComment = defineAsyncComponent(() => import('@/components/articleBrowser/comments/browserComments.vue'))

const pinia = useStore()
const router = useRouter()
const MdCatalog = MdEditor.MdCatalog

const ifCover = ref(false)

//右侧的md-catalog目录的监听设置
const scrollElement = document.documentElement

//准备route发送带id的请求获取对应的文章
const route = useRoute()
const articleId = route.query.id

//请求相应的id的文章
const { data: res } = await useAxios.get('/getidarticle', {
    params: {
        id: articleId
    }
})
const browseArticle = res.data  //拿到了相应的文章

const commentsList = {
    id: articleId,
    comments: browseArticle.comments
}  //拿到评论列表
provide('comments', commentsList)  //依赖注入给孙组件评论列表

//获取作者的信息
const { data: author } = await useAxios.get('/userinfo', {
    params: {
        account: browseArticle.author
    }
})
const authorInfo = author.data


//这里是文章顶部的编辑按钮，点击展开下面的抽屉
let showEditorBox = false
const expandEdiorBox = () => {
    showEditorBox = !showEditorBox
    if (showEditorBox === true) {
        gsap.to('.ediorBox', {
            duration: .3,
            height: 80
        })
    } else {
        gsap.to('.ediorBox', {
            duration: .3,
            height: 0
        })
    }
}

//编辑文章
const openEditor = async () => {
    const session = localStorage.getItem('userAccount')
    if (session) {
        const sessionInfo = JSON.parse(window.atob(session))
        if (sessionInfo.root === true) {  //拥有管理员权限，可以直接编辑文章
            router.push({
                path: '/articleeditor',
                query: {
                    id: articleId,
                }
            })
        } else {   //没有权限，判断是否是自己写的文章
            const { data: res } = await useAxios.get('/getidarticle', {
                params: {
                    id: articleId
                }
            })

            if (res.data.author === sessionInfo.account) {
                router.push({
                    path: '/articleeditor',
                    query: {
                        id: articleId,
                    }
                })
            } else {
                ElMessage.error('只能编辑自己写的文章')
            }

        }
    } else {
        alert('只有管理员权限可以操作')
        router.push('/login')
    }


}

const containerGsap = () => {
    gsap.from('.contaier', {
        y: 50,
        duration: 0.3
    })
}

const gsapEnterCover = (el: any, done: any) => {
    gsap.from(el, {
        y: -50,
        duration: 0.3
    })
    done()
}

onMounted(() => {
    containerGsap()
    if (browseArticle.cover) {
        ifCover.value = true
    } else {
        const coverImg = document.querySelector('.contaier') as HTMLElement
        coverImg.style.marginTop = '5rem'
    }
})


//删除文章的弹出框
const dialogFormVisible = ref(false)
const randomNum = ref<string>()
const code = ref<string>()
const openDel = async () => {
    const session = localStorage.getItem('userAccount')
    if (session) {
        const sessionInfo = JSON.parse(window.atob(session))
        if (sessionInfo.root === true) {   //有权限，直接删除
            dialogFormVisible.value = true
            randomNum.value = Math.floor(Math.random() * 10000).toString()
        } else {   //没权限，判断是不是自己的文章
            const { data: res } = await useAxios.get('/getidarticle', {
                params: {
                    id: articleId
                }
            })

            if (res.data.author === sessionInfo.account) {
                dialogFormVisible.value = true
                randomNum.value = Math.floor(Math.random() * 10000).toString()
            } else {
                ElMessage.error('只能删除自己写的文章')
            }
        }
    } else {
        alert('只有管理员权限可以操作')
        router.push('/login')
    }
}


const trueDel = async () => {
    if (code.value === randomNum.value) {
        code.value = ''
        dialogFormVisible.value = false
        const { data: res } = await useAxios.get('/delarticle', {
            params: {
                id: articleId
            }
        })
        if (res.status === 0) {
            alert('删除成功')
            router.replace('/article')
        }
    } else {
        alert('验证码错误')
        code.value = ''
    }
}

const goToPersonalCenter = () => {
    const token = localStorage.getItem('userAccount')
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        if (tokenInfo.account === authorInfo.account) {   //如果点击的是自己的留言的头像，就跳转到自己的个人中心
            router.push('/personalcenter')
        } else {    //不是就正常跳转到别人的个人中心
            router.push({
                path: '/otherspersonalcenter',
                query: {
                    account: authorInfo.account
                }
            })
        }
    } else {    //未登录，直接跳转到别人的个人中心
        router.push({
            path: '/otherspersonalcenter',
            query: {
                account: authorInfo.account
            }
        })
    }
}


//置顶文章按钮点击事件
const topArticle = async () => {
    const token = localStorage.getItem('userAccount')
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        if (tokenInfo.root === true) {  //有权限
            let { data: res } = await useAxios.get("/toparticle", {
                params: {
                    id: articleId
                }
            })
            if (res.status === 0) {
                ElMessage({
                    message: '置顶成功',
                    type: 'success',
                })
            } else {
                ElMessage.error('网络错误！')
            }
        } else {
            ElMessage.error('没有操作权限')
        }
    } else {
        ElMessage.error('没有登录')
    }
}

</script>

<style scoped lang="less">
.coverImg {
    width: 80%;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    height: 40rem;
    position: relative;
    border-radius: 1rem;
    overflow: hidden;

    img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .mask {
        width: 100%;
        height: 50%;
        background-image: linear-gradient(rgba(255, 255, 255, 0), rgb(43, 43, 43));
        position: absolute;
        bottom: 0;
        left: 0;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: rgb(245, 245, 245);

        .title {
            font-size: 3.5rem;
            font-weight: 600;
        }

        .author {
            display: flex;
            align-items: center;
            font-size: 1.5rem;
            padding: 1rem 0;

            &:hover {
                cursor: pointer;
            }


            .head {
                width: 3.5rem;
                height: 3.5rem;
                border-radius: 50%;
                overflow: hidden;
                margin-right: 1rem;

                img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
            }
        }

        .time {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            align-items: center;

            :nth-child(1) {
                padding: 0;
                font-size: 2.6rem;
            }

            span {
                margin-left: .2rem;
                font-size: 1.6rem;
            }

            .lastUpdataTime {
                margin-left: 3rem;

                &>span {
                    font-size: 1.5rem;
                    font-weight: 700;
                    color: var(--special-font-color);
                }
            }
        }

    }
}

.articleMd {
    display: flex;
    width: 80%;
    margin: 0 auto;

    .contaier {
        width: 80%;

        .mdTextTop {
            background-color: #fff;
            padding: 1.4rem;
            border-radius: .5rem;
            box-shadow: .1rem .1rem .5rem var(--gray-sahdow);

            .editor {
                .option {
                    background-color: #f8f8f8;
                    color: #cecece;
                    border-radius: 10rem;
                    width: 100%;
                    height: 2rem;
                    cursor: pointer;
                    transition: all .5s;

                    &:hover {
                        background-color: #f0f0f0;
                        color: #646464;
                    }
                }

                .ediorBox {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    overflow: hidden;
                    margin-top: .5rem;
                    width: 100%;
                    height: 0;

                    button {
                        width: 9rem;
                        height: 3rem;
                        border-radius: .8rem;
                        margin: 0 1rem;
                        border: .2rem solid var(--border-line);
                        background-color: transparent;
                        cursor: pointer;
                        transition: all .3s;

                        &:hover {
                            box-shadow: 0 0 10px 0 var(--border-line);
                        }
                    }

                    :nth-child(3) {
                        border: .2rem solid red;

                        &:hover {
                            box-shadow: 0 0 10px 0 red;
                        }
                    }
                }

            }

        }


        .articleIndex {
            min-height: 40rem;

            .mdText {
                background-color: transparent;
            }
        }
    }

    .catalog {
        margin-top: 6rem;
        flex: 1;
        margin-left: 2rem;

        .cataSide {
            max-width: 20rem;
            position: sticky;
            top: 1rem;

            .titleTop {
                width: 100%;
                background-color: rgb(255, 255, 255);
                margin-bottom: .5rem;
                display: flex;
                align-items: center;
                box-sizing: border-box;
                font-size: 1.7rem;
                font-weight: 600;
                padding: 1rem;
                border-radius: .5rem;

            }

            .browserCatalog {
                width: 100%;
                background-color: rgb(255, 255, 255);
                border-radius: .5rem;
                box-shadow: 0 0 .3rem .1rem rgba(255, 255, 255, 0.4);
                box-sizing: border-box;
                padding: 2rem 1rem;
                font-size: 1.2rem;
            }
        }
    }


}


@media screen and (max-width: 1200px) {
    .coverImg {
        width: 90%;
    }

    .articleMd {
        margin: 0 auto;
        width: 90%;
    }
}


@media screen and (max-width: 800px) {
    .coverImg {
        width: 100%;
    }

    .articleMd {
        width: 100%;

        .contaier {
            width: 100%;
        }

        .catalog {
            display: none;
        }
    }
}
</style>