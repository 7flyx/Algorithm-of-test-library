package class11;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-05
 * Time: 15:41
 * Description: 给定一个二叉树，再给你一个其中的节点，返回这个节点的后继节点。
 * 后继节点：中序遍历的下一个节点
 */
public class Code06_SuccessorNode {
    public static void main(String[] args) {

    }

    private static class TreeNode2 {
        public int val;
        public TreeNode2 left;
        public TreeNode2 right;
        public TreeNode2 parent;

        public TreeNode2(int val) {
            this.val = val;
        }
    }

    public static TreeNode2 getLastNode(TreeNode2 root, TreeNode2 node) {
        if (root == null || node == null) {
            return null;
        }

        if (node.right != null) { // 有右孩子的时候
            return getMostLeftNode(node.right); // 返回右子树最左的节点
        }
        // 没有右孩子的时候
        // 需要往上走，找父节点
        TreeNode2 parent = node.parent;
        while (parent != null && parent.right == node) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    private static TreeNode2 getMostLeftNode(TreeNode2 node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
