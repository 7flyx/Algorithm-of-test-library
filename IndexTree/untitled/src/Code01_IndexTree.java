/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-01-03
 * Time: 21:30
 * Description:在前线段树的基础之上，重新改了一下。IndexTree是单点更新的，而线段树是对一个范围内进行更新的，
 * 好处就在于，IndexTree可以很简答的改成二维数组，三维数组的形式/
 * 玩法：从左到右计算前缀和，每次计算的时候，看看前面已经计算好的所有数据中，是否有相同长度的组合，类似于连连看的
 * 形式。
 */
public class Code01_IndexTree {

    private static class IndexTree {
        private int[] help; //保存前缀和的数据
        private int N; //长度

        public IndexTree(int size) {
            N = size;
            help = new int[N + 1];//还是舍弃下标0位置
        }

        //返回index位置的前缀和
        public int sum(int index) {
            int res = 0;
            while (index > 0) {
                res += help[index];
                index -= index & -index; //减去最靠右的1
            }
            return res;
        }

        //改变数组中，某一个位置的值。前缀和也是需要改的
        public void add(int index, int d) {
            while (index <= N) {
                help[index] += d;
                index += index & -index; //下一个影响的位置，就是累加上当前index最靠右的1
            }
        }
    }

    public static class IndexTree2 {
        private int[] nums; //原数组
        private int[] tree; //累加和数组
        private int length; //数组长度

        public IndexTree2(int N) {
            this.length = N + 1; //要舍弃0下标的空间
            this.nums = new int[length];
            this.tree = new int[length];
        }

        //在index位置放入，val值
        public void add(int index, int val) {
            if (index < 1 || index >= length) {
                return; //越界的情况
            }
            nums[index] += val; //改动原数组
            for (int i = index; i < length; i += (i & -i)) {
                tree[i] += val; //改累加和数组
            }
        }

        public void update(int index, int newVal) {
            if (index < 1 || index >= length) {
                return;//越界的情况
            }
            int value = newVal - nums[index]; //计算与之前的差值，最后累加到tree数组即可
            nums[index] = newVal; //改为新的数据
            for (int i = index; i < length; i += (i & -i)) {
                tree[i] += value; //累加差值即可
            }
        }

        //查询1 ~ index位置的累加和
        public int query(int index) {
            if (index < 1 || index >= length) {
                return -1; //越界的情况
            }
            int res = 0;
            for (int i = index; i > 0; i -= (i & -i)) {
                res += tree[i];
            }
            return res;
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 20000;
        IndexTree tree = new IndexTree(N);
        IndexTree2 tree2 = new IndexTree2(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                tree2.add(index, add);
            } else {
                if (tree.sum(index) != tree2.query(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }


}
