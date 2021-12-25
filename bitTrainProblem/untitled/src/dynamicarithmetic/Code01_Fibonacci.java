package dynamicarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:12
 * Description:斐波那契数列-还有用线代的知识解的，能做到时间复杂度O(logN)
 * https://www.nowcoder.com/practice/c6c7742f5ba7442aada113136ddea0c3?tpId=13&tqId=11160&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 */
public class Code01_Fibonacci {
    class Solution {
        public int Fibonacci(int n) {
            if (n < 1) {
                return 0;
            }
            if (n < 3) {
                return 1;
            }
            int num1 = 1;
            int num2 = 1;
            int num3 = 0;
            for (int i = 3; i <= n; i++) {
                num3 = num1 +num2;
                num1 = num2;
                num2 = num3;
            }
            return num2;
        }
    }
}
