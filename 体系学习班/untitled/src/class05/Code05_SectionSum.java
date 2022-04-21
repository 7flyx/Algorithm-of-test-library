package class05;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-21
 * Time: 17:32
 * Description: LeetCode： 327. 区间和的个数
 */
public class Code05_SectionSum {
    public static void main(String[] args) {
        int[] arr = {-1, 1};
        int lower = 0;
        int upper = 0;

    }

    public static int calcSectionSum(int[] arr, int lower, int upper) {
        if (arr == null || lower > upper) {
            return 0;
        }

        // 先计算出前缀和，对前缀和进行归并排序的操作，在merge函数中，计算出结果
        int[] preSum = new int[arr.length];
        preSum[0] = arr[0];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i]; // 前缀和
        }
        return process(preSum, 0, preSum.length - 1, lower, upper);
    }

    public static int process(int[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            // 当前前缀和表示的范围是[0 ~ L]
            // 如果在[lower, upper]范围内，则就是一种情况，否则就不是
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }

        int mid = L + ((R - L) >> 1);
        int left = process(sum, L, mid, lower, upper); // 左部分
        int right = process(sum, mid + 1, R, lower, upper); // 右部分
        int merge = merge(sum, L, mid, R, lower, upper);
        return left + right + merge; // 返回三者的和
    }

    private static int merge(int[] sum, int L, int mid, int R, int lower, int upper) {
        // 先定义流程，计算我们想要的数据
        int windowL = L;
        int windowR = L; // 实则就是左闭右开区间，[windowL, windowR)
        int res = 0; // 结果
        // 从右边的视角出发，在左边找一个范围上的数
        for (int i = mid + 1; i <= R; i++) {
            int min = sum[i] - upper; // 在左边数组查找的左边界
            int max = sum[i] - lower; // 在左边数组查找的右边界
            // 滑动windowR和windowL，形成新的窗口范围
            while (windowR <= mid && sum[windowR] <= max) {
                windowR++; // 窗口向右滑动
            }
            // 此处的sum[windowL] < min 也就代表 windowL的位置，一定是要>=min的
            while (windowL <= mid && sum[windowL] < min) {
                windowL++; // 窗口向右滑动
            }
            res += windowR - windowL; // 右边界-左边界，就是答案
        }
        // 再写常规的归并排序，让数据有序
        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int index = 0;
        while (p1 <= mid && p2 <= R) {
            help[index++] = sum[p1] <= sum[p2]? sum[p1++] : sum[p2++];
        }

        while (p1 <= mid) {
            help[index++] = sum[p1++];
        }
        while (p2 <= R) {
            help[index++] = sum[p2++];
        }

        // 将数据放回原数组
        for (int i = 0; i < help.length; i++) {
            sum[i + L] = help[i];
        }
        return res;
    }
}
