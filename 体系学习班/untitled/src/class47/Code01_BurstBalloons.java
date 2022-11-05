package class47;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-05
 * Time: 15:00
 * Description: LeetCode312题、戳气球问题
 * 有n个气球，编号为0到n-1，每个气球上都标有一个数字，这些数字存在数组nums中
 * 现在要求你戳破所有的气球。戳破第i个气球，你可以获得nums[i - 1] * nums[i] * nums[i + 1] 枚硬币
 * 这里的i-1和i+1代表和i相邻的、没有被戳爆的！两个气球的序号
 * 如果i-1或i+1超出了数组的边界，那么就当它是一个数字为1的气球
 * 求所能获得硬币的最大数量
 */
public class Code01_BurstBalloons {
    public static void main(String[] args) {
        int[] nums = {3, 1, 5, 8};
        System.out.println(maxCoins0(nums));
        System.out.println(maxCoins1(nums));
        System.out.println(maxCoins2(nums));
    }

    // 暴力递归版本---以某个位置的气球最后打爆为目标
    public static int maxCoins0(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1; // 左右两边设置为1，表示边界
        for (int i = 0; i < N; i++) {
            help[i + 1] = nums[i];
        }
        return process(help, 1, N);
    }

    /**
     * 在L~R范围内，打爆所有气球的最大得分。
     * 前提条件是L-1位置 和R+1位置的气球是 没有爆的。（这个潜规则很重要）
     * 思路是：以每个位置的气球最后打爆为目标，以这个思路进行枚举。
     *
     * @param help 处理过后的气球数组
     * @param L    左边界
     * @param R    右边界
     * @return 返回最大得分
     */
    private static int process(int[] help, int L, int R) {
        if (L == R) { // 还只剩一个气球的时候
            return help[L - 1] * help[L] * help[R + 1];
        }
        // L位置的气球最后打爆。比如： L-1  L....R  R + 1。先打爆 (L+1,R)范围的气球
        int ans = process(help, L + 1, R) + help[L - 1] * help[L] * help[R + 1];
        // R位置的气球最后打爆。
        ans = Math.max(ans, process(help, L, R - 1) + help[L - 1] * help[R] * help[R + 1]);
        // 枚举L+1~R-1范围的气球
        for (int i = L + 1; i < R; i++) { // i位置的气球最后打爆
            int left = process(help, L, i - 1);
            int right = process(help, i + 1, R);
            int cur = help[L - 1] * help[i] * help[R + 1];
            if (left + right + cur > ans) {
                ans = left + right + cur;
            }
        }
        return ans;
    }

    // 记忆化搜索版本
    public static int maxCoins1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = nums[i];
        }

        // 根据递归的版本可知，L和R的范围在1~N之间
        int[][] dp = new int[N + 1][N + 1];
        return process1(help, 1, N, dp);
    }

    private static int process1(int[] help, int L, int R, int[][] dp) {
        if (dp[L][R] != 0) {
            return dp[L][R];
        }
        if (L == R) {
            dp[L][R] = help[L - 1] * help[L] * help[R + 1];
            return dp[L][R];
        }
        // L位置的气球最后打爆
        int ans = process1(help, L + 1, R, dp) + help[L - 1] * help[L] * help[R + 1];
        // R位置的气球最后打爆
        ans = Math.max(ans, process1(help, L, R - 1, dp) + help[L - 1] * help[R] * help[R + 1]);
        // 枚举L+1~R-1范围内的气球
        for (int i = L + 1; i < R; i++) { // i位置的气球最后打爆
            int left = process1(help, L, i - 1, dp);
            int right = process1(help, i + 1, R, dp);
            int cur = help[L - 1] * help[i] * help[R + 1];
            if (left + right + cur > ans) {
                ans = left + right + cur;
            }
        }
        dp[L][R] = ans;
        return ans;
    }

    // dp版本
    public static int maxCoins2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int[] help = new int[N + 2];
        help[0] = help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = nums[i];
        }
        int[][] dp = new int[N + 1][N + 1];
        // base case是 L==R的时候
        for (int i = 1; i <= N; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        // 填写普遍位置---从下往上填，只填右上角部分
        for (int L = N - 1; L >= 1; L--) { // 行
            for (int R = L + 1; R <= N; R++) { // 列
                // 1、L位置的气球最后打爆
                // 2、R位置的气球最后打爆
                dp[L][R] = Math.max(dp[L + 1][R] + help[L - 1] * help[L] * help[R + 1],
                        dp[L][R - 1] + help[L - 1] * help[R] * help[R + 1]);
                // 枚举L+ 1~R-1范围上的气球
                for (int i = L + 1; i < R; i++) {
                    int left = dp[L][i - 1];
                    int right = dp[i + 1][R];
                    int cur = help[L - 1] * help[i] * help[R + 1];
                    dp[L][R] = Math.max(dp[L][R], left + right + cur);
                }
            }
        }
        return dp[1][N];
    }
}
