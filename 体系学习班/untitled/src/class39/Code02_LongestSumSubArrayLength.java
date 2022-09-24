package class39;

import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:26
 * Description:
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的，返回其长度。
 *
 * 总结：此时累加和并不具备单调性，滑动窗口不能用。看到关于子数组的题目，可往 以 开始（结尾）处为基础，往前推导或者往后推导过程。
 */
public class Code02_LongestSumSubArrayLength {
    public static void main(String[] args) {
        int[] arr = {100, 20, -10, -20, 60, 50, 40};
        int k = 90;
        System.out.println(getLongestSubArrayLength(arr, k));
    }

    public static int getLongestSubArrayLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 思路；以某个位置结尾，往前推导。运用前缀和数组进行推导。
        int sum = 0;
        int len = 0;
        HashMap<Integer, Integer> preMap = new HashMap<>(); // 存储前缀和第一次出现的位置
        preMap.put(0, -1); // base case。假设当前前缀和就等于k。此时就需要当前这个参数
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (preMap.containsKey(sum - k)) { // 往前找 “差值”
                len = Math.max(len, i - preMap.get(sum - k));
            }
            if(!preMap.containsKey(sum)) { // 只记录最先出现结果的前缀和
                preMap.put(sum, i);
            }
        }
        return len;
    }
}
