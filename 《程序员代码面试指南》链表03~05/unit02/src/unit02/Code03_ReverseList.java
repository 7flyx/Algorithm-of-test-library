package unit02;

/**
 * Created by flyx
 * Description: 反转单双链表
 * User: 听风
 * Date: 2021-07-28
 * Time: 9:07
 */

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Code03_ReverseList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        String[] values1 = sc.readLine().split(" ");

        int m = Integer.parseInt(sc.readLine());
        String[] values2 = sc.readLine().split(" ");

        Node head1 = new Node(0);
        Node cur = head1;
        Node pre = null;
        for (int i = 0; i < n; i++) { //单链表
            cur.next = new Node(Integer.parseInt(values1[i]));
            cur = cur.next;
        }
        head1 = head1.next;

        Node head2 = new Node(0);
        cur = head2;
        for (int i = 0; i < m; i++) {
            cur.next = new Node(Integer.parseInt(values2[i]));
            cur = cur.next;
            cur.last = pre;
            pre = cur;
        }
        head2 = head2.next;

        head1 = reverseOneWayList(head1);
        head2 = reverseTwoWayList(head2);
        while (head1 != null) {
            System.out.print(head1.val + " ");
            head1= head1.next;
        }
        System.out.println();
        while (head2 != null) {
            System.out.print(head2.val + " ");
            head2 = head2.next;
        }
    }

    public static class Node {
        public int val;
        public Node last;
        public Node next;

        public Node(int val) {
            this.val = val;
            last = null;
            next = null;
        }
    }

    public static Node reverseOneWayList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node cur = head;
        Node pre = null;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static Node reverseTwoWayList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node cur = head;
        Node next = null;
        Node pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            cur.last = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

}