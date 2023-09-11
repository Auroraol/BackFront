package com.jindao;

public class ArrayDemo04 {
    public static void main(String[] args) {
        int[][] array1 = new int[11][11];
        array1[1][2] = 1;
        array1[2][3] = 2;
        System.out.println("输出原始数组:");
        for (int[] ints : array1) {
            for(int n : ints) {
                System.out.print(n+"\t");
            }
            System.out.println();
        }

        // 稀疏数组
        //1.获取有效个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int i1 = 0; i1 < 11; i1++) {
                if (array1[i][i1] != 0)
                    sum++;
            }
        }
        System.out.println("有效个数: " + sum);

        //2.创建稀疏数组
        int[][] array2 = new int[sum + 1][3];
        // 初始化, 行列非零值数量
        array2[0][0] = 11;
        array2[0][1] = 11;
        array2[0][2] = sum;
        //存非零值
        int count = 0;
        for (int i = 0; i < array1.length; i++) {
            for (int i1 = 0; i1 < array1[i].length; i1++) {
                if (array1[i][i1] != 0) {
                    count++;
                    array2[count][0] = i;
                    array2[count][1] = i1;
                    array2[count][2] = array1[1][i1];
                }
            }
        }

        // 输出稀疏数组
        System.out.println("稀疏数组");
        for (int i = 0; i < array2.length; i++) {
            System.out.println(array2[i][0] + "\t"
                    + array2[i][1] + "\t"
                    + array2[i][2] + "\t");
        }

        // 还原稀疏数组
        //1.读取
        int[][] array3 = new int[array2[0][0]][array2[0][1]];
        //2.还原
        for (int i = 1; i < array2.length; i++) {
            array3[array2[i][0]][array2[i][1]] = array2[i][2];
        }
        //3.打印
        for (int[] ints : array3) {
            for(int n : ints) {
                System.out.print(n+"\t");
            }
            System.out.println();

        }
    }
}
