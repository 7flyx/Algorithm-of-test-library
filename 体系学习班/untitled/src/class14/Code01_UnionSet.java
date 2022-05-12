package class14;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-12
 * Time: 16:06
 * Description: 并查集
 */
public class Code01_UnionSet<T> {
    private static class Node<T> {
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

    private HashMap<T, Node<T>> nodes; // T类型的数据，对应到Node<T>
    private HashMap<Node<T>, Node<T>> parent; // 存放一个节点的父节点
    // 存放一个集合的大小，以每个集合的代表节点为标志，来表示这个集合的大小
    // 不是代表节点的，不用存储所在集合的大小。这样设计，能够表示当前所有数据中
    // 已经划分成了几个集合了。有多少个代表节点，就表示有多少个集合
    private HashMap<Node<T>, Integer> sizeMap;

    public Code01_UnionSet(List<T> list) {
        this.nodes = new HashMap<>();
        this.parent = new HashMap<>();
        this.sizeMap = new HashMap<>();
        // 遍历list表，将所有的数据都包装一层
        for (T val : list) {
            Node<T> node = new Node<>(val);
            nodes.put(val, node);
            parent.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    // 将两个数据所在的集合，合并成一个集合
    public void union(T a, T b) {
        Node<T> nodeA = nodes.get(a);
        Node<T> nodeB = nodes.get(b);
        Node<T> parentA = findFather(nodeA);
        Node<T> parentB = findFather(nodeB);
        if (parentA != parentB) { // 不在一个集合的时候，合并
            int sizeA = sizeMap.get(parentA);
            int sizeB = sizeMap.get(parentB);
            // 区分出大小集合，小集合挂在大集合下面
            Node<T> big = sizeA >= sizeB? parentA : parentB;
            Node<T> small = sizeA < sizeB? parentA : parentB;
            parent.put(small, big);
            sizeMap.put(big, sizeA + sizeB);
            sizeMap.remove(small); // 删除小集合的大小
        }
    }

    // 返回一个节点所在集合的代表节点，并随带实现路径压缩，使其均摊时间，使时间复杂度为O(1)
    private Node<T> findFather(Node<T> cur) {
        Stack<Node<T>> stack = new Stack<>(); // 临时存储沿途节点，用于路径压缩
        while (cur != parent.get(cur)) {
            stack.push(cur);
            cur = parent.get(cur);
        }
        // 沿途节点的父节点，全部指向所在集合的代表节点，实现路径压缩
        while (!stack.isEmpty()) {
            parent.put(stack.pop(), cur);
        }
        return cur; // 返回所在集合的代表节点
    }

    public boolean isSameSet(T a, T b) {
        Node<T> nodeA = nodes.get(a);
        Node<T> nodeB = nodes.get(b);
        return findFather(nodeA) == findFather(nodeB);
    }

}
