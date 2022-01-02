import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-01-02
 * Time: 16:08
 * Description:线段树
 * 为了实现，在数组中的一段连续的范围了，快速实现增删改查操作。具体的习题可看LeetCode 699题
 */
public class SegmentTree {
    private int length; //数组的长度再+1，因为0下标位置不需要
    private int[] arr; //原数组的所有数据，只是整体向右移动了一位
    private int[] sum; //某个范围上的总和
    private int[] lazy; //懒数组，为了实现某个范围上的操作，只需改这一个位置的数据
    private int[] change; //update更新的数据
    private boolean[] update; //表示相应的下标位置是否需要进行update

    public SegmentTree(int[] arr) {
        length = arr.length + 1;
        this.arr = new int[length];
        for (int i = 1; i < length; i++) {
            this.arr[i] = arr[i - 1];
        }

        sum = new int[length << 2]; //4倍的长度
        lazy = new int[length << 2];
        change = new int[length << 2];
        update = new boolean[length << 2];
    }

    private void pushUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1]; //当前节点的总和，就等于左右两个孩子的总和
    }

    /**
     * @param rt sum数组的下标
     * @param ln 左子树的节点数
     * @param rn 右子树的节点数
     */
    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true; //将孩子节点改为true
            update[rt << 1 | 1] = true;
            change[rt << 1] = change[rt]; //孩子的change继承父节点
            change[rt << 1 | 1] = change[rt];
            lazy[rt << 1] = 0; //孩子的懒数组归0
            lazy[rt << 1 | 1] = 0;
            sum[rt << 1] = ln * change[rt]; //左孩子的总和
            sum[rt << 1 | 1] = rn * change[rt]; //右孩子的总和
            update[rt] = false;
        }

        //懒数组，往下更新。也就是将当前数组的数据，下分发
        if (lazy[rt] != 0) {
            lazy[rt << 1] += lazy[rt];
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1] += ln * lazy[rt]; //左孩子的总和增加
            sum[rt << 1 | 1] += rn * lazy[rt]; //右孩子的总和增加
            lazy[rt] = 0;
        }
    }

    /**
     * 对sum数组进行初始化
     * @param l  左边界
     * @param r  右边界
     * @param rt sum的下标
     */
    public void build(int l, int r, int rt) {
        if (l == r) {
            sum[rt] = arr[l];
            return;
        }
        int mid = (l + r) >> 1; //中位数
        build(l, mid, rt << 1); //左子树
        build(mid + 1, r, rt << 1 | 1); //右子树
        pushUp(rt); //两边汇总
    }

    /**
     * 在L~R范围内，添加C
     *
     * @param L  目标的左边界
     * @param R  目标的右边界
     * @param C  新增的数量
     * @param l  当前递归的左边界
     * @param r  当前递归的右边界
     * @param rt sum数组的下标
     */
    public void add(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && R >= r) { //被加工的范围超过了当前递归的范围，可以懒
            sum[rt] += C * (r - l + 1); //当前位置的总和+ C*个数
            lazy[rt] += C; //懒数组的参数加C
            return;
        }
        //不能懒，直接再取中位数，递归调用
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            add(L, R, C, l, mid, rt << 1); //递归左子树
        }
        if (R > mid) {
            add(L, R, C, mid + 1, r, rt << 1 | 1); //递归右子树
        }
        pushUp(rt); //当前位置做汇总
    }

    /**
     * L~R范围内，改为新参数
     * @param L 目标的左边界
     * @param R 目标的右边界
     * @param C 目标的新参数
     * @param l 当前递归的左边界
     * @param r 当前递归的右边界
     * @param rt sum数组的下标
     */
    public void update(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && R >= r) { //能够懒到
            update[rt] = true;
            change[rt] = C; //存储的改变的值
            sum[rt] = C * (r - l + 1); //改总和
            lazy[rt] = 0;
            return;
        }

        //不能懒到，只能取中位数，递归
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            update(L, R, C, l, mid, rt << 1); //左子树
        }
        if (R > mid) {
            update(L, R, C, mid + 1, r, rt << 1 | 1); //右子树
        }
        pushUp(rt); //当前位置汇总
    }

    /**
     * 查询累加和
     * @param L  目标的左边界
     * @param R  目标的右边界
     * @param l  当前递归的左边界
     * @param r  当前递归的右边界
     * @param rt sum数组的下标
     * @return 返回总和
     */
    public long query(int L, int R, int l, int r, int rt) {
        if (L <= l && R >= r) {
            return sum[rt]; //直接拿取
        }
        long res = 0;
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            res += query(L, R, l, mid, rt << 1); //左子树
        }
        if (R > mid) {
            res += query(L, R, mid + 1, r, rt << 1 | 1); //右子树
        }
        return res;
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }

}
