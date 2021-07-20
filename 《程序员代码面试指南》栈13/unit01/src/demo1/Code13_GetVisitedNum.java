package demo1;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Code13_GetVisitedNum {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(sc.readLine());
        String[] nums = sc.readLine().split(" ");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(nums[i]);
        }

        System.out.println(getVisitedNum(arr));
        sc.close();
    }

    public static class Record {
        public int height; //山峰的高度
        public int times; //个数

        public Record(int height, int times) {
            this.height = height;
            this.times = times;
        }
    }

    public static int getVisitedNum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        //遍历数组，找到山峰最高的值的下标
        int nextIndex = 0;
        int maxIndex = 0;
        int res = 0;
        int size = arr.length;
        for (int i = 1; i < size; i++) {
            maxIndex = arr[maxIndex] < arr[i]? i : maxIndex;
        }

        Stack<Record> stack = new Stack<>();
        stack.push(new Record(arr[maxIndex], 1));
        nextIndex = getNextIndex(maxIndex, size); //从最高山峰的下一座山开始，顺时针（next方向）

        //nextIndex不回到maxIndex，循环就是继续的
        while (nextIndex != maxIndex) {
            //还是一样，单调栈结构。栈底是最大值，从栈底到栈顶，是递减的
            while (stack.peek().height < arr[nextIndex]) {
                int k = stack.pop().times;
                //这里的k，就是这个高度的山峰，现在有几个
                res += getInternalSum(k) + (k << 1); //2*k + C（2，k） 对
            }
            //压入当前这个位置的山峰
            if (stack.peek().height == arr[nextIndex]) {
                stack.peek().times++; //相同的高度，数量加1
            } else {
                stack.push(new Record(arr[nextIndex], 1));
            }

            //index往后走
            nextIndex = getNextIndex(nextIndex, size);
        }

        //循环结束后，有三种情况，栈里还剩1 、 2、 > 2的不同高度的山峰数
        while (stack.size() > 2) {
            int k = stack.pop().times;
            //这里的k，就是这个高度的山峰，现在有几个
            res += getInternalSum(k) + (k << 1); //2*k + C（2，k） 对
        }

        while (stack.size() == 2) {
            int k = stack.pop().times;
            res += getInternalSum(k) + (stack.peek().times == 1? k : (k << 1));
        }
        res += getInternalSum(stack.pop().times);
        return res;
    }

    public static int getNextIndex(int index, int size) {
        //跟着数组的长度走就行，走到末尾后，接上头部，也就是循环队列的效果
        return index < (size - 1)? (index + 1) : 0;
    }

    public static int getInternalSum(int k) {
        return k == 1? 0 : (k*(k - 1) / 2); //这里就是计算相同高度的山，之间可以看见的对数，类似排列组合
    }
}
