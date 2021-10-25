package quicksort;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 20:13
 * Description: 挖坑法
 */
public class QuickSort1 {

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

        int p = partition1(array, left, right);
        quick(array, 0, p - 1);
        quick(array, p + 1, right);
    }

    //挖坑法
    private static int partition1(int[] array, int L, int R) {
        int tmp = array[L];
        while (L < R) {
            while (L < R && array[R] >= tmp) { //小的往前放
                R--;
            }
            array[L] = array[R];

            while (L < R && array[L] <= tmp) { //大的往后放
                L++;
            }
            array[R] = array[L];
        }
        array[L] = tmp; //此时的L等于R
        return L;
    }

}
