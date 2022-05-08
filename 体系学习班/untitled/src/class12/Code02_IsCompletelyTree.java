package class12;

import class11.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-08
 * Time: 20:13
 * Description: 判断一颗二叉树是不是完全二叉树
 * https://leetcode-cn.com/problems/check-completeness-of-a-binary-tree/submissions/
 */
public class Code02_IsCompletelyTree {
    public static void main(String[] args) {

    }

    public static boolean isCompletelyTree(TreeNode node) {
        if (node == null) {
            return false;
        }

        // 层序遍历，分为两种情况
        // 1、一个节点没有左孩子，但是有右孩子，那么就不是完全二叉树
        // 2、当遇到第一个没有两个孩子节点的时候，此时后续的层序遍历的结果，必须全部都是叶节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        TreeNode left = null;
        TreeNode right = null;
        boolean leaf = false; // 标志着是否已经遇到过 孩子节点缺失的情况
        while (!queue.isEmpty()) {
            node = queue.poll();
            left = node.left;
            right = node.right;
            if ((leaf && (left != null || right != null)) || (left == null && right != null)) {
                return false;
            }
            // 遇到不双全的节点
            if (left == null  || right == null) {
                leaf = true;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
        }
        return true;
    }
}
