package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-14
 * Time: 10:39
 * Description: 最长回文子序列 LeetCode516
 */
public class Code07_LongestPalindromeSubsequence {
    public static void main(String[] args) {
        String str = "aa";
        System.out.println(longestPalindromeSubsequence1(str));
        System.out.println(longestPalindromeSubsequence2(str));
        System.out.println(longestPalindromeSubsequence3(str));
        System.out.println(longestPalindromeSubsequence4(str));
        System.out.println(longestPalindromeSubsequence5(str));
    }

    // 反转字符串后，用 最长公共子序列解
    public static int longestPalindromeSubsequence1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int N = str.length();
        // 得到str的回文串，然后用回文串和主串进行 最长公共子序列的改法，就能得到
        // 最长回文子序列了
        String reverseStr = new StringBuilder(str).reverse().toString();
        char[] str1 = str.toCharArray();
        char[] str2 = reverseStr.toCharArray();
        int[][] dp = new int[N][N]; // str作为行，reverseStr作为列
        dp[0][0] = str2[0] == str1[0] ? 1 : 0;
        // 第一行和第一列
        for (int j = 1; j < N; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1]; // 第一行
            dp[j][0] = str1[j] == str2[0] ? 1 : dp[j - 1][0]; // 第一列
        }

        // 填写普遍位置
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ?
                        dp[i - 1][j - 1] + 1 : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][N - 1];
    }

    // 反转字符串后，用最长公共子序列解，空间压缩版本
    public static int longestPalindromeSubsequence2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int N = str.length();
        // 得到str的回文串，然后用回文串和主串进行 最长公共子序列的改法，就能得到
        // 最长回文子序列了
        String reverseStr = new StringBuilder(str).reverse().toString();
        char[] str1 = str.toCharArray();
        char[] str2 = reverseStr.toCharArray();
        int[] dp = new int[N];
        dp[0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < N; j++) {
            dp[j] = str1[0] == str2[j] ? 1 : dp[j - 1];
        }
        // 填写普遍位置
        for (int i = 1; i < N; i++) {
            int leftUp = dp[0]; // 左上角
            dp[0] = str1[i] == str2[0] ? 1 : dp[0];
            for (int j = 1; j < N; j++) {
                int up = dp[j];
                dp[j] = Math.max(dp[j - 1], up); // 左边和上边取最大值
                if (str1[i] == str2[j]) {
                    dp[j] = Math.max(dp[j], 1 + leftUp);
                }
                leftUp = up; // 更新左上角的值
            }
        }
        return dp[N - 1];
    }

    // 不反转字符串，用“范围尝试模型”解--递归版本
    public static int longestPalindromeSubsequence3(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        /*
           函数定义 f(str, L, R)，返回是L~R范围内最长回文子序列长度。（开头位置怎么怎么样，结尾位置怎么怎么样的尝试）
           四种尝试的可能性：
           1、不以L位置的字符作为 回文子序列的开头，调用 f(str, L+1, R)
           2、不以R位置的字符作为 回文子序列的结尾，调用 f(str, L, R-1)
           3、即不以L位置字符开头，也不以R位置字符结尾，调用 f(str, L+1,R-1)
           4、如果L位置和R位置的字符相同，那就调用 f(str, L+1,R-1) + 2，加2就是L和R位置的字符数量
         */
        char[] chars = str.toCharArray();
        return process1(chars, 0, chars.length - 1);
    }

    public static int process1(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) { // L和R位置相邻，预防下次递归调用L+1，R-1时，二者错过了，提前判断
            return str[L] == str[R] ? 2 : 1; // 相等的话就是2，不等的话就是1
        }
        int p1 = process1(str, L + 1, R);
        int p2 = process1(str, L, R - 1);
        int p3 = process1(str, L + 1, R - 1);
        int p4 = str[L] == str[R] ? 2 + process1(str, L + 1, R - 1) : 0;
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    // 不反转字符串，用“范围尝试模型”解--经典dp版本
    public static int longestPalindromeSubsequence4(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        /*
           函数定义 f(str, L, R)，返回是L~R范围内最长回文子序列长度。（开头位置怎么怎么样，结尾位置怎么怎么样的尝试）
           四种尝试的可能性：
           1、不以L位置的字符作为 回文子序列的开头，调用 f(str, L+1, R)
           2、不以R位置的字符作为 回文子序列的结尾，调用 f(str, L, R-1)
           3、即不以L位置字符开头，也不以R位置字符结尾，调用 f(str, L+1,R-1)
           4、如果L位置和R位置的字符相同，那就调用 f(str, L+1,R-1) + 2，加2就是L和R位置的字符数量
         */
        // 观察递归版本的代码，可变参数只有L和R，只需建立二维表即可
        char[] chars = str.toCharArray();
        int N = chars.length;
        int[][] dp = new int[N][N];
        // base case---左上到右下的对角线（L==R），以及这根对角线向右平移一个单位的对角线（L==R-1）
        dp[N - 1][N - 1] = 1; // 右下角顶点处
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1; // L == R的情况
            dp[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1; // L == R - 1的情况
        }

        // 填写普遍位置---从下往上 从左往右
        for (int L = N - 3; L >= 0; L--) { // 行
            for (int R = L + 2; R < N; R++) { // 列
//                int p1 = dp[L + 1][R]; // 下边
//                int p2 = dp[L][R - 1]; // 左边
//                int p3 = dp[L + 1][R - 1]; // 左下角
//                int p4 = chars[L] == chars[R] ? 2 + dp[L + 1][R - 1] : 0;
//                dp[L][R] = Math.max(Math.max(p1, p2), Math.max(p3, p4));

                // 优化掉了左下角的值的调用，因为左边和下边的值，一定会抓取到这个左下角的值
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                if (chars[L] == chars[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    // 不反转字符串，用“范围尝试模型”解--dp空间压缩版本
    public static int longestPalindromeSubsequence5(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }
        // 分析经典dp版本，可知每个位置的只依赖于左边和下边的值，可用一维数组代替
        char[] chars = str.toCharArray();
        int N = chars.length;
        int[] dp = new int[N];
        dp[N - 1] = chars[N - 1] == chars[N - 2]? 2 : 1; // 倒数第2行最后一个位置，L==R-1时
        dp[N - 2] = 1; // 倒数第2行，L==R时
        for (int L = N - 3; L >= 0; L--) {
            dp[L] = 1; // L == R时
            int leftDown = dp[L + 1]; // 左下角的值
            dp[L + 1] = chars[L] == chars[L + 1]? 2 : 1; // L == R - 1 时
            for (int R = L + 2; R < N; R++) {
                int cur = dp[R]; // 记录当前的值，下次循环的左下角的值
                dp[R] = Math.max(dp[R], dp[R - 1]); // 下边和左边取最大值
                if (chars[L] == chars[R]) {
                    dp[R] = Math.max(dp[R], leftDown + 2);
                }
                leftDown = cur; // 更新左下角的值
            }
        }
        return dp[N - 1];
    }
}
