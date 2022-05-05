package class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-05
 * Time: 15:20
 * Description: 整个二叉树中，最宽的一层，有多少个节点（不包括null）。
 */
public class Code05_BinaryTreeLevelNodes {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(3);
        node.right = new TreeNode(2);
        node.left.left = new TreeNode(5);
        node.left.right = new TreeNode(3);
        node.right.right = new TreeNode(9);
        System.out.println(getLevelMaxNodes2(node));


        TreeNode node2 = new TreeNode(1);
        node2.left = new TreeNode(3);
        node2.left.left = new TreeNode(5);
        node2.left.left.left = new TreeNode(6);
        node2.right = new TreeNode(2);
        node2.right.right = new TreeNode(9);
        node2.right.right.right = new TreeNode(7);

        System.out.println(getLevelMaxNodes2(node2));
    }

    // 不算null节点的情况
    public static int getLevelMaxNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        int res = 0;
        while (!queue.isEmpty()) {
            int curLevelSize = queue.size(); // 当前队列中的所有元素，都是在同一层的
            // 结算一下当前队列中的元素个数即可
            res = Math.max(res, curLevelSize);
            while (curLevelSize-- != 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }
        return res;
    }

    // 算上null节点的情况---LeetCode662题
    public static int getLevelMaxNodes2(TreeNode node) {
        if (node == null) {
            return 0;
        }

        HashMap<TreeNode, Integer> map = new HashMap<>(); // 用一张表记录每个节点对应的层序遍历 序号
        map.put(node, 1); // 根节点序号是1，那么根节点的左孩子是2，右孩子是3……
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        int res = 1;
        TreeNode leftNode = null; // 当前这一层的最左边节点
        TreeNode rightNode = null; // 当前这一层的最右边节点
        while (!queue.isEmpty()) {
            int curLevelSize = queue.size(); // 当前队列中的所有元素，都是在同一层的
            leftNode = queue.peek(); // 队头就是当前层最左节点
            while (curLevelSize-- != 0) {
                TreeNode tmp = queue.poll();
                if (tmp.left != null) {
                    queue.add(tmp.left);
                    map.put(tmp.left, map.get(tmp) * 2);
                }
                if(tmp.right != null) {
                    queue.add(tmp.right);
                    map.put(tmp.right, map.get(tmp) * 2 + 1);
                }
                rightNode = tmp; // 更新这一层最右节点
            }
            // 最右节点的值 - 最左节点的值 + 1，就是当前层数的最大节点数
            res = Math.max(res, map.get(rightNode) - map.get(leftNode) + 1);
        }
        return res;
    }
}
