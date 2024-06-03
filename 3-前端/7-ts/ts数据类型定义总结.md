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

### 常用方法

1. **map**: 对数组的每个元素执行提供的函数，并返回一个新数组，其中包含每次函数调用的结果。

```ts
const numbers = [1, 2, 3, 4, 5];
const doubled = numbers.map(num => num * 2);
console.log(doubled); // 输出: [2, 4, 6, 8, 10]
```

1. **filter**: 使用提供的函数测试所有元素，并创建一个包含所有通过测试的元素的新数组。

```ts
const numbers = [1, 2, 3, 4, 5];
const evenNumbers = numbers.filter(num => num % 2 === 0);
console.log(evenNumbers); // 输出: [2, 4]
```

1. **reduce**: 对数组中的所有元素执行一个提供的函数，并将其结果累积到单个输出。

```ts
const numbers = [1, 2, 3, 4, 5];
const sum = numbers.reduce((acc, curr) => acc + curr, 0);
console.log(sum); // 输出: 15
```

1. **find**: 返回数组中满足提供的测试函数的第一个元素的值。

```ts
const numbers = [1, 2, 3, 4, 5];
const found = numbers.find(num => num > 3);
console.log(found); // 输出: 4
```

1. **forEach**: 对数组的每个元素执行提供的函数。

```ts
const numbers = [1, 2, 3, 4, 5];
numbers.forEach(num => console.log(num));
// 输出:
// 1
// 2
// 3
// 4
// 5
```

1. **some**: `some` 方法测试数组中是否至少有一个元素通过了由提供的函数实现的测试。如果有至少一个元素通过了测试，则返回 `true`，否则返回 `false`。

```
const numbers = [1, 2, 3, 4, 5];
const hasEvenNumber = numbers.some(num => num % 2 === 0);
console.log(hasEvenNumber); // 输出: true
```

1. **every**: `every` 方法测试数组中的所有元素是否都通过了由提供的函数实现的测试。如果所有元素都通过了测试，则返回 `true`，否则返回 `false`。

```
const numbers = [2, 4, 6, 8, 10];
const allEvenNumbers = numbers.every(num => num % 2 === 0);
console.log(allEvenNumbers); // 输出: true
```

1. **sort**: `sort` 方法用于对数组的元素进行排序，并返回排序后的数组。默认情况下，`sort` 方法会将元素转换为字符串，然后按照 Unicode 码点的顺序进行排序。

```
const numbers = [3, 1, 5, 2, 4];
const sortedNumbers = numbers.sort((a, b) => a - b);
console.log(sortedNumbers); // 输出: [1, 2, 3, 4, 5]
```

1. **find**:`find()` 方法来检查对象数组中是否包含特定值的示例：

```
// 检查是否存在 'admin' 角色
var isAdmin = userInfo.roles.find(role => role === 'admin');

if (isAdmin) {
    // 包含 'admin' 角色
} else {
    // 不包含 'admin' 角色
}
```

1. **push()**: 将一个或多个元素添加到数组的末尾，并返回新数组的长度。

   ```
   javascriptCopy Codeconst array = [1, 2, 3];
   array.push(4); // [1, 2, 3, 4]
   ```

2. **pop()**: 移除数组的最后一个元素，并返回该元素的值。

   ```
   javascriptCopy Codeconst array = [1, 2, 3];
   const poppedElement = array.pop(); // 3
   ```

3. **shift()**: 移除数组的第一个元素，并返回该元素的值。

   ```
   javascriptCopy Codeconst array = [1, 2, 3];
   const shiftedElement = array.shift(); // 1
   ```

4. **unshift()**: 在数组的开头添加一个或多个元素，并返回新数组的长度。

   ```
   javascriptCopy Codeconst array = [2, 3];
   array.unshift(1); // [1, 2, 3]
   ```

1. **splice()**: 从数组中添加或移除元素。

   ```
   javascriptCopy Codeconst array = [1, 2, 3, 4, 5];
   array.splice(2, 1); // 移除索引为 2 的元素，[1, 2, 4, 5]
   array.splice(2, 0, 3); // 在索引为 2 的位置添加元素 3，[1, 2, 3, 4, 5]
   ```

2. **slice()**: 返回数组的一部分，不修改原数组。

   ```
   javascriptCopy Codeconst array = [1, 2, 3, 4, 5];
   const slicedArray = array.slice(1, 3); // [2, 3]
   ```

3. **concat()**: 合并两个或多个数组，并返回新数组。

   ```
   javascriptCopy Codeconst array1 = [1, 2];
   const array2 = [3, 4];
   const concatenatedArray = array1.concat(array2); // [1, 2, 3, 4]
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

**vue3中响应数据不考虑去声明变量类型, 如果写不出来也不考虑**

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

**<span>注意: 在vue3 + ts 中的v-if语句中也可以使用</span>**

```html
<div class="btns-bar">
    <el-popover
      v-if="userInfo && (userInfo.id === comment.fromUser.id || 
      userInfo.roles?.includes('admin'))"
      placement="bottom"
 >
```

# 类型转换

1. 尖括号语法：

```
let someValue: any = "this is a string";
let strLength: number = (<string>someValue).length;
```

1. as 语法：

```
let someValue: any = "this is a string";
let strLength: number = (someValue as string).length;
```

**上述一般不用, 用以下方法**

```ts
1.number数据类型：页面刷新后，其类型会转换为`string`类型。那么我们在路由刷新页面，对传递过来的属性值做一次`Number()`转换，就是不管页面有没有刷新都做一次`Number()`转换；

2.string数据类型：页面刷新后，其类型依然为`string`类型，无需任何操作；

3.boolean数据类型：页面刷新后，其类型会转换为`string`类型。那么我们在路由刷新页面，对传递过来的属性值做一次`Boolean()`转换，就是不管页面有没有刷新都做一次`Boolean()`转换；

4.undefined数据类型：页面刷新后，其类型依然为`undefined`类型，无需任何操作；

5.null数据类型：页面刷新后，其类型依然为`null`类型，无需任何操作；

6.object数据类型：页面刷新后，其类型会转换为`string`类型。那么我们在路由跳转传参页面对属性值做一次`JSON.stringify()`预处理，然后在路由刷新页面对该值进行`JSON.parse()`转换即可；
```

## 核心作用

## 定义

![image-20240601174515020](ts%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B%E5%AE%9A%E4%B9%89%E6%80%BB%E7%BB%93.assets/image-20240601174515020.png)

```ts
interface Chat {
    id: number,
    room: number,
    data: ChatInfo
}

interface ChatInfo {
    id?: number,
    avatar: string,
    name: string,
    content: string,
    date: Date
}

type ChatUserInfo = Omit<ChatInfo, "id" | "content" | "date">
```

## 使用

可以直接使用, 无需导入

```ts
import { defineStore } from "pinia";

export const useChatStore = defineStore("chat", {
  state: () => ({
    // 当前房间号
    room: undefined as number | undefined,
    // 当前用户信息
    chatUserInfo: undefined as ChatUserInfo | undefined,
  }),
  actions: {
    // 判断登记的信息是否为空
    isNull() {
      return !(this.chatUserInfo?.name && this.chatUserInfo?.avatar);
    },
    updateRoom(n: number) {
      this.room = n;
    },
    updateChatUserInfo(data: ChatUserInfo) {
      this.chatUserInfo = data;
    },
  },
});

```

# 接口与类型别名

## 1.相对于对象

```typescript
// interface
interface Point {
  x: number;
  y: number;
}

// type
type Point = {
  x: number;
  y: number;
};
```

## 2.相对于函数

```typescript
// interface
interface SetPoint {
  (x: number, y: number): void;
}

// type
type SetPoint = (x: number, y: number) => void;
```

## 3.相对于类

```typescript
// interface
interface Point {
  x: number;
  y: number;
}

class SomePoint implements Point {
  x: 1;
  y: 2;
}

// type
type Point2 = {
  x: number;
  y: number;
};

class SomePoint2 implements Point2 {
  x: 1;
  y: 2;
}
```

## 4.相对于其他类型

与接口不同，类型别名还可以用于其他类型，如基本类型（原始值）、联合类型、元组。

```typescript
// primitive 基本类型（原始值)
type Name = string;


// union 联合类型
type PartialPoint = PartialPointX | PartialPointY;

// tuple 元组
type Data = [number, string];

// dom 节点
let div = document.createElement('div');
type B = typeof div;
```

## 5.相对于扩展（extends）

- interface extends interface

```typescript
interface Name { 
  name: string; 
}
interface User extends Name { 
  age: number; 
}
123456
```

- type extends type

```typescript
type Name = { 
  name: string; 
}
type User = Name & { age: number  };
```

- interface extends type

```typescript
type Name = { 
  name: string; 
}
interface User extends Name { 
  age: number; 
}
```

- type extends interface

```typescript
interface Name { 
  name: string; 
}
type User = Name & { 
  age: number; 
}
```

## 6.相对于同名合并

interface 能够声明合并

```typescript
interface User {
  name: string
  age: number
}

interface User {
  sex: string
}

/*
User 接口为 {
  name: string
  age: number
  sex: string 
}
*/
```

## 7.相对于计算属性

type 能使用 in 关键字生成映射类型，但 interface 不行。

语法与索引签名的语法类型，内部使用了 for … in。 具有三个部分：

- 类型变量 K，它会依次绑定到每个属性。
- 字符串字面量联合的 Keys，它包含了要迭代的属性名的集合。
- 属性的结果类型。

```typescript
type Keys = "firstName" | "secondName"

type DudeType = {
  [key in Keys]: string
}

const test: DudeType = {
  firstName: "前端收割机",
  secondName: "alan"
}

// error!
interface DudeType2 {
  [key in keys]: string
}
```

在很多场景下，interface 和 type都能使用，因此两者在很多时候会被混淆：

- 类型：对象、函数两者都适用，但是 type 可以用于基础类型、联合类型、元祖等。
- 同名合并：interface 支持，type 不支持。
- 计算属性：type 支持, interface 不支持。

## 8.外部文件中使用

如果你在外部文件中使用 `ArticleRequestData` 类型时遇到了找不到名称的问题，可能是因为模块导入的方式不正确。请确保按照以下步骤操作：

在 `chat.ts` 文件中定义 `ArticleRequestData` 类型，并导出它：

```
export type ArticleRequestData = {
    // ... （ArticleRequestData 的定义）
}
```

在需要使用 `ArticleRequestData` 类型的外部文件中，使用正确的路径和方式导入它：

```
import { ArticleRequestData } from './chat';

// 现在可以在 externalFile.ts 中使用 ArticleRequestData 类型
```

## 使用建议

1、官方推荐使用 interface，其他无法满足需求的情况下用 type。但是因为**联合类型**和**交叉类型**是比较常用的，所以避免不了大量使用 type 的场景，一些复杂类型也需要通过组装后形成类型别名来使用。

2、如果想保持代码统一，还是可选择使用 type。通过上面的对比，type 其实可涵盖 interface 的大部分场景。



# vue中使用ts

[项目中使用了vue3+ts总结经验-CSDN博客](https://blog.csdn.net/jiurikeai/article/details/134260158)
