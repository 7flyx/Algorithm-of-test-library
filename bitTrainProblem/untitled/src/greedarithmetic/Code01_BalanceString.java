package greedarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:28
 * Description:平衡字符串
 * https://leetcode-cn.com/problems/split-a-string-in-balanced-strings/
 */
public class Code01_BalanceString {
    public int balancedStringSplit(String s) {
        if (s == null || !isVaild(s)) {
            return 0;
        }
        int res = 0;
        int count = 0; //L++， R--
        int N = s.length();
        for (int i = 0; i <N; i++) {
            if (s.charAt(i) == 'L') {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                res++;
            }
        }
        return res;
    }

    private boolean isVaild(String s) {
        int N = s.length();
        char tmp = '0';
        for (int i = 0; i < N; i++) {
            tmp = s.charAt(i);
            if (tmp != 'L' && tmp != 'R') {
                return false;
            }
        }
        return true;
    }
}
