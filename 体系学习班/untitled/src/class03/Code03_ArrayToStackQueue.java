package class03;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 17:14
 * Description: 用数组实现栈和队列
 */
public class Code03_ArrayToStackQueue {
    public static void main(String[] args) {
//        ArrayToStack<Integer> stack = new ArrayToStack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//
//        System.out.println(stack.size());
//        System.out.println(stack.peek());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());

        CycleQueue queue = new CycleQueue(5);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        System.out.println(queue.peek()); // 1
        System.out.println(queue.size()); // 5
        System.out.println(queue.poll()); // 弹出1
        queue.add(6);
        //queue.add(7); // 会报错

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }

    // 用数组实现栈
    public static class ArrayToStack<T> {
        private ArrayList<T> arr;
        private int size;

        public ArrayToStack() {
            arr = new ArrayList<>();
        }

        public void push(T value) {
            arr.add(value);
            size++;
        }

        public T pop() {
            if (size == 0) {
                throw new RuntimeException("栈为空");
            }
            size--;
            return arr.remove(size); // 返回最后一个元素，并删除
        }

        public T peek() {
            if (size == 0) {
                throw new RuntimeException("栈为空");
            }
            return arr.get(size - 1); // 返回最后一个元素
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    // 用数组实现循环队列
    public static class CycleQueue {
        private int[] arr;
        private int head;
        private int tail;
        private int size;

        public CycleQueue(int length) {
            arr = new int[length];
        }

        public void add(int value) {
            if (size == arr.length) {
                throw new RuntimeException("队列满了");
            }

            arr[tail] = value;
            tail = (tail + 1) % arr.length; //更新tail的值
            size++;
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("队列为空");
            }
            int res = arr[head];
            head = (head + 1) % arr.length; // 更新head的值
            size--;
            return res;
        }

        public int peek() {
            if (size == 0) {
                throw new RuntimeException("队列为空");
            }
            return arr[head];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }
}
