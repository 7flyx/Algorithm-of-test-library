package demo;


/**
 * 在迷迷糊糊的大草原，小红捡到了n根木棍，第i根木棍的长度是i，
 * 小红现在很开心。想选出其中的三根木棍组成三角形。
 * 但是小明想捉弄小红，想去掉一些木棍，使得小红任意选三根木棍都不能组成三角形。
 * 请问小明最少去掉多少根木棍？
 * 给定N，返回至少去掉多少根？
 */
public class Code04_CrabstickTriangle {
    public static void main(String[] args) {
        System.out.println(calcFibAndCrabstick(5));
    }

    public static int calcFibAndCrabstick(int N) {
        if (N < 3) {
            throw new RuntimeException("输入参数异常");
        }
        if (N == 3) {
            return 0;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        int i = 0;
        for (; ; i++) {
            if (pre <= N && cur > N) {
                break;
            }
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return N - i;
    }
}
