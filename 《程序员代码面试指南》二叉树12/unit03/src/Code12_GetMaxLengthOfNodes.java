
import java.io.*;
import java.util.*;
/**
 * Created by flyx
 * Description: 给定一颗二叉树和一个整数 sum，求累加和为 sum 的最长路径长度。路径是指从某个节点往下，
 *          每次最多选择一个孩子节点或者不选所形成的节点链。
 * User: 听风
 * Date: 2021-09-09
 * Time: 20:44
 */

public class Code12_GetMaxLengthOfNodes {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");

        TreeNode root = createTree(in);
        int k = Integer.parseInt(in.readLine());

        System.out.println(getMaxNodesOfPath(root, k));

        in.close();
    }

    private static class TreeNode {
        public int val, number;
        public TreeNode left, right;
        public TreeNode(int val, int number) {
            this.val = val;
            this.number = number;
        }
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode createTree(BufferedReader in) throws IOException {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        String[] values = in.readLine().split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        root.number = Integer.parseInt(values[3]);
        if (!values[1].equals("0")) {
            root.left = new TreeNode(Integer.parseInt(values[1]));
            stack.push(root.left);
        }
        if (!values[2].equals("0")) {
            root.right = new TreeNode(Integer.parseInt(values[2]));
            stack.push(root.right);
        }

        while (!stack.isEmpty()) {
            values = in.readLine().split(" ");
            while (!stack.isEmpty() && stack.peek().val != Integer.parseInt(values[0])) {
                help.push(stack.pop());
            }
            TreeNode node = stack.pop();
            node.number = Integer.parseInt(values[3]);
            if (!values[1].equals("0")) {
                node.left = new TreeNode(Integer.parseInt(values[1]));
                stack.push(node.left);
            }
            if (!values[2].equals("0")) {
                node.right = new TreeNode(Integer.parseInt(values[2]));
                stack.push(node.right);
            }

            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }

        return root;
    }

    public static int getMaxNodesOfPath(TreeNode node, int k) {
        if (node == null) {
            return 0;
        }

        HashMap<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, 0); //特别重要
        return process(node, k, 0, 1, 0, sumMap);
    }

    private static int process(TreeNode node, int k, int preSum, int level, int maxLen, HashMap<Integer, Integer> sumMap) {
        if (node == null) {
            return maxLen;
        }

        int curSum = preSum + node.number;
        if (!sumMap.containsKey(curSum)) { //没有当前值，就新建
            sumMap.put(curSum, level);
        }
        if (sumMap.containsKey(curSum - k)) { //有当前值，更新最大值
            maxLen = Math.max(maxLen, level - sumMap.get(curSum - k));
        }

        //分别向左右子树进行递归
        maxLen = process(node.left, k, curSum, level + 1, maxLen, sumMap);
        maxLen = process(node.right, k, curSum, level + 1, maxLen, sumMap);

        //当左右子树递归结束后，还应判断一下当前level和当前节点的层数，如果相等，要删除
        if (level == sumMap.get(curSum)) {
            sumMap.remove(curSum);
        }

        return maxLen;
    }
}
