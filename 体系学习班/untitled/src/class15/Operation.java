package class15;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-30
 * Time: 20:33
 * Description: 图的一系列操作
 */
public class Operation {
    // 从一个点出发，
    public void dfs(Node start) {
        if (start == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>(); // 用于记录已经遍历过的节点
        stack.push(start);
        set.add(start);
        System.out.print(start.value + " ");
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            for (Node next : node.nodes) {
                if (!set.contains(next)) {
                    System.out.print(next.value + " ");
                    stack.push(node); // 先压入上层节点
                    stack.push(next); // 再压入下层节点
                    set.add(next);
                    break;
                }
            }
        }
    }

    public void bfs(Node start) {
        if(start == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>(); // 记录已经遍历过的节点
        queue.add(start);
        set.add(start);
        while(!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.value + " ");
            for (Node next : node.nodes) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }

}
