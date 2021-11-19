import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-19
 * Time: 16:09
 * Description:
 */

public class Code11_MostLeftOne {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //int n = sc.nextInt();
        //System.out.println(simple(n));
        for (int i = 1; i < 10; i++) {
            System.out.println(calcAllCast(i));
        }
    }

    public static int calcAllCast(int n) {
        if (n < 0) {
            return 0;
        }
        //return process(n, 0, 1);
        return process(n);
    }

    //递归版本
    public static int process(int n, int index, int cur) {
        if (index == n - 1) {
            return 1;
        }
        if (index > n - 1) {
            return 0;
        }
        int res = 0;
        if (cur == 1) {
            res += process(n, index + 1, 1);
            res += process(n, index + 1, 0);
        } else {
            res += process(n, index + 1, 1);
        }
        return res;
    }

    //动态规划
    public static int process(int n) {
        int[] dp1 = new int[n + 1]; //代表当前位置是1的情况
        int[] dp0 = new int[n + 1]; //代表当前位置是0 的情况
        dp1[n] = 1; //最后一个位置只能放入1，才是当前的最大值
        dp0[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp1[i] = (dp1[i + 1] + dp0[i + 1]) % 536870912;
            dp0[i] = dp1[i + 1] % 536870912;
        }
        return dp1[0];
    }

    //打表法，根据暴力递归的版本。找出规律
    //会发现，最终的结果跟斐波那契数差不多
    public static int simple(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int pre = 1;
        int cur = 2;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }
}
