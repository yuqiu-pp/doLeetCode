package com.leet.code;


// 二叉搜索树
// 左节点小于父节点，右节点大于父节点
// 查找效率高
public class SolutionBST {

    // 遍历，把值在 [L,R] 之间的所有节点找出来
    public int rangeSumBST(TreeNode root, int L, int R) {

        if ()

        return 0;
    }

    public void traverseBST(TreeNode root, int L, int R){

        if (root.val >)
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

    public static void main(String[] args) {
        SolutionBST solution = new SolutionBST();

        Integer[] B = {10,5,15,3,7,null,18};

        TreeNode root = solution.createBSTByArray(B, 0);

        int n = solution.rangeSumBST(root, 7, 15);
        System.out.println(n);
    }
}
