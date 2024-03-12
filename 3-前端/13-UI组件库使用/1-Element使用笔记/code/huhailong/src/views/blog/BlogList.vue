<template>
  <div
    class="blog-list"
    v-loading="loading"
    element-loading-text="内容加载中……"
  >
    <el-backtop>
      <el-button type="primary" round size="small">
        <i class="fa fa-arrow-up"></i>
        回到顶部
      </el-button>
    </el-backtop><div v-blog-title data-title="智慧博客"></div>

    <el-row>
      <el-col :lg="{ span: 6, offset: 1 }" style="display: flex; margin-top:1rem">
        <el-input
          @keypress.native.enter="getBlogList"
          placeholder="搜索内容"
          v-model="keyword"
          clearable
          @clear="getBlogList"
        ></el-input>
        <el-button
          @click="getBlogList"
          icon="el-icon-search"
          class="search-btn"
          type="primary"
          >搜索</el-button
        >
      </el-col>
    </el-row>
    <el-row>
      <el-col :lg="{ span: 22, offset: 1 }">
        <div class="content-list">
          <!-- <el-empty
            v-if="blogList.length == 0"
            description="空空如也，什么都没有"
          ></el-empty> -->
          <el-card
            v-for="item in blogList"
            :key="item.id"
            style="margin-bottom: 1rem"
          >
            <div class="content-title" @click="showDetail(item)">
              {{ item.title }}
            </div>
            <el-row>
              <el-col :lg="4">
                <el-image :src="item.coverImage">
                  <div slot="placeholder" class="image-slot">
                    加载中<span class="dot">...</span>
                  </div>
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </el-col>
              <el-col :lg="20">
                <div class="content-describe">
                  {{ item.subtitle }}
                </div>
              </el-col>
            </el-row>
            <div class="content-footer">
              <span class="content-footer-item"
                ><i class="fa fa-user"></i>作者: {{ item.source }}</span
              >
              <span class="content-footer-item"> {{ item.updateTime }}</span>
            </div>
          </el-card>
          <div style="margin:10px 0;">
                <el-pagination @current-change="goPage" background layout="prev, pager, next" :total="total"></el-pagination>
            </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as blogApi from "@/api/blog/api.js";
export default {
  name: "BlogList",
  data() {
    return {
      keyword: "",
      pageNum:1,
      total:0,
      pageSize:10,
      loading: false,
      blogList: [],
    };
  },
  methods: {
    goPage(num){
      this.pageNum = num;
      this.getBlogList();
    },
    getBlogList() {
      this.loading = true;
      blogApi.getBlog({pageNum: this.pageNum, pageSize: this.pageSize, keyword: this.keyword }).then((res) => {
        this.blogList = res.data.list;
        this.total = res.data.total;
        this.loading = false;
      }).catch((err)=>{
        console.log(err)
        this.$message({
          type: 'error',
          message: err.message,
          offset: 70
        })
        this.loading = false
      });
    },
    showDetail(data) {
      this.$router.push("/blogDetail/" + data.id + "/" + data.title);
    },
  },
  mounted() {
    this.getBlogList();
  },
};
</script>
<style lang="scss" scoped>
.blog-list {
  // background: linear-gradient(45deg, #34495E, #41B883);
  padding: 0 10px;
  min-height: calc(100vh - 8rem);
  .search-btn {
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
  }
  .content-list {
    margin-top: 1rem;
    text-align: left;
    .content-title {
      cursor: pointer;
      font-size: 24px;
      padding-bottom: 1rem;
    }
    .content-describe {
      line-height: 24px;
      padding-left: 0.5rem;
      font-size: 14px;
    }
    .content-footer {
      font-size: 13px;
      padding-top: 0.5rem;
      color: gray;
    }
    .content-footer-item {
      padding-right: 1rem;
    }
  }
}
</style>
<style>
.blog-list .el-input__inner {
  border-top-right-radius: 0px;
  border-bottom-right-radius: 0px;
  border-color: #34495E;
}
.el-backtop{
  background-color: transparent;
}
</style>