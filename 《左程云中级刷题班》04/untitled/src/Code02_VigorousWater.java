/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-13
 * Time: 19:55
 * Description:
 * 给定一个数组arr， 已知其中所有的值都是非负的， 将这个数组看作一个容器，
 * 请返回容器能装多少水
 * 比如， arr = {3， 1， 2， 5， 2， 4}， 根据值画出的直方图就是容器形状， 该容
 * 器可以装下5格水
 * 再比如， arr = {4， 5， 1， 3， 2}， 该容器可以装下2格水
 */
public class Code02_VigorousWater {
    public static void main(String[] args) {
        int[] array = {3, 1, 2, 5, 2, 4};
        System.out.println(calcVigorousWater(array));
        System.out.println(calcVigorousWater2(array));
    }

    /**
     * 根据数组的数据，可能得到相应的柱子的高度，柱子之间的高度差，形成的水坑
     * 就是盛水的地方。
     * 分析：相对于每一个柱子来说，可以向左右两边寻找max，两边的max都比当前
     * 柱子的高度大，说明当前位置是可以盛水的，高度则就是两边的max的最小值，再
     * 减去当前位置柱子的高度即可。从左到右遍历
     *
     * @param array 柱子的高度
     * @return 返回能盛多少格水
     */
    public static int calcVigorousWater(int[] array) {
        if (array == null || array.length < 3) {
            return 0;
        }

        //第一个位置和最后一个位置，不可能能盛水
        int res = 0;
        for (int i = 1; i < array.length - 1; i++) {
            int[] max = calcTwoMax(array, i);
            if (max[0] < array[i] || max[1] < array[i]) {
                continue;
            }
            res += Math.min(max[0], max[1]) - array[i];
        }
        return res;
    }

    //计算数组两边的最大值
    private static int[] calcTwoMax(int[] array, int index) {
        int[] res = {-1, -1};
        for (int i = 0; i < index; i++) {
            if (res[0] < array[i]) {
                res[0] = array[i];
            }
        }
        for (int i = index + 1; i < array.length; i++) {
            if (res[1] < array[i]) {
                res[1] = array[i];
            }
        }
        return res;
    }

    /**
     * 既然遍历数组，找左右两边的最大值，则还可以进行优化
     * 定义两个遍历，L,R。分别指向数组最前面和最后面
     * 因为最前面的柱子和最后面的柱子是无法盛水的，所以我们从第2个和倒数第2个，分别是L和R
     * 还有左边最大值和右边最大值。
     * 比如，左边max < 右边max。并且结算L位置
     * 右边max < 左边max，则结算R位置
     * @param array 柱子的高度
     * @return 能盛多少格水
     */
    public static int calcVigorousWater2(int[] array) {
        if (array == null || array.length < 3) {
            return 0;
        }

        int leftMax = array[0]; //第一个数据
        int rightMax = array[array.length - 1]; //最后一个数据
        int res = 0; //结果
        int L = 1; //第二个数据
        int R = array.length - 2; //倒数第2个数据
        while (L <= R) {
            if (leftMax < rightMax) { //结算L位置
                res += Math.max(leftMax - array[L], 0);
                leftMax = Math.max(leftMax, array[L]); //更新左边最大值
                L++;
            } else { //结算R位置
                res += Math.max(rightMax - array[R], 0);
                rightMax = Math.max(rightMax, array[R]);
                R--;
            }
        }
        return res;
    }
}
