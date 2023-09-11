package java_code;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 16:11
 */

public class ClassTest {

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		//1,获取到Class对象
		Class<?> c = Class.forName("java_code.Person");//包名.类名
		//2,获取指定的构造方法
		Constructor<?> con = c.getDeclaredConstructor(String.class, int.class);	//private Person(String name, int age)  //私有构造方法
		//3,暴力反射//取消 Java 语言访问检查
		con.setAccessible(true);
		//4,通过构造方法类中的功能，创建对象
		Object Person = con.newInstance("小明", 23);
		System.out.println(Person);
	}
}
