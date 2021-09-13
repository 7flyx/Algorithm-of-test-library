/**
 * Created by flyx
 * Description: KMP算法实现; 给你一个文本串S，一个非空模板串T，问S在T中出现了多少次
 * User: 听风
 * Date: 2021-09-13
 * Time: 11:04
 */
public class KMP {

    public static void main(String[] args) {
        String s = "abababab"; //主串
        String m = "babab"; //子串

        //System.out.println(BF(s, m));
        System.out.println(getIndexOf(s, m));

    }


    public static int BF(String s, String m) {
        if (s == null || m == null) {
            return -1;
        }

        char[] str1 = s.toCharArray(); //主串
        char[] str2 = m.toCharArray(); //子串
        int i = 0;
        int j = 0;
        while (i < s.length() && j < m.length()) {
            if (str1[i] == str2[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1; //回溯到起点的下一个位置
                j = 0;
            }
        }
        return j == m.length()? i - j : -1;
    }

    //KMP算法
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null ) {
            return -1;
        }
        int[] next = getNextArr( m);
        char[] str1 = s.toCharArray(); //主串
        char[] str2 = m.toCharArray(); //子串
        int i = 0; //指向主串
        int j = 0; //指向子串
        while (i < s.length() && j < m.length()) {
            if (str1[i] == str2[j]) {
                i++;
                j++;
            } else if (j == 0) { //j来到子串的最前面了，说明此时主串的位置不匹配，就往后走
                i++;
            } else {
                j = next[j];
            }
        }
        return j == m.length()? i - j : -1; //判断子串是否已经走完了
    }

    private static int[] getNextArr( String m) {
        if ( m.length() < 2) {
            return new int[] {-1};
        }
        if (m.length() < 3) {
            return new int[] {-1, 0};
        }

        int[] res = new int[m.length()];
        char[] str = m.toCharArray();
        res[0] = -1;
        res[1] = 0;
        int i = 2; //从第三个字符开始
        int cn = 0; //表示当前字符的前一个字符，next域的值
        while (i < m.length()) {
            if (str[i - 1] == str[cn]) {
                res[i++] = ++cn;
            } else if (cn > 0) { //cn表示前后缀字符串匹配的长度
                cn = res[cn];
            } else {
                res[i++] = 0; //没找到前后缀匹配的字符串，就为0
            }
        }
        return res;
    }
}
