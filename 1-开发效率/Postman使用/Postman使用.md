## PostMan测试

![image-20230801214507895](Postman使用.assets/image-20230801214507895.png)

以广东培正学院强智教务系统为例：http://qzjw.peizheng.edu.cn/jsxsd/

步骤：
1.根据账户密码，去发起登录请求
请求地址：http://qzjw.peizheng.edu.cn/jsxsd/xk/LoginToXk
请求方式：PSOT
![在这里插入图片描述](Postman使用.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dxMjMyMw==,size_16,color_FFFFFF,t_70.png)
2.**因为强智还使用的JSP，所以基本都是 后端Session来存储用户信息，浏览器存入用户的cookie，在下一次请求当中将Cookie放入请求头。**

此处找到PostMan的heade信息：找到Set-Cookie
![在这里插入图片描述](Postman使用.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dxMjMyMw==,size_16,color_FFFFFF,t_70-16908976286561.png)

3.如果登录成功的话，这一串就是后端返回的Cookie信息了。有了Cookie信息，就相当于有了强智服务器识别你身份的钥匙，后续需要的各种操作，如查成绩，查信息，查课表，抢课，考勤等都可以使用。
拿到这个信息去继续请求：
请求地址：http://qzjw.peizheng.edu.cn/jsxsd/framework/xsMain_new.jsp?t1=1
请求方式：GET
![在这里插入图片描述](Postman使用.assets/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dxMjMyMw==,size_16,color_FFFFFF,t_70-16908976286562.png)

分析发现

```
请求地址：以广东培正学院强智教务系统为例：http://qzjw.peizheng.edu.cn/jsxsd/
请求方式：PSOT
账号密码通过base64加密
```

ccu教务系统分析:

**登录页面**

<img src="Postman使用.assets/image-20230802123759652.png" alt="image-20230802123759652" style="zoom:67%;" />

![image-20230803202242841](Postman使用.assets/image-20230803202242841.png)

注意: view source 是请求里需要的.  view parse解析源码的里的数据展示

![image-20230802123657256](Postman使用.assets/image-20230802123657256.png)

得到Cookie:

```c
JSESSIONID     B884FAC21A1688B9CF169EB5BDE0614B
SERVERID2      Serverl
```

课程表页面

https://cdjwc.webvpn.ccu.edu.cn/jsxsd/xskb/xskb_list.do

![image-20230802172009356](Postman使用.assets/image-20230802172009356.png)

![image-20230802173055354](Postman使用.assets/image-20230802173055354.png)



## Params、form-data、x-www-form-urlencoded、raw、binary的区别及后端接收方式

1、Params 的请求参数会出现在url中，为key=value格式，后端可以用@RequestParam接收。

![image-20230803101430382](Postman使用.assets/image-20230803101430382.png)

 2、form-data的请求是在body中，为key=value格式，同时可以传文件，Content-Type为multipart/form-data，后端可以用@RequestParam接收。

![image-20230803101420155](Postman使用.assets/image-20230803101420155.png)

3、x-www-form-urlencoded的请求是在body中， 为key=value格式，无法传文件，Content-Type为application/x-www-form-urlencoded。

4、raw(不同api管理工具范围有细微区别)的请求是在body中，一般包含text、js、json等，可按照不同类型上传任意格式的文本，后端可以用@RequestBody接收。

![image-20230803101409662](Postman使用.assets/image-20230803101409662.png)

5、binary用于上传二进制数据，通常用来上传文件且一次只能上传一个文件。

# POST请求报错：“status“: 415, “error“: “Unsupported Media Type“, “message“: “Content type

## 使用Postman测试post请求时报错:

错误如下：

![img](Postman使用.assets/20201004143926853.png)

错误码：415表示不支持内容类型。

## 错误原因：

  我使用的是body传参，如果使用Body传参的话，就需要在设置Headers。
找了一下错误原因是没有正确设置内容类型标头。（下图为错误之处）

![img](Postman使用.assets/20201004144110109.png)

## 解决办法：

重新建一个请求，需要在Headers里添加如下配置：

![img](Postman使用.assets/20201004144403798.png)





# Postman如何导入和导出接口文件



## **生成****json文件导入的方式**

### 2.1、导出json文件

![img](Postman%E4%BD%BF%E7%94%A8.assets/8e49c62f937543f5a84dcb1386ab1ea4.png)



选择版本，导出

![img](Postman%E4%BD%BF%E7%94%A8.assets/5613b8ca3919492ca86a41f3669a6ef5.png)



选择导出文件存放位置

![img](Postman%E4%BD%BF%E7%94%A8.assets/d542d790fdee4430af87794f4f6ed8c5.png)

### 

### 2.2、导入json文件

![img](Postman%E4%BD%BF%E7%94%A8.assets/0f1f15aa66334ff9984942066a17c5ad.png)



![image-20240508223418054](Postman%E4%BD%BF%E7%94%A8.assets/image-20240508223418054.png)

选择导出的json文件进行导入

![img](Postman%E4%BD%BF%E7%94%A8.assets/970ccd291a7f40299ef9e9d08f86a067.png)

# Postman 导入导出 curl 命令详细步骤

![img](Postman%E4%BD%BF%E7%94%A8.assets/6e231ea76c87e57e0974ea1b9f31a948.png)



[Postman 导入curl 、导出成curl、导出成对应语言代码-CSDN博客](https://blog.csdn.net/qq_41767116/article/details/121463916)
