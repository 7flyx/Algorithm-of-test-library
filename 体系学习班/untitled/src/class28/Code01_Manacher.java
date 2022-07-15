package class28;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-15
 * Time: 16:16
 * Description: manacher 算法，最长回文子串问题
 * https://www.nowcoder.com/practice/c1408adc44294f88a795144e50c23e7c
 */
public class Code01_Manacher {
    public static void main(String[] args) {
        String str = "abcd12321eee";
        System.out.println(manacher(str));
    }

    public static int manacher(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = processStr(str);
        int N = chars.length;
        int R = -1; // 右边界，左闭右开区间
        int C = -1; // 中心点
        int max = 0;
        int[] pArr = new int[N]; // 回文半径数组
        for (int i = 0; i < N; i++) {
            // 首先根据对称，拿到C点左侧相应的回文半径
            pArr[i] = i < R? Math.min(pArr[2 * C - i], R - i) : 1;
            // 根据已经计算出来的初始半径，此时再向两边扩展
            while (i - pArr[i] >= 0 && i + pArr[i] < N) {
                if (chars[i - pArr[i]] == chars[i + pArr[i]]) {
                    pArr[i]++; // 回文半径增加
                } else { // 不相等，直接跳出循环
                    break;
                }
            }
            // 更新R，C和max
            if (i + pArr[i] > R) {
                R = i + pArr[i]; // 右边界
                C = i; // 以当前点作为新的中心点
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    // 每个字符之间添加#，用于间隔
    private static char[] processStr(String str) {
        int N = str.length();
        char[] res = new char[2 * N + 1];
        for (int i = 0; i < res.length; i += 2) {
            res[i] = '#';
        }
        int index = 0;
        for (int i = 1; i < res.length; i+= 2) {
            res[i] = str.charAt(index++);
        }
        return res;
    }
}
