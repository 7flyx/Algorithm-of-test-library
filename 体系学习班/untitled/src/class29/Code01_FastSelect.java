package class29;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-16
 * Time: 16:29
 * Description:  最小的K个数。又或者是快速定位到第K小（大）的数
 * https://leetcode.cn/problems/zui-xiao-de-kge-shu-lcof/
 */
public class Code01_FastSelect {
    public static void main(String[] args) {
        int[] arr = {5, 3, 6, 7, 7, 8, 9, 1, 2, 3};
        System.out.println(potholing(arr, 0, arr.length - 1));
        System.out.println(Arrays.toString(arr));

//        int k = 2;
//        System.out.println(Arrays.toString(getLeastNumbers(arr, k)));
    }

    // 用随机选择基准值的快排
    public static int[] getLeastNumbers(int[] arr, int k) {
        if (arr == null || arr.length <= k) {
            return arr;
        }
        process(arr, 0, arr.length - 1, k);
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    public static void process(int[] arr, int L, int R, int k) {
        if (L == R) {
            return;
        }
        int pivot = L + (int)(Math.random() * (R - L)) + 1; // 基准值
        swap(arr, pivot, R); // 将基准值放到最后一个位置
        int[] mid = netherlands(arr, L, R);
        if (mid[0] <= k && k <= mid[1]) {
            return;
        } else if (mid[0] >= k) {
            process(arr, L, mid[0] - 1, k); // 往左侧走
        } else {
            process(arr, mid[1] + 1, R, k); // 往右侧走
        }
    }

    // 荷兰国旗问题优化
    private static int[] netherlands(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;
        int index = l;
        while (index < more) {
            if(arr[index] < arr[r]) {
                swap(arr, index++, ++less);
            } else if (arr[index] > arr[r]) {
                swap(arr, index, --more);
            } else { // 和基准值相等时候，不用交换
                index++;
            }
        }
        // 将arr[r]位置的基准值放回中间处
        // l....less less+1....more-1  more...r
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }

    // 挖坑法
    private static int potholing(int[] arr, int l, int r) {
        int pivot = arr[l];
        int less = l;
        int more = r;
        while (less < more) {
            // 上面已经临时保存了less位置，现在从右侧拿数填在这里
            while (less < more && arr[more] >= pivot) { // 右边找比pivot小的
                more--;
            }
            arr[less] = arr[more];
            // 现在填写more位置
            while (less < more && arr[less] <= pivot) {
                less++;
            }
            arr[more] = arr[less];
        }
        // 挺下来的时候，二者肯定相等
        arr[less] = pivot;
        return less;
    }
}
