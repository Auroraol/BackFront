package lesson01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 20:49
 */
public class TcpServer {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream inputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			//1.我得有一个地址
			serverSocket = new ServerSocket(9999);
			while (true) {
				//2.等待客户端链接
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				byte[] bytes = new byte[1024];
				//3.读取客户端的消息
				byteArrayOutputStream = new ByteArrayOutputStream();
				int len = 0;
				while ((len = inputStream.read(bytes)) != -1) {
					byteArrayOutputStream.write(bytes, 0, len);
				}
				System.out.println(byteArrayOutputStream.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

