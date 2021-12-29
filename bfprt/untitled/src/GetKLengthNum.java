/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-29
 * Time: 21:26
 * Description:给定一个无序数组，返回第K大的数。
 *
 */
public class GetKLengthNum {

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return -1;
        }
        //到底是计算第K大还是第K小，我们只需改变一下递归函数传递的参数即可。原函数是计算第K小的
        return bfprt(nums, 0, nums.length - 1, nums.length - k );
    }

    private int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }

        //获取基准值-小组内排序
        int pivot = medianOfMedians(arr, L, R);
        int[] mid = partition(arr, L, R, pivot); //划分区间
        if (index >= mid[0] && index <= mid[1]) {
            return arr[index];
        } else if (index < mid[0]) {
            return bfprt(arr, L, mid[0] - 1, index);
        } else {
            return bfprt(arr, mid[1] + 1, R, index);
        }
    }

    private int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0? 0 : 1;
        int[] tmp = new int[size / 5 + offset];
        for (int i = 0; i < tmp.length; i++) {
            int start = L + i * 5; //每个小组的开始下标
            tmp[i] = getMedianNum(arr, start, Math.min(start + 4, R));//闭区间
        }
        return bfprt(tmp, 0, tmp.length - 1, tmp.length / 2);//求tmp的中位数
    }

    private int getMedianNum(int[] arr, int L, int R) {
        selectSort(arr, L, R); //闭区间
        return arr[(R + L) / 2];
    }

    private int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int index = L;
        while (index < more) {
            if(arr[index] == pivot) {
                index++;
            } else if (arr[index] > pivot) {
                swap(arr, index, --more);
            } else {
                swap(arr, index++, ++less);
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private void selectSort(int[] arr, int left, int right) {
        for (int i = left; i <= right - 1; i++) {
            int min = i;
            for (int j = i + 1; j <= right; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }
        }
    }

    private void swap(int[] arr, int left, int right) {
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }
}