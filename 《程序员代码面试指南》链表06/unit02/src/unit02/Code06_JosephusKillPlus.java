package unit02;

import java.util.Scanner;

/**
 * Created by flyx
 * Description:单链表的约瑟夫环问题。进阶版，也就是大数问题
 * User: 听风
 * Date: 2021-07-30
 * Time: 12:16
 */
public class Code06_JosephusKillPlus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //结点数
        int m = sc.nextInt(); //报m的结点，over
        Node head = new Node(0);
        Node cur = head;
        for (int i = 1; i <= n; i++) {
            cur.next = new Node(i);
            cur = cur.next;
        }
        head = head.next;
        System.out.println(josephusKill2(head, n, m).val);
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    public static Node josephusKill2(Node head, int i, int m) {
        if (head == null || head.next == null || m < 1) {
            return head;
        }

        int tmp = getLive(i, m);
        while (--tmp > 0) {
            head = head.next;
        }
        return head;
    }

    //计算的是，哪一个结点活了下来，返回的是编号，从头结点编号为1，开始
    //递归的版本
    public static int getLive(int i, int m) {
        if (i == 1) {
            return 1;
        }
        return (getLive(i - 1, m) + m - 1) % i + 1; //（新编号 + m - 1） % i + 1 。根据x%i的函数图像进行推导
    }

    //迭代的版本
    public static int getLive2(int i, int m) {
        if (i == 1) {
            return 1;
        }
        int res = 1;
        for (int k = 2; k <= i; k++) {
            res = (res + m - 1) % k + 1 ;
        }
        return res;
    }
}
