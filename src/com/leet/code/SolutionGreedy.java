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


    public static void main(String[] args) {
        SolutionGreedy solution = new SolutionGreedy();

        int[] children = {250,490,328,149,495,325,314,360,333,418,430,458};
        int[] s = {29,310,236,441,200,267,115,59,277,42,361,112,483,104,338,69,438,55,318,470,20,490,455,119,259,51,492,50,160,414,38,289,429,446,350,412,12,515,367,397,122,35,522,355,448,266,333,500,211,226,203,366,240,324,111,280,520,321,211,360,437,292,512,161,85,139,12,211,236,213,377,85,494};
        System.out.println(s.length);
        System.out.println(solution.findContentChildren(children, s));
    }

}
