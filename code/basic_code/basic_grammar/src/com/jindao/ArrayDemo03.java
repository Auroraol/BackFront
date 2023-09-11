package com.jindao;

import java.util.Arrays;

public class ArrayDemo03 {
    public static void main(String[] args) {
        int[] array = {1, 4, 5, 6, 72, 2, 2, 2, 25, 6, 7};
        int[] sort = sort(array);
        System.out.println(Arrays.toString(sort));
    }
    
    public static int[] sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                if (array[j+1] >= array[j]) {
                    array[j] ^= array[j+1];
                    array[j+1] ^= array[j];
                    array[j] ^= array[j+1];
                }
            }
        }
        return array;
    }
}
