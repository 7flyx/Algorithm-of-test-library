import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-03
 * Time: 15:33
 * Description:最长递增子序列
 */
public class Code03_MaxLengthSubStr {
    public static void main(String[] args) {
        int[] arr = {1, 23, 5, 6, 3, 7, 8, 10, 11};
        int[] res = calcMaxLengthStr(arr);
        System.out.println(Arrays.toString(res));
    }

    public static int[] calcMaxLengthStr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[]{};
        }

        int[] dp = getDp(arr);
        int index = 0; //最大长度的下标值
        int maxLen = arr[0];  //最大长度
        for (int i = 1; i < dp.length; i++) {
            if (maxLen < dp[i]) {
                index = i;
                maxLen = dp[i];
            }
        }
        int[] res = new int[maxLen];
        res[--maxLen] = arr[index];
        for (int i = index - 1; i >= 0; i--) {
            if (arr[i] < res[maxLen] && dp[i] + 1 == dp[index]) {
                res[--maxLen] = arr[i];
                index = i;
            }
        }
        return res;
    }


    public static int[] getDp(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int[] ends = new int[arr.length];
        ends[0] = arr[0]; //第一个数组结尾的递增子序列
        int right = 0;//有效区的范围
        for (int i = 1; i < dp.length; i++) {
            int L = 0;
            int R = right;
            while (L <= R) {
                int mid = (L + R) / 2;
                if (ends[mid] < arr[i]) {
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }

            dp[i] = L + 1; //当前位置的最长递增长度就是L+1
            right = Math.max(right, L); //更新有效区的范围
            ends[L] = arr[i]; //以当前字符结尾的递增子序列
        }
        return dp;
    }
}
