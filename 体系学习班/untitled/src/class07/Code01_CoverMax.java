package class07;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-03
 * Time: 16:09
 * Description: 第8节 加强堆
 * 题意： 给定一个二维数组，里面存储的是线段的起始点和结束点，形如：{{1，3}， {2,5}，{5,7}}
 * 问你最多有多少条线段是重合的？ （连接点不算重合，比如2,5 和 5,7.二者不算重合线段）
 * https://www.nowcoder.com/questionTerminal/1ae8d0b6bb4e4bcdbf64ec491f63fc37
 */
public class Code01_CoverMax {
    private static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] lines = new int[n][2];
        for (int i = 0; i < n; i++) {
            lines[i][0] = sc.nextInt();
            lines[i][1] = sc.nextInt();
        }
        System.out.println(coverMax1(lines));
        System.out.println(coverMax2(lines));
    }

    // 抽取两个点之间的小数，再遍历整个数组。时间复杂度O((max - min) * N)
    public static int coverMax1(int[][] lines) {
        if (lines == null || lines.length == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]); // 开头
            max = Math.max(max, lines[i][1]); // 结尾
        }

        int res = 0;
        // 从两点的中间开始计算，比如 1.5, 2.5, 3.5等，因为这样就能避开两点重合的而不算的情况
        for (double num = min + 0.5; num < max; num++) {
            // 拿着num这个值，遍历整个数组进行统计
            int tmp = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < num && lines[i][1] > num) {
                    tmp++;
                }
            }
            res = Math.max(res, tmp);
        }
        return res;
    }

    // 用小根堆，堆里存储的是每个线段的结尾数值。
    // 流程：先对整个数组，以线段的起始点进行排序，小-》大
    // 然后从左开始遍历，拿起起点，堆里比起点值小的或相等的，都弹出堆。
    // 最后压入当前线段的终点，此刻堆里有几个线段，就是以当前线段的起点为左边界，计算出来的值
    public static int coverMax2(int[][] lines) {
        if (lines == null || lines.length == 0) {
            return 0;
        }

        Line[] arr = getLinesArray(lines);
        Arrays.sort(arr, (o1, o2) -> {
            return o1.start - o2.start; // 以起点进行排升序
        });

        // 默认的就是小根堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            // 堆里的终点值比当前的起点值小，就弹出，不要
            while (!minHeap.isEmpty() && minHeap.peek() <= arr[i].start) {
                minHeap.poll();
            }

            minHeap.add(arr[i].end); // 压入终点值
            res = Math.max(res, minHeap.size()); // 当前结果就是堆里的元素个数
        }
        return res;
    }

    private static Line[] getLinesArray(int[][] lines) {
        Line[] arr = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            arr[i] = new Line(lines[i][0], lines[i][1]);
        }
        return arr;
    }

}
