package class40;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-04
 * Time: 15:08
 * Description:
 * 一条直线上有居民点，邮局只能建在居民点上
 * 给定一个有序正数数组arr，每个值表示 居民点的一维坐标，再给定一个正数 num，表示邮局数量
 * 选择num个居民点建立num个邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 * arr=[1,2,3,4,5,1000]，num=2
 * 第一个邮局建立在3位置，第二个邮局建立在1000位置
 * 那么1位置到邮局的距离为2，2位置到邮局距离为1，3位置到邮局的距离为0，4位置到邮局的距离为1，5位置到邮局的距离为2
 * 1000位置到邮局的距离为0
 * 这种方案下的总距离为6，其他任何方案的总距离都不会比该方案的总距离更短，所以返回6
 */
public class Code05_PostOfficeProblem {
    public static void main(String[] args) {
        int testTime = 10000;
        int length = 100;
        int range = 10000;
        System.out.println("test started.");
        for (int i = 0; i < testTime; i++) {
            int[] arr = getRandomArray(length, range);
            int num = (int) (Math.random() * 20) + 1;
            int ans1 = postOfficeProblem1(arr, num);
            int ans2 = postOfficeProblem2(arr, num);
            if (ans1 != ans2) {
                System.out.println("ans1: " + ans1);
                System.out.println("ans2: " + ans2);
                System.out.println("error.");
                break;
            }
        }
        System.out.println("test finished.");
    }

    // 枚举过程，时间复杂度O(N^2 * num)
    public static int postOfficeProblem1(int[] arr, int num) {
        if (arr == null || num <= 0) {
            return 0;
        }
        // 整体思路跟画家问题类似
        // dp[i][j]表示 在0~i范围，用j个邮局怎么进行划分，得到的最短距离
        // 在0~k范围，用j-1个邮局。剩下的k+1~i范围，用第j个邮局。这样进行尝试
        int N = arr.length;
        int[][] dp = new int[N][num + 1];
        int[][] w = getWArray(arr);
        // 只有一个居民点的时候，还能建立邮局的时候，最短距离就是0
//        dp[0][0~j] = 0;
        // 没有邮局的时候，就是无效状态，距离也是0
//        dp[0~i][0] = 0;
        //只有1个邮局的时候，就变成了i~j范围的居民点，如何建立。在w数组已经计算过了
        for (int i = 0; i < N; i++) {
            dp[i][1] = w[0][i];
        }
        // 填写普遍位置-从第3列开始，从上往下填写
        for (int j = 2; j <= num; j++) { // 邮局数量
            for (int i = 1; i < N; i++) {
                // 还剩j个邮局，如何分配给0~i个居民点
                // 当前第j个邮局负责0个、1个、2个居名点……，依次枚举全部过程
                int ans = Integer.MAX_VALUE;
                for (int leftEnd = i; leftEnd >= -1; leftEnd--) { // leftEnd=-1的时候，表示左部分没有居民点。居民点全在右部分
                    int leftDistance = leftEnd >= 0 ? dp[leftEnd][j - 1] : 0;
                    int rightDistance = leftEnd + 1 <= i ? w[leftEnd + 1][i] : 0;
                    int tmp = leftDistance + rightDistance;
                    if (tmp < ans) {
                        ans = tmp;
                    }
                }
                dp[i][j] = ans; // 存储最短距离结果
            }
        }
        return dp[N - 1][num]; // 返回右下角即可
    }

    // 四边形不等式优化，时间复杂度O(N * num)，能减少枚举的次数
    public static int postOfficeProblem2(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num <= 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N][num + 1];
        int[][] best = new int[N][num + 1]; // 存储最优划分边界值 的右边界
        int[][] w = getWArray(arr); // 只有1个邮局的时候，每个区间范围的最短距离
        // 只有1个邮局的时候，就是拿取w数组的某个范围内的数据即可
        for (int i = 0; i < N; i++) {
            dp[i][1] = w[0][i];
            best[i][1] = -1; // 只有1个邮局时，最优划分边界是-1
        }
        // 只有1个居民点时，只要还有邮局，最短距离就是0
        // dp[0][1~num] = 0;
        // 没有邮局的时候，dp[0~i][0] = 0
        for (int j = 2; j <= num; j++) { // 邮局数量
            for (int i = N - 1; i >= 1; i--) {
                int down = best[i][j - 1]; // 下限值。左部分的末尾下标
                int up = i == N - 1 ? i : best[i + 1][j]; // 上限值
                int ans = Integer.MAX_VALUE;
                int choose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {// 在下限值和上限值之间进行枚举
                    int leftDistance = leftEnd >= 0 ? dp[leftEnd][j - 1] : 0;
                    int rightDistance = leftEnd + 1 <= i ? w[leftEnd + 1][i] : 0;
                    int tmp = leftDistance + rightDistance;
                    if (tmp <= ans) {
                        ans = tmp;
                        choose = leftEnd; // 记录最优划分边界值
                    }
                }
                best[i][j] = choose; // 存储最优划分边界值
                dp[i][j] = ans;
            }
        }
        return dp[N - 1][num]; // 返回右下角的数据即可
    }

    // 返回一个二维数组，w[i][j]表示在i~j范围的居民点，只建立一个邮局的情况
    // 如何使其每个居民点到邮局的距离最短。
    private static int[][] getWArray(int[] arr) {
        int N = arr.length;
        int[][] w = new int[N][N];
        /*
            划分结果如下：
                1、奇数个居民点的时候，邮局建立在“中间点”即可
                2、偶数个居民点的时候，邮局建立在 “上中点”或者“下中点”的时候，二者都能够得到最短的距离。
            所以只存在一个邮局的时候，推导出所有组合内的居民点的选取是有技巧的：
                1、当前是奇数，+1后变成了偶数。此时新增点处的结果是 w[i][j-1] + (arr[j] - 奇数时中间点的arr值)
                2、当前是偶数，+1后变成了奇数。此时新增点处的结果是 w[i][j-1] + (arr[j] - 偶数时的中点值，上下中点值一样)
         */
        // 只有1个居民点的时候，邮局只能建立在这个居民点上，距离=0
        // dp[i][i] = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                w[i][j] = w[i][j - 1] + (arr[j] - arr[(i + j) >> 1]); // i~j范围内的中间值
            }
        }
        return w;
    }

    // for test
    private static int[] getRandomArray(int length, int range) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * range) + 1;
        }
        Arrays.sort(arr);
        return arr;
    }
}
