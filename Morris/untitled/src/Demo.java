/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-09-28
 * Time: 21:26
 * Description:
 */
public class Demo {


    static int leafSize = 0;
    public static void getLeafSize(Node head) {
        if (head == null) {
            return;
        }
        if (head.left == null&& head.right == null) {
            leafSize++;
        }
        getLeafSize(head.left);
        getLeafSize(head.right);
    }

    public static int getKLevelSize(Node root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }

        return process(root, 1, k);
    }

    private static int process(Node node, int i, int k) {
        if (node == null) {
            return 0;
        }
        if (i == k) {
            return 1;
        }
        int left = process(node.left, i + 1, k);
        int right = process(node.right, i + 1, k);
        return left + right;
    }

}
