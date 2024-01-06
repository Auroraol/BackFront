# 变量

**使用 `let` 或 `const` 来定义变量,  不要使用`var`**：

```js
//方法1:   定义全局变量或者局部局部
let globalVar = 10;    

//方法2:   不能修改
const anotherGlobalVar = 20;  
```

使用 `var` 声明的全局变量存在变量提升（hoisting）和潜在的全局污染问题，因此不推荐使用。

# 函数

## 函数定义

**建议使用 `匿名函数` 或 `箭头函数` 来定义函数**：

```js
//方法1:   匿名函数
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



