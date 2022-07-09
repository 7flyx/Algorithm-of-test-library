package class25;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-08
 * Time: 20:53
 * Description: LeetCode1856题 子数组最小乘积的最大值
 * https://leetcode.cn/problems/maximum-subarray-min-product/
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 * 一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 * 那么所有子数组中，这个值最大是多少？
 */
public class Code02_SubArrayProduct {
    /**
     * 分析：以每个位置的数作为最小值，向两边扩展，找比当前位置的数还小的位置，
     * 左右两边夹起来的范围内进行结算。
     *
     * @param nums 原数组
     * @return 子数组的乘积中，返回最大的值
     */
    public static int maxSumMinProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        long[] preSum = new long[N]; // 前缀和数组
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        Stack<Integer> stack = new Stack<>(); // 根据题意，维持一个单调递减栈
        long max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                int min = nums[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, left == -1 ?
                        preSum[i - 1] * min : (preSum[i - 1] - preSum[left]) * min);
            }
            stack.push(i);
        }
        // 数组遍历完之后，处理栈中剩下的数据
        while (!stack.isEmpty()) {
            int min = nums[stack.pop()];
            int left = stack.isEmpty()? -1 : stack.peek();
            max = Math.max(max, left == -1?
                    preSum[N - 1] * min : (preSum[N - 1] - preSum[left]) * min);
        }
        return (int)(max % 1000000007);
    }
}
