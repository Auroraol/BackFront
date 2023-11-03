package com.demo01;

// 模拟龟兔赛跑
class Race implements Runnable {
    //胜利者
    private static String winner;

    public void run(){
        for(int i = 0; i <=100;  i++) {
            //模拟兔子休息
            if(Thread.currentThread().getName().equals("兔子") && i % 10 == 0){
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            //判饰比赛是否结束

            boolean flag = gameOver(i);
            if (flag){
                break;
            }
            System.out.println(Thread.currentThread().getName() + "-->跑了" + i + "步");
        }
    }
    //判断是否完成比赛
    private boolean gameOver(int steps){
        //判浙是否疗胜利者
        if(winner!=null){//已经存在胜利者了
            return true;
        }
        if (steps >= 100) {
            winner = Thread.currentThread().getName();
            System.out.println("胜利者: " + winner);
            return true;
        }
        return false;
    }

}

public class TestThread4 {
    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race, "乌龟").start();
        new Thread(race, "兔子").start();
    }
}
