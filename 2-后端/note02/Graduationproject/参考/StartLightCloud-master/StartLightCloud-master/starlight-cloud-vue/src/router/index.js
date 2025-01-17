import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router';
import { shallowRef } from 'vue';

const routes = [
    {
        path: '/',
        component: () => import('../components/Login.vue')
    },
    {
        path: '/login',
        component: () => import('../components/Login.vue')
    },
    {
        path: '/register',
        component: () => import('../components/Register.vue')
    },
    ,
    {
        path: '/home',
        component: () => import('../components/Home.vue')
    }
    ,
    {
        path: '/wallet',
        component: () => import('../components/Center.vue'),
    },
    {
        path: '/shop',
        component: () => import('../components/Shop.vue'),
    }
];


const router = createRouter({
    //history: createWebHashHistory(),  // hash路由模式
      history: createWebHistory(),  // history路由模式
    routes
});

//白名单
const whitelist = ['/login','/','/register']

//路由守卫跳转拦截
router.beforeEach((to, from, next) => {

    //遇到白名单直接放行
    if(whitelist.indexOf(to.path) !== -1){
        next()
        return
    }

    //校验是否有token
    let token = $cookies.get("token"); 
    if(token == null){
        next('/')
    }
    else{
        next()
    }
}
)

export default router;
