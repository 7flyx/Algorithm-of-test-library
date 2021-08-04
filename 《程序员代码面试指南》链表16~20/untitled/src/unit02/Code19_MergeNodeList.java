package unit02;
import java.io.*;

/**
 * Created by flyx
 * Description: 给定两个升序的单链表的头节点 head1 和 head2，请合并两个升序链表， 合并后的链表依然升序，并返回合并后链表的头节点。
 * User: 听风
 * Date: 2021-08-04
 * Time: 16:23
 */

public class Code19_MergeNodeList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine()); //单链表1
        String[] values1 = sc.readLine().split(" ");
        Node head1 = new Node(0);
        Node cur = head1;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values1[i]));
            cur = cur.next;
        }
        head1 = head1.next;

        int m = Integer.parseInt(sc.readLine()); //单链表2
        String[] values2 = sc.readLine().split(" ");
        Node head2 = new Node(0);
        cur = head2;
        for (int i = 0; i < m; i++) {
            cur.next = new Node(Integer.parseInt(values2[i]));
            cur = cur.next;
        }
        head2 = head2.next;

        cur = mergeNodeList(head1, head2);
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
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

    public static Node mergeNodeList(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return head1 == null? head2 : head1;
        }

        Node newHead = head1.val <= head2.val? head1 : head2;
        Node tail = newHead;
        if (newHead.val == head1.val) {
            head1 = head1.next;
        } else {
            head2 = head2.next;
        }

        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                tail.next = head1;
                tail = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                tail = head2;
                head2 = head2.next;
            }
        }
        tail.next = head1 == null? head2 : head1;
        return newHead;

    }
}
