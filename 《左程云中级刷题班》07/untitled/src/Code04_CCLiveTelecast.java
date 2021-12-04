/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-04
 * Time: 9:37
 * Description:
 * CC里面有一个土豪很喜欢一位女直播Kiki唱歌， 平时就经常给她点赞、 送礼、 私聊。 最近CC直播平台在举行
 * 中秋之星主播唱歌比赛， 假设一开始该女主播的初始人气值为start， 能够晋升下一轮人气需要刚好达到end，
 * 土豪给主播增加人气的可以采取的方法有：
 * a. 点赞 花费x C币， 人气 + 2
 * b. 送礼 花费y C币， 人气 * 2
 * c. 私聊 花费z C币， 人气 - 2
 * 其中 end 远大于start且end为偶数， 请写一个程序帮助土豪计算一下， 最少花费多少C币就能帮助该主播
 * Kiki将人气刚好达到end， 从而能够晋级下一轮？
 * 输入描述：
 * 第一行输入5个数据， 分别为： x y z start end， 每项数据以空格分开。
 * 其中： 0＜x, y, z＜＝ 10000， 0＜start, end＜＝ 1000000
 * 输出描述：
 * 需要花费的最少C币。
 * 示例1:
 * 输入
 * 3 100 1 2 6
 * 输出
 * 6
 */
public class Code04_CCLiveTelecast {
    public static void main(String[] args) {
        int add = 6;
        int times = 5;
        int del = 1;
        int start = 10;
        int end = 30;
        System.out.println(minCoins1(add, times, del, start, end));
        System.out.println(minCoins2(add, times, del, start, end));
    }

    public static int minCoins1(int add, int times, int del, int start, int end) {
        if (start > end || (start & 1) != 0 || (end & 1) != 0) {
            return -1;
        }
        return process(0, end, add, times, del, start, end * 2, ((end - start) / 2) * add);
    }

    /**
     * 从目标人气，倒着减，来达到start
     * @param pre 达到当前人气花费的钱数
     * @param aim 目标的人气
     * @param add 点赞
     * @param times 送礼
     * @param del 私聊
     * @param finish 最初人气
     * @param limitAim 人气值的最大值。end的2倍
     * @param limitCoin 普通点赞的钱数
     * @return 最小钱数
     */
    public static int process(int pre, int aim,
                              int add, int times, int del, int finish,
                              int limitAim, int limitCoin) {
        if (pre > limitCoin) { //当前的钱数超过了普通点赞的钱数
            return Integer.MAX_VALUE;
        }
        if (aim < 0) { //目标人气为0了
            return Integer.MAX_VALUE;
        }
//        if (aim > limitAim) { //aim是做减法的，根本无法超过limitAim（end*2）
//            return Integer.MAX_VALUE; //如果是从start往上做加法，这base case有用
//        }
        if (aim == finish) { //目标人气和初始人气相等时，是一种解决方案
            return pre;
        }
        int min = Integer.MAX_VALUE;
        int p1 = process(pre + add, aim - 2, add, times, del, finish, limitAim, limitCoin);
        if (p1 != Integer.MAX_VALUE) {
            min = p1;
        }
        int p2 = process(pre + del, aim + 2, add, times, del, finish, limitAim, limitCoin);
        if (p2 != Integer.MAX_VALUE) {
            min = Math.min(min, p2);
        }
        if ((aim & 1) == 0) {
            int p3 = process(pre + times, aim / 2, add, times, del, finish, limitAim, limitCoin);
            if (p3 != Integer.MAX_VALUE) {
                min = Math.min(min, p3);
            }
        }
        return min;
    }

    public static int minCoins2(int add, int times, int del, int start, int end) {
        if (start > end) {
            return -1;
        }

        int N = ((end - start) / 2) * add;// 行数
        int M = end * 2; //列数
        int[][] dp = new int[N + 1][M + 1];
        for (int pre = 0; pre <= N; pre++) { //行数
            for (int aim = 0; aim <= M; aim++) { //列数
                if (aim == start) { //当aim等于初始值的时候，也就是start这一列
                    dp[pre][aim] = pre;
                } else {
                    dp[pre][aim] = Integer.MAX_VALUE;
                }
            }
        }

        //填写普遍位置，根据尝试推导依赖关系。从下往上，从左往右
        for (int pre = N; pre >= 0; pre--) {
            for (int aim = 0; aim <= M; aim++) {
                if (aim - 2 >= 0 && pre + add <= N) {
                    dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + add][aim -2]);
                }
                if (aim + 2 <= M && pre + del <= N) {
                    dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + del][aim + 2]);
                }
                if ((aim & 1) == 0) {
                    if (aim / 2 >= 0 && pre + times <= N) {
                        dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + times][aim / 2]);
                    }
                }
            }
        }
        return dp[0][end];
    }
}
