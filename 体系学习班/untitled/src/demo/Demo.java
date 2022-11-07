package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-07
 * Time: 16:23
 * Description:
 * 求数组中和为P整数倍的最小集合
 * <p>
 * 示例：当P=5的时候，如果Case1:　输入　S = {1, 2, 3, 4, 5},
 * 则输出 T = {5} (元素个数为１）Case2:　输入　S = {1,2,3,4},
 * 则输出 T = {1, 4} 或　T = {2, 3} Case3: 输入　S = {1,2,6,7,12,17},
 * 则输出 T = {1,2,7} 或 T = {6,12,17} Case4:
 * 输入　S = {1,2,6,7,11, 12,13, 14, 17, 22}, 则输出 T = {1,14} 或 T = {2, 13} ...
 */
public class Demo {
    public static void main(String[] args) {
        int P = 103;
//        int[] S = {1, 2, 3, 4, 5};
//        int[] S = {1, 2, 6, 7, 12, 17};
        // int[] S = {1, 2, 6, 7, 11, 12, 13, 14, 17, 22};
        int[] S = {99};
        System.out.println(func(P, S));
    }

    public static List<Integer> func(int P, int[] S) {
        int N = S.length;
        int[] help = new int[N];
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            help[i] = S[i] % P;
            if (help[i] == 0) { // 刚好能整除，直接返回即可
                ans.add(S[i]);
                return ans;
            }
        }
        // 根据新搞得help数组进行开dp
        int[][] dp = new int[N][P + 1];
        int[][] len = new int[N][P + 1]; // 表示更短的距离
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1); // 先全部无效状态
            dp[i][0] = -2; // 第一列，表示 P=0的时候
        }
        dp[0][S[0]] = 0; // 第一行，表示只有一个数的情况
        len[0][S[0]] = 1;
        for (int i = 1; i < N; i++) { // 从上往下
            for (int j = 1; j <= P; j++) { // 从左往右
                if (dp[i - 1][j] != -1) { // 表示在0~i-1范围，已经能够凑齐j，就直接拿取即可。也就是不要i位置的数
                    dp[i][j] = dp[i - 1][j];
                    len[i][j] = len[i - 1][j];
                }
                if (j - help[i] >= 0 && dp[i - 1][j - help[i]] != -1) { // 尝试要i位置的数看看
                    int curLength = 1 + len[i - 1][j - help[i]];
                    if (len[i][j] == 0 || len[i][j] >= curLength) { // 说明dp[i][j]还没有值，或者是当前curLength更优
                        len[i][j] = curLength;
                        dp[i][j] = i;
                    }
                }
            }
        }
        if (dp[N - 1][P] != -1) { // 说明有解
            int row = N - 1;
            int col = P;
            while (row >= 0 && col >= 0 && dp[row][col] != -2) {
                int index = dp[row - 1][col];
                int num = S[index];
                ans.add(0, num); // 头插法
                row -= 1;
                col -= num % P;
            }
        }
        return ans; // 说明无解
    }
}
