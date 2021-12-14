import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-14
 * Time: 14:53
 * Description: 两种不同类型的硬币，有多少种组合方式
 * 现有n1+n2种面值的硬币， 其中前n1种为普通币， 可以取任意枚， 后n2种为纪念币，
 * 每种最多只能取一枚， 每种硬币有一个面值， 问能用多少种方法拼出m的面值？
 */
public class Code03_TwoCoinCombine {
    public static void main(String[] args) {
        int[] coins1 = {1, 2, 5, 10};
        int[] coins2 = {1, 5, 10, 25};
        System.out.println(calcTwoCoinCombine(coins1, coins2, 13));
    }

    /**
     * 实则就是对两种硬币的计算一个值,求对m的值。
     * 然后假设a中硬币需要计算a分钱，b中硬币需要计算b分钱。
     * a*b，就是其中的一种答案，然后再（a-1） * （b+1）……
     * 对全部的结果进行求和即可
     *
     * @param coins1 第一种硬币的面值
     * @param coins2 第二种硬币的面值
     * @return 返回总的发法数
     */
    public static int calcTwoCoinCombine(int[] coins1, int[] coins2, int m) {
        if (coins1 == null || coins2 == null || coins1.length < 1 || coins2.length < 1 || m < 0) {
            return 0;
        }

        int[][] dp1 = getDp1(coins1, m); //计算任意枚硬币的组合方式
        int[][] dp2 = getDp2(coins2, m); //计算只有一枚硬币的组合方式
        int res = 0;
        for (int left = 0; left <= m; left++) { //dp1能组合的钱数目标，剩下的就是dp2的
            //两种方式，进行相乘，得到一个结果，然后累加
            res += (dp1[coins1.length - 1][left] * dp2[coins2.length - 1][m - left]);
        }
        return res;
    }

    //计算有任意枚硬币的组合方法数
    private static int[][] getDp1(int[] coins, int m) {
        int N = coins.length;
        int[][] dp = new int[N][m + 1]; //建立dp表
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1; //第一列全部都是1
        }
        for (int j = 1; j <= m; j++) {
            if (j % coins[0] == 0) {
                dp[0][j] = 1; //第一列，能被当前硬币数整除的，都是一种方法数
            }
        }
        //填写普遍位置的值
        for (int i = 1; i < N; i++) { //行
            for (int money = 1; money <= m; money++) { //列
                if (money - coins[i] >= 0) { //不越界的情况，加左边一个“单位”的值
                    dp[i][money] = dp[i - 1][money] + dp[i][money - coins[i]];
                } else {
                    dp[i][money] = dp[i - 1][money]; //直接拿取它上边的值
                }
            }
        }
        return dp;
    }

    //计算只有一枚硬币的组合方法数
    public static int[][] getDp2(int[] coins, int m) {
        int[][] dp = new int[coins.length][m + 1];
        for (int i = 0; i < coins.length; i++) {
            dp[i][0] = 1; //第一列全部都是1
        }
        if (coins[0] <= m) { //第一行，只有这一个位置是1
            dp[0][coins[0]] = 1;
        }

        for (int i = 1; i < coins.length; i++) { //行
            for (int j = 1; j <= m; j++) { //列
                if (j - coins[i] >= 0) {
                    //因为只能要一个硬币，要了之后，只能去看i-1位置硬币，组成j-coins【i】的数
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j]; //也就是递归代码中，不要当前位置的硬币
                }
            }
        }
        return dp;
    }

}
