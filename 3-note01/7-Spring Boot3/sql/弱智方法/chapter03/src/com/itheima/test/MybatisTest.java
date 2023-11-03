package com.itheima.test;

import com.itheima.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class MybatisTest {
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
    public void findAllStudentTest() {
        // SqlSessionִ��ӳ���ļ��ж����SQL��������ӳ����
        List<Student> list =
                sqlSession.selectList("com.itheima.mapper.StudentMapper." +
                        "findAllStudent");
        for (Student student : list) {
            System.out.println(student);
        }
    }

    @After
    public void destory() {
        //�ύ����
        sqlSession.commit();
        //�ر�����
        sqlSession.close();
    }
}
