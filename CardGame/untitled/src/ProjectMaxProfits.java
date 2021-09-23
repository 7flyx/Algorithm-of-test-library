import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-09-23
 * Time: 21:18
 * Description: 做项目的最大收益
 * 第一行三个整数N, W, K。表示总的项目数量，初始资金，最多可以做的项目数量
 * 第二行有N个正整数，表示costs数组
 * 第三行有N个正整数，表示profits数组

 */

public class ProjectMaxProfits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); //项目数量
        int W = sc.nextInt(); //初始资金
        int K = sc.nextInt(); //只能做的项目数量
        int[] costs = new int[N];
        Node[] arr = new Node[N]; //将成本和利润放到一起

        for (int i = 0; i < N; i++) {
            costs[i] = sc.nextInt();
        }
        for (int i = 0; i < N; i++) {
            arr[i] = new Node(costs[i], sc.nextInt());
        }

        //以项目的成本用小根堆排序。再弹出能够做的项目，放入大根堆
        PriorityQueue<Node> costQueue = new PriorityQueue<>(new CostCompare());
        PriorityQueue<Node> profitQueue = new PriorityQueue<>(new ProfitCompare());
        for (int i = 0; i < N; i++) {
            costQueue.add(arr[i]); //将所有的节点，以成本进行排序，放入堆中
        }

        for (int i = 0; i < K; i++) {
            while (!costQueue.isEmpty() && costQueue.peek().cost <= W) {
                profitQueue.add(costQueue.poll());
            }

            if (profitQueue.isEmpty()) {
                break;
            }

            Node node = profitQueue.poll();
            W += node.profit;
        }

        System.out.println(W);
    }

    private static class Node {
        public int cost;
        public int profit;

        public Node(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    private static class CostCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.cost - o2.cost; //升序
        }
    }

    private static class ProfitCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.profit - o1.profit; //降序
        }
    }
}