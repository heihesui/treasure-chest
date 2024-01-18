package org.example;

import java.util.Arrays;

public class DigitalDp {
    String s;
    int[][] memo;

    /**
     * 一个数位Dp的模板
     * 用来求解n之内每一位都不重复的所有情况
     */
    public int countSpecialNumbers(int n) {
        this.s = String.valueOf(n);
        this.memo = new int[s.length()][1 << 10];
        for (var item : memo) {
            Arrays.fill(item, -1);
        }
        return find(0, 0, true, false);
    }

    private int find(int index, int flag, boolean isLimited, boolean isNum) {
        if (index == s.length()) {
            return isNum ? 1 : 0;
        }
        if (!isLimited && isNum && memo[index][flag] != -1) {
            return memo[index][flag];
        }
        int res = 0;
        if (!isNum) {
            res = find(index + 1, flag, false, false);
        }
        int up = isLimited ? s.charAt(index) - '0' : 9;
        for (int i = 0; i <= up; i++) {
            if (i == 0 && !isNum) {
                continue;
            }
            if ((flag >> i & 1) == 1) {
                continue;
            }
            res += find(index + 1, flag | (1 << i), isLimited && up == i, true);
        }
        if (!isLimited && isNum) {
            memo[index][flag] = res;
        }
        return res;
    }
}
