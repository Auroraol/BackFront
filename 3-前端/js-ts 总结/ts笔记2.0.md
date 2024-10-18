# 注释

```ts
  /**
   * 返回第三方授权处理的中间件，返回授权URL
   * 
   * @param {string} componentAppId - 第三方平台的应用 ID。
   * @param {string} redirectUrl - 授权成功后的回调地址。
   * @param {number} [authType=Component.AUTH_TYPE_BOTH] - 第三方平台授权类型，默认值为 `Component.AUTH_TYPE_BOTH`。
   *  - 1: 授权方手机端只展示公众号列表
   *  - 2: 授权方手机端只展示小程序列表
   *  - 3: 授权方手机端展示公众号和小程序列表
   * @param {number} [pageStyle=Component.PAGE_STYLE_PC] - 第三方平台授权页样式，默认值为 `Component.PAGE_STYLE_PC`。
   *  - 1: 适用于PC的页面样式
   *  - 2: 适用于移动设备的页面样式
   * @returns {Promise<string>} 返回一个 Promise，解析为授权 URL。
   */
```



# 变量

**使用 `let` 或 `const` 来定义变量,  不要使用`var`**：

```js
// 方法1: 定义全局变量或局部变量
let globalVar: number = 10;  // 明确指定了变量的类型为 number

// 方法2: 不能修改
const anotherGlobalVar: number = 20;  // 明确指定了变量的类型为 number
```

使用 `var` 声明的全局变量存在变量提升（hoisting）和潜在的全局污染问题，因此不推荐使用。

## 基础类型

```ts
let globalVar: number = 10;  // 明确指定了变量的类型为 number
let message: string = "Hello, world!";  // 字符串类型
let hasData: boolean = true;  // 布尔类型
let list: number[] = [1, 2, 3];  // 数组类型
let x: [string, number] = ["hello", 10];  // 元组类型
enum Color {Red, Green, Blue}  // 枚举类型
let notSure: any = 4;  // 任何类型
let nothingMuch: void = doSomething();  // 没有返回值的函数
let nothing: null = null;  // null 类型
let u: undefined = undefined;  // undefined 类型
let never: never = errorFunction();  // never 类型
```

## 复杂类型

```ts
// 注: 一般会新建xxx.d.ts或者xxx.ts文件在里面单独定义, export interface xxx { .... }
export interface JsonResult {
  code: number
  msg: string
  data: object | any | null
}

let point: JsonResult = { code: 10, msg: "20", data: null };  // 接口类型

// 注: 一般会新建xxx.d.ts或者xxx.ts文件在里面单独定义, export type xxx { .... }
type Point = {
    x: number;
    y: number;
};

/*
选择建议:
如果你需要定义一个可以被扩展或重用的结构，使用 interface。
如果你需要定义一个不会改变的类型，或者需要定义复杂的类型（如联合类型、交叉类型等），使用 type。
如果你需要定义一个具体的类型，不会进行扩展或合并，使用 type。
*/

class Clock {
    currentTime: Date;
    constructor(h: number, m: number) { }
}

let clock: Clock = new Clock(12, 30);  // 类类型

type StringArray = string[];
let myArray: StringArray = ['Bob', 'Fred'];  // 类型别名

type Name = 'Alice' | 'Bob' | 'Charlie';  // 联合类型
let name: Name = 'Bob';

type Complex = { a: number } & { b: string };  // 交叉类型
let x: Complex = { a: 5, b: 'hello' };
```

## **特殊类型**

```typescript
let x: Promise<string> = new Promise(resolve => {
    setTimeout(() => resolve("done!"), 1000);
});  // Promise 类型

let x: ReadonlyArray<number> = [1, 2, 3];  // 只读数组类型

type PartialPoint = Partial<Point>;
let point: PartialPoint = { x: 10 };  // 部分属性可选类型

type RequiredPoint = Required<Point>;
let point: RequiredPoint = { x: 10, y: 20 };  // 所有属性必需类型

type PickPoint = Pick<Point, 'x' | 'y'>;
let point: PickPoint = { x: 10, y: 20 };  // 选择属性类型

type OmitPoint = Omit<Point, 'x'>;
let point: OmitPoint = { y: 20 };  // 排除属性类型

type FunctionType = () => void;
let fn: FunctionType = () => { console.log('hello'); };  // 函数类型
```

## **内置对象类型**

```typescript
let x: Object = { a: 10, b: 20 };  // Object 类型

function fn(): void { console.log('hello'); }
let x: Function = fn;  // Function 类型

let x: Date = new Date();  // Date 类型

let x: RegExp = /s+/;  // RegExp 类型
```

## **类型工具**

```typescript
let x = "hello";
type TypeOfX = typeof x;  // 使用 typeof 获取类型

class C {
    greet() { console.log("hello"); }
}
let c = new C();
type TypeOfC = typeof c;  // 使用 typeof 获取实例类型

function isNumber(x: any): x is number {
    return typeof x === "number";
}
let x: number | string = Math.random() ? 1 : "one";
if (isNumber(x)) {
    console.log(x.toFixed(2));
}  // 使用类型守卫
```

# 工具类

## 判断参数是否为空

```ts
export function isEmpty(value: any): value is false | 0 | 0n | "" | null | undefined | number {
    return (
        value === false ||
        value === 0 ||
        value === 0n ||
        value === "" ||
        value === null ||
        value === undefined ||
        (typeof value === "number" && Number.isNaN(value)) ||
        (Array.isArray(value) && value.length === 0) ||
        (typeof value === "object" && value !== null && Object.keys(value).length === 0)
    );
}

export function isNotEmpty(value: any): value is true | number | string | object {
    return !isEmpty(value);
}
```

## 数据类型检测

```javascript
export function getParamType<T>(item: T): string | null | undefined {
    if (item === null) return null;
    if (item === undefined) return undefined;
    return Object.prototype.toString.call(item).slice(8, -1) as string;
}
// 返回值可以是 'String', 'Function', 'Boolean', 'Object', 'Number', 'Array', 'Null', 'Undefined' 等
```

## 数组去重

```ts
export function arrayUniq<T>(array: T[]): T[] {
    return Array.from(new Set(array));
}
```

# 函数

## 函数定义

**建议使用 `匿名函数` 或 `箭头函数` 来定义函数**：

```ts
// 方法0: 普通函数
function add(a: number, b: number): number {
  return a + b;
}

//方法1:   匿名函数, 调用的方式，函数调用必须写到函数体下面
const sum = function(a: number, b: number): number {
  return a + b;
}

//方法2:   箭头函数  ❤基于原型的面向对象不使用箭头函数 ❤DOM事件回调函数不使用箭头函数
const sum1 = (a: number, b: number): number => {
  return a + b;
}
```

使用 `命名函数` 声明的函数存在函数提升（hoisting）和潜在的全局污染问题，因此不推荐使用。

如果使用`let`关键字声明的函数`sum`可以在定义后重新赋值，也就是说可以修改`sum`指向的函数, 修改后前一个函数是无法调用的 (**不推荐**)：

```ts
const sum = function(a: number, b: number): number {
  return a + b;
}

sum = const sum = function(a: number, b: number): number {  // 允许
  return a * b;
}
console.log(sum(100,2)) //200
```

**`命名函数`一般用于DOM事件回调函数:**

```ts
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

### 可选的参数

用 `?` 表示可选的参数, 注意: **可选参数放在必需参数后面**

```ts
function buildName(firstName: string, lastName?: string) {
    if (lastName) {
        return firstName + ' ' + lastName;
    } else {
        return firstName;
    }
}
let tomcat = buildName('Tom', 'Cat');
let tom = buildName('Tom');
```

### 参数默认值

 注意: **参数默认值最好放在必需参数后面**

```ts
function buildName(firstName: string = 'Tom', lastName: string) {
    return firstName + ' ' + lastName;
}

//此处是赋了初始值,所以可以传入undefined
let cat = buildName(undefined, 'Cat');
```

### 剩余参数

ES6 中，使用 `...rest` 的方式获取函数中的剩余参数（rest 参数）：

注意: **剩余参数必须放在必需参数后面**

```js
function push(array, ...items) {
    items.forEach(function(item) {
        array.push(item);
    });
}

let a: any[] = [];
push(a, 1, 2, 3);
```

### 重载

比如:  需要实现一个函数 `reverse`，输入数字 `123` 的时候，输出反转的数字 `321`，输入字符串 `'hello'` 的时候，输出反转的字符串 `'olleh'`。

**利用联合类型实现**：

```ts
function reverse(x: number | string): number | string {
    if (typeof x === 'number') {
        return Number(x.toString().split('').reverse().join(''));
    } else if (typeof x === 'string') {
        return x.split('').reverse().join('');
    }
}
```

并不清晰明了, 解决方法**使用重载定义多个 `reverse` 的函数类型**：

```ts
function reverse(x: number): number;
function reverse(x: string): string;
function reverse(x: number | string): number | string {
    if (typeof x === 'number') {
        return Number(x.toString().split('').reverse().join(''));
    } else if (typeof x === 'string') {
        return x.split('').reverse().join('');
    }
}

//使用
reverse(231);
reverse("ww2");
```

重复定义了多次函数 `reverse`，前几次都是函数定义，最后一次是函数实现。在编辑器的代码提示中，可以正确的看到前两个提示。

# 模块

导出

```js
// 默认导出
export default function() {
    // 函数体...
};

// 按需导出
export const a: number = 10;

// 方法0: 普通函数
export function add(a: number, b: number): number {
  return a + b;
}

// 方法1: 匿名函数
export const sum: (a: number, b: number) => number = function(a: number, b: number): number {
  return a + b;
}

// 方法2: 箭头函数
export const fn: () => void = () => {
  console.log('内容');
}

// 或者用 as 来命名
const e: number = 1;
const f: number = 2;
export { e as outE, f as outF };
```

导入

```js
// 默认导入时的接收名称可以任意名称，只要是合法的成员名称即可
import result from './xxx.js'

import { a, b as c, fn } from './xxx.js'
```

例子

### 使用命名导出

```ts
// constants.js

// 定义常量
export const AUTH_TYPE_MP = 1;               // 授权方手机端只展示公众号列表
export const AUTH_TYPE_MINI_PROGRAM = 2;     // 授权方手机端只展示小程序列表
export const AUTH_TYPE_BOTH = 3;             // 授权方手机端展示公众号和小程序列表

export const PAGE_STYLE_PC = 1;              // 适用于PC的页面样式
export const PAGE_STYLE_MOBILE = 2;          // 适用于移动设备的页面样式

// 导出函数
export function clearQuota() { /* ... */ }
export function getComponentAccessToken() { /* ... */ }
export function getPreAuthCode() { /* ... */ }
export function getAuthorizationUrl() { /* ... */ }
export function getAuthorizerList() { /* ... */ }

//在其他模块中，您可以像这样导入所需的常量和函数：
import { 
    clearQuota, 
    getComponentAccessToken, 
    getPreAuthCode, 
    getAuthorizationUrl, 
    getAuthorizerList,
    AUTH_TYPE_MP, 
    AUTH_TYPE_MINI_PROGRAM, 
    AUTH_TYPE_BOTH, 
    PAGE_STYLE_PC, 
    PAGE_STYLE_MOBILE 
} from './constants.js';
```

### 使用默认导出（封装所有）

```ts
// constants.js

// 定义常量
const AUTH_TYPE_MP = 1;               // 授权方手机端只展示公众号列表
const AUTH_TYPE_MINI_PROGRAM = 2;     // 授权方手机端只展示小程序列表
const AUTH_TYPE_BOTH = 3;             // 授权方手机端展示公众号和小程序列表

const PAGE_STYLE_PC = 1;              // 适用于PC的页面样式
const PAGE_STYLE_MOBILE = 2;          // 适用于移动设备的页面样式

// 定义函数
function clearQuota() { /* ... */ }
function getComponentAccessToken() { /* ... */ }
function getPreAuthCode() { /* ... */ }
function getAuthorizationUrl() { /* ... */ }
function getAuthorizerList() { /* ... */ }

// 默认导出一个对象
export default {
    clearQuota, 
    getComponentAccessToken, 
    getPreAuthCode, 
    getAuthorizationUrl, 
    getAuthorizerList,
    AUTH_TYPE_MP, 
    AUTH_TYPE_MINI_PROGRAM, 
    AUTH_TYPE_BOTH, 
    PAGE_STYLE_PC, 
    PAGE_STYLE_MOBILE
};

//在其他模块中，您可以这样导入：
import constants from './constants.js';

const { 
    clearQuota, 
    getComponentAccessToken, 
    getPreAuthCode, 
    getAuthorizationUrl, 
    getAuthorizerList,
    AUTH_TYPE_MP, 
    AUTH_TYPE_MINI_PROGRAM, 
    AUTH_TYPE_BOTH, 
    PAGE_STYLE_PC, 
    PAGE_STYLE_MOBILE 
} = constants;

```



# 面向对象 : TODO

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

对象解构赋值的基本语法：

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

# 字符串拼接

## 1. 模板字符串（Template Strings）

```ts
const url = `https://api.weixin.qq.com/product/service/check_auth?component_access_token=${component_access_token}`;
```

## 2. 字符串连接（Concatenation）

```ts
const component_access_token = 'your-component-access-token';

const url = 'https://api.weixin.qq.com/product/service/check_auth?component_access_token=' + component_access_token;

console.log(url); // 输出：https://api.weixin.qq.com/product/service/check_auth?component_access_token=your-component-access-token
```

## 3. 使用 url 模块（Node.js）

如果你在 Node.js 环境中，可以使用 url 模块来构建 URL：

```ts
import { URL } from 'url';

const component_access_token = 'your-component-access-token';

const url = new URL('https://api.weixin.qq.com/product/service/check_auth');
url.searchParams.set('component_access_token', component_access_token);

console.log(url.toString()); // 输出：https://api.weixin.qq.com/product/service/check_auth?component_access_token=your-component-access-token
```

## 3. 使用 qs 库

如果你需要更复杂的查询字符串处理，可以使用 qs 库：

```ts
import qs from 'qs';

const component_access_token = 'your-component-access-token';

const query = qs.stringify({
    component_access_token: component_access_token
});

const url = `https://api.weixin.qq.com/product/service/check_auth?${query}`;

console.log(url); // 输出：https://api.weixin.qq.com/product/service/check_auth?component_access_token=your-component-access-token

```

# 请求参数拼接

## @querystring

const querystring = require('querystring')

```ts
let url = 'https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info'
let query = { component_access_token: "AAA" }
url += '?' + querystring.stringify(query)

// 结果
https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=AAA
```

或者

```ts

export function getAuthorizationUrl(componentAppId, preAuthCode, redirectUrl, authType, pageStyle) {
  let url = {
    [PAGE_STYLE_PC]: 'https://mp.weixin.qq.com/cgi-bin/componentloginpage',
    [PAGE_STYLE_MOBILE]: 'https://mp.weixin.qq.com/safe/bindcomponent'
  }[pageStyle]

  let query = { component_appid: componentAppId, pre_auth_code: preAuthCode, redirect_uri: redirectUrl }

  if (typeof authType === 'number') {
    Object.assign(query, { auth_type: authType })
  } else if (typeof authType === 'string') {
    Object.assign(query, { biz_appid: authType })
  }

  if (pageStyle === PAGE_STYLE_MOBILE) {
    Object.assign(query, { action: 'bindcomponent', no_scan: 1 })
    url += '?' + querystring.stringify(query) + '#wechat_redirect'
  } else if (pageStyle === PAGE_STYLE_PC) {
    url += '?' + querystring.stringify(query)
  }

  return url
}

```

