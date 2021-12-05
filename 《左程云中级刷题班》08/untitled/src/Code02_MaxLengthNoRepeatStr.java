import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-04
 * Time: 16:42
 * Description:
 * 在一个字符串中找到没有重复字符子串中最长的长度。
 * 例如：
 * abcabcbb没有重复字符的最长子串是abc， 长度为3
 * bbbbb， 答案是b， 长度为1
 * pwwkew， 答案是wke， 长度是3
 * 要求： 答案必须是子串， "pwke" 是一个子字符序列但不是一个子字符串。
 */
public class Code02_MaxLengthNoRepeatStr {
    public static void main(String[] args) {
        String str = "pwwkew";
        System.out.println(getNoRepeatSubStr(str));
    }

    public static String getNoRepeatSubStr(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        //只要提到关于子串的问题，大概率就是“以当前字符结尾，会怎么样……”
        int N = str.length();
        char[] ch = str.toCharArray();
        int[] dp = new int[N];
        HashMap<Character, Integer> map = new HashMap<>(); //记录上一次出现这个字符的位置
        map.put(ch[0], 0);
        dp[0] = 1;
        int maxIndex = 0; //目前最长子串的下标值
        for (int i = 1; i < N; i++) {
            if (!map.containsKey(ch[i])) {
                dp[i] = 1 + dp[i - 1]; //暂时还没出现当前字符的位置，就是上一个字符的dp+1
                map.put(ch[i], i);
            } else {
                //前面已经出现过这个字符，则需要和i - 1-【i-1】的值比较
                //i-1-dp[i-1]的值，就是以上一个字符结尾的子串，有多长。再和ch[i]上次出现的位置进行比较
                //二者取离i位置最近的那个，就是瓶颈
                dp[i] = i - Math.max( i - 1 - dp[i - 1], map.get(ch[i]));
                map.put(ch[i], i); //更新当前字符的下标
            }
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i; //更新最长子串的下标值
            }
        }
        System.out.println(Arrays.toString(dp));
        return str.substring(maxIndex - dp[maxIndex] + 1, maxIndex + 1);
    }

}
