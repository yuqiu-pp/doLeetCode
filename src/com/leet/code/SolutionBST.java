package com.leet.code;


import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;

import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.util.*;

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


    // leetCode 653

    // bsf遍历
    // 优先级队列 存储每层节点
    public boolean findTarget(TreeNode root, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        if(root == null){
            return false;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // bsf遍历
        while (queue.peek() != null){
            TreeNode node = queue.poll();
            int sub = k - node.val;
            if (map.containsKey(sub)){
                return true;
            }else{
                map.put(node.val, 1);
            }
            // 下一层加入队列
            if (node.left != null){
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }

        return false;
    }


    // leetCode 863

    // 1. target作为跟节点时，统计距离为k的子节点
    // 2. 删除掉targ的子节点，统计与target所在层相加=k的节点
    boolean flag = true;

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K)
    {
        List<Integer> rst = new ArrayList<>();

        if (root == null || target == null){
            return rst;
        }

        if(K == 0){
            rst.add(target.val);
            return rst;
        }

        // 1.统计距离为k的子节点
        getChildNode(target, K, rst);
        // 删除target的子节点
        target.right = target.left = null;



        // 统计与target所在层相加=k的节点
        // <层，节点值>
        List<TreeNode> curr = new ArrayList<>();

        curr.add(root);
        while (curr.size() > 0){
            List<TreeNode> next = new ArrayList<>();
            for (int i = 0; i < curr.size(); i++) {
                TreeNode node = curr.get(i);
                // 2. 父节点或父节点子树节点距离为k的子节点
                // 确定target在left，还是right
                boolean bn = parentDfs(node.left, K-1, target.val);
                if (bn){
                    rst.add(node.val);
                    // 右子树中k-1距离的节点OK
                    getChildNode(node.right, K-1, rst);
                    next.add(node.left);
                }else {
                    bn = parentDfs(node.right, K-1, target.val);
                    if (bn){
                        rst.add(node.val);
                        getChildNode(node.left, K-1, rst);
                        next.add(node.right);
                    }
                }

                if (!bn){
                    if (node.right != null) {
                        next.add(node.right);
                    }
                    if (node.left != null) {
                        next.add(node.left);
                    }
                }
            }
            curr.clear();
            curr = next;
            K--;
        }

        return rst;
    }

    public void getChildNode(TreeNode root, int k, List<Integer> list){
        if (root == null || k < 0){
            return;
        }
        if (k == 0){
            list.add(root.val);
            return;
        }
        getChildNode(root.left, k-1, list);
        getChildNode(root.right, k-1, list);
    }

    public boolean targetDfs(TreeNode node, int k, int val){
        if (node == null || k < 0){
            return false;
        }
        if (node.val == val && k == 0){
            return true;
        }

        return parentDfs(node.left, k-1, val) || parentDfs(node.right, k-1, val);
    }

    public boolean parentDfs(TreeNode node, int k, int val){
        if (node == null || k < 0){
            return false;
        }
        if (node.val == val && k == 0){
            return true;
        }

        return parentDfs(node.left, k-1, val) || parentDfs(node.right, k-1, val);
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


        // Integer[] C = {4,2,6,1,3,null,null};
        // TreeNode root = solution.createBSTByArray(C, 0);
        // System.out.println(solution.minDiffInBST(root));

        // System.out.println(solution.findTarget(root, 6));

        // 863
        Integer[] C = {0,null,1,null,null,2,5,null,null,null,null,null,3,null,5};
        TreeNode tmp = new TreeNode(4);
        TreeNode root = solution.createBSTByArray(C, 0);
        TreeNode node = root.right.left;
        root.right.left.right.right = tmp;
        List<Integer> list = solution.distanceK(root, node, 2);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
