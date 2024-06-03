<template>
    <div>
        <Suspense>
            <template #default>
                <!-- 下面的其他内容 -->
                <IndexArticle></IndexArticle>
            </template>

            <!-- 加载完成前的载入动画 -->
            <template #fallback>
                <div class="window">
                    <Loading class="winLoad"></Loading>
                </div>
            </template>
        </Suspense>
    </div>
</template>

<script setup lang="ts">
import { defineAsyncComponent } from "vue";
import Loading from "@/components/loading/loading.vue";
const IndexArticle = defineAsyncComponent(() => import('@/components/index/IndexArticle.vue'))

</script>

<style scoped lang="less">
@fontColor: #fff;


.window {
    width: 100vw;
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>