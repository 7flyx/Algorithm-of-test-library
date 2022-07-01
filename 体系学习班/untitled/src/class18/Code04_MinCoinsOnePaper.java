package class18;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-26
 * Time: 15:45
 * Description: 零钱兑换升级版本（之前的版本是货币无限个，现在是有限个了。求最少货币数）
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 返回组成aim的最少货币数
 * 注意：因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 */
public class Code04_MinCoinsOnePaper {
    public static void main(String[] args) {
        int[] coins = {1, 2, 1, 2, 1, 4, 3};
        int aim = 4;
        System.out.println(coinChange1(coins, aim));
        System.out.println(coinChange2(coins, aim));
        System.out.println(coinChange3(coins, aim));
        System.out.println(coinChange4(coins, aim));
        System.out.println(coinChange5(coins, aim));
    }

    // 暴力递归版本，每个位置选择要与不要两种状态
    public static int coinChange1(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int ans = process(coins, aim, 0);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process(int[] coins, int rest, int index) {
        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int p1 = process(coins, rest, index + 1); // 不要的情况
        int p2 = Integer.MAX_VALUE;
        if (coins[index] <= rest) {
            p2 = process(coins, rest - coins[index], index + 1);
        }
        if (p2 != Integer.MAX_VALUE) {
            p2 += 1;
        }
        return Math.min(p1, p2);
    }

    // 根据coinChange1改dp
    public static int coinChange2(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        Arrays.fill(dp[N], Integer.MAX_VALUE);
        dp[N][0] = 0; // 最后一行，除了[n][0] =0，其他位置都是无效解
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = dp[i + 1][rest];
                if (coins[i] <= rest && dp[i + 1][rest - coins[i]] != Integer.MAX_VALUE) {
                    dp[i][rest] = Math.min(dp[i][rest], dp[i + 1][rest - coins[i]] + 1);
                }
            }
        }
        return dp[0][aim] == Integer.MAX_VALUE ? -1 : dp[0][aim];
    }

    // 统计每张货币的张数，然后再去尝试要0张、1张....
    public static int coinChange3(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        int N = coins.length;

        Node[] nodes = getNodesOfCoins(coins);
        return process(nodes, aim, 0);
    }

    public static int process(Node[] nodes, int rest, int index) {
        if (index == nodes.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int zhangs = nodes[index].zhang;
        int coin = nodes[index].coin;
        int ans = Integer.MAX_VALUE;
        for (int zhang = 0; zhang <= zhangs && coin * zhang <= rest; zhang++) {
            int next = process(nodes, rest - zhang * coin, index + 1);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, next + zhang);
            }
        }
        return ans;
    }

    // 根据coinChange3进行改dp
    public static int coinChange4(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] nodes = getNodesOfCoins(coins);
        int N = nodes.length;
        int[][] dp = new int[N + 1][aim + 1];
        Arrays.fill(dp[N], Integer.MAX_VALUE); // 无效解的情况
        dp[N][0] = 0; // base case
        for (int i = N - 1; i >= 0; i--) {
            int zhangs = nodes[i].zhang;
            int coin = nodes[i].coin;
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = dp[i + 1][rest]; // 当前货币不要的情况，后续从1张开始枚举
                for (int zhang = 1; zhang <= zhangs && zhang * coin <= rest; zhang++) {
                    int next = dp[i + 1][rest - zhang * coin];
                    if (next != Integer.MAX_VALUE) {
                        dp[i][rest] = Math.min(dp[i][rest],
                                dp[i + 1][rest - zhang * coin] + zhang);
                    }
                }
            }
        }
        return dp[0][aim] == Integer.MAX_VALUE ? -1 : dp[0][aim];
    }

    // 在原dp的基础之上，省掉枚举行为，用滑动窗口进行优化
    public static int coinChange5(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] nodes = getNodesOfCoins(coins);
        int N = nodes.length;
        int[][] dp = new int[N + 1][aim + 1];
        Arrays.fill(dp[N], Integer.MAX_VALUE);
        dp[N][0] = 0; // base case
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            int zhangs = nodes[i].zhang;
            int coin = nodes[i].coin;
            for (int rest = 0; rest < Math.min(coin, aim + 1); rest++) { // 这里判断min，怕的是一张货币直接就超过aim了
                LinkedList<Integer> w = new LinkedList<>();
                w.addLast(rest); // 每一轮的第一个位置
                dp[i][rest] = dp[i + 1][rest]; // 当前位置第0张的时候，直接拿取下方的值
                for (int j = rest + coin; j <= aim; j += coin) {
                    while (!w.isEmpty() && (dp[i + 1][w.peekLast()] == Integer.MAX_VALUE ||
                            compensate(w.peekLast(), j, coin) + dp[i + 1][w.peekLast()] >= dp[i + 1][j])) {
                        w.pollLast();
                    }
                    w.addLast(j);
                    int leftOver = j - (zhangs + 1) * coin; // 左边界，需要舍弃
                    if (w.peekFirst() == leftOver) {
                        w.pollFirst();
                    }
                    dp[i][j] = dp[i + 1][w.peekFirst()] + compensate(w.peekFirst(), j, coin);
                }
            }
        }
        return dp[0][aim] == Integer.MAX_VALUE? -1 : dp[0][aim];
    }

    // 补偿，在不同的位置，有不同的张数，所以要额外加一点
    private static int compensate(int peekLast, int j, int coin) {
        return (j - peekLast) / coin;
    }

    private static Node[] getNodesOfCoins(int[] coins) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : coins) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        Node[] nodes = new Node[map.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            nodes[index++] = new Node(entry.getKey(), entry.getValue());
        }
        return nodes;
    }

    private static class Node {
        public int coin;
        public int zhang;

        public Node(int coin, int zhang) {
            this.coin = coin;
            this.zhang = zhang;
        }
    }
}
