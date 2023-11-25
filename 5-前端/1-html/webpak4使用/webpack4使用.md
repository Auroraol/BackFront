# 安装

**本地安装：**

安装最新版本或特定版本：

```cmd
npm install --save-dev webpack
npm install --save-dev webpack@<version>
```

使用 webpack v4+ 版本，还需要安装 CLI

```cmd
npm instal1 --save-dev webpack-cli@3.3.12
```

**全局安装：**

通过以下NPM安装方式,可以使webpack在全局环境下可用:

```cmd
npm install --global webpack@4.0.0
```

**初始化项目:**

首先我们创建一个目录，初始化 npm，安装 webpack-cli （此工具用于在命令行中运行 webpack） ：

```cmd
mkdir webpack-demo
cd webpack-demo
npm init -y
npm install webpack@4.0.4 webpack-cli@3.3.12 --save-dev
```

