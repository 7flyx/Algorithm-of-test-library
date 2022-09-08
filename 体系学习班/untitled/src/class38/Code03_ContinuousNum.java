package class38;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-06
 * Time: 20:11
 * Description:
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如，5=2+3，5就是这样的数；12=3+4+5，12就是这样的数
 * 2=1+1，2不是这样的数，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 */
public class Code03_ContinuousNum {
    public static void main(String[] args) {
        for (int i = 3; i <= 100; i++) {
//            System.out.println(continuousNum(i));
            System.out.println(continuousNum2(i));
        }

    }

    // 暴力拆解
    public static boolean continuousNum(int N) {
        if (N <= 2) {
            return false;
        }

        // 暴力拆解，以1开始往后、以2开始往后、以3开始往后
        for (int i = 1; i <= N; i++) { // 就算是两个数相加，最多也不会超过一半
            int sum = i;
            for (int num = i + 1; num <= N; num++) {
                sum += num;
                if (sum == N) {
                    System.out.print("目标值：" + N + "起始值：" + i);
                    return true;
                }
                if (sum > N) {
                    break;
                }
            }
        }
        System.out.print(N + ": ");
        return false;
    }

    public static boolean continuousNum2(int N) {
        // 根据暴力拆解，找出规律。一个数的二进制只有一个1时。这个数就不能被连续的数相加得到
        return N != (N & (-N)); // 取出最右侧的1。进行判断
    }
}
