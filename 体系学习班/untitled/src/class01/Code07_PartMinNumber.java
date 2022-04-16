package class01;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-16
 * Time: 22:45
 * Description:
 * 局部最小值问题
 * 定义何为局部最小值：
 * arr[0] < arr[1]，0位置是局部最小；
 * arr[N-1] < arr[N-2]，N-1位置是局部最小；
 * arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
 * 给定一个数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
 */
public class Code07_PartMinNumber {
    public static void main(String[] args) {
        int[] arr = {3, 2, 10, 30, 50, 8, 9};
        System.out.println(getPartMinNumber(arr));
    }

    // 返回局部最小值
    // 左边界 arr[0] < arr[1] ,返回0下标即可
    // 右边界 arr[N - 2] > arr[N - 1] ，返回N - 1即可
    public static int getPartMinNumber(int[] arr) {
        if (arr == null || arr.length == 0) { // 不足两个数的情况
            return -1;
        }
        // 左边界
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        int n = arr.length;
        // 右边界
        if (arr[n - 2] > arr[n - 1]) {
            return n - 1;
        }


        // 左边高，右边高，局部最小值肯定在中间位置
        // 因为这左右两边已经确定了，所以根据左边或右边，肯定能够作出决策
        int left = 1;
        int right = n - 2;
        int mid = 0;
        while (left < right) { // 至少还有两个数的情况，循环继续
            mid = left + ((right - left) >> 1);
            // mid作为顶点，看左边和右边的高度
            // arr[mid - 1] < arr[mid]，则就是 单调递增的趋势
            // arr[mid] > arr[mid + 1]，则就是 单调递减的趋势
            if (arr[mid] > arr[mid - 1]) { // mid比左边大
                right = mid - 1; // 截掉右部分
            } else if (arr[mid] > arr[mid + 1]) { // mid比右边大
                left = mid + 1; // 截掉左部分
            } else {
                return mid;
            }

        }
        return left; // 循环停下时，left == right
    }
}
