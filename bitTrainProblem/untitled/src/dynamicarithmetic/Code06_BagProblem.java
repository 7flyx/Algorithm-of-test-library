package dynamicarithmetic;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:22
 * Description:背包问题
 * https://www.lintcode.com/problem/125/
 */
public class Code06_BagProblem {
    public static void main(String[] args) {
        int[] A = {2, 3, 5, 7};
        int[] V = {1, 5, 2, 4};
        System.out.println(backPackII(10, A, V));
        System.out.println(process2(10, A, V));
    }

    public static int backPackII(int m, int[] A, int[] V) {
        if (m < 0 || A == null || V == null || A.length != V.length) {
            return 0;
        }

        int row = A.length;
        int col = m;
        int[][] dp = new int[row + 1][col + 1]; //index是行，背包大小m是列
        //最后一行的数据都是0
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j]; //当前位置的物品，不要。
                //背包还能够装下。
                if (j + A[i] <= m) {
                    dp[i][j] = Math.max(dp[i + 1][j + A[i]] + V[i], dp[i][j]); //要了当前位置的物品，取最好结果
                }
            }
        }
        return dp[0][0];//返回的是左上角的值
    }

    //递归版本
    public static int process1(int m, int[] A, int[] V, int index, int curM) {
        if (curM > m) { //背包装不下
            return -1;
        }
        if (curM == m || index == A.length) {
            return 0;
        }
        int p1 = process1(m, A, V, index + 1, curM); //当前位置不要
        int p2 = process1(m, A, V, index + 1, curM + A[index]);
        if (p2 == -1) {
            return p1;
        }
        return Math.max(p1, p2 + V[index]);
    }

    //dp空间压缩
    public static int process2(int m, int[] A, int[] V) {
        int[] dp = new int[m + 1];
        //最后一行的数据都是0，在java中就不用填了
        for (int i = A.length - 1; i >= 0; i--) {
            for (int j = 0; j <= m; j++) { //注意与经典dp有一定的区别
                if (j + A[i] <= m) {
                    dp[j] = Math.max(dp[j], dp[j + A[i]] + V[i]);
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[0];
    }


}
