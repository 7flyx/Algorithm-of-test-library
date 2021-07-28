package unit02;

/**
 * Created by flyx
 * Description: 一行两个整数 n 和 m， n 表示环形链表的长度， m 表示每次报数到 m 就自杀。  约瑟夫环问题（初阶）
 * User: 听风
 * Date: 2021-07-28
 * Time: 11:02
 */

import java.util.Scanner;

public class Code05_JosephusKill {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //链表的长度
        int m = sc.nextInt(); //报的数

        Node head = new Node(0);
        Node cur = head;
        for (int i = 1; i <= n; i++) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        head = head.next;
        cur.next = head;


        System.out.println(josephusKill1(head, m).val);
        System.out.println(josephusKill2(head, m).val);
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    //书上的源码
    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }

        Node last = head;
        while (last.next != head) {
            last = last.next;
        }

        int k = 0;
        while (head != last) {
            if (++k == m) { //省去了pre指针
                last.next = head.next;
                k = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    //自己写的
    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == null) {
            return head;
        }

        int k = 0;
        while (head.next != head) {
            if (++k == m - 1) { //省去了pre指针
                head.next = head.next.next;
                k = 0;
            }
            head = head.next;
        }
        return head;
    }
}
