package class01;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 21:20
 * Description: 插入排序
 */
public class Code03_InsertSort {
    public static void main(String[] args) {
        int[] arr = {2, 6, 7, 4, 3, 6, 7, 9};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr) {
        if (arr == null) {
            return;
        }

        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int tmp = arr[i]; // 临时保存当前值
            int j = i; // 从当前位置出发，向左边看过去，比较交换即可
            for (; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1]; // 前一个元素，往后移动
            }
            arr[j] = tmp;
        }
    }
}
