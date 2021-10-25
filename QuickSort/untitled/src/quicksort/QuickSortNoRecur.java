package quicksort;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 21:13
 * Description: 非递归版本的快速排序
 * 实则压入的就是左右两边子数组的下标而已。
 * 这份代码，只是传递非递归的思想。partition方法，也可以是三数取中、荷兰国旗问题优化等等
 */
public class QuickSortNoRecur {
    public static void quickSortNoRecur(int[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(array.length - 1);
        while (!stack.isEmpty()) {
            int right = stack.pop(); //右边界
            int left = stack.pop(); //左边界
            int p = partition(array, left, right);

            //分割后的数组，子数组的元素应该大于2.不然就没必要再压入
            if (p > left + 1) {
                stack.push(left);
                stack.push(p - 1);
            }
            if (p < right - 1) {
                stack.push(p + 1);
                stack.push(right);
            }
        }
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
}
