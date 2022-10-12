package class41;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-11
 * Time: 16:55
 * Description:
 * TSP问题
 * 有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0
 * 所有点到点的距离都存在一个N*N的二维数组matrix里，也就是整张图由邻接矩阵表示
 * 现要求一旅行商从k城市出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城
 * 参数给定一个matrix，给定k。返回总距离最短的路的距离。
 */
public class Code02_TSP {
    public static void main(String[] args) {
        int len = 10; // 城市数量
        int range = 100; // 距离范围
        int testTime = 1000;
        System.out.println("test started");
        for (int i = 0; i < testTime; i++) {
            int[][] matrix = generateMatrix(len, range);
            int ans1 = ways4(matrix); // 没有状态压缩的暴力解
            int ans2 = ways2(matrix); // 有状态压缩的暴力解
            int ans3 = ways3(matrix); // 有状态压缩的暴力解
            if (ans1 != ans2 || ans1 != ans3) {
                for (int j = 0; j < len; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("ans1: " + ans1);
                System.out.println("ans2: " + ans2);
                System.out.println("ans3: " + ans3);
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test finished");
    }

    // for test
    private static int[][] generateMatrix(int len, int range) {
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * range) + 1;
                matrix[j][i] = matrix[i][j];
            }
        }
        return matrix;
    }

    // 暴力解。时间复杂度O(N!)
    public static int ways1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length; // 城市的数量
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++) { // 1表示这个城市还没有走过,null表示这个城市已经走过了
            set.add(i, 1);
        }
        return process(matrix, set, 0); // 从0号城市出发，要走回0号城市
    }

    private static int process(int[][] matrix, List<Integer> set, int start) {
        int cityNumber = 0;
        for (int i = 0; i < set.size(); i++) { // 统计还没有走过的城市
            if (set.get(i) != null) {
                cityNumber++;
            }
        }
        if (cityNumber == 1) { // 0号城市就是全局的终点。从0出发，走到0，形成环形
            return matrix[start][0];
        }
        // 到这里，说明还不止一个城市没有走过。需要枚举
        int ans = Integer.MAX_VALUE;
        set.set(start, null); // 当前start点已经走过，去尝试其他城市点
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null) { // 某个城市还没有走过，就去尝试
                ans = Math.min(ans,
                        process(matrix, set, i) + matrix[start][i]); // 下一个起点就是i位置
            }
        }
        set.set(start, 1);
        return ans;
    }

    // 状态压缩版本，N不能超过32
    public static int ways2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        // 根据ways1分析，可变参数是List和int类型的数据
        // 而List的数据范围在0~N-1范围。并且每一个数据就代表 “使用过 和 没使用过”
        // 所以也是可以使用位信息来表示每一个数字的情况
        int N = matrix.length;
        int cityStatus = (1 << N) - 1; // 位信息：1表示还没走过，0表示已经走过了
        return process2(matrix, cityStatus, 0); // 从0号城市出发，回到0号城市
    }

    // 状态压缩的版本（暴力解）
    private static int process2(int[][] matrix, int cityStatus, int start) {
        if (cityStatus == (cityStatus & (-cityStatus))) { // 只有1位的位信息是1了。则说明只还剩start这一个城市没有走了
            return matrix[start][0];
        }
        // 到这里，说明还不止1个城市没走过，需要枚举
        cityStatus &= (~(1 << start)); // 将start号城市标记为0，表示已经走过了
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((cityStatus & (1 << i)) != 0) { // 如果该位有1，表示还没走过
                ans = Math.min(ans,
                        process2(matrix, cityStatus, i) + matrix[start][i]);
            }
        }
        cityStatus |= (1 << start); // 将start号城市标记为1，恢复现场
        return ans;
    }

    // 状态压缩后，进行记忆化搜索。时间复杂度O(2^N * N)。N不能超过32
    public static int ways3(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        // 根据ways1分析，可变参数是List和int类型的数据
        // 而List的数据范围在0~N-1范围。并且每一个数据就代表 “使用过 和 没使用过”
        // 所以也是可以使用位信息来表示每一个数字的情况。
        // 全部城市状态就是 0000000 ~ 1111111.这样的范围，所以只需建一张表，存储下这些状态即可
        int N = matrix.length;
        int[][] dp = new int[1 << N][N]; // 行表示 cityStatus，列表示 城市出发点
        int cityStatus = (1 << N) - 1;
        return process3(matrix, cityStatus, 0, dp); // 从0号城市开始，回到0号城市的，最短距离
    }

    private static int process3(int[][] matrix, int cityStatus, int start, int[][] dp) {
        if (dp[cityStatus][start] != 0) { // 当前状态已经计算过了，直接拿数据即可
            return dp[cityStatus][start];
        }
        if (cityStatus == (cityStatus & (-cityStatus))) { // 只有一个城市没走过的时候
            dp[cityStatus][start] = matrix[start][0];
            return matrix[start][0];
        }
        cityStatus &= (~(1 << start)); // 将start号城市标记为0
        int ans = Integer.MAX_VALUE;
        // 到这里，说明还不止一个城市没走过，需要进行枚举
        for (int i = 0; i < matrix.length; i++) {
            if ((cityStatus & (1 << i)) != 0) { // 说明i号城市还没有走过，直接进行尝试
                ans = Math.min(ans,
                        process3(matrix, cityStatus, i, dp) + matrix[start][i]);
            }
        }
        cityStatus |= (1 << start); // 将start号城市标记为1
        dp[cityStatus][start] = ans; // 存储结果
        return ans;
    }

    // 严格的动态规划版本
    public static int ways4(int[][] matrix) {
        if (matrix == null || matrix.length <= 1 || matrix[0].length <= 1) { // 只有一个城市的时候
            return 0;
        }
        int N = matrix.length;
        int[][] dp = new int[1 << N][N]; // 行是城市状态。列是 城市出发点
        // 第0行表示，没有任何城市可以走，是无效状态
        // 填写普遍位置。从上往下
        for (int status = 1; status < dp.length; status++) { // 行
            for (int start = 0; start < N; start++) { // 列
                if ((status & (1 << start)) != 0) {
                    if (status == (status & (-status))) { // 切记，不仅仅只是数字1只有1个二进制1。其他的状态有可能也是。
                        dp[status][start] = matrix[start][0];
                    } else {
                        // 枚举
                        int ans = Integer.MAX_VALUE;
                        int lastStatus = status & (~(1 << start)); // 下一层递归的状态
                        for (int i = 0; i < N; i++) {
                            if ((status & (1 << i)) != 0 && i != start) { // 位信息1表示这个城市还没走过。并且不能自己走向自己
                                ans = Math.min(ans,
                                        dp[lastStatus][i] + matrix[start][i]);
                            }
                        }
                        dp[status][start] = ans;
                    }
                }
            }
        }
        return dp[(1 << N) - 1][0]; // 从0号城市出发，回到0号城市的最小代价
    }
}
