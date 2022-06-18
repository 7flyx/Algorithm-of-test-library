package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-17
 * Time: 21:07
 * Description: 货币组合问题。
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 */
public class Code11_CoinsCombine1 {
    public static void main(String[] args) {
        int[] coins = {1, 1, 1};
        System.out.println(coinsCombine(coins, 2));
        System.out.println(coinsCombine2(coins, 2));
        System.out.println(coinsCombine3(coins, 2));
    }

    public static int coinsCombine(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim <= 0) {
            return 0;
        }
        return process1(coins, aim, 0);
    }

    private static int process1(int[] coins, int aim, int index) {
        if (aim < 0) {
            return 0;
        }
        if (index == coins.length) {
            return aim == 0 ? 1 : 0;
        }
        // 当前位置的货币，不要
        int noTake = process1(coins, aim, index + 1);
        int take = process1(coins, aim - coins[index], index + 1);
        return noTake + take;
    }

    // 经典dp版本
    public static int coinsCombine2(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim <= 0) {
            return 0;
        }
        // 分析可变参数有两个：aim和index
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = dp[i + 1][j]; // 不要当前货币的情况
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i + 1][j - coins[i]];
                }
            }
        }
        return dp[0][aim];
    }

    // dp空间压缩
    public static int coinsCombine3(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim <= 0) {
            return 0;
        }
        int N = coins.length;
        int[] dp = new int[aim + 1];
        dp[0] = 1;
        for (int i = N - 1; i >= 0; i--) { // 从下往上
            for (int j = aim; j >= 0; j--) { // 从右往左更新
//                dp[i][i] = dp[i + 1][j]; // 获取他下边的值
                if (j - coins[i] >= 0) {
                    dp[j] += dp[j - coins[i]]; // + dp[i+1][j - coins[i]]
                }
            }
        }
        return dp[aim];
    }
}
