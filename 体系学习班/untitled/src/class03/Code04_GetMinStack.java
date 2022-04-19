package class03;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 17:47
 * Description: 搞一个最小值栈
 */
public class Code04_GetMinStack {
    private Stack<Integer> stack; // 主数据栈
    private Stack<Integer> minStack; // 最小值栈
    private int size;

    public Code04_GetMinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int value) {
        stack.push(value);
        if (minStack.isEmpty() || minStack.peek() >= value) { // 压入value值，作为新的最小值
            minStack.push(value);
        } else { // 再次压入栈顶元素
            minStack.push(minStack.peek());
        }
        size++;
    }

    public int pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("栈为空");
        }
        size--;
        minStack.pop(); // 弹出对应位置的最小值
        return stack.pop();
    }

    public int peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("栈为空");
        }

        return stack.peek();
    }

    public int getMin() {
        if (minStack.isEmpty()) {
            throw new RuntimeException("栈为空");
        }

        return minStack.peek();
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        Code04_GetMinStack minStack = new Code04_GetMinStack();
        minStack.push(3);
        minStack.push(2);
        minStack.push(1);
        System.out.println(minStack.getMin());
        System.out.println(minStack.pop());
        System.out.println(minStack.getMin());
        System.out.println(minStack.size());
    }
}
