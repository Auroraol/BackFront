<template>
    <div class="contaier">
        <div class="filter">
            <div class="title">
                <div class="left">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-16"></use>
                    </svg>
                    <div class="titleChild">
                        <span>文章列表</span>
                        <span>ARTICLE LIST</span>
                    </div>
                </div>
                <div class="right">
                    <input type="text" placeholder="搜索文章" class="searchIpt" v-model="searchInfo">
                    <button class="btn">
                        <p>搜索</p>
                    </button>
                </div>
            </div>
            <div class="funnel">
                <div>
                    <div>筛选 TAGS </div>
                    <div class="funnel-info-maring">
                        <span class="labelTag no-choose" @click="chooseTag(tag.content)" v-for="tag in tags"
                            :key="tag.id">{{
                                tag.content
                            }}</span>
                    </div>
                </div>
                <div>
                    <div>已选 CHOOSE </div>
                    <div class="funnel-info-maring">
                        <el-tag class="mx-1" closable :disable-transitions="false" @close="handleClose(tag)" type="success"
                            v-for="tag in filterTags.filterTag" :key="tag">{{
                                tag
                            }}
                        </el-tag>
                    </div>
                </div>
            </div>
        </div>
        <div class="list" :key="pages.nowPage">
            <!-- 使用了异步加载并且加上了加载动画的文章盒子 -->
            <Suspense v-for="item in showArticles.showArticle" :key="item.id">
                <template #default>
                    <ArticleBox :info="item" class="articleBox"></ArticleBox>
                </template>

                <!-- 加载完成前的载入动画 -->
                <template #fallback>
                    <div class="window">
                        <Loading class="winLoad"></Loading>
                    </div>
                </template>
            </Suspense>

        </div>
        <div class="page">
            <div class="allPage">共有{{ showArticles.showArticle.length }}条数据</div>
            <el-pagination background layout="prev, pager, next" :page-count="pages.page.length"
                :current-page="pages.nowPage" @update:current-page="updataPage" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, watchEffect, defineAsyncComponent, ref, onMounted } from "vue";
import useAxios from "@/hooks/axios/axios";
import { useRoute } from 'vue-router'
import Loading from "@/components/loading/loading2.vue";
import gsap from 'gsap'
// import { useStore } from "../../store/count";
// const pinia = useStore()
const ArticleBox = defineAsyncComponent(() => import('@/components/utils/articleBox.vue'))

const route = useRoute()
//文章列表
const { data: res } = await useAxios.get('/getarticle')
const articles = res.data
const showArticle = articles.reverse()

onMounted(() => {
    gsap.from('.title', {
        duration: 0.3,
        x: -100,
        opacity: 0.2
    })
    gsap.from('.funnel', {
        duration: 0.3,
        x: 100,
        opacity: 0.2
    })
    gsap.from('.list', {
        duration: 0.3,
        y: 100,
        opacity: 0.2
    })
})

//应该有的页数
const countForPage = 9 //每一页展示多少个
type Page = {
    page: Array<number>
    pageNums: number
    nowPage: number
}
let pages = reactive<Page>({
    page: [],
    pageNums: articles.length,
    nowPage: 1
})
// 当前展示页
//不能使用let来定义reactive，这样在重新赋值的时候内存地址会变，无法实现响应式
const showArticles = reactive({
    showArticle: showArticle.slice(countForPage * (pages.nowPage - 1), countForPage * (pages.nowPage - 1) + countForPage)
})
watchEffect(() => {
    //先清空一下数组，再计算页数
    pages.page = []
    if (pages.pageNums % countForPage === 0) {
        for (let i = 0; i < Math.floor(pages.pageNums / countForPage); i++) {
            pages.page.push(i + 1)
        }
    } else {
        for (let i = 0; i < Math.floor(pages.pageNums / countForPage) + 1; i++) {
            pages.page.push(i + 1)
        }
    }

})
const updataPage = (val: number) => {
    pages.nowPage = val
}

type Article = {
    id: number
    title: string
    author: string
    tag: Array<string>
    cover: string
    time: string
    oneSentence: string
    content: string
}
//所有的tag
//请求所有标签
const { data: allTag } = await useAxios.get('/gettags')
const tags = allTag.data
//选中的筛选条件tags
type Tag = {
    filterTag: Array<string>
}
//选中标签的数组
const filterTags = reactive<Tag>({
    filterTag: []
})
//如果是从首页选择标签快速跳转进来，接收一个选择的筛选标签添加到筛选列表中
const quickTag = route.query.quickTag?.toString()
//如果传参了，就加进去，没穿就不用管
if (quickTag) {
    filterTags.filterTag.push(quickTag)
}
const reFreshPage = () => {  //刷新页码
    pages.pageNums = showArticles.showArticle.length
    showArticles.showArticle = showArticles.showArticle.slice(countForPage * (pages.nowPage - 1), countForPage * (pages.nowPage - 1) + countForPage)
}
//选中筛选tag
const chooseTag = (tagName: string) => {
    //如果已经选中了则不要重复选中
    if (filterTags.filterTag.indexOf(tagName) !== -1) {
        return
    } else {
        filterTags.filterTag.push(tagName)
    }
}
//选中筛选标签有变化就要重新过滤文章，展示符合条件的
const searchInfo = ref('')  //搜索框内容
watchEffect(() => {
    showArticles.showArticle = showArticle.filter((item: Article) => {  //筛选标签
        return filterTags.filterTag.every(val => item.tag.includes(val)) && item.title.includes(searchInfo.value)
    })
    reFreshPage()
})
//筛选标签被删掉
const handleClose = (tagName: string) => {
    filterTags.filterTag.splice(filterTags.filterTag.indexOf(tagName), 1)
}
</script>

<style scoped lang="less">
.funnel-info-maring {
    margin-left: 2rem;

    :deep(.mx-1) {
        padding: .6rem;
        margin: 0 .3rem;
    }
}

.no-choose {
    color: black;
    background-color: #fff;
    border: .1rem solid black;
    cursor: pointer;
    transition: all .1s;

    &:hover {
        box-shadow: .1rem .1rem .2rem var(--box-shadow);
        transform: translateY(-0.1rem);
    }
}

.contaier {
    width: 70%;
    // box-sizing: border-box;
    margin: 0 auto;
    // border: .1rem solid black;
    // box-shadow: .1rem .1rem .5rem var(--gray-sahdow);
    // border-radius: .5rem;
    // padding: 1.5rem;
    // padding-top: 3rem;
    // background-color: var(--backgeound-color);

    .filter {
        .title {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 4rem;
            padding: 2rem;
            background-color: #fff;
            border-radius: 1rem;

            .left {
                display: flex;

                .titleChild {
                    margin-left: 1rem;
                    font-size: 1.5rem;
                    display: flex;
                    flex-direction: column;
                    align-items: flex-start;
                }
            }

            .right {
                display: flex;

                .searchIpt {
                    box-sizing: border-box;
                    padding: .5rem;
                    border: .2rem solid #919191;
                    outline: none;
                    border-radius: .6rem;
                    font-size: 1.2rem;
                }

                .btn {
                    box-sizing: border-box;
                    padding: 0 1rem;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    font-size: 1.2rem;
                    color: #fff;
                    border-radius: .6rem;
                    margin-left: .5rem;
                    background-color: var(--special-font-color);
                    cursor: pointer;
                }
            }
        }

        .funnel {
            box-sizing: border-box;
            padding: 2rem;
            font-size: 1.7rem;
            margin-top: 1rem;
            background-color: #fff;
            border-radius: 1rem;

            >div {
                padding: .7rem 0;
                display: flex;
                align-items: center;
            }

        }

    }

    .list {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        column-gap: 1rem;
        justify-content: center;
        justify-items: center;

        .articleBox {
            width: 100%;
            margin: 1rem;
            height: 38rem;
        }
    }

    .page {
        .allPage {
            font-size: 1.4rem;
            margin: .5rem;
        }
    }
}

@media screen and (max-width:1250px) {
    .contaier {
        .list {
            grid-template-columns: repeat(2, 1fr);
        }
    }

}

@media screen and (max-width:800px) {
    .contaier {
        width: 100%;

        .list {
            grid-template-columns: repeat(1, 1fr);
        }
    }

}
</style>