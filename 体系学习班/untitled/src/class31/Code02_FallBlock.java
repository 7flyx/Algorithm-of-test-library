package class31;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-05
 * Time: 9:08
 * Description: LeetCode699 掉落的方块
 */
public class Code02_FallBlock {
    public static void main(String[] args) {
        int[][] positions = {{6, 1}, {9, 2}, {2, 4}};
        Solution solution = new Solution();
        List<Integer> list = solution.fallingSquares(positions);
        System.out.println(Arrays.toString(list.toArray()));

    }

    static class Solution {
        public List<Integer> fallingSquares(int[][] positions) {
            if (positions == null) {
                return new ArrayList<>();
            }
            HashMap<Integer, Integer> map = getCompressArr(positions);
            int N = map.size();
            SegmentTree segmentTree = new SegmentTree(N);
            List<Integer> ans = new ArrayList<>();
            int max = 0; // 全局的最大高度值
            for (int i = 0; i < positions.length; i++) {
                int L = map.get(positions[i][0]); // 方块的左边界
                int R = map.get(positions[i][0] + positions[i][1] - 1); // 方块的右边界
                int height = segmentTree.query(L, R, 1, N, 1) + positions[i][1]; // 当前更新的高度值
                max = Math.max(max, height);
                ans.add(max);
                segmentTree.update(L, R, 1, N, 1, height); // 更新L、R范围的高度值
            }
            return ans;
        }

        /**
         * 因为线段树中的数组不好开辟空间的大小，所以题目给的数组的信息，需要进行压缩统计
         *
         * @param positions 原数组
         * @return 每个左右边界，在整个positions中的顺序
         */
        private HashMap<Integer, Integer> getCompressArr(int[][] positions) {
            TreeSet<Integer> pos = new TreeSet<>();
            for (int[] arr : positions) {
                pos.add(arr[0]);
                pos.add(arr[0] + arr[1] - 1); // arr[1]是方块的宽度。得到当前方块的右边界
            }
            HashMap<Integer, Integer> ans = new HashMap<>();
            int count = 0; // 方块的顺序
            for (Integer index : pos) {
                ans.put(index, ++count); // 从1开始计数
            }
            return ans;
        }

        private static class SegmentTree {
            private int[] height; // 高度数组，也就是原先的sum数组
            private int[] change;
            private boolean[] update;
            private int length;

            public SegmentTree(int size) {
                length = size + 1;
                height = new int[length << 2];
                change = new int[length << 2];
                update = new boolean[length << 2];
            }

            /**
             * @param L     待查询范围的左边界
             * @param R     待查询范围的右边界
             * @param left  当前递归的左边界
             * @param right 当前递归的右边界
             * @param rt    根节点下标
             * @param num   更新的值
             */
            public void update(int L, int R, int left, int right, int rt, int num) {
                if (L <= left && R >= right) { // 可以懒
                    change[rt] = num;
                    height[rt] = num; // height数组更新
                    update[rt] = true;
                    return;
                }
                // 二分，往下递归
                int mid = left + ((right - left) >> 1);
                // 先往下分发lazy数组的数据
                pushDown(mid - left + 1, right - mid, rt);
                if (L <= mid) {
                    update(L, R, left, mid, rt << 1, num);
                }
                if (R > mid) {
                    update(L, R, mid + 1, right, rt << 1 | 1, num);
                }
                pushUP(rt); // 汇总数据
            }

            private void pushUP(int rt) {
                height[rt] = Math.max(height[rt << 1], height[rt << 1 | 1]);
            }

            private void pushDown(int leftChildSum, int rightChildSum, int rt) {
                if (update[rt]) {
                    height[rt << 1] = change[rt];
                    height[rt << 1 | 1] = change[rt];
                    change[rt << 1] = change[rt];
                    change[rt << 1 | 1] = change[rt];
                    update[rt << 1] = true;
                    update[rt << 1 | 1] = true;
                    update[rt] = false;
                }
            }

            public int query(int L, int R, int left, int right, int rt) {
                if (L <= left && R >= right) {
                    return height[rt];
                }
                int ans = 0;
                int mid = left + ((right - left) >> 1);
                pushDown(mid - left + 1, right - mid, rt);
                if (L <= mid) {
                    ans = Math.max(ans, query(L, R, left, mid, rt << 1));
                }
                if (R > mid) {
                    ans = Math.max(ans, query(L, R, mid + 1, right, rt << 1 | 1));
                }
                return ans;
            }
        }
    }
}
