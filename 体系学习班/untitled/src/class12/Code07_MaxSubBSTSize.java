package class12;

import class11.TreeNode;

import java.util.ArrayList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-09
 * Time: 16:08
 * Description: 给定一颗二叉树，树上存在搜索二叉树，返回最大的搜索二叉树的节点数。
 */
public class Code07_MaxSubBSTSize {
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != getMaxSubBSTSize(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    // for test 生成可能含有搜索二叉子树的树
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    private static class Info {
        public boolean isBST; // 是不是搜索二叉树
        public int max; // 子树上的最大值
        public int min; // 子树上的最小值
        public int maxSubBSTSize; // 最大的搜索二叉树的大小

        public Info(boolean isBST, int max, int min, int maxSubBSTSize) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
            this.maxSubBSTSize = maxSubBSTSize;
        }
    }

    public static int getMaxSubBSTSize(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return process(node).maxSubBSTSize;
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return null; // 因为不好定义max和min，所以只能返回null，在调用方那边进行判断
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isBST = true; // 是不是搜索二叉树
        int max = node.val; // 子树上的最大值
        int min = node.val; // 子树上的最小值
        int maxSubBSTSize = 0; // 最大的搜索二叉树的大小
        if (leftInfo != null) {
            // 左子树还是搜索二叉树，且当前值 大于 左子树的最大值
            isBST = leftInfo.max < node.val && leftInfo.isBST;
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            // 右子树还是搜索二叉树，且当前值 小于 右子树的最小值
            isBST = isBST? (node.val < rightInfo.min && rightInfo.isBST) : isBST;
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        // 经过上面的两个if，如果当前节点还 可以是搜索二叉树的情况下，就可以统计
        // 以当前节点作为根节点时，子树的节点数了
        int leftSize = leftInfo != null? leftInfo.maxSubBSTSize : 0;
        int rightSize = rightInfo != null? rightInfo.maxSubBSTSize : 0;
        if (isBST) {
            maxSubBSTSize = leftSize + rightSize + 1;
            return new Info(isBST, max, min, maxSubBSTSize);
        }

        // 走到这一步，说明不能以当前节点作为根节点，此时计算左右两边子树的最大搜索树的大小，返回即可
        return new Info(isBST, max, min, Math.max(leftSize, rightSize));
    }

    // 生成当前节点的中序遍历结果，判断是不是一个升序的，如果是的话，就返回这个升序的个数，否则返回0
    public static int getBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // 暴力方法-用于测试
    public static int maxSubBSTSize1(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head); // 判断以当前节点作为根节点的情况
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }
}
