package class47;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-10
 * Time: 14:52
 * Description: 图论--最大网络流算法---Dinic
 * <p>
 * 测试链接：https://lightoj.com/problem/internet-bandwidth
 */
public class DinicAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt(); // case数量
        for (int i = 1; i <= cases; i++) {
            int n = sc.nextInt(); // 节点数
            int s = sc.nextInt(); // 起点
            int t = sc.nextInt(); // 终点
            int m = sc.nextInt(); // 边的数量
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = sc.nextInt(); // from
                int to = sc.nextInt(); // to
                int weight = sc.nextInt(); // available
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight); // 反向边（因为给的是无向图，所以要单独添加一次反向边）
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
    }

    public static class Edge {
        public int from; // 起点
        public int to; // 终点
        public int available; // 线路最大承载量

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }

    /**
     * Dinic算法在这里有3个优化：
     *  1、depth数组，保证了在跳转时，不会跳转到相同深度的节点上
     *  2、添加反向边，可用作回退
     *  3、cur数组，用于存储某一个点作为起点时，它底下的哪些边已经遍历过了，从还没遍历过（或带宽还没占满）的边开始遍历
     */
    public static class Dinic {
        private int N; // 实际节点数+1
        private ArrayList<ArrayList<Integer>> nexts; // 存储的是某个起点，这个点下面的边在edges中的编号
        private ArrayList<Edge> edges; // 所有的边的信息
        private int[] depth; // 存储类似于二叉树的深度，节点在往跳转时，只能跳转到比当前节点更深的节点上。避免了走相同深度的节点
        private int[] cur; // cur[i] 表示 第i个点，当前已经遍历完哪几条边了。下次深搜时，接着这条边开始递归

        //        num：城市节点数
        public Dinic(int num) {
            N = num + 1;
            nexts = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                nexts.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[N];
            cur = new int[N];
        }

        /**
         * 在图中添加边的信息
         *
         * @param u 起点
         * @param v 终点
         * @param r 边的最大承载量（带宽）
         */
        public void addEdge(int u, int v, int r) {
            int num = edges.size(); // 根据边的数量来编号
            edges.add(new Edge(u, v, r)); // 正向边
            nexts.get(u).add(num);
            edges.add(new Edge(v, u, 0)); // 反向边
            nexts.get(v).add(num + 1);
        }

        public int maxFlow(int s, int t) {
            int flow = 0;
            while(bfs(s, t)) {
                Arrays.fill(cur, 0);
                flow += dfs(s, t, Integer.MAX_VALUE); // 这里填int最大值，会在挑选第一条边的时候进行缩小
                Arrays.fill(depth, 0);
            }
            return flow;
        }

        // 探索s能不能走到t点，并且记录沿途的深度depth
        private boolean bfs(int s, int t) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(s);
            boolean[] visited = new boolean[N];
            visited[s] = true;
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                int size = nexts.get(cur).size(); // 以cur为起点，下面有多少边就遍历
                for (int i = 0; i < size; i++) {
                    Edge edge = edges.get(nexts.get(cur).get(i)); // 取出这条边
                    int v = edge.to; // 下一中转点
                    if (!visited[v] && edge.available > 0) { // 中转点还没走过，并且带宽还有剩余的
                        visited[v] = true;
                        depth[v] = depth[cur] + 1;
                        if (v == t) { // 到终点了，提前跳出循环
                            break;
                        }
                        queue.add(v); // 将下一中转点压入队列
                    }
                }
            }
            return visited[t];
        }

        // 接着cur[s]已经遍历过的边，往下遍历，返回s走到t的最大承载量是多少
        private int dfs(int s, int t, int r) {
            if (s == t || r == 0) {
                return r;
            }
            int flow = 0; // 累加flow总和
            int f = 0; // 临时变量---存储的是后续走到t点的最小承载量
            int size = nexts.get(s).size(); // 以s作为起点，往下级跳转的边的数量
            for (; cur[s] < size; cur[s]++) {
                int edgeNum = nexts.get(s).get(cur[s]); // 拿到cur[s]对应的某条边的编号
                Edge edge1 = edges.get(edgeNum); // 正向边
                Edge edge2 = edges.get(edgeNum ^ 1); // 反向边，比如正向边是0，则反向边 = 0^1 = 1。反过来 1 ^ 1 = 0就能得到正向边
                if (depth[edge1.to] == depth[s] + 1 && (f = dfs(edge1.to, t, Math.min(edge1.available, r))) != 0) {
                    edge1.available -= f; // 正向边减少
                    edge2.available += f; // 反向边增加
                    flow += f; // 累加到总和里面
                    r -= f; // r是任务量，如果任务量已经归零了，就可以退出了。如果没有归零，循环继续，让其他线路分担任务量
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }
    }
}
