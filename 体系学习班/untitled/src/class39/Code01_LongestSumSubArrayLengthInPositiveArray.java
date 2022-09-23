package class39;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:24
 * Description:
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K，找到arr的所有子数组里，哪个子数组的累加和等于K
 * 并且是长度最大的，返回其长度。
 *
 * 总结：滑动窗口
 */
public class Code01_LongestSumSubArrayLengthInPositiveArray {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 2, 1, 1, 1, 1,  1, 5};
        System.out.println(getLongestSubArrayLength(arr, 6));
    }

    // 滑动窗口的经典题目。数组本身就是正整数的数据，具有单调性。
    public static int getLongestSubArrayLength(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0) {
            return 0;
        }
        int L = 0;
        int R = 0; // 左闭右开区间
        int len = 0;
        int sum = 0;
        while (R < arr.length) {
            if (sum == k) {
                len = Math.max(len, R - L);
                sum += arr[R++]; // 在这里++，为的是防止数组中存在有0的情况，为了不错误这种情况
            } else if (sum > k) {
                sum -= arr[L++];
            } else { // sum < k
                sum += arr[R++];
            }
        }
        return len;
    }
}
