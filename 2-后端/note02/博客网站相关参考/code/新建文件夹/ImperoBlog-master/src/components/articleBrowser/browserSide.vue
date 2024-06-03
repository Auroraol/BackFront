<template>
    <div class="authorInfo" ref="authorInfo">
        <div class="dianzan">
            <div class="praise" v-if="ifPraise" @click="praise">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-31dianzan"></use>
                </svg>
            </div>
            <div class="praise" v-else @click="praise">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-dianzan"></use>
                </svg>
            </div>
        </div>
        <div class="shoucang">
            <div class="praise collection" v-if="ifCollection" @click="collection">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-shoucang"></use>
                </svg>
            </div>
            <div class="praise collection" v-else @click="collection">
                <svg class="icon" aria-hidden="true">
                    <use xlink:href="#icon-shoucang1"></use>
                </svg>
            </div>
        </div>
        <div class="praise collection" v-if="goTop" @click="goTopBtn">
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-huojian"></use>
            </svg>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';
import { ElMessage } from 'element-plus'
import useAxios from '@/hooks/axios/axios';
import { useStore } from '@/store/count';

const authorInfo = ref<HTMLElement>()  //获取dom
const goTop = ref(false)  //回到顶部
const ifPraise = ref(true)  //是否点赞  true是没有
const ifCollection = ref(true)  //是否收藏 true是没有
const pinia = useStore()

type Props = {
    id: string
}
const props = defineProps<Props>()

const praiseBox = () => {
    if (window.scrollY > 600) {
        goTop.value = true
    } else {
        goTop.value = false
    }
}

onMounted(async () => {
    document.addEventListener('scroll', praiseBox)
    //调用接口看是否已经给文章点过赞了
    const token = localStorage.getItem('userAccount')
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        const { data: res } = await useAxios.get('/ifparise', {
            params: {
                account: tokenInfo.account,
                articleId: props.id
            }
        })

        if (res.status === 1) {
            //已经点过赞了
            ifPraise.value = false
        } else {
            ifPraise.value = true
        }
    }

    //调用接口看是否已经给文章收藏了
    if (token) {
        const tokenInfo = JSON.parse(window.atob(token))
        const { data: res } = await useAxios.get('/ifcollection', {
            params: {
                account: tokenInfo.account,
                articleId: props.id
            }
        })

        if (res.status === 1) {
            //已经收藏了
            ifCollection.value = false
        } else {
            ifCollection.value = true
        }
    }

})

onUnmounted(() => {
    document.removeEventListener('scroll', praiseBox)
})

const goTopBtn = () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    })

}

const praise = async () => {
    //点赞
    //调用点赞接口
    //获取当前用户信息
    const token = localStorage.getItem('userAccount')
    if (!token) {
        ElMessage.error('没有登录，不可以点赞')
    } else {
        const tokenInfo = JSON.parse(window.atob(token))
        const { data: res } = await useAxios.get('/parisearticle', {


            params: {
                account: tokenInfo.account,
                articleId: props.id
            }
        })

        
        if (res.status === 0) {
            //点赞成功
            ElMessage({
                message: '点赞成功',
                type: 'success',
            })
            ifPraise.value = !ifPraise.value
            pinia.pariseOrCollection += 1
        } else {
            //取消点赞成功
            ElMessage({
                message: '取消点赞',
                type: 'success',
            })
            ifPraise.value = !ifPraise.value
            pinia.pariseOrCollection += 1
        }
    }


}

const collection = async () => {
    //收藏
    //调用收藏接口
    //获取当前用户信息
    const token = localStorage.getItem('userAccount')
    if (!token) {
        ElMessage.error('没有登录，不可以收藏文章')
    } else {
        const tokenInfo = JSON.parse(window.atob(token))
        const { data: res } = await useAxios.get('/collectionarticle', {
            params: {
                account: tokenInfo.account,
                articleId: props.id
            }
        })

        if (res.status === 0) {
            //收藏成功
            ElMessage({
                message: '收藏成功',
                type: 'success',
            })
            ifCollection.value = !ifCollection.value
            pinia.pariseOrCollection += 1
        } else {
            //取消收藏
            ElMessage({
                message: '取消收藏',
                type: 'success',
            })
            ifCollection.value = !ifCollection.value
            pinia.pariseOrCollection += 1
        }
    }

}

</script>

<style scoped lang="less">
.authorInfo {
    margin-top: 3rem;
    width: 5rem;
    height: 20rem;
    border: .2rem solid var();
    border-radius: .5rem;
    display: flex;
    flex-direction: column;
    align-items: center;

    .praise {
        background-color: rgb(255, 255, 255);
        width: 4rem;
        height: 4rem;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 2rem;

        &:hover {
            cursor: pointer;
            // background-color: var(--special-font-color);
            // color: #fff;
            box-shadow: 0 0 .2rem .2rem var(--special-font-color);
        }
    }

    .collection {
        margin-top: 1.5rem;
    }
}
</style>