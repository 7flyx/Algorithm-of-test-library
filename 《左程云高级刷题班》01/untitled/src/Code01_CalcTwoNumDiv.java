/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-09
 * Time: 20:50
 * Description:
 * 给定一个数组， 求如果排序之后， 相邻两数的最大差值。
 * 要求时间复杂度O(N)， 且要求不能用非基于比较的排序。
 */
public class Code01_CalcTwoNumDiv {
    public static void main(String[] args) {
        int[] arr = {1,3,5,2,7,8,9};
        System.out.println(calcTwoNumDiv(arr));
    }

    /**
     * 计算排序之后相邻两个数之间的差值，使其最大。
     * 但是时间复杂度O(N)，可能会想到计数、基数、桶排序。但是又明确限制了不能使用非基于比较的排序，
     * 分析：
     * 1. 遍历一遍数组，找到最大值和最小值。
     * 2. 再根据数组的元素个数N，建立N+1个桶
     * 3. 再用将min~max范围内的数，等分为N+1个桶，总共也就N个数，这里准备了N+1的桶，始终会多出来一个桶
     * 正是这多出来了一个桶，使其在所有的数据中，最大值肯定是大于这一个桶的容量的（等分的容量）。
     * 这就建立了一个平凡解，使其最终的答案不可能存在于同一个桶，只可能存在于不同桶之间
     * 然后前一个桶的最大值跟后一个桶的最小值，是相邻的数据，二者作差，作为res，
     * 循环上诉的res，遍历一遍所有的桶即可。
     * 而一个桶中，只需要存储max和min两个值。用两个数组表示即可（maxs，mins）
     * @param array 未排序的数据
     * @return 返回相邻两个数的最大差值
     */
    public static int calcTwoNumDiv(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i : array) { //先遍历一遍数组，求最大值最小值
            max = Math.max(max, i);
            min = Math.min(min, i);
        }
        if (max == min) {
            return 0; //如果最大值和最小值相等，说明这个数组只有这一个数据
        }
        int len = array.length;
        boolean[] hasNum = new boolean[len + 1];
        int[] maxs = new int[len + 1];
        int[] mins = new int[len + 1];
        //遍历数据
        for (int i = 0; i < array.length; i++) {
            int bit = getBit(array[i], len, max, min); //桶的位置
            if (!hasNum[bit]) {
                maxs[bit] = array[i];
                mins[bit] = array[i];
                hasNum[bit] =true;
            } else {
                maxs[bit] = Math.max(maxs[bit], array[i]);
                mins[bit] = Math.min(mins[bit], array[i]);
            }
        }

        //第一个桶和最后一个桶，肯定是有数据的。
        int preMax = maxs[0];
        int res = Integer.MIN_VALUE; //最终的结果
        for (int i = 1; i < len; i++) {
            if (hasNum[i]) {
                res = Math.max(res, mins[i] - preMax);
                preMax = maxs[i]; //更新前一个的最大值
            }
        }
        return res;
    }

    public static int getBit(int num, int len, int max, int min) {
        return  ((num - min) * len) / (max - min); //计算num应该存储在哪个桶
    }
}
