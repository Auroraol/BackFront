import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import SlideVerify from 'vue-monoplasty-slide-verify';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './assets/elTable/style.css'
import store from './store'
import VueCookies from 'vue-cookies'
import VueClipboards from 'vue-clipboard2'

const app = createApp(App)


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}


app.use(router).use(ElementPlus).use(SlideVerify).use(store).use(VueCookies).use(VueClipboards).mount('#app')
