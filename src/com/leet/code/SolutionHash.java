package com.leet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SolutionHash {

    public List<String> topKFrequent(String[] words, int k) {
        int len = words.length;
        if (len==0 || k==0){
            return null;
        }

        ArrayList<String> rst = new ArrayList<String>();

        // 通过hash表统计单词出现的次数
        HashMap<String, Integer> map = new HashMap<>(len);
        // 并把重复单词过滤掉. 用栈的方式：第一次出现压栈. 用数组不好处理
        Stack stack = new Stack<>();
        for (String w : words){
            // 如果存在次数+1
            if (map.containsKey(w)){
                map.put(w, map.get(w) + 1);
            }else {
                map.put(w, 1);
                stack.push(w);
            }
        }
        // 不重复单词出栈，放入数组
        String[] singleWords = new String[len];
        int i = 0;
        while (!stack.isEmpty()){
            singleWords[i++] = (String) stack.pop();
        }
        len = i;

        // 建立单词和次数关联，以便比较
        int[] numOfWords = new int[len];
        for (int j = 0; j < len; j++) {
            numOfWords[j] = map.get(singleWords[j]);
        }

        // 找最大的次数
        int max = 0;
        String[] equalWords = new String[len];
        for (int j = 0, m = 0; j < len; j++, m++) {
            if (numOfWords[max] < numOfWords[j]){
                max = j;
                m = 0;
            }
            // 次数相同
            if (numOfWords[max] == numOfWords[j]){
                m = 0;
            }

        }





        // 遍历过滤后的数组，每次找出次数最大的单词，写入rst
        // 通过栈来保存次数最大的单词索引。大于栈顶：出栈，进栈；等于栈顶：进栈；
        // 如果仍然小于K，继续遍历；大于等于K时，排序
        // for (int j = 0; j < len; j++) {
        //     if (stack.isEmpty()){
        //         stack.push(i);
        //     }else {
        //         int num = map.get(singleWords[i]);
        //         if (num > (Integer) stack.peek()){
        //             stack.pop();
        //             stack.push()
        //         }
        //     }
        // }

        return rst;
    }


    // LeetCode 609
    public List<List<String>> findDuplicate(String[] paths) {

        HashMap<String, List<String>> map = new HashMap<>();
        List<List<String>> list = new ArrayList<>();

        // 遍历数组，解析字符串。格式："root/a 1.txt(abcd) 2.txt(efgh)"
        for (String ps : paths){
            String[] all = ps.split(" ");

            int len = all.length;
            String path = all[0];
            String file = "";
            String data = "";
            // 可能是多个文件
            for (int i = 1; i < len; i++) {
                file = all[i].split("\\(")[0];
                data = all[i].split("\\(")[1].split("\\)")[0];
                // key=文件内容   val=[path+/+file]
                if (!map.containsKey(data)){
                    List<String> ls = new ArrayList<>();
                    ls.add(path+"/"+file);
                    map.put(data, ls);
                }else {
                    List<String> ls = map.get(data);
                    ls.add(path+"/"+file);
                    map.put(data, ls);
                }
            }
        }

        // 遍历hash表，把路径方式List
        map.forEach((k, v)->list.add(v));

        return list;
    }


    public static void main(String[] args) {
        SolutionHash solution = new SolutionHash();

        // String[] words = {"i", "love", "leetcode", "i", "love", "coding"};
        // List<String> rst = solution.topKFrequent(words, 2);
        // for (String s : rst) {
        //     System.out.print(s);
        // }
        // System.out.println();

        String[] files = {"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)",
                "root 4.txt(efgh)"};

        List<List<String>> list = solution.findDuplicate(files);
        for(List<String> ls : list){
            for (String s : ls){
                System.out.print(s + ",");
            }
            System.out.println();
        }
    }
}
