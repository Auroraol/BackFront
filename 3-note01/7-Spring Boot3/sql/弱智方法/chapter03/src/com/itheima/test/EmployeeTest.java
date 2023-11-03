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

import com.itheima.pojo.Employee;
import com.itheima.pojo.User;
	/**
	 * ���ų��������
	 */
	public class EmployeeTest {
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
        Employee employee = session.selectOne("findAllemployeeById", 6);
        //������
        System.out.println(employee.getname());
        
        // ���
        Employee employee1 = new Employee();
        employee1.setid(5);
        employee1.setname("ttrose");
        employee1.setage(24);
        employee1.setposition("Ա��");
        int rows;
    	rows = session.insert("com.itheima.mapper"
				+ ".EmployeeMapper.insertEmployee", employee1);

        if(rows > 0){
	        System.out.println("���ɹ�������"+rows+"�����ݣ�");
	    }else{
	        System.out.println("ִ�в������ʧ�ܣ�����");
	    }
        
        //
        employee1.setname("ttr");
        rows = session.update("com.itheima.mapper"
	            + ".EmployeeMapper.updateUser", employee1);
	    // 4.3ͨ�����ؽ���жϸ��²����Ƿ�ִ�гɹ�
	    if(rows > 0){
	        System.out.println("���ɹ��޸���"+rows+"�����ݣ�");
	    }else{
	        System.out.println("ִ���޸Ĳ���ʧ�ܣ�����");
	    }

        //
        rows = session.delete("com.itheima.mapper"
	            + ".EmployeeMapper.deleteUser", 4);
	    // 4.2ͨ�����ؽ���ж�ɾ�������Ƿ�ִ�гɹ�
	    if(rows > 0){
	        System.out.println("���ɹ�ɾ����"+rows+"�����ݣ�");
	    }else{
	        System.out.println("ִ��ɾ������ʧ�ܣ�����");
	    }

        
        
        //�ر�session
        session.close();
		}
	}
