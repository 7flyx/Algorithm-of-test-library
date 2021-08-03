package unit02;

import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.util.Stack;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-03
 * Time: 20:00
 */

public class Code15_SearchTreeToList {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        int index = 0;
        int[] arr = new int[n * 3];

        for (int i = 0; i < n; i++) {
            String[] value = sc.readLine().split(" ");
            for (int j = 0; j < 3; j++) {
                arr[index++] = Integer.parseInt(value[j]);
            }
        }

        //建立搜索二叉树
        TreeNode head = createTree(arr);
        //printTree(head);
        Node node = treeToNodeList(head);
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
    }

    public static class TreeNode {
        public int fa;
        public TreeNode lchild;
        public TreeNode rchild;

        public TreeNode(int fa) {
            this.fa = fa;
        }
    }

    public static class Node {
        public int val;
        public Node last;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(int[] arr) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        TreeNode head = new TreeNode(arr[0]);
        head.lchild = arr[1] == 0 ? null : new TreeNode(arr[1]);
        head.rchild = arr[2] == 0 ? null : new TreeNode(arr[2]);
        if (head.lchild != null) {
            stack.push(head.lchild);
        }
        if (head.rchild != null) {
            stack.push(head.rchild);
        }

        int index = 3;
        while (index < arr.length) {
            while (!stack.isEmpty() && stack.peek().fa != arr[index] && arr[index] != 0) {
                help.push(stack.pop());
            }
            index++;
            TreeNode tmp = stack.pop();
            tmp.lchild = arr[index++] == 0 ? null : new TreeNode(arr[index - 1]);
            tmp.rchild = arr[index++] == 0 ? null : new TreeNode(arr[index - 1]);
            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
            if (tmp.lchild != null) {
                stack.push(tmp.lchild);
            }
            if (tmp.rchild != null) {
                stack.push(tmp.rchild);
            }
        }
        return head;
    }

    public static Node treeToNodeList(TreeNode head) {
        if (head == null) {
            return null;
        }

        Node pre = null;
        Node newHead = null;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) { //非递归，中序遍历二叉树
            if (head != null) {
                stack.push(head);
                head = head.lchild;
            } else {
                TreeNode tmp = stack.pop();
                Node node = new Node(tmp.fa);
                node.last = pre;
                if (pre != null) {
                    pre.next = node;
                } else {
                    newHead = node;
                }
                pre = node;
                head = tmp.rchild;
            }
        }
        return newHead;
    }

    public static void printTree(TreeNode head) {
        if (head == null) {
            return;
        }
        printTree(head.lchild);
        System.out.println(head.fa);
        printTree(head.rchild);
    }
}