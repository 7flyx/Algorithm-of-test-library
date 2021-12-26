package dynamicarithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:23
 * Description:回文串分割
 * https://www.nowcoder.com/practice/1025ffc2939547e39e8a38a955de1dd3?tpId=46&tqId=29048&tPage=1&rp=1&ru=/ta/leetcode&qru=/ta/leetcode/question-ranking
 */
public class Code07_PalindromeStrPartition {
    public static void main(String[] args) {
        String str = "aab";
        System.out.println(minCut(str));
    }

    public static int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        char[] ch = s.toCharArray();
        int[] dp = new int[ch.length + 1];
        //用一张表存储字符在前面出现过的所有位置
        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int i = 1; i <= ch.length; i++) {
            if (!map.containsKey(ch[i - 1])) { //如果前面没有出现过这个字符
                dp[i] = dp[i - 1] + 1;
                List<Integer> list = new ArrayList<>();
                list.add(i - 1);
                map.put(ch[i - 1], list);
            } else {//如果前面出现过这个字符
                List<Integer> list = map.get(ch[i - 1]);
                int size = list.size();
                dp[i] = dp[i - 1] + 1;
                for (int j = 0; j < size; j++) {//遍历前面出现过的每一个字符，进行判断，是不是最优的解法
                    int preIndex = list.get(j);
                    if (sure(ch, preIndex + 1, i - 2)) {//判断是不是回文串
                        dp[i] = Math.min(dp[i], dp[preIndex] + 1);
                    }
                }
                list.add(i - 1); //将当前字符的位置放入表中
            }
        }
        return dp[ch.length] - 1;
    }

    private static boolean sure(char[] ch, int l, int r) {
        while (l <= r) {
            if (ch[l] != ch[r]) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

}
