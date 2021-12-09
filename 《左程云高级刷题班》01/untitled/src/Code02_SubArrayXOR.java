import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-09
 * Time: 21:50
 * Description: 划分子数组，达到每个组的异或和为0。
 * 给出n个数字 a_1,...,a_n， 问最多有多少不重叠的非空区间， 使得每个区间内数字的
 * xor都等于0。
 */
public class Code02_SubArrayXOR {
    public static void main1(String[] args) {
        int[] arr = {3, 2, 1, 0, 4, 0, 3, 2, 1, 0, 0};
        System.out.println(calcMaxSubSum(arr));
    }

    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] eors = new int[arr.length];
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
            eors[i] = eor;
        }
        int[] mosts = new int[arr.length];
        mosts[0] = arr[0] == 0 ? 1 : 0;
        for (int i = 1; i < arr.length; i++) {
            mosts[i] = eors[i] == 0 ? 1 : 0;
            for (int j = 0; j < i; j++) {
                if ((eors[i] ^ eors[j]) == 0) { //以i位置结尾，一个个尝试0~i之间的数据
                    mosts[i] = Math.max(mosts[i], mosts[j] + 1);
                }
            }
            mosts[i] = Math.max(mosts[i], mosts[i - 1]); //和前一个数据进行比较
        }
        return mosts[mosts.length - 1];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 5000;
        int maxSize = 300;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = calcMaxSubSum(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    /**
     * 计算有多少个子数组能异或和为0
     * @param arr 数组
     * @return 返回最多的子数组，使疑惑和为0
     */
    public static int calcMaxSubSum(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }

        int N= arr.length;
        int[] dp = new int[N];
        //dp[i]表示以i位置结尾的子数组，最多能划分多少个异或和为0的
        dp[0] = arr[0] == 0? 1 : 0; //第一个字符，以自己为一组，如果本身就是0，那就是一种划分结果
        int eor = arr[0]; //计算的是0~i所有数据的异或
        //用一张表存储每个位置的异或值，用于后续向前查询是否有相同的异或值，因为两个相同值的异或是0
        HashMap<Integer, Integer> lastNumMap = new HashMap<>();
        lastNumMap.put(0, -1);
        lastNumMap.put(eor, 0); //第一个数字的异或值
        int preIndex = 0;
        for (int i = 1; i < N; i++) {
            eor ^= arr[i]; //先异或当前位置的值
            if (lastNumMap.containsKey(eor)) { //如果前面的数组有过eor当前的值，说明当前可以组成一个子数组
                preIndex = lastNumMap.get(eor); //拿到前一个数字的下标
                dp[i] = preIndex == -1? 1 : dp[preIndex] + 1;
            }
            dp[i] = Math.max(dp[i], dp[i - 1]); //dp[i]可能是前面数组的划分结果，不一定是最优的
            lastNumMap.put(eor, i);
        }
        return dp[N - 1];
    }
}
