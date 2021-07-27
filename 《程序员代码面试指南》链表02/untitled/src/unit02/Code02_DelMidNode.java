package unit02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by flyx
 * Description: 给定一个链表，实现删除链表第 K 个节点的函数。
 * User: 听风
 * Date: 2021-07-27
 * Time: 11:33
 */

public class Code02_DelMidNode {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);
        String[] values = sc.readLine().split(" ");
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;

        head = delMidNode(head, m);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
            next = null;
        }
    }

    public static Node delMidNode(Node head, int m) {
        if (head == null || m < 1) {
            return head;
        }

        if (m == 1) {
            return head.next;
        }

        Node cur = head;
        Node pre = null;
        while (--m > 0 && cur != null) {
            pre = cur;
            cur = cur.next;
        }
        if (cur != null) {
            pre.next = cur.next;
            return head;
        }
        return null;
    }
}
