package demo;

import java.util.Arrays;

/**
 * 已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历 数组，返回后序遍历数组。
 *  比如给定： int[] pre = { 1, 2, 4,5, 3, 6, 7 }; 
 *  int[] in = { 4, 2, 5, 1, 6, 3, 7 }; 
 *  返回： {4,5,2,6,7,3,1}
 * 
 * @author Administrator
 *
 */
public class Code04_PreOrderOfBinaryTree {
    public static void main(String[] args) {
        int[] pre = { 1, 2, 4,5, 3, 6, 7 };
        int[] in = { 4, 2, 5, 1, 6, 3, 7 };

        int[] pos = getPosOrderArray(pre, in);
        System.out.println(Arrays.toString(pos));
    }

    public static int[] getPosOrderArray(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return new int[]{};
        }
        int N = in.length;
        int[] posOrder = new int[N];
        process(pre, 0, N - 1,
                in, 0, N - 1,
                posOrder, 0, N - 1);
        return posOrder;
    }

    /**
     * 根据前序和中序，生成后序。
     * 前序：头左右
     * 后序：左右头
     * 即就是前序的第一个数，放到后序的最后一个数.
     * 可以将中序数组的所有数据，放到一个哈希表中，节省遍历中序数组的时间
     */
    public static void process(int[] pre, int preL, int preR,
                               int[] in, int inL, int inR,
                               int[]pos, int posL, int posR) {
        if (preL > preR) {
            return;
        }

        if (preL == preR) {
            pos[posR] = pre[preL];
            return;
        }

        pos[posR] = pre[preL]; //第一个数，放到pos的最后一个位置
        int find = inL;
        for (; find <= inR; find++) {
            if (in[find] == pre[preL]) {
                break; //在中序数组中查找这个数的位置
            }
        }
        int leftNum = find - inL;
        process(pre, preL + 1, preL + leftNum,
                in, inL, find - 1,
                pos, posL, posL + leftNum - 1);
        process(pre, preL + leftNum + 1,preR,
                in, find + 1, inR,
                pos, posL + leftNum, posR - 1);
    }


}
