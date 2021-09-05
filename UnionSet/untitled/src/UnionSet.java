import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by flyx
 * Description: 并查集大致框架---代码中的数据（Node），可以是其他，比如二叉树节点、图的边、节点等等 抽象的数据
 * User: 听风
 * Date: 2021-09-05
 * Time: 16:22
 */

public class UnionSet {
    private HashMap<Node, Node> fatherMap; //key表示当前这个数据，value表示这个数据的代表（父亲）是谁
    private HashMap<Node, Integer> sizeMap; //表示当前这个组（集合）的大小

    public UnionSet() { //构造方法
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();
    }

    private static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    //初始化并查集
    public void makeSet(List<Node> list) {
        if (list == null) {
            return;
        }
        fatherMap.clear();
        sizeMap.clear(); //先将表清空

        //遍历list，把每一个节点，都放入哈希表中
        for (Node node : list) {
            fatherMap.put(node, node); //第一个参数是节点本身，第二个参数就是这个组的代表
            sizeMap.put(node, 1); //第一个参数是这个组的代表，第二个参数是大小
        }
    }

    //判断是不是同一个组
    public boolean isSameSet(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return false;
        }
        return findFather(node1) == findFather(node2); //查找各自的代表节点，看是不是同一个。
    }

    //查找代表节点，并做路径压缩
    private Node findFather(Node node) {
        if (node == null) {
            return null;
        }
        //查找代表节点
        Stack<Node> path = new Stack<>(); //存储沿途的节点
        while (node != fatherMap.get(node)) { //代表节点不是自己本身，就继续查找
            path.push(node);
            node = fatherMap.get(node);
        }
        //路径压缩
        while (!path.isEmpty()) {
            Node tmp = path.pop();
            fatherMap.put(tmp, node); //此时的node，就是这个组的代表节点
        }

        return node;
    }

    //合并操作
    public void union(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return;
        }
        int node1Size = sizeMap.get(node1);
        int node2Size = sizeMap.get(node2); //分别得到两个节点所在组的大小
        Node node1Father = fatherMap.get(node1);
        Node node2Father = fatherMap.get(node2); //分别拿到两个节点的代表节点

        if (node1Father != node2Father) { //两个节点，不在同一个组，就合并
            if (node1Size < node2Size) { //node1 挂在 node2
                fatherMap.put(node1Father, node2Father);
                sizeMap.put(node2Father, node1Size + node2Size); //新的组，大小是原来两个组的和
                sizeMap.remove(node1Father); //小组的数据，就不需要了，删除
            } else { //node2 挂在 node1
                //跟上面操作类似
                fatherMap.put(node2Father, node1Father);
                sizeMap.put(node1Father, node1Size + node2Size);
                sizeMap.remove(node1Father);
            }
        }
    }
}
