package java_code;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 17:37
 */
public class User{
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

	private User(int age){
		this.age = age;
		this.name = "小明";
	}

	public String getName(){
		return  name;
	}

	private int addAge(){
		age += 10;
		return age;
	}

	public void  show(){
		System.out.println("name:" + name + "---age:" + age );
	}
}