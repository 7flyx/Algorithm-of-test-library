package class05;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-21
 * Time: 11:12
 * Description: LeetCode：剑指 Offer 51. 数组中的逆序对
 * 题目：给定一个数组，问数组中的每个数，它的右边有多少个比它小的数？ 这样的数构成逆序对。
 * 例如：【2， 3， 1， 0】，对于2来说，右边有两个数比2小，构成两个逆序对（2，1）和（2，0）
 */
public class Code03_InversionPair {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 0};
        System.out.println(getInversionPair(arr));
    }

    public static int getInversionPair(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }

        int mid = l + ((r - l) >> 1);
        int res = 0;
        res += process(arr, l, mid);
        res += process(arr, mid + 1, r);
        res += merge(arr, l, mid, r);
        return res;
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int p1 = m;
        int p2 = r;
        int index = help.length - 1; // 从最后一个数开始
        int pair = 0;
        // 这里不同于前面的题，常规的合并是从左往右合并
        // 这里因为特殊原因，是从右向左合并的
        while (p1 >= l && p2 >= m + 1) {
            pair += arr[p1] > arr[p2]? (p2 - m) : 0; // 找的是 左部分的数 大于 右部分的数
            help[index--] = arr[p1] > arr[p2]? arr[p1--] : arr[p2--]; // 等于的时候，先拷贝右边的数
        }

        while (p1 >= l) {
            help[index--] = arr[p1--];
        }
        while (p2 >= m + 1) {
            help[index--] = arr[p2--];
        }

        // 将数据返回原数组
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return pair;
    }
}
