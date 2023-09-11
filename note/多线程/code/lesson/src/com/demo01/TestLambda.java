package com.demo01;

public class TestLambda {

	// 2. 静态内部类
	static class Like2 implements ILike {
		public void lambda() {
			System.out.println("lambda2");
		}
	}

	public static void main(String[] args) {
		ILike like = new Like();
		like.lambda();

		like = new Like2();
		like.lambda();

		// 3. 局部内部类
		class Like3 implements ILike {
			public void lambda() {
				System.out.println("lambda3");
			}
		}
		like = new Like3();
		like.lambda();

		// 4.匿名内部类，没有类的名称，必须借助接口或者父类
		like = new ILike() {
			@Override
			public void lambda() {
				System.out.println("lambda4");
			}
		};
		like.lambda();

		// 5.lambda表达式
		like = () -> {
			System.out.println("lambda5");
		};
		like.lambda();
	}
}

// 定义一个函数式接口
interface ILike {
	void lambda();
}

//1. 实现类
class Like implements ILike {
	public void lambda() {
		System.out.println("lambda1");
	}
}