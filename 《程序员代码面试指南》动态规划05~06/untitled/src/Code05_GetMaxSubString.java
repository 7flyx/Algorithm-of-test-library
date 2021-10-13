import java.io.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-13
 * Time: 18:11
 * Description: 给定两个子串，输出最长公共子串。如果子串为空，则返回-1
 */

public class Code05_GetMaxSubString {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str1 = in.readLine();
        String str2 = in.readLine();

        System.out.println(lcst2(str1, str2));
        in.close();
    }

    //方法2，空间复杂的O(1)。不计算dp表，直接带范围运算
    public static String lcst2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return "";
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int row = 0;
        int col = str2.length() - 1;
        int max = 0; //最大值
        int end = -1; //更新最大值的下标
        while (row < s1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < s1.length && j < s2.length) {
                if (s1[i] == s2[j]) {
                    len++;
                } else {
                    len = 0;
                }
                if (len > max) { //更新max
                    max = len;
                    end = i;
                }
                i++;
                j++;
            }

            //实则就是先将str2，从后面开始一个个比较。一直到头
            //然后才是从str1，从头到尾开始比较。每次比较的都是一条斜线
            //也即是对应dp表的斜线
            if (col > 0) { //先移动列
                col--;
            } else { //再移动行
                row++;
            }
        }
        if (end == -1) return "-1";
        return str1.substring(end - max + 1, end + 1);
    }

    //方法一，空间复杂度（N*M）
    public static void lcst1(String str1, String str2) {
        int[][] dp = getdp(str1.toCharArray(), str2.toCharArray());
        int max = 0; //最大长度
        int end = -1; //下标值
        for(int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (dp[i][j] > max) {
                    max = dp[i][j];
                    end = i;
                }
            }
        }
        if (end != -1) {
            System.out.println(str1.substring(end - max + 1, end + 1)); //左闭右开区间的值
        } else {
            System.out.println(-1);
        }
    }

    public static int[][] getdp(char[] str1, char[] str2) {
        if (str1 == null || str2 == null || str1.length == 0 || str2.length == 0) {
            return new int[1][];
        }

        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0]? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = str1[i] == str2[0]? 1 : 0;
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = str1[0] == str2[j]? 1 : 0;
        }

        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                if (str1[i] == str2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }
}