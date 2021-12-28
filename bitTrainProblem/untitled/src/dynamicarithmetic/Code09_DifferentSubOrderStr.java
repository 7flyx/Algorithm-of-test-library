package dynamicarithmetic;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:26
 * Description:不同的子序列
 * https://www.nowcoder.com/practice/ed2923e49d3d495f8321aa46ade9f873?tpId=46&tqId=29065&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 */
public class Code09_DifferentSubOrderStr {
    public int numDistinct1 (String S, String T) {
        if (S == null || T == null || S.length() < T.length()) {
            return 0;
        }

        int N = S.length();
        int M = T.length();
        //第一行和第一列都是空串
        int[][] dp = new int[N + 1][M + 1];
        for (int i = 0; i <= N; i++) {
            dp[i][0] = 1; //主串变为空串，只有一种方法
        }
        //dp[0][i] = 0，空的主串，无法变成非空的子序列

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                //如果s（i - 1）==T(j - 1)，那么在子序列中要与不要两种状态
                //如果不相等，那么就只能不要s(i - 1)，只能去看s(i - 2)的状态
                dp[i][j] = S.charAt(i - 1) == T.charAt(j - 1)? dp[i - 1][j] + dp[i - 1][j - 1]:
                        dp[i - 1][j];
            }
        }
        return dp[N][M];
    }

    public int numDistinct(String S, String T) {
        if (S == null || T == null || S.length() < T.length()) {
            return 0;
        }

        int N = S.length();
        int M = T.length();
        int[] dp = new int[N + 1];
        Arrays.fill(dp, 1); //全部填充为1
        for (int i = 1; i <= M; i++) {
            int tmp = 0;
            dp[0] = i > 1? 0 : dp[0];
            int leftUp = dp[0];
            for (int j = 1; j <= N; j++) {
                tmp = dp[j]; //下一个左上角的值
                dp[j] = j == 1? 0 : dp[j - 1];
                if (S.charAt(j - 1) == T.charAt(i - 1)) {
                    dp[j] += leftUp; // 加上dp[i-1][j-1]
                }
                leftUp = tmp;
            }
        }

        return dp[N];
    }

}
