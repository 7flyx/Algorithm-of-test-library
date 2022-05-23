package class03;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 16:38
 * Description: 用链表实现 栈和队列
 */

class Solution {
    private static class Node {
        public int value;
        public Node next;

        public Node(){}
        public Node(int value) {
            this.value = value;
        }
    }
}

public class Code02_LinkedListToStackQueue {
    public static void main(String[] args) {
        LinkedListToQueue<Integer> queue = new LinkedListToQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.peek());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    // 用双链表实现栈结构
    public static class LinkedListToStack<T> {
        private DoubleNode<T> top; // 只需要记录栈顶位置即可
        private int size; // 统计元素个数

        public void push(T value) {
            if (this.top == null) { // 还没有节点的时候，需要建立头节点
                this.top = new DoubleNode<>(value);
            } else {
                this.top.next = new DoubleNode<>(value);
                this.top.next.last = this.top; // 后继节点的前驱指针
                this.top = this.top.next;
            }
            this.size++;
        }

        public T pop() {
            if (this.top == null) {
                throw new RuntimeException("栈为空");
            }
            T res = this.top.value;
            DoubleNode<T> pre = this.top.last;
            if (pre != null) { // 前面还有节点的情况
                pre.next = null; // 断开连接
            }
            this.top.last = null;
            this.top = pre;

            this.size--;
            return res;
        }

        public T peek() {
            if (this.top == null) {
                throw  new RuntimeException("栈为空");
            }
            return top.value;
        }

        public boolean isEmpty() {
            return this.top == null;
        }

        public int size() {
            return size;
        }
    }

    // 用双链表实现队列结构
    public static class LinkedListToQueue<T> {
        private DoubleNode<T> head;
        private DoubleNode<T> tail;
        private int size;

        public void add(T value) {
            if (tail == null) { // 空的时候，头尾都需要改
                head = new DoubleNode<>(value);
                tail = head;
            } else {
                tail.next = new DoubleNode<>(value);
                tail.next.last = tail; // 新节点的前驱指针
                tail = tail.next;
            }
            size++;
        }

        public T poll() {
            if (head == null) {
                throw new RuntimeException("队列为空");
            }
            T res = head.value;
            DoubleNode<T> next = head.next;
            if (next == null) { // 也就是只有一个节点的时候
                head = null;
                tail = null;
            } else {
                head.next = null; // 先将当前节点的下一指针置空
                next.last = null; // 新的头节点的前驱指针置空，使旧的节点被回收
                head = next; // 更新为新的节点
            }
            size--;
            return res;
        }

        public T peek() {
            if (head == null) {
                throw new RuntimeException("队列为空");
            }
            return head.value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }
}
