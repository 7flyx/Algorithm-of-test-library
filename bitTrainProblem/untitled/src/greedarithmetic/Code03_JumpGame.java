package greedarithmetic;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:30
 * Description:跳跃游戏
 * https://leetcode-cn.com/problems/jump-game/
 */
public class Code03_JumpGame {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        return process1(nums);
    }

    //递归版本
    private boolean process(int[] nums, int index) {
        if(index == nums.length - 1) {
            return true;
        }
        if (index >= nums.length) {
            return false;
        }

        int limit = nums[index]; //当前位置能跳的步数
        for (int i = 1; i <= limit; i++) { //枚举每一步
            if (process(nums, index + i)) {//枚举
                return true;
            }
        }
        return false;
    }

    //最开始想的是dp，然后发现没必要建表，直接一个范围就能搞定
    private boolean process1(int[] nums) {
        int N = nums.length;
        int lastTrue = N - 1; //最后一个true的位置
        //从后往前遍历，有新的true，就更新，使之当前遍历的位置与下一个true最近
        for (int i = N - 1; i >= 0; i--) {
            int limit = nums[i];
            if (i + limit >= lastTrue) {//能够到达下一个true的位置
                lastTrue = i;
            }
        }
        return lastTrue == 0;
    }
}
