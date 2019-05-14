package com.leet.code;


import sun.security.util.Length;

import java.nio.channels.Pipe;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class SolutionDynamicPlan {


    // weight: 物品重量，n: 物品个数，w: 背包可承载重量
    public int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w+1]; // 默认值 false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][weight[0]] = true;

        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
                if (states[i-1][j] == true)
                    states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) {// 把第 i 个物品放入背包
                if (states[i-1][j]==true)
                    states[i][j+weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n-1][i] == true)
                return i;
        }
        return 0;
    }


    // 有0时不适用
    public int minCostClimbingStairs1(int[] cost) {
        int n = cost.length;
        if (n == 1){
            return cost[0];
        }
        if (n <= 2){
            return Math.min(cost[0], cost[1]);
        }

        int sum = 0;
        int i = n;
        for (; n >= 2; ) {
            sum += Math.min(cost[n - 1], cost[n - 2]);
            n = (cost[n - 1] < cost[n - 2]) ? (n - 1) : (n - 2);
        }

        return sum;
    }

    // LeetCode 746

    /**
     *  执行用时 : 3 ms, 在Min Cost Climbing Stairs的Java提交中击败了94.92% 的用户
        内存消耗 : 38.2 MB, 在Min Cost Climbing Stairs的Java提交中击败了79.36% 的用户
     */

    // 动态规划
    // 到达每个点的花费的集合 看成状态集合，然后根据这个集合推导下一个点的花费集合
    // 因为题目只求花费的最小值，所以集合只保留一个值即可
    // 动态的向前推进
    // 推导依据：f(n) = f(n-1) + f(n-2) + val(n)  斐波那契数
    // 到达第cost.length个点(数组外的点)时，花费值即为所求
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if (n == 1){
            return cost[0];
        }
        if (n == 2){
            return Math.min(cost[0], cost[1]);
        }

        int[] sum = new int[n+1];
        sum[0] = cost[0];
        sum[1] = cost[1];
        int i = 2;
        // 计算每个点的花费集合，只保留最小值
        for (i = 2; i < n; i++) {
            sum[i] = Math.min(sum[i - 1], sum[i - 2]) + cost[i];
        }

        return Math.min(sum[i - 1], sum[i - 2]);
    }



    // LeetCode 62

    /**
     *  执行用时 : 1 ms, 在Unique Paths的Java提交中击败了88.40% 的用户
        内存消耗 : 33.2 MB, 在Unique Paths的Java提交中击败了13.92% 的用户
     */

    public int uniquePaths(int m, int n) {
        if (m<2 || n< 2){
            return 1;
        }

        // 起点matix[0][0]    终点matix[n-1][m-1]
        int[][] matix = new int[n][m];
        // 0行和0列初始化为1
        for (int i = 0; i < n; i++) {
            matix[i][0] = 1;
        }
        for (int j = 0; j < m; j++) {
            matix[0][j] = 1;
        }

        // 到达每个点的方法数量
        // 每个点的数量 = 上面和左面相邻点的方法相加
        // f(x, y) = f(x, y-1) + f(x-1, y)
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                matix[i][j] = matix[i][j-1] + matix[i-1][j];

            }
        }

        return matix[n-1][m-1];
    }


    public int uniquePaths2(int m, int n) {
        if (m<2 || n< 2){
            return 1;
        }

        int[][] matix = new int[n][m];
        // 起点matix[0][0]    终点matix[n-1][m-1]

        matix[0][1] = 1;
        matix[1][0] = 1;
        // 到达每个点的方法数量
        // 每个点的数量 = 上面和左面相邻点的方法相加
        // f(x, y) = f(x, y-1) + f(x-1, y)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j-1>=0 && i-1>=0){
                    matix[i][j] = matix[i][j-1] + matix[i-1][j];
                }else if (j-1<0 && i-1>0){
                    matix[i][j] = matix[i-1][j];
                }else if (j-1>0 && i-1<0){
                    matix[i][j] = matix[i][j-1];
                }
            }
        }

        return matix[n-1][m-1];
    }


    // LeetCode 64

    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int[][] matix = new int[grid.length][grid[0].length];

        // 到达每个点的数字总和 = min(上面, 左面) + 自己
        // f(x, y) = min(f(x, y-1) + f(x-1, y)) + val[x][y]
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (j-1>=0 && i-1>=0){
                    matix[i][j] = Math.min(matix[i][j-1], matix[i-1][j]) + grid[i][j];
                }else if (j-1<0 && i-1>=0){
                    matix[i][j] = matix[i-1][j] + grid[i][j];
                }else if (j-1>=0 && i-1<0){
                    matix[i][j] = matix[i][j-1] + grid[i][j];
                }
            }
        }

        return matix[grid.length-1][grid[0].length-1] + grid[0][0];
    }



    // LeetCode 309

    /**
     *  执行用时 : 2 ms, 在Best Time to Buy and Sell Stock with Cooldown的Java提交中击败了99.80% 的用户
        内存消耗 : 34.1 MB, 在Best Time to Buy and Sell Stock with Cooldown的Java提交中击败了97.99% 的用户
     */

    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 0){
            return 0;
        }

        int[] buy = new int[len];
        int[] sell = new int[len];

        buy[0] = 0 - prices[0];
        sell[0] = 0;

        for (int i = 1; i < len; i++) {
            if (i == 1){
                buy[i] = Math.max(sell[0]-prices[i], buy[i-1]);
            }else {
                buy[i] = Math.max(sell[i-2]-prices[i], buy[i-1]);
            }
            sell[i] = Math.max(prices[i]+buy[i-1], sell[i-1]);
        }

        return Math.max(sell[len-1], buy[len-1]);
    }


    // LeetCode 714

    // 涨价时，买入，因为是峰谷
    // 跌价时，卖出，因为是峰顶
    // 每次（峰顶 - 峰谷）的差值和为利润最大
    public int maxProfit1(int[] prices, int fee) {
        if (prices.length <= 1){
            return 0;
        }

        int buyPrice = 0;
        int sum = 0;    // 利润总和

        for (int i = 1; i < prices.length; i++) {
            // 涨价时，买入 或 持有
            if (prices[i-1] < prices[i] && i < prices.length-1){
                if (buyPrice == 0){
                    buyPrice = prices[i-1];
                }
            }
            // 跌价时，卖出
            else {
                int sub = 0;
                if (i == prices.length-1){
                    sub = prices[i] - buyPrice - fee;
                }else {
                    sub = prices[i-1] - buyPrice - fee;
                }

                if (buyPrice != 0 && sub > 0){
                    sum += sub;
                    buyPrice = prices[i];
                }
            }
        }

        return sum;
    }



    // LeetCode 714

    /**
     *  执行用时 : 16 ms, 在Best Time to Buy and Sell Stock with Transaction Fee的Java提交中击败了57.04% 的用户
        内存消耗 : 70.7 MB, 在Best Time to Buy and Sell Stock with Transaction Fee的Java提交中击败了30.91% 的用户

     */
    public int maxProfit(int[] prices, int fee) {
        if (prices.length <= 1){
            return 0;
        }

        int len = prices.length;
        int[] buy = new int[len];
        int[] sell = new int[len];

        // 第一天
        buy[0] = 0-prices[0];
        sell[0] = prices[0];

        // 推导公式
        for (int i = 1; i < prices.length; i++) {
            buy[i] = Math.max(buy[i-1], sell[i-1]-prices[i]);
            sell[i] = Math.max(sell[i-1], buy[i-1]+prices[i]-fee);
        }

        return Math.max(buy[len-1], sell[len-1]);
    }




    public static void main(String[] args) {
        SolutionDynamicPlan solution = new SolutionDynamicPlan();

        // int[] weight = {2,2,4,6,3};  // 物品重量
        // int n = 5; // 物品个数
        // int w = 9; // 背包承受的最大重量
        // System.out.println(solution.knapsack(weight, n, w));

        // int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        // int[] cost = {0,2,2,1};
        // System.out.println(solution.minCostClimbingStairs(cost));

        // System.out.println(solution.uniquePaths(7, 3));

        // System.out.println(solution.minPathSum(new int[][]{{9,1,4,8}}));

        // System.out.println(solution.maxProfit(new int[]{1, 2, 3, 0, 2}));

        System.out.println(solution.maxProfit(new int[]{1,3,2,8,4,9}, 2));

    }

}
