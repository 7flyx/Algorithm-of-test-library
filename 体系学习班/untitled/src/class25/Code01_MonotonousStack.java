package class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-08
 * Time: 20:19
 * Description: 单调栈结构---细分为单调递增栈和单调递减栈。用于计算某一个位置的数，它左右两边最近的比它小（大）的数。
 */
public class Code01_MonotonousStack {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 5, 4, 7, 2, 1, 7};
//        int[][] res = getNearLessNoRepeat(arr);
        int[][] res2 = getNearLessRepeat(arr);
        for (int i = 0; i < arr.length; i++) {
//            System.out.println(Arrays.toString(res[i]));
            System.out.println(Arrays.toString(res2[i]));
        }
    }

    // arr数组没有重复值的情况
    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[][]{};
        }
        Stack<Integer> stack = new Stack<>(); // 单调递减栈
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) { // 此处该大小于，就可以切换递增和递减栈
                int num = stack.pop();
                int left = stack.isEmpty()? -1 : stack.peek();
                res[num][0] = left; // 左边
                res[num][1] = i; // 右边
            }
            stack.push(i);
        }
        // 数组遍历完之后，栈里还剩的元素就是右边没有值的情况
        while(!stack.isEmpty()) {
            int num = stack.pop();
            int left = stack.isEmpty()? -1 : stack.peek();
            res[num][0] = left; // 左边
            res[num][1] = -1; // 右边没有值
        }
        return res;
    }

    // arr数组有重复值的情况---在之前的基础之上，用集合存储即可
    public static int[][] getNearLessRepeat(int[] arr) {
        if (arr ==  null || arr.length == 0) {
            return new int[][]{};
        }
        Stack<List<Integer>> stack = new Stack<>(); // 单调递减栈
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popList = stack.pop();
                // 获取此时栈顶的数组中，最后一个数
                int left = stack.isEmpty()? -1 : stack.peek().get(stack.peek().size() - 1);
                for (int num : popList) { // 填放数据
                    res[num][0] = left;
                    res[num][1] = i;
                }
            }
            // 当前栈顶的元素和arr[i] 相等，融为一体
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        // 数组遍历完之后，处理栈里剩下的元素
        while (!stack.isEmpty()) {
            List<Integer> popList = stack.pop();
            int left = stack.isEmpty()? -1 : stack.peek().get(stack.peek().size() - 1);
            for (int num : popList) {
                res[num][0] = left; // 左边
                res[num][1] = -1; // 右边没有值
            }
        }
        return res;
    }
}
