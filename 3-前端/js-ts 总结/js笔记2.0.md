# 变量



**使用 `let` 或 `const` 来定义变量,  不要使用`var`**：

```js
//方法1:   定义全局变量或者局部局部
let globalVar = 10;    

//方法2:   不能修改
const anotherGlobalVar = 20;  
```

使用 `var` 声明的全局变量存在变量提升（hoisting）和潜在的全局污染问题，因此不推荐使用。

# 工具类

## 判断对象是否为空

```java
//输入想要检测的json数据 如果为空返回返回false
export function isNullObject(model) {
  if (typeof model === "object") {
    let hasProp = false;
    for (const prop in model) {
        hasProp = true;
        break;
    }
    if (hasProp) {
        return false;
    }
    return true;
  } else {
      throw "model is not object";
  }
}
```

## 数据类型检测

```javascript
//检测变量的数据类型
export function getParamType(item) {
    if (item === null) return null;
    if (item === undefined) return undefined;
    return Object.prototype.toString.call(item).slice(8, -1);
}
//返回String Function Boolean Object Number
```

## 数组去重

```javascript
export function arrayUniq(array){
  return Array.from(new Set(array));
}
```

# 函数

## 函数定义

**建议使用 `匿名函数` 或 `箭头函数` 来定义函数**：

```js
// 方法0: 普通函数
function add(a, b) {
  return a + b;
}

//方法1:   匿名函数, 调用的方式，函数调用必须写到函数体下面
const sum = function(a, b) {
	return a + b;
}

//方法2:   箭头函数  ❤基于原型的面向对象不使用箭头函数 ❤DOM事件回调函数不使用箭头函数
const sum1 = (a, b) => {
	return a + b;
}
```

使用 `命名函数` 声明的函数存在函数提升（hoisting）和潜在的全局污染问题，因此不推荐使用。

如果使用`let`关键字声明的函数`sum`可以在定义后重新赋值，也就是说可以修改`sum`指向的函数, 修改后前一个函数是无法调用的 (**不推荐**)：

```js
let sum = function(a, b) {
  return a + b;
}

sum = function(a, b) {   // 允许  
  return a * b;
}
console.log(sum(100,2)) //200
```

**`命名函数`一般用于DOM事件回调函数:**

```js
let btn = document.querySelector('#btn');
btn.addEventListener('click',function(){
    console.log(duanzi);                                                                       })
```

## 函数参数

### 参数(传值/引用)

- a)	基本类型-传值；
- b)	引用类型-地址；

基本类型是通过传值（传递副本）的方式进行传递的。JavaScript 中的基本类型包括字符串（String）、数字（Number）、布尔值（Boolean）、null、undefined 和 Symbol。

引用类型是通过地址（引用）的方式进行传递的。JavaScript 中的引用类型包括对象（Object）、数组（Array）、函数（Function）等。

**基本类型-传值例子:**

```js
var abs = function(x){
    //手动抛出异常来判断
    if (typeof x !== 'number') {
        throw 'not a number';
    }
    if (x >= 0) {
        return x;
    }else{
        return -x;
    }
}

abs(-100)  //100
```

**引用类型-地址例子:**

```js
let modifyObject = function (obj) {
  obj.value = 10;
}

let myObject = { value: 5 };

modifyObject(myObject);
console.log(myObject.value); // 输出 10
```

### 默认值

```html
<script>
  // 设置参数默认值
  function sayHi(name="小明", age=18) {
    document.write(`<p>大家好，我叫${name}，我今年${age}岁了。</p>`);
  }
  // 调用函数
  sayHi();
  sayHi('小红');
  sayHi('小刚', 21);
</script>
```

### 动态参数

`arguments` 是函数内部内置的伪数组变量，它包含了调用函数时传入的所有实参。

```html
<script>
  // 求生函数，计算所有参数的和
  function sum() {
    // console.log(arguments);
    let s = 0;
    for(let i = 0; i < arguments.length; i++) {
      s += arguments[i];
    }
    console.log(s);
  }

  // 调用求和函数
  sum(5, 10); // 两个参数
  sum(1, 2, 4); // 两个参数
</script>
```

### rest参数

例如：函数定义了两个参数，但是传入了第三个参数，要怎么取出第三个参数呢？

```js
function test(a,b){
    console.log('a->'+a);
    console.log('b->'+b);
    //以前的方法取第三个元素：
    if (arguments.length>2) {
        for(var i = 2; i<arguments.length;i++){
            console.log(arguments[i])
        }
    }
}

//现在的方法(rest参数)：在函数中定义rest参数
function test1(a,b,...rest){
    console.log('a->'+a);
    console.log('b->'+b);
    console.log(rest);
}
```

### 剩余参数(ES6新增)

```js
<script>
  function config(baseURL, ...other) {
    console.log(baseURL);
    // other 是真数组，动态获取实参
    console.log(other);
  }

  // 调用函数
  config('http://baidu.com', 'get', 'json');
</script>
```

# 模块

导出

```js
/* 默认导出 */
export default function() {
    //......
}; 

/* 按需导出 */
export const a = 10

// 方法0: 普通函数
export function add(a, b) {
  return a + b;
}

//方法1:   匿名函数
export const sum = function(a, b) {
	return a + b;
}

//方法2:   箭头函数
export const fn = () => {
  console.log('内容')
}


// 或者用 as 来命名
const e = 1;
const f = 2;
export { e as outE, f as outF };
```

导入

```js
// 默认导入时的接收名称可以任意名称，只要是合法的成员名称即可
import result from './xxx.js'

import { a, b as c, fn } from './xxx.js'
```

# 面向对象

详细见[面向对象1笔记](.\参考\第3天(重要).md)

详细见[面向对象2笔记](.\参考\第4天(重要).md)

<font color="red">支持动态为对象添加方法/属性</font>

## 方式1: 字面量对象

字面量对象适合创建单个对象或对象比较简单的情况 

```js
let user = {
    name: '小明',
    // 普通函数 es6写法 仅仅是function xxx() {} 的简化写法  (推荐)
    walk() {
        console.log(this.name);  //小明
    },
    // 普通函数 es5写法    
    setName: function (name) {
      this.name = this.name;
    },
    // 箭头函数  在字面量对象中使用箭头函数会导致 this 指向错误。
    // 一个参数的时候()可省
    eat:()=>{
        console.log(this.name)   //undefined
    },
};

// 普通函数
function sayHi() {
    console.log("动态1-" + this.name);  //动态1-小明
}

// 匿名函数
const sayHi2 = function(){
    console.log("动态2-" + this.name);   //动态2-小明
}

// 箭头函数  
const sayHi3 = ()=>{
    console.log("动态3-" + this.name);   //动态3-undefined
}


// ------------------------- 调用 -------------------------    
user.walk();
user.eat();

//动态为对象添加方法/属性
user.eat1 = sayHi;
user.eat1()

user.eat2 = sayHi2;
user.eat2()

user.eat3 = sayHi3;
user.eat3()
```

运行结果

```
小明
undefined
动态1-小明
动态2-小明
动态3-undefined
```

## 方式2: 构造函数

构造函数适合需要创建多个相似对象实例、需要在创建对象时执行额外逻辑的情况

### 动态为对象添加方法/属性 方式1

```js
function User(name) {
  this.name = name;
  //方式1  匿名函数   (推荐)
  this.walk = function() {
    console.log(this.name);    // 小明
  };
   //方式3 箭头函数
   this.eat = () => {
      console.log(this.name);   // 小明 
    }
}

// 普通函数
function sayHi() {
  console.log("动态1-" + this.name);  //动态1-小明
}

// 匿名函数(推荐)
const sayHi2 = function() {
  console.log("动态2-" + this.name);  //动态2-小明
}

// 箭头函数
const sayHi3 = () => {
  console.log("动态3-" + this.name);   //动态3-undefined
}


// ------------------------- 调用 -------------------------
const user = new User('小明');
user.walk();  
user.eat();  

//动态为对象添加方法/属性
user.eat1 = sayHi;
user.eat1();  

user.eat2 = sayHi2;
user.eat2();  

user.eat3 = sayHi3;
user.eat3();  
```

```
小明
小明
动态1-小明
动态2-小明
动态3-undefined
```

### 动态为对象添加方法/属性 方式2 [推荐]

注意: **当访问对象的属性或方法时，先在当前实例对象是查找，然后再去原型对象查找，并且原型对象被所有实例共享。**

```js
function User(name) {
  	this.name = name;
    //方式1  匿名函数   (推荐)
    this.walk = function() {
        console.log(this.name);    // 小明
    };
    //方式3 箭头函数
    this.eat = () => {
        console.log(this.name);   // 小明 
    }
}

// 使用原型对象的方式
User.prototype.sayHi = function() {
  console.log("动态1-" + this.name);
};

User.prototype.sayHi2 = function() {
  console.log("动态2-" + this.name);
};

User.prototype.sayHi3 = function() {
  console.log("动态3-" + this.name);
};

const user = new User('小明');
user.sayHi();   //动态2-小明
user.sayHi2();  //动态2-小明
user.sayHi3();  //动态2-小明
```

```js
小明
小明
动态1-小明
动态2-小明
动态3-小明
```

## 方法3: class

ECMAScript 6 新增,  详细见[面向对象2笔记](.\参考\第4天(重要).md)



# 运算符（...）

展开运算符的常见作用大致分为：

1. `展开数组：`展开运算符（…）会把数组中各项展开显示。
2. `拼接数组：`展开运算符还可以用来拼接两个数组，把各项元素连接到一起，形成一个新数组。（可以在数组任意位置拼接）
3. `拷贝数组：`执行的都是浅拷贝(只遍历一层)。
4. `在函数中使用：`展开运算符可以运用在函数参数中。
5. `构造字面量对象:`展开运算符在对`对象`使用时，应当注意以`{}包裹起来。`

## 1、展开数组

```javascript
	let arr1 = [1, 3, 5, 7, 9]
    console.log(arr1)
    console.log(...arr1)  // 展开一个数组
```

![image-20240430164235736](js%E7%AC%94%E8%AE%B02.0.assets/image-20240430164235736.png)

## 2、拼接数组

```javascript
	let arr1 = [1, 3, 5, 7, 9]
	let arr2 = [2, 4, 6, 8, 10]
	let arr3 = [...arr1, ...arr2]  // 连接数组
	console.log(arr3)
```

![image-20240430164636807](js%E7%AC%94%E8%AE%B02.0.assets/image-20240430164636807.png)

可以在任何地方拼接，比如

```js
var parts = ['shoulders', 'knees'];
var lyrics = ['head', ...parts, 'and', 'toes']; 
console.log(lyrics)
```

![image-20240430164907618](js%E7%AC%94%E8%AE%B02.0.assets/image-20240430164907618.png)

## 3、拷贝数组

```javascript
var arr = [1, 2, 3];
var arr2 = [...arr]; 
arr2.push(4);

// arr2 此时变成 [1, 2, 3, 4]
// arr 不受影响
```

## 4、函数中的展开语法

```js
// javascript内置数组方法reduce
function sum(...numbers) {
  //前后
  return numbers.reduce((preValue, currentValue) => {
    return preValue + currentValue
  })
}

console.log(sum(1, 2, 3, 4))
// 控制台输出：10
```

## 5、构造字面量对象时使用展开语法

在构造字面量对象时，也可以使用展开运算符：

```javascript
var obj1 = { foo: 'bar', x: 42 };
var obj2 = { foo: 'baz', y: 13 };

var clonedObj = { ...obj1 };
// 克隆后的对象: { foo: "bar", x: 42 }

var mergedObj = { ...obj1, ...obj2 };
// 合并后的对象: { foo: "baz", x: 42, y: 13 }
```

```js
const obj1 = {a: 1, b: 2};
const obj2 = {...obj1, c: 30};
console.log(obj2);         // {a:1, b:2, c:30}
const obj3 = {b: 20, c: 30};
const obj4 = {...obj2, ...obj3};  // 合并对象
 
console.log(obj4);         // {a:1, b:20, c:30}
```

# 解构赋值

JavaScript 对象解构赋值的基本语法：

```js
const { 属性1, 属性2, ... } = 对象;
```

其中，`属性1`, `属性2` 等是对象的属性名，`对象` 是要解构的对象。

例如，假设有一个对象 `person`：

```js
const person = {
  name: 'John',
  age: 30,
  gender: 'male'
};
```

可以使用解构赋值来提取对象中的属性并赋值给变量：

```js
const { name, age } = person;
console.log(name); // 输出: John
console.log(age); // 输出: 30
```

此外，也可以在解构赋值语法中指定默认值：

```js
const { name, age, country = 'USA' } = person;
console.log(country); // 输出: USA，因为 person 对象中没有 country 属性，所以使用了默认值
```

如果想要将解构赋值和重命名结合使用，可以在解构赋值语法中使用别名：

```js
const { name: fullName, age } = person;
console.log(fullName); // 输出: John，因为我们将 person 对象的 name 属性重命名为 fullName
```

需要注意的是，解构赋值语法只能提取已经声明的变量。如果尝试提取一个未声明的变量，会导致错误。





# Node.js

```js
const express = require('express');
const app = express();

// 第一个中间件：记录请求信息
app.use((req, res, next) => {
    console.log(`请求方法: ${req.method}, 请求路径: ${req.path}`);
    next(); // 将请求传递给下一个中间件
});

// 第二个中间件：模拟身份验证
app.use((req, res, next) => {
    const isAuthenticated = true; // 假设用户已认证
    if (isAuthenticated) {
        next(); // 继续处理请求
    } else {
        res.status(401).send('未授权'); // 如果未认证，发送 401 响应
    }
});

// 处理特定路由的请求
app.get('/dashboard', (req, res) => {
    res.send('欢迎来到用户仪表板！'); // 发送响应
});

// 启动服务器
app.listen(3000, () => {
    console.log('服务器正在运行，端口: 3000');
});

```

| 特性           | `res.end()`                     | `res.send()`                             |
| -------------- | ------------------------------- | ---------------------------------------- |
| **模块**       | Node.js 原生 `http` 模块        | Express.js 框架                          |
| **功能**       | 结束响应并可发送内容            | 发送响应并自动结束                       |
| **参数**       | 可接收一个可选的字符串或 Buffer | 接收任意数据类型（字符串、对象、数组等） |
| **自动设置头** | 不自动设置响应头                | 根据数据类型自动设置 Content-Type        |
| **用法**       | 适合简单的 HTTP 服务器          | 适合 Express.js 应用程序                 |
