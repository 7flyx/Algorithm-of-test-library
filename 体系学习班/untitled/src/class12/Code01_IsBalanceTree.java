package class12;

import class11.TreeNode;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-08
 * Time: 20:09
 * Description: 树形dp练习
 *              判断一个二叉树是不是平衡树。
 *              https://leetcode-cn.com/problems/check-balance-lcci/submissions/
 */
public class Code01_IsBalanceTree {
    public static void main(String[] args) {

    }

    public static boolean isBalanceTree(TreeNode node) {
        if (node == null) {
            return true;
        }
        return process(node).isBalance;
    }

    private static class Info {
        public boolean isBalance;
        public int height;

        public Info(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        // 左右两个，有一个不是平衡树，或者两边的高度相差超过1了，就直接返回了
        if (!leftInfo.isBalance || !rightInfo.isBalance ||
                Math.abs(rightInfo.height - leftInfo.height) > 1) {
            return new Info(false, 0);
        }
        return new Info(true, Math.max(leftInfo.height, rightInfo.height) + 1);
    }
}
