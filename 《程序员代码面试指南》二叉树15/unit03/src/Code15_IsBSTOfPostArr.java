import java.io.*;
/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-09-11
 * Time: 19:25
 */

public class Code15_IsBSTOfPostArr {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());
        String[] nums = in.readLine().split(" ");
        int[] array = nextInt(nums, n);

        System.out.println(isPost(array, 0, n - 1));
    }


    public static boolean isPost(int[] nums, int left, int right) {
        if (left == right) {
            return true;
        }

        int less = -1; //左子树最右边
        int more = right; //右子树最左边
        for (int i = left; i < right; i++) {
            if (nums[i] < nums[right]) {
                less = i;
            } else {
                more = more == right? i : more; //确定右子树最左边
            }
        }

        if (less == -1 || more == right) {
            return isPost(nums, left, right - 1); //当前是左斜树，或右斜树
        }

        //两边的边界，相差不是一个单位，说明不对劲
        if (less != more - 1) {
            return false;
        }

        //分别判断左子树和右子树
        return isPost(nums, left, less) && isPost(nums, more, right - 1);
    }

    public static int[] nextInt(String[] nums, int n) {
        if (nums == null) {
            return null;
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = Integer.parseInt(nums[i]);
        }
        return res;
    }
}