package class13;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-11
 * Time: 16:31
 * Description: 贪心算法系列
 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中字典序最小的结果。
 */
public class Code01_StringArraySort {
    public static void main(String[] args) {

    }

    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            // 二者拼接字符串，谁最小，谁放在前面
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    public static String getSmallestDictOrder(String[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        Arrays.sort(arr, new StringComparator());
        // 此时数组中的所有字符串，都来到了它应该来到的位置，此时只需要拼接上所有的字符串即可
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }
}
