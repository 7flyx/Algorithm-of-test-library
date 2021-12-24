package backtrackingarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:14
 * Description:岛屿的周长
 * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
 * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，
 * 一个或多个表示陆地的格子相连组成的岛屿）。
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。
 * 格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/island-perimeter
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code03_IslandLength {
    class Solution {
        public int islandPerimeter(int[][] grid) {
            boolean[][] isVisit = new boolean[grid.length][grid[0].length];
            return process(grid, 0, 0, isVisit);
        }

        private int process(int[][] grid, int i, int j, boolean[][] isVisit) {
            if (i < 0 || j < 0 || j >= grid[0].length || i >= grid.length || isVisit[i][j]) return 0;
            int sum = 0;
            if (grid[i][j] == 1) {
                if (i - 1 < 0 || grid[i - 1][j] == 0) {
                    sum++;
                }
                if (i + 1 >= grid.length || grid[i + 1][j] == 0) {
                    sum++;
                }
                if (j - 1 < 0 || grid[i][j - 1] == 0) {
                    sum++;
                }
                if (j + 1 >= grid[0].length || grid[i][j + 1] == 0) {
                    sum++;
                }
            }
            isVisit[i][j] = true;
            sum += process(grid, i, j + 1, isVisit); //往右走
            sum += process(grid, i +1, j, isVisit); //往下走
            return sum;

        }
    }
}
