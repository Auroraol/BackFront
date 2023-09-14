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
	 * 入门程序测试类
	 */
	public class EmployeeTest {
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
        Employee employee = session.selectOne("findAllemployeeById", 6);
        //输出结果
        System.out.println(employee.getname());
        
        // 添加
        Employee employee1 = new Employee();
        employee1.setid(5);
        employee1.setname("ttrose");
        employee1.setage(24);
        employee1.setposition("员工");
        int rows;
    	rows = session.insert("com.itheima.mapper"
				+ ".EmployeeMapper.insertEmployee", employee1);

        if(rows > 0){
	        System.out.println("您成功插入了"+rows+"条数据！");
	    }else{
	        System.out.println("执行插入操作失败！！！");
	    }
        
        //
        employee1.setname("ttr");
        rows = session.update("com.itheima.mapper"
	            + ".EmployeeMapper.updateUser", employee1);
	    // 4.3通过返回结果判断更新操作是否执行成功
	    if(rows > 0){
	        System.out.println("您成功修改了"+rows+"条数据！");
	    }else{
	        System.out.println("执行修改操作失败！！！");
	    }

        //
        rows = session.delete("com.itheima.mapper"
	            + ".EmployeeMapper.deleteUser", 4);
	    // 4.2通过返回结果判断删除操作是否执行成功
	    if(rows > 0){
	        System.out.println("您成功删除了"+rows+"条数据！");
	    }else{
	        System.out.println("执行删除操作失败！！！");
	    }

        
        
        //关闭session
        session.close();
		}
	}
