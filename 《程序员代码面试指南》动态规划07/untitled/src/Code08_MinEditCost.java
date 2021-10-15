import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-15
 * Time: 16:06
 * Description:
 */

public class Code08_MinEditCost {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        int ic = sc.nextInt(); //插入代价
        int dc = sc.nextInt();//删除代价
        int rc = sc.nextInt(); //替换代价

        System.out.println(minCost3(str1, str2, ic, dc, rc));
    }

    public static int minCost(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int N = s1.length;
        int M = s2.length;
        int[][] dp = new int[N + 1][M + 1];

        //行是str1，列是str2。是改变str1的状态变为str2
        //行是删除，列是插入
        for (int i = 1; i <= N; i++) {
            dp[i][0] = dc * i;
        }
        for (int i = 1; i <= M; i++) {
            dp[0][i] = ic * i;
        }

        //普通位置，推导依赖关系
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //当前字符不相等，则计算左上角字符的值，以及加上当前位置的替换代价
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }

                //除了相等与不相等的情况，还有就是计算上一行的值和左移一列的值
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
            }
        }

        return dp[N][M];
    }

    public static int minCost3(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        char[] longs = s2.length >= s1.length? s2 : s1;
        char[] shorts = longs == s1? s2 : s1;

        if (s1.length < s2.length) { //如果此时longs不是s1字符串，就交换插入和删除的代价
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }

        int[] dp = new int[shorts.length + 1]; //以列数作为长度进行空间压缩
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = i * ic;
        }

        int up = 0; //当前字符的上边的值
        int leftUp = 0; //当前字符的左上角的值

        //推导普通位置，依赖关系
        for (int i = 1; i <= longs.length; i++) { //指向行数，指向str1
            leftUp = dp[0];
            dp[0] = i * dc; //更新新的一行的值
            for (int j = 1; j <= shorts.length; j++) {
                up = dp[j]; //dp[i - 1][j]
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = leftUp; //左上角的值
                } else {
                    dp[j] = leftUp + rc; //左上角的值加上替换代价
                }

                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], up + dc); //上一行同位置的值即就是up
                leftUp = up; //更新左上角的值
            }
        }
        return dp[shorts.length];
    }
}
