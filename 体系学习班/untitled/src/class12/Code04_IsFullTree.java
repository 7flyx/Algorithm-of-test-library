package class12;

import class11.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-08
 * Time: 20:14
 * Description: 判断一颗二叉树是不是满二叉树
 */
public class Code04_IsFullTree {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(1);
        node.right = new TreeNode(1);
        node.right.right = new TreeNode(2);
//        node.right.left = new TreeNode(2);
        System.out.println(isFullTree(node));
    }

    public static boolean isFullTree(TreeNode node) {
        if (node == null) {
            return false;
        }

        // 层序遍历，不满足情况的，有两种
        // 1、只有一个孩子节点的情况，直接返回false
        // 2、出现叶子节点的时候，后续全部就是叶子节点，否则返回false
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        boolean leaf = false; // 标志着是否已经遇到过叶子节点了
        TreeNode left = null;
        TreeNode right = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            left = node.left;
            right = node.right;
            // 只有一个孩子节点的情况，或者已经遇到叶子节点了，但后续的却不是叶子节点
            if ((left != null && right == null) || (left == null && right != null) ||
                    (leaf && (left != null || right != null))) { // 这里只判断left！=null也是可以的，因为前面的已经判断过了，多写这个，只是为了好理解
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            // 遇到叶子节点的情况，改动leaf
            if (left == null && right == null) {
                leaf = true;
            }
        }
        return true;
    }
}
