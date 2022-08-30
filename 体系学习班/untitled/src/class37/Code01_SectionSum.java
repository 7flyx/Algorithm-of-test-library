package class37;

import java.util.HashSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-29
 * Time: 8:55
 * Description: LeetCode： 327. 区间和的个数 （clas.Code05写过一个归并排序的解法）
 * 给定一个数组arr，和两个整数a和b（a<=b）。求arr中有多少个子数组，累加和在[a,b]这个范围上。返回达标的子数组数量
 */
public class Code01_SectionSum {
    public static void main(String[] args) {
        int[] arr = {2147483647, -2147483648, -1, 0};
        int lower = -1;
        int upper = 0;
        int i = countRangeSum(arr, lower, upper);
        System.out.println(i);
    }

    /**
     * 假设需要计算子数组累加和在[10,60]范围内，在以 i位置 作为子数组的末尾时，有多少子数组满足条件？依次以
     * 0位置、1位置、2位置...最后位置 结尾。依次枚举
     * 为了计算某个子数组的累加和在[10,60]，变向的也就是在计算 0 ~ k (k 在0~i之间)范围上的子数组
     * 累加和在 [preSum[i] - 60, preSum[i] - 10] 这个范围的子数组。
     *
     * @param nums  原数组
     * @param lower 下限值
     * @param upper 上限值
     * @return 返回满足条件的子数组数量
     */
    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || upper < lower) {
            return -1;
        }
        int N = nums.length;
        long[] preSum = new long[N]; // 前缀和数组
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }

        // 根据分析，实则就是需要一个结构，能够快速查询到 < sum的子数组的累加和数量。
        // 所以需要的功能有：添加数据、查询<sum的个数、还要支持重复值的插入
        SizeBalancedTreeSet sbSet = new SizeBalancedTreeSet();
        sbSet.add(0); // 要先插入0。表示一个数都没有的时候，子数组前缀和是0
        int ans = 0;
        for (int i = 0; i < N; i++) {
            long leftBorder = preSum[i] - upper; // 左边界
            long rightBorder = preSum[i] - lower; // 右边界
            long l = sbSet.lessKeySize(leftBorder);
            long r = sbSet.lessKeySize(rightBorder + 1);
            ans += (int) (r - l);
            sbSet.add(preSum[i]); // 要将当前的累加和放入平衡树中
        }
        return ans;
    }

    private static class SBTNode {
        public long value;
        public SBTNode left;
        public SBTNode right;
        public long size; // 不同key的节点数
        public long all; // 当前节点及子树，总的节点数

        public SBTNode(long value) {
            this.value = value;
            this.size = 1;
            this.all = 1;
        }
    }

    private static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>(); // 便于查询当前平衡树 是否插入过某个值

        public void add(long value) {
            boolean contains = set.contains(value);
            root = add(root, value, contains);
            set.add(value);
        }

        private SBTNode add(SBTNode node, long value, boolean contains) {
            if (node == null) {
                return new SBTNode(value);
            }
            node.all++;
            if (value == node.value) {
                return node;
            } else {
                if (!contains) { // 如果当前树上没有这个节点，沿途的size要++
                    node.size++;
                }
                if (value < node.value) {
                    node.left = add(node.left, value, contains);
                } else {
                    node.right = add(node.right, value, contains);
                }
            }
            return maintain(node);
        }

        private SBTNode maintain(SBTNode node) {
            if (node == null) {
                return null;
            }
            long curSize = node.size;
            long leftSize = node.left != null ? node.left.size : 0;
            long rightSize = node.right != null ? node.right.size : 0;
            long leftLeftSize = node.left != null && node.left.left != null ? node.left.left.size : 0;
            long leftRightSize = node.left != null && node.left.right != null ? node.left.right.size : 0;
            long rightRightSize = node.right != null && node.right.right != null ? node.right.right.size : 0;
            long rightLeftSize = node.right != null && node.right.left != null ? node.right.left.size : 0;
            if (leftLeftSize > rightSize) { // LL
                node = rightRotate(node);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (leftRightSize > rightSize) { // LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightLeftSize > leftSize) { // RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightRightSize > leftSize) { // RR
                node = leftRotate(node);
                node.left = maintain(node.left);
                node = maintain(node);
            }
            return node;
        }


        // 返回 < key的节点数
        public long lessKeySize(long key) {
            SBTNode node = root;
            long ans = 0;
            while (node != null) {
                if (node.value == key) {
                    return ans + (node.left != null ? node.left.all : 0);
                } else if (node.value < key) { // 往右子树走
                    // 累加上左子树的all 和 当前节点值的个数
                    ans += node.all - (node.right != null ? node.right.all : 0);
                    node = node.right;
                } else { // 往左子树走
                    node = node.left;
                }
            }
            return ans;
        }

        // 返回 > key的节点数
        public long moreKeySize(long key) {
            // 首先查找< key+1 的节点数，剩下的就是>key 的节点数
            return root != null ? (root.all - lessKeySize(key + 1)) : 0;
        }

        private SBTNode rightRotate(SBTNode node) {
            // 提前计算出当前节点值的个数
            long same = node.all - node.left.all - (node.right != null ? node.right.all : 0);
            SBTNode leftNode = node.left;
            node.left = leftNode.right;
            leftNode.right = node;
            leftNode.size = node.size; // 当前节点下的所有不同key
            leftNode.all = node.all; // 当前节点下的所有key
            node.size = (node.left != null ? node.left.size : 0) + (node.right != null ? node.right.size : 0) + 1;
            node.all = (node.left != null ? node.left.all : 0) + (node.right != null ? node.right.all : 0) + same;
            return leftNode;
        }

        private SBTNode leftRotate(SBTNode node) {
            // 提前计算出当前节点值的个数
            long same = node.all - (node.left != null ? node.left.all : 0) - node.right.all;
            SBTNode rightNode = node.right;
            node.right = rightNode.left;
            rightNode.left = node;
            rightNode.all = node.all;
            rightNode.size = node.size;
            node.all = (node.left != null ? node.left.all : 0) + (node.right != null ? node.right.all : 0) + same;
            node.size = (node.left != null ? node.left.size : 0) + (node.right != null ? node.right.size : 0) + 1;
            return rightNode;
        }
    }
}
