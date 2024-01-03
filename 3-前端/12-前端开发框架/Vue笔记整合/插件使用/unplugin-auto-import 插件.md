 **Vue自动导入神器 unplugin-auto-import 支持非预设库**

# 使用效果

以 Vue 为例，在没有使用自动导入前，需要手写以下的 `import` 语句：

```js
import { computed, ref } from 'vue'
const count = ref(0)
const doubled = computed(() => count.value * 2)
```

使用 `unplugin-auto-import `插件后：

```js
const count = ref(0)
const doubled = computed(() => count.value * 2)
```

# 配置

```ts
import { defineConfig } from 'vite';
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import vue from '@vitejs/plugin-vue';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';
import * as path from 'path';

export default defineConfig({
  base: './',
  // 配置插件
  plugins: [
    vue(), //vue
    // 引入unplugin-auto-import  【这里】
    AutoImport({
      //引入vue 自动注册api插件
      imports: ['vue', 'vue-router', 'vuex'], // 配置需要自动导入的库
      dts: 'src/auto-import.d.ts' // 自动引入生成api的地址
    }),
    // 引入Element-plus
    AutoImport({
      //plus按需引入
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      //plus按需引入
      resolvers: [ElementPlusResolver()],
      dts: 'src/components.d.ts' //自动引入生成的组件的地址
    })
    // TODO
  ],
  build: {
    minify: 'terser',
    terserOptions: {
      compress: {
        //生产环境时移除console
        drop_console: true,
        drop_debugger: true
      }
    }
  },

  resolve: {
    //配置根路径别名： import('/@/pages/login/login.vue')
    alias: {
      '/@': path.resolve(__dirname, 'src')
    }
  },
  
  // 跨域
  server: {
    //使用IP能访问
    host: '0.0.0.0',
    // 热更新
    hmr: true,
    //自定义代理规则
    proxy: {
      // 选项写法
      '/api': {
        // target: "https://admin.ccc.com",  //代理服务器路径
        // changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, ""),
      }
    }
  }
/*
      proxy: {
      '/api1': {// 匹配所有以 '/api1'开头的请求路径
        target: 'http://localhost:5000',// 代理目标的基础路径  //代理服务器路径
        changeOrigin: true,
        pathRewrite: {'^/api1': ''}   // 前缀,必须写
      },
      '/api2': {// 匹配所有以 '/api2'开头的请求路径
        target: 'http://localhost:5001',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api2': ''}
      }
    }
  //changeOrigin设置为true时，服务器收到的请求头中的host为：localhost:5000
  //changeOrigin设置为false时，服务器收到的请求头中的host为：localhost:8080
  //changeOrigin默认值为true
*/
});
```

参考:  [告别手动引入依赖：unplugin-auto-import 插件](https://juejin.cn/post/7208099384070815803)