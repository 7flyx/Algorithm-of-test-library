/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-03
 * Time: 14:15
 * Description:
 * 小KQM得P算到一法个扩神展奇题的目数二列: 1, 12, 123,...12345678910,1234567891011...。
 * 并且小Q对于能否被3整除这个性质很感兴趣。
 * 小Q现在希望你能帮他计算一下从数列的第l个到第r个(包含端点)有多少个数可以被3整除。
 * 输入描述：
 * 输入包括两个整数l和r(1 <= l <= r <= 1e9), 表示要求解的区间两端。
 * 输出描述：
 * 输出一个整数, 表示区间内能被3整除的数字个数。
 * 示例1:
 * 输入
 * 2 5
 * 输出
 * 3
 */
public class Code01_SpecialArray {
    public static void main(String[] args) {
        int l = 2;
        int r = 5;
        System.out.println(calcSpecialNumber1(l, r));
        System.out.println(calcSpecialNumber2(l, r));
    }

    /**
     * 返回这个闭区间内，所有满足能被3整除的数
     * @param l 左边界
     * @param r 右边界
     * @return 有多少个参数可以被整除
     */
    public static int calcSpecialNumber1(int l, int r) {
        StringBuilder sb = new StringBuilder();
        int res = 0;
        for (int i = 1; i <= r; i++) {
            sb.append(i);
            if (i >= l) {
                res += process(sb.toString().toCharArray());
            }
        }
        return res;
    }

    public static int process(char[] ch) {
        if (ch == null || ch.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < ch.length; i++) {
            sum += ch[i] - '0';
        }
        if (sum % 3 == 0) { //能被3整除
            return 1;
        }
        return 0;
    }
    /*
        实则需要计算一个数能不能被3整除，原始的思路就是一项一项进行累加，但其实反推回来，
        就是一个等差数列1 2  …… 19 20
        的求和，直接套公式，在取模即可
        N * (a1 + aN) / 2
     */
    public static int calcSpecialNumber2(int l, int r) {
        int res = 0;
        for (int i = l; i <= r; i++) {
            //等差数列求和公式
            if ((i * (i + 1) / 2) % 3 == 0) {
                res++;
            }
        }
        return res;
    }
}
