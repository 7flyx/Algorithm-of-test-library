package demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Code09_GetNearLess {
    public static void main(String[] args) throws IOException {
        /*
                单调栈结构
                给定一个可能含有重复值的数组 arr，找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i] 小的位置。
                返回所有位置相应的信息。
         */

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        int[] arr = new int[n];
        String[] s = sc.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }
        int[][] res = getNearLess(arr); //数据量大了，没跑完
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            sb.append(res[i][0]).append(" ").append(res[i][1]).append('\n');
        }
        System.out.println(sb);

//
//        int[] left = new int[n];
//        int[] right = new int[n];
//        Arrays.fill(left, -1); //全部填充为-1
//        Arrays.fill(right, -1);
//        method(left, right, arr);
//        for (int i = 0; i < n; i++) {
//            System.out.printf("%d %d\n",left[i],right[i]);
//        }
    }

    public static int[][] getNearLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        Stack<List<Integer>> stack = new Stack<List<Integer>>();
        int[][] res = new int[arr.length][2];
        //遍历数组
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popList = stack.pop();
                int leftPoint = stack.isEmpty()? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popi : popList) {
                    res[popi][0] = leftPoint;
                    res[popi][1] = i;
                }
            }
            //压入数据i
            if (!stack.isEmpty() && arr[i] == stack.peek().get(0)) {
                stack.peek().add(i); //相等的元素，跟栈顶元素放到一个表即可
            } else {
                ArrayList<Integer> list = new ArrayList<>(); //新的元素，需要建表
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> popList = stack.pop();
            int leftPoint = stack.isEmpty()? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popi : popList) {
                res[popi][0] = leftPoint;
                res[popi][1] = -1;
            }
        }
        return res;
    }

    public static void method(int[] left, int[] right, int[] arr) {
        if (arr != null) {
            //遍历所有的数据
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < arr.length; i++) {
                while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                    right[stack.pop()] = i;
                }
                int leftPoint = stack.isEmpty()? -1 : stack.peek();
                if (!stack.isEmpty() && arr[leftPoint] != arr[i]) {
                    left[i] = leftPoint; // 准备压入进去的i，左边的值就是当前的栈顶元素
                } else if(!stack.isEmpty()) { //当前栈顶元素与即将被压入的i，相等
                    left[i] = left[leftPoint]; //即将被压入的i， 他的左边值就是当前栈顶元素的左边值，因为相等嘛
                }
                stack.push(i);
            }
            //循环结束即可，因为剩下的肯定都是-1的情况
        }
    }
}
