package class16;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-05
 * Time: 16:51
 * Description: 打印一个字符串的全排列字符串
 */
public class Code03_SubStr {
    public static void main(String[] args) {
        String str = "acc";
        List<String> ans = penetration1(str);
        List<String> ans2 = penetration2(str);
        System.out.println(ans);
        System.out.println(ans2);
    }

    // 全排列-没去重
    public static List<String> penetration1(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> ans = new ArrayList<>();
        process1(str.toCharArray(), 0, ans);
        return ans;
    }

    public static void process1(char[] chars, int index, List<String> ans) {
        if (index == chars.length) {
            ans.add(new String(chars));
            return;
        }
        // 从当前位置开始，跟后续的字符进行交换，得到一个状态，往后续传递
        for (int i = index; i < chars.length; i++) {
            swap(chars, i, index); // 跟后续的字符交换位置
            process1(chars, index + 1, ans);
            swap(chars, i, index); // 恢复现场
        }
    }

    // 全排列-去重版本
    public static List<String> penetration2(String str) {
        if (str == null) {
            return new ArrayList<>();
        }
        List<String> ans = new ArrayList<>();
        process2(str.toCharArray(), 0, ans);
        return ans;
    }

    public static void process2(char[] chars, int index, List<String> ans) {
        if (index == chars.length) {
            ans.add(new String(chars));
            return;
        }
        boolean[] visited = new boolean[256]; // 标记当前位置已经遍历过某个字符的情况，所谓的剪枝
        // 从当前位置开始，跟后续的字符进行交换，得到一个状态，往后续传递
        for (int i = index; i < chars.length; i++) {
            if (!visited[chars[i]]) {
                visited[chars[i]] = true;
                swap(chars, i, index); // 跟后续的字符交换位置
                process2(chars, index + 1, ans);
                swap(chars, i, index); // 恢复现场
            }
        }
    }

    private static void swap(char[] chars, int i, int index) {
        char tmp = chars[i];
        chars[i] = chars[index];
        chars[index] = tmp;
    }
}
