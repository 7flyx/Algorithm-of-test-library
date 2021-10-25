package quicksort;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 20:26
 * Description: 随机选取基准值
 */
public class QuickSort2 {
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

        int index = left + (int)(Math.random() * (right - left));
        swap(array, left, index); //将随机值，放到left,然后再次调用挖坑法
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


    private static void swap(int[] array, int L, int R) {
        int tmp = array[L];
        array[L] = array[R];
        array[R] = tmp;
    }
}
