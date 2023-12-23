package lesson01;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 20:48
 */
public class TcpClient {
	public static void main(String[] args) {
		InetAddress inetAddress = null;
		Socket socket = null;
		OutputStream outputStream = null;
		//1.要知道服务器的地址，端口号
		while(true) {
			try {
				inetAddress = InetAddress.getByName("127.0.0.1");
				int port = 9999;
				//2.创建一个socket链接
				socket = new Socket(inetAddress, port);
				//3.发送消息 IO流
				outputStream = socket.getOutputStream();
				Scanner sc = new Scanner(System.in);
				outputStream.write(sc.nextLine().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}  finally {
				try {
					assert outputStream != null;
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					socket.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
	}
}
