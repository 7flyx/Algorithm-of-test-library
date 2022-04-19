package class03;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 16:21
 * Description: 反转 单链表 和 双链表
 */
public class Code01_ReverseLinkedList {
    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node = reverseNode(node);
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }

        DoubleNode node2 = new DoubleNode(1);
        node2.next = new DoubleNode(2);
        node2.next.last = node2;
        node2.next.next = new DoubleNode(3);
        node2.next.next.last = node2.next;
        node2 = reverseDoubleNode(node2);
        System.out.println("反转成功");
    }

    // 反转单链表
    public static Node reverseNode(Node node) {
        if (node == null || node.next == null) { // 空节点或只有一个节点的情况，直接返回
            return node;
        }

        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    // 反转双链表
    public static DoubleNode reverseDoubleNode(DoubleNode node) {
        if (node == null || node.next == null) { // 空节点 或者只有一个节点的情况，直接返回
            return node;
        }
        DoubleNode pre = null;
        DoubleNode next = null;
        while (node != null) {
            next = node.next;
            node.last = node.next; // 更新前驱节点
            node.next = pre; // 更新后继节点

            pre = node; // 更新pre
            node = next; // 更新node
        }
        return pre;
    }
}
