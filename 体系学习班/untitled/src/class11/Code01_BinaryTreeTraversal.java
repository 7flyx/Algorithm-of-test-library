package class11;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-03
 * Time: 18:24
 * Description: 二叉树的前序、中序 、后续遍历
 */
public class Code01_BinaryTreeTraversal {

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        NonRecursion nonRecursion = new NonRecursion();
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);

        System.out.println("递归版本遍历：");
        recursion.preorder(node);
        System.out.println();
        recursion.inorder(node);
        System.out.println();
        recursion.postorder(node);
        System.out.println();
        System.out.println("非递归版本遍历：");
        nonRecursion.preorder(node);
        nonRecursion.inorder(node);
        nonRecursion.postorder(node);
        nonRecursion.postorder2(node);

    }

    // 递归版本的一系列操作
    private static class Recursion {
        // 前序遍历
        public void preorder(TreeNode root) {
            if (root == null) {
                return;
            }
            System.out.print(root.val + " ");
            preorder(root.left);
            preorder(root.right);
        }

        // 中序遍历
        public void inorder(TreeNode root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }

        // 后序遍历
        public void postorder(TreeNode root) {
            if (root == null) {
                return;
            }
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.val + " ");
        }
    }

    // 非递归版本的一系列操作
    public static class NonRecursion {
        // 前序遍历
        public void preorder(TreeNode root) {
            if (root == null) {
                return;
            }

            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                System.out.print(root.val + " "); // 首先打印当前节点的值
                if (root.right != null) { // 先判断右边
                    stack.push(root.right);
                }
                if (root.left != null) { // 再判断左边
                    stack.push(root.left);
                }
            }
            System.out.println();
        }

        // 中序遍历
        public void inorder(TreeNode root) {
            if (root == null) {
                return;
            }

            Stack<TreeNode> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                if (root != null) {
                    stack.push(root);
                    root = root.left; // 先往左边走
                } else { // 当前root=null，说明该弹出栈顶元素并打印了
                    root = stack.pop();
                    System.out.print(root.val + " ");
                    root = root.right; // 转头往右边走
                }
            }
            System.out.println();
        }

        // 后序遍历-- 辅助栈实现，空间复杂度O(N)
        public void postorder(TreeNode root) {
            if (root == null) {
                return;
            }

            Stack<TreeNode> stack = new Stack<>();
            Stack<TreeNode> help = new Stack<>(); // 用于存储逆序的结果
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                help.push(root); // 将节点压入辅助栈中
                if (root.left != null) { // 先压左边
                    stack.push(root.left);
                }
                if (root.right != null) { // 再压右边
                    stack.push(root.right);
                }
            }

            // 打印help栈中的数据
            while (!help.isEmpty()) {
                System.out.print(help.pop().val + " ");
            }
            System.out.println();
        }

        // 后序遍历--省一个辅助栈的空间
        public void postorder2(TreeNode root) {
            if (root == null) {
                return;
            }

            TreeNode pre = root; // 上次遍历打印过的节点
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.peek();
                if (root.left != null && root.left != pre && root.right != pre) { // 左边不为空，并且当前节点的左右孩子都没被打印过
                    stack.push(root.left);
                } else if (root.right != null && root.right != pre) { // 右边不为空，且右孩子没有被打印过
                    stack.push(root.right);
                } else { // 左右孩子都是空，或者都已经被打印过了，该打印当前节点了
                    System.out.print(stack.pop().val + " "); // 弹出栈顶并打印
                    pre = root; // 记录最近一次打印的节点
                }
            }
            System.out.println();
        }
    }
}
