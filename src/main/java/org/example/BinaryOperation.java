package org.example;

public class BinaryOperation {
    public static void main(String[] args) {
        //bitCount 返回int中bit为1的位数
        System.out.println(Integer.bitCount(260));
        //将最后一位bit 1 转为bit 0
        int num = 260;
        System.out.println(num & (num - 1));
    }
}