package dynamicarithmetic;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:17
 * Description:路径总数
 * 一个机器人在m×n大小的地图的左上角（起点）。
 * 机器人每次可以向下或向右移动。机器人要到达地图的右下角（终点）。
 * 可以有多少种不同的路径从起点走到终点？
 * https://www.nowcoder.com/practice/166eaff8439d4cd898e3ba933fbc6358?tpId=46&tqId=29117&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 *
 */
public class Code04_PathSum {
    public int uniquePaths (int m, int n) {
        if (m < 0 || n <0) {
            return 0;
        }
        return process3(m, n);
    }

    //暴力递归，过不了
    private int process(int m, int n) {
        if (m < 1 || n < 1) {
            return 0;
        }
        if (m == 1 && n == 1) {
            return 1;
        }
        return process(m, n - 1) + process(m - 1, n);
    }

    //经典dp，可变参数2个
    private int process2(int m, int n) {
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    //dp空间压缩
    private int process3(int m, int n) {
        if (n == 1 || m == 1) {
            return 1;
        }
        int row = m > n? m : n;//行数
        int col = row == m? n : m; //列数，也是dp的大小
        int[] dp = new int[col];
        Arrays.fill(dp, 1); //全部填充为1
        //不管是行作为dp的长度，还是列作为dp的长度，依赖关系还是没变
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[col - 1];
    }
}
