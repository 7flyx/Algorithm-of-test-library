package quicksort;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 20:57
 * Description: 优化-荷兰国旗问题
 */
public class QuickSort4 {
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
        //随机值，是使这个快速排序的时间复杂度达到O(N*logN)的关键
        int index = left + (int) (Math.random() * (right - left));
        swap(array, index, right); //将随机值，放到数组的最后
        int[] mid = partition(array, left, right);
        quick(array, left, mid[0] - 1);
        quick(array, mid[1] + 1, right);
    }

    private static int[] partition(int[] array, int L, int R) {
        if (L == R) {
            return new int[]{L, L};
        }
        if (L > R) {
            return new int[]{-1, -1};
        }

        int less = L - 1; //小于区
        int more = R; //大于区
        int index = L;
        while (index < more) {
            if (array[index] > array[R]) { //大于的情况
                swap(array, index, --more);
            } else if (array[index] < array[R]) { //小于的情况
                swap(array, index++, ++less);
            } else { //等于的情况
                index++;
            }
        }
        swap(array, more, R); //将最后一个元素，放回中间位置
        //L……less 小于区 ， less + 1……more 等于区，more+ 1 ……R 大于区
        return new int[]{less + 1, more};
    }

    private static void swap(int[] array, int L, int R) {
        int tmp = array[L];
        array[L] = array[R];
        array[R] = tmp;
    }
}
