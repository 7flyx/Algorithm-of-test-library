package class12;

import class11.TreeNode;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-10
 * Time: 16:13
 * Description: 最低公共祖先
 */
public class Code08_LowestCommonAncestor {
    public static void main(String[] args) {

    }

    // 递归套路解
    public static TreeNode getLowestCommonAncestor1(TreeNode head, TreeNode o1, TreeNode o2) {
        return process(head, o1, o2).ans;
    }

    private static class Info  {
        public boolean findA;
        public boolean findB;
        public TreeNode ans;

        public Info(boolean findA, boolean findB, TreeNode ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    public static Info process(TreeNode head, TreeNode a, TreeNode b) {
        if (head == null) {
            return new Info(false, false, null);
        }

        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);
        boolean findA = (head == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (head == b) || leftInfo.findB || rightInfo.findB;
        TreeNode ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (findA && findB) {
                ans = head;
            }
        }
        return new Info(findA, findB, ans);
    }

    // 题解中的最优解
    public static TreeNode getLowestCommonAncestor(TreeNode head, TreeNode o1, TreeNode o2) {
        if (head == null) {
            return null;
        }
        if (o1 == head || o2 == head) {
            return head;
        }
        TreeNode left = getLowestCommonAncestor(head.left, o1, o2);
        TreeNode right = getLowestCommonAncestor(head.right, o1, o2);
        if (left != null && right != null) {
            return head;
        }
        return left != null? left : right;
    }
}
