package class02;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:07
 * Description: 给定一个数组，其中有 两种数出现了奇数次，其余的都出现了偶数次
 * 请返回出现奇数次的两个数。
 */

public class Code03_TwoEvenAndOddNumber {
    static class Solution {
        public int[] getTwoOddNumber(int[] arr) {
            if (arr == null) {
                return new int[2];
            }

            int eor = 0;
            for (int num : arr) {
                eor ^= num;
            }

            // eor就是 出现奇数次的两个数 a和b的异或和
            int mostRightOne = eor & (-eor); // 拿到最右侧的1
            int res = 0;
            for (int num : arr) {
                // 挑选出num在mostRightOne这个位置有1 的情况
                if ((num & mostRightOne) != 0) { //说明num这个位置有1
                    res ^= num;
                }
            }
            int[] tmp = new int[2];
            tmp[0] = res;
            tmp[1] = res ^ eor;
            return tmp;
        }
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int range = 100;
        int length = 100;
        int testTime = 10000;
        boolean success = true;
        for (int i = 0; i < testTime && success; i++) {
            int testLen = (int)(Math.random() * length) + 2;
            while ((testLen & 1) == 1) { // 必须是偶数
                testLen = (int)(Math.random() * length) + 2;
            }
            int[] arr = generate(testLen, range);
            int[] copy = Arrays.copyOf(arr, testLen);
            int[] testAns = getTwoOddNumber(arr);
            int[] userAns = solution.getTwoOddNumber(arr);
            if ((testAns[0] == userAns[0] && testAns[1] == userAns[1]) ||
            (testAns[0] == userAns[1] && testAns[1] == userAns[0])) { // 测试通过的情况
                continue;
            } else {
                System.out.println("测试数据：" + Arrays.toString(arr));
                System.out.println("期望输出：" + Arrays.toString(testAns));
                System.out.println("实际输出：" + Arrays.toString(userAns));
                success = false;
            }
        }
        if (success) {
            System.out.println("测试通过");
        }
    }

    public static int[] getTwoOddNumber(int[] arr) {
        if (arr == null) {
            return new int[2];
        }

        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }

        // eor就是 出现奇数次的两个数 a和b的异或和
        int mostRightOne = eor & (-eor); // 拿到最右侧的1
        int res = 0;
        for (int num : arr) {
            // 挑选出num在mostRightOne这个位置有1 的情况
            if ((num & mostRightOne) != 0) { //说明num这个位置有1
                res ^= num;
            }
        }
        int[] tmp = new int[2];
        tmp[0] = res;
        tmp[1] = res ^ eor;
        return tmp;
    }

    public static int[] generate(int length, int range) {
        int[] arr = new int[length];
        int aim1 = (int) (Math.random() * range); // 真命天子
        int aim2 = (int) (Math.random() * range); // 真命天子
        while (aim1 == aim2) {
            aim2 = (int) (Math.random() * range);
        }
        int tmp = (int) (Math.random() * (length) / 2) + 1;
        while ((tmp & 1) == 0) { // 偶数的情况，重新搞
            tmp = (int) (Math.random() * length) + 1;
        }
        int index = 0;
        while (index < tmp) { // 先填写真命天子1
            arr[index++] = aim1;
        }

        // 填写真命天子2
        tmp = (int) (Math.random() * (length - index)) + 1;
        while ((tmp & 1) == 0) { // 偶数的情况，重新搞
            tmp = (int) (Math.random() * (length - index)) + 1;
        }
        while (tmp-- > 0) { // 再填写真命天子2
            arr[index++] = aim2;
        }

        while (index < length - 6) {
            int num = (int) (Math.random() * range);
            while (num == aim1 || num == aim2) {
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
