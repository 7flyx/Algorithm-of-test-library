package class11;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-03
 * Time: 19:12
 * Description: 二叉树的层序遍历
 */
public class Code02_BinaryTreeLevelTraversal {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);
        levelTraversal(node);
    }

    public static void levelTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            System.out.print(root.val + " ");
            if (root.left != null) {
                queue.add(root.left);
            }
            if (root.right != null) {
                queue.add(root.right);
            }
        }
        System.out.println();
    }

}
