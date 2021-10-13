import java.io.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-13
 * Time: 18:15
 * Description: 给定两个子串，返回最长公共子序列。如若没有，则返回-1
 */

public class Code06_GetMaxSubOrder {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str1 = in.readLine();
        String str2 = in.readLine();

        int[][] dp = getdp(str1.toCharArray(), str2.toCharArray());

        char[] res = new char[dp[dp.length - 1][dp[0].length - 1]];
        int index = res.length;
        int n = dp.length - 1;
        int m = dp[0].length - 1;
        while (index > 0) {
            if (str1.charAt(n) == str2.charAt(m)) {
                res[--index] = str1.charAt(n);
                m--;
                n--;
            } else if (n - 1 >= 0 && dp[n - 1][m] == dp[n][m]){
                n--; //向上移动
            } else if (m - 1 >= 0 && dp[n][ m - 1] == dp[n][m]){ //dp[n][m - 1] == dp[n][m]
                m--; //向左移动
            }
        }


        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
        }
        if (res.length == 0) {
            System.out.println(-1);
        }
        in.close();
    }

    public static int[][] getdp(char[] str1, char[] str2) {
        if (str1 == null || str2 == null) {
            return null;
        }

        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0]? 1 : 0; //第一个字符相等的情况
        //分别填写第一行和第一列
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0]? 1 : 0);
        }
        for (int i = 1; i < str2.length; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], str2[i] == str1[0]? 1 : 0);
        }

        //填写普通位置的。分别依赖于三种情况
        //1、 上方、左方、左上方+1.三者取最大值
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                //上方和左方取最大值
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);

                //左上方。前提是当前位置的字符是相等的情况下
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }

}