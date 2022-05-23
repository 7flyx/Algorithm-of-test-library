package class01;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 22:03
 * Description: 在一个升序数组中，找到大于等于num的最左位置。例如：
 * 1 2 3 3 4 4 4 5 5 5 5 5 7 8，找到>=5，最左边的位置
 * 上述例子就返回下标 7 即可
 */


public class Code05_BeEqualOrGreaterThanNumber {

    static class Solution {
        public int beEqualOrGreaterThanNumber(int[] arr, int k) {
            if (arr == null) {
                return -1;
            }

            int left = 0;
            int right = arr.length;
            int res = -1;
            int mid = 0;
            while (left < right) { // 还有两个数以上的情况，循环继续
                mid = left + ((right - left) >> 1);
                if (arr[mid] >= k) { // 截掉右部分
                    right = mid - 1;
                    res = mid; // 更新下标值
                } else { // arr[mid] < k。截掉左部分
                    left = mid + 1;
                }
            }
            return res;
        }
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int testTime = 1000;
        int length = 100;
        int range = 1000;
        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int[] arr = generate((int) (Math.random() * length), range);
            int[] copy = Arrays.copyOf(arr, arr.length);
            int index = (int) (Math.random() * (arr.length)); // 0 ~ arr.length - 1范围
            int testAns = test(arr, index);
            int userAns = solution.beEqualOrGreaterThanNumber(copy, index);
            if (testAns != userAns) {
                System.out.println("测试数据：" + Arrays.toString(arr));
                System.out.println("预期输出：" + testAns);
                System.out.println("实际输出：" + userAns);
                success = false;
                break;
            }
        }
        if (success) {
            System.out.println("测试通过");
        }
    }

    public static int test(int[] arr, int k) {
        if (arr == null) {
            return -1;
        }

        int left = 0;
        int right = arr.length;
        int res = -1;
        int mid = 0;
        while (left < right) { // 还有两个数以上的情况，循环继续
            mid = left + ((right - left) >> 1);
            if (arr[mid] >= k) { // 截掉右部分
                right = mid - 1;
                res = mid; // 更新下标值
            } else { // arr[mid] < k。截掉左部分
                left = mid + 1;
            }
        }
        return res;
    }

    public static int[] generate(int length, int range) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * range) - (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }
}
