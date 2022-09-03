package class37;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-29
 * Time: 15:44
 * Description:有一个滑动窗口：
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * 想知道每一个窗口状态的中位数。
 * 题意：给定一个窗口大小和一个数组，计算相应的子数组的中位数
 */
public class Code02_SlidingWindowMedian {
    public static void main(String[] args) {

    }

    private static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            // 首先比value值，value值相等的情况，才是比较index
            return value != o.value ? Integer.valueOf(value).compareTo(o.value) :
                    Integer.valueOf(index).compareTo(o.index);
        }
    }

    private static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> l;
        public SBTNode<K> r;
        public int size;

        public SBTNode(K key) {
            this.key = key;
            size = 1;
        }
    }

    private static class SizeBalancedTreeMap<K extends Comparable<K>> {
        private SBTNode<K> root;

        private SBTNode<K> leftRotate(SBTNode<K> node) {
            SBTNode<K> rightNode = node.r;
            node.r = rightNode.l;
            rightNode.l = node;
            rightNode.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<K> rightRotate(SBTNode<K> node) {
            SBTNode<K> leftNode = node.l;
            node.l = leftNode.r;
            leftNode.r = node;
            leftNode.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return leftNode;
        }

        public void add(K key) {
            if (key == null) {
                throw new RuntimeException("key is invalid.");
            }

            root = add(root, key);
        }

        private SBTNode<K> add(SBTNode<K> node, K key) {
            if (node == null) {
                return new SBTNode<>(key);
            } else {
                node.size++;
                if (key.compareTo(node.key) < 0) { // key小
                    node.l = add(node.l, key);
                } else { // key大
                    node.r = add(node.r, key);
                }
            }
            // 维持平衡性
            return maintain(node);
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("key is invalid.");
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        private SBTNode<K> delete(SBTNode<K> node, K key) {
            node.size--;
            if (key.compareTo(node.key) < 0) {
                node.l = delete(node.l, key);
            } else if (key.compareTo(node.key) > 0) {
                node.r = delete(node.r, key);
            } else { // 相等的情况
                if (node.l == null && node.r == null) {
                    node = null; // C++,需要释放空间
                } else if (node.r == null) { // 左子树不为空，右子树为空
                    node = node.l;
                } else if (node.l == null) { // 左子树为空，右子树不为空
                    node = node.r;
                } else { // 左右子树都不为空
                    SBTNode<K> pre = null; // des的父节点
                    SBTNode<K> des = node.r;
                    des.size--;
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.l = des.r;
                        des.r = node.r;
                    }
                    des.l = node.l;
                    des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
                    node = des;
                }
            }
            return maintain(node); // 可以选择在删除时，不维持平衡。在add的时才维持平衡
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SBTNode<K> lastNode = findLastNode(key); // 查找相等的，或者离key的父节点
            return lastNode != null && key.compareTo(lastNode.key) == 0;
        }

        // 查找等于key的，或者是 离key节点上一层的父节点
        private SBTNode<K> findLastNode(K key) {
            SBTNode<K> pre = root;
            SBTNode<K> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        private SBTNode<K> maintain(SBTNode<K> node) {
            if (node == null) {
                return null;
            }
            int leftSize = node.l != null ? node.l.size : 0;
            int rightSize = node.r != null ? node.r.size : 0;
            int leftLeftSize = node.l != null && node.l.l != null ? node.l.l.size : 0;
            int leftRightSize = node.l != null && node.l.r != null ? node.l.r.size : 0;
            int rightRightSize = node.r != null && node.r.r != null ? node.r.r.size : 0;
            int rightLeftSize = node.r != null && node.r.l != null ? node.r.l.size : 0;
            if (leftLeftSize > rightSize) { // LL
                node = rightRotate(node);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (leftRightSize > rightSize) { // LR
                node.l = leftRotate(node.l);
                node = rightRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rightLeftSize > leftSize) { // RL
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rightRightSize > leftSize) { // RR
                node = leftRotate(node);
                node.l = maintain(node.l);
                node = maintain(node);
            }
            return node;
        }

        public int size() {
            return root != null? root.size : 0;
        }

        public K getIndexNode(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("index is invalid.");
            }
            return getIndexNode(root, index + 1).key;
        }

        // 在node 上，或者第kth偏移量的节点
        private SBTNode<K> getIndexNode(SBTNode<K> node, int kth) {
            SBTNode<K> cur = node;
            while (cur != null) {
                if (cur.l != null && cur.l.size + 1 == kth) { // 左子树有kth-1个节点，则当前node就是第k个
                    break;
                } else if (cur.l != null && cur.l.size >= kth) { // 左子树的节点数，比k多，说明往左子树走
                    cur = cur.l;
                } else { // 左子树的节点数，比k小。说明剩下的在右子树
                    // 得减去左子树+根节点，这二者的全部节点数
                    kth -= 1 + (cur.l != null? cur.l.size : 0);
                    cur = cur.r;
                }
            }
           return cur;
        }
    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || k > nums.length) {
            return new double[]{};
        }

        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
        for (int i = 0; i < k - 1; i++) { // 先将k-1个数组，放入结构中
            map.add(new Node(i, nums[i]));
        }
        int N = nums.length;
        double[] ans = new double[N - k + 1];
        int p1 = 0; // 指向ans数组
        for (int i = k - 1; i < N; i++) {
            map.add(new Node(i, nums[i]));
            int size = map.size();
            if (size % 2 == 0) { // 偶数情况
                int upMidNum = size / 2 - 1; // 上中位数
                int downMinNum = upMidNum + 1; // 下中位数
                Node node1 = map.getIndexNode(upMidNum);
                Node node2 = map.getIndexNode(downMinNum);
                ans[p1++] = (node1.value + node2.value) / 2.0; // 计算二者的平均值
            } else { // 奇数情况
                ans[p1++] = (double)map.getIndexNode(size / 2).value;
            }
            // 删除最先进入的node值。因为该结构内部的比较方法是比较index和value，所以这里直接
            // 新建相同的节点即可
            map.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }
}
