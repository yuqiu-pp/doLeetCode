package com.leet.code;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;

public class Recursion {

    // LeetCode 698
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int len = nums.length;
        if (len < k){
            return false;
        }

        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            canpart(nums, i, sum, k-i);
        }


        return true;
    }

    public boolean canpart(int[] nums, int pos, int sum, int k){
        int len = nums.length - pos;
        if (len < k){
            return false;
        }
        for (int i = pos; i < len; i++) {

        }

        return true;
    }



    // LeetCode 687  longest-univalue-path
    public int longestUnivaluePath(TreeNode root) {
        return longestPath(root);
    }


    public int longestPath(TreeNode node){
        if (node == null){
            return 0;
        }

        // 节点值与左右子节点值相同，路径=左+右+2
        if (node.left!=null && node.right!=null && node.val==node.left.val && node.val==node.right.val){
            return longestPath(node.left) + longestPath(node.right) + 2;
        }

        // 节点值与左子节点值相同，路径=max(左+1, 右)
        if (node.left!=null && node.val==node.left.val){
            return Math.max(longestPath(node.left)+1, longestPath(node.right));
        }

        // 节点值与右子节点值相同，路径=max(左, 右+1)
        if (node.right!=null && node.val==node.right.val){
            return Math.max(longestPath(node.right)+1, longestPath(node.left));
        }

        // 节点值与左右子节点值都不相同，路径=max(左，右)
        return Math.max(longestPath(node.left), longestPath(node.right));

    }


    public void inOrderTraverse(TreeNode root){
        if (root == null){
            return;
        }

        System.out.println(root.val);
        inOrderTraverse(root.left);
        inOrderTraverse(root.right);
    }


    public ArrayList createTreeByArray1(int[] a){
        int len = a.length;

        // 初始化二叉树，用数组存储

        ArrayList<TreeNode> tree = new ArrayList<TreeNode>(len);

        for (int i = 0; i < len; i++) {
            tree.add(new TreeNode(a[i]));
        }
        // 左子树节点在数组中的位置  2*i+1
        // 右子树  2*i+2
        for (int i = 0; i < len; i++) {
            if (2 * i + 1 < len) {
                tree.get(i).left = tree.get(2 * i + 1);
            }
            if (2 * i + 2 < len){
                tree.get(i).right = tree.get(2 * i + 2);
            }

        }

        return tree;
    }


    // public TreeNode createTreeByArray(int[] a, int index){
    //     int len = a.length;
    //
    //     // 初始化二叉树，用数组存储
    //     // 创建当前节点
    //     TreeNode node = null;
    //
    //     // 左子树节点在数组中的位置  2*i+1
    //     // 右子树  2*i+2
    //     if (index < len) {
    //         node = new TreeNode(a[index]);
    //         // 创建左节点
    //         node.left = createTreeByArray(a, 2 * index + 1);
    //         // 创建右节点
    //         node.right = createTreeByArray(a, 2 * index + 2);
    //     }
    //
    //     return node;
    // }

    public TreeNode createTreeByArray(Integer[] a, int index){
        int len = a.length;

        // 初始化二叉树，用数组存储
        // 创建当前节点
        TreeNode node = null;

        // 左子树节点在数组中的位置  2*i+1
        // 右子树  2*i+2
        if (index < len && a[index]!=null) {
            node = new TreeNode(a[index]);
            // 创建左节点
            node.left = createTreeByArray(a, 2 * index + 1);
            // 创建右节点
            node.right = createTreeByArray(a, 2 * index + 2);
        }

        return node;
    }

    public static void main(String[] args) {
        Recursion solution = new Recursion();

        int[] A = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;

        boolean b = solution.canPartitionKSubsets(A, k);
        System.out.println(b);


        // int[] A = {5,4,5,1,1,5};
        // int[] A = {1,4,5,4,4,-1,5};
        // int[] A = {5,5,5,5,4,1};
        //
        // ArrayList<TreeNode> treeBin = solution.createTreeByArray1(A);
        // for (TreeNode t: treeBin) {
        //     System.out.println(t.val + " " + t +" " + t.left + " " + t.right);
        // }

        // Integer[] A = {5,5,5,5,4,1};
        // Integer[] B = {-9,5,0,-2,-6,null,null,5,null,null,-3,6,-5,null,null,null,0};
        // Integer[] B = {1,null,1,1,1,1,1,1};
        //
        // TreeNode root = solution.createTreeByArray(B, 0);
        // // 中序遍历
        // // solution.inOrderTraverse(treeBin.get(0));
        //
        // int n = solution.longestUnivaluePath(root);
        // System.out.println(n);
    }
}
