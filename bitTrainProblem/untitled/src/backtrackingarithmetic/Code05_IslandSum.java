package backtrackingarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:20
 * Description:岛屿的数量
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code05_IslandSum {
    //通过遍历一点，然后将一片岛屿的数据进行标记，将数据存储在另外一张表里
    class Solution {
        public int numIslands(char[][] grid) {
            if (grid == null) {
                return 0;
            }

            boolean[][] isVisit = new boolean[grid.length][grid[0].length];
            int res = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (!isVisit[i][j] && grid[i][j] == '1') {
                        res++;
                        infection(grid, isVisit, i, j); //进行感染，一片岛屿将全部感染
                    }
                }
            }
            return res;
        }

        private void infection(char[][] grid, boolean[][] isVisit, int i, int j) {
            if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0' || isVisit[i][j]) {
                return;
            }
            isVisit[i][j] = true;
            infection(grid, isVisit, i + 1, j);
            infection(grid, isVisit, i - 1, j);
            infection(grid, isVisit, i, j + 1);
            infection(grid, isVisit, i, j - 1);
        }
    }
}
