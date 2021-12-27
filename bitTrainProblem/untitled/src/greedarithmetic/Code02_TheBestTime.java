package greedarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:29
 * Description:买卖股票的最佳时机
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 */
public class Code02_TheBestTime {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        boolean isUp = false; //判断上一段数据是不是升序的
        int res = 0;// 结果
        int min = prices[0];
        int pre = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (pre < prices[i]) {
                pre = prices[i];
                isUp = true;
                continue;
            } else if (isUp) { //当前位置的数据小于pre，且上一段数据是升序的，结算
                res += pre - min;
            }
            min = prices[i];
            pre = min;
        }
        if (isUp) { //给的数组全部都是升序，则最后一个元素时，需要结算
            res += pre - min;
        }
        return res;
    }
}
