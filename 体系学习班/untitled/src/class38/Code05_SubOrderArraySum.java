package class38;

import java.util.TreeSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-14
 * Time: 20:28
 * Description:
 * 给定一个非负数组arr，和一个正数m，返回arr的所有子序列中累加和%m之后的最大值。
 * （第一解：m不算太大，arr[i]的数据不大，例如1000以内，总之使其代码指令条数在10^8 以内。
 * 第二解：arr[i]的数据很大，m不算太大。
 * 第三解：m和arr[i]都很大，但数组长度比较小。分治）
 */
public class Code05_SubOrderArraySum {
    public static void main(String[] args) {
        int[] arr = {112, 200, 100, 50, 90, 100, 222};
        int m = 300;
        System.out.println(getMaxSum1(arr, m));
        System.out.println(getMaxSum2(arr, m));
        System.out.println(getMaxSum3(arr, m));
        System.out.println(max2(arr, m));
    }

    // 解法一：以index和sum作为行和列
    public static long getMaxSum1(int[] arr, int m) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        int[][] dp = new int[N + 1][sum + 1];
        // 填写最后一行，也就是index = arr.length的时候
        for (int j = 0; j <= sum; j++) {
            dp[N][j] = j % m;
        }
        // 填写普通位置
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= sum; j++) {
                // 每个位置选择要与不要两种情况
                //1、不要这个数
                dp[i][j] = dp[i + 1][j];
                //2、要这个数
                if (j + arr[i] <= sum) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j + arr[i]]);
                }
            }
        }
        return dp[0][0];
    }

    // 解法二：arr[i]的数据很大，m不算太大。就只能以index和m作为行列
    public static long getMaxSum2(int[] arr, int m) {
        if (arr == null) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][m];
        for (int j = 0; j < m; j++) { // 最后一行的数据。base case
            dp[N][j] = j;
        }
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                // 1、不要当前数据
                dp[i][j] = dp[i + 1][j];
                // 2、要当前数
                dp[i][j] = Math.max(dp[i][j], dp[i + 1][(j + arr[i]) % m]);
            }
        }
        return dp[0][0]; // 递归调用是f(arr, m, index, sum)。
    }

    // 解法三：arr[i]和m的数据范围都很大。导致dp表很大。不能在10^8的指令条数内做到。但是arr.length很小。
    // 像这种明显已经超过数据范围，但数据量很少。可以往“分治”方向考虑。
    // 也就是根据尝试，每个数分为要和不要两种状况。假设总共30个数，那纯暴力的解法就是2^30。
    // 分治：划分为左右两部分（根据状况数量），左侧2^15，右侧2^15。然后左右两部分做整合，大概整体能达到2^16的规模。
    public static long getMaxSum3(int[] arr, int m) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        int mid = N / 2;
        TreeSet<Long> set1 = new TreeSet<>();
        long left = process(arr, m, 0, mid, 0, set1); // 左侧的最大结果
        TreeSet<Long> set2 = new TreeSet<>();
        long right = process(arr, m, mid + 1, N - 1, 0, set2); // 右侧的最大结果
        long ans = Math.max(left, right);
        for (long num : set1) {
            Long restNum = set2.floor(m - num - 1);
            if (restNum != null) {
                ans = Math.max(ans, num + restNum);
            }
        }
        return ans;
    }

    // 暴力过程。递归调用的最大深度，是比较小的
    private static long process(int[] arr, int m, int start, int end, long sum, TreeSet<Long> set) {
        if (start == end + 1) {
            set.add(sum);
            return sum;
        }
        long p1 = process(arr, m, start + 1, end, sum, set); // 不要当前数
        long p2 = process(arr, m, start + 1, end, (sum + arr[start]) % m, set);
        return Math.max(p1, p2);
    }

    // 以下的max2和max3都是左神写的，与上面的代码有些许差别。这个代码空间稍微更省一点
    public static int max2(int[] arr, int m) {
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    public static int max3(int[] arr, int m) {
        int N = arr.length;
        // 0...m-1
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                // dp[i][j] T or F
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                if (cur <= j) {
                    dp[i][j] |= dp[i - 1][j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][m + j - cur];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }
}
