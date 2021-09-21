/**
 * Created by flyx
 * Description: Manacher算法，得到一个字符串的最长回文子串。有一个回文半径数组
 * User: 听风
 * Date: 2021-09-21
 * Time: 17:21
 */
public class Manacher {

    public static void main(String[] args) {
        String str = "aba123321aaa";
        System.out.println(manacher(str));
    }

    public static int manacher(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] s = manacherString(str); //得到处理后的字符串
        int[] pArr= new int[s.length]; //回文半径数组
        int R = -1; //当前回文串的右边界。即R-1位置，为有效字符范围
        int C = -1; //当前回文串的中心点
        int max = 0;
        for (int i = 0; i < s.length; i++) {
            //如果i在R的外面，当前最长回文串就是本身1。如果i在R范围内，则分为3种小情况。压线、在LR范围内、有一边在L外。这三种情况
            pArr[i] = i < R? Math.min(R - i, pArr[2 * C - i]) : 1;

            //以下循环，包括了分析中的四种情况。减少了代码量。但时间复杂度没变
            while (i + pArr[i] < s.length && i - pArr[i] > -1) {
                if (s[i + pArr[i]] == s[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            //新扩展的回文子串，超过的原来R的值。更新
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            max = Math.max(max, pArr[i]); //判断当前更新的回文半径值，是不是最大的
        }

        return max - 1; //最终的回文半径值，与原字符串的回文长度，相差1
    }

    //将原字符串121处理为 #1#2#1#
    private static char[] manacherString(String str) {
        if (str == null) {
            return new char[] {};
        }

        char[] s = str.toCharArray();
        char[] res = new char[2 * str.length() + 1]; //2倍长，多一个
        int index = 0; //指向s数组的下标
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 1? s[index++] : '#'; //偶数位置放‘#’，奇数位置放原字符
        }
        return res;
    }

}
