package com.leet.code;


import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;

import java.math.BigDecimal;
import java.util.Stack;

// 二叉搜索树
// 左节点小于父节点，右节点大于父节点
// 查找效率高
public class SolutionBST {

    // LeetCode 938

    // 遍历，把值在 [L,R] 之间的所有节点找出来
    // public int rangeSumBST(TreeNode root, int L, int R) {
    //     if (root == null){
    //         return 0;
    //     }
    //
    //     if (root.val >= L && root.val <= R){
    //         stack.push(root.val);
    //     }
    //     if (root.val <= L){
    //         findValInRegion(root.right, L, R, stack);
    //     }else if (root.val >= R){
    //         findValInRegion(root.left, L, R, stack);
    //     }else {
    //         findValInRegion(root.left, L, R, stack);
    //         findValInRegion(root.right, L, R, stack);
    //     }
    //
    //
    //     return sum;
    // }

    /**
     *  执行用时 : 8 ms, 在Range Sum of BST的Java提交中击败了25.00% 的用户
        内存消耗 : 52.4 MB, 在Range Sum of BST的Java提交中击败了43.90% 的用户
     */
    // 遍历，把值在 [L,R] 之间的所有节点找出来
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null){
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        findValInRegion(root, L, R, stack);

        int sum = 0;
        while (!stack.isEmpty()){
            sum += stack.pop();
        }
        return sum;
    }

    /**
     *  执行用时 : 7 ms, 在Range Sum of BST的Java提交中击败了31.48% 的用户
        内存消耗 : 52.8 MB, 在Range Sum of BST的Java提交中击败了35.37% 的用户
     */
    // 属于区间的存入栈，同时利用搜索树的特点
    public void findValInRegion(TreeNode root, int L, int R, Stack<Integer> stack){
        if (root == null){
            return;
        }

        if (root.val >= L && root.val <= R){
            stack.push(root.val);
        }
        if (root.val <= L){
            findValInRegion(root.right, L, R, stack);
        }else if (root.val >= R){
            findValInRegion(root.left, L, R, stack);
        }else {
            findValInRegion(root.left, L, R, stack);
            findValInRegion(root.right, L, R, stack);
        }
    }

    // 属于区间的存入栈
    public void findValInRegion1(TreeNode root, int L, int R, Stack<Integer> stack){
        if (root == null){
            return;
        }
        // left小于
        if (root.val >= L && root.val <= R){
            stack.push(root.val);
        }

        findValInRegion1(root.left, L, R, stack);
        findValInRegion1(root.right, L, R, stack);
    }


    public TreeNode createBSTByArray(Integer[] a, int index){
        int len = a.length;

        // 创建当前节点
        TreeNode node = null;

        // root放index=0
        // 左子树节点在数组中的位置  2*i+1
        // 右子树  2*i+2
        if (index < len && a[index]!=null) {
            node = new TreeNode(a[index]);
            // 创建左节点
            node.left = createBSTByArray(a, 2 * index + 1);
            // 创建右节点
            node.right = createBSTByArray(a, 2 * index + 2);
        }
        return node;
    }


    // LeetCode 220

    // 每个元素和后面的元素计算差
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {

        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                int m = j - i;
                int p = Math.abs(nums[i]);
                int q = Math.abs(nums[j]);
                if ((nums[i]>=0 && nums[j]<0) || (nums[i]<0 && nums[j]>=0)){
                    if (p > m || q > m){
                        return false;
                    }
                }
                if (Math.abs(p-q) <=t && (m <= k)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsNearbyAlmostDuplicate1(int[] nums, int k, int t) {

        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                BigDecimal m = new BigDecimal(nums[i]);
                BigDecimal n = new BigDecimal(nums[j]);
                m = m.subtract(n).abs();
                if ((m.compareTo(new BigDecimal(t)) == -1) && ((j - i) <= k)){
                    return true;
                }
            }
        }

        return false;
    }



    // LeetCode 783


    /**
     *  执行用时 : 14 ms, 在Minimum Distance Between BST Nodes的Java提交中击败了5.45% 的用户
        内存消耗 : 34.6 MB, 在Minimum Distance Between BST Nodes的Java提交中击败了74.41% 的用户
     */
    // 中序遍历得到一个，然后计算两两差值
    public int minDiffInBST(TreeNode root) {

        Stack<Integer> stack = new Stack<>();
        traverseBSF(root, stack);

        int[] array = new int[stack.size()];
        for (int i = 0; !stack.isEmpty(); i++) {
            array[i] = stack.pop();
        }

        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int m = array[i] - array[j];
                if (stack.isEmpty()){
                    stack.push(m);
                }else {
                    if (m < stack.peek()){
                        stack.pop();
                        stack.push(m);
                    }
                }
            }
        }

        return stack.pop();
    }

    // 中序遍历，有序输出
    public void traverseBSF(TreeNode root, Stack<Integer> stack){
        if (root == null){
            return;
        }

        traverseBSF(root.left, stack);
        stack.push(root.val);
        traverseBSF(root.right, stack);
    }


    public static void main(String[] args) {
        SolutionBST solution = new SolutionBST();

        Integer[] B = {10,5,15,3,7,13,18,1,null,6};

        // TreeNode root = solution.createBSTByArray(B, 0);
        //
        // int n = solution.rangeSumBST(root, 6, 10);
        // System.out.println(n);

        // int[] A = {2, 0,-2, 2};
        // System.out.println(solution.containsNearbyAlmostDuplicate1(A, 1, 2147483647));


        Integer[] C = {4,2,6,1,3,null,null};
        TreeNode root = solution.createBSTByArray(C, 0);
        System.out.println(solution.minDiffInBST(root));

    }
}
