package unit01;

import java.util.*;
import java.io.*;

public class Code01_getMin {
    private final Stack<Integer> stackData;
    private final Stack<Integer> stackMin;

    public Code01_getMin() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public void push(int newNum) {
        if (stackMin.isEmpty() || newNum <= stackMin.peek() ) {
            stackMin.push(newNum);
        }
        stackData.push(newNum);
    }

    public int pop() {
        if (stackData.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        int value = stackData.pop();
        if (value == getmin()) {
            stackMin.pop();
        }
        return value;
    }

    public int getmin() {
        if (stackMin.isEmpty()) {
            throw new RuntimeException("Your stack is empty.");
        }
        return stackMin.peek();
    }

    public static void main(String[] args) throws IOException {
        Code01_getMin m = new Code01_getMin();

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(sc.readLine());

        for (int i = 0; i < N; i++) {
            String[] inputdata = sc.readLine().split(" "); //将所有的操作读取进来
            if (inputdata[0].equals("push")) {
                m.push(Integer.parseInt(inputdata[1])); //自动转换为int
            } else if (inputdata[0].equals("pop")) {
                m.pop(); //不需要输出
            } else {
                System.out.println(m.getmin());
            }
        }
        sc.close(); //缓冲输入流  关闭
    }
}
