package com.leet.code;

public class SolutionDimidiate {





    /**
     *  执行用时 : 2 ms, 在Pow(x, n)的Java提交中击败了99.78% 的用户
     内存消耗 : 32.9 MB, 在Pow(x, n)的Java提交中击败了89.11% 的用户
     */
    // 把幂次方n除2，结果就是两个幂次方是n/2的数的相乘，这样只需要计算1次n/2次方的运算
    // 依次类推，直到指数变为1，不能再分
    // 如果指数为奇数，二分之后，计算结果时需要多乘一次x
    // LeetCode提交，报栈溢出
    public double myPow(double x, int n) {
        // 递归出口
        if (n == 0){
            return 1;
        }
        if (n == 1){
            return x;
        }
        // 指数是负数
        if (n == -1){
            return 1/x;
        }

        // 计算n/2次方
        double rst = myPow(x, n/2);
        // 计算n次方
        rst = rst * rst;
        // 奇次方需要多乘一次
        if (n % 2 != 0){
            if (n > 0){
                rst *= x;
            }else {
                rst *= 1/x;
            }

        }

        return rst;
    }



    /**
     *  执行用时 : 22 ms, 在Arranging Coins的Java提交中击败了79.62% 的用户
     内存消耗 : 32.8 MB, 在Arranging Coins的Java提交中击败了92.65% 的用户
     */
    public int arrangeCoins(int n) {
        if (n == 0){
            return 0;
        }

        // 循环分解，直到剩余数量 < 分解次数+1
        int i = 0;
        for (i = 1; i < n; i++) {
            n -= i;
            if (n < i + 1){
                break;
            }
        }

        return i;
    }
}
