package unit02;

import java.util.*;


public class Code01_MargeCommonList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node head1 = new Node(0);
        Node cur = head1;
        for (int i = 0; i < n; i++) {
            cur.next = new Node(sc.nextInt());
            cur = cur.next;
        }
        head1 = head1.next;

        Node head2 = new Node(0);
        cur = head2;
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            cur.next = new Node(sc.nextInt());
            cur = cur.next;
        }
        head2 = head2.next;

        printCommonList(head1, head2);

    }

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
            next = null;
        }
    }

    public static void printCommonList(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return;
        }

        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                head1 = head1.next;
            } else if (head1.val > head2.val){
                head2 = head2.next;
            } else {
                System.out.print(head1.val + " ");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
    }
}

