import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-21
 * Time: 18:37
 * Description: 字符串交错组合
 */

public class Code08_StrCross {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        String aim = sc.nextLine();

        boolean res = isCross2(str1, str2, aim);
        System.out.println(res? "YES" : "NO");
    }

    public static boolean isCross1(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) {
            return false;
        }

        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();

        if (ch1.length + ch2.length != chaim.length) {
            return false;
        }

        //以ch1作为行，以ch2作为列
        //dp[i][j] 表示，chaim[0 ~ i + j - 1] 的字符串，能否被ch1和ch2组合而成
        boolean[][] dp = new boolean[ch1.length + 1][ch2.length + 1];
        dp[0][0] = true;

        //分别填写行和列的数据
        for (int i = 1; i < dp.length; i++) {
            if (ch1[i - 1] != chaim[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }

        for (int j = 1; j < dp[0].length; j++) {
            if (ch2[j - 1] != chaim[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }

        //填写普通位置
        //dp[i][j] 表示chaim[0 ~ i + j - 1] 的字符串
        //首先判断chaim[0 ~ i + j - 2] 是否相等。分为两种情况
        //（i, j - 1） 或者是(i - 1, j)
        //然后才是判断当前来到的字符是否相等。实则就是判断第i字符和i+j的字符是否相等
        //又因为是从0下标开始的，所以两边都需要减1
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if ((ch1[i - 1] == chaim[i + j - 1] && dp[i - 1][j]) ||
                        (ch2[j - 1] == chaim[i + j - 1] && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[ch1.length][ch2.length];
    }

    //空间压缩
    public static boolean isCross2(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) {
            return false;
        }

        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        char[] chaim = aim.toCharArray();
        if (ch1.length != aim.length() - ch2.length) {
            return false;
        }

        //以短的字符串作为列，长的作为行
        char[] longs = ch1.length > ch2.length? ch1 : ch2;
        char[] shorts = longs == ch1? ch2 : ch1;

        boolean[] dp = new boolean[shorts.length + 1];
        dp[0] = true; //空串的时候
        for (int j = 1; j < dp.length; j++) {
            if (shorts[j - 1] != chaim[j - 1]) {
                break;
            }
            dp[j] = true;
        }

        //填写普通位置
        for (int i = 1; i <= longs.length; i++) {
            dp[0] = dp[0] && longs[i - 1] == chaim[i - 1];
            for (int j = 1; j <= shorts.length; j++) {
                if ((longs[i - 1] == chaim[i + j - 1] && dp[j]) ||
                        (shorts[j - 1] == chaim[i + j - 1] && dp[j - 1])) {
                    dp[j] = true;
                } else {
                    dp[j] = false;
                }
            }
        }
        return dp[shorts.length];
    }
}

