package com.leet.code;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SolutionTire {

    // LeetCode 720

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

    // 插入一个字符
    public void insert(char[] text){
        TrieNode p = root;

        int len = text.length;
        for (int i = 0; i < len; i++) {
            // 字符插入位置索引
            int index = text[i] - 'a';
            // 根据索引插入children中。如果已经存在则不处理
            if (p.children[index] == null){
                p.children[index] = new TrieNode(text[i]);
            }
            // 到下一层遍历
            p = p.children[index];
        }
        p.isEndingChar = true;
    }


    // 查找一个字符串
    public boolean find(char[] pattern){
        TrieNode p = root;

        for (int i = 0; i < pattern.length; i++) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null){
                return false;
            }
            // 继续向下一层查询
            p = p.children[index];
        }
        if (p.isEndingChar == false){
            return true;
        }

        return true;
    }


    // Tire树中最长单词，且排序靠前
    // 建立trid树时，记录每个字符串的长度，并把两者建立映射 HashMap<len, List<String>>
    // ? 如何判断由其它单词组成
    // 长度最长的字符串除外，用其它字符串建立Trie数，然后在树中查询最长单词是否存在  有bug
    // 单词全部加入trie树，按长度顺序，依次查询（去掉末尾字符查询） bug ["a", "banana", "app", "app", "ap", "apply", "apple"]
    // 从最短的字符串开始建树，后面下一个，如果减1能找到，插入并记录为rst
    public String longestWord(String[] words) {
        int[] wordsLen = new int[words.length];
        HashMap<Integer, List<String>> map = new HashMap<>();
        TrieNode p = root;

        for (int i = 0; i < words.length; i++) {
            int len = words[i].length();
            wordsLen[i] = len;
            List<String> list = null;
            if (map.containsKey(len)){
                list = map.get(len);
            }else {
                list = new ArrayList<>();
            }
            list.add(words[i]);
            map.put(len, list);
        }

        Arrays.sort(wordsLen);
        String rst = "";
        // 按word长度递增顺序，开始建trie树
        // 先用word去掉尾字符在trie树中查询，找到rst记录，并插入树
        for (int i = 0; i < wordsLen.length; i++) {
            int len = wordsLen[i];
            List<String> list = map.get(len);
            for (String word : list){
                char[] text = word.toCharArray();
                char[] txt = Arrays.copyOf(text, text.length-1);
                if (i != 0){
                    if (find(txt)){
                        if (rst.length() == word.length()){
                            if (rst.compareTo(word) > 0){
                                rst = word;
                                insert(text);
                            }
                        }else {
                            rst = word;
                            insert(text);
                        }
                    }
                }else {
                    rst = word;
                    insert(text);
                }
            }
        }

        return rst;
    }

    /**
     *  执行用时 : 64 ms, 在Longest Word in Dictionary的Java提交中击败了15.21% 的用户
        内存消耗 : 48.8 MB, 在Longest Word in Dictionary的Java提交中击败了39.23% 的用户
     */
    // 通过单词长度递减，然后在words中查询，如果能找到，符合题目要求
    public String longestWord1(String[] words) {
        int[] wordsLen = new int[words.length];
        HashMap<String, Integer> map = new HashMap<>();
        TrieNode p = root;

        for (int i = 0; i < words.length; i++) {
            int len = words[i].length();
            wordsLen[i] = len;
            if (!map.containsKey(words[i])){
                map.put(words[i], 1);
            }
        }

        String rst = "";
        for (int i = 0; i < words.length; i++) {
            String wd = words[i];
            int len = wd.length();
            int j = 0;
            for (j = 1; j < len; j++) {
                wd = wd.substring(0, len - j);
                // 子串在words中没有
                if (!map.containsKey(wd)){
                    break;
                }
            }
            if (j == len){
                if (rst.length() == 0 || rst.length() < len){
                    rst = words[i];
                }else if (rst.length() == len && rst.compareTo(words[i]) > 0){
                    rst = words[i];
                }
            }
        }
        return rst;
    }


    public static void main(String[] args) {
        SolutionTire solution = new SolutionTire();

        String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};

        System.out.println(solution.longestWord(words));
    }
}
