package org.example;

public class Calculate {
    public static void main(String[] args) {
        //bitCount 返回int中bit为1的位数
        System.out.println(Integer.bitCount(260));
        //将最后一位bit 1 转为bit 0
        int num = 260;
        System.out.println(num & (num - 1));
    }

    // 计算最大公约数
    private static int calculateGCD(int a, int b) {
        if (b == 0) return a;
        else return calculateGCD(b, a % b);
    }

    // 计算最小公倍数
    private static int calculateLCM(int a, int b) {
        return (a * b) / calculateGCD(a, b);
    }
}