package demo;
import java.io.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 16:31
 * Description:
 * 给定一个正方形矩阵， 只用有限几个变量， 实现矩阵中每个位置的数顺时针转动
 * 90度， 比如如下的矩阵
 * 0 1 2 3
 * 4 5 6 7
 * 8 9 10 11
 * 12 13 14 15
 * 矩阵应该被调整为：
 * 12 8 4 0
 * 13 9 5 1
 * 14 10 6 2
 * 15 11 7 3
 */

public class Code04_RotateMatrix {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int[][] martix = new int[n][n];
        for (int i = 0; i < n; i++) {
            nums = in.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                martix[i][j] = Integer.parseInt(nums[j]);
            }
        }

        rotate(martix);
        for (int[] i : martix) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }

        in.close();
    }

    public static void rotate(int[][] martix) {
        if (martix == null) {
            return;
        }

        int ar = 0;
        int ac = 0;
        int br = martix.length - 1;
        int bc = martix.length - 1; //正方形，长宽都一样
        while (ar < br) {
            process(martix, ar++, ac++, br--, bc--);
        }
    }

    private static void process(int[][] martix, int ar, int ac, int br, int bc) {
        int len = bc - ac + 1; //宽度
        for (int i = 0; i < len - 1; i++) {
            int tmp = martix[ar][ac + i];
            martix[ar][ac + i] = martix[br - i][ac];
            martix[br - i][ac] = martix[br][bc - i];
            martix[br][bc - i] = martix[ar + i][bc];
            martix[ar + i][bc] = tmp;
        }
    }
}
