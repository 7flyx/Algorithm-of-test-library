package class10;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-04
 * Time: 16:03
 * Description: 给你两个单链表，有可能是存在有环的。现在问你两个单链表相交的第一个节点是？如果没有的话，就返回null
 */
public class Code05_TwoIntersectList {

    private static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

    }

    // 获取两个单链表相交的第一个节点
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) { // 两个无环链表的相交问题
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) { // 两个有环链表的相交问题
            return hasLoop(head1, loop1, head2, loop2);
        }
        return null; // 只有其中一个链表有环时，两个链表绝对不可能相交
    }

    // 获取入环节点
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) { // 必须大于3个节点的情况
            return null;
        }

        Node slow = head.next; // 第2个节点开始
        Node fast = head.next.next; // 第3个节点开始
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) { // 末尾是null的时候，直接返回即可
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        // 循环停下时，肯定是两个节点相遇了
        fast = head; // 从头节点再次出发
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // 循环再次停下来时，就是入环节点
    }

    // 两个无环链表的相交问题
    public static Node noLoop(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) { // 两个链表的最后一个节点没相交，说明整个链表都不相交
            return null;
        }
        cur1 = n > 0 ? head1 : head2; // cur1指向长链表
        cur2 = cur1 == head1 ? head2 : head1; // cur2指向短链表
        n = Math.abs(n);
        while (n-- != 0) { // 长链表提前走多出来的长度
            cur1 = cur1.next;
        }
        while (cur1 != cur2) { // 两个链表长度想等时，同时走就行
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 两个有环链表的相交问题
    public static Node hasLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (loop1 == loop2) { // 两个入环节点相同的时，只需判断环以外的那段
            Node cur1 = head1;
            Node cur2 = head2;
            int n = 0;
            while (cur1.next != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2.next != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2; // 指向长链表
            cur2 = cur1 == head1 ? head2 : head1; // 指向短链表
            n = Math.abs(n);
            while (n-- != 0) {
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else { // 不相等的话，只有两种情况，一个是两个链表并不相交，一个是两个入环节点在圆圈上
            // 让其中一个入环节点，绕着圆圈跑一圈，能够发现与另外一个入环节点相遇，说明就是相交节点
            Node runNode = loop1.next;
            while(runNode != loop1) {
                if (runNode == loop2) { // 在圆圈上能够与loop2相遇，说明有相交
                    return loop2;
                }
                runNode = runNode.next;
            }
        }
        return null;
    }

    // 两个无环链表的相交节点，解法2
    public static Node noLoop2(Node headA, Node headB) {
        Node head1 = headA;
        Node head2 = headB;
        while (head1 != head2) {
            head1 = head1.next == null? headB : head1.next; // 遇到null时，连上head2
            head2 = head2.next == null? headA : head2.next; // 遇到null时，连上head1
        }
        return head1;
    }
}
