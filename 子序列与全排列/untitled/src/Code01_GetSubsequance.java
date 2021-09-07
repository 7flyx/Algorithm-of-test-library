import java.util.ArrayList;

/**
 * Created by flyx
 * Description:打印一个字符串的全部子序列，包括空字符串。
 * User: 听风
 * Date: 2021-09-07
 * Time: 10:37
 */
public class Code01_GetSubsequance {

    public static void main(String[] args) {
        printAllSubsquence("abc");
        System.out.println("=======");
        ArrayList<String> list = new ArrayList<>();
        getSubsequence2("abc".toCharArray(), 0, list, new StringBuilder());
        for (String str : list) {
            System.out.println(str);
        }
        int[] nums = new int[9];

    }

    public static void printAllSubsquence(String str) {
        char[] chs = str.toCharArray();
        getSubsequence1(chs, 0);
    }

    public static void getSubsequence1(char[] chs, int i) {
        if (i == chs.length) {
            System.out.println(String.valueOf(chs));
            return;
        }
        getSubsequence1(chs, i + 1);
        char tmp = chs[i];
        chs[i] = '\0'; // \0 作为字符串的结束标志
        getSubsequence1(chs, i + 1);
        chs[i] = tmp;
    }

    //方法二
    //res  是本次循环的一次结果，最后将这个结果放入list里面
    public static void getSubsequence2(char[] str, int i, ArrayList<String> list, StringBuilder res) {
        if (i == str.length) {
            list.add(res.toString());
            return;
        }

        res.append(str[i]); //要当前字符
        getSubsequence2(str, i + 1, list, res);

        res.delete(res.length() - 1, res.length()); //不要当前字符
        getSubsequence2(str, i + 1, list, res);

    }

}


