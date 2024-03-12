<template>
  <div class="edit-blog" v-loading="loading">
    <div v-blog-title data-title="发布文章"></div>
    <mavon-editor
      ref="mdedit"
      class="editBox"
      :ishljs="true"
      :codeStyle="code_style"
      :externalLink="externalLink"
      @imgAdd="imgAdd"
      v-model="blogContent.content"
    ></mavon-editor>
    <div class="footer">
      <el-button @click="addPre" size="small" style="margin-right:10px;" type="primary"
        ><i class="fa fa-send-o"></i>{{this.$route.params.blogId?'更新内容':'发布内容'}}</el-button
      >
    </div>
    <el-dialog :visible.sync="showAddPre">
      <template slot="title">
          <div style="color:#ffffff;"><i class="el-icon-setting"></i> 发布设置</div>
      </template>
      <el-form size="medium">
        <el-form-item>
          <template slot="label">
            <div>
             <i class="fa fa-font"></i>文章标题<span v-if="warningType == 'title'" class="warning"
                ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i
                >{{ warningMesg }}</span
              >
            </div>
          </template>
          <el-input
            type="text"
            maxlength="200"
            show-word-limit
            placeholder="输入文章标题"
            v-model="blogContent.title"
          />
        </el-form-item>
        <el-form-item>
          <template slot="label">
            <div>
              <i class="fa fa-align-left"></i>文章描述<span v-if="warningType == 'subtitle'" class="warning"
                ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i
                >{{ warningMesg }}</span
              >
            </div>
          </template>
          <el-input
            type="textarea"
            placeholder="内容摘要描述"
            maxlength="500"
            show-word-limit
            rows="3"
            v-model="blogContent.subtitle"
          />
        </el-form-item>
        <el-form-item label="文章封面">
          <template slot="label">
            <div>
              <i class="fa fa-picture-o"></i>文章封面<span v-if="warningType == 'coverImage'" class="warning"
                ><i class="fa fa-exclamation-triangle" aria-hidden="true"></i
                >{{ warningMesg }}</span
              >
            </div>
          </template>
          <el-input type="text" placeholder="输入图片地址" v-model="blogContent.coverImage">
            <el-upload slot="append" :action="url+'/blog/content/uploadImage'" 
            :headers="header" :on-success="handleSuccess" :show-file-list="false" 
            :on-progress="handleProgress"
            accept=".jpg,.jpeg,.png,.gif,.JPG,.JPEG,.PBG,.GIF">
              <el-button icon="el-icon-upload" :loading="uploadFlag">上传图片</el-button>
            </el-upload>
          </el-input>
          <el-progress v-if="uploadTotal>0" style="margin-top:5px;" :text-inside="true" :status="uploadStatus" :stroke-width="26" :percentage="uploadTotal"></el-progress>
        </el-form-item>
        <el-form-item>
          <el-button
            class="medium"
            size="small"
            type="info"
            icon="el-icon-close"
            @click="showAddPre=false;"
            >取消</el-button
          >
          <el-button
            v-if="!isSet"
            class="medium"
            size="small"
            type="primary"
            icon="el-icon-check"
            @click="addBlog('add')"
            >发布</el-button
          >
          <el-button
            v-if="isSet"
            class="medium"
            size="small"
            type="primary"
            icon="el-icon-check"
            @click="addBlog('set')"
            >更新</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import * as formValid from "@/util/formValid.js";
import * as blogApi from "@/api/blog/api.js";
export default {
  name: "EditBlog",
  data() {
    return {
      isSet: false,
      loading: false,
      uploadTotal: 0,
      uploadStatus: 'default',
      uploadFlag: false,
      warningType: "",
      warningMesg: "",
      blogContent: {
        coverImage:''
      },
      showAddPre: false,
      code_style: "dark",
      url: window._apiUrl.serverUrl,
      header:{
        Authorization: localStorage.getItem('hhl-token')
      },
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
    addPre(){
        if(this.blogContent.content==null||this.blogContent.content.trim()==''){
            this.$alert('为编辑任何内容', '提示', {confirmButtonText: '确定'});
            return;
        }
        this.showAddPre=true;
    },
    addBlog(type) {
      let result = formValid.checkBlog(this.blogContent);
      if (result.status) {
        if(type=='add'){
          blogApi.addBlog(this.blogContent).then(res=>{
              this.$message({
                  type: res.status?'success':'error',
                  customClass: 'messageClass',
                  message: res.message,
                  offset: 70
              })
              if(res.status){
                this.$router.push("/");
              }
              this.showAddPre = false;
          })
        }else{
          blogApi.setBlog(this.blogContent).then(res=>{
            this.$alert(res.message,'提示',{confirmButtonText:'确认'})
            this.$router.push("/");
            this.showAddPre = false;
          })
        }
        
      } else {
        this.warningType = result.type;
        this.warningMesg = result.message;
      }
    },
    handleSuccess(res){
      if(res.status){
        this.uploadStatus = 'success';
        this.blogContent.coverImage = res.data
      }else{
        this.uploadStatus = 'error';
      }
      this.uploadFlag = false;
      
    },
    handleProgress(event){
      this.uploadFlag = true;
      this.uploadTotal = event.percent; //更新上传进度
    },
    imgAdd(pos,file){
      let imgData = new FormData();
      imgData.append('file',file);
      blogApi.uploadImage(imgData).then(res=>{
        console.log(res.data.data);
        console.log(this.$refs.mdedit)
        this.$refs.mdedit.$img2Url(pos,res.data.data)
      });
    },
    getBlogById(id) {
      this.loading = true;
      blogApi.getBlogById(id).then((res) => {
        this.blogContent = res.data;
        this.loading = false;
      });
    },
  },
  mounted() {
    let blogId = this.$route.params.blogId;
    if(blogId){
      this.isSet = true;
      this.getBlogById(blogId);
    }
  },
};
</script>
<style lang="scss" scoped>
.edit-blog {
  width: 100%;
  height: calc(100vh - 8rem);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.editBox {
  width: calc(100% - 10px);
  height: 100%;
}
.footer {
  padding-top: 1rem;
  margin-right: 10px;
  width: 100%;
  text-align: right;
}
.messageClass{
  z-index: 1600;
}
</style>
<style>
.edit-blog .el-dialog{
    border-radius: 4px;
}
.edit-blog .el-dialog__header{
    background-color: #333333;
    padding: 10px;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    text-align: left;
}
.edit-blog .el-dialog__title{
    color: #ffffff;
}
.edit-blog .el-dialog__headerbtn{
    top: 10px;
}
</style>