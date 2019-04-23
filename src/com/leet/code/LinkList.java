package com.leet.code;


import java.util.HashMap;
import java.util.List;

// 链表
public class LinkList {
    public ListNode head;
    public ListNode current;

    LinkList(){
        this.head = null;
        this.current = null;
    }

    public void add(int data){
        ListNode newNode = new ListNode(data);

        // 在头部插入节点
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
    }

    public void traverse(ListNode head){
        while (head.next != null){
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println(head.val);
    }




    public static void main(String[] args) {
        LinkList linkList = new LinkList();

        linkList.add(3);
        linkList.add(3);
        linkList.add(2);
        linkList.add(1);
        linkList.add(1);

        linkList.traverse(linkList.head);


    }



}


class SolutionLink {

    /**
     *  执行用时 : 2 ms, 在Merge Two Sorted Lists的Java提交中击败了99.44% 的用户
        内存消耗 : 37.1 MB, 在Merge Two Sorted Lists的Java提交中击败了81.42% 的用户
     */
    // 将两个链表通过比较，有序的插入到新的链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }

        ListNode head = null;
        ListNode tail = null;
        ListNode current1 = l1;
        ListNode current2 = l2;
        while (current1 != null && current2 != null){
            // System.out.print(current1.val);
            // System.out.println(current2.val);
            // 谁小谁插入新链表
            if (current1.val <= current2.val){
                if (tail == null){
                    tail = current1;
                    head = tail;
                }else {
                    tail.next = current1;
                    tail = current1;
                }
                current1 = current1.next;
            }
            else {
                if (tail == null){
                    tail = current2;
                    head = tail;
                }else {
                    tail.next = current2;
                    tail = current2;
                }
                current2 = current2.next;
            }
        }
        // 剩余列表并入新链表
        if (current1 == null){
            tail.next = current2;
        }else {
            tail.next = current1;
        }

        return head;
    }

    /**
     *  执行用时 : 2 ms, 在Merge Two Sorted Lists的Java提交中击败了99.44% 的用户
        内存消耗 : 35.2 MB, 在Merge Two Sorted Lists的Java提交中击败了91.64% 的用户
     */
    // 将两个链表通过比较，有序的插入到新的链表
    // 引入哨兵节点，减少代码复杂度
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        System.out.println("mergeTwoLists2");
        if (l1 == null){
            return l2;
        }
            if (l2 == null){
            return l1;
        }

        // 哨兵节点
        ListNode temp = new ListNode(0);
        ListNode head = temp;
        ListNode tail = temp;
        // 不改变原始参数
        ListNode current1 = l1;
        ListNode current2 = l2;
        while (current1 != null && current2 != null){
            // 谁小谁插入新链表
            if (current1.val <= current2.val){
                tail.next = current1;
                tail = current1;
                current1 = current1.next;
            }
            else {
                tail.next = current2;
                tail = current2;
                current2 = current2.next;
            }
        }
        // 剩余列表并入新链表
        if (current1 == null){
            tail.next = current2;
        }else {
            tail.next = current1;
        }

        return head.next;
    }


    public ListNode deleteDuplicates(ListNode head) {
        System.out.println("deleteDuplicates");

        // 遍历链表读取每个节点，与后面的数据相比较，有相同的则删除节点
        ListNode p = head;
        ListNode q = head.next;

        while (p != null && p.next != null){
            // 优化：将第2次遍历时间复杂度降为O(1)，利用hash表
            // 扫描过的节点存入hash表，下一个节点如果在hash表中查到，说明是重复数据，删除
            while (q != null){
                if (p.val == q.val){
                    // 删除q指向节点
                    p.next = q.next;
                }
                q = q.next;
            }
            p = p.next;
            q = p.next;
        }
        this.printNodes(head);
        return head;
    }


    /**
     *  执行用时 : 4 ms, 在Remove Duplicates from Sorted List的Java提交中击败了5.37% 的用户
        内存消耗 : 34.5 MB, 在Remove Duplicates from Sorted List的Java提交中击败了99.90% 的用户
     */
    public ListNode deleteDuplicates2(ListNode head) {
        System.out.println("deleteDuplicates2");

        if (head == null){
            return head;
        }

        // 遍历链表读取每个节点，如果是不重复节点插入新链表中
        // 判断节点是否重复的方法：
        // 每次读出节点，先去hash表中查询，如果存在，则为重复节点；否则，插入hash表

        HashMap<Integer, Integer> map = new HashMap<>();
        // 遍历链表的节点指针. head节点是第一个，必然不会是因为重复要删除的节点
        map.put(head.val, head.val);
        ListNode current = head.next;
        // 新链表的尾节点.  插入队尾时用
        ListNode tail = head;


        while (current != null){
            // 没找到，说明不是重复节点，该节点加入新链表和hash表
            if (!map.containsKey(current.val)){
                System.out.println(current.val);
                map.put(current.val, current.val);
                tail.next = current;
                tail = current;
            }
            current = current.next;
        }
        tail.next = null;
        this.printNodes(head);
        return head;
    }


    public ListNode deleteDuplicates3(ListNode head) {
        System.out.println("deleteDuplicates3");

        if (head == null || head.next == null){
            return head;
        }

        // 遍历链表读取每个节点，如果是不重复节点插入新链表中
        // 判断节点是否重复的方法：
        // 每次读出节点，先去hash表中查询，如果存在，则为重复节点；否则，插入hash表

        HashMap<Integer, Integer> map = new HashMap<>();
        // 遍历链表的节点指针
        ListNode current = head;

        // 遍历链表，把每个节点出现次数计入hash表
        while (current != null){
            // 没找到，第一次出现
            if (!map.containsKey(current.val)){
                map.put(current.val, 1);
            }else {
                // 次数加1
                map.put(current.val, map.get(current.val)+1);
                System.out.print(current.val);
                System.out.println( map.get(current.val));
            }
            current = current.next;
        }

        current = head;
        // 新链表的尾节点.  插入队尾时用
        ListNode tail = null;
        head = null;

        // 遍历链表，把次数大于1的节点删除
        while (current != null){
            System.out.print(current.val);
            System.out.println(map.get(current.val));

            // 加入新链表
            if (map.get(current.val) == 1){
                if (tail == null){
                    tail = current;
                    head = current;
                }else {
                    tail.next = current;
                    tail = current;
                }
            }
            current = current.next;
        }
        if (tail != null) {
            tail.next = null;
        }

        return head;
    }

    // public boolean deleteNode(ListNode node){
    //     if ()
    // }


    public void printNodes(ListNode head){
        while (head.next != null){
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println(head.val);
    }


    public static void main(String[] args) {
        // 链表初始化
        LinkList linkList = new LinkList();
        LinkList linkList1 = new LinkList();

        linkList.add(6);
        linkList.add(4);
        linkList.add(2);
        linkList.add(1);
        linkList.traverse(linkList.head);

        linkList1.add(4);
        linkList1.add(3);
        linkList1.add(1);
        linkList.traverse(linkList1.head);

        SolutionLink solution = new SolutionLink();

        // ListNode node = solution.deleteDuplicates3(linkList.head);
        // solution.printNodes(node);

        ListNode node = solution.mergeTwoLists2(linkList.head, linkList1.head);
        solution.printNodes(node);
    }
}
