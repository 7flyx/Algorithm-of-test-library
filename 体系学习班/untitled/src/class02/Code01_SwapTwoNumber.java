package class02;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:02
 * Description: 不用额外变量，交换两个数据
 */
public class Code01_SwapTwoNumber {
    static class Solution {
        public void swap(int[] arr) {
            int tmp = arr[0];
            arr[0] = arr[1];
            arr[1] = tmp;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int testTime = 1000;
        int length = 2;
        int range = 10000;
        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int[] arr = new int[length];
            arr[0] = (int) (Math.random() * range);
            arr[1] = (int) (Math.random() * range);
            int[] copy = Arrays.copyOf(arr, arr.length);
            int[] copy2 = Arrays.copyOf(arr, arr.length);
            test(arr);
            solution.swap(copy);
            if (arr[0] != copy[0] || arr[1] != copy[1]) {
                System.out.println("测试数据: " + Arrays.toString(copy2));
                System.out.println("期望输出： " + Arrays.toString(arr));
                System.out.println("实际输出： " + Arrays.toString(copy));
                success = false;
            }
        }
        if(success) {
            System.out.println("测试通过");
        }
    }

    public static void test(int[] arr) {
        arr[0] = arr[0] ^ arr[1];
        arr[1] = arr[0] ^ arr[1];
        arr[0] = arr[0] ^ arr[1];
    }
}
