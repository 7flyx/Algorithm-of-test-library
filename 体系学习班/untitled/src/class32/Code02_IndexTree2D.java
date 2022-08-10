package class32;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-10
 * Time: 15:24
 * Description:
 * 测试链接：https://leetcode.com/problems/range-sum-query-2d-mutable
 * 但这个题是付费题目
 * 提交时把类名、构造函数名从Code02_IndexTree2D改成NumMatrix
 */
public class Code02_IndexTree2D {
    private int[][] nums;
    private int[][] tree;
    private int N; // 行数
    private int M; // 列数

    public Code02_IndexTree2D(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        N = matrix.length + 1;
        M = matrix[0].length + 1;
        nums = new int[N][M];
        tree = new int[N][M];
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < M - 1; j++) {
                update(matrix[i][j], i, j);
            }
        }
    }

    // row,col位置 更新值 val。 row,col的范围在 0~N-1，或者0~M-1
    public void update(int val, int row, int col) {
        if (N == 0 || M == 0 || row < 0 || col < 0 || row > N - 1 || col > M - 1) {
            return;
        }
        int num = val - nums[row + 1][col + 1]; // 差值
        nums[row + 1][col + 1] = val;
        for (int i = row + 1; i < N; i++) {
            for (int j = col + 1; j < M; j++) {
                tree[i][j] += num;
            }
        }
    }

    // 返回 row，col 到左上角的矩形的累加和
    private int sum(int row, int col) {
        if (row < 0 || col < 0 || row > N - 1 || col > M - 1) {
            return 0;
        }
        int ans = 0;
        for (int i = row + 1; i > 0; i -= (i & -i)) {
            for (int j = col + 1; j > 0; j -= (j & -j)) {
                ans += tree[i][j];
            }
        }
        return ans;
    }

    /**
     * @param row1 左上角
     * @param col1 左上角
     * @param row2 右下角
     * @param col2 右下角
     * @return 返回左上角 到 右下角 围成的矩形的累加和
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        return sum(row2, col2) - sum(row2, col1 - 1) - sum(row1, col2 - 1) + sum(row1 - 1, col1 - 1);
    }
}
