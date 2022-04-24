package class09;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-24
 * Time: 18:47
 * Description: 基数排序 - 最适合于正数排序；其实也不是不可以给负数，比如先找最小的数，让整个数组
 * 先加上Math.abs(最小值)，排序后，再全部减去Math.abs(最小值)，只是需要考虑溢出的情况
 */
public class Code03_RadixSort {
    public static void main(String[] args) {
        int[] arr = {20, 44, 633, 90, 65, 74};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int digit = 0; // 最大值的位数
        for (int i = 0; i < arr.length; i++) {
            digit = Math.max(digit, (arr[i] + "").length());
        }

        int[] count = new int[10]; // 用数组来替换原思路中的10个桶
        int[] help = new int[arr.length]; // 暂存arr中的数据
        for (int d = 1; d <= digit; d++) {
            for (int j = 0; j < arr.length; j++) {
                int index = getDigit(arr[j], d);
                count[index]++; // 以某一位数字进行排序
            }

            // 计算累计和
            for (int j = 1; j < 10; j++) {
                count[j] += count[j - 1];
            }

            // 从数组最后面开始遍历
            for (int j = arr.length - 1; j >= 0; j--) {
                int index =  getDigit(arr[j], d);
                if (count[index]-- > 0) {
                    // 假设count[index] = 3,则表示0~2范围可填入数据，当前数填入2下标处
                    help[count[index]] = arr[j];
                }
            }
            // 将数据从辅助数组转移到arr中
            System.arraycopy(help, 0, arr, 0, arr.length);
            Arrays.fill(count, 0); // 桶里面的数据归0
        }
    }

    // 获取某一位数值
    private static int getDigit(int x, int d) {
        return (x / (int)(Math.pow(10, d - 1))) % 10;
    }

}
