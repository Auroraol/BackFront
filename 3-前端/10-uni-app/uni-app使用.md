# 📂 uniapp项目搭建及基本架构

官方文档：[https://uniapp.dcloud.io/quickstart-cli](https://uniapp.dcloud.io/quickstart-cli)

首先确保vue-cil已经安装，若未安装，使用以下命令安装：

```bash
npm i -g @vue/cli
```

通过 cli 生成项目：

```bash
# vue2 + vue-cli
vue create -p dcloudio/uni-preset-vue my-project # 正式版
vue create -p dcloudio/uni-preset-vue#alpha my-alpha-project # alpha版

# vue3 + vite 
npx degit dcloudio/uni-preset-vue#vite my-vue3-project # javascript
npx degit dcloudio/uni-preset-vue#vite-ts my-vue3-project # typescript
```

详情见: [uni-app项目报错解决](https://blog.csdn.net/xiaoxia188/article/details/122161329)

创建好的项目结构如下：

```bash
┌─components            uni-app公共组件目录
│  └─comp-a.vue         可复用的a组件
├─wxcomponents           小程序私有组件，遵循小程序的开发方式，也可使用Vue的写法
│   └──miniprogram-slide-view
│        ├─index.js
│        ├─index.vue
│        ├─index.json
│        └─index.wxss
├─pages                 业务页面文件存放目录
│  ├─index
│  │  └─index.vue       index页面
│  └─list
│     └─list.vue        list页面
├─static                存放应用引用静态资源（如图片、视频等）的地方，注意：静态资源只能存放于此
├─main.js               Vue初始化入口文件
├─App.vue               应用配置，用来配置App全局样式以及监听
├─manifest.json         配置应用名称、appid、logo、版本等打包信息
└─pages.json            配置页面路由、导航条、选项卡等页面类信息，应用的生命周期
```

## 项目结构规划

最重要的是src目录的规划，一些关键的目录：

- `api` 存放请求
- `components` 存放全局组件
- `directives` 存放全局指令
- `filter` 存放全局过滤器
- `hybrid` 存放混合编译页面
- `library` 自定义库
- `pages` 页面
- `static` 静态资源
- `store` Vuex状态管理

**必要的工具**

工欲善其事，必先利其器，列举常用的一些开发工具：

- pug
- pug-plain-loader
- stylus
- stylus-loader
- node-sass
- prettier
- postcss-comment

## 使用vscode开发

开发工具:

- [使用HBuilderX开发](https://uniapp.dcloud.io/quickstart)
- [使用WebStrom开发](https://ask.dcloud.net.cn/article/36307)
- [使用VSCode开发](https://ask.dcloud.net.cn/article/36286)

常用插件:

![](uni-app%E4%BD%BF%E7%94%A8.assets/image-20231204224800534.png)

## 配置文件

### pages.json

配置路由的文件, 基本结构为：

```json
{
	"pages": [
      {
        "path": "pages/Login",
        "style": {
          "navigationBarTitleText": "登录",
          "navigationStyle": "custom"
        }
      },
      {
        "path": "pages/Home",
        "style": {
          "navigationBarTitleText": "首页",
          "navigationStyle": "custom"
        }
      },
      {
        "path": "pages/Ucenter",
        "style": {
          "navigationBarTitleText": "我的",
          "navigationStyle": "custom"
        }
      }
	],
  "subPackages": [{
    "root": "package1",
    "pages": [{
      "path": "page1",
      "style": {
        "navigationBarTitleText": "page1"
      }
    }]
  }],
  "tabBar": {
    "color": "#7A7E83",
    "selectedColor": "#3cc51f",
    "borderStyle": "black",
    "backgroundColor": "#ffffff",
    "list": [{
      "pagePath": "pages/Home",
      "iconPath": "static/tabbar/home1.svg",
      "selectedIconPath": "static/tabbar/home2.svg",
      "text": "首页"
    }, {
      "pagePath": "pages/Ucenter",
      "iconPath": "static/tabbar/my1.svg",
      "selectedIconPath": "static/tabbar/my2.svg",
      "text": "我的"
    }]
  },
	"globalStyle": {
		"navigationBarTextStyle": "black",
		"navigationBarTitleText": "uni-app",
		"navigationBarBackgroundColor": "#F8F8F8",
		"backgroundColor": "#F8F8F8"
	}
}
```

#### 页面配置

通过 `pages` 进行页面配置

包括以下属性

- `path` 页面路径
- `style` 页面默认样式

其中 `style` 包括以下属性：

- `navigationBarTitleText` 标题栏文字
- `navigationStyle` 可选 default/custom, 默认 default, custom 即取消默认的原生导航栏
- `disableScroll` 是否允许页面滚动, 默认true
- `enablePullDownRefresh` 是否开启下拉刷新, 默认false
- `onReachBottomDistance` 页面上拉触底事件触发时距页面底部距离，单位只支持px

#### 子包

通过 `subPackages` 进行子包配置，主要针对于微信小程序

包括以下属性：

- `root` 子包根目录
- `pages` 同根下的pages

#### 底部导航

通过 `tabBar` 配置底部导航

包括以下属性：

- `color` 未被选择的tab文字颜色
- `selectedColor` 被选择的tab文字颜色
- `borderStyle` 边框色
- `backgroundColor` 背景色
- `list` 底部导航的页面

其中 `list` 包括以下属性：

- `pagePath` 页面路径
- `iconPath` 未激活的图标路径
- `selectedIconPath` 激活的图标路径
- `text` 文字

#### 全局样式

`globalStyle` 用于配置默认的全局页面样式，可被 `pages[].style` 覆盖

包括以下属性：

- `navigationBarBackgroundColor` 导航栏背景颜色 (同状态栏背景色)
- `navigationBarTextStyle` 导航栏标题颜色及状态栏前景颜色，仅支持 `black`/`white`
- `navigationBarTitleText` 导航栏标题文字内容
- `backgroundColor` 窗口的背景色 (仅微信小程序支持)
- `enablePullDownRefresh` 是否开启下拉刷新, 默认false
- `onReachBottomDistance` 页面上拉触底事件触发时距页面底部距离，单位只支持px

详见: [uniapp pages.json](https://uniapp.dcloud.io/collocation/pages)

### manifest.json

详见: [uniapp manifest.json](https://uniapp.dcloud.io/collocation/manifest)

## 平台特性

### 条件编译

由于各个平台有起独特性，可以使用类似于C语言的条件编译语句，指定一段代码只在特定的环境中被编译。

格式：

```javascript
//#ifdef %PLATFORM%
平台特有的API实现
//#endif
```


判断某个平台存在：

```javascript
//#ifdef %PLATFORM%
需条件编译的代码
//#endif
```

判断某个平台不存在：

```javascript
//#ifndef %PLATFORM%
需条件编译的代码
//#endif
```

[%PLATFORM%](https://uniapp.dcloud.io/platform?id=%e6%9d%a1%e4%bb%b6%e7%bc%96%e8%af%91) 可取值如下：

| 值                      | 平台                                                         | 相关规范                                                     |
| :---------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| APP-PLUS                | App                                                          | [HTML5+ App 规范](http://www.html5plus.org/doc/)             |
| APP-PLUS-NVUE或APP-NVUE | App nvue                                                     |                                                              |
| H5                      | H5                                                           |                                                              |
| MP-WEIXIN               | 微信小程序                                                   | [微信小程序](https://developers.weixin.qq.com/miniprogram/dev/api/) |
| MP-ALIPAY               | 支付宝小程序                                                 |                                                              |
| MP-BAIDU                | 百度小程序                                                   |                                                              |
| MP-TOUTIAO              | 字节跳动小程序                                               |                                                              |
| MP-QQ                   | QQ小程序                                                     |                                                              |
| MP-360                  | 360小程序                                                    |                                                              |
| MP                      | 微信小程序/支付宝小程序/百度小程序/字节跳动小程序/QQ小程序/360小程序 |                                                              |
| QUICKAPP-WEBVIEW        | 快应用通用(包含联盟、华为)                                   |                                                              |
| QUICKAPP-WEBVIEW-UNION  | 快应用联盟                                                   |                                                              |
| QUICKAPP-WEBVIEW-HUAWEI | 快应用华为                                                   |                                                              |


**支持的文件**

- .vue
- .js
- .css
- pages.json
- 各预编译语言文件，如：.scss、.less、.stylus、.ts、.pug

例如：

```javascript
//#ifdef APP-PLUS
plus.push.addEventListener('click', function (msg) {
    var payload = null;
    var action = '';
    if (msg.payload) {
        if (typeof msg.payload === 'string') {
            payload = JSON.parse(msg.payload);
        }
        action = payload.action;
        if (action === 'open') {
            plus.webview.open(payload.url);
        }
    }
});
//#endif
```

在HTML或CSS中也可以使用条件编译：

```html
<!-- #ifdef MP-WEIXIN -->
需条件编译的代码
<!-- #endif -->
```

```javascript
/* #ifdef %PLATFORM% */
需条件编译的代码
/* #endif */
```

可以使用 `||` 连接多个平台，例如：

```javascript
// #ifdef H5 || MP-WEIXIN
需条件编译的代码
// #endif
```

### 使用Vue进行开发

#### 兼容支持

由于需要兼容多端，许多浏览器特性、Vue特性是不能使用的，支持情况如下：

支持：

- 条件渲染（v-if、v-show）
- 列表渲染（v-for）
- 计算属性（computed）
- 事件处理（v-on，@）、事件修饰符

不支持：

- 所有的 BOM／DOM 都不能用
- v-html 指令不能用，可以使用 rich-text组件 代替
- Vue 过滤器（filter）
- data 必须声明为返回一个初始数据对象的函数
- 按键修饰符
- 模板中不支持复杂的JavaScript表达式

#### 事件处理

uni-app 支持大部分 Vue 中的事件，对部分事件进行了改写：

```javascript
// 事件映射表，左侧为 WEB 事件，右侧为 ``uni-app`` 对应事件
{
  click: 'tap',
  touchstart: 'touchstart',
  touchmove: 'touchmove',
  touchcancel: 'touchcancel',
  touchend: 'touchend',
  tap: 'tap',
  longtap: 'longtap',
  input: 'input',
  change: 'change',
  submit: 'submit',
  blur: 'blur',
  focus: 'focus',
  reset: 'reset',
  confirm: 'confirm',
  columnchange: 'columnchange',
  linechange: 'linechange',
  error: 'error',
  scrolltoupper: 'scrolltoupper',
  scrolltolower: 'scrolltolower',
  scroll: 'scroll'
}
```

在 input 和 textarea 中 change 事件会被转为 blur 事件。

##### 事件修饰符

- stop 的使用会阻止冒泡，但是同时绑定了一个非冒泡事件，会导致该元素上的 catchEventName 失效！
- prevent 可以直接干掉，因为uni-app里没有什么默认事件，比如 submit 并不会跳转页面
- self 没有可以判断的标识
- once 也不能做，因为uni-app没有 removeEventListener，虽然可以直接在 handleProxy 中处理，但非常的不优雅，违背了原意，暂不考虑

### 其他不支持的部分

#### 模板中不支持复杂的JavaScript表达式

目前可以使用的有 `+ - * % ?: ! == === > < [] .` 。

比如以下模板语法就不支持：

```xml
<view>{{ message.split('').reverse().join('') }}</view>
```

通常这种情况使用计算属性即可。

### 生命周期

#### 应用程序生命周期

这是整个程序的生命周期函数，仅可在App.vue中监听，在其它页面监听无效。

- **onLaunch**	当uni-app 初始化完成时触发（全局只触发一次）
- **onShow**	当 uni-app 启动，或从后台进入前台显示
- **onHide**	当 uni-app 从前台进入后台

#### 页面生命周期

uni-app 支持如下页面生命周期函数：

- **onLoad**	监听页面加载，其参数为上个页面传递的数据，参数类型为Object（用于页面传参）
- **onShow**	监听页面显示
- **onReady**	监听页面初次渲染完成
- **onHide**	监听页面隐藏
- **onUnload**	监听页面卸载
- **onPullDownRefresh**	监听用户下拉动作，一般用于下拉刷新，参考示例
- **onReachBottom**	页面上拉触底事件的处理函数
- **onShareAppMessage**	用户点击右上角分享，仅微信小程序支持
- **onNavigationBarButtonTap**	监听原生标题栏按钮点击事件，参数为Object，仅5+ App支持
- **onPageScroll**	监听页面滚动，参数为Object

#### Vue 实例生命周期

跟正常开发 Vue 一样，由以下 Vue 的生命周期构成：

- **beforeCreate**
- **created**
- **beforeMount**
- **mounted**
- **beforeUpdate**
- **updated**

### 页面事件监听

#### pageScrollTo

pageScrollTo 可以滚动到指定页面指定位置，通常用于制作返回顶部，以及一些滑动特效。

```javascript
uni.pageScrollTo({
  scrollTop: 0,
  duration: 300
});
```

- **scrollTop**  Number  必填，滚动到页面的目标位置（单位px）
- **duration**  Number  可选，滚动动画的时长，默认300ms，单位 ms

#### 下拉刷新

首先，得在 page.json 中开启当前页的下拉刷新配置：

```json
{
  "pages": [
    {
      "path": "pages/index/index",
      "style": {
        "enablePullDownRefresh": true
      }
    }
  ]
}
```

这样就可以在页面中监听其下拉事件了：

```javascript
export default {
  onPullDownRefresh () {
    console.log('refresh');
    setTimeout(function () {
      uni.stopPullDownRefresh();
    }, 1000);
  }
}
```

:::info
onPullDownRefresh 是跟 onLoad 同级的页面事件。
:::

下拉事件可以通过下拉页面触发，也可通过事件绑定触发，比如绑定一个按钮事件：

```vue
<template lang='pug'>
  .test
    button(@tap='refresh') refresh
</template>

<script>
export default {
  methods: {
    refresh () {
      uni.startPullDownRefresh();
    }
  }
}
</script>
```

- `uni.startPullDownRefresh()` 开始刷新
- `uni.stopPullDownRefresh()` 停止刷新

#### 上拉加载更多

首先，得在 page.json 中开启当前页的触底距离，也可以不配置，默认为50：

```json
{
  "pages": [
    {
      "path": "pages/index/index",
      "style": {
        "onReachBottomDistance": 50
      }
    }
  ]
}
```

在页面中监听其触底事件：

```javascript
export default {
  onReachBottom () {
    console.log('get more data');
  }
}
```

封装了一个上拉加载更多的组件：

```vue
<template lang="pug">
scroll-view.wrap(scroll-y
  upper-threshold="0.01"
  @scrolltoupper='scrolltoupper'
  @scrolltolower='scrolltolower'
)
  .content
    .list
      .item(v-for='(item, key) in list' :key='key') {{item}}
  load-more(:loadingType='loadingType')
</template>

<script>
import throttle from 'lodash.throttle'
export default {
  data () {
    return {
      loadingType: 0,
      last: 20,
      list: Array.from({length: this.last}, (v, i) => `item${i+1}`)
    }
  },
  methods: {
    scrolltoupper: throttle(function () {
      if (this.loadingType) return
      this.loadingType = 1
      console.log('scrolltoupper');
    }, 2000),
    scrolltolower: throttle(function () {
      if (this.loadingType) return
      this.loadingType = 1
      setTimeout(() => {
        this.loadingType = 2
      }, 1000)
    }, 2000)
  }
}
</script>

<style scoped lang="stylus">
.wrap
  height: 100vh;
  .content
    .item
      font-size: 1.2em;
      padding: 1em;
      border-bottom: 1px dashed #ff0;
</style>
```

这是 load-more 组件的写法，参考了官方 Demo 的写法：

`components/load-more.vue`

```vue
<template>
	<view class="load-more">
		<view class="loading-img" v-show="loadingType === 1 && showImage">
			<view class="load1">
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
			</view>
			<view class="load2">
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
			</view>
			<view class="load3">
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
				<view :style="{background:color}"></view>
			</view>
		</view>
		<text class="loading-text" :style="{color:color}">{{loadingType === 0 ? contentText.contentdown : (loadingType === 1 ? contentText.contentrefresh : contentText.contentnomore)}}</text>
	</view>
</template>

<script>
	export default {
		name: "load-more",
		props: {
			loadingType: {
				//上拉的状态：0-loading前；1-loading中；2-没有更多了
				type: Number,
				default: 0
			},
			showImage: {
				type: Boolean,
				default: true
			},
			color: {
				type: String,
				default: "#777777"
			},
			contentText: {
				type: Object,
				default () {
					return {
						contentdown: "上拉显示更多",
						contentrefresh: "正在加载...",
						contentnomore: "没有更多数据了"
					};
				}
			}
		},
		data() {
			return {}
		}
	}
</script>

<style>
	.load-more {
		display: flex;
		flex-direction: row;
		height: 80upx;
		align-items: center;
		justify-content: center;
	}

	.loading-img {
		height: 24px;
		width: 24px;
		margin-right: 10px;
	}

	.loading-text {
		font-size: 15px;
		color: #777777;
	}

	.loading-img>view {
		position: absolute;
	}

	.load1,
	.load2,
	.load3 {
		height: 24px;
		width: 24px;
	}

	.load2 {
		transform: rotate(30deg);
	}

	.load3 {
		transform: rotate(60deg);
	}

	.loading-img>view view {
		width: 6px;
		height: 2px;
		border-top-left-radius: 1px;
		border-bottom-left-radius: 1px;
		background: #777;
		position: absolute;
		opacity: 0.2;
		transform-origin: 50%;
		-webkit-animation: load 1.56s ease infinite;
	}

	.loading-img>view view:nth-child(1) {
		transform: rotate(90deg);
		top: 2px;
		left: 9px;
	}

	.loading-img>view view:nth-child(2) {
		-webkit-transform: rotate(180deg);
		top: 11px;
		right: 0px;
	}

	.loading-img>view view:nth-child(3) {
		transform: rotate(270deg);
		bottom: 2px;
		left: 9px;
	}

	.loading-img>view view:nth-child(4) {
		top: 11px;
		left: 0px;
	}

	.load1 view:nth-child(1) {
		animation-delay: 0s;
	}

	.load2 view:nth-child(1) {
		animation-delay: 0.13s;
	}

	.load3 view:nth-child(1) {
		animation-delay: 0.26s;
	}

	.load1 view:nth-child(2) {
		animation-delay: 0.39s;
	}

	.load2 view:nth-child(2) {
		animation-delay: 0.52s;
	}

	.load3 view:nth-child(2) {
		animation-delay: 0.65s;
	}

	.load1 view:nth-child(3) {
		animation-delay: 0.78s;
	}

	.load2 view:nth-child(3) {
		animation-delay: 0.91s;
	}

	.load3 view:nth-child(3) {
		animation-delay: 1.04s;
	}

	.load1 view:nth-child(4) {
		animation-delay: 1.17s;
	}

	.load2 view:nth-child(4) {
		animation-delay: 1.30s;
	}

	.load3 view:nth-child(4) {
		animation-delay: 1.43s;
	}

	@-webkit-keyframes load {
		0% {
			opacity: 1;
		}

		100% {
			opacity: 0.2;
		}
	}
</style>
```

注意到，这里没用使用图片，也没有使用字体图标，而是纯代码生成的一个加载更多动画。

全局引入：`main.js`

```javascript
import loadMore from './components/load-more.vue'
Vue.component('loadMore', loadMore)
```

#### 页面滚动监听

通过 onPageScroll 监听页面滚动：

```javascript
export default {
  onPageScroll (e) {
    console.log(e.scrollTop); // 页面在垂直方向已滚动的距离（单位px）
  }
}
```

可以使用防抖函数防止事件频繁调用：

```javascript
import throttle from 'lodash.throttle'
export default {
  onPageScroll: throttle(function (e) {
    console.log(e.scrollTop);
  }, 2000)
}
```

#### 页面分享

使用 onShareAppMessage 可设置页面分享，接收一个参数 obj，包括如下内容：

- **from**  String  转发事件来源。button：页面内转发按钮；menu：右上角转发菜单
- **target** 如果 from 值是 button，则 target 是触发这次转发事件的 button，否则为 undefined

onShareAppMessage 需要设置一个返回值，用于自定义分享的标题、路径、图片，都是可选的。

- **title** 默认为当前小程序名称
- **path** 默认为当前页面 path ，若自定义必须是以 / 开头的完整路径
- **imageUrl** 默认使用当前页面截图

```javascript
export default {
  onShareAppMessage (e) {
    console.log(e);
    return {
      title: 'Hello world',
      path: '/',
      imageUrl: '/static/imgs/test.png'
    }
  }
}
```

<img src="uni-app%E4%BD%BF%E7%94%A8.assets/image-20231204230202171.png" alt="image-20231204230202171" style="zoom:50%;" />

#### scroll-view 滚动监听

在 scroll-view 组件中，提供了三个事件监听：

- **scrolltoupper** 滑动到容器顶部触发的事件，距离由 upper-threshold 决定，默认 50
- **scrolltolower** 滑动到容器底部触发的事件，距离由 lower-threshold 决定，默认 50
- **scroll** 容器滚动时触发，接收一个 event 参数，可获取当前位置 event.detail = {scrollLeft, scrollTop, scrollHeight, scrollWidth, deltaX, deltaY}

```vue
<template lang="pug">
scroll-view.wrap(scroll-y
  upper-threshold="0.01"
  lower-threshold="0.01"
  @scrolltoupper='scrolltoupper'
  @scrolltolower='scrolltolower'
)
  .content
    .list
      .item(v-for='(item, key) in list' :key='key') {{item}}
</template>

<script>
import throttle from 'lodash.throttle'
export default {
  data () {
    return {
      list: Array.from({length: this.last}, (v, i) => `item${i+1}`)
    }
  },
  methods: {
    scrolltoupper: throttle(function () {
      console.log('scrolltoupper');
    }, 2000),
    scrolltolower: throttle(function () {
      console.log('scrolltolower');
    }, 2000)
  }
}
</script>

<style scoped lang="stylus">
.wrap
  height: 100vh;
  .content
    .item
      font-size: 1.2em;
      padding: 1em;
      border-bottom: 1px dashed #ff0;
</style>
```

以上代码，使用了 `lodash.throttle` 防止抖动。

### 参考资料

- [uni-app 条件编译](https://uniapp.dcloud.io/platform)
- [页面事件处理函数](https://developers.weixin.qq.com/miniprogram/dev/framework/app-service/page.html#%E9%A1%B5%E9%9D%A2%E4%BA%8B%E4%BB%B6%E5%A4%84%E7%90%86%E5%87%BD%E6%95%B0)
- [scroll-view](http://uniapp.dcloud.io/component/scroll-view)
- [微信小程序开发(十四)scroll-view实现下拉刷新上拉加载更多](https://blog.csdn.net/zhuming3834/article/details/74452139/)

## 样式规范

### 支持的单位

经测试，uni-app支持以下CSS单位：

- px
- upx
- rpx
- rem
- em
- vw、vh
- %

### rpx

uni-app样式采用rpx为单位，无论什么机型的手机，将屏幕宽度切分为 750 份（即屏幕宽度锁定为750rpx，iPhone6屏幕宽度375的两倍）。

计算公式为：

```
页面元素宽度在 uni-app 中的宽度计算公式 = 750 * 元素在设计稿中的宽度 / 设计稿基准宽度
```

比如：

- 若设计稿宽度为 640px，元素 A 在设计稿上的宽度为 100px，那么元素 A 在 uni-app 里面的宽度应该设为：750 * 100 / 640，结果为：117rpx。
- 若设计稿宽度为 375px，元素 B 在设计稿上的宽度为 200px，那么元素 B 在 uni-app 里面的宽度应该设为：750 * 200 / 375，结果为：400rpx。

### rpx与px转换

```javascript
  function rpx2px(num) {
    return (num * uni.getSystemInfoSync().windowWidth) / 750
  }

  // 将物理像素转化为设备像素
  function px2rpx(num) {
    return (num * 750) / uni.getSystemInfoSync().windowWidth
  }
```

参考：

- [早期 uni-app 提供了 upx ，目前已经推荐统一改为 rpx 了](https://ask.dcloud.net.cn/article/36130)
- [uni-app 尺寸单位](https://uniapp.dcloud.io/frame?id=%e5%b0%ba%e5%af%b8%e5%8d%95%e4%bd%8d)

### upx与px转换

:warning:
此API已过时，建议改为rpx

使用 `uni.upx2px(Number)` 将upx转换为px

注意：动态绑定的 style 不支持直接使用 upx。

```xml
<!-- - 静态upx赋值生效 -->
<view class="test" style="width:200upx"></view>
<!-- - 动态绑定不生效 -->
<view class="test" :style="{width:winWidth + 'upx;'}"></view>
```

需要先换算为px再进行赋值：

```vue
<template>
  <view>
    <view :style="{width: halfWidth}">
      半屏宽度
    </view>
  </view>
</template>

<script>
  export default {
    computed: {
      halfWidth() {
        return uni.upx2px(750 / 2) + 'px';
      }
    }
  }
</script>
```

### 样式导入

可以直接通过 [@import ](/import ) 导入样式。 

```html
<style>
@import "../../common/uni.css";

.uni-card {
  box-shadow: none;
}
</style>
```

### 选择器

uni-app 仅支持有限的选择器：

- `.class`
- `#id`
- `element`
- `element,element`
- `::after`
- `::before`

### 固定值

uni-app 中以下组件的高度是固定的，不可修改：

- 导航栏高度固定为 44px
- tabBar 高度固定为 56px

### 自定义组件命名限制

自定义组件不能使用以下名称作为组件名：

```
a、canvas、cell、content、countdown、datepicker、div、element、embed、header、image、img、indicator、input、link、list、loading-indicator、loading、marquee、meta、refresh、richtext、script、scrollable、scroller、select、slider-neighbor、slider、slot、span、spinner、style、svg、switch、tabbar、tabheader、template、text、textarea、timepicker、trisition-group、trisition、video、view、web
```

# 小兔鲜儿项目模板代码

拉取小兔鲜儿项目模板代码

```
git clone http://git.itcast.cn/heimaqianduan/erabbit-uni-app-vue3-ts.git heima-shop
```

![image-20231205102544364](uni-app%E4%BD%BF%E7%94%A8.assets/image-20231205102544364.png)