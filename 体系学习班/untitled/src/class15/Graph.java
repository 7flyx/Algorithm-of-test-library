package class15;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-30
 * Time: 20:13
 * Description: 整张图的描述，有点和边构成
 */
public class Graph {
    public HashMap<Integer, Node> allNodes;
    public ArrayList<Edge> allEdges;

    public Graph() {
        this.allNodes = new HashMap<>();
        this.allEdges = new ArrayList<>();
    }

    // 数组中有3列，如下
    // [distance, from, to]
    public void arrayToGraph(int[][] matrix) {
        if (matrix == null) {
            return;
        }

        for (int[] row : matrix) {
            int distance = row[0];
            int from = row[0];
            int to = row[0];
            // 如果表中没有起点和终点，就新建
            if (!allNodes.containsKey(from)) {
                allNodes.put(from, new Node(from));
            }
            if(!allNodes.containsKey(to)) {
                allNodes.put(to, new Node(to));
            }
            // 获取到相应的节点，补充节点信息
            Node fromNode = allNodes.get(from);
            Node toNode = allNodes.get(to);
            Edge edge = new Edge(distance, fromNode, toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.nodes.add(toNode);
            fromNode.edges.add(edge);
            allEdges.add(edge); // 将新的边加入Graph中
        }
    }
}
