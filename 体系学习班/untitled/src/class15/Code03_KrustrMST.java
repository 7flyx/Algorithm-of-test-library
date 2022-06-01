package class15;

import java.util.*;

// 克鲁斯卡尔最小生成树
public class Code03_KrustrMST {
    public HashSet<Edge> krustrMST(Graph graph) {
        if(graph ==  null) {
            return new HashSet<>();
        }

        // 存储整张图中全部的边的数
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1, o2) -> {
            return o1.distance - o2.distance; // 以边的长度进行排序，小的边排在前面
        });

        for (Edge edge : graph.allEdges) {
            queue.add(edge);
        }

        // 并查集
        UnionSet unionSet = new UnionSet(graph.allNodes.values());
        HashSet<Edge> ans = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (!unionSet.isSameSet(edge.from, edge.to)) { //起点和终点不在一个集合的时候，说明不会形成环，可以选择要了这条边
                ans.add(edge);
                unionSet.union(edge.from, edge.to);
            }
        }
        return ans;
    }

    private static class UnionSet {
        private HashMap<Node, Node> parent;
        private HashMap<Node, Integer> sizeMap;
        private Node[] helpStack; // 代替辅助栈

        public UnionSet(Collection<Node> list) {
            helpStack = new Node[list.size()];
            parent = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : list) {
                parent.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public void union(Node o1, Node o2) {
            Node parent1 = findFather(o1);
            Node parent2 = findFather(o2);
            if(parent1 != parent2) {
                int size1 = sizeMap.get(parent1);
                int size2 = sizeMap.get(parent2);
                if (size1 >= size2) {
                    sizeMap.put(parent1, size1 + size2);
                    sizeMap.remove(parent2);
                    parent.put(parent2, parent1);
                } else {
                    sizeMap.put(parent2, size1 + size2);
                    sizeMap.remove(parent1);
                    parent.put(parent1, parent2);
                }
            }
        }

        private Node findFather(Node node) {
            int hi = 0;
            while (node != parent.get(node)) {
                helpStack[hi++] = node;
                node = parent.get(node);
            }

            // 路径压缩
            while (hi-- > 0) {
                parent.put(helpStack[hi], node);
            }
            return node;
        }

        public boolean isSameSet(Node o1, Node o2) {
            return findFather(o1) == findFather(o2);
        }
    }
}
