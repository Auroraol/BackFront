# nodejs 版本管理工具 nvmw

[execjs](https://so.csdn.net/so/search?q=execjs&spm=1001.2101.3001.7020) 库只支持 nodejs 14.0.0 以上的版本，win7 最高只支持 node 13.14.0 版本

nvmw：Windows [nodejs](https://so.csdn.net/so/search?q=nodejs&spm=1001.2101.3001.7020) 版本管理工具，方便进行版本切换及安装删除

nvmw 的安装：npm install -g nvmw，自行下载链接：https://github.com/TimothyGu/nvmw

安装完成后输出 nvmw 查看是否安装成功：

![img](node%E4%BD%BF%E7%94%A8.assets/7757659dad3f43a793ccf2a518a0a263.png)

Options 中提供了查看 nvmw 版本和帮助信息的方式（nvmw -V，nvmw -h），Commands 中是 nvmw 相关的方：

- install <version>  安装指定版本的 node
- uninstall <version> 卸载指定版本的 node
- use <version>    在当前终端使用指定版本的 node
- deactivate      在当前终端撤销 nvmw 相关操作
- switch <version>   将指定版本的 node 设置为默认，即在任意终端生效
- switch-deactivate  永久撤销 nvmw 相关操作
- ls          查看安装的所有 node 版本
- ls-remote      查看所有可安装的版本
- cleanup       下载后会有个 cache 缓存文件夹，该命令为清除缓存
- help [command]    展示所有 nvmw 相关命令

Examples 即红框内为官方示例：

- nvmw install v0.10.20：通过 nvmw 下载版本为 0.10.20 的 nodejs
- nvmw uninstall v0.10.20：通过 nvmw 卸载版本为 0.10.20 的 nodejs
- nvmw use v0.10.20：通过 nvmw 切换到版本为 0.10.20 的 nodejs

查看 Node.js 全部版本：https://nodejs.org/zh-cn/download/releases/，可以查看对应的 V8 环境和 npm 的版本，更新的还挺频繁的，选择一个需要的版本，cmd 中通过 nvmw 进行下载，要注意的是 nvmw 只支持 4.5.0 以上版本的 node 版本下载与切换：

![img](node%E4%BD%BF%E7%94%A8.assets/ba5b731b041a4aa9b9ef37c6a5c214ca.png)

当然 nvmw ls-remote 也可以查看所有可安装的 nodejs 版本，下载前可设置相关镜像提升下载速度，当然直接下载感觉也不是很慢：

```javascript
set "NVMW_NODEJS_ORG_MIRROR=http://npm.taobao.org/mirrors/node"



set "NVMW_IOJS_ORG_MIRROR=http://npm.taobao.org/mirrors/iojs"



set "NVMW_NPM_MIRROR=http://npm.taobao.org/mirrors/npm"
```

使用的基本步骤如下，例如所需版本为 16.17.1：

1. nvmw install v16.17.1
2. nvmw use v16.17.1

以上两步即可完成 nodejs 的版本切换，不过 nvmw use 只是在当前终端切换了版本，关闭即失效，nvmw switch 可永久切换，在任意终端生效，推荐使用 switch，nvmw ls 可以查看当前所有的 nodejs 版本，system version 即 * 后的部分为当前 nodejs 的版本：

![img](node%E4%BD%BF%E7%94%A8.assets/fbdf82b441494a20864ae433788f9b64.png)

注意：使用 nvmw use/switch 前需要将 nvwm 所在文件夹的路径添加到环境变量 Path 中，默认安装路径为：C:\Users\Administrator\nvmw，添加后需要重启 cmd 再输入 node -v 会发现切换成功：

![img](node%E4%BD%BF%E7%94%A8.assets/af84ac63a1854dcca9271dadd2717bc9.png)

手动切换版本更稳定，不过同样终端关闭即失效：

```javascript
// PATH=nvmw 下载的 nodejs 版本路径，有的 admin 为 Administrator



set "PATH=C:\Users\admin\nvmw\nodejs\v16.17.1;%PATH%"
```

system version (v16.15.1)：当前版本为 16.15.1，以下成功完成切换： 

![img](node%E4%BD%BF%E7%94%A8.assets/2c0bf9f8ec4940d7a2ed40e92f2a1444.png)

如果使用 nvmw 之前就有一个 node 版本，用 nvmw 下载第二个 node 版本的时候会把原本就有的版本删掉，例如一开始有 16.16.0，用 nvmw 下了 16.17.0 后又下了个 16.17.1，16.16.0 就没了，但不是说只能有两个版本的 node，只是 nvmw 覆盖了初始的 

想下最新版本还是建议直接去官网，感觉这个工具切换并不稳定，毕竟也很多年没有更新过了：

[Download | Node.js](https://nodejs.org/en/download/)

![img](node%E4%BD%BF%E7%94%A8.assets/715a1117aa314bd4a87902966c420a26.png)







安装的nvmw，今天想要用nvmw切换时，居然给我报错了：

![img](node%E4%BD%BF%E7%94%A8.assets/dad146097c44462b990dfb492b850915.png)

 然后我就走上了使用[nvm](https://so.csdn.net/so/search?q=nvm&spm=1001.2101.3001.7020)替换nvmw之路。。

## 1.安装

[nvm-windows下载](https://github.com/coreybutler/nvm-windows) [下载release版](https://github.com/coreybutler/nvm-windows/releases) 中Assets中的包，window10，我是下载nvm-setup.zip再点击里面的文件，直接安装：

![img](node%E4%BD%BF%E7%94%A8.assets/a4900d7e759843d1ab9093e1f9fbdb1e.png)

 先同意协议:

![img](node%E4%BD%BF%E7%94%A8.assets/1b3ac6a3202c4bc9bc24c04306f5977e.png)

 

next 配置nvm的安装位置，任意一个你喜欢的位置：

![img](node%E4%BD%BF%E7%94%A8.assets/9efddb9efbda4478bbe971a79153ed9a.png)

 

next 设置node的symlink文件夹位置,**这个文件夹的名字一定不能含有中文或空格:**

![img](node%E4%BD%BF%E7%94%A8.assets/560c7ac2509e4925a4e14b595ec0ff60.png)

 ![img](node%E4%BD%BF%E7%94%A8.assets/19653ff90e6349f28c3cdf5fe6454237.png)

 

next 如果在安装nvm之前，电脑上就已经安装有node的，会看到如下图，询问你是否用nvm管理已经存在的[node版本](https://so.csdn.net/so/search?q=node版本&spm=1001.2101.3001.7020)。一定要选‘是’，这个弹窗可能会出现好几次，都点是:

![img](node%E4%BD%BF%E7%94%A8.assets/85174e9fc93b4fdbb98ab4fc02984c03.png)

![img](node%E4%BD%BF%E7%94%A8.assets/4432f710ef824a40aa689ca90ec72bfa.png)

##  2.验证是否可以成功使用

右键使用管理员权限打开一个命令行：（nvm在切换node版本时需要系统的管理员权限，所以需要以管理员身份打开命令行窗口）

![img](node%E4%BD%BF%E7%94%A8.assets/508d725e5e5f4aa68f119e6e5564e9fd.png)

 

输入nvm v，会显示nvm的版本号：

![img](node%E4%BD%BF%E7%94%A8.assets/5d7b0065386d4da5ade4f238cd56f157.png)

查看环境变量：我的电脑->右键选择【属性】->左侧选择【高级系统设置】->右下角【环境变量】->如图：

![img](node%E4%BD%BF%E7%94%A8.assets/7d67528c0035463788ce028b2e0c9734.png)

 

 

可以改变远程下载[nodejs](https://so.csdn.net/so/search?q=nodejs&spm=1001.2101.3001.7020)的地址源为淘宝镜像： 

```bash
nvm node_mirror https://npm.taobao.org/mirrors/node/



nvm npm_mirror https://npm.taobao.org/mirrors/npm/
```

 结果：![img](node%E4%BD%BF%E7%94%A8.assets/d958c3901e234ca19ed21d09becf66a7.png)

 

##  3.下载node版本：

```bash
nvm install v18.16.0
```

结果：

![img](node%E4%BD%BF%E7%94%A8.assets/af8a1a532c3348c189f77a8ca0fc778b.png)

##  

## 4.查看node版本：

```bash
nvm list
```

结果：

![img](node%E4%BD%BF%E7%94%A8.assets/4ecca4b493474da1856b105a9afc00ce.png)



##  5.切换node版本：

```bash
nvm use 11.3.0
```

好吧，失败：

![](node%E4%BD%BF%E7%94%A8.assets/1fc9509be83f400db3545c6ba9887e20.png)

 