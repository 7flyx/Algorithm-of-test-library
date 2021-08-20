
import java.io.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by flyx
 * Description: 遍历二叉树
 * User: 听风
 * Date: 2021-08-20
 * Time: 10:15
 */


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val) {
        this.val = val;
    }
}

public class Code01_ErgodicTree {
    public static void main(String[] args) throws IOException  {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]); //结点数
        int m = Integer.parseInt(nums[1]); //根结点
        TreeNode root = new TreeNode(m);
        HashMap<Integer, TreeNode> map = new HashMap<>();
        map.put(m, root);
        for (int i = 0; i < n; i++) {
            nums = in.readLine().split(" ");
            createTree(nums, map);
        }

        preOrderUnRecur(root);
        inOrderUnRecur(root);
        posOrderUnRecur(root);
        in.close();
    }

    public static void createTree(String[] nums, HashMap<Integer, TreeNode> map) {
        if (nums == null || map == null) {
            return;
        }
        TreeNode node = map.get(Integer.parseInt(nums[0]));
        if (!nums[1].equals("0")) {
            node.left = new TreeNode(Integer.parseInt(nums[1]));
            map.put(Integer.parseInt(nums[1]), node.left);
        }
        if (!nums[2].equals("0")) {
            node.right = new TreeNode(Integer.parseInt(nums[2]));
            map.put(Integer.parseInt(nums[2]), node.right);
        }
        map.remove(Integer.parseInt(nums[0]));
    }

    public static void preOrderUnRecur(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        System.out.println();
    }

    public static void inOrderUnRecur(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.print(root.val + " ");
                root = root.right;
            }
        }
        System.out.println();
    }

    public static void posOrderUnRecur(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode pre = root; //指向打印过的结点，初始化为root根结点
        TreeNode cur = null;
        while (!stack.isEmpty()) {
            cur = stack.peek(); //拿到栈顶元素
            if (cur.left != null && cur.left != pre && cur.right != pre) {
                stack.push(cur.left);
            } else if (cur.right != null && cur.right != pre) {
                stack.push(cur.right);
            } else {
                pre = cur;
                System.out.print(stack.pop().val + " ");
            }
        }
        System.out.println();
    }
}