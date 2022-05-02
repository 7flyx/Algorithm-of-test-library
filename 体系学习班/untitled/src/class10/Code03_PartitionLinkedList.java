package class10;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-02
 * Time: 17:42
 * Description: 在单链表上做荷兰国旗问题：也就是给定一个划分值，将链表划分为 小于区 等于区 大于区
 */
public class Code03_PartitionLinkedList {
    private static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node node = buildList(10);
        int pivot = node.value; // 假设以第1个节点的值作为划分值
        printList(node);
        node = getPartitionList(node, pivot);
        printList(node);
    }

    public static Node getPartitionList(Node node, int pivot) {
        if (node == null || node.next == null) {
            return node;
        }

        Node sH = null; // 小于区的头部
        Node sT = null; // 小于区的尾部
        Node eH = null; // 等于区的头部
        Node eT = null; // 等于区的尾部
        Node bH = null; // 大于区的头部
        Node bT = null; // 大于区的尾部
        Node next = null;

        while (node != null) {
            next = node.next;
            node.next = null; // 断开当前节点的连接
            if (node.value < pivot) {
                if (sH == null) {
                    sH = node;
                } else {
                    sT.next = node;
                }
                sT = node;
            } else if (node.value > pivot) {
                if (bH == null) {
                    bH = node;
                } else {
                    bT.next = node;
                }
                bT = node;
            } else {
                if (eH == null) {
                    eH = node;
                } else {
                    eT.next = node;
                }
                eT = node;
            }
            node = next;
        }

        // 连接三个区域
        if (sH != null) {
            sT.next = eH; // 小于区连接等于区
            eT = eT == null? sT : eT; // 谁不是null，谁就去连接大于区
        }
        if (eT != null) { // eT有可能是null的，切记不是判断eH哦
            eT.next = bH;
        }
        return sH != null? sH : (eH != null? eH : bH);

    }

    public static Node buildList(int length) {
        if (length < 1) {
            return null;
        }

        Node node = new Node(getRandomNumber(20));
        Node tmp = node;
        while (--length != 0) {
            tmp.next = new Node(getRandomNumber(20));
            tmp = tmp.next;
        }

        return node;
    }

    private static int getRandomNumber(int range) {
        return (int)(Math.random() * range) + 1;
    }

    public static void printList(Node node) {
        while(node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
}
