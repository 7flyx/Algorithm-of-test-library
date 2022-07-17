package class29;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-16
 * Time: 17:05
 * Description:最小的K个数。又或者是快速定位到第K小（大）的数
 * https://leetcode.cn/problems/zui-xiao-de-kge-shu-lcof/
 */
public class Code02_Bfprt {
    public static void main(String[] args) {
        int[] arr = {0, 0, 1, 2, 4, 2, 2, 3, 1, 4};
        int k = 8;
        System.out.println(Arrays.toString(getLeastNumbers(arr, k)));
    }

    public static int[] getLeastNumbers(int[] arr, int k) {
        if (arr == null || arr.length <= k) {
            return arr;
        }
        bfprt(arr, 0, arr.length - 1, k);
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    public static int bfprt(int[] arr, int l, int r, int k) {
        if (l == r) {
            return arr[l];
        }
        int pivot = medianOfMedians(arr, l, r);
        // 根据基准值进行荷兰国旗问题优化
        int[] mid = netherlands(arr, l, r, pivot);
        if (mid[0] <= k && k <= mid[1]) {
            return arr[k];
        } else if (mid[0] > k) {
            return bfprt(arr, l, mid[0] - 1, k);
        } else {
            return bfprt(arr, mid[1] + 1, r, k);
        }
    }


    private static int[] netherlands(int[] arr, int l, int r, int pivot) {
        int less = l - 1;
        int more = r + 1;
        int index = l;
        while (index < more) {
            if (arr[index] < pivot) {
                swap(arr, index++, ++less);
            } else if (arr[index] > pivot) {
                swap(arr, index, --more);
            } else {
                index++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }

    // 获取基准值
    private static int medianOfMedians(int[] arr, int l, int r) {
        // 5个数 5个数一组，取5个数的中间值
        int len = r - l + 1;
        int size = len / 5;
        int offset = len % 5 == 0 ? 0 : 1;
        int[] tmp = new int[size + offset];
        for (int i = 0; i < tmp.length; i++) {
            int left = l + 5 * i; // 5个数的左边界
            int right = Math.min(r, l + 4); // 5个数的右边界
            tmp[i] = sortAndGetMidNum(arr, left, right);
        }
        // 再返回tmp数组中的中间值
        return bfprt(tmp, 0, tmp.length - 1, tmp.length / 2);
    }

    // 排序这5个数，并返回中间值
    private static int sortAndGetMidNum(int[] arr, int left, int right) {
        // 直接插入排序
        for (int i = left; i < right; i++) {
            int cur = arr[i];
            int j = i - 1;
            for (; j >= left; j--) {
                if (arr[j] > arr[j + 1]) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = cur;
        }
        return arr[left + (right - left) / 2]; // 返回中间值
    }
}
