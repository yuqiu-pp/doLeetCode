package com.leet.code;


import org.omg.CORBA.IRObject;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class SolutionBinTree {

    int i = 0;

    public int findSecondMinimumValue1(TreeNode root) {
        // 节点数量
        int len = inOrderTraverse(root);
        if (len <= 1){
            return -1;
        }

        int[] node = new int[len];
        inOrderTraverseToArray(root, node);

        // 数组排序
        Arrays.sort(node);

        if (len == 2){
            if (node[0] == node[i]) {
                return -1;
            }else {
                if (node[0] < node[i]){
                    return node[0];
                }else {
                    return node[1];
                }

            }
        }

        // 找第二小的值. 末尾最大
        for (int j = len-3; j >= 0; j--) {
            if (node[j] < node[len-2]){
                return node[len-2];
            }
        }

        return -1;
    }

    // 遍历节点放入数组
    public void inOrderTraverseToArray(TreeNode root, int[]array){
        if (root == null){
            return;
        }

        // System.out.println(root.val);
        array[i++] = root.val;

        inOrderTraverseToArray(root.left, array);
        inOrderTraverseToArray(root.right, array);

    }

    public int inOrderTraverse(TreeNode root){
        if (root == null){
            return 0;
        }

        int num = 0;
        // System.out.println(root.val);
        num ++;
        num += inOrderTraverse(root.left);
        num += inOrderTraverse(root.right);

        return num;
    }


    public TreeNode createTreeByArray(Integer[] a, int index){
        int len = a.length;

        // 初始化二叉树，用数组存储
        // 创建当前节点
        TreeNode node = null;

        // root放index=0
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


    // root 一定是最小的
    // 子节点不小于节点，所以子树中大于root的最小值就是第二小值
    // min(left中大于root的最小值，right中大于root的最小值)
    public int findSecondMinimumValue2(TreeNode root) {
        if (root == null){
            return -1;
        }

        int n = findMinValGreaterRoot(root, root.val);

        if (n == root.val){
            return -1;
        }

        return n;
    }

    /**
     *  执行用时 : 1 ms, 在Second Minimum Node In a Binary Tree的Java提交中击败了85.64% 的用户
        内存消耗 : 33.5 MB, 在Second Minimum Node In a Binary Tree的Java提交中击败了84.38% 的用户
     */

    public int findMinValGreaterRoot1(TreeNode node, int val) {
        if (node.left==null && node.right==null){
            return node.val;
        }

        // 右子树中大于root的最小值
        int minRight = 0;
        if (node.left==null && node.right!=null){
            // 相等继续递归查询
            if (node.right.val == val){
                minRight = findMinValGreaterRoot(node.right, val);
            }else {
                minRight = node.right.val;
            }
            return minRight;
        }

        // 左子树中大于root的最小值
        int minLeft = 0;
        if (node.right==null && node.left!=null){
            if (node.left.val == val){
                minLeft = findMinValGreaterRoot(node.left, val);
            }else {
                minLeft = node.left.val;
            }
            return minLeft;
        }

        if (node.left!=null && node.right!=null){
            if (node.left.val == val){
                minLeft = findMinValGreaterRoot(node.left, val);
            }else {
                minLeft = node.left.val;
            }

            if (node.right.val == val){
                minRight = findMinValGreaterRoot(node.right, val);
            }else {
                minRight = node.right.val;
            }

            if (minLeft!=val && minRight!=val){
                return Math.min(minLeft, minRight);
            }else {
                return Math.max(minLeft, minRight);
            }
        }

        return 0;
    }


    /**
     *  执行用时 : 1 ms, 在Second Minimum Node In a Binary Tree的Java提交中击败了85.64% 的用户
        内存消耗 : 34.3 MB, 在Second Minimum Node In a Binary Tree的Java提交中击败了63.54% 的用户
     */
    public int findMinValGreaterRoot(TreeNode node, int val) {
        if (node.left==null && node.right==null){
            return node.val;
        }

        // 右子树中大于root的最小值
        int minRight = node.right.val;
        if (node.right!=null){
            // 相等继续递归查询
            if (node.right.val == val){
                minRight = findMinValGreaterRoot(node.right, val);
            }
        }

        // 左子树中大于root的最小值
        int minLeft = node.left.val;
        if (node.left!=null){
            if (node.left.val == val){
                minLeft = findMinValGreaterRoot(node.left, val);
            }
        }

        int n = 0;
        if (minLeft!=val && minRight!=val){
            n = Math.min(minLeft, minRight);
        }else {
            n = Math.max(minLeft, minRight);
        }

        return n;
    }


    /**
     *  执行用时 : 1 ms, 在Second Minimum Node In a Binary Tree的Java提交中击败了85.64% 的用户
        内存消耗 : 34.1 MB, 在Second Minimum Node In a Binary Tree的Java提交中击败了75.00% 的用户
     */
    // root 一定是最小的
    // 子节点 不小于 父节点，所以子树中大于root的最小值就是第二小值
    // 遍历除root外的所有节点，找大于root的最小值。 最小值用栈保存
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null){
            return -1;
        }

        Stack<Integer> stack = new Stack<>();

        findMinValToStack(root.left, stack, root.val);
        findMinValToStack(root.right, stack, root.val);

        if (stack.isEmpty()){
            return -1;
        }

        return stack.pop();
    }

    public void findMinValToStack(TreeNode node, Stack<Integer> stack, int val){
        if (node == null){
            return;
        }

        // 前序遍历
        // 当前节点值 > root， 可能入栈
        if (node.val>val ){
            // 当前值 < 栈顶，替换栈顶值
            if (stack.isEmpty()){
                stack.push(node.val);
            }else {
                if (node.val < stack.peek()){
                    stack.pop();
                    stack.push(node.val);
                }
            }
        }

        findMinValToStack(node.left, stack, val);
        findMinValToStack(node.right, stack, val);
    }


    // LeetCode 236

    /**
     *  执行用时 : 46 ms, 在Lowest Common Ancestor of a Binary Tree的Java提交中击败了9.28% 的用户
        内存消耗 : 43.2 MB, 在Lowest Common Ancestor of a Binary Tree的Java提交中击败了5.04% 的用户
     */

    // 查询节点，利用栈记录路径
    // 比对路径中最近的共同节点
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null){
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        // 遍历查询节点p
        findValToStack(root, p, stack);
        // 因为路径有顺序，所以val放入数组，用于比对
        // node存入hash表，用来查询
        int len = stack.size();
        int[] pPathVal = new int[len];
        HashMap<Integer, TreeNode> pMap = new HashMap<>();
        while (!stack.isEmpty()){
            TreeNode treeNode = stack.pop();
            pMap.put(treeNode.val, treeNode);
            pPathVal[len-1] = treeNode.val;
            len --;
        }

        stack.push(root);
        // 遍历查询节点q
        findValToStack(root, q, stack);

        len = stack.size();
        int[] qPathVal = new int[len];
        while (!stack.isEmpty()){
            TreeNode treeNode = stack.pop();
            qPathVal[len-1] = treeNode.val;
            len --;
        }

        // 比对数组中的数据，找到最后一个相同值
        len = Math.min(pPathVal.length, qPathVal.length);
        int j = 0;
        for (j = 0; j < len; j++) {
            if (pPathVal[j] != qPathVal[j]){
                break;
            }
        }

        return pMap.get(pPathVal[j-1]);
    }

    public void findValToStack(TreeNode node, TreeNode obj, Stack<TreeNode> stack){
        if (node == null){
            return;
        }

        // 前序遍历
        if (node.val == obj.val){
            stack.push(obj);
            return;
        }
        if (node.left != null){
            stack.push(node.left);
            // 找到结束递归
            if (node.left.val == obj.val){
                return;
            }else {
                findValToStack(node.left, obj, stack);
            }
        }
        if (node.right != null){
            stack.push(node.right);
            if (node.right.val == obj.val){
                return;
            }else {
                findValToStack(node.right, obj, stack);
            }
        }

        if (stack.peek().val == obj.val){
            return;
        }
        // 左右子树都没找到
        stack.pop();
    }


    public void findValToStack1(TreeNode node, TreeNode obj, Stack<TreeNode> stack){
        if (node == null){
            return;
        }

        // 前序遍历
        if (node.val == obj.val){
            stack.push(obj);
            return;
        }
        if (node.left != null && stack.peek().val != obj.val){
            stack.push(node.left);
            // 找到结束递归
            if (node.left.val != obj.val){
                findValToStack(node.left, obj, stack);
            }else {
                return;
            }
        }
        if (node.right != null && stack.peek().val != obj.val){
            stack.push(node.right);
            if (node.right.val != obj.val){
                findValToStack(node.right, obj, stack);
            }else {
                return;
            }
        }
        if (node.left==null && node.right==null){
            stack.pop();
            return;
        }

        if (stack.peek().val == obj.val){
            return;
        }

        // 左右子树都没找到
        stack.pop();
    }



    public static void main(String[] args) {
        SolutionBinTree solution = new SolutionBinTree();

        // Integer[] B = {-9,5,0,-2,-6,null,null,5,null,null,-3,6,-5,null,null,null,0};
        // Integer[] B = {2,2,5,null,null,5,7};  // findSecondMinimumValue
        Integer[] B = {37,-34,-48,null,-100,-101,48,null,null,null,null,-54,null,-71,-22,null,null,null,8};

        TreeNode root = solution.createTreeByArray(B, 0);
        // 中序遍历
        // System.out.println(solution.inEOrderTraverse(root));

        // int f = solution.findSecondMinimumValue(root);
        // System.out.println(f);

        TreeNode p = new TreeNode(-71);
        TreeNode q = new TreeNode(48);
        p = solution.lowestCommonAncestor(root, p, q);
        System.out.println(p.val);
    }
}
