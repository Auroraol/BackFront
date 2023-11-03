package java_code;

import sun.swing.FilePane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 11:38
 */
public class IoTest {
	public static void main(String[] args) throws IOException {
		String filepath = "test.txt";
		//读
		FileInputStream fileInputStream = new FileInputStream(filepath);
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = fileInputStream.read(buffer)) != -1) {
			System.out.println(new String(buffer, 0, len));
		}
		fileInputStream.close();

		//写
		FileOutputStream fileOutputStream = new FileOutputStream(filepath);
		fileOutputStream.write("学习IO流".getBytes());
		fileOutputStream.close();
	}
}
