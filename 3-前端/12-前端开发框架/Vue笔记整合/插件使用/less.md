#### 安装less

```javascript
npm install less@3.0.4 --save
npm install less-loader@5.0.0 --save
12
```

#### 使用

```javascript
<style lang="less" scoped>

</style>
123
```

#### 定义全局样式

assets/index.less

```javascript
html,
body,
#app {
  width: 100%;
  height: 100%;
  margin: 0px;
  font-family: "微软雅黑", "Helvetica Neue", Helvetica, Arial, sans-serif;
}

* {
  font-family: "微软雅黑", "Helvetica Neue", Helvetica, Arial, sans-serif;
  user-select: none;
}

html,
body,
ul,
li,
ol,
dl,
dd,
dt,
p,
h1,
h2,
h3,
h4,
h5,
h6,
form,
fieldset,
legend,
img {
  margin: 0;
  padding: 0;
}

fieldset,
img,
input,
button,
textarea {
  border: none;
  padding: 0;
  margin: 0;
  outline-style: none;
}

ul,
ol {
  list-style: none;
}

input {
  padding-top: 0;
  padding-bottom: 0;
  //font-family: "SimSun", "宋体";
}

select,
input {
  vertical-align: middle;
}

select,
input,
textarea {
  font-size: 12px;
  margin: 0;
}

textarea {
  resize: none;
}

/*防止拖动*/

img {
  border: 0;
  vertical-align: middle;
  max-width: 100%;
}

/*  去掉图片低测默认的3像素空白缝隙*/
table {
  border-collapse: collapse;
}

body {
  font: 12px/150% Arial, Verdana, "\5b8b\4f53";
}

.clearfix:before,
.clearfix:after {
  content: "";
  display: table;
}

.clearfix:after {
  clear: both;
}

.clearfix {
  *zoom: 1;
  /*IE/7/6*/
}

a {
  color: #666;
  text-decoration: none;
}

h1,
h2,
h3,
h4,
h5,
h6 {
  text-decoration: none;
  font-weight: normal;
  font-size: 100%;
}

s,
i,
em {
  font-style: normal;
  text-decoration: none;
}

/* IE 浏览器 */
.scrollbar-body {
  /*三角箭头的颜色*/
  scrollbar-arrow-color: #0581ab;
  /*滚动条滑块按钮的颜色*/
  scrollbar-face-color: #0581ab;
  /*滚动条整体颜色*/
  scrollbar-highlight-color: #0a393f;
  /*滚动条阴影*/
  scrollbar-shadow-color: #0099dd;
  /*滚动条轨道颜色*/
  scrollbar-track-color: #0a393f;
  /*滚动条3d亮色阴影边框的外观颜色——左边和上边的阴影色*/
  scrollbar-3dlight-color: #0099dd;
  /*滚动条3d暗色阴影边框的外观颜色——右边和下边的阴影色*/
  scrollbar-darkshadow-color: #0099dd;
  /*滚动条基准颜色*/
  scrollbar-base-color: #0581ab;
}

.scrollbar-body {
  overflow: hidden;
  overflow-y: auto;
}

.scrollbar-body::-webkit-scrollbar {
  width: 0.36vw;
  background-color: rgba(51, 238, 255, 0.2);
}

.scrollbar-body::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(2, 160, 251, 0.3);
  background-color: rgba(0, 192, 255, 0.51);
  border-radius: 0.18vw;
}

.is_hover {
  cursor: pointer;
  transition: opacity 0.3s;
}

.is_hover:hover {
  opacity: 0.85;
}

.float-right {
  float: right;
}

.float-right:after {
  content: " ";
  display: inline-block;
  clear: both;
}


```

#### App.vue

```javascript
<template>
  <div id="app">
      <router-view/>
  </div>
</template>

<style lang="less">
  @import "./assets/css/index";
</style
```