package class17;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-10
 * Time: 17:14
 * Description: 数字字符串转为 字符串
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class Code04_NumberStrToCharString {
    public static void main(String[] args) {
        String str = "111";
        System.out.println(numberStrToString(str));
        System.out.println(numberStrToString2(str));
        System.out.println(numberStrToString3(str));
        System.out.println(numberStrToString4(str));
    }

    // 递归版本
    public static int numberStrToString(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    public static int process(char[] chars, int index) {
        if (index == chars.length) {
            return 1;
        }
        if (chars[index] == '0') { // 当前位置是字符0的情况，直接返回了，题目并没有定义字符0的情况
            return 0;
        }
        // 当前字符自己作为一个字母的情况
        int ans = process(chars, index + 1);
        // 还有下一个字符，并且能和当前字符组合成26以内的数字
        if (index + 1 < chars.length) {
            if ((chars[index] == '1') ||
                    (chars[index] == '2' && chars[index + 1] <= '6')) {
                ans += process(chars, index + 2);
            }
        }
        return ans;
    }

    // 记忆化搜索版本
    public static int numberStrToString2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int N = str.length();
        int[] dp = new int[N + 1];
        Arrays.fill(dp, -1); // 全部填写为-1.表示这个位置还没来到过
        process2(str.toCharArray(), 0, dp);
        return dp[0];
    }

    public static int process2(char[] chars, int index, int[] dp) {
        if (dp[index] != -1) {
            return dp[index];
        }
        if (index == chars.length) {
            dp[index] = 1;
            return 1;
        }
        if (chars[index] == '0') {
            dp[index] = 0;
            return 0;
        }
        // 当前字符自己作为一个字母的情况
        int ans = process2(chars, index + 1, dp);
        // 还有下一个字符的情况，看看能不能和当前字符组合成26以内的数字
        if (index + 1 < chars.length) {
            if ((chars[index] == '1') ||
                    (chars[index] == '2' && chars[index + 1] <= '6')) {
                ans += process2(chars, index + 2, dp);
            }
        }
        dp[index] = ans;
        return ans;
    }

    // 经典dp版本
    public static int numberStrToString3(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int N = str.length();
        char[] chars = str.toCharArray();
        int[] dp = new int[N + 1];
        dp[N] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            if (chars[i] != '0') { // 当前位置的字符不是0的情况下，才有后续的结果
                dp[i] = dp[i + 1]; // 当前字符自己组合成一个字母的情况
                if (i + 1 < N) {
                    if ((chars[i] == '1') ||
                            (chars[i] == '2' && chars[i + 1] <= '6')) {
                        dp[i] += dp[i + 2];
                    }
                }
            }
        }
        return dp[0];
    }

    // 空间压缩版本
    public static int numberStrToString4(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        // 观察经典dp版本的代码，可以知道，当前位置的数据，依赖于紧挨着的后面两个位置
        // 所以我们只需要3个变量即可
        int second = 1; // 最后的一个位置
        int first = 1; // 倒数第二个位置
        int cur = 0; // 当前位置
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != '0') {
                cur = first;
                if (i + 1 < chars.length) {
                    if (chars[i] == '1' ||
                            (chars[i] == '2' && chars[i + 1] <= '6')) {
                        cur += second;
                    }
                }
            } else { // 当前字符是0的情况下
                cur = 0;
            }
            second = first;
            first = cur;
        }
        return cur;
    }
}
