package com.leet.code;


import com.sun.imageio.plugins.common.I18N;

import java.util.*;

// LeetCode 703


/**
 *  执行用时 : 131 ms, 在Kth Largest Element in a Stream的Java提交中击败了76.24% 的用户
 内存消耗 : 57.9 MB, 在Kth Largest Element in a Stream的Java提交中击败了43.19% 的用户
 */
class KthLargest2 {

    // 优先队列实现 小顶堆
    private PriorityQueue<Integer> minHeap;
    private int n;

    public KthLargest2(int k, int[] nums) {
        // 优先队列实现 默认小顶堆
        minHeap = new PriorityQueue<>(k);
        // minHeap = new PriorityQueue<>(k, (i1, i2)->{return i1 - i2;}); 大顶堆
        n = k;

        // 将nums中数据插入堆
        for (int i = 0; i < nums.length; i++){
            insert(minHeap, nums[i]);
        }
    }

    public int add(int val) {
        insert(minHeap, val);
        int rst = minHeap.peek();
        return rst;
    }

    // 如果val<=Top3(堆顶)，不插入
    // 如果val>Top3，插入(最后一个节点替换堆顶->堆化->插入到最后一个节点->堆化)
    // 堆不满，直接插入到最后一个节点，然后堆化
    public void insert(PriorityQueue<Integer> a, int m){
        int count = minHeap.size();

        // 堆满
        if (count >= n){
            if (m > a.peek()){
                // 删除堆顶，最后一个元素替换，自上往下堆化
                a.poll();
                count --;
            }else {
                return;
            }
        }
        // 插入元素到队尾，自下往上堆化
        count ++;
        a.add(m);
    }

}


class KthLargest {

    private int[] heap;
    private int n;
    private int count;

    // 构造一个小顶堆
    public KthLargest(int k, int[] nums) {
        n = k;
        count = 0;
        // root放在数组的[1]
        heap = new int[k + 1];

        // 将nums中数据插入来构建大顶堆
        for (int i = 0; i < nums.length; i++) {
            insert(heap, nums[i]);
        }
    }

    public int add(int val) {

        insert(heap, val);
        int rst = heap[1];
        return rst;
    }

    // 堆满
    // 如果val<=Top3(堆顶)，不插入
    // 如果val>Top3，插入(最后一个节点替换堆顶->堆化->插入到最后一个节点->堆化)
    // 堆不满，直接插入到最后一个节点，然后堆化
    public void insert(int[] a, int m){
        int pos = 0;
        // 堆满
        if (count >= n){
            if (m > a[1]){
                // 删除堆顶，最后一个元素替换，自上往下堆化
                a[1] = a[n];
                count --;
                heapifyFromPeak(a, count, 1);
            }else {
                return;
            }
        }
        // 插入元素到队尾，自下往上堆化
        count ++;
        a[count] = m;
        heapifyFromBottom(a, count);
    }

    /**
     *
     * @param n 堆当前元素个数
     */
    // 自下往上堆化
    public void heapifyFromBottom(int[] a, int n){
        int i = n;
        while (i/2 > 0 && a[i] < a[i/2]){
            swap(a, i, i/2);
            i = i/2;
        }
    }

    /**
     *  执行用时 : 132 ms, 在Kth Largest Element in a Stream的Java提交中击败了74.62% 的用户
        内存消耗 : 53.2 MB, 在Kth Largest Element in a Stream的Java提交中击败了80.34% 的用户
     */
    // 自上往下堆化
    public void heapifyFromPeak(int[] a, int n, int i){
        while (true){
            // 没有子树
            if (i*2 > n){
                return;
            }

            int minPos = i;
            // 右子树存在，左一定存在
            //  找小的一边向下交换
            if (i*2+1 <=n){
                if (a[i*2] < a[i*2+1]){
                    if (a[i] > a[i*2]){
                        minPos = i*2;
                    }else {
                        return;
                    }
                }else {
                    if (a[i] > a[i*2+1]){
                        minPos = i*2+1;
                    }else {
                        return;
                    }
                }
            }
            // 子树只存在一个(必然是左)
            else
            {
                if (a[i] > a[i*2]){
                    minPos = i*2;
                }else {
                    return;
                }
            }
            swap(a, i, minPos);
            i = minPos;
        }
    }


    /**
     *  执行用时 : 133 ms, 在Kth Largest Element in a Stream的Java提交中击败了72.34% 的用户
        内存消耗 : 55.1 MB, 在Kth Largest Element in a Stream的Java提交中击败了69.54% 的用户
     * @param a 堆数组
     * @param n 元素个数
     * @param i 堆化的起始位置
     */
    // 自上往下堆化
    public void heapifyFromPeak1(int[] a, int n, int i){
        while (true){
            //  找小的一边向下交换
            int minPos = i;
            if (i*2 <=n && i*2+1 <=n){
                if (a[i*2] < a[i*2+1]){
                    if (i*2 <= n && a[i] > a[i*2]){
                        minPos = i*2;
                    }else {
                        return;
                    }
                    swap(a, i, minPos);
                    i = minPos;
                }else {
                    if (i*2 <= n && a[i] > a[i*2+1]){
                        minPos = i*2+1;
                    }
                    else {
                        return;
                    }
                    swap(a, i, minPos);
                    i = minPos;
                }
            }else{
                if (i*2 <= n && a[i] > a[i*2]){
                    minPos = i*2;
                }
                else if (i*2+1 <= n && a[i] > a[i*2+1]){
                    minPos = i*2+1;
                }
                else if (minPos == i){
                    return;
                }
                swap(a, i, minPos);
                i = minPos;
            }
        }

    }

    public void swap(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}


public class SolutionHeap {

    // LeetCode 273


    /**
     *  执行用时 : 300 ms, 在Find K Pairs with Smallest Sums的Java提交中击败了5.36% 的用户
        内存消耗 : 103.4 MB, 在Find K Pairs with Smallest Sums的Java提交中击败了5.34% 的用户
     */

    // 有序数组
    // 最终要通过sum来比较大小，所以要键已和与元素对的映射关系
    // Hash表<sum, [1,2]>可以，但是sum可能相同，所以不能用
    // 改成<sum, List<[1,2],[2,1]>>，只是每次插入数据时，要取出list，插入后再put

    // 最小的K对数，类似于TopK(小顶堆)，但正好相反，所以考虑用大顶堆实现
    // 大于堆顶丢弃；小于堆顶则替换堆顶，重新堆化
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        if (nums1.length == 0 || nums2.length == 0){
            return new ArrayList<>();
        }

        // nums1中前k个数，分别和num2中前k个数组合，sum最小的组合一定在这里面
        if (k > nums1.length * nums2.length){
            k = nums1.length * nums2.length;
        }
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        initHeap(k);

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int sum = nums1[i] + nums2[j];
                int[] pair = {nums1[i], nums2[j]};
                List<int[]> list = null;

                if (map.containsKey(sum)){
                    list = map.get(sum);
                }else{
                    list = new ArrayList<>();

                }
                list.add(pair);
                map.put(sum, list);
                insert(sum);
            }
        }

        List<int[]> rst = new ArrayList<>(count);
        // 堆排序，遍历堆，根据sum，在hash表查数字对
        heapSort(heap, count);
        // 去重
        Set<Integer> set = new TreeSet<>();
        for (int i = 1; i <= count; i++) {
            set.add(heap[i]);
        }
        for (int i : set) {
            for (int[] pair : map.get(i)){
                rst.add(pair);
                if (rst.size() == k){
                    return rst;
                }
            }
        }

        return rst;
    }


    private int n;
    private int count;
    private int[] heap;

    public void initHeap(int k){
        n = k;
        count = 0;
        heap = new int[k+1]; // root放在索引1
    }

    // 大顶堆
    public void insert(int val){
        // 堆满
        if (count >= n){
            if (val < heap[1]){
                // 删除堆顶，用最后一个元素替换，自顶向下堆化
                heap[1] = heap[n];
                count --;
                heapifyFromPeak(heap, count, 1);
            }else {
                return;
            }
        }
        count ++;
        heap[count] = val;
        // 自底向上堆化
        heapifyFromBottom(heap, count);
    }

    // 大顶堆，自顶向下堆化
    public void heapifyFromPeak(int[] a, int n, int i){
        while (true){
            //  找大的一边向下交换
            int maxPos = i;
            // 左右都有
            if (i*2 <=n && i*2+1 <=n){
                if (a[i*2] > a[i*2+1]){
                    if (i*2 <= n && a[i] < a[i*2]){
                        maxPos = i*2;
                    }else {
                        return;
                    }
                    swap(a, i, maxPos);
                    i = maxPos;
                }else {
                    if (i*2 <= n && a[i] < a[i*2+1]){
                        maxPos = i*2+1;
                    }
                    else {
                        return;
                    }
                    swap(a, i, maxPos);
                    i = maxPos;
                }
            }else {
                if (i*2 <= n && a[i] < a[i*2]){
                    maxPos = i*2;
                }
                else if (maxPos == i){
                    return;
                }
                swap(a, i, maxPos);
                i = maxPos;
            }
        }

    }
    public void heapifyFromPeak2(int[] heap, int n, int pos){
        int i = pos;

        while (true){
            if (i*2 <= n && heap[i] < heap[i*2]){
                swap(heap, i, i*2);
                i = i*2;
            }
            else if (i*2+1 <= n && heap[i] < heap[i*2+1]){
                swap(heap, i, i*2+1);
                i = i*2+1;
            }
            else {
                break;
            }
        }
    }

    // 大顶堆，自底向上堆化
    public void heapifyFromBottom(int[] heap, int n){
        int i = n;
        while (i/2 > 0 && heap[i] > heap[i/2]){
            swap(heap, i, i/2);
            i = i/2;
        }
    }

    // 大顶堆排序
    public void heapSort(int[] heap, int n){
        int k = n;
        while (k > 1){
            swap(heap, 1, k);
            k --;
            heapifyFromPeak(heap, k, 1);
        }
    }


    public void swap(int[] a, int i, int j){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }



    public static void main(String[] args) {

        // -------------- LeetCode 273 -----------------------
        // int[] nums1 = {1,7,11};
        // int[] nums2 = {2,4,6};
        // int k = 3;

        // int[] nums1 = {1,1,2};
        // int[] nums2 = {1,2,3};
        // int k = 10;

        // int[] nums1 = {1,2,3,3,3};
        // int[] nums2 = {-3,22,35,56,70,100,123,200};
        // int k = 10;
        // SolutionHeap solution = new SolutionHeap();
        // List<int[]> rst = solution.kSmallestPairs(nums1, nums2, k);
        // for (int[] a : rst){
        //     for (int i : a){
        //         System.out.print(i + ",");
        //     }
        //     System.out.println();
        // }

        // -------------- LeetCode 703 -----------------------
        int k = 3;
        int[] nums = {4,5,8,2};
        KthLargest2 kthLargest = new KthLargest2(k, nums);
        int[] addNums = {3,5,10,9,4};
        //
        // int k = 1;
        // int[] nums = {};
        // KthLargest kthLargest = new KthLargest(k, nums);
        // int[] addNums = {-3,-2,-4,0,4};

        // int k = 7;
        // int[] nums = {-10,1,3,1,4,10,3,9,4,5,1};
        // KthLargest2 kthLargest = new KthLargest2(k, nums);
        // int[] addNums = {3,2,3,1,2,4,5,5,6,7,7,8,2,3,1,1,1,10,11,5,6,2,4,7,8,5,6};
        for (int i : addNums){
            System.out.print(kthLargest.add(i) + ",");
        }
        System.out.println();
    }
}
