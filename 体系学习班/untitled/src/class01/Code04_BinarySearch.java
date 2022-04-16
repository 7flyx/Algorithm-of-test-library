package class01;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 22:01
 * Description: 二分查找，一个数组中有n这个数吗？
 */
public class Code04_BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5, 7, 8};
        System.out.println(getIndexOfNumber(arr, 7));
    }

    public static int getIndexOfNumber(int[] arr, int num) {
        if (arr == null){
            return -1;
        }

        int left = 0;
        int right = arr.length;
        int mid = 0;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (arr[mid] > num) {
                right = mid - 1;
            } else if (arr[mid] < num) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left; // 退出循环，left == right
    }
}
