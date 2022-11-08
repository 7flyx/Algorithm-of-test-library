package class47;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-06
 * Time: 9:46
 * Description:
 * 如果一个字符相邻的位置没有相同字符，那么这个位置的字符出现不能被消掉
 * 比如:"ab"，其中a和b都不能被消掉
 * 如果一个字符相邻的位置有相同字符，就可以一起消掉
 * 比如:"abbbc"，中间一串的b是可以被消掉的，消除之后剩下"ac"
 * 某些字符如果消掉了，剩下的字符认为重新靠在一起
 * 给定一个字符串，你可以决定每一步消除的顺序，目标是请尽可能多的消掉字符，返回最少的剩余字符数量
 * 比如："aacca", 如果先消掉最左侧的"aa"，那么将剩下"cca"，然后把"cc"消掉，剩下的"a"将无法再消除，返回1
 * 但是如果先消掉中间的"cc"，那么将剩下"aaa"，最后都消掉就一个字符也不剩了，返回0，这才是最优解。
 * 再比如："baaccabb"，
 * 如果先消除最左侧的两个a，剩下"bccabb"，如果再消除最左侧的两个c，剩下"babb"，最后消除最右侧的两个b，剩下"ba"无法再消除，返回2
 * 而最优策略是：
 * 如果先消除中间的两个c，剩下"baaabb"，如果再消除中间的三个a，剩下"bbb"，最后消除三个b，不留下任何字符，返回0，这才是最优解
 */
public class Code03_RemoveChar {
    public static void main(String[] args) {
        String str = "aaaa";
        System.out.println(removeChar(str));
        System.out.println(removeChar1(str));
        System.out.println(removeChar2(str));
        System.out.println(removeChar3(str));
    }

    // 暴力递归版本
    public static int removeChar(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int N = chars.length;
        return process(chars, 0, N - 1, 0);
    }

    private static int process(char[] chars, int L, int R, int K) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return K > 0 ? 0 : 1; // 指的是前K个 和 L位置相等的字符，如果没有，那就只剩下L位置这一个字符，无法消除
        }
        // 将前K个字符 跟 L位置的字符一起消除
        // 如果K=0时，则就留着L位置的字符即可
        int ans = (K > 0 ? 0 : 1) + process(chars, L + 1, R, 0);
        // 枚举剩下的所有字符
        for (int i = L + 1; i <= R; i++) {
            if (chars[i] == chars[L]) {
                if (process(chars, L + 1, i - 1, 0) == 0) { // L+1~i-1这个范围的字符能全部消除，则L和i位置的字符才能靠拢
                    ans = Math.min(ans, process(chars, i, R, K + 1));
                }
            }
        }
        return ans;
    }

    // 常规的dp版本
    public static int removeChar1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int N = chars.length;
        int[][][] dp = new int[N][N][N];
        return process1(chars, 0, N - 1, 0, dp);
    }

    private static int process1(char[] chars, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return K > 0 ? 0 : 1;
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        // 将前K个 和L位置相等的字符一起消除
        int ans = (K > 0 ? 0 : 1) + process1(chars, L + 1, R, 0, dp);
        // 枚举剩下的所有情况
        for (int i = L + 1; i <= R; i++) {
            if (chars[i] == chars[L]) {
                if (process1(chars, L + 1, i - 1, 0, dp) == 0) {
                    ans = Math.min(ans, process1(chars, i, R, K + 1, dp));
                }
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }

    // 优化常数项的递归版本
    public static int removeChar2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int N = chars.length;
        return process2(chars, 0, N - 1, 0);
    }

    private static int process2(char[] chars, int L, int R, int K) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return K > 0 ? 0 : 1;
        }
        int index = L; // 新的L位置。也就是“压缩”跟L位置相同字符的一块连续的区域
        while (index + 1 <= R && chars[index + 1] == chars[L]) {
            index++;
        }
        int pre = K + index - L;
        int ans = (pre > 0 ? 0 : 1) + process2(chars, index + 1, R, 0);
        // 枚举剩下的字符
        for (int i = index + 1; i <= R; i++) {
            if (chars[i] == chars[L] && chars[i] != chars[i - 1]) {
                if (process2(chars, index + 1, i - 1, 0) == 0) { // 二者中间的所有字符都能消除，前后才能靠拢
                    ans = Math.min(ans, process2(chars, i, R, pre + 1));
                }
            }
        }
        return ans;
    }

    // 优化常数项的记忆化搜索版本----最优解
    public static int removeChar3(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int N = chars.length;
        int[][][] dp = new int[N][N][N];
        return process3(chars, 0, N - 1, 0, dp);
    }

    private static int process3(char[] chars, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return K > 0 ? 0 : 1; // 将前K个 和L位置相同的字符一起消除
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        int index = L;
        while (index + 1 <= R && chars[index + 1] == chars[L]) {
            index++;
        }
        int pre = K + index - L;
        int ans = (pre > 0? 0 : 1) + process3(chars, index + 1, R, 0, dp);
        // 枚举剩下的所有字符
        for (int i = index + 1; i <= R; i++) {
            if (chars[i] == chars[L] && chars[i] != chars[i - 1]) { // 找到后续跟L位置相同，且还是那一块区域的第一个字符
                if (process3(chars, index + 1, i - 1, 0, dp) == 0) {
                    ans = Math.min(ans, process3(chars, i, R, pre + 1, dp));
                }
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }
}
