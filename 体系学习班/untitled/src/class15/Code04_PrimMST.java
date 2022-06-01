package class15;

import java.util.HashSet;
import java.util.PriorityQueue;

public class Code04_PrimMST {
    public HashSet<Edge> primMST(Graph graph) {
        if (graph == null) {
            return new HashSet<>();
        }
        // 存储解锁了的边，以小的边放在前面
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1, o2) -> {
            return o1.distance - o2.distance;
        });

        HashSet<Node> selectedNode = new HashSet<>(); // 存储已经选择过的点
        HashSet<Edge> ans = new HashSet<>();
        for (Node node : graph.allNodes.values()) {
            if (!selectedNode.contains(node)) {
                selectedNode.add(node);
                // 解锁当前点的直接边，放入小根堆中去
                for (Edge edge : node.edges) {
                    queue.add(edge);
                }
                // 以当前点开始，生成最小树
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();
                    Node toNode = edge.to;
                    if (!selectedNode.contains(toNode)) {
                        selectedNode.add(toNode);
                        ans.add(edge); // 目前来说，最短的边
                        for(Edge nextEdge : toNode.edges) {
                            queue.add(nextEdge);
                        }
                    }
                }
            }
        }
        return ans;
    }
}
