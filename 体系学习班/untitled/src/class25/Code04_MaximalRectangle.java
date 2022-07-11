package class25;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-08
 * Time: 22:23
 * Description: LeetCode85题 最大矩形
 * https://leetcode.cn/problems/maximal-rectangle/
 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩形内部有多少个1（面积）
 */
public class Code04_MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = -1;
        int N = matrix.length; // 行
        int M = matrix[0].length; // 列
        int[] bottom = new int[M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                bottom[j] = matrix[i][j] == '0'? 0 : bottom[j] + 1; // 是0的情况，就清0，否则累加
            }
            max = Math.max(max, getBottomMax(bottom));
        }
        return max;
    }

    private int getBottomMax(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = -1;
        int N = heights.length;
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int h = heights[stack.pop()];
                int left = stack.isEmpty()? -1 : stack.peek();
                max = Math.max(max, h * (i - left - 1));
            }
            stack.push(i);
        }
        // 数组遍历完之后，处理栈中剩下的数据
        while (!stack.isEmpty()) {
            int h  = heights[stack.pop()];
            int left = stack.isEmpty()? -1 : stack.peek();
            max = Math.max(max, h * (N - left - 1));
        }
        return max;
    }

    // 用数组模拟栈
    private int getBottomMax2(int[] heights) {
        int N = heights.length;
        int[] stack = new int[N];
        int max = -1;
        int si = 0;
        for (int i = 0; i < N; i++) {
            while (si != 0 && heights[stack[si - 1]] >= heights[i]) {
                int h = heights[stack[--si]];
                int left = si == 0? -1 : stack[si - 1];
                max = Math.max(max, h * (i - left - 1));
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中剩下的数据
        while (si != 0) {
            int h  = heights[stack[--si]];
            int left = si == 0? -1 : stack[si - 1];
            max = Math.max(max, h * (N - left - 1));
        }
        return max;
    }
}
