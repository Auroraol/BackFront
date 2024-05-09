> 官方配置文档：[l2dwidget.js.org/docs/class/…](https://link.juejin.cn/?target=https%3A%2F%2Fl2dwidget.js.org%2Fdocs%2Fclass%2Fsrc%2Findex.js~L2Dwidget.html%23instance-method-init)

> 第一步先去下载插件素材：[github.com/xiazeyu/liv…](https://link.juejin.cn/?target=https%3A%2F%2Fgithub.com%2Fxiazeyu%2Flive2d-widget-models) 第二，将packge里面的静态资源放到你的vue项目的static下

# 博客的吉祥物

| **技术栈**                               | **官网** `or` **github**                       |
| ---------------------------------------- | ---------------------------------------------- |
| pixi.js ^6.4.2                           | https://pixijs.com/                            |
| pixi-live2d-display^0.4.0                | https://github.com/guansss/pixi-live2d-display |
| Cubism SDK ps:2或4版本根据自己的模型选择 | https://www.live2d.com/                        |

开源项目pixi-live2d-display：https://github.com/guansss/pixi-live2d-display
中文文档：https://github.com/guansss/pixi-live2d-display/blob/master/README.zh.md
API手册：https://guansss.github.io/pixi-live2d-display/api/index.html



```
npm install pixi.js@6.4.2
npm install pixi-live2d-display
```

# Live2D官方SDK

下载地址 :https://www.live2d.com/en/sdk/about/  

前端SDK需对应web选项

![image-20240507200556980](live2d%E6%8F%92%E4%BB%B6.assets/image-20240507200556980.png)

下载解压后得到 CubismSdkForWeb-4/Core/live2dcubismcore.min.js







剩下的两个依赖因为没有npm包所以只能在项目的index.html文件中导入
同时也要注意将`live2dcubismcore.js.map`这个文件放在和`live2dcubismcore.min.js`同级目录下

```HTML
    <script src="./src/library/live2d.min.js"></script>
    <script src="./src/library/live2dcubismcore.min.js"></script>
```

###### 三、模型的引入

我这边是直接在app.vue文件上挂载了。不想在app引入的话可以自己封装成一个组件

