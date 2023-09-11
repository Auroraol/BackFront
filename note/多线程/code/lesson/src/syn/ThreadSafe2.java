package syn;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 14:36
 */

public class ThreadSafe2 implements Runnable {
	private int ticket = 100;

	@Override
	public void run() {
		while (true)
			if (ticket > 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "：出售票，票号为" + ticket);
				ticket--;
			} else {
				break;
			}
	}

	public static void main(String[] args) {
		ThreadSafe2 safe = new ThreadSafe2();
		Thread t1 = new Thread(safe, "窗口1");
		Thread t2 = new Thread(safe, "窗口2");
		Thread t3 = new Thread(safe, "窗口3");
		t1.start();
		t2.start();
		t3.start();
	}
}