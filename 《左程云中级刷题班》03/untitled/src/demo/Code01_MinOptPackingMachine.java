package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 16:28
 * Description:
 * 有n个打包机器从左到右一字排开， 上方有一个自动装置会抓取一批放物品到每个打
 * 包机上， 放到每个机器上的这些物品数量有多有少， 由于物品数量不相同， 需要工人
 * 将每个机器上的物品进行移动从而到达物品数量相等才能打包。 每个物品重量太大、
 * 每次只能搬一个物品进行移动， 为了省力， 只在相邻的机器上移动。 请计算在搬动最
 * 小轮数的前提下， 使每个机器上的物品数量相等。 如果不能使每个机器上的物品相同，
 * 返回-1。
 *
 * 力扣原题：超级洗衣机问题
 */
public class Code01_MinOptPackingMachine {

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        System.out.println(findMinMoves(machines));
    }

    /**
     分析：
     遍历每个位置的衣服数量，然后计算以当前位置为中心点，
     left = 左部分实际的衣服数量 - 左部分应该有的衣服数量
     right = 右部分实际的衣服数量 - 右部分应该有的衣服数量。
     整体分为三种情况：
     1. left 和 right 都是负数。则说明当前i位置的洗衣机的衣服太多了，需要向左右两边给衣服，但是每次只能给1件衣服，
     所以总共需要|left| + |right|，二者的绝对值相加。
     2. left 和 right 都是正数。则说明两边的衣服都多出来了，都需要往i位置的洗衣服放。因为并没有限制洗衣服一轮能接
     收多少衣服，所以左右两边可以同时往i位置放衣服，则需要left和right两个数谁最大，就是需要几轮。
     3. left和right，一正一负。则说明有一边衣服多了，另一边衣服少了。数量少的那一边只需要接收衣服，不需要向外拿。所以
     往外面拿的那一边，就是瓶颈，正数就是当前需要的几轮的数量。
     */
    public static int findMinMoves(int[] machines) {
        if (machines == null || machines.length < 2) {
            return 0;
        }

        int sum = 0;
        int N = machines.length;
        for (int i = 0; i < N; i++) {
            sum += machines[i];
        }
        if (sum % N != 0) {
            return -1; //总数不能平分，则直接返回-1
        }
        int number = sum / N; //每个洗衣机应该有的衣服数量
        int leftSum = 0; //左部分有多少衣服
        int res = 0; //最终的结果，取遍历时的最大值，则就是瓶颈
        //从左到右遍历，计算以当前位置为中心点，左部分需要（多）多少衣服，右部分需要（多）多少衣服
        for (int i = 0; i < N; i++) {
            int left = leftSum - i * number; //计算左部分还差（多）几件衣服
            int right = sum - leftSum - machines[i] - (N - i - 1) * number; //计算右部分还差（多）几件衣服
            if (left < 0 && right < 0) { //两边都差衣服，则只能从当前位置拿衣服。每次只能拿1件
                res = Math.max(res, -left - right); //二者取绝对值相加
            } else { //left right都是正数，或者一正一负。则取最大值
                res = Math.max(res, Math.max(left, right));
            }
            leftSum += machines[i];
        }
        return res;
    }
}
