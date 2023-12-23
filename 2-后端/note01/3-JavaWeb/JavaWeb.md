# 1、基本概念

## 1.1 基本概念

1. 静态web：html，css（提供给所有人看的数据，始终不会发生变化）
2. 动态web：Servlet/JSP，ASP，PHP  (淘宝等几乎所有的网站)
   （提供给所有人看的数据，始终会发生变化，每个人在不同时间，不同地方看到的都不同）

## 1.2 web应用程序

web应用程序：可以提供浏览器访问的程序

- a.html、b.html等多个web资源，这些web资源可以被外界访问，对外界提供服务。
- 你们能访问到的任何一个页面或者资源，都存在于这个世界的某一个角落的计算机上。
- URL：统一资源定位符。
- 这个统一的web资源会被放在同一个文件夹下，web应用程序—>Tomcat：服务器。
- **web应用程序编写完毕后，若想提供给外界访问：需要一个服务器来统一管理。**
- 一个web应用由多部分组成 (静态web，动态web)
  + html，SS，is
  + isp，servlet
  + Java程序
  + jar包
  + 配置文件 (Properties)

## 1.3 静态web

*.htm，*.html，这些都是网页的后缀，如果服务器上一直存在这些东西，我们就可以直接进行读取。

![img](JavaWeb.assets/kuangstudy6ab7a752-fa17-431e-be07-163b8865e4b1.jpg)

静态web存在的缺点:

+ Web页面无法动态更新，所有用户看到都是同一个页面
  轮播图，点击特效:伪动态
  +  JavaScript [实际开发中]
  + VBScript

+ **==它无法和数据库交互(数据无法持久化，用户无法交互)==**

### 1.4 动态web

页面会动态展示：Web的页面展示的效果因人而异。
![img](JavaWeb.assets/kuangstudy5f53e972-3ea0-4539-b0de-27be95784eda.jpg)

缺点:

+ 加入服务器的动态web资源出现了错误，我们需要重新编写我们的后台程序,重新发布
+ 停机维护

优点:

+ Web页面可以动态更新，所有用户看到都不是同一个页面
+ 它可以与数据库交互（数据无法持久化，用户无法交互

#  2、web服务器

## 2.1 web服务器技术

+ ASP

+ PHP

+ **JSP/Servlet :**

  + B/S：浏览和服务器

  + C/S：客户端和服务器

    Sun公司主推的B/S架构

    基于ava语言的(所有的大公司，或者一些开源的组件，都是用Java写的

    可以承载三高问题带来的影响

    语法像ASP，ASP-->|SP，加强市场强度

**服务器是一种被动的操作，用来处理用户的一些请求和给用户一些响应信息。**

IIS:   微软的服务器

tomcat:  Tomcat服务器是一个免费的开放源代码的Web应用服务器，属于轻量级应用服务器，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP程序的首选。
对于一个初学者来说，可以这样认为，当在一台机器上配置好Apache服务器，可利用它响应HTML（标准通用标记语言下的一个应用）页面的访问请求。实际上Tomcat是Apache服务器的扩展，但运行时它是独立运行的，所以当你运行tomcat时，它实际上作为一个与Apache 独立的进程单独运行的。

## 2.2 **常见的web的服务器**

- Apache、
- Nginx、
- IIS、
- WebLogic、
- WebSphere、
- **Tomcat**

# 3、Tomcat

## 3.1 安装tomcat

tomcat官网：http://tomcat.apache.org/

1、选择合适在的压缩包
<img src="JavaWeb.assets/kuangstudyf2b70702-82bb-46bc-8850-47016cbffb15.png" alt="img" style="zoom:67%;" />

2、下载压缩包并解压

## 3.2 Tomcat启动和配置

![img](JavaWeb.assets/kuangstudye48fea27-079b-4b1b-85e0-851235de9c1f.png)

启动。关闭Tomcat

![img](JavaWeb.assets/kuangstudya2d6eb7c-5d3c-47e2-b1e1-3e0a9e0b2638.png)

访问测试：http://localhost:8080/

<img src="JavaWeb.assets/kuangstudyaf5074c2-f24d-48a1-a4a3-e795566cb3c3.png" alt="img" style="zoom: 33%;" />

可能遇到的问题：

1. Java环境变量没有配置
2. 闪退问题：需要配置兼容性
3. 乱码问题：配置文件中设置

## 3.3 配置

![img](JavaWeb.assets/kuangstudy480f442c-0db0-4d95-ac81-34bdc8e50a2d.png)

<img src="JavaWeb.assets/kuangstudy48420cfc-3029-4923-8ad7-ed9ad217a000.jpg" alt="img" style="zoom:67%;" />
**网站是如何访问的:**

1. 输入一个域名，回车

2. 检查本机的C:Windows\System32\drivers\etc\hosts配置文件下有没有这个域名映射
   有：直接返回对应的ip地址，这个地址中，有我们需要访问的web程序，可以直接访问。

   没有：去DNS服务器找，找到的话就返回，找不到就返回找不到。
   ![img](JavaWeb.assets/kuangstudy6de04f31-84b6-43c1-8d81-ed0665f49ae5.jpg)

## 3.4 发布一个web网站

将自己写的网站，放到服务器(Tomcat)中指定的web应用的文件夹（webapps）下，就可以访问了

网站应该有的结构

```java
--webapps ：Tomcat服务器的web目录
    -ROOT
    - demoweb ：网站的目录名
        - WEB-INF
            -classes : java程序
            -lib：web应用所依赖的jar包
            -web.xml ：网站配置文件
    - index.html 默认的首页
    - static
        -css
            -style.css
        -js
        -img
        -.....
```





# 4.Http

## 4.1 什么是http

HTTP（超文本传输协议）是一个简单的请求-响应协议，它通常运行在TCP之上。

- 文本：html，字符串…
- 超文本：图片，音乐，视频，定位，地图…
- 默认端口：80
  HTTPS：安全的协议
- 默认端口：443

## 4.2 http的两个时代

- http1.0：客户端可以与web服务器连接后，只能获得一个web资源，断开连接。
- http2.0：客户端可以与web服务器连接后，可以获得多个web资源。

## 4.3 http请求

客户端—->发请求(request)—->服务器（比如：访问百度）
`General`

```
// 请求地址
Request URL: https://www.baidu.com/
// 请求方法
Request Method: GET
// 状态代码
Status Code: 200 OK
// 远程地址
Remote Address: 14.215.177.38:443
// 引用站点策略
Referrer Policy: strict-origin-when-cross-origin
```

`Request Headers`

```
Request Headers
Accept: text/htmlAccept-Encoding: gzip, deflate, br// 语言Accept-Language: zh-CN,zh;q=0.9Cache-Control: max-age=0Connection: keep-alive
```

**请求行**

- 请求行中的请求方式：GET
- 请求方式：Get，Post，HEAD，DELETE，PUT…
  GET:请求能够携带的参数比较少，大小有限制，会在浏览器的URL地址栏显示数据内容，不安全，但高效。
  POST:请求能够携带的参数没有限制，大小没有限制，不会在浏览器的URL地址栏显示数据内容，安全，但不高效。

**请求头（消息头）**

```
Accept: 告诉浏览器，它所支持的数据类型
Accept-Encoding: 告诉浏览器，它支持哪种编码格式：GBK,UTF-8,GB2312,ISO8859-1
Accept-Language: 告诉浏览器，它的语言环境
Cache-Control: 缓存控制
Connection: 告诉浏览器，请求完成是断开还是保持
HOST：主机
```

## 4.4 http响应

**响应头**

```
// 缓存控制
Cache-Control: no-cache
// 保持连接（http1.1）
Connection: keep-alive
// 文本编码类型
Content-Encoding: gzip
// 响应类型
Content-Type: text/html;charset=utf-8
```

**响应体**

```
Accept: 告诉浏览器，它所支持的数据类型
Accept-Encoding: 告诉浏览器，它支持哪种编码格式：GBK,UTF-8,GB2312,ISO8859-1
Accept-Language: 告诉浏览器，它的语言环境
Cache-Control: 缓存控制
Connection: 告诉浏览器，请求完成是断开还是保持
HOST：主机
Refrush：告诉客户端，多久刷新一次
Location：让网页重新定位
```

**响应状态码**

- 200：响应成功
- 3xx：请求重定向（304等等）
- 4xx：找不到资源（404等等）
- 5xx：服务器代码错误（500代码错误，502网关错误）

# 5.Maven

## 5.1、Maven项目架构管理工具

**好处方便导入jar包的**

Maven的核心思想：**约定大于配置**

## 下载安装Maven

官网;https://maven.apache.org/

<img src="JavaWeb.assets/kuangstudyc6aa9da5-ec43-40e9-ac1e-e436635764c5.png" alt="img" style="zoom: 50%;" />

下载完成后，解压即可；

## 5.3、配置环境变量

在系统环境变量中

配置以下配置：

1. MAVEN_HOME maven的目录
2. 在系统的path中配置 %MAVEN_HOME%\bin
3. ![image-20230321130454128](JavaWeb.assets/image-20230321130454128.png)
4. 测试Maven是否安装成功，保证必须配置完毕！

## 5.4、配置阿里云加速镜像

使用阿里云Maven中央仓库作为下载源，速度更快更稳定。

- maven 安装目录的 `conf/settings.xml`

- 在`<mirrors></mirrors>`标签中添加 `mirror`子节点

  ```
  <mirror>
  <id>aliyunmaven</id>
  <mirrorOf>*</mirrorOf>
  <name>阿里云公共仓库</name>
  <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
  ```

## 5.5、本地仓库

修改本地的仓库存放位置

- maven 安装目录的 `conf/settings.xml`
- 在`<settings></settings>`标签中添加 `localRepository`子节点，按个人习惯存放。

```
<localRepository>C:\Program Files\Java\tools\apache-maven-3.9.1\maven-repo</localRepository>
```

## 5.6、在IDEA中使用Maven

### 永久设置项目的maven配置方法

1、启动IDEA

![image-20230321150544724](JavaWeb.assets/image-20230321150544724.png)



![image-20230321150636037](JavaWeb.assets/image-20230321150636037.png)

![image-20230321150733791](JavaWeb.assets/image-20230321150733791.png)



2、 创建一个MavenWeb项目![img](JavaWeb.assets/fe4738049eb74796acb93d74cfadac23.png)

![img](JavaWeb.assets/94d250b22fe646fbb23097e1e4531e2a.png)

**等待构建成功：** 

![img](JavaWeb.assets/98a849b7b11343cebe21848af97770c8.png)

 **3.删除pom.xml中多余的插件，从war往下删到剩一个</project>**

![img](JavaWeb.assets/2d68c25f12fd474192574f21b10313e9.png)

 成果：

![img](JavaWeb.assets/fc3a614123404c1b9fd2ea4f9b3287c8.png)

**4. 右键main-->新建-->目录**

![img](JavaWeb.assets/d668e15c472b4b86a3934b134da35ffa.png)

根据提示补齐这两个目录（分两次添加进来就好了）

![img](JavaWeb.assets/aedd8dbb2e0b461785563cd4bb41256d.png)

成果：这样就补齐成功了

<img src="JavaWeb.assets/f35518e1c7564793a998e70c87f8e33e.png" alt="img" style="zoom:50%;" />

 **最终成果展示**

<img src="JavaWeb.assets/caf9327e933f4a47be780a8309e4648d.png" alt="img" style="zoom: 50%;" />

**5.检查IDEA中的Maven设置**

![img](JavaWeb.assets/kuangstudyc62e74d0-df28-471b-bcdf-ee5691cf7014.png)

## 5.7、创建一个普通的Maven项目

![img](JavaWeb.assets/kuangstudy7db8abd4-52c0-49ac-8df5-e68520396574.png)

![img](JavaWeb.assets/kuangstudy457ae41e-c996-4ad9-bed3-212a08eb5f64.png)

只有在**Web应用下才有**的项目结构

![img](JavaWeb.assets/kuangstudy961352ab-08ee-4896-b40d-943f7fb364d7.png)

## 5.8、标记文件夹功能

<img src="JavaWeb.assets/kuangstudy5571109f-7c39-4b21-b8cd-37da0324f2d4.png" alt="img" style="zoom:67%;" />

## 5.9、在 IDEA中配置Tomcat

**1、添加tomcat**

![img](JavaWeb.assets/kuangstudya7afd32e-52d0-4ba3-b96f-7977c003c767.png)

**2、相关配置**

![image-20230321135739674](JavaWeb.assets/image-20230321135739674.png)

**3、关联网站**

<img src="JavaWeb.assets/image-20230321140029621.png" alt="image-20230321140029621" style="zoom: 67%;" />

4、**启动tomcat**

<img src="JavaWeb.assets/kuangstudy0f97c64f-40f7-469c-a2f7-467bfa895c5f.png" alt="img" style="zoom:50%;" />

<img src="JavaWeb.assets/kuangstudya1cbcde6-52b0-4d4e-97d6-7d8a4a1bd2fc.png" alt="img" style="zoom: 50%;" />

## 5.10、pom文件

**pom.xml 是Maven的核心配置文件.**

```xml
This XML file does not appear to have any style information associated with it. The document tree is shown below.
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<!-- 项目信息  -->
<groupId>org.example</groupId>
<artifactId>mavenweb01</artifactId>
<version>1.0-SNAPSHOT</version>
<modules>
<module>servlet-01</module>
<module>servlet-02</module>
<module>servlet-03</module>
<module>response</module>
<module>request</module>
</modules>
<!-- 顶目打包方式
  iar: java应用
  War: JavaWeb应用
   -->
<packaging>pom</packaging>
<name>mavenweb01 Maven Webapp</name>
<!--  FIXME change it to the project's website  -->
<url>http://www.example.com</url>
<!-- 配置 -->
<properties>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<maven.compiler.source>1.7</maven.compiler.source>
<maven.compiler.target>1.7</maven.compiler.target>
</properties>
<!-- 项目依赖 -->
<dependencies>
<dependency>
<groupId>junit</groupId>
<artifactId>junit</artifactId>
<version>4.11</version>
<scope>test</scope>
</dependency>
<!--  servlet  -->
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>javax.servlet-api</artifactId>
<version>3.1.0</version>
<scope>provided</scope>
</dependency>
<!--  jsp  -->
<dependency>
<groupId>javax.servlet.jsp</groupId>
<artifactId>javax.servlet.jsp-api</artifactId>
<version>2.3.3</version>
<scope>provided</scope>
</dependency>
<!-- avaServer Pages(TM) API  -->
<!--  https://mvnrepository.com/artifact/javax.servlet.jsp/jsp-api  -->
<dependency>
<groupId>javax.servlet.jsp</groupId>
<artifactId>jsp-api</artifactId>
<version>2.1</version>
<scope>provided</scope>
</dependency>
</dependencies>
<build>
<!-- 在build中配置resources，来防止我们资源导出失败的问题 -->
<resources>
<resource>
<directory>src/main/resources</directory>
<includes>
<include>**/*.properties</include>
<include>**/*.xml</include>
</includes>
<filtering>true</filtering>
</resource>
<resource>
<directory>src/main/java</directory>
<includes>
<include>**/*.properties</include>
<include>**/*.xml</include>
</includes>
<filtering>true</filtering>
</resource>
</resources>
<finalName>mavenweb01</finalName>
<pluginManagement>
<!--  lock down plugins versions to avoid using Maven defaults (may be moved to parent pom)  -->
<plugins>
<plugin>
<artifactId>maven-clean-plugin</artifactId>
<version>3.1.0</version>
</plugin>
<!--  see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging  -->
<plugin>
<artifactId>maven-resources-plugin</artifactId>
<version>3.0.2</version>
</plugin>
<plugin>
<artifactId>maven-compiler-plugin</artifactId>
<version>3.8.0</version>
</plugin>
<plugin>
<artifactId>maven-surefire-plugin</artifactId>
<version>2.22.1</version>
</plugin>
<plugin>
<artifactId>maven-war-plugin</artifactId>
<version>3.2.2</version>
</plugin>
<plugin>
<artifactId>maven-install-plugin</artifactId>
<version>2.5.2</version>
</plugin>
<plugin>
<artifactId>maven-deploy-plugin</artifactId>
<version>2.8.2</version>
</plugin>
</plugins>
</pluginManagement>
</build>
</project>
```

记录了项目具体依赖的Jar包的配置版本。

![img](JavaWeb.assets/kuangstudy6af7ec61-9b12-49d6-8e30-b846269d8353.png)

maven由于它是约定大于配置，之后可以能遇到写的配置文件不生效的问题。

解决方案：

```
<!--在build中配置resources，来防止我们资源导出失败的问题-->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```

## 5.12、Maven仓库的使用

地址：https://mvnrepository.com/

![img](JavaWeb.assets/kuangstudy2a6eec80-3ade-40e7-bcc3-1cbff5a256b2.png)

点击进去，可以查看到使用的人数及版本更新时间

![img](JavaWeb.assets/kuangstudye817321f-045a-4549-b976-cc0371304c66.png)

点击对应的版本，里面有maven引用的配置信息，复制到本地的POM.xml下，重新引用一下依赖，就可以导致对应的Jar包。

![img](JavaWeb.assets/kuangstudyd48bc21f-cfd0-4422-b591-5923c1cb9378.png)

查询项目的依赖就导入进来了。![img](JavaWeb.assets/kuangstudy8743fe2a-cffb-4b41-b602-351484d827a9.png)

![img](JavaWeb.assets/kuangstudye3b5ef0d-e3ca-47b2-91b9-2e2b51ba5d00.png)

# 6. Servlet

## 6.1. Servlet简介

Servlet用java语言开发动态资源网站的技术。

**把实现了Servlet接口的Java程序叫做，Servlet**

servlet生成的class文件存放在tomcat中webapps目录下。

## 6.2. HelloServlet(重点)

Serlvet接口有两个默认的实现类：`HttpServlet`，`GenericServlet`

Serlvet接口Sun公司有两个默认的实现类：HttpServlet，GenericServlet
![img](JavaWeb.assets/kuangstudya8b378f6-e3bd-40ba-865c-bd61670f3f51.jpg)

1. 构建一个普通的Maven项目，删掉里面的src目录，作为父项目.  以后这个项目里面建立Moudel；这个空的工程就是Maven主工程；(方便学习)

1. 父工程中pom.xml会有：

   ```xml
        <modules>
            <module>servlet-01</module>
        </modules>
   ```

   子项目pom.xml中会有：

   ```xml
        <parent>
            <groupId>org.example</groupId>
            <artifactId>javaweb-servlet</artifactId>
            <version>1.0-SNAPSHOT</version>
        </parent>
   ```

   父项目中的jar包，子项目可以直接使用。反之，不可。

   

1、构建一个普通的Maven项目，删掉里面的src目录，以后我们的学习就在这个项目里面建立 Moudel；这个空的工程就是Maven主工程；

**在父工程引用相关的Jar包**

普通maven转web项目 https://www.cnblogs.com/jichi/p/11356759.html

```xml
<!-- servlet -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
<!-- jsp -->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.3</version>
    <scope>provided</scope>
</dependency>
```

**子项目**

编写一个普通类，继承HttpServlet

```java
public class HelloWorld extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doGet(req, resp);
		PrintWriter writer = resp.getWriter();
		writer.print("Hello,Servlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
```

**Servlet的映射**

映射：我们写的是JAVA程序，但是要通过浏览器访问，而浏览器需要连接web服务器， 所以我们需要再web服务中注册我们写的Servlet，让浏览器找到我们java程序的发布目录

<font color = red > 在**web.xml文件**中做serlet映射 </font>

```xml
  <!--注册Servlet-->
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>com.jindao.servlet.HelloWorld</servlet-class> // 类
    </servlet>
  <!--Servlet的请求路径-->
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>   //网址
  </servlet-mapping>
```

3.5、子项目配置Tomcat，并访问。

**更改一下虚拟访问的路径:**

![image-20230321163640840](JavaWeb.assets/image-20230321163640840.png)

**运行:**

首页: index.jsp显示的内容

![image-20230321165159034](JavaWeb.assets/image-20230321165159034.png)

servlet中java程序显示的

![image-20230321163810581](JavaWeb.assets/image-20230321163810581.png)

项目结构:

![image-20230321164009541](JavaWeb.assets/image-20230321164009541.png)



## 6.3 Servlet原理

![img](JavaWeb.assets/kuangstudybf07068f-3fe7-4651-ba6d-88f522085994.jpg)

## 6.4.  Servlet映射问题(每个java文件都得做映射)

1、一个Servlet可以指定一个映射路径

```xml
<servlet-mapping>
    <servlet-name>hello</servlet-name>    <!--注册的servlet-->
    <url-pattern>/hello</url-pattern>     <!--url请求路径-->
</servlet-mapping>
```

2、一个Servlet可以指定多个映射路径

```xml
<servlet-mapping>
    <servlet-name>hello</servlet-name>  
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello2</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello3</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello4</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello5</url-pattern>
</servlet-mapping>
```

3、一个Servlet可以指定通用映射路径

```
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello/*</url-pattern>
</servlet-mapping>
```

4、作为默认请求路径

```
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

5、指定一些后缀或者前缀等等…

```xml
<!-- 注意，*前面不能加项目映射的路径-->
<servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>*.demo</url-pattern>
</servlet-mapping>
```

6、优先级问题
指定了固有的映射路径优先级最高**，如果找不到就会走默认的处理请求**；

```xml
<!--404-->
<servlet>
    <servlet-name>error</servlet-name>
    <servlet-class>com.jindao.servlet.ErrorServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>error</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

现在一般不做映射了,写个这个就行

```jsp
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

<!-- http://localhost:8080/netBank 请求页面 -->
      <welcome-file-list>
          <welcome-file>login.jsp</welcome-file>
      </welcome-file-list>
</web-app>

```



## 6.5、ServletContext类

web容器在启动的时候，它会为每个web程序都创建一个对应的ServletContext对象，它代表了当前的web应用。

### 1、共享数据

我在这个Servlet中保存的数据，可以在另外一个servlet中拿到；

![img](JavaWeb.assets/kuangstudy8761b223-9619-4d95-83c3-bc83b21a0658.jpg)

**将一个共享数据保存在了ServletContext : **

```java
public class HelloServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //this.getInitParameter() 初始化参数
        //this.getServletConfig() Servlet配置
        //this.getServletContext() Servlet上下文
		ServletContext context = this.getServletContext();
		String username = "小明"; //数据
		context.setAttribute("username", username);  //将一个数据保存在了ServletContext
	}
}
```

**从ServletContext获取username值的对象出来**

```java
public class GetServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		//从ServletContext获取username值的对象出来
		String username = (String) context.getAttribute("username");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print("名字:"+username);
	}
```

在**web.xml文件**中做serlet映射

```xml
  <!--注册Servlet-->
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>com.jindao.servlet.HelloServlet </servlet-class>
  </servlet>
  <servlet>
    <servlet-name>getc</servlet-name>
    <servlet-class>com.jindao.servlet.GetServlet</servlet-class>
  </servlet>

  <!--Servlet的请求路径-->
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>getc</servlet-name>
    <url-pattern>/getc</url-pattern>
  </servlet-mapping>
```

**测试运行结果**

### 2、获取初始化参数

在**web.xml文件**中操作

```xml
<!--配置一些web应用初始化参数-->
<context-param>
    <param-name>application-name</param-name>
    <param-value>学习Servlet</param-value>
</context-param>
```

```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
    ServletContext context = this.getServletContext();
    String url = context.getInitParameter("application-name");
    resp.setContentType("text/html");
    resp.setCharacterEncoding("utf-8");
    resp.getWriter().print(url);
}
```

### 3、请求转发

```java
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        System.out.println("进入了DemoServlet4");
        //RequestDispatcher requestDispatcher = context.getRequestDispatcher("/demo4"); //转发的请求路径
        //requestDispatcher.forward(req,resp); //调用forward实现请求转发；
        context.getRequestDispatcher("/demo3").forward(req,resp);  // 进入demo3页面
    }
```

```xml
// web.xml文件
// 请求sd4
<servlet>
 <servlet-name>gp</servlet-name>
 <servlet-class>com.sunyiwenlong.servlet.ServletDemo03</servlet-class>
</servlet>
<servlet-mapping>
 <servlet-name>gp</servlet-name>
 <url-pattern>/gp</url-pattern>
</servlet-mapping>
<servlet>
 <servlet-name>sd4</servlet-name>
 <servlet-class>com.sunyiwenlong.servlet.ServletDemo04</servlet-class>
</servlet>
<servlet-mapping>
 <servlet-name>sd4</servlet-name>
 <url-pattern>/sd4</url-pattern>
</servlet-mapping>
```



### 4、读取资源文件

1. Properties

- 在java目录下新建properties
- **在resources目录下新建properties发现：都被打包到了同一个路径下：classes，我们俗称这个路径为classpath。**

思路：需要一个文件流

```java
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/db.properties");  //需要一个文件流
        Properties prop = new Properties();
        prop.load(is);
        String user = prop.getProperty("username");
        String pwd = prop.getProperty("password");
        resp.getWriter().print(user+":"+pwd);  // 发送
    }
```

在**web.xml文件**中做serlet映射

**测试运行结果**

## 6.6. HttpServletResponse

web服务器接收到客户端的http请求，针对这个请求，分别创建一个**代表请求的HttpServletRequest对 象**，**代表响应的一个HttpServletResponse；**

- 获取客户端请求过来的参数：`HttpServletRequest`
- 响应客户端响应一些信息：`HttpServletResponse`

1、**向浏览器输出消息**

```java
public ServletOutputStream getOutputStream() throws IOException;
public PrintWriter getWriter() throws IOException;
```

**2、响应的状态码**

```java
public static final int SC_CONTINUE = 100;
 /**
  * Status code (200) indicating the request succeeded normally.
  */
 public static final int SC_OK = 200;
 /**
  * Status code (302) indicating that the resource has temporarily
  * moved to another location, but that future references should
  * still use the original URI to access the resource.
  *
  * This definition is being retained for backwards compatibility.
  * SC_FOUND is now the preferred definition.
  */
 public static final int SC_MOVED_TEMPORARILY = 302;
 /**
 * Status code (302) indicating that the resource reside
 * temporarily under a different URI. Since the redirection might
 * be altered on occasion, the client should continue to use the
 * Request-URI for future requests.(HTTP/1.1) To represent the
 * status code (302), it is recommended to use this variable.
 */
 public static final int SC_FOUND = 302;
 /**
  * Status code (304) indicating that a conditional GET operation
  * found that the resource was available and not modified.
  */
 public static final int SC_NOT_MODIFIED = 304;
 /**
  * Status code (404) indicating that the requested resource is not
  * available.
  */
 public static final int SC_NOT_FOUND = 404;
 /**
  * Status code (500) indicating an error inside the HTTP server
  * which prevented it from fulfilling the request.
  */
 public static final int SC_INTERNAL_SERVER_ERROR = 500;
 /**
  * Status code (502) indicating that the HTTP server received an
  * invalid response from a server it consulted when acting as a
  * proxy or gateway.
  */
 public static final int SC_BAD_GATEWAY = 502;
 // ...
```

**常见应用:**

2、常见应用

1.向浏览器输出消息 (之前那些)

```java
public class HelloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter(); //响应流
        writer.print("Hello,Servlet");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);         
    }
}
```

2. 下载文件

```
1. 要获取下载文件的路径
2. 下载的文件名
3. 设置想办法训浏览器能够支持下载我们需要的东西
4. 获取下载文件的输入流
5. 创建缓冲区
6. 获取OutputStream对象
7，将FileoutputStream流写入到buffer缓冲区
8. 使用OutputStream将缓冲区中的数据输出到客户端!
```

```java

public class FileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 要获取下载文件的路径
		//String realPath = this.getServletContext().getRealPath("/1.png");
		String realPath = "C:\\Users\\16658\\Desktop\\java\\java\\note\\JavaWeb\\code\\mavenweb01\\response\\src\\main\\resources\\1.png";
		System.out.println("下载文件的路径: " + realPath);

//		2. 下载的文件名
		String filename = realPath.substring(realPath.lastIndexOf("\\") + 1);// 1.png
//		3. 设置想办法训浏览器能够支持下载我们需要的东西
		resp.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename,"utf-8"));
//		4. 获取下载文件的输入流
		FileInputStream in = new FileInputStream(realPath);
//		5. 创建缓冲区
		int len = 0;
		byte[] buffer = new byte[1024];// 每次读取的长度
//		6. 获取OutputStream对象
		ServletOutputStream out = resp.getOutputStream();
//		7.将FileoutputStream流写入到buffer缓冲区
		while ((len = in.read(buffer))>0){// 每次读取的长度大于0的情况下，就写出去
			out.write(buffer,0,len);// 写出字节，从0写到len
		}
		// 8.使用OutputStream将缓冲区中的数据输出到客户端！
		in.close();
		out.close();
	}
}
```

3.验证码功能

```java
public class ImageServlet extends HttpServlet {
 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     // 让浏览器3秒刷新一次
     resp.setHeader("refresh", "3");
     // 在内存中创建一个图片
     BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);// 宽、高、颜色
     // 得到图片
     Graphics2D g = (Graphics2D) image.getGraphics();// 得到一只2D的笔
     // 设置图片的背景颜色
     g.setColor(Color.white);
     g.fillRect(0, 0, 80, 20);// 填充颜色
     // 换个背景颜色
     g.setColor(Color.BLUE);
     // 设置字体样式：粗体，20
     g.setFont(new Font(null,Font.BOLD,20));
     // 画一个字符串（给图片写数据）
     g.drawString(makeNum(),0,20);
     // 告诉浏览器，这个请求用图片的方式打开
     resp.setContentType("image/jpeg");
     // 网站存在缓存，不让浏览器缓存
     resp.setDateHeader("expires",-1);
     resp.setHeader("Cache-Control","no-cache");
     resp.setHeader("Pragma","no-cache");
     // 把图片写给浏览器
     boolean write = ImageIO.write(image, "jpg",resp.getOutputStream());
 }
 // 生成随机数
 private String makeNum() {
     Random random = new Random();
     String num = random.nextInt(9999999) + "";// 随机数，最大七位，[0,9999999)
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < 7 - num.length(); i++) {// 不足七位，则添加0
         sb.append("0");
     }
     num = sb.toString()+num;// 不足七位，在随机数前面添加0
     return num;
 }
 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     this.doGet(req, resp);
 }
}
```

![image-20230322102404060](JavaWeb.assets/image-20230322102404060.png)



### 4. **实现请求重定向(重要)**

B一个web资源收到客户端A请求后，B他会通知A客户端去访问另外一个web资源C，这个过程叫重定
<img src="JavaWeb.assets/image-20230322103437265.png" alt="image-20230322103437265" style="zoom:50%;" />

**应用场景:  用户登录**

- **转发**：
  - **客户端浏览器的地址栏没有变化**
  - 服务端内部转发
  - 刷新会重新提交数据
  - 会保存请求域中数据
- **重定向**
  - 客户端浏览器的地址栏有变化
  - 客户端重新请求
  - 刷新不会重新提交数据
  - 不会保存请求域中数据

相同点：页面都会实现跳转

不同点：

- 请求转发的时候，url地址栏不会产生变化。307
- 重定向时候，url地址栏会发生变化。302

**转发流程**：

<img src="JavaWeb.assets/kuangstudy63322d80-403b-45e3-9ac1-9bde1a82abba.png" alt="img" style="zoom: 50%;" />

实现代码：

```java
request.getRequestDispatcher("/地址").forward(request, response);
```

**重定向流程**：

<img src="JavaWeb.assets/kuangstudy14f6ce78-7333-4a15-98d2-ee06401b2156.png" alt="img" style="zoom:50%;" />



实现代码

```
response.sendRedirect("/地址");
```

转发和重定向

相同点:  页面都会实现跳转
不同点:   请求转发的时候，url不会产生变化,  重定向时候，url地址栏会发生变化；



**实现请求重定向**

==由客户端进行的页面跳转，称为重定向==

```java
public class RedirectServlet extends HttpServlet {
 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     /*resp.setHeader("Location","/response_war/image");
     resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);*/
     resp.sendRedirect("/response_war/image");// 重定向相当于上面两行代码  //参数:  虚拟访问路径/url请求路径
 }
 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     this.doGet(req, resp);
 }
}
```



**应用:**

**jsp文件**

![image-20230322153211523](JavaWeb.assets/image-20230322153211523.png)

```jsp
<html>

<body>
<h2>Hello World!</h2>
<!--中文-->
<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<!--如果跳转失败证明jsp页面还不支持el表达式，最简单的方法是在jsp上面加上-->
<%@ page isELIgnored="false"%>
<!--表单-->
<%--这里的路径，需要项目的路径+url路径
${ pageContext.request.contextPath} 代表当前项目
--%>
<form action="${pageContext.request.contextPath}/login" method="get">
    <p>名字: <input type="text" name="username" ></p>
    <p>密码: <input type="password" name="password" id="password"></p>
    <p> <input type="submit"></p>
</form>

</body>
</html>

```

**重定向后跳转的jsp文件**

![image-20230322153243166](JavaWeb.assets/image-20230322153243166.png)

**实现重定向的文件**

![image-20230322153332616](JavaWeb.assets/image-20230322153332616.png)

```java
 public class RequestTest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 处理请求
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(username  + ":" + password);
		//
		resp.sendRedirect("/r/success.jsp");
	}
}
```

运行效果

![image-20230322153534651](JavaWeb.assets/image-20230322153534651.png)

![image-20230322153520927](JavaWeb.assets/image-20230322153520927.png)

##  6.7. HttpServletRequest

HttpServletRequest代表客户端的请求，用户通过Http协议访问服务器，HTTP请求中的所有信息会被封装到HttpServletRequest，通过这个HttpServletRequest的方法，获得客户端的所有信息。

### 1. 获取前端传递的参数
![img](JavaWeb.assets/kuangstudyfbb80cd9-dac2-49f1-aea8-625e2b074aa9.jpg)

### 2. 请求转发

   **前端：**

   index.jsp页面

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <%@ page isELIgnored="false"%>
   
   <html>
   <head>
       <title>登录</title>
   </head>
   <body>
   
   <h1>登录</h1>
   <div style="text-align: center">
   
       <form action="${pageContext.request.contextPath}/login" method="post">
               用户名:   <input type="text" name="username"> <br>
               密码：<input type="password" name="password"> <br >
               爱好:
               <input type="checkbox" name="hobbys" value="女孩">女孩
               <input type="checkbox" name="hobbys" value="代码">代码
               <input type="checkbox" name="hobbys" value="唱歌">唱歌
               <input type="checkbox" name="hobbys" value="电影"> 电影
           <br>
           <input type="submit">
       </form>
   </div>
   
   
   </body>
   </html>
   ```

   success.jsp页面

   ```jsp
   <%--
     Created by IntelliJ IDEA.
     User: 16658
     Date: 2023/3/22
     Time: 15:57
     To change this template use File | Settings | File Templates.
   --%>
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>success</title>
   </head>
   <body>
   <h1> 登录成功</h1>
   </body>
   </html>
   
   ```

**后端：**

```java
public class LoginServlet extends HttpServlet {
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String username = req.getParameter("username");
      String password = req.getParameter("password");
      String[] hobbys = req.getParameterValues("hobbys");

      System.out.println("=============");
      System.out.println(username);
      System.out.println(password);
      System.out.println(Arrays.toString(hobbys));

      // 转发 // 斜杠/就代表当前项目路径(虚拟路径)
      req.getRequestDispatcher("/success.jsp").forward(req, resp);
      
   }
}
```

**web.xml**

```xml
  <servlet>
    <servlet-name>loginServlet</servlet-name>
    <servlet-class>com.jindao.request.LoginServlet</servlet-class>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>loginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
```

# 7. cookie/session

## 7.1 会话

无状态的会话：用户打开一个浏览器，点击了很多超链接，访问多个web资源，关闭浏览器，这个过程可以称之为会话。
有状态的会话：一个用户打开一个浏览器，访问某些资源（网站），下次再来访问该资源（网站），我们会知道这个用户曾经来过，称之为有状态会话；

```
一个网站，怎么证明你来过？
```

1. 服务端给客户端一个信件，客户端下次访问服务端带上信件就可以了；cookie（客户端）
2. 服务器登记你来过了，下次你来的时候我来匹配你；seesion（服务端）

## 7.2 保存会话的两种技术

cookie：

- 客户端技术，（响应、请求）

session：

- 服务端技术，利用这个技术，可以保存用户的会话信息.  我们可以把信息或者数据放在Session中。

## 7.3 Cookie

![img](JavaWeb.assets/kuangstudy436007a6-2fe3-4f8b-a276-ba59867e156f.jpg)

1、访问网站时，会返回cookie信息。

2、客户端访问时，需要带上cookie，方便知道你是谁。

```java
Cookie[] cookies = req.getCookies(); //获得Cookie
cookie.getName(); //获得cookie中的key
cookie.getValue(); //获得cookie中的vlaue

new Cookie("lastLoginTime", System.currentTimeMillis()+""); //新建一个cookie
cookie.setMaxAge(24*60*60); //设置cookie的有效期
resp.addCookie(cookie); //响应给客户端一个cookie
```

**cookie：一般会保存在本地的 用户目录下 appdata；**

- 一个Cookie只能保存一个信息；
- 一个web站点可以给浏览器发送多个cookie，最多存放20个cookie；
- Cookie大小有限制4kb；
- **300个cookie浏览器上限**

**删除Cookie**；

- 不设置有效期，关闭浏览器，自动失效；
- 设置有效期时间为 0 ；
- 解决coolie里有中文的情况:
- ![image-20230322173342762](JavaWeb.assets/image-20230322173342762.png)

代码:

```java
package com.jindao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

public class CookieDemo01 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");

		PrintWriter out = resp.getWriter();  //向浏览器写入一些东西
		Cookie[] cookies = req.getCookies();  //这星返回数纽，说明Cookie可能存在多个

		// 判断cookie是否
		if (cookies != null){
			out.write("你上一次访问的时间");
			for (Cookie cookie : cookies) {
				// 获取cookie名字
				if (cookie.getName().equals("lastLoginTime")) {
					//获取cookie值
					long lastLoginTime = Long.parseLong(cookie.getValue());
					Date date = new Date(lastLoginTime);
					out.write(date.toLocaleString());
				}
			}
		}else {
			// 第一次访问
			out.write("这是您第一次访问本网站");
		}

		// 服务器响应一个cookie
		Cookie cookie = new Cookie("lastLoginTime", System.currentTimeMillis() + "");
		cookie.setMaxAge(24*60*60);  // 设置cookie的有效期为一天，单位是：秒
		resp.addCookie(cookie);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
```

![image-20230322173810923](JavaWeb.assets/image-20230322173810923.png)

## 7.4 Session（重点）

**访问网站的时候，Session会在服务器保存。**

![img](JavaWeb.assets/kuangstudyeb1e8d24-496a-4939-a2ce-4d9e9817da6c.jpg)

什么是Session：

- 服务器会给每一个用户（浏览器）创建一个Seesion对象；
- 一个Seesion独占一个浏览器，只要浏览器没有关闭，这个Session就存在；
- **用户登录之后，整个网站它都可以访问！—> 保存用户的信息；用户的权限等等**

使用Session：

```java
//得到Session
HttpSession session = req.getSession();
//给Session中存数据
session.setAttribute("name","admin");
//获取Session中的数据
session.getAttribute("name");
//删除Session中的数据
session.removeAttribute("name");
//获取Session的ID
String sessionId = session.getId()
//手动注销Session
session.invalidate();
```

**会话自动过期：web.xml配置**

```java
<!--设置Session默认的失效时间-->
<session-config>
<!--15分钟后Session自动失效，以分钟为单位-->
<session-timeout>15</session-timeout>
</session-config>
```

### 1. **共享数据**

<img src="JavaWeb.assets/image-20230323110221202.png" alt="image-20230323110221202" style="zoom:50%;" />

**sessionDemo01**

```java
public class sessionDemo01 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");
		// 得到session数据
		HttpSession session = req.getSession();
		// 给session中存入东西(可以是对象)
		session.setAttribute("name", new Person("小明", 18));
		//获得session的id
		String id = session.getId();
		// 判断session是不是新的创建
		if (session.isNew()){
			resp.getWriter().write("session创建成功, ID" + id);

		}else {
			resp.getWriter().write("session以及在服务器中存在了, ID" + id);
		}
	}
}

```

**sessionDemo02**

```java
public class sessionDemo02 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-16");
		resp.setCharacterEncoding("utf-16");
		// 得到session数据
		HttpSession session = req.getSession();
		// 得到session中存入东西
		Person name = (Person) session.getAttribute("name");
		System.out.println(name);
	}
}
```

**person**

```java
public class Person {
	private String name;
	private  int age;

	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Person(String name) {
		this.name = name;
	}

	public Person(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
```

效果:

![image-20230323110249192](JavaWeb.assets/image-20230323110249192.png)

输入 [localhost:8080/r/s2](http://localhost:8080/r/s2)得到:

![image-20230323110307054](JavaWeb.assets/image-20230323110307054.png)

### 2. 注销

手动注销

```java
public class sessionDemo03 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// 手动注销 (不用)
		session.removeAttribute("小明");
		session.invalidate();  // 注销-->相当于关闭浏览器

	}
}
```

自动注销

```xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <!--设置session默认的失效时间-->
  <session-config>
    <!--15分钟后session自动失效，以分钟为单位-->
    <session-timeout>15</session-timeout>
  </session-config>
  
</web-app>
```

### 3. **Session和cookie的区别**

- Cookie是把用户的数据写给用户的浏览器，浏览器保存 （可以保存多个）
- Session把用户的数据写到用户独占Session中，服务器端保存 （保存重要的信息，减少服务器资源的浪费）
- **Session对象由服务器创建；访问网站的时候，Session会在服务器保存。**

使用场景：

- 保存一个登录用户的信息；
- 在整个网站中经常会使用的数据，将它保存在Session中；
- 购物车信息

## 7.5 token

如果是开发web应用的话，用两者都可以，但如果是开发API接口，前后端分离，最好使用Token，因为session+cookies是基于web的，但针对API接口可能会考虑到移动端，app是没有cookies和session的。

**Token的生命周期**

**1）用户未登录**

用户执行注册/登录→

一旦基础数据校验成功，后端生成Token，并且Token包含此次注册/登录用户的用户名并通过JsonResponse返回给前端→

前端拿到返回的Token后，存入浏览器本地存储

**2）用户每次访问博客页面**

从本地存储中拿出Token→

JS将Token　放入request的Authorization头，发送http请求向后端索要数据→

服务器接到前端请求（当前URL加了loging_check,并且请求方法在methods参数中）,进行校验→从requestAuthorization头拿出Token→校验→校验不通过，返回前端异常代码/校验通过，正常执行对应的视图函数→前端一旦接到关于Token的异常码，则删除本地存储中的Token,且将用户转至登录界面。

**如何设置Token的有效期？**

其实Token作为一个概念模型，开发者完全可以针对自己开发的应用自定义Token，只要能做到不让不法分子钻系统漏洞即可。

那么为Token设置有效期还有必要吗？

对于这个问题，大家不妨先看两个例子：

例1：登录密码

登录密码一般要求定期改变密码，以防止泄漏，所以密码是有有效期的。

例2：安全证书

SSL安全证书都有有效期，目的是为了解决吊销的问题。

所以无论是从安全的角度考虑，还是从吊销的角度考虑，Token都需要设有效期。

那么，Token的有效期多长合适呢？

一般来说，基于系统安全的需要当然需要尽可能的短，但也不能短得离谱：如果在用户正常操作的过程中，Token过期失效要求重新登录，用户体验岂不是很糟糕？

为了解决在操作过程不让用户感到Token失效的问题，有一种方案是在服务器端保存Token状态，用户每次操作都会自动刷新（推迟）Token的过期时间。

如此操作会存在一个问题，即在前后端分离、单页App等情况下，每秒可能发起多次请求，如果每次都去刷新过期时间会产生非常大的代价，同样地，如果Token的过期时间被持久化到数据库或文件，代价就更大了。所以通常为了提升效率、减少消耗，会把Token的过期时保存在缓存或者内存中。

另一种方案是使用RefreshToken，它可以避免频繁的读写操作。这种方案中，服务端无需刷新Token的过期时间，一旦Token过期，就反馈给前端，前端使用RefreshToken申请一个全新Token继续使用。

这种方案中，服务端只需要在客户端请求更新Token的时候对RefreshToken的有效性进行一次检查，大大减少了更新有效期的操作，也就避免了频繁读写。当然RefreshToken也是有有效期的，但是这个有效期就可以长一点了。

使用 Token 和 Refresh Token 的时序图如下：

1）登录

![img](JavaWeb.assets/v2-249b858821ae8e68420c37ebacb9486f_720w.webp)

图：来源于网络

2）业务请求

![img](JavaWeb.assets/v2-97a479838a94cd365523961682643a98_720w.webp)

图：来源于网络

3）Token 过期，刷新 Token

![img](JavaWeb.assets/v2-fa0a5c054a69a31d270b981df4b1ddb8_720w.webp)

# 8. JSP

## 8.1 什么是JSP

**Java Server Pages ： Java服务器端页面，也和Servlet一样，用于动态Web技术**！

最大的特点：

- 写JSP就像在写HTML

- 区别：

  - HTML只给用户提供静态的数据

  - JSP页面中可以嵌入JAVA代码，为用户提供动态数据；

## 8.2 JSP原理

- 服务器内部工作
  - tomcat中有一个work目录；
  - IDEA中使用Tomcat的会在IDEA的tomcat中生产一个work目录

Tomcat部署项目下的`\work\Catalina\localhost\ROOT\org\apache\jsp`

发现页面转变成了Java程序！

![img](JavaWeb.assets/kuangstudy9d390fe2-47e7-499e-ba97-2da8fe2fbe24.png)

浏览器向服务器发送请求，不管访问什么资源，其实都是在访问Servlet！

**JSP最终也会被转换成为一个Java类**, JSP 本质上就是一个Servlet。

![image-20230323141734839](JavaWeb.assets/image-20230323141734839.png)

```java
// 初始化
public void _jspInit() {
  }
// 销毁
  public void _jspDestroy() {
  }
// JSPservice
  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
```

1. 判断请求的方式
2. 内置一些对象（9个）

```java
final javax.servlet.jsp.PageContext pageContext;    // 页面上下文
javax.servlet.http.HttpSession session = null;        // session
final javax.servlet.ServletContext application;        // applicationContext
final javax.servlet.ServletConfig config;            // config
javax.servlet.jsp.JspWriter out = null;                // out
final java.lang.Object page = this;                    // page：当前
HttpServletRequest request;                            // 请求
HttpServletResponse response;                        // 响应
```

输出页面前增加的代码

```java
response.setContentType("text/html;charset=UTF-8");// 设置响应的页面类型
pageContext = _jspxFactory.getPageContext(this, request, response,
         null, true, 8192, true);
_jspx_page_context = pageContext;
application = pageContext.getServletContext();
config = pageContext.getServletConfig();
session = pageContext.getSession();
out = pageContext.getOut();
_jspx_out = out;
```

以上的内置对象可以在jsp页面中直接使用

在JSP页面中；  只要是JAVA代码就会原封不动的输出；
如果是HTML代码，就会被转换为：

```java
out.write("<html>\r\n");
```

**原理图:**

![img](JavaWeb.assets/kuangstudy91ca1997-e219-4e14-9457-6c0f4ce3ade4.jpg)

## 8.3 JSP基础语法(了解)

JSP 作为java技术的一种应用，它拥有一些自己扩充的语法（了解，知道即可！），可支持Java所有语法！

### JSP表达式(用来将程序的输出)

```jsp
<%--JSP表达式
作用：用来将程序的输出，输出到客户端，可以写JAVA代码。 <%= 变量或者表达式%>
--%>

<%= new java.util.Date()%>
```

### jsp脚本片段

```jsp
<%--jsp脚本片段--%>

<%
    int sum = 0;
    for (int i = 1; i <=100 ; i++) {
        sum+=i;
    }
    out.println("<h1>Sum="+sum+"</h1>");
%>

<%--下面可以正常写html代码--%>
<p>
    今天天气好。
</p>

<%--Java代码和HTML穿插使用--%>

<%
    for (int i = 0; i < 5; i++) {
%>
    <h1>Hello,World <%=i%> </h1>
<%
    }
%>



<% 
    int status = accountEnquiry.getStatus();
    if (status == 1) { 
%>
    data-status="启用"
<% 
    } else {
%>
    data-status="冻结"
<%
    }
%>
```

<img src="JavaWeb.assets/image-20230323143501472.png" alt="image-20230323143501472" style="zoom:33%;" />

### JSP声明

```jsp
<%!
static {
    System.out.println("Loading Servlet!");
}
private int globalVar = 0;
public void demo(){
    System.out.println("demo--->！");
}
%>
```

**JSP声明：会被编译到JSP生成Java的类中！ 其他的(表达式/脚本片段)，就会被生成到_jspService方法中！**

在JSP，嵌入Java代码即可！

### JSP注释

```jsp
<%--注释--%>
```

**JSP的注释，不会在客户端显示，HTML注释就会！**

## 8.4 JSP指令

1. **定制错误页面**

   ```jsp
   <%--定制错误页面--%>
   <%@ page errorPage="error/500.jsp" %>
   ```

   或者

   **在web.xml定制全局的错误页面**
   ![img](https://kuangstudy.oss-cn-beijing.aliyuncs.com/bbs/2022/07/11/kuangstudycfd43edb-06bc-4796-b7d6-0b5022cc3a88.jpg)

### 包含上下文

1. **包含头部和尾部**

   头部和尾部是所有页面共有的那种

   ```jsp
   <%@page args.... %>
   <%@include file=""%>
   <%-- jsp指令
       @include会将两个页面合二为一
   --%>
   <%@include file="common/header.jsp"%>
   <h1>网页主体</h1>
   <%@include file="common/footer.jsp"%>
   <hr>
   
   
   <%-- jsp标签
   	jsp:include：拼接页面，本质还是三个
   --%>
   <jsp:include page="/common/header.jsp"/>
   <h1>网页主体</h1>
   <jsp:include page="/common/footer.jsp"/>
   ```

   ![img](JavaWeb.assets/kuangstudyb64caaa0-79ff-44e6-9a17-5371bfad0057.jpg)


![image-20230323152833241](JavaWeb.assets/image-20230323152833241.png)

**运行效果:**

<img src="JavaWeb.assets/image-20230323152741157.png" alt="image-20230323152741157" style="zoom:33%;" />

## 8.5 九大内置对象

   - PageContext `存东西`
   - Request `存东西`
   - Response
   - Session `存东西`
   - Application（ServletContext） `存东西`
   - Config（ServletConfig）
   - out
   - page
   - exception

存东西的区别

   ```java
   pageContext.setAttribute("name1","张三1");// 保存的数据只在一个页面中有效
   request.setAttribute("name2","张三2");// 保存的数据只在一次请求中有效，请求转发会携带这个数据
   session.setAttribute("name3","张三3");// 保存的数据只在一次会话中有效，从打开浏览器到关闭浏览器
   application.setAttribute("name4","张三4");// 保存的数据只在服务器中有效，从打开服务器到关闭服务器
   ```

**request**：客户端向服务器发送请求，产生的数据，可能过会就用不上了，例：某个文章！

**session**：客户端向服务器发送请求，产生的数据，可能用完了还要用，例：用户信息；

**application**：客户端向服务器发送请求，产生的数据，一个用户用完了，其他用户还可能使用。例：全局参数。

![img](JavaWeb.assets/kuangstudy8374695c-80b2-4c0a-96ee-e588b6604359.jpg)

```java
<html>
<body>
<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<h2>Hello World!</h2>

<%--内置对象--%>
<%
    pageContext.setAttribute("name1", "小明1");  // 保存的数据只在一个页面中有效
    request.setAttribute("name2", "小明2");     // 保存的数据一次请求中有效，请求转发会携带这个数据
	session.setAttribute("name3", "小明3");  // 保存的数据只在一次会话中有效, 从打开浏览器到关闭浏览器
    application.setAttribute("name4", "小明4"); // 保存的数据只在服务器中有效，从打开浏览器到关闭浏览器
%>

<%
    //从pageContext取出，通过寻找的方式来取
    String name1 =(String)pageContext.findAttribute("name1");
    String name2 =(String)pageContext.findAttribute("name2");
    String name3 =(String)pageContext.findAttribute("name3");
    String name4 =(String)pageContext.findAttribute("name4");
    //String name5 =(String)pageContext.findAttribute("name5");            //  不存在
    out.println(name1);
%>

</body>
</html>

```

![image-20230323175728369](JavaWeb.assets/image-20230323175728369.png)

## 8.6 jsp标签、JSTL标签、EL表达式

### **JSP标签**

JSP自带比较简单的标签。

![image-20230323182649175](JavaWeb.assets/image-20230323182649175.png)

![image-20230323182704832](JavaWeb.assets/image-20230323182704832.png)

![image-20230323182738767](JavaWeb.assets/image-20230323182738767.png)

### **JSTL表达式**

扩展很多自定义标签。

#### **EL表达式**： `${ }`

- 获取数据
- 执行运算
- 获取web开发的常用对象

####  JSTL表达式

JSTL支持通用的、结构化的任务，比如迭代，条件判断，XML文档操作，国际化标签，SQL标签。

![img](JavaWeb.assets/kuangstudy19ab45dc-bc7d-46e2-a95c-2964b1254a03.png)

#### 所需仓库

```xml
<!-- JSTL表达式的依赖 -->
<dependency>
    <groupId>javax.servlet.jsp.jstl</groupId>
    <artifactId>jstl-api</artifactId>
    <version>1.2</version>
</dependency>
<!-- standard标签库 -->
<dependency>
    <groupId>taglibs</groupId>
    <artifactId>standard</artifactId>
    <version>1.1.2</version>
</dependency>

```



[jsp中EL表达式不起作用的问题 解决方法](https://www.cnblogs.com/jsoso/p/11219955.html)

问题描述：在jsp页面中使用el表达式取值，取不到值，但是使用jsp中嵌套java代码可以取到值，对应代码如下：

![image-20230715160324184](JavaWeb.assets/image-20230715160324184.png)

结果：

```
time: ${requestScope.time} 
time: ${requestScope.get("time")} 
time: Wed Dec 23 16:02:03 CST 2015 

time: ${time}
```

解决方法： 
在使用el表达式的jsp中添加一个配置：`<%@page isELIgnored="false" %>，如下图`

![img](JavaWeb.assets/1745551-20190721004525321-525200751.png)

 

该配置代表在本jsp中使用el表达式，可以解析其中的值。若isELIgnored设置为true，代表在本页忽略el表达式，当做字符串直接显示。配置过后，el表达式正常工作，显示正常。

那么为什么会这样？首先，查看web.xml中配置的<web-app>标签，jsp servlet版本是多少？标签内容如下：

```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >
发现使用版本为2.3。

注意：jsp servlet 2.4版本或者更新的版本，isELIgnored默认值为false，而2.3或者更早的版本isELIgnored默认值为true。就导致了出现EL表达式无效的情况。 

至此，问题已经解决。 
```



```
            <!-- Assuming you have a list of accountEnquiry objects -->
            <%
                List<Bankcard> list = (List<Bankcard>) request.getAttribute("list");
            %>
            <% for (Bankcard accountEnquiry : list) { %>
            <tr>
                <td><%= accountEnquiry.getCardid() %></td>
                <td><%= accountEnquiry.getCardnumber() %></td>
                <td><%= accountEnquiry.getStatus() %></td>
            </tr>
            <% } %>

```





# 9. mvc三层架构

## 10.过滤器（Filter）

Filter：过滤器，用来过滤网站的数据：

- 处理中文乱码。
- 登录验证。

Filter开发步骤：

```java
package com.bank.filter;

import com.bank.model.Account;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",
        servletNames = {
                "TransferServlet", "TradeListByTimeServlet", "TradeListServlet", "TransferServlet"
        },
        value = {
                // 如果是白名单的地址，直接放行
                "/index.jsp",
                "/transfer.jsp",
                "/access.jsp",
                "/admin/manager.jsp"
        })
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // 获取session
        Account admin = (Account) req.getSession().getAttribute("admin");
        Account user = (Account) req.getSession().getAttribute("user");

        if (admin != null || user != null){
            // 登录了，直接放行
            chain.doFilter(request, response);
        }else {
            resp.sendRedirect("login.jsp");
        }
    }
}
```







![image-20230715124039800](JavaWeb.assets/image-20230715124039800.png)

![image-20230715124055902](JavaWeb.assets/image-20230715124055902.png)





![image-20230715124718932](JavaWeb.assets/image-20230715124718932.png)

```
目录

1、dao包

2、domain包

3、service包

4、utils包

5、web包

6、impl包

1、dao包
Dao：数据库相关操作 数据访问。
Dao 数据访问层：就是用来访问数据库实现数据的持久化（把内存中的数据永久保存到硬盘中 ）。

2、domain包
domain：实体类（entity）

3、service包
在此层做相应的业务逻辑处理。

4、utils包
工具包

5、web包
一般 存放 servlet 和 filter 。

6、impl包
impl是implement的简写，换言之，放置的应该是接口的实现类。

一般三层开发中，service层和dao层都会有接口及其实现类，为了项目能更好的管理和开发，一般将接口放一个包，实现类放置在这个包的子包中。

DTO(Data Transfer Object): —包命名为dto
简单一点理解就是在数据传输的时候用到的

栗子：

更id对商品进行查询，这里可以看到数据库中查询出来返回的结果是DO对象，要将数据进行传递就转化为DTO对象

   	public ProductDTO selectById(Long id) {
        ProductDO productDO = productMapper.selectProductById(id);
        if (ObjectUtils.isEmpty(productDO)) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(productDO, productDTO);
        return productDTO;
    }
```





[(81条消息) Java的Class.getClassLoader().getResourceAsStream()与Class.getResourceAsStream()理解_懒惰的gler的博客-CSDN博客](https://blog.csdn.net/u010858605/article/details/113663883)



#### Class.getClassLoader().getResourceAsStream()

Class是当前类的Class对象，Class.getClassLoader()是获取当前类的类加载器。类加载器的大概作用是当需要使用一个类时，加载该类的".class"文件，并创建对应的class对象，将class文件加载到虚拟机的内存。getResourceAsStream()是获取资源的输入流。**类加载器默认是从classPath路径加载资源。**

因此，当使用Class.getClassLoader.getResourceAsStream()加载资源时，是从classPath路径下进行加载，放在resources下的文件加载时不能加（“/”）。
![在这里插入图片描述](JavaWeb.assets/2021020610272528.png)

```java
InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("xx.properties");
```





[(81条消息) Tomcat与Servlet、JSP、JDK的版本适配问题（解决访问出现404和500的问题）_tomcat高版本兼容低版本吗_得过且过的勇者y的博客-CSDN博客](https://blog.csdn.net/qq_25046827/article/details/121490243)





# html和后端进行数据交互

注意: 这里的后端并没有分离

## 三种向后端提供数据的方法

### 一、表单提交

表单是HTML中最常用的交互方式之一。它可以向后端发送请求，并将表单中的数据传递给后端进行处理。表单可以使用GET或POST方法来提交数据，GET方法会将数据附加到URL的末尾，而POST方法则会将数据放在请求体中。的请求，并将用户名和密码作为参数传递给了后端。后端可以通过解析请求体中的数据来获取这些参数。

[ <%=request.getContextPath()%>是为了解决相对路径的问题，可返回站点的根路径。]()

```html
<%--->前端</%--->  
<form action="<%= request.getContextPath()%>/account?method=login" class="form-validate" id="loginFrom" method="post">
    //xxxxxxx
</form>  
<%--->                     
分析:
method=login  --> 相当于带的参数, 后端可以拿到
method="post" --> 使用doPost解析
</%--->    


<%--->后端</%--->    
@WebServlet("/account")  <%--必须对应--%>
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

 }
```

### 二、AJAX请求

AJAX是一种异步的数据交互方式，可以在不刷新页面的情况下向后端发送请求，并获取后端返回的数据。AJAX请求可以使用XMLHttpRequest对象来实现。

#### 2.1.概念

-  ASynchronous JavaScript And XML 异步的JavaScript 和 XML
-  异步和同步
   - 客户端和服务器端相互通信的基础上
   - 同步
     - 客户端必须等待服务器端的响应。在等待的期间客户端不能做其他操作
   - 异步
     - 客户端不需要等待服务器端的响应。在服务器处理请求的过程中，客户端可以进行其他的操作。
-  Ajax 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术。
   - 通过在后台与服务器进行少量数据交换，Ajax 可以使网页实现异步更新。这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。
   - 传统的网页（不使用 Ajax）如果需要更新内容，必须重载整个网页页面。
   - 提升用户的体验。

#### 2.2.实现方式

##### 2.2.1原生的JS实现方式

代码实现

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        //定义方法
        function  fun() {
            //发送异步请求
            //1.创建核心对象
            var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            //2. 建立连接
            /*
                参数：
                    1. 请求方式：GET、POST
                        * get方式，请求参数在URL后边拼接。send方法为空参
                        * post方式，请求参数在send方法中定义
                    2. 请求的URL：
                    3. 同步或异步请求：true（异步）或 false（同步）
             */
            xmlhttp.open("GET","ajaxServlet?username=tom",true);
            //3.发送请求
            xmlhttp.send();
            //4.接受并处理来自服务器的响应结果
            //获取方式 ：xmlhttp.responseText
            //什么时候获取？当服务器响应成功后再获取
            //当xmlhttp对象的就绪状态改变时，触发事件onreadystatechange。
            xmlhttp.onreadystatechange=function()
            {
                //判断readyState就绪状态是否为4，判断status响应状态码是否为200
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                   //获取服务器的响应结果
                    var responseText = xmlhttp.responseText;
                    alert(responseText);
                }
            }
        }
        
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="fun();">
    <input>
</body>
</html>
```

##### 2.2.2 JQeury实现方式

例子

```java
// html代码  
//使用$.ajax()发送异步请求
              $.ajax({
                url:"register" , // 请求路径
                type:"POST" , //请求方式
                data:{"userName":name,"passWord":passWord},
                success:function (data) {
                  window.location.href = "login.jsp"; // 重定向到login.jsp页面
                },//响应成功后的回调函数
                error:function () {
                  alert("出错啦...")
                },//表示如果请求响应出现错误，会执行的回调函数
                dataType:"text"//设置接受到的响应数据的格式
              });



// java代码
@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

	private AccountService accountService = new AccountServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("userName");
		String password = req.getParameter("passWord");

		try {
				Integer i = 	accountService.register(name, password);
				if (i == -1) {
					req.setAttribute("failMsg", "  注册失败");  //存
					req.getRequestDispatcher("login.jsp").forward(req, resp);  //回到登录页面
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
```

\1. $.ajax()

```html
* 语法：$.ajax({键值对});
			 //使用$.ajax()发送异步请求
	            $.ajax({
	                url:"ajaxServlet1111" , // 请求路径
	                type:"POST" , //请求方式
	                //data: "username=jack&age=23",//请求参数
	                data:{"username":"jack","age":23},
	                success:function (data) {
	                    alert(data);
	                },//响应成功后的回调函数
	                error:function () {
	                    alert("出错啦...")
	                },//表示如果请求响应出现错误，会执行的回调函数
	
	                dataType:"text"//设置接受到的响应数据的格式
	            });
```

测试案例：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        //定义方法
        function  fun() {
            //使用$.ajax()发送异步请求
            $.ajax({
                url:"ajaxServlet1111" , // 请求路径
                type:"POST" , //请求方式
                //data: "username=jack&age=23",//请求参数
                data:{"username":"jack","age":23},
                success:function (data) {
                    alert(data);
                },//响应成功后的回调函数
                error:function () {
                    alert("出错啦...")
                },//表示如果请求响应出现错误，会执行的回调函数
                dataType:"text"//设置接受到的响应数据的格式
            });
        } 
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="fun();">
    <input>
</body>
</html>
```

\2. $.get()：发送get请求

```cobol
            * 语法：$.get(url, [data], [callback], [type])
				* 参数：
					* url：请求路径
					* data：请求参数
					* callback：回调函数
					* type：响应结果的类型
```

测试案例：

```html
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        //定义方法
        function  fun() {
            $.get("ajaxServlet",{username:"rose"},function (data) {
                alert(data);
            },"text");
        }
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="fun();">
    <input>
</body>
</html>
```

\3. $.post()：发送post请求

```java
            * 语法：$.post(url, [data], [callback], [type])
				* 参数：
					* url：请求路径
					* data：请求参数
					* callback：回调函数
					* type：响应结果的类型
```

测试案例：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        
        //定义方法
        function  fun() {
            $.post("ajaxServlet",{username:"rose"},function (data) {
                alert(data);
            },"text");
        }
    </script>
</head>
<body>
    <input type="button" value="发送异步请求" onclick="fun();">
</body>
</html>
```

4.jQuery 使用 JSONP

```javascript
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>JSONP 实例</title>
    <script src="https://cdn.static.runoob.com/libs/jquery/1.8.3/jquery.js"></script>    
</head>
<body>
<div id="divCustomers"></div>
<script>
$.getJSON("https://www.runoob.com/try/ajax/jsonp.php?jsoncallback=?", function(data) {
    
    var html = '<ul>';
    for(var i = 0; i < data.length; i++)
    {
        html += '<li>' + data[i] + '</li>';
    }
    html += '</ul>';
    
    $('#divCustomers').html(html); 
});
</script>
</body>
</html>
 
//向服务端发送删除指令
$.getJSON("userServlet?method=delete", {id: data.id}, function (result) {
    if (result.code == 0) {
        obj.del(); //删除对应行（tr）的DOM结构
        layer.alert("删除成功", {icon: 1, title: "提示"}, function (i) {
            layer.close(i);
            layer.close(index);
        });
        table.reload('test', {
            page: {
                curr: 1
            }
        })
    }
})
```

对应的servlet代码：

```java
@WebServlet("/ajaxServlet")
public class AjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数
        String username = request.getParameter("username");
 
       /* //处理业务逻辑。耗时
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //2.打印username
        System.out.println(username);
 
        //3.响应
        response.getWriter().write("hello : " + username);
 
 
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```

##### 2.2.3 html的form表单实现ajax请求（重要）

```javascript
<!--
HTML页面三种方法加载ajax请求
1.去掉form标签
2.使用input type="submit"  需要设置<form onsubmit="return false">或者input type="button"
3.使用<button>标签，需要设置type="button"
-->
 
//在页面加载完成后
$(function () {
    $("#btn").click(function () {
        //获取username文本输入框的值
        // let username = document.getElementById("username").value;
        // let password = document.getElementById("password").value;
        //发送ajax请求
        $.ajax({
            url: "loginAjaxServlet",
            // data: {"username": username, "password": password},
            //序列化表单数据，根据input的name属性和value属性进行序列化，key(name):value(value),序列化之后自动变成json
            data:$("#myform").serialize(),
            dataType: "JSON",
            type: "POST",
            //返回的数据dataJSON格式
            success: function (data) {
                //具体操作
                if (data.code == 0) {
                    window.location.href = "showUser.html"
                } else {
                    alert(data.msg)
                }
            }
        });
    });
});
```

例子

```java
// html代码  
//使用$.ajax()发送异步请求
              $.ajax({
                url:"register" , // 请求路径
                type:"POST" , //请求方式
                data:{"userName":name,"passWord":passWord},
                success:function (data) {
                  window.location.href = "login.jsp"; // 重定向到login.jsp页面
                },//响应成功后的回调函数
                error:function () {
                  alert("出错啦...")
                },//表示如果请求响应出现错误，会执行的回调函数
                dataType:"text"//设置接受到的响应数据的格式
              });



// java代码
@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

	private AccountService accountService = new AccountServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("userName");
		String password = req.getParameter("passWord");

		try {
				Integer i = 	accountService.register(name, password);
				if (i == -1) {
					req.setAttribute("failMsg", "  注册失败");  //存
					req.getRequestDispatcher("login.jsp").forward(req, resp);  //回到登录页面
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
```

##### 2.2.4html页面之间传递参数（重要）

Js获取参数方法

```javascript
//根据传递过来的参数name获取对应的值
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}
 
1. var r = location.search.substr(1).match(reg);
location.search.substr(1) 用于获得 URL 的查询部分 (?后面的内容).
比如地址栏中的地址是
r[0] = “&name=QQ&” 是整个正则表达式匹配到的内容
r[1] = “&” 是正则表达式中第一个括号匹配到的内容,即 (^|&) 匹配到的内容
r[2] = “QQ” 是正则表达式中第二个括号匹配到的内容,即 ([^&]*)匹配到的内容
r[3] = “&” 是正则表达式中最后一个括号匹配到的内容即 (&|$) 匹配到的内容
```

测试案例：

```javascript
//传值案例
window.location.href = "updateUser.html?id=" + id;
 
//取值页面的代码
<script src="./js/getParameter.js"></script>
</style>
<script>
    $(function () {
        let id = getParameter("id");
        })
</script>
```



### 三、WebSocket

WebSocket是一种全双工的数据交互方式，可以在客户端和服务器之间建立一个持久的连接，并实现实时通信。WebSocket可以使用WebSocket对象来实现。

```
```ew WebSocket('ws://localhost:8080');openction() {d('Hello, World!');

};messagectiont) {t.data);
```

上面的代码中，我们使用WebSocket对象向后端发送了一个Hello, World!的消息，并在获取到后端返回的数据后弹出了一个提示框。后端可以通过监听WebSocket对象的事件来获取客户端发送的数据，并返回数据给客户端。

HTML可以通过表单提交、AJAX请求和WebSocket等方式与后端进行数据交互。不同的方式适用于不同的场景，开发者可以根据实际需求选择合适的方式进行数据交互。



## 两种向前端提供数据的方法

```java
// 后端
// 1 转发  
req.setAttribute("failMsg", "  密码错误");  //failMsg=密码错误
req.getRequestDispatcher("login.jsp").forward(req, resp);  //发给前端login页面
 
//2  重定向,跳转到其他页面,带上数据admin
session.setAttribute( "admin", accountDto.getAccount());  // session保存数据
resp.sendRedirect( "/netBank/admin/manager.jsp");


// 前端
// 在html中使用EL表达式可以拿到数据/ 或者使用jsp脚本片段
<!-- Assuming you have a list of accountEnquiry objects -->
<%
List<Bankcard> list = (List<Bankcard>) request.getAttribute("list");
%>
<% for (Bankcard accountEnquiry : list) { %>
<tr>
<td><%= accountEnquiry.getCardid() %></td>
<td><%= accountEnquiry.getCardnumber() %></td>
<%--<td><%= accountEnquiry.getStatus() %></td>--%>
<%
int status = accountEnquiry.getStatus();
if (status == 1) {
%>
<td>未激活</td>
<%
} else {
%>
<td>激活</td>
<%
}
%>
```

[(81条消息) Ajax异步请求使用方法（详细）_爱笑的tiger的博客-CSDN博客](https://blog.csdn.net/weixin_45060598/article/details/120686932)



# 重定向方法sendRedirect()中的路径问题

**一、使用绝对路径**
  response.sendRedirect(“绝对路径的URL地址”)；
  如http://www.baidu.com，直接跳转到该地址(注意，http头不可省略)。
  也可以使用本地服务器绝对路径地址http://localhost:8080/myProject/123.mp3。
  这个是最简单的，可以访问到任何你可以访问的HTTP资源。
二、使用相对路径(这里以本地服务器地址为例）
  1、路径前面没有斜线“/”
  如response.sendRedirect(example/index.html);
  容器会根据原先请求的URL建立一个完整的路径，如原先路径是http://localhost:8080/myProject/app/index.html , 那么会直接访问到http://localhost:8080/myProject/app/example/index.html
  2、路径前面有斜线“/” 
  如response.sendRedirect(/example/index.html);
  容器会根据原先请求的URL，相对于Web应用本身(不带虚拟地址)，建立一个完整的路径，如原先路径是http://localhost:8080/myProject/app/index.html , 那么会直接访问到http://localhost:8080/example/index.html

路径表示法是一个要了解的东西，这里有文章讲得很好：文件路径常用表示方法，点我查看

3、一些细节问题
假如一开始访问到的是http://localhost:8080/myProject/index.html , 然后该页面通过GET请求访问到对应的Servlet, 此时重定向的相对路径不是相对于Servlet而言的，而是相对于原先请求路径而言（或者说相对于用户浏览器上面显示的页面地址而言），即相对于：
http://localhost:8080/myProject/index.html 而言。

```
<web-app>
  <display-name>Archetype Created Web Application</display-name>

<!-- http://localhost:8080/netBank 请求页面 -->
      <welcome-file-list>
          <welcome-file>login.jsp</welcome-file>
      </welcome-file-list>
</web-app>


//login.jsp ---> http://localhost:8080/netBank  //项目路径
```

一、**绝对路径**：包括协议名称、主机地址、端口、web项目名称等的完整请求路径。

例如：

```
$.ajax({
    url:"http://localhost:8080/webname/test"
});
```

好处：比如在webA项目中的ajax需要请求webB项目中的服务，则必须使用绝对路径。

坏处：使用绝对路径要求古地理更web项目的名称，如果webB项目重命名了，则对应的ajax请求都需要修改。

二、**相对路径**：不需要协议名、主机地址、端口、web项目名称，只需要请求的路径。

假设：

项目路径(+虚拟路径)：http://localhost:8080/webname

页面路径：/webname/index.html（A页面），/webname/test/test.html（B页面）

请求路径：/request/ajaxtest，  request/ajaxtest

## 路径前面有斜线“/”(服务器的根路径)

1、如果请求路径以根路径开头，则无论什么ajax在什么页面，该请求都是相对于服务器的根路径(http://localhost:8080/)，最后的请求路径都是：http://localhost:8080/request/ajaxtest

例如：

```
$.ajax({
    url :"/request/ajaxtest"
});

结果:
http://localhost:8080/request/ajaxtest

//
req.setAttribute("list", this.qccountEnquiryService.list());
req.getRequestDispatcher("admin/adminmanager.jsp").forward(req, resp);
```

原因：以"/"开头，是表示该请求基于从服务器的根路径，即不是相对于html的路径。

**特别的:**

```java
// http://localhost:8080/netBank   项目路径
//@WebServlet(name = "AccountEnquiryServlet", value = "/accountEnquiry") 这个是相对于跟路径的项目路径  http://localhost:8080/netBank/accountEnquiry
```

##  路径前面没有斜线“/”(html所在的路径)

2、如果请求不以根路径开头（常见），则该请求路径是相对于html所在的路径的。

```java
a、假如请求在A页面/webname/index.html页面，最终的请求路径是：http://localhost:8080/webname/request/ajaxtest。
 
    $.ajax({
        url:"request/ajaxtest"
    });
```

原因：index.html页面对应的路径是"/webname/"，所以将url跟在这个路径下就是最终的请求路径。

```java
 b、假如请求在B页面/webname/test/test.html页面，最终的请求路径是：http://localhost:8080/webname/test/request/ajaxtest。
   
    $.ajax({
        url:"request/ajaxtest"
    });
```

原因：test.html页面对应的路径是“/webname/test/”，所以将url要跟在test这一级下面。

![image-20230716153716485](JavaWeb.assets/image-20230716153716485.png)

/

![image-20230716204557799](JavaWeb.assets/image-20230716204557799.png)

![image-20230716204632320](JavaWeb.assets/image-20230716204632320.png)



# 前端后端交互请求的几种方式



以下是常见的请求方式及其使用例子：

常见的请求方式有以下几种:

\1. GET：获取资源，[请求参数](https://so.csdn.net/so/search?q=请求参数&spm=1001.2101.3001.7020)在URL中传递，一般用于数据查询操作。
\2. POST：提交数据，请求参数在请求体中传递，一般用于数据新增和修改操作。
\3. PUT：更新数据，请求参数在请求体中传递，一般用于数据更新操作。
\4. DELETE：删除数据，请求参数在URL中传递，一般用于数据删除操作。

一、[GET请求](https://so.csdn.net/so/search?q=GET请求&spm=1001.2101.3001.7020)

1.前端使用例子：

```
axios.get('/api/getUserInfo?id=123')
 .then(response => {
  console.log(response.data);
 })
 .catch(error => {
  console.log(error);
 });
```

2.后端使用例子：

```
@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
@ResponseBody
public User getUserInfo(@RequestParam("id") String id) {
  User user = userService.getUserById(id);
  return user;
}
```

二、POST请求

1.前端使用例子：

```
axios.post('/api/login', {
  username: 'admin',
  password: '123456'
 })
 .then(response => {
  console.log(response.data);
 })
 .catch(error => {
  console.log(error);
 });
```

2.后端使用例子：

```
@RequestMapping(value = "/login", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> login(@RequestBody Map<String, Object> params) {
  String username = (String) params.get("username");
  String password = (String) params.get("password");
  // 验证用户名和密码
  // ...
  Map<String, Object> result = new HashMap<>();
  result.put("code", 200);
  result.put("msg", "登录成功");
  return result;
}
```

三、PUT请求

1.前端使用例子：

```
axios.put('/api/updateUserInfo', {
  id: 123,
  name: '张三',
  age: 18
 })
 .then(response => {
  console.log(response.data);
 })
 .catch(error => {
  console.log(error);
 });
```

2.后端使用例子：

```
@RequestMapping(value = "/updateUserInfo", method = RequestMethod.PUT)
@ResponseBody
public Map<String, Object> updateUserInfo(@RequestBody User user) {
  // 更新用户信息
  Map<String, Object> result = new HashMap<>();
  result.put("code", 200);
  result.put("msg", "更新成功");
  return result;
}
```

四、DELETE请求

1.前端使用例子：

```
axios.delete('/api/deleteUser?id=123')
 .then(response => {
  console.log(response.data);
 })
 .catch(error => {
  console.log(error);
 });
```

2.后端使用例子：

```
@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
@ResponseBody
public Map<String, Object> deleteUser(@RequestParam("id") String id) {
  // 删除用户信息
  // ...
  Map<String, Object> result = new HashMap<>();
  result.put("code", 200);
  result.put("msg", "删除成功");
  return result;
}
```





# javaweb相关依赖

```xml
<!-- servlet: 运行在Web服务器或应用服务器上的程序(必要)-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
<!-- jsp: 就是在HTML里面嵌入Java代码-->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.3</version>
    <scope>provided</scope>
</dependency>
<!-- standard标签库 -->
<dependency>
    <groupId>taglibs</groupId>
    <artifactId>standard</artifactId>
    <version>1.1.2</version>
</dependency>
```

**启用EL表达式的解析**

```jsp
<%@ page isELIgnored="false" %>
```

