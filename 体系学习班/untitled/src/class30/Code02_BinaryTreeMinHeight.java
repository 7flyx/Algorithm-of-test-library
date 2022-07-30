package class30;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-27
 * Time: 21:00
 * Description: 二叉树的最小深度 用Morris改
 */
public class Code02_BinaryTreeMinHeight {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(7);

//        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);

        System.out.println(minHeight(root));
    }

    private static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     *  思路：1、确定cur节点的所在层level。
     *      2、判断 cur.right 节点，是不是真正的cur的右孩子节点。（有可能是因为Morris本身改动过的）
     * @param root 根节点
     * @return 返回整颗树的最小高度
     */
    public static int minHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        int level = 0; // cur节点的所在层数
        int ans = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                int countLevel = 1; // 统计cur左子树，最右一列的高度
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                    countLevel++;
                }
                if (mostRight.right == null) { // 第1次来到cur节点
                    mostRight.right = cur;
                    cur = cur.left; // 还能往左子树走
                    level++;
                    continue;
                } else { // 第2次来到cur节点
                    if (mostRight.left == null) { // mostRight是叶子节点的情况
                        ans = Math.min(ans, level); // 结算mostRight的高度
                    }
                    mostRight.right = null;
                    level -= countLevel;
                }
            } else { // cur.left = null，只能到cur节点一次
                level++;
            }
            cur = cur.right; // 往右子树转
        }

        // 最后判断整棵树最右一列的最后一个节点，看是不是叶子节点，也要判断一下
        level = 1;
        while (root.right != null) {
            root = root.right;
            level++;
        }
        if (root.left == null) { // 左右孩子都是null的情况
            ans = Math.min(level, ans);
        }
        return ans;
    }
}
