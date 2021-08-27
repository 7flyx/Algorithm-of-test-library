import java.util.*;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-27
 * Time: 9:52
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("-----");
        TreeNode head = new TreeNode(4);
        head.left = new TreeNode(3);
        head.right = new TreeNode(5);
        head.left.left = new TreeNode(1);
        head.left.right = new TreeNode(2);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);
        display2(head, 7);
    }
    private static class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private static class ReturnInfo {
        public int height;
        public boolean isBalance;

        public ReturnInfo(int height, boolean isBalance) {
            this.height = height;
            this.isBalance = isBalance;
        }
    }

    public static void display(TreeNode node) {
        if (node == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<TreeNode> list = new ArrayList<>();
        TreeNode cur = node;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                list.add(cur);
                cur = cur.right;
            }
        }

        int length = list.size(); //框框总长度
        cur = node;
        Queue<TreeNode> queue = new LinkedList<>();
        int level = 1;
        int curNodes = 0;

        queue.add(cur);
        while (!queue.isEmpty()) {
            for (int i = 0; i < length / (level << 1); i++) {
                System.out.print("    "); //四个空格
            }
            cur = queue.poll();
            if (cur != null) {
                System.out.printf("%4d", cur.val);
                queue.add(cur.left);
                queue.add(cur.right);
            } else {
                System.out.print("    ");
            }
            curNodes++;
            if (curNodes == Math.pow(2, level) - 1) {
                curNodes = 0;
                level++;
                System.out.println();
            }
            if (curNodes != 0)
                for (int i = 0; i < length / (level << 1); i++) {
                    System.out.print("    "); //四个空格
                }
        }
    }

    public static void display2(TreeNode node, int n) {
        if (node == null) {
            return;
        }

        int height = isBalanceTree(node).height;
        int[][] board = new int[height][n];
        process(node, board, 0, n - 1, 0);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    System.out.print("    ");
                } else {
                    System.out.printf("%4d", board[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void process(TreeNode node,int[][] board, int left, int right, int level) {
        if (node == null || board == null || left > right || level == board.length) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        process(node.left, board, left, mid,level + 1);
        board[level][left + (right - left) / 2] = node.val;
        process(node.right, board, mid + 1,right, level + 1);
    }

    public static ReturnInfo isBalanceTree(TreeNode node) {
        if (node == null) {
            return new ReturnInfo(0, true);
        }

        ReturnInfo leftData = isBalanceTree(node.left);
        ReturnInfo rightData = isBalanceTree(node.right);

        int height = Math.max(leftData.height, rightData.height) + 1;
        boolean isBalance = true;
        if (!leftData.isBalance || !rightData.isBalance) {
            isBalance = false;
        }
        if (Math.abs(leftData.height - rightData.height) > 1) {
            isBalance = false;
        }
        return new ReturnInfo(height, isBalance);
    }
}

