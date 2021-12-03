/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-03
 * Time: 14:48
 * Description: 求完全二叉树的节点数
 */
public class Code02_CalcTreeNodeNum {

    private static class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        System.out.println(calcTreeNodeSum1(node));
        System.out.println(calcTreeNodeSum2(node));





    }

    /**
     * 求完全二叉树的节点数，可以使用套路来解，向左子树要信息，向右子树要信息
     *O(N)的解法
     * @param node 根节点
     * @return 总节点数
     */
    public static int calcTreeNodeSum1(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = calcTreeNodeSum1(node.left);
        int right = calcTreeNodeSum1(node.right);
        return left + right + 1;
    }

    public static int calcTreeNodeSum2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftLevel = getLevel(node.left);
        return process(node, leftLevel);
    }

    public static int process(TreeNode node,  int leftLevel) {
        if (node == null) return 0;
        int res = 0;
        int rightLevel = getLevel(node.right);
        if (rightLevel == leftLevel) {
            res = (int)Math.pow(2,leftLevel);
            res += process(node.right,  rightLevel - 1);
        } else {
            res = (int)Math.pow(2, rightLevel);
            res += process(node.left,  leftLevel - 1);
        }
        return res;
    }

    public static int getLevel(TreeNode node) {
        int level = 0;
        while(node != null) {
            node = node.left;
            level++;
        }
        return level;
    }
}