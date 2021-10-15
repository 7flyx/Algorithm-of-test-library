import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-14
 * Time: 20:40
 * Description:
 * 给定一个整型数组arr，其中可能有正有负有零。你可以随意把整个数组切成若干个不相容的子数组，
 * 求异或和为0的子数组最多可能有多少个？整数异或和定义：把数组中所有的数异或起来得到的值。
 */


public class Code07_GetSubArrEOR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(mostEOR(arr));
    }

    public static int mostEOR(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //dp[0...i]，表示0……i范围内的最大子数组异或和为0 的个数
        int[] dp = new int[arr.length];
        dp[0] = arr[0] == 0 ? 1 : 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); //前者表示异或和，后者表示下标
        map.put(arr[0], 0);
        int eor = arr[0];

        for (int i = 1; i < arr.length; i++) {
            eor ^= arr[i];
            if (map.containsKey(eor)) { //如果前面的数据有相等的eor
                int index = map.get(eor);
                dp[i] = index == -1 ? 1 : dp[index] + 1; //dp[index] + 1
            }

            //实则就是dp[i - 1] 和 dp[index] + 1
            dp[i] = Math.max(dp[i], dp[i - 1]);
            map.put(eor, i);
        }

        return dp[dp.length - 1];
    }
}