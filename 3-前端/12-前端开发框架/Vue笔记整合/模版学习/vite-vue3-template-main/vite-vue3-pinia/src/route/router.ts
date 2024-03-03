import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from "vue-router";

// 导入路由404分模块
import NoFond from "./no-fond";
// 导出多个要导出一个数组形式，在router.js中使用,必须使用扩展运算全部导入

//引入main.ts
import app from "../main";

const routes = [
  // 重定向
  {
    path: "/index.html",
    redirect: "/",
  },
  {
    path: "/",
    name: "home",
    component: () => import("/@/pages/home/home.vue"),
    meta: {
      loading: true,
      title: "首页",
    },
  },
  {
    path: "/vaxiso",
    component: () => import("/@/pages/vaxiso/vaxiso.vue"),
    meta: {
      loading: true,
      title: "vaxiso",
    },
  },
  {
    path: "/editTable",
    component: () => import("/@/pages/edit-table/index.vue"),
    meta: {
      loading: true,
      title: "editTable",
    },
  },
  {
    path: "/test",
    component: () => import("/@/pages/vue-hook-test/test.vue"),
    meta: {
      loading: true,
      title: "test",
    },
  },
  {
    path: "/test2",
    component: () => import("/@/pages/vue-hook-test2/test.vue"),
    meta: {
      loading: true,
      title: "test2",
    },
  },
  {
    path: "/test3",
    component: () => import("/@/pages/vue-hook-test3/test.vue"),
    meta: {
      loading: true,
      title: "test3",
    },
  },
  // Pinia
  {
    path: "/storeTest1",
    component: () => import("../pages/store-test/test.vue"),
    meta: {
      loading: true,
      title: "storeTest1",
    },
  },
  // 测试$route使用
  {
    // url http://localhost:8080/routeTest1/20
    path: "/routeTest1/:id",
    component: () => import("../pages/route-test/test1.vue"),
    meta: {
      loading: true,
      title: "routeTest1",
    },
  },
  {
    // url http://localhost:8080/routeTest2/20
    path: "/routeTest2/:id",
    component: () => import("../pages/route-test/test2.vue"),
    meta: {
      loading: true,
      title: "routeTest2",
    },
  },
  // 路由参数
  {
    // url http://localhost:3000/routeTest3/20/定积分
    path: "/routeTest3/:id/:title",
    component: () => import("../pages/route-test/test3.vue"),
    meta: {
      loading: true,
      title: "routeTest3",
    },
  },
  {
    // url http://localhost:3000/routeTest4?id=20&title=定积分
    path: "/routeTest4",
    component: () => import("../pages/route-test/test4.vue"),
    props($route){
      return {
        id:$route.query.id,
        title:$route.query.title,
        // 可以携带默认值
        default: '我是默认参数'
      }
    },
    meta: {
      loading: true,
      title: "routeTest4",
    }
  },
  // 路由分模块
  NoFond,
];

// 导出路由
const router = createRouter({
  history: createWebHistory(), //开启history模式
  // history: createWebHashHistory(), //开启hash模式
  routes,
});

// 在路由元信息配置守卫 requiredPath为true, 适合守卫多个页面 vue3next() 变成return true
router.beforeEach((to, from, next) => {
  if (to.meta.loading) {
    app.config.globalProperties.$loading.showLoading();
    next();
  } else {
    next();
  }
});

router.afterEach((to, from) => {
  if (to.meta.loading) {
    app.config.globalProperties.$loading.hideLoading();
  }
});
export default router;
