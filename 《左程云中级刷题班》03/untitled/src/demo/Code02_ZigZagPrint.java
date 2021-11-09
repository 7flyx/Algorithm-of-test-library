package demo;
import java.io.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 16:30
 * Description:
 * 用zigzag的方式打印矩阵， 比如如下的矩阵
 * 0 1 2 3
 * 4 5 6 7
 * 8 9 10 11
 * 打印顺序为： 0 1 4 8 5 2 3 6 9 10 7 11
 */

public class Code02_ZigZagPrint {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);
        String[][] values = new String[n][m];
        for (int i = 0; i < n; i++) {
            values[i] = in.readLine().split(" ");
        }

        printOfArr(values);

        in.close();
    }

    public static void printOfArr(String[][] values) {
        if (values == null || values.length < 1 || values[0].length < 1) {
            return;
        }
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 0;
        boolean isFromLeftToRight = true;

        while (row1 < values.length && col2 < values[0].length){
            process(values, isFromLeftToRight, row1, col1, row2, col2);
            //横向点
            row1 = col1 + 1 == values[0].length? row1 + 1 : row1;
            col1 = col1 + 1 != values[0].length? col1 + 1 : col1;

            //纵向点
            col2 = row2 + 1 == values.length? col2 + 1: col2;
            row2 = row2 + 1 != values.length? row2 + 1 : row2;

            isFromLeftToRight = !isFromLeftToRight;
        }
    }

    private static void process(String[][] values, boolean isFromLeftToRight,
                                int row1, int col1, int row2, int col2) {
        if (isFromLeftToRight) {
            while (row1 != row2) {
                System.out.print(values[row2--][col2++] + " ");
            }
            System.out.print(values[row2][col2] + " ");
        } else {
            while (row1 != row2) {
                System.out.print(values[row1++][col1--] + " ");
            }
            System.out.print(values[row1][col1] + " ");
        }
    }
}