package demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Code12_GetMountNumNoRepeat {
    public static int flag = 0;

    public static void main(String[] args) throws IOException {
        /*
                给定一个不含有负数且没有重复值的数组 arr，请问有多少对山峰能够相互看见？

                第一行一个整数 T，表示测试数据的组数。
                每组数据的第一行有三个数字 n, p, m，其中 n 表示 山峰的数量，
                山峰的高度数组等于 1 - p 的 p! 个全排列按字典序排序后的第 m 个全排列的前 n 项
         */

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(sc.readLine());
        String[] nums = sc.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int p = Integer.parseInt(nums[1]);
        int m = Integer.parseInt(nums[2]);
        int[] arr = new int[p];
        for (int i = 0; i < p; i++) {
            arr[i] = i + 1;
        }
        int[] res = new int[p];
        getMountNum(arr, 0, res, m);

        if (p >= n) {
            int num = n != 0 && n != 1 && n != 2? (2 * n - 3) : (n == 0 || n == 1? 0 : 1);
            System.out.println(num);
        } else {
            int num = p != 0 && p != 1 && p != 2? (2 * p - 3) : (p == 0 || p == 1? 0 : 1);
            System.out.println(num);
        }
    }

    public static void getMountNum(int[] arr, int index, int[] res, int m) {
        if (index == arr.length) {
            flag++;
            return;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            res[index] = arr[index];
            getMountNum(arr, index + 1, res, m);
            if (flag == m) {
                break;
            }
            swap(arr, index, i);
            res[index] = arr[index];
        }
    }

    public static void swap(int[] arr, int left, int right) {
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }
}
