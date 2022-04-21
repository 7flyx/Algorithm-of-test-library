package class05;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-21
 * Time: 10:35
 * Description: 实现递归、非递归的归并排序
 */
public class Code01_MergeSort {
    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 5, 8, 6, 7, 9};
        mergeSort2(arr);
        System.out.println(Arrays.toString(arr));


        System.out.println();
        System.out.printf("%d\n", 10);
        System.out.print("hello world");
    }

    // 递归
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        if (r == l) {
            return;
        }

        int mid = l + ((r - l) >> 1); // 中间值
        process(arr, l, mid);
        process(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    // 非递归
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int mergeSize = 1; // 左半部分的长度
        int N = arr.length;
        while (mergeSize < N) { // 增长速度是2的幂，所以时间复杂度是log N
            int L = 0; // 从第一个元素开始
            while (L < N) {
                int M = L + mergeSize - 1; // 中间值
                if (M >= N) { // 没有右半部分的数据，不够一次merge
                    break;
                }

                int R = Math.min(N - 1, M + mergeSize); // 取最小值
                merge(arr, L, M, R); // 合并
                L = R + 1; // 更新左边的起点
            }
            // 上面循环结束，说明当前步长合并结束，步长*2
            if (mergeSize > N / 2) { // 防止溢出
                break;
            }
            mergeSize <<= 1; // mergeSize * 2
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1]; // 临时存储数据
        int p1 = l;
        int p2 = m + 1;
        int index = 0; //指向help数组
        while (p1 <= m && p2 <= r) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 循环结束，肯定是有一个部分已经遍历完了，将另外一部分也放入help即可
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        // 将help排好序的数据，全部放回原数组
        for (int i = 0; i < help.length; i++) {
            arr[i + l] = help[i];
        }
    }
}
