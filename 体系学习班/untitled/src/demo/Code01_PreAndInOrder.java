package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-08
 * Time: 20:58
 * Description: 2022-9-8 腾讯音乐 笔试第1题
 * 已如一个二叉树的先序遍历序列和中序遍历序列，但其中一些节点的值可能相同。请你返回所有满足条件的二叉树。二叉树在数组中的顺序是任意的.
 */
public class Code01_PreAndInOrder {
    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    // 返回的是层序遍历的结果
    public static List<List<Character>> getAllTree(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return new ArrayList<>();
        }
        List<List<Character>> ans = new ArrayList<>();
//        process(pre, 0, pre.length - 1, in, 0, in.length - 1,
//                new ArrayList<>(), ans);
        return ans;
    }

//    private static void process(int[] pre, int l1, int r1,
//                                int[] in, int l2, int r2,
//                                List<Character> list,
//                                List<List<Character>> ans) {
//        if (l1 > r1 || l2 > r2) {
//            return;
//        }
//
//        int root = pre[l1];
//        for (int i = l2; i <= r2; i++) {
//            if (in[i] == root) {
//                list.add()
//                process(pre, l1 + 1, r1,
//                        in, l2, i - 1,
//                        list, ans);
//            }
//        }
//    }

}
