import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-22
 * Time: 17:20
 * Description:
 */


public class Code12_SubArraySum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(minSumArr(arr));
    }


    public static int minSumArr(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }
        int sum = arr[0];
        int max = arr[0];
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            sum += arr[i];
            if (sum < 0) {
                sum = 0; //如果当前累加和小于0，则重新归0
            } else {
                max = Math.max(sum, max);
            }
        }
        return max;
    }
}
