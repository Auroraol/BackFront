import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    redirect: 'blog',
    children: [
      {
        path: 'blog',
        name: 'BlogList',
        component: () => import('../views/blog/BlogList.vue')
      },
      {
        path: 'editBlog',
        name: 'EditBlog',
        component: () => import('../views/blog/EditBlog.vue'),
        meta: {
          checkIsLogin: true
        }
      },
      {
        path: 'blogManager',
        name: 'BLogManager',
        component: () => import('../views/blog/BlogManager.vue'),
        meta: {
          checkIsLogin: true
        }
      },
      {
        path: 'userCenter',
        name: 'UserCenter',
        component: () => import('../views/user/UserCenter.vue'),
        meta: {
          checkIsLogin: true
        }
      },
      {
        path: 'blogDetail/:id/:title',
        name: 'BlogDetail',
        props: true,
        component: () => import('../views/blog/BlogDetail.vue')
      },
      {
        path: 'project',
        name: 'Project',
        component: () => import('../views/project/Project.vue')
      },
      {
        path: 'resource',
        name: 'Resource',
        component: () => import('../views/resource/Resource.vue')
      },
      {
        path: '/about',
        name: 'About',
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/findPassword/:code',
    name: 'FindPassword',
    props: true,
    component: () => import('../views/FindPassword.vue'),
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.checkIsLogin) {
    if (localStorage.getItem('hhl-token') != null) {
      next()
    } else {
      next({name: 'Login'})
    }
  } else {
    next()
  }

})

export default router
