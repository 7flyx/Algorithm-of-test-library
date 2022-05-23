package class03;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 16:21
 * Description: 反转 单链表 和 双链表
 */



public class Code01_ReverseLinkedList {
    static class Solution {
        public DoubleNode reverseDoubleNode(DoubleNode node) {
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

        public Node reverseList(Node node) {
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
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int testTime = 10000;
        int length = 50;
        int range = 1000;
        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int testLen = (int)(Math.random() * length) + 2; // [2,52)
            DoubleNode list = generateDoubleList(testLen, range);
            DoubleNode copy = copyList(list);
            DoubleNode showList = copyList(list);
            DoubleNode testAns = reverseDoubleNode(list);
            DoubleNode userAns = solution.reverseDoubleNode(copy);
            if (!judgeList(testAns, userAns)) {
                System.out.print("测试数据：");
                printList(showList);
                System.out.print("预期输出：");
                printList(testAns);
                System.out.print("实际输出：");
                printList(userAns);
                success = false;
            }
        }
        if (success) {
            System.out.println("测试通过");
        }
    }

    private static void printList(DoubleNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main1(String[] args) {
        Solution solution = new Solution();
        int testTime = 10000;
        int length = 50;
        int range = 1000;
        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int testLen = (int)(Math.random() * length) + 2; // [2,52)
            Node list = generate(testLen, range);
            Node copy = copyList(list);
            Node showList = copyList(copy);
            Node testAns = reverseNode(list);
            Node userAns = solution.reverseList(copy);
            if (!judgeList(testAns, userAns)) {
                System.out.print("测试数据：");
                printList(showList);
                System.out.print("预期输出：");
                printList(testAns);
                System.out.print("实际输出：");
                printList(userAns);
                success = false;
            }
        }
        if (success) {
            System.out.println("测试通过");
        }
    }

    private static void printList(Node node) {
        while (node != null) {
            System.out.print(node.value + " ");
        }
        node = node.next;
        System.out.println(" ");
    }

    private static boolean judgeList(Node testAns, Node userAns) {
        if (testAns == null || userAns == null) {
            return false;
        }
        while (testAns != null && userAns != null) {
            if(testAns.value != userAns.value) {
                return false;
            }
            testAns = testAns.next;
            userAns = userAns.next;
        }
        return testAns == null && userAns == null;
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

    public static Node generate(int length, int range) {
        Node head = new Node(1);
        Node cur = head;
        while (length-- > 0) {
            cur.next = new Node((int)(Math.random() * range));
            cur = cur.next;
        }
        return head.next;
    }

    public static Node copyList(Node node) {
        if(node == null) {
            return null;
        }
        Node head = new Node(1);
        Node cur = head;
        while (node != null) {
            cur.next = new Node(node.value);
            node = node.next;
            cur = cur.next;
        }
        return head.next;
    }

    public static DoubleNode generateDoubleList(int length, int range) {
        DoubleNode head = new DoubleNode(1);
        DoubleNode cur = head;
        DoubleNode pre = null;
        while (length-- > 0) {
            cur.next = new DoubleNode((int)(Math.random() * range));
            cur = cur.next;
            cur.last = pre;
            pre = cur;
        }
        return head.next;
    }

    public static DoubleNode copyList(DoubleNode node) {
        DoubleNode head = new DoubleNode(1);
        DoubleNode cur = head;
        DoubleNode pre = null;
        while (node != null) {
            cur.next = new DoubleNode(node.value);
            cur.next.last = pre;
            cur = cur.next;
            pre = cur;
            node = node.next;
        }
        return head.next;
    }

    private static boolean judgeList(DoubleNode testAns, DoubleNode userAns) {
        while(testAns != null && userAns != null) {
            if (testAns.value != userAns.value) {
                return false;
            }
            testAns = testAns.next;
            userAns = userAns.next;
        }
        return testAns == null && userAns == null;
    }
}