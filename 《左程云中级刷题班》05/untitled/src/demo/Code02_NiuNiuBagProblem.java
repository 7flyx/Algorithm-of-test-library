package demo;


import java.util.Arrays;

/**
 * 牛牛准备参加学校组织的春游，出发前牛牛准备往背包里面装入一些零食，牛牛的背包容量是w
 * 牛牛家里一共有n袋零食，第i袋零食的体积是v[i],
 * 牛牛想知道在总体积不超过背包容量的情况下，一共有多少种零食放法。（总体积为0，也是一种放法）
 */
public class Code02_NiuNiuBagProblem {
    public static void main(String[] args) {
        int[] v = {2, 3, 7, 3, 8,6,3,10};
        int w = 15;
        System.out.println(bagProblem(v, w));
        System.out.println(bagProblem2(v, w));
        System.out.println(bagProblem3(v, w));
    }

    /**
     * @param v 零食的体积
     * @param w 背包的容量
     * @return 放回方法数
     */
    public static int bagProblem(int[] v, int w) {
        if (v == null || v.length < 1) {
            return 0;
        }

        if (w == 0) {
            return 1;
        }

        return process(v, w, 0);
    }

    public static int process(int[] v, int w, int index) {
        if (index == v.length) {
            return w >= 0 ? 1 : 0; //来到了最后一个位置，看背包剩余容量
        }

        int res = 0;
        res += process(v, w, index + 1); //不要当前位置的零食
        res += process(v, w - v[index], index + 1); //要当前位置的零食
        return res;
    }

    /**
     *  经典的dp
     * @param v
     * @param w
     * @return
     */
    public static int bagProblem2(int[] v, int w) {
        if (v == null || v.length == 0) {
            return 0;
        }
        if (w == 0) return 1;

        int N = v.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            dp[N][i] = 1;
        }

        for (int i = N - 1; i >= 0; i--) { //index
            for (int j = w; j >= 0; j--) { //w，剩余容量
                dp[i][j] = dp[i + 1][j];
                if (j - v[i] >= 0) {
                    dp[i][j] += dp[i + 1][j - v[i]];
                }
            }
        }

        return dp[0][w];
    }

    /**
     * 空间压缩
     * @param v
     * @param w
     * @return
     */
    public static int bagProblem3(int[] v, int w) {
        if (v == null || v.length == 0) {
            return 0;
        }
        if (w == 0) return 1;

        int N = v.length;
        int[] dp = new int[w + 1];
        Arrays.fill(dp,1);

        for (int i = N - 1; i >= 0; i--) { //index
            for (int j = w; j >= 0; j--) {
                if (j - v[i] >= 0) {
                    dp[j] += dp[j - v[i]];
                }
            }
        }
        return dp[w];
    }
}
