package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-14
 * Time: 10:49
 * Description:
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 */
public class Code08_HorseJump {
    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int k = 8;
        System.out.println(jump1(x, y, k));
        System.out.println(jump2(x, y, k));
        System.out.println(jump3(x, y, k));
    }

    // 递归版本
    public static int jump1(int x, int y, int k) {
        if (k < 1 || x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }

        return process1(x, y, k, 0, 0);
    }

    public static int process1(int x, int y, int rest, int curX, int curY) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            return 0;
        }
        if (rest == 0) {
            return (x == curX && y == curY) ? 1 : 0;
        }
        int ways = process1(x, y, rest - 1, curX + 2, curY + 1);
        ways += process1(x, y, rest - 1, curX + 2, curY - 1); // 上方向
        ways += process1(x, y, rest - 1, curX + 1, curY + 2);
        ways += process1(x, y, rest - 1, curX - 1, curY + 2);
        ways += process1(x, y, rest - 1, curX - 2, curY + 1);
        ways += process1(x, y, rest - 1, curX - 2, curY - 1);
        ways += process1(x, y, rest - 1, curX + 1, curY - 2);
        ways += process1(x, y, rest - 1, curX - 1, curY - 2);
        return ways;
    }

    // 经典dp版本
    public static int jump2(int x, int y, int k) {
        if (k < 1 || x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }

        // 根据递归版本的代码，可以得到可变参数有3个：curX，curY， rest
        int[][][] dp = new int[9][10][k + 1]; // rest的范围是0 ~ k
        dp[x][y][0] = 1; // base case
        for (int rest = 1; rest <= k; rest++) {
            for (int curX = 0; curX < 9; curX++) {
                for (int curY = 0; curY < 10; curY++) {
                    int ways = getWays(dp, rest - 1, curX + 2, curY + 1);
                    ways += getWays(dp, rest - 1, curX + 2, curY - 1); // 上方向
                    ways += getWays(dp, rest - 1, curX + 1, curY + 2);
                    ways += getWays(dp, rest - 1, curX - 1, curY + 2);
                    ways += getWays(dp, rest - 1, curX - 2, curY + 1);
                    ways += getWays(dp, rest - 1, curX - 2, curY - 1);
                    ways += getWays(dp, rest - 1, curX + 1, curY - 2);
                    ways += getWays(dp, rest - 1, curX - 1, curY - 2);
                    dp[curX][curY][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    // 获取dp表中的数据，额外功能就是处理一下越界
    private static int getWays(int[][][] dp, int rest, int x, int y) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return dp[x][y][rest];
    }

    // dp空间压缩
    public static int jump3(int x, int y, int k) {
        if (k < 1 || x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        int[][] dp = new int[9][10];
        int[][] help = new int[9][10]; // 临时表，用于临时存储计算出来的总和
        dp[x][y] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int curX = 0; curX < 9; curX++) {
                for (int curY = 0; curY < 10; curY++) {
                    int ways = getWays(dp, curX + 2, curY + 1);
                    ways += getWays(dp, curX + 2, curY - 1); // 上方向
                    ways += getWays(dp, curX + 1, curY + 2);
                    ways += getWays(dp, curX - 1, curY + 2);
                    ways += getWays(dp, curX - 2, curY + 1);
                    ways += getWays(dp, curX - 2, curY - 1);
                    ways += getWays(dp, curX + 1, curY - 2);
                    ways += getWays(dp, curX - 1, curY - 2);
                    help[curX][curY] = ways;
                }
            }
            int[][] tmp = help;
            help = dp;
            dp = tmp;
        }
        return dp[0][0];
    }

    private static int getWays(int[][] dp, int x, int y) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return dp[x][y];
    }
}
