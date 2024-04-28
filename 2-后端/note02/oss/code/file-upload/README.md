# file-upload
![img.png](img.png)

文件上传到服务器某个目录，然后SpringBoot配置虚拟路径，映射到此目录。

## 上传测试

![image-20240422213951346](README.assets/image-20240422213951346.png)



查看文件夹，已上传成功

![image-20240422214012848](README.assets/image-20240422214012848.png)

## 访问测试

将上传接口返回的path拼接上域名或者ip端口、访问 http://localhost:8080/files/202302021010345680.jpg，得到：

![image-20240422214134744](README.assets/image-20240422214134744.png)
