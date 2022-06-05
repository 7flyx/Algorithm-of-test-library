package class16;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-05
 * Time: 16:50
 * Description: 子序列
 */
public class Code02_SubOrderStr {
    public static void main(String[] args) {
        String str = "abc";
        System.out.println(subOrderStr(str));
    }

    // 子序列-不去重版本。去重的话，存储结果时用set即可
    public static List<String> subOrderStr(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> ans = new ArrayList<>();
        process(str.toCharArray(), 0, "", ans);
        return ans;
    }

    private static void process(char[] chars, int index, String path, List<String> ans) {
        if (index == chars.length) {
            ans.add(path);
            return;
        }
        // 不要当前的字符
        process(chars, index + 1, path, ans);
        // 要了当前的字符
        process(chars, index + 1, path + chars[index], ans);
    }
}
