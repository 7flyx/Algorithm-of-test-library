package class35;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-15
 * Time: 21:44
 * Description: 平衡二叉树
 */
public class Code01_AVLTree {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(8);
        tree.insert(7);
        tree.insert(15);
        tree.insert(20);
        tree.insert(9);

        System.out.println();
    }

    private static class TreeNode {
        public int val;
        public int height;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val, int height) {
            this.val = val;
            this.height = height;
        }
    }

    private static class AVLTree {
        private TreeNode root;

        // 左旋
        private TreeNode leftRotate(TreeNode node) {
            TreeNode newHead = node.right;
            node.right = newHead.left;
            newHead.left = node;
            node.height = Math.max(getHeight(node.left),
                    getHeight(node.right)) + 1;
            newHead.height = Math.max(node.height, newHead.right.height) + 1;
            return newHead;
        }

        // 右旋
        private TreeNode rightRotate(TreeNode node) {
            TreeNode newHead = node.left;
            node.left = newHead.right;
            newHead.right = node;
            node.height = Math.max(getHeight(node.left),
                    getHeight(node.right)) + 1;
            newHead.height = Math.max(newHead.left.height, node.height) + 1;
            return newHead;
        }

        private int getHeight(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        private int getBalanceFactory(TreeNode node) {
            return getHeight(node.left) - getHeight(node.right);
        }

        public void insert(int val) {
            root = insert(root, val);
        }

        private TreeNode insert(TreeNode node, int val) {
            if (node == null) {
                return new TreeNode(val, 1);
            } else if (val < node.val) { // 往左子树走
                node.left = insert(node.left, val);
            } else { // 往右子树走
                node.right = insert(node.right, val);
            }
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            // 处理平衡
            int balanceFactory = getBalanceFactory(node);
            if (Math.abs(balanceFactory) > 1) {
                if (balanceFactory > 1 && getBalanceFactory(node.left) >= 0) { // 右旋
                    node = rightRotate(node);
                }
                if (balanceFactory > 1 && getBalanceFactory(node.left) < 0) { // 先左旋，再右旋
                    node.left = leftRotate(node.left);
                    node = rightRotate(node);
                }
                if (balanceFactory < -1 && getBalanceFactory(node.right) <= 0) { // 左旋
                    node = leftRotate(node);
                }
                if (balanceFactory < -1 && getBalanceFactory(node.right) > 0) { // 先右旋，再左旋
                    node.right = rightRotate(node.right);
                    node = leftRotate(node);
                }
            }
            return node;
        }

        public void delete(int val) {
            root = delete(root, val);
        }

        private TreeNode delete(TreeNode node, int val) {
            if (node == null) {
                return null;
            } else if (val < node.val) {
                node.left = delete(node.left, val);
            } else if (val > node.val) {
                node.right = delete(node.right, val);
            } else { // = val的时候
                if (node.right == null) { // 只有左子树
                    node = node.left;
                } else if (node.left == null) { // 只有右子树
                    node = node.right;
                } else { // 左右子树都存在的情况
                    TreeNode minNode = node.right; // 右子树上最左的节点
                    TreeNode preNode = null; // minNode的父节点
                    while (minNode.left != null) {
                        preNode = minNode;
                        minNode = minNode.left;
                    }
                    if (preNode != null) {
                        preNode.left = minNode.right;
                        minNode.right = node.right;
                    }
                    minNode.left = node.left;
                    // C++，释放node空间
                    node = minNode;
                }
            }
            if(node == null) { // 有可能变为null的。
                return null;
            }
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            // 处理平衡
            int balanceFactory = getBalanceFactory(node);
            if (Math.abs(balanceFactory) > 1) {
                if (balanceFactory > 1 && getBalanceFactory(node.left) >= 0) { // 右旋
                    node = rightRotate(node);
                }
                if (balanceFactory > 1 && getBalanceFactory(node.left) < 0) { // 先左旋，再右旋
                    node.left = leftRotate(node.left);
                    node = rightRotate(node);
                }
                if (balanceFactory < -1 && getBalanceFactory(node.right) <= 0) { // 左旋
                    node = leftRotate(node);
                }
                if (balanceFactory < -1 && getBalanceFactory(node.right) > 0) { // 先右旋，再左旋
                    node.right = rightRotate(node.right);
                    node = leftRotate(node);
                }
            }
            return node;
        }
    }
}
