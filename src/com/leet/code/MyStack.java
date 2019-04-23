package com.leet.code;

import java.util.Arrays;
import java.util.Stack;

public class MyStack {

}


class SolutionStack {

    // LeetCode 503
    // 遍历数组，每个数都和其后面的数据比较，直到循环回到自己的位置
    // 100,    1, 11, 1, 110, 109
    // 当前值   小  小  小  大
    // 那么当前值 -> 110 之间的数据，都应该用110替换。
    // 可以用栈来暂存这些数据。栈空表明区间内的所有数据都替换完成
    // 由于我们是替换数据，所以栈中存的应该是元素的索引值


    /**
     *  执行用时 : 41 ms, 在Next Greater Element II的Java提交中击败了85.24% 的用户
        内存消耗 : 39 MB, 在Next Greater Element II的Java提交中击败了98.20% 的用户
     */

    // 取一个元素，如果该元素比栈顶元素小，直接压栈
    // 如果比栈顶元素大，则开始替换栈中元素。最后将该元素压栈
    public int[] nextGreaterElements2(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int len = nums.length;
        int[] rst = new int[len];
        Arrays.fill(rst, -1);

        // 由于是循环数组，遍历时长度取2*len
        for (int i = 0; i < 2 * len; i++) {
            int ii = i % len;
            // 出栈条件判断
            while (!stack.isEmpty() && nums[ii] > nums[stack.peek()]) {
                rst[stack.pop()] = nums[ii];
            }

            // 入栈条件判断
            // 由于当前元素可能导致出栈操作，所以要等出栈完成后，再做入栈判断
            if (i < len){
                stack.push(ii);
            }
        }

        return rst;
    }

    /**
     *  执行用时 : 50 ms, 在Next Greater Element II的Java提交中击败了78.54% 的用户
        内存消耗 : 51.1 MB, 在Next Greater Element II的Java提交中击败了66.67% 的用户
     */
    public int[] nextGreaterElements1(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int len = nums.length;
        int[] rst = new int[len];
        Arrays.fill(rst, -1);

        // 由于是循环数组，遍历时长度取2*len
        for (int i = 0; i < 2 * len; i++) {
            int ii = i % len;
            // 出栈条件判断
            if (!stack.isEmpty() && nums[ii] > nums[stack.peek()]) {
                while (!stack.isEmpty()) {
                    // 值相等结束出栈
                    if (nums[ii] <= nums[stack.peek()]){
                        break;
                    }
                    rst[stack.pop()] = nums[ii];
                }
            }
            // 入栈条件判断
            // 由于当前元素可能导致出栈操作，所以要等出栈完成后，再做入栈判断
            if (i < len){
                stack.push(ii);
            }
        }

        return rst;
    }

    /**
     *  执行用时 : 58 ms, 在Next Greater Element II的Java提交中击败了68.78% 的用户
        内存消耗 : 40.8 MB, 在Next Greater Element II的Java提交中击败了97.30% 的用户
     */
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int len = nums.length;
        int[] rst = new int[len];
        Arrays.fill(rst, -1);

        // 由于是循环数组，遍历时长度取2*len
        for (int i = 0; i < 2 * len; i++) {
            int ii = i % len;
            // 出栈条件判断
            if (!stack.isEmpty() && nums[ii] > nums[stack.peek()]) {
                while (!stack.isEmpty()) {
                    // 当前值不再大于栈顶值结束出栈
                    if (nums[ii] <= nums[stack.peek()]){
                        break;
                    }
                    rst[stack.pop()] = nums[ii];
                }
            }
            // 入栈条件判断
            // 由于当前元素可能导致出栈操作，所以要等出栈完成后，再做入栈判断
            if (i < len && stack.isEmpty()) {
                stack.push(ii);
                continue;
            }
            if (!stack.isEmpty() && nums[ii] <= nums[stack.peek()]) {
                if (i < len) {
                    stack.push(ii);
                }
            }

        }

        return rst;
    }

    /**
     *  执行用时 : 6 ms, 在Valid Parentheses的Java提交中击败了89.11% 的用户
        内存消耗 : 33.7 MB, 在Valid Parentheses的Java提交中击败了93.14% 的用户
     */
    // 利用栈实现括号匹配检查
    // 左括号入栈，右括号出栈
    public boolean isValid(String s) {
        if (s.length() == 0){
            return true;
        }

        if (s.length() == 1){
            return false;
        }

        Stack stack = new Stack();

        char c = '0';
        char d = '0';

        // 遍历str
        for (int i = 0; i < s.length() ; i++) {
            c = s.charAt(i);
            if (c=='(' || c=='{' || c=='['){
                stack.push(c);
            }

            if (c==')' || c=='}' || c==']'){
                if (stack.isEmpty()){
                    return false;
                }

                d = (char)stack.pop();
                if ((c==')' && d=='(') || (c=='}' && d=='{') || (c==']' && d=='[')){

                }else {
                    return false;
                }
            }
        }
        // 栈应该是空
        if (!stack.empty()){
            return false;
        }

        return true;
    }


    /**
     *  执行用时 : 4 ms, 在Valid Parentheses的Java提交中击败了97.47% 的用户
        内存消耗 : 36.1 MB, 在Valid Parentheses的Java提交中击败了43.49% 的用户
     */
    public boolean isValid2(String s) {
        if (s.length() == 0){
            return true;
        }

        if (s.length() == 1){
            return false;
        }

        Stack stack = new Stack();

        char c = '0';
        char d = '0';

        // 遍历str
        for (int i = 0; i < s.length() ; i++) {
            c = s.charAt(i);
            if (c=='(' || c=='{' || c=='['){
                if (c == '('){
                    stack.push(')');
                }else if (c == '{'){
                    stack.push('}');
                }else {
                    stack.push(']');
                }
            }

            if (c==')' || c=='}' || c==']'){
                if (stack.isEmpty()){
                    return false;
                }

                d = (char)stack.pop();
                if (c != d){
                    return false;
                }
            }
        }
        // 栈应该是空
        if (!stack.empty()){
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        SolutionStack solution = new SolutionStack();

        // String s = "()[]{}";
        // System.out.println(solution.isValid2(s));
        //
        // s = "(ert)9";
        // System.out.println(solution.isValid2(s));
        //
        // s = "[])";
        // System.out.println(solution.isValid2(s));

        // int[] A = {1, 4, 4, 4, 3, 2, 3};
        int[] A = {1,2,3,4,5,6,5,4,5,1,2,3};

        int[] B = solution. nextGreaterElements1(A);

        for (int a: B) {
            System.out.print(a + ",");
        }
        System.out.println();

    }
}
