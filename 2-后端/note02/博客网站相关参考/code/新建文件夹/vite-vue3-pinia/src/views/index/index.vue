<template>
  <div ref="container" class="home-container">
    <app-header />
    <div class="content-container">
      <div class="side-left">
        <ul class="list-header">
          <li
            v-for="(item, index) in mainTabs"
            :key="index"
            class="list-header-item"
            :class="{ 'main-active': mainActive === index }"
            @click="mainTabClick(index)"
          >{{ item }}</li>
        </ul>
        <article-list :list="artList" :loading="loading" />

        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="size"
          :current-page="current"
          :hide-on-single-page="true"
          :total="total"
          @current-change="currentChange"
        />
      </div>

      <div v-if="device === 'desktop'" class="content-right-side-container">
        <right-side-about />
        <right-side-tags />
        <right-side-comment />
        <right-side-recommend />
        <right-side-section />
      </div>
    </div>
  </div>
</template>

<script setup>
import AppHeader from '@/components/Header/index'
import ArticleList from '@/components/ArticleList'
import RightSideAbout from '@/components/RightSideAbout'
import RightSideTags from '@/components/RightSideTags'
import RightSideComment from '@/components/RightSideComment'
import RightSideRecommend from '@/components/RightSideRecommend'
import RightSideSection from '@/components/RightSideSection'
import { pagePublishedArticle } from '@/api/article.js'


const mainTabs = ref(['最新', '热门'])
    const current = ref(1)
    const size = ref(6)
    const total = ref(0)
    const mainActive = ref(0)
    const loading = ref(false)
    const artList = ref([])

    const orderBy = computed(() => {
      return mainActive.value === 0 ? 'publish_time' : 'view_count'
    })

    // const { device } = mapGetters(['device'])

    const mainTabClick = (index) => {
      current.value = 1
      total.value = 0
      mainActive.value = index
      getArtList()
    }

    const currentChange = (current) => {
      current.value = current
      getArtList()
    }

    const getArtList = () => {
      loading.value = true
      const params = {
        current: current.value,
        size: size.value,
        orderBy: orderBy.value
      }
      pagePublishedArticle(params).then(
        res => {
          loading.value = false
          total.value = res.data.total
          artList.value = res.data.records
          $refs.container.scrollTop = 0
        },
        error => {
          console.error(error)
          loading.value = false
        }
      )
    }

</script>

<style lang="scss" scoped>
@import '@/styles/variables';
.home-container {
	width: 100%;
  height: 100vh;
	overflow-x: hidden;
  overflow-y: -webkit-overlay;
  overflow-y: overlay;

  .content-container {
    width: 100%;
    max-width: $ContentContainerW;
    box-sizing: border-box;
    margin: 0 auto;
    position: relative;
    margin-top: 15px;
    display: flex;
    color: #909090;
    align-items: flex-start;

    @media screen and (max-width: 960px){
      margin-top: 0;
    }

    .side-left {
      flex: 1;
      background: #fff;
      border-radius: 2px;

      @media screen and (max-width: 960px){
        width: 100%;
      }

      .list-header {
        margin: 0;
        box-sizing: border-box;
        padding: 15px 12px;
        display: flex;
        align-items: center;
        font-size: 14px;
        border-bottom: 1px solid hsla(0,0%,59.2%,.1);

        .list-header-item {
          list-style: none;
          padding: 0 15px;
          border-right: 1px solid hsla(0,0%,59.2%,.2);
          cursor: pointer;

          &:last-child {
            border-right: none;
          }

          &:hover {
            color: #007fff;
          }
        }

        .main-active {
          color: #007fff;
        }
      }

      .el-pagination {
        padding: 30px;
        text-align: center;
        background: #eee;
      }
    }

    .content-right-side-container {
      width: $ContentRightSideW;
      box-sizing: border-box;
      margin-left: 20px;
      font-size: 14px;
      position: relative;

      @media screen and (max-width: 960px){
        display: none;
      }
    }
  }
}
</style>
