package dynamicarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:17
 * Description:最小路径和
 * 给定一个由非负整数填充的m x n的二维数组，现在要从二维数组的左上角走到右下角，请找出路径上的所有数字之和最小的路径。
 * 注意：你每次只能向下或向右移动。
 * https://www.nowcoder.com/practice/23462ed010024fcabb7dbd3df57c715e?tpId=46&tqId=29115&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 */
public class Code05_MinPathSum {
    class Solution {
        /**
         * @param grid int整型二维数组
         * @return int整型
         */
        public int minPathSum (int[][] grid) {
            if (grid == null) {
                return 0;
            }

            return process2(grid);
        }

        //经典dp
        private int process(int[][] grid) {
            int row = grid.length;
            int col =  grid[0].length;
            int[][] dp = new int[row][col];
            dp[0][0] = grid[0][0];
            for (int i = 1; i < col; i++) {//第一行
                dp[0][i] = dp[0][i - 1] + grid[0][i];
            }
            for (int j = 1; j < row; j++) {//第一列
                dp[j][0] = dp[j - 1][0] + grid[j][0];
            }
            for (int i = 1; i < row; i++) {
                for (int j = 1; j < col; j++) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
            return dp[row - 1][col - 1];
        }

        //dp空间压缩
        private int process2(int[][] grid) {
            int row = Math.max(grid.length, grid[0].length);
            int col = Math.min(grid.length, grid[0].length);
            int[] dp = new int[col];
            dp[0] = grid[0][0];
            //填写base case
            for (int j = 1; j < col; j++) {
                if (col == grid[0].length) {//还是以前的列数作为dp的长度
                    dp[j] = grid[0][j] + dp[j - 1];
                } else {//以前的行数作为dp的长度
                    dp[j] = grid[j][0] + dp[j - 1];
                }
            }

            //填写普遍位置
            if (col == grid[0].length) {//还是以前的列数作为dp的长度
                for (int i = 1; i < row; i++) {
                    dp[0] += grid[i][0];
                    for (int j = 1; j < col; j++) {
                        if (dp[j - 1] < dp[j]) {//左边的值最小
                            dp[j] = dp[j - 1];
                        }
                        dp[j] += grid[i][j];
                    }
                }
            } else {//以前的行数作为dp的长度
                for (int i = 1; i < row; i++) {
                    dp[0] += grid[0][i];
                    for (int j = 1; j < col; j++) {
                        if (dp[j - 1] < dp[j]) {//左边的值最小
                            dp[j] = dp[j - 1];
                        }
                        dp[j] += grid[j][i];
                    }
                }
            }
            return dp[col - 1];
        }


    }
}
