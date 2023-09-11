package com.jindao;

public class Variable {
    //直接赋值
    final int m = 100;

    //final修饰的成员变量，需要在创建对象前赋值，否则报错。
    final int n;
    public Variable(){
        //可以在创建对象时所调用的构造方法中，为变量n赋值
        n = 2016;

        final int n;
    }
    public static void main(String[] args) {

        Variable variable = new  Variable();
        }

}
