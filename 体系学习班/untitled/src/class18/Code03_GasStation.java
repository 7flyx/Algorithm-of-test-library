package class18;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-26
 * Time: 15:44
 * Description:LeetCode134题 加油站的良好出发点问题
 * 终极版本是 返回每个位置能不能饶一圈。
 *
 *  在一条环路上有 n个加油站，其中第 i个加油站有汽油gas[i]升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1个加油站需要消耗汽油cost[i]升。你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/gas-station
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code03_GasStation {
    public static void main(String[] args) {
        int[] gas = {2, 3, 4};
        int[] cost = {3, 4, 3};
        boolean[] ans = gasNiceStartStation(gas, cost);
        System.out.println(Arrays.toString(ans));
    }

    // 没有阉割版，需要返回每个位置是否符合条件
    public static boolean[] gasNiceStartStation(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return new boolean[]{false};
        }
        int N = gas.length;
        int[] divNum = new int[N]; // 差值数组
        int[] preSum = new int[2 * N]; // 前缀和数组，2倍的原因在于 题意说的是一个环形路线，比如从4出发，能走回1位置
        LinkedList<Integer> queue = new LinkedList<>(); // 维持最小值结构。窗口大小就是gas数组的长度
        for (int i = 0; i < N; i++) { // 计算差值
            divNum[i] = gas[i] - cost[i];
        }
        preSum[0] = divNum[0];
        queue.addFirst(0);
        for (int i = 1; i < preSum.length; i++) { // 计算前缀和
            preSum[i] = divNum[i % N] + preSum[i - 1];
            if (i < N) { // 更新滑动窗口 0 ~ N- 1范围内
                while (!queue.isEmpty() && preSum[queue.peekLast()] >= preSum[i]) {
                    queue.pollLast();
                }
                queue.addLast(i);
            }
        }
        boolean[] ans = new boolean[N];
        for (int i = 0; i < N; i++) { // 卡住窗口的左边界
            // 滑动窗口在上面已经更新了，先结算
            int pre = i == 0? 0 : preSum[i - 1]; // 当前窗口的左边前缀和
            if (preSum[queue.peekFirst()] - pre >= 0) {
                ans[i] = true;
            }
            // 再更新滑动窗口
            while (!queue.isEmpty() && preSum[queue.peekLast()] >= preSum[i + N]) {
                queue.pollLast();
            }
            queue.addLast(i + N);
            if (queue.peekFirst() == i) { // 窗口的最小值是当前的左边界，就弹出，下次窗口不在这个范围了
                queue.pollFirst();
            }
        }
        return ans;
    }

    // LeetCode134题 只需返回一个位置的即可
    public static int gasNiceStartStation2(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }
        int N = gas.length;
        int[] divNum = new int[N];
        int[] preSum = new int[2 * N]; // 前缀和数组，2倍的原因在于 题意说的是一个环形路线，比如从4出发，能走回1位置
        LinkedList<Integer> queue = new LinkedList<>(); // 维持最小值结构。窗口大小就是gas数组的长度
        for (int i = 0; i < N; i++) { // 计算差值
            divNum[i] = gas[i] - cost[i];
        }
        preSum[0] = divNum[0];
        queue.addFirst(0);
        for (int i = 1; i < preSum.length; i++) { // 计算前缀和
            preSum[i] = divNum[i % N] + preSum[i - 1];
            if (i < N) { // 更新滑动窗口 0 ~ N- 1范围内
                while (!queue.isEmpty() && preSum[queue.peekLast()] >= preSum[i]) {
                    queue.pollLast();
                }
                queue.addLast(i);
            }
        }

        for (int i = 0; i < N; i++) { // 卡住窗口的左边界
            // 滑动窗口在上面已经更新了，先结算
            int pre = i == 0? 0 : preSum[i - 1]; // 当前窗口的左边前缀和
            if (preSum[queue.peekFirst()] - pre >= 0) {
                return i;
            }
            // 再更新滑动窗口
            while (!queue.isEmpty() && preSum[queue.peekLast()] >= preSum[i + N]) {
                queue.pollLast();
            }
            queue.addLast(i + N);
            if (queue.peekFirst() == i) { // 窗口的最小值是当前的左边界，就弹出，下次窗口不在这个范围了
                queue.pollFirst();
            }
        }
        return -1;
    }
}
