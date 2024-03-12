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

```
import gsap from 'gsap'

const router = useRouter()

onMounted(() => {
    //动画
    gsap.from('.articleLeft', {
        duration: .5,
        x: 50,  //右向左移动  -50左向右移动
        opacity: 0.2
    })
})
```

这段代码使用了GSAP动画库中的from方法，它会从指定的初始状态（在这里是x轴偏移50个单位，不透明度为0.2）开始，然后在0.5秒的持续时间内将元素动画到其初始状态。这段代码的作用是对类名为articleLeft的元素应用一个从右向左移动并逐渐显现的动画效果。







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



div里标签元素居中(一般用于文字)

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
