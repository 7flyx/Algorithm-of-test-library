package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-17
 * Time: 20:58
 * Description:  最小路径和
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 */
public class Code10_MatrixMinPathSum {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 3, 4, 5},
                {7, 9, 10, 3},
                {8, 6, 44, 1}};
        System.out.println(getMinPathSum1(matrix));
        System.out.println(getMinPathSum2(matrix));
        System.out.println(getMinPathSum3(matrix));
    }

    // 经典dp版本
    public static int getMinPathSum1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < N; i++) { // 第一列
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        for (int j = 1; j < M; j++) { // 第一行
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }

        // 普遍位置
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                // 上边和左边的值，取最小值
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[N - 1][M - 1];
    }

    // dp空间压缩版本1
    public static int getMinPathSum2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int[] dp = new int[M];
        dp[0] = matrix[0][0];
        for (int j = 1; j < M; j++) {
            dp[j] = dp[j - 1] + matrix[0][j];
        }
        // 普通位置
        for (int i = 1; i < N; i++) {
            dp[0] += matrix[i][0];
            for (int j = 1; j < M; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + matrix[i][j];
            }
        }
        return dp[M - 1];
    }

    // dp空间压缩，最优版本
    public static int getMinPathSum3(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        if (N >= M) { //M短一点，以M作为列，申请数组
            int[] dp = new int[M];
            dp[0] = matrix[0][0];
            for (int j = 1; j < M; j++) {
                dp[j] = dp[j - 1] + matrix[0][j];
            }
            // 普通位置
            for (int i = 1; i < N; i++) {
                dp[0] += matrix[i][0];
                for (int j = 1; j < M; j++) {
                    dp[j] = Math.min(dp[j], dp[j - 1]) + matrix[i][j];
                }
            }
            return dp[M - 1];
        }
        // 以N作为列的情况
        int[] dp = new int[N];
        dp[0] = matrix[0][0];
        for (int j = 1; j < M; j++) {
            dp[0] += matrix[0][j];
            for (int i = 1; i < N; i++) {
                dp[i] = Math.min(dp[i - 1], dp[i]) + matrix[i][j];
            }
        }
        return dp[N - 1];
    }
}
