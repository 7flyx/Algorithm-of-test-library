import java.util.HashMap;

/**
 * Created by flyx
 * Description: 迪杰斯特拉算法 改进版 --改成堆的形式存储最小边的信息
 * User: 听风
 * Date: 2021-09-05
 * Time: 19:03
 */
public class Dijkstra {

    private static class NodeRecord {
        public Node node;
        public int distance;
        public NodeRecord(Node node, int distance) {
            this.distance = distance;
            this.node = node;
        }
    }

    private static class NodeHeap {
        private Node[] nodes; //存储节点信息
        private HashMap<Node, Integer> heapIndexMap; //存储下标
        private HashMap<Node, Integer> distanceMap; //存储某点到这个点的距离
        private int size; //当前堆中的大小

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) { //在堆上
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) { //没有进过堆
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        //上浮处理
        public void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index- 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) >> 1;
            }
        }

        //下沉处理
        public void heapify(int index, int size) {
            int left = index * 2 + 1; //下一层节点的左孩子
            while (left < size) {
                int minChild = left + 1 < size &&
                        distanceMap.get(nodes[left]) > distanceMap.get(nodes[left + 1])? left + 1 : left;
                if (minChild == size) { //size是这个堆，目前的大小
                    break;
                }
                swap(minChild, index);
                index = minChild;
                left = index * 2 + 1;
            }
        }

        public NodeRecord pop() {
            NodeRecord res = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1); //与最后一个元素进行交换
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null; //回收node节点
            heapify(0, --size);
            return res;
        }

        public boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        public boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node node = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = node;
        }
    }

    public HashMap<Node, Integer> dijkstra(Node from, int size) {
        if (from == null) {
            throw new RuntimeException("from node is null.");
        }

        NodeHeap heap = new NodeHeap(size);
        heap.addOrUpdateOrIgnore(from, 0);
        HashMap<Node, Integer> res = new HashMap<>();
        while (res.size() != size - 1 && !heap.isEmpty()) { //共有size个点，连通整个图，只需size - 1条边
            NodeRecord record = heap.pop();
            Node node = record.node;
            int distance = record.distance;
            res.put(node, distance);
            for (Edge edge : node.edges) {
                heap.addOrUpdateOrIgnore(edge.to, distance + edge.weight);
            }
        }
        return res;
    }
}
