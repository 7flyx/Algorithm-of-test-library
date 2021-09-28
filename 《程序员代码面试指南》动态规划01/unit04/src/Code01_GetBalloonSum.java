import java.util.*;
/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-09-28
 * Time: 21:46
 * Description: 打气球的最大分数
 * https://www.nowcoder.com/practice/35119064d0224c35ab1ab612bffee8df?tpId=101&&tqId=33088
 */

public class Code01_GetBalloonSum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println(calcBalloonSum2(arr));
    }

    /**
     * 暴力递归版本
     * @param arr 气球的数值
     * @return 返回结果
     */
    public static int calcBalloonSum(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0]; //只有一个数值的情况，直接返回
        }

        //将数组两边添加1，解决递归时的越界问题
        int n = arr.length;
        int[] copyArr = new int[n + 2];
        copyArr[0] = 1;
        copyArr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            copyArr[i + 1] = arr[i];
        }
        //两边的数值1，只是为了解决越界问题，实则还是求的原范围内的数据
        return process(copyArr, 1, n);
    }

    private static int process(int[] copy, int L, int R) {
        if (L == R) {//只剩一个气球的情况
            return copy[L - 1] * copy[L]* copy[R + 1];
        }
        //先打L，或者R。二则取最大值
        int max = Math.max(copy[L - 1] * copy[L]* copy[R + 1] + process(copy, L + 1, R),
                copy[L - 1] * copy[R] * copy[R + 1] + process(copy, L, R - 1));

        //再打L到R范围内的，取最大值
        for (int i = L + 1; i < R; i++) {
            max = Math.max(max, copy[L - 1] * copy[i] * copy[R + 1]
                    + process(copy, L, i - 1) + process(copy, i + 1, R));
        }
        return max;
    }

    /**
     * 动态规划版本
     * @param arr 气球的数值
     * @return 结果
     */
    public static int calcBalloonSum2(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }

        int n = arr.length;
        int[] copyArr= new int[n + 2];
        copyArr[0] = 1;
        copyArr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            copyArr[i + 1] = arr[i];
        }

        int[][] dp = new int[n + 2][n + 2]; //缓存
        for (int i = 1; i <= n; i++) {
            dp[i][i] = copyArr[i - 1] * copyArr[i] * copyArr[i + 1];
        }

        //从最下面一行，往上，从左往右
        //每个位置都依赖于左边和右边的值
        for (int L = n; L >= 1; L--) {
            for (int R = L + 1; R <= n; R++) {
                //求解LR范围内的
                //最后打爆左边的
                int finalL = copyArr[L - 1] * copyArr[L] * copyArr[R + 1] + dp[L + 1][R];
                //最后打爆右边的
                int finalR = copyArr[L - 1] * copyArr[R] * copyArr[R + 1] + dp[L][R - 1];
                dp[L][R] = Math.max(finalL, finalR); //左右两边取最大值

                //再枚举LR范围内的数值
                for (int i = L + 1; i < R; i++) {
                    dp[L][R] = Math.max(dp[L][R],
                            copyArr[L - 1] * copyArr[i] * copyArr[R + 1] +
                                    dp[L][i -1] + dp[i + 1][R]);
                }
            }
        }

        return dp[1][n];
    }
}
