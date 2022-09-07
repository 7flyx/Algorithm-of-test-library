package class38;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-06
 * Time: 20:10
 * Description:
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里，有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不管是牛还是羊，每一轮能吃的草量必须是：1，4，16，64…(4的某次方)
 * 谁最先把草吃完，谁获胜，假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定。根据唯一的参数N，返回谁会赢
 */
public class Code02_EatGrass {
    public static void main(String[] args) {
        int N = 6;
        for (int i = 0; i <= 100; i++) {
            System.out.printf("%2d: %s\n", i, whoWin2(i));
        }
    }

    // N 份草。返回“先手”、“后手”，二者谁会赢。
    public static String whoWin1(int N) {
        if (N <= 4) { // 枚举前4份草的情况
            return (N == 0 || N == 2)? "后手" : "先手";
        }

        int wantEat = 1; // 当前“先手”吃的草的数量
        while (wantEat <= N) {
            if(whoWin1(N - wantEat).equals("后手")) { // 下一层的“后手”，就是当前层的“先手”
                return "先手";
            }
            if (wantEat <= N / 4) { // 目的就是为了防止溢出
                wantEat *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }

    // 打表后优化的结果
    public static String whoWin2(int N ) {
        int num = N % 5;
        return (num == 0 || num == 2)? "后手" : "先手";
    }
}
