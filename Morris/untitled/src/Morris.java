import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-09-28
 * Time: 17:48
 * Description: 二叉树的神级遍历方式-Morris方法
 */
class Node {
    public int val;
    public Node left, right;

    public Node(int val) {
        this.val = val;
    }
}

public class Morris {
    public static void main(String[] args) {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.left.left = new Node(4);
        node.left.right = new Node(5);
        node.right.left = new Node(6);
        //node.right.right = new Node(7);

        morrisPre(node);
        System.out.println();

        morrisIn(node);
        System.out.println();
        
        morrisPost(node);
        System.out.println();


    }

    /**
     * 整体框架
     * @param head 根结点
     */
    public static void morris(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        Node moreRight = null; //左子树的最右节点
        while (cur != null) {
            moreRight = cur.left;
            if (moreRight != null) { //有左子树的情况
                while (moreRight.right != null && moreRight.right != cur) {
                    moreRight = moreRight.right; //向右子树
                }

                if (moreRight.right == null) { //第一次来到该节点的情况
                    moreRight.right = cur;
                    cur = cur.left; //继续向左子树前进
                    continue; //循环继续，
                } else { //第二次来到该节点的情况
                    moreRight.right = null;
                }

            }
            //如果cur没有左子树，或者是moreRight第二次来到的时候，才能到这里
            cur = cur.right;
        }
    }

    /**
     * 前序遍历---分为两种情况：有左子树和 没有左子树
     * 没有左子树：直接打印该值
     * 有左子树：morris遍历，会来到该节点两次。第一次的时候打印
     * @param head 根
     */
    public static void morrisPre(Node head) {
        if(head == null) {
            return;
        }
        Node cur = head;
        Node moreRight = null;
        while (cur != null) {
            moreRight = cur.left;
            if (moreRight != null) {
                while (moreRight.right != null && moreRight.right != cur) {
                    moreRight = moreRight.right; //转向右子树，查找最右节点
                }
                if (moreRight.right == null) { //第一次来到该节点
                    System.out.print(cur.val + " "); //有左子树，第一次来到该节点，就打印
                    moreRight.right = cur;
                    cur = cur.left; //继续走左子树
                    continue;
                } else { //第二次来到该节点
                    moreRight.right = null;
                }
            } else {
                System.out.print(cur.val + " "); //没有左子树，直接打印该值
            }
            cur = cur.right; //转向右子树
        }
    }

    /**
     * 中序遍历---分为两种情况：有左子树和 没有左子树
     * 没有左子树：直接打印该值
     * 有左子树：morris遍历会来到该节点两次。第2次打印该值
     * @param head 根结点
     */
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }

        Node cur = head;
        Node moreRight = null;
        while (cur != null) {
            moreRight = cur.left;
            if (moreRight != null) {
                while (moreRight.right != null && moreRight.right != cur) {
                    moreRight = moreRight.right;
                }
                if (moreRight.right == null) {
                    moreRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    moreRight.right = null;
                }
            }
            System.out.print(cur.val + " "); //输出
            cur = cur.right;
        }
    }

    /**
     * 后序遍历---在第二次遍历到同一个节点时，逆序打印该节点的左子树的右边界节点
     * @param head 根结点
     *
     */
    public static void morrisPost(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node moreRight = null;
        while (cur != null) {
            moreRight = cur.left;
            if (moreRight != null) {
                while (moreRight.right != null && moreRight.right != cur) {
                    moreRight = moreRight.right;
                }

                if (moreRight.right == null) {
                    moreRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    moreRight.right = null;
                    printNode(cur.left);
                }
            }
            cur = cur.right;
        }
        printNode(head); //最后要逆序打印整棵树的右边界值
    }

    private static void printNode(Node node) {
        //类似于链表反转
        Node tail = reverseNode(node);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverseNode(tail);
    }

    private static Node reverseNode(Node node) {
        Node prev = null;
        Node next = null;
        while (node != null) {
            next = node.right; //右边界节点
            node.right = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

}
