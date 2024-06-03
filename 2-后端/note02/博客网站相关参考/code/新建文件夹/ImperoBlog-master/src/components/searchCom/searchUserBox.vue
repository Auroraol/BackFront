<template>
    <div class="searchUserBox-container" @click="goToPersonalCenter">
        <!-- 左侧头像 -->
        <img class="headImage" :src="info.headImg" alt="">
        <!-- 右侧信息 -->
        <div class="userInfo">
            <!-- 昵称 -->
            <span>{{ info.nickName }}</span>
            <!-- 粉丝数、发布数 -->
            <p>{{ info.fans.length + "粉丝" + " · " + info.pushArticleNum + "发布" }}</p>
            <!-- 个人简介 -->
            <p>{{ info.introduce }}</p>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useStore } from '@/store/count';
import { goToPersonalCenterHook } from '@/hooks/goToPersonalCenter/goToPersonalCenter'
const pinia = useStore()

type Props = {
    info: any,
    height: string
}
const props = defineProps<Props>()
const propsHeight = props.height + "rem"
console.log(props.info);

const goToPersonalCenter = () => {
    goToPersonalCenterHook(props.info.account)
}

</script>

<style scoped lang="less">
.searchUserBox-container {
    box-sizing: border-box;
    display: flex;
    padding: 1rem;
    background-color: #fff;
    border-radius: .6rem;
    height: v-bind(propsHeight);
    transition: all .1s;

    &:hover {
        cursor: pointer;
        transform: translateY(-.1rem);

        .userInfo span {
            color: var(--special-font-color);
        }
    }

    .headImage {
        border-radius: 50%;
    }

    .userInfo {
        display: flex;
        flex-direction: column;
        margin-left: 1rem;
        max-width: 100%;
        overflow: hidden;

        span {
            font-size: 1.5rem;
            font-weight: 600;
            transition: all .2s;
        }

        p {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    }
}
</style>