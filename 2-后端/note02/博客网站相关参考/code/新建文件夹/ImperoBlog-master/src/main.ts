import { createApp } from 'vue'
//element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import App from './App.vue'
//加载pinia
import { createPinia } from "pinia";
//加载vue-router
import router from "./router/index";
//加载阿里巴巴矢量图标库
import './iconfont/iconfont.js'
//加载basecss
import './baseCss/base.css'


createApp(App).use(createPinia()).use(router).use(ElementPlus, { locale: zhCn }).mount('#app')
