package com.demo01;

// 创建线程方法一：继承Thread类，重写run方法，调用Start()方法开启线程
public class TestThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1200; i++) {
            System.out.println("Thread多线程" + i);
        }
    }

    public static void main(String[] args) {
        TestThread1 threadTest = new TestThread1(); // 创建一个线程对象
        threadTest.start(); // 调用start()方法开启线程
        for (int i = 0; i < 1200; i++) {
            System.out.println("main主线程" + i);
        }
    }
}
