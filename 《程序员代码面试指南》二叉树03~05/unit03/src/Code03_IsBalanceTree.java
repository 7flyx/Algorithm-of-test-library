import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description: 判断是不是平衡二叉树
 * User: 听风
 * Date: 2021-08-27
 * Time: 9:45
 */

public class Code03_IsBalanceTree {
    private static int row = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int root = Integer.parseInt(nums[1]);
        String[][] arr = new String[n][3];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readLine().split(" ");
        }
        TreeNode node = createTree(arr);
        display2(node, n);
        System.out.println(isBalanceTree(node).isBalance);

        in.close();
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

    public static TreeNode createTree(String[][] arr) {
        if (row == arr.length) {
            return null;
        }
        int curRow = row;
        row++;
        TreeNode res = new TreeNode(Integer.parseInt(arr[curRow][0]));
        if (!arr[curRow][1].equals("0")) {
            res.left = createTree(arr);
        }
        if (!arr[curRow][2].equals("0")) {
            res.right = createTree(arr);
        }
        return res;
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

    private static void process(TreeNode node, int[][] board, int left, int right, int level) {
        if (node == null || board == null || left > right || level == board.length) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        process(node.left, board, left, mid,level + 1);
        board[level][left + (right - left) / 2] = node.val;
        process(node.right, board, mid + 1,right, level + 1);
    }

}
