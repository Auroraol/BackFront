官网: [hutool: 🍬小而全的Java工具类库，使Java拥有函数式语言般的优雅 (gitee.com)](https://gitee.com/dromara/hutool)

参考文档: [日期时间工具-DateUtil | Hutool](https://doc.hutool.cn/pages/DateUtil/#开始和结束时间)

如果你想像Spring-Boot一样引入Hutool，再由子模块决定用到哪些模块，你可以在父模块中加入：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-bom</artifactId>
            <version>${hutool.version}</version>
            <type>pom</type>
            <!-- 注意这里是import -->
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

在子模块中就可以引入自己需要的模块了：

```xml
<dependencies>
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-http</artifactId>
    </dependency>
</dependencies>
```

> 使用import的方式，只会引入hutool-bom内的dependencyManagement的配置，其它配置在这个引用方式下完全不起作用。

![image-20240215173330883](%E5%B7%A5%E5%85%B7%E5%BA%93.assets/image-20240215173330883.png)