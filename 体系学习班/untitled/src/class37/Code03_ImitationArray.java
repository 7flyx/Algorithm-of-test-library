package class37;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-03
 * Time: 9:13
 * Description: 模仿顺序表，有增删查改操作。但是每个操作时间复杂度控制在logN水平。
 * 原理：用到了平衡树，因为左右旋转，并不会改变每个节点的相对顺序。
 */
public class Code03_ImitationArray {
    private static class SBTNode<V> {
        public V value;
        public SBTNode<V> l;
        public SBTNode<V> r;
        public int size;

        public SBTNode(V value) {
            this.value = value;
            this.size = 1;
        }
    }

    private static class SBTList<V> {
        private SBTNode<V> root;

        private SBTNode<V> leftRotate(SBTNode<V> node) {
            SBTNode<V> rightNode = node.r;
            node.r = rightNode.l;
            rightNode.l = node;
            rightNode.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<V> rightRotate(SBTNode<V> node) {
            SBTNode<V> leftNode = node.l;
            node.l = leftNode.r;
            leftNode.r = node;
            leftNode.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<V> maintain(SBTNode<V> node) {
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
            } else if (rightRightSize > leftSize) { // RR
                node = leftRotate(node);
                node.l = maintain(node.l);
                node = maintain(node);
            } else if (rightLeftSize > leftSize) { // RL
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            }
            return node;
        }

        private SBTNode<V> add(SBTNode<V> node, int index, V value) {
            if (node == null) {
                return new SBTNode<>(value);
            }
            node.size++;
            int leftAndNodeSize = (node.l != null ? node.l.size : 0) + 1;
            if (index <= leftAndNodeSize) { // 等于的时候，也是往左子树走
                node.l = add(node.l, index, value);
            } else {
                node.r = add(node.r, index - leftAndNodeSize, value);
            }
            return maintain(node); // 维持平衡
        }

        private SBTNode<V> remove(SBTNode<V> node, int index) {
            if (node == null) {
                return null;
            }
            node.size--;
            int leftAndNodeSize = (node.l != null ? node.l.size : 0) + 1;
            if (index < leftAndNodeSize) {
                node.l = remove(node.l, index);
            } else if (index > leftAndNodeSize) {
                node.r = remove(node.r, index - leftAndNodeSize);
            } else { // index == leftAndNodeSize的时候
                if (node.l == null && node.r == null) {
                    // C++需要释放空间
                    node = null;
                } else if (node.r == null) { // 左孩纸不是空，右孩子是空
                    node = node.l;
                } else if (node.l == null) { // 左孩子是空，右孩子不是空
                    node = node.r;
                } else { // 左右孩子都不为空
                    SBTNode<V> des = node.r;
                    SBTNode<V> pre = null;
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
            return maintain(node); // 也可以在remove中不维持平衡，在add时维持
        }

        // index是0~size-1
        public void add(int index, V value) {
            if (index < 0 || index > this.size()) {
                throw new RuntimeException("index is invalid.");
            }
            root = add(root, index + 1, value);
        }

        // index是0~size-1
        public V get(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("index is invalid.");
            }
            index++;
            SBTNode<V> node = root;
            while (node != null) {
                int leftAndNodeSize = (node.l != null ? node.l.size : 0) + 1;
                if (index == leftAndNodeSize) {
                    return node.value;
                } else if (index < leftAndNodeSize) {
                    node = node.l;
                } else {
                    node = node.r;
                    index -= leftAndNodeSize;
                }
            }
            return null;
        }

        // index是0~size-1
        public void remove(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("index is invalid.");
            }
            root = remove(root, index + 1);
        }

        public int size() {
            return root != null ? root.size : 0;
        }

        public String printAll() {
            if (root == null) {
                return "";
            }
            SBTNode<V> node = root;
            Stack<SBTNode<V>> stack = new Stack<>();
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.push(node);
                    node = node.l;
                } else {
                    node = stack.pop();
                    sb.append(node.value).append(" ");
                    node = node.r;
                }
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        SBTList<Integer> list = new SBTList<>();
        list.add(0, 0);
        list.add(1, 1);
        list.add(2, 2);
        list.add(3, 3);
        System.out.println(list.printAll());
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        list.add(3, 4);
        System.out.println(list.printAll());
        list.remove(3);
        System.out.println(list.printAll());
    }
}
