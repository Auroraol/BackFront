<template>
  <div class="discuss">
    <el-card header="评论">
      <template slot="header">
        <div class="header-box">
          <div class="card-header">
            评论<el-avatar
              style="margin-left: 10px"
              size="medium"
              :src="avatar"
            ></el-avatar>
          </div>
          <el-button @click="submitDiscuss" size="mini" type="primary" plain
            >提交</el-button
          >
        </div>
      </template>
      <el-input
        maxlength="255"
        v-model="discuss.content"
        type="textarea"
        placeholder="输入评论内容"
        rows="4"
      ></el-input>
    </el-card>
    <div class="showDiscuss">
      <div class="discuss-item" v-for="item in discussList" :key="item.id">
        <div class="discuss-item-parent">
          <div class="top-part">
            <el-avatar :src="item.avatar" :size="30"></el-avatar>
            <span class="username">{{ item.username }}: </span>
            <span>{{ item.content }}</span>
          </div>
          <div class="bottom-part" v-if="isLogin">
            <span class="time">{{ item.createTime }}</span>
            <span class="option" @click="showId = item.id; replyContent = ''">回复</span>
            <span v-if="item.username==username" class="option" @click="delDiscuss(item.id,'discuss')">删除</span>
          </div>
          <el-input @blur="handleBlur" size="small" v-if="item.id==showId" v-model="replyContent" style="width:90%;margin-bottom:10px;" placeholder="输入回复内容">
            <el-button type="primary" slot="append" @click="submitReply(item,'discuss')">回复</el-button>
          </el-input>
        </div>

        <div
          class="discuss-item-child"
          v-for="item2 in item.replyDiscussList"
          :key="item2.id"
        >
          <div class="top-part">
            <el-avatar icon="el-icon-user-solid" :src="item2.fromAvatar" :size="30"></el-avatar>
            <span class="username"
              >{{ item2.fromUsername }} 回复 {{ item2.toUsername }}:
            </span>
            <span>{{ item2.content }}</span>
          </div>
          <div class="bottom-part" v-if="isLogin">
            <span class="time">{{ item2.createTime }}</span
            ><span class="option" @click="showId = item2.id; replyContent = ''">回复</span>
            <span class="option" v-if="item2.fromUsername==username" @click="delDiscuss(item2.id,'reply')">删除</span>
          </div>
          <el-input v-if="item2.id==showId" size="small" @blur="handleBlur" v-model="replyContent" style="width:90%; margin-bottom:10px;" placeholder="输入回复内容">
            <el-button type="primary" slot="append" @click="submitReply(item2,'reply')">回复</el-button>
          </el-input>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import * as blogApi from '@/api/blog/api.js'
export default {
  name: "Discuss",
  props:["blogId"],
  data() {
    return {
      showId: -1,
      avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      username: '',
      isLogin: false,
      discuss: {},
      discussList: [],
      replyContent: ''
    };
  },
  methods: {
    handleBlur(type){
      if(type=='discuss'){
        if(this.discuss.content==''||!this.discuss.content){
          this.showId = -1;
        }
      }else{
        if(this.replyContent==''){
          this.showId = -1;
        }
      }
      
    },
    getUserInfo(){
      if(this.isLogin){
        let info = localStorage.getItem("hhl-info");
        let userData = JSON.parse(info);
        this.avatar = userData.userInfo.avatar;
        this.username = userData.user.username
      }
    },
    getDiscuss(){
      blogApi.getDiscuss({blogId:this.blogId}).then(res=>{
        this.discussList = res.data
      })
    },
    addDiscuss(){
      this.discuss.username = this.username;
      this.discuss.blogId = this.blogId;
      this.discuss.avatar = this.avatar;
      blogApi.addDiscuss(this.discuss).then(res=>{
        this.$alert(res.message, '提示', {confirmButtonText: '确定'});
        if(res.status){
          this.discuss.content = ''
          this.getDiscuss();
        }
      })
    },
    submitDiscuss() {
      if (!this.isLogin) {
        this.$confirm("当前未登录，评论功能需要用户先登录, 是否登录?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.common.prePath = this.$router.history.current.path;
          this.$router.push("/login");
        });
      }
      if (
        this.discuss.content != undefined &&
        this.discuss.content.trim() != "" &&
        this.isLogin
      ) {
        this.addDiscuss();
      }
    },
    submitReply(data,type){
      console.log(type,data);
      blogApi.addReply({
        discussId: type=='discuss'?data.id:data.discussId,
        fromUsername: this.username,
        fromAvatar: this.avatar,
        toUsername: type=='discuss'?data.username:data.fromUsername,
        toAvatar: type=='discuss'?data.avatar:data.fromAvatar,
        content: this.replyContent,

      }).then(res=>{
        console.log(res)
        this.getDiscuss();
        this.showId = -1;
      })
    },
    delDiscuss(id,type){
      if(type=='discuss'){
        blogApi.delDiscuss([id]).then(res=>{
          this.makeMessage(res);
          this.getDiscuss();
        })
      }else{
        blogApi.delReply([id]).then(res=>{
          this.makeMessage(res);
          this.getDiscuss();
        })
      }
    },
    makeMessage(res){
      this.$message({
        type: res.status?'success':'error',
        message: res.message,
        offset: 100
      })
    }
  },
  mounted() {
    this.isLogin = localStorage.getItem("isLogin") == "true" ? true : false;
    this.getUserInfo();
    this.getDiscuss();
  },
};
</script>
<style lang="scss" scoped>
.header-box {
  display: flex;
  justify-content: space-between;
}
.card-header {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
}
.showDiscuss {
  margin-top: 1rem;
  color: white;
  .discuss-item-parent{
    text-align: left;
  }
  .discuss-item {
    margin: 20px 10px;
    .top-part,
    .bottom-part {
      margin-bottom: 0.5rem;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
    }
    .bottom-part {
      margin-left: 1.5rem;
    }
    .discuss-item-child {
      text-align: left;
      margin-left: 1.8rem;
    }
    .username {
      padding-left: 10px;
      font-weight: bold;
      padding-right: 10px;
    }
    .time {
      padding-left: 1rem;
      color: rgb(187, 187, 187);
    }
    .option {
      cursor: pointer;
      margin-left: 1rem;
      color: white;
      font-size: 13px;
    }
  }
}
</style>
<style>
.discuss .el-card__header {
  padding: 10px 20px;
}
.discuss .el-card__body {
  padding: 10px;
}
</style>