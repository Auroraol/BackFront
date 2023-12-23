package java_code;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 15:34
 */
public class ObjectTest02 {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		readObj();//对象的反序列化。
	}
	public static void readObj() throws IOException, ClassNotFoundException {
		//1,定义流对象关联存储了对象文件。
		FileInputStream fis = new FileInputStream("obj.object");
		//2,建立用于读取对象的功能对象。
		ObjectInputStream ois = new ObjectInputStream(fis);
		Person obj = (Person)ois.readObject();
		System.out.println(obj.toString());
	}

}
