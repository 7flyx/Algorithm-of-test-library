package class15;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-31
 * Time: 18:35
 * Description: lintcode 127题 拓扑排序
 */
public class Code02_TopologySortDFS1 {

    // Definition for Directed graph.
    private static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;

        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 以后续的节点数进行搜索的
    private static class Solution1 {
        /**
         * @param graph: A list of Directed graph node
         * @return: Any topological order for the given graph.
         */
        public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
            if (graph == null) {
                return new ArrayList<>();
            }
            List<Node> ans = new ArrayList<>();
            HashMap<DirectedGraphNode, Node> record = new HashMap<>();
            for (DirectedGraphNode node : graph) {
                bfs(node, record);
            }
            for (Node node : record.values()) {
                ans.add(node);
            }
            // ans.sort(new MyComparator());
            Collections.sort(ans, new MyComparator());
            ArrayList<DirectedGraphNode> list = new ArrayList<>();
            for (Node node : ans) {
                list.add(node.node);
            }
            return list;
        }

        private static class Node {
            public DirectedGraphNode node; // 节点本身的数据
            public long count; // 下级节点的个数

            public Node(DirectedGraphNode node, long count) {
                this.node = node;
                this.count = count;
            }
        }

        public static class MyComparator implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.count == o2.count ? 0 : (o1.count > o2.count ? -1 : 1);
            }
        }

        // 跑宽度优先遍历，加上记忆化搜索，来到当前节点，去搜索他的下级节点（通向下一个节点）
        // 一个节点的 所有下级节点越多，说明这个节点在整个拓扑排序中，越靠前。这是解这道题的关键
        private Node bfs(DirectedGraphNode node, HashMap<DirectedGraphNode, Node> record) {
            if (record.containsKey(node)) {
                return record.get(node);
            }
            long count = 0;
            for (DirectedGraphNode next : node.neighbors) { // 遍历下级节点
                count += bfs(next, record).count;
            }
            Node node2 = new Node(node, count + 1);
            record.put(node, node2); // 下级节点的所有数加上自己本身
            return node2;
        }
    }

    // 以深度进行搜索的
    private static class Solution2 {
        // 提交下面的
        public static class Record {
            public DirectedGraphNode node;
            public int deep;

            public Record(DirectedGraphNode n, int o) {
                node = n;
                deep = o;
            }
        }

        public static class MyComparator implements Comparator<Record> {

            @Override
            public int compare(Record o1, Record o2) {
                return o2.deep - o1.deep;
            }
        }

        public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
            HashMap<DirectedGraphNode, Record> order = new HashMap<>();
            for (DirectedGraphNode cur : graph) {
                f(cur, order);
            }
            ArrayList<Record> recordArr = new ArrayList<>();
            for (Record r : order.values()) {
                recordArr.add(r);
            }
            recordArr.sort(new MyComparator());
            ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
            for (Record r : recordArr) {
                ans.add(r.node);
            }
            return ans;
        }

        public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
            if (order.containsKey(cur)) {
                return order.get(cur);
            }
            int follow = 0;
            for (DirectedGraphNode next : cur.neighbors) {
                follow = Math.max(follow, f(next, order).deep);
            }
            Record ans = new Record(cur, follow + 1);
            order.put(cur, ans);
            return ans;
        }
    }
}
