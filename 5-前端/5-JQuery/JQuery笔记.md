## jQuery

<font color = red>用于操作BOM对象, 替代js原始的方式</font>

### 导入jQuery

可以通过多种方法在网页中添加 jQuery。 您可以使用以下方法：

- 从 [jquery.com](https://link.zhihu.com/?target=http%3A//jquery.com/download/) 下载 jQuery 库

- 从 CDN 中载入 jQuery, 如从 Google 中加载 jQuery 有两个版本的 jQuery 可供下载：

- Production version - 用于实际的网站中，已被精简和压缩。

- Development version - 用于测试和开发（未压缩，是可读的代码） 以上两个版本都可以从 [jquery.com](https://link.zhihu.com/?target=http%3A//jquery.com/download/) 中下载。 jQuery 库是一个 JavaScript 文件，您可以使用 HTML 的 <script> 标签引用它：

  ```js
  <head>
  <script src="jquery-1.10.2.min.js"></script>
  </head>
  ```

关键：独立的js文件不需要引用任何jquery.js文件，只需要在相应的html网页中引用jquery.js和.js文件，jquery文件引用必须在.js文件之前。<font color =red>js文件中没有引入jquery，一样可以用jquery的，就是在html中引入jquery，但是顺序一定不能错，一定是先引入jquery，再引入js文件。</font>

### 选择器

原生的js选择器的种类有：

- 标签选择器：document.getElementByTagName()
- id选择器： document.getElementByID()
- 类选择器： document.getElementByClassName()

jq选择器有（css中的选择器他全部都能用）：

$(“选择器”) // 选择器格式与 css 选择器格式一摸一样

####  jQuery 基础选择器

| 名称       | 用法           | 描述                           |
| ---------- | -------------- | ------------------------------ |
| ID选择器   | $("#id");      | 获取指定 ID 的元素             |
| 类选择器   | $(".class");   | 获取同一类 class 的元素        |
| 标签选择器 | $(“div”);      | 获取同一类标签的所有元素       |
| 并集选择器 | $(“div,li,p”); | 使用逗号分隔，获取多个元素     |
| 交集选择器 | $(“div.show”); | 获取 class 为 show 的 div 元素 |
| 全选选择器 | $("*");        | 匹配所有元素                   |

####  jQuery 层级选择器

| 名称       | 用法        | 描述                                                         |
| ---------- | ----------- | ------------------------------------------------------------ |
| 子代选择器 | $(“ul>li”); | 使用 > 号，获取儿子层级的元素；注意，并不会获取孙子层级的元素 |
| 后代选择器 | $(“ul li”); | 使用空格，代表后代选择器，获取 ul 下的所有 li 元素，包括孙子等 |

####  过滤选择器

| 名称       | 用法           | 描述                                                        |
| ---------- | -------------- | ----------------------------------------------------------- |
| :eq(index) | $(“li:eq(1)”); | 获取到的li元素中，选择索引号为1的元素，索引号index从0开始。 |
| :odd       | $(“li:odd”);   | 获取到的li元素中，选择索引号为奇数的元素                    |
| :even      | $(“li:even”);  | 获取到的li元素中，选择索引号为偶数的元素                    |

####  筛选选择器（方法）

> 筛选选择器的功能与过滤选择器有点类似，但是用法不一样，筛选选择器主要是方法。

| 名称               | 用法                                                         | 描述                                                    |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------- |
| parent()           | $(".first").parent();                                        | 查找父亲                                                |
| children(selector) | $(“ul”).children(“li”)      | 相当于$(“ul>li”)，子类选择器   |                                                         |
| find(selector)     | $(“ul”).find(“li”);         | 相当于$(“ul li”)，后代选择器   |                                                         |
| siblings(selector) | $(".first").siblings(“li”);                                  | 查找兄弟节点，不包括自己本身。                          |
| next()             | $(".first").next();                                          | 查找当前元素之后的下一个兄弟                            |
| nextAll()          | $(".first").nextAll();                                       | 查找当前元素之后的所有同辈元素                          |
| prev()             | $(".last").prev();                                           | 查找当前元素之前的上一个兄弟                            |
| prevAll()          | $(“last”).prevAll();                                         | 查找当前元素之前的所有同辈元素                          |
| hasClass(class)    | $(“div”).hasClass(“show”)                                    | 检查当前的元素是否含有某个特定的类，如果有，则返回 true |
| eq(index)          | $(“li”).eq(2);              | 相当于$(“li:eq(2)”)，index 从0开始 |                                                         |

### 排他思想

```javascript
$(function() {
  $("button").click(function(){
      $(this).css();  // 对自己设置变化
      $(this).siblings("button").css();  // 给其他兄弟去掉变化
  })
})
```

###  链式编程

```javascript
$(function() {
  $("button").click(function(){
   	$(this).css().siblings("button").css();  // 对自己设置变化,给其他兄弟去掉变化
  })
})
```

### 隐式迭代

遍历 jQuery 对象内部 DOM 元素（伪数组形式存储）的过程就叫**隐式迭代**

**简单理解：给匹配的所有元素进行循环遍历，执行相应的方法，而不是我们再进行循环，简化我们的操作，方便我们的调用。**

```javascript
// 给四个div设置背景颜色为粉色 jquery对象不能使用style
$("div").css("background", "pink");
// 隐式迭代就是把匹配的所有元素内部进行遍历循环，给每一个元素添加css这个方法
$("ul li").css("color", "red");
```

### 响应事件(个人理解入口函数):crossed_swords:

```js
$(function () {
   // init操作
});
```

例子

```js
<！--要求：获取鼠标当前的一个坐标-->
mouse : <span id="mouseMove"></span>
<div id="divMove">
在这里移动鼠标试试
</div>

<script>
    //当网页元素加载完毕之后，响应事件
    //在html加载完成后执行的代码
    $(function () {
        $('#divMove').mousemove(function (e) {
            $('#mouseMove').text('x:'+e.pagex + 'y:'+e.pageY)
        })
    });
</script>

//或者
window.onload = function () {  }
```

##  jQuery 样式操作

### 操作 css 方法

jQuery 可以使用 css 方法来修改简单元素样式；也可以操作类，修改多个样式。

- 参数只写属性名，则是返回属性值

```javascript
$(this).css("color");
```

- 参数是**属性名**，**属性值**，中间由逗号分隔，是一组样式，属性必须加引号，值如果是数字可以不用跟单位和引号

```javascript
$(this).css("color","red");
```

- 参数可以是对象形式，方便设置多组样式。属性名和属性值用冒号隔开，属性可以不用加引号

```javascript
$(this).css({"color":"red","font-size":"20px"});
```

### 设置样式类方法

作用等同于以前的 classList，可以操作类样式，注意操作类里面的参数不要加点

- 添加类

```javascript
$("div").addClass("current");
```

- 移除类

```javascript
$("div").removeClass("current");
```

- 切换类

```javascript
$("div").toggleClass("current");
```

### 与原生 js 区别

```javascript
// js 中的 className 会覆盖元素原先里面的类名
var one = document.querySelector(".one");
one.className = "two";  // one : class="two"

// jQuery 里面的类操作只是对指定类进行操作，不影响原先的类名
$(".one").addClass("two");  // one: class="one two"
```

## jQuery 效果

### 3.3.1 显示隐藏效果

① 显示效果

- 语法规范

```javascript
show([speed,[easing],[fn]])
```

- 概述
  - 显示隐藏的匹配元素。
  - 这个就是 ‘show( speed, [callback] )’ 无动画的版本。如果选择的元素是可见的，这个方法将不会改变任何东西。无论这个元素是通过hide()方法隐藏的还是在CSS里设置了display:none;，这个方法都将有效。
- 显示参数
  - 参数都可以省略，省略后没有动画，直接显示。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

② 隐藏效果

- 语法规范

```javascript
hide([speed,[easing],[fn]])
```

- 概述
  - 隐藏显示的元素。
  - 这个就是 ‘hide( speed, [callback] )’ 的无动画版。如果选择的元素是隐藏的，这个方法将不会改变任何东西。
- 显示参数
  - 参数都可以省略，省略后没有动画，直接隐藏。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

### 3.3.2 滑动效果

① 下滑效果

- 语法规范

```javascript
slideDown([speed],[easing],[fn])
```

- 概述
  - 通过高度变化（向下增大）来动态地显示所有匹配的元素，在显示完成后可选地触发一个回调函数。
  - 这个动画效果只调整元素的高度，可以使匹配的元素以“滑动”的方式显示出来。
- 显示参数
  - 参数都可以省略。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

② 上滑效果

- 语法规范

```javascript
slideUp([speed,[easing],[fn]])
```

- 概述
  - 通过高度变化（向上减小）来动态地隐藏所有匹配的元素，在隐藏完成后可选地触发一个回调函数。
  - 这个动画效果只调整元素的高度，可以使匹配的元素以“滑动”的方式隐藏起来。
- 显示参数
  - 参数都可以省略。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

③ 滑动效果

- 语法规范

```javascript
slideToggle([speed],[easing],[fn])
```

- 概述
  - 通过高度变化来切换所有匹配元素的可见性，并在切换完成后可选地触发一个回调函数。
  - 这个动画效果只调整元素的高度，可以使匹配的元素以“滑动”的方式隐藏或显示。
- 显示参数
  - 参数都可以省略。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

### 3.3.3 事件切换

- 语法规范

```javascript
hover([over,]out)
```

- 概述
  - 一个模仿悬停事件（鼠标移动到一个对象上面及移出这个对象）的方法。这是一个自定义的方法，它为频繁使用的任务提供了一种“保持在其中”的状态。
  - 当鼠标移动到一个匹配的元素上面时，会触发指定的第一个函数。当鼠标移出这个元素时，会触发指定的第二个函数。而且，会伴随着对鼠标是否仍然处在特定元素中的检测（例如，处在div中的图像），如果是，则会继续保持“悬停”状态，而不触发移出事件（修正了使用mouseout事件的一个常见错误）。
- 显示参数
  - `over`:鼠标移到元素上要触发的函数（相当于 mouseenter）。
  - `out`:鼠标移出元素要触发的函数（相当于 mouseenter）。

### 3.3.4 动画队列及其停止排队方法

① 动画或效果队列

动画或者效果一旦触发就会执行，如果多次触发，就造成多个动画或者效果排队执行。

② 停止排队

- 语法规范

```javascript
stop([clearQueue],[jumpToEnd])
```

- 概述
  - 停止所有在指定元素上正在运行的动画。
  - 如果队列中有等待执行的动画(并且clearQueue没有设为true)，他们将被马上执行。
- 显示参数
  - `queue`:用来停止动画的队列名称。
  - `clearQueue`:如果设置成true，则清空队列。可以立即结束动画。
  - `jumpToEnd`:如果设置成true，则完成队列。可以立即完成动画。

> 注意：stop() 写到动画或者效果的**前面**，相当于停止结束上一次的动画。

### 3.3.5 淡入淡出效果

① 淡入效果

- 语法规范

```javascript
fadeIn([speed],[easing],[fn])
```

- 概述
  - 通过不透明度的变化来实现所有匹配元素的淡入效果，并在动画完成后可选地触发一个回调函数。
  - 这个动画只调整元素的不透明度，也就是说所有匹配的元素的高度和宽度不会发生变化。
- 显示参数
  - 参数都可以省略
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

② 淡出效果

- 语法规范

```javascript
fadeOut([speed],[easing],[fn])
```

- 概述
  - 通过不透明度的变化来实现所有匹配元素的淡出效果，并在动画完成后可选地触发一个回调函数。
  - 这个动画只调整元素的不透明度，也就是说所有匹配的元素的高度和宽度不会发生变化。
- 显示参数
  - 参数都可以省略。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

③ 淡入淡出切换效果

- 语法规范

```javascript
fadeToggle([speed,[easing],[fn]])
1
```

- 概述
  - 通过不透明度的变化来开关所有匹配元素的淡入和淡出效果，并在动画完成后可选地触发一个回调函数。
  - 这个动画只调整元素的不透明度，也就是说所有匹配的元素的高度和宽度不会发生变化。
- 显示参数
  - 参数都可以省略。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

④ 渐进方式调整到指定的不透明度

- 语法规范

```javascript
fadeTo([[speed],opacity,[easing],[fn]])
1
```

- 概述
  - 把所有匹配元素的不透明度以渐进方式调整到指定的不透明度，并在动画完成后可选地触发一个回调函数。
  - 这个动画只调整元素的不透明度，也就是说所有匹配的元素的高度和宽度不会发生变化。
- 显示参数
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `opacity`:一个0至1之间表示透明度的数字。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

### 3.3.6 自定义动画 animate

- 语法规范

```javascript
animate(params,[speed],[easing],[fn])
1
```

- 概述
  - 用于创建自定义动画的函数。
  - 这个函数的关键在于指定动画形式及结果样式属性对象。这个对象中每个属性都表示一个可以变化的样式属性（如“height”、“top”或“opacity”）。注意：所有指定的属性必须用骆驼形式，比如用marginLeft代替margin-left。
  - 而每个属性的值表示这个样式属性到多少时动画结束。如果是一个数值，样式属性就会从当前的值渐变到指定的值。如果使用的是“hide”、“show”或“toggle”这样的字符串值，则会为该属性调用默认的动画形式。
- 显示参数
  - `params`:一组包含作为动画属性和终值的样式属性和及其值的集合（用对象实现），**必须写**。
  - `speed`:三种预定速度之一的字符串(“slow”,“normal”, or “fast”)或表示动画时长的毫秒数值(如：1000)。
  - `easing`:(Optional) 用来指定切换效果，默认是"swing"，可用参数"linear"。
  - `fn`:在动画完成时执行的函数，每个元素执行一次。

## 属性操作

### 3.4.1 设置或获取元素固有属性值 prop()

所谓元素固有属性就是元素本身自带的属性，如`<a>`元素里面的`herf`，`<input>`元素里面的`type`。

① 获取属性语法

```javascript
prop("属性")
```

② 设置属性语法

```javascript
prop("属性","属性值")
```

### 3.4.2 设置或获取元素自定义属性值 attr()

自定义属性就是用户自己给元素添加的属性。比如给`li`添加`index="1"`。

① 获取属性语法

```javascript
attr("属性")
```

② 设置属性语法

```javascript
attr("属性","属性值")
```

> 该方法也可用于获取 H5 自定义属性。

### 3.4.3 数据缓存 data()

data()方法可以在指定的元素上存取数据，并不会修改DOM 的元素结构，一旦页面刷新，之前存放的数据都将被移除。

① 附加数据语法

```javascript
data("key","value")  // 向被选元素附加数据
```

② 获取数据语法

```javascript
data("key")  // 从被选元素获取数据
```

> 如果浏览器支持 HTML5，同样可以读取该 DOM 中使用 data-[key] = [value] 所存储的值。

## jQuery 内容文本值

### 3.5.1 普通元素里的结构内容 html()

> 相当于原生 js 中的 innerHTML

```javascript
html()  // 获取元素的内容
html("内容")  // 设置元素的内容
```

### 3.5.2 普通元素文本内容 text()

> 相当于原生 js 中的 innerText

```javascript
text()  // 获取元素的内容
text("内容")  // 设置元素的内容
```

例子

```js
<ul id="test_ul">
    <li class="js">JavaScript</li>
    <li name="python">Python</li>
</ul>
<script>
		//拿到这个标签的文本，
        var a = $('#test_ul li[name=python]').text();   
        console.log(a);//Python
        //修改这个标签的文本
        $('#test_ul li[name=python]').text('123');  /
        //修改样式
        var b = $('#test_ul').html();  
        console.log(b);
		/*
        <li class="js">JavaScript</li>
        <li name="python">123</li>
		*/
</script>
```

### 3.5.3 表单值 val()

> 相当于原生 js 中的 value

```javascript
val()  // 获取元素的内容
val("内容")  // 设置元素的内容
```

## jQuery 元素操作

### 3.6.1 遍历元素

隐式迭代：对同一类元素做相同操作。如果做不同操作，需要使用遍历：

```javascript
$("div").each(function (index, domEle) {xxx;})

// 也可以写成
$.each($("div"),function (index, domEle) {xxx;}))
// 主要用于处理数据
```

> - index 是每个元素的索引号，domEle 是每个 DOM 元素对象，不是 jQuery 对象
> - 想要使用 jQuery 方法，就要给这个 dom 元素转换为 jQuery 对象 $(domEle)

### 3.6.2 创建元素

① 内部添加

```javascript
$("ul").append(li);//放到最后面，给ul标签放入li,类似原生 appendChild
```

例子

```js
$(function(){
var $h1=$(“<h1 title='一级标题'  class='red'  id='id'>DOM文档对象模型</h1>”);
$(“body”).append($h1);
})
```

② 外部添加

```javascript
el.before(内容);  // 把内容放到目标元素前面
el.after(内容);  // 把内容放到目标元素后面
```

> - 内部添加元素后是父子关系
> - 外部添加元素后是兄弟关系

3.6.3 删除元素

```javascript
el.remove()  // 删除匹配节点（本身）
el.empty()  // 删除匹配节点的子节点
el.html("")  // 相当于 empty()
```

##### 1. 在节点内部插入内容-------后插

***\*（1）、append()方法在被选元素的结尾（仍然在内部）插入指定内容\****

  提示：append() 和 appendTo() 方法执行的任务相同。不同之处在于：内容的位置和选择器。

​    语法：$(selector).append(content)

  content必需。规定要插入的内容（可包含 HTML 标签）。

```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
    
$(document).ready(function(){
  $("button").click(function(){
    $("p").append(" <b>Hello world!</b>");    // 向 p 元素的结尾添加内容
  });
});
    
</script>
</head>
<body>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button>在每个 p 元素的结尾添加内容</button>
</body>
</html>
```

![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center.png)





 使用函数来附加内容，使用函数在指定元素的结尾插入内容。

​    语法：$(selector).append(function(index,html))

 function(index,html) 必需。规定返回待插入内容的函数。

 index - 可选。接收选择器的 index 位置。

 html - 可选。接收选择器的当前 HTML。



```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
    
$(document).ready(function(){
  $("button").click(function(){
      
    $("p").append(function(n){
      return "<b>This p element has index " + n + "</b>";
    });
      
  });
});
    
</script>
</head>
<body>
<h1>This is a heading</h1>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button>在每个 p 元素的结尾添加内容</button>
</body>
</html>
```


![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center-165793619344851.png)

----------



##### 2. 在节点内部插入内容-------前插

（3）、prepend() 方法在被选元素的开头（仍位于内部）插入指定内容\

 提示：prepend() 和 prependTo() 方法作用相同。差异在于语法：内容和选择器的位置，以及 prependTo() 无法使用函数来插入内容。

​    语法：$(selector).prepend(content)

 content必需。规定要插入的内容（可包含 HTML 标签）。



```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("button").click(function(){
    $("p").prepend("<b>Hello world!</b> ");
  });
});
</script>
</head>
<body>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button>在每个 p 元素的开头插入内容</button>
</body>
</html>
```


![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center-165793619344853.png)



 使用函数来附加内容，使用函数在被选元素的开头插入指定的内容。

​    语法：$(selector).prepend(function(index,html))

 function(index,html) 必需。规定返回待插入内容的函数。

 index - 可选。接受选择器的 index 位置。

 html - 可选。接受选择器的当前 HTML。



```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("button").click(function(){
    $("p").prepend(function(n){
      return "<b>这个 p 元素的 index 是：" + n + "</b> ";
    });
  });
});
</script>
</head>
<body>
<h1>这是一个标题</h1>
<p>这是一个段落。</p>
<p>这是另一个段落。</p>
<button>在每个 p 元素的开头插入内容</button>
</body>
</html>
```

![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center-165793619344854.png)



-----------------



##### 3. 在节点外部插入内容-------后插

***\*（1）、after() 方法在被选元素后插入指定的内容\****

  方法将查找节点，然后把新建的元素添加到节点后面做为节点的兄弟节点。

​    语法：$(selector).after(content)

  content必需。规定要插入的内容（可包含 HTML 标签）。

```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("button").click(function(){
      
    $("p").after("<p>Hello world!</p>");   
    
      
  });
});
</script>
</head>
<body>
<p>This is a paragraph.</p>
<button>在每个 p 元素后插入内容</button>
</body>
</html>
```


![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center-165793619344856.png)



 使用函数来插入内容，使用函数在被选元素之后插入指定的内容。

​    语法：$(selector).after(function(index))

 function(index) 必需。规定返回待插入内容的函数。

 index - 可选。接收选择器的 index 位置。



```html
<html>
<head>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("button").click(function(){
    $("p").after(function(n){
      return "<p>The p element above has index " + n + "</p>";
    });
  });
});
</script>
</head>
<body>
<h1>This is a heading</h1>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button>在每个 p 元素后插入内容</button>
</body>
</html>
```


![img](JQuery%E7%AC%94%E8%AE%B0.assets/Center-165793619344957.png)

-----------



##### 4. 在节点外部插入内容-------前插

***\*（2）、before() 方法在被选元素前插入指定的内容\****

​    语法：$(selector).before(content)

  content 必需。规定要插入的内容（可包含 HTML 标签）。

```
before()方法在每一个匹配的元素之前插入，做为匹配元素的前一个兄弟节点。方法如下:$(target).before(element);例：

$("p").before("<span>下面是个段落</span>");

before方法查找每个元素p，将新建的span元素插入到元素p之前做为p的前一个兄弟节点。
```





（3）**、insertAfter()把匹配的元素插入到另一个指定的元素集合的后面**

 注释：如果该方法用于已有元素，这些元素会被从当前位置移走，然后被添加到被选元素之后。

​    语法：$(content).insertAfter(selector)

 content必需。规定要插入的内容。可能的值：选择器表达式、HTML 标记

 selector必需。规定在何处插入被选元素。

```
insertAfter()方法将新建的元素插入到查找到的目标元素后，做为目标元素的兄弟节点。方法如下：$(element).insertAfter(target);例:

$("<p>insertAfter操作</p>").insertAfter("span");

方法将新建的p元素添加到查找到目标元素span后面，做为目标元素后面的第一个兄弟节点。
```

![image-20220713120555879](JQuery%E7%AC%94%E8%AE%B0.assets/image-20220713120555879.png)

![image-20220713120726025](JQuery%E7%AC%94%E8%AE%B0.assets/image-20220713120726025.png)

![image-20220713120734160](JQuery%E7%AC%94%E8%AE%B0.assets/image-20220713120734160.png)

```c++
`attr()`方法修改属性值**attr(name,value) 

 //设置属性的值
```

```javascript
用600毫秒的时间将段落缓慢的切换显示状态

$("p").toggle("slow");
```

```c++
获取文本框中的值
    
$("input").val();
```

[jQuery 创建和插入元素_A_山水子农的博客-CSDN博客_jquery创建元素](https://blog.csdn.net/qq_27626333/article/details/51927022)

### 3.6.3 获取元素

```js
// HTML结构
<div id="myDiv">这是我的DIV</div>
 
// jQuery代码
var myElement = $("#myDiv"); // 使用ID选择器获取元素本身
console.log(myElement); // 输出：[<div id=​"myDiv">​这是我的DIV​</div>​]
```

##  jQuery 尺寸、位置操作

### 3.7.1 jQuery 尺寸

| 语法                               | 用法                                                  |
| ---------------------------------- | ----------------------------------------------------- |
| width()/height()                   | 取得匹配元素宽度和高度值，只算width/height            |
| innerWidth()/innerHeight()         | 取得匹配元素宽度和高度值，包括padding                 |
| outerWidth()/outerHeight()         | 取得匹配元素宽度和高度值，包括padding和border         |
| outerWidth(true)/outerHeight(true) | 取得匹配元素宽度和高度值，包括padding、border和margin |

> - 以上参数为空，则获取响应值，返回的是数字型
> - 如果参数未数字，则是修改相应值
> - 参数可以不写单位

### 3.7.2 jQuery 位置

① offset() 相对于文档的偏移坐标

+ 语法：jQuery 对象.offset();
+ 返回一个对象，对象中包含了元素的位置
+ 注意：offset() 方法获取的元素的位置，永远参照文档,和定位没有关系. 

- 该方法有2个属性 left 和 top。offset().top 用于获取距离文档顶部的距离，offset().left 用于获取距离文档左侧的距离。

- 可以设置元素的偏移：offset({top:10,left:10})。

-  offset() 方法设置或返回被选元素相对于文档的偏移坐标，跟父级没有关系: 

- <iframe height="300" style="width: 100%;" scrolling="no" title="Untitled" src="https://codepen.io/Auroraol/embed/xxMMeBg?default-tab=html%2Cresult" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true">
    See the Pen <a href="https://codepen.io/Auroraol/pen/xxMMeBg">
    Untitled</a> by Aurora  (<a href="https://codepen.io/Auroraol">@Auroraol</a>)
    on <a href="https://codepen.io">CodePen</a>.
  </iframe>

② position() 相对上级定位参考元素的位置

+ 语法：jQuery 对象.position();

+ 返回的一个对象，对象中包含了元素的位置

+ 注意：position() 方法获取的元素的位置,参照最近的定位元素（和定位有关系）

+ position() 方法用于返回被选元素相对于**带有定位的父级**偏移坐标，如果父级没有定位，则以文档为准。

+ <iframe height="300" style="width: 100%;" scrolling="no" title="Untitled" src="https://codepen.io/Auroraol/embed/oNmmRxE?default-tab=html%2Cresult" frameborder="no" loading="lazy" allowtransparency="true" allowfullscreen="true">
    See the Pen <a href="https://codepen.io/Auroraol/pen/oNmmRxE">
    Untitled</a> by Aurora  (<a href="https://codepen.io/Auroraol">@Auroraol</a>)
    on <a href="https://codepen.io">CodePen</a>.
  </iframe>

③ scrollTop()/scrollLeft() 操作卷去的页面间距

+ 获取

+ - 语法：jQuery 对象.scrollTop();
  - 返回数字

+ 设置

+ - 语法：jQuery 对象.scrollTop(数字)

④ css设置元素的位置

```typescript
import '../public/style/index.less';
import $ from 'jquery'; // 引入 jQuery 库


// 定义食物类 Food
class Food{
    //定义一个属性表示食物所对应的元素
    element: JQuery<HTMLElement>

    constructor(){
        //获取页面中的 food 元素并将其赋值给 element
        this.element = $('#food')
    }

    get x(){
        return this.element.position()?.left
    }

    get y(){
        return this.element.position()?.top
    }

    change(){
        // 生成一个阔机的位置
        // 食物的位置最小足0 最大是298  //304-4-10
        // 蛇移动一次就是一格，一格的大小就是10，所以就要求食物的
        // this.element.scrollLeft(290)
           // Generate a random horizontal position between 0 and 290
           const newX = Math.round(Math.random() * 29) * 10;
           const newy = Math.round(Math.random() * 29) * 10;
        
           // Set the new horizontal position using the css method
           this.element.css('left', `${newX}px`);
           this.element.css('top', `${newy}px`);
    }
}


// 测试
const food = new Food()
console.log(food.x);
console.log(food.y);
food.change()
console.log(food.x);
console.log(food.y);
```

## jQuery 事件

### [JQuery](https://so.csdn.net/so/search?q=JQuery&spm=1001.2101.3001.7020)中的常用事件

| 事件                  | 说明                                         |
| --------------------- | -------------------------------------------- |
| .click()              | 鼠标单击触发事件，参数可选（data，function） |
| .dblclick()           | 双击触发，同上                               |
| .mousedown()/up()     | 鼠标按下/弹起触发事件                        |
| .mousemove()          | 鼠标移动事件                                 |
| .mouseover()/out()    | 鼠标移入/移出触发事件                        |
| .mouseenter()/leave() | 鼠标进入/离开触发事件*                       |
| .hover(func1,func2)   | 鼠标移入调用func1函数，移出调用func2函数     |
| .focusin()            | 鼠标聚焦到该元素时触发事件                   |
| .focusout()           | 鼠标失去焦点时触发事件                       |
| . focus()/.blur()     | 鼠标聚焦/失去焦点触发事件（不支持冒泡）      |
| .change()             | 表单元素发生改变时触发事件                   |
| .select()             | 文本元素被选中时触发事件                     |
| .submit()             | 表单提交动作触发                             |
| .keydown()/up()       | 键盘按键按下/弹起触发                        |
| .on()                 | 多事件的绑定                                 |
| .off()                | 移除事件的绑定                               |
| .trigger(“event”)     | 触发事件event调用                            |
| .triggerHandler()     | 触发事件，不会冒泡，不会触发默认事件         |

#### 事件函数有三种用法

```javascript
//直接绑定事件到元素上
$('.target1').keydown(function(e) {
     //通过对象e获取输入的值
    $("em:first").text(e.target.value)   
});
 
//传递参数调用函数处理
$("#test").click(11111,function(e) {
    //this指向 div元素
    //e.data  => 11111 通过e传递参数数据
});
 
//手动触发已绑定的点击事件
$elem.click()
```

#### mouseover与mouseenter区别

+ 不论鼠标指针穿过被选元素或其子元素，都会触发 mouseover 事件，称作支持冒泡处理，冒泡处理指子元素与父元素共同定义的事件，在触发子元素时，或者没有定义子元素，事件就会向父级传播，引发父级事件触发。
+ 只有在鼠标指针穿过被选元素时，才会触发 mouseenter 事件。

3、form元素是有默认提交表单的行为，如果通过submit处理的话，需要禁止浏览器的这个默认行为。传统的方式是调用事件对象 e.preventDefault() 来处理， jQuery中可以直接在函数中最后结尾return false即可。

```javascript
//回车键或者点击提交表单后禁止浏览器默认跳转：
$('#target2').submit(function() {
    alert('捕获提交表达动作,阻止页面跳转')
    return false;
});
```

#### 事件处理on()绑定事件

基本用法：.on( events ,[ selector ] ,[ data ] )

最常见的给元素绑定一个点击事件，对比一下快捷方式与on方式的不同

```javascript
$("#elem").click(function(){})  //快捷方式
$("#elem").on('click',function(){}) //on方式
 
//多个事件绑定同一个函数，通过空格分离，传递不同的事件名，可以同时绑定多个事件
$("#elem").on("mouseover mouseout",function(){ });
 
//多个事件绑定不同函数
$("#elem").on({
    mouseover:function(){}, 
    mouseout:function(){}
});
 
//将数据传递到处理程序
$( "button" ).on( "click", {    //第二个参数传递数据给函数调用
  name: "Mr.Tory"
}, greet );

function greet(event ) {
  alert( "Hello " + event.data.name ); //输出Hello Mr.Tory
}
```

#### 事件处理 off() 解绑事件

- 语法规范

```javascript
off(events,[selector],[fn])
```

- 概述
  - 在选择元素上移除一个或多个事件的事件处理函数。
  - off() 方法移除用 on() 绑定的事件处理程序。
- 显示参数
  - `events`:一个或多个空格分隔的事件类型和可选的命名空间，或仅仅是命名空间，比如"click", “keydown.myPlugin”, 或者 “.myPlugin”。
  - `selector`:一个最初传递到 .on() 事件处理程序附加的选择器。
  - `fn`:事件处理程序函数以前附加事件上，或特殊值 false。
- 示例

```javascript
off();  // 解除所有事件
off("click");  // 只解除点击事件
off("click", "li");  // 解除事件委托
```

####  事件处理 one() 绑定一次性事件

- 语法规范

```javascript
one(type,[data],fn)
1
```

- 概述
  - 为每一个匹配元素的特定事件（像click）绑定一个一次性的事件处理函数。
- 显示参数
  - `type`:添加到元素的一个或多个事件。由空格分隔多个事件。必须是有效的事件。
  - `data`:将要传递给事件处理函数的数据映射。
  - `fn`:每当事件触发时执行的函数。

####  自动触发事件 trigger()

有些事件希望自动触发，比如轮播图自动播放功能与点击右侧按钮功能一致，可以利用定时器自动触发右侧按钮点击事件，不必鼠标点击触发。

```javascript
el.click();  // 简写形式
el.trigger("type");  // 自动触发形式
el.triggerHandler("type");  // 自动触发形式，不会触发元素的默认行为
```

### 事件对象

| .type              | 事件类型.如果使用一个事件处理函数来处理多个事件, 可以使用此属性获得事件类型,比如click |
| ------------------ | ------------------------------------------------------------ |
| .data              | 事件调用时传入额外参数                                       |
| .target            | 触发该事件的 DOM 元素                                        |
| .which             | 指示按了哪个键或按钮                                         |
| .timeStamp         | 该属性返回从 1970 年 1 月 1 日到事件发生时的毫秒数           |
| .pageX/Y           | 相对于文档左/上边缘的鼠标位置                                |
| .result            | 上一个相同事件处理器函数返回的值                             |
| .preventDefalut()  | 阻止事件的默认动作                                           |
| .stopPropagation() | 取消事件冒泡                                                 |

事件被触发，就会有事件对象的产生

```javascript
on(events,[selector],function(even) { })
even.preventDefault()  // 阻止默认行为 或者用 return false
even.stopPropagation()  // 阻止冒泡
```

```javascript
$("#content").click(function(e) {
   $("#msg").html("<p>外层div元素被单击</p>");
   e.stopPropagation();                         //通过event方法阻止事件冒泡  
});
```

### 使用AJAX请求

通过函数$.ajax({name:val, name:val,...});可以实现异步通信，即在不刷新页面的情况下，页面向服务器发送并请求数据然后再加载到页面中。其参数如下：

- url：链接地址，字符串表示
- data：需发送到服务器的数据，以键值对表示{A: '...', B: '...'}
- type：请求类型，"POST" 或 "GET"
- timeout：请求超时时间，单位为毫秒，数值表示
- cache：是否缓存请求结果，bool表示
- contentType：内容类型，默认为"application/x-www-form-urlencoded"，代表以字符串的形式如 id=2019&password=123456传送数据。当我们要传输复杂数据格式时可以将其设置为指定格式或者为false。
- dataType：服务器响应的数据类型，字符串表示；当填写为json时，回调函数中无需再对数据反序列化为json
- success：请求成功后，执行的回调函数
- error：请求失败后，执行的回调函数
- complete：请求完成后调用的函数，无论请求是成功还是失败，都会调用该函数；如果设置了success与error函数，则该函数在它们之后被调用
- async：是否异步处理，默认为true；设置该值为false后，代码不会继续执行，而是原地等待服务器返回数据，并完成相应的回调函数后，再向下执行
- username：访问认证请求中携带的用户名，字符串表示
- password：返回认证请求中携带的密码，字符串表示
- cache：当发起一次GET请求后，会把获得的结果以缓存的形式进行存储，当再次发起请求时，如果 cache 的值是 true ，那么会直接从缓存中读取，而不是再次发起一个请求
- processData：默认值是 true，将上传数据转化为字符串的形式。当我们上传文件的时候不希望转换，因此要改成false

```javascript
$.ajax({
    url: "/register",
    data: {name: 'Anna'},
    type: "POST",
    dataType: "json",
    success: function(data) {
        data = jQuery.parseJSON(data);  //dataType指明了返回数据为json类型，故不需要再反序列化
        ...
    }
});
```

除了$.ajax()之外，jQuery还在此基础上封装了一些具体的请求函数。$.get(url, data, func, dataType);和$.post(url, data, func, dataType);用于直接以GET或POST形式发送请求。*$.getJSON(url, data, func);用于以GET发送请求并接收JSON数据。$('#info-div').load(url, data, func);*用于将请求的数据填充到DOM节点info-div中。

### 案例

请参考下面这段初始HTML代码：

```html
<div id="n1">
	<p id="n2"><span>CodePlayer</span></p>
	<p id="n3"><span>专注于编程开发技术分享</span></p>
	<em id="n4">https://codeplayer.vip</em>
</div>
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

<p id="n5">Google</p>
```

我们为`<div>`中的所有`<p>`元素绑定点击事件：

```js
// 为div中的所有p元素绑定click事件处理程序
// 只有n2、n3可以触发该事件
$("div").on("click", "p", function(){
    // 这里的this指向触发点击事件的p元素(Element)
	alert( $(this).text() );
});
```

如果要绑定所有的`<p>`元素，你可以编写如下jQuery代码：

```js
//为所有p元素绑定click事件处理程序(注意：这里省略了selector参数)
//n2、n3、n5均可触发该事件
$("p").on("click", function(event){
// 这里的this指向触发点击事件的p元素(Element)
	alert( $(this).text() );
});
```

此外，我们还可以同时绑定多个事件，并为事件处理函数传递一些附加的数据，我们可以通过jQuery为事件处理函数传入的参数event(Event事件对象)来进行处理：

```js
var data = { id: 5, name: "张三" };

// 为n5绑定mouseenter mouseleave两个事件，并为其传入附加数据data
// 附加数据可以是任意类型
$("body").on("mouseenter mouseleave", "#n5", data, function(event){
	var $me = $(this);
	var options = event.data; // 这就是传入的附加数据
	if( event.type == "mouseenter"){
		$me.html( "你好，" + options.name + "!");		
	}else if(event.type == "mouseleave" ){
		$me.html( "再见!");		
	}			
});
```

此外，即使符合条件的元素是on()函数执行后新添加，绑定事件对其依然有效。同样以初始HTML代码为例，我们可以编写如下jQuery代码：

```js
// 为div中的所有p元素绑定click事件处理程序
// 只有n2、n3可以触发该事件
$("div").on("click", "p", function(event){
	alert( $(this).text() );
});

// 后添加的n6也可以触发上述click事件，因为它也是div中的p元素
$("#n1").append('<p id="n6">上述绑定的click事件对此元素也生效!</p>');
```

参数events还支持为事件类型附加额外的命名空间。当为同一元素绑定多个相同类型的事件处理函数时。使用命名空间，可以在触发事件、移除事件时限定触发或移除的范围。

```js
function clickHandler(event){
	alert( "触发时的命名空间:[" + event.namespace + "]");
}

var $p = $("p");

// A：为所有p元素绑定click事件，定义在foo和bar两个命名空间下
$p.on( "click.foo.bar", clickHandler );

// B：为所有p元素绑定click事件，定义在test命名空间下
$p.on( "click.test", clickHandler );

var $n2 = $("#n2");

// 触发所有click事件
$n2.trigger("click"); // 触发A和B (event.namespace = "")

// 触发定义在foo命名空间下的click事件
$n2.trigger("click.foo"); // 触发A (event.namespace = "foo")
// 触发定义在bar命名空间下的click事件
$n2.trigger("click.bar"); // 触发A (event.namespace = "bar")
// 触发同时定义在foo和bar两个命名空间下的click事件
$n2.trigger("click.foo.bar"); // 触发A (event.namespace = "bar.foo")

// 触发定义在test命名空间下的click事件
$n2.trigger("click.test"); // 触发B (event.namespace = "test")

// 移除所有p元素定义在foo命名空间下的click事件处理函数
$p.off( "click.foo" ); // 移除A
```

on()函数的参数eventsMap是一个对象，可以"属性-值"的方式指定多个"事件类型-处理函数"。对应的示例代码如下：

```js
var data = { id: 5, name: "张三" };

var events = {
	"mouseenter": function(event){
		$(this).html( "你好，" + event.data.name + "!");		
	},
	
	"mouseleave": function(event){
		$(this).html( "再见!");
	}		
};

//为n5绑定mouseenter mouseleave两个事件，并为其传入附加数据data
$("body").on(events, "#n5", data);
```

### 综合代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- 引入jQuery -->
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script>
        var data = { id: 5, name: "张三" };

        // 入口函数
        $(function(){
           // 在html加载完成后执行的代码

            // 案例1   
            //    //方法1
            //    $("p").click(function(){
            //        alert($(this).text());
            //    });
            //方法2
            $("p").on("click", function(event){
            // 这里的this指向触发点击事件的p元素(Element)
                alert($(this).text());
            });


            // 案例2
            // 为`<body>`中的所有`id=n5`元素绑定事件：
            // 为n5绑定mouseenter mouseleave两个事件，并为其传入附加数据data
            // 附加数据可以是任意类型
            $("body").on("mouseenter mouseleave", "#n5", data, function(event){
                var $me = $(this);
                var options = event.data; // 这就是传入的附加数据
                if(event.type == "mouseenter"){
                    $me.html( "你好，" + options.name + "!");		
                }else if(event.type == "mouseleave" ){
                    $me.html( "再见!");		
                }			
            });


            //案例3
            // 为div中的所有p元素绑定click事件处理程序
            // 只有n2、n3可以触发该事件
            $("div").on("click", "p", function(event){
                alert( $(this).text() );
            });
            // 后添加的n6也可以触发上述click事件，因为它也是div中的p元素
            $("#n1").append('<p id="n6">我是创建在div中的元素,绑定的click事件对此元素也生效!</p>');


            //案例4
            // on()函数的第一个参数eventsMap是一个对象，可以"属性-值"的方式指定多个"事件类型-处理函数"
            var events = {
                "mouseenter": function(event){
                    //event.data == 传进来的参数
                    $(this).html( "你好，" + event.data.name + "!");		
                },
                "mouseleave": function(event){
                    $(this).html( "再见!");
                }		
            };
            //为n5绑定mouseenter mouseleave两个事件，并为其传入附加数据data
            $("body").on(events, ".n6", data);

        });
    </script>
    
</head>
<body>
    <div id="n1">
        <p id="n2"><span>CodePlayer</span></p>
        <p id="n3"><span>jquery事件处理</span></p>
        <em id="n4">https://</em>
    </div>
    <p id="n5">Google</p>
    <p class="n6">案例4</p>
</body>
</html>
```

详细代码见jsCode\事件\jQuery事件处理方式\demo01.html

## html自带的事件(一般用JQuery替代)

==通过html自带的事件 + js的函数实现==

#### 鼠标事件

- click ： 单击
- dblick：双击
- mousedown：鼠标按下
- mouseup：鼠标抬起
- mouseout：鼠标离开
- mousemove：鼠标一移动
- mouseenter：鼠标进入

```html
<!--html-->
<button onclick="myClick()">鼠标单击</button> 
<button ondblclick="myDBClick()">鼠标双击</button> 
<button onmousedown="myMouseDown()" onmouseup="myMouseUp()">鼠标按下和抬起</button> 
<button onmouseover="myMouseOver()" onmouseout="myMouseOut()">鼠标悬浮和离开</button> 
<button onmousemove="myMouseMove()">鼠标移动</button> 
<button onmouseenter="myMouseEnter()" onmouseleave="myMouseLeave()">鼠标进入和离开</button>
```

```js
//JS
   function myClick() {  
       console.log("你单击了按钮！");  
   }  
   function myDBClick() {  
       console.log("你双击了按钮！");  
   }  
   function myMouseDown() {  
       console.log("鼠标按下了！");  
   }  
   function myMouseUp() {  
       console.log("鼠标抬起了！");  
   }  
   function myMouseOver() {  
       console.log("鼠标悬浮！");  
   }  
   function myMouseOut() {  
       console.log("鼠标离开！")  
   }  
   function myMouseMove() {  
       console.log("鼠标移动！")  
   }  
   function myMouseEnter() {  
       console.log("鼠标进入！")  
   }  
   function myMouseLeave() {  
       console.log("鼠标离开！")  
   }   
```

#### 键盘事件

- keydown：按键按下
- keyup：按键抬起
- keypress：按键按下抬起

```html
<!--html-->
<input id="name" type="text" onkeydown="myKeyDown(this.id)" onkeyup="myKeyUp(this.id)"> 
```

```js
//JS 
/*输出输入的字符*/
function myKeyDown(id) {
 	console.log(document.getElementById(id).value);
}

/*按键结束，字体转换为大写*/
function myKeyUp(id) {
  	var text = document.getElementById(id).value;
	document.getElementById(id).value = text.toUpperCase();
}
```

#### HTML事件

- load：文档加载完成
- select：被选中的时候
- change：内容被改变
- focus：得到光标
- resize：窗口尺寸变化
- scroll：滚动条移动

```js
//html
<body onload="loaded()"> 
  <div style="height: 3000px" ></div> 
  <input type="text" id="name" onselect="mySelect(this.id)"> 
  <input type="text" id="name2" onchange="myChange(this.id)"> 
  <input type="text" id="name3" onfocus="myFocus()"> 
</body> 

//JS
window.onload = function () {  
       console.log("文档加载完毕！");  
   };  
   /*window.onunload = function () {  
    alert("文档被关闭！");  
    };*/  
   /*打印选中的文本*/  
   function mySelect(id) {  
       var text = document.getElementById(id).value;  
       console.log(text);  
   }  
   /*内容被改变时*/  
   function myChange(id) {  
       var text = document.getElementById(id).value;  
       console.log(text);  
   }  
   /*得到光标*/  
   function myFocus() {  
       console.log("得到光标！");  
   }  
   /*窗口尺寸变化*/  
window.onresize = function () {  
       console.log("窗口变化！")  
   };  
   /*滚动条移动*/  
window.onscroll = function () {  
       console.log("滚动");  
   }  
```

### 事件模型

```js
//html
<body> 
<!--脚本模型：行内绑定--> 
<button onclick="alert('hello')">hello</button> 
<!--内联模型--> 
<button onclick="showHello()">hello2</button> 
<!--动态绑定--> 
<button id="btn">hello3</button> 
</body> 

//JS
<script> 
   function showHello() {  
       alert("hello");  
   }  
   /*DOM0：同一个元素只能添加一个同类事件  
    * 如果添加多个，后面的会把前面的覆盖掉*/  
   var btn = document.getElementById("btn");  
btn.onclick = function () {  
       alert("hello");  
   };  
btn.onclick = function () {  
       alert("hello world");  
   };  
   /*DOM2:可以给一个元素添加多个同类事件*/  
   btn.addEventListener("click", function () {  
       alert("hello1");  
   });  
   btn.addEventListener("click", function () {  
       alert("hello2");  
   });  
   /*不同浏览器的兼容写法*/  
   /*IE*/  
   if (btn.attachEvent) {  
       btn.attachEvent("onclick", function () {  
           alert("hello3");  
       });  
   /*W3C*/  
   } else {  
       btn.addEventListener("click", function () {  
           alert("hello4");  
       })  
   }  
</script> 
```

### 事件冒泡与事件捕获

#### 冒泡的案例：

```text
<div id="s1">s1
    <div id="s2">s2</div>
</div>

<script>
    s1.addEventListener("click",function(e){
        console.log("s1 冒泡事件");
    },false);
    s2.addEventListener("click",function(e){
        console.log("s2 冒泡事件");
    },false);
</script>
```

当我们点击s1的时候，执行结果如下：

![img](JQuery%E7%AC%94%E8%AE%B0.assets/v2-0dc7e700ad7827d141019e98666c77ec_720w.webp)

当我们点击s2的时候，执行结果如下：

![img](JQuery%E7%AC%94%E8%AE%B0.assets/v2-d0063f490cc97dd80fcdba70e0318b19_720w.webp)

#### 捕获的案例：

```text
<div id="s1">s1
    <div id="s2">s2</div>
</div>

<script>
    s1.addEventListener("click",function(e){
        console.log("s1 捕获事件");
    },true);
    s2.addEventListener("click",function(e){
        console.log("s2 捕获事件");
    },true);
</script>
```

当我们点击s1的时候，执行结果如下：

![img](JQuery%E7%AC%94%E8%AE%B0.assets/v2-903701f437ea838f17c3839331847544_720w.webp)

当我们点击s2的时候，执行结果如下：

![img](JQuery%E7%AC%94%E8%AE%B0.assets/v2-a28ca4f5b2be096fe080442e54151f21_720w.webp)

### 事件代理

#### 不使用事件代理：

```text
//html
<ul id="color-list">
    <li>red</li>
    <li>yellow</li>
    <li>blue</li>
    <li>green</li>
    <li>black</li>
    <li>white</li>
</ul>

//Js 给所有节点添加监听事件
<script>
(function(){
    var color_list = document.getElementById('color-list');
    var colors = color_list.getElementsByTagName('li');
    for(var i=0;i<colors.length;i++){                          
        colors[i].addEventListener('click',showColor,false);
    };
    function showColor(e){
        var x = e.target;
        console.log("The color is " + x.innerHTML);
    };
})();
</script>
```

#### 使用事件代理：

```text
//html
<ul id="color-list">
    <li>red</li>
    <li>yellow</li>
    <li>blue</li>
    <li>green</li>
    <li>black</li>
    <li>white</li>
</ul>

//Js 只绑定一个事件处理函数（优化）
<script>
(function(){
    var color_list = document.getElementById('color-list');
    color_list.addEventListener('click',showColor,false);
    function showColor(e){
        var x = e.target;
        if(x.nodeName.toLowerCase() === 'li'){
            console.log('The color is ' + x.innerHTML);
        }
    }
})();
</script>
```

### 阻止默认事件：

```text**
//html
<body>
  <a href="http://www,baidu.com" onclick="stop(event)">百度</a>
</body>

//JS
<script>
  function stop(event){
    if(event.preventDefault()){
      event.preventDefault()
    }else{
      event.returnValue = false
    }
    alert('不跳转！')
}
</script>
```





```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>判断闰年</title>
    <script type="text/javascript">
        function is_leapyear(){
            var year = document.getElementById('year').value;
            if (parseInt(year)!=NaN && year>0) {
                if (year % 4== 0 && year % 100 !== 0 || year % 400 == 0) {
                    alert(year + '年是闰年')
                } else {
                    alert(year + '年不是闰年')
                }
            } else {
                alert('输入有误，请重新输入！')
            }
        }
    </script>
</head>
<body>
    <h1>判断闰年</h1>
    <form>
        <p>
            <label>年份：</label>
            <input type="text" id="year">
        </p>
        <input type="button" onclick="is_leapyear()" value="提交">
    </form>
</body>
</html>
```

#