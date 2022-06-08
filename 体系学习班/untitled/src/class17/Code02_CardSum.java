package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-07
 * Time: 18:07
 * Description:
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数
 */
public class Code02_CardSum {
    public static void main(String[] args) {
        int[] cards = {2, 100, 50, 4};
        System.out.println(getCardSum(cards));
        System.out.println(getCardSum2(cards));
        System.out.println(getCardSum3(cards));
    }

    public static int getCardSum(int[] card) {
        if (card == null || card.length == 0) {
            return 0;
        }
        return Math.max(firstTalk(card, 0, card.length - 1),
                secondTalk(card, 0, card.length - 1));
    }

    // 先手拿
    public static int firstTalk(int[] cards, int left, int right) {
        if (left == right) {
            return cards[left];
        }
        return Math.max(secondTalk(cards, left + 1, right) + cards[left],
                secondTalk(cards, left, right - 1) + cards[right]);
    }

    // 后手拿
    private static int secondTalk(int[] cards, int left, int right) {
        if (left == right) { // 因为是后手拿，此时只有一张牌的情况下，后手就没有了
            return 0;
        }
        return Math.min(firstTalk(cards, left + 1, right),
                firstTalk(cards, left, right - 1));
    }

    // 记忆化搜索版本
    public static int getCardSum2(int[] card) {
        if (card == null || card.length == 0) {
            return 0;
        }
        int N = card.length;
        int[][] firstTalk = new int[N][N];
        int[][] secondTalk = new int[N][N];


        return Math.max(firstTalk2(card, 0, N - 1, firstTalk, secondTalk),
                secondTalk2(card, 0, N - 1, firstTalk, secondTalk));
    }

    // 先手拿
    public static int firstTalk2(int[] cards, int left, int right, int[][] firstTalk, int[][] secondTalk) {
        if (firstTalk[left][right] != 0) {
            return firstTalk[left][right];
        }

        if (left == right) {
            firstTalk[left][right] = cards[left];
        } else {
            firstTalk[left][right] = Math.max(secondTalk2(cards, left + 1, right, firstTalk, secondTalk) + cards[left],
                    secondTalk2(cards, left, right - 1, firstTalk, secondTalk) + cards[right]);
        }
        return firstTalk[left][right];
    }

    // 后手拿
    public static int secondTalk2(int[] cards, int left, int right, int[][] firstTalk, int[][] secondTalk) {
        if (secondTalk[left][right] != 0) {
            return secondTalk[left][right];
        }
        if (left == right) { // 因为是后手拿，此时只有一张牌的情况下，后手就没有了
            return 0;
        }
        secondTalk[left][right] = Math.min(firstTalk2(cards, left + 1, right, firstTalk, secondTalk),
                firstTalk2(cards, left, right - 1, firstTalk, secondTalk));
        return secondTalk[left][right];
    }

    // 经典dp版本
    public static int getCardSum3(int[] card) {
        if (card == null || card.length == 0) {
            return 0;
        }
        int N = card.length;
        int[][] firstTalk = new int[N][N];
        int[][] secondTalk = new int[N][N];
        // 填写base case
        for (int i = 0; i < N; i++) {
            firstTalk[i][i] = card[i]; // 先手拿的表
//            secondTalk[i][i] = 0; // 后手拿的表，默认就是0了
        }

        // 普遍位置
        for (int i = 1; i < N; i++) {
            int row = 0;
            int col = i;
            while (row < N && col < N) {
                firstTalk[row][col] = Math.max(secondTalk[row + 1][col] + card[row],
                        secondTalk[row][col - 1] + card[col]); // 先手拿的情况
                secondTalk[row][col] = Math.min(firstTalk[row + 1][col], firstTalk[row][col - 1]); // 后手拿的情况
                row++;
                col++; // 整体向右下角移动
            }
        }
        return Math.max(firstTalk[0][N - 1], secondTalk[0][N - 1]);
    }
}
