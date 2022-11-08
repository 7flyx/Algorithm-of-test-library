package class47;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-06
 * Time: 11:55
 * Description:
 * 给定一个数组arr，和一个正数M，返回在arr的子数组在长度不超过M的情况下，最大的累加和
 */
public class Code04_MaxSumOfArray {
    public static void main(String[] args) {
        int[] arr = {20, -10, 50, 20, -50, 30, 30, 60};
        System.out.println(maxSum(arr, 3));
        System.out.println(maxSum1(arr, 3));
        int testTime = 1000;
        int range = 100;
        int length = 10;
        System.out.println("test started.");
        for (int i = 0; i < testTime; i++) {
            int[] nums = generateArray(length, range);
            int M = (int) (Math.random() * length) + 1;
            int ans1 = maxSum(nums, M);
            int ans2 = maxSum1(nums, M);
            if (ans1 != ans2) {
                System.out.println("ans1: " + ans1);
                System.out.println("ans2: " + ans2);
                System.out.println("M: " + M);
                System.out.println(Arrays.toString(nums));
                System.out.println("test failed.");
                break;
            }
        }
        System.out.println("test finished.");
    }

    // for test
    public static int[] generateArray(int length, int range) {
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = (int) (Math.random() * range) - (int) (Math.random() * range);
        }
        return ans;
    }

    public static int maxSum(int[] nums, int M) {
        if (nums == null || nums.length == 0 || M <= 0) {
            return 0;
        }
        int N = nums.length;
        int[] preSum = new int[N];
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        LinkedList<Integer> qmax = new LinkedList<>(); // preSum[R] 队头大，队尾小
        int R = 0;
        int end = Math.min(N, M);
        for (; R < end; R++) {
            while (!qmax.isEmpty() && preSum[qmax.peekLast()] <= preSum[R]) {
                qmax.pollLast();
            }
            qmax.addLast(R);
        }
        int ans = preSum[qmax.peekFirst()];
        int L = 0;
        for (; R < N; R++, L++) { // i和L边界一起向右滑动
            if (qmax.peekFirst() <= L) { // 队头已经小于L边界，直接丢弃
                qmax.pollFirst();
            }
            while (!qmax.isEmpty() && preSum[qmax.peekLast()] <= preSum[R]) {
                qmax.pollLast();
            }
            qmax.addLast(R);
            ans = Math.max(ans, preSum[qmax.peekFirst()] - preSum[L]);
        }
        // 结算队列里面剩余的数据
        for(; L < N - 1; L++) { // 已经没有数据再进队列，我们只需要卡住左边界，缩小窗口长度即可
            if (qmax.peekFirst() <= L) {
                qmax.pollFirst();
            }
            ans = Math.max(ans, preSum[qmax.peekFirst()] - preSum[L]);
        }
        return ans;
    }

    // for test
    public static int maxSum1(int[] nums, int M) {
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < Math.min(i + M, nums.length); j++) {
                sum += nums[j];
                ans = Math.max(ans, sum); // 不超过M长度的窗口
            }
        }
        return ans;
    }
}
