package greedarithmetic;

import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:36
 * Description:无重叠区间
 * https://leetcode-cn.com/problems/non-overlapping-intervals/
 */
public class Code08_NotRepeatRange {

    private static class Node {
        int start, end;
        public Node(int start, int end) {
            this.start  = start;
            this.end = end;
        }
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return 0;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>((o1, o2)->{
            if (o1.end == o2.end) {
                return o1.start - o2.start;
            }
            return o1.end - o2.end;
        });
        //以每个节点的结束作为标志，谁结束的最早，谁就在前面
        for (int[] arr : intervals) {
            minHeap.add(new Node(arr[0], arr[1]));
        }

        int preEnd = minHeap.poll().end;
        int size = intervals.length - 1;
        int used = 1;
        while (size-- > 0) {
            Node node = minHeap.poll();
            if (preEnd <= node.start) {
                preEnd = node.end;
                used++;
            }
        }
        return intervals.length - used;
    }
}
