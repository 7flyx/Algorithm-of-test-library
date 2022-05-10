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

    // 层序遍历的方式进行解
    public static boolean isCompletelyTree1(TreeNode node) {
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

    // 递归套路解-向左右子树进行要信息
    // 整个可能性有四种
    public static boolean isCompletelyTree2(TreeNode node) {
        if (node == null) {
            return false;
        }
        return process(node).isCBT;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull &&
                leftInfo.height == rightInfo.height;
        boolean isCBT = isFull; // 是满二叉树，也就是完全二叉树
        // 整合以下4种可能性
        // 可能性一: 判断两颗子树是不是满二叉树且高度还一样，那就是完全二叉树
        // 可能性二：左子树是满二叉树，右子树是完全二叉树，左边height = 右边height，就是完全二叉树
        if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        // 可能性三：左子树是完全二叉树，右子树是满二叉树，左边height = 右边height，就是完全二叉树
        if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        // 可能性四：左边是满二叉树，右边是满二叉树，且左边height = 右边height+ 1，就是完全二叉树
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }

    private static class Info {
        public boolean isFull; //是不是满二叉树
        public boolean isCBT; // 是不是完全二叉树
        public int height; // 高度

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

}
