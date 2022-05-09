package class12;

import class11.TreeNode;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-08
 * Time: 20:15
 * Description: 给定两个节点，判断这两个节点在二叉树的距离，也就是二者之间的连通，需要走多少步。
 */
public class Code05_GetTwoNodeDistance {
    public static void main(String[] args) {

    }

    public static int getTwoNodeDistance(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || node1 == null || node2 == null) {
            return 0;
        }
        if (root == node1 || root == node2) {
            return 1;
        }

        int leftInfo = getTwoNodeDistance(root.left, node1, node2);
        int rightInfo = getTwoNodeDistance(root.right, node1, node2);
        if (leftInfo != 0 && rightInfo != 0) {
            return 1 + leftInfo + rightInfo;
        }
        return leftInfo != 0? leftInfo : rightInfo;
    }
}
