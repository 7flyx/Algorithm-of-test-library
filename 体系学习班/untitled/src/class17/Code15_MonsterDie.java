package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-20
 * Time: 16:02
 * Description: 怪兽被砍死的概率。
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 */
public class Code15_MonsterDie {
    public static void main(String[] args) {
        int N = 5;
        int M = 10;
        int K = 2;
        System.out.println(monsterDie1(N, M, K));
        System.out.println(monsterDie2(N, M, K));
        System.out.println(monsterDie3(N, M, K));
        System.out.println(monsterDie4(N, M, K));
        System.out.println(monsterDie5(N, M, K));
    }

    // 先计算存活的概率，然后用1减，得到死亡的概率
    public static double monsterDie1(int N, int M, int K) {
        if (N <= 0 || M < 0 || K < 0) {
            return 0;
        }

        // 计算生存的概率
        int live = process(N, M, K);
        return 1 - live / Math.pow(M + 1, K);
    }

    private static int process(int N, int M, int K) {
        if (N <= 0) { // 怪兽已经死了，直接返回
            return 0;
        }
        if (K == 0) {
            return 1;
        }
        int ans = 0;
        for (int i = 0; i <= M; i++) {
            ans += process(N - i, M, K - 1);
        }
        return ans;
    }

    // 直接计算死亡的概率，唯一需要注意的是 不能在函数中提前判断血量，血量小于0后，还应继续往下递归
    public static double monsterDie2(int N, int M, int K) {
        if (N <= 0 || M < 0 || K < 0) {
            return 0;
        }
        long die = process2(N, M, K);
        return die / Math.pow(M + 1, K);
    }

    // 死亡的方法数
    private static long process2(int N, int M, int K) {
        if (K == 0) {
            return N <= 0 ? 1 : 0;
        }
        long ans = 0;
        for (int i = 0; i <= M; i++) {
            ans += process2(N - i, M, K - 1);
        }
        return ans;
    }

    // 经典dp版本---根据monsterDie1改
    public static double monsterDie3(int N, int M, int K) {
        if (N <= 0 || M < 0 || K < 0) {
            return 0;
        }
        // 可变参数有两个：N和K
        int[][] dp = new int[K + 1][N + 1];
        //base case，生存的节点
        for (int j = 1; j <= N; j++) {
            dp[0][j] = 1;
        }
        // 填写普遍位置
        for (int k = 1; k <= K; k++) {
            for (int n = 0; n <= N; n++) {
                // 枚举每一刀的血量
                int ans = 0;
                for (int i = 0; i <= M && i <= n; i++) {
                    if (n - i >= 0) {
                        ans += dp[k - 1][n - i];
                    }
                }
                dp[k][n] = ans;
            }
        }
        return 1 - dp[K][N] / Math.pow(M + 1, K);
    }

    // 经典dp版本---斜率优化
    public static double monsterDie4(int N, int M, int K) {
        if (N <= 0 || M < 0 || K < 0) {
            return 0;
        }
        // 可变参数有两个：N和K
        int[][] dp = new int[K + 1][N + 1];
        //base case，生存的节点
        for (int j = 1; j <= N; j++) {
            dp[0][j] = 1;
        }

        // 填写普遍位置
        for (int k = 1; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                dp[k][n] = dp[k][n - 1] + dp[k - 1][n]; // 一刀，0血，直接拿取上边的值+左边的值
                // 左边的值也是会枚举计算上一行的数据，因为总的计算宽度是M+1，所以
                // 计算当前位置的值的时候，可以直接拿取左边的值+当前位置上边的值
                // 因为左边的值，对于当前位置来说，多计算了一个最左侧的数据，需要减去
                if (n - M - 1 >= 0) {
                    dp[k][n] -= dp[k - 1][n - M - 1];
                }
            }
        }
        return 1 - dp[K][N] / Math.pow(M + 1, K);
    }

    public static double monsterDie5(int N, int M, int K) {
        if (N <= 0 || M < 0 || K < 0) {
            return 0;
        }
        // 可变参数有两个：N和K，再看依赖关系，每个位置只依赖于当前行的左边和上一行的右边位置（从左往右）
        // 所以用两个一维数组代替即可
        int[] preDp = new int[N + 1];
        int[] curDp = new int[N + 1];
        //base case，生存的节点
        for (int j = 1; j <= N; j++) {
            preDp[j] = 1;
        }
        // 填写普遍位置
        for (int k = 1; k <= K; k++) {
            for (int n =1; n <= N; n++) {
                curDp[n] = preDp[n] + curDp[n - 1]; // 当前行左边的值+上一行上边的值
                if (n - M - 1 >= 0) { // 减去最左侧多计算的位置
                    curDp[n] -= preDp[n - M - 1];
                }
            }
            int[] tmp = curDp;
            curDp = preDp;
            preDp = tmp;
        }
        return 1 - preDp[N] / Math.pow(M + 1, K);
    }
}
