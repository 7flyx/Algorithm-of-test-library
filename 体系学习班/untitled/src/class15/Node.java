package class15;

import java.util.ArrayList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-30
 * Time: 20:09
 * Description: 点的描述
 */
public class Node {
    public int value;
    public int in; // 入度
    public int out; // 出度
    public ArrayList<Edge> edges; // 通向邻居节点的边
    public ArrayList<Node> nodes; // 下一邻居节点

    public Node() {
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
    }
    public Node(int value) {
        this.value = value;
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
    }
}
