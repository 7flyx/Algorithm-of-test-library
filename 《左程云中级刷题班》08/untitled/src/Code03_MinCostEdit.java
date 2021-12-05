import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-04
 * Time: 16:42
 * Description:
 * 给定两个字符串str1和str2， 再给定三个整数ic、 dc和rc， 分别代表插入、 删
 * 除和替换一个字符的代价， 返回将str1编辑成str2的最小代价。
 * 【 举例】
 * str1="abc"， str2="adc"， ic=5， dc=3， rc=2
 * 从"abc"编辑成"adc"， 把'b'替换成'd'是代价最小的， 所以返回2
 * str1="abc"， str2="adc"， ic=5， dc=3， rc=100
 * 从"abc"编辑成"adc"， 先删除'b'， 然后插入'd'是代价最小的， 所以返回8
 * str1="abc"， str2="abc"， ic=5， dc=3， rc=2
 * 不用编辑了， 本来就是一样的字符串， 所以返回0
 */
public class Code03_MinCostEdit {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "adc";
        int ic = 5;
        int dc = 3;
        int rc = 100;
        System.out.println(minCostEdit1(str1, str2, ic, dc, rc));
        System.out.println(minCostEdit2(str1, str2, ic, dc, rc));
    }

    /**
     * 经典的dp
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @param ic   插入代价
     * @param dc   删除代价
     * @param rc   替换代价
     * @return 返回最小编辑代价
     */
    public static int minCostEdit1(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null || str1.equals(str2)) {
            return 0;
        }

        int N = str1.length();
        int M = str2.length();
        //横向 第一个是空串，纵向第一个也是空串
        //纵向是str1，横向是str2.从str1转换str2
        int[][] dp = new int[N + 1][M + 1]; //多开辟一个位置，用作空串
        for (int i = 1; i <= N; i++) {
            dp[i][0] = dc * i; //第一列，都是删除的代价。从一个字符串，删除为空串
        }
        for (int j = 1; j <= M; j++) {
            dp[0][j] = ic * j; //第一行，都是插入的代价。从一个空串开始插入
        }

        //填写普遍位置
        for (int i = 1; i <= N; i++) { //行
            for (int j = 1; j <= N; j++) { //列
                /*
                    几种情况：str1转换为str2.
                    dp[i][j]表示 从str1（0……i）转换为str2（0……j）的代价
                    1 以下两行就是str（0……i-1）转化为str2（j）的代价，再加上dc的代价
                    2 str1（0……i）转换为str2（0……j-1）的代价，再加上ic的代价
                    3. 当前位置str1[i] = str2[j] 直接拿左上角的值【i-1】【j-1】
                    4. 当前位置的字符不相等，加上替换字符的代价
                 */
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;//替换字符的代价
                }
                //dp[i][j]表示 从str1（0……i）转换为str2（0……j）的代价
                //以下两行就是str（0……i-1）转化为str2（j）的代价，再加上dc的代价
                //str1（0……i）转换为str2（0……j-1）的代价，再加上ic的代价
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
            }
        }
        return dp[N][M]; //返回右下角的值
    }

    /**
     * 空间压缩，从上到下更新的，依赖于左边、上边和左上角的值。
     * 所以只需建立一个数组，从上到下，从左到右，滚动刷新即可
     */
    public static int minCostEdit2(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        int N = str1.length();
        int M = str2.length();
        int[] dp = new int[M + 1]; //以str2作为横向
        //dp[0] = 0; //空串的编辑代价
        for (int j = 1; j <= M; j++) {
            dp[j] = j * ic;
        }

        for (int i = 1; i <= N; i++) {
            int leftUp = dp[0]; //左上角的代价【i-1】【j-1】
            int up = 0; //上方的代价
            dp[0] = i * dc; //第一列的删除代价
            for (int j = 1; j <= M; j++) { //dp[j]没更新之前，就是dp[i-1][j]
                up = dp[j]; //上方的值
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[j] = leftUp;
                } else {
                    dp[j] = leftUp + rc; //加上替换的代价
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + dc);
                dp[j] = Math.min(dp[j], up + ic);
                leftUp = up; //进入下一列，提前更新左上角的代价
            }
        }
        return dp[M];
    }
}
