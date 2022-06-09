package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-09
 * Time: 11:07
 * Description: 经典背包问题
 */
public class Code03_BagProblem {
    public static void main(String[] args) {
        int[] weight = {1, 2, 3, 4};
        int[] value = {2, 4, 4, 5};
        System.out.println(getMaxValue1(weight, value, 5));
        System.out.println(getMaxValue2(weight, value, 5));
        System.out.println(getMaxValue3(weight, value, 5));
        System.out.println(getMaxValue4(weight, value, 5));
    }

    /**
     * @param weight 物品的重量
     * @param value  物品的价值
     * @param bag    背包容量
     * @return 返回在背包能装下的情况下的最大价值
     */
    public static int getMaxValue1(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || bag <= 0) {
            return 0;
        }
        return process1(weight, value, bag, 0);
    }

    private static int process1(int[] weight, int[] value, int bag, int index) {
        if (index == weight.length) {
            return 0;
        }
        int p1 = process1(weight, value, bag, index + 1); // 不要当前位置的物品
        int p2 = -1;
        if (bag - weight[index] >= 0) {
            p2 = process1(weight, value, bag - weight[index], index + 1) + value[index];
        }
        return Math.max(p1, p2);
    }

    public static int getMaxValue2(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || bag <= 0) {
            return 0;
        }
        int N = weight.length;
        int[][] dp = new int[bag + 1][N + 1]; // 背包容量作为行，物品个数作为列
        // base case 是 最右一列都是0，因为java中的数组默认值就是0
        return process2(weight, value, bag, 0, dp);
    }

    private static int process2(int[] weight, int[] value, int bag, int index, int[][] dp) {
        if (dp[bag][index] != 0) {
            return dp[bag][index];
        }
        if (index == weight.length) {
            return 0;
        }
        int p1 = process2(weight, value, bag, index + 1, dp); // 不要当前位置的物品
        int p2 = -1;
        if (bag - weight[index] >= 0) {
            p2 = process2(weight, value, bag - weight[index], index + 1, dp) + value[index];
        }
        dp[bag][index] = Math.max(p1, p2);
        return dp[bag][index];
    }

    // 经典dp版本
    public static int getMaxValue3(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || bag <= 0 || value.length != weight.length) {
            return 0;
        }
        int N = weight.length;
        // 根据递归版本可知，整个递归函数可变参数只有两个： bag 背包容量 和 index 下标
        int[][] dp = new int[N + 1][bag + 1];
        // base case，index = 数组末尾时，只需返回0即可。而java中的数组默认值就是0
        for (int index = N - 1; index >= 0; index--) { // 从倒数第2行开始填
            for (int rest = 0; rest <= bag; rest++) { // 背包容量
                dp[index][rest] = dp[index + 1][rest]; // 当前位置的物品不要的情况
                if (rest - weight[index] >= 0 ) { // 要了当前物品，并且背包能装下的情况
                    dp[index][rest] = Math.max(dp[index][rest],
                            dp[index + 1][rest - weight[index]] + value[index]);
                }
            }
        }
        return dp[0][bag];
    }

    // 空间压缩版本
    public static int getMaxValue4(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || value.length != weight.length || bag <= 0) {
            return 0;
        }
        int N = weight.length;
        // 根据经典dp版本的代码，可知，每个行的数据，它只依赖于下一行的数据，所以可只创建一个一维数组即可
        int[] dp = new int[bag + 1];
        // base case，index = 数组末尾时，返回值是0，而java中的数组默认值就是0
        for (int index = N - 1; index >= 0; index--) {
            // 因为当前更新数据，需要拿取到当前位置左手边的数据，
            // 所以不能从左往右更新,得从右往左更新
            for (int rest = bag; rest >= 0; rest--) {
                // 1、默认不动，就是直接拿取的下一行的数据。也就是不要当前物品的情况
                // 2、要当前位置物品的情况
                if (rest - weight[index] >= 0) {
                    dp[rest] = Math.max(dp[rest],
                            dp[rest - weight[index]] + value[index]);
                }
            }
        }
        return dp[bag]; // 返回最后位置即可，也就是对应到二维表的右上角位置
    }
}
