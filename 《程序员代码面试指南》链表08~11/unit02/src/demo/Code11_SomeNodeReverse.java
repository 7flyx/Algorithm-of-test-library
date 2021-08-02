package demo;

import java.io.*;
/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-02
 * Time: 17:57
 */

public class Code11_SomeNodeReverse {
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
        int k = Integer.parseInt(sc.readLine());
        head = someNodeReverse(head, k);
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

    public static Node someNodeReverse(Node head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int[] nums = new int[k];
        Node preNode = head;
        Node cur = head;
        while(cur != null) {
            int i = 0;
            for (i = 0; i < k && cur != null; i++) {
                nums[i] = cur.val;
                cur = cur.next;
            }
            if (i == k) {
                for (i = k - 1; i >= 0; i--) {
                    preNode.val = nums[i];
                    preNode = preNode.next;
                }
            }
        }
        return head;
    }
}
