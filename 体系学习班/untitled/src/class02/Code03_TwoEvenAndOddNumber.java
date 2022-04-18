package class02;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:07
 * Description: 给定一个数组，其中有 两种数出现了奇数次，其余的都出现了偶数次
 * 请返回出现奇数次的两个数。
 */
public class Code03_TwoEvenAndOddNumber {
    public static void main(String[] args) {
        int[] arr = {2, 2, 2, 3, 4, 5, 5, 4, 6, 6, 7, 8, 7, 8};
        System.out.println(Arrays.toString(getTwoOddNumber(arr)));
    }

    public static int[] getTwoOddNumber(int[] arr) {
        if (arr == null) {
            return new int[2];
        }

        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }

        // eor就是 出现奇数次的两个数 a和b的异或和
        int mostRightOne = eor & (-eor); // 拿到最右侧的1
        int res = 0;
        for (int num : arr) {
            // 挑选出num在mostRightOne这个位置有1 的情况
            if ((num & mostRightOne) != 0) { //说明num这个位置有1
                res ^= num;
            }
        }
        int[] tmp = new int[2];
        tmp[0] = res;
        tmp[1] = res ^ eor;
        return tmp;
    }
}
