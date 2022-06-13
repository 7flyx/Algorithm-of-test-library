package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-12
 * Time: 11:48
 * Description: 样本对应模型，以样本的末尾作为标志，去整理可能性
 * 最长公共子序列问题 LeetCode 剑指offer2 95题
 */
public class Code06_LongestCommonSubsequence {
    public static void main(String[] args) {
        String str1 = "abcde";
        String str2 = "ace";
        System.out.println(longestCommonSubsequence(str1, str2));
        System.out.println(longestCommonSubsequence2(str1, str2));
        System.out.println(longestCommonSubsequence3(str1, str2));
    }

    //    暴力递归版本
    public static int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0 || s1.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process1(str1, str2, str1.length - 1, str2.length - 1);
    }

    public static int process1(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[0] == str2[0] ? 1 : 0;
        } else if (i == 0) { // str1来到了第一个字符位置
            if (str1[i] == str2[j]) {
                return 1;
            } else { // i位置不能往前动了，去查找j-1位置
                return process1(str1, str2, i, j - 1);
            }
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else { // j位置不能再往前动了，去找i-1位置
                return process1(str1, str2, i - 1, j);
            }
        } else { // i != 0 && j != 0的情况
            int p1 = process1(str1, str2, i, j - 1); // 以str1当前字符结尾的情况
            int p2 = process1(str1, str2, i - 1, j); // 以str2当前字符结尾的情况
            // 判断当前i和j位置的字符是否相等，如果相等的话，去判断他两前一个位置的情况
            int p3 = str1[i] == str2[j] ? 1 + process1(str1, str2, i - 1, j - 1) : 0;
            return Math.max(p1, Math.max(p2, p3)); // 三种可能性取最优的结果
        }
    }

    // 经典dp版本
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0 || s1.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M]; // 以str1作为行，以str2作为列
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        // 第一行
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        // 第一列
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        // 普遍位置
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i][j - 1]; // 左边位置，状态是以str1结尾字符为标志，查str2字符串前面的情况
                int p2 = dp[i - 1][j]; // 上边位置，状态是以str2结尾字符为标志，查str1字符串前面的情况
                int p3 = str1[i] == str2[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3)); // 三者取最优结果
            }
        }
        return dp[N - 1][M - 1]; // 返回右下角结果，即递归函数的初始化状态
    }

    // 空间压缩版本
    public static int longestCommonSubsequence3(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0 || s1.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        // 根据经典的dp版本可知，每个位置的依赖关系，只是来源于上边、左边和左上角的位置
        // 所以可以直接使用一维数组代替
        int longSide = Math.max(N, M); // 长边。作为行
        int shortSide = Math.min(N, M); // 短边。作为列，用于申请数组
        char[] longStr = longSide == str1.length ? str1 : str2; // 长的字符串
        char[] shortStr = longStr == str1 ? str2 : str1; // 短的字符串
        int[] dp = new int[shortSide]; // 以短边作为列，申请数组
        dp[0] = longStr[0] == shortStr[0] ? 1 : 0; // base case
        for (int j = 1; j < shortSide; j++) { // base case
            dp[j] = longStr[0] == shortStr[j] ? 1 : dp[j - 1];
        }
        // 填写普遍位置
        for (int i = 1; i < longSide; i++) { // 长边作为行
            int leftUp = dp[0]; // 左上角的值
            dp[0] = longStr[i] == shortStr[0] ? 1 : dp[0]; // 数组第一个位置的情况
            for (int j = 1; j < shortSide; j++) { // 短边作为列
                int p1 = dp[j - 1]; // 左边
                int p2 = dp[j]; // 上边，也是后续待更新的leftUp值
                int p3 = longStr[i] == shortStr[j] ? 1 + leftUp : 0;
                dp[j] = Math.max(p1, Math.max(p2, p3));
                // 更新依赖位置的数据
                leftUp = p2;
            }
        }
        return dp[shortSide - 1];
    }
}
