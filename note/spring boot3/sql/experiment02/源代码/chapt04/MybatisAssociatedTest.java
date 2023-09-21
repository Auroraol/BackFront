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
	        //�����ȡ�ļ���
	        String resources = "mybatis-config.xml";
	        //������
	        Reader reader = null;
	        try {
	            //��ȡmybatis-config.xml�ļ���reader������
	            reader = Resources.getResourceAsReader(resources);
	            //��ʼ��mybatis,����SqlSessionFactory��Ķ���
	            SqlSessionFactory sqlMapper = new
	                    SqlSessionFactoryBuilder().build(reader);
	            //����session����
	            sqlSession = sqlMapper.openSession();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

//	    @Test
//	    public void findAllStudentTest() {
//	        // SqlSessionִ��ӳ���ļ��ж����SQL��������ӳ����
//	        List<Student> list =
//	                sqlSession.selectList("com.itheima.mapper.StudentMapper." +
//	                        "findAllStudent");
//	        for (Student student : list) {
//	            System.out.println(student);
//	        }
//	    }
	    
//	/**
//     * Ƕ�ײ�ѯ
//     */
//    @Test
//    public void findPersonByIdTest() {
//        // 1��ͨ���������ȡSqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.ʹ��MyBatisǶ�ײ�ѯ�ķ�ʽ��ѯidΪ1���˵���Ϣ
//        Person person = session.selectOne("com.itheima.mapper."
//                + "PersonMapper.findPersonById", 1);
//        // 3�������ѯ�����Ϣ
//        System.out.println(person);
//        // 4���ر�SqlSession
//        session.close();
//    }

    /**
     * Ƕ�׽��
     */
//    @Test
//    public void findPersonByIdTest2() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.ʹ��MyBatisǶ�׽���ķ�����ѯidΪ1����Ա��Ϣ
//        Person person = session.selectOne("com.itheima.mapper."
//                + "PersonMapper.findPersonById2", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(person);
//        // 4.�ر�SqlSession
//        session.close();
//    }
//
//    /**
//     * һ�Զ�
//     */
//    @Test
//    public void findUserTest() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.��ѯidΪ1���û���Ϣ
//        Users users = session.selectOne("com.itheima.mapper."
//                + "UsersMapper.findUserWithOrders", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(users);
//        // 4.�ر�SqlSession
//        session.close();
//    }
//
//    /**
//     * ��Զ�
//     */
//    @Test
//    public void findOrdersTest() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.��ѯidΪ1�Ķ����е���Ʒ��Ϣ
//        Orders orders = session.selectOne("com.itheima.mapper."
//                + "OrdersMapper.findOrdersWithPorduct2", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(orders);
//        // 4.�ر�SqlSession
//        session.close();
//    }
//
//    /**
//     * ����id��ѯͼ����Ϣ��-һ������
//     */
//    @Test
//    public void findBookByIdTest1() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.ʹ��session1��ѯidΪ1��ͼ�����Ϣ
//        Book book1 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(book1.toString());
//        // 4.ʹ��session1��ѯidΪ1��ͼ�����Ϣ
//        Book book2 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 5.�����ѯ�����Ϣ
//        System.out.println(book2.toString());
//        // 6.�ر�SqlSession
//        session.close();
//    }
//
//    @Test
//    public void findBookByIdTest2() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session = MyBatisUtils.getSession();
//        // 2.ʹ��session��ѯidΪ1��ͼ�����Ϣ
//        Book book1 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(book1.toString());
//        Book book2 = new Book();
//        book2.setId(3);
//        book2.setBookName("MySQL���ݿ�����");
//        book2.setPrice(40.0);
//        // 4.ʹ��session����idΪ3��ͼ�����Ϣ
//        session.update("com.itheima.mapper."
//                + "BookMapper.updateBook", book2);
//        session.commit();
//        Book book3 = session.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 5.�����ѯ�����Ϣ
//        System.out.println(book1.toString());
//        // 6.�ر�SqlSession
//        session.close();
//    }
//
//    /**
//     * ����id��ѯͼ����Ϣ��-��������
//     */
    @Test
    public void findBookByIdTest3() {
        // 1.ͨ������������SqlSession����
        SqlSession session1 = MyBatisUtils.getSession();
        SqlSession session2 = MyBatisUtils.getSession();
        // 2.ʹ��session1��ѯidΪ1��ͼ�����Ϣ
        Book book1 = session1.selectOne("com.itheima.mapper."
                + "BookMapper.findBookById", 1);
        // 3.�����ѯ�����Ϣ
        System.out.println(book1.toString());
        // 4.�ر�SqlSession1
        session1.close();
        // 5.ʹ��session2��ѯidΪ1��ͼ�����Ϣ
        Book book2 = session2.selectOne("com.itheima.mapper."
                + "BookMapper.findBookById", 1);
        // 6.�����ѯ�����Ϣ
        System.out.println(book2.toString());
        // 7.�ر�SqlSession2
        session2.close();
    }
//
//    @Test
//    public void findBookByIdTest4() {
//        // 1.ͨ������������SqlSession����
//        SqlSession session1 = MyBatisUtils.getSession();
//        SqlSession session2 = MyBatisUtils.getSession();
//        SqlSession session3 = MyBatisUtils.getSession();
//        // 2.ʹ��session1��ѯidΪ1��ͼ�����Ϣ
//        Book book1 = session1.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 3.�����ѯ�����Ϣ
//        System.out.println(book1.toString());
//        // 4.�ر�SqlSession
//        session1.close();
//        Book book2 = new Book();
//        book2.setId(2);
//        book2.setBookName("Java Web���򿪷�����");
//        book2.setPrice(45.0);
//        // 5.ʹ��session2����idΪ2��ͼ�����Ϣ
//        session2.update("com.itheima.mapper."
//                + "BookMapper.updateBook", book2);
//        session2.commit();
//        session2.close();
//        // 6.ʹ��session3��ѯidΪ1��ͼ�����Ϣ
//        Book book3 = session3.selectOne("com.itheima.mapper."
//                + "BookMapper.findBookById", 1);
//        // 7.�����ѯ�����Ϣ
//        System.out.println(book3.toString());
//        // 8.�ر�SqlSession
//        session3.close();
//    }


    @After
    public void destory() {
        //�ύ����
        sqlSession.commit();
        //�ر�����
        sqlSession.close();
    }
}
