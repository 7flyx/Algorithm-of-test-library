package backtrackingarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:31
 * Description: N皇后
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/n-queens
 *
 * 第二问链接：https://leetcode-cn.com/problems/n-queens-ii/
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code11_NQueen {
    class Solution {
        public int totalNQueens(int n) {
            if (n == 1) {
                return 1;
            }
            if (n < 4) {
                return 0;
            }

            return process(n);
        }

        private int process(int n) {
            int limit = n == 32? -1 : (1 << n) - 1; //先确定一行，作为限制
            return process(limit, 0, 0, 0);
        }

        private int process(int limit, int curLim, int leftLim, int rightLim) {
            if (limit == curLim) {
                return 1;
            }
            int next = ~(curLim | leftLim | rightLim) & limit; //next里面的1，就是可以摆放的位置
            int mostRight = 0;
            int res = 0;
            while (next != 0) {
                mostRight = (~next + 1) & next;
                next -= mostRight;
                res += process(limit,
                        (curLim | mostRight),
                        (leftLim | mostRight) << 1,
                        (rightLim | mostRight) >>> 1);
            }
            return res;
        }
    }
}
