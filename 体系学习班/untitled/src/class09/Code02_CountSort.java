package class09;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-24
 * Time: 18:40
 * Description: 计数排序
 * 适合于明确了数据范围的情况
 */
public class Code02_CountSort {
    public static void main(String[] args) {
        int[] arr = {2, 4, 5, 2, 10, 19, 20};
        countSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void countSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int[] count = new int[500]; // 假设数据范围在500以内
        for (int num : arr) {
            count[num]++;
        }

        int index = 0;
        for (int i = 0; i < count.length && index != arr.length; i++) {
            while (count[i]-- != 0) {
                arr[index++] =i;
            }
        }
    }
}
