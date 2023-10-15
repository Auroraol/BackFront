# IDEA显示service服务

[IDEA显示services服务_idea 显示service_追求卓越583的博客-CSDN博客](https://blog.csdn.net/zhuiqiuzhuoyue583/article/details/128952012)

<img src="idea使用.assets/885a911d8e7646bf897ab4ea92b9dbc3.png" alt="img" style="zoom:67%;" />





# IDEA test程序无法输入 This view is read-only 解决办法

### 问题

@test程序无法输入，按回车键显示如下：
![在这里插入图片描述](idea使用.assets/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAcGVhcno=,size_20,color_FFFFFF,t_70,g_se,x_16.png)

### 解决办法

在IDEA ***Help -> Edit Custom VM Options*** 中**添加**如下代码，然后重启IDEA即可。

```bash
-Deditable.java.test.console=true
```



# idea重启

如下图所示
（1）通过File–>Invalidate Caches进入重启窗口

![img](idea使用.assets/1005434-20220428105519698-1100372645.png)

 

 （2）选择自己所需要的重启方式，四个按钮，一共三种重启方式：

![img](idea使用.assets/1005434-20220428105535985-203093428.png)