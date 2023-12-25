#  HTML5拖动API的应用

## 拖拽基础知识

### 拖拽

+ 拖拽：Drag

+ 释放：Drop

拖拽指的是鼠标点击源对象后一直移动对象不松手，一但松手即释放了。

### 源对象和目标对象

+ 源对象：指的是我们鼠标点击的一个事物，这里<font color=red>可以是一张图片，一个DIV，一段文本</font>等等。

+ 目标对象：指的是我们拖动源对象后移动到一块区域，源对象可以进入这个区域，可以在这个区域上方悬停(未松手)，可以释松手释放将源对象放置此处(已松手)，也可以悬停后离开该区域。

### 可拖拽属性

将想要拖拽的元素的 [draggable](https://developer.mozilla.org/zh-CN/docs/Web/HTML/Global_attributes#attr-draggable) 属性设置成：

```html
 draggable="true"
```

举例：

```html
<div draggable="true" ondragstart="dragstart" ondrag="drag" ondragend="dragend">
  Drag me
</div>
```

### 拖拽事件

被拖动的源对象可以触发的事件：

​	(1)ondragstart：源对象开始被拖动

​	(2)ondrag：源对象被拖动过程中(鼠标可能在移动也可能未移动)

​	(3)ondragend：源对象被拖动结束

拖动源对象可以进入到上方的目标对象可以触发的事件：

​	(1)ondragenter：目标对象被源对象拖动着进入

​	(2)ondragover：目标对象被源对象拖动着悬停在上方

​	(3)ondragleave：源对象拖动着离开了目标对象

​	(4)ondrop：源对象拖动着在目标对象上方释放/松手

### 阻止默认事件

如果你想要允许放置，你必须取消 dragenter 、 dragover 、ondrop 事件来阻止默认的处理。你可以在属性定义的事件监听程序返回 false，或者调用事件的 preventDefault() 方法来实现这一点。

```html
<div ondragover="return false">
<div ondragover="event.preventDefault()">
```

如果是在页面中拖动，则设置document阻止默认事件

```js
document.ondragover = function(e){e.preventDefault()}
document.ondrop = function(e){e.preventDefault()}
```

### 获取拖拽对象

在事件绑定中通过this或event.target获取拖拽对象（看事件绑定到源对象还是目标对象，对应获取的就是源对象或目标对象）。

在源对象中获取的就是当前拖拽的源对象：

```js
let source = document.querySelector('.source'),
source.addEventListener('dragstart',function(event){
  console.log(this === event.target) // true
},false);
```

在目标对象中获取的就是当前拖拽的目标对象：

```js
let target = document.querySelector('.target'),
target.addEventListener('drop',function(event){
  console.log(this === event.target) // true
},false);
```

注意：在Vue中不能使用this获取拖拽对象，this会获取到当前Vue组件实例

### 拖拽数据传递

HTML5为所有的拖动相关事件提供了一个新的属性：

```js
e.dataTransfer // 数据传递对象
```

功能：用于在源对象和目标对象的事件间传递数据

```js
e.dataTransfer.setData(k, v); // k-v必须都是string类型
```

源对象上的事件处理中保存数据：

```js
let v = e.dataTransfer.getData(k);
```

目标对象上的事件处理中读取数据：

```js
let v = e.dataTransfer.getData(k);
```

## 单元素拖动

将单一元素在页面中拖动，只需要监听源对象的三个事件，在drag事件中实时更新元素的位置即可。

示例：

<iframe class="ne-thirdparty-iframe" data-testid="ne-thirdparty-reader-iframe" sandbox="allow-forms allow-orientation-lock allow-presentation allow-same-origin allow-scripts" data-src="https://codepen.io/quanzaiyu-the-decoder/embed/GRmQzWj" frameborder="0" allowfullscreen="" src="https://codepen.io/quanzaiyu-the-decoder/embed/GRmQzWj" style="box-sizing: border-box; user-select: auto !important; position: relative; width: 746.4px; height: 500px; top: 0px; left: 0px; background: var(--lakex-editor-background-primary);"></iframe>

源码：

```vue
<template>
<div class="source-box">
  <div draggable="true" @dragstart="dragstart" @drag="drag" @dragend="dragend" class="source" data-index="1"></div>
</div>
</template>

<script>
document.ondragover = function(e){e.preventDefault()}
document.ondrop = function(e){e.preventDefault()}
export default {
  data() {
    return {
      offsetX: 0,
      offsetY: 0
    }
  },
  methods: {
    dragstart(e) {
      this.offsetX= e.offsetX;
      this.offsetY= e.offsetY;
    },
    drag(e) {
      let x = e.pageX
      let y = e.pageY
      // drag事件最后一刻，无法读取鼠标的坐标，pageX和pageY都变为0
      if(x === 0 && y === 0) {
        return // 不处理拖动最后一刻X和Y都为0的情形
      }
      x -= this.offsetX
      y -= this.offsetY

      let element = e.target
      element.style.left= x + 'px'
      element.style.top= y + 'px'
    },
    dragend(e) {
      console.log('dragend')
      console.log(e)
    },
  }
}
</script>

<style>
.source-box {
  position: relative;
  width: 100%;
  height: 100%;
}

.source {
  position: absolute;
  width: 200px;
  height: 200px;
  background-color: red;
  top: 0;
  left: 0;
}
</style>
```

## 拖拽到目标元素

在页面中存在源对象与目标对象，源对象传递数据，目标对象接收数据，并对数据进行处理。

只有在drop事件中可以获取到传递的数据

### 拖拽移动、复制、删除

思路：

移动：获取到源对象和目标对象，使用appendChild将源对象添加到目标对象中

复制：获取到源对象和目标对象，使用cloneNode克隆出一个与源对象相同的对象，再使用appendChild将源对象添加到目标对象中

删除：获取到源对象及其父节点，通过父节点的removeChild方法删除此DOM

示例：

<iframe class="ne-thirdparty-iframe" data-testid="ne-thirdparty-reader-iframe" sandbox="allow-forms allow-orientation-lock allow-presentation allow-same-origin allow-scripts" data-src="https://codepen.io/quanzaiyu-the-decoder/embed/MWmQKOO" frameborder="0" allowfullscreen="" src="https://codepen.io/quanzaiyu-the-decoder/embed/MWmQKOO" style="box-sizing: border-box; user-select: auto !important; position: relative; width: 746.4px; height: 500px; top: 0px; left: 0px; background: var(--lakex-editor-background-primary);"></iframe>

源码：

```vue
<template>
<div>
  <div class="source-box">
    <div draggable="true" @dragstart="dragstart" @drag="drag" @dragend="dragend" class="source" data-index="1">源1</div>
    <div draggable="true" @dragstart="dragstart" @drag="drag" @dragend="dragend" class="source" data-index="2">源2</div>
    <div draggable="true" @dragstart="dragstart" @drag="drag" @dragend="dragend" class="source" data-index="3">源3</div>
    <div draggable="true" @dragstart="dragstart" @drag="drag" @dragend="dragend" class="source" data-index="4">源4</div>
  </div>
  <div class="target-box">
    <div @dragenter="dragenter" @dragleave="dragleave" @dragover="dragover" @drop="drop1" class="target">目标1: 移动</div>
    <div @dragenter="dragenter" @dragleave="dragleave" @dragover="dragover" @drop="drop2" class="target">目标2: 复制</div>
    <div @dragenter="dragenter" @dragleave="dragleave" @dragover="dragover" @drop="drop3" class="target">目标3: 删除</div>
  </div>
</div>
</template>

<script>
/* eslint-disable */
export default {
  methods: {
    dragstart(e) {
      console.log('dragstart')
      console.log(e)
      e.dataTransfer.dropEffect = "copy";
      e.dataTransfer.setData('index', e.target.dataset.index)
    },
    drag(e) {
      // console.log('drag')
      // console.log(e)
    },
    dragend(e) {
      console.log('dragend')
      console.log(e)
    },
    dragenter(e) {
      console.log('dragenter')
      console.log(e)
    },
    dragleave(e) {
      console.log('dragleave')
      console.log(e)
    },
    dragover(e) {
      // console.log('dragover')
      // console.log(e)
      event.preventDefault()
      return false
    },
    drop1(e) {
      console.log('drop')
      console.log(e)
      // 只有drop事件的时候可以获取到传递的数据
      let index = e.dataTransfer.getData('index')
      let element = document.querySelector(`.source-box > .source:nth-child(${index})`)
      e.target.appendChild(element)
    },
    drop2(e) {
      let index = e.dataTransfer.getData('index')
      let element = document.querySelector(`.source-box > .source:nth-child(${index})`)
      let elementClone = element.cloneNode(true)
      e.target.appendChild(elementClone)
    },
    drop3(e) {
      let index = e.dataTransfer.getData('index')
      let element = document.querySelector(`.source-box > .source:nth-child(${index})`)
      element.parentNode.removeChild(element)
    },
  }
}
</script>

<style>
.source-box, .target-box {
  display: flex;
}

.source {
  width: 200px;
  height: 200px;
  background-color: red;
  margin-top: 20px;
  margin-right: 20px;
}

.target {
  width: 300px;
  height: 300px;
  background-color: #ccc;
  margin-top: 20px;
  margin-right: 20px;
}
</style>
```

### 拖拽排序

思路：

●在一个列表中，每个元素都可以被拖放，那首先要给每个元素设置 draggable 属性为 true。

●监听每个元素的 dragstart 事件，对源对象做样式处理来区分。

●监听每个元素的 dragenter 事件，当源对象进入到当前元素里，就把源对象添加到该元素之前。这样后面的元素就会被源对象挤下去了，实现了排序的效果。

●但是会发现，源对象无法排到最后一个去，只能在倒数第二。这时就要监听 dragleave 事件，当过程对象是最后一个元素时，源对象离开了过程对象，这时就把源对象添加到最后去。

示例：

<iframe class="ne-thirdparty-iframe" data-testid="ne-thirdparty-reader-iframe" sandbox="allow-forms allow-orientation-lock allow-presentation allow-same-origin allow-scripts" data-src="https://codepen.io/quanzaiyu-the-decoder/embed/zYwWPMK" frameborder="0" allowfullscreen="" src="https://codepen.io/quanzaiyu-the-decoder/embed/zYwWPMK" style="box-sizing: border-box; user-select: auto !important; position: relative; width: 746.4px; height: 500px; top: 0px; left: 0px; background: var(--lakex-editor-background-primary);"></iframe>

源码：

```vue
<template>
<ul class="list">
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目1</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目2</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目3</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目4</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目5</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目6</li>
  <li class="item" draggable="true" @dragstart="dragstart" @dragend="dragend" @dragenter="dragenter" @dragleave="dragleave">项目7</li>
</ul>
</template>

<script>
document.ondragover = function(e){e.preventDefault()}
document.ondrop = function(e){e.preventDefault()}
export default {
  data() {
    return {
      dragElement: null,
      lock: true
    }
  },
  methods: {
    // 源对象
    dragstart(e) {
      // 记录当前拖动的元素
      this.dragElement = e.target
      e.target.style.backgroundColor = '#f8f8f8'
    },
    dragend(e) {
      e.preventDefault()
      e.target.style.backgroundColor = '#fff' // 拖放结束还原拖动元素的背景
    },
    // 目标对象
    dragenter(e) {
      if(this.dragElement !== e.target){
        e.target.parentNode.insertBefore(this.dragElement, e.target) // 把拖动元素添加到当前元素的前面
      }
    },
    dragleave(e) {
      if(this.dragElement !== e.target){
        // 当前元素时最后一个元素
        if(this.lock && (e.target === e.target.parentNode.lastElementChild || e.target === e.target.parentNode.lastChild)) {
          e.target.parentNode.appendChild(this.dragElement) // 把拖动元素添加最后面
          this.lock = false
        } else {
          this.lock = true
        }
      }
    },
  }
}
</script>
```

在移动端完全不兼容上面的实现，可以使用移动端拖拽兼容库达到目的：[mobile-drag-drop](https://github.com/timruffles/mobile-drag-drop)

只需要在原有的代码中引入该插件，即可在移动端上实现拖动了。

如果使用了包管理，安装：

```
npm install mobile-drag-drop --save
# or
yarn add mobile-drag-drop
```

引入：

```
import {polyfill} from "mobile-drag-drop";

// optional import of scroll behaviour
import {scrollBehaviourDragImageTranslateOverride} from "mobile-drag-drop/scroll-behaviour";

// options are optional ;)
polyfill({
    // use this to make use of the scroll behaviour
    dragImageTranslateOverride: scrollBehaviourDragImageTranslateOverride
});
```

## 拖拽时的文件操作

在dataTransfer中可以获取到拖拽时包含的文件对象，通过e.dataTransfer.files获取。

**HTML5新增的文件操作对象**

File： 代表一个文件对象

FileList： 代表一个文件列表对象，类数组

FileReader：用于从文件中读取数据

FileWriter：用于向文件中写出数据

**拖拽显示图片**

示例：

<iframe class="ne-thirdparty-iframe" data-testid="ne-thirdparty-reader-iframe" sandbox="allow-forms allow-orientation-lock allow-presentation allow-same-origin allow-scripts" data-src="https://codepen.io/quanzaiyu-the-decoder/embed/eYWVXod" frameborder="0" allowfullscreen="" src="https://codepen.io/quanzaiyu-the-decoder/embed/eYWVXod" style="box-sizing: border-box; user-select: auto !important; position: relative; width: 746.4px; height: 500px; top: 0px; left: 0px; background: var(--lakex-editor-background-primary);"></iframe>

源码：

```html
<template>
<div class="target-box">
  <div @drop="drop" class="target">拖拽到此处显示图片</div>
</div>
</template>

<script>
document.ondragover = function(e){
  e.preventDefault(); //使得drop事件可以触发
}
document.ondrop = function(e){
  e.preventDefault(); //阻止在新窗口中打开图片，否则仍然会执行下载操作！！！
}

export default {
  methods: {
    drop(e) {
      e.preventDefault()
      let f0 = e.dataTransfer.files[0];
      console.log(f0); //文件对象 File

      //从文件对象中读取数据
      let fr = new FileReader()
      //fr.readAsText(f0) //从文件中读取文本字符串
      fr.readAsDataURL(f0) //从文件中读取URL数据
      fr.onload = function() {
        console.log(fr.result) // base64 URI
        let img = new Image()
        img.src = fr.result
        e.target.appendChild(img)
      }
      return false
    }
  }
}
</script>

<style>
.source-box, .target-box {
  display: flex;
}

.source {
  width: 200px;
  height: 200px;
  background-color: red;
  margin-top: 20px;
  margin-right: 20px;
}

.target {
  width: 300px;
  height: 300px;
  background-color: #ccc;
  margin-top: 20px;
  margin-right: 20px;
}

.target > img {
  width: 100%;
  height: 100%;
}
</style>
```

# **使用html2canvas生成海报**

html2canvas官网：

[html2canvas - Screenshots with JavaScript (hertzen.com)](https://html2canvas.hertzen.com/)

GitHub：

https://www.yuque.com/r/goto?url=https%3A%2F%2Fgithub.com%2Fniklasvh%2Fhtml2canvas

## 使用html2canvas

安装依赖：

```
yarn add html2canvas
```

在Vue中的使用示例：

```vue
<template>
  <div class="canvas-app">
    <!-- canvas原内容 -->
    <div class="canvas-content">
      <div class="canvas-content-text">这是文本内容</div>
    </div>
    
    <!-- 按钮 -->
    <div class="canvas-btns">
      <div @click="generatorCanvas">生成海报</div>
    </div>
    
    <!-- canvas生成弹窗 -->
    <div class="canvas-wrap">
      <div class="canvas-wrap-img">
        <img :src="imgUrl" alt="">
      </div>
      <div class="canvas-wrap-content"></div>
    </div>
  </div>
</template>

<script>
  import html2canvas from 'html2canvas';
  export default {
    data() {
      return {
        imgUrl: ''
      }
    },
    methods: {
      generatorCanvas() {
        let ele = document.querySelector('.canvas-content')
        html2canvas(ele, {
          backgroundColor: "#ffffff",
          allowTaint: true,  //开启跨域
          useCORS: true,
          scrollY: 0,
          scrollX: 0,
        }).then(canvas => {
          let wrap = document.querySelector('.canvas-wrap-content')
          wrap.appendChild(canvas);
          this.imgUrl = canvas.toDataURL('image/png');
          // debugger
        });
      }
    }
  };
</script>

<style>
  .canvas-content {
    height: 50px;
    background-color: #0f0;
  }
  .canvas-content-text {
    color: red;
  }
</style>
```

生成后的DOM结构：

![Snipaste_2022-04-14_09-54-02.png](HTML%E7%AC%94%E8%AE%B0.assets/1649901282426-fd1a4e5c-ac00-43ad-94ff-2fd46606d476.png)

## 图片跨域问题

如果是同源的图片路径，像这种，生成海报图片是正常的：

```html
<div class="canvas-content">
  <div class="canvas-content-text">这是文本内容</div>
  <div>
    <img src="/favicon.png" width="100" height="100" alt="">
  </div>
</div>
```

如果是非同源的图片路径，生成海报将报错：

```html
<div class="canvas-content">
  <div class="canvas-content-text">这是文本内容</div>
  <div>
    <img src="https://www.baidu.com/img/flexible/logo/pc/result.png" alt="">
  </div>
</div>
```

点击“生成海报”报跨域问题：

![Snipaste_2022-04-14_10-12-43.png](HTML%E7%AC%94%E8%AE%B0.assets/1649902391232-11c0a93b-4eee-44a7-b83f-a52ab9764fae.png)

解决方案有两种，都需要服务端支持。

方法一：服务器设置静态资源响应头 Access-Control-Allow-Origin为 *或者域名白名单，然后在img标签添加crossorigin="anonymous"属性。

```
<img crossorigin=“anonymous” src="https://www.baidu.com/img/flexible/logo/pc/result.png" alt="">
```

方法二：使用代理服务器获取资源。参考：

[niklasvh/html2canvas-proxy-nodejs: Express middleware proxy for html2canvas (github.com)](https://github.com/niklasvh/html2canvas-proxy-nodejs)

# **HTML5语音合成**

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
  <input id="input" />
  <button onclick="read()">阅读</button>

  <script>
    function read() {
      let input = document.getElementById('input');
      let inputValue = input.value;
      console.log(inputValue);
      let voiceValue = new SpeechSynthesisUtterance(inputValue);
      speechSynthesis.speak(voiceValue);
    }
  </script>
</body>
</html>
```

在输入框中输入值，点击“阅读”按钮，可以在浏览器中发出阅读输入的值。