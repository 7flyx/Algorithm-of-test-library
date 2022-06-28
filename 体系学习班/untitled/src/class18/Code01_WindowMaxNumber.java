package class18;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-26
 * Time: 15:40
 * Description: 窗口最大值。
 * 假设一个固定大小为W的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 */
public class Code01_WindowMaxNumber {
    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int w = 3;
        System.out.println(Arrays.toString(windowMaxNumber(arr, w)));
    }

    public static int[] windowMaxNumber(int[] arr, int w) {
        if (arr == null || arr.length == 0 || arr.length < w) {
            return new int[]{};
        }
        int N = arr.length;
        int[] ans = new int[N - w + 1];
        int index = 0;
        LinkedList<Integer> queue = new LinkedList<>(); // 双端队列
        for (int i = 0; i < arr.length; i++) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[i]) { // 维持头部大，尾部小的结构
                queue.pollLast();
            }
            queue.addLast(i);
            if (i - queue.peekFirst() == w) {
                queue.pollFirst();
            }
            if (i >= w - 1) {
                ans[index++] = arr[queue.peekFirst()];
            }
        }
        return ans;
    }
}
