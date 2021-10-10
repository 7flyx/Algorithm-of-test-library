/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-10-10
 * Time: 20:46
 * Description:
 *     //象棋，走马的方法数
 * //在横9纵10的棋盘上，起始点是（0，0）。目标点是自定义在棋盘内的某一点
 * //k是需要具体走的步数
 */
public class Demo {
    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int k = 10;
        System.out.println(getNumbers1(x, y, k));
        System.out.println(getNumbers2(x, y, k));
    }

    //递归版本
    public static int getNumbers1(int x, int y, int step) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        if (step == 0) {
            return (x == 0 && y == 0) ? 1 : 0;
        }

        //递归调用方法，走8个点位
        return getNumbers1(x + 2, y + 1, step - 1) +
                getNumbers1(x - 2, y + 1, step - 1) +
                getNumbers1(x + 1, y + 2, step - 1) +
                getNumbers1(x + 1, y - 2, step - 1) +
                getNumbers1(x + 2, y - 1, step - 1) +
                getNumbers1(x - 2, y - 1, step - 1) +
                getNumbers1(x - 1, y + 2, step - 1) +
                getNumbers1(x - 1, y - 2, step - 1);
    }

    //动态规划版本-建立三维表，从低往上填写
    public static int getNumbers2(int x, int y, int step) {
        int[][][] dp = new int[9][10][step + 1];
        dp[0][0][0] = 1; //只有最底层的左下角的中才是1，其他的都是0

        //可变参数是3个，所以三层循环即可填完这个表
        for (int k = 1; k <= step; k++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    dp[i][j][k] += getValues(dp, i + 2, j + 1, k - 1);
                    dp[i][j][k] += getValues(dp, i - 2, j + 1, k - 1);
                    dp[i][j][k] += getValues(dp, i + 1, j + 2, k - 1);
                    dp[i][j][k] += getValues(dp, i + 1, j - 2, k - 1);
                    dp[i][j][k] += getValues(dp, i + 2, j - 1, k - 1);
                    dp[i][j][k] += getValues(dp, i - 2, j - 1, k - 1);
                    dp[i][j][k] += getValues(dp, i - 1, j + 2, k - 1);
                    dp[i][j][k] += getValues(dp, i - 1, j - 2, k - 1);
                }
            }
        }
        return dp[x][y][step];
    }

    public static int getValues(int[][][] dp, int x, int y, int step) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return dp[x][y][step];
    }


}
