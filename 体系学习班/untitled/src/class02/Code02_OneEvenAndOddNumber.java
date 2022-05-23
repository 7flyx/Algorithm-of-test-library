package class02;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:04
 * Description: 给定一个数组，其中有一种数出现了奇数次，其他的都出现了偶数次
 * 请返回出现奇数次的数。
 */
public class Code02_OneEvenAndOddNumber {
    static class Solution {
        public int getOddNumber(int[] arr) {
            if (arr == null) {
                return -1;
            }

            int eor = 0;
            for (int num : arr) {
                eor ^= num;
            }
            return eor;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int testTime = 10000;
        int length = 100;
        int range = 100;

        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int testLen = (int) (Math.random() * length);
            while ((testLen & 1) == 0) {
                testLen = (int) (Math.random() * length);
            }
            int[] arr = generate(testLen, range);
            int[] copy = Arrays.copyOf(arr, arr.length);
            int testAns = test(arr);
            int userAns = solution.getOddNumber(copy);
            if (testAns != userAns) {
                System.out.println("测试数据：" + Arrays.toString(arr));
                System.out.println("期望输出：" + testAns);
                System.out.println("实际输出：" + userAns);
                success = false;
            }
        }
        if (success) {
            System.out.println("测试通过");
        }
    }

    public static int test(int[] arr) {
        if (arr == null) {
            return -1;
        }

        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }
        return eor;
    }

    public static int[] generate(int length, int range) {
        int[] arr = new int[length];
        int aim = (int) (Math.random() * range); // 真命天子
        int tmp = (int) (Math.random() * length) + 1;
        while ((tmp & 1) == 0) { // 偶数的情况，重新搞
            tmp = (int) (Math.random() * length) + 1;
        }
        int index = 0;
        while (index < tmp) { // 先填写真命天子
            arr[index++] = aim;
        }

        while (index < length - 6) {
            int num = (int) (Math.random() * range);
            while (num == aim) {
                num = (int) (Math.random() * range);
            }
            int count = (int) (Math.random() * (length - index));
            while ((count & 1) == 1) { // 必须是偶数
                count = (int) (Math.random() * (length - index));
            }
            while (count-- > 0) {
                arr[index++] = num;
            }
        }
        int num = (int) (Math.random() * range);
        while (index < length) {
            arr[index++] = num;
        }

        // 打乱顺序
        for (int i = length - 1; i >= 1; i--) {
            index = (int) (Math.random() * (i));
            swap(arr, i, index);
        }
        return arr;
    }

    private static void swap(int[] arr, int i, int index) {
        int tmp = arr[i];
        arr[i] = arr[index];
        arr[index] = tmp;
    }
}
