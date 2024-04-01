## [官网](https://router.vuejs.org/zh/guide/advanced/meta.html)

- [vue-router 官网](https://router.vuejs.org/)
- [vue-router 中文文档](https://router.vuejs.org/zh/)

+ 理解： 一个路由（route）就是一组映射关系（key - value），多个路由需要路由器（router）进行管理。

+ 前端路由：key是路径，value是组件。

vue-router 主要用于创建单页应用，再结合 vue.js 的时候显得得心应手。

所谓单页面应用，只有一个页面，所以不需要跳转！除非是要做同构应用。vue所谓的路由，可以理解为一个标识，告诉vue当url是什么的时候去渲染什么路由页面。如果真的想跳转页面，直接用a标签。

## 一、安装与使用

### 安装

```shell
npm  install vue-router
```

基本组件:

- **router-view**  路由入口
- **router-link**    路由链接

### 基本使用

使用方式：

1. 导入 Vue 和 VueRouter，调用 Vue.use(VueRouter)
2. 引入路由组件
3. 定义路由
4. 创建 router 实例，然后传 `routes` 配置
5. 创建和挂载根实例

在vue-cli创建的项目中， `main.js` 配置如下：
```javascript
import Vue from 'vue'
import App from './App.vue'
import Router from 'vue-router'

const routes = [
  { path: '/home', name: 'home', component: { template: '<div>Home</div>' } },
  { path: '/page', name: 'page', component: { template: '<div>Page</div>' } }
]

const router = new Router({
  hashbang: true,
  mode: 'history',
  routes
})

Vue.use(Router)

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
```

在 `App.vue` 中：
```vue
<template lang="pug">
div
  p
    <router-link to="/home"> Home</router-link>
    span(style="margin: 0 10px") |
    <router-link to="/page"> Page</router-link>
  router-view
</template>
```
效果：![GIF.gif](https://cdn.nlark.com/yuque/0/2020/gif/2213540/1607933542163-77c74955-70d0-4385-9332-26c01310ccf6.gif#align=left&display=inline&height=77&originHeight=77&originWidth=229&size=2988&status=done&style=none&width=229)

### 几个注意点

实现切换（active-class可配置高亮样式）

```vue
<router-link active-class="active" to="/about">About</router-link>
```

指定路由页面展示位置

```vue
<router-view></router-view>
```

1. 路由组件通常存放在```pages```文件夹，一般组件通常存放在```components```文件夹。
2. 通过切换，“隐藏”了的路由组件，默认是被销毁掉的，需要的时候再去挂载。
3. 每个组件都有自己的```$route```属性，里面存储着自己的路由信息。
4. 整个应用只有一个router，可以通过组件的```$router```属性获取到。

### 模版1:crossed_swords:

router.ts

```js
import
{
  createRouter,
  createWebHistory,
  createWebHashHistory,
} from "vue-router";

// 导入路由404分模块
import NoFond from "./NoFond";
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
    component: () => import("/@/pages/editTable/index.vue"),
    meta: {
      loading: true,
      title: "editTable",
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

```

NoFond.ts

```ts
export default {
  // 路由分模块
  // 如果url找不到就会报404,必须放在路由页面最下面
  //   !!! 项目中不能以关键字命名，否则会报错 包括不限于 xx.vue组件页面
  path: "/:pathMatch(.*)",
  component: () => import("/@/pages/404/NoFondPage.vue"),
  meta: {
    loading: true,
    title: "404",
  },
};
```

### 模版2:crossed_swords:

```ts
import Vue from 'vue';
import Router from 'vue-router';
import Index from '@/pages/Index';
const Login = resolve => require(['@/pages/Login'], resolve);
const SignUp = resolve => require(['@/pages/SignUp'], resolve);
const ForgetPassword = resolve => require(['@/pages/forgetPassword'], resolve);
const GoodsList = resolve => require(['@/pages/GoodsList'], resolve);
const GoodsDetail = resolve => require(['@/pages/GoodsDetail'], resolve);
const ShoppingCart = resolve => require(['@/pages/ShoppingCart'], resolve);
const Cart = resolve => require(['@/pages/Cart'], resolve);
const Pay = resolve => require(['@/pages/payment/Pay'], resolve);
const PayDone = resolve => require(['@/pages/payment/PayDone'], resolve);
const PayMent = resolve => require(['@/pages/payment/PayMent'], resolve);
const ThirdPay = resolve => require(['@/pages/payment/thirdPay'], resolve);
const Feedback = resolve => require(['@/pages/Feedback'], resolve);
const Coupon = resolve => require(['@/pages/couponCenter'], resolve);
const seckill = resolve => require(['@/pages/promotion/seckill'], resolve);
const article = resolve => require(['@/pages/article/index'], resolve);
const PointMall = resolve => require(['@/pages/PointMall'], resolve);

/*
 * 会员中心
 * 订单列表
 */
const MyOrder = resolve => require([`@/pages/home/orderCenter/MyOrder`], resolve);
const OrderDetail = resolve => require([`@/pages/home/orderCenter/OrderDetail`], resolve);
const MyAddress = resolve => require(['@/pages/home/orderCenter/MyAddress'], resolve);
const AddAddress = resolve => require(['@/pages/home/orderCenter/AddAddress'], resolve);
const Complain = resolve => require(['@/pages/home/orderCenter/Complain'], resolve);
const AfterSale = resolve => require(['@/pages/home/orderCenter/AfterSale'], resolve);
const AfterSaleDetail = resolve => require(['@/pages/home/orderCenter/AfterSaleDetail'], resolve);
const ApplyAfterSale = resolve => require(['@/pages/home/orderCenter/ApplyAfterSale'], resolve);

/*
 * 会员中心
 */
const Profile = resolve => require(['@/pages/home/memberCenter/Profile'], resolve);
const AccountSafe = resolve => require(['@/pages/home/memberCenter/AccountSafe'], resolve);
const ModifyPwd = resolve => require(['@/pages/home/memberCenter/ModifyPwd'], resolve);
const AccountBind = resolve => require(['@/pages/home/memberCenter/AccountBind'], resolve);
const Favorites = resolve => require(['@/pages/home/memberCenter/Favorites'], resolve);
const Distribution = resolve => require(['@/pages/home/memberCenter/Distribution'], resolve);
const CommentList = resolve => require(['@/pages/home/memberCenter/CommentList'], resolve); // 评价列表
const AddEval = resolve => require(['@/pages/home/memberCenter/evaluation/AddEval'], resolve); // 添加评价
const EvalDetail = resolve => require(['@/pages/home/memberCenter/evaluation/EvalDetail'], resolve);
const ComplainList = resolve => require(['@/pages/home/memberCenter/ComplainList'], resolve);
const ComplainDetail = resolve => require(['@/pages/home/memberCenter/ComplainDetail'], resolve);
const Invoice = resolve => require(['@/pages/home/memberCenter/Invoice'], resolve);
const Point = resolve => require(['@/pages/home/memberCenter/Point'], resolve);
const MsgList = resolve => require(['@/pages/home/memberCenter/memberMsg/MsgList'], resolve);
const MsgDetail = resolve => require(['@/pages/home/memberCenter/memberMsg/MsgDetail'], resolve);

/*
 * 会员中心
 * 账户中心
 * */
const Coupons = resolve => require(['@/pages/home/userCenter/Coupons'], resolve);
const MyTracks = resolve => require(['@/pages/home/userCenter/MyTracks'], resolve);
const MoneyManagement = resolve => require(['@/pages/home/userCenter/MoneyManagement'], resolve);

const Home = resolve => require(['@/pages/user/Home'], resolve);

const MyShoppingCart = resolve => require(['@/pages/home/MyShoppingCart'], resolve);
const Merchant = resolve => require(['@/pages/Merchant'], resolve);
// const AllCategories = resolve => require(['@/pages/AllCategories'], resolve);
const UserMain = resolve => require(['@/pages/home/Main'], resolve);

/**
 * 店铺入驻 首页
 * 店铺入驻  申请页
 */
const ShopEntry = resolve => require(['@/pages/shopEntry/shop-entry'], resolve);

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [{
    path: '/', // 首页
    name: 'Index',
    component: Index
  },
  {
    path: '/login', // 登陆
    name: 'Login',
    component: Login,
    meta: {
      title: '码神商城 登录'
    }
  },
  {
    path: '/SignUp', // 注册
    name: 'SignUp',
    component: SignUp,
    meta: {
      title: '码神商城 注册'
    }
  },
  {
    path: '/forgetPassword', // 忘记密码
    name: 'forgetPassword',
    component: ForgetPassword,
    meta: {
      title: '码神商城 忘记密码'
    }
  },
  {
    path: '/goodsList', // 商品列表
    name: 'GoodsList',
    component: GoodsList
  },
  {
    path: '/goodsDetail', // 商品详情
    name: 'GoodsDetail',
    component: GoodsDetail,
    meta: {title: '商品详情'}
  },
  {
    path: '/shoppingCart', // 头部购物车
    name: 'ShoppingCart',
    component: ShoppingCart
  },
  {
    path: '/cart', // 购物车
    name: 'Cart',
    component: Cart,
    meta: {title: '购物车'}
  },
  {
    path: '/pay', // 支付页面
    name: 'Pay',
    component: Pay,
    meta: {title: '订单结算'}
  },
  {
    path: '/payMent',
    name: 'PayMent',
    component: PayMent
  },
  {
    path: '/PointMall',
    name: 'PointMall',
    component: PointMall,
    meta: {title: '积分商城'}
  },
  {
    path: '/qrpay', // 三方支付
    name: 'qrpay',
    component: ThirdPay
  },
  {
    path: '/payDone', // 支付成功页面
    name: 'PayDone',
    component: PayDone
  },
  {
    path: '/feedback', // 反馈页面
    name: 'Feedback',
    component: Feedback,
    meta: {
      title: '码神商城 登录'
    }
  },
  {
    path: '/article', // 文章页面
    name: 'article',
    component: article,
    meta: {
      title: '帮助中心'
    }
  },
  {
    path: '/shopEntry',
    name: 'shopEntry',
    component: ShopEntry,
    meta: {
      title: '店铺入驻'
    }
  },
  {
    path: '/coupon',
    name: 'coupon',
    component: Coupon,
    meta: {
      title: '领券中心'
    }
  },
  {
    path: '/seckill',
    name: 'seckill',
    component: seckill,
    meta: {
      title: '限时秒杀'
    }
  },
  {
    path: '/home', // 主页
    component: Home,
    children: [{
      path: '/',
      name: 'Home',
      component: UserMain,
      meta: {
        title: '会员中心'
      }
    },
    {
      path: 'MyTracks',
      name: 'MyTracks',
      component: MyTracks,
      meta: {title: '我的足迹'}
    },
    {
      path: 'MoneyManagement',
      name: 'MoneyManagement',
      component: MoneyManagement
    },
    {
      path: 'Complain',
      name: 'Complain',
      component: Complain
    },
    {
      path: 'Coupons',
      name: 'Coupons',
      component: Coupons
    },
    {
      path: 'CommentList',
      name: 'CommentList',
      component: CommentList,
      mate: {title: '评价列表'}
    },
    {
      path: 'AddEval',
      name: 'AddEval',
      component: AddEval,
      mate: {title: '添加评价'}
    },
    {
      path: 'EvalDetail',
      name: 'EvalDetail',
      component: EvalDetail,
      mate: {title: '评价详情'}
    },
    {
      path: 'ComplainList',
      name: 'ComplainList',
      component: ComplainList
    },
    {
      path: 'ComplainDetail',
      name: 'ComplainDetail',
      component: ComplainDetail
    },
    {
      path: 'Invoice',
      name: 'Invoice',
      component: Invoice
    },
    {
      path: 'AccountSafe',
      name: 'AccountSafe',
      component: AccountSafe
    },
    {
      path: 'ModifyPwd',
      name: 'ModifyPwd',
      component: ModifyPwd
    },
    {
      path: 'Favorites',
      name: 'Favorites',
      component: Favorites
    },
    {
      path: 'Distribution',
      name: 'Distribution',
      component: Distribution,
      meta: {title: '我的投诉'}
    },
    {
      path: 'Point',
      name: 'Point',
      component: Point,
      meta: {title: '我的积分'}
    },
    {
      path: 'Profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: 'AccountBind',
      name: 'AccountBind',
      component: AccountBind
    },
    {
      path: 'AfterSale',
      name: 'AfterSale',
      component: AfterSale,
      meta: {title: '码神商城 售后'}
    },
    {
      path: 'ApplyAfterSale',
      name: 'ApplyAfterSale',
      component: ApplyAfterSale,
      meta: {title: '申请售后'}
    },
    {
      path: '/home/MyAddress',
      name: 'MyAddress',
      component: MyAddress,
      meta: {title: '收货地址'}
    },
    {
      path: 'AddAddress',
      name: 'AddAddress',
      component: AddAddress
    },
    {
      path: 'MsgList',
      name: 'MsgList',
      component: MsgList,
      meta: {title: '我的消息'}
    },
    {
      path: 'MsgDetail',
      name: 'MsgDetail',
      component: MsgDetail,
      meta: {title: '我的消息'}
    },
    {
      path: 'MyOrder',
      name: 'MyOrder',
      component: MyOrder,
      meta: {title: '我的订单'}
    },
    {
      path: 'OrderDetail',
      name: 'OrderDetail',
      component: OrderDetail,
      meta: {title: '订单详情'}
    },
    {
      path: 'AfterSaleDetail',
      name: 'AfterSaleDetail',
      component: AfterSaleDetail,
      meta: {title: '售后详情'}
    },
    {
      path: 'MyShoppingCart',
      name: 'MyShoppingCart',
      component: MyShoppingCart
    }
    ]
  },
  {
    path: '/merchant',
    name: 'Merchant',
    component: Merchant,
    meta: {title: '店铺'}
  }
  ]
});

```



## 二、`$route`与`$router`

使用模块注册 vue-router，将会在 `Vue.prototype` 中注入一个 `$route` 和 `$router` 的只读属性。

其中，`$route` 用于监测当前路由，`$router` 用于进行路由操作，事实上，`$route` 与 `$router.currentRoute` 相同。

**$route**<br />**<img src="https://cdn.nlark.com/yuque/0/2020/png/2213540/1607933616987-5fa42f67-be8a-4149-8f02-76ce522e75b3.png#align=left&display=inline&height=162&originHeight=162&originWidth=240&size=2790&status=done&style=none&width=240" alt="025.png" style="zoom:67%;" />**

**$router**<br />**<img src="https://cdn.nlark.com/yuque/0/2020/png/2213540/1607933624065-ef297bfc-e8c4-4a25-8d44-13fdac55c358.png#align=left&display=inline&height=639&originHeight=639&originWidth=705&size=21557&status=done&style=none&width=705" alt="026.png" style="zoom:67%;" />**

- **$router**为VueRouter实例，比如想要导航到不同URL，则使用`$router.push`方法
- **$route**为当前router跳转对象，里面可以获取name、path、query、params等

### vue3

在Vue 3中，`$route`对象通常与Vue Router关联，通常在组件的生命周期钩子中访问。

1. 方法1  **`<script setup>` 中:**

   ```vue
   <template>
     <div>
       <h2>当前路由信息</h2>
       <p>路径：{{ currentRoutePath }}</p>
     </div>
   </template>
   
   
   
   <script setup>
   import { onMounted, ref } from 'vue';
   import { useRoute } from 'vue-router';
   
   const route = useRoute();
   
   const currentRoutePath = ref('');
   currentRoutePath.value = route.path;
   console.log('当前路由路径：', currentRoutePath.value);
   
   </script>
   
   // script setup 中使用
   ```

   - 当使用`<script setup>`语法时，这特别有用。
   - 在 `<script setup>` 语法中，不能直接使用 `$route`

2. 方法2  **直接在模板中:**

   - 如果不在`setup`函数内，可以直接在模板中访问`$route`，无需使用`this`。

   ```vue
   <template>
     <div>
       <h2>当前路由信息</h2>
       <p>路径：{{ $route.path }}</p>
       <p>参数：{{ $route.params }}</p>
       <p>查询参数：{{ $route.query }}</p>
     </div>
   </template>
   
   <script setup>
   </script>
   
   // 可以直接在模板中
   ```

   - 这等同于在模板中使用`this.$route`。

3. 非`<script setup>`中

   ```vue
   <script>
   import { onMounted, getCurrentInstance } from 'vue';
   
   export default {
     name: 'MyComponent',
   
     setup() {
       const instance = getCurrentInstance();
   
       onMounted(() => {
         // 
         console.log('当前路由路径：', instance.proxy.$route.path);
       });
   
       return {};
     },
   };
   </script>
   ```

### vue2

==在Vue.js 2中，this.$route 可以在Vue组件的任何生命周期钩子函数和方法中被调用。和vue3有很大区别==

   ```js
   export default {
     mounted() {
       console.log(this.$route.path); // 在mounted钩子函数中获取当前路由的路径
     },
     methods: {
       navigateToHome() {
         if (this.$route.path !== '/home') {
           this.$router.push('/home'); // 通过this.$route执行路由跳转
         }
       }
     },
     computed: {
       isAboutPage() {
         return this.$route.path === '/about'; // 通过计算属性获取当前路由是否是/about
       }
     }
   }
   ```

## 三、路由状态管理

[vuex-router-sync](https://www.npmjs.com/package/vuex-router-sync) 是一款将 vue-router 的 `$route` 属性同步到 vuex 中 state 的插件, 可以通过操作路由改变状态管理, 方便监视路由变化。

使用方式：
```javascript
import { sync } from 'vuex-router-sync'
import store from './vuex/store' // vuex store instance
import router from './router' // vue-router instance

const unsync = sync(store, router)
```

使用：
```javascript
store.state.route.path   // current path (string)
store.state.route.params // current params (object)
store.state.route.query  // current query (object)
```

## 四、**路由历史栈:crossed_swords:**

在使用路由的单页应用中，每访问一个页面，便将其加入**路由历史栈**当中，vue-router 提供各种控制路由的方法，操作方法同 `window.history`。

### 声明式路由

- `<router-link :to='...'></router-link>`
- `<router-link :to="..." replace></router-link>`

直接书写路径：

```html
<router-link to='/'></router-link>
```

通过对象的方式：

```html
<router-link :to="{ name: 'home' }"></router-link>
<router-link :to="{ path: 'foo/bar/index' }"></router-link>
<router-link :to="{ name: 'user', params: { userId: 123 }}">User</router-link>
<router-link :to="{ path: 'register', query: { plan: 'private' } }"></router-link>
```

 **<router-link>的replace属性:  **

1. 作用：控制路由跳转时操作浏览器历史记录的模式
2. 浏览器的历史记录有两种写入方式：分别为```push```和```replace```，```push```是追加历史记录，```replace```是替换当前记录。路由跳转时候默认为```push```
3. 如何开启```replace```模式：```<router-link replace .......>News</router-link>```

### 编程式路由

- `router.push(location, onComplete?, onAbort?)` 跳转
- `router.back()` 后退
- `router.forward()` 前进
- `router.go(n)` 直接抵达指定的路由历史栈中的某个页面，指定n值，当前页面为 0，正数为此页之后的页面，负数为此页之前的页面
- `router.replace(location, onComplete?, onAbort?)` 替换

**使用**

```ts
<script setup lang="ts">
    
import { useRouter } from 'vue-router'
const router = useRouter();
const toPage = (url) => {
  router.push(url) // 字符串形式跳转  //路由地址
}

</script>
```

直接书写路径： 路由地址什么啥就是什么
```javascript
router.push('路由地址')
router.push('/home')
router.push('/home?from=index')
```

通过对象的方式：
```javascript
router.push({ name: 'home' })
router.push({ path: 'foo/bar/index' })
```

动态路由参数的传递, 有以下两种写法：
```javascript
const userId = 123
router.push({ name: 'user', params: { userId }}) // -> /user/123
router.push({ path: `/user/${userId}` }) // -> /user/123
```

查询字符串的写法，有以下两种写法：
```javascript
router.push(`register?plan=${private}`)
router.push({ path: 'register', query: { plan: 'private' }})
```

总结：

```js
//$router的两个API
router .push({
	name:'xiangqing',
		params:{
			id:xxx,
			title:xxx
		}
})

router.replace({
	name:'xiangqing',
		params:{
			id:xxx,
			title:xxx
		}
})
router.forward() //前进
router.back() //后退
router.go() //可前进也可后退
```

## 五、路由模式

### 两种工作模式

1. hash模式：
   1. 地址中永远带着#号，不美观 。
   2. 若以后将地址通过第三方手机app分享，若app校验严格，则地址会被标记为不合法。
   3. 兼容性较好。
2. history模式：
   1. 地址干净，美观 。
   2. 兼容性和hash模式相比略差。
   3. 应用部署上线时需要后端人员支持，解决刷新页面服务端404的问题。

**router/index.js**

```javascript
const router = new VueRouter({
  mode: 'history',
  routes: [
      ...//路由
  ]
})
```

当使用 history 模式时，URL 就像正常的 url，例如 `http://yoursite.com/user/id`。

### 服务器配置以支持History模式
**nginx**
```bash
location / {
  try_files $uri $uri/ /index.html;
}
```

**Apache**
```xml
<IfModule mod_rewrite.c>
  RewriteEngine On
  RewriteBase /
  RewriteRule ^index\.html$ - [L]
  RewriteCond %{REQUEST_FILENAME} !-f
  RewriteCond %{REQUEST_FILENAME} !-d
  RewriteRule . /index.html [L]
</IfModule>
```

**Node.js**
```javascript
const http = require('http')
const fs = require('fs')
const httpPort = 80

http.createServer((req, res) => {
  fs.readFile('index.htm', 'utf-8', (err, content) => {
    if (err) {
      console.log('We cannot open "index.htm" file.')
    }

    res.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8'
    })

    res.end(content)
  })
}).listen(httpPort, () => {
  console.log('Server listening on: http://localhost:%s', httpPort)
})
```

## 六、路由传参:crossed_swords:

### 几个注意点

1. 路由组件通常存放在```pages```文件夹，一般组件通常存放在```components```文件夹。
2. 通过切换，“隐藏”了的路由组件，默认是被销毁掉的，需要的时候再去挂载。
3. 每个组件都有自己的```$route```属性，里面存储着自己的路由信息。
4. 整个应用只有一个router，可以通过组件的```$router```属性获取到。

### 路由的query参数

 @RequestParam()

1. 传递参数

   ```vue
   <!-- 跳转并携带query参数，to的字符串写法 -->
   <router-link :to="/home/message/detail?id=666&title=你好">跳转</router-link>
   				
   <!-- 跳转并携带query参数，to的对象写法 -->
   <router-link 
   	:to="{
   		path:'/home/message/detail',
   		query:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

2. 接收参数：

   ```js
   $route.query.id
   $route.query.title
   ```

例子

![image-20231201102458989](%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201102458989.png)

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
			<!-- 指定组件的呈现位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>

```

router/index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					//
					children:[
						{
							path:'detail',
							component:Detail,
						}
					]
				}
			]
		}
	]
})

```

pages/Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带query参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail?id=${m.id}&title=${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->

				<!-- 跳转路由并携带query参数，to的对象写法 
				传递参数给/home/message/detail路由所表示的组件
				-->
				<router-link :to="{
					path:'/home/message/detail',
					// 携带参数
					query:{
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<!-- 展示区域 -->
		<router-view></router-view>  
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

pages/Detail.vue

```vue
<template>
	<ul>
		<!-- 接收参数 -->
		<li>消息编号：{{$route.query.id}}</li>
		<li>消息标题：{{$route.query.title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		mounted() {
			console.log(this.$route)
		},
	}
</script>
```

…….

运行结果

<img src="%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201140755215.png" alt="image-20231201140755215" style="zoom:50%;" />



### 路由的props配置[query进级]

​	作用：让路由组件更方便的收到参数

```js
{
	name:'xiangqing',
	path:'detail/:id',
	component:Detail,

	//第一种写法：props值为对象，该对象中所有的key-value的组合最终都会通过props传给Detail组件
	// props:{a:900}

	//第二种写法：props值为布尔值，布尔值为true，则把路由收到的所有params参数通过props传给Detail组件
	// props:true
	
	//第三种写法：props值为函数，该函数返回的对象中每一组key-value都会通过props传给Detail组件
	props(route){
		return {
			id:route.query.id,
			title:route.query.title
		}
	}
} 
```

![image-20231201102352825](%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201102352825.png)

index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			name:'guanyu',
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					children:[
						{
							name:'xiangqing',
							path:'detail',
							component:Detail,
							//props的第一种写法，值为对象，该对象中的所有key-value都会以props的形式传给Detail组件。
							// props:{a:1,b:'hello'}

							//props的第二种写法，值为布尔值，若布尔值为真，就会把该路由组件收到的所有params参数，以props的形式传给Detail组件。
							// props:true

							//props的第三种写法，值为函数
							props($route){
								return {
									id:$route.query.id,
									title:$route.query.title,
									a:1,
									b:'hello'
								}
							}

						}
					]
				}
			]
		}
	]
})

```

Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带params参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->
				<!-- 跳转路由并携带params参数，to的对象写法 -->
				<router-link :to="{
					name:'xiangqing',
					query:{              // 
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

Detail.vue

```vue
<template>
	<ul>
		<li>消息编号：{{id}}</li>
		<li>消息标题：{{title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		props:['id','title'],
		computed: {
			// id(){
			// 	return this.$route.query.id
			// },
			// title(){
			// 	return this.$route.query.title
			// },
		},
		mounted() {
			// console.log(this.$route)
		},
	}
</script>
```



<img src="%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201101852565.png" alt="image-20231201101852565" style="zoom:50%;" />

### 路由的params参数

 @PathVariable()

1. 配置路由，声明接收params参数

   ```js
   {
   	path:'/home',
   	component:Home,
   	children:[
   		{
   			path:'news',
   			component:News
   		},
   		{
   			component:Message,
   			children:[
   				{
   					name:'xiangqing',
   					path:'detail/:id/:title', //使用占位符声明接收params参数 //动态路径参数以冒号开头
   					component:Detail
   				}
   			]
   		}
   	]
   }
   ```

2. 传递参数

   ```vue
   <!-- 跳转并携带params参数，to的字符串写法 -->
   <router-link :to="/home/message/detail/666/你好">跳转</router-link>
   				
   <!-- 跳转并携带params参数，to的对象写法 -->
   <router-link 
   	:to="{
   		name:'xiangqing',
   		params:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

   > 特别注意：路由携带params参数时，若使用to的对象写法，则不能使用path配置项，必须使用name配置！

3. 接收参数：

   ```js
   $route.params.id
   $route.params.title
   ```

例子

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
						<!-- 指定组件的呈现位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>
```

router/index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			name:'guanyu',
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					children:[
						{
							name:'xiangqing',
							path:'detail/:id/:title', //参数:id/:title
							component:Detail,
						}
					]
				}
			]
		}
	]
})
```

pages/Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带params参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->

				<!-- 跳转路由并携带params参数，to的对象写法 -->
				<router-link :to="{
					name:'xiangqing',  //必须用name
					params:{ //携带params参数
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

pages/Detail.vue

```vue
<template>
	<ul>
        <!-- 接收参数 -->
		<li>消息编号：{{$route.params.id}}</li>            
		<li>消息标题：{{$route.params.title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		mounted() {
			// console.log(this.$route)
		},
	}
```

运行结果

<img src="%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201140950403.png" alt="image-20231201140950403" style="zoom:50%;" />

## 七、缓存路由组件

1. 作用：让不展示的路由组件保持挂载，不销毁, 保留数据。

2. 具体编码：

   ```vue
   <!-- 缓存多个路由组件 -->
   <!-- <keep-alive :include="['News','Message']"> -->
   				
   <!-- 缓存一个路由组件, (保持活着), News必须是组件名 -->
   <keep-alive include="News"> 
       <router-view></router-view>
   </keep-alive>
   ```

## 八、动态路由
我们经常需要把某种模式匹配到的所有路由，全都映射到同个组件。例如，我们有一个 `User` 组件，对于所有 ID 各不相同的用户，都要使用这个组件来渲染。那么，我们可以在 `vue-router` 的路由路径中使用『动态路径参数』（dynamic segment）来达到这个效果：
```javascript
const User = {
  template: '<div>User {{ $route.params.id }}</div>'
}

const router = new VueRouter({
  routes: [
    // 动态路径参数 以冒号开头
    {
      path: '/user/:id',
      component: {
        template: '<div>User {{ $route.params.id }}</div>'
      }
    }
  ]
})
```

现在呢，像 `/user/foo` 和 `/user/bar` 都将映射到相同的路由。

一个『路径参数』使用冒号 `:` 标记。当匹配到一个路由时，参数值会被设置到 `this.$route.params`，可以在每个组件内使用。

甚至可以在一个路由中设置多段『路径参数』，对应的值都会设置到 `$route.params` 中。例如：

| 模式 | 匹配路径 | $route.params |
| --- | --- | --- |
| /user/:username | /user/evan | `{ username: 'evan' }` |
| /user/:username/post/:post_id | /user/evan/post/123 | `{ username: 'evan', post_id: 123 }` |


除了 `$route.params` 外，`$route` 对象还提供了其它有用的信息，例如，`$route.query`（如果 URL 中有查询参数）、`$route.hash` 等等。

## 九、响应路由的变化
当使用路由参数时，例如从 `/user/foo` 导航到 `/user/bar`，**原来的组件实例会被复用**。因为两个路由都渲染同个组件，比起销毁再创建，复用则显得更加高效。**不过，这也意味着组件的生命周期钩子不会再被调用**。

复用组件时，如果想对路由参数的变化作出响应的话，可以简单地 watch（监测变化） `$route` 对象：

```javascript
const User = {
  template: '...',
  watch: {
    '$route' (to, from) {
      // 对路由变化作出响应...
    }
  }
}
```

或者使用 2.2 中引入的 `beforeRouteUpdate` 守卫：

```javascript
const User = {
  template: '...',
  beforeRouteUpdate (to, from, next) {
    // react to route changes...
    // don't forget to call next()
  }
}
```

## 十、嵌套路由:crossed_swords:

> 实现嵌套子页面

### 例子1

1. 配置路由规则，使用children配置项：

   ```js
   routes:[
   	{
   		path:'/about',
   		component:About,
   	},
   	{
   		path:'/home',
   		component:Home,
   		children:[ //通过children配置子级路由
   			{
   				path:'news', //此处一定不要写：/news
   				component:News
   			},
   			{
   				path:'message',//此处一定不要写：/message
   				component:Message
   			}
   		]
   	}
   ]
   ```

2. 跳转（要写完整路径）：

   ```vue
   <router-link to="/home/news">News</router-link>
   ```

示例

<img src="%F0%9F%93%83%20Vue%20Router%203.assets/image-20231122200936282.png" alt="image-20231122200936282" style="zoom:67%;" />

router/index.js 路由

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			path:'/about',
			component:About
		},
		// 多级路由
		{
			path:'/home',
			component:Home,
			// 
			children:[
				{
					path:'news',  //此处一定不要写：/news
					component:News,
				},
				{
					path:'message',
					component:Message,
				}
			]
		}
	]
})

```

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
						<!-- 指定组件的呈现位置 类似于插件-->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>

```

路由组件通常存放在```pages```文件夹

![image-20231128210058546](%F0%9F%93%83%20Vue%20Router%203.assets/image-20231128210058546.png)

运行效果

<img src="%F0%9F%93%83%20Vue%20Router%203.assets/image-20231122201139356.png" alt="image-20231122201139356" style="zoom:67%;" />



### 例子2

在组件中：

**<router-view>** 是用来渲染通过路由映射过来的组件，当路径更改时，<router-view> 中的内容也会发生更改

```vue
<template>
  <div class="me">
    <div class="me-header">个人中心</div>
    <h3>二级路由，嵌套路由</h3>
 
    <div class="tab">
        <router-link to="/me/a">
            <div class="children">我是a组件</div>
        </router-link>
        <router-link to="/me/b">
            <div class="children">我是b组件</div>
        </router-link>
        <router-link to="/me/c">
            <div class="children">我是c组件</div>
        </router-link>
    </div>
 
    <!--路由视图-->
    <router-view/>
        
  </div>
</template>
```

在router的index中子路由配置：

```coffeescript
  {
    path: '/me',
    name: 'me',
    component:  ()=> import('@/views/me.vue'),
    children: [
      {
        path: 'a',
        component: () => import('@/components/me/a.vue'),
      },
      {
        path: 'b',
        component: () => import('@/components/me/b.vue'),
      },
      {
        path: 'c',
        component: () => import('@/components/me/c.vue'),
      },
      
    ]  
  }
```

点击“我是a组件”按钮路由就渲染me下的a组件内容，点击“我是**b**组件”按钮路由就渲染me下的b组件内容，点击“我是**c**组件”按钮路由就渲染me下的c组件内容

效果如下：

点击我是a组件

![img](%F0%9F%93%83%20Vue%20Router%203.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01pc25pY2U=,size_16,color_FFFFFF,t_70.png)

点击我是b组件

![img](%F0%9F%93%83%20Vue%20Router%203.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01pc25pY2U=,size_16,color_FFFFFF,t_70-17107595205571.png)

点击我是c组件

![img](%F0%9F%93%83%20Vue%20Router%203.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01pc25pY2U=,size_16,color_FFFFFF,t_70-17107595205572.png)

 

## 十一、命名路由

作用：可以简化路由的跳转。

如何使用

1. 给路由命名：

   ```js
   {
   	path:'/demo',         
   	component:Demo,
   	children:[
   		{
   			path:'test',
   			component:Test,
   			children:[
   				{
                       name:'hello' //给路由命名  //一般取path相同的名字//一级路由简化没有意义
   					path:'welcome',
   					component:Hello,
   				}
   			]
   		}
   	]
   }
   ```

2. 简化跳转：

   ```vue
   <!--简化前，需要写完整的路径 -->
   <router-link to="/demo/test/welcome">跳转</router-link>
   
   <!--简化后，直接通过名字跳转 -->
   <router-link :to="{name:'hello'}">跳转</router-link>
   
   <!--简化写法配合传递参数 -->
   <router-link 
   	:to="{
   		name:'hello',
   		query:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

## 十二、路由重定向

重定向也是通过 `routes` 配置来完成，下面例子是从 `/a` 重定向到 `/b`：
```javascript
const router = new VueRouter({
  routes: [
    { path: '/a', redirect: '/b' }
  ]
})
```

重定向的目标也可以是一个命名的路由：
```javascript
const router = new VueRouter({
  routes: [
    { path: '/a', redirect: { name: 'foo' }}
  ]
})
```

甚至是一个方法，动态返回重定向目标：
```javascript
const router = new VueRouter({
  routes: [
    { path: '/a', redirect: to => {
      // 方法接收 目标路由 作为参数
      // return 重定向的 字符串路径/路径对象
    }}
  ]
})
```

## 十三、路由守卫:crossed_swords:

1. 作用：对路由进行权限控制(登录后才能看)
2. 分类：全局守卫、独享守卫、组件内守卫

![image-20231201134917426](%F0%9F%93%83%20Vue%20Router%203.assets/image-20231201134917426.png)

router/index.js

### 全局守卫

```js
//全局前置守卫：初始化时执行、每次路由切换前执行
//全局前置路由守卫————初始化的时候被调用、每次路由切换之前被调用
router.beforeEach((to,from,next)=>{
	console.log('beforeEach',to,from)
	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
		if(localStorage.getItem('school') === 'atguigu'){ //权限控制的具体规则
			next() //放行
		}else{
			alert('暂无权限查看')
			// next({name:'guanyu'})
		}
	}else{
		next() //放行
	}
})

//全局后置守卫：初始化时执行、每次路由切换后执行
//全局后置路由守卫————初始化的时候被调用、每次路由切换之后被调用	// 可以用来切换网页标题
router.afterEach((to,from)=>{
	console.log('afterEach',to,from)
	if(to.meta.title){ 
		document.title = to.meta.title   //修改网页的title
	}else{
		document.title = 'vue_test'
	}
})
```

### 独享守卫

```js
beforeEnter(to,from,next){
	console.log('beforeEnter',to,from)
	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
		if(localStorage.getItem('school') === 'atguigu'){
			next()
		}else{
			alert('暂无权限查看')
			// next({name:'guanyu'})
		}
	}else{
		next()
	}
}
```

### 组件内守卫

```js
//进入守卫：通过路由规则，进入该组件时被调用
beforeRouteEnter (to, from, next) {
},
//离开守卫：通过路由规则，离开该组件时被调用
beforeRouteLeave (to, from, next) {
}
```

### 注意事项

第一个例子中的代码如下：

```js
router.beforeEach((to, from, next) => {
  if (to.path === "/login") {
     next(); // 放行
  }

  const token = localstorage.getItem("token");
  if (!token) {
    return next("/login");
  } 
  next();
});
```

第二个例子中的代码如下：

```js
router.beforeEach((to, from, next) => {
  if (to.path === "/login") {
    return next();
  }
  
  const token = localStorage.getItem("token"); 
  if (!token) {
    return next("/login");
  }
  next();
});
```

在第一个例子中，当 `to.path` 不等于 "/login" 时，代码执行到 `next()` 后没有 `return`，这意味着函数会继续往下执行，导致可能出现重复执行 `next()` 的情况。这可能会导致一些意外行为。

在第二个例子中，无论何种情况，都在执行 `next` 后直接使用 `return`，这样能够确保后续代码不会被执行，从而避免了潜在的问题。

## 十四、路由元信息:crossed_swords:

定义路由的时候可以配置 `meta` 字段：
```javascript
const router = new VueRouter({
  routes: [
    {
      path: '/foo',
      component: Foo,
      children: [
        {
          path: 'bar',
          component: Bar,
          // a meta field
          meta: { requiresAuth: true }
        }
      ]
    }
  ]
})
```

那么如何访问这个 `meta` 字段呢？

首先，我们称呼 `routes` 配置中的每个路由对象为 **路由记录**。路由记录可以是嵌套的，因此，当一个路由匹配成功后，他可能匹配多个路由记录

例如，根据上面的路由配置，`/foo/bar` 这个 URL 将会匹配父路由记录以及子路由记录。

一个路由匹配到的所有路由记录会暴露为 `$route` 对象（还有在导航守卫中的路由对象）的 `$route.matched` 数组。因此，我们需要遍历 `$route.matched` 来检查路由记录中的 `meta` 字段。

下面例子展示在全局导航守卫中检查元字段：
```javascript
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    if (!auth.loggedIn()) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else {
    next() // 确保一定要调用 next()
  }
})
```

### 自定义的路由元信息

例如当您希望隐藏某些不重要的路由或特定权限的路由时。您可以在定义路由时添加 `hidden: true` 属性，然后在菜单组件或面包屑组件中根据路由元信息来过滤需要显示的路由。

```vue
// 在路由定义中添加 hidden 属性
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: {
      hidden: true
    }
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/contact',
    name: 'Contact',
    component: Contact,
    meta: {
      hidden: true
    }
  }
]

// 在菜单组件中根据 hidden 属性来判断是否需要显示该路由
<template>
  <div>
    <router-link to="/">Home</router-link>
    <router-link v-for="route in visibleRoutes" :key="route.name" :to="route.path">{{ route.name }}</router-link>
  </div>
</template>

<script>
export default {
  computed: {
    // 过滤掉 hidden 为 true 的路由
    visibleRoutes () {
      return this.$router.options.routes.filter(route => !route.meta.hidden)
    }
  }
}
</script>

```



## 十五、路由动画

可以在路由入口外围套上一层 transition 以实现路由切换过程中的动画效果：
```html
<script src="//unpkg.com/vue/dist/vue.js"></script>
<script src="//unpkg.com/vue-router/dist/vue-router.js"></script>

<div id="app">
  <router-view></router-view>
</div>
```

```javascript
const Home = {
  template: `
    <div class="home">
      <h2>Home</h2>
      <p>hello</p>
    </div>
  `
}

const Parent = {
  data () {
    return {
      transitionName: 'slide-left'
    }
  },
  // dynamically set transition based on route change
  watch: {
    '$route' (to, from) {
      const toDepth = to.path.split('/').length
      const fromDepth = from.path.split('/').length
      this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'
    }
  },
  template: `
    <div class="parent">
      <h2>Parent</h2>
      <transition :name="transitionName">
        <router-view class="child-view"></router-view>
      </transition>
    </div>
  `
}

const Default = { template: '<div class="default">default</div>' }
const Foo = { template: '<div class="foo">foo</div>' }
const Bar = { template: '<div class="bar">bar</div>' }

const router = new VueRouter({
  mode: 'history',
  routes: [
    { path: '/', component: Home },
    { path: '/parent', component: Parent,
      children: [
        { path: '', component: Default },
        { path: 'foo', component: Foo },
        { path: 'bar', component: Bar }
      ]
    }
  ]
})

new Vue({
  router,
  template: `
    <div id="app">
      <h1>Transitions</h1>
      <ul>
        <li><router-link to="/">/</router-link></li>
        <li><router-link to="/parent">/parent</router-link></li>
        <li><router-link to="/parent/foo">/parent/foo</router-link></li>
        <li><router-link to="/parent/bar">/parent/bar</router-link></li>
      </ul>
      <transition name="fade" mode="out-in">
        <router-view class="view"></router-view>
      </transition>
    </div>
  `
}).$mount('#app')
```

```css
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s ease;
}
.fade-enter, .fade-leave-active {
  opacity: 0
}
.child-view {
  position: absolute;
  transition: all .5s cubic-bezier(.55,0,.1,1);
}
.slide-left-enter, .slide-right-leave-active {
  opacity: 0;
  -webkit-transform: translate(30px, 0);
  transform: translate(30px, 0);
}
.slide-left-leave-active, .slide-right-enter {
  opacity: 0;
  -webkit-transform: translate(-30px, 0);
  transform: translate(-30px, 0);
}
```

## 十六、数据获取
有时候，进入某个路由后，需要从服务器获取数据。例如，在渲染用户信息时，你需要从服务器获取用户的数据。我们可以通过两种方式来实现：

- **导航完成之后获取**：先完成导航，然后在接下来的组件生命周期钩子中获取数据。在数据获取期间显示『加载中』之类的指示。
- **导航完成之前获取**：导航完成前，在路由进入的守卫中获取数据，在数据获取成功后执行导航。

从技术角度讲，两种方式都不错 —— 就看你想要的用户体验是哪种。

### 导航完成后获取数据

当你使用这种方式时，我们会马上导航和渲染组件，然后在组件的 `created` 钩子中获取数据。这让我们有机会在数据获取期间展示一个 loading 状态，还可以在不同视图间展示不同的 loading 状态。

假设我们有一个 `Post` 组件，需要基于 `$route.params.id` 获取文章数据：
```html
<template>
  <div class="post">
    <div class="loading" v-if="loading">
      Loading...
    </div>

    <div v-if="error" class="error">
      {{ error }}
    </div>

    <div v-if="post" class="content">
      <h2>{{ post.title }}</h2>
      <p>{{ post.body }}</p>
    </div>
  </div>
</template>
```

```javascript
export default {
  data () {
    return {
      loading: false,
      post: null,
      error: null
    }
  },
  created () {
    // 组件创建完后获取数据，
    // 此时 data 已经被 observed 了
    this.fetchData()
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'fetchData'
  },
  methods: {
    fetchData () {
      this.error = this.post = null
      this.loading = true
      // replace getPost with your data fetching util / API wrapper
      getPost(this.$route.params.id, (err, post) => {
        this.loading = false
        if (err) {
          this.error = err.toString()
        } else {
          this.post = post
        }
      })
    }
  }
}
```

### 在导航完成前获取数据

通过这种方式，我们在导航转入新的路由前获取数据。我们可以在接下来的组件的 `beforeRouteEnter` 守卫中获取数据，当数据获取成功后只调用 `next` 方法。
```javascript
export default {
  data () {
    return {
      post: null,
      error: null
    }
  },
  beforeRouteEnter (to, from, next) {
    getPost(to.params.id, (err, post) => {
      next(vm => vm.setData(err, post))
    })
  },
  // 路由改变前，组件就已经渲染完了
  // 逻辑稍稍不同
  beforeRouteUpdate (to, from, next) {
    this.post = null
    getPost(to.params.id, (err, post) => {
      this.setData(err, post)
      next()
    })
  },
  methods: {
    setData (err, post) {
      if (err) {
        this.error = err.toString()
      } else {
        this.post = post
      }
    }
  }
}
```

在为后面的视图获取数据时，用户会停留在当前的界面，因此建议在数据获取期间，显示一些进度条或者别的指示。如果数据获取失败，同样有必要展示一些全局的错误提醒。

## 十七、滚动行为
使用前端路由，当切换到新路由时，想要页面滚到顶部，或者是保持原先的滚动位置，就像重新加载页面那样。 `vue-router` 能做到，而且更好，它让你可以自定义路由切换时页面如何滚动。

这个功能只在支持 history.pushState 的浏览器中可用

当创建一个 Router 实例，你可以提供一个 `scrollBehavior` 方法：
```javascript
const router = new VueRouter({
  routes: [...],
  scrollBehavior (to, from, savedPosition) {
    // return 期望滚动到哪个的位置
  }
})
```

`scrollBehavior` 方法接收 `to` 和 `from` 路由对象。第三个参数 `savedPosition` 当且仅当 `popstate` 导航 (通过浏览器的 前进/后退 按钮触发) 时才可用。

这个方法返回滚动位置的对象信息，长这样：

- `{ x: number, y: number }`
- `{ selector: string, offset? : { x: number, y: number }}` (offset 只在 2.6.0+ 支持)

如果返回一个 falsy (译者注：falsy 不是 `false`，[参考这里](https://developer.mozilla.org/zh-CN/docs/Glossary/Falsy))的值，或者是一个空对象，那么不会发生滚动。

举例：
```javascript
scrollBehavior (to, from, savedPosition) {
  return { x: 0, y: 0 }
}
```

对于所有路由导航，简单地让页面滚动到顶部。

返回 `savedPosition`，在按下 后退/前进 按钮时，就会像浏览器的原生表现那样：
```javascript
scrollBehavior (to, from, savedPosition) {
  if (savedPosition) {
    return savedPosition
  } else {
    return { x: 0, y: 0 }
  }
}
```

如果你要模拟『滚动到锚点』的行为：
```javascript
scrollBehavior (to, from, savedPosition) {
  if (to.hash) {
    return {
      selector: to.hash
    }
  }
}
```

我们还可以利用[路由元信息](https://router.vuejs.org/zh-cn/advanced/meta.html)更细颗粒度地控制滚动。查看完整例子请[移步这里](https://github.com/vuejs/vue-router/blob/next/examples/scroll-behavior/app.js)。

### 异步滚动
2.8.0+ , 也可以返回一个 Promise 来得出预期的位置描述：
```javascript
scrollBehavior (to, from, savedPosition) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({ x: 0, y: 0 })
    }, 500)
  })
}
```

将其挂载到从页面级别的过渡组件的事件上，令其滚动行为和页面过渡一起良好运行是可能的。但是考虑到用例的多样性和复杂性，我们仅提供这个原始的接口，以支持不同用户场景的具体实现。

## 十八、路由懒加载
当打包构建应用时，Javascript 包会变得非常大，影响页面加载。如果我们能把不同路由对应的组件分割成不同的代码块，然后当路由被访问的时候才加载对应组件，这样就更加高效了。

结合 Vue 的[异步组件](https://cn.vuejs.org/guide/components.html#%E5%BC%82%E6%AD%A5%E7%BB%84%E4%BB%B6)和 Webpack 的[代码分割功能](https://doc.webpack-china.org/guides/code-splitting-async/#require-ensure-/)，轻松实现路由组件的懒加载。

首先，可以将异步组件定义为返回一个 Promise 的工厂函数 (该函数返回的 Promise 应该 resolve 组件本身)：
```javascript
const Foo = () => Promise.resolve({ /* 组件定义对象 */ })
```

第二，在 Webpack 2 中，我们可以使用[动态 import](https://github.com/tc39/proposal-dynamic-import)语法来定义代码分块点 (split point)：
```javascript
import('./Foo.vue') // 返回 Promise
```

如果您使用的是 Babel，你将需要添加 [`syntax-dynamic-import`](https://babeljs.io/docs/plugins/syntax-dynamic-import/) 插件，才能使 Babel 可以正确地解析语法。

结合这两者，这就是如何定义一个能够被 Webpack 自动代码分割的异步组件。
```javascript
const Foo = () => import('./Foo.vue')
```

在路由配置中什么都不需要改变，只需要像往常一样使用 `Foo`：
```javascript
const router = new VueRouter({
  routes: [
    { path: '/foo', component: Foo }
  ]
})
```

### 把组件按组分块

有时候我们想把某个路由下的所有组件都打包在同个异步块 (chunk) 中。只需要使用 [命名 chunk](https://webpack.js.org/guides/code-splitting-require/#chunkname)，一个特殊的注释语法来提供 chunk name (需要 Webpack > 2.4)。
```javascript
const Foo = () => import(/* webpackChunkName: "group-foo" */ './Foo.vue')
const Bar = () => import(/* webpackChunkName: "group-foo" */ './Bar.vue')
const Baz = () => import(/* webpackChunkName: "group-foo" */ './Baz.vue')
```

Webpack 会将任何一个异步模块与相同的块名称组合到相同的异步块中。

### 两个新的生命周期钩子

1. 作用：路由组件所独有的两个钩子，用于捕获路由组件的激活状态。
2. 具体名字：
   1. ```activated```路由组件被激活时触发。
   2. ```deactivated```路由组件失活时触发。



## 十九、综合[只针对vue3]

### `$route`对象

#### 方法1

**route  路由**

![image-20240106223844261](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223844261.png)

**路由视图**

![image-20240106223720567](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223720567.png)

**调用结果**

![image-20240106224329140](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106224329140.png)

#### 方法2

**route  路由**

![image-20240106223825682](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223825682.png)

**路由视图**

![image-20240106224000062](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106224000062.png)

**调用结果**

![image-20240106224152687](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106224152687.png)

### /参数

**route  路由**

![image-20240106221922018](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106221922018.png)

**路由视图**

![image-20240106221854558](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106221854558.png)

**调用结果**

![image-20240106223404276](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223404276.png)

### ?参数

**route  路由**

![image-20240106223253763](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223253763.png)

**路由视图**

![image-20240106223212216](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223212216.png)

**调用结果**

![image-20240106223438136](%F0%9F%93%83%20Vue%20Router%203.assets/image-20240106223438136.png)



