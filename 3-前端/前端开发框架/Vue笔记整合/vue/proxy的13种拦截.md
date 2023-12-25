# proxy

## 引言

Vue采用什么数据劫持

1. vue2 Object defineProperty
2. vue3 Proxy

取代的原因

```
vue2中Object.defineProperty监听不到改组8个'push'
this.arr[9]='123'
解决方式是：Vue.Set()或this.Sset()
```

Proxy:  缺点IE9以下都不兼容  Edge12+
Object.defineProperty: 缺点IE8以下都不兼容

Vue 3 : Proxy劫持数据:   1.数据劫持 ⒉.观察者 3.数据解析{} 指令 js对象 diff算法 ——>dom

## 使用

**参考的是MDN文档**https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Proxy

### Proxy 使用

```js
proxy = new Proxy({}, {
  apply: Reflect.apply,
  construct: Reflect.construct,
  defineProperty: Reflect.defineProperty,
  getOwnPropertyDescriptor: Reflect.getOwnPropertyDescriptor,
  deleteProperty: Reflect.deleteProperty,
  getPrototypeOf: Reflect.getPrototypeOf,
  setPrototypeOf: Reflect.setPrototypeOf,
  isExtensible: Reflect.isExtensible,
  preventExtensions: Reflect.preventExtensions,
  get: Reflect.get,
  set: Reflect.set,
  has: Reflect.has,
  ownKeys: Reflect.ownKeys,
});
```

例子

```js
const obj = {
	height: 1.88,
}

// 1.创建一个Proxy对象
/*
参数1: 目标
参数2: 具体操作
*/
const objProxy = new Proxy(obj, {
    /*
    参数1: 原对象
    参数2: key(原对象属性)
    参数3: 新值
    */
	set(target, key, newValue) {
		console.log(`监听: 监听${key}的设置值: `, newValue)
		// 如果这里不跟目标对象做联动赋值 其改变代理对象不会影响被代理对象
		// target[key] = newValue
		return true
	},
	get(target, key) {
		// console.log(`监听: 监听${key}的获取`)
		return 1
	},
})

obj.age = 122
objProxy.age = 1555
console.log(objProxy.age) // 1
console.log(obj.age) // 122
obj.zzzz = 1000
console.log(objProxy.zzzz) // 1
```

### set

```js
//1.
const p = new Proxy(target, {
  set: function(target, property, value, receiver) {
  }
});
/*
 1.1.target– 是目标对象，作为第一个参数传递给的对象new Proxy，
 1.2.property– 属性名称，
 1.3.value- 设置的值
 1.4.receiver– 设置值的this指向，receiver就是this在它的调用中使用的对象。通常这是proxy对象本身
*/

//2.通过下面案例可以发现 代理陷阱种 set方法需要有返回值，如果是true则表示赋值成功，Reflect.set 接受的参数和代理陷阱中set 参数一样，而且返回值是boolean 其实proxy 的代理陷阱和 reflect 是配套

new Proxy(numbers, {
    // (*)
    set(target, prop, val) {
        // to intercept property writing
        if (typeof val == 'number') {
            return Reflect.set(target, prop, val) // Reflect.set(...arguments);
        } else {
            return false
        }
    },
})
```

### get

```
拦截是获取属性值其
```

```js
let numbers = [0, 1, 2]

numbers = new Proxy(numbers, {
    /*
    1.拦截是获取属性值
     1.1.访问属性: proxy[foo]和 proxy.bar
     1.2.Reflect.get()
    */
    get(target, prop, receiver) {
        if (prop in target) {
            return Reflect.get(target, prop, receiver) // Reflect.get(...arguments);
        } else {
            return 0 // default value
        }
    },
})

console.log(numbers[1]) // 1
console.log(numbers[100]) // 0
```

### has

```
has 陷阱拦截 是in ，但是不是for in ，像下面案例只拦截是Proxy 对象
```

```js
var obj = { a: 10 }
var p = new Proxy(obj, {
    has: function (target, key) {
        if (key === 'a') return false
        return Reflect.set(target, key)
    },
})

console.log('a' in p) // false
console.log('a' in obj) // true

for (let key in p) { 
    console.log(key) // a
}
```

### deleteProperty

```
deleteProperty 拦截 是 delete
```

```js
var obj = { a: 10, b: 10 }
var p = new Proxy(obj, {
    deleteProperty: function (target, key) {
        if (key === 'a') return false
        return Reflect.deleteProperty(target, key)
    },
})

// 因为设置deleteProperty 所以不能删除
delete p.a
console.log(p) // { a: 10, b: 10 }
console.log(obj) // { a: 10, b: 10 }

// 只是给proxy 做了设置因此目标对象还是可以删除
delete obj.a
console.log(p) // { b: 10 }
console.log(obj) // { b: 10 }
```

### getPrototypeOf

```
1.是对'[[GetPrototypeOf]]' 的拦截，其中有五种形式都可以对其触发
  1.1.Object.getPrototypeOf()
  1.2.Reflect.getPrototypeOf()
  1.3.__proto__
  1.4.Object.prototype.isPrototypeOf()
  1.5.instanceof
```

```js
var obj = {};
var p = new Proxy(obj, {
    getPrototypeOf(target) {
        return Array.prototype;
    }
});

console.log(
    Object.getPrototypeOf(p) === Array.prototype,  // true
    Reflect.getPrototypeOf(p) === Array.prototype, // true
    p.__proto__ === Array.prototype,               // true
    Array.prototype.isPrototypeOf(p),              // true
    p instanceof Array                             // true
);
```

### setPrototypeOf

```
1.可以拦截方法
 1.1.Object.setPrototypeOf()
 1.2.Reflect.setPrototypeOf()
2.可以用了禁止更改对象的原型
```

```js
var handlerReturnsFalse = {
    setPrototypeOf(target, newProto) {
        return false;
    }
};

var newProto = {};
var target = {};

var p1 = new Proxy(target, handlerReturnsFalse);
Object.setPrototypeOf(p1, newProto); // throws a TypeError
Reflect.setPrototypeOf(p1, newProto); // returns false
```

## 例子

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>贪吃蛇</title>
  </head>
  <body>
   <div>
      <input type="text" id="input">
      <p id="show"></p>
    </div>
    <script>
        let obj={};
        const input = document.getElementById("input");
        const show = document.getElementById("show");
        
        //设置代理
        let newobj = new Proxy(obj, {
            set(target，key，value) {
                //vue3里面此段代码采用发布订阅的模式传递到订阅者/调用模板解析 diff对比改变更新
                if (key === 'text') {
                    input.value = value;
                    show.innerHTML = value;
                }
                //this.subtribe.emit("")
                return Reflect.set(target, key, value);

            },
            get(target，key) {
                return Reflect.get(target, key);
            )
        })
            
        // 输入框绑定事件
        input.addEventListener("keyup", function (e){
            newobj.text = e.target.value;
        })
	</script>
  </body>
</html>
```

![image-20231219193021343](proxy%E7%9A%8413%E7%A7%8D%E6%8B%A6%E6%88%AA.assets/image-20231219193021343.png)

## 总结

针对数据的拦截:   get   set   has   deleteProperty
遍历 ownKeys
属性描述器  getOwnPropertyDescriptor  defineProperty 设置修饰器

# Reflect

```js
//1.Object.getOwnPropertyNames(Reflect) 获取到'Reflect' 上的属性，因为这些属性都是不可枚举的因此没有使用'Object.keys' 来获取，一共获取了13个静态方法
['defineProperty', 'deleteProperty', 'apply', 'construct', 'get', 'getOwnPropertyDescriptor', 'getPrototypeOf', 'has', 'isExtensible', 'ownKeys', 'preventExtensions', 'set', 'setPrototypeOf']

//2.十三个静态方法具体使用规则
Reflect.apply(target, thisArg, args)
Reflect.construct(target, args)
Reflect.get(target, name, receiver)
Reflect.set(target, name, value, receiver)
Reflect.defineProperty(target, name, desc)
Reflect.deleteProperty(target, name)
Reflect.has(target, name)
Reflect.ownKeys(target)
Reflect.isExtensible(target)
Reflect.preventExtensions(target)
Reflect.getOwnPropertyDescriptor(target, name)
Reflect.getPrototypeOf(target)
Reflect.setPrototypeOf(target, prototype)
```

参考 [Reflect ](https://www.kancloud.cn/cyyspring/more/2666837)





