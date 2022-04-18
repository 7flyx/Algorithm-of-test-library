package class02;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:02
 * Description: 不用额外变量，交换两个数据
 */
public class Code01_SwapTwoNumber {
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 20;

        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num1 ^ num2;
        System.out.println(num1 + " " + num2);
    }
}
