<template>
  <!-- 用户个人中心 -->
  <div class="user-center">
    <el-row style="width:100%;">
      <el-col :lg="{span:12,offset:6}" :md="{span:12,offset:6}" :xs="{span:20,offset:2}">
        <el-card>
          <div class="header">
            <input ref="uploadAvatar" type="file" hidden/>
            <el-tooltip content="双击修改头像">
              <el-avatar @dblclick.native="setAvatar" :size="100" :src="userData.userInfo.avatar"></el-avatar>
            </el-tooltip>
            <p v-if="canEditRemark"><input style="width: 300px;" :autofocus="canEditRemark" type="text" v-model="userData.userInfo.remark" @blur="save"/></p>
            <p v-if="!canEditRemark">{{ userData.userInfo.remark }}<i @click="editFun('remark')" class="fa fa-edit edit"></i></p>
          </div>
          <hr/>
          <div class="userInfo">
              <div class="item"><span style="font-weight:bold;">用户角色：</span><span v-for="(item,index) in userData.user.rolesList" :key="index">{{item.roleName}}{{userData.user.rolesList.length==index+1?'':'、'}}</span></div>
              <div class="item"><span style="font-weight:bold;">账号名称：</span><span>{{userData.user.username}}</span></div>
              <div class="item"><span style="font-weight:bold;">用户昵称：</span>
              <span><input :autofocus="canEditNickName" v-if="canEditNickName" type="text" v-model="userData.userInfo.nickName" @blur="save"/></span>
              <span v-if="!canEditNickName">{{userData.userInfo.nickName}}<i @click="editFun('nickName')" style="padding-left:10px;" class="fa fa-edit edit"></i></span></div>
              <div class="item"><span style="font-weight:bold;">用户邮箱：</span><span>{{userData.user.userEmail}}</span></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as userApi from "@/api/sso/api.js";
export default {
  name: "UserCenter",
  data() {
    return {
      userData: {
          user: {
              rolesList:[]
          },
          userInfo: {
              avatar: ''
          }
      },
      canEditRemark: false,
      canEditNickName: false,
    };
  },
  methods: {
    getUserInfo() {
      userApi.getUserInfo().then((res) => {
        console.log(res)
        this.userData = res.data;
        this.userAvatar = res.data.userInfo.avatar
      });
    },
    editFun(type){
      if(type=='remark'){
        this.canEditRemark = true;
      }
      if(type=='nickName'){
        this.canEditNickName = true;
      }
    },
    save(){
      this.canEditRemark = false;
      this.canEditNickName = false;
    },
    setAvatar(){
      this.$refs.uploadAvatar.dispatchEvent(new MouseEvent('click'))
    }
  },
  mounted() {
    this.getUserInfo();
  },
};
</script>
<style lang="scss" scoped>
.user-center{
  height: calc(100vh - 8rem);
  display: flex;
  align-items: center;
}
.edit{
  cursor: pointer;
}
.userInfo{
    width: 100%;
    text-align: left;
    .item{
      padding: 5px 0;
    }
}
</style>