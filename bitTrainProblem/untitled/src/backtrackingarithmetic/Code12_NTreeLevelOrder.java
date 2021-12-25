package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:34
 * Description:N叉树的层序遍历
 * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
 * 树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
 * LeetCode：https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
 */
public class Code12_NTreeLevelOrder {
    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    class Solution {
        public List<List<Integer>> levelOrder(Node root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> res = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                while (size-- != 0) {
                    root = queue.poll();
                    list.add(root.val);
                    queue.addAll(root.children);
                }
                res.add(list);
            }
            return res;
        }
    }
}
