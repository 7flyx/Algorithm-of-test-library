package class39;

import java.util.TreeMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-23
 * Time: 14:29
 * Description:
 * 给定一个数组arr，给定一个值v，求子数组平均值小于等于v的最长子数组长度
 */
public class Code04_AvgLessEqualValueSubArray {
    // 用于测试
    public static void main(String[] args) {
        System.out.println("测试开始");
        int maxLen = 20;
        int maxValue = 100;
        int testTime = 500000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int ans1 = getMaxSubArrayLength(arr1, value);
            int ans2 = ways2(arr2, value);
            if (ans1 != ans2 ) {
                System.out.println("测试出错！");
                System.out.print("测试数组：");
                printArray(arr);
                System.out.println("子数组平均值不小于 ：" + value);
                System.out.println("方法1得到的最大长度：" + ans1);
                System.out.println("方法2得到的最大长度：" + ans2);
                System.out.println("=========================");
            }
        }
        System.out.println("测试结束");
    }

    // 用于测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    // 用于测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 用于测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int getMaxSubArrayLength(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 举个例子：假设数组长度为10， v=10
        // 0 1 2 3 4 5 6 7 8 9
        // 假设最终答案的长度=7，我们只需保证某长度为7的子数组累加和 <= 7 * 10即可
        // 进一步的，提前将每个位置的数据-10。后续在计算累加和的时候，只需要保证累加和<=0即可
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            arr[i] -= v;
        }
        // 此时arr中的数据还是存在 正数 负数 0的情况，所以单纯的使用滑动窗口行不通，不具备单调性
        int[] minSum = new int[N];
        int[] minSumEnd = new int[N];
        minSum[N - 1] = arr[N - 1]; // 最小数值
        minSumEnd[N - 1] = N - 1; // 某一块范围的右边界
        // 填写minSum和minSumEnd数组
        for (int i = N - 2; i >= 0; i--) {
            if (minSum[i + 1] < 0) {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            } else {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }
        // 在上述“划分”好的块中，使用滑动窗口
        int L = 0;
        int R = 0; // 左闭右开区间
        int sum = 0;
        int len = 0;
        while (L < N) {
            while (R < N && sum + minSum[R] <= 0) {
                sum += minSum[R];
                R = minSumEnd[R] + 1; // 下一块数据的开始点
            }
            // 循环停下来时，就是累加下一个字母就会超过0。结算长度即可
            len = Math.max(len, R - L);
            if (L < R) { // 窗口内还有数值的情况
                sum -= arr[L++];
            } else { // 窗口内已经没有数值了。此时sum累加R位置的数还会超过。说明R位置的数不能使用。
                L++;
                R++;
            }
        }
        return len;
    }

    // 左神写的
    // 想实现的解法2，时间复杂度O(N*logN)
    public static int ways2(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        TreeMap<Integer, Integer> origins = new TreeMap<>();
        int ans = 0;
        int modify = 0;
        for (int i = 0; i < arr.length; i++) {
            int p1 = arr[i] <= v ? 1 : 0;
            int p2 = 0;
            int querry = -arr[i] - modify;
            if (origins.floorKey(querry) != null) {
                p2 = i - origins.get(origins.floorKey(querry)) + 1;
            }
            ans = Math.max(ans, Math.max(p1, p2));
            int curOrigin = -modify - v;
            if (origins.floorKey(curOrigin) == null) {
                origins.put(curOrigin, i);
            }
            modify += arr[i] - v;
        }
        return ans;
    }
}
