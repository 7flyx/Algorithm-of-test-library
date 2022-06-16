package class17;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-14
 * Time: 14:43
 * Description: 2019年京东原题 洗咖啡杯问题
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 * 四个参数：int[] arr、int N，int a、int b
 */
public class Code09_Coffee {

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }

    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static int minTime1(int[] arr, int N, int a, int b) {
        if (arr == null || arr.length == 0 || N <= 0) {
            return 0;
        }
        int[] drinks = forceMake(arr, N);
        return forceWash(drinks, a, b, 0, 0);
    }

    private static class Machine {
        public int timePoint; // 空闲时间
        public int workTime; // 制作一杯咖啡的时间

        public Machine(int timePoint, int makeTime) {
            this.timePoint = timePoint;
            this.workTime = makeTime;
        }
    }

    private static int[] forceMake(int[] arr, int N) {
        PriorityQueue<Machine> minHeap = new PriorityQueue<>((o1, o2) -> {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);  // 二者相加得到的是当前做好一杯咖啡的时间
        });

        for (int makeTime : arr) {
            minHeap.add(new Machine(0, makeTime));
        }

        int[] drinks = new int[N];
        for (int i = 0; i < N; i++) {
            Machine curCoffee = minHeap.poll();
            curCoffee.timePoint += curCoffee.workTime; // 新的空闲时间
            drinks[i] = curCoffee.timePoint;
            minHeap.add(curCoffee);
        }
        return drinks;
    }

    private static int forceWash(int[] drinks, int a, int b, int index, int washLine) {
        if (index == drinks.length) {
            return 0; // 理应来说是返回机器洗完的时间，当时上层递归处理时，是取max，所以这里并不会影响最终的结果
        }
        // 1、机器洗当前杯子
        int newWashLine = Math.max(drinks[index], washLine) + a;
        int next1 = forceWash(drinks, a, b, index + 1, newWashLine);
        int p1 = Math.max(newWashLine, next1); // 当前杯子机器洗和后续杯子洗完的时间，二者取最慢时间点

        // 2、自然挥发
        int autoClear = drinks[index] + b; // 自动挥发干净后的时间点
        // 自动挥发的时间和后续杯子洗完的时间，取最慢的时间点
        int p2 = Math.max(autoClear, forceWash(drinks, a, b, index + 1, washLine));
        return Math.min(p1, p2); // 机器洗和自动挥发，二者取最快的结果就行
    }

    // 根据minTime1改的经典dp
    private static int minTime2(int[] arr, int N, int a, int b) {
        if (arr == null || arr.length == 0 || N <= 0) {
            return 0;
        }
        int[] drinks = forceMake(arr, N); // 数组已经有序了
        // 根据递归函数可知  可变参数有2个：index和washLine
        // washLine的最大范围也就是 当所有的杯子都用机器洗的时候
        int maxWashLine = 0;
        for (int i = 0; i < N; i++) {
            maxWashLine = Math.max(maxWashLine, drinks[i]) + a;
        }
        int[][] dp = new int[N + 1][maxWashLine + 1];
        // base case---最后一行的情况
//        for (int washLine = 0; washLine <= maxWashLine; washLine++) {
//            dp[N][washLine] = washLine;
//        }

        // 填写普遍位置
        for (int index = N - 1; index >= 0; index--) {
            for (int washLine = 0; washLine <= maxWashLine; washLine++) {
                // 1、机器洗
                int newWashLine = Math.max(drinks[index], washLine) + a;
                int next1 = 0;
                if (newWashLine <= maxWashLine) {
                    next1 = dp[index + 1][newWashLine];
                }
                int p1 = Math.max(newWashLine, next1); // 机器洗当前杯子和后续杯子洗完的时间，二者取最慢
                // 2、自动挥发
                int autoClear = drinks[index] + b;
                int next2 = dp[index + 1][washLine];
                int p2 = Math.max(autoClear, next2); // 自动挥发的时间和后续杯子洗完的时间，二者取最慢
                dp[index][washLine] = Math.min(p1, p2); // 机器洗和自动挥发，二者取最快
            }
        }
        return dp[0][0];
    }

    public static class MachineComparator implements Comparator<Machine> {

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    // 优良一点的暴力尝试的方法
    public static int zuo(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTime(drinks, a, b, 0, 0);
    }

    // drinks 所有杯子可以开始洗的时间
    // wash 单杯洗干净的时间（串行）
    // air 挥发干净的时间(并行)
    // free 洗的机器什么时候可用
    // drinks[index.....]都变干净，最早的结束时间（返回）
    public static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        if (index == drinks.length) {
            return 0;
        }
        // index号杯子 决定洗
        int selfClean1 = Math.max(drinks[index], free) + wash;
        int restClean1 = bestTime(drinks, wash, air, index + 1, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);

        // index号杯子 决定挥发
        int selfClean2 = drinks[index] + air;
        int restClean2 = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(selfClean2, restClean2);
        return Math.min(p1, p2);
    }

    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }
}
