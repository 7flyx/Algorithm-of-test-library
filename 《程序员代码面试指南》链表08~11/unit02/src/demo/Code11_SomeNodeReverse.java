package demo;

import java.io.*;
/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-02
 * Time: 17:57
 */

public class Code11_SomeNodeReverse {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(sc.readLine());
        String[] values = sc.readLine().split(" ");
        Node head = new Node(0);
        Node cur = head;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(Integer.parseInt(values[i]));
            cur = cur.next;
        }
        head = head.next;
        int k = Integer.parseInt(sc.readLine());
        head = someNodeReverse1(head, k);
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }

        sc.close();
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

	//直接交换数据
    public static Node someNodeReverse1(Node head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int[] nums = new int[k];
        Node preNode = head;
        Node cur = head;
        while(cur != null) {
            int i = 0;
            for (i = 0; i < k && cur != null; i++) {
                nums[i] = cur.val;
                cur = cur.next;
            }
            if (i == k) {
                for (i = k - 1; i >= 0; i--) {
                    preNode.val = nums[i];
                    preNode = preNode.next;
                }
            }
        }
        return head;
    }
	
	//用栈来存储每个结点，然后弹出连接
	public static Node someNodeReverse2(Node head, int k) {
        if (head == null || k < 2) {
            return head; 
        }
        //压栈，就结点直接压入栈中，再弹出进行连接
        Node newHead = head;
        Node cur = head;
        Node pre = null; //表示弹出栈后，最后一个结点，需要进行连接下一组的数据
        Node next = null;
        Stack<Node> stack = new Stack<>();
        while (cur != null) {
            next = cur.next;
            stack.push(cur);
            if (stack.size() == k) {
                pre = resign2(stack, pre, next); //返回的是，栈中最后的一个结点，表示下一组的上一结点
                newHead = newHead == head? cur : newHead;
            }
            cur = next;
        }
        return newHead;
    }
    
    public static Node resign2(Stack<Node> stack, Node pre, Node next) {
        Node cur = stack.pop();
        if (pre != null) {
            pre.next = cur;
        }
        Node last = null;
        while (!stack.isEmpty()) {
            last = stack.pop();
            cur.next = last;
            cur = last;
        }
        cur.next = next; //连上下一个结点
        return cur;
    }

	//不使用额外的数据结构，直接在原链表上进行连接
	public static Node someNodeReverse3(Node head, int k) {
        if (head == null || k < 2) {
            return head;
        }
        int count = 1; //计数
        Node cur = head;
        Node pre = null; //指向上一次反转完后的最后一个结点
        Node next = null;
        Node start = null; //反转链表的开始结点
        while (cur != null) {
            next = cur.next;
            if (count == k) {
                start = pre == null? head : pre.next; //确定需要反转链表的开始结点
                head = pre == null? cur : head;
                resign3(pre, start, cur, next);
                pre = start; //反转之后，start就是在后面。pre定位到这里，代表下一组的上一结点
                count = 0;
            }
            count++;
            cur = next;
        }
        return head;
    }
    
    public static void resign3(Node left, Node start, Node end, Node right) {
        Node cur = start;
        Node pre = null;
        Node next = null;
        while (cur != right) { //不与right，也就是不与下一组结点相遇的
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        if (left != null) {
            left.next = end; //连接上新的头部
        }
        start.next = right; //连接上新的尾巴
    }
}
