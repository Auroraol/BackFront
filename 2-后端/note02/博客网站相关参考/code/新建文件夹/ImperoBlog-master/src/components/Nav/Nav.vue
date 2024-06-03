<template>
  <div class="nav" v-show="media">
    <div class="navList">
      <ul>
        <div class="logoImg" @click="clickLogo">
          <img src="@/imgs/logo.png" alt="Impero's Blog" />
        </div>
        <li>
          <router-link to="/index">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-shouye1"></use>
            </svg>
            <span>首页</span>
          </router-link>
        </li>
        <li>
          <router-link to="/article">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-wenzhang1"></use>
            </svg>
            <span>文章</span>
          </router-link>
        </li>
        <li>
          <router-link to="/leavemsg">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-rili"></use>
            </svg>
            <span>留言</span>
          </router-link>
        </li>
        <li>
          <router-link to="/write">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-24"></use>
            </svg>
            <span>写文</span>
          </router-link>
        </li>
        <li>
          <router-link :to="{ path: '/chat', query: { name: '默认房间' } }">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-liaotian"></use>
            </svg>
            <span>聊天室</span>
          </router-link>
        </li>
        <li>
          <router-link to="/more">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-gengduo4"></use>
            </svg>
            <span>更多</span>
          </router-link>
        </li>
      </ul>
    </div>
    <SearchInput fontSize="1" :clearIpt="true">
      <template #submitBtn>
        <p style="font-size: 1.2rem; font-weight: 700; padding: 0.5rem">></p>
      </template>
    </SearchInput>
    <div class="navRight">
      <div class="login" @click="login" v-if="ifLog">登录/注册</div>
      <NavUserInfo class="welcome" v-else></NavUserInfo>
    </div>
  </div>

  <!-- v-else -->
  <div v-show="media2" class="media2">
    <div class="userHead">
      <!-- <p v-if="ifLog" @click="clickLogoPhone">帝国睡眼惺忪</p> -->
      <img src="@/imgs/logo.png" alt="" v-if="ifLog" @click="clickLogoPhone" />
      <NavUserInfo
        class="welcome"
        v-else
        @click="goPersonalCenter"
      ></NavUserInfo>
    </div>
    <div @click="openDrawer">
      <NavMore :key="navKey"></NavMore>
    </div>

    <div class="phoneNav">
      <ul>
        <li @click="goPage('/index')">⚘回到首页⚘</li>
        <li @click="goPage('/article')">⚘文章列表⚘</li>
        <li @click="goPage('/leavemsg')">⚘留言版⚘</li>
        <li @click="goPage('/write')">⚘写文章⚘</li>
        <li @click="goPage('/chat')">⚘聊天室⚘</li>
        <li @click="goPage('/more')">⚘更多⚘</li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect, defineAsyncComponent } from "vue";
import { useStore } from "@/store/count";
import { useRouter } from "vue-router";
import SearchInput from "@/components/searchCom/searchInput.vue";
import NavMore from "@/components/utils/uiCopy/navMore.vue";
import gsap from "gsap";
import { goToPersonalCenterHook } from "@/hooks/goToPersonalCenter/goToPersonalCenter";
const NavUserInfo = defineAsyncComponent(
  () => import("@/components/Nav/navUserInfo.vue")
); //要在组件里面使用useAxios要异步引入组件
const router = useRouter();
const pinia = useStore();

const media = ref(true); //根据页面响应使用哪个样式的标题栏
const media2 = ref(false);

//登录
const ifLog = ref(true); //当它是false时是已登录
const login = () => {
  router.push("/login");
};

watchEffect(async () => {
  if (pinia.bodyWidth < 1050) {
    media.value = false;
    media2.value = true;
  } else {
    media.value = true;
    media2.value = false;
  }

  //登录了!修改状态
  if (pinia.sessionInfo) {
    ifLog.value = false;
  } else {
    ifLog.value = true;
  }
});

const goPersonalCenter = () => {
  const token = JSON.parse(window.atob(localStorage.getItem("userAccount")!));
  goToPersonalCenterHook(token.account);
};

//是否打开抽屉
let drawer = false;
//刷新dom的key
const navKey = ref(0);

//点击展开抽屉
const openDrawer = () => {
  drawer = !drawer;
  if (drawer) {
    gsap.to(".phoneNav", {
      height: 800,
      duration: 0.3,
    });
  } else {
    gsap.to(".phoneNav", {
      height: 0,
      duration: 0.3,
    });
  }
};

// 手机端点击导航区某个页面
const goPage = (routerName: string) => {
  if (routerName === "chat") {
    router.push({ path: routerName, query: { name: "默认房间" } });
    openDrawer();
    navKey.value++;
    return;
  }
  router.push(routerName);
  openDrawer();
  navKey.value++;
};

//点击logo
const clickLogo = () => {
  router.push("/index");
};
const clickLogoPhone = () => {
  router.push("/login");
};
</script>

<style scoped lang="less">
.nav {
  // position: absolute;
  // top: 0;
  // left: 0;
  width: 100%;
  height: 4.5rem;
  margin-bottom: 1rem;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  justify-content: space-between;
  // background-color: var(--nav-background-color);

  .navList {
    text-shadow: 0.1rem 0.1rem 0.2rem rgb(61, 61, 61);
    margin-left: 15rem;

    ul {
      display: flex;
      align-items: center;
      // padding-left: 14rem;

      .logoImg {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 15rem;
        height: 4.5rem;
        margin-top: 1rem;
        cursor: pointer;

        img {
          transform: scale(0.8);
          width: 100%;
          object-fit: cover;
        }
      }

      // &:last-child {
      //     border-right: 0.1rem solid rgba(255, 255, 255, .1);
      // }

      li {
        &:hover {
          cursor: pointer;
          background-color: rgba(245, 245, 245, 0.1);
          border-radius: 0.7rem;
          transition: all 0.2s;
        }

        a {
          color: #fff;
          // font-size: 1.2rem;
          padding: 0.4rem 1rem;
          display: flex;
          justify-content: center;
          align-items: center;

          :nth-child(1) {
            font-size: 1.1rem;
            color: rgb(255, 255, 255);
            margin-right: 0.5rem;
          }

          :nth-child(2) {
            font-size: 1.1rem;
            font-weight: 700;
          }

          // &:hover {
          //     color: var(--box-shadow);
          // }
        }
      }
    }
  }

  .navRight {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .login {
      color: rgb(224, 224, 224);
      margin-right: 2rem;
      text-shadow: 0.1rem 0.1rem var(--gray-sahdow);

      &:hover {
        cursor: pointer;
        color: var(--special-font-color);
      }
    }
  }

  .welcome {
    margin-right: 3rem;
  }
}

.media2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: var(--white-font-color);
  background-color: var(--nav-background-color);
  padding: 0 2rem;
  position: relative;
  margin-bottom: 1rem;

  .userHead {
    margin: 0.2rem 0;

    p {
      font-size: 3rem;
      margin: 1rem 0;
    }

    .welcome {
      width: 6rem;
      height: 6rem;
    }

    img {
      margin-top: 0.5rem;
      height: 5rem;
    }
  }

  .phoneNav {
    position: absolute;
    bottom: 0;
    left: 0;
    transform: translateY(100%);
    color: var(--white-font-color);
    font-size: 4rem;
    background-color: var(--nav-background-color);
    width: 100%;
    height: 0;
    overflow: hidden;
    z-index: 1;

    ul {
      margin-top: 1rem;

      li {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 1.8rem 0;
        width: 100%;
      }
    }
  }
}

@media screen and (max-width: 1200px) {
  .nav {
    .navList {
      margin-left: 1rem;
    }
  }
}
</style>