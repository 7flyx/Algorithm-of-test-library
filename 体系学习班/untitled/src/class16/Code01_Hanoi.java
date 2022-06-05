package class16;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-05
 * Time: 16:49
 * Description: 汉诺塔问题
 */
public class Code01_Hanoi {
    public static void main(String[] args) {
        hanoi(3);
    }

    /**
     * @param n 层数
     */
    public static void hanoi(int n) {
        if (n > 0) {
            process(n, "left", "right", "mid");
        }
    }

    public static void process(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("from " + from + " to " + to);
            return;
        }
        process(n - 1, from, other, to); // 上面n-1层，从from点，先移动到other进行中转
        System.out.println("from " + from + " to " + to);
        process(n - 1, other, to, from); // 将上面移动的n-1层，从中转点other移动到to点
    }
}
