# js中JSON详解

## 1. 语法

JSON语法支持表示3种类型的值：

- 简单值：字符串、数值、布尔值和null可以在JSON种出现，特殊值undefined不可以；
- 对象：第一种复杂数据类型，对象表示有序键/值对。每个值可以是简单值，也可以是复杂类型；
- 数组：第二种复杂数据类型，数组表示可以通过数值索引访问的值的有序列表。数组的值可以是任意类型，包括简单值、对象，甚至其他数组。

### 1.1 简单值

最简单的 JSON 可以是一个数值。例如，下面这个字符串也是有效的 JSON： “Hello World!”

注意： JavaScript 字符串与 JSON 字符串的主要区别是，JSON 字符串必须使用双引号（单引号会导致语法错误）。

### 1.2 对象

对象使用与 JavaScript 对象[字面量](https://so.csdn.net/so/search?q=字面量&spm=1001.2101.3001.7020)略为不同的方式表示。以下是 JavaScript 中的对象字面量：

```javascript
const person = {
	name: "lc",
	age: 20
};
```

虽然这对 JavaScript 开发者来说是标准的对象字面量，但 JSON 中的对象必须使用双引号把属性名包围起来，下面的代码与前面的代码是一样的：

```javascript
const obj = {
	"name": "lc",
	"age": 20
};
```

而用 JSON 表示相同的对象的语法是：

```javascript
{
	"name": "lc",
	"age": 20
}
```

与 JavaScript 对象字面量相比，JSON 主要有两处不同:

1. 没有变量声明（JSON 中没有变量）；
2. 最后没有分号（不需要，因为不是 JavaScript 语句）。

属性的值可以是简单值或复杂数据类型值，后者可以在对象中再嵌入对象：

```javascript
{
	"name": "lc",
	"age": 20,
	"school": {
		"name": "kk",
		"location": "上海"
	}
}
```

### 1.3 数组

JSON 的第二种复杂数据类型是数组。数组在 JSON 中使用 JavaScript 的数组字面量形式表示。例如，以下是一个 JavaScript 数组：

```javascript
const arr = [25, "hi", true];
```

在 JSON 中可以使用类似语法表示相同的数组：

```javascript
[25, "hi", true]
```

同样，这里没有变量，也没有分号。数组和对象可以组合使用，以表示更加复杂的数据结构，比如：

```javascript
[
	{ 
		 "name": "lc", 
		 "authors": [ 
			 "Nicholas C. Zakas", 
			 "Matt Frisbie" 
		 ], 
		 "edition": 4, 
		 "year": 2017 
	 },
 	{ 
		 "name": "wmj", 
		 "authors": [ 
			 "Nicholas C. Zakas"
		 ], 
		 "edition": 3, 
		 "year": 2011 
	 }
]
```

## 2. 解析与序列化

> JSON 的迅速流行并不仅仅因为其语法与 JavaScript 类似，很大程度上还因为 JSON 可以直接被解析成可用的 JavaScript 对象。

#### 2.1 JSON对象

JSON对象有两个方法：

- stringify()：将js序列化为JSON字符串；
- parse()：将JSON解析为js值。

JSON.stringify()把一个 JavaScript 对象序列化为一个 JSON 字符串：

```javascript
const book = {
	title: "Professional JavaScript",
	authors:[
		"Nicholas C. Zakas", 
 		"Matt Frisbie"
	],
	edition: 4,
	year: 2017
};

let jsonText = JSON.stringify(book);
```

默认情况下，JSON.stringify()会输出不包含空格或缩进的 JSON 字符串，jsonText 的值是这样的：

```javascript
{"title":"Professional JavaScript","authors":["Nicholas C. Zakas","Matt Frisbie"], "edition":4,"year":2017}
```

注意：在序列化 JavaScript 对象时，所有函数和原型成员都会有意地在结果中省略。此外，值为 undefined的任何属性也会被跳过。最终得到的就是所有实例属性均为有效 JSON 数据类型的表示。

JSON 字符串可以直接传给 JSON.parse()，然后得到相应的 JavaScript 值：

```javascript
let bookCopy = JSON.parse(jsonText);
// bookCopy的数据格式和book一样
```

注意：book 和 bookCopy 是两个完全不同的对象，没有任何关系。但是它们拥有相同的属性和值。如果给 JSON.parse()传入的 JSON 字符串无效，则会导致抛出错误。

#### 2.2 序列化选项

JSON.stringify()方法除了要序列化的对象，还可以接收两个参数。这两个参数可以用于指定其他序列化 JavaScript 对象的方式：

- 第一个参数是过滤器，可以是数组或函数；
- 第二个参数是用于缩进结果 JSON 字符串的选项。

**1. 过滤结果**
如果第二个参数是一个数组，那么JSON.stringify()返回的结果只会包含该数组中列出的对象属性：

```javascript
const book = { 
	title: "Professional JavaScript", 
	authors: [ 
		"Nicholas C. Zakas", 
		"Matt Frisbie" 
	], 
	edition: 4, 
	year: 2017 
};

let jsonText = JSON.stringify(book, ["title", "edition"]);

// jsonText: {"title":"Professional JavaScript","edition":4}
```

如果第二个参数是一个函数，则行为又有不同。提供的函数接收两个参数：属性名（key）和属性
值（value）。可以根据这个 key 决定要对相应属性执行什么操作。这个 key 始终是字符串，只是在值不属于某个键/值对时会是空字符串（注意，返回 undefined 会导致属性被忽略。）：

```javascript
const book = { 
	title: "Professional JavaScript", 
	authors: [ 
		"Nicholas C. Zakas", 
		"Matt Frisbie" 
	], 
	edition: 4, 
	year: 2017 
};

let jsonText = JSON.stringify(book, (key, value) => {
    switch (key) {
        case "authors":
            return value.join(",");
        case "year":
            return 5000;
        case "edition":
            return undefined;
        default:
            return value;
    }
});

// 结果如下
{"title":"Professional JavaScript","authors":"Nicholas C. Zakas,Matt Frisbie","year":5000}

```

**2. 字符串缩进**
JSON.stringify()方法的第三个参数控制缩进和空格。在这个参数是数值时，表示每一级缩进的
空格数。例如，每级缩进 4 个空格，可以这样：

```javascript
const book = { 
	title: "Professional JavaScript", 
	authors: [ 
		"Nicholas C. Zakas", 
		"Matt Frisbie" 
	], 
	edition: 4, 
	year: 2017 
};

let jsonText = JSON.stringify(book, null, 4);
// jsonText 格式如下
{ 
    "title": "Professional JavaScript", 
    "authors": [ 
        "Nicholas C. Zakas", 
        "Matt Frisbie" 
    ], 
    "edition": 4, 
    "year": 2017 
}
```

注意，除了缩进，JSON.stringify()方法还为方便阅读插入了换行符。这个行为对于所有有效的缩进参数都会发生。最大缩进值为 10，大于 10 的值会自动设置为 10。

如果缩进参数是一个字符串而非数值，那么 JSON 字符串中就会使用这个字符串而不是空格来缩进：

```javascript
const book = { 
	title: "Professional JavaScript", 
	authors: [ 
		"Nicholas C. Zakas", 
		"Matt Frisbie" 
	], 
	edition: 4, 
	year: 2017 
};

let jsonText = JSON.stringify(book, null, "--");
// 结果如下
{ 
--"title": "Professional JavaScript", 
--"authors": [ 
----"Nicholas C. Zakas", 
----"Matt Frisbie" 
--], 
--"edition": 4, 
--"year": 2017 
}
```

使用字符串时同样有 10 个字符的长度限制。如果字符串长度超过 10，则会在第 10 个字符处截断。

#### 2.3 解析选项

> JSON.parse()方法也可以接收一个额外的参数，是一个函数，该函数也接收两个参数，属性名（key）和属性值（value），另外也
> 需要返回值。

如果此函数返回 undefined，则结果中就会删除相应的键。如果返回了其他任何值，则该值就
会成为相应键的值插入到结果中。该函数经常被用于把日期字符串转换为 Date 对象。例如：

```javascript
    const book = { 
	title: "Professional JavaScript", 
	authors: [ 
		"Nicholas C. Zakas", 
		"Matt Frisbie" 
	], 
	edition: 4, 
	year: 2017,
	releaseDate: new Date(2022, 4, 3)
};

let jsonText = JSON.stringify(book);
let bookCopy = JSON.parse(jsonText, (key, value) => key == "releaseDate" ? new Date(value) : value);
console.log(bookCopy.releaseDate.getFullYear()); // 2022
```





# Object.keys（）的详解和用法

[Object.keys（）的详解和用法 - 掘金 (juejin.cn)](https://juejin.cn/post/7195082863094530105)
