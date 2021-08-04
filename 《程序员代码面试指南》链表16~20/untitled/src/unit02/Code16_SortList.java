package unit02;

import java.io.*;
/**
 * Created by flyx
 * Description:  给定一个无序单链表，实现单链表的选择排序(按升序排序)。
*                第一行一个整数 n，表示单链表的节点数量。
 *              第二行 n 个整数 val 表示单链表的各个节点。
 * User: 听风
 * Date: 2021-08-04
 * Time: 11:51
 */

public class Code16_SortList {
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

        head = sortList(head);
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

    public static Node sortList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = null; //头插法
        Node cur = head;
        Node maxNode = null;
        Node next = null;
        Node pre = null;
        while (cur != null) {
            int max = cur.val;
            next = cur;
            pre = null;
            maxNode = cur;

            while (next.next != null) {
                if (max < next.next.val) { //找当前链表的最大值，重新接入newHead
                    pre = next;
                    maxNode = next.next;
                    max = maxNode.val;
                }
                next = next.next;
            }

            if (pre != null) {
                pre.next = maxNode.next;
            } else {
                cur = maxNode.next;
            }

            maxNode.next = newHead; //头插法
            newHead = maxNode;
        }
        return newHead;
    }
}