package class10;

import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-02
 * Time: 20:02
 * Description: 拷贝一个链表，这个链表有随机指针的那种
 * https://leetcode-cn.com/problems/fu-za-lian-biao-de-fu-zhi-lcof/
 */
public class Code04_CopyRandomList {
    private static class Node {
        public int value;
        public Node next;
        public Node rand; // 随机指针

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }

    // 用容器解--笔试用
    public static Node copyRandomList1(Node node) {
        if (node == null) {
            return null;
        }

        HashMap<Node, Node> map = new HashMap<>();
        Node tmp = node;
        while (tmp != null) {
            map.put(tmp, new Node(tmp.value));
            tmp = tmp.next;
        }

        tmp = node;
        while (node != null) {
            map.get(node).next = map.get(node.next); // next指针
            map.get(node).rand = map.get(node.rand); // rand指针
            node = node.next;
        }

        return map.get(tmp); // 返回新的头节点即可
    }

    // 不用容器解--面试用
    // 将新节点挂在老节点的后面，再去连接random指针即可，最后再将新老节点进行分离
    // 例如： 1 -> 1' -> 2 -> 2' -> 3 -> 3'
    public static Node copyRandomList2(Node node) {
        if (node == null) {
            return null;
        }

        Node tmp = node;
        Node next = null;
        while (tmp != null) {
            next = tmp.next;
            tmp.next = new Node(tmp.value);
            tmp.next.next = next; // 新节点的next要连接tmp后的老节点
            tmp = next;
        }

        // 连接新节点的random指针
        tmp = node;
        while (tmp != null) {
            Node newNode = tmp.next;
            next = newNode.next; // 下一个老节点
            newNode.rand = tmp.rand != null? tmp.rand.next : null; // 有可能是null，要判断一下
            tmp = next; // 跳转到下一个节点
        }

        // 分离新老节点
        tmp = node;
        Node res = tmp.next;
        while (tmp != null) {
            Node newNode = tmp.next;
            next = newNode.next; // 下一个老节点
            newNode.next = next != null? next.next : null; // 新节点 连接 新节点，需要判断null的情况
            tmp.next = next; // 老节点 连接 老节点
            tmp = next; // 跳转到下一个节点上去
        }

        return res;
    }
}
