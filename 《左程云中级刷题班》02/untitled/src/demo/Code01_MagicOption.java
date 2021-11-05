package demo;

import java.util.HashSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-04
 * Time: 21:21
 * Description:
 * 给一个包含n个整数元素的集合a， 一个包含m个整数元素的集合b。
 * 定义magic操作为， 从一个集合中取出一个元素， 放到另一个集合里， 且操作过
 * 后每个集合的平均值都大大于于操作前。
 * 注意以下两点：
 * 1） 不可以把一个集合的元素取空， 这样就没有平均值了
 * 2） 值为x的元素从集合b取出放入集合a， 但集合a中已经有值为x的元素， 则a的
 * 平均值不变（因为集合元素不会重复） ， b的平均值可能会改变（因为x被取出
 * 了）
 * 问最多可以进行多少次magic操作？
 */
public class Code01_MagicOption {
    public static void main(String[] args) {
        int[] a = {1, 2, 5};
        int[] b = {2, 3, 4, 5, 6};
        System.out.println(calcMagicOption(a, b));
    }

    /**
     * 保证a数组和b数组的元素都没有重复值。
     * 若两集合平均值相同，则无法magic
     * 若不同，也只能从平均值大的，往平均值小的里忙放
     *
     * @param a a数组不为空，且没有重复值
     * @param b b数组不为空，且没有重复值
     * @return magic操作次数
     */
    public static int calcMagicOption(int[] a, int[] b) {
        if (a == null || b == null || a.length < 2 || b.length < 2) {
            return 0;
        }

        double sumA = 0.0;
        double sumB = 0.0;
        for (int i = 0; i < a.length; i++) {
            sumA += (double) a[i];
        }
        for (int i = 0; i < b.length; i++) {
            sumB += (double) b[i];
        }
        if (avg(sumA, a.length) == avg(sumB, b.length)) {
            return 0;
        }

        int[] arrMore = null;
        int[] arrLess = null;
        double sumMore = 0.0;
        double sumLess = 0.0;
        if (avg(sumA, a.length) < avg(sumB, b.length)) {
            arrMore = b;
            arrLess = a;
            sumMore = sumB;
            sumLess = sumA;
        } else {
            arrMore = a;
            arrLess = b;
            sumMore = sumA;
            sumLess = sumB;
        }

        //将平均值小的所有数据，放入set
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arrLess.length; i++) {
            set.add(arrLess[i]);
        }

        int lenMore = arrMore.length;
        int lenLess = arrLess.length;
        int opt = 0; //最后答案
        //遍历数组
        for (int i = 0; i < arrMore.length; i++) {
            double cur = (double) arrMore[i];
            //在两个平均值的范围内，且另外一个数组中没有这个数字
            if (cur > avg(sumLess, lenLess) &&
                    cur < avg(sumMore, lenMore) && !set.contains(arrMore[i])) {
                opt++;
                lenLess++;
                lenMore--;
                sumMore -= cur;
                sumLess += cur;
                set.add(arrMore[i]);
            }
        }
        return opt;
    }

    private static double avg(double sum, int sz) {
        return sum / (double) sz;
    }
}
