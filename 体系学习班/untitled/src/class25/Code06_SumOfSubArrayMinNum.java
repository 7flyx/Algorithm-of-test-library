package class25;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-11
 * Time: 22:04
 * Description: LeetCode907题 子数组的最小值之和
 * https://leetcode.cn/problems/sum-of-subarray-minimums/
 * 给定一个数组arr，返回所有子数组最小值的累加和
 * <p>
 * 分析：
 * 以i位置的数作为当前子数组的最小值，往左右两边扩展，找bi当前位置小的数。
 * 比如     6    9    7
 * 下标：    3 ...8...10
 * 如上，则下标4~9范围内，都是可以把8位置的9作为子数组的最小值。有如下子数组：
 * 下标：4~8,4~9, 5~8,5~9, 6~8,6~9, 7~8,7~9, 8~8,8~9
 * 也就是下标8左侧的个数 * 下标8右侧的个数，就是以8位置作为最小值的全部子数组。
 * 即(8 - 3) * (10 - 8) = 10
 * 以上只能处理没有重复值的情况，如果有重复值的情况，需要再稍微改一下，比如多个子数组被重复计算
 */
public class Code06_SumOfSubArrayMinNum {
    public static void main(String[] args) {
        int[] arr = {71,55,82,55};
        System.out.println("暴力");
        System.out.println("left:" + Arrays.toString(getNumLeftLessArr1(arr)));
        System.out.println("right:" + Arrays.toString(getNumRightLargerArr1(arr)));
        System.out.println("单调栈");
        System.out.println("left:" + Arrays.toString(getNumLeftLessArr2(arr)));
        System.out.println("right:" + Arrays.toString(getNumRightLargerArr2(arr)));
    }

    // 终极版本，经过下面的几次优化之后，这就是最优的
    public static int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] stack = new int[N]; // 单调递减栈，要找一个数左边比它小，右边小于等于它即可
        int si = 0;
        long ans = 0;
        for (int i = 0; i < N; i++) {
            while (si != 0 && arr[stack[si - 1]] >= arr[i]) {
                int cur = stack[--si];
                int left = si == 0 ? -1 : stack[si - 1];
                long start = cur - left; // 左侧起始位置
                long end = i - cur;
                ans += start * end * arr[cur];
                ans %= 1000000007;
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中剩下的数据
        while (si != 0) {
            int cur = stack[--si];
            int left = si == 0 ? -1 : stack[si - 1];
            long start = cur - left; // 左侧起始位置
            long end = N - cur;
            ans += start * end * arr[cur];
            ans %= 1000000007;
        }
        return (int)ans;
    }

    // 雏形
    public static int sumSubarrayMins1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
//        int[] left = getNumLeftLessArr2(arr); // 在i位置的左边，且比arr[i]小，且最近的数的下标
//        int[] right = getNumRightLargerArr2(arr); // 在i位置的右边，且比arr[i]小于或者等，且最近的数的下标
        int[][] index = getNumLeftAndRightLessArr(arr); // 一次单调栈，搞定左右两侧的数据
        int[] left = index[0];
        int[] right = index[1];
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i]; // 左侧起始位置
            long end = (right[i] == -1 ? arr.length : right[i]) - i;
            ans += start * end * arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    // 获取每个数的左侧小于它的，且离他最近的（单调栈版本）O(N)
    private static int[] getNumLeftLessArr2(int[] arr) {
        int N = arr.length;
        int[] stack = new int[N]; // 单调递减栈，要找一个数左右两边比它小的
        int[] ans = new int[N];
        int si = 0;
        for (int i = 0; i < N; i++) {
            while (si != 0 && arr[stack[si - 1]] >= arr[i]) {
                int cur = stack[--si];
                int left = si == 0 ? -1 : stack[si - 1];
                ans[cur] = left;
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中剩下的数据
        while (si != 0) {
            int cur = stack[--si];
            int left = si == 0 ? -1 : stack[si - 1];
            ans[cur] = left;
        }
        return ans;
    }

    // 获取每个数的右侧小于等于它的，且离他最近的（单调栈版本）O(N)
    private static int[] getNumRightLargerArr2(int[] arr) {
        int N = arr.length;
        int[] stack = new int[N];
        int si = 0;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            while (si != 0 && arr[stack[si - 1]] >= arr[i]) {
                int cur = stack[--si];
                ans[cur] = i;
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，栈中还剩下的数据，都是右侧没有小于等于它的数
        while (si != 0) {
            int cur = stack[--si];
            ans[cur] = -1;
        }
        return ans;
    }

    // 获取每个数的左侧小于它的，右侧小于等于它的，且离他最近的（单调栈版本）O(N)
    private static int[][] getNumLeftAndRightLessArr(int[] arr) {
        int N = arr.length;
        int[] stack = new int[N]; // 单调递减栈，要找一个数左右两边比它小的
        int[][] ans = new int[2][N];
        int si = 0;
        for (int i = 0; i < N; i++) {
            while (si != 0 && arr[stack[si - 1]] >= arr[i]) { // 此处
                int cur = stack[--si];
                int left = si == 0 ? -1 : stack[si - 1];
                ans[0][cur] = left;
                ans[1][cur] = i;
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中剩下的数据。
        // 左侧就是cur的下一个位置，右侧就是-1的情况（右边没有比它小的值）
        while (si != 0) {
            int cur = stack[--si];
            int left = si == 0 ? -1 : stack[si - 1];
            ans[0][cur] = left;
            ans[1][cur] = -1;
        }
        return ans;
    }

    // 获取每个数的右侧小于等于它的，且离他最近的（暴力版本）O(N^2)
    private static int[] getNumRightLargerArr1(int[] arr) {
        int N = arr.length;
        int[] res = new int[N];
        res[N - 1] = -1;
        for (int i = N - 2; i >= 0; i--) {
            res[i] = -1;
            for (int j = i + 1; j < N; j++) {
                if (arr[j] <= arr[i]) { // 小于等于
                    res[i] = j;
                    break;
                }
            }
        }
        return res;
    }

    // 获取每个数的左侧小于它的，且离他最近的（暴力版本）O(N^2)
    private static int[] getNumLeftLessArr1(int[] arr) {
        int N = arr.length;
        int[] res = new int[N];
        res[0] = -1;
        for (int i = 1; i < N; i++) {
            res[i] = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    res[i] = j;
                    break;
                }
            }
        }
        return res;
    }
}
