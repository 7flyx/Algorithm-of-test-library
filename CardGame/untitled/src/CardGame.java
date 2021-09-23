import java.io.*;

/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-09-23
 * Time: 20:51
 * Description: 纸牌博弈问题。给定一个数组，每个数代表一张纸牌的分数。两个选手，一个先拿纸牌，一个后拿
 * 每次只能拿走数组最左或最右的纸牌。假设两个玩家都很聪明。问那位玩家拿取的纸牌分数最多？分数是多少？
 */

public class CardGame {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        String[] nums = in.readLine().split(" ");
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(nums[i]);
        }
        System.out.println(calcChampionSum2(array));

        in.close();
    }

    /**
     * 暴力递归解法
     * @param array 纸牌数组
     * @return 返回最大分数和
     */
    public static int calcChampionSum1(int[] array) {
        if (array == null) {
            return 0;
        }
        return Math.max(first(array, 0, array.length - 1), second(array, 0, array.length - 1));
    }

    public static int first(int[] array, int L,  int R){
        if (L == R) {
            return array[L];
        }
        return Math.max(second(array, L + 1, R) + array[L],
                second(array, L, R - 1) + array[R]);
    }

    public static int second(int[] array, int L, int R) {
        if (L == R) {
            return 0;
        }
        return Math.min(first(array, L + 1, R), first(array, L, R - 1));
    }

    /**
     递归时，用到了两个方法，进行互相调用，那么这里的动态规划，也是需要两个不同表
     进行互相的操作即可。可变参数就两个。即建立一个二维数组
     */
    public static int calcChampionSum2(int[] array) {
        if (array == null || array.length < 1) {
            return 0;
        }

        int[][] f = new int[array.length][array.length];
        int[][] s = new int[array.length][array.length];

        //先手数组，在L等于R时，填入相应的值即可。后手数组是填0
        for (int i = 0; i < array.length; i++) {
            f[i][i] = array[i];
        }

        //外层循环，控制还有几条斜线没有填写
        for (int i = 1; i < array.length; i++) {
            int L = 0; //行
            int R = i; //列
            while (L < array.length && R < array.length) { //行和列都不越界的情况下
                f[L][R] = Math.max(s[L + 1][R] + array[L], s[L][R - 1] + array[R] );
                s[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(f[0][array.length - 1], s[0][array.length - 1]);
    }
}
