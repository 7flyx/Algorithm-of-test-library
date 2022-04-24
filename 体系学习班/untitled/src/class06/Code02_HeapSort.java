package class06;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-24
 * Time: 14:50
 * Description: 堆排序
 */
public class Code02_HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 5, 3, 2, 7, 9, 7};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 这样插入进入，这段代码的数据复杂度就是O(N*logN)
//        for (int i = 1; i < arr.length; i++) {
//            heapInsert(arr, i); // 先建立堆
//        }

        // 而建堆的过程，只是O(N)，所以从数组最后开始，进行建堆，会快一点
        for (int i = arr.length - 1; i >= 0; i--) {
            // 以当前位置为parent，向下调整
            heapify(arr, i, arr.length);
        }

        // 再弹出堆顶元素，放到数组的末尾--O(N*logN)
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i); // 与最后一个元素进行交换
            heapify(arr, 0, i); // 重新调整堆结构
        }
    }

    private static void heapInsert(int[] arr, int index) {
        while (index > 0 && arr[index] > arr[(index - 1) >> 1]) {
            swap(arr, index, (index - 1) >> 1);
            index = (index - 1) >> 1;
        }
    }

    // 将堆顶元素，尝试向下调整
    private static void heapify(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while (left < size) { // 左孩子节点还在size的范围内
            // 找左右孩子节点最大的
            int biggest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            if (arr[biggest] <= arr[index]) { // 建大堆，孩子节点干不过父节点，跳出循环
                break;
            }

            swap(arr, biggest, index);
            index = biggest;
            left = index * 2 + 1;
        }
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }
}


