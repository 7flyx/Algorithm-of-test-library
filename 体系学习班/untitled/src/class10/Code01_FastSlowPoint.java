package class10;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-02
 * Time: 16:33
 * Description: 快慢指针练习
 * 1)输入链表头节点，奇数长度返回中点，偶数长度返回上中点
 * 2)输入链表头节点，奇数长度返回中点，偶数长度返回下中点
 * 3)输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
 * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 */
public class Code01_FastSlowPoint {
    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
//        node.next.next.next = new Node(4);
//        node.next.next.next.next = new Node(5);
        System.out.println(getMinNode4(node).value);
    }

    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    //    1)输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    //    快慢指针都是从第1个节点开始，循环卡 快指针的后两个节点
    public static Node getMinNode1(Node node) {
        if (node == null) {
            return null;
        }

        Node fast = node; // 快指针
        Node slow = node; // 慢指针
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //    2)输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    //    快慢指针都是从第1个节点开始，卡住 fast ！= null 和 fast的下一个节点！=null
    public static Node getMinNode2(Node node) {
        if (node == null || node.next == null) { // 没有节点，或者只有一个节点的时候，直接返回
            return null;
        }

        Node fast = node; // 快指针
        Node slow = node; // 慢指针
        // 卡的是 fast 和 fast下一个节点
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    //    3)输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    //    慢指针从第1个节点开始，快指针从第3个节点开始。 卡的是fast的后两个节点
    public static Node getMinNode3(Node node) {
        if (node == null || node.next == null || node.next.next == null) { // 节点必须大于等于3个
            return null;
        }

        Node slow = node; // 第1个节点开始
        Node fast = node.next.next; // 第3个节点开始
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // fast起点是第3个节点，每次时跳跃2个节点，也就是fast指针始终是在奇数节点上
        return slow;
    }

    //    4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个(也就是上中点)
    //    慢指针从第1个节点开始，快指针从第2个节点开始。卡的是fast的后两个节点
    public static Node getMinNode4(Node node) {
        if (node == null) {
            return null;
        }

        Node slow = node;
        Node fast = node.next; // 第2个节点开始
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}

