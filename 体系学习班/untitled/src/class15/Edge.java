package class15;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-30
 * Time: 20:11
 * Description: 边的描述
 */
public class Edge {
    public int distance; // 边的长度
    public Node from;
    public Node to;

    public Edge(int distance, Node from, Node to) {
        this.distance = distance;
        this.from = from;
        this.to = to;
    }
}
