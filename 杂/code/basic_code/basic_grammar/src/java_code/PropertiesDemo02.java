package java_code;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 12:29
 */
public class PropertiesDemo02 {
	public static void main(String[] args) throws IOException {
		//创建集合对象
		Properties prop = new Properties();

		//添加元素到集合
		//prop.put(key, value);
		prop.put("周迅", "张学友");
		prop.put("李小璐", "贾乃亮");
		prop.put("杨幂", "刘恺威");

		//3，创建流
		FileWriter out = new FileWriter("prop.properties");
		//4，把集合中的数据存储到流所对应的文件中
		prop.store(out, "save data");
		//5，关闭流
		out.close();
		}
	}
