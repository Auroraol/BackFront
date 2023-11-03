package syn;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 15:34
 */

public class DieLockDemo {
	public static void main(String[] args) {
		List<String> s1 = new ArrayList<String>();
		List<String> s2 = new ArrayList<String>();

		new Thread(() -> {
			synchronized (s1) {
				s1.add("1");
				s2.add("a");
				//当线程1拿到锁s1时，并执行以上代码后，休眠0.1秒，进入阻塞状态，
				//此时线程2开始执行了
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//休眠结束后，需要s2的锁
				synchronized (s2) {
					s1.add("2");
					s2.add("b");
					System.out.println(s1);
					System.out.println(s2);
				}
			}

		}).start();

		new Thread(() -> {
			synchronized (s2) {
				s1.add("3");
				s2.add("c");
				//线程2执行后，休眠0.1秒后，进入阻塞状态
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//休眠结束后，此时线程2需要s1的锁，而线程1也需要s2的锁，因此它们就进入了死循环。
				synchronized (s1) {
					s1.add("4");
					s2.add("d");
					System.out.println(s1);
					System.out.println(s2);
				}
			}
		}).start();

	}
}