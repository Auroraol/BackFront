package com.itheima.test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itheima.pojo.Book;
import com.itheima.pojo.Orders;
import com.itheima.pojo.Person;
import com.itheima.pojo.Student;
import com.itheima.pojo.Users;
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

//	    @Test
//	    public void findAllStudentTest() {
//	        // SqlSession执行映射文件中定义的SQL，并返回映射结果
//	        List<Student> list =
//	                sqlSession.selectList("com.itheima.mapper.StudentMapper." +
//	                        "findAllStudent");
//	        for (Student student : list) {
//	            System.out.println(student);
//	        }
//	    }
	    
//	/**
//     * 嵌套查询
//     */
//    @Test
//    public void findPersonByIdTest() {
//        // 1、通过工具类获取SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.使用MyBatis嵌套查询的方式查询id为1的人的信息
//        Person person = session.selectOne("com.itheima.mapper."
//                + "PersonMapper.findPersonById", 1);
//        // 3、输出查询结果信息
//        System.out.println(person);
//        // 4、关闭SqlSession
//        session.close();
//    }

    /**
     * 嵌套结果
     */
//    @Test
//    public void findPersonByIdTest2() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.使用MyBatis嵌套结果的方法查询id为1的人员信息
//        Person person = session.selectOne("com.itheima.mapper."
//                + "PersonMapper.findPersonById2", 1);
//        // 3.输出查询结果信息
//        System.out.println(person);
//        // 4.关闭SqlSession
//        session.close();
//    }
//
//    /**
//     * 一对多
//     */
//    @Test
//    public void findUserTest() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.查询id为1的用户信息
//        Users users = session.selectOne("com.itheima.mapper."
//                + "UsersMapper.findUserWithOrders", 1);
//        // 3.输出查询结果信息
//        System.out.println(users);
//        // 4.关闭SqlSession
//        session.close();
//    }
//
//    /**
//     * 多对多
//     */
//    @Test
//    public void findOrdersTest() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.查询id为1的订单中的商品信息
//        Orders orders = session.selectOne("com.itheima.mapper."
//                + "OrdersMapper.findOrdersWithPorduct2", 1);
//        // 3.输出查询结果信息
//        System.out.println(orders);
//        // 4.关闭SqlSession
//        session.close();
//    }
//
//    /**
//     * 根据id查询图书信息—-一级缓存
//     */
//    @Test
//    public void findBookByIdTest1() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.使用session1查询id为1的图书的信息
//        Book book1 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.输出查询结果信息
//        System.out.println(book1.toString());
//        // 4.使用session1查询id为1的图书的信息
//        Book book2 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 5.输出查询结果信息
//        System.out.println(book2.toString());
//        // 6.关闭SqlSession
//        session.close();
//    }
//
//    @Test
//    public void findBookByIdTest2() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.使用session查询id为1的图书的信息
//        Book book1 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.输出查询结果信息
//        System.out.println(book1.toString());
//        Book book2 = new Book();
//        book2.setId(3);
//        book2.setBookName("MySQL数据库入门");
//        book2.setPrice(40.0);
//        // 4.使用session更新id为3的图书的信息
//        session.update("com.itheima.mapper."
//                + "BookMapper.updateBook", book2);
//        session.commit();
//        Book book3 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 5.输出查询结果信息
//        System.out.println(book1.toString());
//        // 6.关闭SqlSession
//        session.close();
//    }
//
//    /**
//     * 根据id查询图书信息—-二级缓存
//     */
    @Test
    public void findBookByIdTest3() {
        // 1.通过工具类生成SqlSession对象
        SqlSession session1 = MyBatisUtils.getSession();
        SqlSession session2 = MyBatisUtils.getSession();
        // 2.使用session1查询id为1的图书的信息
        Book book1 = session1.selectOne("com.itheima.mapper."
                + "BookMapper.findBookById", 1);
        // 3.输出查询结果信息
        System.out.println(book1.toString());
        // 4.关闭SqlSession1
        session1.close();
        // 5.使用session2查询id为1的图书的信息
        Book book2 = session2.selectOne("com.itheima.mapper."
                + "BookMapper.findBookById", 1);
        // 6.输出查询结果信息
        System.out.println(book2.toString());
        // 7.关闭SqlSession2
        session2.close();
    }
//
//    @Test
//    public void findBookByIdTest4() {
//        // 1.通过工具类生成SqlSession对象
//        SqlSession session1 = MyBatisUtils.getSession();
//        SqlSession session2 = MyBatisUtils.getSession();
//        SqlSession session3 = MyBatisUtils.getSession();
//        // 2.使用session1查询id为1的图书的信息
//        Book book1 = session1.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.输出查询结果信息
//        System.out.println(book1.toString());
//        // 4.关闭SqlSession
//        session1.close();
//        Book book2 = new Book();
//        book2.setId(2);
//        book2.setBookName("Java Web程序开发进阶");
//        book2.setPrice(45.0);
//        // 5.使用session2更新id为2的图书的信息
//        session2.update("com.itheima.mapper."
//                + "BookMapper.updateBook", book2);
//        session2.commit();
//        session2.close();
//        // 6.使用session3查询id为1的图书的信息
//        Book book3 = session3.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 7.输出查询结果信息
//        System.out.println(book3.toString());
//        // 8.关闭SqlSession
//        session3.close();
//    }


    @After
    public void destory() {
        //提交事务
        sqlSession.commit();
        //关闭事务
        sqlSession.close();
    }
}
