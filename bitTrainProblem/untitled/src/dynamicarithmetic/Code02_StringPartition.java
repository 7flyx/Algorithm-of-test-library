package dynamicarithmetic;

import java.util.Set;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:14
 * Description: 字符串分割
 * 给定一个字符串s和一组单词dict，判断s是否可以用空格分割成一个单词序列，
 * 使得单词序列中所有的单词都是dict中的单词（序列可以包含一个或多个单词）。
 * 例如:
 * 给定s=“nowcode”；
 * dict=["now", "code"].
 * 返回true，因为"nowcode"可以被分割成"now code".
 * https://www.nowcoder.com/practice/5f3b7bf611764c8ba7868f3ed40d6b2c?tpId=46&tqId=29041&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 */
public class Code02_StringPartition {
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || dict== null) return false;

        //像这种，会以子串为单位的，一般都可以以当前位置的字符结尾，
        //会怎么怎么样
        int N = s.length();
        boolean[] dp = new boolean[N + 1];
        dp[0] = true;
        for (int i= 1; i <= N; i++) {
            //从i位置，向前的字符串，只要有一个满足条件就行，枚举前面的所有字符
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[N];
    }
}
