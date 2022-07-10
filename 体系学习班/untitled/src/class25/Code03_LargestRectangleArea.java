package class25;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-08
 * Time: 21:54
 * Description: LeetCode84题 柱状图中最大的矩形
 * https://leetcode.com/problems/largest-rectangle-in-histogram
 * 给定一个非负数组arr，代表直方图，返回直方图的最大长方形面积
 */
public class Code03_LargestRectangleArea {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>(); // 维持单调递减栈，能够获取每个位置的数，左右两边距离近的最小值
        int N = heights.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int h = heights[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, h * (i - left - 1));
            }
            stack.push(i);
        }
        // 数组遍历完之后，处理栈中的元素
        while (!stack.isEmpty()) {
            int h = heights[stack.pop()];
            int left = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, h * (N - left - 1));
        }
        return max;
    }

    // 用数组来实现栈，替换JDK提供的栈，优化常数时间
    public int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int N = heights.length;
        int si = 0;
        int[] stack = new int[N]; // 维持单调递减栈，能够获取每个位置的数，左右两边距离近的最小值
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            while (si != 0 && heights[stack[si - 1]] >= heights[i]) {
                int h = heights[stack[--si]];
                int left = si == 0 ? -1 : stack[si - 1];
                max = Math.max(max, h * (i - left - 1));
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中的元素
        while (si != 0) {
            int h = heights[stack[--si]];
            int left = si == 0 ? -1 : stack[si - 1];
            max = Math.max(max, h * (N - left - 1));
        }
        return max;
    }
}
