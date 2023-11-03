//package com.jindao;
//
//public class ForDemo01 {
//    public static void main(String[] args) {
//        int oddSum = 0;
//        int evenSum = 0;
//        for (int i = 0; i < 100; i++) {
//            if ((i & 1)==1) {
//                oddSum += i;
//            }else {
//                evenSum += i;
//            }
//        }
//        System.out.println("奇数的和" + oddSum);
//        System.out.println("偶数的和" + evenSum);
//    }
//}
class HuiWen{
    public static void main(String[] args) {
        char[]  lenStr ={'A', '：', '：','A'};  //   需要判断的回文
        int top = 0 ;
        char[] s = new char[101]; //定义的一个数组
        int next;
        int mid = lenStr.length/2-1;//找到回文的中心点
        for(int i = 0 ;i<=mid; i++){
            //把回文的前部分放到新的数组中
            s[++top] = lenStr[i] ;
        }
        //判断字符长度是奇数还是偶数，并找出需要进行字符匹配的起始下标
        if(lenStr.length%2 == 0){
            next = mid+1;
        }else {
            next = mid+2;
        }
        //    开始匹配
        for(int n = next; n<=lenStr.length-1; n++){
            if(lenStr[n] != s[top]){
                break;
            }else {
                top--;
            }
        }
        //如果top的值为0，则说明栈内的所有的字符都被一一匹配了
        if(top == 0){
            System.out.println("YES");
        }else {
            System.out.println("NO");
        }
    }

}