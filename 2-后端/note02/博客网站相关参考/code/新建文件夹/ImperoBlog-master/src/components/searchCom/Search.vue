<template>
    <div class="search-container">
        <!-- 上半部分搜索栏 -->
        <div class="searchCom">
            <SearchInput fontSize="1.5" style="padding: .5rem 0;" :searchInfoProps="(route.query.info as string)">
                <template #beforeIcon>
                    <svg class="icon" aria-hidden="true" style="font-size: 1.3rem; margin: 0 1rem; margin-right: 0;">
                        <use xlink:href="#icon-a-tubiaosousuo"></use>
                    </svg>
                </template>
                <template #submitBtn>
                    <div class="searchBtn">
                        <p>搜索</p>
                    </div>
                </template>
            </SearchInput>
        </div>
        <!-- 下面的搜索结果 -->
        <div class="searchRes" :key="pinia.refreshSearchRes">
            <el-tabs v-model="activeName" class="demo-tabs">
                <el-tab-pane label="文章" name="first" class="articleList">
                    <Suspense v-if="searchRes.article.length === 0 ? false : true" v-for="item in searchRes.article"
                        :key="item.id">
                        <template #default>
                            <ArticleBox :info="item" style="height: 35rem;"></ArticleBox>
                        </template>
                        <template #fallback>
                            <div class="window">
                                <Loading class="winLoad"></Loading>
                            </div>
                        </template>
                    </Suspense>
                    <p v-else>没有合适的搜索结果🧐</p>
                </el-tab-pane>
                <el-tab-pane label="用户" name="second" class="userList">
                    <Suspense v-if="searchRes.user.length === 0 ? false : true" v-for="item in searchRes.user"
                        :key="item.id">
                        <template #default>
                            <searchUserBox :info="item" height="8"></searchUserBox>
                        </template>
                        <template #fallback>
                            <div class="window">
                                <Loading class="winLoad"></Loading>
                            </div>
                        </template>
                    </Suspense>
                    <p v-else>没有合适的搜索结果🧐</p>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref, reactive, watchEffect, defineAsyncComponent } from 'vue'
import { useStore } from '@/store/count';
import useAxios from '@/hooks/axios/axios';
import SearchInput from '@/components/searchCom/searchInput.vue'
import Loading from "@/components/loading/loading2.vue";
import searchUserBox from '@/components/searchCom/searchUserBox.vue';
// const searchUserBox = defineAsyncComponent(() => import('@/components/searchCom/searchUserBox.vue'))
import ArticleBox from '@/components/utils/articleBox.vue';
// const ArticleBox = defineAsyncComponent(() => import('@/components/articleBox/articleBox.vue'))

const activeName = ref('first')
const route = useRoute()
const pinia = useStore()

const searchInfo = ref(route.query.info)  //搜索的内容

const searchRes = reactive({  //搜索的结果
    article: new Array,
    user: new Array
})

const reqArticle = async () => {  //请求文章
    const { data: searchArticleRes } = await useAxios.get('/searcharticle', {
        params: {
            title: searchInfo.value
        }
    })
    searchRes.article = searchArticleRes.data  //请求回来的文章搜索结果
}
const reqUser = async () => {  //请求用户
    const { data: searchUserRes } = await useAxios.get('/searchuser', {
        params: {
            account: searchInfo.value
        }
    })
    searchRes.user = searchUserRes.data  //请求回来的用户搜索结果
}

reqArticle()
reqUser()

watchEffect(() => {
    if (pinia.refreshSearchRes) {
        searchInfo.value = route.query.info
        reqArticle()
        reqUser()
    }
})

</script>

<style scoped lang="less">
.search-container {
    margin: 0 auto;
    width: 75%;
    min-height: 55rem;
    overflow: auto;
    box-sizing: border-box;
    padding: 1rem;

    .searchCom {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 2rem;

        .searchBtn {
            margin: 0 .5rem;
            padding: .5rem 1.5rem;
            background-color: var(--special-font-color);
            border-radius: .5rem;
            color: #fff;
        }

    }

    .searchRes {
        margin-top: 1rem;
        font-size: 1.2rem;

        .articleList {
            margin-top: 0;
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 1rem;
        }

        .userList {
            margin-top: 0;
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 1rem;
        }
    }
}
</style>