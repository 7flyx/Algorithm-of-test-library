package class18;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-26
 * Time: 15:42
 * Description:
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 */
public class Code02_NumOfSubArray {
    public static void main(String[] args) {
        int maxLen = 100;
        int range = 100;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateArray(maxLen, range);
            int num = (int) (Math.random() * (range / 2));
            int ans1 = right(arr, num);
            int ans2 = sumOfSubArray(arr, num);
            if (ans1 != ans2) {
                System.out.println("错误");
                System.out.println("arr: " + Arrays.toString(arr));
                System.out.println("num: " + num);
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] generateArray(int maxLen, int range) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * range) - (int) (Math.random() * range) + 1;
        }
        return ans;
    }

    public static int sumOfSubArray(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num <= 0) {
            return 0;
        }
        // 搞一个滑动窗口，定义是：从L位置出发，一直向右扩展，当出现L位置 与 R位置之间的max和min的 差值，大于num时就停下
        // 此时L……R-1位置，这一段范围就有如下情况：
        // L...L+1,是一个子数组
        // L...L+2,是一个子数组
        // L...L+3，是一个子数组
        // ....
        int N = arr.length;
        int ans = 0;
        LinkedList<Integer> maxQueue = new LinkedList<>(); // 维持最大值结构
        LinkedList<Integer> minQueue = new LinkedList<>(); // 维持最小值结构
        int R = 0;
        for (int L = 0; L < N; L++) { // 左边界
            while (R < N) { //  // 右边界
                // 更新最大值最小值
                while (!maxQueue.isEmpty() && arr[maxQueue.peekLast()] <= arr[R]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(R);
                while (!minQueue.isEmpty() && arr[minQueue.peekLast()] >= arr[R]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(R);
                // 当前窗口内的最大值-最小值，超过了num，就跳出
                if (arr[maxQueue.peekFirst()] - arr[minQueue.peekFirst()] > num) {
                    break;
                }
                R++;
            }
            ans += R - L;
            // 删除最大窗口前面的值和 最小窗口前面的值
            if (maxQueue.peekFirst() == L) {
                maxQueue.pollFirst();
            }
            if (minQueue.peekFirst() == L) {
                minQueue.pollFirst();
            }
        }
        return ans;
    }

    // 暴力解
    public static int right(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num <= 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        // 枚举每一个子数组
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int max = arr[i];
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    max = Math.max(max, arr[k]);
                    min = Math.min(min, arr[k]);
                }
                if (max - min <= num) {
                    count++;
                }
            }
        }
        return count;
    }

}
