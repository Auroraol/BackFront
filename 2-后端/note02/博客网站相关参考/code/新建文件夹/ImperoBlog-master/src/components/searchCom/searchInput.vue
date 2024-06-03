<template>
    <div class="searchIpt-container">
        <slot name="beforeIcon"></slot>
        <input type="text" class="search" placeholder="搜索" @keyup="toSearch" v-model="searchInfo">
        <button @click="submitSearch">
            <slot name="submitBtn"></slot>
        </button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useStore } from '@/store/count'
const router = useRouter()
const pinia = useStore()

type Props = {
    fontSize: string,
    clearIpt?: boolean,  //默认是false，不清空
    searchInfoProps?: string
}
const props = defineProps<Props>()
const fontSize = props.fontSize + 'rem'

//nav栏的搜索框
const searchInfo = ref<string | undefined>(props.searchInfoProps)
const clearInput = () => {
    searchInfo.value = ''
}
const submitSearch = () => {
    if (searchInfo.value === '') {
        ElMessage({
            message: '搜索内容不能为空',
            type: 'warning',
        })
        return
    }
    router.push({ path: '/search', query: { info: searchInfo.value } })
    if (props.clearIpt === true) clearInput()
    pinia.refreshSearchRes++
}
const toSearch = (s: KeyboardEvent) => {
    if (s.key === 'Enter') {
        submitSearch()
    }
}
</script>

<style scoped lang="less">
.searchIpt-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffffff;
    border-radius: .5rem;
    overflow: hidden;

    .search {
        box-sizing: border-box;
        padding: .5rem;
        outline: none;
        border: none;
        font-size: v-bind(fontSize);
    }

    button {
        background-color: transparent;
        cursor: pointer;
    }
}
</style>