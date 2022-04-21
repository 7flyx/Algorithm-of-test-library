package class05;


/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-21
 * Time: 11:30
 * Description: 给定一个数组，问你数组中有多少个数满足这个条件？
 * arr[i] > arr[大于i] * 2
 */
public class Code04_CountRightDoubleNum {
    public static void main(String[] args) {
        int[] arr = {8, 2, 5, 3};
        System.out.println(countRightDoubleNum(arr));
    }

    public static int countRightDoubleNum(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }

        int m = l + ((r - l) >> 1);
        int res = 0;
        res += process(arr, l, m);
        res += process(arr, m + 1, r);
        res += merge(arr, l, m, r);
        return res;
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = m + 1;
        int windowR = m + 1; // [m + 1, windowR)，左闭右开区间
        int res = 0;
        int index = 0; // 指向help数组
        while (windowR <= r && p1 <= m) {
            while (windowR <= r && arr[p1] > arr[windowR] * 2) {
                windowR++; // 一直向右滑动，不回退技巧
            }
            res += (windowR - (m + 1)); // 统计个数
            p1++;
        }

        p1 = l;
        while (p1 <= m && p2 <= r) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        // 将数据返回原数组
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }
}
