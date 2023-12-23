package java_code;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 17:38
 */
public class TestPerformance {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

		//************************获得Class对象******************************//
		//方式一:已知某个类的实例，通过对象查询
		User user = new User();
		Class<?> c1 = user.getClass();
		//方式二:若已知具体的类，通过类名.class获得
		Class<?> c2 = User.class;
		//方式三:已知一个类的全类名，forname()获得  推荐 Class.forName().var --> 补全
		Class<?> c3 = Class.forName("java_code.User");

		//************************通过反射方式，获取构造方法，创建对象******************************//
		// 1. 构造无参对象
		User user1 = (User) c3.newInstance();  //调用类的无参构造器
		System.out.println(user1);
		//	2. 构造有参对象 getDeclaredConstructor(参数类型)
		Constructor<?> constructor = c3.getDeclaredConstructor(int.class, String.class);    //获取指定的构造方法
		User user2 = (User) constructor.newInstance(0, "大夫");                          //创建对象
		System.out.println(user2);
		System.out.println("=======================");

		//************************通过反射方式，获取私有构造方法，创建对象******************************//
		//构造有参对象 getDeclaredConstructor(参数类型)
		Constructor<?> con = c3.getDeclaredConstructor(int.class);	//private User(int age)
		//暴力反射
		con.setAccessible(true);//取消 Java 语言访问检查
		//通过构造方法类中的功能，创建对象
		Object obj = con.newInstance( 23);
		System.out.println(obj);

		//************************通过反射获取成员方法并使用******************************//
		// 1. 获取多个方法
		Method[] methods = c3.getMethods();   //获得本类及父类的全部public方法
		for (Method method : methods) {
			System.out.println("getMethods:" + method);
		}
		methods = c3.getDeclaredMethods();   //获得本类的所有方法(包括私有)
		for (Method method : methods) {
			System.out.println("getDeclaredMethods:" + method);
		}
		// 2. 获取指定的方法
		Method show = c3.getMethod("show", null);
		// 执行找到的方法 invoke(对象,"方法参数")  调用方法
		show.invoke(user2);

		// 3. 获取指定的私有方法
		Method addAge = c3.getDeclaredMethod("addAge", null);
		addAge.setAccessible(true);
		System.out.println(addAge.invoke(user2));
		System.out.println("=======================");


		//************************通过反射获取成员变量并使用******************************//
		// 1. 获得指定属性的值
		Field[] fields = c3.getFields();      //只能找到public属性
		for (Field field : fields) {
			System.out.println("getFields:" + field);
		}

		fields = c3.getDeclaredFields();  //找到全部的属性,包含私有
		for (Field field : fields) {
			System.out.println("getDeclaredFields:" + field);
		}

		// 2. 获得指定公有属性的值
		Field age = c3.getDeclaredField("age");
		//赋值, set(对象,"属性赋值")  给属性赋值
		age.set(user2, 100);
		System.out.println(user2.age);

		// 2. 获得指定私有属性的值
		Field name = c3.getDeclaredField("name");
		name.setAccessible(true); //取消 Java 语言访问检查
		//赋值
		name.set(user2, "JJ");
		System.out.println(user2.getName());
		System.out.println("=======================");
	}

}
