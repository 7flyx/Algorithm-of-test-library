package class47;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-11-05
 * Time: 16:12
 * Description: LeetCode546题 移除盒子
 * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色，你将经过若干轮操作去去掉盒子
 * 直到所有的盒子都去掉为止，每一轮你可以移除具有相同颜色的连续k个盒子（k>= 1）
 * 这样一轮之后你将得到 k * k 个积分，当你将所有盒子都去掉之后，求你能获得的最大积分和
 * Leetcode题目：https://leetcode.cn/problems/remove-boxes/
 */
public class Code02_RemoveBoxes {
    public static void main(String[] args) {
        int[] boxes = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        int[] arr = {10, 8, 5, 1, 9, 6, 6, 9, 6, 10};
        System.out.println(removeBoxes(boxes)); // 暴力递归版本
        System.out.println(removeBoxes2(arr)); // 常规的dp版本
        System.out.println(removeBoxes3(arr)); // 优化常数项的递归版本
        System.out.println(removeBoxes4(arr)); // 优化常数项的dp版本
        System.out.println(removeBoxes5(arr)); // 优化常数项的记忆化搜索---最优解（不会计算出无用的一维数据，所以最快）
    }

    public static int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        return process(boxes, 0, boxes.length - 1, 0);
    }

    /**
     * @param boxes 带有颜色的盒子
     * @param L     左边界
     * @param R     右边界
     * @param K     在L位置往前，有连续K个boxes[L]颜色的盒子
     * @return 返回L~R范围的盒子，怎么消除，能获得最大得分。
     */
    private static int process(int[] boxes, int L, int R, int K) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return (K + 1) * (K + 1);
        }
        // 将前K个盒子 和 L位置的盒子，一起消除
        int way = process(boxes, L + 1, R, 0) + (K + 1) * (K + 1);
        for (int i = L + 1; i <= R; i++) {
            if (boxes[i] == boxes[L]) {
                int cur = process(boxes, L + 1, i - 1, 0) + process(boxes, i, R, K + 1);
                way = Math.max(way, cur);
            }
        }
        return way;
    }

    public static int removeBoxes2(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int N = boxes.length;
        int[][][] dp = new int[N][N][N + 1];
        // base case 是 L==R的时候
        for (int i = 0; i < N; i++) {
            for (int k = 0; k <= N; k++) {
                dp[i][i][k] = (k + 1) * (k + 1);
            }
        }
        // 填写普遍位置----从下往上填写
        for (int L = N - 2; L >= 0; L--) {  // 行
            for (int R = L + 1; R < N; R++) { // 列
                for (int k = 0; k < N; k++) { // 只能选到N-1个盒子，最后一个盒子在base case里已经处理了。
                    // 将前k个 和boxes[L]颜色相同的盒子消掉
                    dp[L][R][k] = dp[L + 1][R][0] + (k + 1) * (k + 1);
                    for (int i = L + 1; i <= R; i++) {
                        if (boxes[i] == boxes[L]) {
                            // (K个) L, (L+ 1 .... i - 1), i ...... R
                            int cur = dp[L + 1][i - 1][0] + dp[i][R][k + 1];
                            dp[L][R][k] = Math.max(dp[L][R][k], cur);
                        }
                    }
                }
            }
        }
        return dp[0][N - 1][0];
    }

    // 优化常数项的递归版本
    public static int removeBoxes3(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int N = boxes.length;
        return process3(boxes, 0, N - 1, 0);
    }

    private static int process3(int[] boxes, int L, int R, int K) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return (K + 1) * (K + 1);
        }
        int index = L;
        while (index + 1 <= R && boxes[index + 1] == boxes[L]) { // 找跟boxes[L]颜色相同的且还是连在一起的盒子
            K++;
            index++;
        }
        // 循环停下来时，index就来到了boxes[L]颜色最后的一个盒子上
        // 1、将这前K个盒子 和 index位置的盒子一起消除
        int ans = process3(boxes, index + 1, R, 0) + (K + 1) * (K + 1);
        // 2、枚举后续的每个盒子，看能否与L位置的盒子合在一起
        for (int i = index + 1; i <= R; i++) {
            // 找到与L位置相同的盒子，并且还只能找后续某一块区域的第一个。（因为可以进行压缩）
            if (boxes[i] == boxes[L] && boxes[i] != boxes[i - 1]) {
                // index - L 表示上面的while循环找到了多少个跟L位置相同颜色的盒子
                // 下述的K+1+index-L，这里的+1，指的是L位置本身就是1个盒子，不能把这盒子搞丢了
                int cur = process3(boxes, index + 1, i - 1, 0) + process3(boxes, i, R, K + 1);
                ans = Math.max(ans, cur);
            }
        }
        return ans;
    }

    // 优化常数项的dp版本---这个dp里面是个三维表，但是其中有一维K，是全部枚举了，导致速度并不快
    // 并且这个K只需要其中几个值，所以很多的K计算出来都没有使用到
    // 所以还是需要使用记忆化搜索，才是最优解
    public static int removeBoxes4(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int N = boxes.length;
        int[][][] dp = new int[N][N][N + 1];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k <= N; k++) {
                dp[i][i][k] = (k + 1) * (k + 1); // base case，L==R的时候
            }
        }
        // 填写普遍位置---从下往上填写
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                int index = L; // 这个index其实就是新的起点，应当做L的新开始点，后续的遍历都是在这个点的基础之上进行的
                while (index + 1 <= R && boxes[index + 1] == boxes[L]) { // 循环停下时，index指向boxes[L]颜色这一块的最后一个
                    index++;
                }
                for (int k = 0; k < N; k++) {
                    int newK = k + 1 + index - L;
                    // 将前k个 和 boxes[L]位置颜色相同的一起消除掉
                    dp[L][R][k] = (index + 1 < N ? dp[index + 1][R][0] : 0)
                            + (newK) * (newK);
                    for (int i = index + 1; i <= R; i++) { // 从index位置开始
                        if (boxes[i] == boxes[L] && boxes[i] != boxes[i - 1]) {
                            int cur = dp[index + 1][i - 1][0] + (newK <= N ? dp[i][R][newK] : 0);
                            dp[L][R][k] = Math.max(dp[L][R][k], cur);
                        }
                    }
                }
            }
        }

        return dp[0][N - 1][0];
    }

    // 优化常数项的记忆化搜索版本----最优解
    public static int removeBoxes5(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        return process5(boxes, 0, N - 1, 0, dp);
    }

    private static int process5(int[] boxes, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        if (L == R) {
            dp[L][R][K] = (K + 1) * (K + 1);
            return dp[L][R][K];
        }
        int index = L; // 相当于新的起点L，后续所有的计算都是在此基础之上，而不是在L上
        while (index + 1 <= R && boxes[index + 1] == boxes[L]) {
            index++;
        }
        int pre = K + index - L; // 新的K值
        // 将前K个 和当前index位置相同颜色的盒子一起消除掉
        int ans = process5(boxes, index + 1, R, 0, dp) + (pre + 1) * (pre + 1);
        // 枚举L+1~R范围内的所有盒子，
        for (int i = index + 1; i <= R; i++) {
            if (boxes[i] == boxes[L] && boxes[i] != boxes[i - 1]) {
                int cur = process5(boxes, index + 1, i - 1, 0, dp) + process5(boxes, i, R, pre + 1, dp);
                ans = Math.max(ans, cur);
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }
}
