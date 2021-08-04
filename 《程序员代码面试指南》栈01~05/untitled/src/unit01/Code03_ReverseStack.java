package unit01;

import java.util.*;


public class Code03_ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            stack.push(sc.nextInt());
        }
        reverse(stack);
        for (Integer i : stack) {
            System.out.printf("%d ",i);
        }
    }

    public static void reverse(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int i = getBottomNum(stack);
            reverse(stack);
            stack.push(i);
        }
    }

    public static int getBottomNum(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int i = getBottomNum(stack);
            stack.push(result); //需要重新压入
            return i;
        }
    }
}