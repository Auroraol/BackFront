# go-zero rbac

#### 介绍
使用jwt进行前端鉴权，redis缓存相关jwt数据。
目前只完成rbbac及微信后台对接等相关业务逻辑，其他业务逻辑还未写
#### 软件架构

├── admin 后端管理前端文件\
│   ├── Dockerfile\
│   ├── LICENSE\
│   ├── README.md\
│   ├── babel.config.js\
│   ├── nginx.conf\
│   ├── package-lock.json\
│   ├── package.json\
│   ├── postcss.config.js\
│   ├── public\
│   ├── src\
│   ├── vue.config.js\
│   ├── yarn-error.log\
│   └── yarn.lock\
├── admin-api 后端交互api业务接口\
│   ├── Dockerfile\
│   ├── admin.go\
│   ├── assets\
│   ├── doc\
│   ├── etc\
│   └── internal\
├── go.mod\
├── go.sum\
├── rpc rpc相关业务\
│   ├── app rbac微服务\
│   ├── model model层\
│   ├── sys 系统层\
│   └── wechat 微信sdk相关业务\
├── server.shell etcd安装\
├── utils 工具类\
│   ├── cache\
│   ├── common\
│   ├── encryption\
│   ├── filter\
│   ├── gconv\
│   ├── prefix\
│   ├── sdk\
│   └── upload\
└── wechat-api 微信api相关业务接口\
    ├── doc\
    ├── etc\
    ├── internal\
    └── wechat.go

#### 安装教程

1.  使用server.shell相关命令安装etcd
2.  配置rpc下app、sys、wechat下etc数据库链接及redis链接
3.  依次启动app、sys、wechat、admin-api、wechat-api服务

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
