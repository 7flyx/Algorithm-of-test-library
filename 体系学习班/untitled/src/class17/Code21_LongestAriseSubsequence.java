package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-21
 * Time: 9:43
 * Description: LeetCode300、最长递增子序列的长度
 */

public class Code21_LongestAriseSubsequence {
    public static void main(String[] args) {
//        int[] arr = {10,9,2,5,3,7,101,18};
        int[] arr = {0, 1, 0, 3, 3, 3};
//        int res = longestAriseSubsequence(arr);
//        System.out.println(res);
        StringBuilder sb = new StringBuilder();
        System.out.println("hello");
    }

    public static int longestAriseSubsequence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] len = new int[N]; // 对应的下标表示当前长度，对应的值表示 当前长度的末尾值
        int right = -1; // 表示已经更新到的范围（右边界）
        for (int i = 0; i < N; i++) {
            right = Math.max(right, updateLen(len, arr[i], right));
        }
        return right + 1;
    }

    public static int updateLen(int[] len, int num, int right) {
        int l = 0;
        int r = right;
        int mid = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (len[mid] < num) { // 这写小于，是严格的递增。写小于等于，是不严格的递增子序列
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        // 此时l位置，来到了最佳的位置
        len[l] = num; // 表示l+1长度的子序列，以num数字结尾
        return l;
    }
}


