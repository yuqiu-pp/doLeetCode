package com.leet.code;

import java.util.Arrays;

public class SolutionGreedy {

    // LeetCode 455

    /**
     *  执行用时 : 21 ms, 在Assign Cookies的Java提交中击败了48.49% 的用户
        内存消耗 : 50 MB, 在Assign Cookies的Java提交中击败了26.09% 的用户
     */

    // 贪心算法
    // 遍历每个孩子，依次选出适合他的最小饼干
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int num = 0;
        int curr = 0;
        for (int i = 0; i < g.length; i++) {
            for (int j = curr; j < s.length; j++) {
                curr++;
                if (g[i] <= s[j]){
                    num ++;
                    break;
                }
            }
            if (curr > s.length){
                break;
            }
        }

        return num;
    }



    // LeetCode 714

    /**
     *  执行用时 : 9 ms, 在Best Time to Buy and Sell Stock with Transaction Fee的Java提交中击败了94.95% 的用户
        内存消耗 : 72 MB, 在Best Time to Buy and Sell Stock with Transaction Fee的Java提交中击败了23.64% 的用户
     */

    // 贪心算法求解
    // 1）问题抽象：n天股票价格中，抽取某些天进行买卖
    // 2）利润最大：最低价买，最高价卖
    // 手续费意味着：如果多卖一次的利润 <= fee，那就应该少卖一次
    // 高点卖出后，如果股票下跌超过fee，那么卖出就是正确的；
    // 下跌 prices[i] - prices[i+1] > fee，即 prices[i]-fee > prices[i+1]
    // 所以卖出后，设置minPrice = prices[i] - fee

    // 当卖亏了怎么办？
    // 把价格拉低补回来

    public int maxProfit(int[] prices, int fee) {
        if (prices.length <= 1){
            return 0;
        }

        int minPirce = prices[0];
        int sum = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= minPirce){
                minPirce = prices[i];
            }else {
                // 当天价格卖出利润大于0
                int n = prices[i] - minPirce - fee;
                if (n > 0){
                    sum += n;
                    // 后面有可能存在利润更大的，如果出现这种情况，把那部分利润补回来就行了
                    // ？？
                    minPirce = prices[i] - fee;
                }
            }
        }
        return sum;
    }



    public static void main(String[] args) {
        SolutionGreedy solution = new SolutionGreedy();

        // int[] children = {250,490,328,149,495,325,314,360,333,418,430,458};
        // int[] s = {29,310,236,441,200,267,115,59,277,42,361,112,483,104,338,69,438,55,318,470,20,490,455,119,259,51,492,50,160,414,38,289,429,446,350,412,12,515,367,397,122,35,522,355,448,266,333,500,211,226,203,366,240,324,111,280,520,321,211,360,437,292,512,161,85,139,12,211,236,213,377,85,494};
        // System.out.println(s.length);
        // System.out.println(solution.findContentChildren(children, s));

        int[] prices = {1,3,7,5,10,3};
        System.out.println(solution.maxProfit(prices, 3));
    }

}
