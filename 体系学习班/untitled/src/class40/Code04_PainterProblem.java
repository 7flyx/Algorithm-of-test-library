package class40;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-30
 * Time: 18:23
 * Description: 测试链接：https://leetcode.cn/problems/split-array-largest-sum/
 * 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定一个整数num
 * 表示画匠的数量，每个画匠只能画连在一起的画作
 * 所有的画家并行工作，返回完成所有的画作需要的最少时间
 * arr=[3,1,4]，num=2。
 * 最好的分配方式为第一个画匠画3和1，所需时间为4
 * 第二个画匠画4，所需时间为4
 * 所以返回4
 * arr=[1,1,1,4,3]，num=3
 * 最好的分配方式为第一个画匠画前三个1，所需时间为3
 * 第二个画匠画4，所需时间为4
 * 第三个画匠画3，所需时间为3
 * 返回4
 */
public class Code04_PainterProblem {
    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6, 7, 7, 8, 2, 3, 1, 1, 1, 10, 11, 5, 6, 2, 4, 7, 8, 5, 6};
        int num = 20;
        System.out.println(splitArray2(arr, num));
        System.out.println(splitArray3(arr, num));
    }

    // 含有枚举过程的dp。O(N^2 * num)
    public static int splitArray1(int[] arr, int num) {
        if (arr == null || num <= 0) {
            return 0;
        }
        int[] preSum = sum(arr); // 前缀和数组，多开一个空间，为了后续简化边界判断
        int N = arr.length;
        int[][] dp = new int[N][num + 1]; // dp[i][j]表示 完成0~i幅画，有j个画家，最少花费多少时间。
        // dp[0~i][0]第一列全是0
        //第一行
        for (int j = 1; j <= num; j++) {
            dp[0][j] = arr[0];
        }
        // 第二列
        for (int i = 1; i < N; i++) {
            dp[i][1] = dp[i - 1][1] + arr[i];
        }
        // 填写普遍位置。 从左往右，从下往上填。一列一列的填
        for (int j = 2; j <= num; j++) {
            for (int i = 1; i < N; i++) {
                int ans = Integer.MAX_VALUE;
                for (int k = i; k >= 0; k--) { // 最后一个画家独立完成的画的数量。枚举
                    // 0~k幅画让j-1个画家完成，k+1 ~ i的画，让第j个画家完成
                    ans = Math.min(ans,
                            Math.max(dp[k][j - 1], w(preSum, k + 1, i)));
                }
                dp[i][j] = ans;
            }
        }
        return dp[N - 1][num];
    }

    // 优化枚举过程的dp。时间O(N * num)
    public static int splitArray2(int[] arr, int num) {
        if (arr == null || num <= 0) {
            return 0;
        }
        int N = arr.length;
        int[] preSum = sum(arr); // 前缀和数组，多开一个空间，为了后续简化边界判断
        int[][] dp = new int[N][num + 1]; // dp[i][j]表示 完成0~i幅画，有j个画家，最少花费多少时间。
        int[][] best = new int[N][num + 1]; // 存储最优边界划分值。左部分的末尾下标值
        // dp[0~i][0]第一列全是0。best第一列，根本不会用到，不用填写
        //第一行
        for (int j = 1; j <= num; j++) {
            dp[0][j] = arr[0];
            best[0][j] = -1; // 只有1幅画，无法分割。只能是-1
        }
        // 第二列
        for (int i = 1; i < N; i++) {
            dp[i][1] = dp[i - 1][1] + arr[i];
            best[i][1] = -1; // 只有一个画家，也无需分割。只能是-1
        }
        // 填写普遍位置。 从左往右，从下往上填。一列一列的填。要凑（左、下）这样的对儿
        for (int j = 2; j <= num; j++) {
            // 首先更新这一列的最后一行的数据。再往上推导这一列。dp[i][j] 依赖于 dp[i][j-1]和dp[i+1][j]
            int ans = Integer.MAX_VALUE;
            int choose = -1; // 最优划分边界值
            for (int k = N - 1; k >= 0; k--) {
                // 0~k幅画让j-1个画家完成，k+1 ~ i的画，让第j个画家完成
                int tmp = Math.max(dp[k][j - 1], w(preSum, k + 1, N - 1));
                // 注意下面的if一定是 < 课上的错误就是此处！当时写的 <= ！
                // 也就是说，只有取得明显的好处才移动！
                // 举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优:
                // (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责
                // (2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
                // 第一种方案划分为，[0~2] [3~3]
                // 第二种方案划分为，[0~3] [无]
                // 两种方案的答案都是8，但是划分点位置一定不要移动!
                // 只有明显取得好处时(<)，划分点位置才移动!
                // 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
                // 比如上面的两个方案，如果你移动到了方案二，你会得到:
                // [2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
                // 最优划分点为3位置(best[3][3])
                // 那么当4个画匠时，也就是求解dp[3][4]时
                // 因为best[3][3] = 3，这个值提供了dp[3][4]的下限
                // 而事实上dp[3][4]的最优划分为:
                // [0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
                // 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
                // 提供了对数器验证，你可以改成<=，对数器和leetcode都过不了
                // 这里是<，对数器和leetcode都能通过
                // 这里面会让同学们感到困惑的点：
                // 为啥==的时候，不移动，只有<的时候，才移动呢？例子懂了，但是道理何在？
                // 哈哈哈哈哈，看了邮局选址问题，你更懵，请看42节！
                if (tmp < ans) {
                    ans = tmp;
                    choose = k; // 左部分的末尾下标值
                }
            }
            dp[N - 1][j] = ans;
            best[N - 1][j] = choose;
            // 正式进入往上推导这一列的数据
            for (int i = N - 2; i >= 1; i--) {
                ans = Integer.MAX_VALUE;
                choose = -1;
                for (int k = best[i][j - 1]; k <= best[i + 1][j]; k++) { // 在上限和下限之间进行枚举
                    // 0~k幅画让j-1个画家完成，k+1 ~ i的画，让第j个画家完成
                    if (k != -1) {
                        int tmp = Math.max(dp[k][j - 1], w(preSum, k + 1, i));
                        if (tmp < ans) {
                            ans = tmp;
                            choose = k; // 左部分的末尾下标值
                        }
                    }
                }
                dp[i][j] = ans;
                best[i][j] = choose;
            }
        }
        return dp[N - 1][num];
    }

    private static int[] sum(int[] arr) {
        int N = arr.length;
        int[] preSum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            preSum[i + 1] = preSum[i] + arr[i];
        }
        return preSum;
    }

    private static int w(int[] preSum, int L, int R) {
        return preSum[R + 1] - preSum[L];
    }

    // 不是四边形不等式的解法。而是另外一种猜法。时间复杂度O(N*logSum)。Sum指的是数组的累加和
    public static int splitArray3(int[] arr, int num) {
        if (arr == null || num <= 0) {
            return 0;
        }

        long sum = 0; // 统计累加和
        for (int number : arr) {
            sum += number;
        }
        /*
            定义一个函数：f(arr, aim)。表示数组最少该怎么划分子区间，能使子区间的累加和<=aim。返回最少需要多少个子区间。
         */
        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = l + ((r - l) >> 1); // 以累加和的中间值作为aim
            int tmp = getNeedParts(arr, mid);
            if (tmp <= num) { // 划分出来的子区间个数 <=num。说明此时以mid作为划分值能够划分出来。继续加压，使mid变小
                ans = mid; // 记录num个画家，最少需要多少时间，就是此时的mid值
                r = mid - 1; // 往左部分划分，使mid变小。条件变严苛
            } else {
                l = mid + 1; // 往右部分划分，使mid变大。条件变宽松
            }
        }
        return (int)ans;
    }

    private static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) { // 某个单独的数据已经超过了aim，就无法以aim为目标划分子区间
                return Integer.MAX_VALUE;
            }
        }
        int parts = 0; // 子区间个数
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (sum + arr[i] > aim) {
                parts++;
                sum = arr[i];
            } else {
                sum += arr[i];
            }
        }
        return parts + (sum <= aim? 1 : 0); // 最后需要算一下sum，不然会漏掉一个
    }
}
