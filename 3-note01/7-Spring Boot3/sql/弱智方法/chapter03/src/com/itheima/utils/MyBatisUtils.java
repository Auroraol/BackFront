package com.itheima.utils;
	import java.io.Reader;
	import org.apache.ibatis.io.Resources;
	import org.apache.ibatis.session.SqlSession;
	import org.apache.ibatis.session.SqlSessionFactory;
	import org.apache.ibatis.session.SqlSessionFactoryBuilder;
	/**
	 * ������
	 */
	public class MyBatisUtils {
		private static SqlSessionFactory sqlSessionFactory = null;
		// ��ʼ��SqlSessionFactory����
		static {
			try {
				// ʹ��MyBatis�ṩ��Resources�����MyBatis�������ļ�
				Reader reader = 
							Resources.getResourceAsReader("mybatis-config.xml");
				// ����SqlSessionFactory����
				sqlSessionFactory = 
						new SqlSessionFactoryBuilder().build(reader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//��ȡSqlSession����ľ�̬����
		public static SqlSession getSession() {
			return sqlSessionFactory.openSession();
		}
	}

