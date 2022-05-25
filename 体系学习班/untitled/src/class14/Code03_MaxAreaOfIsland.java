package class14;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-24
 * Time: 22:35
 * Description: LeetCode 200题 岛屿的数量
 */
public class Code03_MaxAreaOfIsland {
    // 通过一个点，去感染附近的点。DFS
    class Solution {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                return 0;
            }

            int row = grid.length;
            int col = grid[0].length;
            int res = 0;
            // 遍历每一个位置，调用infect函数进行感染操作
            boolean[][] visit = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1' && !visit[i][j]) {
                        infect(visit, grid, i, j);
                        res++;
                    }
                }
            }
            return res;
        }

        private void infect(boolean[][] visit, char[][] grid, int r, int c) {
            if (r < 0 || r == visit.length || c < 0 || c == visit[0].length ||
                    visit[r][c] || grid[r][c] != '1') {
                return;
            }

            visit[r][c] = true;
            infect(visit, grid, r + 1, c);
            infect(visit, grid, r - 1, c);
            infect(visit, grid, r, c + 1);
            infect(visit, grid, r, c - 1);
        }
    }

    // 使用并查集的方式
    static class Solution2 {
        private int[] parent;
        private int[] size;
        private int[] helpStack;
        private int sets; // 集合数量
        private int col; // 总的列数

        public Solution2(char[][] board) {
            int r = board.length;
            int c = board[0].length;
            int length = r * c;
            parent = new int[length];
            size = new int[length];
            helpStack = new int[length];
            sets = 0;
            col = c; // 总的列数
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (board[i][j] == '1') {
                        int index = getIndex(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        // 根据行和列，得到在一维数组中的位置
        private int getIndex(int r, int c) {
            return r * col + c;
        }

        // 总共两个点，对此进行合并。以下四个参数，分别就是横坐标纵坐标
        public void union(int r1, int c1, int r2, int c2) {
            int dot1 = getIndex(r1, c1);
            int dot2 = getIndex(r2, c2);
            int f1 = findFather(dot1);
            int f2 = findFather(dot2);
            if (f1 != f2) { // 不在一个集合的时候，就合并起来
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    size[f2] = 0;
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    size[f1] = 0;
                    parent[f1] = f2;
                }
                sets--; // 合并两个集合之后，总的集合数就少了一个
            }
        }

        private int findFather(int index) {
            int hi = 0;
            while (index != parent[index]) {
                helpStack[hi++] = index;
                index = parent[index];
            }
            // 路径压缩
            while (hi-- > 0) {
                parent[helpStack[hi]] = index;
            }
            return index;
        }

        public int getSets() {
            return sets;
        }
    }

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        Solution2 unionSet = new Solution2(grid);
        // 处理第一行
        for (int col = 1; col < grid[0].length; col++) {
            if (grid[0][col - 1] == '1' && grid[0][col] == '1') {
                unionSet.union(0, col - 1, 0, col);
            }
        }
        // 处理第一列
        for (int row = 1; row < grid.length; row++) {
            if (grid[row - 1][0] == '1' && grid[row][0] == '1') {
                unionSet.union(row - 1, 0, row, 0);
            }
        }
        // 处理普遍位置
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') { // 左边
                        unionSet.union(i - 1, j, i, j);
                    }
                    if (grid[i][j - 1] == '1') { // 上边
                        unionSet.union(i, j - 1, i, j);
                    }
                }
            }
        }
        return unionSet.getSets();
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}};
        System.out.println(numIslands(grid));
    }
}
