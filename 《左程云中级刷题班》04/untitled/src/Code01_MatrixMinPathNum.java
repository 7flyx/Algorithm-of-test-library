import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-13
 * Time: 16:23
 * Description: 矩阵的最小路径和
 * 给定一个 n * m 的矩阵 a，从左上角开始每次只能向右或者向下走，
 * 最后到达右下角的位置，路径上所有的数字累加起来就是路径和，输出所有的路径中最小的路径和。
 */
public class Code01_MatrixMinPathNum {
    public static void main(String[] args) {
        int n = 4;
        int m = 4;
        int[][] matrix = {{1, 3, 5, 9, 4},
                {8, 1, 3, 4, 4},
                {5, 0, 6, 1,4},
                {8, 8, 4, 0,6}};
        System.out.println(calcPathSum(matrix));
        System.out.println(calcPathSum2(matrix));
        System.out.println(calcPathSum3(matrix));
    }

    public static int calcPathSum(int[][] matrix) {
        if (matrix == null || matrix.length < 1) {
            return 0;
        }

        return calcPathSum(matrix, 0, 0);
    }

    public static int calcPathSum(int[][] matrix, int row, int col) {
        if (row >= matrix.length || col >= matrix[0].length) {
            return -1;
        }

        int right = calcPathSum(matrix, row, col + 1); //右
        int down = calcPathSum(matrix, row + 1, col); //下
        if (right == -1 && down == -1) {
            return matrix[row][col];
        }
        if (right == -1) {
            return matrix[row][col] + down;
        }
        if (down == -1) {
            return matrix[row][col] + right;
        }
        return Math.min(right, down) + matrix[row][col];
    }

    //经典动态规划
    public static int calcPathSum2(int[][] matrix) {
        if (matrix == null) {
            return 0;
        }

        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        dp[n - 1][m - 1] = matrix[n - 1][m - 1]; //最右下角的数据

        //最后一行的数据
        for (int i = m - 2; i >= 0; i--) {
            dp[n - 1][i] = matrix[n - 1][i] + dp[n - 1][i + 1];
        }
        //最右一列的数据
        for (int i = n - 2; i >= 0; i--) {
            dp[i][m - 1] = matrix[i][m - 1] + dp[i + 1][m - 1];
        }

        //填写普通位置
        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                dp[i][j] = matrix[i][j] + Math.min(dp[i + 1][j], dp[i][j + 1]);
            }
        }
        return dp[0][0];
    }


    public static int calcPathSum3(int[][] matrix) {
        if (matrix == null || matrix.length < 1) {
            return 0;
        }

        int n = matrix.length;
        int m = matrix[0].length;
        int[] dp = new int[Math.min(n, m)];
        dp[dp.length - 1] = matrix[n - 1][m - 1];
        int shorts = 0;
        int longs = 0;
        if (dp.length == n) { //竖着遍历
            shorts = n;
            longs = m;
            for (int i = n - 2; i >= 0; i--) {
                dp[i] = matrix[i][m - 1] + dp[i + 1];
            }
        } else {
            shorts = m;
            longs = n;
            for (int j = m - 2; j >= 0; j--) {
                dp[j] = matrix[n - 1][j] + dp[j + 1];
            }
        }

        for (int i = longs - 2; i >= 0; i--) {
            //更新数组的最后一个数值
            dp[shorts - 1] += (shorts == n?  matrix[n - 1][i] : matrix[i][m - 1]);
            for (int j = shorts - 2; j >= 0; j--) {
                int tmp = shorts == n? matrix[j][i] : matrix[i][j];
                dp[j] = tmp + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }

}
