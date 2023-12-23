package java_code;

import java.io.*;

/**
 * @Author: LFJ
 * @Date: 2023-09-05 12:03
 */
public class BufferTest {

	public static final int BUFFER_SIZE = 1024 * 1024 * 1;

	/**
	 * 方式一：不加缓冲区的情况
	 *
	 * @throws IOException
	 */
	public static void test1() throws IOException {
		//需求：把vedio拷贝到vedio1
		String sourceFilePath = "E:\\desktop.zip";
		String destFilePath = "E:\\desktop1.zip";
		//创建读取文件对象
		FileInputStream fis = new FileInputStream(sourceFilePath);
		//创建写入文件对象
		FileOutputStream fos = new FileOutputStream(destFilePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		long start = System.currentTimeMillis();
		int len = 0;
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);//21365
		//缓冲流是在普通流的基础上多了一个缓冲区  ,
		// 当读或写的数据达到一定量,再自动往文件里进行读写,如果没有装满缓冲区文件就已经读(写)完毕 ,
		// 那么这个时候已经读写到缓冲区的数据需要手动使其朝目标文件进行读写.
		//所以使用缓冲流一定要调用flush方法
		bos.flush();
		bis.close();
		bos.close();
	}

	/**
	 * 方式二：加缓冲区的情况   (推荐)
	 * @throws IOException
	 */
	public static void test2() throws IOException {
		//需求：把vedio拷贝到vedio1
		String sourceFilePath = "E:\\desktop.zip";
		String destFilePath = "E:\\desktop2.zip";
		//创建读取文件对象
		FileInputStream fis = new FileInputStream(sourceFilePath);
		//创建写入文件对象
		FileOutputStream fos = new FileOutputStream(destFilePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		long start = System.currentTimeMillis();
		// 缓冲区
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		while ((len = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);//975
		bos.flush();
		bis.close();
		bos.close();
	}

	/**
	 * 方式3：构造函数加入缓冲大小的情况
	 * @throws IOException
	 */
	public static void test3() throws IOException {
		// 需求：把vedio拷贝到vedio1
		String sourceFilePath = "E:\\desktop.zip";
		String destFilePath = "E:\\desktop3.zip";
		//创建读取文件对象
		FileInputStream fis = new FileInputStream(sourceFilePath);
		//创建写入文件对象
		FileOutputStream fos = new FileOutputStream(destFilePath);
		BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE);   //构造函数加入缓冲大小的情况
		BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

		long start = System.currentTimeMillis();
		int len = 0;
		while ((len = bis.read()) != -1) {
			bos.write(len);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);//3036
		bos.flush();
		bis.close();
		bos.close();
	}

	public static void main(String[]args) throws IOException {
		//测试拷贝一个大小为200M时的性能问题
		test1();
		test2();
		test3();
	}
}
