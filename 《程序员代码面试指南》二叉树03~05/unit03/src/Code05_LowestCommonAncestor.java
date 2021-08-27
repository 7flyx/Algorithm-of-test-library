
import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-27
 * Time: 20:51
 */


public class Code05_LowestCommonAncestor {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        String[][] arr = new String[n][];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readLine().split(" ");
        }
        TreeNode root = createTree(arr);

        nums = in.readLine().split(" ");
        TreeNode node1 = getNode(root, Integer.parseInt(nums[0])); //拿到两个节点
        TreeNode node2 = getNode(root,Integer.parseInt(nums[1]));
        TreeNode ancestor = lowestCommonAncestor(root, node1, node2); //返回最近的公共祖先节点
        System.out.println(ancestor.val);

        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right, parent;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(String[][] arr) {
        if (arr == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        TreeNode root = new TreeNode(Integer.parseInt(arr[0][0]));
        stack.push(root);
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && stack.peek().val != Integer.parseInt(arr[i][0])) {
                help.push(stack.pop());
            }
            TreeNode cur = stack.pop();
            if (!arr[i][1].equals("0")) {
                cur.left = new TreeNode(Integer.parseInt(arr[i][1]));
                cur.left.parent = cur;
                stack.push(cur.left);
            }
            if (!arr[i][2].equals("0")) {
                cur.right = new TreeNode(Integer.parseInt(arr[i][2]));
                cur.right.parent = cur;
                stack.push(cur.right);
            }
            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }
        return root;
    }

    public static TreeNode getNode(TreeNode root, int val){
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                if(root.val == val) {
                    break;
                }
                root = root.right;
            }
        }
        return root;
    }

    //递归解法
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || root == node1 || root == node2) {
            return root; //遇到null或者node1，或者node2，就直接返回
        }

        TreeNode left = lowestCommonAncestor(root.left, node1, node2);
        TreeNode right = lowestCommonAncestor(root.right, node1, node2);
        if (left != null && right != null) {
            return root; //左右两边都不是空，返回当前节点
        }
        //不然就是，至少有一个是null，返回另一个
        return left != null? left : right;
    }

    //存入哈希表，遍历解法
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || node1 == null || node2 == null) {
            return null;
        }

        HashMap<TreeNode, TreeNode> fatherMap = new HashMap<>();
        fatherMap.put(root, root); //根结点的父亲就是自己
        loadFatherMap(root, fatherMap);

        HashSet<TreeNode> set1 = new HashSet<>();
        TreeNode cur = node1;
        while (cur != fatherMap.get(cur)) {
            set1.add(cur);
            cur = fatherMap.get(cur);
        }

        cur = node2;
        while (cur != fatherMap.get(cur)) {
            if (set1.contains(cur)) {
                return cur;
            }
            cur = fatherMap.get(cur);
        }
        return root;
    }

    private static void loadFatherMap(TreeNode root, HashMap<TreeNode, TreeNode> fatherMap) {
        if (root == null) {
            return;
        }
        if(root.left != null) {
            fatherMap.put(root.left, root);
            loadFatherMap(root.left, fatherMap);
        }
        if (root.right != null) {
            fatherMap.put(root.right, root);
            loadFatherMap(root.right, fatherMap);
        }
    }

}
