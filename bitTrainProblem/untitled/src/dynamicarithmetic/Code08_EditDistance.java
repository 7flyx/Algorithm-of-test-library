package dynamicarithmetic;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:25
 * Description:编辑距离
 * https://www.nowcoder.com/practice/81d7738f954242e5ade5e65ec40e5027?tpId=46&tqId=29106&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 */
public class Code08_EditDistance {


    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }
        int row = word1.length();
        int col = word2.length();
        //横向纵向第一个位置都是空串
        int[][] dp = new int[row + 1][col + 1];
        for (int i = 1; i <= row; i++) {
            dp[i][0] = i; //操作次数
        }
        for (int i = 1; i <= col; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {//当前字符相等
                    dp[i][j] = dp[i - 1][j - 1];
                } else {//当前字符不相等。删除-插入-替换，三者代价取最小
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[row][col];
    }

}
