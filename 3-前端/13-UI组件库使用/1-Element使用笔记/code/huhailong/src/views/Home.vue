<template>
  <div class="home">
    <div class="top">
      <div class="top-left">
        <div class="logo" @click="clickMe">BlogSystem</div>
        <el-menu
          mode="horizontal"
          router
          background-color="#34495E"
          text-color="#fff"
          active-text-color="#40B180"
          default-active="/blogList"
          close="top-menu"
        >
          <el-menu-item index="/blog">博客</el-menu-item>
          <el-submenu v-if="isMobile" index="">
            <template slot="title">更多</template>
            <!-- <el-menu-item index="/project">项目</el-menu-item> -->
            <!-- <el-menu-item index="/resource">资源</el-menu-item> -->
            <el-menu-item v-if="isAdmin" index="/blogManager">博客管理</el-menu-item>
            <el-menu-item v-if="isAdmin" index="/editBlog">发布博客</el-menu-item
            >
            <el-menu-item index="/userCenter" style="color: white;" v-if="isLogin">个人中心</el-menu-item>
            <el-menu-item index="/about">关于</el-menu-item>
          </el-submenu>
          <!-- <el-menu-item v-if="!isMobile" index="/project">项目</el-menu-item> -->
          <!-- <el-menu-item v-if="!isMobile" index="/resource">资源</el-menu-item> -->
          <el-menu-item v-if="isAdmin&&!isMobile" index="/editBlog">发布博客</el-menu-item>
          <el-menu-item v-if="isAdmin&&!isMobile" index="/blogManager">博客管理</el-menu-item>
          <el-menu-item v-if="!isMobile" index="/about">关于</el-menu-item>
        </el-menu>
      </div>
      <div class="top-right">
        <el-link style="margin-right: 10px; color: white;" @click="goLogin" v-if="!isLogin"
          ><i class="fa fa-sign-in"></i>登录</el-link
        >
        <el-link style="color: white;" @click="goUserCenter" v-if="isLogin&&!isMobile"><i class="fa fa-user"></i>个人中心</el-link>
        <el-link style="margin:0 10px; color: white;" @click="logout" v-if="isLogin"
          ><i class="fa fa-sign-out"></i>退出</el-link
        >
      </div>
    </div>
    <div class="home-box">
      <router-view></router-view>
    </div>
    <bei-an class="footer"></bei-an>
  </div>
</template>

<script>
import BeiAn from "@/components/BeiAn.vue";
import * as blogApi from '@/api/blog/api.js'
export default {
  name: "Home",
  components: {
    BeiAn,
  },
  data() {
    return {
      isLogin: false,
      isAdmin: false,
      isMobile: false,
      width: 0,
      height: 0,
      screenWidth: document.body.clientWidth,
    };
  },
  methods: {
    goUserCenter(){
      this.$router.push('/userCenter')
    },
    logout() {
      localStorage.removeItem("hhl-token");
      localStorage.removeItem("isLogin");
      localStorage.removeItem("hhl-info");
      this.isLogin = false;
      this.isAdmin = false;
    },
    clickMe() {
      this.$router.push("/");
    },
    goLogin() {
      this.common.prePath = this.$router.history.current.path;
      this.$router.push("/login");
    },
    getScreen() {
      let screenWidth = document.body.clientWidth;
      let screenHeight = document.body.clientHeight;
      return screenWidth / screenHeight;
    },
    listenScreen() {
      let initScale = this.getScreen();
      if (initScale < 1) {
        this.isMobile = true;
      }
      window.addEventListener("resize", () => {
        this.isMobile = this.getScreen() < 1 ? true : false;
      });
    },
    initSystem() {
      if (localStorage.getItem("isLogin") == "true") {
        this.isLogin = true;
      } else {
        this.isLogin = false;
      }
      //如果登录成功则回去用户的相关信息
      if (this.isLogin) {
        let info = localStorage.getItem("hhl-info");
        let obj = JSON.parse(info);
        obj.user.rolesList.forEach((element) => {
          if (element.role == "ROLE_ADMIN") {
            this.isAdmin = true;
          }
        });
      }
    },
    addView(){
      blogApi.addView();
    }
  },
  mounted() {
    this.initSystem(); //初始胡缓存
    this.listenScreen();
    this.addView();
  },
};
</script>
<style lang="scss" scoped>
.home {
  padding-top: 4rem;
}
.avatar {
  cursor: pointer;
}
.top {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  background-color: #34495E;
  position: fixed;
  top: 0;
  z-index: 15001;
  .top-left {
    display: flex;
    align-items: center;
    padding-left: 10px;
  }
  .top-right {
    padding-right: 10px;
    color: white;
  }
  .logo {
    color: white;
    cursor: pointer;
    font-weight: bold;
    font-size: 18px;
    padding-bottom: 4px;
  }
}
.home-box {
  overflow: auto;
  min-height: calc(100vh - 8rem);
}
.footer {
  position: relative;
  bottom: 0;
}
</style>
<style>
.home .el-menu.el-menu--horizontal {
  border-bottom: none;
}
.home .el-menu--horizontal > .el-menu-item {
  border-bottom: none;
}
.home .el-menu--horizontal > .el-menu-item.is-active {
  font-weight: bold;
}
</style>
