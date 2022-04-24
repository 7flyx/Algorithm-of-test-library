package class06;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-24
 * Time: 14:09
 * Description: 快速排序
 */
public class Code01_QuickSort {
    public static void main(String[] args) {
        int[] arr = {2, 6, 4, 3, 2, 1, 5};
        quickSort2(arr);
        System.out.println(Arrays.toString(arr));
    }

    // 递归版本-快速排序
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort1(arr, 0, arr.length - 1);
    }

    private static void quickSort1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        // 选取基准值
        int point = l + (int)(Math.random() * (r - l));
        swap(arr, r, point); // 与最右边的参数交换
        int[] mid = partition(arr, l, r);// 荷兰国旗问题优化
        quickSort1(arr, l, mid[0] - 1); // 左部分
        quickSort1(arr, mid[1] + 1, r); // 右部分
    }

    // 荷兰国旗问题优化
    private static int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;
        int index = l;
        // 还没遇到大于区时，循环继续
        while (index < more) {
            if (arr[index] > arr[r]) { // 大于区
                swap(arr, index, --more); //交换之后，index还不能移动
            } else if (arr[index] < arr[r]) { // 小于区
                swap(arr, index++, ++less);
            } else { // 等于的时候，index移动就行
                index++;
            }
        }

        // 此时就分为三个区域：l……less， less + 1……more - 1， more …… r
        swap(arr, more, r); // 将r位置的基准值交换回来
        return new int[]{less + 1, more}; // 返回等于区的范围
    }

    // 非递归版本-快速排序
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int l = 0;
        int r = arr.length - 1;
        Stack<Integer> stack = new Stack<>();
        stack.push(l); // 先压入左边界
        stack.push(r); // 再压入右边界
        while (!stack.isEmpty()) {
            r = stack.pop(); // 先弹出的是右边界
            l = stack.pop();

            int point = l + (int)(Math.random() * (r - l));
            swap(arr, point, r); // 先与最后一个元素进行交换
            int[] mid = partition(arr, l, r);
            if (mid[0] - 1 - l > 1) { // 左半部分的元素还不止1个
                stack.push(l);
                stack.push(mid[0] - 1);
            }
            if (r - mid[1] - 1 > 1) { // 右半部分的元素还不止1个
                stack.push(mid[1] + 1);
                stack.push(r);
            }
        }
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[r];
        arr[r] = arr[l];
        arr[l] = tmp;
    }
}
