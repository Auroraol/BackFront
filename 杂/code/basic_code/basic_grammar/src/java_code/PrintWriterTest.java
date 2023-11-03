package java_code;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 15:43
 */
public class PrintWriterTest {
	public static void main(String[] args) throws IOException {
		//创建流
		PrintWriter out = new PrintWriter(new FileWriter("printFile.txt"), true);
		//2，写数据
		for (int i=0; i<5; i++) {
			out.println("helloWorld");
		}
		out.println(111);
		//3,关闭流
		out.close();
	}

}
