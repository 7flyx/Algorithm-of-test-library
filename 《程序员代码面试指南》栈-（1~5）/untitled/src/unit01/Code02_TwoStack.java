package unit01;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Code02_TwoStack {
    private final Stack<Integer> stack1;
    private final Stack<Integer> stack2;

    public Code02_TwoStack() {
        this.stack1 = new Stack<Integer>(); //第一次进入
        this.stack2 = new Stack<Integer>(); //将stack1的数据放入stack2
    }

    public void add(int num) {
        stack1.push(num);
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    public int poll() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new RuntimeException("your stack is empty.");
        }
        return stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new RuntimeException("your stack is empty.");
        }
        return stack2.peek();
    }

    public static void main(String[] args) throws IOException {
        Code02_TwoStack m = new Code02_TwoStack();

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(sc.readLine());
        for (int i = 0; i < N; i++) {
            String[] inputData = sc.readLine().split(" ");
            if (inputData[0].equals("add")) {
                m.add(Integer.parseInt(inputData[1]));
            } else if (inputData[0].equals("peek")) {
                System.out.println(m.peek());
            } else {
                m.poll(); //弹出元素不需要打印
            }
        }
        sc.close();
    }

}


