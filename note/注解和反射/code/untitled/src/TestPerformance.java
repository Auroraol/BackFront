import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: LFJ
 * @Date: 2023-02-04 14:46
 */
public class TestPerformance {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
		//获得Class对象
		//方式一:已知某个类的实例，通过对象查询
		User user = new User();
		Class<?> c1 = user.getClass();
        //方式二:已知一个类的全类名，forname()获得
		Class<?> c2 = Class.forName("User");
		//方式三:若已知具体的类，通过类名.class获得，推荐
		Class<?> c3 = User.class;


		//构造无参对象,这里创建的对象也能用对象.方法的方式调用方法
        User user1 = (User) c3.newInstance();  //调用类的无参构造器
        System.out.println(user1);
		//	构造有参对象
        Constructor<?> constructor = c3.getDeclaredConstructor(int.class, String.class);
        User user2 = (User) constructor.newInstance(0, "大夫");
        System.out.println(user2);
		System.out.println("=======================");


		//通过反射调用类方法
		//获得类的方法
		Method[] methods = c3.getMethods();   //获得本类及父类的全部public方法
		for (Method method : methods) {
			System.out.println("getMethods:" + method);
		}
		methods = c3.getDeclaredMethods();   //获得本类的所有方法
		for (Method method : methods) {
			System.out.println("getDeclaredMethods:" + method);
		}
		//获取指定的方法
		Method show = c3.getDeclaredMethod("show", null);
		//invoke(对象,"方法参数")  调用方法
		show.invoke(user2);
		//user2.show();
		Method addAge = c3.getDeclaredMethod("addAge", null);
		System.out.println(addAge.invoke(user2));
		System.out.println("=======================");


		//通过反射操作类属性
		//获得指定属性的值
		Field[] fields = c3.getFields();      //只能找到public属性
		for (Field field : fields) {
			System.out.println("getFields:" + field);
		}
		fields = c3.getDeclaredFields(); //找到全部的属性
		for (Field field : fields) {
			System.out.println("getDeclaredFields:" + field);
		}
		//获得指定属性的值
		Field age = c3.getDeclaredField("age");
		//set(对象,"属性赋值")  给属性赋值
		age.set(user2, 100);
		System.out.println(user2.age);

		Field name = c3.getDeclaredField("name");
		//不能直接操作私有属性,我们需要关闭程序的安全检测,属性或方法的setAccessible(true)
		//设置安全检测
		name.setAccessible(true);
		name.set(user2, "JJ");
		System.out.println(user2.getName());
		System.out.println("=======================");
	}
}

class User{
	public  int age;
	private  String name;

	User(){
		age = 0;
		name = "L";
	}

	User(int age, String name){
		this.age = age;
		this.name = name;
	}

	public String getName(){
		return  name;
	}

	public int addAge(){
		age += 10;
		return age;
	}

	public void  show(){
		System.out.println("name:" + name + "---age:" + age );
	}
}