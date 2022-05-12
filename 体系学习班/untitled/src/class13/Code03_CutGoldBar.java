package class13;

import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-12
 * Time: 12:01
 * Description: 分割金条。
 * 一块金条切成两半，是需要花费和长度数值一样的铜板
 * 比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板?
 * 例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 * 如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
 * 但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
 * 输入一个数组，返回分割的最小代价
 */
public class Code03_CutGoldBar {
    public static void main(String[] args) {

    }

    public static int getSmallestCost(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : arr) {
            minHeap.add(num);
        }

        int res = 0;
        while (minHeap.size() > 1) {
            // 以堆中最小的两个数据，进行组合，得到一个结果，这个结果
            // 就是其中的一个分割代价--对应的就是哈夫曼编码的思想
            int tmp = minHeap.poll() + minHeap.poll();
            res += tmp;
            minHeap.add(tmp);
        }
        return res;
    }
}
