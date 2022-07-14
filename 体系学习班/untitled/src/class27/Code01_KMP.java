package class27;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-13
 * Time: 20:37
 * Description: KMP算法。
 */
public class Code01_KMP {
    public static void main(String[] args) {
        String s1 = "ababab";
        String s2 = "abababab";
        System.out.println(indexOf(s1, s2));
        System.out.println(kmp(s1, s2));
    }

    // s2模式串，s1是主串，在s1里面找s2
    public static int indexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0) {
            return -1;
        }
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int[] next = getNextArray(ch2);
        int index1 = 0; // 指向ch1
        int index2 = 0; // 指向 ch2
        while (index1 < ch1.length && index2 < ch2.length) {
            if (ch1[index1] == ch2[index2]) {
                index1++;
                index2++;
            } else if (index2 > 0) { // index2还能往前跳转的时候
                index2 = next[index2]; // index2往前跳
            } else { // 当前位置，既不相等，index2也不能往前跳了，说明index1位置出发行不通，index1后移
                index1++;
            }
        }
        return index2 == ch2.length ? index1 - index2 : -1;
    }

    private static int[] getNextArray(char[] s2) {
        if (s2.length == 1) {
            return new int[]{-1};
        }
        // 找的其实就是前后缀字符串
        int[] res = new int[s2.length];
        res[0] = -1;
        res[1] = 0;
        int i = 2; // 从第3个字符开始判断
        int cn = 0;
        while (i < s2.length) {
            if (s2[cn] == s2[i - 1]) { // 切记这里其实是i的前一个位置
                res[i++] = ++cn;
            } else if (cn > 0) {
                cn = res[cn];
            } else {
                res[i++] = 0;
            }
        }
        return res;
    }

    // 牛客网测试 https://www.nowcoder.com/practice/bb1615c381cc4237919d1aa448083bcc
    public static int kmp(String m, String s) {
        // write code here
        if (s == null || m == null) {
            return -1;
        }
        int[] next = getNextArr(m);
        char[] str1 = s.toCharArray(); //主串
        char[] str2 = m.toCharArray(); //子串
        int i = 0; //指向主串
        int j = 0; //指向子串
        int res = 0;
        while (i < s.length()) {
            if (str1[i] == str2[j]) {
                i++;
                j++;
            } else if (j == 0) { //j来到子串的最前面了，说明此时主串的位置不匹配，就往后走
                i++;
            } else {
                j = next[j];
            }

            if (j == m.length()) {
                res++;
                //i = i - next[j - 1]; //回溯当前next域的值，即就是当前位置前后缀字符串长度
                j = next[j - 1];
                i--; //i本身走过了子串的长度，回溯一个位置，跟新的j位置比较
            }
        }
        return res; //判断子串是否已经走完了
    }

    private static int[] getNextArr(String S) {
        if (S.length() < 2) {
            return new int[]{-1};
        }
        if (S.length() < 3) {
            return new int[]{-1, 0};
        }

        char[] str = S.toCharArray();
        int[] next = new int[S.length()];
        next[0] = -1;
        next[1] = 0;
        int i = 2; //从第三个字符开始
        int cn = 0; //当前i字符，前一个字符的next域值
        while (i < S.length()) {
            if (str[i - 1] == str[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0; //没匹配到前后缀字符串
            }
        }
        return next;
    }

}
