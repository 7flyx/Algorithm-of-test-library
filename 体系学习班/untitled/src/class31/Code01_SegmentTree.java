package class31;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-31
 * Time: 21:02
 * Description: 线段树。在数组的某个连续区间内，快速的增删改查操作。
 */
public class Code01_SegmentTree {
    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int start = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int end = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(start, end, root);
        long sum = seg.query(L, R, start, end, root);
        System.out.println(sum);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, start, end, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, start, end, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        sum = seg.query(L, R, start, end, root);
        System.out.println(sum);
    }

    private static class SegmentTree {
        private int[] tree; // 从下标1位置开始填入
        private int[] change; // 存储修改的值
        private int[] lazy; // 懒更新数组
        private int[] sum; // 存储某个范围内的数据总和，根据需求而定。
        private boolean[] update; // 记录相应下标位置是否需要进行更新
        private int length;

        public SegmentTree(int[] arr) {
            this.length = arr.length + 1;
            tree = new int[length];
            for (int i = 1; i < length; i++) { // 将数据填充到tree中
                this.tree[i] = arr[i - 1];
            }
            change = new int[length << 2];
            lazy = new int[length << 2];
            sum = new int[length << 2];
            update = new boolean[length << 2];
        }

        // 对sum数组进行初始化，也就是计算出相应区间的总和
        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = tree[l];
                return;
            }
            int mid = (r + l) / 2;
            build(l, mid, rt << 1); // 递归左子树
            build(mid + 1, r, rt << 1 | 1); // 递归右子树
            pushUp(rt); // 两边汇总
        }

        /**
         * 在l和r范围内，添加某个数
         *
         * @param L   需要修改数据的范围的左边界
         * @param R   需要修改数据的范围的右边界
         * @param l   当前左边界
         * @param r   当前右边界
         * @param num 添加的值
         * @param rt  lazy数组的下标
         */
        public void add(int L, int R, int num, int l, int r, int rt) {
            if (L <= l && R >= r) { // 当前递归范围，超出了修改数据的范围，可以懒
                sum[rt] += (r - l + 1) * num; // 总和
                lazy[rt] += num;
                return;
            }
            // 不能懒的情况，取中位数进行递归
            int mid = (r + l) / 2;
            // 先将上次lazy数组留下的数据向下分发之后，再进行调用
            // mid - l + 1是左子树的节点数
            // r - mid 是右子树的节点数
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) { // 递归左子树
                add(L, R, num, l, mid, rt << 1);
            }
            if (R > mid) { // 递归右子树
                add(L, R, num, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt); // 等左右子树递归完，再做汇总
        }

        /**
         * L、R范围内更新值
         *
         * @param L   更新范围左边界
         * @param R   更新范围右边界
         * @param num 更新值
         * @param l   当前递归的左边界
         * @param r   当前递归的右边界
         * @param rt  change数组的下标
         */
        public void update(int L, int R, int num, int l, int r, int rt) {
            if (L <= l && R >= r) { // 当前递归范围超过了待更新的范围
                update[rt] = true;
                change[rt] = num;
                sum[rt] = (r - l + 1) * num; // 重新计算sum
                lazy[rt] = 0; // lazy数组对应的位置要归0
                return;
            }
            // 没有懒到，取中位数，往下递归
            int mid = l + ((r - l) >> 1);
            // 先往下分发数据，然后才是递归调用
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, num, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, num, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt); // 汇总数据
        }

        // 查询L和R范围内的sum总和
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = l + ((r - l) >> 1);
            pushDown(rt, mid - l + 1, r - mid); // 往下分发
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        // lazy数组向下分发数据
        private void pushDown(int rt, int leftChildSum, int rightChildSum) {
            // 用于add方法
            if (lazy[rt] != 0) {  // 懒数组的数据不为0，说明要往下分发
                sum[rt << 1] += lazy[rt] * leftChildSum; //左子树的总和
                sum[rt << 1 | 1] += lazy[rt] * rightChildSum; // 右子树的总和
                // 更新左右子树的lazy数组
                lazy[rt << 1] += lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                lazy[rt] = 0; // 当然位置的lazy值归0
            }
            // 用于update方法
            if (update[rt]) { // 是否需要更新的情况
                // 标志update数组，表示需要更新
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                // 更新左右子树的change值
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // 更新左右子树的sum总和
                sum[rt << 1] = change[rt] * leftChildSum;
                sum[rt << 1 | 1] = change[rt] * rightChildSum;
                // 左右子树的lazy数组都需要归0
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                update[rt] = false; // 当前位置的数据分发完了，就改回false
            }
        }

        // 汇总数据
        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1]; // 将左右子树的数据进行汇总
        }
    }
}
