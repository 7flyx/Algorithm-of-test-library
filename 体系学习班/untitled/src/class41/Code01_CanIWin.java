package class41;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-10
 * Time: 19:23
 * Description: leetcode 464. 我能赢吗
 * 在"100 game"这个游戏中，两名玩家轮流选择从1到10的任意整数，累计整数和
 * 先使得累计整数和达到或超过100的玩家，即为胜者，如果我们将游戏规则改为 “玩家不能重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从1到15的整数（不放回），直到累计整数和 >= 100
 * 给定一个整数maxChoosableInteger（整数池中可选择的最大数）和另一个整数desiredTotal（累计和）
 * 判断先出手的玩家是否能稳赢（假设两位玩家游戏时都表现最佳）
 * 你可以假设maxChoosableInteger不会大于 20，desiredTotal不会大于 300。
 * Leetcode题目：https://leetcode.com/problems/can-i-win/
 */
public class Code01_CanIWin {
    public static void main(String[] args) {
        int choose = 10;
        int total = 1;
        System.out.println(canIWin3(choose, total));
    }

    /**
     * 暴力尝试
     *
     * @param choose 可选择的数的范围在[1, choose]
     * @param total  总和
     * @return 返回先手会不会赢。（谁最先不能拿数字，谁就输了）
     */
    public static boolean canIWin(int choose, int total) {
        if (choose <= 0 || total <= 0) {
            return false; // 先手直接就不能拿数字，直接输
        }
        if ((((1 + choose) * choose) >> 1) < total) { // 等差数列求和公式，choose范围的总和不能凑出total。直接false
            return false;
        }
        boolean[] isSelected = new boolean[choose + 1]; // 下标表示number。true表示已经使用过了
        // 暴力尝试过程，尝试1、2、3……
        return process(isSelected, total); // 返回值，表示先手会不会赢
    }

    private static boolean process(boolean[] isSelected, int total) {
        if (total <= 0) { // total总和已经<=0，表示不能在选择数据。 则先手输
            return false;
        }
        for (int i = 1; i < isSelected.length; i++) { // 枚举choose范围的所有数据
            if (!isSelected[i]) { // i位置的数据没有使用过，去尝试
                isSelected[i] = true;
                boolean next = process(isSelected, total - i); // 后续过程，先后手调换了
                isSelected[i] = false; // 还原现场
                if (!next) { // 后续过程返回false。表示后续过程的“先手”输了，在当前层就表示“后手”输了
                    return true;
                }
            }
        }
        return false;
    }

    // 状态压缩版本。因为题目的choose不会超过20.而int是32位。可以用位信息来表示数据
    // 1表示已经使用过了。0表示还没有使用过。（还是会超时）
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((((1 + choose) * choose) >> 1) < total) { // 等差数列求和公式，choose范围的总和不能凑出total。直接false
            return false;
        }
        return process2(choose, total, 0);
    }

    private static boolean process2(int choose, int total, int isSelected) {
        if (total <= 0) { // 没有数据可以选择，先手输
            return false;
        }
        for (int i = 1; i <= choose; i++) {
            if (((1 << i) & isSelected) == 0) { // 表示i位置的数还没有使用过
                boolean next = process2(choose, total - i, isSelected | (1 << i));
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }


    // 根据第2个解法（状态压缩）版本，可以得出，可变参数有2个（total、isSelected）
    // 而第1个解法用的是数组，第2个解法用的是int类型的参数。
    // 则可以根据掉2个解法，进行dp表格设计
    public static boolean canIWin3(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((((1 + choose) * choose) >> 1) < total) { // 等差数列求和公式，choose范围的总和不能凑出total。直接false
            return false;
        }
        int[] dp = new int[1 << choose];
        // dp[i] == 1 表示 先手赢
        // dp[i] == -1 表示 先手输
        // dp[i] == 0 表示 这个状态还没计算过
        // status表示位信息，1表示已经使用过，0表示没有使用过
        return process3(choose, total, 0, dp);
    }

    // 记忆化搜索
    private static boolean process3(int choose, int total, int status, int[] dp) {
        if (dp[status] != 0) { // 表示已经计算过这个位置的状态
            return dp[status] == 1; // 先手输赢状态
        }
        // 枚举1~choose所有选择

        boolean ans = false;
        if (total > 0) { // 剩余总和还>0的情况
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) { // 表示这个位置的数还没使用过
                    boolean next = process3(choose, total - i, status | (1 << i), dp);
                    if (!next) { // next表示后续过程，=false，表示当前递归的先手赢了。
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans? 1 : -1;
        return ans;
    }
}
