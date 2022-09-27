package class39;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:31
 * Description:
 * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 * 输入：
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 输出：
 * 7 4 1
 * 8 5 2
 * 9 6 3
 */
public class Code05_RotateMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        rotateMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    public static void rotateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) { // 必须是正方形
            return;
        }
        int r1 = 0;
        int c1 = 0;
        int r2 = matrix.length - 1;
        int c2 = matrix[0].length - 1;
        while (r1 < r2 && c1 < c2) {
            rotate(matrix, r1++, c1++, r2--, c2--);
        }
    }

    private static void rotate(int[][] matrix, int r1, int c1, int r2, int c2) {
        for (int i = 0; i < c2 - c1; i++) {// 二者的差值个数，就是这一行有几个参数需要旋转
            int tmp = matrix[r1][c1 + i];
            matrix[r1][c1 + i] = matrix[r2 - i][c1];
            matrix[r2 - i][c1] = matrix[r2][c2 - i];
            matrix[r2][c2 - i] = matrix[r1 + i][c2];
            matrix[r1 + i][c2] = tmp;
        }
    }
}
