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
