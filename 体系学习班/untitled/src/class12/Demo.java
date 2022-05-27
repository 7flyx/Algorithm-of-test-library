package class12;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-09
 * Time: 22:50
 * Description:
 */
public class Demo {
    class Solution1 {
        class Difference {
            private int[] diff;

            public Difference(int[] nums) {
                if (nums == null) {
                    return;
                }
                int len = nums.length;
                if (len == 0) {
                    return;
                }
                diff = new int[len];
                diff[0] = nums[0];
                for (int i = 1; i < len; ++i) {
                    diff[i] = nums[i] - diff[i - 1];
                }
            }

            public void increment(int i, int j, int val) {
                diff[i] += val;
                if (j + 1 < diff.length) {
                    diff[j + 1] -= val;
                }
            }

            public int[] result() {
                int len = diff.length;
                int[] res = new int[len];
                res[0] = diff[0];
                for (int i = 1; i < len; ++i) {
                    res[i] = res[i - 1] + diff[i];
                }
                return res;
            }
        }

        public boolean carPooling(int[][] trips, int capacity) {
            int[] diff = new int[1001];
            Difference df = new Difference(diff);
            for (int[] trip : trips) {
                int val = trip[0];
                int i = trip[1];
                int j = trip[2] - 1;
                df.increment(i, j, val);
            }
            int[] res = df.result();
            for (int i = 0; i < res.length; ++i) {
                if (capacity < res[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution {
        public boolean carPooling(int[][] trips, int capacity) {
            if (trips == null || capacity < 0) {
                return false;
            }
            Arrays.sort(trips, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1]; // 以上车时间进行排序，最先上车的在前面
                }
            });
            PriorityQueue<Node> minHeap = new PriorityQueue<>((o1, o2) -> {
                return o1.to - o2.to; // 以结束位置进行排序，小的在前面
            });
            for (int i = 0; i < trips.length; i++) {
                // 首先看有没有要下车的
                while (!minHeap.isEmpty()) {
                    Node node = minHeap.peek();
                    if (node.to <= trips[i][1]) { // 车上的人下车时间，和当前乘客上车时间重合或小，可以下车
                        minHeap.poll();
                        capacity += node.num; // 座位加回来
                    } else { // 没有人下车，跳出循环，准备让乘客上车
                        break;
                    }
                }
                if (capacity < trips[i][0]) { // 当前车位不够，直接false
                    return false;
                }
                capacity -= trips[i][0];
                minHeap.add(new Node(trips[i][0], trips[i][1], trips[i][2]));
            }
            // 循环结束，乘客全部上车，返回true
            return true;
        }

        private class Node {
            public int num; // 乘客数量
            public int from;
            public int to;

            public Node(int num, int from, int to) {
                this.num = num;
                this.from = from;
                this.to = to;
            }
        }
    }

}
