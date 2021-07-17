package unit01;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Code10_MaxRecSize {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);
        int[][] data = new int[n][m];
        for (int i = 0; i < n; i++) {
            String[] rows = sc.readLine().split(" ");
            for (int j = 0; j < m; j++) {
                data[i][j] = Integer.parseInt(rows[j]);
            }
        }
        System.out.println(maxRecSize(data));
    }

    public static int maxRecSize(int[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            return 0;
        }
        int maxSize = 0;
        int n = arr.length;
        int m = arr[0].length;
        int[] nowRows = new int[m]; //现在这一行的数据
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nowRows[j] = arr[i][j] == 0? 0 : nowRows[j] + 1; //从第一行往下累加
            }
            maxSize = Math.max(maxSize, maxRecFromBottom(nowRows));
        }
        return maxSize;
    }

    public static int maxRecFromBottom(int[] nowRows) {
        if (nowRows == null || nowRows.length == 0) {
            return 0;
        }
        int maxSize = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nowRows.length; i++) {
            //单调栈结构
            while (!stack.isEmpty() && nowRows[i] <= nowRows[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty()? -1 : stack.peek();
                int max = (i - k - 1) * nowRows[j]; //计算j位置，以及往左的面积-----最左边可扩大到k+1位置的柱子上
                maxSize = Math.max(max, maxSize);
            }
            stack.push(i);
        }
        //栈中还剩下的就是相对来说比较大的值，此时这里（i - k - 1） * nowRows[j] 的i
        //就是nowRows的长度
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty()? -1 : stack.peek();
            int max = (nowRows.length - k - 1) * nowRows[j];
            maxSize = Math.max(max, maxSize);
        }
        return maxSize;
    }
}