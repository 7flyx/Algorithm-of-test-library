import java.util.Scanner;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-13
 * Time: 20:38
 * Description:
 * 给定一个数组arr长度为N， 你可以把任意长度大于0且小于N的前缀作为左部分， 剩下的
 * 作为右部分。 但是每种划分下都有左部分的最大值和右部分的最大值， 请返回最大的，
 * 左部分最大值减去右部分最大值的绝对值。
 */
public class Code03_SplitArrayCalcMax {


    /**
     *  因为每次都是需要计算左右两部分的最大值的，并且题目是需要求整体分割后max的差值
     *  分析：可以先求整个数组的全局最大值。这样的话，可以站在全局最大值的角度，将数组划分为两个部分。
     *  一、要么全局最大值在左边，则只需找右部分的最大值，即可
     *  二、要么全局最大值在右边，则只需找左部分的最大值，即可
     *  但是，在求解左右部分的最大值的时候，分为这两种情况：
     *       1. 范围（最左 …… 全局max-1），假设最左位置的数据就是左部分最大值，则只需全局减去最左下标的数据
     *       假设，左部分的最大值，是在省略号位置，那么要想求最后的答案，只能将省略号划分为右部分，因为全局max的存在，怎么划分都不会影响
     *       全局max。
     *       综上：真正影响结果的就是最左边的值。这是瓶颈。右边也是如此
     * @param arr
     * @return 返回分割后的最大值的差值
     */
    public static int calcMax(int[] arr) {
        if (arr == null || arr.length < 1) {
            return 0;
        }

        int max = 0; //全局最大值
        for(int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        //全局最大值分别减去第一个数和最后一个数即可
        return Math.max(Math.abs(max - arr[0]),
                Math.abs(max - arr[arr.length - 1]));
    }
}
