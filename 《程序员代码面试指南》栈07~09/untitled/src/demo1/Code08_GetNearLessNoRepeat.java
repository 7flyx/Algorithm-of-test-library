package demo1;

import java.util.*;

public class Code08_GetNearLessNoRepeat {
    public static void main(String[] args) {
        /*
            单调栈结构--
            从栈顶到栈底，必须是严格的升序或者降序
         */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int[][] res = getNearLessNoRepeat(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) { //拼接字符串，比两层for循环快
            sb.append(res[i][0]).append(" ").append(res[i][1]).append('\n');
        }
        System.out.print(sb);
    }

    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        //遍历数组的所有数据
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) { // 保证栈中的元素，从栈顶到栈底为降序
                int popPoint = stack.pop();
                int leftPoint = stack.isEmpty()? -1 : stack.peek();
                res[popPoint][0] = leftPoint;
                res[popPoint][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int popPoint = stack.pop();
            int leftPoint = stack.isEmpty()? -1 : stack.peek();
            res[popPoint][0] = leftPoint;
            res[popPoint][1] = -1;
        }
        return res;
    }
}