package class12;

import class11.TreeNode;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-08
 * Time: 20:13
 * Description: 判断一个二叉树是不是搜索二叉树。
 * https://leetcode-cn.com/problems/validate-binary-search-tree/submissions/
 */
public class Code03_IsBST {
    public static void main(String[] args) {

    }

    private static class Info {
        public boolean isBST;
        public int max;
        public int min;
        public boolean isNullNode; // 表示当前节点是不是空节点

        public Info(boolean isBST, int max, int min, boolean isNullNode) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.isNullNode = isNullNode;
        }
    }

    public static boolean isValidBST(TreeNode node) {
        if (node == null) {
            return false;
        }
        return process(node).isBST;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        if (!leftInfo.isBST || !rightInfo.isBST ||
                (!leftInfo.isNullNode && leftInfo.max >= node.val) || // 左子树不是null的情况
                (!rightInfo.isNullNode && rightInfo.min <= node.val)) { // 右子树不是null的情况
            return new Info(false, 0, 0, false);
        }
        int max = Math.max(node.val, rightInfo.max);
        int min = Math.min(node.val, leftInfo.min);
        return new Info(true, max, min, false);
    }
}
