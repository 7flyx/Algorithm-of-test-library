package unit02;

import java.util.HashSet;

/**
 * Created by flyx
 * Description: 删除无序单链表中重复出现的节点
 *              题意：给定一个无序的单链表，删除其中重复出现的结点；例如：
 *              1->2->2->2->3->2->3->1->4->5->null     》  1->2->3->4->5->null
 * <p>
 *          两种方法： 1、如果链表的长度为N，请实现时间复杂度达到O(N) （哈希表）
 *                   2、额外空间复杂度为O(1)  （类似于选择排序）
 * User: 听风
 * Date: 2021-08-03
 * Time: 17:28
 */
public class Code14_DelRepeatNode {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 4, 2, 1, 1, 5, 9, 5, 4, 1, 1, 2, 2, 36, 65, 95};
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < arr.length; i++) {
            cur.next = new Node(arr[i]);
            cur = cur.next;
        }
        head = head.next;
        printList(head);
        head = delRepeat3(head);
        printList(head);
    }

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    //哈希表
    public static Node delRepeat1(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        HashSet<Integer> set = new HashSet<>();
        Node cur = head.next;
        Node pre = head;
        set.add(head.val);
        while (cur != null) {
            if (set.contains(cur.val)) {
                pre.next = cur.next;
            } else {
                set.add(cur.val);
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    //类似于选择排序。从前往后比较
    public static Node delRepeat2(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node flagNum = head;
        Node cur = head.next;
        Node pre = null;
        while (cur != null) {
            Node next = cur;
            pre = flagNum;
            while (next != null) {
                if (flagNum.val == next.val) {
                    pre.next = next.next;
                } else {
                    pre = next; //紧跟着next走
                }
                next = next.next;
            }
            printList(head);
            flagNum = cur;
            cur = cur.next;
        }
        return head;
    }

    //和delRepeat2,一样的
    public static Node delRepeat3(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null;
        Node cur = head;
        Node next = null;
        while (cur != null) {
            pre = cur;
            next = cur.next;
            while (next != null) {
                if (cur.val == next.val) {
                    pre.next = next.next;
                } else {
                    pre = next;
                }
                next = next.next;
            }
            printList(head);
            cur = cur.next;
        }
        return head;
    }

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println();
    }
}
