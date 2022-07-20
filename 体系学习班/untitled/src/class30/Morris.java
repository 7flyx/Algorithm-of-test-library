package class30;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-20
 * Time: 19:57
 * Description: Morris遍历二叉树，实现前序、中序、后序遍历版本
 */
public class Morris {
    static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        morrisPreOrder(root);
        morrisInOrder(root);
        morrisPostOrder(root);
        System.out.println("非递归遍历");
        preOrderNoRecursion(root);
        inOrderNoRecursion(root);
        postOrderNoRecursion(root);
        System.out.println("非递归后序遍历，只有一个栈实现");
        postOrderNoRecursion2(root);
    }

    // 前序遍历二叉树
    public static void morrisPreOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        // 找到当前节点的左子树上 最右的节点，并将该节点的右指针指向当前cur节点
        TreeNode mostRight = null;
        TreeNode cur = root;
        while (cur != null) { // 只要cur没有遍历完，循环就继续
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) { // 往最右节点靠拢
                    mostRight = mostRight.right;
                }
                // 停下来时，有两种情况
                // 1是右指针为null，说明是第一次遍历到当前节点
                // 2是右指针指向cur，说明是第二次遍历到当前节点
                if (mostRight.right == null) {
                    mostRight.right = cur; // 指向cur节点
                    System.out.print(cur.val + " ");
                    cur = cur.left;
                    continue; // 继续往左子树走
                } else {
                    mostRight.right = null;
                }
            } else { // 往右子树走之前，先打印当前cur的值
                System.out.print(cur.val + " ");
            }
            cur = cur.right; // 转向右子树
        }
        System.out.println();
    }

    // 中序遍历二叉树
    public static void morrisInOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                // 往最右节点靠拢
                while(mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第1次来到cur节点
                    mostRight.right = cur;
                    cur = cur.left; // 继续往左子树走
                    continue;
                } else { // 第2次来到cur节点
                    mostRight.right = null;
                    System.out.print(cur.val + " ");
                }
            } else {
                System.out.print(cur.val + " ");
            }
            cur = cur.right; // 往右子树转
        }
        System.out.println();
    }

    // 后序遍历二叉树
    public static void morrisPostOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 第2次来到cur节点，此时就打印cur.left节点，最靠右这一列的节点
                    mostRight.right = null;
                    printList(cur.left);
                }
            }
            cur = cur.right; // 往右子树转
        }
        printList(root); // 最后打印根节点最靠右的一列
        System.out.println();
    }

    private static void printList(TreeNode left) {
        // 首先反转最靠右的一列，从下面往上打印
        TreeNode node = reverseList(left);
        TreeNode cur = node;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverseList(node); // 再反转回来
    }

    // 反转TreeNode最右这一列节点
    public static TreeNode reverseList(TreeNode node) {
        TreeNode pre = null;
        TreeNode next = null;
        while (node != null) {
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    // 非递归前序遍历
    public static void preOrderNoRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            System.out.print(root.val + " ");
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                stack.push(root.left);
            }
        }
        System.out.println();
    }

    // 非递归中序遍历
    public static void inOrderNoRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else { // 此时root = null.就打印当前栈顶元素
                root = stack.pop();
                System.out.print(root.val + " ");
                root = root.right; // 转向右子树
            }
        }
        System.out.println();
    }

    // 非递归后序遍历
    public static void postOrderNoRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> helpStack = new Stack<>(); // 将遍历的结果存储在栈中，最后打印
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            helpStack.push(root);
            if (root.left != null) {
                stack.push(root.left);
            }
            if (root.right != null) {
                stack.push(root.right);
            }
        }
        // 打印helpStack中的数据
        while (!helpStack.isEmpty()) {
            root = helpStack.pop();
            System.out.print(root.val + " ");
        }
        System.out.println();
    }

    public static void postOrderNoRecursion2(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode pre = root; // 上一次打印的节点
        while (!stack.isEmpty()) {
            root = stack.peek();
            if (root.left != null && root.left != pre && root.right != pre) {
                stack.push(root.left);
            } else if (root.right != null && root.right != pre) {
                stack.push(root.right);
            } else {
                pre = root;
                stack.pop(); // 弹出栈顶元素
                System.out.print(root.val + " ");
            }
        }
        System.out.println();
    }
}
