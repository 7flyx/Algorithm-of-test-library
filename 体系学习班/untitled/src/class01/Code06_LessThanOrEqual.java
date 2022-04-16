package class01;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 22:05
 * Description: 在一个升序数组中，找到小于等于num的最右位置。例如：
 * 1 2 3 3 4 4 4 5 5 5 5 5 7 8，找到<=5，最左边的位置
 * 上述例子就返回下标 11 即可
 */
public class Code06_LessThanOrEqual {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5, 7, 8};
        System.out.println(lessThanOrEqualNumber(arr, 5));
    }

    public static int lessThanOrEqualNumber(int[] arr, int k) {
        if (arr == null) {
            return -1;
        }

        int left = 0;
        int right = arr.length;
        int mid = 0;
        int res = -1;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (arr[mid] <= k) {
                res = mid; // 更新下标
                left = mid + 1; // 截掉左部分
            } else { // arr[mid] > k
                right = mid - 1; // 戒掉 右部分
            }
        }
        return res;
    }
}
