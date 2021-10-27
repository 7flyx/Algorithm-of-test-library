import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-27
 * Time: 19:07
 * Description: 龙与地下城游戏
 * 1）骑士从左上角出发，每次只能向右或向下走，最后到达右下角见到公主。
 * 2）地图中每个位置的值代表骑士要遭遇的事情。如果是负数，说明此处有怪兽，要让骑士损失血量。如果是非负数，代表此处有血瓶，能让骑士回血。
 * 3）骑士从左上角到右下角的过程中，走到任何一个位置时，血量都不能少于1。为了保证骑土能见到公主，初始血量至少是多少?
 * 根据map,输出初始血量。
 */

public class Code09_DragonUndergroundCity {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        System.out.println(minHP2(map));
    }

    public static int minHP1(int[][] map) {
        if (map == null) {
            return -1;
        }

        int n = map.length;
        int m = map[0].length;
        int[][] dp = new int[n--][m--];
        dp[n][m] = map[n][m] > 0? 1 : -map[n][m] + 1;

        //最后一行的数据
        for (int i = m - 1; i >= 0; i--) {
            dp[n][i] = Math.max(dp[n][i + 1] - map[n][i], 1);
        }

        int right = 0;
        int down = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i][m] = Math.max(dp[i + 1][m] - map[i][m], 1);
            for (int j = m - 1; j >= 0; j--) {
                right = Math.max(dp[i][j + 1] - map[i][j], 1);
                down = Math.max(dp[i + 1][j] - map[i][j], 1);
                dp[i][j] = Math.min(right, down);
            }
        }
        return dp[0][0];
    }

    /**
     * dp空间压缩
     * @param map 图
     * @return 返回最低血量
     */
    public static int minHP2(int[][] map) {
        if (map == null) {
            return -1;
        }

        int longs = Math.max(map.length, map[0].length);
        int shorts = Math.min(map.length, map[0].length);
        boolean isRow = longs == map.length; //是不是以最后一行作为数组长度
        int[] dp = new int[shorts];
        int tmp = map[map.length - 1][map[0].length - 1];
        dp[shorts - 1] = tmp > 0? 1 : -tmp + 1;

        int row = 0;
        int col = 0;
        for (int i = shorts - 2; i >= 0; i--) {
            row = isRow? longs - 1 : i;
            col = isRow? i : longs - 1;
            dp[i] = Math.max(dp[i + 1] - map[row][col], 1);
        }

        //填写普通位置
        for (int i = longs - 2; i >= 0; i--) {
            row = isRow? i : shorts - 1;
            col = isRow? shorts - 1 : i; //这两个，卡的就是最右的值
            dp[shorts - 1] = Math.max(dp[shorts - 1] - map[row][col], 1);
            for (int j = shorts - 2; j >= 0; j--) {
                row = isRow? i : j;
                col = isRow? j : i; //中间的普通位置
                int choosen1 = Math.max(dp[j] - map[row][col], 1);
                int choosen2 = Math.max(dp[j + 1] - map[row][col], 1);
                dp[j] = Math.min(choosen1, choosen2);
            }
        }
        return dp[0];
    }
}
