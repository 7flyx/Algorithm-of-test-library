package class38;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-14
 * Time: 20:32
 * Description:
 * 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]，背包容量为w，牛牛想知道在总体积不超过背包容量的情况下,
 * 一共有多少种零食放法，体积为0也算一种放法
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9，v[I] (0 <= v[i] <= 10^9）
 * （数组长度本身不大，但是每一项的数据量特别大。往分治方向想）
 *
 * 本文件是Code02_SnacksWays问题的牛客题目解答
 * 但是用的分治的方法
 * 这是牛客的测试链接：
 * https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 * 把如下的全部代码拷贝进编辑器（java）
 * 可以直接通过
 */
public class Code06_HoldSnacks {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int w = sc.nextInt();
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = sc.nextInt();
        }
//        int[] v = {1, 2, 4};
//        int w = 10;
        System.out.println(ways1(v, w));
    }

    public static long ways1(int[] v, int bag) {
        if (v == null) {
            return 0;
        }
        if (v.length == 1) {
            return v[0] <= bag ? 2 : 1;
        }
        int N = v.length;
        int mid = N / 2;
        TreeMap<Long, Long> map1 = new TreeMap<>();
        long left = process(v, 0, mid, 0, bag, map1);
        TreeMap<Long, Long> map2 = new TreeMap<>();
        long right = process(v, mid + 1, N - 1, 0, bag, map2);
        long ans = left + right;
        TreeMap<Long, Long> preMap = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : map2.entrySet()) {
            pre += entry.getValue();
            preMap.put(entry.getKey(), pre);
        }
        for (Map.Entry<Long, Long> entry : map1.entrySet()) {
            Long lWeight = entry.getKey(); //此处的key指的是 背包还已经用了多少空间
            Long lWays = entry.getValue();
            Long floor = preMap.floorKey(bag - lWeight); // 此时就是计算还剩下多少空间可以用
            if (floor != null) {
                ans += lWays * (preMap.get(floor));
            }
        }
        return ans + 1; // +1，是因为一个物品也不要的情况。背包是空的时候。
    }

    private static long process(int[] v, int index, int end, long sum, long w, TreeMap<Long, Long> map) {
        if (index == end + 1) {
//            if (map.containsKey(rest)) {
//                map.put(rest, map.get(rest) + 1);
//            } else {
//                map.put(rest, 1);
//            }
            if (sum == 0) { // 一个物品都没有拿的时候，后续单独计算
                return 0;
            }
            map.put(sum, map.getOrDefault(sum, 0L) + 1);
            return 1;
        }

        // 每个位置的物品，可以要或者不要。
        // 1、不要当前的物品
        long p1 = process(v, index + 1, end, sum, w, map);
        // 2、要当前的物品
        long p2 = 0;
        if (sum + v[index] <= w) {
            p2 = process(v, index + 1, end, sum + v[index], w, map);
        }
        return p1 + p2;
    }
}
