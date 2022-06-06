package class17;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-06
 * Time: 18:21
 * Description: 机器人走路
 *
 */
public class Code01_RobotWalk {
    public static void main(String[] args) {
        System.out.println(way1(4, 2, 4, 4));
        System.out.println(way2(4, 2, 4, 4));
        System.out.println(way3(4, 2, 4, 4));
    }

    /**
     *  暴力递归版本
     * @param N 路的长度
     * @param start 开始起点
     * @param aim 目的地
     * @param K 总的步数
     * @return 返回从起点到终点处，有多少种方法
     */
    public static int way1(int N, int start, int aim , int K) {
        if (N < 2 || start < 0 || start > N || aim < 0 || aim > N || K < 0) {
            return 0;
        }
        return process1(N, aim, start, K);
    }

    public static int process1(int N, int aim, int cur, int rest) {
        if(rest == 0) {
            return cur == aim? 1 : 0;
        }
        // 如果当前是左边界，就只能往右走
        if (cur == 1) {
            return process1(N, aim, cur + 1, rest - 1);
        }
        // 如果当前是右边界，就只能往左走
        if (cur == N) {
            return process1(N, aim , cur - 1, rest  - 1);
        }
        // 中间位置，既能往右走也能往左走
        return process1(N, aim, cur - 1, rest - 1) + process1(N,aim, cur + 1, rest - 1);
    }

    /**
     *  记忆化搜索版本
     * @param N 路的长度
     * @param start 开始起点
     * @param aim 目的地
     * @param K 总的步数
     * @return 返回从起点到终点处，有多少种方法
     */
    public static int way2(int N, int start, int aim, int K) {
        if (N < 2 || start < 0 || start > N || aim < 0 || aim > N || K < 0) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1]; // 路的长度作为行，总步数作为列
        for (int[] arr : dp) { // 全部填写为-1
            Arrays.fill(arr, -1);
        }
        return process2(N, aim, start, K, dp);
    }

    // 以 cur，rest作为标记，二维数组进行存储
    private static int process2(int N, int aim, int cur, int rest, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        if(rest == 0) {
            dp[cur][rest] = cur == aim? 1 : 0;
            return dp[cur][rest];
        }

        int ans = 0;
        if (cur == 1) {
            ans = process2(N, aim, cur + 1, rest - 1, dp);
        } else if (cur == N) {
            ans = process2(N, aim, cur - 1, rest - 1, dp);
        } else {
            ans = process2(N, aim, cur + 1, rest - 1, dp) + process2(N, aim, cur - 1, rest - 1, dp);
        }
        dp[cur][rest] = ans; // 存储当前位置的数据
        return ans;
    }

    // 经典动态规划版本
    public static int way3(int N, int start, int aim, int K) {
        if (N < 2 || start < 0 || start > N || aim < 0 || aim > N || K < 0) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1]; // 以路的长度作为行，以总步数作为列
        // base case
        dp[aim][0] = 1; // 除了这个位置，当前这一列的数据都是0
        for (int rest = 1; rest <= K; rest++) { // 总步数作为列
            // 分析整个递归函数，每个位置的依赖关系是
            // 第一行的数据，依赖于左下角
            // 最后一行的数据，依赖于左上角
            // 中间位置的数据，依赖于 左上角+ 左下角
            dp[1][rest] = dp[2][rest - 1]; // 左下角
            dp[N][rest] = dp[N - 1][rest - 1]; // 左上角
            for (int cur = 2; cur < N; cur++) { // 路长作为行，普遍位置
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
        }
        return dp[start][K]; // 递归函数的调用的初始状态就是返回结果
    }
}
