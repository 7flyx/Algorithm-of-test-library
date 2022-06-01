package class15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class Code05_Dijkstra {
    // 从一个起点，到整张图其他点的最短路径
    public HashMap<Node, Integer> dijkstra(Node start) {
        if (start == null) {
            return new HashMap<>();
        }
        // 小根堆，存储的是边
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        // 已经选择过的点
        HashSet<Node> selectedNode = new HashSet<>();
        // 存储start到每一个点的最短距离
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(start, 0);
        Node minDistanceNode = findMinDistanceNodeAndUnSelected(distanceMap, selectedNode);
        while (minDistanceNode != null) {
            int distance = distanceMap.get(minDistanceNode);
            for (Edge edge : minDistanceNode.edges) {
                if (!distanceMap.containsKey(edge.to)) { // 当前distanceMap中还没有走到edge.to的边，需要新建
                    distanceMap.put(edge.to, distanceMap.get(minDistanceNode) + edge.distance);
                } else { // 如果表中已经有到edge.to的边了，那么二者就需要取最优的结果才行
                    distanceMap.put(edge.to,
                            Math.min(distance + edge.distance, distanceMap.get(edge.to)));
                }
            }
            // 更新minDistanceNode
            selectedNode.add(minDistanceNode);
            minDistanceNode = findMinDistanceNodeAndUnSelected(distanceMap, selectedNode);
        }
        return distanceMap;
    }

    private Node findMinDistanceNodeAndUnSelected(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNode) {
        Node minDistanceNode = null;
        int distance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int curDistance = entry.getValue();
            if (!selectedNode.contains(node) && curDistance < distance) { // 当前点没有被选择过，并且距离还是目前最短的
                distance = curDistance;
                minDistanceNode = node;
            }
        }
        return minDistanceNode;
    }
}
