package com.leet.code;

import java.util.HashMap;
import java.util.Stack;

public class Array {

}


class SolutionArray {

    // LeetCode 698




    // LeetCode 503
    /**
     *  执行用时 : 72 ms, 在Next Greater Element II的Java提交中击败了52.02% 的用户
        内存消耗 : 53.7 MB, 在Next Greater Element II的Java提交中击败了47.75% 的用户
     */
    // 遍历数组，每个数都和其后面的数据比较，直到循环回到自己的位置
    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        int[] A = new int[len];
        int flag = 0;

        for (int i = 0; i < len ; i++) {
            flag = 0;
            // 比较 当前位置之后 ~ 最后一个元素 之间的数据
            for (int j = i+1; j < len; j++) {
                if (nums[i] < nums[j]){
                    A[i] = nums[j];
                    flag = 1;
                    break;
                }
            }
            // 已经找到，不在进行后面数据的比较
            if (flag == 1){
                continue;
            }
            // 循环到从第一个元素开始比较
            for (int j = 0; j < i; j++) {
                if (nums[i] < nums[j]){
                    A[i] = nums[j];
                    flag = 1;
                    break;
                }
            }
            if (flag == 1){
                continue;
            }
            // 没有找到
            A[i] = -1;
        }

        return A;
    }

    /**
     *  执行用时 : 285 ms, 在Next Greater Element II的Java提交中击败了12.62% 的用户
        内存消耗 : 60.7 MB, 在Next Greater Element II的Java提交中击败了18.02% 的用户
     */
    // 遍历数组，每个数都和其后面的数据比较，直到循环回到自己的位置
    public int[] nextGreaterElements1(int[] nums) {
        int len = nums.length;
        int[] A = new int[len];
        int flag = 0;

        for (int i = 0; i < len ; i++) {
            flag = 0;
            // 按顺序循环比较 当前元素和其它元素
            int end = len+i;
            for (int j = i+1; j < end; j++) {
                if (nums[i] < nums[j%len]){
                    A[i] = nums[j%len];
                    flag = 1;
                    break;
                }
            }
            // 没有找到
            if (flag != 1){
                A[i] = -1;
            }
        }

        return A;
    }

    /**
     *  执行用时 : 5 ms, 在Sort Array By Parity II的Java提交中击败了94.36% 的用户
        内存消耗 : 52 MB, 在Sort Array By Parity II的Java提交中击败了19.95% 的用户
     */
    // 新建数组，偶数插入偶位置，奇数插入奇位置
    // 两个指针分别指向偶、奇位置
    public int[] sortArrayByParityII(int[] A) {
        int[] B = new int[A.length];

        int odd = 1;
        int even = 0;

        for (int i = 0; i < A.length ; i++) {
            if (A[i] % 2 == 0){
                B[even] = A[i];
                even += 2;
            }else {
                B[odd] = A[i];
                odd += 2;
            }
        }

        return B;

    }


    /**
     *  执行用时 : 3 ms, 在Sort Array By Parity的Java提交中击败了99.80% 的用户
        内存消耗 : 49.2 MB, 在Sort Array By Parity的Java提交中击败了43.10% 的用户
     */
    // 新建数组，偶数从前往后存入，奇数从后往前存入
    public int[] sortArrayByParity(int[] A) {
        int[] B = new int[A.length];

        int head = 0;
        int tail = A.length -1;
        // 遍历数据，检查奇偶性
        for (int i = 0; i < A.length ; i++) {
            if (A[i] % 2 == 0){
                B[head] = A[i];
                head ++;
            }else {
                B[tail] = A[i];
                tail --;
            }
            // 所有数据都遍历完
            if (head > tail){
                break;
            }
        }
        return B;
    }

    /**
     * 执行用时 : 3 ms, 在Sort Array By Parity的Java提交中击败了99.80% 的用户
        内存消耗 : 44 MB, 在Sort Array By Parity的Java提交中击败了76.99% 的用户
     */
    // 两个指针分别从前后扫描，偶数从前往后存入，奇数从后往前存入
    public int[] sortArrayByParity1(int[] A) {
        if (A.length == 1){
            return A;
        }

        int head = 0;
        int tail = A.length -1;
        int swap = 0;
        // 遍历数据，检查奇偶性
        for (int i = 0; i < A.length ; i++) {
            // head指向偶数，前进
            if (A[head] % 2 == 0){
                head ++;
            }
            // head指向奇数，查找尾部最后一个偶数，和head交换
            else {
                if (A[tail] % 2 != 0){
                    tail --;
                }else {
                    swap = A[head];
                    A[head] = A[tail];
                    A[tail] = swap;
                    head ++;
                    tail --;
                }

            }
            // 所有数据都遍历完
            if (head >= tail){
                break;
            }
        }
        return A;
    }


    public int[] twoSum(int[] nums, int target) {
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            // 差属于数组
            int sub = target - nums[i];
            if (map.containsKey(sub)){
                int index = map.get(sub);
                stack.push(Math.max(index, i));
                stack.push(Math.min(index, i));
            }else{
                map.put(nums[i], i);
            }
        }

        len = stack.size();
        if (len == 0){
            return null;
        }

        int[] rst = new int[len];
        for (int i = 0; i < len; i++) {
            rst[i] = stack.pop();
        }

        return rst;
    }


    public static void main(String[] args) {
        int[] A = {1, 2, 1};
        // int[] A = {5, 4, 3, 2, 1};

        SolutionArray solution = new SolutionArray();
        // int[] B = solution.sortArrayByParity1(A);
        // int[] B = solution.sortArrayByParityII(A);
        int[] B = solution.nextGreaterElements1(A);

        for (int a: B) {
            System.out.print(a + ",");
        }
        System.out.println();
    }
}


