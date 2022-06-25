package class17;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-23
 * Time: 13:24
 * Description:
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 */
public class Code18_LesserGatherSum1 {
    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 100, 10, 2};
        System.out.println(lesserGatherSum1(arr));
        System.out.println(lesserGatherSum2(arr));
        System.out.println(lesserGatherSum3(arr));
    }

    // 暴力递归版本
    public static int lesserGatherSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return process(arr, 0, sum / 2);
    }

    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        }
        int p1 = process(arr, index + 1, rest); // 当前位置的数不要
        int p2 = -1;
        if (arr[index] <= rest) {
            p2 = process(arr, index + 1, rest - arr[index]) + arr[index];
        }
        return Math.max(p1, p2);
    }

    // 经典dp版本
    public static int lesserGatherSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        // 根据递归版本，可知可变参数有2个：index和rest
        int N = arr.length;
        int[][] dp = new int[N + 1][sum + 1];
//        Arrays.fill(dp[N], 0); // 最后一行全部填0
        for(int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                int p1 = dp[i + 1][rest];
                int p2 = -1;
                if (arr[i] <= rest) {
                    p2 = dp[i + 1][rest - arr[i]] + arr[i];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    // dp空间压缩
    public static int lesserGatherSum3(int[] arr) {
        if (arr ==  null || arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int N = arr.length;
        // 根据经典dp的代码，可以知道，每个位置依赖于他下一行，所以只需要一个一维数组即可
        int[] dp = new int[sum + 1];
        // dp[0……sum] = 0
        for (int i = N - 1; i >= 0; i--) {
            // 需要从右往左更新，因为每个位置依赖于他下一行的左边
            for (int rest = sum; rest >= 0; rest--) {
                // dp[i][rest] = dp[i+1][rest];
                if (arr[i] <= rest) {
                    dp[rest] = Math.max(dp[rest], arr[i] + dp[rest - arr[i]]);
                }
            }
        }
        return dp[sum];
    }
}
