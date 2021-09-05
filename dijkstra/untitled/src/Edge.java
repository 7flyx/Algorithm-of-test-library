/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-09-05
 * Time: 19:10
 */
public class Edge {
    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
