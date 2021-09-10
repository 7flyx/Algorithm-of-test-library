import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-09-10
 * Time: 22:13
 */

public class Code13_IsTopologyTree {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        TreeNode root1 = createTree(in);
        nums = in.readLine().split(" ");
        TreeNode root2 = createTree(in);

        TreeNode commonNode = findNode(root1, root2);
        System.out.println(isTopologyTree(commonNode, root2));
        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left,right;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(BufferedReader in) throws IOException {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        String[] values = in.readLine().split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        if (!values[1].equals("0")) {
            root.left = new TreeNode(Integer.parseInt(values[1]));
            stack.push(root.left);
        }
        if (!values[2].equals("0")) {
            root.right = new TreeNode(Integer.parseInt(values[2]));
            stack.push(root.right);
        }

        while (!stack.isEmpty()) {
            values = in.readLine().split(" ");
            while (!stack.isEmpty() && stack.peek().val != Integer.parseInt(values[0])) {
                help.push(stack.pop());
            }
            TreeNode node = stack.pop();
            if (!values[1].equals("0")) {
                node.left = new TreeNode(Integer.parseInt(values[1]));
                stack.push(node.left);
            }
            if (!values[2].equals("0")) {
                node.right = new TreeNode(Integer.parseInt(values[2]));
                stack.push(node.right);
            }

            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }
        return root;
    }

    //在t1树，查找t2的根节点
    public static TreeNode findNode(TreeNode root, TreeNode node) {
        if (root == null || root.val == node.val) {
            return root;
        }

        TreeNode leftDate = findNode(root.left, node);
        TreeNode rightDate = findNode(root.right, node);
        return leftDate != null? leftDate : rightDate;
    }

    //node1 为主树，node2为子树
    public static boolean isTopologyTree(TreeNode node1, TreeNode node2) {
        if (node2 == null) {
            return true;
        }
        if (node1 == null || node1.val != node2.val) {
            return false;
        }

        boolean leftDate = isTopologyTree(node1.left, node2.left);
        boolean rightDate = isTopologyTree(node1.right, node2.right);
        return leftDate && rightDate? true : false;
    }
}