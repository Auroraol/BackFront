package java_code;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 15:22
 */
public class ObjectTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		/*
		 * 将一个对象存储到持久化(硬盘)的设备上。
		 */
		writeObj();//对象的序列化。
	}

	public static void writeObj() throws IOException {
		//1,明确存储对象的文件。
		FileOutputStream fos = new FileOutputStream("obj.object");
		//2，给操作文件对象加入写入对象功能。
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		//3，调用了写入对象的方法。
		oos.writeObject(new Person("wangcai",20));
		//关闭资源。
		oos.close();
	}
}
