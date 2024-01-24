import
{
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
    path: "/vuex",
    component: () => import("/@/pages/vuex/vuex.vue"),
    meta: {
      loading: true,
      title: "vuex",
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
      title: "test",
    },
},
{
  path: "/test3",
  component: () => import("/@/pages/vue-hook-test3/test.vue"),
  meta: {
    loading: true,
    title: "test",
  },
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
router.beforeEach((to, from, next) =>
{
  if (to.meta.loading)
  {
    app.config.globalProperties.$loading.showLoading();
    next();
  } else
  {
    next();
  }
});

router.afterEach((to, from) =>
{
  if (to.meta.loading)
  {
    app.config.globalProperties.$loading.hideLoading();
  }
});
export default router;
