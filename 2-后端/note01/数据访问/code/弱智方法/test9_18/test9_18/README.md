
```c
test9_18
├─ README.md
├─ src
│  ├─ com
│  │  └─ itheima
│  │     ├─ mapper
│  │     │  ├─ IdCardMapper.xml
│  │     │  ├─ PersonMapper.xml
│  │     │  └─ UsersMapper.xml
│  │     ├─ pojo
│  │     │  ├─ IdCard.java
│  │     │  ├─ Orders.java
│  │     │  ├─ Person.java
│  │     │  └─ User.java
│  │     ├─ test
│  │     │  └─ MybatisAssociatedTest.java
│  │     └─ utils
│  │        └─ MyBatisUtils.java
│  ├─ db.properties
│  ├─ log4j.properties
│  └─ mybatis-config.xml
└─ WebContent
   ├─ META-INF
   │  └─ MANIFEST.MF
   └─ WEB-INF
      └─ lib
         ├─ ant-1.9.6.jar
         ├─ ant-launcher-1.9.6.jar
         ├─ asm-5.1.jar
         ├─ cglib-3.2.4.jar
         ├─ commons-logging-1.2.jar
         ├─ javassist-3.21.0-GA.jar
         ├─ log4j-1.2.17.jar
         ├─ log4j-api-2.3.jar
         ├─ log4j-core-2.3.jar
         ├─ mybatis-3.4.2.jar
         ├─ mysql-connector-java-5.1.40-bin.jar
         ├─ ognl-3.1.12.jar
         ├─ slf4j-api-1.7.22.jar
         └─ slf4j-log4j12-1.7.22.jar
```





 **db.properties文件**

配置sql

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/数据库名
jdbc.username=账号
jdbc.password=密码
```





```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 加载配置文件 db.properties-->
    <properties resource="db.properties"/>
    <settings>
        <setting name="lazyLoadingEnabled" value="true"/>
        <setting name="aggressiveLazyLoading" value="false"/>
        <setting name="cacheEnabled" value="true"/>
    </settings>

    <!--使用扫描包的形式定义别名, 需要扫描的包名:com.itheima.pojo -->
    <typeAliases>
        <package name="com.itheima.pojo"/>
    </typeAliases>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
					<property name="driver" value="${jdbc.driver}" />
					<property name="url" value="${jdbc.url}" />>
					<property name="username" value="${jdbc.username}" />
					<property name="password" value="${jdbc.password}" />
            </dataSource>
        </environment>
    </environments>
    <!-- mapping文件路径配置, 注册映射文件 -->
    <mappers>
		<mapper resource="com/itheima/mapper/IdCardMapper.xml" />
		<mapper resource="com/itheima/mapper/PersonMapper.xml" />
		<mapper resource="com/itheima/mapper/UsersMapper.xml" />
    </mappers>
</configuration>
```

映射

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- namespace表示命名空间, 调用的com.itheima.mapper.UserMapper -->
<mapper namespace="com.itheima.mapper.UsersMapper">
	<!--参数名要和数据实体对应 -->
	<select id="findUserWithOrders" parameterType="Integer"  resultMap="UserWithOrdersResult">
		SELECT u.*,o.id as orders_id,o.number 
		from tb_user u,tb_orders o 
		WHERE u.id=o.user_id 
        and u.id=#{id}
	</select>
	<!--接受对应sql语句的返回结果-->
	<!--User是类, colum是表查出的字段-->
	<resultMap type="User" id="UserWithOrdersResult">
		<id property="id" column="id"/>
		<result property="username" column="username"/>
		<result property="address" column="address"/>
		<!-- 一对多关联映射：collection  ofType表示属性集合中元素的类型，List<Orders>属性即Orders类 -->
		<collection property="ordersList" ofType="Orders">
			<id property="id" column="orders_id"/>
			<result property="number" column="number"/>
		</collection>
	</resultMap>
</mapper>

```

测试类

```java
package com.itheima.test;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.itheima.pojo.User;
import com.itheima.utils.MyBatisUtils;


public class MybatisAssociatedTest {
	  private SqlSessionFactory sqlSessionFactory;
	    private SqlSession sqlSession;

	    @Before
	    public void init() {
	        //定义读取文件名
	        String resources = "mybatis-config.xml";
	        //创建流
	        Reader reader = null;
	        try {
	            //读取mybatis-config.xml文件到reader对象中
	            reader = Resources.getResourceAsReader(resources);
	            //初始化mybatis,创建SqlSessionFactory类的对象
	            SqlSessionFactory sqlMapper = new
	                    SqlSessionFactoryBuilder().build(reader);
	            //创建session对象
	            sqlSession = sqlMapper.openSession();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Test
	    public void findPersonByIdTest() {
	        // 1.通过工具类生成qlSession对象
	        SqlSession session = MyBatisUtils.getSession();
	        // 2.使用MyBatis嵌套查询的方式查询id为1的信息 
	        Person person = session.selectOne("com.itheima.mapper." 
	                                   + "PersonMapper.findPersonById", 1);
	        // 3.输出查询结果信息
	        System.out.println(person);
	        // 4.关闭qlSession
	        session.close();
	    }
	    
	    /**
	     * 一对多??	
	     */
	    @Test
	    public void findUserTest() {
	        // 1.通过工具类生成qlSession对象
	    	 SqlSession session = MyBatisUtils.getSession();
	        // 2.查询id为1的信息 
	        User user = session.selectOne("com.itheima.mapper."
	                                + "UserMapper.findUserWithOrders", 1);
	        // 3.输出查询结果信息
	        System.out.println(user);
	        // 4.关闭qlSession
	        session.close();
	    }
}

```

