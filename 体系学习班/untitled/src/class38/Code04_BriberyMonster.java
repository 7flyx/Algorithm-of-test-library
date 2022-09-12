package class38;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-09
 * Time: 20:05
 * Description:
 * int[] d，d[i]：i号怪兽的能力
 * int[] p，p[i]：i号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你
 * 他的能力直接累加到你的能力上；如果你当前的能力，大于等于i号怪兽的能力
 * 你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你
 * 他的能力直接累加到你的能力上
 * 返回通过所有的怪兽，需要花的最小钱数
 * （课上会给出不同的数据量描述）
 *
 * 总结：这道题以money或者ability两个变量作为列。根据题目所给的数据量，推测应该使用哪个变量作为列。
 */
public class Code04_BriberyMonster {
    public static void main(String[] args) {
        int[] d = {10, 20, 5};
        int[] p = {5, 15, 5};
        System.out.println(getMinMoney(d, p));
    }

    // d怪兽的能力值。p怪兽的价格
    public static int getMinMoney(int[] d, int[] price) {
        if (d == null || price == null || d.length != price.length) {
            return -1;
        }
//        return process1(d, price); // 以index和ability作为行列
        return process2(d, price); // 以index和money作为行列
    }

    private static int process1(int[] d, int[] p) {
        // 尝试：process(d, p, index, ability) 第4个参数表示当前递归的能力值
        int row = d.length; // 行
        int col = 0; // 列。用钱购买全部的怪兽，就能获得最大能力值
        for (int i = 0; i < p.length; i++) {
            col += d[i];
        }

        //dp[i][j] 表示到达第i号怪兽，能力为j时，花的最少钱数
        int[][] dp = new int[row + 1][col + 1];
        // dp[row][0~col] = 0 // base case

        // 填写dp表
        for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j <= col; j++) {
                // 分为两种情况
                // 1、不管能力值够不够，都直接购买怪兽
                if (j + d[i] <= col) {
                    dp[i][j] = dp[i + 1][j + d[i]] + p[i]; // 花p[i]的钱，提升能力，进入到下一个怪兽
                }
                // 2、如果能力值 >=d[i]，可以尝试直接嘎了怪兽
                if (j >= d[i]) { // 拿取下一行，j能力值的最少钱数。进行比较，取最少钱数即可
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j]);
                }
            }
        }
        return dp[0][0]; // 递归调用process(d,p,0,0)
    }

    private static int process2(int[] d, int[] p) {
        // 尝试：process(d, p, index, money)
        int row = d.length;
        int col = 0;
        for (int money : p) { // 以钱数作为列
            col += money;
        }
        // dp[i][j]表示 到达第i号怪兽，必须花费j元。能够达到的最大能力值
        // 切记是必须花费j元，不能多 也不能少
        int[][] dp = new int[row][col + 1];
        for (int i = 0; i < row; i++) { // 将全部的结果先填-1
            Arrays.fill(dp[i], -1);
        }
        dp[0][p[0]] = d[0]; // 花费p[0]的钱，购买怪兽，获得的最大能力值。第0行其余位置都是-1
        for (int i = 1; i < row; i++) {
            for (int j = 0; j <= col; j++) {
                // 分为两种情况
                // 1、不管当前能力值够不够，都直接买
                if (j - p[i] >= 0 && dp[i - 1][j - p[i]] != -1) {
                    dp[i][j] = dp[i - 1][j - p[i]] + d[i - 1];
                }
                // 2、如果能力值够，可以选择不买
                if (j >= d[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }
        // 从左往右遍历最后一行的数据，最先不是-1的，j就是答案
        for (int j = 0; j <= col; j++) {
            if (dp[row - 1][j] != -1) {
                return j;
            }
        }
        return -1;
    }

    // 尝试过程
    // 从0....index号怪兽，花的钱，必须严格==money
    // 如果通过不了，返回-1
    // 如果可以通过，返回能通过情况下的最大能力值
    public static long process2(int[] d, int[] p, int index, int money) {
        if (index == -1) { // 一个怪兽也没遇到呢
            return money == 0 ? 0 : -1;
        }
        // index >= 0
        // 1) 不贿赂当前index号怪兽
        long preMaxAbility = process2(d, p, index - 1, money);
        long p1 = -1;
        if (preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }
        // 2) 贿赂当前的怪兽 当前的钱 p[index]
        long preMaxAbility2 = process2(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility2 != -1) {
            p2 = d[index] + preMaxAbility2;
        }
        return Math.max(p1, p2);
    }
}
