package class01;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 21:20
 * Description:
 */
public class Code02_BubbleSort {
    public static void main(String[] args) {
        int[] arr = {2, 6, 7, 4, 3, 6, 7, 9};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        if (arr == null) {
            return;
        }

        int n = arr.length;
        boolean flag = true; // 是否有序
        for (int i = 0; i < n - 1 && flag; i++) { // 趟数
            flag = false;
            for (int j = 0; j < n - 1 - i; j++) { // 比较次数
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;
                }
            }
        }
    }

    private static void swap(int[] arr, int j, int i) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
