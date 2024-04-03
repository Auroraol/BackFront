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



```
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
- default-active：激活当前菜单的index。 
-   active-text-color="#31c27c"  点击后文字颜色
- router： 是否使用路由，启用后可以通过index作为地址进行跳转。
- <[el-menu](https://so.csdn.net/so/search?q=el-menu&spm=1001.2101.3001.7020)-item index="1">导航栏中的子菜单标签。
- <el-submenu> 生成二级菜单。

代码实现：

```html
<template>
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







# 动画

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

相当于父级块居中

```
 <div style="display: flex; justify-content: center;">
     <!---->
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

# Affix 固钉

```vue
<template>
  <el-affix>
    <div style="background-color: #2580B3;">
    </div>
  </el-affix>
</template>
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

```
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



## 表单

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

