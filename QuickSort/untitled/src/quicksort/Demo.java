package quicksort;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 20:24
 * Description: 测试类
 */
public class Demo {
    public static void main(String[] args) {
        int[] array1 = {10,60,50,20,5,2,6,90};
        QuickSort1.quickSort(array1); //挖坑法
        System.out.println("挖坑法: " + Arrays.toString(array1));

        int[] array2 = {22,11,33,65,24,98,20,5,2,3,1,5};
        QuickSort2.quickSort(array2); 
        System.out.println("随机取值法: " + Arrays.toString(array2));

        int[] array3 = {33,20,10,65,20,98,35};
        QuickSort3.quickSort(array3);
        System.out.println("三数取中法: " + Arrays.toString(array3));

        int[] array4 = {50,30,40,51,61,33,23,21,15};
        QuickSort4.quickSort(array4);
        System.out.println("荷兰国旗问题优化: " + Arrays.toString(array4));

        int[] array5 = {60,30,10,22,30,25,65,45,80};
        QuickSortNoRecur.quickSortNoRecur(array5);
        System.out.println("非递归版本: " + Arrays.toString(array5));
    }
}
