import java.util.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-28
 * Time: 20:44
 * Description:
 * 给定一个字符串str，str全部由数字字符组成，如果str中的某一个或者相邻两个字符组成的子串值在1~26之间，
 * 则这个子串可以转换为一个字母。规定‘1’转换为“A”，“2”转换为“B”......"26"转化为“Z“。
 * 请求出str有多少种不同的转换结果，由于答案可能会比较大，
 */

public class Code10_NumberToChar {
    static int mod = (int)(1e9+7);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        System.out.println(num(str));
    }

    //暴力递归版本
    public static int numberToChar1(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }
        return process1(str.toCharArray(), 0);
    }

    private static int process1(char[] arr, int index) {
        if (index >= arr.length) {
            return 1;
        }
        if (arr[index] == '0') {
            return 0;
        }

        int p1 = process1(arr, index + 1);
        int p2 = -1;
        if (index + 1 < arr.length && arr[index + 1] <= '6' && arr[index] <= '2') {
            p2 =  process1(arr, index + 2);
        }

        if (p2 != -1) {
            return p2 + p1;
        }
        return p1;
    }

    public static int numberToChar2(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }
        int N = str.length();
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                dp[i] = 0;
                continue;
            }

            int p1 = dp[i + 1];
            int p2 = 0;
            if (i + 1 < N && str.charAt(i) <= '6' && str.charAt(i) <= '2') {
                p2 = dp[i + 2];
            }
            dp[i] = (p1 + p2);
        }
        return dp[0];
    }

    public static int numberToChar3(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }

        int N = str.length();
        char[] ch = str.toCharArray();
        int cur = str.charAt(N - 1) == '0'? 0 : 1;//当前字符的情况
        int tmp = 0;
        int next = 1; //cur的下一个字符的    情况
        for (int i = N - 2; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                next = cur;
                cur = 0;
            } else {
                tmp = cur;
                if (str.charAt(i) <= '2' && str.charAt(i + 1) <= '6') {
                    cur += next;
                    cur %= mod;
                }
                next = tmp;
            }
        }
        return cur % mod;
    }

    public static int num(String str){
        if(str == null || str.equals(""))
            return 0;
        char[] chs = str.toCharArray();
        int cur = chs[chs.length - 1] == '0' ? 0 : 1;
        int next = 1;
        int tmp = 0;
        for(int i = chs.length - 2; i >= 0; i--){
            if(chs[i] == '0'){
                next = cur;
                cur = 0;
            }else{
                tmp = cur;
                if((chs[i] - '0')*10 + chs[i + 1] - '0' < 27){
                    cur += next;
                    cur %= mod;
                }
                next = tmp;
            }
        }
        return cur%mod;
    }
}