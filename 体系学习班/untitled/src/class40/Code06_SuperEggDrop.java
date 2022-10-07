package class40;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-04
 * Time: 15:10
 * Description: https://leetcode.cn/problems/super-egg-drop/
 * 一座大楼有0~N层，地面算作第0层，最高的一层为第N层
 * 已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)
 * 给定整数N作为楼层数，再给定整数K作为棋子数
 * 返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数.
 * <p>
 * 一次只能扔一个棋子
 * N=10，K=1
 * 返回10
 * 因为只有1棵棋子，所以不得不从第1层开始一直试到第10层
 * 在最差的情况下，即第10层是不会摔坏的最高层，最少也要扔10次
 * <p>
 * N=3，K=2
 * 返回2
 * 先在2层扔1棵棋子，如果碎了试第1层，如果没碎试第3层
 * N=105，K=2
 * 返回14
 */
public class Code06_SuperEggDrop {
    public static void main(String[] args) {
        int N = 6;
        int K = 2;
        System.out.println(superEggDrop1(K, N));
        System.out.println(superEggDrop2(K, N));
        System.out.println(superEggDrop3(1, 2));
    }

    // 递归进行尝试，有枚举行为。
    public static int superEggDrop1(int K, int N) {
        if (N <= 0 || K <= 0) {
            return 0;
        }
        if (K == 1) {
            return N;
        }

        int[][] dp = new int[N + 1][K + 1]; // dp[i][j]表示i层，有j个鸡蛋，最差需要扔多少次。
        // 第一行表示第0层，这一层根本就不会碎，全部填写0
        // 第二行表示第1层，只需扔1次就能知道碎没碎，即这一层全是1
        for (int j = 1; j <= K; j++) {
            dp[1][j] = 1;
        }
        // 第一列表示没有鸡蛋，这一列都是无效状态
        // 第二列表示有1个鸡蛋，此时就只能从第1层往上，一层一层的尝试。所以填写N
        for (int i = 2; i <= N; i++) {
            dp[i][1] = i;
        }
        // 推导：函数f(N, K),例如 f(100, 3)表示100层楼，3个鸡蛋，怎么尝试？
        // 只能从1层、2层、3层……开始枚举，尝试鸡蛋碎和没碎，就分为了两种情况
        // 1、碎了。说明从x层扔，会碎。递归调用f(x-1, 2)
        // 2、没碎。说明从x层扔，不会碎。递归调用f(100-x, 3)
        // 因为总是以最坏的运气进行尝试，所以二者取max，表示最坏情况。
        // 填写普遍位置
        for (int i = 2; i <= N; i++) { // 楼层数
            for (int j = 2; j <= K; j++) { // 鸡蛋数
                int ans = Integer.MAX_VALUE;
                for (int x = 1; x <= i; x++) { // 枚举从第1层开始扔
                    ans = Math.min(ans,
                            Math.max(dp[x - 1][j - 1], dp[i - x][j]) + 1); // 要累加上这扔的1次
                }
                dp[i][j] = ans;
            }
        }
        return dp[N][K];
    }

    // 四边形不等式优化。
    public static int superEggDrop2(int K, int N) {
        if (K <= 0 || N <= 0) {
            return 0;
        }

        int[][] dp = new int[N + 1][K + 1];
        int[][] best = new int[N + 1][K + 1]; // 最优划分边界值
        // 第一行和第一列都是0
        // 第二行表示只有1层，只需要扔一次即可
        for (int j = 1; j <= K; j++) {
            dp[1][j] = 1;
            best[1][j] = 1; // 只有1层楼的最优划分边界值
        }
        // 第二列表示只有1个鸡蛋，最坏情况下，只能从第1层往上尝试。即最坏情况就是楼层高度N
        for (int i = 1; i <= N; i++) {
            dp[i][1] = i;
        }
        // 填写普遍位置--从上往下，从右往左填写。
        // 根据分析，dp[i][j]的依赖是 dp[0~i-1][j]这一列和dp[0~i-1][j-1]这一列
        // 这也符合四边形不等式的优化特点。只需凑出上边和右边的dp值即可
        for (int i = 2; i <= N; i++) {
            for (int j = K; j >= 2; j--) {
                int down = best[i - 1][j]; // 下限值
                int up = j == K ? i : best[i][j + 1]; // 上限值
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int x = down; x <= up; x++) { // 在下限和上限之间进行枚举
                    int tmp = Math.max(dp[x - 1][j - 1], dp[i - x][j]);
                    if (tmp <= ans) {
                        ans = tmp;
                        bestChoose = x;
                    }
                }
                best[i][j] = bestChoose;
                dp[i][j] = ans + 1; // 还需加上当前扔的这一次
            }
        }
        return dp[N][K];
    }

    public static int superEggDrop3(int K, int N) {
        if (N <= 0 || K <= 0) {
            return 0;
        }
        // 定义dp表
        // dp[i][j]表示 i个鸡蛋，扔j次。能得到的最高楼层数是多少
        // 第一行，表示只有1个鸡蛋，随着次数的增加，楼层数也跟着增加
        // 第一列，表示只有1次机会，那只能得到1层楼的高度，要做好最坏的打算
        // dp[i][j]的推导过程：
        // 无论怎么扔鸡蛋，无非就是碎和没碎两种情况
        // 1、如果碎了，则鸡蛋数量-1，次数-1，即dp[i-1][j-1]
        // 2、如果没碎，则鸡蛋数量不变，次数-1，即dp[i][j-1]
        // 3、上述二者相加，还额外+1.表示当前扔的这一次，一定能测试出当前x这一层。
        // 图示：[地面…………（碎了，dp[i-1][j-1]）……… x ………(没碎，dp[i][j-1])…………顶层]
        // 根据依赖关系，发现每个位置的计算只依赖于左侧和左上角部分的数据，所以能够做dp空间压缩，
        // 也就无需确定j的范围
        int[] dp = new int[K + 1];
        // dp[0]表示没有鸡蛋，空着不用即可
        // 向右滚动
        int j = 0; // 扔的次数
        while (true) {
            j++; // 扔的次数+1
            int leftUp = 0; // 左上角的值
            for (int i = 1; i < dp.length; i++) {
                int tmp = dp[i]; // 临时保存下一次的左上角的值
                dp[i] += leftUp + 1;
                leftUp = tmp;
                if (dp[i] >= N) {
                    System.out.println("lll: " + dp[i]);
                    return j;
                }
            }
        }
    }


}
