# Vue.js命名风格指南

## 命名分类

现在常用的vue命名规范无外乎四种：

- `camelCase`(驼峰式 )
- `kebab-case`(短横线连接式)
- `PascalCase`(帕斯卡命名式)
- `Snake`(下划线连接式)

## 文件夹命名

如果你展开 `node_modules` 中的项目依赖，你会发现，几乎所有的项目文件夹命名都是 `kebab-case` 命名的，使用`kebab-case`命名的文件夹比`camelCase`命名的文件夹看起来更清晰。

属于components文件夹下的子文件夹，也统一使用 `kebab-case` 的风格。

## 组件命名

1、自定义组件名必须是**多个单词组合**的，并且是**完整的单词**而不是单词的缩写。

```js
// 错误
components/
|- sd-settings.vue
|- u-prof-opts.vue

// 正确
components/
|- student-dashboard-settings.vue
|- user-profile-options.vue
```

(**推荐**)这里全部使用`kebab-case`格式，主要是后面很多会使用到`kebab-case`格式，方便记忆。

2、应用特定样式和约定的**基础组件** (也就是展示类的、无逻辑的或无状态的组件) 应该全部以一个特定的前缀开头，比如 `Base`、`App` 或 `V`。而且一般放在`全局注册`，因为会被频繁使用。

```js
// 错误
components/
|- MyButton.vue
|- VueTable.vue
|- Icon.vue

// 正确
components/
|- BaseButton.vue
|- BaseTable.vue
|- BaseIcon.vue
```

3、组件名中的**单词顺序**

组件名应该以高级别的 (通常是一般化描述的) 单词开头，以**描述性的修饰词结尾**。

```js
// 错误
components/
|- ClearSearchButton.vue
|- RunSearchButton.vue
|- SearchInput.vue

// 正确
components/
|- SearchButtonClear.vue
|- SearchButtonRun.vue
|- SearchInputQuery.vue
```

4、在JS中的组件名大小写

也就是在注册组件的时候，全部使用 `PascalCase` 格式。

```js
import MyComponent from './my-component.vue'

export default {
  name: 'MyComponent',
  components:{MyComponent}
}
```

5、html模板中的组件命名

(**推荐**)不管是单标签还是双标签，全部使用 `kebab-case` 格式，主要是为了方便。

```html
<!--全部使用kebab-case格式-->
<my-component />
<my-component></my-component>
```

6、prop名称的大小写

在子组件html中传入prop的为`kebab-case`格式，子组件接收方采用 `camelCase` 格式。

```js
// 错误
<welcome-message greetingText="hi"/>
    
props: {
  'greeting-text': String
}

// 正确
<welcome-message greeting-text="hi"/>
    
props: {
  greetingText: String
}
```

7、组件事件命名

统一使用 `kebab-case` 格式，并且以`动词`结尾。

```js
// 正确
this.$emit('dom-resize');
this.$emit('api-load');
```

## 命名总结

1、采用`kebab-case`命名的：

- 文件夹
- 单文件组件
- **组件在html模板<template>中使用(`<my-component></my-component>`)**
- 在模板中prop传入属性到子组件(`<my-componnet set-text="hello"/>`)
- 所有事件名(`this.$emit('api-reload')`)
- **使用组件    <user-cpn></user-cpn>**

2、采用`PascalCase`命名：

- 公共基础组件(`MfcSelect`)
- **js中components注册组件时(`import MyComponent from './my-component.vue'`)**
- 组件的name属性(`name: 'MyComponent'`)

3、采用`camelCase` 命名：

- 子组件接收prop属性

4、view路由视图  用`kebab-case`命名的

5、index.vue  统一使用小写

# 项目结构

<img src="Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240104130850947.png" alt="image-20240104130850947" style="zoom:67%;" />

命名方式种类：helloVue(Camel Case即驼峰命名法)，HelloVue（PascalCase即帕斯卡命名法）
为了以防万一，命名的时候统一使用帕斯卡命名法
但是在使用的时候统一使用 kebab-case命名方法

**view里面可以创建 components里面写view的具体 实现**

![image-20240303235557690](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240303235557690.png)

**components文件交 主要写公共功能**

![image-20240303235607989](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240303235607989.png)





或者

view就是视图

components文件写视图的具体实现





![image-20240330155838029](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240330155838029.png)



![image-20240330155859793](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240330155859793.png)



![image-20240330155944527](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240330155944527.png)



![image-20240330160022005](Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240330160022005.png)



**kebab-case:**

+ 路由
+ 组件使用
+ 标签 class="xxx-yyy"

**小驼峰:**

+ 方法
+ 属性
+ index.vue

**大驼峰:**

+ 组件

**双引号:**

+ 标签 class="xxx-yyy"

**单引号:**

+ `<script></script>`中
