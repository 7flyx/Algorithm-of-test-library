package class16;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-05
 * Time: 16:51
 * Description: 给定一个栈的数据，不使用其他额外的数据结构，如何逆序这个栈。
 */
public class Code04_ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        reverseStack(stack);
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private static void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int cur = getBottomData(stack); // 当前这一层填放当前栈底部元素
        reverseStack(stack); // 递归后续过程，后续过程填写完成之后，才填写当前这个一层的数据
        stack.push(cur);
    }

    private static int getBottomData(Stack<Integer> stack) {
        if (stack.size() == 1) {
            return stack.pop(); // 返回底部元素
        }
        int cur = stack.pop();
        int last = getBottomData(stack);
        stack.push(cur); // 将当前这一层的数据压回去
        return last;
    }
}
