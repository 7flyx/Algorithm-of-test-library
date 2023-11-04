/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-01-03
 * Time: 21:48
 * Description: 改二维的IndexTree
 */
public class Code02_IndexTree2D {
    private static class IndexTree {
        private int[][] tree;
        private int[][] nums;
        private int N;
        private int M;

        public IndexTree(int[][] arr) {
            N = arr.length;
            M = arr[0].length;
            tree = new int[N + 1][M + 1];
            nums = new int[N][M]; //也就是原数组的数据
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    add(i, j, arr[i][j]); //将二维数组的数据放入tree和nums中
                }
            }
        }

        public void add(int row, int col, int val) {
            if (this.N <= 0 || this.M <= 0) {
                return;
            }

            int add = val - nums[row][col]; //val是新改的值，计算与原来的差值，填到tree中即可
            nums[row][col] = val; //放入原数据
            for (int i = row + 1; i <= this.N; i += i & (-i)) {
                for (int j = col + 1; j <= this.M; j += j & (-j)) {
                    tree[i][j] += add;
                }
            }
        }

        //返回的是左上角 - row col，所框选的区域的总和
        public int sum(int row, int col) {
            int res = 0;
            for (int i = row + 1; i > 0; i -= i & (-i)) {
                for (int j = col + 1; j > 0; j -= j & (-j)) {
                    res += tree[i][j];
                }
            }
            return res;
        }

        //计算两个坐标点围成的矩形的面积
        public int calcAcre(int row1, int col1, int row2, int col2) {
            if (row1 > row2 || col1 > col2) {
                return -1;
            }

            int tmp = sum(row2, col2); //总的区域
            if (row1 - 1 >= 0 && col1 - 1 >= 0) {
                tmp += sum(row1 - 1, col1 - 1); //加上左上角的区域
            }
            tmp -= sum(row2, col1 - 1); //减去左半部分
            tmp -= sum(row1 - 1, col2); //减去上半部分
            return tmp;
        }
    }
}
