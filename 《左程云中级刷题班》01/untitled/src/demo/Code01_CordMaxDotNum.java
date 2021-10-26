package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 16:01
 * Description:给定一个有序数组arr， 代表数轴上从左到右有n个点arr[0]、 arr[1]...arr[n－ 1]，
 * 给定一个正数L， 代表一根长度为L的绳子， 求绳子最多能覆盖其中的几个点。
 */
public class Code01_CordMaxDotNum {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 10, 12, 15, 16};
        System.out.println(countMaxDot(array, 6));
        System.out.println(countMaxDot2(array, 6));
    }

    //方法一，小贪心策略。以右边界对其，当前查找
    public static int countMaxDot(int[] array, int L) {
        if (array == null || array.length < 1 || L < 1) {
            return 0;
        }
        int max = 0;
        int N = array.length;
        for (int i = 0; i < N; i++) {
            int leftBorder = array[i] - L; //左边界值
            int index = getLeftIndex(array, leftBorder, i);
            max = Math.max(i - index + 1, max);
        }
        return max;
    }

    public static int getLeftIndex(int[] array, int leftBorder, int R) {
        int index = R;
        int L = 0;
        while (L <= R) {//需要取等号，有特殊情况
            int mid = L + ((R - L) >> 1);
            if (array[mid] < leftBorder) {
                L = mid + 1;
            } else {
                index = mid; //记录中间值。本身就是记录大于等于leftBorder的
                R = mid - 1;
            }
        }
        return index;
    }

    //方法二，滑动窗口
    public static int countMaxDot2(int[] array, int L) {
        if (array == null || array.length < 1 || L < 1) {
            return 0;
        }
        int left = 0; //窗口的左边界
        int right = 0; //窗口的右边界
        int max = 1;
        for (int i = 1; i < array.length; i++) {
            right++; //右边界会一直往右扩展，只需卡住左边界即可
            while (array[i] - L > array[left]) {
                left++; //左边界缩小
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
