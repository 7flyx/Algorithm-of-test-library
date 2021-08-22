import java.util.ArrayList;

/**
 * Created by flyx
 * Description: 平衡二叉树
 * User: 听风
 * Date: 2021-08-21
 * Time: 18:16
 */
public class AVLTree {
    private TreeNode root; //根结点

    private static class TreeNode {
        public int val;
        public int height;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            this.height = 1; //初始化高度为1
        }
    }

    public void add(int val) {
        root = add(root, val);
    }

    private TreeNode add(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }

        if (val < node.val) {
            node.left = add(node.left, val);
        } else { //大于等于的情况，还是需要新建节点
            node.right = add(node.right, val);
        }

        //计算当前节点的高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1; //取左右两边的最大值，再加1

        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            //LL型，做右旋转处理
            if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
                return R_Rotate(node); //直接将新的根结点返回即可
            }

            //RR型，做左旋转处理
            if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
                 return L_Rotate(node); //新的根节点，直接返回
            }

            //LR型，先对左子树进行左旋转，然后再对根节点进行右旋转
            if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
                node.left = L_Rotate(node.left); //先进行左旋转，变成LL型
                return R_Rotate(node); //再进行右旋转
            }

            //RL型，先对右子树进行右旋转，然后再对根节点进行左旋转
            node.right = R_Rotate(node.right);
            return L_Rotate(node);
        }
        return node;
    }

    /*
                视图
                            x                                       y
                          /   \                                   /   \
                         y    T4                                 z    x
                        / \               右旋转处理（x）》       / \   / \
                       z   T3                                 T1  T2 T3 T4
                      / \
                     T1  T2
         */
    private TreeNode R_Rotate(TreeNode x) {
        //首先保存x的左子节点
        TreeNode y = x.left;
        //右旋转过程
        x.left = y.right;
        y.right = x;
        //重新计算高度 ----切记：先计算下面的x的高度
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y; //以y作为新的根节点
    }

    /*
          视图
                      x                                       y
                    /   \                                   /   \
                   T1    y                                 x     z
                        / \         左旋转处理（x）》       / \   / \
                       T2  z                            T1  T2 T3 T4
                          / \
                         T3 T4
   */
    private TreeNode L_Rotate(TreeNode x) {
        //首先保存x的右子节点
        TreeNode y = x.right;
        //左旋转过程
        x.right = y.left;
        y.left = x;
        //重新计算height---切记，先计算下面的x的高度
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y; //返回y，作为新的根节点
    }

    private int getBalanceFactor(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public void remove(int val) {
        root = remove(root, val);
    }

    private TreeNode remove(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        TreeNode resultNode = null;
        if (val < node.val) { //小于
            node.left = remove(node.left, val);
            resultNode = node;
        } else if (val > node.val){ //大于
            node.right = remove(node.right, val);
            resultNode = node;
        } else if (node.left != null && node.right != null) { //相等，有两个孩子节点的情况
            TreeNode minNode = getMinNode(node.right);

            minNode.right = remove(node.right, minNode.val); //连接右子树，需要删除minNode在右子树上的存在
            minNode.left = node.left; //连接左子树

            node.left = node.right = null;
            resultNode = minNode;
        } else { //相等，只有一个孩子节点，或者没有孩子节点的情况
            resultNode = node.left != null? node.left : node.right;
        }

        //上面选择语句结束后，肯定是已经删除完成了，现在要做的是判断当前节点是否平衡
        //如果不平衡，还是跟add方法一样，分为那四种情况来调整即可
        if (resultNode == null) {
            return null;
        }

        resultNode.height = Math.max(getHeight(resultNode.left), getHeight(resultNode.right)) + 1;
        int balanceFactor = getBalanceFactor(resultNode);
        if (Math.abs(balanceFactor) > 1) {
            //LL型
            if (balanceFactor > 1 && getBalanceFactor(resultNode.left) >= 0) {
                return R_Rotate(resultNode);
            }

            //RR型
            if (balanceFactor < -1 && getBalanceFactor(resultNode.right) <= 0) {
                return L_Rotate(resultNode);
            }

            //LR型
            if (balanceFactor> 1 && getBalanceFactor(resultNode.left) < 0) {
                resultNode.left = L_Rotate(resultNode.left);
                return R_Rotate(resultNode);
            }

            //RL型
            resultNode.right = R_Rotate(resultNode.right);
            return L_Rotate(resultNode);
        }
        return resultNode;
    }

    private TreeNode getMinNode(TreeNode node) {
        TreeNode pre = null;
        TreeNode cur = node;
        while (cur != null) {
            pre = cur;
            cur = cur.left;
        }
        return pre;
    }

    //判断是否是平衡二叉树
    public boolean isBalanceTree() {
        return isBalanceTree(root).isBalanced;
    }
    private static class ReturnInfoBalance {
        public boolean isBalanced;

        public int height;
        public ReturnInfoBalance(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
    private ReturnInfoBalance isBalanceTree(TreeNode node) {
        if (node == null) {
            return new ReturnInfoBalance(true, 0);
        }

        ReturnInfoBalance left = isBalanceTree(node.left); //向左子树要信息
        ReturnInfoBalance right = isBalanceTree(node.right); //向右子树要信息

        int height = Math.max(left.height, right.height) + 1; //计算的当前节点的高度
        int balanceFactor = left.height - right.height;
        boolean isBalanced = left.isBalanced && right.isBalanced; //判断左右子树是否是平衡树

        ReturnInfoBalance res = new ReturnInfoBalance(isBalanced, height);
        if (Math.abs(balanceFactor) > 1) {
            res.isBalanced = false;
        }
        return res;
    }

    //判断是否是搜索二叉树
    public boolean isBST() {
        if (root == null) {
            return true;
        }
        return isBST(root).isBST;
    }
    private static class ReturnInfoBST {
        public boolean isBST;
        public int max; //最大值
        public int min; //最小值

        public ReturnInfoBST(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }
    private ReturnInfoBST isBST(TreeNode node) {
        if (node == null) {
            return null;
        }

        ReturnInfoBST left = isBST(node.left);
        ReturnInfoBST right = isBST(node.right);

        int min = node.val;
        int max = node.val;
        if (left != null) {
            min = Math.min(min, left.min);
            max = Math.max(max, left.max);
        }
        if (right != null) {
            min = Math.min(min, right.min);
            max = Math.max(max, right.max);
        }

        boolean isBST = true;
        if ((left != null && !left.isBST && left.max >= node.val) ||
                ((right != null && !right.isBST && right.min < node.val))) {
            isBST = false;
        }

        return new ReturnInfoBST(isBST, max, min);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(TreeNode node, ArrayList<Integer> list) {
        if (node == null) {
            return;
        }
        inOrder(node.left, list);
        //System.out.print(node.val + " ");
        list.add(node.val);
        inOrder(node.right, list);
    }

    public boolean isBST2() {
        if (root == null) {
            return true;
        }
        ArrayList<Integer> list = new ArrayList<>();
        inOrder(root, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    public boolean isEmpty() {
        return root == null;
    }

}
