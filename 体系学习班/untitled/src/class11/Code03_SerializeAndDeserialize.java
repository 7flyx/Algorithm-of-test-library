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
        // preorder(node, sb); // 前序遍历进行编码
        levelOrderEncode(node, sb); // 层序遍历进行编码
        return sb.toString();
    }

    public static TreeNode deserialize(String str) {
        if (str == null) {
            return null;
        }
//        String[] list = str.split(","); // 分割每个数值
//        Queue<String> queue = new LinkedList<>();
//        queue.addAll(Arrays.asList(list)); // 将数组的数据全部转移到队列中
//        return decode(queue);

        return levelOrderDecode(str); // 层序遍历进行解码
    }

    // 前序遍历进行解码
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

    // 前序遍历进行编码
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

    // 层序遍历进行编码
    public static void levelOrderEncode(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        sb.append(node.val).append(","); // 首先将根节点的数据加入进去
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.left != null) { // 判断弹出节点的孩子节点，并记录相应的数据
                sb.append(node.left.val).append(",");
                queue.add(node.left); // 将节点放入队列中
            } else {
                sb.append("#").append(","); // 以 # 表示null
            }
            if (node.right != null) {
                sb.append(node.right.val).append(",");
                queue.add(node.right);
            } else {
                sb.append("#").append(",");
            }
        }
    }

    // 层序遍历进行解码
    public static TreeNode levelOrderDecode(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String[] values = str.split(","); // 先分割出每一个节点的数据
        int index = 1; // 指向values数组
        TreeNode node = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while(!queue.isEmpty()) {
            TreeNode tmp = queue.poll();
            if (!values[index++].equals("#")) { // 先算左孩子
                tmp.left = new TreeNode(Integer.parseInt(values[index - 1]));
                queue.add(tmp.left);
            }
            if (!values[index++].equals("#")) {
                tmp.right = new TreeNode(Integer.parseInt(values[index - 1]));
                queue.add(tmp.right);
            }
        }
        return node;
    }
}
