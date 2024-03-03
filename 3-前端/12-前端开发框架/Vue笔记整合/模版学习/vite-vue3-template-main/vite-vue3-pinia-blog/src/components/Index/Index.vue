<template>
    <!--中间 -->
    <div class="card">
        <el-row :gutter="20">
            <el-col :span="6">
                <div class="card3 bg-purple" > </div>
            </el-col>
            <el-col :span="12">
                <div class="card3 bg-purple-light"></div>
            </el-col>
            <el-col :span="6">
                <div class="card3 bg-purple"></div>
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
    import { defineAsyncComponent } from "vue";
    const IndexArticle = defineAsyncComponent(() => import('/@/components/Index/IndexArticle.vue'))

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

    .card {
        margin-top: 20px;
        margin-left: 15px;
        margin-right: 15px;
    }

    .el-row {
        margin-bottom: 20px;

        &:last-child {
            margin-bottom: 0;
        }
    }

    .card3 {
        border-radius: 0.5rem;
        min-height: 650px;
    }

    .bg-purple-dark {
        background: #99a9bf;
    }

    .bg-purple {
        background: #d3dce6;
    }

    .bg-purple-light {
        background: #e5e9f2;
    }
</style>