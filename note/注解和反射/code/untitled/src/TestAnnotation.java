import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Author: LFJ
 * @Date: 2023-02-04 15:57
 */
public class TestAnnotation {
	public static void main(String[] args) throws NoSuchFieldException {
//		Class c1 = Class.forName("cn.doris.reflection.Student2");
		Class<Student2> c1 = Student2.class;
		//通过反射该类的获取注解
		Annotation[] annotations = c1.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println(annotation);
		}
		//获得注解value
		TableDoris tableDoris = (TableDoris) c1.getAnnotation(TableDoris.class);
		String value = tableDoris.value();
		System.out.println(value);  //db_student

		//获得类指定的注解
		Field name = c1.getDeclaredField("name");  // 获得name属性
		// getAnnotation(参数类型) 得到注解
		FiledDoris annotation = name.getAnnotation(FiledDoris.class);
		System.out.println(annotation.columnName()); //db_name
		System.out.println(annotation.type());       //varchar
		System.out.println(annotation.length());     //200
	}
}

@TableDoris("db_student")
class Student2 {
	@FiledDoris(columnName = "db_id", type = "int", length = 10)
	private int id;
	@FiledDoris(columnName = "db_age", type = "int", length = 3)
	private int age;
	@FiledDoris(columnName = "db_name", type = "varchar", length = 200)
	private String name;
	public Student2() {
	}
	public Student2(int id, int age, String name) {
		this.id = id;
		this.age = age;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student2{" +
				"id=" + id +
				", age=" + age +
				", name='" + name + '\'' +
				'}';
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
//类名注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableDoris {
	String value();
}

//属性注解
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FiledDoris {
	String columnName();
	String type();
	int length();
}

