package class02;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:04
 * Description: 给定一个数组，其中有一种数出现了奇数次，其他的都出现了偶数次
 * 请返回出现奇数次的数。
 */
public class Code02_OneEvenAndOddNumber {
    public static void main(String[] args) {
        int[] arr = {2, 1, 1, 2, 3, 4, 5, 5, 4, 3, 3};
        System.out.println(getOddNumber(arr));
    }

    public static int getOddNumber(int[] arr) {
        if (arr == null) {
            return -1;
        }

        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }
        return eor;
    }
}
