import java.util.*;
import java.io.*;
/**
 * Created by flyx
 * Description:  二叉树的按层打印与ZigZag打印
 * User: 听风
 * Date: 2021-08-31
 * Time: 17:26
 */

public class Code07_LevelOrderAndZigZapOrder {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        TreeNode root = createTree(in);

        StringBuilder sb = new StringBuilder();
        levelOrder(root, sb);
        System.out.println(sb.toString());

        sb = new StringBuilder();
        zigZagOrder(root, sb);
        System.out.println(sb.toString());

        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right;

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
            while(!stack.isEmpty() && stack.peek().val != Integer.parseInt(values[0])) {
                help.push(stack.pop());
            }
            TreeNode cur = stack.pop();
            if (!values[1].equals("0")) {
                cur.left = new TreeNode(Integer.parseInt(values[1]));
                stack.push(cur.left);
            }
            if (!values[2].equals("0")) {
                cur.right = new TreeNode(Integer.parseInt(values[2]));
                stack.push(cur.right);
            }
            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }
        return root;
    }

    public static void levelOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode nextLevelNode = null; //下一层的最后一个节点
        TreeNode curLevelNode = root; //当前层的最后一个节点
        int curLevel = 1;
        sb.append("Level ").append(curLevel).append(" : ");
        while (queue.size() != 0) {
            TreeNode cur = queue.poll();
            sb.append(cur.val).append(" ");
            if (cur.left != null) {
                queue.add(cur.left);
                nextLevelNode = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextLevelNode = cur.right;
            }
            if (cur == curLevelNode ) { //已经是当前这一层最后的一个节点
                curLevelNode = nextLevelNode;
                nextLevelNode = null;
                curLevel++;
                if (queue.size() != 0) {
                    sb.append("\n").append("Level ").append(curLevel).append(" : ");
                }
            }
        }
    }

    public static void zigZagOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return;
        }

        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        queue1.add(root);
        int level = 1;
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            sb.append("Level ").append(level);
            if (level % 2 == 1) {
                sb.append(" left to right: ");
                while (!queue1.isEmpty()) {
                    TreeNode cur = queue1.pop();
                    sb.append(cur.val).append(" ");
                    if (cur.left != null) {
                        queue2.push(cur.left);
                    }
                    if (cur.right != null) {
                        queue2.push(cur.right);
                    }
                }
                sb.append("\n");
            } else {
                sb.append(" right to left: ");
                while (!queue2.isEmpty()) {
                    TreeNode cur = queue2.pop();
                    sb.append(cur.val).append(" ");
                    if (cur.right != null) {
                        queue1.push(cur.right);
                    }
                    if (cur.left != null) {
                        queue1.push(cur.left);
                    }
                }
                sb.append(" ");
            }
            ++level;
        }
    }
}
