package com.jindao;

import java.util.Scanner;

public class Scan {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        double sum = 0;
        int count = 0;
        System.out.println("请输入数字(输入不是数字退出)");
        while (scanner.hasNextDouble())
        {
            double x = scanner.nextDouble();
            count++;
            sum+= x;
        }
        System.out.println(count + "个数的和为" + sum);
        System.out.println(count + "个数的平均值" + sum/count);
    }
}

