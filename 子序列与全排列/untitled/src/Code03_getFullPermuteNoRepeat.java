import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by flyx
 * Description: 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * User: 听风
 * Date: 2021-09-07
 * Time: 14:55
 */
public class Code03_getFullPermuteNoRepeat {

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        }
        List<List<Integer>> res = new ArrayList<>();
        getFullPermute(nums, 0, res); //递归调用子过程
        return res;
    }

    //nums， 是所有的数据。
//nums[0 ... i - 1] 范围内，是已经确定好的数据
//nums[i...] 范围上的数据，就是上图中，绿色框里面剩下的数据，还是待选择的状态
//res，就是最后的结果
    private void getFullPermute(int[] nums, int i, List<List<Integer>> res) {
        if (i == nums.length) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int data : nums) {
                tmp.add(data);
            }
            res.add(tmp);
            return;
        }

        HashSet<Integer> visited = new HashSet<>(); //用于存储数值，来没来过这个位置
        //循环遍历剩下的数据
        for (int j = i; j < nums.length; j++) {
            if(!visited.contains(nums[j])) {
                visited.add(nums[j]); //添加已经来过这个位置的数据
                swap(nums, i, j); //在剩下的数据中，选取一个，放到当前i位置，然后去做递归

                getFullPermute(nums, i + 1, res);

                //交换之后，也应交换回来，保证后面的递归过程不乱序
                //比如：26行数据交换之前： 0 ~ i-1 ： 123 ；     i~末尾： 456
                //26行交换之后： 0 ~ i-1 ： 123 ；              i~末尾： 546
                //然后要将刚才交换的数据，重新交换回来：546 -》 456
                //然后才去交换 4 和 6 这两个数据；  也就是  有无后效性的问题
                swap(nums, i, j);
            }
        }
    }

    private void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }
}
