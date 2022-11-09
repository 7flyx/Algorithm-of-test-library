package class47;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-09
 * Time: 8:40
 * Description: leetcode664题 奇怪的打印机 （难点就在于猜法上，如何进行尝试，才是这个题的难点）
 * 有台奇怪的打印机有以下两个特殊要求：
 * 打印机每次只能打印由同一个字符组成的序列。
 * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串s，你的任务是计算这个打印机打印它需要的最少打印次数。
 * Leetcode题目：https://leetcode.cn/problems/strange-printer/
 */
public class Code05_StrangePrinter {
    public static void main(String[] args) {
        String s = "aaabbb";
        System.out.println(strangePrinter(s));
        System.out.println(strangePrinter1(s));
    }

    // 暴力递归版本
    public static int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        return process(chars, 0, N - 1);
    }

    // 将L~R范围的字符，全部刷新为 chars[L]相同的字符，需要的最少次数
    // 而枚举时，是分割成两个部分的字符串进行尝试，所以只要这两个字符串的第一个字符是相等的
    // 则表示这两个字符串想要刷成同一种字符，那么整体只需要刷一次即可，无需让两个部分分别刷一次
    private static int process(char[] chars, int L, int R) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return 1;
        }
        int ans = R - L + 1; // 最坏情况就是全部的字符自己单独刷一次
        for (int i = L + 1; i <= R; i++) { // 在后续的所有字符中进行枚举
            ans = Math.min(ans,
                    process(chars, L, i - 1) + process(chars, i, R)
                            - (chars[L] == chars[i] ? 1 : 0)); // 划分出来的左部分和右部分的首字符相等，就可以减少一次刷的次数
        }
        return ans;
    }

    // 动态规划版本
    public static int strangePrinter1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 可变参数只有L和R两个，所以只需要一个二维表即可
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) { // base case, 只有L位置一个字符的情况
            dp[i][i] = 1;
        }
        // 填写普遍位置---从下往上
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                int ans = R - L + 1; // 最坏情况也就是每个字符单独刷一遍
                for (int i = L + 1; i <= R; i++) {
                    ans = Math.min(ans,
                            dp[L][i - 1] + dp[i][R] - (chars[L] == chars[i]? 1 : 0));
                }
                dp[L][R] = ans;
            }
        }
        return dp[0][N - 1];
    }


    public static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 可变参数只有L和R两个，所以只需要一个二维表即可
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) { // base case, 只有L位置一个字符的情况
            dp[i][i] = 1;
        }
        // 填写普遍位置---从下往上
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                if (chars[R] == chars[R - 1]) {
                    dp[L][R] = dp[L][R - 1];
                } else {
                    int ans = R - L + 1; // 最坏情况也就是每个字符单独刷一遍
                    for (int i = L + 1; i <= R; i++) {
                        ans = Math.min(ans,
                                dp[L][i - 1] + dp[i][R] - (chars[L] == chars[i]? 1 : 0));
                    }
                    dp[L][R] = ans;
                }
            }
        }
        return dp[0][N - 1];
    }
}
