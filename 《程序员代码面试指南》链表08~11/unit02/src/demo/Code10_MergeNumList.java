package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by flyx
 * Description:假设链表中每一个节点的值都在 0 - 9 之间，那么链表整体就可以代表一个整数。
 *              给定两个这种链表，请生成代表两个整数相加值的结果链表。
 *              例如：链表 1 为 9->3->7，链表 2 为 6->3，最后生成新的结果链表为 1->0->0->0。
 * User: 听风
 * Date: 2021-08-02
 * Time: 17:06
 */

public class Code10_MergeNumList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);

        String[] val1 = sc.readLine().split(" ");
        Node head1 = new Node(0);
        Node cur = head1;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(val1[i]));
            cur = cur.next;
        }
        head1 = head1.next;

        String[] val2 = sc.readLine().split(" ");
        Node head2 = new Node(0);
        cur = head2;
        for (int i = 0; i < m; i++) {
            cur.next = new Node(Integer.parseInt(val2[i]));
            cur = cur.next;
        }
        head2 = head2.next;

        Node res = mergeNumList1(head1, head2);

        while (res != null) {
            System.out.print(res.val + " ");
            res = res.next;
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

    //栈作为辅助
    public static Node mergeNumList1(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (head1 != null) {
            stack1.push(head1.val);
            head1 = head1.next;
        }
        while (head2 != null) {
            stack2.push(head2.val);
            head2 = head2.next;
        }

        Node head = null;
        Node node = null;
        int num1 = 0;
        int num2 = 0;
        int ca = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            num1 = stack1.isEmpty()? 0 : stack1.pop();
            num2 = stack2.isEmpty()? 0 : stack2.pop();
            ca = num1 + num2 + ca;
            node = new Node(ca % 10);
            node.next = head;
            head = node;
            ca /= 10;
        }
        if (ca == 1) {
            node = new Node(ca);
            node.next = head;
            head = node;
        }
        return head;
    }

    //反转链表后，进行计算，后面再反转回来
    public static Node mergeNumList2(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        head1 = reverseList(head1);
        head2 = reverseList(head2);
        Node tmp1 = head1;
        Node tmp2 = head2;
        int num1 = 0;
        int num2 = 0;
        int ca = 0; //进位
        Node res = null;
        Node node = null;
        while (head1 != null || head2 != null) {
            num1 = head1 == null? 0 : head1.val;
            num2 = head2 == null? 0 : head2.val;
            ca = num1 + num2 + ca;
            node = new Node(ca % 10);
            ca /= 10;
            node.next = res;
            res = node;
            head1 = head1 == null? null : head1.next;
            head2 = head2 == null? null : head2.next;
        }
        if (ca == 1) {
            node = new Node(ca);
            node.next = res;
            res = node;
        }

        reverseList(tmp1);
        reverseList(tmp2);
        return res;
    }

    public static Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}
