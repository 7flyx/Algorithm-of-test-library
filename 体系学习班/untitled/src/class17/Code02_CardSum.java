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


}
