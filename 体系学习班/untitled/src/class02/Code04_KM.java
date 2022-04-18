package class02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-18
 * Time: 21:14
 * Description: 给定一个数组，其中一种数出现了K次，其余的数都是出现了M次，M>1，并且M>K的
 * 请返回这个出现K次的数
 */
public class Code04_KM {
    public static void main(String[] args) {
        int numKings = 10; // 数字种数
        int range = 100; // 生成的数字范围
        int testTimes = 10000; // 测试次数
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * 9) + 1; // 1~9范围
            int b = (int) (Math.random() * 9) + 1; // 1~9范围
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            // 生成随机数组
            int[] arr = generateArray(numKings, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = getKM(arr, k, m);
            if (ans1 != ans2) {
                System.out.println("测试失败");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] generateArray(int numKings, int range, int k, int m) {
        // 总共的个数是 k + (numKings - 1) * m
        int[] res = new int[k + m * (numKings - 1)];
        int kTimeNumber = getRandomNumber(range); // 真命天子
        int index = 0;
        for (; index < k; index++) { // 先填写真命天子
            res[index] = kTimeNumber;
        }

        HashSet<Integer> set = new HashSet<>(); // 用于过滤掉重复的数值
        set.add(kTimeNumber);
        numKings--; // 减去真命天子这个数
        while (numKings != 0) {
            int num = getRandomNumber(range);
            while (set.contains(num)) { //如果已经有这个数了，重新挑选
                num = getRandomNumber(range);
            }
            set.add(num);
            numKings--;
            for (int i = 0; i < m; i++) { // 出现了m次
                res[index++] = num;
            }
        }

        // 随机打乱全部数据
        for (int i = 0; i < res.length; i++) {
            int j = (int)(Math.random() * res.length); // 下标范围在 0 ~ N - 1
            if (i != j) {
                int tmp = res[i];
                res[i] = res[j];
                res[j] = tmp;
            }
        }
        return res;
    }

    // [-range, +range] 范围
    public static int getRandomNumber(int range) {
        return ((int)(Math.random() * range) + 1) - ((int)(Math.random() * range) + 1);
    }

    public static int getKM(int[] arr, int k, int m) {
        if (arr == null || k > m) {
            return -1;
        }

        // 0 指的是低位
        // 31 指的是高位
        int[] t = new int[32]; // 听过一个数组，统计每个数字每一位的的情况
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32 && ((arr[i] >> j) != 0); j++) { // 填写t数组
                if (((arr[i] >> j) & 1) == 1) { // 当前位是1的情况，进行累加
                    t[j] += 1;
                }
            }
        }

        // 再结算t数组中的每一位
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) { // 不是m的整数倍，说明 求余数后就是 k
                res += (1 << i);
            }
        }
        return res;
    }

    // 测试方法
    public static int test(int[] arr, int k, int m) {
        if (arr == null || k > m) {
            return -1;
        }

        // 统计频率即可
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                map.put(num, 1 + map.get(num));
            }
        }

        Set<Integer> integers = map.keySet();
        for (int num : integers) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }
}
