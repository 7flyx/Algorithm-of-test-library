package unit01;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Code11_GetArrayNum {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = sc.readLine().split(" ");
        String[] data = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int num = Integer.parseInt(nums[1]);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(data[i]);
        }

        System.out.println(getNum(arr, num));
    }

    public static int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //与“生成窗口数组的最大值”的队列一样的性质
        LinkedList<Integer> qmin = new LinkedList<>();
        LinkedList<Integer> qmax = new LinkedList<>();
        //以i表示子数组的开头，以j表示子数组的末尾
        //arr[i...j] 计算这个范围内的最大值以及最小值
        int i = 0;
        int j = 0;
        int res = 0;
        while (i < arr.length) {
            while (j < arr.length) {
                while (!qmin.isEmpty() && arr[j] <= arr[qmin.peekLast()]) {
                    qmin.pollLast();
                }
                qmin.addLast(j);

                while (!qmax.isEmpty() && arr[j] >= arr[qmax.peekLast()]) {
                    qmax.pollLast();
                }
                qmax.addLast(j);

                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                j++;
            }
            res += j - i; //有多少个子数组，当前就有多少个结果
            if (qmin.getFirst() == i) { //最小值是i的情况
                qmin.pollFirst();
            }
            if (qmax.getFirst() == i) { //最大值是i的情况
                qmax.pollFirst();
            }
            i++;
        }
        return res;
    }
}
