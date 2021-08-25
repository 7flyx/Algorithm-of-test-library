import java.util.Stack;

/**
 * Created by flyx
 * Description: 2-3树 -》 红黑树
 *          时间复杂度为O(2logN)，更适合于添加删除操作。综合性能比AVL树好
 * User: 听风
 * Date: 2021-08-25
 * Time: 10:30
 */
public class RBTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private static class TreeNode {
        public int val;
        public TreeNode left, right;
        public boolean color;

        public TreeNode(int val) {
            this.val = val;
            color = RED; //初始值为red
        }
    }

    private TreeNode root;

    /**
     *               新插入的节点，在node的右边，需要左旋转
     *          node                                x
     *          /  \                              /  \
     *         T1   x           左旋转          node  T3
     *             / \        --------->      /  \
     *           T2  T3                      T1  T2
     *
     * @param node 需要旋转的节点
     * @return 新的根结点
     */
    private TreeNode leftRotate(TreeNode node) {
        TreeNode x = node.right;
        //左旋转
        node.right = x.left;
        x.left = node;
        //改变相应的颜色
        x.color = node.color;//新根结点的颜色，来自于原来根结点的颜色
        node.color = RED;//原来的根结点，该为红色
        return x;
    }

    /**
     *        新插入的节点，在node的左边的左边，需要右旋转---就是AVL中的LL型
     *          node                                x
     *          /  \                              /  \
     *         x   T1           右旋转       newNode  node
     *        /  \            --------->             /  \
     *  newNode  T2                                 T1  T2
     *
     * @param node 需要旋转的节点
     * @return 新的根结点
     */
    private TreeNode rightRotate(TreeNode node) {
        TreeNode x = node.left;
        //右旋转
        node.left = x.right;
        x.right = node;

        x.color = node.color; //新根结点的颜色，来自于原来根结点的颜色
        node.color = RED;//原来的根结点，改为红色
        return x;
    }

    /**
     *     颜色反转---传入进来的情况
     *         黑                         红
     *        /  \         反转后        /   \
     *       红  红      -------->      黑   黑
     * @param node 颜色反转
     */
    private void flipColors(TreeNode node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public void add(int val) {
        root = add(root, val); //方法重载
        root.color = BLACK; //保持根结点是黑色
    }

    private TreeNode add(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val); //默认是红色节点
        }

        if (val < node.val) {
            node.left = add(node.left, val);
        } else {
            node.right = add(node.right, val);
        }

        //维护机制
        if (!isRed(node.left) && isRed(node.right)) { //左孩子黑色，右孩子红色
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) { //左孩子红色，左孩子的左孩子也是红色--LL型
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) { //左右孩子都是红色
            flipColors(node);
        }
        return node;
    }

    public void remove(int val) {
        root = remove(root, val);
    }

    private TreeNode remove(TreeNode node, int val) {
        if (node == null) {
            return null;
        }

        if (val < node.val) {
            node.left = remove(node.left, val);
        } else if (val > node.val){
            node.right = remove(node.right, val);
        } else if (node.left != null && node.right != null) { //有两个孩子节点
            TreeNode minNode = getMinNode(node.right); //向右子树索要最小节点
            minNode.left = node.left;
            minNode.right = remove(node.right, minNode.val); //向右子树删除minNode节点
            return minNode;
        } else { //有一个孩子节点，或者是没有孩子节点
            node = node.left != null? node.left : node.right; //直接覆盖在node上，直接返回
        }
        return node;
    }

    private TreeNode getMinNode(TreeNode node) {
        TreeNode pre = node;
        while (node != null) {
            pre = node;
            node = node.left; //向左子树寻找
        }
        return pre;
    }

    public void displayOfInOrder() {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.val + " ");
                cur = cur.right;
            }
        }
        System.out.println();
    }

    public boolean isRed(TreeNode node) {
        return node == null? BLACK : (node.color == RED); //如果是null，说明是叶节点的下面一个
    }

}

