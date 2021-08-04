package demo1;

import java.util.*;

public class Code07_GetMaxWindowNum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int w = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        String s = getMaxWindowNum(arr, w);
        System.out.println(s);
    }

    public static String getMaxWindowNum(int[] arr, int w) {
        if (arr == null || w == 0 || arr.length == 0) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
                qmax.pollLast(); //必须保证队头的元素是最大的
            }
            qmax.addLast(i);
            if (qmax.peekFirst() == i - w) {
                qmax.pollFirst(); //舍弃队头，过期了
            }
            if(i >= w - 1) {
                res.append(arr[qmax.peekFirst()]).append(" ");
            }
        }
        return res.toString();
    }
}