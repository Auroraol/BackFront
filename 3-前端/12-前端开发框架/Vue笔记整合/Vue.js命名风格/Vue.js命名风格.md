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

2、采用`PascalCase`命名：

- 公共基础组件(`MfcSelect`)
- **js中components注册组件时(`import MyComponent from './my-component.vue'`)**
- 组件的name属性(`name: 'MyComponent'`)

3、采用`camelCase` 命名：

- 子组件接收prop属性

<img src="Vue.js%E5%91%BD%E5%90%8D%E9%A3%8E%E6%A0%BC.assets/image-20240104130850947.png" alt="image-20240104130850947" style="zoom:67%;" />