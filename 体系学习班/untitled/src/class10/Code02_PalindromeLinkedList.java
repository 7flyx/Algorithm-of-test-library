package class10;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-02
 * Time: 17:16
 * Description: 判断一个单链表是不是回文的
 */
public class Code02_PalindromeLinkedList {

    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(2);
//        node.next.next.next.next = new Node(1);
        System.out.println(isPalindromeList2(node));

    }

    // 用容器解决--笔试用
    public static boolean isPalindromeList1(Node node) {
        if(node == null) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        Node tmp = node;
        while (tmp != null){
            stack.push(tmp.value);
            tmp = tmp.next;
        }
        // 弹出全部结果，判断即可
        while (node != null) {
            if (stack.pop() != node.value) {
                return false;
            }
            node = node.next;
        }
        return true;
    }

    public static boolean isPalindromeList2(Node node) {
        if (node == null) {
            return false;
        }
        Node fast = node;
        Node slow = node;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 此时的slow就是上中节点（中点），反转后续的节点，进行判断
        Node rightNode = reverseList(slow.next); // 右侧链表的头节点
        Node tmp = rightNode;
        slow.next = null; // 暂时断开后续的节点
        boolean res = true; // 结果
        while (tmp != null && node != null) {
            if (tmp.value != node.value) {
                res = false;
                break;
            }
            tmp = tmp.next;
            node = node.next;
        }

        // 将链表反转回来
        slow.next = reverseList(rightNode); // 重新连接上slow的后续节点
        return res;
    }

    // 反转单链表
    private static Node reverseList(Node node) {
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
}
