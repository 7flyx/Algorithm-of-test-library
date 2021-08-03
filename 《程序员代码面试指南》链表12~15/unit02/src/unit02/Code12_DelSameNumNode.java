package unit02;

import java.io.*;
/**
 * Created by flyx
 * Description:  给出一个链表和一个整数 num，输出删除链表中节点值等于 num 的节点之后的链表。
 * User: 听风
 * Date: 2021-08-03
 * Time: 15:08
 */

public class Code12_DelSameNumNode {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        String[] values = sc.readLine().split(" ");
        int num = Integer.parseInt(sc.readLine());

        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;

        head = delSameNumNode(head, num);
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

    public static Node delSameNumNode(Node head, int num) {
        if (head == null) {
            return head;
        }
        while (head != null && head.val == num) {
            head = head.next; //换头结点的情况
        }

        Node pre = head;
        while (pre.next != null) {
            if (pre.next.val == num) {
                pre.next = pre.next.next;
            } else {
                pre = pre.next;
            }
        }
        return head;
    }
}