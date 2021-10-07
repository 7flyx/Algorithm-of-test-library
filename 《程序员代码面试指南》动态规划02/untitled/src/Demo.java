import java.util.Arrays;
import java.util.Scanner;
/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-10-07
 * Time: 17:36
 * Description: 换钱的最少货币数
 * 输入包括两行，第一行两个整数n（0<=n<=1000）代表数组长度和aim（0<=aim<=5000），第二行n个不重复的正整数，代表arr
 */

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int aim = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(getCoin2(arr, aim));
    }

    //递归
    public static int getCoin1(int[] arr, int aim) {
        if (arr == null || arr.length < 1) {
            return -1;
        }
        return process(arr,0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return -1;
        }
        if (rest == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int k = 0; arr[index] * k <= rest; k++) {
            int tmp = process(arr, index + 1, rest - k * arr[index]);
            if (tmp != -1) {
                min = Math.min(min, k + tmp);
            }
        }
        return min == Integer.MAX_VALUE? -1 : min;
    }

    //动态规划
    public static int getCoin2(int[] arr, int aim) {
        if (arr == null || arr.length < 1) {
            return -1;
        }
        int[][] dp = new int[arr.length + 1][aim + 1];

        for (int i = 1; i <= aim; i++) {
            dp[arr.length][i] = -1; //最后一行全是1
        }
        //从下到上，从左到右填表
        for (int index = arr.length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = -1;

                //依赖于下边的值
                if (dp[index + 1][rest] != -1) {
                    dp[index][rest] = dp[index + 1][rest];
                }

                //依赖于左边的值
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != -1) {
                    if(dp[index][rest] == -1) {
                        dp[index][rest] = dp[index][rest - arr[index]] + 1;
                    } else {
                        dp[index][rest] = Math.min(dp[index][rest - arr[index]] + 1,
                                dp[index][rest]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    //dp空间压缩
    public static int getCoin3(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int[] dp = new int[aim + 1];
        Arrays.fill(dp, -1); //相当于二维表的最后一行数据
        dp[0] = 0;

        //从标准的动态规划入手，从下到上填写表格。外层循环就是行数，也就是次数
        for (int i = arr.length - 1; i >= 0; i--) {
             for (int rest = 0; rest <= aim; rest++) {
                 if (rest - arr[i] >= 0 && dp[rest - arr[i]] != -1) {
                     if (dp[rest] == -1) {
                         dp[rest] = dp[rest - arr[i]] + 1;
                     } else {
                         dp[rest] = Math.min(dp[rest], dp[rest - arr[i]] + 1);
                     }
                 }
             }
        }

        return dp[aim];
    }
}