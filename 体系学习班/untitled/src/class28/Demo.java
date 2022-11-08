package class28;

import java.util.LinkedList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-15
 * Time: 18:04
 * Description:
 */
public class Demo {
    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println(gasNiceStartStation2(gas, cost));
    }
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
