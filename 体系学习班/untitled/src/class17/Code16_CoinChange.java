package class17;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-20
 * Time: 16:03
 * Description: LeetCode322 零钱兑换。 就类似于前几次写的那个硬币组合方法数。
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 */
public class Code16_CoinChange {
    public static void main(String[] args) {
        int[] arr = {1, 2, 5};
        int aim = 11;
        System.out.println(coinChange1(arr, aim));
        System.out.println(coinChange2(arr, aim));
        System.out.println(coinChange3(arr, aim));
        System.out.println(coinChange4(arr, aim));
    }

    // 暴力递归
    public static int coinChange1(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, aim, 0);
    }

    private static int process(int[] arr, int rest, int index) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE; // 返回的是组成rest的货币数，系统最大值表示这个是无效值
        }

        int ans = Integer.MAX_VALUE;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            int next = process(arr, rest - zhang * arr[index], index + 1);
            if (next != Integer.MAX_VALUE) { // 后续是有效值的情况，才判断最小
                ans = Math.min(ans, zhang + next);
            }
        }
        return ans;
    }

    // 经典dp版本
    public static int coinChange2(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        Arrays.fill(dp[N], Integer.MAX_VALUE);
        dp[N][0] = 0; // 在N=0时，只有rest=0时，才是0，其余都是无效值
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int zhang = 0; zhang * arr[i] <= rest; zhang++) {
                    if (dp[i + 1][rest - zhang * arr[i]] != Integer.MAX_VALUE) { // 下一层调用是有效值的情况
                        ans = Math.min(ans, dp[i + 1][rest - zhang * arr[i]] + zhang);
                    }
                }
                dp[i][rest] = ans;
            }
        }
        return dp[0][aim];
    }

    // 经典dp版本+斜率优化
    public static int coinChange3(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        Arrays.fill(dp[N], Integer.MAX_VALUE);
        dp[N][0] = 0; // 在N=0时，只有rest=0时，才是0，其余都是无效值
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = dp[i + 1][rest]; // 当前位置的货币一张也不要的情况
                // 左边的数据 + 1（要一张货币的情况），再进行判断
                // 左边的这个数据，已经计算过它的全部依赖，直接拿着用就行
                if (rest - arr[i] >= 0 && dp[i][rest - arr[i]] != Integer.MAX_VALUE) {
                    dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    // dp空间压缩+斜率优化
    public static int coinChange4(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] dp = new int[aim + 1];
        int N = arr.length;
        Arrays.fill(dp, Integer.MAX_VALUE); // 先全部填写无效值
        dp[0] = 0; // N = arr.length, rest = 0的时候
        System.out.println("coinChange4: ");
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                // dp[i][rest] = dp[i+1][rest]; // 拿取下边的值
                if (rest - arr[i] >= 0 && dp[rest - arr[i]] != Integer.MAX_VALUE) {
                    dp[rest] = Math.min(dp[rest], dp[rest - arr[i]] + 1);
                }
            }
        }
        return dp[aim];
    }
}
