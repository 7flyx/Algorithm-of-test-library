package class38;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-06
 * Time: 20:08
 * Description:
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量
 * 1）能装下6个苹果的袋子
 * 2）能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，
 * 且使用的每个袋子必须装满，给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1。
 */
public class Code01_BuyApples {
    public static void main(String[] args) {
        int N = 100;

        for (int i = 1; i <= 100; i++) {
            int ans =  getBagNum2(i);
            System.out.printf("%2d: %2d\n", i, ans);
        }
    }

    // N个苹果
    public static int getBagNum1(int N) {
        if (N < 6 || N % 2 != 0) {
            return -1;
        }

        int bag6 = 0; // 6号袋子的数量
        int bag8 = N / 8;  // 8号袋子的数量
        int rest = N - 8 * bag8; // 剩余的苹果数
        while (bag8 >= 0) {
            if (rest % 6 == 0) { // 剩下的苹果刚好能用6号袋子装下
                return bag8 + rest / 6;
            } else { // 剩下的苹果数不能用6号袋子装下
                rest += 8;
                bag8--;
            }
        }
        return -1;
    }

    // 打表的优化版本
    public static int getBagNum2(int N) {
        if (N % 2 != 0) { // 奇数的情况，直接返回-1
            return -1;
        }
        if (N < 18) {
            return N == 8? 1 : (N == 12 || N == 14 || N == 16? 2 : -1);
        }
        return (N - 18) / 8 + 3; // 打表出来的结果
    }

}
