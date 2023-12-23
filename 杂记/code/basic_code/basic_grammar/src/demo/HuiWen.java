package demo;

//public class HuiWen {
//        public static void main(String[] args) {
//            char[]  lenStr ={'a', '+', 'b','&', 'b', '+', 'a'};  //   需要判断的回文
//            int top = 0 ;
//            char[] s = new char[101]; //定义的一个数组
//            int next;
//            int mid = lenStr.length/2-1;//找到回文的中心点
//            for(int i = 0 ;i<=mid; i++){
//                //把回文的前部分放到新的数组中
//                s[++top] = lenStr[i] ;
//            }
//            //判断字符长度是奇数还是偶数，并找出需要进行字符匹配的起始下标
//            if(lenStr.length%2 == 0){
//                next = mid+1;
//            }else {
//                next = mid+2;
//            }
//            //    开始匹配
//            for(int n = next; n<=lenStr.length-1; n++){
//                if(lenStr[n] != s[top]){
//                    break;
//                }else {
//                    top--;
//                }
//            }
//            //如果top的值为0，则说明栈内的所有的字符都被一一匹配了
//            if(top == 0){
//                System.out.println("YES");
//            }else {
//                System.out.println("NO");
//            }
//        }
//}
//


//public class HuiWen {
//    public static void main(String[] args) {
//        char chinalord1 = '马';
//        char chinaword2 = '士';
//        char chinaword3 = '卓';
//
//        System.out.println("汉字\"马\"字在unicode表中的顺序位置:" + (int)chinalord1);
//        System.out.println("汉字\"士\"字在unicode表中的顺序位置:" + (int)chinaword2);
//        System.out.println("汉字\"卓\"字在unicode表中的顺序位置:" + (int)chinaword3);
//
//    }
//}

//
public class HuiWen {
    public static void main(String[] args) {
        char chinalord4 = '单';
        char chinaword5 = '胜';
        char chinaword6 = '霖';

        System.out.println("汉字\"单\"字在unicode表中的顺序位置:" + (int)chinalord4);
        System.out.println("汉字\"胜\"字在unicode表中的顺序位置:" + (int)chinaword5);
        System.out.println("汉字\"霖\"字在unicode表中的顺序位置:" + (int)chinaword6);
    }
}