package class25;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-08
 * Time: 22:28
 * Description: LeetCode1504题 统计全1的子矩形个数。
 * https://leetcode.cn/problems/count-submatrices-with-all-ones/
 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的子矩形数量
 */
public class Code05_CountSubMatricesWithAllOnes {
    public static void main(String[] args) {
        int[][] mat = {{1, 1, 1}, {0, 1, 1}};
        System.out.println(numSubmat(mat));
    }
    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        // 做矩阵压缩，思路是
        // 以每一行作为当前矩阵的底，然后计算这一行的矩阵个数
        int N = mat.length;
        int M = mat[0].length;
        int ans = 0;
        int[] bottom = new int[M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) { // 矩阵压缩
                bottom[j] = mat[i][j] == 0? 0 : bottom[j] + 1;
            }
            ans += calcFromBottom(bottom);
        }
        return ans;
    }

    // 计算这一行为底，有多少个全1的子矩阵
    private static int calcFromBottom(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int[] stack = new int[heights.length]; // 单调递减栈，得到的是栈顶元素 左右两边都小于它的情况
        int si = 0;
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            while(si != 0 && heights[stack[si - 1]] >= heights[i]) {
                int cur = stack[--si];
                // 比如 左高 3   cur高7   右高5
                // 只需计算高度为7、6的矩阵，高度为5和3的，他们自己后面会结算
                if (heights[cur] > heights[i]) { // cur高和当前i高相等时，会留到后面i结算的时候进行计算
                    int left = si == 0? -1 : stack[si - 1]; // 左边柱子的下标
                    int n = i - left - 1; // 以cur高的圆柱的个数
                    int down = Math.max(left == -1? 0 : heights[left], heights[i]); // 左右两侧的柱子，取最高的情况
                    ans += (heights[cur] - down) * num(n);
                }
            }
            stack[si++] = i;
        }
        // 数组遍历完之后，处理栈中剩下的元素---此时可以理解为栈中每个圆柱，右侧都是比它高的
        while (si != 0) {
            int cur = stack[--si];
            int left = si == 0? -1 : stack[si - 1];
            int n = heights.length - left - 1; // 比cur高的，cur右侧全部都是比cur高的圆柱
            int down = left == -1? 0 : heights[left]; // 右侧没有柱子不用管，左侧还有柱子，需要用到
            ans += (heights[cur] - down) * num(n);
        }
        return ans;
    }

    private static int num(int n) {
        return n * (n + 1) / 2; // 等差数列求和公式
    }
}
