package unit02;

/**
 * Created by flyx
 * Description: 两个单链表相交的一系列问题
 *              题意： 在本题中，单链表可能有环，也可能无环。给定两个单链表的头结点head1和head2，这两个链表可能相交，也可能不相交。
 *              请实现一个函数，如果两个链表相交，请返回相交的第一个结点，如果不相交，返回null即可
 *
 *              注：一个链表有环，一个链表无环。不可能相交
 * User: 听风
 * Date: 2021-08-03
 * Time: 15:34
 */
public class Code13_TwoNodeListCross {
    public static void main(String[] args) {

    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    public static Node getSameNodeOfTwoList(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        //先是判断两个链表是否有环，有环的话，返回第一个入环结点
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        if (loop1 == null && loop2 == null) { //两个无环链表判断是否相交
            return noLoopListCross(head1, head2);
        }
        if (loop1 != null && loop2 != null) { //两个有环链表判断是否相交
            return bothLoopListCross(head1, loop1, head2, loop2);
        }
        return null; //一个有环，一个无环，必不相交
    }

    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) { //空、一个结点、两个结点，构不成环
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        //既然停下来了，说明已经相交了
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; //入环结点
    }

    public static Node noLoopListCross(Node head1, Node head2) {
        int n = 0; //从0开始，都少一个结点
        Node cur1 = head1;
        Node cur2 = head2;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if(cur1 != cur2) {
            return null; //尾结点不一样，不相交
        }
        cur1 = n > 0? head1 : head2; //长链表
        cur2 = cur1 == head1? head2 : head1; //短链表
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next; //长的链表，先走一段
        }
        while (cur1.val != cur2.val) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;

        /*
            方法二
        while (head1 != head2) {
            head1 = head1 == null? head2 : head1.next;
            head2 = head2 == null? head1 : head2.next;
        }
        return head1;
         */
    }

    public static Node bothLoopListCross(Node head1, Node loop1, Node head2, Node loop2) {
        if(loop1 == loop2) { //结点相同的情况，说明在入环前就相遇了,跟无环相遇的一样
            while (head1 != head2) {
                head1 = head1 == loop1? head2 : head1.next;
                head2 = head2 == loop2? head1 : head2.next;
            }
            return head1;
        }

        //入环结点不一样，说明在环中可能相遇
        Node cur = loop1.next;
        while (cur != loop1) {
            if (cur.val == loop2.val) {
                return loop1; //返回loop1或者loop2都可以
            }
            cur = cur.next;
        }
        return null;
    }
}
