package unit02;

/**
 * Created by flyx
 * Description:给定一个单链表，在链表中把第 L 个节点到第 R 个节点这一部分进行反转。
 * User: 听风
 * Date: 2021-07-28
 * Time: 9:56
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Code04_ReversePartList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        String[] values = sc.readLine().split(" ");
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;

        String[] nums = sc.readLine().split(" ");
        //head = reversePartList1(head, Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
        head = reversePartList2(head, Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    //自己写的
    public static Node reversePartList1(Node head, int from, int to) {
        if (head == null || head.next == null || from > to) {
            return head;
        }
        int pace = to - from + 1; //总需要走的步数
        Node LHead = head; //左边的需要接上的头结点
        while (--from > 1) {
            LHead = LHead.next;
        }
        Node pre = null;
        Node cur = from + 1 == 1? head : LHead.next; //上面的while循环，先自减了，所以要from + 1
        Node next = null;
        while (cur != null && pace-- != 0) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        if (from + 1 == 1) { //实则就是，from == 1时，需要换头结点
            head.next = next;
            return pre;
        } else if (cur != null) {
            Node tmp = LHead.next;
            LHead.next = pre;
            tmp.next = next;
            return head;
        }
        LHead.next = pre; //R太大时，链表的节点数不够的话，就返回当前已经反转好的链表即可
        return head;
    }

    //书上的源码
    public static Node reversePartList2(Node head, int from, int to) {
        if (head == null || head.next == null) {
            return head;
        }
        int len = 0;
        Node node1 = head;
        Node fPre = null; //from的前一结点
        Node tPos = null; //to的后一结点
        while (node1 != null) {
            len++;
            fPre = len == from - 1? node1 : fPre;
            tPos = len == to + 1? node1 : tPos;
            node1 = node1.next;
        }
        if (from < 1 || to > len || from > to) {
            return head; //from和to，不在链表范围内
        }
        node1 = fPre == null? head : fPre.next;
        Node node2 = node1.next;
        node1.next = tPos; //提前将后面的结点直接接上
        while (node2 != tPos) {
            Node next = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = next;
        }
        if (fPre != null) {
            fPre.next = node1; //反转结点的最后一个结点，类似于pre
            return head;
        }
        return node1; //如果fPre是null，返回新的头结点就行
    }
}
