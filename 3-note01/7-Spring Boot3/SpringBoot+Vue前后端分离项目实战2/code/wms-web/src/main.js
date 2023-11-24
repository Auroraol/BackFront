import { createApp } from 'vue'
import App from './App.vue'
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

const app = createApp(App);
// app.use(ElementPlus);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.mount("#app");
