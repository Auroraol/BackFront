【Ajax】https://blog.csdn.net/weixin_44972008/article/details/113772348
【axios】https://blog.csdn.net/weixin_44972008/article/details/114368528

https://blog.csdn.net/weixin_44972008/article/details/113779708
【Promise手写】https://blog.csdn.net/weixin_44972008/article/details/1141349951. 

# 引言

## promise对象的介绍

### 概念

+  Promise是`异步编程的一种解决方案`，比传统的解决方案——回调函数和事件——更合理和更强大。所谓
+ Promise，简单说就是一个容器，里面保存着某个未来才会结束的事件（通常是一个异步操作）的结果。

+ 通俗讲，`Promise是一个许诺、承诺`,是对未来事情的承诺，承诺不一定能完成，但是无论是否能完成都会有一个结果。

### 抽象表达

+ Promise是一门新的技术(ES6规范)

+ Promise是Js中进行异步编程的新解决方案,  旧方案是单纯使用回调函数,  比如:

  + fs 文件操作

    ```js
    require('fs').readFile('./index.html', (err,data)=>{})
    ```

  + AJAX

    ```js
    $.get( '/server' , (data)=>{})
    ```

  - 定时器

    ```js
    setTimeout(()=>{
    }, 1000);
    ```

  - 数据库操作

### 具体表达

+ 从语法上看：`Promise`是一个构造函数 (自己身上有`all`、`reject`、`resolve`这几个方法，原型上有`then`、`catch`等方法)
+ 从功能上看：`promise`对象用来封装一个异步操作并可以获取其成功/失败的结果值

## promise对象的优缺点

#### 优点

- 可以将异步操作以同步操作的流程表达出来，支持链式调用,  避免了层层嵌套的回调函数（防止回调地狱）(回调函数嵌套调用，外部回调函数异步执行的结果是嵌套的回调执行的条件)
- promise对象提供统一的接口，使得控制异步操作更加容易
- 指定回调函数的方式更加灵活
  + 旧的:  必须在启动异步任务前指定
  + promise:  启动异步任 => 返回promie对象 => 给 promise 对象绑定回调函数(甚至可以在异步任务结束后指定/多个) 

#### 缺点

- promise对象无法取消的。一旦新建了他就会立即执行，无法中途取消。
- 如果不设置回调函数，promise内部抛出的错误，不会反应到外部
- 当处于pending状态时候，无法得知目前进展到那个阶段了。

## promise对象的特点

### 对象状态不受外界影响

promise对象代表一个异步操作，有三种状态

- pending：初始状态，不是成功或者失败状态（比如正在进行网络请求，或者定时器没有到时间）
- fulfilled：意味着操作完成（当我们主动调用resolve时候，并且会调用.then()）
- rejected：意味着操作失败(当我们主动回调reject时，并且会回调.catch())
  只有异步操作的结果，可以决定当前是哪一种状态

### 一旦状态改变，就不会再变，任何时候都可以得到这个结果

promise对象状态改变的两种可能

+ 从pending变为resolve

+ 从pending变为rejected

只要发生这两种情况的变化，就一直会保持这个结果。就算改变已经发生了，再对promise对象添加回调函数，也会立即得到这个这个结果 **`这与事件完全不同的，事件的特点是，如果你错过了他，再去监听，是得不到结果的`**



# 备知识

### 1.1 实例对象与函数对象

- 实例对象：new 函数产生的对象，称为实例对象，简称为对象
- 函数对象：将函数作为对象使用时，称为函数对象

```javascript
function Fn() {
    // Fn只能称为函数
}
const fn = new Fn() // Fn只有new过的才可以称为构造函数
//fn称为实例对象
console.log(Fn.prototype)// Fn作为对象使用时，才可以称为函数对象
Fn.bind({
   }) //Fn作为函数对象使用
$('#test') // $作为函数使用
$.get('/test') // $作为函数对象使用
```

> ()左边是函数，点左边是对象(函数对象、实例对象)

### 1.2 两种类型的回调函数

#### 1. 同步回调

> 立即执行，完全执行完了才结束，不会放入回调队列中

数组遍历相关的回调 / [Promise](https://so.csdn.net/so/search?q=Promise&spm=1001.2101.3001.7020)的executor函数

```javascript
const arr = [1, 3, 5];
arr.forEach(item => {
    // 遍历回调，同步回调，不会放入队列，一上来就要执行
  console.log(item);
})
console.log('forEach()之后')
```

![在这里插入图片描述](Promise%E6%95%99%E7%A8%8B.assets/20210210161324356.png)

#### 2. 异步回调

> 不会立即执行，会放入回调队列中将来执行

定时器回调 / ajax回调 / Promise成功或失败的回调

```javascript
// 定时器回调
setTimeout(() => {
    // 异步回调，会放入队列中将来执行
  console.log('timeout callback()')
}, 0)
console.log('setTimeout()之后')
```

![在这里插入图片描述](Promise%E6%95%99%E7%A8%8B.assets/20210210161353629.png)

```javascript
// Promise 成功或失败的回调
new Promise((resolve, reject) => {
   
  resolve(1)
}).then(
  value => {
   console.log('value', value)},
  reason => {
   console.log('reason', reason)}
)
console.log('----')
// ----
// value 1
```

> js 引擎先把初始化的同步代码都执行完成后，才执行回调队列中的代码

### 1.3 JS中的异常error处理

#### 1. 错误的类型

`Error`：所有错误的父类型

`ReferenceError`：引用的变量不存在

```javascript
console.log(a) // ReferenceError:a is not defined
1
```

`TypeError`：数据类型不正确

```javascript
let b
console.log(b.xxx)
// TypeError:Cannot read property 'xxx' of undefined

let c = {
   }
c.xxx()
// TypeError:c.xxx is not a function
12345678
```

`RangeError`：数据值不在其所允许的范围内

```javascript
function fn() {
   
  fn()
}
fn()
// RangeError:Maximum call stack size exceeded
123456
```

`SyntaxError`：语法错误

```javascript
const c = """"
// SyntaxError:Unexpected string
12
```

#### 2. 错误处理（捕获与抛出）

抛出错误：`throw error`

```javascript
function something() {
   
  if (Date.now()%2===1) {
   
    console.log('当前时间为奇数，可以执行任务')
  } else {
    //如果时间为偶数抛出异常，由调用来处理
    throw new Error('当前时间为偶数，无法执行任务')
  }
}
12345678910
```

捕获错误：`try ... catch`

```javascript
// 捕获处理异常
try {
   
  something()
} catch (error) {
   
  alert(error.message)
}
12345678
```

#### 3. 错误对象

- massage 属性：错误相关信息
- stack 属性：函数调用栈记录信息

```javascript
try {
   
  let d
  console.log(d.xxx)
} catch (error) {
   
  console.log(error.message)
  console.log(error.stack)
}
console.log('出错之后')
// Cannot read property 'xxx' of undefined
// TypeError:Cannot read property 'xxx' of undefined
// 出错之后
12345678910111213
```

> 因为错误被捕获处理了，后面的代码才能运行下去，打印出‘出错之后’

# Promise使用

## Promise 的状态

###  a) 状态

实例对象中的一个属性 『PromiseState』

- pending 未决定的
- resolved / fullfilled 成功
- rejected 失败

### b)  状态改变

+ pending 变为 resolved   (待处理的——>已解决)

+ pending 变为 rejected    (待处理的——>拒绝)

### c) 注意

- 对象的状态不受外界影响
- 只有这两种，且一个 `promise` 对象只能改变一次
- 一旦状态改变，就不会再变，任何时候都可以得到这个结果
- 无论成功还是失败，都会有一个结果数据。成功的结果数据一般称为 `value`，而失败的一般称为 `reason`。

## Promise 的基本流程

![image-20231220204453196](Promise%E6%95%99%E7%A8%8B.assets/image-20231220204453196.png)

## promise的基本使用

### 例子:1  使用 promise 封装基于定时器的异步

```js
<script >
    
  function doDelay(time) {
    // 1. 创建 promise 对象(pending 状态), 指定执行器函数
    return new Promise((resolve, reject) => {
      // 2. 在执行器函数中启动异步任务
      console.log('启动异步任务')
      setTimeout(() => {
        console.log('延迟任务开始执行...')
        const time = Date.now() // 假设: 时间为奇数代表成功, 为偶数代表失败
        if (time % 2 === 1) { // 成功了
          // 3. 1. 如果成功了, 调用 resolve()并传入成功的 value
          resolve('成功的数据 ' + time)
        } else { // 失败了
          // 3.2. 如果失败了, 调用 reject()并传入失败的 reason
          reject('失败的数据 ' + time)
        }
      }, time)
    })
  }
const promise = doDelay(2000)
promise.then(// promise 指定成功或失败的回调函数来获取成功的 vlaue 或失败的 reason
    value => {// 成功的回调函数 onResolved, 得到成功的 value
      console.log('成功的 value: ', value)
    },
    reason => { // 失败的回调函数 onRejected, 得到失败的 reason
      console.log('失败的 reason: ', reason)
    },
  ) 
</script>
```

### 例子: 2  使用 promise 封装原生ajax 异步请求

```js
<script >
  /*
  可复用的发 ajax 请求的函数: xhr + promise
  */
  function promiseAjax(url) {
    return new Promise((resolve, reject) => {
      // 1、创建对象
      const xhr = new XMLHttpRequest()
      // 2、初始化
      xhr.open("GET", url)
      // 3、发送
      xhr.send()
      // 4、事件绑定，处理服务器端返回的结果
      xhr.onreadystatechange = () => {
        if (xhr.readyState !== 4) return
        const {
          status,
          response
        } = xhr
        // 请求成功, 调用 resolve(value)
        if (status >= 200 && status < 300) {
          resolve(JSON.parse(response))  // 设置响应体类型 xhr.responseType = 'json';则可以自动转换不需要JSON.parse()
        } else { // 请求失败, 调用 reject(reason)
          reject(new Error('请求失败: status: ' + status))
        }
      }
    })
  }

promiseAjax('https://api.apiopen.top2/getJoke?page=1&count=2&type=video').then(
      value => {
        console.log('显示成功数据', value)
      },
      error => {
        alert(error.message)
      }
    ) 
</script>
```

### 例子3  fs模块使用Promise

```js
const fs = require('fs');

//以前回调函数 形式----------------------------------------------------
 fs.readFile('./resource/content.txt', (err, data) => {
     // 如果出错 则抛出错误
     if(err)  throw err;
     //输出文件内容
     console.log(data.toString());
 });


//现在Promise 形式-----------------------------------------------------------
/**
 * 封装一个函数 mineReadFile 读取文件内容
 * 参数:  path  文件路径
 * 返回:  promise 对象
 */
function mineReadFile(path){
    return new Promise((resolve, reject) => {
        //读取文件
        require('fs').readFile(path, (err, data) =>{
            //判断
            if(err) reject(err);
            //成功
            resolve(data);
        });
    });
}

mineReadFile('./resource/content.txt')
.then(
    value=>{ // 成功的回调函数 onResolved, 得到成功的 value
    //输出文件内容
    	console.log(value.toString());
	}, 
    reason=>{ // 失败的回调函数 onRejected, 得到失败的 reason
    	console.log(reason);
    }
)
```



### 例子4:  util.promisify方法

> 可以将函数自动封装成promise,不用再去手动封装

```js
//引入 util 模块
const util = require('util');
//引入 fs 模块
const fs = require('fs');
//返回一个新的函数
let mineReadFile = util.promisify(fs.readFile);

mineReadFile('./resource/content.txt').then(value => {
  console.log(value.toString());
});
```

### 总结

```javascript
const promise = new Promise(function(resolve, reject) {
  // ... some code
  if (/* 异步操作成功 */){
   
    resolve(value);
  } else {
   
    reject(reason);
  }
});
```

`Promise`构造函数接受**一个函数**（执行器函数）作为参数，该函数的**两个参数**分别是`resolve`和`reject`。它们是**两个函数**，由 JavaScript 引擎提供，不用自己部署。

+ `resolve`函数的作用是，将`Promise`对象的状态从“未完成”变为“成功”（即从 `pending` 变为 `resolved`），在**异步操作成功**时调用，并将异步操作的结果，作为参数`value`传递出去；
+ `reject`函数的作用是，将`Promise`对象的状态从“未完成”变为“失败”（即从 `pending` 变为 `rejected`），在**异步操作失败**时调用，并将异步操作报出的错误，作为参数`error`/`reason`传递出去。

`Promise`实例生成以后，可以用`then`方法分别指定`resolved`状态和`rejected`状态的回调函数。

```javascript
promise.then(function(value) {
   
  // success
}, function(reason) {
   
  // failure
});
```

`then`方法可以接受**两个回调函数**作为参数。

+ 第一个回调函数`onResolved()`是`Promise`对象的状态变为`resolved`时调用
+ 第二个回调函数`onRejected()`是`Promise`对象的状态变为`rejected`时调用
+ 这两个函数都是可选的，不一定要提供。它们都接受`Promise`对象传出的值作为参数

## Promise中的常用 API 概述

###  Promise 构造函数: Promise (excutor) {}

> (1) executor 函数: 执行器 (resolve, reject) => {}
>
> (2) resolve 函数: 内部定义成功时我们调用的函数 value => {}
>
> (3) reject 函数: 内部定义失败时我们调用的函数 reason => {}

说明: 

+ executor 会在 Promise 内部立即同步调用,   异步操作在执行器中执行,换话说Promise支持同步也支持异步操作

### Promise.prototype.then 方法: (onResolved, onRejected) => {}

> (1) onResolved 函数: 成功的回调函数 (value) => {}
>
> (2) onRejected 函数: 失败的回调函数 (reason) => {}

说明: 

+ 指定得到成功 value 的成功回调
+ 指定得到失败 reason 的失败回调
+  返回一个新的 promise 对象

### Promise.prototype.catch 方法: (onRejected) => {}

> (1) onRejected 函数: 失败的回调函数 (reason) => {}
>
> (2) 异常穿透使用:   当运行到最后,没被处理的所有异常错误都会进入这个方法的回调函数中

说明: 

+ 指定得到失败 reason 的失败回调

### Promise.resolve 方法: (value) => {}

> (1) value: 成功的数据或 promise 对象
>
> ```js
> let p1 = Promise.resolve(521);
> //如果传入的参数为非Promise类型的对象，则返回的结果为成功promise对象
> //如果传入的参数为Promise对象，则参数的结果决定了resolve 的结果
> let p2 = Promise.resolve(new Promise((resolve，reject)=> {
> 	//resolve( 'OK ');
> 	reject('Error');
> }));
> 
> //console.log(p2);
> p2.catch(reason => {
> console.log(reason);  //Error
> )
> ```

说明: 

+ 返回一个成功/失败的 promise 对象, 直接改变promise状态

### Promise.reject 方法: (reason) => {}

> (1) reason: 失败的原因
>
> ```js
>   let p3 = Promise.reject(new Promise((resolve, reject) => {  resolve('OK'); }));      
>   console.log(p3);  //ok
> ```

说明: 

+ 返回一个失败的 promise 对象,直接改变promise状态

### Promise.all 方法: (promises) => {}

> promises: 包含 n 个 promise 的数组
>
> ```js
> let p1 = new Promise((resolve, reject) => { resolve('成功');  })
> let p2 = Promise.reject('错误错误错误'); //失败
> let p3 = Promise.resolve('也是成功')    //成功 
> const result = Promise.all([p1, p2, p3]);
> console.log(result);  //失败
> ```

说明: 

+ 返回一个新的 promise, 只有所有的 promise 都成功才成功,  只要有一 个失败了就直接失败

### Promise.race 方法: (promises) => {}

> (1) promises: 包含 n 个 promise 的数组
>
> ```js
> let p1 = new Promise((resolve, reject) => {
>     setTimeout(() => {
>         resolve('OK');
>     }, 1000);
> })
> let p2 = Promise.resolve('Success');
> let p3 = Promise.resolve('Oh Yeah');
> //调用
> const result = Promise.race([p1, p2, p3]);   //看谁先成功
> console.log(result);  // Success
> ```

说明: 

+ 返回一个新的 promise, `第一个完成`的 promise 的结果状态就是最终的结果状态,如p1延时,开启了异步,内部正常是同步进行,所以`p2>p3>p1`,结果是`P2`

## Promise的几个关键问题

### 1. 改变 promise 的状态?

(1) resolve(value): 如果当前是 pending 就会变为 resolved

(2) reject(reason): 如果当前是 pending 就会变为 rejected

(3) 抛出异常:  如果当前是 pending 就会变为 rejected

```js
let p = new Promise((resolve, reject){
    //1. resolve 函数
    // resolve('ok'); // pending=> fulfilled (resolved)
    //2.reject 函数
    // reject("error");// pending => rejecte
    //3.抛出错误
    throw '出问题了!'
})
```

### 2. 一个 promise 指定多个成功/失败回调函数, 都会调用吗?

当 promise `改变为对应状态时`都会调用,改变状态后,  多个回调函数都会调用

```js
let p = new Promise((resolve, reject) => {  resolve('OK');});
///指定回调 - 1
p.then(value => {  console.log(value); });  //ok
//指定回调 - 2
p.then(value => { console.log(value); });  //ok
```

### 3. 改变 promise 状态和指定回调函数谁先谁后?

(1) 都有可能, 正常情况下是先指定回调再改变状态, 但也可以先改状态再指定回调

+ 先指定回调再改变状态(`异步`):先指定回调--> 再改变状态 -->改变状态后才进入异步队列执行回调函数

+  先改状态再指定回调(`同步`):改变状态 -->指定回调 `并马上执行`回调

(2) 如何先改状态再`指定`回调? -->注意:指定并不是执行

+  在执行器中直接调用 resolve()/reject() -->即,不使用定时器等方法,执行器内直接同步操作

+  延迟更长时间才调用 then() -->即,在`.then()`这个方法外再包一层例如延时器这种方法

(3) 什么时候才能得到数据?

+ 如果先指定的回调, 那当状态发生改变时, 回调函数就会调用, 得到数据
+  如果先改变的状态, 那当指定回调时, 回调函数就会调用, 得到数据
+ 总而言之回调的时候得到数据

```js
let p = new Promise((resolve, reject) => {
    //异步,这样写会先指定回调,再改变状态
    setTimeout(() => {
        resolve('OK');//状态
    }, 1000); 
    
    //同步,这样写会先改变状态,再指定回调
    resolve('OK');  //状态
});

// 回调
p.then(
    value => {
        console.log(value);
    }, 
    reason => {}
)
```

(4) 个人理解--结合源码

 源码中,promise的状态是通过一个`默认为padding`的变量进行判断,所以当你`resolve/reject`延时(异步导致当then加载时,状态还未修改)后,这时直接进行p.then()会发现,目前状态还是`进行中`,所以只是这样导致只有同步操作才能成功. 所以promise将传入的`回调函数`拷贝到promise对象实例上,然后在`resolve/reject`的执行过程中再进行调用,达到异步的目的

### 4.  promise.then() 返回的新 promise 的结果状态由什么决定?

(1) 简单表达: **新 promise 的结果状态由 then()指定的回调函数执行的结果决定**

(2) 详细表达:

+  如果抛出异常, 新 promise 变为 rejected, reason 为抛出的异常
+ 如果返回的是非 promise 的任意值, 新 promise 变为 resolved, value 为返回的值
+ 如果返回的是另一个新 promise, 此 promise 的结果就会成为新 promise 的结果

```js
let p = new Promise((resolve, reject) => {
	resolve('ok');
});
//执行 then 方法
let result = p.then(
    value => {
        console.log(value);
        // 情况1. 抛出错误 ,变为 rejected
        throw '出了问题';
        // 情况2. 返回结果是非 Promise 类型的对象,新 promise 变为 resolved  值就是返回值
        return 521;
        // 情况3. 返回结果是 Promise 对象,此 promise 的结果就会成为新 promise 的结果
        return new Promise((resolve, reject) => {
        // resolve('success');
      		reject('error');
    	});
 	}, 
    reason => {
	console.warn(reason); 
	}
);
```

输出结果

```
出了问题
521
error
```

### 5.  promise 如何串连多个操作任务?

(1) promise 的 then()返回一个新的 promise, 可以开成 then()的链式调用

(2) 通过 then 的链式调用串连多个同步/异步任务,这样就能用`then()`将多个同步或异步操作串联成一个同步队列

```js
<script>
let p = new Promise((resolve, reject) => { 
    setTimeout(() => {resolve('OK'); }, 1000); 
});

p.then(
    value => {
    	return new Promise((resolve, reject) => { resolve("success"); });
    }
).then(
    value => {
        console.log(value);   //success
    }
).then(
    value => { 
        console.log(value);  //undefined
   }
)
</script>
```

### 6. promise 异常传透?

可以在每个then()的第二个回调函数中进行err处理,也可以利用异常穿透特性,到最后用`catch`去承接统一处理,两者一起用时,前者会生效(因为err已经将其处理,就不会再往下穿透)而走不到后面的catch

- 当使用 promise 的 then 链式调用时,  可以在最后指定失败的回调
- 前面任何操作出了异常, 都会传到最后失败的回调中处理

```js
let p = new Promise((resolve, reject) =>{
    setTimeout(( =>i
    resolve( 'oK ');
});

p.then(value => {
  throw '失败啦!';
}).then(value => {
	console.log(222);
}).then(value => {
    	console.log(333);
}).catch(reason => {
	console.warn(reason);      //失败啦!
});
```

例子

```js
getJSON('./hong.json')
   .then(function(posts) { throw new Error('抛出异常') })
  .then(res=>console.log(res),e=>console.log('被then的错误回调捕获',e) )
   .catch(function(error) {
		 // 处理 getJSON 和 前一个回调函数运行时发生的错误
 		console.log('错误捕获: ', error);
  });
//执行结果: 被then的错误回调捕获 Error: 抛出异常

/******************** 利用异常穿透 ****************************************/
getJSON('./hong.json')
   .then(function(posts) { throw new Error('抛出异常') })
  .then(res=>console.log(res) ) //此处差异,不指定 reject 回调,利用异常穿透传到最后
   .catch(function(error) {
 		console.log('错误捕获: ', error);
  });
//执行结果:  错误捕获:  Error: 抛出异常
```

注:可以在每个then()的第二个回调函数中进行err处理,也可以利用异常穿透特性,到最后用`catch`去承接统一处理,两者一起用时,前者会生效(因为err已经将其处理,就不会再往下穿透)而走不到后面的catch![image-20210927105504988](Promise%E6%95%99%E7%A8%8B.assets/image-20210927105504988-170307933219816.png)

### 7. 中断 promise 链?

在`关键问题2`中,可以得知,当promise状态改变时,他的链式调用都会生效,那如果我们有这个一个实际需求:我们有5个then(),但其中有条件判断,如当我符合或者不符合第三个then条件时,要直接中断链式调用,不再走下面的then,该如何?

(1) 当使用 promise 的 then 链式调用时, 在中间中断, 不再调用后面的回调函数

(2) 办法: 在回调函数中返回一个 `pendding` 状态的`promise 对象`

```js
<script>
let p = new Promise((resolve, reject) => {
    setTimeout(() => { resolve('OK');}, 1000);
});

p.then(value => {
    console.log(111);
    //有且只有这一个方式 return new Promise(() => {})
    return new Promise(() => {});
}).then(value => { 
    console.log(222);
}).then(value => { 
    console.log(333);
}).catch(reason => {
    console.warn(reason);
});
</script>
```

输出结果

```
111
```





[【JavaScript】同步与异步-异步与并行-异步运行机制-为什么要异步编程-异步与回调-回调地狱-JavaScript中的异步操作](https://blog.csdn.net/weixin_44972008/article/details/114380206)】







# Promise API 用法详解

> ES6 规定，`Promise`对象是一个构造函数，用来生成`Promise`实例。
>
> 此部分是对于 **Promise API 用法的详解** ,尽量详细地列举其常见用法,所以篇幅较长

## Ⅰ - 基本用法

#### ① 举个创造 Promise 实例的栗子

> 下面代码创造了一个`Promise`实例。
>
> ```
> const promise = new Promise(function(resolve, reject) {
> if (/* 异步操作成功 */)  resolve(value); //将该 Promise 修改为成功且返回
> else  reject(error); //将该 Promise 修改为失败且返回
> });
> ```
>
> `Promise`构造函数接受一个函数作为参数，该函数的两个参数分别是`resolve`和`reject`。它们是两个函数，由 JavaScript 引擎提供，不用自己部署。
>
> `resolve`函数的作用是，将`Promise`对象的状态从“未完成”变为“成功”（即从 pending 变为 resolved），在异步操作成功时调用，并将异步操作的结果，作为参数传递出去；`reject`函数的作用是，将`Promise`对象的状态从“未完成”变为“失败”（即从 pending 变为 rejected），在异步操作失败时调用，并将异步操作报出的错误，作为参数传递出去。

#### ② 使用 [ then ] 方法分别指定 成功/失败 的回调

> `Promise`实例生成以后，可以用 [ then() ] 方法分别指定`resolved`状态和`rejected`状态的回调函数。
>
> ```
> promise.then(function(value) {
> // 当promise状态返回为resolve 时会执行的回调函数
> }, function(error) {
> // 当promise状态返回为rejected 时会执行的回调函数
> });
> ```
>
> [ then ] 方法可以接受两个回调函数作为参数。第一个回调函数是`Promise`对象的状态变为`resolved`时调用，第二个回调函数是`Promise`对象的状态变为`rejected`时调用。其中，**第二个函数是可选的，不一定要提供**。这两个函数都接受`Promise`对象传出的值作为参数。

#### ③ 举个 Promise 对象的简单栗子

> 下面是一个`Promise`对象的简单例子。
>
> > setTimeout的第三个参数是给第一个函数的参数，而且是先于第一个参数(即回调函数)执行的
>
> ```
> function timeout(ms) { //声明一个方法, 传入的 参数ms 为延时器时间
> return new Promise((resolve, reject) => {
>   //这行代码实际效果: 当 [ms] 毫秒后 执行 resolve('努力学习的汪')
>   setTimeout(resolve, ms, '努力学习的汪'); 
> });
> }
> 
> timeout(1000).then((value) => {  console.log(value) });
> //打印结果 : 努力学习的汪
> ```
>
> 上面代码中，`timeout`方法返回一个`Promise`实例，表示一段时间以后才会发生的结果。过了指定的时间（`ms`参数）以后，`Promise`实例的状态变为`resolved`，就会触发`then`方法绑定的回调函数。

#### ④ Promise 新建后就会立即执行

> ```
> let promise = new Promise(function(resolve, reject) {
> console.log('Promise');
> resolve();
> });
> 
> promise.then(function() {
> console.log('resolved.');
> });
> 
> console.log('Hi!');
> 
> // Promise
> // Hi!
> // resolved //可以发现,明明then是在 Hi 前面,却最后打印
> ```
>
> 上面代码中，Promise 新建后立即执行，所以首先输出的是`Promise`。然后，`then`方法指定的回调函数，将在当前脚本所有同步任务执行完才会执行，所以`resolved`最后输出。
>
> 实际上,这个运行结果相关知识点是 [ [宏任务与微任务](https://gitee.com/hongjilin/hongs-study-notes/tree/master/编程_前端开发学习笔记/Promise学习笔记#四宏任务与微任务) ] ,单独梳理在下方.这里可以先初步理解为:
>
> 1. JS是单线程的,至上往下运行,在声明 **Promise** 时实际上已经执行到了内部方法
> 2. 为何 resolve() 运行后没有立即打印?
>
> - JS中用来存储待执行回调函数的队列包含2个不同特定的列队
>
>   > `宏队列`:用来保存待执行的宏任务(回调),比如:`定时器`回调/ajax回调/dom事件回调
>   >
>   > `微队列`:用来保存待执行的微任务(回调),比如:`Promise`的回调/muntation回调
>
> - JS执行时会区别这2个队列:
>
>   > JS执行引擎首先必须执行所有的`初始化同步任务`代码
>   >
>   > 每次准备取出第一个`宏任务执行前`,都要将所有的`微任务`一个一个取出来执行

#### ⑤ 举个异步加载图片的栗子

> ```
> function loadImageAsync(url) {
> return new Promise(function(resolve, reject) {
> const image = new Image();
> 
> image.onload = function() {
> console.log('图片加载成功')
> resolve(image);
> };
> 
> image.onerror = function() {
> reject(new Error(`无法从 ${url} 中加载图片` ));
> };
> image.src = url;
> });
> }
> loadImageAsync('正确的url') //打印图片加载成功
> loadImageAsync('错误的url') //抛出异常
> ```
>
> 上面代码中，使用`Promise`包装了一个图片加载的异步操作。如果加载成功，就调用`resolve`方法，否则就调用`reject`方法。
>
> ![image-20210926180306961](Promise%E6%95%99%E7%A8%8B.assets/image-20210926180306961.png)

#### ⑥ 举个用`Promise`对象实现的 Ajax 操作的栗子

> Ajax知识点不懂的同学要去补一下: 这里可以看本人梳理的ajax笔记 --> [点我跳转](https://gitee.com/hongjilin/hongs-study-notes/tree/master/编程_前端开发学习笔记/Ajax、Axios学习笔记)
>
> ```
> const getJSON = function(url) {
> const promise = new Promise(function(resolve, reject){
> const handler = function() {
> if (this.readyState !== 4)  return; //当readyState 为4 时直接返回,不修改 promise 状态
> if (this.status === 200) resolve(this.response); //返回状态为 200 时将状态修改为成功,且将响应内容返回
>  else  reject(new Error(this.statusText)); //失败时抛出异常
> };
> const client = new XMLHttpRequest(); //实例化xml实例
> client.open("GET", url); //下面这几行都是对xml实例进行配置,不懂的同学要去补一下ajax知识点
> client.onreadystatechange = handler;
> client.responseType = "json";
> client.setRequestHeader("Accept", "application/json");
> client.send();
> });
> return promise;
> };
> 
> getJSON("./hong.json").then(function(json) {
> console.log('Contents: ' , json);
> }, function(error) {
> console.error('出错了', error);
> });
> ```
>
> 上面代码中，`getJSON`是对 XMLHttpRequest 对象的封装，用于发出一个针对 JSON 数据的 HTTP 请求，并且返回一个`Promise`对象。需要注意的是，在`getJSON`内部，`resolve`函数和`reject`函数调用时，都带有参数。
>
> ![image-20210926182129672](Promise%E6%95%99%E7%A8%8B.assets/image-20210926182129672.png)
>
> > 小贴士:此处可能有同学想尝试却发现读取本地文件会有跨域问题,这边教一下你们
>
> > ![image-20210926182506259](Promise%E6%95%99%E7%A8%8B.assets/image-20210926182506259.png)

#### ⑦ resolve() 的参数可以是另一个 Promise 实例

> 如果调用`resolve`函数和`reject`函数时带有参数，那么它们的参数会被传递给回调函数。`reject`函数的参数通常是`Error`对象的实例，表示抛出的错误；`resolve`函数的参数除了正常的值以外，还可能是另一个 Promise 实例，比如像下面这样。
>
> ```
> const p1 = new Promise(function (resolve, reject) {});
> 
> const p2 = new Promise(function (resolve, reject) { resolve(p1) })
> ```
>
> 上面代码中，`p1`和`p2`都是 Promise 的实例，但是`p2`的`resolve`方法将`p1`作为参数，即一个异步操作的结果是返回另一个异步操作。
>
> 注意，这时`p1`的状态就会传递给`p2`，也就是说，`p1`的状态决定了`p2`的状态。如果`p1`的状态是`pending`，那么`p2`的回调函数就会等待`p1`的状态改变；如果`p1`的状态已经是`resolved`或者`rejected`，那么`p2`的回调函数将会立刻执行。
>
> ```
> const p1 = new Promise(function (resolve, reject) {
> setTimeout(() => reject(new Error('p1的状态改为错误')), 0)
> })
> 
> const p2 = new Promise(function (resolve, reject) {
> setTimeout(() => resolve(p1), 3000) //将p1 传给p2
> })
> 
> p2.then(result => console.log(result),result=>console.log('失败'))
> .catch(error => console.log('catch异常捕获:'+error))
> //首先报错
> //运行三秒后打印: 失败
> ```
>
> ##### 上面代码运行后执行效果:
>
> - 首先马上会打印一个报错 : "Uncaught (in promise) Error: p1的状态改为错误" (红色报错)
> - 然后等3秒后再打印: '失败'
> - 注意: 如果 **p2.then()** 中没有写 **reject** 回调函数(第二个参数),则会被 **catch** 捕获,变为`catch异常捕获:Error: p1的状态改为错误`
>
> ##### 解释:
>
> > - 首先前面说过,promise定义时就会立即执行,所以刚开始就运行了 **p1 的reject()**,所以直接控制台报错了
> > - `resolve`方法返回的是`p1`。由于`p2`返回的是另一个 Promise，导致`p2`自己的状态无效了，由`p1`的状态决定`p2`的状态
> > - 总结来说,promise返回promise这种嵌套形式,将由最内层的promise决定外层的状态

#### ⑧ 调用`resolve`或`reject`并不会终结 Promise 的参数函数的执行

> 调用`resolve`或`reject`并不会终结 Promise 的参数函数的执行。
>
> ```
> new Promise((resolve, reject) => {
> resolve(1);
> console.log(2);
> }).then(r => {
> console.log(r);
> });
> // 2
> // 1
> ```
>
> 上面代码中，调用`resolve(1)`以后，后面的`console.log(2)`还是会执行，并且会首先打印出来。这是因为立即 resolved 的 Promise 是在本轮事件循环的末尾执行，总是晚于本轮循环的同步任务。

#### ⑨ 建议在修改状态函数前加return

> 一般来说，调用`resolve`或`reject`以后，Promise 的使命就完成了，后继操作应该放到`then`方法里面，而不应该直接写在`resolve`或`reject`的后面。所以，最好在它们前面加上`return`语句，这样就不会有意外。
>
> ```
> new Promise((resolve, reject) => {
> return resolve(1);
> // 后面的语句不会执行
> console.log(2);
> })
> ```
>
> 有同学可能就会问了,不加感觉也没啥事啊,反正我在这个函数体内就是要做这些操作,放在 `resolve/reject`前后好像都不影响啊! 这里我给举个实际场景

##### a) 不加 return 导致的错误场景举🌰

> 一般来说,错误发生在 Promise 内,是不会传到外部的,只会在 Promise 内部消化,详见下方API详解部分的 [②Promise.prototype.catch()](#② Promise.prototype.catch())
>
> ```
> const promise = new Promise(function (resolve, reject) {
> resolve('成功了'); //如果你加了 return , 函数执行到此步就停止了
> setTimeout(function () { throw new Error('错误错误!!!!!') }, 0)
> });
> promise.then(function (value) { console.log(value) });
> // ok
> // Uncaught Error: 错误错误!!!!
> ```
>
> 上面代码中，Promise 指定在下一轮“事件循环”再抛出错误。到了那个时候，Promise 的运行已经结束了，所以这个错误是在 Promise 函数体外抛出的，会冒泡到最外层，成了未捕获的错误。

## Ⅱ - API 用法详解

> 此处将对于所有API进行详细剖析,参照资料为 [阮一峰的ES6日志](https://gitee.com/hongjilin/hongs-study-notes/tree/master/编程_前端开发学习笔记/Promise学习笔记)

#### ① Promise.prototype.then()

> Promise 实例具有`then`方法，也就是说，`then`方法是定义在原型对象`Promise.prototype`上的。它的作用是为 Promise 实例添加状态改变时的回调函数。前面说过，`then`方法的第一个参数是`resolved`状态的回调函数，第二个参数（可选）是`rejected`状态的回调函数。

##### a) `then`方法返回的是一个新的`Promise`实例

> `then`方法返回的是一个新的`Promise`实例（注意，不是原来那个`Promise`实例）。因此可以采用链式写法，即`then`方法后面再调用另一个`then`方法。
>
> ```
> getJSON("./hong.json").then(function(json) {
> return json.name;
> }).then(function(name) {
> console.log(`My name is ${name}` )
> });
> ```
>
> 上面的代码使用`then`方法，依次指定了两个回调函数。第一个回调函数完成以后，会将返回结果作为参数，传入第二个回调函数。

##### b) 采用链式的`then`, 会等待前一个Promise状态发生改变才会被调用

> 采用链式的`then`，可以指定一组按照次序调用的回调函数。这时，前一个回调函数，有可能返回的还是一个`Promise`对象（即有异步操作），这时后一个回调函数，就会等待该`Promise`对象的状态发生变化，才会被调用。
>
> ```
> getJSON("./hong.json")
> .then(function(json) {  return getJSON(json.name)})
> .then(
>     function (name) { console.log("resolved: My name is ", name)}, 
>     function (err){ console.log("rejected: ", err)}
>    );
> ```
>
> 上面代码中，第一个`then`方法指定的回调函数，返回的是另一个`Promise`对象。这时，第二个`then`方法指定的回调函数，就会等待这个新的`Promise`对象状态发生变化。如果变为`resolved`，就调用第一个回调函数，如果状态变为`rejected`，就调用第二个回调函数。

##### c) 使用箭头函数简写

> 如果采用箭头函数，上面的代码可以写得更简洁 (实际代码中基本都是这样写了)
>
> ```
> getJSON("./hong.json")
> .then(json => getJSON(json.name) )
> .then(
>     name => console.log("resolved: My name is ", name), 
>     err => console.log("rejected: ", err)
>    );
> ```

#### ② Promise.prototype.catch()

> `Promise.prototype.catch()`方法是`.then(null, rejection)`或`.then(undefined, rejection)`的别名，用于指定发生错误时的回调函数。

##### a) 基本用法

> ```
> getJSON('./hong.json')
> .then(function(posts) {})
> .catch(function(error) {
>   	// 处理 getJSON 和 前一个回调函数运行时发生的错误
>   	console.log('发生错误！', error);
>    });
> ```
>
> 上面代码中，`getJSON()`方法返回一个 Promise 对象
>
> > - 如果该对象状态变为`resolved`，则会调用`then()`方法指定的回调函数；
> > - 如果异步操作抛出错误，状态就会变为`rejected`，就会调用`catch()`方法指定的回调函数，处理这个错误
> > - 另外，`then()`方法指定的回调函数，如果运行中抛出错误，也会被`catch()`方法捕获。
> > - 被 catch 方法捕获的前提是前方的 then() 方法中没有对 `rejected` 进行捕获处理(即没有写reject回调函数)
>
> ```
> p.then((val) => console.log('指定成功回调:', val))
> .catch((err) => console.log('在catch中进行 rejected 的处理', err));
> // 等同于
> p.then((val) => console.log('指定成功回调:', val))
> .then(null, (err) => console.log("等同于另起一个then,只指定 rejected 的处理", err));
> ```

##### b) `reject()`方法的作用，等同于抛出错误

> ```
> const promise = new Promise(function(resolve, reject) {
> throw new Error('直接抛出错误');
> });
> promise.catch(function(error) {
> console.log('异常捕获: ',error);
> });
> //异常捕获:  Error: 直接抛出错误
> ```
>
> 上面代码中，`promise`抛出一个错误，就被`catch()`方法指定的回调函数捕获。注意，上面的写法与下面两种写法是等价的。
>
> ```
> /******************  写法一 ***************************************/
> const promise = new Promise(function(resolve, reject) {
> try {
> throw new Error('直接抛出错误');
> } catch(e) {
> console.log('进入catch,然后再用 reject(e)抛出 ')
> reject(e) 
> }
> });
> promise.catch(function(error) {
> console.log(error);
> });
> //进入catch,然后再用 reject(e)抛出 
> //Error: 直接抛出错误
> 
> /******************  写法二 ***************************************/
> const promise1 = new Promise(function(resolve, reject) {
> reject(new Error('使用 reject() 抛出错误'));
> });
> promise1.catch(function(error) {
> console.log(error);
> });
> //Error: 使用 reject() 抛出错误
> ```
>
> 比较上面两种写法，可以发现`reject()`方法的作用，等同于抛出错误,所以不必用try..catch()去承接后再去抛出了

##### c) 如果 Promise 状态已经被修改，再抛出错误是无效的

> ```
> const promise = new Promise(function(resolve, reject) {
> resolve('成功了'); //换成 reject('成功了') 结果也是一样的
> throw new Error('成功后扔抛出异常');
> });
> promise
> .then(function(value) { console.log(value) })
> .catch(function(error) { console.log(error) });
> // 成功了
> ```
>
> 上面代码中，Promise 在`resolve/reject`语句后面，再抛出错误，不会被捕获，等于没有抛出。因为 Promise 的状态一旦改变，就永久保持该状态，不会再变了(前面有说过)

##### d) Promise 对象的错误具有 “冒泡” 性质

> Promise 对象的错误具有“冒泡”性质，会一直向后传递，直到被捕获为止。也就是说，错误总是会被下一个`catch`语句捕获。
>
> ```
> getJSON('./hong.json') //第一个promise
> .then(function(post) { //第二个promise
>   	 return getJSON(post.commentURL)
>   })
> .then(function(comments) { //第三个promise
>   })
> .catch(function(error) {
>   	// 处理前面三个Promise产生的错误
>   });
> ```
>
> 上面代码中，一共有三个 Promise 对象(**then返回的仍可能是一个Promise对象**)：一个由`getJSON()`产生，两个由`then()`产生。它们之中任何一个抛出的错误，都会被最后一个`catch()`捕获。
>
> 也是因为这个特性,有了 **异常穿透问题**

##### e) 异常穿透问题

> - 当使用 promise 的 then 链式调用时, 可以在最后指定失败的回调
> - 前面任何操作出了异常, 都会传到最后失败的回调中处理
>
> ```
> getJSON('./hong.json')
>  .then(function(posts) { throw new Error('抛出异常') })
>   .then(res=>console.log(res),e=>console.log('被then的错误回调捕获',e) )
>  .catch(function(error) {
>   	 // 处理 getJSON 和 前一个回调函数运行时发生的错误
>   	console.log('错误捕获: ', error);
>   });
> //执行结果: 被then的错误回调捕获 Error: 抛出异常
> 
> /******************** 利用异常穿透 ****************************************/
> getJSON('./hong.json')
>  .then(function(posts) { throw new Error('抛出异常') })
>   .then(res=>console.log(res) ) //此处差异,不指定 reject 回调,利用异常穿透传到最后
>  .catch(function(error) {
>   	console.log('错误捕获: ', error);
>   });
> //执行结果:  错误捕获:  Error: 抛出异常
> ```
>
> 注:可以在每个then()的第二个回调函数中进行err处理,也可以利用异常穿透特性,到最后用`catch`去承接统一处理,两者一起用时,前者会生效(因为err已经将其处理,就不会再往下穿透)而走不到后面的catch![image-20210927105504988](Promise%E6%95%99%E7%A8%8B.assets/image-20210927105504988.png)

##### f) 建议使用 catch() 进行异常处理

> 一般来说，不要在`then()`方法里面定义 Reject 状态的回调函数（即`then`的第二个参数），总是使用`catch`方法。
>
> ```
> // bad
> promise
> .then(
> data=> console.log('成功',data),
> err=>console.log('失败了',err)
>   );
> /********* 好的写法 ********************/
> promise
> .then( data=> console.log('成功',data)) //只指定成功回调
> .catch( err=>console.log('失败了',err));
> ```
>
> 上面代码中，第二种写法要好于第一种写法:
>
> - 理由是第二种写法可以捕获前面`then`方法执行中的错误
> - 也更接近同步的写法（`try/catch`）
> - 因此, 建议总是使用`catch()`方法，而不使用`then()`方法的第二个参数。

##### g) 与传统 `try/catch` 代码块的差异

> 跟传统的`try/catch`代码块不同的是，如果没有使用`catch()`方法指定错误处理的回调函数，Promise 对象抛出的错误不会传递到外层代码，即不会有任何反应。
>
> ```
> const someAsyncThing = function() {
> return new Promise(function(resolve, reject) {
> // 下面一行会报错，因为hong 没有声明
> resolve( hong );
> });
> };
> //Promise 的 then() 处理,但不处理异常
> someAsyncThing().then(function() { console.log('只指定成功回调,不处理异常错误') });
> 
> setTimeout(() => { console.log('努力学习的汪') }, 2000);
> // Uncaught (in promise) ReferenceError: hong is not defined
> // 努力学习的汪
> ```
>
> 上面代码中，`someAsyncThing()`函数产生的 Promise 对象，内部有语法错误。
>
> > - 浏览器运行到这一行，会打印出错误提示`Uncaught (in promise) ReferenceError: hong is not defined`
> > - 但是不会退出进程、终止脚本执行, 2 秒之后还是会输出`努力学习的汪`。
> > - 这就是说，Promise 内部的错误不会影响到 Promise 外部的代码，通俗的说法就是“Promise 会吃掉错误”。

##### h) catch()方法后还能跟 then() 方法

> 一般总是建议，Promise 对象后面要跟`catch()`方法，这样可以处理 Promise 内部发生的错误。`catch()`方法返回的还是一个 Promise 对象，因此后面还可以接着调用`then()`方法。
>
> ```
> const someAsyncThing = function() {
> return new Promise(function(resolve, reject) {
> // 下面一行会报错，因为 hong 没有声明
> resolve( hong );
> });
> };
> 
> someAsyncThing()
> .catch(function(error) {  console.log('捉到错误咯:', error)})
> .then(function() { console.log('错误捕获后我还要浪') });
> //捉到错误咯: ReferenceError: hong is not defined
> //错误捕获后我还要浪
> ```
>
> 上面代码运行完`catch()`方法指定的回调函数，会接着运行后面那个`then()`方法指定的回调函数。
>
> 如果没有报错，则会跳过`catch()`方法。
>
> ```
> Promise.resolve('硬是成功了')
> .catch(function(error) { console.log('捉错误', error) })
> .then(v => console.log('catch后面的then: ',v) );
> //catch后面的then:  硬是成功了
> ```
>
> 上面的代码因为没有报错，跳过了`catch()`方法，直接执行后面的`then()`方法。此时，要是`then()`方法里面报错，就与前面的`catch()`无关了。

##### i) `catch()`方法之中，还能再抛出错误

> `catch()`方法之中，还能再抛出错误。
>
> ```
> const someAsyncThing = function() {
> return new Promise(function(resolve, reject) {
> // 下面一行会报错，因为 hong 没有声明
> resolve( hong );
> });
> };
> 
> someAsyncThing()
> .then(() =>  someOtherAsyncThing()) 
> .catch(function(error) {
>   	 console.log('ctach:', error);
>   	 // 下面一行会报错，因为 sum 没有声明
>   	  sum ++;
>   })
> .then(function() { console.log('捕获后的then()')});
> 
> // ctach: [ReferenceError: hong is not defined]
> // Uncaught (in promise) ReferenceError: sum is not defined
> ```
>
> 上面代码中，`catch()`方法抛出一个错误，因为后面没有别的`catch()`方法了，导致这个错误不会被捕获，也不会传递到外层。如果改写一下，结果就不一样了。
>
> ```
> someAsyncThing().then(function() {
> return someOtherAsyncThing();
> }).catch(function(error) {
> console.log('catch: ', error);
> // 下面一行会报错，因为 sum 没有声明
> sum ++;
> }).catch(function(error) {
> console.log('catch()后的catch: ', error);
> });
> //catch:  ReferenceError: hong is not defined
> //catch()后的catch:  ReferenceError: sum is not defined
> ```
>
> 上面代码中，第二个`catch()`方法用来捕获前一个`catch()`方法抛出的错误。

#### ③ Promise.prototype.finally()

> `finally()`方法用于指定不管 Promise 对象最后状态如何，都会执行的操作。该方法是 `ES2018` 引入标准的。
>
> ```
> promise
> .then(result => {···})
> .catch(error => {···})
> .finally(() => {···});
> ```
>
> 上面代码中，不管`promise`最后的状态，在执行完`then`或`catch`指定的回调函数以后，都会执行`finally`方法指定的回调函数。
>
> > - `finally`方法的回调函数不接受任何参数，
> > - 这意味着没有办法知道，前面的 Promise 状态到底是`fulfilled`还是`rejected`。
> > - 这表明，`finally`方法里面的操作，应该是与状态无关的，不依赖于 Promise 的执行结果。

##### a) `finally`本质上是`then`方法的特例

> ```
> promise
> .finally(() => {});
> 
> // 等同于
> promise
> .then(
> result =>  result ,
> error =>  throw error
> );
> ```
>
> 上面代码中，如果不使用`finally`方法，同样的语句需要为成功和失败两种情况各写一次。有了`finally`方法，则只需要写一次。

##### b) 它的实现

> 它的实现也很简单。
>
> ```
> Promise.prototype.finally = function (callback) {
> let P = this.constructor;
> return this.then(
> value  => P.resolve(callback()).then(() => value),
> reason => P.resolve(callback()).then(() => { throw reason })
> );
> };
> ```
>
> 上面代码中，不管前面的 Promise 是`fulfilled`还是`rejected`，都会执行回调函数`callback`。
>
> 从上面的实现还可以看到，`finally`方法总是会返回原来的值(传入什么即传出什么)
>
> ```
> // resolve 的值是 undefined
> Promise.resolve(2).then(() => {}, () => {})
> 
> // resolve 的值是 2
> Promise.resolve(2).finally(() => {})
> 
> // reject 的值是 undefined
> Promise.reject(3).then(() => {}, () => {})
> 
> // reject 的值是 3
> Promise.reject(3).finally(() => {})
> ```
>
> ![image-20210927135255264](Promise%E6%95%99%E7%A8%8B.assets/image-20210927135255264.png)

#### ④ Promise.all()

> `Promise.all()`方法用于将多个 Promise 实例，包装成一个新的 Promise 实例。
>
> ```
> const p = Promise.all([p1, p2, p3]);
> ```
>
> > - `Promise.all()`方法接受一个数组作为参数，
> > - `p1`、`p2`、`p3`都是 Promise 实例，如果不是，就会先调用下面讲到的`Promise.resolve`方法，将参数转为 Promise 实例，再进一步处理。
> > - 另外，`Promise.all()`方法的参数可以不是数组，但必须具有 Iterator 接口，且返回的每个成员都是 Promise 实例。

##### a) 返回的状态由什么决定?

> `p`的状态由`p1`、`p2`、`p3`决定，分成两种情况。
>
> > 1. 只有`p1`、`p2`、`p3`的状态都变成`fulfilled`，`p`的状态才会变成`fulfilled`，此时`p1`、`p2`、`p3`的返回值组成一个数组，传递给`p`的回调函数。
> > 2. 只要`p1`、`p2`、`p3`之中有一个被`rejected`，`p`的状态就变成`rejected`，此时第一个被`reject`的实例的返回值，会传递给`p`的回调函数。
>
> ###### 下面是一个具体的例子。
>
> ```
> // 生成一个Promise对象的数组
> const promises = ['hong', 1, 2, 3, 4, 5].map(item {
> return getJSON( item+'.json');
> });
> 
> Promise.all(promises).then(function (posts) {
> // ...
> }).catch(function(reason){
> // ...
> });
> ```
>
> 上面代码中，`promises`是包含 6 个 Promise 实例的数组，只有这 6 个实例的状态 **都** 变成`fulfilled`，或者**其中有一个变为`rejected`**，才会调用`Promise.all`方法后面的回调函数。
>
> ###### 下面是另一个例子
>
> ```
> const databasePromise = connectDatabase(); //假设定义了一个异步方法,此方法能拿到你需要的所有数据
> 
> const booksPromise = databasePromise     //定义一个方法,在 databasePromise() 执行后寻找其内部书本信息
> .then(findAllBooks);
> 
> const userPromise = databasePromise    //定义一个方法,在 databasePromise() 执行后寻找其内部当前用户信息
> .then(getCurrentUser);
> 
> Promise.all([
> booksPromise,
> userPromise
> ])
> .then(([books, user]) => pickTopRecommendations(books, user));
> ```
>
> 上面代码中，`booksPromise`和`userPromise`是两个异步操作，只有等到它们的结果都返回了，才会触发`pickTopRecommendations`这个回调函数。

##### b) 如果参数中的Promise实例定义了自己的catch方法 ?

> 注意，如果作为参数的 Promise 实例，自己定义了`catch`方法，那么它一旦被`rejected`，并不会触发`Promise.all()`的`catch`方法。
>
> ```
> //定义一个状态将为成功的的promise
> const p1 = new Promise((resolve, reject) => { resolve('hello')})
> .then(result => result)
> .catch(e => e);
> 
> //定义一个将抛出错误的promise
> const p2 = new Promise((resolve, reject) => { throw new Error('报错了') })
> .then(result => result)
> .catch(e =>{
> console.log('p2自己的catch捕获: ', e)
> return e; //异常获取后原样返回,不做修改
> });
> 
> //调用 Promise.all 方法
> Promise.all([p1, p2])
> .then(result => console.log(' Promise.all 方法中的成功回调: ', result))
> .catch(e => console.log(" Promise.all 方法中的catch", e));
> 
> //p2自己的catch捕获:  Error: 报错了
> // Promise.all 方法中的成功回调:  (2) ['hello', Error: 报错了]
> ```
>
> 上面代码中，
>
> > - `p1`会`resolved`，`p2`首先会`rejected`
> > - 但是`p2`有自己的`catch`方法，该方法返回的是一个新的 Promise 实例，`p2`指向的实际上是这个实例。
> > - 该实例执行完`catch`方法后，也会变成`resolved`，导致`Promise.all()`方法参数里面的两个实例都会`resolved`
> > - 因此会调用`then`方法指定的回调函数，而不会调用`catch`方法指定的回调函数

##### c) 如果参数中的Promise实例 `没有` 定义自己的catch方法 ?

> 如果`p2`没有自己的`catch`方法，就会调用`Promise.all()`的`catch`方法。
>
> ```
> //定义一个状态将为成功的的promise
> const p1 = new Promise((resolve, reject) => { resolve('hello')})
> .then(result => result)
> 
> //定义一个将抛出错误的promise
> const p2 = new Promise((resolve, reject) => { throw new Error('报错了') })
> .then(result => result)
> 
> //调用 Promise.all 方法
> Promise.all([p1, p2])
> .then(result => console.log(' Promise.all 方法中的成功回调: ', result))
> .catch(e => console.log(" Promise.all 方法中的catch", e));
> 
> // Promise.all 方法中的catch Error: 报错了
> ```

#### ⑤ Promise.race()

> `Promise.race()`方法同样是将多个 Promise 实例，包装成一个新的 Promise 实例。
>
> ```
> const p = Promise.race([p1, p2, p3]);
> ```
>
> 上面代码中，只要`p1`、`p2`、`p3`之中有一个实例率先改变状态，`p`的状态就跟着改变。那个率先改变的 Promise 实例的返回值，就传递给`p`的回调函数。
>
> `Promise.race()`方法的参数与`Promise.all()`方法一样，如果不是 Promise 实例，就会先调用下面讲到的`Promise.resolve()`方法，将参数转为 Promise 实例，再进一步处理。

##### a) 举个简单的🌰

> 如p1延时,开启了异步,内部正常是同步进行,所以`p2>p3>p1`,结果是`P2`
>
> ```
> let p1 = new Promise((resolve, reject) => {
> setTimeout(() => {
> resolve('OK');
> }, 1000);
> })
> let p2 = Promise.resolve('Success');
> let p3 = Promise.resolve('Oh Yeah');
> //调用
> const result = Promise.race([p1, p2, p3]);
> console.log(result);
> ```

##### b) 举个应用实🌰

> 下面是一个例子，如果指定时间内没有获得结果，就将 Promise 的状态变为`reject`，否则变为`resolve`。
>
> ```
> const p = Promise.race([
> fetch('https://gitee.com/hongjilin'),
> new Promise(function (resolve, reject) {
> setTimeout(() => reject(new Error('请求超时!!!!')), 5000)
> })
> ]);
> 
> p
> .then(console.log)
> .catch(console.error);
> ```
>
> 上面代码中，如果 5 秒之内`fetch`方法无法返回结果，变量`p`的状态就会变为`rejected`，从而触发`catch`方法指定的回调函数。
>
> 是不是很好用又简单

#### ⑥ Promise.allSettled()

> `Promise.allSettled()`方法接受一组 Promise 实例作为参数，包装成一个新的 Promise 实例。
>
> **只有等到所有这些参数实例都返回结果**，不管是`fulfilled`还是`rejected`，包装实例才会结束。
>
> 该方法由 [ES2020](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Ftc39%2Fproposal-promise-allSettled) 引入。

##### a) 举个简单的🌰

> ```
> const promises = [
> fetch('https://gitee.com/hongjilin'),
> fetch('https://github.com/Hongjilin'),
> fetch('./hong.json'),
> ];
> loading = true; //请求前将 loading 改为true ; 页面出现滚动加载图标蒙层
> await Promise.allSettled(promises);
> loading = false;
> ```
>
> 上面代码对服务器发出三个请求，等到三个请求都结束，不管请求成功还是失败，加载的滚动图标就会消失。

##### b) 该方法返回的新的 Promise 实例，一旦结束，状态总是`fulfilled`，不会变成`rejected`

> 该方法返回的新的 Promise 实例，一旦结束，状态总是`fulfilled`，不会变成`rejected`。状态变成`fulfilled`后，Promise 的监听函数接收到的参数是一个数组，每个成员对应一个传入`Promise.allSettled()`的 Promise 实例。
>
> ```
> const resolved = Promise.resolve('返回成功状态的promise');
> const rejected = Promise.reject('返回失败状态的promise');
> 
> const allSettledPromise = Promise.allSettled([resolved, rejected]);
> // Promise.allSettled 得到的新实例状态只会是 `fulfilled`
> allSettledPromise.then(function (results) {
> console.log(results); //注意,这是 `fulfilled` 的回调函数,只有其状态为成功才能进到这里
> });
> /*
> [
>   { "status": "fulfilled", "value": "返回成功状态的promise" },
>   { "status": "rejected", "reason": "返回失败状态的promise" }
> ]
> */
> ```
>
> > - `Promise.allSettled()`的返回值`allSettledPromise`，状态只可能变成`fulfilled`(注意,是 **allSettledPromise** 的状态,而不是内部的promise实例)
> > - 它的监听函数接收到的参数是数组`results`。该数组的每个成员都是一个对象，对应的是传入`Promise.allSettled()`的 Promise 实例。
> > - 每个对象都有`status`属性，该属性的值只可能是字符串`fulfilled`或字符串`rejected`。
> > - `fulfilled`时，对象有`value`属性，`rejected`时有`reason`属性，对应两种状态的返回值。

##### c) 举个返回值用法的🌰

> ```
> const promises = [ fetch('./hong.json'), fetch('https://gitee.com/hongjilin') ];
> const results = await Promise.allSettled(promises);
> 
> // 过滤出成功的请求
> const successfulPromises = results.filter(item => item.status === 'fulfilled');
> 
> // 过滤出失败的请求，并取得它们的失败原因
> const errors = results
> .filter(p => p.status === 'rejected')
> .map(p => p.reason);
> ```
>
> 有时候，我们不关心异步操作的结果，只关心这些操作有没有结束。这时，`Promise.allSettled()`方法就很有用。如果没有这个方法，想要确保所有操作都结束，就很麻烦。`Promise.all()`方法无法做到这一点。
>
> ```
> const urls = [ 'https://gitee.com/hongjilin' ,'https://github.com/Hongjilin'];
> const requests = urls.map(x => fetch(x));
> //举例用 Promise.all 尝试实现,很明显,难以实现
> try {
> await Promise.all(requests);
> console.log('所有请求都成功。');
> } catch {
> console.log('至少一个请求失败，其他请求可能还没结束。');
> }
> ```
>
> 上面代码中，`Promise.all()`无法确定所有请求都结束。想要达到这个目的，写起来很麻烦，有了`Promise.allSettled()`，这就很容易了

#### ⑦ Promise.any()

> ES2021 引入了[`Promise.any()`方法](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Ftc39%2Fproposal-promise-any)。该方法接受一组 Promise 实例作为参数，包装成一个新的 Promise 实例返回。只要参数实例有一个变成`fulfilled`状态，包装实例就会变成`fulfilled`状态；如果所有参数实例都变成`rejected`状态，包装实例就会变成`rejected`状态。

##### a) 与 `Promise.race()` 方法的区别

> `Promise.any()`跟`Promise.race()`方法很像，只有一点不同，就是不会因为某个 Promise 变成`rejected`状态而结束。
>
> ```
> const promises = [
> fetch('https://gitee.com/hongjilin').then(() => 'a'),
> fetch('https://github.com/Hongjilin').then(() => 'b'),
> fetch('./hong.json').then(() => 'c'),
> ];
> try {
> const first = await Promise.any(promises);
> console.log(first);
> } catch (error) {
> console.log(error);
> }
> ```
>
> 上面代码中，`Promise.any()`方法的参数数组包含三个 Promise 操作。其中只要有一个变成`fulfilled`，`Promise.any()`返回的 Promise 对象就变成`fulfilled`。如果所有三个操作都变成`rejected`，那么`await`命令就会抛出错误。

##### b) Promise.any() 抛出的错误

> `Promise.any()`抛出的错误，不是一个一般的错误，而是一个 AggregateError 实例。它相当于一个数组，每个成员对应一个被`rejected`的操作所抛出的错误。下面是 AggregateError 的实现示例。
>
> ```
> new AggregateError() extends Array -> AggregateError
> 
> const err = new AggregateError();
> err.push(new Error("first error"));
> err.push(new Error("second error"));
> throw err;
> ```
>
> 捕捉错误时，如果不用`try...catch`结构和 await 命令，可以像下面这样写。
>
> ```
> Promise.any(promises).then(
> (first) => {
> // Any of the promises was fulfilled.
> },
> (error) => {
> // All of the promises were rejected.
> }
> );
> ```

##### c) 再举个🌰

> 下面是一个例子。
>
> ```
> const resolved = Promise.resolve('成功');
> const rejected = Promise.reject('失败了');
> const alsoRejected = Promise.reject('太失败了');
> 
> Promise.any([resolved, rejected, alsoRejected]).then(function (result) {
> console.log(result); // 成功
> });
> 
> Promise.any([rejected, alsoRejected]).catch(function (results) {
> console.log(results);  //AggregateError: All promises were rejected
> });
> ```
>
> 三个Promise中有一个为成功,则总的结果就是成功,三个中全部失败,才会变成失败

#### ⑧ Promise.resolve()

> 有时需要将现有对象转为 Promise 对象，`Promise.resolve()`方法就起到这个作用。
>
> ```
> const jsPromise = Promise.resolve($.ajax('https://gitee.com/hongjilin'));
> ```
>
> 上面代码将 jQuery 生成的`deferred`对象，转为一个新的 Promise 对象。
>
> `Promise.resolve()`等价于下面的写法。
>
> ```
> Promise.resolve('努力学习的汪')
> // 等价于
> new Promise(resolve => resolve('努力学习的汪'))
> ```
>
> `Promise.resolve()`方法的参数分成四种情况

##### a) 参数是一个 Promise 实例

> 如果参数是 Promise 实例，那么`Promise.resolve`将不做任何修改、原封不动地返回这个实例。

##### **b) 参数是一个`thenable`对象**

> `thenable`对象指的是具有`then`方法的对象，比如下面这个对象。
>
> ```
> let thenable = {
>   then: function(resolve, reject) {
>   	resolve('成功');
>   }
> };
> ```
>
> `Promise.resolve()`方法会将这个对象转为 Promise 对象，然后就立即执行`thenable`对象的`then()`方法。
>
> ```
> let thenable = {
>   then: function(resolve, reject) { resolve('成功') }
> };
> 
> let p1 = Promise.resolve(thenable);
> p1.then(function (value) {
>   console.log(value);  // '成功'
> });
> ```
>
> 上面代码中，`thenable`对象的`then()`方法执行后，对象`p1`的状态就变为`resolved`，从而立即执行最后那个`then()`方法指定的回调函数，输出 **'成功'**。

##### c) 参数不是具有`then()`方法的对象，或根本就不是对象

> 如果参数是一个原始值，或者是一个不具有`then()`方法的对象，则`Promise.resolve()`方法返回一个新的 Promise 对象，状态为`resolved`。
>
> ```
> const p = Promise.resolve('努力学习的汪');
> 
> p.then(function (s) {
> console.log(s)
> });
> // 努力学习的汪
> ```
>
> 上面代码生成一个新的 Promise 对象的实例`p`。
>
> > - 由于字符串 `努力学习的汪` 不属于异步操作（判断方法是字符串对象不具有 then 方法）
> > - 返回 Promise 实例的状态从一生成就是`resolved`，所以回调函数会立即执行
> > - `Promise.resolve()`方法的参数会同时传给回调函数作为其参数

##### d) 不带有任何参数

> `Promise.resolve()`方法允许调用时不带参数，直接返回一个`resolved`状态的 Promise 对象。
>
> 所以，如果希望得到一个 Promise 对象，比较方便的方法就是直接调用`Promise.resolve()`方法。
>
> ```
> const p = Promise.resolve();
> 
> p.then(function () {});
> ```
>
> 上面代码的变量`p`就是一个 Promise 对象。
>
> 需要注意的是，立即`resolve()`的 Promise 对象，是在本轮“事件循环”（event loop）的结束时执行，而不是在下一轮“事件循环”的开始时 --> 不懂的同学请看 [JavaScript笔记中的#4事件循环模型event-loop机制](https://gitee.com/hongjilin/hongs-study-notes/tree/master/编程_前端开发学习笔记/HTML+CSS+JS基础笔记/JavaScript笔记#4事件循环模型event-loop机制) ,本人在此有进行详细的解析
>
> ```
> setTimeout(function () {
> console.log('three'); //这里是新的一轮事件循环
> }, 0);
> 
> Promise.resolve().then(function () {
> console.log('two'); //本轮同步代码结束后,新一轮事件循环前,就执行
> });
> 
> console.log('one');
> 
> // one
> // two
> // three
> ```
>
> 上面代码中，`setTimeout(fn, 0)`在下一轮“事件循环”开始时执行，`Promise.resolve()`在本轮“事件循环”结束时执行，`console.log('one')`则是立即执行，因此最先输出。

#### ⑨ Promise.reject()

> `Promise.reject(reason)`方法也会返回一个新的 Promise 实例，该实例的状态为`rejected`。
>
> ```
> const p = Promise.reject('出错了');
> // 等同于
> const p = new Promise((resolve, reject) => reject('出错了'))
> 
> p.then(null, function (s) {
> console.log(s)
> });
> // 出错了
> ```
>
> 上面代码生成一个 Promise 对象的实例`p`，状态为`rejected`，回调函数会立即执行。
>
> `Promise.reject()`方法的参数，会原封不动地作为`reject`的理由，变成后续方法的参数。
>
> ```
> Promise.reject('出错了')
> .catch(e => {
> console.log(e === '出错了')
> })
> // true
> ```
>
> 上面代码中，`Promise.reject()`方法的参数是一个字符串，后面`catch()`方法的参数`e`就是这个字符串。

#### ⑩ Promise.try()

> 实际开发中，经常遇到一种情况：不知道或者不想区分，函数`f`是同步函数还是异步操作，但是想用 Promise 来处理它。因为这样就可以不管`f`是否包含异步操作，都用`then`方法指定下一步流程，用`catch`方法处理`f`抛出的错误。一般就会采用下面的写法。
>
> ```
> Promise.resolve().then(f)
> ```
>
> 上面的写法有一个缺点，就是如果`f`是同步函数，那么它会在本轮事件循环的末尾执行。
>
> ```
> const f = () => console.log('now');
> Promise.resolve().then(f);
> console.log('next');
> // next
> // now
> ```
>
> 上面代码中，函数`f`是同步的，但是用 Promise 包装了以后，就变成异步执行了。
>
> ###### 那么有没有一种方法，让同步函数同步执行，异步函数异步执行，并且让它们具有统一的 API 呢？

##### a) 写法一 : 用`async`函数来写

> 该知识点如果不懂的可以继续往下看,这是ES6的另外一块知识点内容
>
> ```
> const f = () => console.log('now');
> (async () => f())();
> console.log('next');
> // now
> // next
> ```
>
> 上面代码中，第二行是一个立即执行的匿名函数，会立即执行里面的`async`函数，因此如果`f`是同步的，就会得到同步的结果；如果`f`是异步的，就可以用`then`指定下一步，就像下面的写法。
>
> ```
> (async () => f())()
> .then(...)
> ```
>
> 需要注意的是，`async () => f()`会吃掉`f()`抛出的错误。所以，如果想捕获错误，要使用`promise.catch`方法。
>
> ```
> (async () => f())()
> .then(...)
> .catch(...)
> ```

##### b) 写法二 : 使用`new Promise()`

> ```
> const f = () => console.log('now');
> (
>  () => new Promise(
>    resolve => resolve(f())
>  )
> )();
> console.log('next');
> // now
> // next
> ```
>
> 上面代码也是使用立即执行的匿名函数，执行`new Promise()`。这种情况下，同步函数也是同步执行的。

##### c) Promise.try的引出

> 鉴于这是一个很常见的需求，所以现在有一个[提案](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Fljharb%2Fproposal-promise-try)，提供`Promise.try`方法替代上面的写法。
>
> ```
> const f = () => console.log('now');
> Promise.try(f);
> console.log('next');
> // now
> // next
> ```
>
> 事实上，`Promise.try`存在已久，Promise 库[`Bluebird`](https://gitee.com/link?target=http%3A%2F%2Fbluebirdjs.com%2Fdocs%2Fapi%2Fpromise.try.html)、[`Q`](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Fkriskowal%2Fq%2Fwiki%2FAPI-Reference%23promisefcallargs)和[`when`](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Fcujojs%2Fwhen%2Fblob%2Fmaster%2Fdocs%2Fapi.md%23whentry)，早就提供了这个方法。
>
> 由于`Promise.try`为所有操作提供了统一的处理机制，所以如果想用`then`方法管理流程，最好都用`Promise.try`包装一下。这样有[许多好处](https://gitee.com/link?target=http%3A%2F%2Fcryto.net%2F~joepie91%2Fblog%2F2016%2F05%2F11%2Fwhat-is-promise-try-and-why-does-it-matter%2F)，其中一点就是可以更好地管理异常。
>
> ```
> function getUsername(userId) {
>  return database.users.get({id: userId})
>  .then(function(user) {
>    return user.name;
>  });
> }
> ```
>
> 上面代码中，`database.users.get()`返回一个 Promise 对象，如果抛出异步错误，可以用`catch`方法捕获，就像下面这样写。
>
> ```
> database.users.get({id: userId})
> .then(...)
> .catch(...)
> ```
>
> 但是`database.users.get()`可能还会抛出同步错误（比如数据库连接错误，具体要看实现方法），这时你就不得不用`try...catch`去捕获。
>
> ```
> try {
>  database.users.get({id: userId})
>  .then(...)
>  .catch(...)
> } catch (e) {
>  // ...
> }
> ```
>
> 上面这样的写法就很笨拙了，这时就可以统一用`promise.catch()`捕获所有同步和异步的错误。
>
> ```
> Promise.try(() => database.users.get({id: userId}))
>  .then(...)
>  .catch(...)
> ```
>
> 事实上，`Promise.try`就是模拟`try`代码块，就像`promise.catch`模拟的是`catch`代码块。

------

# 自定义Promise手写

> 1. 下方的`Promise.prototype.then`与`Promise.resolve`为什么一个挂载在`prototype`而另一个挂载在实例对象上?
>
> 解:原因是分别为静态方法与实例方法
>
> -->上面的需要new实例化的时候自动继承实例`prototype`上的方法和属性,所以用`实例对象.then()`来调用,而下面的Promise.resolve是静态方法,不用new,是可以直接Promise.resolve()调用
>
> ## **此部分可以跳过不看,类似手撕源码**

## Ⅰ-Promise的实例方法实现

### 1 - 初始结构搭建

> html引入,该章节后续html大部分重复 除非必要,否则不再放上来

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Promise-封装 | 1 - 初始结构搭建</title>
    <script src="./promise.js"></script>
</head>
<body>
    <script>
        let p = new Promise((resolve, reject) => {
            resolve('OK');
        });
        p.then(value => {
            console.log(value);
        }, reason=>{
            console.warn(reason);
        })
    </script>
</body>
</html>
```

> promise.js -->使用原生写法,最后会改为class写法

```
function Promise(executor){}
//添加 then 方法
Promise.prototype.then = function(onResolved, onRejected){}
```

### 2 - resolve 与 reject构建与基础实现

> 1. 使用`const self = this;`保存this执行,使function中可以取得当前实例
>
> ps:可以不使用该方法保存,但是下方function需要`改为箭头函数`,否则`function默认指向是window`
>
> 之后代码默认使用`self`保存this,箭头函数方式将在最后改为class写法时使用
>
> 1. 默认设置 `PromiseState = 'pending'以及 PromiseResult = null`,这就是promise状态基础

```
//声明构造函数
function Promise(executor) {
  //添加属性
  this.PromiseState = 'pending';
  this.PromiseResult = null;
  //保存实例对象的 this 的值
/*  此处可以不写,但是下面function方法需要改为箭头函数,否则function默认指向是window */
  const self = this; 
  //resolve 函数
  function resolve(data) {--------------------------------------------
    //1. 修改对象的状态 (promiseState)
    self.PromiseState = 'fulfilled'; // resolved
    //2. 设置对象结果值 (promiseResult)
    self.PromiseResult = data;
  }
  //reject 函数
  function reject(data) {----------------------------------------------
    //1. 修改对象的状态 (promiseState)
    self.PromiseState = 'rejected'; // 
    //2. 设置对象结果值 (promiseResult)
    self.PromiseResult = data;
  }
  //同步调用『执行器函数』
  executor(resolve, reject);
}
//添加 then 方法
Promise.prototype.then = function (onResolved, onRejected) {}
```

### 3 - throw 抛出异常改变状态

> 1. 在2的基础上进行修改:将执行器放入`try-catch()`中
> 2. 在catch中使用`reject()`修改 promise 对象状态为『`失败`』

```
 try {
    //同步调用『执行器函数』
    executor(resolve, reject);
  } catch (e) {
    //修改 promise 对象状态为『失败』
    reject(e);
  }
```

### 4 - 状态只能修改一次

> 1. 基于2 3代码中resolve和reject方法进修改
> 2. 在成功与失败函数中添加判断` if(self.PromiseState !== 'pending') return;`,如果进入函数时状态不为`pending`直接退出,这样就能做到状态只能从`pending`改至其他状态且做到只能改一次

```
html调用--------------------------------------------------------
 let p = new Promise((resolve, reject) => {
      reject("error");
      resolve('OK');
      //抛出异常
      // throw "error";
    });
 console.log(p);
promise.js修改--------------------------------------------------------

  //resolve 函数
    function resolve(data){
        //判断状态
        if(self.PromiseState !== 'pending') return;
        //1. 修改对象的状态 (promiseState)
        self.PromiseState = 'fulfilled';// resolved
        //2. 设置对象结果值 (promiseResult)
        self.PromiseResult = data;
    }
    //reject 函数
    function reject(data){
        //判断状态
        if(self.PromiseState !== 'pending') return;
        //1. 修改对象的状态 (promiseState)
        self.PromiseState = 'rejected';// 
        //2. 设置对象结果值 (promiseResult)
        self.PromiseResult = data;
    }
```

### 5 - then 方法执行回调基础实现

> 1. 修改`Promise.prototype.then`方法
> 2. 传入`then(成功回调,失败回调)`,当调用then后,会判断当前`this.PromiseState`的状态,当其为成功时调用`成功回调`,失败时调用`失败回调`

```
html调用------------------------------------------------------------
    let p = new Promise((resolve, reject) => {
      // resolve('OK');// reject("Error");
      throw "ERROR";
    });
    p.then(
        value => {console.log(value); }, 
        reason => {console.warn(reason);}
    )
promise.js修改与实现-----------------------------------------------------
//添加 then 方法
Promise.prototype.then = function (onResolved, onRejected) {
  //调用回调函数  PromiseState
  if (this.PromiseState === 'fulfilled') {onResolved(this.PromiseResult);}
  if (this.PromiseState === 'rejected') {onRejected(this.PromiseResult);}
}
```

### 6 - 异步任务 then 方法实现

> 1. 此处对于5有四处修改,下面上`js代码`
> 2. 当我运行`异步代码`后,我的执行器内部代码还未返回(因为用了定时器,里面的代码进入了异步队列),所以当我下面的.then()运行时:我的`p`为`pending`状态,所以根本不会执行resolve与reject方法
>
> 解:添加判断`pending`状态,将当前回调函数保存到实例对象(存到实例上是为了更方便)中,这样后续改变状态时候才调用得到
>
> 1. 为什么要将回调保存到实例上而不是直接调用?
>
> `理由`:因为我的回调函数需要在我的promise状态改变后(成功或者失败),再根据状态选择运行哪个函数 所以当你调用`then()`时却检测到状态为`pending`,说明这时候的promise在异步队列 不能直接运行成功或者失败函数
>
> `解决`:因为`resolve与reject`方法与`then()`不在同一个作用域中,并不能共享`then(成功回调,失败回调)`的参数,所以在判断状态为`pending`时将回调保存到实例对象上.然后将回调函数的调用放在`resolve()与reject()`中
>
> 这样当我代码运行到异步队列的`resolve()或reject()`时,就可以在这个函数中运行回调函数,实现异步then
>
> 1. 此处的then`仍有瑕疵`,需要继续完善

```
html调用------------------------------------------------------------
 //实例化对象
    let p = new Promise((resolve, reject) => {
      setTimeout(() => {reject("error"); /* resolve('OK');*/}, 1000);
    });
    p.then(value => {console.log(value);},reason => { console.warn(reason);});
    console.log(p);

promise.js修改与实现-----------------------------------------------------
//声明构造函数
function Promise(executor) {
  this.PromiseState = 'pending'; this.PromiseResult = null;
  // 声明属性     
  this.callback = {};			-----------新添加1
  const self = this; 
    
  //resolve 函数
  function resolve(data) {
    //判断状态
    if (self.PromiseState !== 'pending') return;
    self.PromiseState = 'fulfilled'; self.PromiseResult = data;
    //调用成功的回调函数  加判断的原因是防止无回调报错
    if (self.callback.onResolved) { self.callback.onResolved(data); }  ------------新添加2 最重要 
  }
    
  //reject 函数
  function reject(data) {
    if (self.PromiseState !== 'pending') return;
    self.PromiseState = 'rejected'; self.PromiseResult = data;
    //执行回调						
    if (self.callback.onResolved) { self.callback.onResolved(data);}  ------------新添加3
  }
  try {executor(resolve, reject);} catch (e) {reject(e);}
}

//添加 then 方法
Promise.prototype.then = function (onResolved, onRejected) {
  //调用回调函数  PromiseState
  if (this.PromiseState === 'fulfilled') {onResolved(this.PromiseResult);}
  if (this.PromiseState === 'rejected') { onRejected(this.PromiseResult);}
  //判断 pending 状态
  if (this.PromiseState === 'pending') {  ------------新添加4
    //保存回调函数
    this.callback = {
      onResolved: onResolved,
      onRejected: onRejected
    }
  }
}
```

### 7 - 指定多个回调

> 1. 基于6代码进行修改 只展示修改部分代码
> 2. `6`中保存回调函数的方式有BUG,如果我有多个`.then()`,后面加载的回调函数会覆盖之前的回调函数,导致最后回调函数`有且只有`最后一个
>
> 解:使用`数组`的方式进行存储回调函数,调用时也是用数组循环取出
>
> 1. 此处的then`仍有瑕疵`,需要继续完善

```
html调用------------------------------------------------------------
//实例化对象
   let p = new Promise((resolve, reject) => {setTimeout(() => {reject('No');}, 1000);});
   p.then(value => { console.log(value);}, reason=>{console.warn(reason);});
   p.then(value => { alert(value);}, reason=>{ alert(reason);});
   console.log(p);

promise.js修改与实现-----------------------------------------------------
Promise.prototype.then = function (onResolved, onRejected) {
       //resolve 函数
    function resolve(data){
  		.....
        //调用成功的回调函数
        // if (self.callback.onResolved) { self.callback.onResolved(data); } 
        self.callbacks.forEach(item => {   --------修改1
            item.onResolved(data);
        });
    }
    //reject 函数
    function reject(data){
     	 ......
        //执行失败的回调
        // if (self.callback.onResolved) { self.callback.onResolved(data);}
        self.callbacks.forEach(item => {		------修改2
            item.onRejected(data);
        });
    }
    
  //添加 then 方法
Promise.prototype.then = function(onResolved, onRejected){
    ........
    //判断 pending 状态
    if(this.PromiseState === 'pending'){
        //保存回调函数
        //  this.callback = { onResolved: onResolved, onRejected: onRejected }
        this.callbacks.push({					--------修改3
            onResolved: onResolved,
            onRejected: onRejected
        });
    }
}
```

### 8 - 同步任务 then 返回结果

> 1. 在之前的then运行结果中得知,我们使用 [ then ] 后的返回结果是其回调函数的返回结果,而我们需要的返回结果是一个新的promise对象
>
> 解:所以我们在then中`return new Promise()`,使其得到的是一个新的promise对象
>
> 1. 在为`解决问题1`后产生一个新问题:新的promise对象因为没有用`rejerect与resolve`方法,导致返回的状态一直是`pending`
>
> 解:在新的promise中判断`运行回调函数`后的返回值是什么,然后根据其不同类型给其赋予不同状态
>
>  Ⅰ-`if(result instanceof Promise)`:返回值一个新的②promise对象(因为是新的promise的回调函数返回值,称`②promise对象`),在返回值(因为是promise对象)的`.then()`回调函数中使用rejerect与resolve方法,将其`自身的状态`赋予外层的promise,
>
>  即 回调函数中的promise 赋值 给then返回值 , 所以 `最终返回状态==回调函数中的新promise状态`
>
>  Ⅱ-如果返回值是一个`非promise`对象,返回状态设置为成功
>
>  Ⅲ-如果返回值是一个异常,返回状态设置为失败

```
html调用------------------------------------------------------------
  //实例化对象
    let p = new Promise((resolve, reject) => {resolve('OK');});
    //执行 then 方法
    const res = p.then(
     value => { throw "FAIL";},
    reason => { console.warn(reason);});
    console.log(res);

promise.js修改与实现-----------------------------------------------------
//添加 then 方法
Promise.prototype.then = function(onResolved, onRejected){
    return new Promise((resolve, reject) => {
        //调用回调函数  PromiseState
 //  if(this.PromiseState === 'fulfilled'){ onResolved(this.PromiseResult);} 未修改时代码
        if(this.PromiseState === 'fulfilled'){    -------修改1 
            try{
                //获取回调函数的执行结果
                let result = onResolved(this.PromiseResult);
                //判断
                if(result instanceof Promise){//如果是 Promise 类型的对象,我就将下一个promise结果赋予外层
                    result.then(v => {  resolve(v); },r=>{reject(r);})
                }else{resolve(result);}  //如果返回的不是promise对象,都将其赋予成功状态
            }catch(e){
                rejerect(e);	//如果出错了,则返回失败状态
            }
        }
        if(this.PromiseState === 'rejected'){ onRejected(this.PromiseResult);}------此部分修改与修改1一样
        //判断 pending 状态
        if(this.PromiseState === 'pending'){
            this.callbacks.push({ onResolved: onResolved, onRejected: onRejected});
        }
    })
}
```

### 9 - 异步任务 then 返回结果

> 1. 异步任务是修改`if(this.PromiseState === 'pending')`后面的值,原因参考`6`,下面代码只举例这部分修改
> 2. 因为我们需要增加then状态修改,所以在我们保存回调函数这一步我们可以对于回调函数进行`加工`,`添加判断其回调函数的返回值`的代码块再存入实例的回调函数中
>
> Ⅰ-声明一个新的函数:其内部功能->先运行`onResolved回调函数`,再将其返回值取出,进行判断其返回值(这个过程同`8`)
>
> Ⅱ-加工后存入实例回调函数数组,之后在`resolve与reject`方法中调用即可(同`6`)

```
html调用------------------------------------------------------------
   //实例化对象
    let p = new Promise((resolve, reject) => {
      setTimeout(() => {reject("Error");}, 1000)}); // resolve('OK');
    //执行 then 方法
    const res = p.then(value => {
      // return 'oh Yeah';  //如果有返回,根据其返回值得到相应的状态:字符串为成功,抛出为错误
      throw 'error';
    }, reason => {
      console.warn(reason, "xx"); //如果只是打印没返回,则实际上时返回一个undefined,
      //在我们封装js中,undefined会判定为非promise对象,所以状态为成功,结果为undefined
      return "sss"   // throw 'error';
    });
    console.log(res);

promise.js修改与实现-----------------------------------------------------
    //判断 pending 状态
    if (this.PromiseState === 'pending') {
      //保存回调函数
      this.callbacks.push({
          
        onResolved: function () {
          try {
            //执行成功回调函数
            let result = onResolved(self.PromiseResult);
            //判断 其结果
            if (result instanceof Promise) {
              result.then(
                  v => { resolve(v);},
                  r => {reject(r);}
                 )
            } else {resolve(result);}
          } catch (e) {reject(e);}
        },
          
        onRejected: function () {
          try {
            //执行成功回调函数
            let result = onRejected(self.PromiseResult);
            //判断
            if (result instanceof Promise) {
              result.then(
                  v => {resolve(v); },
                  r => {reject(r);}
                 )
            } else {resolve(result);}
          } catch (e) { reject(e); }
        }
      });
    }
```

### 10- then方法代码优化

> 1. 在8、9、10中可以看出,其判断与改变返回结果状态的代码块是基本重复的,所以可以将其抽出

```
//添加 then 方法
Promise.prototype.then = function (onResolved, onRejected) {
  const self = this;
  return new Promise((resolve, reject) => {
    封装函数----------------------------------------------------------------------------
    function callback(type) {
      try {
        //获取回调函数的执行结果
        let result = type(self.PromiseResult);
        //判断
        if (result instanceof Promise) {
          //如果是 Promise 类型的对象
          result.then(v => {
            resolve(v);
          }, r => {
            reject(r);
          })
        } else {
          //结果的对象状态为『成功』
          resolve(result);
        }
      } catch (e) {
        reject(e);
      }
    }
  -----------------------------------------------------------------------------------    
    //调用回调函数  PromiseState
    if (this.PromiseState === 'fulfilled') {
      callback(onResolved);
    }
    if (this.PromiseState === 'rejected') {
      callback(onRejected);
    }
    //判断 pending 状态
    if (this.PromiseState === 'pending') {
      //保存回调函数
      this.callbacks.push({
        onResolved: function () {
          callback(onResolved);
        },
        onRejected: function () {
          callback(onRejected);
        }
      });
    }
  })
}
```

### 11 - catch 方法与异常穿透与值传递

> 1. 异常穿透:添加`catch 方法 `,并且需要进行回调函数为`undefined的`处理
> 2. 当我`then()`中只传一个回调或者不传回调函数时,运行代码会报错,因为运行时调用的回调函数是`undefined`
>
> 解:进行回调函数判断,当其为空时,基于默认回调函数内容:`直接往外抛出`这样下方的`then() or catch()`就可以承接到异常或者值

```
html调用------------------------------------------------------------  
//实例化对象
    let p = new Promise((resolve, reject) => {
      setTimeout(() => {resolve('OK'); }, 1000);
    });
    //值传递
    p.then()
    .then(value => {console.log(222);})
      .then(value => {console.log(333);})
        .catch(reason => {console.warn(reason);});
promise.js修改与实现-----------------------------------------------------
//添加 then 方法
Promise.prototype.then = function (onResolved, onRejected) {
	...				-----------修改1
  if (typeof onRejected !== 'function') {onRejected = reason => { throw reason;}}
  if (typeof onResolved !== 'function') { onResolved = value => value;}
	 ....
}
//添加 catch 方法  
Promise.prototype.catch = function(onRejected){  ---------------异常穿透 修改2
    return this.then(undefined, onRejected);
}
```

## Ⅱ-Promise的静态方法实现

### 1 - Promise.resolve 封装

> 1. 判断传入的参数是否为`promise对象`:
>
>    Ⅰ-如果为`promise`:将其状态与结果赋值给外层promise对象
>
>    Ⅱ-如果为`非promise`:状态设置为成功

```
html调用------------------------------------------------------------  
 const p = Promise.resolve('OK');
 const p2 = Promise.resolve(new Promise((resolve, reject) => {     
      reject("error");// resolve('Success');
    }));
 const p3 = Promise.resolve(Promise.resolve('Oh Yeah'));
 console.log(p3);

promise.js修改与实现-----------------------------------------------------
//添加 resolve 方法
Promise.resolve = function(value){
    //返回promise对象
    return new Promise((resolve, reject) => {
        if(value instanceof Promise){
            value.then(
                v=>{resolve(v);},
                r=>{reject(r);}
            )}else{resolve(value); }//状态设置为成功
    });
}
```

### 2 - Promise.resolve 封装

> 不同于resolve,这个方法只要把传入参数再次传出去,并将状态改为`失败`即可

```
html调用------------------------------------------------------------  
   //Promise.reject
    const p = Promise.reject('Error');
    const p2 = Promise.reject(new Promise((resolve, reject) => {
      resolve('OK');
    }));
    console.log(p);
    console.log(p2);

promise.js修改与实现-----------------------------------------------------
//添加 reject 方法
Promise.reject = function (reason) {
  return new Promise((resolve, reject) => {
    reject(reason);
  });
}
```

### 3 - Promise.all 封装

> 1. 遍历传入的promise数组,每当遍历结果是成功,则用计数器记录,当计数器等同于数组长度,则全部成功,这时候可以返回`成功`状态
> 2. 如果当数组中任意一个promise的执行结果是`reject`,直接中断,返回状态为`失败`

```
html调用------------------------------------------------------------  
let p1 = new Promise((resolve, reject) => {
      setTimeout(() => {resolve('OK'); }, 1000)
    })
    let p2 = Promise.reject('Success');
    let p3 = Promise.resolve('Oh Yeah');
    //调用 all 方法
    let result = Promise.all([p1, p2, p3]);
    console.log(result);

promise.js修改与实现-----------------------------------------------------
//添加 all 方法
Promise.all = function (promises) {
  //返回结果为promise对象
  return new Promise((resolve, reject) => {
    //声明变量
    let count = 0;
    let arr = [];
    //遍历
    for (let i = 0; i < promises.length; i++) {
      promises[i].then(v => {
        //得知对象的状态是成功
        //每个promise对象 都成功
        count++;
        //将当前promise对象成功的结果 存入到数组中
        arr[i] = v;
        //判断
        if (count === promises.length) {resolve(arr);}//修改状态
      }, r => {
        reject(r);
      });
    }
  });
}
```

### 4 - Promise.race 封装

> 直接谁先执行就返回谁的运行结果即可

```
html调用------------------------------------------------------------  
 let p1 = new Promise((resolve, reject) => {
      setTimeout(() => {resolve('OK');});
    });
    let p2 = Promise.reject('Success');
    let p3 = Promise.resolve('Oh Yeah');
    //调用 race 方法
    let result = Promise.race([p1, p2, p3]);
    console.log(result);

promise.js修改与实现-----------------------------------------------------
//添加 race 方法
Promise.race = function (promises) {
  return new Promise((resolve, reject) => {
    for (let i = 0; i < promises.length; i++) {
      promises[i].then(v => {
        //修改返回对象的状态为 『成功』
        resolve(v);
      }, r => {
        //修改返回对象的状态为 『失败』
        reject(r);
      })
    }
  });
}
```

## Ⅲ-其他优化

### 1 - 回调函数『异步执行』

> 1. 如果我们运行下面代码,正确顺序是: 111 --> 333 -->444
>
> ```
>  let p1 = new Promise((resolve, reject) => {
>      reject('OK');
>      console.log(111);
>    });
> 
>    p1.then(value => {
>      console.log(222);
>    }, reason => {
>      console.log(444);
>    });
> 
>    console.log(333);
> ```
>
> 1. 但当我们运行之前封装的 **Promise** 代码时,结果却是:111 --> 444 --> 333
>
> 我们需要将我们的then方法变成`异步方法`
>
> 1. 我们只要在以下四处地方的`回调函数调用`外层包裹一层定时器(不一定是定时器,开启异步即可),即可做到异步操作
>
> ```
> function resolve(data){
>        setTimeout(() => { self.callbacks.forEach(item => { item.onResolved(data); }); });--修改1
>    }
>   //reject 函数
>    function reject(data){
>        setTimeout(() => { self.callbacks.forEach(item => { item.onRejected(data); }); });---修改2
>    }
> 
> //添加 then 方法
> Promise.prototype.then = function(onResolved, onRejected){
>    return new Promise((resolve, reject) => {
>        //调用回调函数  PromiseState
>       /*  修改前代码
>       if (this.PromiseState === 'fulfilled') { callback(onResolved); }
>   		if (this.PromiseState === 'rejected') { callback(onRejected);
>   		 */
>        if(this.PromiseState === 'fulfilled'){setTimeout(() => { callback(onResolved);});}  -----修改3
>        if(this.PromiseState === 'rejected'){ setTimeout(() => { callback(onRejected);});   ---修改4
>        }
>    }
> ```
>
> 1. `相关原理参照js事件循环机制、宏任务与微任务`

### 2- class改写promise

> 1. 其中将`self=this`保存this指向方式改为箭头函数表示(在上面示例中也有效果)
> 2. 将其改为class写法
> 3. 下面为promisedemo.js代码
>
> ```
> class Promise {
>  //构造方法
>  constructor(executor) {
>    //添加属性
>    this.PromiseState = 'pending';
>    this.PromiseResult = null;
>    //声明属性
>    this.callbacks = [];
>    //保存实例对象的 this 的值
>    //resolve 函数
>    let resolve = (data) => {
>      //判断状态
>      if (this.PromiseState !== 'pending') return;
>      //1. 修改对象的状态 (promiseState)
>      this.PromiseState = 'fulfilled'; // resolved
>      //2. 设置对象结果值 (promiseResult)
>      this.PromiseResult = data;
>      //调用成功的回调函数
>      setTimeout(() => {
>        this.callbacks.forEach(item => {
>          item.onResolved(data);
>        });
>      });
>    }
>    //reject 函数
>    let reject = (data) => {
>      //判断状态
>      if (this.PromiseState !== 'pending') return;
>      //1. 修改对象的状态 (promiseState)
>      this.PromiseState = 'rejected'; // 
>      //2. 设置对象结果值 (promiseResult)
>      this.PromiseResult = data;
>      //执行失败的回调
>      setTimeout(() => {
>        this.callbacks.forEach(item => {
>          item.onRejected(data);
>        });
>      });
>    }
>    try {
>      //同步调用『执行器函数』
>      executor(resolve, reject);
>    } catch (e) {
>      //修改 promise 对象状态为『失败』
>      reject(e);
>    }
>  }
> 
>  //then 方法封装
>  then(onResolved, onRejected) {
>    //判断回调函数参数
>    if (typeof onRejected !== 'function') {
>      onRejected = reason => {
>        throw reason;
>      }
>    }
>    if (typeof onResolved !== 'function') {
>      onResolved = value => value;
>      //value => { return value};
>    }
>    return new Promise((resolve, reject) => {
>      //封装函数
>      let callback = (type) => {
>        try {
>          //获取回调函数的执行结果
>          let result = type(this.PromiseResult);
>          //判断
>          if (result instanceof Promise) {
>            //如果是 Promise 类型的对象
>            result.then(v => {
>              resolve(v);
>            }, r => {
>              reject(r);
>            })
>          } else {
>            //结果的对象状态为『成功』
>            resolve(result);
>          }
>        } catch (e) {
>          reject(e);
>        }
>      }
>      //调用回调函数  PromiseState
>      if (this.PromiseState === 'fulfilled') {
>        setTimeout(() => {
>          callback(onResolved);
>        });
>      }
>      if (this.PromiseState === 'rejected') {
>        setTimeout(() => {
>          callback(onRejected);
>        });
>      }
>      //判断 pending 状态
>      if (this.PromiseState === 'pending') {
>        //保存回调函数
>        this.callbacks.push({
>          onResolved: function () {
>            callback(onResolved);
>          },
>          onRejected: function () {
>            callback(onRejected);
>          }
>        });
>      }
>    })
>  }
> 
>  //catch 方法
>  catch (onRejected) {
>    return this.then(undefined, onRejected);
>  }
> 
>  //添加 resolve 方法
>  static resolve(value) {
>    //返回promise对象
>    return new Promise((resolve, reject) => {
>      if (value instanceof Promise) {
>        value.then(v => {
>          resolve(v);
>        }, r => {
>          reject(r);
>        })
>      } else {
>        //状态设置为成功
>        resolve(value);
>      }
>    });
>  }
> 
>  //添加 reject 方法
>  static reject(reason) {
>    return new Promise((resolve, reject) => {
>      reject(reason);
>    });
>  }
> 
>  //添加 all 方法
>  static all(promises) {
>    //返回结果为promise对象
>    return new Promise((resolve, reject) => {
>      //声明变量
>      let count = 0;
>      let arr = [];
>      //遍历
>      for (let i = 0; i < promises.length; i++) {
>        //
>        promises[i].then(v => {
>          //得知对象的状态是成功
>          //每个promise对象 都成功
>          count++;
>          //将当前promise对象成功的结果 存入到数组中
>          arr[i] = v;
>          //判断
>          if (count === promises.length) {
>            //修改状态
>            resolve(arr);
>          }
>        }, r => {
>          reject(r);
>        });
>      }
>    });
>  }
> 
>  //添加 race 方法
>  static race(promises) {
>    return new Promise((resolve, reject) => {
>      for (let i = 0; i < promises.length; i++) {
>        promises[i].then(v => {
>          //修改返回对象的状态为 『成功』
>          resolve(v);
>        }, r => {
>          //修改返回对象的状态为 『失败』
>          reject(r);
>        })
>      }
>    });
>  }
> }
> ```

> html文件调用
>
> ```
> <!DOCTYPE html>
> <html lang="en">
> 
> <head>
>  <meta charset="UTF-8">
>  <meta name="viewport" content="width=device-width, initial-scale=1.0">
>  <title>class版本封装</title>
>  <script src="./promisedemo.js"></script>
> </head>
> 
> <body>
>  <script>
>    let p1 = new Promise((resolve, reject) => {
>      setTimeout(() => {
>        // resolve("OK");
>        reject("Erosssr");
>      })
>    });
> 
>    p1.then(value => {
>      console.log(value);
>    }, reason => {
>      console.warn(reason);
>    });
> 
>    console.log(Promise.resolve('OK'));
>  </script>
> </body>
> 
> </html>
> ```

## V-补充

### 1. 整体结构框架

```javascript
/**
 * 自定义Promise函数模块：IIFE
 */
(function (window) {
   
  const PENDING = 'pending'
  const RESOLVED = 'fulfilled'
  const REJECTED = 'rejected'

  /**
   * Promise构造函数
   * @param {function} executor 执行器函数（同步执行）(resolve, reject) => {}
   */
  function Promise(executor) {
   
  
  }

  /**
   * Promise原型对象then方法 
   * 指定成功和失败的回调函数
   * @param {function} onResolved 成功的回调函数(value) => {}
   * @param {function} onRejected 失败的回调函数(reason) => {}
   * @returns 一个新的promise对象结果由onResolved/onRejected执行的结果决定
   */
  Promise.prototype.then = function (onResolved, onRejected) {
   
    
  }

  /**
   * Promise原型对象catch方法
   * 指定失败的回调函数
   * @param {function} onRejected 失败的回调函数(reason) => {}
   * @returns 一个新的promise对象
   */
  Promise.prototype.catch = function (onRejected) {
   
    
  }

  /**
   * Promise函数对象resolve方法
   * @param {*} value 成功的值
   * @returns 一个成功/失败的promise
   */
   Promise.resolve = function (value) {
   

  }

  /**
   * Promise函数对象reject方法
   * @param {*} resaon 失败的原因
   * @returns 一个失败的promise
   */
  Promise.reject = function (resaon) {
   

  }

  /**
   * Promise函数对象all方法
   * @param {Array<Promise>} promises 
   * @returns 一个promise，只有当所有promise都成功时才成功，否则只要有一个失败就失败
   */
  Promise.all = function (promises) {
   

  }

  /**
   *Promise函数对象race方法
   * @param {Array<Promise>} promises 
   * @returns 返回 一个promise，其结果由第一个完成的promise决定
   */
  Promise.race = function (promises) {
   

  }


  // 向外暴露Promise函数
  window.Promise = Promise
})(window)
```

### 2. Promise(executor)

- Promise构造函数
- executor: 执行器函数（同步执行）

```javascript
/**
 * Promise构造函数
 * @param {*} executor 执行器函数（同步执行）(resolve, reject) => {}
 */
function Promise(executor) {
   

  const self = this; // 保存当前实例对象的this的值
  // 添加属性
  self.PromiseState = PENDING // 给promise对象指定status属性，初始值为pending
  self.PromiseResult = null // 给promise对象指定一个用于存储结果数据的属性
  self.callbacks = [] // 存的是对象 每个元素的结构：{onResolved() {}, onRejected() {}}

  /**
   * executor内部定义成功时调用的函数
   * @param {*} value 成功的值
   * @returns 
   */
  function resolve(value) {
   
    // 如果当前状态不是pending，直接结束
    if (self.PromiseState !== PENDING) return
    // 1. 修改对象的状态（promiseState）为 fulfilled
    self.PromiseState = RESOLVED 
    // 2. 设置对象结果值（promiseResult）为 value
    self.PromiseResult = value
    // 如果有待执行的callback函数，立即【异步】执行回调函数onResolved
    if (self.callbacks.length > 0) {
   
      setTimeout(() => {
    // 放入队列中执行所有成功的回调
        self.callbacks.forEach(callbacksObj => {
   
          callbacksObj.onResolved(value)
        })
      }, 0)
    }
  }
  
  /**
   * executor内部定义失败时调用的函数
   * @param {*} reason 失败的原因
   * @returns 
   */
  function reject(reason) {
   
    // 如果当前状态不是pending，直接结束
    if (self.PromiseState !== PENDING) return
    // 1. 修改对象的状态（promiseState）为 rejected
    self.PromiseState = REJECTED
    // 2. 设置对象结果值（promiseResult）为 reason
    self.PromiseResult = reason
    // 如果有待执行的callback函数，立即【异步】执行回调函数onRejected
    if (self.callbacks.length > 0) {
   
      setTimeout(() => {
    // 放入队列中执行所有失败的回调
        self.callbacks.forEach(callbacksObj => {
   
          callbacksObj.onRejected(reason)
        })
      }, 0)
    }
  }
  
  // 立即【同步】执行executor函数
  try {
   
    executor(resolve, reject)
  } catch(error) {
    // 如果执行器抛出异常，promise对象变成rejected状态
    reject(error)
  }
}
```

### 3. Promise.prototype.then

```javascript
Promise.prototype.then = function (onResolved, onRejected) {
   
  const self = this
  return new Promise((resolve, reject) => {
   
    if (self.PromiseState === PENDING) {
    // 假如当前状态还是pending状态，将回调函数保存起来
      self.callbacks.push({
   
        onResolved(value) {
   
          // onResolved(self.PromiseResult)
          try {
   
            const result = onResolved(self.PromiseResult) // 执行成功的回调 result接收返回值
            if (result instanceof Promise) {
    // 3. 如果回调函数返回的是promise
              // result.then(
```

# Promise+ async + await

> ##### 1)Promise==>异步
>
> ##### 2)await==>异步转同步
>
> 1. await 可以理解为是 async wait 的简写。await 必须出现在 async 函数内部，不能单独使用。
> 2. await 后面可以跟任何的JS 表达式。虽然说 await 可以等很多类型的东西，但是它最主要的意图是用来等待 Promise 对象的状态被 resolved。如果await的是 promise对象会造成异步函数停止执行并且等待 promise 的解决,如果等的是正常的表达式则立即执行
>
> ##### 3)async==>同步转异步
>
> 1. 方法体内部的某个表达式使用await修饰，那么这个方法体所属方法必须要用async修饰所以使用awit方法会自动升级为异步方法
>
> ###### 4)mdn文档
>
> 1. [async](https://gitee.com/link?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FJavaScript%2FReference%2FStatements%2Fasync_function)
> 2. [await](https://gitee.com/link?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FJavaScript%2FReference%2FOperators%2Fawait)

## async函数

> 1. 函数的返回值为 promise 对象
> 2. promise 对象的结果由 async 函数执行的返回值决定
> 3. 和then的返回规则一样

```js
async function main(){
//1. 如果返回值是一个非Promise类型的数据
// return 521;
//2. 如果返回的是一个Promise对象
 // return new Promise((resolve, reject) => {
    // resolve('OK');
    reject('Error');
// });
//3. 抛出异常throw."ohNo"
}

let result = main();
console.log(result);  //失败 Error
```

返回一个promise对象

![image-20231221112326243](Promise%E6%95%99%E7%A8%8B.assets/image-20231221112326243.png)

## await表达式

> 1. await 右侧的表达式一般为 promise 对象, 但也可以是其它的值
> 2. 如果表达式是 promise 对象, await 返回的是 promise 成功的值
> 3. 如果表达式是其它值, 直接将此值作为 await 的返回值

```js
async function main(){

    let p = new Promise((resolve, reject) => [
        // resolve('OK');
        reject('Error');
    }
	//1. 右侧为promise的情况
    // let res = await p;
    //console.log(res)  //ok
    //2。右侧为其他类型的数据
    // let res2 = await 20;
    //console.log(res)  //20
    //3。如果promise是失败的状态
    try{
    	let res3 = await p;
    }catch(e){
    	console.log(e);  //Error
	}
}

let result = main();
```

## 注意

> 1. await 必须写在 async 函数中, 但 async 函数中可以没有 await
> 2. 如果 await 的 promise 失败了, 就会抛出异常, 需要通过 try...catch 捕获处理

## 例子1

```js
//使用回调函数的方式
fs.readFile('./resource/1.html'，(err,data1) =>{
	if(err) throw err;
	fs.readFile('./resource/2.html',(err,data2)=>{
    	if(err) throw err;
		fs.readFile('./resource/3.html',(err,data3) => {
            if(err) throw err;
			console.log(data1+data2+data3);
        })
     })
})
```

```js
//使用async 与 await

const fs = require('fs');
const util = require('util');
const mineReadFile = util.promisify(fs.readFile); //可以将函数自动封装成promise,不用再去手动封装

async function main(){
    //读取第一个文件的内容
    try{
        let data1 = await mineReadFile('./resource/1.html'):
        let data2 = await mineReadFile('./resource/2.html'):
        let data3 = await mineReadFile('./resource/3.html'):
        console.log(data1 + data2 + data3);
    }catch(e){
        console.log(e); 
    }
}
```

## 例子2

```js
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>

  <body>
    <div>
      <input
        type="button"
        name="qx"
        id="qx"
        value="保存"
        class="button"
        onclick="saveData(1)"
      />
    </div>
    <button id="btn" type="button">这是一个按钮</button>
  </body>
  <script>
    function saveData(status) {
      if (
        status == "1" &&
        !confirm("您是否确认提交当前评价,提交后不能修改！")
      ) {
      } else {
        console.log("提交");
        document.getElementById("Form1").submit();
      }
    }

    /*
      可复用的发 ajax 请求的函数: xhr + promise
      */
    function promiseAjax(url) {
      return new Promise((resolve, reject) => {
        // 1、创建对象
        const xhr = new XMLHttpRequest();
        // 2、初始化
        xhr.open("GET", url);
        // 3、发送
        xhr.send();
        // 4、事件绑定，处理服务器端返回的结果
        xhr.onreadystatechange = function () {
          // 服务端返回所有结果
          if (xhr.readyState === 4) {
            // 2xx 成功
            if (xhr.status >= 200 && xhr.status < 300) {
                //console.log( xhr.response)
              resolve(JSON.parse(xhr.response)); // 设置响应体类型 xhr.responseType = 'json';则可以自动转换不需要JSON.parse()
            } else {
              // 请求失败, 调用 reject(reason)
              reject(new Error("请求失败: status: " + status));
            }
          }
        };
      });
    }

    let btn = document.querySelector("#btn");
    btn.addEventListener("click", async function () {
      try {
        /*
        curl -X 'GET' \
        'https://api.apiopen.top/api/sentences' \
        -H 'accept: application/json'
        */
        const responseRsult = await promiseAjax(          // 返回promise 成功的值
        "https://api.apiopen.top/api/sentences"
        );
        console.log(responseRsult); // 在控制台打印获取到的数据
        console.log(responseRsult.result.name);
      } catch (error) {
        console.error("发生错误:", error);
      }
    });
  </script>
</html>
```

![image-20231221121924073](Promise%E6%95%99%E7%A8%8B.assets/image-20231221121924073.png)

## 如何在Promise外部使用Promise的结果

> 用到的本章节知识:
>
> 1、axios本质上就是一个promise,所以下面用定时器+Promise模拟axios,效果一样,可以将`new Promise(resolve => {setTimeout(function() { resolve("promise普通结果"); }, 1000); })`等价于`axios({})`
>
> 2、resolve() 与reject()是修改Promise状态并往外抛出的,一个Promise只能改变一次状态,所以一个primise中只能调用一次
>
> 3、 上一步抛出后可以在下面 的.then()中获取到
>
> Ⅰ-如果没有用.then(),则值会抛往Promise外部
>
> Ⅱ-如果声明了.then(),则值会被.then()接住,放到里面处理,如果需要再次抛出--`某些业务场景需要` ,然后在下一个then()或者外部使用, 则可以 .then(v=>return v) ---前提这个链式调用前曾使用过resolve() 与reject()才用return,不然就用这两个resolve() 与reject()
>
> ```
> //讲解时写的简单demo
> let resolveCommon = ()=> {
>  let result="普通promise初始值"
>   result=new Promise(resolve => {setTimeout(function() { resolve("promise普通结果"); }, 1000); })
>  console.log(result)
>  //打印结果: Promise { <pending> } 
> };
> let resolveAsync=async ()=> {
>  let result="await+async的promise初始值"
>   result=await new Promise(resolve => { setTimeout(function() { resolve("这是async+await结果"); }, 1000);})
>  console.log(result)
>  //打印结果: 这是async+await结果  这里就是正确的值,你可以在下一步进行正常使用,也可以用在下一步的promise中
>  //------------------------------------------------------
>  //在第二个promise中调用使用
>  let result2=""
>  result2= await new Promise(resolve => { setTimeout(function() { resolve(result+"+经过第二个promise加工"); }, 1000);})
>  .then(v=>{
>    console.log("第二个promise的then()中打印并返回:",v)
>    return v+",经过then()加工返回"
>  })
>  console.log("最终结果:第二个promise外部结果打印,",result2)
>  //---------------------------------------------
> };
> resolveCommon()  //调用普通promise函数
> resolveAsync()    //调用await+async
> /**
> 运行结果
> 1.resolveCommon() 运行结果:    Promise { <pending> }
> 2.resolveAsync() 运行结果:     
>  这是async+await结果
>  第二个promise的then()中打印并返回: 这是async+await结果+经过第二个promise加工
>  最终结果:第二个promise外部结果打印, 这是async+await结果+经过第二个promise加工,经过then()加工返回
> */
> ```
>
> 原因解析:
>
> 1. new Promise()是一个异步任务,会加到异步队列中,而正常运行比如console.log()是同步运行的(即从上往下运行),会加到同步队列
>
> 所以 Promise()通常是会在同一等级的同步任务之后才得到结果的 所以你得到的是一个挂起的 Promise { } 对象
>
> 1. 而await则是让跟在后面的异步任务转为同步任务(效果如此,就通俗来讲,具体概念需要自学),所以result就能得到一个已经修改状态为成功或者失败的值
>
> 所以下面的任务就可以使用到这个值
>
> 1. 为什么这些操作要放在同一个async fn()=>{} 中?
>
> 1)Promise==>异步
>
> 2)await==>异步转同步
>
> 1. await 可以理解为是 async wait 的简写。await 必须出现在 async 函数内部，不能单独使用。
> 2. await 后面可以跟任何的JS 表达式。虽然说 await 可以等很多类型的东西，但是它最主要的意图是用来等待 Promise 对象的状态被 resolved。如果await的是 promise对象会造成异步函数停止执行并且等待 promise 的解决,如果等的是正常的表达式则立即执行
>
> 3)async==>同步转异步
>
> 方法体内部的某个表达式使用await修饰，那么这个方法体所属方法必须要用async修饰所以使用awit方法会自动升级为异步方法

# 四、宏任务与微任务

## Ⅰ-说明

> 原理图:
>
> ![Promise系统学习_宏任务微任务原理图](Promise%E6%95%99%E7%A8%8B.assets/Promise%E7%B3%BB%E7%BB%9F%E5%AD%A6%E4%B9%A0_%E5%AE%8F%E4%BB%BB%E5%8A%A1%E5%BE%AE%E4%BB%BB%E5%8A%A1%E5%8E%9F%E7%90%86%E5%9B%BE.png)
>
> 说明:
>
> 1. JS中用来存储待执行回调函数的队列包含2个不同特定的列队
>    - `宏队列`:用来保存待执行的宏任务(回调),比如:`定时器`回调/ajax回调/dom事件回调
>    - `微队列`:用来保存待执行的微任务(回调),比如:`Promise`的回调/muntation回调
> 2. JS执行时会区别这2个队列:
>    - JS执行引擎首先必须执行所有的`初始化同步任务`代码
>    - 每次准备取出第一个`宏任务执行前`,都要将所有的`微任务`一个一个取出来执行

## Ⅱ-代码与示例

> 你需要一些栗子来帮助验证自己的想法是否正确,尽量先不看结果去自己思考下打印结果顺序

### 1、代码示例:

#### a) 首先给出注释的栗子举一个

> 此处会给出每个打印放入什么队列,加深你的印象
>
> ```
> setTimeout(() => { 
>       console.log('timeout callback1（）')//立即放入宏队列
>       Promise.resolve(3).then(
>         value => { 
>           console.log('Promise onResolved3()', value)//当这个宏任务执行后 立马放入微队列,所以这个微任务执行完后下个宏任务才能执行 
>         }
>       )
>     }, 0)
> 
>     setTimeout(() => { 
>       console.log('timeout callback2（）') //立即放入宏队列,
>     }, 0)
> 
>     Promise.resolve(1).then(
>       value => { 
>         console.log('Promise onResolved1()', value)//立即放入微队列
>         setTimeout(() => {
>           console.log('timeout callback3（）', value) //立即放入宏任务
>         }, 0)
>       }
>     )
> 
>     Promise.resolve(2).then(
>       value => { 
>         console.log('Promise onResolved2()', value)//立即放入微队列
>       }
>     )
> console.log('同步代码') //同步代码立即执行
> ```
>
> 结果放在下方,就怕你不小心瞄到

#### b) 尝试自己思考下

> 尝试自己脑海中用自己理解 '运行' 一下, 然后把结果写下来,再去下面结果做对比
>
> ```
> setTimeout(() => console.log('代码开始执行'),0)
> new Promise((resolve,reject) => {
>  console.log('开始for循环');
>  for(let i=0;i<10000;i++){
>    i == 99 && resolve()
>  }
> }).then(() => console.log('执行then函数'))
> console.log('代码执行结束');
> ```

### 2、示例结果:

#### a) 第一个栗子的结果

> ```
> '同步代码',
>  'Promise onResolved1()',
>  'Promise onResolved2()',
>  'timeout callback1（）',
>  'Promise onResolved3()',
>  'timeout callback2（）',
>  'timeout callback3（）'
> ```

#### b) 第二个栗子的结果

> ![image-20210827094130354](Promise%E6%95%99%E7%A8%8B.assets/image-20210827094130354.png)
>
> PS: 可以忽略`undefined`这个打印结果, 因为这会加重我们对于宏任务与微任务的理解负担.
>
> 当然人都是会好奇的,没有打破砂锅问到底的精神呢也当不了一个好程序员,那我就在下方额外给出解释

# 五、对浏览器console控制台输出undefined的分析

## Ⅰ- 出现场景

> ![image-20210827095144833](Promise%E6%95%99%E7%A8%8B.assets/image-20210827095144833.png)

## Ⅱ - 尝试输入其他内容进行分析

> ![image-20210827095702736](Promise%E6%95%99%E7%A8%8B.assets/image-20210827095702736.png)
>
> 那么做个合理推测: 应该是在控制台输入的内容,它的 `返回值` 会显示出来,这让我们不禁想到JS的 [ **eval()** ]

## Ⅲ - eval（string）

> 其作用是将 接收的 string 字符串作为参数，对其进行JavaScript 表达式或语句 计算，返回得到的值；
>
> 如果是没有返回值的表达式或语句，则会返回 undefined ；
>
> 如果没有合法的表达式和语句，则会抛出 SyntaxError 异常 。
>
> 于是我们可以猜测Console控制台的实质 就是 调用了eval（）函数

## Ⅳ - 验证一下

> ![image-20210827100156570](Promise%E6%95%99%E7%A8%8B.assets/image-20210827100156570.png)
>
> 大家都是聪明人,看到这个结果应该就不用我组织语言来解释了吧

## Ⅴ - 分析其在宏任务与微任务的打印顺序

> 首先看图:![image-20210827094130354](Promise%E6%95%99%E7%A8%8B.assets/image-20210827094130354.png)
>
> 可以看到 [ undefined ] 实在微任务完成后,宏任务执行前 打印