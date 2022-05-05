package class11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-05
 * Time: 14:55
 * Description: LeetCode431题 ，将N叉树转换为二叉树，再将这个二叉树转换为N叉树。
 * 思路：N叉树每个节点的孩子节点，转换成二叉树时，挂在二叉树的左子树中，最靠右的节点，比如：
 *
 *          N叉树                 二叉树
 *           N                      N
 *        /  |  \                /     \
 *       a   b   c              a       null
 *                               \
 *                                b
 *                                 \
 *                                  c
 */
public class Code04_NTreeToBinaryTree {
    // N叉树节点
    private static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static void main(String[] args) {

    }

    // LeetCode提交的代码
    class Codec {
        // 将N叉树转为二叉树
        public TreeNode encode(Node root) {
            if(root == null) {
                return null;
            }
            TreeNode node = new TreeNode(root.val);
            node.left = en(root.children);
            return node;
        }

        // 递归处理孩子节点们
        private TreeNode en(List<Node> children) {
            if (children == null || children.size() == 0) {
                return null;
            }
            TreeNode res = new TreeNode(children.get(0).val); // 第一个孩子节点的值
            TreeNode cur = res;
            int size = children.size();
            for (int i = 1; i < size; i++) {
                TreeNode tmp = new TreeNode(children.get(i).val);
                tmp.left = en(children.get(i).children); // 递归处理当前节点的孩子节点们
                cur.right = tmp; // 挂在cur的右子树上
                cur = cur.right;
            }
            return res;
        }

        // 将二叉树转为N叉树
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            Node node = new Node(root.val, de(root.left));
            return node;
        }

        // 递归函数，构造出孩子节点列表
        private List<Node> de(TreeNode leftTree) {
            List<Node> children = new ArrayList<>();
            // 填写list表
            while (leftTree != null) {
                Node tmp = new Node(leftTree.val, de(leftTree.left)); // 递归左子树，搞出他的孩子节点
                children.add(tmp);
                leftTree = leftTree.right; // 往右子树走，表示当前层的下一个孩子节点
            }
            return children;
        }
    }
}
