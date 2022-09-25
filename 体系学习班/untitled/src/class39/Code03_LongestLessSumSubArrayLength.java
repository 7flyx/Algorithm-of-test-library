package class39;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:27
 * Description:
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的，返回其长度。
 * <p>
 * 常见的解法就是 Code02的思想，以某个数结尾，往前找。但需要用到有序表，所以整体的时间复杂度在O(N*logN)
 * 这道题还有O(N)的解法。
 */
public class Code03_LongestLessSumSubArrayLength {
    public static void main(String[] args) {
        System.out.println("test started.");
        int length = 100;
        int testTime = 10000;
        int range = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateArray(length, range);
            int k = (int)((Math.random() * range) - (Math.random() * range)) +1;
            int ans1 = getLongestLessSumSubArrayLength(arr, k);
            int ans2 = maxLengthAwesome(arr, k);
            if (ans1 != ans2) {
                System.out.println("K: " + k);
                System.out.println(Arrays.toString(arr));
                System.out.println("ans1: " + ans1);
                System.out.println("ans2: " + ans2);
                System.out.println("error");
                break;
            }
        }
        System.out.println("test finished.");
    }

    private static int[] generateArray(int length, int range) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)((Math.random() * range) - (Math.random() * range)) + 1;
        }
        return arr;
    }

    public static int getLongestLessSumSubArrayLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[] minSum = new int[N]; // i位置的数作为开始，后续子数组累加和最少是多少。
        int[] minSumEnd = new int[N]; // i位置开始，往后累加和最小时，末尾的下标
        // 从后往前遍历，填写这两个表
        minSum[N - 1] = arr[N - 1];
        minSumEnd[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            if (minSum[i + 1] < 0) { // 下一个位置是<=0的数，就能累加，组合成最小数值
                minSum[i] = minSum[i + 1] + arr[i];
                minSumEnd[i] = minSumEnd[i + 1];
            } else { // 以自己本身作为结尾
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }
        // 上述两张表组合使用，就能将arr数组分割成一块一块的
        // 比如：(0~5)(6~8)(9~15).....
        // 这每一块的开始位置，就计算出了这一块最小的累加和。
        // 后续我们只需以“这一块”为单位。在这一块上玩 滑动窗口
        int len = 0;
        int sum = 0;
        int L = 0;
        int R = 0; // 左闭右开区间
        while (L < N) {
            while (R < N && sum + minSum[R] <= k) {
                sum += minSum[R];
                R = minSumEnd[R] + 1; // 来到“下一块”的开始位置
            }
            len = Math.max(len, R - L); // 上面循环停止，就表示sum再累加就会超过k。则计算长度
            // 更新L和R的值
            if ( L < R) { // 累加和超过k。并且L < R
                sum -= arr[L++];
            } else { // L == R。但R位置的数累加上去，已经超过k了。所以LR都要同时移动,表示R位置的根本就不能用
                L++;
                R++;
            }
        }
        return len;
    }

    // 左神写的版本
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        // 迟迟扩不进来那一块儿的开头位置
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i) { // 还有窗口，哪怕窗口没有数字 [i~end) [4,4)
                sum -= arr[i];
            } else { // i == end,  即将 i++, i > end, 此时窗口概念维持不住了，所以end跟着i一起走
                end = i + 1;
            }
        }
        return ans;
    }
}
