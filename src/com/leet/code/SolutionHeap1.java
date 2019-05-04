package com.leet.code;


import com.sun.org.apache.bcel.internal.generic.SWAP;

class KthLargest1 {

    private int[] heap;
    private int n;
    private int count;

    // 构造一个堆
    public KthLargest1(int k, int[] nums) {
        n = k;
        count = 0;
        // root放在数组的[1]
        heap = new int[k + 1];

        // 将nums中数据插入来构建大顶堆
        for (int i = 0; i < nums.length; i++) {
            insert1(heap, nums[i]);
        }
    }

    // 在堆中查询，如果<=Top3，直接返回Top3
    // 如果>Top3，插入，重新堆化，然后返回Top3
    // 插入时，如果堆满，删除最小值，再插入
    public int add1(int val) {

        insert1(heap, val);
        int rst = getTopK(heap, n);

        return rst;
    }

    // 边插入边堆化
    public void insert1(int[] a, int m){
        // 堆满
        // 如果插入数 > Top3最小元素，替换
        // 否则，不插入
        int pos = 0;
        if (count >= n){
            int topk = getTopK(a, n);
            if (m > topk){
                pos = getTopKPos(a, topk);
            }else {
                return;
            }
        }else {
            count ++;
            pos = count;
        }

        a[pos] = m;
        // 自下往上堆化
        int i = pos;
        while (i/2 > 0 && a[i] > a[i/2]){
            swap(a, i, i/2);
            i = i/2;
        }
    }

    // 查找Top
    // 每次删除大顶堆的堆顶，重新堆化。K次
    public int getTopK(int[]heap, int k){
        int len = heap.length;
        int[] tmp = new int[len];
        for (int i = 0; i < len; i++) {
            tmp[i] = heap[i];
        }

        for (int i = 0; i < k-1; i++) {
            // 删除堆顶，最后一个元素替换
            tmp[1] = tmp[len-1];
            len --;
            // 重新堆化
            heapify(tmp, len, 1);
        }

        return tmp[1];
    }

    public int getTopKPos(int[]heap, int k){
        int len = heap.length;

        int i = 0;
        for (i = 1; i < len; i++) {
            if (heap[i] == k){
                break;
            }
        }

        return i;
    }

    // 自上往下堆化
    public void heapify(int[] a, int n, int i){
        while (true){
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]){
                maxPos = i*2;
            }
            else if (i*2+1 <= n && a[maxPos] < a[i*2+1]){
                maxPos = i*2+1;
            }
            else if (maxPos == i){
                break;
            }
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    public void swap(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}


public class SolutionHeap1 {

    public static void main(String[] args) {
        int k = 3;
        int[] nums = {4,5,8,2};
        KthLargest1 kthLargest = new KthLargest1(k, nums);
        int[] addNums = {3,5,10,9,4};
        //
        // int k = 1;
        // int[] nums = {};
        // KthLargest kthLargest = new KthLargest(k, nums);
        // int[] addNums = {-3,-2,-4,0,4};
        for (int i : addNums){
            System.out.print(kthLargest.add1(i) + ",");
        }
        System.out.println();
    }
}
