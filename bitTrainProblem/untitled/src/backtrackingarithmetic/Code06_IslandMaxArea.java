package backtrackingarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:22
 * Description:岛屿的最大面积
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-area-of-island
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code06_IslandMaxArea {
    class Solution {
        public int maxAreaOfIsland(int[][] grid) {
            if (grid == null) {
                return 0;
            }
            boolean[][] isVisit = new boolean[grid.length][grid[0].length];
            int res = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (!isVisit[i][j] && grid[i][j] == 1) {
                        res = Math.max(res, infection(grid, isVisit, i, j)); //取最大值
                    }
                }
            }
            return res;
        }
        //通过感染函数，进行计算所感染的岛屿数量
        private int infection(int[][] grid, boolean[][] isVisit, int i, int j) {
            if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0 || isVisit[i][j]) {
                return 0;
            }
            isVisit[i][j] = true;
            int res = 1;
            res += infection(grid, isVisit, i, j + 1);
            res += infection(grid, isVisit, i, j - 1);
            res += infection(grid, isVisit, i + 1, j);
            res += infection(grid, isVisit, i - 1, j);
            return res;
        }
    }
}
