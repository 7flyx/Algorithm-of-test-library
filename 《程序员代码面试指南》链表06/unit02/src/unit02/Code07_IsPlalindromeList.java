package unit02;

import java.util.Stack;
import java.util.Scanner;

/**
 * Created by flyx
 * Description:给定一个链表，请判断该链表是否为回文结构。
 * User: 听风
 * Date: 2021-07-30
 * Time: 12:58
 */

public class Code07_IsPlalindromeList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //链表的长度
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(sc.nextInt());
            cur = cur.next;
        }
        head = head.next;
        System.out.println(isPlalindromeList1(head, n));

    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    //将链表全部压入栈中
    public static boolean isPlalindromeList1(Node head, int size) {
        if (head == null || head.next == null) {
            return true;
        }
        Node cur = head;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            stack.push(cur.val);
            cur = cur.next;
        }

        for (int i = 0; i < size; i++) {
            if (head.val != stack.pop()) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //压入一半的数据
    public static boolean isPlalindromeList2(Node head, int size) {
        if (head == null || head.next == null) {
            return true;
        }

        Node cur = head;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size / 2; i++) { //先压入前半部分
            stack.push(cur.val);
            cur = cur.next;
        }

        if ((size & 1) == 1) { //奇数个结点，跳过中间结点
            cur = cur.next;
        }

        for (int i = 0; i < size / 2 ; i++) {
            if (cur.val != stack.pop()) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    //进阶解法，将右边部分，反转链表，指向中间结点
    public static boolean isPlalindromeList3(Node head, int size) {
        if (head == null || head.next == null) {
            return true;
        }

        boolean res = true;
        Node leftStart = head;
        Node rightStart = null;
        Node cur = head;
        for (int i = 0; i < size / 2; i++) {
            cur = cur.next;
        }

        rightStart =  reverseList(cur); //右半部分的头结点
        cur = rightStart;
        for (int i = 0; i < size / 2; i++) {
            if (cur.val != leftStart.val) {
                res = false;
                break;
            }
            cur = cur.next;
            leftStart = leftStart.next;
        }
        reverseList(rightStart); //恢复链表，不需要接收返回值。本身上一个结点的next域，没被修改
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
