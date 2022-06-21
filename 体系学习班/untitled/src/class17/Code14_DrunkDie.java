package class17;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-17
 * Time: 21:11
 * Description: 醉汉生存的概率。
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 */
public class Code14_DrunkDie {
    public static void main(String[] args) {
        int N = 9;
        int M = 10;
        int row = 4;
        int col = 1;
        int k = 9;
        System.out.println(drunkSurviveProbability1(N, M, row, col, k));
        System.out.println(drunkSurviveProbability2(N, M, row, col, k));
        System.out.println(drunkSurviveProbability3(N, M, row, col, k));
    }

    /**
     * @param N   区域宽度
     * @param M   区域长度
     * @param row 当前位置
     * @param col 当前位置
     * @param k   剩余步数
     * @return 返回醉汉的存活概率
     */
    public static double drunkSurviveProbability1(int N, int M, int row, int col, int k) {
        if (row < 0 || row >= N || col < 0 || col >= M || k <= 0) {
            return 0;
        }
        // 每个位置能向 上下左右四个方向移动，那么总共k步，总共走的方法数是4^k
        double sum = Math.pow(4, k);
        return process1(N, M, row, col, k) / sum;
    }

    public static int process1(int N, int M, int row, int col, int k) {
        if (row < 0 || row == N || col < 0 || col == M) { // 越界的情况，直接返回0，返回的就是生存的次数
            return 0;
        }
        if (k == 0) {
            return 1;
        }
        int ans = 0;
        ans += process1(N, M, row - 1, col, k - 1);
        ans += process1(N, M, row + 1, col, k - 1);
        ans += process1(N, M, row, col - 1, k - 1);
        ans += process1(N, M, row, col + 1, k - 1);
        return ans;
    }

    // 经典dp版本
    public static double drunkSurviveProbability2(int N, int M, int row, int col, int k) {
        if (row < 0 || col < 0 || row >= N || col >= M || k <= 0) {
            return 0;
        }
        // 由递归函数可知，可变参数有三个row,col, k
        int[][][] dp = new int[N][M][k + 1];
        // k = 0时，那一层都是base case = 1 ，也就是生存的情况
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }

        // 填写普遍位置
        for (int level = 1; level <= k; level++) { // 层数
            for (int i = 0; i < N; i++) { // 行
                for (int j = 0; j < M; j++) { // 列
                    // 每个位置是依赖于上下左右四个方向，而递归调用时，有可能会出现越界的情况
                    // 所以单独写一个函数用于获取下一层的数值
                    dp[i][j][level] += pick(dp, N, M, i + 1, j, level - 1);
                    dp[i][j][level] += pick(dp, N, M, i - 1, j, level - 1);
                    dp[i][j][level] += pick(dp, N, M, i, j + 1, level - 1);
                    dp[i][j][level] += pick(dp, N, M, i, j - 1, level - 1);
                }
            }
        }
        return dp[row][col][k] / Math.pow(4, k);
    }

    private static int pick(int[][][] dp, int N, int M, int i, int j, int level) {
        if (i < 0 || i >= N || j < 0 || j >= M) {
            return 0;
        }
        return dp[i][j][level];
    }

    // dp空间压缩
    public static double drunkSurviveProbability3(int N, int M, int row, int col, int k) {
        if (row < 0 || col < 0 || row >= N || col >= M || k <= 0) {
            return 0;
        }
        // 分析经典dp的代码，可知每个位置只依赖于下一层的数据，所有使用两个二维数组交替使用
        int[][] curLevel = new int[N][M];
        int[][] nextLevel = new int[N][M];
        for (int i = 0; i < N; i++) { // 全部填充为1
            Arrays.fill(nextLevel[i], 1);
        }
        // 填写普遍位置
        for (int level = 1; level <= k; level++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    curLevel[i][j] = 0;
                    curLevel[i][j] += pick(nextLevel, N, M, i + 1, j);
                    curLevel[i][j] += pick(nextLevel, N, M, i - 1, j);
                    curLevel[i][j] += pick(nextLevel, N, M, i, j + 1);
                    curLevel[i][j] += pick(nextLevel, N, M, i, j - 1);
                }
            }
            int[][] tmp = curLevel;
            curLevel = nextLevel;
            nextLevel = tmp;
        }
        return nextLevel[row][col] / Math.pow(4, k);
    }

    private static int pick(int[][] nextLevel, int N, int M, int i, int j) {
        if (i < 0 || j < 0 || i >= N || j >= M) {
            return 0;
        }
        return nextLevel[i][j];
    }
}
