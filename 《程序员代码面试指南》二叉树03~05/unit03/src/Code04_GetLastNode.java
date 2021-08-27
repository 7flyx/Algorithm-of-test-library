import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description: 在二叉树中，找到一个节点的后继节点；CD175
 * User: 听风
 * Date: 2021-08-27
 * Time: 18:36
 */

public class Code04_GetLastNode {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        String[][] arr = new String[n][];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readLine().split(" ");
        }

        TreeNode node = createTree(arr, null);
        TreeNode lastNode = getLastNode(node, Integer.parseInt(in.readLine()));
        System.out.println(lastNode == null? 0 : lastNode.val);
        inOrder(node);
        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right, parent;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(String[][] arr, TreeNode parent) {
        if (arr == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        TreeNode root = new TreeNode(Integer.parseInt(arr[0][0]));
        stack.push(root);

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && stack.peek().val != Integer.parseInt(arr[i][0])) {
                help.push(stack.pop());
            }
            if (stack.isEmpty()) {
                throw new RuntimeException("stack is empty.");
            }
            TreeNode cur =  stack.pop();
            if (!arr[i][1].equals("0")) {
                cur.left = new TreeNode(Integer.parseInt(arr[i][1]));
                cur.left.parent = cur;
                stack.push(cur.left);
            }
            if (!arr[i][2].equals("0")) {
                cur.right = new TreeNode(Integer.parseInt(arr[i][2]));
                cur.right.parent = cur;
                stack.push(cur.right);
            }
            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }
        return root;
    }

    public static TreeNode getLastNode(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        //找到被查找的节点
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = node;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                if (cur.val == val) { //找到当前节点，就跳出循环
                    break;
                }
                cur = cur.right;
            }
        }

        if (cur != null && cur.right != null) { //往左子树上寻找
            TreeNode pre = null;
            cur = cur.right;
            while (cur != null) {
                pre = cur;
                cur = cur.left;
            }
            return pre;
        } else { //向上回溯
            TreeNode parent = cur == null? null : cur.parent;
            while (parent != null && parent.right == cur) {
                cur = parent;
                parent = cur.parent;
            }
            return parent;
        }
    }

    public static void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }
}