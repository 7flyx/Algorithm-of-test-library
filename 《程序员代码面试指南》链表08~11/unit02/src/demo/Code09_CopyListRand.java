package demo;

import java.util.HashMap;

/**
 * Created by flyx
 * Description:复制一个含有随机指针的链表，并返回复制的链表
 * User: 听风
 * Date: 2021-08-02
 * Time: 16:30
 */
public class Code09_CopyListRand {
    public static void main(String[] args) {

    }

    public static class Node {
        public int val;
        public Node next;
        public Node rand;
        public Node (int val) {
            this.val = val;
        }
    }

    public static Node copyRandList1(Node head) {
        if (head == null) {
            return head;
        }

        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    public static Node copyRandList2(Node head) {
        if (head == null) {
            return head;
        }

        //通过复制一个结点，连接在这个结点的后面
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        while (cur != null) {
            Node next = cur.next.next;
            Node copy = cur.next;
            copy.rand = cur.rand == null? null: cur.rand.next; //直接下级结点
            cur = next;
        }

        //分离链表
        Node res = head.next;
        cur = head;
        while (cur != null) {
            Node next = cur.next.next;
            Node copy = cur.next;
            cur.next = next;
            copy.next = next == null? null : next.next;
            cur = next;
        }
        return res;
    }
}
