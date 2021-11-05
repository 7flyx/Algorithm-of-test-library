package demo;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-04
 * Time: 22:08
 * Description:
 * 请编写一个程序， 对一个栈里的整型数据， 按升序进行排序（即排序前， 栈里
 * 的数据是无序的， 排序后最大元素位于栈顶） ， 要求最多只能使用一个额外的
 * 栈存放临时数据， 但不得将元素复制到别的数据结构中。
 */
public class Code03_InStackSort {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(3);
        stack.push(5);
        stack.push(1);
        stackSort(stack);
        System.out.println();
    }

    public static void stackSort(Stack<Integer> stack) {
        if (stack == null || stack.size() < 2) {
            return;
        }

        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            while (!stack.isEmpty() && (help.isEmpty() || stack.peek() <= help.peek())) {
                help.push(stack.pop());
            }
            if (stack.isEmpty()) {
                break;
            }
            int tmp = stack.pop(); //较大的元素-也可以不用这个临时变量，直接使用异或运算即可
            while (!help.isEmpty() && help.peek() < tmp) {
                stack.push(help.pop());
            }
            help.push(tmp);
        }
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }
}
