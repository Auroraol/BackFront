package gaoji;

/**
 * @Author: LFJ
 * @Date: 2023-02-02 16:27
 */
public class TestPc2 {
	public static void main(String[] args) {
		TV tv = new TV();
		new Player(tv).start();
		new Watcher(tv).start();
	}
}

//生产者-->演员
class Player extends Thread {
	TV tv;

	public Player(TV tv) {
		this.tv = tv;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			if ((i & 1) == 0) {
				this.tv.play("喜羊羊");
			} else {
				this.tv.play("抖音");
			}
		}
	}
}

//消费者-->观众
class Watcher extends Thread {
	TV tv;

	public Watcher(TV tv) {
		this.tv = tv;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			tv.watch();
		}
	}
}

//产品-->节目
class TV {
	//演员表演,观众等待T
	//观众观看,演员等待F
	String voice;//表演的节目
	boolean flag = true;

	//表演
	public synchronized void play(String voice) {
		if (!flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 能表演
		System.out.println("演员表演了:" + voice);
		//通知观众观看
		this.notifyAll();
		this.voice = voice;
		this.flag = !this.flag;
	}

	//观看
	public synchronized void watch() {
		if (flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("观看了:" + voice);

		//通知颂员表演
		this.notifyAll();
		this.flag = !this.flag;
	}
}