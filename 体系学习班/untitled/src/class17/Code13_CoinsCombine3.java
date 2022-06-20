package class17;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-17
 * Time: 21:10
 * Description:
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class Code13_CoinsCombine3 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 1, 2, 1, 2};
        int aim = 4;
        System.out.println(coinsCombine1(arr, aim));
        System.out.println(coinsCombine2(arr, aim));
        System.out.println(coinsCombine3(arr, aim));
        System.out.println(coinsCombine4(arr, aim));
    }

    private static class Node {
        public int coin; // 货币面值
        public int sum; // 货币数量

        public Node(int coin, int sum) {
            this.coin = coin;
            this.sum = sum;
        }
    }

    private static Node[] getAllCoins(int[] coins) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : coins) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        Node[] res = new Node[map.size()];
        int index = 0;
        // 将map中的全部数据放入Node数组中
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            res[index++] = new Node(entry.getKey(), entry.getValue());
        }
        return res;
    }

    // 暴力递归版本
    public static int coinsCombine1(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] arr = getAllCoins(coins);
        return process1(arr, aim, 0);
    }

    private static int process1(Node[] coins, int rest, int index) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0; // 方法数
        }
        int sum = coins[index].sum;
        int coin = coins[index].coin;
        int ways = 0;
        for (int zhang = 0; zhang * coin <= rest && zhang <= sum; zhang++) {
            ways += process1(coins, rest - zhang * coin, index + 1);
        }
        return ways;
    }

    // 经典dp版本---有枚举行为
    public static int coinsCombine2(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] arr = getAllCoins(coins);
        int N = arr.length;
        // 根据暴力递归版本可知，可变参数有两个：index和aim
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[i].coin <= j && zhang <= arr[i].sum; zhang++) {
                    ways += dp[i + 1][j - zhang * arr[i].coin];
                }
                dp[i][j] = ways;
            }
        }
        return dp[0][aim];
    }

    // 经典dp版本---斜率优化
    public static int coinsCombine3(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] arr = getAllCoins(coins);
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) { // 指向arr数组
            for (int j = 0; j <= aim; j++) { // 也就是rest，剩余的钱数
                // 因为每一种货币都有了一定的张数限制，所以斜率优化的时候，不能只
                // 累加 每个位置 左手边的数值，还需减去左手边的数值所掌控范围的最左边的那个数据
                int sum = arr[i].sum;
                int coin = arr[i].coin;
                dp[i][j] = dp[i + 1][j]; // 当前位置的货币，一张也不要的情况
                if (j - coin >= 0) {
                    dp[i][j] += dp[i][j - coin];
                    if (j - coin * (sum + 1) >= 0) { // 减去dp[i][j-coin]范围内，最左边的那个数（多算了）
                        dp[i][j] -= dp[i + 1][j - coin * (sum + 1)];
                    }
                }
            }
        }
        return dp[0][aim];
    }

    // dp空间压缩+斜率优化
    public static int coinsCombine4(int[] coins, int aim) {
        if (coins == null || coins.length == 0) {
            return 0;
        }
        Node[] arr = getAllCoins(coins);
        int N = arr.length;
        // 根据经典dp+斜率优化的版本，可以看出整个代码中，
        // 每个位置依赖于它当前行的左边 和 下一行的左边和下边，所以用两个一维数组交替使用即可
        int[] curDp = new int[aim + 1];
        int[] nextDp = new int[aim + 1];
        nextDp[0] = 1; // base case
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int sum = arr[i].sum;
                int coin = arr[i].coin;
                curDp[j] = nextDp[j]; // 当前货币一个也不要的情况
                if (j - coin >= 0) {
                     curDp[j] += curDp[j - coin]; // 累加当前行的左边
                    if (j - coin * (sum + 1) >= 0) {
                        curDp[j] -= nextDp[j - coin * (sum + 1)]; // 减去多计算的位置
                    }
                }
            }
            // 两个数组交替
            int[] tmp = curDp;
            curDp = nextDp;
            nextDp = tmp;
        }
        return nextDp[aim];
    }
}
