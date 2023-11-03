package com.jindao;

import java.util.Scanner;

public class ArraryDemo01 {
    public static void main(String[] args) {
        int[] nums = new int[10];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            nums[i] =  scanner.nextInt();
        }
        System.out.println("求和");
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        System.out.println(sum);
    }
}
