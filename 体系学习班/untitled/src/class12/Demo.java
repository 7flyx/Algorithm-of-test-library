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
                    return o1[1] - o2[1]; // ??????????????????????????????????????????????????????
                }
            });
            PriorityQueue<Node> minHeap = new PriorityQueue<>((o1, o2) -> {
                return o1.to - o2.to; // ?????????????????????????????????????????????
            });
            for (int i = 0; i < trips.length; i++) {
                // ??????????????????????????????
                while (!minHeap.isEmpty()) {
                    Node node = minHeap.peek();
                    if (node.to <= trips[i][1]) { // ?????????????????????????????????????????????????????????????????????????????????
                        minHeap.poll();
                        capacity += node.num; // ???????????????
                    } else { // ??????????????????????????????????????????????????????
                        break;
                    }
                }
                if (capacity < trips[i][0]) { // ???????????????????????????false
                    return false;
                }
                capacity -= trips[i][0];
                minHeap.add(new Node(trips[i][0], trips[i][1], trips[i][2]));
            }
            // ??????????????????????????????????????????true
            return true;
        }

        private class Node {
            public int num; // ????????????
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
