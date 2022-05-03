package class11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-03
 * Time: 20:01
 * Description: 二叉树的序列化和反序列化
 */
public class Code03_SerializeAndDeserialize {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.left = new TreeNode(6);
        node.right.right = new TreeNode(7);

        String str = serialize(node);
        System.out.println(str);
        TreeNode node2 = deserialize(str);
        preorder(node);
        System.out.println();
        preorder(node2);
    }

    public static String serialize(TreeNode node) {
        StringBuilder sb = new StringBuilder();
        preorder(node, sb);
        return sb.toString();
    }

    public static TreeNode deserialize(String str) {
        if (str == null) {
            return null;
        }
        String[] list = str.split(","); // 分割每个数值
        Queue<String> queue = new LinkedList<>();
        queue.addAll(Arrays.asList(list)); // 将数组的数据全部转移到队列中
        return decode(queue);
    }

    private static TreeNode decode(Queue<String> queue) {
        String poll = queue.poll();
        if (poll.equals("#")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(poll));
        node.left = decode(queue);
        node.right = decode(queue);
        return node;
    }

    private static void preorder(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("#").append(",");
            return;
        }

        sb.append(node.val).append(",");
        preorder(node.left, sb);
        preorder(node.right, sb);
    }

    private static void preorder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preorder(node.left);
        preorder(node.right);
    }
}
