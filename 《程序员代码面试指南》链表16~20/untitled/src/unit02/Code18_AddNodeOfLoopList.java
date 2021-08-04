package unit02;

import java.io.*;

/**
 * Created by flyx
 * Description:  一个环形单链表从头节点 head 开始不降序，同时由最后的节点指回头节点。
 *              给定这样一个环形单链表的头节点 head 和 一个整数 num， 请生成节点值为 num 的新节点，
 *              并插入到这个环形链表中，保证调整后的链表依然有序。
 * User: 听风
 * Date: 2021-08-04
 * Time: 15:58
 */

public class Code18_AddNodeOfLoopList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine()); //链表长度
        String[] values = sc.readLine().split(" ");
        int num = Integer.parseInt(sc.readLine()); //需要插入的数
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;
        head = addNodeOfLoopList(head, num);
        for (int i = 0; i < n + 1; i++) {
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

    public static Node addNodeOfLoopList(Node head, int num) {
        Node node = new Node(num);
        Node pre = head;
        Node cur = head.next;
        while (cur != head) {
            if (num > cur.val) {
                pre = cur;
                cur = cur.next;
            } else {
               break;
            }
        }
        node.next = pre.next;
        pre.next = node;
        return head.val < num ? head : node; //判断，有可能num最大，或者最小，需要换头
    }
}
