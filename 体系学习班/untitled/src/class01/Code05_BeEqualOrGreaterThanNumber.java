package class01;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 22:03
 * Description: 在一个升序数组中，找到大于等于num的最左位置。例如：
 *     1 2 3 3 4 4 4 5 5 5 5 5 7 8，找到>=5，最左边的位置
 *     上述例子就返回下标 7 即可
 */
public class Code05_BeEqualOrGreaterThanNumber {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5, 7, 8};
        System.out.println(beEqualOrGreaterThanNumber(arr, 5));
    }

    public static int beEqualOrGreaterThanNumber(int[] arr, int k) {
        if (arr == null) {
            return -1;
        }

        int left = 0;
        int right = arr.length;
        int res = -1;
        int mid = 0;
        while (left < right) { // 还有两个数以上的情况，循环继续
            mid = left + ((right - left) >> 1);
            if (arr[mid] >= k) { // 截掉右部分
                right = mid - 1;
                res = mid; // 更新下标值
            } else { // arr[mid] < k。截掉左部分
                left = mid + 1;
            }
        }
        return res;
    }
}
