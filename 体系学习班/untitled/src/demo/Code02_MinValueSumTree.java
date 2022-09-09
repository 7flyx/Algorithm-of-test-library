package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-08
 * Time: 19:37
 * Description: 2022.9.8 腾讯音乐 笔试第2题
 * 给定一棵二叉树,二叉树的每个结点只有0或2个孩子。
 * 你需要对每个结点赋值一个正整数，使得每个结点的左右子树权值和相等。你需要返回所有结点的最小权值和对109＋7取模的结果。
 * 二叉树结点个数不超过10^5.
 */
public class Code02_MinValueSumTree {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(0);
        node.left = new TreeNode(0);
        node.right = new TreeNode(0);
        node.right.left = new TreeNode(0);
        node.right.right = new TreeNode(0);

        System.out.println(getTreeMinValueSum(node));
    }

    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static int getTreeMinValueSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        node.value = 1;
        int leftTree = getTreeMinValueSum(node.left);
        int rightTree = getTreeMinValueSum(node.right);
        if (leftTree == rightTree) {
            return (leftTree + rightTree + 1) % 1000000009;
        } else if (leftTree < rightTree) { // 左边权重小了
            node.left.value += rightTree - leftTree;
            return (2 * rightTree + 1) % 1000000009;
        } else { // 右边权重小了
            node.right.value += leftTree - rightTree;
            return (2 * leftTree + 1) % 1000000009;
        }
    }


}
