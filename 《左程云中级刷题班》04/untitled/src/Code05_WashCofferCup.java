import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-14
 * Time: 20:08
 * Description:
 * 首先，给你几个数据：
 * 数组arr：表示几个咖啡机，这几个咖啡机生产一杯咖啡所需要的时间就是数组中的值，
 * 例如arr=[2,3,7]就表示第一台咖啡机生产一杯咖啡需要2单位时间，第二台需要3单位时间，第三台需要7单位时间。
 * int N：表示有N个人需要用咖啡机制作咖啡，每人一杯，同时，假设制作完咖啡后，喝咖啡时间为0，一口闷。
 * int a：表示用洗碗机洗一个咖啡杯需要的时间，串行运行。
 * int b：表示咖啡杯也可以不洗，自然晾干的时间，咖啡杯要么洗，要么晾干。
 * 现在，请你求出这N个人从开始用咖啡杯制作咖啡到杯子洗好或者晾干的最少时间？
 */
public class Code05_WashCofferCup {
    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 9, 10, 11};
        int N = 20;
        int a = 9;
        int b = 12;
        int[] res = calcMakeAndWashCoffer(arr, N, a, b);
        System.out.println(Arrays.toString(res));
    }

    private static class Node {
        public int startTime; //机器有空闲的时间
        public int requireTime; //冲一个咖啡的时间

        public Node(int startTime, int requireTime) {
            this.requireTime = requireTime;
            this.startTime = startTime;
        }
    }

    /**
     * 主方法
     * @param arr 咖啡机，以及冲一杯咖啡的时间
     * @param N   要喝咖啡的人数
     * @param a   洗杯子的机器，洗一个杯子的时间
     * @param b   自然晾干杯子的时间
     * @return 返回最少时间
     */
    public static int[] calcMakeAndWashCoffer(int[] arr, int N, int a, int b) {
        if (arr == null || arr.length == 0 || N < 1) {
            return new int[]{};
        }

        //首先需要计算出每个人，拿到咖啡的时间点。然后根据这个时间点计算洗咖啡杯的开始时间
        //根据小根堆，来计算每个人拿到咖啡的时间
        PriorityQueue<Node> minHeap = new PriorityQueue<>((o1, o2) -> {
            return (o1.startTime + o1.requireTime) - (o2.startTime + o2.requireTime);
        });

        for (int i = 0; i < arr.length; i++) {
            minHeap.add(new Node(0, arr[i])); //将所有咖啡机的状态放入堆中
        }
        int[] drinks = new int[N]; //存放每个人拿到咖啡的时间
        for (int i = 0; i < N; i++) {
            Node node = minHeap.poll();
            drinks[i] = node.requireTime + node.startTime;
            node.startTime = drinks[i]; //更新咖啡机空闲的时间
            minHeap.add(node); //再次将node压入，节省开辟新节点的空间
        }
        System.out.println(Arrays.toString(drinks));

        //接下来就是根据喝完咖啡的时间，来安排洗杯子。
        //洗杯子有两个方法：自然晾干和机器洗。
        //所以每一个杯子的状态就是，要么自己晾干，要么机器洗，这两种状态
        int p2 = washCofferCup(drinks, a, b, 0, 0);
        int p1 = washCofferCup2(drinks, a, b);
        return new int[]{p2, p1};
    }

    /**
     * 暴力递归求解洗杯子的最佳时间
     *
     * @param drinks   喝完咖啡的时间点
     * @param a        机器洗一个杯子的时间
     * @param b        自然晾干的时间
     * @param index    指向drink数组
     * @param washLine 当前机器空闲的时间点
     * @return 返回最佳时间
     */
    public static int washCofferCup(int[] drinks, int a, int b, int index, int washLine) {
        if (index == drinks.length - 1) { //最后一个杯子的时候
            //机器洗和自然晾干的时间，取最优时间点
            return Math.min(Math.max(drinks[index], washLine) + a, drinks[index] + b);
        }

        //分为机器洗和自然晾干两种可能性，分别列举即可
        int wash = Math.max(drinks[index], washLine) + a; //机器洗完杯子的时间
        //向下递归，计算洗完后面杯子的最优时间
        int next = washCofferCup(drinks, a, b, index + 1, wash); //传递的是洗完杯子的时间
        int p1 = Math.max(wash, next); //二者取最大值。

        int autoDry = drinks[index] + b; //自然晾干的时间
        next = washCofferCup(drinks, a, b, index + 1, washLine); //机器空闲时间不变
        int p2 = Math.max(autoDry, next); //二者取最大值
        return Math.min(p1, p2); //求整体最优，则当前决策是机器洗还是自然晾干。取最优结果
    }

    /**
     * 动态规划版本
     * @param drinks
     * @param a
     * @param b
     * @return
     */
    public static int washCofferCup2(int[] drinks, int a, int b) {
        //根据递归版本推导出，可变参数就两个 index 和washLine。则动态规划就是一个二维表
        //问题就在于washLine的值，怎么计算？？
        //也就是所有的杯子，都用机器洗，计算出来的时间，就是washLine的极限值
        int limit = 0;
        int N = drinks.length;
        for (int i = 0; i < N; i++) {
            limit = Math.max(limit, drinks[i]) + a;
        }

        int[][] dp = new int[N][limit];
        for (int i = 0; i < limit; i++) {
            //最后一个杯子，机器洗和自然晾干的时间，求最优解
            dp[N - 1][i] = Math.min(Math.max(drinks[N - 1], i) + a, drinks[N - 1] + b); //Math.max(drinks[N - 1], i)中：为什么要和i比较？
        }

        for (int i = N - 2; i >= 0; i--) {
            for (int wash = 0; wash < limit; wash += 1) {
                int washByMachine = Math.max(drinks[i], wash) + a;
                int next = 0; //后续杯子的最优时间
                if (washByMachine < limit) {
                    next = dp[i + 1][washByMachine];
                }
                dp[i][wash] = Math.max(washByMachine, next); //整体是求最大值

                int autoDry = drinks[i] + b;
                next = dp[i + 1][wash];
                dp[i][wash] = Math.min(dp[i][wash], Math.max(autoDry, next)); //整体取最小值
            }
        }
        return dp[0][0];
    }

}
