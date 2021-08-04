package unit01;

import java.util.*;

public class Code05_HelpStackSort {
    public static boolean flagPop = false;
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(); //主要的数据栈
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            stack.push(sc.nextInt());
        }
        //stackSort1(stack); //数据量太大，会栈溢出
        stackSort2(stack);

        for (int i = 0; i < N; i++) {
            System.out.print(stack.pop() + " ");
        }
    }

    public static void stackSort1(Stack<Integer> stack) {
        Stack<Integer> helpStack = new Stack<>(); //辅助栈
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            flagPop = false;
            int tmpMax = getTmpMaxNum(stack, Integer.MIN_VALUE); //弹出当前栈中最大的元素
            helpStack.push(tmpMax);
        }
        for (int i = 0; i < size; i++) {
            stack.push(helpStack.pop());
        }
    }

    public static int getTmpMaxNum(Stack<Integer> stack, int max) { //这里的boolean并不能起到作用，不是指针
        int result = stack.pop();
        int nextMax = max;
        if (result > max) {
            max = result;
            nextMax = result;
        }
        if (!stack.isEmpty()) {
            nextMax = getTmpMaxNum(stack, max);
        }
        if (nextMax == result && !flagPop) {
            flagPop = true;
        } else {
            stack.push(result); //重新压入数据，并不是最大的数值
        }
        return nextMax;
    }

    public static void stackSort2(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            while (!help.isEmpty() && cur > help.peek()) {
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }
}

