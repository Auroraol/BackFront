package com.demo01;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 14:22
 */

// 测试守护线程
// 上帝守护你
public class TestThread5 {
	public static void main(String[] args) {
		God god = new God();
		You you = new You();

		Thread thread = new Thread(god);
		thread.setDaemon(true);  // 设置成守护线程
		thread.start();

		new Thread(you).start();


	}
}

// 上帝
class God implements Runnable {
	public void run() {
		while (true) {
			System.out.println("上帝保佑你");
		}
	}
}

// 你
class You implements Runnable {
	public void run() {
		for (int i = 0; i < 36500; i++) {
			System.out.println("你开心的活着");
		}
	}
}