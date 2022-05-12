package class13;

import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-12
 * Time: 15:44
 * Description: 做项目问题
 * 输入正数数组costs、正数数组profits、正数K和正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * M表示你初始的资金
 * 说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目。
 * 输出：最后获得的最大钱数
 */
public class Code04_DoProject {
    public static void main(String[] args) {

    }

    /**
     * @param costs 每个项目的成本
     * @param profits 每个项目的纯利润
     * @param K 串行做K个项目
     * @param W 启动资金
     * @return 返回最多能挣多少
     */
    public static int getMaxProfit(int[] costs, int[] profits, int K, int W) {
        if (costs == null || profits == null ||profits.length != costs.length) {
            return 0;
        }

        // 小根堆，以成本价排序
        PriorityQueue<Project> costHeap = new PriorityQueue<>((o1, o2) -> {
            return o1.cost - o2.cost; // 成本低的，排在前面
        });
        // 大根堆，以利润排序
        PriorityQueue<Project> profitHeap = new PriorityQueue<>((o1, o2) -> {
            return o2.profit - o1.profit; // 利润大的，排在前面
        });

        // 将全部的项目放入小根堆中
        for (int i = 0; i < costs.length; i++) {
            costHeap.add(new Project(costs[i], profits[i]));
        }

        for (int count = 0; count < K; count++) { // 最多能做K个项目
            // 把能做的项目全部倒入大根堆中
            while(!costHeap.isEmpty() && costHeap.peek().cost <= W) {
                profitHeap.add(costHeap.poll());
            }
            // 如果大根堆一个数据都没有，说明资金不够用，不能做任何一个项目
            if (profitHeap.isEmpty()) {
                return W;
            }
            // 取大根堆中的堆顶元素，作为本次的项目
            W += profitHeap.poll().profit;
        }
        return W;
    }

    private static class Project {
        public int cost;
        public int profit;

        public Project(int cost, int project) {
            this.cost = cost;
            this.profit = project;
        }
    }
}
