package class40;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-30
 * Time: 12:40
 * Description:
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * <p>
 * 把题目一中提到的，min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 * S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 现在要求返回一个长度为N的s数组，
 * s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 得到整个s数组的过程，做到时间复杂度O(N)
 */
public class Code02_BestSplitArray2 {
    public static void main(String[] args) {
        int testTime = 10000;
        int length = 100;
        int range = 1000;
        System.out.println("test started.");
        for (int i = 0; i <testTime; i++) {
            int[] arr = generateArray(length, range);
            int[] ans1 = bestSplitArray1(arr);
            int[] ans2 = bestSplitArray2(arr);
            if (!isSameArray(ans1, ans2)) {
                System.out.println("ans1: " + Arrays.toString(ans1));
                System.out.println("ans2: " + Arrays.toString(ans2));
                System.out.println("test fail.");
                break;
            }
        }
        System.out.println("test finished.");
    }

    private static boolean isSameArray(int[] ans1, int[] ans2) {
        if (ans1 == null || ans2 == null || ans1.length != ans2.length) {
            return false;
        }
        int N = ans1.length;
        for (int i = 0; i < N; i++) {
            if(ans1[i] != ans2[i]) {
                return false;
            }
        }
        return true;
    }

    private static int[] generateArray(int length, int range) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)(Math.random() + range) + 1; // 正整数
        }
        return arr;
    }

    // 枚举 O(N^2)
    public static int[] bestSplitArray1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return new int[]{};
        }

        // 这道题要想做到O(N)的时间复杂度，主要要考虑清楚的是
        // 在第一道题的基础之上，那个最优划分下标 可以 “不回退”。
        // 只要能够做到不回退，这个时间复杂度就在O(N)
        int N = arr.length;
        int[] ans = new int[N]; // 答案
        int[] preSum = new int[N + 1]; // 前缀和数组
        preSum[0] = 0; // 多空一个位置出来，就是为了 简化边界值的判断
        for (int i = 0; i < N; i++) {
            preSum[i + 1] = preSum[i] + arr[i];
        }
        for (int i = 1; i < N; i++) { // 从第2个数开始。第一个数不需要分了。
            int res = 0;
            for (int j = 0; j < i; j++) { // 直接枚举 0 ~ i范围内的所有情况
                int sumL = sum(preSum, 0, j);
                int sumR = sum(preSum, j + 1, i);
                res = Math.max(res, Math.min(sumL, sumR));
            }
            ans[i] = res;
        }
        return ans;
    }

    // 用到不回退技巧。O(N)
    public static int[] bestSplitArray2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return new int[]{};
        }
        // 这道题要想做到O(N)的时间复杂度，主要要考虑清楚的是
        // 在第一道题的基础之上，那个最优划分下标 可以 “不回退”。
        // 只要能够做到不回退，这个时间复杂度就在O(N)
        /*
             不回退的推导：左部分（0~best）。右部分（best+1 ~ N-1）
             1、左累加和 < 右累加和。此时右区间再增加一个正数时，右累加和只可能越来越大于 左累加和。所以best如果再往左走，只会使
                左累加和 变小。
             2、左累加和 > 右累加和。就分为两种情况：
                a)、右区间再增加一个正数，使得 左累加和 < 右累加和。此时左边就成了新的 瓶颈。就回到了情况1。 best再往左滑动的话，
                    也不会取得最优结果
                b)、右区间再增加一个正数，使得 左累加和 > 右累加和。best也不可能往左滑动。因为在上一循环，best来到的就是最佳的位置。
                    比如[3, 100, 8, 9]。此时best来到100和8之间。此时右区间再增加一个数值20，那就是[3, 100, 8, 9, 20]。
                    此时左累加和 103， 右累加和37。如果best往左滑动，也就会使右区间增加100.这显然并不会得到最优的结果。

              综上，best最优划分下标值，能够使用 不回退技巧。
         */
        int N = arr.length;
        int[] ans = new int[N]; // 答案
        int[] preSum = new int[N + 1]; // 前缀和数组
        preSum[0] = 0; // 多空一个位置出来，就是为了 简化边界值的判断
        for (int i = 0; i < N; i++) {
            preSum[i + 1] = preSum[i] + arr[i];
        }
        int best = 0; // 最优划分下标。（0~best）（best+1 ~ N-1）
        for (int i = 1; i < N; i++) { // 从第2个数开始。第一个数不需要分了。
            while (best + 1 < i) { // best最优划分下标 尝试着往右滑动
                int before = Math.min(sum(preSum, 0, best), sum(preSum, best + 1, i)); // 滑动之前
                int after = Math.min(sum(preSum, 0, best + 1), sum(preSum, best + 2, i)); // 滑动之后
                if (after >= before) { // 此处一定是>=。 假设best+1位置的是0。并不会影响累加和，但还是需要往右滑动
                    best += 1;
                } else {
                    break;
                }
            }
            // while循环停下来，此时的best就是来到了最优的划分边界
            // 此时就是i位置的最优划分结果，best的最优划分边界
            ans[i] = Math.min(sum(preSum, 0, best), sum(preSum, best + 1, i));
        }
        return ans;
    }

    // 计算L~R范围上的累加和
    private static int sum(int[] preSum, int L, int R) {
        return preSum[R + 1] - preSum[L];
    }
}
