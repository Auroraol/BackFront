```
GO111MODULE='on'
GONOPROXY='*.xiaoduoai.com'
GONOSUMDB='*.xiaoduoai.com'
GOPRIVATE='*.xiaoduoai.com,*.xiaoduotech.com'
GOPROXY='https://goproxy.cn,https://mirrors.aliyun.com/goproxy/,direct'
```

# **Go Modules**

go module 是go官方自带的go项目依赖管理工具（类似于Java中的maven）。

Go通过Go Modules的依赖管理统一了Go生态中众多的第三方的依赖管理。　

```
go moudle取代旧的基于GOPATH方法来指定在工程中使用哪些源文件或导入包 。使用module时，GOPATH 不再用于解析导入。但是，它仍然用于存储下载的源代码（在 GOPATH/pkg/mod 中）和编译的命令（在 GOPATH/bin 中）。

go module可以将某个项目(文件夹)下的所有依赖整理成一个 go.mod 文件,里面写入了依赖的版本等

使用go module之后我们可不用将代码放置在src下了
```

**目前在Go中存在两种开发模式，GOPATH mode 和 Go Modules mode。**

1. 在Go Modules之前，Go开发中的依赖管理使用GOPATH开发模式。在GOPATH开发模式中，Go命令使用GOPATH环境变量来实现以下几个功能：

　　　　　　1）go install 命令安装二进制库到 $GOBIN，其默认路径为 $GOPATH/bin

　　　　　　2）go install 命令安装编译好的包到 $GOPATH/pkg/ 中。　　

　　　　　　　　例如将example.com/y/z安装到$GOPATH/pkg/example.com/y/z.a

　　　　　　3）go get 命令下载源码包到$GOPATH/src/ 中。　　

　　　　　　　　例如将example.com/y/z下载到$GOPATH/src/example

<img src="GoLand%20%E4%BD%BF%E7%94%A8.assets/1238257-20210804164431877-475409516.png" alt="img" style="zoom: 50%;" />

2. **在使用Go Modules 开发模式中，GOPATH变量的用处**： (目前主流用法)

　　　　　　1）go install 命令安装二进制到 $GOBIN目录，其默认位置为$GOPATH/bin （与GOPATH开发模式相同）

　　　　　　2）go get命令缓存下载的Modules到$GOMODCACHE目录，默认位置为$GOPATH/pkg/mod

　　　　　　3）go get命令缓存下载的checksum数据到$GOPATH/pkg/sumdb目录　　

## **go mod 命令**

| 命令            | 作用                             |
| --------------- | -------------------------------- |
| go mod init     | 生成 go.mod 文件                 |
| go mod download | 下载 go.mod 文件中指明的所有依赖 |
| go mod tidy     | 整理现有的依赖                   |
| go mod graph    | 查看现有的依赖结构               |
| go mod edit     | 编辑 go.mod 文件                 |
| go mod vendor   | 导出项目所有的依赖到 vendor 目录 |
| go mod verify   | 校验一个模块是否被篡改过         |
| go mod why      | 查看为什么需要依赖某模块         |

 **GOPROXY**

　　　　这个环境变量主要是用于设置 Go 模块代理（Go module proxy），其作用是用于使 Go 在后续拉取模块版本时能够脱离传统的 VCS 方式，直接通过镜像站点来快速拉取

　　　　GOPROXY 的默认值是：https://proxy.golang.org,direct，这有一个很严重的问题，就是 proxy.golang.org 在国内是无法访问的，因此这会直接卡住你的第一步，所以你必须在开启 Go modules 的时，同时设置国内的 Go 模块代理，执行如下命令：

```
 go env -w GOPROXY=https://goproxy.cn,direct
```

　　　　GoLand配置GOPROXY：

![img](GoLand%20%E4%BD%BF%E7%94%A8.assets/1238257-20211209164914354-1938833651.png)

```
module go_study

go 1.16

require (
    github.com/gin-gonic/gin v1.7.7
    github.com/go-redis/redis/v8 v8.11.4
    github.com/gomodule/redigo v1.8.6
)　　　　
```

　　　　说明：

　　　　　　module：用于定义当前项目的模块路径

　　　　　　go：用于标识当前模块的Go语言版本，值为初始化模块时的版本，目前来看还只是个标识作用

　　　　　　require：用于设置一个特定的模块版本

　　　　　　exclude：用于从使用中排除一个特定的模块版本

　　　　　　replace：用于将一个模块版本替换为另外一个模块版本

**go build 和 go install：**

　　　**区别：**

　　　　go build：编译包及其依赖项，但不安装包，在项目目录下生成可执行文件（有main包）

　　　　go install：编译并安装包

　　　　　　1）编译生成可执行文件（有main包），将可执行文件放到 bin 目录（$GOPATH/bin）

　　　　　　2）安装包文件（无main包），将编译后的包文件放到 pkg 目录下（$GOPATH/pkg）。

　　　　相同点：

　　　　　　都会生成可执行文件

　　　　不同点：

　　　　　　go build不能生成包文件，go install可以生成包文件

　　　　　　go build生成的可执行文件在当前目录下，go install生成的可执行文件在bin目录下（$GOPATH/bin）

　　　　1、go build

　　　　　　在编译包时，会忽略以 '_test.go' 结尾的文件

　　　　　　命令使用： go build [-o 输出目录] [build flags] [packages]

　　　　　　-o 强制构建的可执行文件输出到指定的输出文件或目录

　　　　　　build flags：

| build flag | 作用                                                         |
| ---------- | ------------------------------------------------------------ |
| -a         | force rebuilding of packages that are already up-to-date     |
| -n         | print the commands but do not run them.                      |
| -p n       | the number of programs, such as build commands or test binaries, that can be run in parallel. The default is the number of CPUs available. |
| -race      | enable data race detection. Supported only on linux/amd64, freebsd/amd64, darwin/amd64, windows/amd64, linux/ppc64le and linux/arm64 (only for 48-bit VMA). |
| -mod mode  | module download mode to use: readonly, vendor, or mod. By default, if a vendor directory is present and the go version in go.mod is 1.14 or higher, the go command acts as if -mod=vendor were set. Otherwise, the go command acts as if -mod=readonly were set. See https://golang.org/ref/mod#build-commands for details. |

　　　　2、go install

　　　　　go install注意：

　　　　　　将 Go 安装目录添加到系统的 shell 路径，这样您将能够运行程序的可执行文件，而无需指定可执行文件的位置：

　　　　　　　　On Linux or Mac, run the following command：export PATH=$PATH:/path/to/your/install/directory

　　　　　　　　On Windows, run the following command：set PATH=%PATH%;C:\path\to\your\install\directory

　　　　　　作为替代方案，如果你的 shell 路径中已经有一个像 $HOME/bin 这样的目录，并且你想在那里安装你的 Go 程序，你可以通过使用 go env 命令设置 GOBIN 变量来更改安装目标

　　　　　　　　go env -w GOBIN=/path/to/your/bin

　　　　　　or

　　　　　　　　go env -w GOBIN=C:\path\to\your\bin　　　　　

　　　　　　然后就可以执行 go install命令编译并安装包文件了

## go mod 引入指定的分支

方法1:

```sh
go get git地址@分支名
如：go get github.com/golang/go@master
```

分支的代码必须是合入的，如果还在评审中的，是不会拉取的

方法2:

![image-20240921172120829](GoLand%20%E4%BD%BF%E7%94%A8.assets/image-20240921172120829.png)

## 更新依赖

```sh
$ go mod tidy
  go: downloading pkg.xx.com/service/lib/db/v6 v6.0.13
$ go mod vendor
```

如果通过上面清除缓存的方式，vendor依赖仓库的内容还没有更新到最新的提交，可以在最新提交分支上重新创建一个新的tag，修改go.mod中依赖的tag为新创建的tag，再使用上面的`go mod tidy` 和 `go mod vendor`分别更新go mod和vendor依赖。
