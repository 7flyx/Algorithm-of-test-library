package demo;

import java.io.*;
/**
 * Created by flyx
 * Description: 给定一个链表，再给定一个整数 pivot，请将链表调整为左部分都是值小于 pivot 的节点，
 *              中间部分都是值等于 pivot 的节点， 右边部分都是大于 pivot 的节点。
 * User: 听风
 * Date: 2021-08-02
 * Time: 15:56
 */

public class Code08_ListPartition {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int pivot = Integer.parseInt(nums[1]);

        String[] values = sc.readLine().split(" ");
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;

        head = listPartition1(head, pivot, n); //以数组的存储形式进行调整
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        sc.close();
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    //方法一：数组存储的形式
    public static Node listPartition1(Node head, int pivot, int size) {
        if (head == null || head.next == null) {
            return head;
        }
        Node cur = head;
        Node[] arr = new Node[size];
        for (int i = 0; i < size; i++) {
            arr[i] = cur;
            cur = cur.next;
        }

        arrPartition(arr, pivot);
        for (int i = 1; i < arr.length; i++) {
            arr[i - 1].next = arr[i];
        }
        arr[arr.length - 1].next = null; //最后一个结点
        return arr[0];
    }

    public static void arrPartition(Node[] arr, int pivot) {
        if (arr == null) {
            return;
        }

        int small = -1; //小于区域
        int big = arr.length; //大于区域
        int index = 0;
        while (index != big) {
            if (arr[index].val < pivot) {
                swap(arr, ++small, index++);
            } else if (arr[index].val > pivot) {
                swap(arr, --big, index);
            } else {
                index++;
            }
        }
    }

    public static void swap(Node[] arr, int L, int R) {
        Node tmp = arr[L];
        arr[L] = arr[R];
        arr[R] = tmp;
    }

    //方法二：定义六个引用变量。分别代表三个区域的头尾指针
    public static Node listPartition2(Node head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }

        Node SH = null;
        Node ST = null; //小于区域的头尾指针

        Node MH = null;
        Node MT = null; //等于区域的头尾指针

        Node BH = null;
        Node BT = null; //大于区域的头尾指针

        while (head != null) {
            Node next = head.next;
            head.next = null; //提前置为null
            if (head.val < pivot) {
                if (SH == null) {
                    SH = head;
                    ST = head;
                } else {
                    ST.next = head;
                    ST = ST.next;
                }
            } else if (head.val > pivot) {
                if (BH == null) {
                    BH = head;
                    BT = head;
                } else {
                    BT.next = head;
                    BT = BT.next;
                }
            } else {
                if (MH == null) {
                    MH = head;
                    MT = head;
                } else {
                    MT.next = head;
                    MT = MT.next;
                }
            }
            head = next;
        }

        //连接三个区域的指针
        if (SH != null) {
            ST.next = MH;
            MT = MT == null? ST : MT;
        }

        if (MT != null) {
            MT.next = BH;
        }
        return SH != null? SH : (MH != null? MH : BH);
    }
}
