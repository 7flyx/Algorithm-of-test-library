import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-10-08
 * Time: 14:07
 * Description: 生成最长递增子序列
 */

public class Code03_GetMostLengthStr {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int[] dp = getdp(arr);
        //根据dp表里的数据，进行逆推
        int max = 0; //长度
        int index = 0;
        int pre = Integer.MAX_VALUE; //记录的是dp值所对应的数据元素
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > max) {
                max = dp[i];
                index = i;
                pre = arr[i];
            } else if (dp[i] == max) {
                //有可能dp值相同，这两相同的dp所对应的数据进行比较，字典序
                if (arr[i] < pre) {
                    pre = arr[i];
                    max = dp[i];
                    index = i;
                }
            }
        }

        int[] res = new int[max];
        res[--max] = arr[index];
        for (int i = index - 1; i >= 0; i--) {
            if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
                res[--max] = arr[i];
                index = i;
            }
        }

        StringBuilder sb = new StringBuilder();

        //打印输出res数组
        for (int i = 0; i < res.length; i++) {
            sb.append(res[i]);
            if (i + 1 != res.length) {
                sb.append(" ");
            }
        }
        System.out.println(sb.toString());
    }

    public static int[] getdp(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }

        int[] dp = new int[arr.length];
        int[] ends = new int[arr.length];
        dp[0] = 1;
        ends[0] = arr[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;

        //遍历数组，填写dp表
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (r + l) / 2;
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }

            right = Math.max(right, l); //更新right的值，l有可能大于right
            dp[i] = l + 1;
            ends[l] = arr[i];
        }
        return dp;
    }
}
