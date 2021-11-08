package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 15:05
 * Description:
 * 牛牛和羊羊都很喜欢青草。 今天他们决定玩青草游戏。
 * 最初有一个装有n份青草的箱子,牛牛和羊羊依次进行,牛牛先开始。 在每个回合中,每个
 * 玩家必须吃一些箱子中的青草,所吃的青草份数必须是4的x次幂,比如1,4,16,64等等。
 * 不能在箱子中吃到有效份数青草的玩家落败。 假定牛牛和羊羊都是按照最佳方法进行游
 * 戏,请输出胜利者的名字
 */
public class Code05_EatGrass {
    public static void main(String[] args) {
        int N = 50; //草的数量
        for (int i = 0; i <= 50; i++) {
            //根据尝试的结果进行推导，简化计算过程
            //System.out.println( i + ": " + winner1(i));
            System.out.println( i + ": " + winner2(i));
        }
    }

    public static String winner1(int N) {
        // 0  1  2  3  4
        //后  先 后 先 先
        if (N < 5) {
            return (N == 0 || N == 2)? "后手" : "先手";
        }

        //递归调用子过程
        int base = 1; //当前先手吃的草的数量
        while (base <= N) {
            //先手有一次赢了，则就是先手赢。当前过程的先手，就是下一个子过程的后手
            if (winner1(N - base).equals("后手")) {
                return "先手";
            }
            if (base > N / 4) {
                break;
            }
            base *= 4;//
        }
        return "后手";
    }

    public static String winner2(int N) {
        if (N % 5 == 0 || N % 5 == 2) {
            return "后手";
        }
        return "先手";
    }
}
