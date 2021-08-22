
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by flyx
 * Description: 测试平衡二叉树
 * User: 听风
 * Date: 2021-08-22
 * Time: 11:52
 */
public class Demo {
    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        ArrayList<Integer> list = new ArrayList<>();
        int[] arrays = new int[100];
        for (int i = 0 ;i < 100; i++) {
            int elem = (int)(Math.random() * 1000) + 1; //生成1~1000的随机数
            list.add(elem);
            avl.add(elem);
            arrays[i] = elem;
        }
        System.out.println("isBST: " + avl.isBST());
        System.out.println("isBalanceTree: " + avl.isBalanceTree());
        avl.inOrder();
        System.out.println();
        System.out.println("=================");

        for (int i  = 0 ; i < 100; i++) {
            avl.remove(arrays[i]);
            if (!avl.isBalanceTree()) {
                throw new RuntimeException("Error : balance");
            }
            if (!avl.isBST2()) {
                throw new RuntimeException("Error : BST2");
            }
            if (!avl.isBST()) {
                avl.inOrder();
                System.out.println();
                System.out.println(Arrays.toString(arrays));
                throw new RuntimeException("Error : BST");
            }
        }
        System.out.println("isEmpty: " + avl.isEmpty());

    }
}
