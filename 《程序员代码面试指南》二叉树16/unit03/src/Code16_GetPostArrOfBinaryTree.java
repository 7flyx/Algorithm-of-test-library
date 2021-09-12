import java.io.*;

/**
 * Created by flyx
 * Description: 给出一棵二叉树的先序和中序数组，通过这两个数组直接生成正确的后序数组。不能建树
 * User: 听风
 * Date: 2021-09-12
 * Time: 16:24
 */

public class Code16_GetPostArrOfBinaryTree {
    private static int index; //静态方法，不能调用非静态成员

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        String[] nums = in.readLine().split(" ");
        int[] preArr = nextInt(nums, n); //生成先序数组
        nums = in.readLine().split(" ");
        int[] inArr = nextInt(nums, n); //生成中序数组
        index = n; //从数组的最后开始填写

        int[] posArr = new int[n];
        getPostArray(preArr, inArr, posArr, new ArrayIndex(0, n - 1), new ArrayIndex(0, n -1));

        for (int i = 0; i < n; i++) {
            System.out.print(posArr[i] + " ");
        }
        in.close();
    }

    public static int[] nextInt(String[] nums, int n) {
        if (nums == null) {
            return null;
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = Integer.parseInt(nums[i]);
        }
        return res;
    }

    private static class ArrayIndex {
        public int left;
        public int right;
        public ArrayIndex(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void getPostArray(int[] preArr, int[] inArr, int[] posArr, ArrayIndex preIndex, ArrayIndex inIndex) {

        if (preIndex.left == preIndex.right) {
            posArr[--index] = preArr[preIndex.left]; //将节点值放入数组
            return;
        }

        if (preIndex.left > preIndex.right ) {
            return;
        }
        posArr[--index] = preArr[preIndex.left]; //将节点值放入数组

        int headIndexOfIn = inIndex.left; //当前节点在中序数组的位置
        for (int i = inIndex.left; i <= inIndex.right; i++) {
            if (inArr[i] == preArr[preIndex.left]) {
                headIndexOfIn = i;
                break;
            }
        }

        int leftNumOfNodes = headIndexOfIn - inIndex.left; //计算出左子树的节点数

        //先进入右子树
        getPostArray(preArr, inArr, posArr,
                new ArrayIndex(preIndex.left + leftNumOfNodes + 1, preIndex.right), new ArrayIndex(headIndexOfIn + 1, inIndex.right));

        //再进入左子树
        getPostArray(preArr, inArr, posArr,
                new ArrayIndex(preIndex.left + 1, preIndex.left + leftNumOfNodes), new ArrayIndex(inIndex.left, headIndexOfIn - 1));
    }
}
