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
	    
	    @Test
	    public void findPersonByIdTest() {
	        // 1.ͨ������������qlSession����
	        SqlSession session = MyBatisUtils.getSession();
	        // 2.ʹ��MyBatisǶ�ײ�ѯ�ķ�ʽ��ѯidΪ1����Ϣ 
	        Person person = session.selectOne("com.itheima.mapper." 
	                                   + "PersonMapper.findPersonById", 1);
	        // 3.�����ѯ�����Ϣ
	        System.out.println(person);
	        // 4.�ر�qlSession
	        session.close();
	    }
	    
	    /**
	     * һ�Զ�??	
	     */
	    @Test
	    public void findUserTest() {
	        // 1.ͨ������������qlSession����
	    	 SqlSession session = MyBatisUtils.getSession();
	        // 2.��ѯidΪ1����Ϣ 
	        User user = session.selectOne("com.itheima.mapper."
	                                + "UserMapper.findUserWithOrders", 1);
	        // 3.�����ѯ�����Ϣ
	        System.out.println(user);
	        // 4.�ر�qlSession
	        session.close();
	    }
}
