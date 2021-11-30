package demo;

import java.util.Arrays;

/**
 * 给定一个二维数组，表示一个矩阵，
 * 计算子矩阵的最大累加和。
 *
 * @author Administrator
 */
public class Code07_SubRecMaxSum {
    public static void main(String[] args) {
        int[][] arr = {
                {-90, 48, 78},
                {64, -40, 64},
                {-81, -7, 66}
        };

        System.out.println(calcSubRecMaxSum(arr));
    }

    public static int calcSubRecMaxSum(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = 0;
        int[] tmp = new int[arr[0].length];
        for (int i = 0; i < arr.length; i++) { //以当前行最为起点
            Arrays.fill(tmp, 0);
            for (int j = i; j < arr.length; j++) { //以第i行作为起点，形成的子矩阵
                calcSumOfArray(arr, tmp, j); //每一行做叠加
                //计算tmp这个子数组的最大累加和
                max = Math.max(calcSubArrayMaxSum(tmp), max);
            }
        }
        return max;
    }

    //计算子数组的最大累加和
    public static int calcSubArrayMaxSum(int[] tmp) {
        int sum = 0;
        int max = 0;
        for (int I : tmp) {
            sum += I;
            max = Math.max(max, sum);
            sum = sum < 0 ? 0 : sum;
        }
        return max;
    }

    public static void calcSumOfArray(int[][] array, int[] tmp, int index) {
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] += array[index][i];
        }
    }
}
