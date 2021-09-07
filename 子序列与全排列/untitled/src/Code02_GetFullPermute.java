import java.util.ArrayList;
import java.util.List;

/**
 * Created by flyx
 * Description:给定一个不含重复数字的数组 `nums` ，返回其 **所有可能的全排列** 。你可以 **按任意顺序** 返回答案
 * User: 听风
 * Date: 2021-09-07
 * Time: 11:51
 */
public class Code02_GetFullPermute {
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        }
        List<List<Integer>> res = new ArrayList<>();
        getFullPermute(nums, 0, res);
        return res;
    }

    private void getFullPermute(int[] nums, int i, List<List<Integer>> res) {
        if (i == nums.length) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int data : nums) {
                tmp.add(data);
            }
            res.add(tmp);
            return;
        }

        for (int j = i; j < nums.length; j++) {
            swap(nums, i, j);
            getFullPermute(nums, i + 1, res);
            swap(nums, i, j);
        }
    }

    private void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }
}
