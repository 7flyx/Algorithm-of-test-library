package class41;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-11
 * Time: 16:56
 * Description:
 * 铺砖问题（最优解其实是轮廓线dp，但是这个解法对大厂刷题来说比较难，掌握课上的解法即可）
 * 你有无限的1*2的砖块，要铺满M*N的区域，
 * 不同的铺法有多少种?
 */
public class Code03_PavingTile {
    public static void main(String[] args) {
        int N = 8;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }

    // 普通暴力解
    public static int ways1(int N, int M) {
        if (N <= 0 || M <= 0) {
            return 0;
        }
        int max = Math.max(N, M); // 做行
        int min = Math.min(N, M); // 做列
        int[] pre = new int[min]; // 0表示没有铺装，1表示已经铺砖了
        Arrays.fill(pre, 1); // 表示上一层已经全部铺满了
        return process(pre, 0, max);
    }

    private static int process(int[] pre, int level, int row) {
        if (level == row) { // 已经来到了最后一层
            // 判断pre这一层，是不是全部填满了
            for (int num : pre) {
                if (num == 0) {
                    return 0;
                }
            }
            return 1; // cur这一层，已经全部填满了，说明当前就是一种铺法
        }

        int[] cur = getCurLevel(pre);
        // 当前这一层的状况已经出来，
        // 决策就是: 比如 001110101
        // 尝试着将砖横着放。
        return dfs(cur, level, 0, row);
    }

    private static int dfs(int[] cur, int level, int col, int row) {
        if (col == cur.length) {
            return process(cur, level + 1, row);
        }
        int ans = 0;
        // 1、当前位置不横着放砖
        ans += dfs(cur, level, col + 1, row);
        // 2、当前位置尝试着 横着放砖
        if (col + 1 < cur.length && cur[col] == 0 && cur[col + 1] == 0) {
            cur[col] = 1;
            cur[col + 1] = 1;
            ans += dfs(cur, level, col + 2, row);
            cur[col] = 0;
            cur[col + 1] = 0;
        }
        return ans;
    }

    private static int[] getCurLevel(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < cur.length; i++) {
            // 上一层已经铺了，这一层就不用铺。上一层没有铺的，砖只能竖着放，把pre和cur的i位置都铺了
            cur[i] = pre[i] == 1 ? 0 : 1;
        }
        return cur;
    }

    // 状态压缩的暴力解
    public static int ways2(int N, int M) {
        if (N <= 0 || M <= 0) {
            return 0;
        }
        int max = Math.max(N, M); // 做行
        int min = Math.min(N, M); // 做列
        int pre = (1 << min) - 1; // 最右边的min个位置都是1。1表示已经铺了砖，0表示还没有铺砖
        return process2(pre, 0, max, min);
    }

    private static int process2(int pre, int level, int row, int col) {
        if (level == row) { // 来到了最后一行，检查cur的状态
            return pre == ((1 << col) - 1) ? 1 : 0;
        }
        int cur = ((1 << col) - 1) & (~pre); // 将pre每个位置0->1, 1->0
        return dfs2(cur, level, 0, row, col);
    }

    private static int dfs2(int cur, int level, int i, int row, int col) {
        if (i == col) {
            return process2(cur, level + 1, row, col);
        }
        // 尝试着横向铺砖
        int ans = 0;
        ans += dfs2(cur, level, i + 1, row, col); // 当前位置不铺砖
        if (i + 1 < col && (((1 << i) & cur) == 0) && (((1 << (i + 1)) & cur) == 0)) {
            ans += dfs2((cur | (3 << i)), level, i + 2, row, col);
        }
        return ans;
    }

    // 记忆化搜索
    public static int ways3(int N, int M) {
        if (N <= 0 || M <= 0) {
            return 0;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        int[][] dp = new int[max + 1][1 << min];
        for (int i = 0; i <= max; i++) {
            Arrays.fill(dp[i], -1); // 无效状态
        }
        return process3(pre, 0, max, min, dp);
    }

    private static int process3(int pre, int level, int row, int col, int[][] dp) {
        if (dp[level][pre] != -1) {
            return dp[level][pre];
        }
        int ans = 0;
        if (level == row) { // 到了最后一行
            ans = (pre == (1 << col) - 1) ? 1 : 0; // 上一层全部填写完了，就表示一种铺法
        } else {
            int cur = (~pre) & ((1 << col) - 1); // 使其1->0, 0->1
            ans = dfs3(cur, level, 0, row, col, dp);
        }
        dp[level][pre] = ans;
        return ans;
    }

    private static int dfs3(int cur, int level, int i, int row, int col, int[][] dp) {
        if (i == col) {
            return process3(cur, level + 1, row, col, dp);
        }
        int ans = 0;
        ans += dfs3(cur, level, i + 1, row, col, dp); // 当前位置不放砖
        // 尝试着横着放砖
        if (i + 1 < col && (cur & (1 << i)) == 0 && (cur & (1 << (i + 1))) == 0) {
            ans += dfs3((cur | (3 << i)), level, i + 2, row, col, dp);
        }
        return ans;
    }

    // 严格依赖的动态规划版本
    public static int ways4(int N, int M) {
        if (N <= 0 || M <= 0) {
            return 0;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1; // 右侧min位全是1
        int[][] dp = new int[max + 1][pre + 1];
        dp[max][pre] = 1; // base case. 这一行其他位置全是0
        for (int level = max - 1; level >= 0; level--) { // 从下往上
            for (int preStatus = 0; preStatus <= pre; preStatus++) { // 从左往右
                int cur = (~preStatus) & pre; // 得到当前这一行的状态，然后去横向铺砖
                dp[level][preStatus] = dfs4(cur, level, 0, max, min, dp);
            }
        }
        return dp[0][pre]; // 返回右上角的值即可
    }

    private static int dfs4(int cur, int level, int i, int row, int col, int[][] dp) {
        if (i == col) {
            return dp[level + 1][cur];
        }
        int ans = 0;
        ans += dfs4(cur, level, i + 1, row, col, dp); // 当前位置不铺砖的情况
        if (i + 1 < col && (cur & (1 << i)) == 0 && (cur & (1 << (i + 1))) == 0) {
            ans += dfs4((cur | (3 << i)), level, i + 2, row, col, dp); // 当前位置以及右侧的位置，横向放一块砖
        }
        return ans;
    }
}
