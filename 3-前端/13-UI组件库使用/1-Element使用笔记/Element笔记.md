> 需要结合css属性使用, 和bootstrap5不一样

# Element的Container布局容器布满全屏

官方代码运行后效果：

![img](Element%E7%AC%94%E8%AE%B0.assets/792795-20191016113114665-551574879-17007541382132.png)

实现不满全屏：

1、需要给包裹的div一个height：100%

2、给#app,html,body,.el-container一个height：100%

3、给el-container设置direction="vertical"，因为包含main和footer

```vue
<template>
<div style="height: 100%">
  .......  
    
</div>
</template>
    
<style type="text/css">
/*
	找到html标签、body标签，和挂载的标签
	都给他们统一设置样式
*/
  html,body,#app,.el-container{
        /*设置内部填充为0，几个布局元素之间没有间距*/
        padding: 0px;
         /*外部间距也是如此设置*/
        margin: 0px;
        /*统一设置高度为100%*/
        height: 100%;
    }
</style>
```

实现之后的效果图如图：

![img](Element%E7%AC%94%E8%AE%B0.assets/792795-20191016113129326-1013135056.png)



**补充方法**

 在全局样式中添加以下样式：

style.css

```
//设置body和html的高度为100%：
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}
```

然后，在组件页面样式中，设置 `根div` 的高度为100%：

```
根div {
  height: 100%;
}
```

# 输入框

默认 占满一行,通过width改变所占大小

![image-20231127184420389](Element%E7%AC%94%E8%AE%B0.assets/image-20231127184420389.png)





# 分页组件el-pagination显示英文转变为中文

![image-20231127195410463](Element%E7%AC%94%E8%AE%B0.assets/image-20231127195410463.png)

```
如果你的element-plus版本为2.2.29以下的

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import 'element-plus/dist/index.css'
 
const app= createApp(App)
app.use(ElementPlus, {locale})
app.mount('#app')

```



```
如果你的element-plus版本为2.2.29以上的

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import zhCn from "element-plus/es/locale/lang/zh-cn";
import 'element-plus/dist/index.css'
 
const app= createApp(App)
app.use(ElementPlus, {locale})
app.mount('#app')

```





```vue
<template>
  <div>
    <el-button mb-2 @click="toggle">Switch Language</el-button>
    <br />

    <el-config-provider :locale="locale">
      <el-table mb-1 :data="[]" /> //表
      <el-pagination               //分页     
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-config-provider>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'

const language = ref('zh-cn')
const locale = computed(() => (language.value === 'zh-cn' ? zhCn : en))

const toggle = () => {
  language.value = language.value === 'zh-cn' ? 'en' : 'zh-cn'
}
</script>
```

![image-20231127200838853](Element%E7%AC%94%E8%AE%B0.assets/image-20231127200838853.png)

![image-20231127200845958](Element%E7%AC%94%E8%AE%B0.assets/image-20231127200845958.png)



# 导航

![image-20231204134054875](Element%E7%AC%94%E8%AE%B0.assets/image-20231204134054875.png)







# TODO

![image-20231204143711244](Element%E7%AC%94%E8%AE%B0.assets/image-20231204143711244.png)

0: 表示该菜单只有管理员能看见

0,1: 表示该菜单用户和管理员都能看见

![image-20231204152828089](Element%E7%AC%94%E8%AE%B0.assets/image-20231204152828089.png)







![image-20231204152510726](Element%E7%AC%94%E8%AE%B0.assets/image-20231204152510726.png)



![image-20231204152439441](Element%E7%AC%94%E8%AE%B0.assets/image-20231204152439441.png)



![image-20231204152419279](Element%E7%AC%94%E8%AE%B0.assets/image-20231204152419279.png)



![image-20231204152347839](Element%E7%AC%94%E8%AE%B0.assets/image-20231204152347839.png)



出入库管理

![image-20231204154223689](Element%E7%AC%94%E8%AE%B0.assets/image-20231204154223689.png)



![image-20231204154931868](Element%E7%AC%94%E8%AE%B0.assets/image-20231204154931868.png)



出⼊库权限控制

![image-20231204154315921](Element%E7%AC%94%E8%AE%B0.assets/image-20231204154315921.png)

0 超级管理员

1 管理员

2 用户

![](Element%E7%AC%94%E8%AE%B0.assets/image-20231204154519254.png)



记录查询权限控制

![image-20231204155448266](Element%E7%AC%94%E8%AE%B0.assets/image-20231204155448266.png)

![image-20231204155455530](Element%E7%AC%94%E8%AE%B0.assets/image-20231204155455530.png)











![image-20231204155611891](Element%E7%AC%94%E8%AE%B0.assets/image-20231204155611891.png)![image-20231204160032978](Element%E7%AC%94%E8%AE%B0.assets/image-20231204160032978.png)



![image-20231204160304438](Element%E7%AC%94%E8%AE%B0.assets/image-20231204160304438.png)







# 动态路由问题

![image-20231204162701262](Element%E7%AC%94%E8%AE%B0.assets/image-20231204162701262.png)

![image-20231204162729435](Element%E7%AC%94%E8%AE%B0.assets/image-20231204162729435.png)![image-20231204162753002](Element%E7%AC%94%E8%AE%B0.assets/image-20231204162753002.png)



![image-20231204162921425](Element%E7%AC%94%E8%AE%B0.assets/image-20231204163048454.png)

![image-20231204163331847](Element%E7%AC%94%E8%AE%B0.assets/image-20231204163331847.png)





