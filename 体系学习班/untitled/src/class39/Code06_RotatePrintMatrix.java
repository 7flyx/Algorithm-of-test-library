package class39;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:34
 * Description:
 * 给定一个正方形或者长方形矩阵matrix，实现转圈打印
 * 输入：
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 输出：1 2 3 6 9 8 7 4 5
 */
public class Code06_RotatePrintMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        printMatrix(matrix);
    }

    public static void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        int r1 = 0;
        int c1 = 0;
        int r2 = matrix.length - 1;
        int c2 = matrix[0].length  -1;
        while (r1 <= r2 && c1 <= c2) {
            printCirculation(matrix, r1++, c1++, r2--, c2--);
        }
    }

    private static void printCirculation(int[][] matrix, int r1, int c1, int r2, int c2) {
        StringBuilder stringBuilder = new StringBuilder();
         // 上面这一行
        for (int j = c1; j <= c2; j++) { // 这里写小于等于，下一循环从r1 + 1开始。为的就是避免只有一个数的时候，四个循环都错过去了
            stringBuilder.append(matrix[r1][j]).append(" ");
        }
        // 右边这一列
        for(int i = r1 + 1; i < r2; i++) {
            stringBuilder.append(matrix[i][c2]).append(" ");
        }
        // 下面这一行
        for (int j = c2; j > c1; j--) {
            stringBuilder.append(matrix[r2][j]).append(" ");
        }
        // 左边这一列
        for (int i = r2; i > r1; i--) {
            stringBuilder.append(matrix[i][c1]).append(" ");
        }
        System.out.print(stringBuilder.toString());
    }
}
