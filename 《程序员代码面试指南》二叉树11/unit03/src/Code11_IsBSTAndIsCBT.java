import java.io.*;
import java.util.*;
/**
 * Created by flyx
 * Description: 判断是不是搜索二叉树，完全二叉树
 * User: 听风
 * Date: 2021-09-08
 * Time: 21:11
 */

public class Code11_IsBSTAndIsCBT {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        TreeNode root = createTree(in);

        System.out.println(isBST(root));
        System.out.println(isPerfectTree(root));

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

    private static class ReturnBST {
        public int max,min;
        public boolean isBST;

        public ReturnBST(boolean isBST, int max, int min) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    public static boolean isBST(TreeNode node) {
        if (node == null) {
            return false;
        }
        return process1(node).isBST;
    }

    private static ReturnBST process1(TreeNode node) {
        if (node == null) {
            return null;
        }
        ReturnBST leftDate = process1(node.left);
        ReturnBST rightDate = process1(node.right);
        int max = node.val;
        int min = node.val;
        boolean isBST = true;
        if (leftDate != null) {
            max = Math.max(max, leftDate.max);
            min = Math.min(min, leftDate.min);
        }
        if (rightDate != null) {
            max = Math.max(max, rightDate.max);
            min = Math.min(min, rightDate.min);
        }

        if (leftDate != null && leftDate.max > node.val) {
            isBST = false;
        }
        if (rightDate != null && rightDate.min < node.val) {
            isBST = false;
        }

        return new ReturnBST(isBST, max, min);
    }

    private static class ReturnPerfect {
        public int height;
        //-1表示左有右没， 0 表示都没有  1表示左没右有
        public int flag; //-1   0   1 三种状态
        public boolean isPerfect;
        public ReturnPerfect(boolean isPerfect, int flag, int height) {
            this.height = height;
            this.flag = flag;
            this.isPerfect = isPerfect;
        }
    }

    public static boolean isPerfectTree(TreeNode node) {
        if (node == null) {
            return false;
        }
        //层序遍历所有节点，如若遇到没有左孩子，却有右孩子的情况，直接返回false
        //如果遇到第一个不双全节点，队列中其余的节点必须是叶节点才行

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        boolean laft = false; //开关，表示当前是否遇到过不双全节点
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }

            if((cur.left == null && cur.right != null) ||
                    (laft && (cur.left != null || cur.right != null))) {
                return false;
            }
            if (cur.left == null || cur.right == null) {
                laft = true;
            }
        }
        return true;
    }

}

