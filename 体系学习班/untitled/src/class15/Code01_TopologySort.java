package class15;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-30
 * Time: 20:56
 * Description: 拓扑排序：从入度为0 的节点开始出发。 也就类似于编译时，那个导包的依赖操作类似
 */
public class Code01_TopologySort {
    public static List<Node> topologySort(Graph graph) {
        if (graph == null) {
            new ArrayList<>();
        }

        HashMap<Node, Integer> inMap = new HashMap<>(); // 每个节点的入度
        Queue<Node> zeroInNode = new LinkedList<>();
        List<Node> ans = new ArrayList<>();
        Collection<Node> nodes = graph.allNodes.values();
        for (Node node : nodes) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInNode.add(node);
            }
        }
        // 遍历所有的入度为0 的节点
        while (!zeroInNode.isEmpty()) {
            Node from = zeroInNode.poll();
            ans.add(from);
            for(Node to : from.nodes) { // 不存在循环依赖的情况，所有不会出现死循环
                inMap.put(to, inMap.get(to) - 1);
                if (inMap.get(to) == 0) { // 终点的入度等于0，就加入队列中
                    zeroInNode.add(to);
                }
            }
        }
        return ans;
    }
}
