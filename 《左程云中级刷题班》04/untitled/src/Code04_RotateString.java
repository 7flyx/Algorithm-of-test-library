
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-14
 * Time: 14:44
 * Description:
 * 如果一个字符串为str， 把字符串str前面任意的部分挪到后面形成的字符串叫
 * 作str的旋转词。 比如str="12345"， str的旋转词有"12345"、 "23451"、
 * "34512"、 "45123"和"51234"。 给定两个字符串a和b， 请判断a和b是否互为旋转
 * 词。
 * 比如：
 * a="cdab"， b="abcd"， 返回true。
 * a="1ab2"， b="ab12"， 返回false。
 * a="2ab1"， b="ab12"， 返回true。
 */
public class Code04_RotateString {
    public static void main(String[] args) {
        String s = "cdab";
        String b = "abcd";
        System.out.println(isRotateString(s, b));

    }

    public static boolean isRotateString(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }

        //将a字符串进行拼接，然后用KMP解题即可
        StringBuilder sb = new StringBuilder(a);
        sb.append(a);
        String tmp = sb.toString();
        return indexOf(tmp, b) != -1;
    }

    private static int indexOf(String str, String sub) {
        if (str == null || sub == null) {
            return -1;
        }

        int[] next = getNext(sub);
        int N = str.length();
        int M = sub.length();
        int i = 0;
        int j = 0;
        while (i < N && j < M) {
            if (str.charAt(i) == sub.charAt(j)) {
                i++;
                j++;
            } else if (j > 0) {
                j = next[j];
            } else {
                i++;
            }
        }
        return j == M? i - j : -1;
    }

    private static int[] getNext(String sub) {
        int n = sub.length();
//        if (n < 3) {
//            //return new int[] {-1, 0};
//        }
        int[] next = new int[n];
        char[] ch = sub.toCharArray();
        next[0] = -1;
        next[1] = 0;
        int cn = 0; //前缀字符串
        int index = 2; //从第三个字符开始
        while (index < n) {
            if (ch[index - 1] == ch[cn]) {
                next[index++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[index++] = 0;
            }
        }
        return next;
    }
}
