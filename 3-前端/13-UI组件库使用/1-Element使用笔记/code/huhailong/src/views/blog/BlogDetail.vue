<template>
  <div
    class="blog-detail"
    v-loading="loading"
    element-loading-text="内容加载中……"
  >
    <div v-blog-title :data-title="title"></div>
    <div class="title">{{ title }}</div>
    <div class="describe">
      <span class="describe-item">作者：{{ blogContent.source }}</span><br/>
      <span class="describe-item">发布时间：{{ blogContent.updateTime }}</span>
      <span class="describe-item" style="margin-left:10px;">阅读量：{{ blogContent.viewCount }}</span>
    </div>
    <mavon-editor
      class="editBox"
      :subfield="false"
      defaultOpen="preview"
      :toolbarsFlag="false"
      :navigation="false"
      :ishljs="true"
      :codeStyle="code_style"
      :externalLink="externalLink"
      v-model="blogContent.content"
    ></mavon-editor>
    <discuss :blogId="id"></discuss>
  </div>
</template>
<script>
import * as blogApi from "@/api/blog/api.js";
import Discuss from "../../components/Discuss.vue";
export default {
  components: { Discuss },
  name: "BlogDetail",
  props: ["id", "title"],
  data() {
    return {
      loading: false,
      blogContent: {},
      code_style: "dark",
      externalLink: {
        markdown_css: function () {
          // 这是你的markdown css文件路径
          return "/markdown/markdown/github-markdown.min.css";
        },
        hljs_js: function () {
          // 这是你的hljs文件路径
          return "/markdown/highlightjs/highlight.min.js";
        },
        hljs_css: function (css) {
          // 这是你的代码高亮配色文件路径
          return "/markdown/highlightjs/styles/" + css + ".min.css";
        },
        hljs_lang: function (lang) {
          // 这是你的代码高亮语言解析路径
          return "/markdown/highlightjs/languages/" + lang + ".min.js";
        },
        katex_css: function () {
          // 这是你的katex配色方案路径路径
          return "/markdown/katex/katex.min.css";
        },
        katex_js: function () {
          // 这是你的katex.js路径
          return "/markdown/katex/katex.min.js";
        },
      },
    };
  },
  methods: {
    getBlogById() {
      this.loading = true;
      blogApi.getBlogById(this.id).then((res) => {
        this.blogContent = res.data;
        this.loading = false;
      });
    },
  },
  mounted() {
    this.getBlogById();
  },
};
</script>
<style lang="scss" scoped>
.blog-detail {
  .title {
    padding-top: 1rem;
    font-size: 24px;
    font-weight: bold;
  }
  .describe {
    width: 100%;
    font-size: 14px;
    text-align: center;
    padding-top: 1rem;
    padding-bottom: 10px;
    .describe-item {
      // margin-right: 1rem;
    }
  }
}
</style>