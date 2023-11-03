package com.itheima.test;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.Reader;
	import java.util.List;
	import org.apache.ibatis.io.Resources;
	import org.apache.ibatis.session.SqlSession;
	import org.apache.ibatis.session.SqlSessionFactory;
	import org.apache.ibatis.session.SqlSessionFactoryBuilder;
	import org.junit.Test;
	import com.itheima.pojo.User;
	/**
	 * 入门程序测试类
	 */
	public class UserTest {
		/**
		 * 根据用户编号查询用户信息
		 */
		@Test
		public void userFindByIdTest()  {
        //读取文件名
        String resources = "mybatis-config.xml";
        //创建流
        Reader reader = null;
        try {
            //读取mybatis-config.xml文件内容到reader对象中
            reader = Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化mybatis数据库,创建SqlSessionFactory类的实例
        SqlSessionFactory sqlMapper = new
                SqlSessionFactoryBuilder().build(reader);
        //创建SqlSession实例
        SqlSession session = sqlMapper.openSession();
        //传入参数查询，返回结果
        User user = session.selectOne("findById", 2);
        //输出结果
        System.out.println(user.getUname());
        //关闭session
        session.close();
		}
	}
