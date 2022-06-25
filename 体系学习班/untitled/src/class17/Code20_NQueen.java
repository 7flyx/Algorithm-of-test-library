package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-23
 * Time: 13:25
 * Description: N皇后问题
 */
public class Code20_NQueen {
    public static void main(String[] args) {
        int n = 8;
        System.out.println(nQueen1(n));
        System.out.println(nQueen2(n));
    }

    // 用一维数组解
    public static int nQueen1(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] record = new int[n]; // 记录皇后的位置，下标表示 行， 数值表示列
        return process(0, record, n);
    }

    public static int process(int i, int[] record, int n) {
        if (i == n) { // i来到了n的位置，说明前面的方案可行
            return 1;
        }
        int ans = 0;
        for (int k = 0; k < n; k++) { // 枚举当前行的每一个位置
            if (isValid(record, i, k)) {
                record[i] = k;
                ans += process(i + 1, record, n);
            }
        }
        return ans;
    }

    private static boolean isValid(int[] record, int i, int k) {
        for (int cur = 0; cur < i; cur++) {
            // 同一列的情况，或者
            // 同斜线的情况，斜线判断其实就是变向的判断斜率
            if (k == record[cur] || Math.abs(cur - i) == Math.abs(record[cur] - k)) {
                return false;
            }
        }
        return true;
    }

    // 用位运算解
    public static int nQueen2(int n) {
        if (n <= 0) {
            return 0;
        }
        // 用二进制表示每个位置的皇后，即就是1表示有皇后，0表示没有黄后
        int limit = (n == 32) ? -1 : (1 << n) - 1;
        return process(limit, 0, 0, 0);
    }

    /**
     * @param limit 所有皇后摆放后的位置情况
     * @param leftLim 右上到左下这根斜线
     * @param curLim 当前行，摆放皇后的情况
     * @param rightLim 左上到右下这根斜线
     * @return 返回总方法数
     */
    public static int process(int limit, int leftLim, int curLim, int rightLim) {
        if (curLim == limit) { // 表示皇后填好了，这是一种情况
            return 1;
        }
        // 当前行能填皇后的位置，全部挑出来
        int pow = limit & (~(leftLim | curLim | rightLim)); // 此时pow中，有1的位置，就是能放皇后的位置
        int ans = 0;
        int mostRightOne = 0; // 最右边的1
        while (pow != 0) {
            mostRightOne = pow & (-pow); // 最右边的1
            ans += process(limit,
                    (leftLim | mostRightOne) << 1,
                    (curLim | mostRightOne),
                    (rightLim | mostRightOne) >> 1);
            pow -= mostRightOne;
        }
        return ans;
    }
}
