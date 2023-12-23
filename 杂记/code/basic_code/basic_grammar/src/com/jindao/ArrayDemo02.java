package com.jindao;

public class ArrayDemo02 {
    public static void main(String[] args) {
        int[] arrays = {1, 2, 3, 4, 5};
        printArray(arrays);
        reverse(arrays);
        System.out.println();
        printArray(arrays);
    }

    // 反转数组
    public static int[] reverse(int[] arrays) {
        int i = 0;
        for (int i1 = arrays.length - 1; i1 >= 0; i1--) {
            arrays[i] = arrays[i1];
            i++;
        }
        return arrays;
    }

    // 打印数组
    public static void printArray(int[] arrays) {
        int sum = 0;
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i]);
        }
    }
}
