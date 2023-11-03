package com.demo01;

public class TestThread3 implements Runnable {
	private int ticketNums = 10;

	public void run() {
		while (true) {
			if (ticketNums <= 0) {
				break;
			}
			//模拟延的
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "--->.拿到了第" + ticketNums + "票");
		}
	}

	public static void main(String[] args) {
		TestThread3 testThread3 = new TestThread3();
		new Thread(testThread3, "小明").start();
		new Thread(testThread3, "小是").start();
		new Thread(testThread3, "小大").start();

	}
}
