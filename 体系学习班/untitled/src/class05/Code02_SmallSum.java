package class05;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-21
 * Time: 10:55
 * Description: 牛客网《程序员代码面试指南》21题 ： 小和问题
 *          上述在线OJ题，是包括了 “等于”的情况，下文代码没写等于的逻辑，需要自己手动改下merge方法
 */
public class Code02_SmallSum {
    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 2, 6};
        System.out.println(calcSmallSum(arr));
    }

    public static int calcSmallSum(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) { // 只有一个数的时候，不会产生小和
            return 0;
        }

        int mid = l + ((r - l) >> 1);
        int res = 0;
        res += process(arr, l, mid);
        res += process(arr, mid + 1, r);
        res += merge(arr, l, mid, r);
        return res;
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l;
        int p2 = mid + 1;
        int index = 0;
        int res = 0;
        while (p1 <= mid && p2 <= r) {
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0; // 不包括等于的情况
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++]; // 等于时，需要先拷贝右边。不然会漏掉一些情况
        }

        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        // 将数据全部放入原数组
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res; // 将小和结果返回
    }
}
