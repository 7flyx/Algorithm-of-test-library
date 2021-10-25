package quicksort;

import javafx.geometry.HorizontalDirection;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 20:42
 * Description: 三数取中法
 */
public class QuickSort3 {
    public static void quickSort(int[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        quick(array, 0, array.length - 1);
    }

    private static void quick(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        threeNumSort(array, left, right); //三数取中，放到left位置
        int p = partition(array, left, right);
        quick(array, 0, p - 1);
        quick(array, p + 1, right);
    }

    private static int partition(int[] array, int L, int R) {
        int tmp = array[L];
        while (L < R) {
            while (L < R && array[R] >= tmp) {
                R--;
            }
            array[L] = array[R];
            while (L < R && array[L] <= tmp) {
                L++;
            }
            array[R] = array[L];
        }
        array[L] = tmp;
        return L;
    }

    /**
     * 取左中右三数，使其有序，然后取中间值，放到第一位。再次调用挖坑法
     * @param array 待排序数组
     */
    private static void threeNumSort(int[] array, int L, int R) {
        int mid = L + ((R - L) >> 1);
        if (array[mid] > array[L]) {
            swap(array, mid, L);
        }
        if (array[mid] > array[R]) {
            swap(array, mid, R);
        }
        if (array[L] > array[R]) {
            swap(array, L, R);
        }
        //以上3个if后，三者已经有序，然后就是让中间值放到第一位去
        swap(array, L, mid);
    }

    private static void swap(int[] array, int L, int R) {
        int tmp = array[L];
        array[L] = array[R];
        array[R] = tmp;
    }
}
