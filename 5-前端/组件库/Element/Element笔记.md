# Element的Container布局容器布满全屏

官方代码运行后效果：

![img](Element%E7%AC%94%E8%AE%B0.assets/792795-20191016113114665-551574879-17007541382132.png)



实现不满全屏：

1、需要给包裹的div一个height：100%

2、给#app,html,body,.el-container一个height：100%

3、给el-container设置direction="vertical"，因为包含main和footer

```vue
<template>
<div style="height: 100%">
  .......  
    
</div>
</template>
    
<style type="text/css">
/*
	找到html标签、body标签，和挂载的标签
	都给他们统一设置样式
*/
  html,body,#app,.el-container{
        /*设置内部填充为0，几个布局元素之间没有间距*/
        padding: 0px;
         /*外部间距也是如此设置*/
        margin: 0px;
        /*统一设置高度为100%*/
        height: 100%;
    }
</style>
```

实现之后的效果图如图：

![img](Element%E7%AC%94%E8%AE%B0.assets/792795-20191016113129326-1013135056.png)



