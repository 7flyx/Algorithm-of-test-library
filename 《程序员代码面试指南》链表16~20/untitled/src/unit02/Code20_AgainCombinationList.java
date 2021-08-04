package unit02;

import java.io.*;
/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-04
 * Time: 17:01
 */

public class Code20_AgainCombinationList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        String[] values1 = sc.readLine().split(" ");
        Node head1 = new Node(0);
        Node cur = head1;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values1[i]));
            cur = cur.next;
        }
        head1 = head1.next;
        againCombinationList(head1);
        while (head1 != null) {
            System.out.print(head1.val + " ");
            head1 = head1.next;
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

    public static void againCombinationList(Node head) {
        if (head == null || head.next == null) {
            return;
        }
        //首先将链表分为两个链表
        Node mid = head;
        Node right = head.next;
        while (right.next != null && right.next.next != null) {
            right = right.next.next;
            mid = mid.next;
        }
        right = mid.next;
        mid.next = null;
        mergeLR(head, right);
    }

    public static void mergeLR(Node left, Node right) {
        Node next = null;
        while (left.next != null) {
            next = right.next; //先从右半部分开始
            right.next = left.next; //连左部分的下一个
            left.next = right; //再连右部分第一个

            left = right.next; //往后走
            right = next;
        }
        left.next = right; //最后停止下来，左部分到头了，右部分还没有
    }
}
