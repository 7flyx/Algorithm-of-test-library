package class32;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-08
 * Time: 15:52
 * Description: IndexTree，算是线段树的另一种形式。也是实现数组区间内的快速增删改查。
 *  与线段树的区别是  能够实现单点更新，比线段树更轻量化
 */
public class Code01_IndexTree {
    public static void main(String[] args) {

    }

    static class IndexTree {
        public int[] nums; // 原数组
        public int[] tree; // 累加和数组
        public int length;

        public IndexTree(int N) {
            this.length = N + 1;
            tree = new int[this.length];
            nums = new int[this.length];
        }

        /**
         * 在index位置插入val值。index从1开始
         * @param val 待插入的值
         * @param index 数组下标
         */
        public void add(int val, int index) {
            if (index < 1 || index >= length) {
                return; // 越界的情况
            }
            nums[index] += val;
            for (int i = index; i < length; i += (i & -i)) { // index位置插入值，会影响后面位置的计算
                tree[i] += val;
            }
        }

        /**
         *  更新index位置的值
         * @param val 更新的值
         * @param index 下标
         */
        public void update(int val, int index) {
            if (index < 1 || index >= length) {
                return;
            }
            int num = val - nums[index]; // 差值
            nums[index] = val;
            for (int i = index; i < length; i += (i & -i)) {
                tree[i] += num; // 累加上 差值
            }
        }

        /**
         *  返回1下标~index下标的累加和
         * @param index 下标
         * @return 返回累加和
         */
        public int query(int index) {
            if (index  < 1 || index >= length) {
                return -1; // 其实返回-1并不合理，应该直接抛出异常
            }
            int ans = 0;
            for (int i = index; i > 0; i -= (i & -i)) {
                ans += tree[i];
            }
            return ans;
        }
    }
}
