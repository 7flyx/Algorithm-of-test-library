package class30;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-27
 * Time: 20:00
 * Description: 用Morris遍历判断是不是搜索二叉树
 */
public class Code01_IsBST {
    private static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isBST(TreeNode root) {
        if (root == null) {
            return false;
        }
        TreeNode pre = null;
        TreeNode cur = root;
        TreeNode mostRight = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第1次来到该节点
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 第2次来到该节点
                    mostRight.right = null;
                }
            }
            // 中序遍历的情况，该在这里判断
            if (pre != null && pre.val >= cur.val) {
                ans = false;
            }
            pre = cur; // 更新pre节点
            cur = cur.right;
        }
        return ans;
    }

}
