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
	 * ���ų��������
	 */
	public class UserTest {
		/**
		 * �����û���Ų�ѯ�û���Ϣ
		 */
		@Test
		public void userFindByIdTest()  {
        //��ȡ�ļ���
        String resources = "mybatis-config.xml";
        //������
        Reader reader = null;
        try {
            //��ȡmybatis-config.xml�ļ����ݵ�reader������
            reader = Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //��ʼ��mybatis���ݿ�,����SqlSessionFactory���ʵ��
        SqlSessionFactory sqlMapper = new
                SqlSessionFactoryBuilder().build(reader);
        //����SqlSessionʵ��
        SqlSession session = sqlMapper.openSession();
        //���������ѯ�����ؽ��
        User user = session.selectOne("findById", 2);
        //������
        System.out.println(user.getUname());
        //�ر�session
        session.close();
		}
	}
