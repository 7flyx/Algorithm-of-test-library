package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:28
 * Description:组合总和
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 
 * 中可以使数字和为目标数 target 的 所有不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。 
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code09_CombineSum {
    class Solution {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            if (candidates == null || candidates.length == 0) {
                return new ArrayList<>();
            }
            List<List<Integer>> res = new ArrayList<>();
            int[] count = new int[candidates.length]; //用这个数组，下标对于的是candidates，值就是用了几个数
            process(candidates, count, target, res,  0, 0);
            return res;
        }

        /**
         * @param candidates 可以用的数
         * @param count 统计每一个数，用了几个
         * @param target 目标值
         * @param res 返回结果
         * @param cur 当前的累加的值
         * @param index 对应candidates
         */
        private void process(int[] candidates, int[] count, int target, List<List<Integer>> res, int cur, int index) {
            if (index == candidates.length || cur > target) {
                if (cur == target) {
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < count.length; i++) {
                        for (int j = 0; j < count[i]; j++) {
                            list.add(candidates[i]); //将数据填写进去
                        }
                    }
                    res.add(list);
                }
                return;
            }

            for (int i = 0; i * candidates[index] + cur <= target; i++) {
                count[index] = i; //将要了几个数，填写进去
                process(candidates, count, target, res, i * candidates[index] + cur, index + 1); //当前位置的数，不要，或者要几个
            }
        }
    }
}
