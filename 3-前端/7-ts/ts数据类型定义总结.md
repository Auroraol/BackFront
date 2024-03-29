## ts数据类型

| 数据类型 | 实例            | 描述                           |
| -------- | --------------- | ------------------------------ |
| number   | 1,2,3,-1,-2,-3  | 任意的数字                     |
| string   | ‘hello’,‘你好’  | 任意的字符串                   |
| boolean  | true,false      | 布尔类型                       |
| array    | [1, 2],         | 数组类型                       |
| 字面量   | 本身            | 限制变量的值就是该字面量的值   |
| any      | ***             | 任意类型                       |
| unknown  | ***             | 类型安全的any                  |
| void     | 空值(undefined) | 没有值(或undefined)            |
| never    | 没有值          | 不能是任何值                   |
| object   | {name:'张三}    | 任意的JS对象                   |
| tuple    | [1,.2]          | 元素，TS新增类型，固定长度数组 |
| enum     | enum{A, B)      | 枚举，TS中新增类型             |

[TypeScript](https://so.csdn.net/so/search?q=TypeScript&spm=1001.2101.3001.7020)支持与JavaScript几乎相同的数据类型，此外还提供了实用的枚举类型方便我们使用。

## 1、数值类型 number

```javascript
	let val1:number; 
	val1 = 123;
```

## 2、布尔类型 boolean

```javascript
	let val2:boolean;
	val2 = true;
```

## 3、字符串类型 string

```javascript
	let val3:string;
	val3 = "123";
	val3 = `val1=${val1}, val2==${val2}`;
```

## 4、数组类型 array

①　方式一

```javascript
	let arr1:Array<number>; // 表示定义了一个名称叫做arr1的数组, 这个数组中将来只能够存储数值类型的数据
	arr1 = [1, 3, 5];
```

②　方式二

```javascript
	let arr2:string[]; // 表示定义了一个名称叫做arr2的数组, 这个数组中将来只能够存储字符串类型的数据
	arr2 = ['a', 'b', 'c'];
```

③　方式三：联合类型

```javascript
	let arr3:(number | string)[];// 表示定义了一个名称叫做arr3的数组, 这个数组中将来既可以存储数值类型的数据, 也可以存储字符串类型的数据
	arr3 = [1, 'b', 2, 'c'];
```

④　方式四：任意类型

```javascript
	let arr4:any[]; // 表示定义了一个名称叫做arr4的数组, 这个数组中将来可以存储任意类型的数据
	arr4 = [1, 'b', false];
```

## 5、[元祖](https://so.csdn.net/so/search?q=元祖&spm=1001.2101.3001.7020)类型

元祖类型其实就是数组类型的扩展，用于保存定长定数据类型的数据。

```javascript
	let arr5:[string, number, boolean]; // 表示定义了一个名称叫做arr5的元祖, 这个元祖中将来可以存储3个元素, 第一个元素必须是字符串类型, 第二个元素必须是数字类型, 第三个元素必须是布尔类型
	arr5 = ['a', 1, true];
```

## 6、[枚举类型](https://so.csdn.net/so/search?q=枚举类型&spm=1001.2101.3001.7020)

枚举类型是TS为JS扩展的一种类型,用于表示固定的几个取值在原生的JS中是没有的。

```javascript
	enum Gender{ // 定义了一个名称叫做Gender的枚举类型, 这个枚举类型的取值有两个, 分别是Male和Femal
	    Male,
	    Femal
	}
	let val:Gender; // 定义了一个名称叫做val的变量, 这个变量中只能保存Male或者Femal
	val = Gender.Male;
	val = Gender.Femal;
```

注意点: TS中的枚举底层实现的本质其实就是数值类型, 所以赋值一个数值不会报错

```javascript
	val = 666; // 不会报错
```

注意点: TS中的枚举类型的取值, 默认是从上至下从0开始递增的，
虽然默认是从0开始递增的, 但是我们也可以手动的指定枚举的取值的值。

```javascript
	console.log(Gender.Male); // 0
	console.log(Gender.Femal);// 1
```

注意点: 如果手动指定了前面枚举值的取值, 那么后面枚举值的取值会根据前面的值来递增。

```javascript
	console.log(Gender.Male); // 6
	console.log(Gender.Femal);// 7
```

注意点: 如果手动指定了后面枚举值的取值, 那么前面枚举值的取值不会受到影响。

```javascript
	console.log(Gender.Male); // 0
	console.log(Gender.Femal);// 6
```

注意点: 我们还可以同时修改多个枚举值的取值, 如果同时修改了多个, 那么修改的是什么最后就是什么

```javascript
console.log(Gender.Male); // 8
console.log(Gender.Femal);// 6
```

我们可以通过枚举值拿到它对应的数字。

```javascript
console.log(Gender.Male); // 0
```

我们还可以通过它对应的数据拿到它的枚举值。

```javascript
console.log(Gender[0]); // Male
```

## 7、any类型

any表示任意类型, 当我们不清楚某个值的具体类型的时候我们就可以使用any。
一般用于定义一些通用性比较强的变量, 或者用于保存从其它框架中获取的不确定类型的值，在TS中任何数据类型的值都可以赋值给any类型。

```javascript
	let value:any; // 定义了一个可以保存任意类型数据的变量
	value = 123;
	value = "abc";
	value = true;
	value = [1, 3, 5];
```

## 8、void类型

void与any正好相反, 表示没有任何类型, 一般用于函数返回值，在TS中只有null和undefined可以赋值给void类型。

```javascript
	function test():void {
	    console.log("hello world");
	}
	test();
```

注意点: null和undefined是所有类型的子类型, 所以我们可以将null和undefined赋值给任意类型

```javascript
	let value:void; // 定义了一个不可以保存任意类型数据的变量, 只能保存null和undefined
	// value = 123; // 报错
	// value = "abc";// 报错
	// value = true;// 报错
	// value = null; // 不会报错
	value = undefined;// 不会报错
```

## 9、Never类型

表示的是那些永不存在的值的类型，一般用于抛出异常或永远不会停止或者根本不可能有返回值的函数。

```javascript
	function demo():never {
	    throw new Error('报错了');
	}
	demo();
	
	function demo2():never {
	    while (true){}
	}
	demo2();
```

## 10、Object类型

表示一个对象。

```javascript
	let obj:object; // 定义了一个只能保存对象的变量
	obj = {name:'lnj', age:33};
```

## 11、vue3

vue3中响应数据不考虑去声明变量类型

```vue
<script setup lang="ts">
const registerInfo = reactive({
  username: "",
  password: "",
  againPassword: "", 
});

const isMobile = ref(false); 
</script>
```



# ?. 和 !

```
 config.headers!['Authorization'] = 'Bearer ' + getAccessToken();
```

1. **使用可选链操作符 ?. ，当前值有可能为空的情况下使用**
2. `!` 表示对一个变量[断言](https://so.csdn.net/so/search?q=断言&spm=1001.2101.3001.7020)其非空，当前值一定有时使用
