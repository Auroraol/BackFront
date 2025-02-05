# [官网](https://element-plus.org/zh-CN/component/button.html)

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

# 布局使用

<iframe height="300" style="width: 100%;" scrolling="no" title="Untitled" src="https://codepen.io/Auroraol/embed/OJGPBXm?default-tab=html%2Cresult" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true">
  See the Pen <a href="https://codepen.io/Auroraol/pen/OJGPBXm">
  Untitled</a> by Aurora  (<a href="https://codepen.io/Auroraol">@Auroraol</a>)
  on <a href="https://codepen.io">CodePen</a>.
</iframe>

```vue
<script src="//unpkg.com/vue@2/dist/vue.js"></script>
<script src="//unpkg.com/element-ui@2.15.14/lib/index.js"></script>
<div id="app">
<template>
<!-- 头部 -->
<div class="content">
<el-row style="margin-bottom: 0;">
<el-col :span="24">
<div class="top bg-purple-dark"></div>
</el-col>
</el-row>
</div>
<!-- 中间 -->
<div class="card">
<el-row>
<el-col :span="12">
<div class="double bg-purple"></div>
</el-col>
<el-col :span="12">
<div class="double bg-purple-light"></div>
</el-col>
</el-row>
<el-row>
  
<el-col :span="8">
<div class="card3 bg-purple" style="margin-right: 36px;"> </div>
</el-col>
<el-col :span="8">
<div class="card3 bg-purple-light" style="margin-right: 36px;"></div>
</el-col>
<el-col :span="8">
<div class="card3 bg-purple"></div>
</el-col>
</el-row>
</div>
<!-- 尾部 -->
<div class="end">
<el-row style="margin-bottom: 0;">
<el-col :span="24">
<div class="end bg-purple-dark"></div>
</el-col>
</el-row>
</div>

</template>
 </div>
```

```
@import url("//unpkg.com/element-ui@2.15.14/lib/theme-chalk/index.css");
.el-row {
margin-bottom: 20px;
&:last-child {
margin-bottom: 0;
}
}
.el-col {
border-radius: 4px;
}
.card{
margin-top: 20px;
margin-left: 70px;
margin-right: 70px;
}
.double {
border-radius: 4px;
min-height: 50px;
}
.card3 {
border-radius: 4px;
min-height: 490px;
}
.top, .end {
border-radius: 4px;
min-height: 50px;
}
.bg-purple-dark {
background: #99a9bf;
}
.bg-purple {
background: #d3dce6;
}
.bg-purple-light {
background: #e5e9f2;
}
.row-bg {
padding: 10px 0;
background-color: #f9fafc;
}
```

![img](Element%E7%AC%94%E8%AE%B0.assets/640.png)

# Layout布局

## 一、el-row和el-col可以很好的控制块级、行级布局，建议：在div中嵌套el-row。el-row和div

类似，div的很多css属性在el-row中都可以使用，el-col则不一定。

## 二、el-col之间的间距问题：

### 1、el-col之间设置间距有两种方法：

####  1）使用el-row自带的gutter：gutter生成的间距如果长度不够会外扩，如：

```cobol
 <el-row>
                  <el-col :span="6" class="col">无间距第一个</el-col>
                  <el-col :span="6" class="col">无间距第二个</el-col>
                  <el-col :span="6" class="col">无间距第三个</el-col>
                  <el-col :span="6" class="col">无间距第四个</el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="6" class="col">第一个</el-col>
                  <el-col :span="6" class="col">第二个</el-col>
                  <el-col :span="6" class="col">第三个</el-col>
                  <el-col :span="6" class="col">第四个</el-col>
                </el-row>
 
 
 
 .col {
            border: 1px solid black;
          }
```

![img](Element%E7%AC%94%E8%AE%B0.assets/20210114153935596.png)

####  2）css设置边距：而css生成的间距长度不够会换行，如：

```cobol
 <el-row>
                  <el-col :span="6" class="col">无间距第一个</el-col>
                  <el-col :span="6" class="col">无间距第二个</el-col>
                  <el-col :span="6" class="col">无间距第三个</el-col>
                  <el-col :span="6" class="col">无间距第四个</el-col>
                </el-row>
               
                <el-row>
                  <el-col :span="6" class="col" style="margin-left:20px">第一个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第二个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第三个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第四个</el-col>
                </el-row>
 
 
 .col {
            border: 1px solid black;
          }
```

![img](Element%E7%AC%94%E8%AE%B0.assets/2021011415445460.png)

### 2、间距对齐方式

将 `type` 属性赋值为 'flex'，可以启用 flex 布局，并可通过 `justify` 属性来指定 start, center, end, space-between, space-around 其中的值来定义子元素的排版方式。

下面以space-between对齐方式为例：

#### 1）gutter的间距：仍是超出：

```cobol
 <el-row>
                  <el-col :span="6" class="col">无间距第一个</el-col>
                  <el-col :span="6" class="col">无间距第二个</el-col>
                  <el-col :span="6" class="col">无间距第三个</el-col>
                  <el-col :span="6" class="col">无间距第四个</el-col>
                </el-row>
                <el-row :gutter="20" type="flex" justify="space-between">
                  <el-col :span="6" class="col">第一个</el-col>
                  <el-col :span="6" class="col">第二个</el-col>
                  <el-col :span="6" class="col">第三个</el-col>
                  <el-col :span="6" class="col">第四个</el-col>
                </el-row>
```

![img](Element%E7%AC%94%E8%AE%B0.assets/20210114155547886.png)

#### 2）css的间距：不换行了：

```cobol
 <el-row type="flex" justify="space-between">
                  <el-col :span="6" class="col" style="margin-left:20px">第一个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第二个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第三个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第四个</el-col>
                </el-row>
```

![img](Element%E7%AC%94%E8%AE%B0.assets/20210114155653264.png)

再把第一个col的左间距去掉

```cobol
 <el-row type="flex" justify="space-between">
                  <el-col :span="6" class="col">第一个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第二个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第三个</el-col>
                  <el-col :span="6" class="col" style="margin-left:20px">第四个</el-col>
                </el-row>
```

![img](Element%E7%AC%94%E8%AE%B0.assets/20210114155804292.png)

## element el-col内元素对齐方式

```vim
<el-col :span="8" :align="left">
  <!-- 左对齐的内容 -->
</el-col>
<el-col :span="8" :align="center">
  <!-- 居中对齐的内容 -->
</el-col>
<el-col :span="8" :align="right">
  <!-- 右对齐的内容 -->
</el-col>
```

# 输入框

默认 占满一行,通过width改变所占大小

![image-20231127184420389](Element%E7%AC%94%E8%AE%B0.assets/image-20231127184420389.png)



例子

![image-20240601205359736](Element%E7%AC%94%E8%AE%B0.assets/image-20240601205359736.png)

```vue
  <el-input
        v-model="chatLogIpt"
        :autosize="{ minRows: 2, maxRows: 6 }"
        :input-style="{ borderRadius: '1rem', flex: '1', height: '100px' }"
        resize="none"
        :maxlength="1000"
        :show-word-limit="true"
        type="textarea"
        placeholder="发送消息"
        @keydown="sendKeyDown"
      />

<script>
//键盘enter发送消息
const sendKeyDown = (e: any) => {
  //按 Ctrl + Enter
  if (e.ctrlKey && e.key === "Enter") {
    sendChatLog();
  }

  // 按Enter
  //   if (e.key === "Enter") {
  //     e.preventDefault(); //阻止默认的换行行为
  //   }
};
</script>
```

`:autosize="{ minRows: 2, maxRows: 6 }"` 是 Element UI 中 `<el-input>` 组件的一个属性，用于设置文本框自动调整高度的行数范围。

- `minRows`: 表示文本框的最小行数。
- `maxRows`: 表示文本框的最大行数。当输入内容超过最大行数时，文本框会出现滚动条

`resize="none"` 是 `<el-input>` 组件的一个属性，用于禁止用户调整文本框的大小。设置 `resize="none"` 后，用户将无法通过拖动文本框边缘来改变文本框的大小。

##  #prepend  #suffix 的使用

```html
    <div class="input-div">
        <el-input v-model="input" class="input-class" size="large" placeholder="Please Input">
            <template #prepend>
                <el-button :icon="Search" />
            </template>
            <template #append>
                <span style="cursor: pointer;">搜索</span>
            </template>
        </el-input>
    </div>
```

```css
<style lang="less">
.input-div {
    width: 500px;
    border-radius: 95px;
    background-color: #fff;
    border-radius: 95px;
    margin-left: 33%;
    margin-top: 2%;
}
 
.input-class {
 
    .el-input-group__prepend {
        border-radius: 95px;
        border: 0;
        box-shadow: 0 0 0 0px;
    }
 
    .el-input__wrapper {
        border-radius: 95px;
        border: 0;
        box-shadow: 0 0 0 0px;
    }
 
    .el-input-group__append {
        border-radius: 95px;
        border: 0;
        box-shadow: 0 0 0 0px;
    }
}
</style>
```



![image-20240308221138040](Element%E7%AC%94%E8%AE%B0.assets/image-20240308221138040.png)

## el-input后面加文字

> 效果图

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/16cf945e0e824e3d9b197706a3987c93.png)

> 代码

```javascript
// 添加代码即可
<i slot="suffix" style="font-style:normal;margin-right: 10px;">分钟</i>
12
// 整个代码
  <el-form-item label="迟到时长" prop="laterTime">
      <el-input
         v-model="temp.laterTime"
       >
         <i slot="suffix" style="font-style:normal;margin-right: 10px;">分钟</i>
      </el-input>
   </el-form-item>
```

```html
<el-input
          placeholder="请输入验证码"
          v-model="loginFormPhone.code"
          inline-message
          >
    <template #suffix>
        <i style="font-style: normal; margin-right: 10px;">获取验证码</i>
    </template>
</el-input>
```

![image-20240328203104800](Element%E7%AC%94%E8%AE%B0.assets/image-20240328203104800.png)

## 显示眼睛

 使用 show-password 属性

![image-20240403171558115](Element%E7%AC%94%E8%AE%B0.assets/image-20240403171558115.png)

```
          <el-input
            size="large"
            show-password
            v-model="form.password"
            placeholder="密码至少6位数"
          />
```

## 标题 + 输入框

```
        <div class="title">
            <label for="title">标题:</label>
            <el-input @input="saveEditor" maxlength="30" v-model="articleWrite.articleTitle" placeholder="输入文章标题"
                show-word-limit type="text" name="title" />
        </div>
        
        
    .title {
        display: flex;
        align-items: center;
        margin: 1rem 0;


        label {
            width: 5rem;
        }

        input {
            width: 10%;
        }
    }
```

## 输入框大小

width: 240px

```
<template>
  <el-input v-model="input" style="width: 240px" placeholder="Please input" />
</template>
```

## 字数限制

maxlength="10" show-word-limit 

```
        <el-input class="input"  maxlength="10" show-word-limit  v-model="title" placeholder="输入文章标题" />

```

![image-20240404214550169](Element%E7%AC%94%E8%AE%B0.assets/image-20240404214550169.png)

## 回车搜索

```ts
<el-input
    style="width: 350px; padding: 0 70px"
    v-model="keyword"
    placeholder="搜索文章"
    @focus="inputFocus"
    @blur="inputBlur"
    @keyup.enter.native="search"
    class="search"
    suffix-icon="el-icon-search"
></el-input>

            
            // 搜索框聚焦
const inputFocus = () => {
  inputIconColor.value = "#1989fa";
};

// 搜索框失焦
const inputBlur = () => {
  inputIconColor.value = "";
};

// 搜索
const search = () => {
  const keywordValue = keyword.value;
  if (keywordValue) {
    router.push({
      path: "/search",
      query: { keyword: keywordValue },
    });
  }
};
```







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

## 使用

![image-20231204134054875](Element%E7%AC%94%E8%AE%B0.assets/image-20231204134054875.png)



```ts
  <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"    
          background-color="transparent"
          active-text-color="#79bbff"
        >
          <el-menu-item index="/index">
            <svg v-if="!isMobile" class="icon" aria-hidden="true">
              <use xlink:href="#icon-shouye1"></use>
            </svg>
            首页
          </el-menu-item>
 </el-menu>
 
 
 
const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === "/xxx") {
    // 某个页面
  } 
  else {
    router.push({ path: `${index}` });
  }
};
```



```ts

<el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="transparent"
            active-text-color="#79bbff"
          >
<el-menu-item index="/index" style="margin-left: 130px">
              <el-icon><House /></el-icon> 首页
            </el-menu-item>
            <el-menu-item index="/category">
              <el-icon><document /></el-icon>
              分类
            </el-menu-item>
          
const activeIndex = ref("1");

const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === "info") {
    // 某个页面
  } else {
    router.push({ path: `${index}` });
  }
};

```

或者不使用 @select="handleSelect"  用    :router="true"

1. 在el-menu加上router
2. index必须绑定路由的path, 或通过这个跳转
3. default-active设为当前路由（this.$route.path）,这样在路由变化的时候，对应的menu-item才会高亮，如：
   ![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/2a4c4467fd9f4ba98b9c00f39591ed6b.png#pic_center)

## 样式

[Elementui中El-menu导航栏-CSDN博客](https://blog.csdn.net/qq_40323256/article/details/125212606?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-125212606-blog-124561084.235^v43^pc_blog_bottom_relevance_base1&spm=1001.2101.3001.4242.1&utm_relevant_index=3)

![img](Element%E7%AC%94%E8%AE%B0.assets/1743197-20191129150112233-1280345805.png)

 

 本来是这样，有下划线有点击背景，但是业务需求不需要，就想办法进行隐藏，控制台可以观察效果找出相应的类进行格式书写

以下效果：

![img](Element%E7%AC%94%E8%AE%B0.assets/1743197-20191129150205598-1247540266.png)

```
<style>
.el-menu-demo{
    height: 40px;
    /* border-bottom-color:none!important; */
    background-color:rgba(0,0,0,0);
    float:right;
    margin-right: 8.54%;

}
/* 点击出来的下划线进行隐藏 */
.el-menu-item.is-active {
    border-bottom: none!important;
    color: rgba(0,0,0,0);
}
/* 整体的下划线进行隐藏 */
.el-menu.el-menu--horizontal{
  border-bottom: none;
}
/* 导航栏的间距等样式 */
.el-menu-item {
  padding: 0 62px;
  font-size: 16px;
}

/* 点击出来的下划线动效残留进行隐藏 */
.el-menu--horizontal>.el-menu-item{
    border-bottom: none;
}
/* 点击以后的背景色进行隐藏 */
.el-menu--horizontal>.el-menu-item:not(.is-disabled):focus, .el-menu--horizontal>.el-menu-item:not(.is-disabled):hover, .el-menu--horizontal>.el-submenu .el-submenu__title:hover {
    background-color: rgba(0,0,0,0);
}
</style>
```

更改element-ui中NavMenu导航栏中的hover背景色和字体色及边框线

```
// 背景透明
.el-menu-demo {
    background: transparent;
}
 
// 去除自带的底部border
.el-menu.el-menu--horizontal {
    border: none;
}
 
// 更改鼠标划过背景色字体色及borer显示
.el-menu--horizontal>>>.el-menu-item:not(.is-disabled):hover {
background:transparent !important;
color: #fff;
border-bottom: 1px solid #fff !important;
}
```

## Element -Ui之NavMenu导航栏

### 一、NavMenu重要标签和参数说明

- mode：[导航栏](https://so.csdn.net/so/search?q=导航栏&spm=1001.2101.3001.7020)的模式，默认值vertical 导航栏处于垂直状态，改为horizonta可变为水平模式。
- index：唯一标志。
- default-active：激活当前菜单的index。 和唯一标志结合
-   active-text-color="#31c27c"  点击后文字颜色
- router： 是否使用路由，启用后可以通过index作为地址进行跳转。
- <[el-menu](https://so.csdn.net/so/search?q=el-menu&spm=1001.2101.3001.7020)-item index="1">导航栏中的子菜单标签。
- `<el-submenu>` 生成二级菜单。
-  :collapse="isCollapse"  是否折叠
-  @open="handleOpen" 选择的哪一项

代码实现：

```html
<template>
<el-menu
      :default-active="2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      text-color="black"
      background-color="white"
      active-text-color="#31c27c"
      router
    >
    <el-menu-item index="1">一</el-menu-item>
    <el-menu-item index="2">二</el-menu-item>
    <el-menu-item index="3">三</el-menu-item>
    <!-- 添加二级菜单 -->
    <el-submenu index="4"> 
        <template slot="title">四</template>
        <el-menu-item index="4-1">4.1</el-menu-item>
        <el-menu-item index="4-2">4.2</el-menu-item>
        <el-menu-item index="4-3">4.3</el-menu-item>
    </el-submenu>
    </el-menu>
</template>
```



<img src="Element%E7%AC%94%E8%AE%B0.assets/image-20240425122016346.png" alt="image-20240425122016346" style="zoom:50%;" />

```vue
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><location /></el-icon>
            <span>Navigator One</span>
          </template>
          <el-menu-item-group title="Group One">
            <el-menu-item index="1-1">item one</el-menu-item>
            <el-menu-item index="1-2">item two</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="Group Two">
            <el-menu-item index="1-3">item three</el-menu-item>
          </el-menu-item-group>
          <el-sub-menu index="1-4">
            <template #title>item four</template>
            <el-menu-item index="1-4-1">item one</el-menu-item>
          </el-sub-menu>
        </el-sub-menu>
   <>
```

` <el-sub-menu>`    一级

`<el-menu-item >` 内容

 `<el-sub-menu >`二级



### 二、导航菜单中的router用法

router[默认值](https://so.csdn.net/so/search?q=默认值&spm=1001.2101.3001.7020)为false需要激活来启用。

```html
<el-menu router="true"></el-menu>
```

其中，index的值作为地址进行跳转。

案例：

```html
<template>
  <div>
    <el-menu
      :default-active="path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      text-color="black"
      background-color="white"
      active-text-color="#31c27c"
      router
    >
       <el-menu-item>
        <img
          src=""
          alt=""
        />
      </el-menu-item>
        <!-- 使用v-for循环添加子菜单 -->
      <template v-for="(item, index) in nav_menu_data">
        <el-menu-item :index="item.path" :key="index">
          {{ item.title }}
        </el-menu-item>
      </template> 
 
      <!-- 登录与退出按钮 -->
      <!-- 从服务端搜索数据 -->
      <el-autocomplete
        v-model="state"
        :fetch-suggestions="querySearchAsync"
        placeholder="搜索音乐、歌单、MV"
        @select="handleSelect"
        class="search"
        suffix-icon="el-icon-search"
      ></el-autocomplete>
      <el-button type="text" class="btn_login">登录</el-button>
    </el-menu>
</div>
</template>
 
<script>
  export default {
  name: "NavMenu",
  data() {
    return {
      path: "",
      nav_menu_data: [
        {
          title: "发现音乐",
          path: "/home/faxian",
        },
        {
          title: "我的音乐",
          path: "/Menu/Page2",
        },
        {
          title: "关注",
          path: "/home/guanzhu",
        },
        {
          title: "分类",
          path: "/Menu/Page4",
        },
      ],
      activeIndex: "1",
    };
  },
  created() {
    this.onRouteChanged();
  },
  watch: {
    $route: "onRouteChanged",
  },
  methods: {
    onRouteChanged() {
      let that = this;
      that.path = that.$route.path;
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>
<style lang="less" scoped>
</style>
```

效果图：

![img](Element%E7%AC%94%E8%AE%B0.assets/1e5e33950ead4f0096f1acf0a6e9df41.png)







1.组件需要先写在<[el-menu](https://so.csdn.net/so/search?q=el-menu&spm=1001.2101.3001.7020)></el-menu>里面

2.一点此单就能显示东西或进入路由的就用

<el-menu-item></el-menu-item>实现

3.点了展开下一级菜单的要写在

<el-submenu>

<template slot=‘title’>标题文本</template>

<el-menu-item></el-menu-item>

</el-submenu>

当el-menu设置router属性时，需要在el-menu-item和el-sunmenu里面写index

index的值必须是字符串，所以在

```
<el-submenu :index="index.toString()">这一句，需要把它转化成字符串
```

常用是属性

 background-color：设置导航菜单的背景颜色（默认值：#ffffff），可以取值为transparent（透明）。

  text-color：设置导航菜单的文本颜色。

  mode：设置导航菜单的摆放方式，默认值：vertical（垂直的），还可以取值为horizontal（水平的）。

  router：设置是否开启[路由模式](https://so.csdn.net/so/search?q=路由模式&spm=1001.2101.3001.7020)，若开启该模式，则<el-menu-item>的index属性需要指定路由地址。

  default-active：设置默认被激活的路由。

  collapse-transition：设置子菜单是否具备折叠动画。

  active-text-color：设置当前激活菜单项的文本颜色。

  unique-opened：设置是否只允许有一个子菜单处于展开状态。

有关导航菜单的CSS样式类：

  （1）去掉垂直导航菜单右侧默认的竖线：若是水平方式放置的导航栏菜单是下方默认的横线。

​     .el-menu{ border-right:none; }

  （2）鼠标经过菜单项：

​     .el-menu-item:hover

  （3）鼠标经过子菜单的标题文本：

​     \>>.el-submenu__title:hover（这里是两个下划线）

  （4）当前被激活菜单项：

​     .el-menu-item.is-active

  （5）子菜单中的菜单项：

​     .el-submenu .el-menu-item

4.如果动态生成菜单项

```javascript
<template>
  <div class="myMenu">
    <el-menu
        background-color="transparent"
        text-color="#ffffff"
        router
        active-text-color="#ffffff"
    >
      <template v-for="(item,index) in menuData">
        <template v-if="item.children">
          <el-submenu :index="index.toString()">
              <template slot="title"><i :class="item.icon"></i>{{item.permissionName}}</template>
            <template v-for="(child,index2) in item.children">
                <el-menu-item :index="`/successPage/${child.path}`">{{child.permissionName}}</el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="`/successPage/${item.path}`"><i :class="item.icon"></i>{{item.permissionName}}</el-menu-item>
        </template>
      </template>
 
    </el-menu>
  </div>
</template>
 
<script>
/*
mapState里面的内容
export default {
    userInfo:{}
}
multations里面的内容
export default {
    setUserInfo(state,payload){
        state.userInfo=payload
        state.userInfo.expiresTime=new Date().getTime()+payload.expires*1000
    }
}
* */
import {mapState} from 'vuex'
export default {
  name: "myMenu",
  data(){
    return{
      menuData:[]
    }
  },
  computed:{
    ...mapState(['userInfo'])
  },
  mounted(){
    let url='/api/getRouter.jsp';
    let data={
      params:{
        permissionIds:this.userInfo.permission
      }
    }
    this.$axios.get(url,data).then(res=>{
      if (res.data.code===200){
        this.menuData=res.data.data
      }
    })
  }
}
</script>
 
<style scoped>
.el-menu{
  border-right:none
}
>>>.el-submenu__title{
  color:#ffffff;
}
.el-menu-item.is-active,
>>>.el-submenu__title:hover,
.el-menu-item:hover{
  background-color: #197199 !important;
}
.el-submenu .el-menu-item{
  text-indent: 20px;
}
</style>
```

userInfo的数据 

### 侧边栏

![image-20240318164236789](Element%E7%AC%94%E8%AE%B0.assets/image-20240318164236789.png)

isCollapse控制打开或者关闭

   active-text-color="#ffd04b"    文字颜色

background-color="#545c64"  背景颜色

```vue
<template>
    <el-menu
      default-active="2"
      class="el-menu-vertical"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose"
    >
    <el-menu-item index="1" class="titleName">
          <el-icon><Management /></el-icon>
          <template #title>图书借阅管理</template>
    </el-menu-item>
 
      <el-sub-menu index="2" class="menu">
        <template #title>
          <el-icon><Management /></el-icon>
          <span>图书信息维护</span>
        </template>
          <el-menu-item index="1-1" class="menu" @click="$emit('addTab','LibrarySortEnter','图书分类录入')">图书分类录入</el-menu-item>
          <el-menu-item index="1-2" class="menu" @click="$emit('addTab','StorePositionEnter','存放位置录入')">存放位置录入</el-menu-item>
          <el-menu-item index="1-3" class="menu">
<!--这里使用<router-link>，通过属性to来进行跳转，里面的name就是刚才在router.js文件中设置的，params这里包含的是路由跳转时传递的参数-->
          <router-link
            :to="{
              name: 'projectGroup:alg',
              params: { id: i.id, name: i.name },
            }"
            >图书录入</router-link>
          </el-menu-item>
      </el-sub-menu>
 
      <el-sub-menu index="3" class="menu">
        <template #title>
          <el-icon><Management /></el-icon>
          <span>会员信息维护</span>
        </template>
          <el-menu-item index="2-1" class="menu">
               <router-link to="/vip">会员表</router-link></el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="4" class="menu">
        <template #title>
          <el-icon><Management /></el-icon>
          <span>图书信息维护</span>
        </template>
          <el-menu-item index="3-1" class="menu">图书借阅单</el-menu-item>
          <el-menu-item index="3-2" class="menu">借阅明细</el-menu-item>
          <el-menu-item index="3-3" class="menu">图书归还单</el-menu-item>
      </el-sub-menu>
 
 
 
    </el-menu>
  </template>
  
  <script setup>
  import { ref,defineExpose } from 'vue'
  import { Management } from '@element-plus/icons-vue'
 
   const isCollapse = ref(false)
  
   const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
  }
   const handleClose = (key, keyPath) => {
    console.log(key, keyPath);
  }
 
  defineExpose({
    isCollapse,
})
  </script>
  
  <style scoped>
  .el-menu-vertical,.el-menu-vertical:not(.el-menu--collapse){
    height: 100vh;
  }
  .titleName{
    font-size: 24px;
    font-weight: 600;
  }
  .menu{
  font-size: 14px;
 
  }
  </style>
```

### element-Plus中el-menu菜单无法正常收缩解决方案

**原因如下图，在把菜单menu动态封装为组件时，新建的文件中，会自动生成div标签，在不记得删除的情况下就会导致层级嵌套错误，无法正常收缩，移除掉div标签后即可正常收缩**

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/98724896cba4443490ec125fd8233366.png)
正常收缩
![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/1511a1d32b6640009724ea7ed349987a.png)





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





# 字体

```
  color: red;
   font-weight: bold;
```

例子

```html
<div class="title">
    <div class="poetry">
        <span>{{ poetry.content }}</span>
        &nbsp;&nbsp;
        <span>{{ `——${poetry.author}《${poetry.origin}》` }}</span>
    </div>
</div>
  
<style>
  .title {
        display: flex;
        align-items: center;
        justify-content: center;
        box-sizing: border-box;
        padding: 1rem;
        background-color: #ffffff;
        min-height: 5rem;
        border-radius: .8rem;
        color: var(--special-font-color);
        font-weight: 700;
        font-size: 1.5rem;
        margin-bottom: 1rem;
    }
 </style>
```

效果:![image-20240304222414595](Element%E7%AC%94%E8%AE%B0.assets/image-20240304222414595.png)(Element%E7%AC%94%E8%AE%B0.assets/image-20240304222230786.png)

# elementUI的button按钮怎么换字体颜色

时间: 2023-10-26 07:27:24 浏览: 186

要修改Element UI的button按钮的字体颜色，可以使用以下CSS样式：

```css
.el-button {
  color: #fff; /* 设置字体颜色为白色 */
}
```

这将会将所有类型的按钮的字体颜色都设置为白色。如果只想修改特定类型的按钮的字体颜色，可以只在类名后面加上对应的类名，例如 `.el-button--default` 只修改默认类型按钮的字体颜色。

相关问题

## elementui显示字体

如果您想在 elementui 中更改字体，可以使用以下 CSS 样式：

```css
.element-* {
  font-family: 'Your Font', sans-serif;
}
```

其中，`*` 代表所需更改的组件名称，`Your Font` 代表您想要使用的字体名称。请确保您已经在项目中引入了所需字体。

例如，如果您想在按钮上使用自定义字体，可以这样写：

```css
.element-button {
  font-family: 'Your Font', sans-serif;
}
```

## elementui如何调整字体

Element UI 的字体可以通过修改全局样式来实现。你可以在项目的样式文件中添加以下代码：

```css
/* 修改 Element UI 的默认字体 */
body {
  font-family: 'Your Font', sans-serif;
}
```

将 `Your Font` 替换为你想要使用的字体名称即可。如果你只想修改某个组件的字体，可以在该组件的样式中添加对应的 `font-family` 属性。例如：

```css
.el-button {
  font-family: 'Your Font', sans-serif;
}
```

这将会修改所有的按钮组件的字体。





# 解决闪屏

> 下述内容可以搭配使用

## 动画

[GSAP 中文教程 中文文档 ｜官方文档 官方教程翻译 ｜好奇代码出品](https://gsap.framer.wiki/)

```ts
import gsap from 'gsap'

const router = useRouter()

onMounted(() => {
    //动画
    gsap.from('.articleLeft', {  //.articleLeft选择器
        duration: 0.5,
        x: 50,  //右向左移动  -50左向右移动
        opacity: 0.2  //透明度
    })
})
```

这段代码使用了GSAP动画库中的from方法，它会从指定的初始状态（在这里是x轴偏移50个单位，不透明度为0.2）开始，然后在0.5秒的持续时间内将元素动画到其初始状态。这段代码的作用是对类名为articleLeft的元素应用一个从右向左移动并逐渐显现的动画效果。

```ts
// 定义一个名为gsapAnimation的函数
const gsapAnimation = () => {
    // 创建一个GSAP动画序列
    const tween = gsap.timeline()
    
    // 从初始状态开始动画'.hedaImg'元素，放大并使用弹性缓动效果
    tween.from('.hedaImg', {
        scale: 1.5,
        ease: "elastic.out(1.5,0.2)",
        duration: 1.5
    })
    
    // 继续动画'.hedaImg'元素，向下移动200像素，持续时间为0.3秒
    .from('.hedaImg', {
        y: 200,
        duration: 0.3
    })
    
    // 动画'.head h1'元素，向下移动20像素，同时淡入显示
    .from('.head h1', {
        y: 20,
        opacity: 0,
    })
    
    // 动画'.info p'元素，向下移动20像素，逐个显示并淡入
    .from('.info p', {
        y: 20,
        stagger: 0.1,
        opacity: 0,
    })
    
    // 动画'.bottom'元素，向下移动20像素，同时淡入显示，延迟0.3秒
    .from('.bottom', {
        y: 20,
        opacity: 0,
        delay: 0.3
    })
}
```



```
//这里是文章顶部的编辑按钮，点击展开下面的抽屉
let showEditorBox = false
const expandEdiorBox = () => {
    showEditorBox = !showEditorBox
    if (showEditorBox === true) {
        gsap.to('.ediorBox', {
            duration: .3,
            height: 80
        })
    } else {
        gsap.to('.ediorBox', {
            duration: .3,
            height: 0
        })
    }
}
```



```
const containerGsap = () => {
    gsap.from('.contaier', {
        y: 50,
        duration: 0.3
    })
}

const gsapEnterCover = (el: any, done: any) => {
    gsap.from(el, {
        y: -50,
        duration: 0.3
    })
    done()
}

onMounted(() => {
    containerGsap()
    }
```

## 过渡

例子1

```css

<style lang="less" scoped>
.container {
  width: 100%;
  background: #fff;
  margin-bottom: 10px;
  padding-bottom: 5px;
  color: #2e3135;
  position: relative;
  margin-top: 2rem;

  border-radius: 0.5rem;
  transition: all 0.3s;
}
```

```
将 CSS 的过渡效果应用于所有属性的变化， 这行 CSS 代码正是用来实现这个效果的。它告诉浏览器在任何 CSS 属性发生变化时都要应用一个持续时间为 0.3 秒的过渡效果。这在让网页元素的变化更加平滑和自然时非常有用
```

例子2

```vue
<template>
  <el-row>
    <el-col :span="2" class="grid-content1">
      <transition name="el-fade-in">
        <BrowserSidePanel
          class="browserSide"
          :id="articleId"
        ></BrowserSidePanel>
      </transition>
    </el-col>
    <el-col :span="16">
      <!-- 使用 -->
      <transition name="el-fade-in">
        <div v-if="!loading" class="layout-left-side container">
          <!-- 这里是你的其他内容 -->
        </div>
      </transition>
    </el-col>
    <el-col :span="6" class="ep-bg-purple">
      <transition name="el-fade-in">
        <div class="grid-content" />
      </transition>
      <el-affix offset="60">
        <!-- 这里是你的其他内容 -->
      </el-affix>
    </el-col>
  </el-row>
</template>


<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
```

![1712891542844](Element%E7%AC%94%E8%AE%B0.assets/1712891542844.gif)





```
       <!-- 使用 transition 组件包裹 router-view -->
      <transition name="fade">
        <!-- 路由 -->
        <router-view />  
      </transition>
```



```
/* 淡入淡出的过渡效果*/
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active 在 <2.1.8 版本中 */ {
  opacity: 0;
}
```



```vue
<template>
  <div>
    <transition name="fade">
      <div v-if="!loading" class="container">内容</div>
    </transition>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: true // 根据实际情况设置 loading 状态
    };
  }
};
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
```



# 使用阿里巴巴图标库

![image-20240304135709257](Element%E7%AC%94%E8%AE%B0.assets/image-20240304135709257.png)

```
/* //阿里巴巴图标库必需的css */
.icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
```





# 居中

```
margin: 0 auto;
 width: 80%;
```

![image-20240410113019779](Element%E7%AC%94%E8%AE%B0.assets/image-20240410113019779.png)

相当于父级块居中

```
 <div style="display: flex; justify-content: center;">
     <!--其他标签-->
</div>
```

![image-20240604105452894](Element%E7%AC%94%E8%AE%B0.assets/image-20240604105452894.png)

```html
      <div style="text-align: center; /* 文本水平居中 */">
        <svg-icon name="empty-df445c2e" width="161px" height="166px"></svg-icon>
        <div class="info">暂无收藏</div>
      </div>
```

div里标签元素居中(一般用于<标签>文字</标签>有文字的标签)

```
 <div style="text-align: center">
        <p>恰到好处的喜欢最舒服</p>
        <p>你不用多好</p>
        <p>我喜欢就好</p>
        <p>我没有很好</p>
        <p>你不嫌弃就好</p>
        <p style="text-align: right; padding-top: 10px">--- zhy</p>
        <el-button
            type="primary"
            round
            size="small"
            @click="showQRCode"
            >扫描二维码关注我</el-button
          >
 </div>
```

![](Element%E7%AC%94%E8%AE%B0.assets/image-20240304152336319.png)

# 正中

这个元素就会在其父元素的中间位置水平和垂直居中显示。

```
  .list-none {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, 50%);
  } 
```



# 满div

```
padding: 0;
margin: 0;
```



# 日历

 [记录改造elementui日历组件实例（日报月报）.html](..\..\..\..\..\..\Desktop\Browser Download\记录改造elementui日历组件实例（日报月报）.html) 



# 图片 + 链接

```
        <a href="https://github.com/Auroraol/my_blog">
            <svg t="1711597890635" class="icon" viewBox="0 0 1024 1024" version="1.1"
               </svg>
        </a>
```



# 文字 + 链接

```
        <p>
                仓库地址: <a href="https://github.com/Auroraol/my_blog"
                    target="_blank">https://github.com/Auroraol/my_blog</a>
            </p>
```

![image-20240317203716604](Element%E7%AC%94%E8%AE%B0.assets/image-20240317203716604.png)

# 文字 + 图片

```
       <a href="https://github.com/Auroraol">
          <img
            src="https://readme-typing-svg.demolab.com?font=Fira+Code&pause=1000&color=fff&width=435&lines=昨日之深渊,今日之浅谈;&center=true&size=23"
            alt="Typing SVG"
          />
       </e>
```

![image-20240317213121984](Element%E7%AC%94%E8%AE%B0.assets/image-20240317213121984.png)

```
  <h1>
          <img :src="randomImageSrc" />
          归档
        </h1>
```

![image-20240418140829792](Element%E7%AC%94%E8%AE%B0.assets/image-20240418140829792.png)

# 头像

[Avatar 头像 | Element Plus (element-plus.org)](https://element-plus.org/zh-CN/component/avatar.html)

![image-20240318212628724](Element%E7%AC%94%E8%AE%B0.assets/image-20240318212628724.png)





# Dropdown 下拉菜单

![image-20240402164832105](Element%E7%AC%94%E8%AE%B0.assets/image-20240402164832105.png)

```vue
<template>
  <el-dropdown @command="handleCommand">
    <span class="el-dropdown-link">
      Dropdown List<el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="a">Action 1</el-dropdown-item>
        <el-dropdown-item command="b">Action 2</el-dropdown-item>
        <el-dropdown-item command="c">Action 3</el-dropdown-item>
        <el-dropdown-item command="d" disabled>Action 4</el-dropdown-item>
        <el-dropdown-item command="e" divided>Action 5</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
export default {
  methods: {
const handleCommand = (command: string | number | object) => {
      // 在这里处理命令事件
      console.log('Command:', command);
    }
  }
}
</script>
```

或者

```vue
<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      Dropdown List<el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
          <router-link to="/a">
            <el-dropdown-item >Action 1</el-dropdown-item>
          </router-link>
           <router-link v to="/b">
            <el-dropdown-item>Action 2</el-dropdown-item>
           </router-link>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
</script>
```



## 搜索下拉

![img](Element%E7%AC%94%E8%AE%B0.assets/7632098d014042dea1b368d4fda05858.png)

1、这样就是一个简单的[select](https://so.csdn.net/so/search?q=select&spm=1001.2101.3001.7020)+option实现的input搜索下拉列表显示的简单的一个模版

![img](Element%E7%AC%94%E8%AE%B0.assets/19273405e3b244d583d8990d305bf8fe.png)

2、这样就会导致 只能选择搜索一次 第二次重新选择就会没有[下拉列表](https://so.csdn.net/so/search?q=下拉列表&spm=1001.2101.3001.7020) 那么我用了blur事件来监听下拉缩回去的时候 就将值清空重新发起请求 这样就会出来了

![img](Element%E7%AC%94%E8%AE%B0.assets/4c65d174bd7040f4ac01c3db93d5092a.png)

3、但是还是会有一个问题 就是我们输入0或者1 或者没有的值搜索的时候就会 清空[输入框](https://so.csdn.net/so/search?q=输入框&spm=1001.2101.3001.7020) 并且立刻缩回去 

原因：因为输入框如果没有发请求的时候会返回一个null那么下拉列表就没东西了 所以就为空了解决：

![img](Element%E7%AC%94%E8%AE%B0.assets/53c5d4ffdaa54ae0a622d12377a86c7c.png)

 当用户输入 0 或者没有的值的时候 就判断一下 如果返回数据为空 就将input 的值为空 重新发起请求 这样列表就又出来了



宽度     style="width: 75px"

![image-20240420134527661](Element%E7%AC%94%E8%AE%B0.assets/image-20240420134527661.png)

## 去掉这个黑框

在控制台查找这个元素，我们可以看见里面有这样一段样式，可以看见外边框outline自动给我们添加了1px,所以我们划过下拉框和点击都会出现这个黑框

![img](Element%E7%AC%94%E8%AE%B0.assets/9be852d5c26d48a499820159a8f77f3e.png)

当前没有去掉的样式及效果

![img](Element%E7%AC%94%E8%AE%B0.assets/b931e71982ad4f00b9c5c6e7d3860050.png)

 

解决的办法也很简单，将边框去掉即可，只需要添加如下代码：

 :deep(:focus-visible) {

 outline: none;

}

注意：这将完全禁用可见焦点状态的外观，包括默认的浏览器轮廓和任何自定义规则。请注意，这可能会影响可访问性，因为一些用户可能依赖这些线框来识别他们正在与哪个元素交互。因此，建议仅在必要时使用此规则，并在可能的情况下提供替代方法来指示焦点状态。

改完之后效果图如下：

![img](Element%E7%AC%94%E8%AE%B0.assets/a2c10fabb58b4ea683d4bddfd82d0143.png)

##  下拉菜单定位偏移问题

![image-20240419122356590](Element%E7%AC%94%E8%AE%B0.assets/image-20240419122356590.png)

## 自定义

![image-20240530183543950](Element%E7%AC%94%E8%AE%B0.assets/image-20240530183543950.png)

```html
            <ul class="one">
              <!-- <li class="one_item">
              </li> -->
              <!-- 扩展页面 -->
              <li class="one_item">
                <router-link :to="ExtendPage.url" class="one_item_nav">
                  {{ ExtendPage.icon }} {{ ExtendPage.name }}

                  <!-- 判断有没有二级分类，有就显示下拉箭头 -->
                  <svg
                    v-if="ExtendPage.children.length"
                    preserveAspectRatio="xMidYMid meet"
                    xmlns="http://www.w3.org/2000/svg"
                    width="1em"
                    height="1em"
                    fill="none"
                    viewBox="0 0 48 48"
                  >
                    <!--?lit$369315321$-->
                    <g>
                      <path
                        stroke-linejoin="round"
                        stroke-width="4"
                        stroke="currentColor"
                        d="M36 18 24 30 12 18"
                        data-follow-stroke="currentColor"
                      ></path>
                    </g>
                  </svg>
                </router-link>

                <!-- 二级导航 -->
                <ul class="two">
                  <li
                    class="two_item"
                    v-for="two in ExtendPage.children"
                    :key="two.id"
                  >
                    <router-link
                      :to="{ path: two.url, query: two.date }"
                      class="two_item_nav"
                      >{{ two.name }}</router-link
                    >
                  </li>
                </ul>
              </li>
            </ul>
```



```js

const ExtendPage = {
  name: "扩展页面",
  icon: "💡",
  url: "",
  children: [
    {
      id: 1,
      name: "个人主页",
      url: "/my",
    },
    {
      id: 2,
      name: "我的相册",
      url: "/photo",
    },
    {
      id: 3,
      name: "数据统计",
      url: "/stats",
    },
    {
      id: 4,
      name: "在线聊天室",
      url: "/chat",
      date: {
        name: "默认房间",
      },
    },
  ],
};
```



```css

// 一级导航
.one {
  display: flex;
  align-items: center;
  height: 60px;
    
  // 导航列表
  .one_item {
    position: relative;
    z-index: 999;
    // 导航
    .one_item_nav {
      display: inline-block;
      padding: 20px;
      color: #333;
      font-size: 15px;
      transition: color 0.3s;
    }

    // 二级导航
    .two {
      display: none;
      overflow: hidden;
      position: absolute;
      top: 50px;
      width: 100%;

      border-radius: 5px;
      background-color: #f9f9f9;
      box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1), 0 2px 6px rgba(0, 0, 0, 0.08);

      .two_item {
        .two_item_nav {
          position: relative;
          display: inline-block;
          width: 100%;
          padding: 10px;
          padding-left: 10px;
          font-size: 15px;
          box-sizing: border-box;
          color: #666;
          transition: all 0.3s;

          // 鼠标经过的小横线
          &::after {
            content: "";
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            width: 0;
            height: 3px;
            background-color: @color;
            transition: width 0.3s;
          }
        }

        // 鼠标经过二级导航的效果
        &:hover .two_item_nav {
          color: @color !important;
          background-color: #f2f2f2;
          padding-left: 30px;

          &:hover::after {
            width: 10px;
          }
        }
      }
    }

    // 鼠标经过哪个，就让哪个二级导航显示
    &:hover .two {
      display: block;
    }
  }
}
```



# Dialog 对话框

![image-20240318221758991](Element%E7%AC%94%E8%AE%B0.assets/image-20240318221758991.png)

![image-20240318221748005](Element%E7%AC%94%E8%AE%B0.assets/image-20240318221748005.png)

例子

```vue
<el-dialog v-model="dialogFormVisible" title="更换昵称" width="500">
  <el-form :model="form">
    <el-form-item label="Promotion name" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off" />
    </el-form-item>
    <el-form-item label="Zones" :label-width="formLabelWidth">
      <el-select v-model="form.region" placeholder="Please select a zone">
        <el-option label="Zone No.1" value="shanghai" />
        <el-option label="Zone No.2" value="beijing" />
      </el-select>
    </el-form-item>
  </el-form>
  <template #footer>
    <div class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </template>
</el-dialog>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {
        name: '',
        region: ''
      }
    };
  },
  methods: {
    submitForm() {
      // 发起URL请求
      axios.post('https://example.com/api/data', this.form)
        .then(response => {
          console.log('请求成功', response.data);
          // 处理请求成功的逻辑
        })
        .catch(error => {
          console.error('请求失败', error);
          // 处理请求失败的逻辑
        });
      
      this.dialogFormVisible = false; // 关闭对话框
    }
  }
}
</script>
```

### 例子

<img src="Element%E7%AC%94%E8%AE%B0.assets/image-20240601213124068.png" alt="image-20240601213124068" style="zoom:50%;" />

```html
 <!-- 登记弹窗 -->
  <el-dialog
    v-model="registerModel"
    title="选择一个身份"
    width="500"
    @close="close"
  >
    <el-form ref="form" :model="chatUserInfo" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="chatUserInfo.name"
          autocomplete="off"
          style="width: 300px"
        />
      </el-form-item>

      <el-form-item label="头像">
        <el-radio-group v-model="chatUserInfo.avatar" class="ml-4">
          <el-radio
            :label="item"
            size="large"
            v-for="(item, index) in avatars"
            :key="index"
          >
            <img :src="avatarFilter(item)" v-avatar alt="" />
          </el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          size="large"
          style="width: 100%"
          @click="submit"
          >选择</el-button
        >
      </el-form-item>
    </el-form>
  </el-dialog>
```

```css
:deep(.el-form-item__content) {
  .el-radio.el-radio--large {
    margin-bottom: 30px;
    margin-top: 10px;

    img {
      width: 50px;
      height: 50px;
      border-radius: 15px;
    }
  }
}
```



```ts

// 登记框
const form = ref<FormInstance>();
const registerModel = ref<boolean>(false);
// 头像列表
const avatars = [
  "Ginger",
  "Patches",
  "Sadie",
  "Casper",
  "Molly",
  "Smokey",
  "Lilly",
];
const avatarFilter = (v: string) =>
  `https://api.dicebear.com/7.x/fun-emoji/svg?seed=${v}`;

// 名称校验规则
const rules = reactive({
  name: [
    { required: true, message: "名称不能为空", trigger: "blur" },
    {
      min: 1,
      max: 10,
      message: "名称长度限制为 1 ~ 10个字符",
      trigger: "blur",
    },
  ],
});

// 用户信息
const chatUserInfo = reactive<ChatUserInfo>({
  name: "",
  avatar: "Ginger",
});

// 关闭弹框触发
const close = () => {
  form.value?.resetFields();
  chatUserInfo.name = "";
  chatUserInfo.avatar = "Ginger";
};

// 提交表单触发
const submit = async () => {
  if (!form.value) return;
  await form.value.validate((valid, fields) => {
    if (valid) {
      useChatStorePinia.updateChatUserInfo(chatUserInfo);

      registerModel.value = false;

      ElMessage({
        message: "🎉选择成功~",
        type: "success",
      });
    } else {
      console.log("error submit!", fields);
    }
  });
};
```



# Affix 固钉

通过设置 `offset` 属性来改变吸顶距离，默认值为 0。

```vue
<template>
   <el-affix  :offset="90" class="包裹的样式">
    <div style="background-color: #2580B3;">
    </div>
  </el-affix>
</template>

//等价 position: fixed; 
```

# Autocomplete 自动补全输

```vue
<template>
  <el-autocomplete
    v-model="searchText"
    :fetch-suggestions="querySearchAsync"
    placeholder="请输入搜索内容"
  ></el-autocomplete>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const searchText = ref('');
    
    const querySearchAsync = async query => {
      try {
        const response = await axios.get(`https://your-elasticsearch-endpoint?q=${query}`);
        return response.data.results;
      } catch (error) {
        console.error('Error fetching suggestions:', error);
        return [];
      }
    };

    return {
      searchText,
      querySearchAsync
    };
  }
}
</script>
```

上面的代码中，我们使用了<el-autocomplete>组件来实现搜索的动态补全功能。当用户输入内容时，会触发querySearchAsync方法来向Elasticsearch发起搜索请求，并返回搜索结果作为建议列表。你需要根据实际情况替换https://your-elasticsearch-endpoint为你的Elasticsearch端点地址，并根据Elasticsearch的API来构建搜索请求和处理返回结果。



# vue3+Element plus实现登录功能

![image-20240328113844266](Element%E7%AC%94%E8%AE%B0.assets/image-20240328113844266.png)



## 搭建登录静态

#### 1、实现左边背景和右边登录栏的总体布局布局：

```javascript
<el-row class="content">
    <!--el-col 列： -->
    <el-col :span="16" :xs="0" class="content-left"></el-col>

    <el-col :span="8" :xs="24" class="content-right">
<el-row>
```

#### 2、账号密码登录和手机号码登录切换使用<el-tabs>组件实现：

#### 3、其他省略

#### 4、全部代码：

```javascript
<template>
  <el-row class="content">
    <!--el-col 列： -->
    <el-col :span="16" :xs="0" class="content-left">



    </el-col>
    <el-col :span="8" :xs="24" class="content-right">
      <div class="loginContent">
        <div class="loginContentTop">
          <div class="header">
            <div class="logo">
              <img src="../assets/images/logo.png" alt="" class="image" />
            </div>
            <div class="fontSize">JinPiKa</div>
          </div>
          <span class="introduce">全球最大的代码托管平台</span>
        </div>
        <div class="loginContentForm">
          <div class="loginMethods">
            <el-tabs>
              <el-tab-pane
                label="账号密码登录"
                class="toLogin"
                :class="{ option: !option }"
                @click="toOption(0)"
              >
                <!-- loginForm: 表单数据对象-->
                <el-form
                  :model="loginForm"
                  :rules="loginFormRules"
                  style="width: 208px"
                >
                  <el-form-item label="" prop="username">
                    <el-input
                      :prefix-icon="User"
                      placeholder="用户名:user"
                      v-model="loginForm.username"
                      inline-message
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="password">
                    <el-input
                      :prefix-icon="Lock"
                      placeholder="密码:user"
                      show-password
                      v-model="loginForm.password"
                      inline-message
                    ></el-input>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <el-tab-pane
                label="手机号码登录"
                class="toLogin"
                :class="{ option: !option }"
                @click="toOption(0)"
              >
                <!-- loginForm: 表单数据对象-->
                <el-form
                  :model="loginFormPhone"
                  :rules="loginFormPhoneRules"
                  style="width: 208px"
                  prop="phone"
                >
                  <el-form-item label="">
                    <el-input
                 :prefix-icon="User"
                      placeholder="请输入手机号"
                      v-model="loginFormPhone.phone"
                      inline-message
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="" prop="code">
                    <el-input
                      :prefix-icon="Lock"
                      placeholder="请输入验证码"
                      v-model="loginFormPhone.code"
                      inline-message
                    ></el-input>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
        <div class="loginContentButton">
          <div class="buttonTop">
            <el-checkbox v-model="checked" label="自动登录" size="small" />
            <el-link type="primary" :underline="false">
              <span style="font-size: 12px">忘记密码</span>
            </el-link>
          </div>
          <el-button type="primary" class="loginButton" @click="tologin">
            登录
          </el-button>
          <el-divider>
            <span class="fengexian">其他登录方式</span>
          </el-divider>
          <div class="svgItems">
            <div class="svgItem">
              <svg-icon
                name="zhifubao"
                width="18px"
                height="18px"
                color="pink"
              ></svg-icon>
            </div>
            <div class="svgItem">
              <svg-icon
                name="taobao"
                width="18px"
                height="18px"
                color="pink"
              ></svg-icon>
            </div>
            <div class="svgItem">
              <svg-icon
                name="weibo"
                width="18px"
                height="18px"
                color="pink"
              ></svg-icon>
            </div>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const loginForm = ref({
  username: '',
  password: ''
});

const loginFormRules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
});

const loginFormPhone = ref({
  phone: '',
  code: ''
});

const loginFormPhoneRules = ref({
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' }
  ]
});

const checked = ref(false);

const toOption = (index) => {
  // Your logic for switching between login options
};

</script>
```

验证图形验证码，和手机短信验证码

[java+vue3实现生成、验证图形验证码，和手机短信验证码_vue3 图片验证码-CSDN博客](https://blog.csdn.net/Johnson_7/article/details/126865190?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-8-126865190-blog-132416244.235^v43^pc_blog_bottom_relevance_base1&spm=1001.2101.3001.4242.5&utm_relevant_index=11)

![image-20240328195915062](Element%E7%AC%94%E8%AE%B0.assets/image-20240328195915062.png)

验证码单独提取出一个组件

```html
<div>
    <img :src="image" style="height: 60px" @click="changeImage" /><span
      @click="changeImage">看不清点击换一张</span>
</div>
```



```js
  // 图片url
  const image = ref(url.value + '/authCode/1');  // 此处为图片生成接口的完整路径
 
  // 更换图片方法
  const changeImage = () => {
    image.value = url.value + '/authCode/' + getNowTime();
  };
  // 加一个当前时间，确保每次刷新都可以重新发送请求
  function getNowTime() {
    var date = new Date();
    //年 getFullYear()：四位数字返回年份
    var year = date.getFullYear(); //getFullYear()代替getYear()
    //月 getMonth()：0 ~ 11
    var month = date.getMonth() + 1;
    //日 getDate()：(1 ~ 31)
    var day = date.getDate();
    //时 getHours()：(0 ~ 23)
    var hour = date.getHours();
    //分 getMinutes()： (0 ~ 59)
    var minute = date.getMinutes();
    //秒 getSeconds()：(0 ~ 59)
    var second = date.getSeconds();
 
    var time = '当前时间是：' + year + month + day + hour + minute + second;
    return time;
  }
```

form表单

```html
  const ruleFormRef = ref<FormInstance>();  
  // 图形验证码校验规则
  const validateCheckNum = (rule: any, value: string, callback: any) => {
    if (value === '') {
      callback(new Error('请输入验证码'));
    } else {
      // 发送校验请求
      getImageCode(value).then((res) => {
        if (res) {
          callback();
        } else {
          callback(new Error('验证码错误'));
        }
      });
    }
  };
  // 表单校验规则
  const rules = reactive({
    checkNum: [{ validator: validateCheckNum, trigger: 'blur' }]
  });
```





```html
<el-form-item label="手机号" prop="phoneNo">
              <el-input v-model="registerData.phoneNo" placeholder="请输入手机号">
                <template #prepend>
                  <span style="width: 0px; margin: 0 0 0 -25px">+86</span>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="手机验证码" prop="checkPhoneNo">
              <el-input
                v-model="registerData.checkPhoneNo"
                placeholder="请输入手机验证码"
                style="width: 160px"
              /><el-button
                :disabled="disabled"
                type="primary"
                style="width: 80px; margin-left: 10px"
                @click="getCheckPhoneNo"
                >{{ getPhoneCodeName }}</el-button
              >
            </el-form-item>
            <el-form-item label="">
              <span v-show="isCheckDesc === true" style="color: red">
                {{checkDesc}}
              </span>
</el-form-item>
```

```js
  const ruleFormRef = ref<FormInstance>();  
  // 获取短信验证码按钮名称
  const getPhoneCodeName = ref('获取验证码');
  // 获取短信验证码按钮是否启用
  const disabled = ref(false);
  // 短信验证码按钮倒计时时间
  const timeNum = ref(60);
  // 发送验证码后的提示内容
  const checkDesc = ref('验证码已发送，可能会有延迟，请注意查收');
  // 发送验证码后的提示内容是否展示
  const isCheckDesc = ref(false);
  // 手机验证码校验规则
  const validateCheckPhoneNo = (rule: any, value: string, callback: any) => {
    if (value === '') {
      callback(new Error('请输入手机验证码'));
    } else {
      receiveMessage(value).then((res) => {
        if (res === '验证超时') {
          callback(new Error('验证码已超时，请重新发送'));
        } else if (res === '验证错误') {
          callback(new Error('验证码错误，请重新输入'));
        } else if (res === '请求体没有phoneKey的值') {
          callback(new Error('请先发送验证码'));
        } else {
          isCheckDesc.value = false;
          callback();
        }
      });
    }
  };
  // 表单校验规则
  const rules = reactive({
    checkPhoneNo: [{ validator: validateCheckPhoneNo, trigger: 'blur' }],
  });
  // 获取手机验证码方法
  const getCheckPhoneNo = () => {
    const reg =
      /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/;
    if (!reg.test(registerData.phoneNo)) {
      ElMessage({
        message: '请先正确输入手机号',
        type: 'warning'
      });
      return;
    }
    disabled.value = true;
    sendMessage(registerData.phoneNo).then((res) => {
      if (res === '手机号格式错误') {
        ElMessage({
          message: '请先正确输入手机号',
          type: 'warning'
        });
        disabled.value = false;
        return;
      } else {
        isCheckDesc.value = true;
        let timer = setInterval(() => {
          --timeNum.value;
          getPhoneCodeName.value = `重新发送 ${timeNum.value}`;
        }, 1000);
        setTimeout(() => {
          clearInterval(timer);
          timeNum.value = 60;
          disabled.value = false;
          getPhoneCodeName.value = '获取验证码';
        }, 60000);
      }
    });
  };
```

## 不同登录方式的切换

```vue

<template>
  <div class="login_container">
    <div class="login_logo">
      <img />
    </div>
    <div class="login_box">
      <!-- 登录表单区域 -->
      <el-tabs :stretch="true">
        <el-tab-pane label="账号密码登录">
          <!-- 账号密码登录表单 -->
          <el-form ref="pwdLoginFormRef" :model="pwdLoginForm" :rules="pwdLoginFormRules">
            <!-- 用户名 -->
            <el-form-item prop="username">
              <el-input placeholder="用户名/邮箱/手机号" clearable prefix-icon="el-icon-user-solid
" v-model="pwdLoginForm.username">
              </el-input>
            </el-form-item>
            <!-- 密码 -->
            <el-form-item prop="password">
              <el-input placeholder="密码" type="password" show-password prefix-icon="el-icon-lock"
                v-model="pwdLoginForm.password">
              </el-input>
            </el-form-item>
            <!-- 按钮区域 -->
            <el-form-item class="login_btns">
              <el-button type="primary" @click="pwdLogin" :loading="loading">登录</el-button>
              <el-button type="primary" @click='toRegister'>注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="邮箱验证登录">
          <!-- 邮箱验证登录表单 -->
          <el-form ref="emailLoginFormRef" :model="emailLoginForm" :rules="emailLoginFormRules">
            <!-- 邮箱 -->
            <el-form-item prop="email">
              <el-input placeholder="邮箱" clearable prefix-icon="el-icon-message" v-model="emailLoginForm.email">
              </el-input>
            </el-form-item>
            <!-- 邮箱验证码 -->
            <el-form-item prop="emailCode">
              <el-input placeholder="验证码" prefix-icon="el-icon-key" v-model="emailLoginForm.emailCode">
                <template #append>
                  <el-button :disabled="disabled" @click="getEmailValidateCode">{{buttonText}}
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
            <!-- 按钮区域 -->
            <el-form-item class="login_btns">
              <el-button type="primary" @click="emailLogin">登录</el-button>
              <el-button type="primary" @click='toRegister'>注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- <el-tab-pane label="手机验证登录"> -->
        <!-- 手机验证登录表单 -->
        <!-- <el-form ref="phoneLoginFormRef" :model="phoneLoginForm" :rules="phoneLoginFormRules"> -->
        <!-- 手机号 -->
        <!-- <el-form-item prop="phone">
              <el-input placeholder="手机号" clearable prefix-icon="el-icon-phone" v-model="phoneLoginForm.phone">
              </el-input>
            </el-form-item> -->
        <!-- 手机验证码 -->
        <!-- <el-form-item prop="phoneCode">
              <el-input placeholder="验证码" prefix-icon="el-icon-key" v-model="phoneLoginForm.phoneCode">
                <template #append>
                  <el-button>获取验证码</el-button>
                </template>
              </el-input>
            </el-form-item> -->
        <!-- 按钮区域 -->
        <!-- <el-form-item class="login_btns">
              <el-button type="primary" @click="phoneLogin">登录</el-button>
              <el-button type="success" @click='toRegister'>注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane> -->
      </el-tabs>
    </div>
    <div class="login_footer">
      登录即表示您已阅读并同意
      <a href="#">服务条款</a>
    </div>
  </div>
</template>

<script>
  import {
    ref,
    reactive,
    toRefs,
    getCurrentInstance
  } from 'vue'
  import qs from 'qs'
  import axios from 'axios'
  import {
    useRouter
  } from 'vue-router'
  export default {
    setup() {
      // 验证邮箱的规则
      var checkEmail = (rule, value, cb) => {
        // 验证邮箱的正则表达式
        const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
        if (regEmail.test(value)) {
          // 合法的邮箱
          return cb()
        }
        cb(new Error('请输入合法的邮箱'))
      }
      // // 验证手机号的规则
      // var checkMobile = (rule, value, cb) => {
      //   // 验证手机号的正则表达式
      //   const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      //   if (regMobile.test(value)) {
      //     return cb()
      //   }
      //   cb(new Error('请输入合法的手机号'))
      // }
      const {
        proxy
      } = getCurrentInstance()
      const router = useRouter()
      const pwdLoginFormRef = ref(null)
      const emailLoginFormRef = ref(null)
      // const phoneLoginFormRef = ref(null)
      const state = reactive({
        pwdLoginForm: {
          username: 'admin',
          password: '123456'
        },
        emailLoginForm: {
          email: '',
          emailCode: ''
        },
        // phoneLoginForm: {
        //   phone: '',
        //   phoneCode: ''
        // },
        pwdLoginFormRules: {
          username: [{
            required: true,
            message: '请输入你的账号',
            trigger: 'blur'
          }],
          // 验证密码是否合法
          password: [{
            required: true,
            message: '请输入你的密码',
            trigger: 'blur'
          }]
        },
        emailLoginFormRules: {
          email: [{
              required: true,
              message: '请输入你的邮箱',
              trigger: 'blur'
            },
            {
              validator: checkEmail,
              trigger: 'blur'
            }
          ],
          emailCode: [{
            required: true,
            message: '请输入你获取到的验证码',
            trigger: 'blur'
          }]
        },
        // phoneLoginFormRules: {
        //   phone: [{
        //       required: true,
        //       message: '请输入你的手机号',
        //       trigger: 'blur'
        //     },
        //     {
        //       validator: checkMobile,
        //       trigger: 'blur'
        //     }
        //   ],
        //   phoneCode: [{
        //     required: true,
        //     message: '请输入你获取到的验证码',
        //     trigger: 'blur'
        //   }]
        // },
        loading: false,
        // 控制获取验证码
        buttonText: '获取验证码',
        disabled: false,
        duration: 60,
        timer: null
      })
      // 账号密码登录
      const pwdLogin = async () => {
        state.loading = true
        const obj = {
          username: state.pwdLoginForm.username,
          password: state.pwdLoginForm.password
        }
        try {
          const res = await new proxy.$request(proxy.$urls.m().pwdLogin, qs.stringify(obj)).modepost()
          console.log(res)
          if (res.data.success != true) {
            new proxy.$tips(res.data.message, 'warning').mess_age()
          } else {
            new proxy.$tips(res.data.message, 'success').mess_age()
            localStorage.setItem('token', res.data.data.token)
            // 成功跳转页面
          }
          state.loading = false
        } catch (e) {
          state.loading = false
          new proxy.$tips('服务器发生错误', 'error').mess_age()
        }
      }
      // 获取邮箱验证码
      const getEmailValidateCode = () => {
        const obj = {
          email: state.emailLoginForm.email
        }
        axios.post('requestUrl', qs.stringify(obj)).then(
          res => {
            if (res.data.success != true) {
              new proxy.$tips(res.data.message, 'warning').mess_age()
            } else {
              console.log(res.data)
              console.log(res.headers)
              new proxy.$tips(res.data.message, 'success').mess_age()
              // localStorage.setItem('token', res.data.data.token)
              state.timer = setInterval(() => {
                state.disabled = true
                const tmp = state.duration--
                state.buttonText = `${tmp}秒后重新获取`
                if (tmp <= 0) {
                  clearInterval(state.timer)
                  state.duration = 60
                  state.buttonText = '重新获取验证码'
                  state.disabled = false
                }
              }, 1000)
            }
          })
      }
      // 邮箱验证登录
      const emailLogin = async () => {
        state.loading = true
        const obj = {
          email: state.emailLoginForm.email,
          emailValidateCode: state.emailLoginForm.emailCode,
          token: localStorage.getItem('token')
        }
        try {
          const res = await new proxy.$request(proxy.$urls.m().emailLogin, qs.stringify(obj)).modepost()
          if (res.data.success != true) {
            new proxy.$tips(res.data.message, 'warning').mess_age()
          } else {
            new proxy.$tips(res.data.message, 'success').mess_age()
            localStorage.setItem('token', res.data.data.token)
            // 成功跳转页面
          }
          state.loading = false
        } catch (e) {
          state.loading = false
          new proxy.$tips('服务器发生错误', 'error').mess_age()
        }
      }
      // 手机号验证登录
      // const phoneLogin = async () => {
      //   const obj = {
      //     phone: state.phoneLoginForm.phone,
      //     phoneCode: state.phoneLoginForm.phoneCode
      //   }
      // }
      const toRegister = () => {
        router.push({
          path: '/register'
        })
      }
      return {
        ...toRefs(state),
        pwdLoginFormRef,
        emailLoginFormRef,
        // phoneLoginFormRef,
        pwdLogin,
        getEmailValidateCode,
        emailLogin,
        // phoneLogin,
        toRegister
      }
    }
  }
</script>

<style lang="less" scoped>
  .login_container {
    width: 100%;
    height: 100%;
    background: url(../assets/登录页面背景图.png);
    position: fixed;
    background-size: 100% 100%;
  }

  .login_box {
    width: 395px;
    height: 435px;
    background-color: rgba(225, 225, 225, 0);
    border: 1px solid rgba(225, 225, 225, 0);
    box-shadow: 2px 3px 3px #aaaaaa;
    border-radius: 5px;
    position: relative;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .login_btns {
    text-align: center;
  }
  
  .login_footer { 
    position: absolute;
    left: 50%;
    top: 70%;
    transform: translate(-50%, -50%);
    border: 1px solid #ffffff;
    width: 395px;
    height: 30px;
    text-align: center;
  }
</style>

<style scoped>
  .login_box {
    backdrop-filter: blur(15px);
    box-shadow: 0 0 5px #fff;
  }
  
  .el-tabs >>> .el-tabs__header { 
    padding: 5% 10% 0 10%;
  }
  
  .el-tabs >>> .el-tabs__item:hover {
      color: #fff;
      cursor: pointer;
  }
  
  .el-tabs >>> .el-tabs__item.is-active {
      color: #c915e5;
  }

  .el-form {
    text-align: center;
  }

  .el-form-item>>>.el-form-item__error {
    left: 10%;
  }

  .el-input {
    width: 80%;
  }
  
  .el-input { 
    --el-input-font-color: black;
  }

  .el-input>>>.el-input__inner {
    background-color: rgba(225, 225, 225, 0);
    box-shadow: 0 0 2px #fff;
    border: 1px solid #fff;
  }

  .el-button {
    border-radius: 10px;
  }

  .el-button--primary {
    --el-button-font-color: #409e40;
    --el-button-background-color: #ffffff;
    --el-button-border-color: #409eff;
    --el-button-hover-color: #66b1ff;
    --el-button-active-font-color: #e6e6e6;
    --el-button-active-background-color: #0d84ff;
    --el-button-active-border-color: #0d84ff;
  }
</style>


```

 **运行后的效果**

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5piv5L2gWk3lk6Xlk6XlkaI=,size_16,color_FFFFFF,t_70,g_se,x_16.png)
![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5piv5L2gWk3lk6Xlk6XlkaI=,size_17,color_FFFFFF,t_70,g_se,x_16.png)
![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5piv5L2gWk3lk6Xlk6XlkaI=,size_15,color_FFFFFF,t_70,g_se,x_16.png)
通过点击上面的Tabs（标签)实现三种不同的登录方式

**再说一下Tabs（标签页）的属性（根据项目的实际需求添加）**

通过给`el-tabs`添加`:stretch="true"`的属性值，实现撑满上方页头的功能；
通过给`el-tabs`添加`type="border-card"`的属性值，实现带有边框的卡片风格样式；
通过给`el-tabs`添加`type="card"`的属性值，实现卡片风格的样式；



# 对齐

```less
<template>
  <div class="xxxx">
    <div class="left-element">左侧元素</div>
    <div class="right-element">右侧元素</div>
  </div>
</template>
 
<style lang="less" scoped>
.xxxx {
  display: flex;
  align-items: center; /* 垂直居中 */
  
  .left-element {
  margin-right: 10px; /* 可以调整间距 */
}
 
.right-element {
  margin-left: auto; /* 推到容器的右侧 */
}
}
 

</style>
```

![image-20240328204513424](Element%E7%AC%94%E8%AE%B0.assets/image-20240328204513424.png)



```

.align-container {
  display: flex;
  align-items: center;
}

      <div class="text align-container" >
           <el-text class="mx-1" >注册登录即表示同意   </el-text>
          <el-link class="btn" @click="terms">用户协议</el-link>
          <el-text class="mx-1">和</el-text>
          <el-link class="btn" @click="privacy"> 隐私政策</el-link>
        </div>
```

![image-20240330184318057](Element%E7%AC%94%E8%AE%B0.assets/image-20240330184318057.png)



```css
  position: absolute;
  top: 50%;
  right: 5%;
  transform: translateY(-50%);
```

这段 CSS 样式用于将一个元素定位在父容器的垂直中间，并且距离父容器右侧的距离为 5%。具体来说：

- `position: absolute;`：将元素的位置相对于最近的非 static 定位的祖先元素进行定位。
- `top: 50%;`：将元素的顶部边缘位置移动到父容器的中间。
- `right: 5%;`：将元素的右边缘位置移动到距离父容器右边界 5% 的位置。
- `transform: translateY(-50%);`：通过 `translateY` 变换将元素在垂直方向上向上移动自身高度的一半，从而实现垂直居中。

这段样式通常用于在设计中实现垂直居中和水平偏移的效果，经常用于定位图标按钮或者提示框等元素









# 左右(有时候可以用定位来实现)

```
      margin-left: auto; /* 推到容器的右侧 */
```

始终在右侧

```vue
<style scoped>
.nav {
  display: flex;
  justify-content: space-between; // 让子元素在父元素两端对齐
}

.el-menu-demo {
  flex-grow: 1; // 让菜单占据剩余空间
}

.nav-user-info {
  margin-left: auto; // 将组件移到最右侧
}
</style>

<template>
  <el-row class="nav" v-if="device !== 'desktop'">
    <el-col :span="24">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="transparent"
        active-text-color="#79bbff"
      >
        <el-menu-item index="/index">首页</el-menu-item>
      </el-menu>
      <nav-user-info :userInfo="userInfo" v-else></nav-user-info>
    </el-col>
  </el-row>
</template>
```

![image-20240507113003762](Element%E7%AC%94%E8%AE%B0.assets/image-20240507113003762.png)





# 分割线

![image-20240328205216860](Element%E7%AC%94%E8%AE%B0.assets/image-20240328205216860.png)

## el-divider样式

```js
.el-divider {
  background-color: #409eff;
}
 
.el-divider__text.is-left {
  color: #409eff;
  left: 0;
  font-weight: bold;
  margin: 0 10px;
  padding: 0 5px;
}
```

![image-20240328212653220](Element%E7%AC%94%E8%AE%B0.assets/image-20240328212653220.png)

## 出现白色遮挡

![image-20240328212706708](Element%E7%AC%94%E8%AE%B0.assets/image-20240328212706708.png)

使用自定义组件

```vue
<template>
    <div class="my-divider">
        <div class="line" :style="{ width: leftWidth }"></div>
        <span class="label">{{ label }}</span>
        <div class="line" :style="{ width: rightWidth }"></div>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';

const props = defineProps({
    label: {
        type: String,
        default: ''
    },
    contentPosition: {
        type: String,
        default: 'center'
    }
});

const leftWidth = ref('50%');
const rightWidth = ref('50%');

const setLineWidth = () => {
    let p = props.contentPosition;
    switch (p) {
        case 'center': {
            leftWidth.value = '50%';
            rightWidth.value = '50%';
            break;
        }
        case 'left': {
            leftWidth.value = '10%';
            rightWidth.value = '90%';
            break;
        }
        case 'right': {
            leftWidth.value = '90%';
            rightWidth.value = '10%';
            break;
        }
    }
};

watch(() => props.contentPosition, () => {
    setLineWidth();
});

setLineWidth();
</script>

<style lang="less" scoped>
.my-divider {
    position: relative;
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    margin: 15px 0;
    color: #000;

    .line {
        background: #000;
        height: 1px;
    }

    .label {
        width: auto;
        padding: 0 12px;
        text-align: center;
        transform: translateY(-1px);
        white-space: nowrap; // 不换行(单行)
    }
}
</style>

```

使用

```
<my-divider content-position="center" label="其他登录方式" />
```

![image-20240328212824372](Element%E7%AC%94%E8%AE%B0.assets/image-20240328212824372.png)



# 下划线

![image-20240304224022928](Element%E7%AC%94%E8%AE%B0.assets/image-20240304224022928.png)

```html
<div class="header">
    <div style="font-size: 1.8rem">名片</div>
    <span></span>
</div>


.header{
span {
        display: block;
        width: 100%;
        height: .5rem;
        background-image: linear-gradient(to left, var(--gradient-start-one), var(--gradient-end-one));
        border-radius: 1rem;
    }
}
```

```
  --gradient-start-one: #EF33B1;
  --gradient-end-one: #F6E6BC;
```

用边框也能做下换线,上化线啥的

```
<div class="header">
</div>

.header{
	border-bottom: .4rem solid var(--border-line);
}
```

```
  --border-line: #54befc;
```

![image-20240305111405182](Element%E7%AC%94%E8%AE%B0.assets/image-20240305111405182.png)

补充

```
整体边框: box-sizing: border-box;
```

```
border-bottom: 1px solid hsla(0,0%,59.2%,.2);
```

![image-20240404115715987](Element%E7%AC%94%E8%AE%B0.assets/image-20240404115715987.png)

# 大小

```
style="    width: 100%; "  // 充满整个父级
```

# 按钮

需求: 没输入表单信息,注册按钮为灰色禁用状态. 输入后启用,点击是手指

```vue
<template>
  <el-row class="content">
    <el-col :span="16" :xs="0" class="content-left"> </el-col>
    <el-col :span="8" :xs="24" class="content-right">
      <div class="register-form">
        <!-- ... -->
        <div class="form-fields">
          <!-- 输入框省略... -->

          <el-button
            size="large"
            type="primary"
            class="button"
            :disabled="!isFormValid"
            @click="submit"
            :style="{ cursor: isFormValid ? 'pointer' : 'not-allowed' }"
          >
            注册
          </el-button>

          <p class="text">
            <!-- ... -->
          </p>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, computed } from "vue";

const registerForm = ref({
  username: "",
  mobile: "",
  code: "",
  password: ""
});

    // 
const isFormValid = computed(() => {
  return (
    registerForm.value.username &&
    registerForm.value.mobile &&
    registerForm.value.code &&
    registerForm.value.password.length >= 6
  );
});

const submit = () => {
  // 提交表单逻辑
};

// 其他方法省略...
</script>

<style scoped>
/* 样式省略... */
</style>

```

### 加载按钮

```html
<el-button
  type="primary"
  :loading="loading"
    注册
</el-button>

const loading = ref(false)  // ture显示加载动画
```





# **Feedback 反馈组件**

## Message 消息提示

```js
const open3 = () => {
  ElMessage({
    message: 'Warning, this is a warning message.',
    type: 'warning',
  })
}
```

![image-20240330161951087](Element%E7%AC%94%E8%AE%B0.assets/image-20240330161951087.png)

![image-20240330161929796](Element%E7%AC%94%E8%AE%B0.assets/image-20240330161929796.png)

## MessageBox 消息弹框

![image-20240416162240329](Element%E7%AC%94%E8%AE%B0.assets/image-20240416162240329.png)



**当需要用户输入内容时，可以使用 Prompt 类型的消息框。**

![image-20240428191645747](Element%E7%AC%94%E8%AE%B0.assets/image-20240428191645747.png)



## Notification 通知

悬浮出现在页面角落，显示全局的通知提醒消息

![image-20240529233850980](Element%E7%AC%94%E8%AE%B0.assets/image-20240529233850980.png)

[Notification 通知 | Element Plus (element-plus.org)](https://element-plus.org/zh-CN/component/notification.html#notification-通知)



# 表单

![image-20240330215122877](Element%E7%AC%94%E8%AE%B0.assets/image-20240330215122877.png)

![image-20240330213807813](Element%E7%AC%94%E8%AE%B0.assets/image-20240330213807813.png)

- `{ required: true, message: '请输入用户名', trigger: 'blur' }`: 表示用户名字段不能为空，如果用户未输入用户名并且焦点从该字段移开时（即失去焦点），会触发提示消息"请输入用户名"。
- `{ pattern: /^[a-zA-Z][a-zA-Z0-9_]{1,15}$/, message: '用户名格式不正确', trigger: 'blur' }`: 表示用户名必须以字母开头，且只能包含字母、数字和下划线，长度为2到16个字符之间。如果用户输入的用户名不符合这个规则，失去焦点时会触发提示消息"用户名格式不正确"。

确保输入字段不为空

+ 使用 `required: true` 

正则

+ pattern: 正则表达式

在 Element UI 的表单验证规则中，`trigger` 属性用于指定验证逻辑的触发方式，常用的触发方式包括：

- `blur`: 当表单项失去焦点时触发验证逻辑。例如，用户在输入框中输入完内容后，点击其他地方使输入框失去焦点时触发验证。
- `change`: 当表单项的值发生改变时触发验证逻辑。这包括用户输入、选择选项等操作。
- `input`: 当用户在表单项中输入内容时即时触发验证逻辑。这个触发方式会随着用户每次输入字符而触发验证。
- `submit`: 在表单提交时触发验证逻辑。通常用于整个表单的统一验证。

例子

```ts
            <el-form-item label="" prop="username">
              <el-input
                size="large"
                :prefix-icon="User"
                placeholder="用户名"
                v-model="loginForm.username"
                inline-message
              ></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input
                size="large"
                :prefix-icon="Lock"
                placeholder="密码"
                show-password
                v-model="loginForm.password"
                inline-message
              ></el-input>
            </el-form-item>
            <div class="button-top">
              <div class="left-element">
                <el-checkbox v-model="checked" label="记住密码" />
              </div>
              <div class="right-element">
                <el-text @click="forgetClick" class="forget" type="primary"
                  >忘记密码</el-text
                >
              </div>
            </div>
            <el-form-item>
              <el-button
                size="large"
                type="primary"
                class="button"
                :loading="loading"
                @click="passwordLogin"
                :disabled="!isFormValid"
                :style="{
                  cursor: isFormValid ? 'pointer' : 'not-allowed',
                }"
              >
                登录
              </el-button>
            </el-form-item>
          </el-form>

const loginFormRules = ref({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});
```

例子

```vue
<template>
  <el-form ref="form" :model="formData" :rules="rules" label-width="100px">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="formData.username"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="mobile">
      <el-input v-model="formData.mobile"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="code">
      <el-input v-model="formData.code"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="formData.password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        username: '',
        mobile: '',
        code: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { pattern: /^[a-zA-Z][a-zA-Z0-9_]{1,15}$/, message: '用户名格式不正确', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: this.validateMobile, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码不能少于6位数', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 表单验证通过，可以提交数据
          // 这里可以调用 vsubmit() 方法
          this.vsubmit();
        } else {
          // 表单验证不通过，不做任何操作
        }
      });
    },
    vsubmit() {
      // 这里放置原来的验证逻辑
      const username = this.formData.username;
      const mobile = this.formData.mobile;
      const code = this.formData.code;
      const password = this.formData.password;

      // 进行验证逻辑，根据需要处理错误信息
      // 注意：这里没有对验证结果进行处理，只是简单示例
    },
    validateMobile(rule, value, callback) {
      if (!value) {
        callback(new Error('请输入手机号'));
      } else if (!/^[1][3-9][0-9]{9}$/.test(value)) {
        callback(new Error('手机号格式不正确'));
      } else {
        callback();
      }
    }
  }
};
</script>

```

**vue3 使用** 

```vue
<template>
  <el-form :model="form" :rules="rules" label-position="left" ref="formRef">
    <el-form-item prop="name">
      <el-input v-model="form.name" placeholder="输入名称" />
    </el-form-item>
    <el-form-item prop="url">
      <el-input v-model="form.url" placeholder="输入链接" />
    </el-form-item>
    <el-form-item prop="icon">
      <el-input v-model="form.icon" placeholder="输入图标" />
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref } from 'vue'

const form = ref({
  icon: '',
  id: null,
  name: '',
  url: ''
})
const rules = ref({
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  url: [{ required: true, message: '请输入链接', trigger: 'blur' }],
  icon: [{ required: true, message: '请输入图标', trigger: 'blur' }]
})

const formRef = ref(null) // 创建表单引用

const saveSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      // 保存逻辑
    }
  })
}
</script>

```



## 间距

表单组件之间的间距可以在CSS中设置

```CSS
.el-form-item{
    margin-bottom: 0px;
}
```

![img](Element%E7%AC%94%E8%AE%B0.assets/1485587-20210225172314721-1766672078.png)
![img](Element%E7%AC%94%E8%AE%B0.assets/1485587-20210225172330388-742064165.png)
![img](Element%E7%AC%94%E8%AE%B0.assets/1485587-20210225172342937-1771071671.png)

![img](Element%E7%AC%94%E8%AE%B0.assets/1485587-20210225172450470-878695081.png)![img](Element%E7%AC%94%E8%AE%B0.assets/1485587-20210225172501156-1417811875.png)

# 上传

![image-20240405134004412](Element%E7%AC%94%E8%AE%B0.assets/image-20240405134004412.png)

  :file-list="files" 绑定的文件列表

 :limit="1" 和    :on-exceed="onExceed" 结合, 当文件数量超过1时, 调用自定义onExceed函数

```
//:http-request="elUploadFunc" 
//覆盖默认的 Xhr 行为 //调用submit会自动调用http-request的函数(elUploadFunc)
const elUploadFunc = (domParam: any) => {
  // 
};
```

```
//:on-success="successUpCover"
const successUpCover = (res) => {
  //res就是<el-upload>中url响应结果

};
```

## 例子

```js
<!-- 图片上传组件 -->
<template>
  <div class="upload-img">
    <p class="title">上传封面:</p>
    <el-upload
      class="upload-demo"
      ref="uploadCover"
      drag="true"
      multiple="false"
      :limit="1"
      list-type="picture"
      :action="path"
      :headers="headers"
      :auto-upload="false"
      :on-success="successUpCover"
      :on-error="errorUpCover"
      :on-change="onChangeCover"
      :on-remove="onRemove"
      :on-exceed="onExceed"
      :before-upload="beforeUpload"
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">
        拖拽至此或者<em>点击上传</em>
        只能上传一张封面,支持(jpg/jpeg/png),不超过500kb
      </div>
      <template #tip>
        <!-- <div class="el-upload__tip">大小小于500kb的Jpg /png文件</div> -->
      </template>
    </el-upload>
    <el-button
      style="margin-left: 10px; margin-right: 10px; margin-bottom: 1rem"
      type="success"
      @click="coverSubmit"
      >上传文件</el-button
    >
  </div>
</template>

<script setup lang="ts">
import Vue from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import type { UploadInstance } from "element-plus";
import { getAccessToken } from "/@/utils/auth";

//提交文章
const uploadCover = ref<UploadInstance>(); //绑定<el-upload></el-upload>
const path = ref(import.meta.env.VITE_APP_BASE_API + "/file/upload"); // 上传图片接口
const limitSize = 500 * 1024; //封面大小限制500kb   3 * 1024 * 1024; 限制3MB
const coverTypeArr = ["image/jpeg", "image/jpg", "image/png", "image/webp"];

const headers = computed(() => {
  return { Authorization: "Bearer " + getAccessToken() }; //上传文件请求头
});

// 子->父
const emits = defineEmits(["uploadSuccess"]);

//封面上传成功的钩子
type Status = {
  status: number;
  code: number;
  message: string;
};
const successUpCover = (res) => {
  // 这里是:action="path" 之后, 自己服务响应结果
  if (res.code !== 200000) {
    ElMessage.error("文件上传失败");
    return;
  }
  // console.error(res.data);
  emits("uploadSuccess", res.data); // 传父  //element-plus 中el-upload 上传成功返回URL
};

//文件列表移除文件时的钩子
const onRemove = (e: any) => {};

//封面上传失败的钩子
const errorUpCover = () => {
  ElMessage.error("封面上传失败");
};

//封面超出上传限制
const onExceed = (e: any) => {
  ElMessage.error("封面超出上传限制，上传失败");
};

//文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
const onChangeCover = (e: any) => {
  //文件格式校验
  if (!coverTypeArr.includes(e.raw.type)) {
    ElMessage({
      showClose: true,
      message: "选择的文件格式错误，错误的格式将不会被上传",
      type: "error",
    });
    return;
  }
  // 文件大小效验
  const isLtSize = e.size > limitSize;
  if (isLtSize) {
    ElMessage.error("文件大小不能大于" + limitSize);
  }
};

//上传前钩子
const beforeUpload = (e: any) => {};

const coverSubmit = () => {
  uploadCover.value!.submit();
};
</script>

<style lang="less" scoped>
// 上传封面
.upload-img {
  margin-top: 2rem;

  display: flex;
  flex-direction: column;
  .title {
    margin-left: 1rem;
    margin-right: 1rem;
    margin-bottom: 1rem;
  }
  .upload-demo {
    margin-left: 1rem;
    margin-right: 1rem;
  }

  .el-upload__tip {
    margin-left: 1rem;
  }
}
</style>

```

**自己服务器进行oss存储**

```js
// 封面上传成功，删除原图片
const coverUploadSuccess = (url) => {
  const oldCover = articleWrite.cover;
  articleWrite.cover = url;
  const params = {
    fullPath: oldCover,
  };
  if (oldCover) {
    deleteFile(params);  // 
  }
  ElMessage({
    message: "封面上传成功",
    type: "success",
  });
};
```



# 标签

span 天然不换行, 有时候方便

![image-20240405160208288](Element%E7%AC%94%E8%AE%B0.assets/image-20240405160208288.png)



```
<span
          class="labelTag no-choose"
          @click="chooseTag(tag)"
          v-for="tag in allTags"
          :key="tag.id"
          >{{ tag }}</span
        >
```

```css
    .no-choose {
    color: black;
    background-color: #fff;
    border: .1rem solid black;
    cursor: pointer;
    transition: all .1s;

    &:hover {
        box-shadow: .2rem .2rem var(--box-shadow);
        transform: translateY(-0.1rem);
    }
```

# 加载

在绑定了`v-loading`指令的元素上添加`element-loading-text`属性，其值会被渲染为加载文案，并显示在加载图标的下方。 

比如:

```
  <ul
    class="note-list"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="#fff"
  >
  </ul>
```

![image-20240408155245963](Element%E7%AC%94%E8%AE%B0.assets/image-20240408155245963.png)

loading=true 时显示

## 应用

```vue
 <div
          v-show="current === 1 && loading"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="#fff"
          style="color: #fff; width: 100%; height: 100px"
        >
          正在加载
        </div>
      </div>
      <div v-if="!loading && commentList.length === 0" class="list-empty">
        还没有评论哦~
      </div>



<script lang="ts" setup>
const loading = ref(false); //默认不显示加载动画

// 获取分页数据
const pageComment = () => {
   loading.value = true;  // 显示加载动画
  const params = {
    articleId: props.articleId,
    current: current.value,
    size: size.value,
  };

  apipageComment(params).then(
    (res) => {
      loading.value = false;
      total.value = res.total;
      const commentListData1 = res.records;
      commentListData1.forEach((comment) => {
        comment.del_visible = false;
        comment.replyList.forEach((reply) => {
          reply.del_visible = false;   // 关闭显示加载动画
        });
      });
      commentList.value = commentListData1;
      console.error(commentList.value.length);
    },
    (error) => {
      console.error(error);
      loading.value = false;
    }
  );
};

</script>
```



# 边框

```
box-sizing: border-box;
```

```
.element {
  border: 2px solid #000; /* 设置边框宽度为 2px，实线样式，颜色为黑色 */
}

.element {
  border-style: solid; /* 边框样式为实线 */
  border-width: 2px; /* 边框宽度为 2px */
  border-color: #000; /* 边框颜色为黑色 */
}

.element {
  border-bottom: 2px solid #000; /* 底边框为 2px 实线，颜色为黑色 */
}
```



如果你想在 CSS 中设置 `border-bottom`，并且使用 `hsla` 来定义颜色，那么你可以根据以下示例来设置：

```
cssCopy Code.my-element {
    border-bottom: 1px solid hsla(0, 0%, 10.2%, 0.1);
}
```

这段代码在 `.my-element` 类的元素上设置了底部边框，边框的颜色是半透明的深灰色，透明度为 0.1。你可以根据需要调整 HSLA 参数中的色相、饱和度、亮度和透明度，来获得你想要的颜色效果。

在 HSLA 中：

- 第一个值是色相（Hue），通常取 0 到 360 度。
- 第二个值是饱和度（Saturation），取 0% 到 100%。
- 第三个值是亮度（Lightness），也取 0% 到 100%。
- 第四个值是透明度（Alpha），取 0 到 1。

如果你希望加深颜色，你可以增加亮度值或者将透明度调高，例如：

```
cssCopy Code.my-element {
    border-bottom: 1px solid hsla(0, 0%, 10.2%, 0.5);
}
```

这样会让底部边框更显眼。你可以根据实际效果来调整这些值。



# 阴影

```
  box-shadow: 0 0 0.5rem 0.2rem var(--gray-light-sahdow);
  // 鼠标悬停
  &:hover {
    box-shadow: 0.1rem 0.1rem 0.5rem var(--gray-sahdow); //阴影
  }
  // 向上动画
    &:hover {
    cursor: pointer;
    transform: translateY(-.3rem);
    }
```

# 圆角

```
  border-radius: 0.5rem;
```

只设置某个角的圆角

```
.element {
  border-top-left-radius: 1rem;
  border-top-right-radius: 2rem;
  border-bottom-left-radius: 0.5rem;
  border-bottom-right-radius: 0.5rem;
}
```

圆

```
    width: 4rem;
    height: 4rem;
    border-radius: 50%;
```



# 手指样式

```css
   cursor: pointer;
```

# 伪元素

```css
  // 鼠标悬停效果
  &:hover {
    color: @color !important;
  }
```



```css
.right-solt:after {
        content: "·";
        margin: 0 5px;
        color: #b2bac2;
}
```

这段 CSS 代码是为具有类名 `.right-solt` 的元素的伪元素 `::after` 添加样式。

- `content: "·";`：设置伪元素的内容为 "·"，即一个小圆点。这个小圆点将在匹配元素的后面显示。
- `margin: 0 5px;`：设置伪元素的外边距为 0 上下方向，5 像素左右方向。这将在伪元素前后添加 5 像素的间距，使得小圆点与其他内容之间有一定的间隔。
- `color: #b2bac2;`：设置小圆点的颜色为 `#b2bac2`，即浅灰色。这将给小圆点赋予指定的颜色。

![image-20240408162137682](Element%E7%AC%94%E8%AE%B0.assets/image-20240408162137682.png)



在 CSS 中，`:nth-child()` 伪类是用来选择父元素下的特定子元素的，而不是直接在类名上使用的。如果你想要对 `.nameAndTme` 下的第一个和第二个 `<span>` 元素分别应用样式，可以这样做：

```
.nameAndTme span:nth-child(1) {
  font-weight: 600;
  font-size: 1.3rem;
  color: red;
}

.nameAndTme span:nth-child(2) {
  color: #333;
  margin-left: 0.5rem;
}
```

这样就能正确地选择`.nameAndTme`下的第一个和第二个 `<span>` 元素，并且应用相应的样式了。



```css
{
  position: fixed;
  top: 0;
  width: 100%;
  height: 60px;
  backdrop-filter: blur(5px);
  transition: background-color $move;
  z-index: 999;

  &::after {
    content: "";
    display: block;
    width: 100%;
    height: 0;
    background: linear-gradient(#fff, transparent 70%);
    transition: background $move;
  }
```

根据你提供的代码片段，这是一个描述一个固定在页面顶部的导航栏的CSS样式。让我解释一下每个属性的作用：

1. `position: fixed;` - 将元素固定在视口中，不随页面滚动而移动。
2. `top: 0;` - 将元素定位在距离顶部的位置为0。
3. `width: 100%;` - 设置元素宽度为100%。
4. `height: 60px;` - 设置元素高度为60像素。
5. `backdrop-filter: blur(5px);` - 应用模糊效果到元素背后内容，模糊半径为5像素。
6. `transition: background-color $move;` - 设置背景颜色变化时的过渡效果，$move 可能是一个变量，用来控制过渡时间或速度。
7. `z-index: 999;` - 设置元素的堆叠顺序，使其位于其他元素之上。

接下来是伪元素 `&::after` 的样式：用于创建一个元素的最后一个子元素，并且通常用于添加一些装饰性的内容或样式

1. `content: "";` - 声明伪元素的内容为空。
2. `display: block;` - 将伪元素设置为块级元素。
3. `width: 100%;` - 设置伪元素宽度为100%（与父元素宽度相同）。
4. `height: 0;` - 初始时将伪元素高度设为0。
5. `background: linear-gradient(#fff, transparent 70%);` - 使用线性渐变设置伪元素的背景，从白色到透明，覆盖高度的70%。
6. `transition: background $move;` - 设置背景变化时的过渡效果，$move 可能是一个变量，用来控制过渡时间或速度。

# 列表

## 例子1

![image-20240408170919584](Element%E7%AC%94%E8%AE%B0.assets/image-20240408170919584.png)

```vue
<template>
  <ul
    class="note-list"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="#fff"
  >
    <transition-group name="fade-list">
      <li v-for="(item, index) in list" :key="index" class="list-item">
         <!-- 封面 -->
        <div class="wrap-img">
          <img :src="item.cover" />
        </div>
        <!-- 用户  -->
        <div class="wrapper-meta">
          <div class="avatar-wrapper">
            <img class="user-avatar" :src="item.user.avatar" />
          </div>
          <span class="right-solt">{{ item.user.nickname }}</span>
          <span class="right-solt">{{ formatD(item.publishTime) }}</span>
          <span class="active" @click="categoryClick(item.categoryId)">{{
            item.categoryName
          }}</span>
        </div>
        <!-- 文章内容 -->
        <div class="content">
          <router-link :to="'/article/' + item.id" class="title"
            ><span v-if="item.original !== 1">【转载】</span>
            {{ item.title }}</router-link
          >
          <p class="abstract multi-ellipsis--l3">{{ item.summary }}</p>
          <!-- 文章标签 -->
          <div class="tags-wrapper">
            <span
              v-for="(tag, index2) in item.tagList"
              :key="index2"
              class="tag active btn"
              @click="tagClick(tag.id)"
            >
              {{ tag.name }}
            </span>
          </div>
          <!-- 评论点赞收藏浏览 -->
          <div class="meta">
            <span>{{ item.commentCount }}&ensp;评论</span>
            <span>{{ item.likeCount }}&ensp;点赞</span>
            <span>{{ item.collectCount }}&ensp;收藏</span>
            <span>{{ item.viewCount }}&ensp;浏览</span>
          </div>
        </div>
      </li>
    </transition-group>
    <div v-show="list.length === 0 && !loading" class="list-empty">
      列表为空
    </div>
  </ul>
</template>

<script setup lang="ts">
import { formatDate } from "/@/utils/format/format-time";
import { useRouter } from "vue-router";

const router = useRouter();

// 传参 props
const props = defineProps({
  list: {
    type: Array,
    default() {
      return [];
    },
  },
  loading: {
    type: Boolean,
    default: true,
  },
});

const formatD = (str: string): string => {
  //2024-04-08 15:03:51 -> 2024/04/08 15:03:51
  str = str.replace(/-/g, "/");
  const date = new Date(str);
  const now = new Date();
  // 是当前年,就不显示年份
  return date.getFullYear() === now.getFullYear()
    ? formatDate(new Date(str), "mm月dd日")
    : formatDate(new Date(str), "YYYY年mm月dd日");
};

const categoryClick = (id: string) => {
  if (router.currentRoute.value.path !== "/category") {
    router.push({
      path: "/category",
      query: { id: id },
    });
  }
};

const tagClick = (id: string) => {
  if (router.currentRoute.value.path !== "/tag") {
    router.push({
      path: "/tag",
      query: { id: id },
    });
  }
};
</script>


<style lang="scss" scoped>
.note-list {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  min-height: 50px;
  background-color: #fff;
  border-radius: 1rem;

  .list-item {
    //   &:hover {
    //     cursor: pointer;
    //     transform: translateY(-.1rem);

    // }

    position: relative;  /*相对定位*/
    min-height: 200px;
    width: 100%;
    border-bottom: 1px solid #1413132f;
    padding: 15px 2px 15px 30px;
    margin-top: 5px;

    @media screen and (max-width: 922px) {
      padding-left: 15px;
      width: 100vw;
    }

    .active {
      cursor: pointer;
      &:hover {
        color: #007fff;
      }
    }
    
    // 封面
    .wrap-img {
      position: absolute; /*绝对定位 根据父类偏移距离 left right top bottom*/
      width: 150px;
      height: 90px;
      top: 50%;
      transform: translateY(-50%);
      right: 20px;

      overflow: hidden;
      border-radius: 4px;
      border: 1px solid #f3f7fa;

      @media screen and (max-width: 922px) {
        width: 100px;
        height: 60px;
        right: 10px;
      }

      img {
        width: 100%;
        height: 100%;
      }
    }

    //用户
    .wrapper-meta {
      font-size: 14px;
      font-weight: 700;
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      .avatar-wrapper {
        position: relative;
        display: inline-block;
        margin-right: 5px;

        img {
          width: 30px;
          height: 30px;
          border-radius: 50%;
          border: 1px solid rgba(0, 0, 0, 0.1);
        }
      }

      .right-solt:after {
        content: "·";
        margin: 0 5px;
        color: #b2bac2;
      }
    }

    // 文章内容
    .content {
      width: 100%;
      padding-right: 180px;

      @media screen and (max-width: 922px) {
        padding-right: 120px;
      }

      .title {
        margin: -7px 0 4px;
        display: inline-block;
        font-size: 18px;
        font-weight: 700;
        line-height: 1.5;
        color: #2f2f2f;

        @media screen and (max-width: 922px) {
          width: 100%;
          font-size: 15px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }

        &:hover {
          text-decoration: underline;
        }
      }

      .abstract {
        margin: 0;
        font-size: 13px;
        line-height: 24px;
        color: #999;
        margin-bottom: 5px;

        @media screen and (max-width: 922px) {
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          text-overflow: ellipsis;
          overflow: hidden;
        }
      }

      .tags-wrapper {
        font-size: 11px;
        margin-bottom: 15px;
        display: flex;
        flex-wrap: wrap;

        .tag {
          border: 1px #999 solid;
          border-radius: 14px;
          padding: 5px 12px;
          margin-right: 3px;

          &:hover {
            border: 1px #007fff solid;
          }
        }
      }

      .meta {
        padding-right: 0 !important;
        font-size: 12px;
        font-weight: normal;
        line-height: 20px;

        span {
          margin-right: 10px;
        }
      }
    }
  }

  .list-empty {
    background: #fff;
    width: 100%;
    height: 100px;
    line-height: 100px;
    border-bottom: 1px solid #f0f0f0;
    text-align: center;
  }
}
</style>

```





##  例子2 ul li

li标签 天然垂直排, 有时候用在方便

![image-20240411143205495](Element%E7%AC%94%E8%AE%B0.assets/image-20240411143205495.png)

```
    <!-- 分享  -->
    <div class="share">
      <ul class="extra-cnt">
        <li>
          <div class="item">
            <a
              target="_blank"
              :href="
                'https://connect.qq.com/widget/shareqq/index.html?url=' +
                codedUrl +
                '&title=' +
                codedTitle +
                '&summary=&style=101&width=96&height=24'
              "
            >
              <svg-icon
                name="qq-login"
                color="green"
                width="24"
                height="24"
              ></svg-icon>
            </a>
          </div>
        </li>

        <li>
          <div class="item">
            <el-popover
              placement="left"
              trigger="hover"
              popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
            >
              <template #reference>
                <svg-icon
                  name="wx2"
                  color="green"
                  width="24"
                  height="24"
                ></svg-icon>
              </template>
              <template #default>
                <div
                  style="
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    :src="
                      'https://api.qrserver.com/v1/create-qr-code/?size=90x90&data=' +
                      codedUrl
                    "
                  />
                </div>
              </template>
            </el-popover>
          </div>
        </li>

        <li
          v-clipboard:copy="url"
          v-clipboard:success="copySuccess"
          class="cp-warpper"
        >
          <div class="item">
            <a>
              <svg-icon name="copy2" width="24" height="24" />
            </a>
          </div>
        </li>
        <ul />
      </ul>
    </div>
```



```
  // 分享
  .extra-cnt {
    position: fixed;
    color: #b2bac2;
    background: #fff;
    top: 420px;
    width: 52px;

    li {
      padding: 10px 0;
      position: relative;

      &:before {
        content: "";
        position: absolute;
        left: 50%;
        bottom: 0;
        width: 20px;
        height: 1px;
        background-color: #e5e5e5;
        margin-left: -10px;
      }

      &:hover {
        //  box-shadow: 0 0 0.1rem 0.1rem var(--special-font-color);
        color: #007fff;
      }

      .item {
        display: flex;
        justify-content: center;
      }
    }
  }
```



# 自适应地调整位置

这两行 CSS 规则使用了 CSS 的 `calc()` 函数，用于进行简单的数学计算并生成动态的样式。让我解释一下这两行规则的含义：

1. `right: calc(100% - 420px);`
   - 这行规则表示将元素的右边缘位置设置为当前容器宽度减去 420 像素。换句话说，元素的右边缘将距离容器右边缘 420 像素的位置。
2. `left: calc(calc(100% - 1040px) / 2);`
   - 这行规则稍微复杂一些。首先，内部的 `calc(100% - 1040px)` 表示将当前容器宽度减去 1040 像素。然后，外部的 `calc(... / 2)` 将前面的结果除以 2，也就是将得到的值除以 2。这意味着元素的左边缘将距离容器左边缘的中心位置（容器宽度减去 1040 像素的一半）的距离。

这两行规则的作用是实现元素相对于容器的动态布局，使得元素可以根据容器的尺寸自适应地调整位置。



# div图片

```
      <div v-if="!loading" class="content-container">
        <div class="coverImg">
          <img :src="article.cover" alt="" />
          <div class="mask">
          </div>
        </div>
      </div>
      
      
 .content-container {
  .coverImg {
    // width: 80%;
    margin: 0 auto;
    // display: flex
    // justify-content: center;
    height: 10rem;
    // position: relative;
    border-radius: 1rem;
    // overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
}
      
      
```

## Vue 3中实现随机显示图片

```vue
<!-- RandomImage.vue -->
<template>
  <div>
    <img :src="randomImageSrc" alt="Random Image" />
    <button @click="changeImage">换一张</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      images: [
        '/img/image1.jpg',
        '/img/image2.jpg',
        '/img/image3.jpg',
        // 添加更多图片路径
      ],
      currentImageIndex: 0
    };
  },
  computed: {
    randomImageSrc() {
      return this.images[this.currentImageIndex];
    }
  },
  methods: {
    changeImage() {
      // 生成一个随机的索引值
      const newIndex = Math.floor(Math.random() * this.images.length);
      // 确保新的索引不与当前索引相同
      if (newIndex !== this.currentImageIndex) {
        this.currentImageIndex = newIndex;
      } else {
        // 如果新的索引与当前索引相同，递归调用changeImage方法，直到索引不同为止
        this.changeImage();
      }
    }
  }
};
</script>
```

在你的Vue应用中使用这个组件：

```vue
htmlCopy Code<template>
  <div>
    <RandomImage />
  </div>
</template>

<script>
import RandomImage from './RandomImage.vue';

export default {
  components: {
    RandomImage
  }
};
</script>
```



# Popover 弹出框(气泡卡片)

![image-20240416184524916](Element%E7%AC%94%E8%AE%B0.assets/image-20240416184524916.png)

![image-20240411142358276](Element%E7%AC%94%E8%AE%B0.assets/image-20240411142358276.png)



```html
 <el-popover
             placement="left"
             trigger="hover"
             popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
             >
     <template #reference>
         <svg-icon
                   name="wx2"
                   color="green"
                   width="24"
                   height="24"
                   ></svg-icon>
     </template>
     <template #default>
         <div
              style="
                     display: flex;
                     justify-content: center;
                     align-items: center;
                     "
              >
             <img
                  :src="
                        'https://api.qrserver.com/v1/create-qr-code/?size=90x90&data=' +
                        codedUrl
                        "
                  />
         </div>
     </template>
</el-popover>
```

![image-20240411142430241](Element%E7%AC%94%E8%AE%B0.assets/image-20240411142430241.png)

# Popconfirm 气泡确认框

[Popconfirm 气泡确认框 | Element Plus (element-plus.org)](https://element-plus.org/zh-CN/component/popconfirm.html#popconfirm-气泡确认框)

![image-20240602162244563](Element%E7%AC%94%E8%AE%B0.assets/image-20240602162244563.png)

# 显示滚动条

```
  max-height: 400px; /* 设置最大高度 */
    overflow-y: auto; /* 当内容溢出时显示滚动条 */
```

```
  .main {
    overflow-y: scroll;
    height: 100%;
  }
```



```css

//滚动条样式
::-webkit-scrollbar {
  width: .5rem;
  height: .5rem;
  background: rgba(255, 255, 255, 0.6);
}

::-webkit-scrollbar-track {
  border-radius: 0;
}

::-webkit-scrollbar-thumb {
  border-radius: 0;
  background-color: rgb(218, 218, 218);
  transition: all .2s;
  border-radius: .5rem;

  &:hover {
    background-color: rgb(172, 172, 172);
  }
}
```

![image-20240412112951316](Element%E7%AC%94%E8%AE%B0.assets/image-20240412112951316.png)



# 背景

公共背景

```
/* ~ %相对于父级所以body html也要设置，尽量不混用 % vh vw ，容易产生未知错误 */
html,
body,
#app {
  width: 100%;
  height: 100%; 
  background-image: linear-gradient(to top, #FFE6FA 65%, #2580B3 100%);
  background-attachment: fixed;
  /* background-color: #54befc; */
  font-size: 13px;
  font-family: '思源黑体 Normal', 'Microsoft YaHei', '黑体';
}
```

解决卡顿问题

```html
<div class="content"
    :style="{ backgroundImage: `url(/image/imgs/1.png)` }">
    
</div>   

.content {
  background: url("") center;
  background-size: cover;

  // 标签加载之后
  &::after {
    content: "";
    width: 100%;
    height: 20%;
  }
}
```





# 将页脚固定在页面底部

当一个HTML页面中含有较少的内容时，Web页面的“footer”部分随着飘上来，处在页面的半腰中间，给视觉效果带来极大的影响，让你的页面看上去很不好看，特别是现在宽屏越来越多，这种现象更是常见。

![img](Element%E7%AC%94%E8%AE%B0.assets/stickyfooter.png)



`.app-container` 元素被设置为 `position: relative;`，这是因为我们要将 `<foot></foot>` 设置为绝对定位，并相对于 `.app-container` 定位。然后，`.foot` 元素被设置为 `position: absolute;`，`bottom: 0;` 表示距离父元素底部为 0，这样它就会固定在页面底部。

```vue
<template>
  <div class="app-container">
  <navigation></navigation>
  <el-config-provider :locale="zhCn">
    <!-- 路由视图 -->
    <router-view></router-view>
  </el-config-provider>
  <foot class="foot"></foot>
    </div>
</template>


<style lang="less">


.app-container {
 position: relative;
  min-height: 100vh;
}


.foot {
  /* Footer 组件样式 */
  position: absolute;
  bottom: 0;
  width: 100%;
}
</style>
```

## 方法

需求：当页面高度不足一屏时需要footer固定显示在页面底部，而页面内容超过一屏时需要footer紧跟在页面内容的最后。

思路：通过获取 网页可见区域高度：document.body.clientHeight;设置内容区域的最小高度，从而曲线救国使footer置底。

代码：

```vue
<template>
    <div id="app">
        <Header></Header>
        <div id="v-content" v-bind:style="{minHeight: Height+'px'}"><router-view /></div>
        <Footer></Footer>
    </div>
</template>

<script>
export default {
  name: 'App',
  data() {
     return {
       Height: 0
     }
  },
  mounted(){
    //动态设置内容高度 让footer始终居底   header+footer的高度是100 //可以修改
    this.Height = document.documentElement.clientHeight - 100;  
　　//监听浏览器窗口变化　
    window.onresize = ()=> {this.Height = document.documentElement.clientHeight -100}
  }
}
</script>
```



```vue
<template>
  <div class="flex flex-col min-h-screen">
    <navigation></navigation>
    <el-config-provider :locale="zhCn">
      <!-- 路由视图 -->
      <router-view class="view"></router-view>
    </el-config-provider>
    <foot class="foot" :style="{ position: footerPosition }"></foot>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

const footerPosition = ref('fixed');

const updateFooterPosition = () => {
  const windowHeight = document.documentElement.clientHeight;
  const documentHeight = document.documentElement.scrollHeight;
  const contentHeight = documentHeight - windowHeight;

  if (contentHeight <= 0) {
    // 当页面内容不足一屏时，页脚固定显示在页面底部
    footerPosition.value = 'fixed';
  } else {
    // 当页面内容超过一屏时，页脚跟随内容在页面的最底部
    footerPosition.value = 'static';
  }
};

onMounted(() => {
  updateFooterPosition();
  window.addEventListener('resize', updateFooterPosition);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', updateFooterPosition);
});
</script>

<style scoped>
/* 可以添加页脚样式 */
</style>

```



# 在线生成二维码

### 搜狐视频 API

[https://my.tv.sohu.com/user/a/wvideo/getQRCode.do?text=http://192.168.1.88&width=500&height=500](https://my.tv.sohu.com/user/a/wvideo/getQRCode.do?text=https://192.168.1.88&width=500&height=500)

width：图片宽

height：图片高

![image-20240414115733844](Element%E7%AC%94%E8%AE%B0.assets/image-20240414115733844.png)

# 表情

[💖 闪亮的爱心 表情符号 — 含义和用法 (emojis.wiki)](https://emojis.wiki/zh/闪亮的爱心/)

[GetEmoji - Copy & Paste All Emojis From The Emoji Keyboard - No apps required](https://getemoji.com/)

1. 使用 Unicode 字符：可以使用 Unicode 字符 \u{1F618} 来表示“飞吻”表情符号。

```
htmlCopy Code<span>&#x1F618;</span>
```

2. 使用 Emoji：直接使用 Emoji 表情字符来表示“飞吻”表情。

```
htmlCopy Code<span>😘</span>
```



## 引用

```css
      .main-tip-label {
        border-left: 5px solid #00a77c;
        border-bottom: 1px solid #00a77c;
        font-size: 18px;
        font-weight: bold;
        line-height: 2;
        color: #545454;
        padding-left: 10px;
      }

```

![image-20240415225613145](Element%E7%AC%94%E8%AE%B0.assets/image-20240415225613145.png)





# p标签文本内容自动换行

![image-20240416230154386](Element%E7%AC%94%E8%AE%B0.assets/image-20240416230154386.png)

要根据 `comment.content` 的内容来调整宽度，你可以使用 CSS 中的 `max-width` 属性。你可以为包含 `comment.content` 的元素设置 `max-width`，这样当内容超出指定宽度时，就会自动换行，而不会撑破容器。

例如，你可以为 `<p>` 标签添加一个类，并设置其 `max-width` 属性，如下所示：

```
<p class="body-text comment-content">{{ comment.content }}</p>
```

然后在你的 CSS 中定义 `.comment-content` 类，并设置 `max-width` 属性，如下所示：

```
cssCopy Code.comment-content {
  max-width: 300px; /* 设置最大宽度为 300 像素，可以根据需要进行调整 */
  /* 如果需要更多的样式，可以在这里继续添加 */
}
```

这样，无论 `comment.content` 的内容有多长，它都不会超出 300 像素的宽度，而是

# Timeline 时间线

![image-20240418132156283](Element%E7%AC%94%E8%AE%B0.assets/image-20240418132156283.png)



# 当标签内容超过容器宽度时，标签会自动换行显示，而不会挤在一起

```html
      <div @click="saveEditor" class="tag-container">
        <span
          class="labelTag"
          @click="chooseTag(tag)"
          v-for="tag in dynamicTags"
          :key="tag.id"
          >{{ tag.name }}</span
        >
      </div>
      
      .tag {
  margin-left: 1rem;
  margin-top: 1rem;
  display: flex;
  align-items: center;
}

.tag-container {
  display: flex;
  flex-wrap: wrap; /* 允许标签换行显示 */
}

.labelTag {
  margin-right: 1.5rem;
  margin-bottom: 0.2rem; /* 调整标签之间的下方间距 */
  padding: 0.2rem 1rem;
  border-radius: 0.5rem;
  font-size: 1.4rem;
  color: black;
  background-color: #fff;
  border: 0.1rem solid black;
  cursor: pointer;
  transition: all 0.1s;

  &:hover {
    box-shadow: 0.2rem 0.2rem var(--box-shadow);
    transform: translateY(-0.1rem);
  }
}
```

![image-20240421165957069](Element%E7%AC%94%E8%AE%B0.assets/image-20240421165957069.png)





# Card

## 卡片

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title></title>
  <style>
    .test {
      float: left;
      width: 25%;
      box-sizing: border-box;
      padding: 10px;
      min-width: 150px;
    }
    .container {
      width: 100%;
    }
    @media (max-width:615px) {
      .test {
        float: left;
        width: 33%;
        box-sizing: border-box;
        padding: 10px;
        min-width: 150px;
      }
    }
    @media (max-width:465px) {
      .test {
        float: left;
        width: 50%;
        box-sizing: border-box;
        padding: 10px;
        min-width: 150px;
      }
    }
    @media (max-width:315px) {
      .test {
        float: left;
        width: 100%;
        box-sizing: border-box;
        padding: 10px;
      }
    }
  </style>
</head>

<body>
  <div class="container">
    <div class="test">
      <img src="./640.webp" style="max-width: 100%;" />
    </div>
    <div class="test">
      <img src="./640.webp" style="max-width: 100%;" />
    </div>
    <div class="test">
      <img src="./640.webp" style="max-width: 100%;" />
    </div>
    <div class="test">
      <img src="./640.webp" style="max-width: 100%;" />
    </div>
  </div>
</body>
</html>

```

再放效果

一行
![image-20240427225511941](Element%E7%AC%94%E8%AE%B0.assets/image-20240427225511941.png)
第二行
<img src="Element%E7%AC%94%E8%AE%B0.assets/image-20240427225517092.png" alt="image-20240427225517092" style="zoom:67%;" />

1、用到的技术是响应式布局（就是监听页面宽度实现不同变化）
2、这里用的是float，当然用flex也是一样的
3、其实不是完美的，比如三个一排的时候是33%，所以还剩下1%在最后面，不影响大局
4、@media中的max-width值得由来：
比如这里设置的每个元素的最小宽度是150px，也就是再低于这个值就会换行，
当有四个换到三个的临界值是 150 * 4 = 600 。也就是说再小就要换行了，这个时候我们主动换行，比如上面的在615的时候就把宽度设置为33%，这样就提前完成转换，实现完美衔接。
同理三到二就是 150 * 3 = 450 。提前15px 就是 465；其它同理
5、这里计算的宽度是浏览器可视范围的宽度，所有计算上面值得时候要加上其它布局的宽度。

## el-card

```html
<div class="card-container">
  <el-card
    v-for="(item, index) in userList"
    :key="index"
    :body-style="{ padding: '0px' }"
    shadow="hover"
    class="card-item"
  >
    <!-- 卡片内容 -->
  </el-card>
</div>
```

然后，在你的 CSS 样式中添加以下代码：

```css
.card-container {
  display: flex;
  flex-wrap: wrap;
}

.card-item {
  flex: 1 1 300px; /* 控制每个卡片的宽度，这里设置为自动扩展，最小宽度为300px */
  margin: 10px; /* 调整卡片之间的间距 */
}
```

这样，`el-card` 就会根据父容器的宽度自动调整布局，实现多行排列的自适应效果。



<img src="Element%E7%AC%94%E8%AE%B0.assets/image-20240427230325857.png" alt="image-20240427230325857" style="zoom: 50%;" />

## 修改el-card中header或body样式或自定义图片样式

[修改el-card中header或body样式或自定义图片样式_el-card 样式-CSDN博客](https://blog.csdn.net/qq_50276105/article/details/127315880)

```
.el-card {
  /deep/ .el-card__header {
    background-color: lightblue;
  }
}
```



# 表格

![image-20240428111548477](Element%E7%AC%94%E8%AE%B0.assets/image-20240428111548477.png)

当 `el-table` 元素中注入 `data` 对象数组后，在 `el-table-column` 中用 `prop` 属性来对应对象中的键名即可填入数据，用 `label` 属性来定义表格的列名。 可以使用 `width` 属性来定义列宽。

## 显示图片

**详情使用见** 

![image-20240428165931657](Element%E7%AC%94%E8%AE%B0.assets/image-20240428165931657.png)



例子

```html
<el-table :data="dataList" :border="true">
                <el-table-column type="index" width="50px"></el-table-column>
                <el-table-column label="图片" width="150px">
                    <template #default="scope">
                        <el-image :src="scope.row.imgUrl" style="width:100px ;"></el-image>
                        <div style="text-align: center;width: 100px;">美景</div>
                    </template>
                </el-table-column>
            </el-table>
```

![image-20240428113439739](Element%E7%AC%94%E8%AE%B0.assets/image-20240428113439739.png)

## 合并行

#### 个人查看element-plus文档，去结合使用 [链接]([Table 表格 | Element Plus (element-plus.org)](https://link.juejin.cn/?target=https%3A%2F%2Felement-plus.org%2Fzh-CN%2Fcomponent%2Ftable.html%23%E5%90%88%E5%B9%B6%E8%A1%8C%E6%88%96%E5%88%97))

element文档中有“合并行或列”的例子： 多行或多列共用一个数据时，可以合并行或列。

通过给 table 传入`span-method`方法可以实现合并行或列， 方法的参数是一个对象，里面包含当前行` row`、当前列 `column`、当前行号` rowIndex`、当前列号 `columnIndex` 四个属性。 该函数可以返回一个包含两个元素的数组，第一个元素代表 `rowspan`，第二个元素代表 `colspan`。 也可以返回一个键名为` rowspan` 和` colspan` 的对象；

```html
<template>
    <el-table
        :data="data"
        border
        :span-method="objectSpanMethod"
    >
        <el-table-column type="selection" width="55" />
        <el-table-column label="#" width="60" prop="">
            <template #default="{ $index }">
                {{ $index + 1 }}
            </template>
        </el-table-column>
        <el-table-column
            label="指标组合名称"
            align="center"
            prop="userName"
            show-overflow-tooltip
        />
        <el-table-column
            label="已使用使用指标"
            align="center"
            prop="value1"
            show-overflow-tooltip
        />
        <el-table-column
            label="权重"
            align="center"
            prop="value2"
            show-overflow-tooltip
        />
        <el-table-column
            label="计算状态"
            align="center"
            prop="detectionTime"
            show-overflow-tooltip
        /> 
        <el-table-column label="操作" width="160" resizable>
            <template #default="scope">
                <el-buttontype="text">删除</el-button>
            </template>
       </el-table-column>
    </el-table>
</template>
```



```javascript
import type { TableColumnCtx } from 'element-plus';
//举例数据格式
const data = [
    {
        userName: "营业收费系统",
        value1: "中国农业银行",
        value2: "2022-03",
        detectionTime: "2022-03-04",
    },
    {
        userName: "营业收费系统",
        value1: "中国农业银行",
        value2: "2022-03",
        detectionTime: "2022-03-04",
    },
    {
        userName: "营业收费系统",
        value1: "中国农业银行",
        value2: "2022-03",
        detectionTime: "2022-03-04",
    },
    {
        userName: "营业收费系统",
        value1: "中国农业银行",
        value2: "2022-03",
        detectionTime: "2022-03-04",
    }
 ];
 
//需要判断的属性组
const spanProps = ['userName', 'detectionTime'];
 
let rowSpansMap = new Map(); //存需要开始合并的行号，向下合并多少行
 
/**
 * 根据列表数据得出需要合并的行
 * @param data 列表数据
 */
const spanPropGroup = (data: any) => {
  let oldRow: any = null; //需要合并的行
  rowSpansMap = new Map(); //重置Map
 
  oldRow = data[0]; //默认第0行为需要合并的行
  rowSpansMap.set(0, 1); //第0行，向下合并一行(其实就是自己单独一行)
  let spanRow = 0; //记录需要开始合并的行号
  for (let i = 1; i < data.length; i++) {
    const item = data[i];
    let isSame = true;
    //遍历需要判断的属性判断对应值是否全部相等
    for (let j = 0; j < spanProps.length; j++) {
      const prop = spanProps[j];
      //只要有一个属性值不相等则记录新的需要合并的行号
      if (item[prop] != oldRow[prop]) {
        oldRow = item;
        rowSpansMap.set(i, 1);
        spanRow = i;
        isSame = false;
        break;
      }
    }
    //如果所有属性值相同则所需要合并的行数+1
    if (isSame) {
      let span = rowSpansMap.get(spanRow);
      rowSpansMap.set(spanRow, span + 1);
    }
  }
};
 
const objectSpanMethod: any = ({ row, column, rowIndex, columnIndex }) => {
  //采样值1-5列所对应的行不需要合并
  if (columnIndex != 3 && columnIndex != 4) {
    //根据当前行号从map中获取开始合并的行根据当前行号从map中获取开始合并的行号，向下合并多少行
    const span = rowSpansMap.get(rowIndex);
    if (span != null) {
      return {
        rowspan: span, //向下合并span行
        colspan: 1,
      };
    } else {
      return {
        rowspan: 0,
        colspan: 0,
      };
    }
  }
};
//进行传递数据
spanPropGroup(data);
```

[ElementuiPlus的table组件实现行拖动与列拖动_element-plus-table-dragable-CSDN博客](https://blog.csdn.net/qq_52845451/article/details/134205694)



# 响应式布局的实现方式（5种）

## 1、百分比布局

> 百分比是相对于 包含块 的计量单位，通过对属性设置百分比来适应不同的屏幕
>
> **包含块：**
>
> \1. 有父元素相对于父元素
>
> \2. 无父元素相对于视口
>
> \3. 或者继承于父元素

```xml
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title> %和rem 布局</title>
    <style>
      html {
        font-size: 30px;
      }
      .box2 {
        width: 10rem;
        height: 10rem;
        background-color: plum;
      }
      .box {
        width: 80%;
        height: 200px;
        background-color: aquamarine;
      }
      .part {
        width: 80%;
        height: 80%;
        background-color: pink;
      }
    </style>
  </head>
  <body>
    <div class="box">
      <div class="part"></div>
    </div>
    <div class="box2"></div>
  </body>
</html>
```

## 2、rem布局 

> ##  rem（font size of the root element）是指相对于根元素的字体大小的单位，rem只是一个相对单位
>
> ​      题外： rem和em的对比
>
> ​        \1. rem和em都是相对单位
>
> ​        \2. rem相对于根元素
>
> ​        \3. em相对于父元素
>
> ​      情景描述：
>
> ​      \1. 设置HTML的根元素的font-size为20px
>
> ​      \2. 设置红色的正方形宽高为2rem
>
> ​      \3. 设置绿色的正方形宽高为40px
>
> ​      \4. 从图中可以看出两个正方形一样宽高
>
> ​      \5. 所以1rem === 根元素字号 === 20px

```xml
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>rem布局</title>
    <style>
      html {
        font-size: 20px;
      }
      .r1 {
        width: 2rem;
        height: 2rem;
        background-color: plum;
      }
      .r2 {
        width: 40px;
        height: 40px;
        background-color: aquamarine;
      }
    </style>
  </head>
  <body>
    <div class="r1"></div>
    <div class="r2"></div>
  </body>
</html>
```

<img src="Element%E7%AC%94%E8%AE%B0.assets/image-20240506224151702.png" alt="image-20240506224151702" style="zoom: 50%;" />



## 3 媒体查询 @media screen        

```xml
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>@media screen</title>
    <style>
      .box {
        width: 10rem;
        height: 10rem;
        background-color: pink;
        margin-left: 20rem;
      }
      @media screen and (min-width: 1200px) {
        html {
          font-size: 20px;
        }
      }
      @media screen and (max-width: 1200px) {
        html {
          font-size: 10px;
        }
      }
    </style>
  </head>
  <body>
    <div class="box"></div>
  </body>
</html>
```



## **4 flex布局** 

```html
<div
        class="edit-head-right"
        style="display: flex; justify-content: space-between"
      >
        <el-select v-model="articleWrite.original" class="col" style="flex: 1">
          <el-option
            v-for="(option, index) in options"
            :key="index"
            :label="option.name"
            :value="option.value"
          />
        </el-select>

        <el-select
          v-model="articleWrite.categoryId"
          class="col"
          placeholder="选择分类"
          style="flex: 1"
        >
          <el-option
            v-for="(category, index) in categories"
            :key="index"
            :label="category.name"
            :value="category.id"
          />
        </el-select>

        <el-button
          class="col"
          type="warning"
          :loading="loading1"
          @click="save(1)"
          style="flex: 1"
          >保存</el-button
        >
        <el-button
          class="col"
          type="danger"
          :loading="loading"
          @click="save(0)"
          style="flex: 1"
          >发布</el-button
        >
      </div>
   </div>
```

![image-20240506224248832](Element%E7%AC%94%E8%AE%B0.assets/image-20240506224248832.png)

这段代码将`.edit-head-right`元素设置为Flex容器，并使用`justify-content: space-between;`将子元素分散对齐。然后，每个子元素都被赋予了`flex: 1;`属性，使它们平均分配剩余的空间。

## 5、vw 和 vh

> `vw`表示相对于视图窗口的宽度，`vh`表示相对于视图窗口高度，除了`vw`和`vh`外，还有`vmin`和`vmax`两个相关的单位。各个单位具体的含义如下：
>
> | 单位 | 含义                                                      |
> | :--- | :-------------------------------------------------------- |
> | vw   | 相对于视窗的宽度，1vw 等于视口宽度的1%，即视窗宽度是100vw |
> | vh   | 相对于视窗的高度，1vh 等于视口高度的1%，即视窗高度是100vh |
> | vmin | vw和vh中的较小值                                          |
> | vmax | vw和vh中的较大值                                          |



# 响应式布局



替换同级的



```
  @media screen and (max-width: 922px) {
    width: 90%;
  }
```





```
@media only screen and (max-width: 1050px) {
  .container-more { //控制  .container-more
    width: 90%;
    .bottom {
      margin: 2rem;
      p {
        font-size: 1.2rem;
      }
    }
  }
}
```



```
 // 手机
 @media screen and (max-width: 960px) {
    background: #fff;
    margin: 0 auto;
    width: 90%;
  }
  
  // 平板
  @media screen and (max-width: 1024px) {
    background: #fff;
    margin: 0 auto;
    width: 90%;
}

```



动态绑定样式

```
 <el-aside class="sidebar-container" :style="{ width: sidebarWidth }">
      <layout-menu />
    </el-aside>
    
const sidebarWidth = computed(() => {
  if (device.value === "mobile" && sidebar.value.opened) {
    return "200px";
  } else {
    return sidebar.value.opened ? "200px" : "0px";
  }
}); 
```



# Drawer 抽屉

```

```





# 波浪页脚

![image-20240507234015975](Element%E7%AC%94%E8%AE%B0.assets/image-20240507234015975.png)

```
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>飞雪前端艺术</title>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        * {
            padding: 0;
            margin: 0;
        }

        h1 {
            font-weight: 300;
            letter-spacing: 2px;
            font-size: 48px;
        }

        p {
            font-family: 'Lato', sans-serif;
            letter-spacing: 1px;
            font-size: 30px;
            color: #333333;
        }

        .header {
            position: relative;
            text-align: center;
            background: linear-gradient(60deg, rgba(84, 58, 183, 1) 0%, rgba(0, 172, 193, 1) 100%);
            color: white;
        }

        .inner-header {
            height: 65vh;
            width: 100%;
            margin: 0;
            padding: 0;
        }

        .flex {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }

        .waves {
            position: relative;
            width: 100%;
            height: 15vh;
            margin-bottom: -7px;
            min-height: 100px;
            max-height: 150px;
        }

        .content {
            position: relative;
            height: 20vh;
            text-align: center;
            background-color: white;
        }

        .parallax>use {
            /* 使use元素执行move-forever动画 */
            animation: move-forever 25s cubic-bezier(.55, .5, .45, .5) infinite;
        }

        .parallax>use:nth-child(1) {
            /* 延迟2秒启动动画  */
            animation-delay: -2s;
            /* 设置动画持续时间为7秒 */
            animation-duration: 7s;
        }

        .parallax>use:nth-child(2) {
            animation-delay: -3s;
            animation-duration: 10s;
        }

        .parallax>use:nth-child(3) {
            animation-delay: -4s;
            animation-duration: 13s;
        }

        .parallax>use:nth-child(4) {
            animation-delay: -5s;
            animation-duration: 20s;
        }

        @keyframes move-forever {
            0% {
                transform: translate3d(-90px, 0, 0);
            }

            100% {
                transform: translate3d(85px, 0, 0);
            }
        }

        @media (max-width: 768px) {
            .waves {
                height: 40px;
                min-height: 40px;
            }

            .content {
                height: 30vh;
            }

            h1 {
                font-size: 24px;
            }
        }

    </style>
</head>

<body>
    <div class="header">
        <div class="inner-header flex"></div>
        <div>
            <!--
                xmlns:SVG命名看见URI
                viewBox:定义当前视口（viewbox）中绘制区域的位置大小
                preserveeAspectRatio：定义了绘制区域在视口中的对齐方式
                shape-rendering：定义了形状的渲染方式（如何呈现头像的锯齿效果）
             -->
            <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
                <!-- 定义一个defs元素，用于存储各种元素的定义，可以被其他元素引用。
                    在defs元素中定义了一个名为“gentle-wave”的路径元素，用于描述波浪形状
                    改路径秒苏联一系列二次贝塞尔曲线的控制点坐标，从而实现了波浪形状 -->
                <defs>
                    <path id="gentle-wave"
                        d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
                </defs>
                <!-- 定义一个g元素，用于讲多个图形组合在一期，并应用一些样式
                    在g元素中使用use元素多次引用了赚钱定义的名为gentle-wave的路径元素
                    通过设置不同的x，y坐标和填充颜色，实现了波浪形状和渐变效果 -->
                <g class="parallax">
                    <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7)" />
                    <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
                    <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
                    <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
                </g>

            </svg>
        </div>
    </div>
    <div class="content flex">
        <p>bilibili-飞雪前端艺术</p>
    </div>
</body>

</html>
```



1. 

# z-index无效，无论设置多大都被其他的元素覆盖

```html
<!DOCTYPE html>
<html>
<head>
	<title>z-index问题</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
	<!-- 如果打开不了，首先就检查这里的jquery导入是否正确了，我这的js文件路径不一样的 -->
	<script src="js/jquery-1.4.2.min.js" type="text/javascript" ></script>  
</head>
<style type="text/css">
.test-div{
	border:1px blue solid;
	width:300px;
	min-height:100px;
	position:relative;
	-z-index:0;
	font-size:18px;
	font-family:microsoft yahe;
	color:#fff;
}
.has-position-absolute{
	border:1px #999999 solid;
	width:100px;
	height:200px;
	background-color:#FAFAFA;
	position:absolute;
	z-index:99;
	top:20px;
	left:50px;
	color:#333333;
}
</style>
<script type="text/javascript">
</script>
<body>
<div id="no-style">
	<div id="div1" class="test-div" style="background-color:blue;">
		div1的z-index为1
		<div id="test-position" class="has-position-absolute">
			div3的z-index为99
		</div>
	</div>
	<div id="div2" class="test-div" style="background-color:red;">
		<span>
			div2的z-index为2，但是现在绝对定位的div3明明z-index比它大，
			却依旧在这个层之下。原因是z-index是相对同一父元素下叠加时的z轴顺序。
			z-index具有继承性，用简单的数学逻辑表示就是：div1的z-index为1，则它
			的子元素div3的z-index就应该是1.xx，如果绝对定位的元素还有子元素，
			则z=index值实际上就应该是1.xx.xx。下面的div2的z-index值为2，所以在div3之上
		</span>
	</div>
	
<pre style="font-size:18px">
浅说position定位及z-index使用
 
使用前提
 
z-index只能在position属性值为relative或absolute或fixed的元素上有效。
 
基本原理
 
z-index值可以控制定位元素在垂直于显示屏方向（Z 轴）上的堆叠顺序（stack order），值大的元素发生重叠时会在值小的元素上面。
 
使用的相对性
 
z-index值只决定同一父元素中的同级子元素的堆叠顺序。父元素的z-index值（如果有）为子元素定义了堆叠顺序（css版堆叠“拼爹”）。
向上追溯找不到含有z-index值的父元素的情况下，则可以视为自由的z-index元素，它可以与父元素的同级兄弟定位元素或其他自由的定位元素来比较z-index的值，
决定其堆叠顺序。同级元素的z-index值如果相同，则堆叠顺序由元素在文档中的先后位置决定，后出现的会在上面。
 
所以如果当你发现一个z-index值较大的元素被值较小的元素遮挡了，请先检查它们之间的dom结点关系，多半是因为其父结点含有激活并设置了z-index值的position定位元素。
 
也因为这个相对性，还会引发浏览器表现不一致出现兼容问题。原因是ie6、7下面position值为非static的元素在未设置z-index值的情况下都会被隐含添加z-index:0，
而Firefox/Chrome等现代浏览器会遵循标准默认z-index:auto不会产生值。
 
还有一点需要注意，负值的z-index也依照大小比较的原理，但一般来说负值的z-index会被透明的body覆盖导致点击等事件响应出现问题，请谨慎使用。
</pre>
</div>
</body>
</html>
```

- z-index值越大越“靠近我们” -- 最初级的认知
- 要搭配position: absolute | relative | fixed 使用才有用呢 -- 稍微进阶一些的认知
- 比较两个兄弟节点谁更“靠近我们”，要看他们的同级父元素的比较呢。-- 可能是大部分前端的终极认知了



（1）简单定义：

通常 z-index 的使用是在有两个重叠的标签，在一定的情况下，控制其中一个在另一个的上方或者下方出现。

z-index值越大就越是在上层。

（2）Z-index 仅能在定位元素上奏效（也就是说要有position属性，再详细的说，它的值需要是relative、absolute、fixed）补充：它的值也可以写成inherit，只要它的结果能是上边的三种也可以。不能是static，因为这个是默认值，相当于没有定位





![preview](Element%E7%AC%94%E8%AE%B0.assets/view.png)

**当不同父元素的子元素之间进行显示时，会根据父级元素的z-index进行渲染，与子元素的z-index值没有关系**

```css
<div class="father"
	<div class="public num01">我是第一个</div>
</div>
<div class="fatherTwo">
	<div class="public num02">我是第二个</div>
</div>
```

```css
.father,
.fatherTwo{
width: 100px;height: 100px;
color: white;
position: relative;
}

.father{z-index: 1;}

.fatherTwo{z-index: 2;} 

.public{width: inherit;height: inherit;}
.num01{
position: absolute;
background-color: black;
margin-top: 50px;
z-index: 1000;
}
.num02{
position: inherit;
background-color: red;
z-index:100;
}
```

![image-20240528165849847](Element%E7%AC%94%E8%AE%B0.assets/image-20240528165849847.png)





**z-index无论设置多高都不起作用情况**

这种情况发生的条件有三个：

1. 父标签 position属性为 relative；
2. 问题标签无position属性（不包括static）；
3. 问题标签含有浮动(float)属性。

例子z-index层级不起作用，浮动会让z-index失效，代码如下:

```html
<div style="position:relative; z-index:9999;">
	<img style="float:left;" src="http://7te9u8.com1.z0.glb.clouddn.com/wp-content/uploads/2014/03/100084691.jpg" alt="div层调整z-index属性无效原因分析及解决方法">
</div>
```

### 解决方法

1. position:relative改为position:absolute；
2. 浮动元素添加position属性（如relative，absolute等）；
3. 去除浮动。



兄弟盒子中绝对定位对z-index的影响

```html
<!-- html部分 -->
<div class="box1">box1</div>
<div class="box2">box2</div>
```

```css

/* css部分 */
.box1{
    width: 200px;
    height: 200px;
    background: salmon;
    position: absolute;
    top:250px;
    left: 100px;
}
.box2{
    width: 200px;
    height: 200px;
    background: rgb(162, 20, 228);
    margin-bottom: 500px;
    position: absolute;
    top: 400px;
    /* 后来者居上，此时box2在box1上面 */
    left: 0px; 
    /* 设置box2在box1后面 */
    z-index: -1; 
}
```

父子盒子中绝对定位对z-index的影响

```css
/* css部分 */
.father{
    width: 200px;
    height: 200px;
    background: darkgreen;
    margin-bottom: 50px;
    /* 此时设置该属性没有作用，若想要父盒子覆盖在子盒子之上，则在子盒子中设置！ */
    z-index: 1; 

    /* 使得子盒子以父盒子为参照 */
    /* position: relative;  */
        }

.child{
    width: 100px;
    height: 100px;
    background: salmon;
    top: 20px;
    left: 20px;

    /* 子盒子需要使用绝对定位才能设置z-index! */
    /* position: absolute;   */
    /* 此时子盒子被覆盖 */
    /* z-index: -1;  */
}

```

# CSS鼠标悬浮动画-hover属性

[八大经典优雅的CSS鼠标悬浮动画-hover属性_css hover 动画-CSDN博客](https://blog.csdn.net/weixin_62650212/article/details/123912722)

#### 1.Grow-Shadow

###### 效果：



![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/60d303d049ca4bf0aad60b12c88b1d92.gif#pic_center)

###### 代码：

```css
/* Grow-Shadow */
.hvr-grow-shadow {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: box-shadow, transform;
  transition-property: box-shadow, transform;
}
.hvr-grow-shadow:hover, .hvr-grow-shadow:focus, .hvr-grow-shadow:active {
  box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5);
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
1234567891011121314151617
```

#### 2.Grow

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/1fa116f6481545238758459981f4bf4e.gif#pic_center)

###### 代码：

```css
/* Grow */
.hvr-grow {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-grow:hover, .hvr-grow:focus, .hvr-grow:active {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
12345678910111213141516
```

#### 3.Shrink

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/0db60c0a21c8466d9fcb4f98c2bf52d0.gif#pic_center)

###### 代码：

```css
/* Shrink */
.hvr-shrink {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-shrink:hover, .hvr-shrink:focus, .hvr-shrink:active {
  -webkit-transform: scale(0.9);
  transform: scale(0.9);
}
12345678910111213141516
```

#### Float

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/8bd7969cc17d4b4bb3fa0eb32a778dc1.gif#pic_center)

###### 代码：

```css
/* Float */
.hvr-float {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
  -webkit-transition-timing-function: ease-out;
  transition-timing-function: ease-out;
}
.hvr-float:hover, .hvr-float:focus, .hvr-float:active {
  -webkit-transform: translateY(-8px);
  transform: translateY(-8px);
}
123456789101112131415161718
```

#### 5.Float Shadow

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/c83ccf12826c472dbd5875c5a2ab1fde.gif#pic_center)

###### 代码：

```css
/* Float Shadow */
.hvr-float-shadow {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  position: relative;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-float-shadow:before {
  pointer-events: none;
  position: absolute;
  z-index: -1;
  content: '';
  top: 100%;
  left: 5%;
  height: 10px;
  width: 90%;
  opacity: 0;
  background: -webkit-radial-gradient(center, ellipse, rgba(0, 0, 0, 0.35) 0%, rgba(0, 0, 0, 0) 80%);
  background: radial-gradient(ellipse at center, rgba(0, 0, 0, 0.35) 0%, rgba(0, 0, 0, 0) 80%);
  /* W3C */
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform, opacity;
  transition-property: transform, opacity;
}
.hvr-float-shadow:hover, .hvr-float-shadow:focus, .hvr-float-shadow:active {
  -webkit-transform: translateY(-5px);
  transform: translateY(-5px);
  /* move the element up by 5px */
}
.hvr-float-shadow:hover:before, .hvr-float-shadow:focus:before, .hvr-float-shadow:active:before {
  opacity: 1;
  -webkit-transform: translateY(5px);
  transform: translateY(5px);
  /* move the element down by 5px (it will stay in place because it's attached to the element that also moves up 5px) */
}
123456789101112131415161718192021222324252627282930313233343536373839404142
```

#### 6.Curl Bottom Right

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/55f2b1654466492f8ddea9d38696106b.gif#pic_center)

###### 代码：

```css
/* Curl Bottom Right */
.hvr-curl-bottom-right {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  position: relative;
}
.hvr-curl-bottom-right:before {
  pointer-events: none;
  position: absolute;
  content: '';
  height: 0;
  width: 0;
  bottom: 0;
  right: 0;
  background: white;
  /* IE9 */
  background: linear-gradient(315deg, white 45%, #aaa 50%, #ccc 56%, white 80%);
  box-shadow: -1px -1px 1px rgba(0, 0, 0, 0.4);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: width, height;
  transition-property: width, height;
}
.hvr-curl-bottom-right:hover:before, .hvr-curl-bottom-right:focus:before, .hvr-curl-bottom-right:active:before {
  width: 25px;
  height: 25px;
}
123456789101112131415161718192021222324252627282930
```

#### 7.Bubble Float Right

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/40446100330e4190bf7250d632b33990.gif#pic_center)

###### 代码：

```css
/* Bubble Float Right */
.hvr-bubble-float-right {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  position: relative;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-bubble-float-right:before {
  position: absolute;
  z-index: -1;
  top: calc(50% - 10px);
  right: 0;
  content: '';
  border-style: solid;
  border-width: 10px 0 10px 10px;
  border-color: transparent transparent transparent #e1e1e1;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-bubble-float-right:hover, .hvr-bubble-float-right:focus, .hvr-bubble-float-right:active {
  -webkit-transform: translateX(-10px);
  transform: translateX(-10px);
}
.hvr-bubble-float-right:hover:before, .hvr-bubble-float-right:focus:before, .hvr-bubble-float-right:active:before {
  -webkit-transform: translateX(10px);
  transform: translateX(10px);
}

```

#### 8.Box Shadow Inset

###### 效果：

![在这里插入图片描述](Element%E7%AC%94%E8%AE%B0.assets/9fffa52342e44a7cbb8ec038d373a1d1.gif#pic_center)

###### 代码：

```css
/* Box Shadow Inset */
.hvr-box-shadow-inset {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: box-shadow;
  transition-property: box-shadow;
  box-shadow: inset 0 0 0 rgba(0, 0, 0, 0.6), 0 0 1px rgba(0, 0, 0, 0);
  /* Hack to improve aliasing on mobile/tablet devices */
}
.hvr-box-shadow-inset:hover, .hvr-box-shadow-inset:focus, .hvr-box-shadow-inset:active {
  box-shadow: inset 2px 2px 2px rgba(0, 0, 0, 0.6), 0 0 
```

# 聊天框

```
    <div class="chatMain" ref="chatMainWindow
    </div>
```



```js
  //把聊天窗口滚动到最底部
  const chatDom = chatMainWindow.value as HTMLElement;
  const observer = new MutationObserver((mutationsList) => {
    // 滚动到最底部
    chatDom.scrollTop = chatDom.scrollHeight;
  });
  observer.observe(chatDom, { childList: true });
```





# el-table表格点击该行任意位置时也勾选上其前面的复选框



