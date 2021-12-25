package dynamicarithmetic;

import java.util.ArrayList;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:15
 * Description:
 * https://www.nowcoder.com/practice/2b7995aa4f7949d99674d975489cb7da?tpId=46&tqId=29060&tPage=2&rp=2&ru=/ta/leetcode&qru=/ta/leetcode/question-rankin
 */
public class Code03_Triangle {
    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        if (triangle == null) {
            return 0;
        }
        return process3(triangle);
    }

    //暴力递归
    private int process(ArrayList<ArrayList<Integer>> triangle, int floor, int index) {
        int left = 0;//左下角的值
        int right = 0;//右下角的值
        if (floor + 1 != triangle.size()) {
            left = process(triangle,floor + 1, index);
            right = process(triangle, floor + 1, index + 1);
        }
        return Math.min(left, right) + triangle.get(floor).get(index);
    }

    //经典的dp
    private int process2(ArrayList<ArrayList<Integer>> triangle) {
        int row = triangle.size();
        int col = row; //行数和列数是相同的
        int[][] dp = new int[row][col];
        for (int i = 0; i < col; i++) { //填写最后一行的数据
            dp[row - 1][i] = triangle.get(row - 1).get(i);
        }

        for (int i = row - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }

    //dp压缩
    private int process3(ArrayList<ArrayList<Integer>> triangle) {
        int row = triangle.size();
        int col = row; //行数和列数是相同的
        int[] dp = new int[row];
        for (int i = 0; i < col; i++) { //填写最后一行的数据
            dp[i] = triangle.get(row - 1).get(i);
        }

        for (int i = row - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (j + 1 < col && dp[j + 1] < dp[j]) {
                    dp[j] = dp[j + 1];
                }
                dp[j] += triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
