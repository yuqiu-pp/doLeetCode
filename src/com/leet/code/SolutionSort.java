package com.leet.code;

import java.util.Arrays;

public class SolutionSort {

    public void wiggleSort(int[] nums) {
        // LeetCode直接运行是奇数、偶数个元素得到结果
        // [2, 3, 1, 3, 2]   [2, 3, 1, 3, 1, 2]
        //  小 大 小  大 小     小 大 小  大 小 大
        // 要实现上面的顺序，需要将数组排序，

        // 排序
        Arrays.sort(nums);

        // 分两半，每次从前半取一个数，后半取一个数
        int len = nums.length;



    }


    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 2, 3, 1};

        SolutionSort solution = new SolutionSort();

        solution.wiggleSort(nums);

        for (int n: nums){
            System.out.print(n + ",");
        }
        System.out.println();
    }
}
