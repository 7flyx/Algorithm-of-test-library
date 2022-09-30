package class40;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-30
 * Time: 12:39
 * Description:
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * 整个过程要求时间复杂度O(N)
 */
public class Code01_BestSplitArray1 {
    public static void main(String[] args) {
        int[] arr = {10, 20, 4, 5, 6, 20};
        System.out.println(bestSplitArray(arr));
    }

    public static int bestSplitArray(int[] arr) {
        if (arr == null || arr.length < 2) { // 如果只有一个数的情况下，无论怎么划分最小值都是0
            return 0;
        }
        int sumAll = 0; // 全部数值的累加和
        for (int num : arr) {
            sumAll += num;
        }
        int sumL = 0;
        int sumR = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            sumL += arr[i]; // 左部分的累加和
            sumR = sumAll - sumL; // 右部分的累加和
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }
}
