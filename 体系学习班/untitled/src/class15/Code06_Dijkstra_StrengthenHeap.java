package class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-04
 * Time: 9:48
 * Description: 迪杰斯特拉算法 - 加强堆改写版本，提高了在寻找下一中转点的速度
 */
public class Code06_Dijkstra_StrengthenHeap {
    // 从一个起点，走向整张图的每个节点的最短距离。前提条件时，整张图中的每一条边的距离，不能有负值
    public HashMap<Node, Integer> dijkstra(Node start) {
        if (start == null) {
            return new HashMap<>();
        }

        HashMap<Node, Integer> ans = new HashMap<>();
        StrengthenHeap heap = new StrengthenHeap();
        HashSet<Node> selectedNode = new HashSet<>();
        heap.add(start, 0); // 起始点
        Node minDistanceNode = findMinDistanceNodeAndUnSelectedNode(heap, selectedNode);
        while (minDistanceNode != null) {
            int distance = heap.getDistance(minDistanceNode);
            for (Edge edge : minDistanceNode.edges) { // 通向下级节点的所有边
                Node toNode = edge.to;
                if (!ans.containsKey(toNode)) { // 表中还没有走向当前节点的路径，就新建
                    heap.add(toNode, distance + edge.distance);
                    ans.put(toNode, distance + edge.distance);
                } else { // 已经有走向下级节点的路径，就选择最优的结果
                    if (distance + edge.distance < ans.get(toNode)) {
                        ans.put(toNode, distance + edge.distance);
                        heap.adjust(toNode, distance + edge.distance);
                    }
                }
            }
            selectedNode.add(minDistanceNode);
            minDistanceNode = findMinDistanceNodeAndUnSelectedNode(heap, selectedNode);
        }
        return ans;
    }

    private Node findMinDistanceNodeAndUnSelectedNode(StrengthenHeap heap, HashSet<Node> selectedNode) {
        while (heap.size() != 0) {
            Node node = heap.poll();
            if (!selectedNode.contains(node)) {
                return node;
            }
        }
        return null;
    }

    private static class StrengthenHeap {
        private ArrayList<Node> nodes;
        private HashMap<Node, Integer> indexMap; // 反向索引表，存储每个节点在数组中的下标值
        private HashMap<Node, Integer> distanceMap; // 节点距离
        private int size; // 表示当前堆的大小值

        public StrengthenHeap() {
            nodes = new ArrayList<>();
            indexMap = new HashMap<>();
            size = 0;
        }

        public void add(Node node, int distance) {
            if (node == null) {
                return;
            }
            if (size == nodes.size()) {
                System.out.println("加强堆空间不足");
                return;
            }
            indexMap.put(node, size++);
            distanceMap.put(node, distance);
            nodes.add(node); // 插入到堆的末尾
            // 向上调整
            heapInsert(size - 1);
        }

        // 弹出堆顶元素
        public Node poll() {
            if (size == 0) {
                System.out.println("堆为空");
                return null;
            }
            Node ans = nodes.get(0);
            swap(0, --size);
            indexMap.remove(ans); // 删除需要弹出元素中的下标
            // 向下调整
            heapify(0);
            return ans;
        }

        // 当前位置的节点，需要重新调整位置，或许是向上调整，或许是向下调整
        public void adjust(Node toNode, int distance) {
            distanceMap.put(toNode, distance); // 更新距离值
            int index  = indexMap.get(toNode);
            heapInsert(index); // 向上调整
            heapify(index); // 向下调整
        }

        // 向下调整
        private void heapify(int index) {
            int left = index * 2 + 1;
            while (left < size) {
                int largest = left + 1 < size && distanceMap.get(nodes.get(left + 1)) > distanceMap.get(nodes.get(left)) ?
                        left + 1 : left;
                if (distanceMap.get(nodes.get(largest)) < distanceMap.get(nodes.get(index))) { // 孩子节点干不过父节点，直接退出
                    return;
                }
                swap(largest, index); // 交换父子的位置
                index = largest;
                left = index * 2 + 1;
            }
        }

        // 向上调整
        private void heapInsert(int i) {
            int parent = (i - 1) / 2;
            while (parent >= 0 && distanceMap.get(nodes.get(parent)) > distanceMap.get(nodes.get(i))) {
                swap(i, parent);
                i = parent;
                parent = (i - 1) >> 1;
            }
        }

        private void swap(int i, int parent) {
            indexMap.put(nodes.get(i), parent); // 在反向索引表的下标也需要交换
            indexMap.put(nodes.get(parent), i);
            Node tmp = nodes.get(i);
            nodes.set(i, nodes.get(parent));
            nodes.set(parent, tmp);
        }

        public boolean contains(Node node) {
            return indexMap.containsKey(node);
        }

        public int size() {
            return size;
        }

        public int getDistance(Node node) {
            return distanceMap.get(node);
        }
    }
}
