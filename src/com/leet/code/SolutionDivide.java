package com.leet.code;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SolutionDivide {

    // LeetCode 241

    /**
     *  执行用时 : 16 ms, 在Different Ways to Add Parentheses的Java提交中击败了8.05% 的用户
        内存消耗 : 41.2 MB, 在Different Ways to Add Parentheses的Java提交中击败了5.34% 的用户
     */

    // 以运算符为界，  分为left和right，递归的计算
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> rst = new ArrayList<Integer>();
        Stack<Integer> stack = new Stack<>();

        int i = 0;
        int val = 0;
        for (i = 0; i < input.length(); i++) {
            List<Integer> left = new ArrayList<Integer>();
            List<Integer> right = new ArrayList<Integer>();
            char c = input.charAt(i);
            if (c=='+' || c=='-' || c=='*'){
                left = diffWaysToCompute(input.substring(0, i));
                right = diffWaysToCompute(input.substring(i+1, input.length()));

                // 返回的两个集合再根据运算符合并
                for (int j = 0; j < left.size(); j++) {
                    for (int k = 0; k < right.size(); k++) {
                        if (c == '+'){
                            val = left.get(j) + right.get(k);
                        }else if (c == '-'){
                            val = left.get(j) - right.get(k);
                        }else {
                            val = left.get(j) * right.get(k);
                        }
                        rst.add(val);
                    }
                }
            }
        }
        // 纯数字时
        if (rst.size() == 0){
            rst.add(Integer.valueOf(input));
        }
        return rst;
    }


    // LeetCode 169

    /**
     *  执行用时 : 40 ms, 在Majority Element的Java提交中击败了19.24% 的用户
        内存消耗 : 48.9 MB, 在Majority Element的Java提交中击败了46.86% 的用户
     */

    // 此次 从n/2分开，分别统计数字出现次数
    // 多种数字的话，可以用散列表<num, 次数>保存
    public int majorityElement(int[] nums) {
        int n = nums.length;
        if (n <= 2){
            return nums[0];
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int i = 0;
        for (i = 0; i < n; i++) {
            if (map.containsKey(nums[i])){
                int m = map.get(nums[i]) + 1;
                if (m > n/2){
                    break;
                }
                map.put(nums[i], m);
            }else {
                map.put(nums[i], 1);
            }
        }

        return nums[i];
    }





    public static void main(String[] args) {
        SolutionDivide solution = new SolutionDivide();

        // String input = "2*3+1-1";
        // for (int i : solution.diffWaysToCompute(input)){
        //     System.out.println(i);
        // }

        // int[] nums = {2,2,1,1,1,2,2};
        int[] nums = {3,2,3};
        System.out.println(solution.majorityElement(nums));
    }


}
