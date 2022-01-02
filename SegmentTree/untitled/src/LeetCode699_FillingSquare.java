import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-01-02
 * Time: 20:30
 * Description: 类似于俄罗斯方法，从上往下落，求最大高度
 */
public class LeetCode699_FillingSquare {

    //坐标没压缩，会导致数组越界
    public List<Integer> fallingSquares1(int[][] positions) {
        if (positions == null) {
            return new ArrayList<>();
        }

        int N = positions.length;
        SegmentTree seg = new SegmentTree(N);
        List<Integer> res = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < N; i++) {
            int L = positions[i][0]; //左边界
            int R = positions[i][1]; //右边界
            int height = positions[i][1] + seg.query(L, R - 1, 1, N, 1);
            max = Math.max(max, height);
            res.add(max);
            seg.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        if (positions == null) {
            return new ArrayList<>();
        }
        HashMap<Integer, Integer> map = index(positions); //坐标压缩
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    private static class SegmentTree {
        private int length; //长度
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int N) {
            length = N + 1;
            max = new int[length << 2];
            change = new int[length << 2];
            update = new boolean[length << 2];
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && R >= r) {
                change[rt] = C; //新高度
                update[rt] = true;
                max[rt] = C;
                return;
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1); //左子树
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1); //右子树
            }
            pushUp(rt); //更新当前位置的值
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return max[rt];
            }
            int left = 0;
            int right = 0;
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }

        private void pushDown(int rt, int ln, int rn) {
            //因为没有涉及到add方法，所以这里只需要写update的代码
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]); //两边取最大值
        }
    }
}
