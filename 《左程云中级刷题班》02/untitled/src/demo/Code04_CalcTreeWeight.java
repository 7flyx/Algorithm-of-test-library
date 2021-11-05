package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-05
 * Time: 19:34
 * Description:
 * 二叉树每个结点都有一个int型权值， 给定一棵二叉树， 要求计算出从根结点到
 * 叶结点的所有路径中， 权值和最大的值为多少
 */
public class Code04_CalcTreeWeight {
    public static void main(String[] args) {

    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static int calcTreeWeight(TreeNode node) {
        if (node.left == null && node.right == null){
            return node.val;
        }
        int next = 0;
        if (node.left != null){
            next = calcTreeWeight(node.left);
        }
        if (node.right != null) {
            next = Math.max(next, calcTreeWeight(node.right));
        }
        return next;
    }

}
