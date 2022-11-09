package class47;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-09
 * Time: 9:41
 * Description:
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1. 0位置的要求：arr[0]<=arr[1]
 * 2. n-1位置的要求：arr[n-1]<=arr[n-2]
 * 3. 中间i位置的要求：arr[i]<=max(arr[i-1],arr[i+1])
 * 但是在arr有些数字丢失了，比如k位置的数字之前是正数，丢失之后k位置的数字为0
 * 请你根据上述条件，计算可能有多少种不同的arr可以满足以上条件
 * 比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1，即[6,9,9]达标
 */
public class Code06_RestoreWays {
    public static void main(String[] args) {
        int[] arr = {6, 0, 9};
        System.out.println(restoreWays(arr));
        System.out.println(restoreWays1(arr));
    }

    // 暴力递归版本
    public static int restoreWays(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        if (arr[N - 1] == 0) {
            int ways = 0;
            for (int i = 1; i <= 200; i++) {
                ways += process(arr, N - 1, i, 2); // 只能让index-1位置去限制index位置的大小
            }
        }
        return process(arr, N - 1, arr[N - 1], 2);
    }

    /**
     * status：共有三种取值：0,1,2
     * 0: 表示 arr[index] < arr[index+1] 左边的小
     * 1: 表示 arr[index] == arr[index+1] 左右两边相等
     * 2: 表示 arr[index] > arr[index+1] 左边的大
     *
     * @param arr    原数组
     * @param index  在index下标处插入v值
     * @param v      待插入的新的值
     * @param status 当前index的值 与 index+1的值 的大小关系
     * @return 使其数组符合规则，返回共有多少种数组
     */
    public static int process(int[] arr, int index, int v, int status) {
        if (index == 0) {
            // 第一个数只能 <= 第二个数，能满足条件就是1种方法数
            // 并且0位置的数是0，或者是等于v的情况
            return (status == 0 || status == 1) && (arr[0] == v || arr[0] == 0) ? 1 : 0;
        }
        if (arr[index] != 0 && arr[index] != v) { // 当前位置不能填数字，并且预先想填写的v值和原先的值不一样，就不能填写，直接返回0
            return 0;
        }
        int ways = 0;
        // 已经决定index位置填写v值，现在去根据index和index+1的大小关系，去尝试index-1位置的数值大小
        if (status == 1 || status == 0) { // index位置的数 <= index+1位置的数
            for (int pre = 1; pre <= 200; pre++) {
                ways += process(arr, index - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else {
            for (int pre = v; pre <= 200; pre++) {
                ways += process(arr, index - 1, pre, pre == v ? 1 : 2);
            }
        }
        return ways;
    }

    // 经典dp版本
    public static int restoreWays1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        for (int v = 1; v <= 200; v++) { // base case
            for (int status = 0; status < 3; status++) {
                dp[0][v][status] = (status == 0 || status == 1) && (arr[0] == 0 || arr[0] == v) ? 1 : 0;
            }
        }

        // 填写普遍位置
        for (int index = 1; index < N; index++) {
            for (int v = 1; v <= 200; v++) {
                for (int status = 0; status < 3; status++) {
                    // status <= 1时，也就是左边的数 <= 右边的数时，是需要枚举1~200之间的全部数值
                    // status == 2时，只需要枚举 v~200之间的数值
                    int ways = 0;
                    if (status == 0 || status == 1) {
                        for (int pre = 1; pre < v; pre++) {
                            ways += dp[index - 1][pre][0];
                        }
                    }
                    ways += dp[index - 1][v][1];
                    for (int pre = v +1; pre <= 200; pre++) {
                        ways += dp[index - 1][pre][ 2];
                    }
                    dp[index][v][status] = ways;
                }
            }
        }
        if (arr[N - 1] == 0) {
            int ways = 0;
            for (int i = 1; i <= 200; i++) {
                ways += dp[N - 1][i][2];
            }
            return ways;
        }
        return dp[N - 1][arr[N - 1]][2];
    }
}
