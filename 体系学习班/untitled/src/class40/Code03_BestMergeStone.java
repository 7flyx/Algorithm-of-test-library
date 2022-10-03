package class40;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-30
 * Time: 12:44
 * Description:
 * 摆放着n堆石子。现要将石子有次序地合并成一堆，规定每次只能选相邻的2堆石子合并成新的一堆
 * 并将新的一堆石子数记为该次合并的得分，求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案。
 * <p>
 * 提示：范围内尝试模型。一个做行，一个做列。
 */
public class Code03_BestMergeStone {
    public static void main(String[] args) {
        int[] stones = {10, 30, 20, 40};
        System.out.println(minCostOfMergeStone2(stones));
        System.out.println(minCostOfMergeStone3(stones));
    }

    public static int[] sum(int[] arr) {
        int N = arr.length;
        int[] s = new int[N + 1];
        s[0] = 0;
        for (int i = 0; i < N; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        return s;
    }

    public static int w(int[] s, int l, int r) {
        return s[r + 1] - s[l];
    }

    public static int min1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] s = sum(arr);
        return process1(0, N - 1, s);
    }

    public static int process1(int L, int R, int[] s) {
        if (L == R) {
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int leftEnd = L; leftEnd < R; leftEnd++) {
            next = Math.min(next, process1(L, leftEnd, s) + process1(leftEnd + 1, R, s));
        }
        return next + w(s, L, R);
    }

    // 有枚举过程dp版本。时间复杂度O(N^3)
    public static int minCostOfMergeStone2(int[] stones) {
        if (stones == null || stones.length < 2) {
            return 0;
        }
        int N = stones.length;
        int[][] dp = new int[N][N];
        //dp[i][j]的含义是 i~j范围内，合并的最小代价。
        // i == j，也就是对角线时，表示只有一块石头，合并代价为0
        // 只有两块石头时，即 i + 1 == j。合并代价就是二者相加
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = stones[i] + stones[i + 1];
        }
        // 填写普遍位置。从下往上，从左往右。
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) { // i == i和 i +1 == j，这两个位置已经填了，直接填i+2即可
                // 假设i = 2, j = 5
                // 依赖位置->
                // 1、(2)+(3~5)
                // 2、(2~3)+ (4~5)
                // 3、(2~4) + (5)
                // 整体思路：将i~j，划分为两个部分，拿取下层调用的状态即可
                dp[i][j] = Integer.MAX_VALUE; // 无效状态
                for (int k = 0; k < j - i; k++) { // 枚举全部
                    dp[i][j] = Math.min(dp[i][j],
                            dp[i][i + k] + dp[i + k + 1][j]); // 划分为两个部分
                }
                dp[i][j] += w2(stones, i, j); // 还需累加上 i~j全部的代价和。即stones[i] + stones[i+1] + .....
            }
        }
        return dp[0][N - 1]; // 返回右上角。即0~N-1范围，最小合并代价。
    }

    private static int w2(int[] stones, int i, int j) {
        int sum = 0;
        for (; i <= j; i++) {
            sum += stones[i];
        }
        return sum;
    }

    // 缩减枚举次数的dp。能使时间复杂度降一阶。（数学证明得出的）
    public static int minCostOfMergeStone3(int[] stones) {
        if (stones == null || stones.length < 2) {
            return 0;
        }
        /*
            根据上面的dp，可得出 整个流程是 从下往上 从左往右填写
            而 在填写dp[i][j]时，dp[i][j-1] 和 dp[i+1][j]这两个位置已经填好了
            分别代表的范围是 i~j-1 和 i+1~j。 这两个范围合起来就是 i~j范围。
            而在填写这两个位置的时候，一定会存在一个 “最优划分边界值”（数组累加和划分问题）
            它能够使其 “不回退“。这就是优化的地方。称为 四边形不等式优化。
            所谓的四边形指的是 (左边格子+下边格子) 和 (上边格子+右边格子)。分别确定了dp[i][j]枚举时的下限值和上限值。
         */
        int N = stones.length;
        int[] preSum = sum(stones); // 前缀和数组。多开一个位置，为后续简化边界判断。
        int[][] dp = new int[N][N];
        int[][] best = new int[N][N]; // 存储相应范围划分的最优边界值。（左区间的末尾下标）
        best[N - 1][N - 1] = -1; // 右下角
        // dp[i][i] = 0
        // 填写i + 1 == j时的格子。也就是 只有两块石头的时候
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = stones[i] + stones[i + 1];
            best[i][i] = -1; // 填写best表. 只有一个石头时，不需要划分边界。填-1
            // 只有两个石头时，划分边界就是在二者之间。填左部分的末尾下标即可
            best[i][i + 1] = i;
        }

        // 填写普遍位置
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                int down = best[i][j - 1]; // 下限边界
                int up = best[i + 1][j]; // 上限边界
                int choose = -1; // 最优划分边界
                int ans = Integer.MAX_VALUE; // 无效状态
                for (int leftEnd = down; leftEnd <= up; leftEnd++) { // 在下限和上限之间进行枚举
                    // k有可能是-1的，需要判断一下。
                    if (leftEnd != -1 && dp[i][leftEnd] + dp[leftEnd + 1][j] < ans) {
                        ans = dp[i][leftEnd] + dp[leftEnd + 1][j];
                        choose = leftEnd;
                    }
                }
                dp[i][j] = ans + w(preSum, i, j);
                best[i][j] = choose;
            }
        }
        return dp[0][N - 1];
    }
}
