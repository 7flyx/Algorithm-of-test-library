package class38;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-14
 * Time: 20:28
 * Description:
 * 给定一个非负数组arr，和一个正数m，返回arr的所有子序列中累加和%m之后的最大值。
 * （第一解：m不算太大，arr[i]的数据不大，例如1000以内，总之使其代码指令条数在10^8 以内。
 * 第二解：arr[i]的数据很大，m不算太大。
 * 第三解：m和arr[i]都很大，但数组长度比较小。分治）
 */
public class Code05_SubOrderArraySum {
    public static void main(String[] args) {
        int[] arr = {112, 200, 100, 50 ,6};
        int m = 300;
        System.out.println(getMaxSum1(arr, m));
    }

    // 解法一：以index和sum作为行和列
    public static long getMaxSum1(int[] arr, int m) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        int[][] dp = new int[N + 1][sum + 1];
        // 填写最后一行，也就是index = arr.length的时候
        for (int j = 0; j <= sum; j++) {
            dp[N][j] = j % m;
        }
        // 填写普通位置
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= sum; j++) {
                // 每个位置选择要与不要两种情况
                //1、不要这个数
                dp[i][j] = dp[i + 1][j];
                //2、要这个数
                if(j + arr[i] <= sum) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j + arr[i]]);
                }
            }
        }
        return dp[0][0];
    }

    // 解法二：arr[i]的数据很大，m不算太大。就只能以index和m作为行列
    public static long getMaxSum2(int[] arr, int m) {
        if (arr == null) {
            return 0;
        }

        return 0;
    }

}
