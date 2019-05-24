package com.leet.code;

import org.omg.PortableInterceptor.INACTIVE;
import sun.font.FileFontStrike;

import javax.sound.midi.Soundbank;
import java.lang.annotation.ElementType;
import java.text.DateFormatSymbols;
import java.util.*;

public class SolutionBackTrack {


    // 八皇后
    // int[] result = new int[8];// 全局或成员变量, 下标表示行, 值表示 queen 存储在哪一列
    // public void cal8queens(int row) { // 调用方式：cal8queens(0);
    //     if (row == 8) { // 8 个棋子都放置好了，打印结果
    //         printQueens(result);
    //         return; // 8 行棋子都放好了，已经没法再往下递归了，所以就 return
    //     }
    //     for (int column = 0; column < 8; ++column) { // 每一行都有 8 中放法
    //         if (isOk(row, column)) { // 有些放法不满足要求
    //             result[row] = column; // 第 row 行的棋子放到了 column 列
    //             cal8queens(row+1); // 考察下一行
    //         }
    //     }
    // }
    //
    // private boolean isOk(int row, int column) {// 判断 row 行 column 列放置是否合适
    //     int leftup = column - 1, rightup = column + 1;
    //     for (int i = row-1; i >= 0; --i) { // 逐行往上考察每一行
    //         if (result[i] == column) return false; // 第 i 行的 column 列有棋子吗？
    //         if (leftup >= 0) { // 考察左上对角线：第 i 行 leftup 列有棋子吗？
    //             if (result[i] == leftup) return false;
    //         }
    //         if (rightup < 8) { // 考察右上对角线：第 i 行 rightup 列有棋子吗？
    //             if (result[i] == rightup) return false;
    //         }
    //         --leftup; ++rightup;
    //     }
    //     return true;
    // }
    //
    // private void printQueens(int[] result) { // 打印出一个二维矩阵
    //     for (int row = 0; row < 8; ++row) {
    //         for (int column = 0; column < 8; ++column) {
    //             if (result[row] == column) System.out.print("Q ");
    //             else System.out.print("* ");
    //         }
    //         System.out.println();
    //     }
    //     System.out.println();
    // }




    // LeetCode 784

    /**
     *  执行用时 : 11 ms, 在Letter Case Permutation的Java提交中击败了60.91% 的用户
        内存消耗 : 47.9 MB, 在Letter Case Permutation的Java提交中击败了66.03% 的用户
     */

    // 分治算法

    // 第一个字符的集合 与 其它剩余字符集合 的组合
    // 其它剩余字符集合 递归用此方法
    public List<String> letterCasePermutation1(String S) {
        if (S.length() == 0){
            List<String> lt = new ArrayList<>();
            lt.add("");
            return lt;
        }

        List<String> rst = new ArrayList<>();
        String num = "";
        int i = 0;
        for (i = 0; i < S.length(); i++) {
            // 找到字母停止循环
            char c = S.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                break;
            }
        }
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        // 没找到字符
        if (i == S.length()){
            left.add(S);
            right.add("");
        }else {
            left.add(S.substring(0, i) + Character.toLowerCase(S.charAt(i)));
            left.add(S.substring(0, i) + Character.toUpperCase(S.charAt(i)));
            right = letterCasePermutation(S.substring(i+1, S.length()));
        }

        // 合并
        for (String p : left){
            for (String q : right){
                rst.add(p + q);
            }
        }

        return rst;
    }


    /**
     *  执行用时 : 14 ms, 在Letter Case Permutation的Java提交中击败了43.06% 的用户
        内存消耗 : 51.1 MB, 在Letter Case Permutation的Java提交中击败了17.35% 的用户
     */
    // 回溯算法

    // "a1b2"
    // 每个字符有两种选择
    // 第一种选择时，计算后面字符的所有组合（递归）
    // 第二种选择时，同样方式
    public List<String> letterCasePermutation(String S) {
        if (S.length() == 0){
            List<String> lt = new ArrayList<>();
            lt.add("");
            return lt;
        }

        List<String> rst = new ArrayList<>();
        dfs("", S, S.length(), rst);
        return rst;
    }


    public void dfs(String curr, String other, int len, List<String> rst){
        if (curr.length() == len){
            rst.add(curr);
            return;
        }

        for (int i = 0; i < other.length(); i++) {
            char c = other.charAt(i);
            if (Character.isAlphabetic(c)){
                // 每个字符有两种选择
                dfs(curr + Character.toLowerCase(c), other.substring(i+1, other.length()), len, rst);
                dfs(curr + Character.toUpperCase(c), other.substring(i+1, other.length()), len, rst);
                return;
            }else {
                curr = curr + c;
            }
        }

        // 都是数字 可以用递归，也可以i==length直接加入rst
        dfs(curr, "", len, rst);
    }



    // LeetCode 51

    // 每行的一个点放好后，递归找后面行的方法
    // 如果无解，则回溯前面的行，调整皇后位置，继续向下递归查找解
    public List<List<String>> solveNQueens(int n) {
        if (n <= 0){
            return null;
        }

        List<List<String>> rst = new ArrayList<>();

        if (n == 1){
            List<String> ls = new ArrayList<>();
            ls.add("Q");
            rst.add(ls);
            return rst;
        }
        int[][] nn = new int[n][n];
        dfsQueens(0, n, nn, rst);
        return rst;
    }

    public void dfsQueens(int m, int n, int[][] nn, List<List<String>> rst){
        // 解法满足
        if (m == n){
            // 数组存入List. 每行一个List<String>
            for (int i = 0; i < n; i++) {
                List<String> ls = new ArrayList<>(n);
                String str = "";
                for (int j = 0; j < n; j++) {
                    if (nn[i][i] == 1){
                        str = "Q";
                    }else {
                        str = ".";
                    }
                    ls.add(str);
                }
                rst.add(ls);
            }
            return;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isPass(i, j, nn)){
                    nn[i][j] = 1;
                    dfsQueens(i+1, n, nn, rst);
                }
            }

        }
    }


    public boolean isPass(int row, int col, int[][] nn){
        // 行内没有冲突
        for (int k = 0; k < nn.length; k++) {
            if (k != col && nn[row][k] == 1){
                return false;
            }
        }
        // 列
        for (int i = 0; i < nn.length; i++) {
            if (i != row && nn[i][col] == 1){
                return false;
            }
        }
        // 左对角线


        return true;
    }


    // LeetCode 211

    class TrieNode {

        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;
        public TrieNode(char data){
            this.data = data;
        }
    }

    // root
    TrieNode root = new TrieNode('\\');

    public void addWord(String word){
        char[] wd = word.toCharArray();

        TrieNode p = root;
        // 遍历单词，在Tire中查找
        for (int i = 0; i < wd.length; i++) {
            int index = wd[i] - 'a';
            // 字符不存在
            if (p.children[index] == null){
                p.children[index] = new TrieNode(wd[i]);
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    public boolean search(String word){
        TrieNode p = root;

        return find(word, root);
    }


    /**
     *  执行用时 : 206 ms, 在Add and Search Word - Data structure design的Java提交中击败了43.97% 的用户
        内存消耗 : 83.1 MB, 在Add and Search Word - Data structure design的Java提交中击败了57.53% 的用户
     */
    public boolean find(String word, TrieNode curr){
        TrieNode p = curr;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == '.'){
                // 从任何一个children[index]!=null 递归的去查找
                // 如果匹配失败，回溯到上一个点，继续
                for (int j = 0; j < p.children.length; j++) {
                    if (p.children[j] != null){
                        String wd = word.substring(i+1, word.length());
                        if (find(wd, p.children[j])){
                            return true;
                        }
                    }
                }
                return false;
            }else {
                int index = c - 'a';
                if (p.children[index] != null){
                    p = p.children[index];
                }else {
                    return false;
                }
            }

        }
        // 不是结尾
        if (!p.isEndingChar){
            return false;
        }

        return true;
    }



    // LeetCode 322

    int minNum = Integer.MAX_VALUE;

    // 回溯  超时
    public int coinChange(int[] coins, int amount) {
        int len = coins.length;
        int num = 0;

        Arrays.sort(coins);
        for (int i = 0; i < coins.length/2; i++) {
            int tmp = coins[i];
            coins[i] = coins[coins.length-1-i];
            coins[coins.length-1-i] = tmp;
        }

        coinBackTrack(coins, amount, num);

        if (minNum != Integer.MAX_VALUE){
            return minNum;
        }
        return -1;
    }

    public void coinBackTrack(int[] coins, int amount, int count){
        if (count >= minNum){
            return;
        }

        if (amount == 0){
            if (count < minNum){
                minNum = count;
            }
            System.out.println(" ok =" + count);
            return;
        }

        if (amount < coins[coins.length-1]){
            return;
        }

        for (int i = 0; i < coins.length; i++) {

            if (amount >= coins[i]){
                // System.out.print(coins[i] + ",");
                // 直接传count+1，amount也一样
                // 这样递归还来amount还是原来的值
                coinBackTrack(coins, amount-coins[i], count+1);
            }

            if (count >= minNum){
                return;
            }
        }
    }



    // public int minVal()


    public static void main(String[] args) {
        SolutionBackTrack solution = new SolutionBackTrack();

        // String str = "12a45";
        // List<String> list = solution.letterCasePermutation(str);
        // for (String s : list){
        //     System.out.println(s);
        // }

        // solution.cal8queens(0);

        // solution.addWord("bad");
        // solution.addWord("dad");
        // solution.addWord("mad");
        // System.out.println(solution.search("pad"));
        // System.out.println(solution.search("b.."));

        // int[] coins = {186,419,83,408};
        // int amount = 6249;
        int[] coins = {474,83,404,3};
        int amount = 264;
        System.out.println(solution.coinChange(coins, amount));

    }
}
