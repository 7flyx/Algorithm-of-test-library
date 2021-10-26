package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 17:05
 * Description:小虎去附近的商店买苹果， 奸诈的商贩使用了捆绑交易， 只提供6个每袋和8个
 * 每袋的包装包装不可拆分。 可是小虎现在只想购买恰好n个苹果， 小虎想购买尽
 * 量少的袋数方便携带。 如果不能购买恰好n个苹果， 小虎将不会购买。 输入一个
 * 整数n， 表示小虎想购买的个苹果， 返回最小使用多少袋子。 如果无论如何都不
 * 能正好装下， 返回-1
 */
public class Code02_PutApple {
    public static void main(String[] args) {
        int N =100;
        System.out.println(putMeans(N));
    }

    public static int putMeans(int N) {
        if (N < 1 || (N & 1) == 1) {
            return -1;
        }
        if (N % 8 == 0) {
            return N / 8;
        }

        int bag8 = N / 8; //装8个苹果的袋子数
        int bag6 = 0;
        for (int i = bag8; i >= 0; i--) {
            int rest = N - i * 8;
            if (rest > 24) { //优化
                break;
            }
            if (rest % 6 == 0) {
                bag8 = i;
                bag6 = rest / 6;
                break;
            }
        }
        //如果bag6的袋子还是0.说明根本就没法装入
        return bag6 == 0? -1 : bag6 + bag8;
    }
}
