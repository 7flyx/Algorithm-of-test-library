package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-20
 * Time: 16:08
 * Description: 分裂数字。
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 */
public class Code17_SplitNumber {
    public static void main(String[] args) {
        System.out.println(splitNumber(4));
        System.out.println(splitNumber2(4));
        System.out.println(splitNumber3(4));
        System.out.println(splitNumber4(4));
    }

    // 暴力递归版本
    public static int splitNumber(int number) {
        if (number == 1) {
            return 1;
        }
        return process(1, number);
    }

    /**
     * @param rest 被分裂的数字
     * @param pre  必须从这个数字开始分裂
     * @return 返回总的分裂数字的方法数
     */
    public static int process(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        int ans = 0;
        for (int i = pre; i <= rest; i++) {
            ans += process(i, rest - i);
        }
        return ans;
    }

    // 经典dp版本
    public static int splitNumber2(int number) {
        if (number == 1) {
            return 1;
        }
        // 根据递归的代码可知，可变参数有两个：rest和pre
        // pre的范围是1~number
        // pre 作为行，rest作为列
        int[][] dp = new int[number + 1][number + 1];
        // base case，rest == 0的时候
        for (int pre = 1; pre <= number; pre++) {
            dp[pre][0] = 1; // rest == 0
            dp[pre][pre] = 1; // rest == pre的时候，原本也是依赖于第一例的值，这里就提前填了
        }

        // 普遍位置
        for (int pre = number - 1; pre >= 1; pre--) { // 分裂数的最小值
            for (int rest = pre + 1; rest <= number; rest++) { //
                int ans = 0;
                for (int i = pre; i <= rest; i++) { // 枚举行为
                    ans += dp[i][rest - i];
                }
                dp[pre][rest] = ans;
            }
        }
        return dp[1][number];
    }

    // 经典dp版本---斜率优化
    public static int splitNumber3(int number) {
        if (number == 1) {
            return 1;
        }
        // 根据递归的代码可知，可变参数有两个：rest和pre
        // pre的范围是1~number
        // pre 作为行，rest作为列
        int[][] dp = new int[number + 1][number + 1];
        // base case，rest == 0的时候
        for (int pre = 1; pre <= number; pre++) {
            dp[pre][0] = 1; // rest == 0
            dp[pre][pre] = 1; // rest == pre的时候，原本也是依赖于第一例的值，这里就提前填了
        }

        // 填写普遍位置
        for (int pre = number - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= number; rest++) {
                // 分析经典dp版本，dp[pre][rest]依赖位置有
                // dp[pre][rest - pre]
                // dp[pre + 1][rest - (pre+1)]
                // dp[pre + 2][rest - (pre+2)]
                // ……  也就是右上到左下的一根斜线
                // 而dp[pre+1][rest]，也是有相应的斜线
                dp[pre][rest] = dp[pre + 1][rest]; // 直接拿取下方的值
                dp[pre][rest] += dp[pre][rest - pre]; // 多出来的一个位置，累加上
            }
        }
        return dp[1][number];
    }

    // dp空间压缩+斜率优化
    public static int splitNumber4(int number) {
        if (number == 1) {
            return 1;
        }
        // 根据递归的代码可知，可变参数有两个：rest和pre
        // pre的范围是1~number
        // pre 作为行，rest作为列
        int[] dp = new int[number + 1];
        // base case，rest == 0的时候
        dp[0] = 1; // rest == 0时
        dp[number] = 1; // rest == pre时，右下角
        for (int pre = number - 1; pre >= 1; pre--) { // 行
//            dp[pre][pre] = 1;
            dp[pre] = 1; // 对角线处
            for (int rest = pre + 1; rest <= number; rest++) { // 列
                // dp[pre][rest] = dp[pre + 1][rest]; // 直接拿取下面的值。
                dp[rest] += dp[rest - pre];
            }
        }
        return dp[number];
    }
}
