package class39;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:36
 * Description:
 * 给定一个正方形或者长方形矩阵matrix，实现zigzag打印
 * 输入：
 * 1  2  3
 * 4  5  6
 * 7  8  9
 *
 * 输出：1 2 4 7 5 3 6 8 9
 */
public class Code07_ZigzagPrintMatrix {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9},{10, 11, 12}};
        printMatrix(matrix);
    }

    public static void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        int r1 = 0;
        int c1 = 0; // 水平方向移动
        int r2 = 0;
        int c2 = 0; // 垂直方向 移动
        boolean isFromUp = false; // 是从上到下打印吗？
        while (true) {
            printCirculation(matrix, r1, c1, r2, c2, isFromUp);
            isFromUp = !isFromUp;
            r1 = c1 + 1 < matrix[0].length ? r1 : r1 + 1;
            c1 = c1 == matrix[0].length - 1 ? c1 : c1 + 1;

            c2 = r2 == matrix.length - 1? c2 + 1 : c2;
            r2 = r2 + 1 < matrix.length ? r2 + 1 : r2;
            if (r1 == r2 && c1 == c2) {
                printCirculation(matrix, r1, c1, r2, c2, isFromUp);
                break;
            }
        }
    }

    private static void printCirculation(int[][] matrix, int r1, int c1, int r2, int c2, boolean isFromUp) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isFromUp) { // 从上往下打印
            while (r1 != r2 && c1 != c2) {
                stringBuilder.append(matrix[r1++][c1--]).append(" ");
            }
            stringBuilder.append(matrix[r2][c2]).append(" ");
        } else {
            while (r1 != r2 && c1 != c2) {
                stringBuilder.append(matrix[r2--][c2++]).append(" ");
            }
            stringBuilder.append(matrix[r1][c1]).append(" ");
        }
        System.out.print(stringBuilder.toString());
    }
}
