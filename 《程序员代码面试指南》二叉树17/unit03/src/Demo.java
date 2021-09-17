import java.io.*;

/**
 *  矩阵的最小路径和，动态规划
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);

        String[][] array = new String[n][];
        for (int i = 0; i < n; i++) {
            array[i] = in.readLine().split(" ");
        }

        System.out.println(getMinPathSum2(array));

        in.close();
    }

    //暴力递归版本
    public static int getMinPathSum1(String[][] array, int i, int j) {
        if (i == array.length || j == array[0].length) {
            return 0;
        }

        int curNum = Integer.parseInt(array[i][j]);
        int rightDate = getMinPathSum1(array, i, j + 1);
        int belowDate = getMinPathSum1(array, i + 1, j);

        return Math.min(rightDate, belowDate) + curNum;
    }

    public static int getMinPathSum2(String[][] array) {
        if (array == null) {
            return 0;
        }
        int row = array.length;
        int col = array[0].length;

        //两个可变参数，行和列，建二维数组作为缓存
        int[][] res = new int[row][col];
        res[row - 1][col - 1] = Integer.parseInt(array[row - 1][col - 1]);
        //填写最后一行和最右的一列数
        for (int i = row - 2; i >= 0; i--) { //最右一列
            res[i][col - 1] = res[i + 1][col - 1] + Integer.parseInt(array[i][col - 1]);
        }
        for (int j = col - 2; j >= 0; j--) { //最后一行
            res[row - 1][j] = res[row - 1][j + 1] + Integer.parseInt(array[row - 1][j]);
        }

        for (int i = row - 2; i >= 0; i--) { //倒数第二行
            for (int j = col - 2; j >= 0; j--) { //倒数第二列
                //从右边和下边拿数据，取最小值
                res[i][j] = Math.min(res[i + 1][j], res[i][j + 1]) + Integer.parseInt(array[i][j]);
            }
        }
        return res[0][0];
    }

}
