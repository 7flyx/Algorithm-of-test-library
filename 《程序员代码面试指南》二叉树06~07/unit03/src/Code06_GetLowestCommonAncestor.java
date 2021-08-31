import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by flyx
 * Description: 返回二叉树中两个节点的最低公共祖先（进阶）
 * User: 听风
 * Date: 2021-08-31
 * Time: 8:09
 */

public class Code06_GetLowestCommonAncestor {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");

        int n = Integer.parseInt(nums[0]);

        TreeNode root = createTree(in);
        Record record = new Record(root);
        int m = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            nums = in.readLine().split(" ");
            TreeNode node1 = getNode(root,Integer.parseInt(nums[0]));
            TreeNode node2 = getNode(root,Integer.parseInt(nums[1]));
            TreeNode ancestor = gerLowestCommonAncestor2(root, node1, node2);
            if (ancestor != null) {
                sb.append(ancestor.val).append("\n");
            }
        }
        System.out.println(sb.toString());
        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right, parent;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(BufferedReader in) throws IOException {
        String[] values = in.readLine().split(" ");
        TreeNode node = new TreeNode(Integer.parseInt(values[0]));
        if (!values[1].equals("0")) {
            node.left = createTree(in);
            node.left.parent = node;
        }
        if (!values[2].equals("0")) {
            node.right = createTree(in);
            node.right.parent = node;
        }
        return node;
    }

    public static TreeNode getNode(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        if (node.val == val) {
            return node;
        }
        TreeNode left = getNode(node.left, val);
        if (left != null) {
            return left;
        }
        return getNode(node.right, val);
    }

    //解法一
    public static TreeNode getLowestCommonAncestor1(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || root == node1 || root == node2) {
            return root; //遇见谁，就返回谁
        }
        TreeNode left = getLowestCommonAncestor1(root.left, node1, node2);
        TreeNode right = getLowestCommonAncestor1(root.right, node1, node2);
        if (left != null && right != null) {
            return root; //两边都不为null，则当前节点就是最低公共祖先节点
        }
        return left != null? left : right; //谁不为空，就返回谁
    }

    /**
     *  解法二：建立哈希表，记录每个节点的左右子树，对应的最低公共节点就是当前这个节点
     */
    private static class Record {
        private final HashMap<TreeNode, HashMap<TreeNode, TreeNode>> map;

        public Record(TreeNode head) {
            map = new HashMap<>();
            initMap(head); //将对应的节点，都建一张表
            setMap(head); //将才建立的每一张表，进行填写
        }

        private void initMap(TreeNode node) {
            if (node == null) {
                return;
            }
            map.put(node, new HashMap<>()); //对应的每一个节点，都建立一张表
            initMap(node.left);
            initMap(node.right);
        }

        private void setMap(TreeNode node) {
            if (node == null) {
                return;
            }

            headRecord(node.left, node);
            headRecord(node.right, node); //将左右子树的所有节点，放入相应的表中
            subRecord(node);

            setMap(node.left);
            setMap(node.right);
        }

        private void headRecord(TreeNode cur, TreeNode head) {
            if (cur == null) {
                return;
            }
            map.get(cur).put(head, head);
            headRecord(cur.left, head);
            headRecord(cur.right, head);
        }

        private void subRecord(TreeNode node) {
            if (node == null) {
                return;
            }

            preLeft(node.left, node.right, node);
            subRecord(node.left);
            subRecord(node.right);
        }

        private void preLeft(TreeNode left, TreeNode right, TreeNode head) {
            if (left == null) {
                return;
            }
            preRight(left, right, head);
            preLeft(left.left, right, head);
            preLeft(left.right, right, head);
        }

        /**
         *  实则就是循环遍历每一个节点，进行组合。 比如node1 和当前头节点的右子树的所有节点进行组合
         *
         * @param left 左子树 -- 固定不动
         * @param right 右子树 -- 去一个个遍历，进行组合
         * @param head  头节点
         */
        private void preRight(TreeNode left, TreeNode right, TreeNode head) {
            if (right == null) {
                return;
            }
            map.get(left).put(right,head);
            preRight(left, right.left, head);
            preRight(left, right.right, head);
        }

        public TreeNode query(TreeNode node1, TreeNode node2) {
            if (node1 == node2) {
                return node1;
            }
            if (map.containsKey(node1) && map.get(node1).containsKey(node2)) {
                return map.get(node1).get(node2);
            }
            if (map.containsKey(node2) && map.get(node2).containsKey(node1)) {
                return map.get(node2).get(node1);
            }
            return null;
        }
    }

    //解法三
    public static TreeNode gerLowestCommonAncestor2(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || node1 == null || node2 == null) {
            return null;
        }
        HashSet<TreeNode> set1 = new HashSet<>();
        while (node1 != null) {
            set1.add(node1);
            node1 = node1.parent;
        }

        while (node2 != null && !set1.contains(node2)) {
            node2 = node2.parent;
        }
        return node2;
    }
}