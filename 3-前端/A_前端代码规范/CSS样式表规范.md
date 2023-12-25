## CSS开发规范

怎么说呢,大部分前端编写样式表的时候并没有特意按照良好地CSS书写规范来写CSS代码,这样写出来的CSS代码不止不够直观,甚至非常影响代码的阅读体验与维护难度,这边我参考了一些文章以及个人经验进行梳理整合,整理出一些常用或者指的学习的CSS开发规范

## Ⅰ- 常见语法规范

### 1、常见语法规范总结

 ###### 常见语法规范

1. 用`两个空格来代替tab` – 这是唯一能保证在所有环境下获得一致展现的方法。
2. 为选择器分组时，将单独的选择器单独放在一行。
3. 为了代码的易读性，在每个声明块的左花括号前添加一个空格。
4. 声明块的右花括号应当单独成行。
5. 每条声明语句的 `:` 后应该插入一个空格。
6. 为了获得更准确的错误报告，每条声明都应该独占一行。
7. 所有声明语句都应当以分号结尾。最后一条声明语句后面的分号是可选的，但是，如果省略这个分号，你的代码可能更易出错。
8. 对于以逗号分隔的属性值，每个逗号后面都应该插入一个空格（例如，`box-shadow`）。
9. 不要在 `rgb()`、`rgba()`、`hsl()`、`hsla()` 或 `rect()` 值的*内部*的逗号后面插入空格。这样利于从多个属性值（既加逗号也加空格）中区分多个颜色值（只加逗号，不加空格）。
10. 对于属性值或颜色参数，省略小于 1 的小数前面的 0 （例如，`.5` 代替 `0.5`；`-.5px` 代替 `-0.5px`）。
11. 十六进制值应该全部小写，例如，`#fff`。在扫描文档时，小写字符易于分辨，因为他们的形式更易于区分。
12. 尽量使用简写形式的十六进制值，例如，用 `#fff` 代替 `#ffffff`。
13. 为选择器中的属性添加双引号，例如，`input[type="text"]`。[只有在某些情况下是可选的](http://mathiasbynens.be/notes/unquoted-attribute-values#css)，但是，为了代码的一致性，建议都加上双引号。
14. 避免为 0 值指定单位，例如，用 `margin: 0;` 代替 `margin: 0px;`。

 ###### 直接上例子

```css
/************************** 不好的CSS 写法 **********************************/
.selector, .selector-secondary, .selector[type=text] {
  padding:15px;
   /* 避免为 0 值指定单位，例如，用 `margin: 0;` 代替 `margin: 0px;` */
  margin:0px 0px 15px;
  background-color:rgba(0, 0, 0, 0.5);
  /* 十六进制值应该全部小写,同时尽量使用简写形式的十六进制值  */  
  box-shadow:0px 1px 2px #CCC,inset 0 1px 0 #FFFFFF  
}

/************************** 好的 CSS 写法 ***************************************/
.selector,                           --为选择器分组时，将单独的选择器单独放在一行
.selector-secondary,
.selector[type="text"] {             --建议为选择器中的属性 `[type="text"]` 添加双引号
  padding: 15px; 				    --每条声明语句的 `:` 后应该插入一个空格
  margin-bottom: 15px; 
  /* 
    不要在 `rgb()`、`rgba()`、`hsl()`、`hsla()` 或 `rect()` 值的内部的逗号后面插入空格
    对于属性值或颜色参数，省略小于 1 的小数前面的 0
    */  
  background-color: rgba(0,0,0,.5);
  /* 
    所有声明语句都应当以分号结尾.后一条声明语句后面的分号是可选的,但略这个分号,你的代码可能更易出错
    对于以逗号分隔的属性值，每个逗号后面都应该插入一个空格
   */
  box-shadow: 0 1px 2px #ccc, inset 0 1px 0 #fff; 
}    							   --声明块的右花括号应当单独成行
```

### 2、声明顺序

相关的属性声明应当归为一组，并按照下面的顺序排列：

1. Positioning  --由于定位（positioning）可以从正常的文档流中移除元素，并且还能覆盖盒模型（box model）相关的样式,因此排在首位
2. Box model  --盒模型排在第二位，因为它决定了组件的尺寸和位置。
3. Typographic  --文字类
4. Visual     --其他属性只是影响组件的*内部（inside）*或者是不影响前两组属性，因此排在后面。

```css
.declaration-order {
  /* Positioning 定位之类*/
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 100;

  /* Box-model 盒模型*/
  display: block;
  float: right;
  width: 100px;
  height: 100px;

  /* Typography 排版印刷类 */
  font: normal 13px "Helvetica Neue", sans-serif;
  line-height: 1.5;
  color: #333;
  text-align: center;

  /* Visual 视觉类 */
  background-color: #f5f5f5;
  border: 1px solid #e5e5e5;
  border-radius: 3px;

  /* Misc 其他混杂类 */
  opacity: 1;
}
```

### 3、尽量不要使用 `@import` 引入css文件

与 `<link` 标签相比，`@import` 指令要慢很多，不光增加了额外的请求次数，还会导致不可预料的问题。替代办法有以下几种：

- 使用多个 `<link` 元素
- 通过 Sass 或 Less 类似的 CSS 预处理器将多个 CSS 文件编译为一个文件
- 通过 Rails、Jekyll 或其他系统中提供过 CSS 文件合并功能

```html
<!-- 使用 link 元素 --
<link rel="stylesheet" href="core.css"

<!-- 应避免的 @imports --
<style
  @import url("more.css");
</style
```

### 4、媒体查询（Media query）的位置

将媒体查询放在尽可能相关规则的附近。不要将他们打包放在一个单一样式文件中或者放在文档底部。如果你把他们分开了，将来只会被大家遗忘(同时也会增大维护力度)

### 5、带前缀的属性

当使用特定厂商的带有前缀的属性时，通过缩进的方式，让每个属性的值在垂直方向对齐，这样便于多行编辑。

```css
/* 带前缀的属性 */
.selector {
  -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
          box-shadow: 0 1px 2px rgba(0,0,0,.15);
}
```

### 6、单行规则声明

对于**只包含一条声明**的样式，为了易读性和便于快速编辑，建议将语句放在同一行。对于带有多条声明的样式，还是应当将声明分为多行。

这样做的关键因素是为了错误检测 –- 例如，CSS 校验器指出在 xxx 行有语法错误。如果是单行单条声明，你就不会忽略这个错误；如果是单行多条声明的话，你就要仔细分析避免漏掉错误了。

```css
/* 一行上的单个声明,这种直接放在一行即可 */
.span1 { width: 60px; }
.span2 { width: 140px; }
.span3 { width: 220px; }
.icon           { background-position: 0 0; }
.icon-home      { background-position: 0 -20px; }
.icon-account   { background-position: 0 -40px; }

/* 多个声明，需要将样式分行 */
.sprite {
  display: inline-block;
  width: 16px;
  height: 15px;
  background-image: url(./hong.png);
}
```

### 7、简写形式的属性

在需要显示地设置所有值的情况下，应当尽量限制使用简写形式的属性声明。常见的滥用简写属性声明的情况如下

- `padding`
- `margin`
- `font`
- `background`
- `border`
- `border-radius`

大部分情况下，我们不需要为简写形式的属性声明指定所有值。例如，HTML 的 heading 元素只需要设置上、下边距（margin）的值，因此，在必要的时候，只需覆盖这两个值就可以。过度使用简写形式的属性声明会导致代码混乱，并且会对属性值带来不必要的覆盖从而引起意外的副作用。

```css
/* 不好的例子 */
.element {
  margin: 0 0 10px;  -- 实际上只要 单独指定 `margin-bottom: 10px` 即可;这样可能会导致覆盖上、水平方向的样式被覆盖
  background: red;
  background: url("image.jpg");
  border-radius: 3px 3px 0 0;  --有时候简化实际上反而不易理解,像这种可以不简化
}

/* 好的例子 */
.element {
  margin-bottom: 10px;
  background-color: red;
  background-image: url("image.jpg");
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
}
```

**正好趁着这个机会补充梳理下简写属性的知识点 ** --查阅资料 : MDN的[`CSS 的简写属性`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/Shorthand_properties); 

#### ①简写属性 的举🌰

1. [`background`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/background) 属性就是一个简写属性，它可以定义 
   * [`background-color`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/background-color)
   * [`background-image`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/background-image)
   * [`background-repeat`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/background-repeat) 
   * [`background-position`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/background-position) 
2. 类似地，最常见的字体相关的属性可以使用 [`font`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/font) 的简写，
3. 盒子（box）各方向的外边距（margin） 可以使用 [`margin`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/margin) 这个简写。

#### ② 棘手的边界情况

虽然它们使用起来非常方便，但在使用时，仍需牢记一些边界情况：

##### a) 没有指定的值会被设置为它的初始值,实际上将会覆盖之前设置的值

没有指定的值会被设置为它的初始值。这听起来似乎本来就很合理的样子，但这确实意味着，它将会覆盖之前设置的值。因此：

```css
background-color: red;
background: url(images/bg.gif) no-repeat top right;
```

以上样式是将 [ background-color ] 的默认值 `transparent`，而不会将 background 的 color 值设置为 `red`

因为第二条规则优先。

##### b) 关键词 inherit 只可以应用于单独属性

* 关键词 inherit 只可以应用于单独属性（individual properties）
* 如果应用于一个简写属性（shorthand property），则必须整体应用，而能对简写属性值的每一个部分单独应用。
* 由于单独属性的漏掉的值会被它们的初始值（initial value）替代，因此不可能允许单个属性通过省略继承的 。
* 这意味着让一个属性的值使用继承值的唯一方法就是使用值是 inherit 的普通属性（longhand property）。

##### c) 简写属性不试图强制它们替代属性的值的特定顺序

1. 简写属性不试图强制它们替代属性的值的特定顺序。
2. 这适用于当这些属性使用不同类型的值时，因为这个时候顺序并不重要。
3. 但当几个属性可以设置相同值的时候，就没那么简单了。处理这些情况分以下几种类型:

###### 处理和盒子（box）边界（edge）相关的属性时,比如 [`border-style`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/border-style)、[`margin`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/margin) 或者 [`padding`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/padding)，

- **1个值的语法：**`border-width: 1em` — 这一个值表示所有的边框宽度,margin、padding同理
- **2个值的语法：**`border-width: 1em 2em` — 第一个值表示垂直方向的，即 top 和 bottom；第二个值表示水平方向的，即 left 和 right
- **3个值的语法：**`border-width: 1em 2em 3em` — 第一个值表示 top；第二个值表示水平方向的，即 left 和 right； 第三个值表示 bottom
- **4个值的语法：**`border-width: 1em 2em 3em 4em` — 这四个值分别表示 top、right、bottom、left，总是按此顺序，即从 top 开始的顺时针顺序（Top-Right-Bottom-Left 首字母与英文单词 trouble 的顺序一致：TRBL）
- 可以发现,实际上1~4个参数不同时,代表的含义完全不同

###### 同样，在处理和盒子的角相关的属性时，比如 [`border-radius`](https://developer.mozilla.org/zh-CN/docs/Web/CSS/border-radius)

* **1个值的语法：**`border-radius: 1em` — 这一个值表示所有的表框角度的半径
* **2个值的语法：**`border-radius: 1em 2em` — 第一个值表示 top-left 和 bottom-right 方向的角；第二个值表示 top-right 和 bottom-left 方向的角
* **3个值的语法：**`border-radius: 1em 2em 3em` — 第一个值表示 top-left 方向的角 ，第二个值表示top-right 和 bottom-left 方向的角，第三个值表示 bottom-right 方向的角
* **4个值的语法：**`border-radius: 1em 2em 3em 4em` — 这四个值分别表示top-left、 top-right、 bottom-right 、bottom-left 方向的角。总是按此顺序，即从top-left开始的顺时针顺序

#### ③  Background 属性

background 有以下属性：

```css
background-color: #000;
background-image: url(images/bg.gif);
background-repeat: no-repeat;
background-position: top right;
```

可以简写成一行声明：

```css
background: #000 url(images/bg.gif) no-repeat top right;
```

简写的形式实际上等价于以上普通属性再加上 `background-attachment: scroll` 以及 CSS3 中的一些附加属性。

#### ④ Font 属性

下面的声明：

```css
font-style: italic;
font-weight: bold;
font-size: .8em;
line-height: 1.2;
font-family: Arial, sans-serif;
```

可以简写成下面的：

```css
font: italic bold .8em/1.2 Arial, sans-serif;
```

这个简写声明实际上等价于以上普通属性再加上 `font-variant: normal` 和 `font-size-adjust: none` (CSS2.0 / CSS3)，`font-stretch: normal` (CSS3)。

#### ⑤ Border 属性

对于 border 来说，宽度、颜色和类型是可以被简写到一个声明里的。比如：

```css
border-width: 1px;
border-style: solid;
border-color: #000;
```

可以简写成：

```css
border: 1px solid #000;
```

#### ⑥ Margin 和 Padding 属性

margin 和 padding 值的简写版本类似。下面的 CSS 声明：

```css
margin-top: 10px;
margin-right: 5px;
margin-bottom: 10px;
margin-left: 5px;
```

和下面的声明是一样的（注意，值是从 top 顺时针开始的：top、right、bottom、接着是 left）

```css
margin: 10px 5px 10px 5px;
```

 ###### Margin 对于多个值有以下的简写规则： `重要`

- 当只有**一个**值声明时，该值会用于所有**四个**值。
- 当只有**两个**值声明时，第一个值用于**上边距**和**下边距**，第二个值用于**左边距**和**右边距**。
- 当有三个值声明时，第一个值用于**上边距**，第二个值用于**左边距**和**右边距**，第三个值用于**下边距。**
- 当有四个值声明时，其会按顺序用于上、右、下、左边距（按顺时针）。

### 8、注释

* 代码是写给自己看的,同时也是写给同事看的. 我们要确保我们的代码能够自描述、注释良好并且易于他人理解
* 好的代码注释应该是能够传达上下文关系以及代码目的的
* 而不是一味地重申一下类名或者模块名,说的就是你啊 铁子
* 对于较长的注释，务必书写完整的句子；对于一般性注解，可以书写简洁的短语。

```css
/* 不好的栗子 */
/* Modal header */
.modal-header {
  ...
}

/* 好的栗子 */
/* 为.modal-title和.modal-close包装元素  */
.modal-header {
  ...
}
```

 ###### 一个推荐的注释写法

```css
/* Header */
内容区
/* End Header */
```

### 9、class命名

- class 名称中只能出现小写字符和破折号（dashe）（**不是下划线，也不是驼峰命名法**）。破折号应当用于相关 class 的命名（类似于命名空间）（例如，`.btn` 和 `.btn-danger`）。
- 避免过度任意的简写。`.btn` 代表 *button*，但是 `.s` 不能表达任何意思。
- class 名称应当尽可能短，并且意义明确。
- 使用有意义的名称。使用有组织的或目的明确的名称，不要使用表现形式的名称。
- 基于最近的父 class 或基本（base） class 作为新 class 的前缀。
- 使用 `.js-*` class 来标识行为（与样式相对），并且不要将这些 class 包含到 CSS 文件中。

```css
/* 不好的🌰 */
.t { ... }
.red { ... }
.header { ... }

/* 好的🌰 */
.tweet { ... }
.important { ... }
.tweet-header { ... }
```

### 10、选择器

- 对于通用元素使用 class ，这样利于渲染性能的优化。
- 对于经常出现的组件，避免使用属性选择器（例如，`[class^="..."]`）。浏览器的性能会受到这些因素的影响。
- 选择器要尽可能短，并且尽量限制组成选择器的元素个数，建议不要超过 3 。
- **只有**在必要的时候才将 class 限制在最近的父元素内（也就是后代选择器）（例如，不使用带前缀的 class 时 – 前缀类似于命名空间）。

```css
/* 不好的🌰 */
span { ... }
.page-container #stream .stream-item .tweet .tweet-header .username { ... }
.avatar { ... }

/* 好的🌰 */
.avatar { ... }
.tweet-header .username { ... }
.tweet .avatar { ... }
```

### 11、代码组织

- 以组件为单位组织代码段。
- 制定一致的注释规范。
- 使用一致的空白符将代码分隔成块，这样利于扫描较大的文档。
- 如果使用了多个 CSS 文件，将其按照组件而非页面的形式分拆，因为页面会被重组，而组件只会被移动。

### 12、编辑器配置

将你的编辑器按照下面的配置进行设置，以避免常见的代码不一致和差异：

- 用两个空格代替制表符（soft-tab 即用空格代表 tab 符）。
- 保存文件时，删除尾部的空白符。
- 设置文件编码为 UTF-8。
- 在文件结尾添加一个空白行。

## Ⅱ - 一些关于**W3C CSS标准**的经验总结

1. 少用偏门。类似break-word断行，z-index手动分层，还有像垂直对齐等等这些偏门CSS最好少用，由于不一定所有浏览器都支持，而且极难通过W3C检测。
2. center不是float的值。很多老手都会把center误认为是float的值，而偏偏不是如此。center只是text-align的值。
3. 对齐不能包括两个值。很多老手会在float或者text-align中填写两个值，比如：float:left top。这是不允许的，浏览器也无法识别。
4. 滚动条颜色最好不要自定义。很多浏览器不能正常识别自定义颜色的滚动条，况且很多自定义颜色都不能通过W3C。
5. 单独滚动条设置。如今经常使用overflow-x（横向滚动条）或者overflow-y（纵向滚动条），在设置这个的时候经常会发现并不是所有的客户端上都无效果，大家在设置的时候最好在body和html同时进行设置。然而这个CSS也不是CSS2.1支持的(CSS2.1支持overflow，同时定义横纵滚动条)，直到CSS3才支持这种定义方式。尽量少用。
6. background和color颜色相反会遭到警告。

## Ⅲ - 一些容易忽略的细节

### 1、连字符CSS选择器命名规范

1.长名称或词组可以使用中横线来为选择器命名。

2.不建议使用“_”下划线来命名CSS选择器，为什么呢？

- 输入的时候少按一个shift键；
- 浏览器兼容问题 （比如使用_tips的选择器命名，在IE6是无效的）
- 能良好区分JavaScript变量命名（JS变量命名是用“_”）

这里有一篇破折号与下划线的详细讨论 中文篇：[点击查看](http://www.cnblogs.com/kaiye/archive/2011/06/13/3039046.html)

### 2、不要随意使用Id

* id在JS是唯一的，不能多次使用
* 而使用class类选择器却可以重复使用，另外id的优先级优先与class
* 所以id应该按需使用，而不能滥用。

### 3、可以为选择器添加状态前缀

有时候可以给选择器添加一个表示状态的前缀，让语义更明了，比如下面是添加了“.is-”前缀。

```css
.widthView {}
/* 可以加个状态 */
.is-widthView {}
```

## Ⅳ - CSS命名规范

 命名永远是程序员的痛啊!!! 面对如山海般的代码,每天命名那么多变量名,实在词穷,所以就有一些'不法分子'取了各种稀奇古怪的名字,实在是让代码阅读性一降再降

 这里也只能罗列出一些常见命名规则,多少能在命名的时候有所参照,防止一个意思取出了十几种名字

### 1、常见的CSS命名规则

* 头：header
* 内容：content/container
* 尾：footer
* 导航：nav
* 侧栏：sidebar
* 栏目：column
* 页面外围控制整体佈局宽度：wrapper
* 左右中：left right center
* 登录条：loginbar
* 标志：logo
* 广告：banner
* 页面主体：main
* 热点：hot
* 新闻：news
* 下载：download
* 子导航：subnav
* 菜单：menu
* 子菜单：submenu
* 搜索：search
* 友情链接：friendlink
* 页脚：footer
* 版权：copyright
* 滚动：scroll
* 内容：content
* 标签：tags
* 文章列表：list
* 提示信息：msg
* 小技巧：tips
* 栏目标题：title
* 加入：joinus
* 指南：guide
* 服务：service
* 注册：regsiter
* 状态：status
* 投票：vote
* 合作伙伴：partner

### 2、Id的命名

#### ① **页面结构**

* 容器: container
* 页头：header
* 内容：content/container
* 页面主体：main
* 页尾：footer
* 导航：nav
* 侧栏：sidebar
* 栏目：column
* 页面外围控制整体布局宽度：wrapper
* 左右中：left right center

#### ② 导航

* 导航：nav
* 主导航：mainnav
* 子导航：subnav
* 顶导航：topnav
* 边导航：sidebar
* 左导航：leftsidebar
* 右导航：rightsidebar
* 菜单：menu
* 子菜单：submenu
* 标题: title
* 摘要: summary

#### ③ **功能**

* 标志：logo
* 广告：banner
* 登陆：login
* 登录条：loginbar
* 注册：register
* 搜索：search
* 功能区：shop
* 标题：title
* 加入：joinus
* 状态：status
* 按钮：btn
* 滚动：scroll
* 标签页：tab
* 文章列表：list
* 提示信息：msg
* 当前的: current
* 小技巧：tips
* 图标: icon
* 注释：note
* 指南：guild
* 服务：service
* 热点：hot
* 新闻：news
* 下载：download
* 投票：vote
* 合作伙伴：partner
* 友情链接：link
* 版权：copyright

### 3、注意事项

1.一律小写;
2.尽量用英文;
3.不加下划线;
4.尽量不缩写，除非一看就明白的单词。

### 4、CSS样式表文件命名

* 主要的 master.css
* 模块 module.css
* 基本共用 base.css
* 布局、版面 layout.css
* 主题 themes.css
* 专栏 columns.css
* 文字 font.css
* 表单 forms.css
* 补丁 mend.css
* 打印 print.css