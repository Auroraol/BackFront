package java_code;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 12:22
 */
public class PropertiesDemo01 {

	public static void main(String[] args) throws IOException {
		//1，创建集合
		Properties prop = new Properties();
		//2，创建流对象
//		FileInputStream in = new FileInputStream("prop.properties");
		FileReader in = new FileReader("prop.properties");
		//3,把流所对应文件中的数据 读取到集合中
		prop.load(in);
		//4,关闭流
		in.close();
		//5,显示集合中的数据
		System.out.println(prop);
		Set<String> strings = prop.stringPropertyNames();
		for (String key : strings)
		{
			String value = prop.getProperty(key);
			System.out.println(key+"==" +value);
		}
	}
}
