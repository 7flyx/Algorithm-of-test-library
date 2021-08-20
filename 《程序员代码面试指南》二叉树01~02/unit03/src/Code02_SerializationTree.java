
import java.io.*;
import java.util.*;
/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-20
 * Time: 11:06
 */



//class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//    public TreeNode(int val) {
//        this.val = val;
//    }
//}

public class Code02_SerializationTree {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        HashMap<String,TreeNode> map = new HashMap<>();
        TreeNode root = new TreeNode(Integer.parseInt(nums[1]));
        map.put(nums[1], root);
        for (int i = 0; i < n; i++) {
            nums = in.readLine().split(" ");
            createTree(nums, map);
        }

        System.out.println(posSerialize(root));

        System.out.println(levelSerialize(root));

        in.close();
    }

    public static void createTree(String[] nums, HashMap<String, TreeNode> map) {
        if (nums == null || map == null) {
            return;
        }

        TreeNode node = map.get(nums[0]);
        if (!nums[1].equals("0")) {
            node.left = new TreeNode(Integer.parseInt(nums[1]));
            map.put(nums[1], node.left);
        }
        if (!nums[2].equals("0")) {
            node.right = new TreeNode(Integer.parseInt(nums[2]));
            map.put(nums[2], node.right);
        }
        map.remove(nums[0]);
    }

    public static String posSerialize(TreeNode root) {
        if (root == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            if (root != null) {
                sb.append(root.val).append("!");
                stack.push(root.right);
                stack.push(root.left);
            } else {
                sb.append("#!");
            }
        }
        return sb.toString();
    }
    public static String levelSerialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            root = queue.poll();
            if (root != null) {
                sb.append(root.val).append("!");
                queue.add(root.left);
                queue.add(root.right);
            } else {
                sb.append("#!");
            }
        }
        return sb.toString();
    }
}