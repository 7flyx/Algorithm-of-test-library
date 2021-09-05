import java.util.ArrayList;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-09-05
 * Time: 19:08
 */
public class Node {
    public int val;
    public int in;
    public int out;
    public ArrayList<Node> nexts;
    public ArrayList<Edge> edges;

    public Node(int val) {
        this.val = val;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
