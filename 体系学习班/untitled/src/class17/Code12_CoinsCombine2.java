package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-17
 * Time: 21:09
 * Description: 货币组合问题2
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class Code12_CoinsCombine2 {
    public static void main(String[] args) {
        int[] arr = {7};
        int aim = 0;
        System.out.println(coinsCombine1(arr, aim));
        System.out.println(coinsCombine2(arr, aim));
        System.out.println(coinsCombine3(arr, aim));
        System.out.println(coinsCombine4(arr, aim));
    }

    // 从左到右的尝试模型-递归版本
    public static int coinsCombine1(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        return process1(coins, aim, 0);
    }

    public static int process1(int[] coins, int rest, int index) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
            ways += process1(coins, rest - coins[index] * zhang, index + 1);
        }
        return ways;
    }

    // 经典dp版本---有枚举行为
    public static int coinsCombine2(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[i] <= j; zhang++) {
                    ways += dp[i + 1][j - coins[i] * zhang];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][aim];
    }

    // 经典dp版本--优化掉枚举行为（斜率优化技巧）
    public static int coinsCombine3(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = dp[i + 1][j]; // 当前位置的货币不要的情况
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i][j - coins[i]]; // 直接拿取当前行，左手边位置的数据
                }
            }
        }
        return dp[0][aim];
    }

    // dp空间压缩+斜率优化
    public static int coinsCombine4(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int N = coins.length;
        int[] dp = new int[aim + 1];
        dp[0] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            // 因为dp[i][j]位置的计算，依赖于当前行左边位置的数据，所以整体从左往右更新
            for (int j = 0; j <= aim; j++) {
                // dp[i][j] = dp[i + 1][j]; // 当前位置的货币不要的情况，直接拿取下边的数据
                if (j - coins[i] >= 0) { // 拿取当前行 左手边的数据
                    dp[j] += dp[j - coins[i]];
                }
            }
        }
        return dp[aim];
    }
}
