package demo;
import java.io.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 16:30
 * Description:
 * 用螺旋的方式打印矩阵， 比如如下的矩阵
 * 0 1 2 3
 * 4 5 6 7
 * 8 9 10 11
 * 打印顺序为： 0 1 2 3 7 11 10 9 8 4 5 6
 */

public class Code03_RotatePrint {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        int m = Integer.parseInt(nums[1]);
        int[][] matrix = new int[n][m];

        for(int i = 0;i < n;i++){
            nums = in.readLine().split(" ");
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(nums[j]);
            }
        }

        System.out.println(rotatePrint(matrix));

        in.close();
    }

    public static String rotatePrint(int[][] matrix) {
        if (matrix == null) {
            return "";
        }

        //确定左上角和右下角的点，打印这两边所围着的矩阵
        int ar = 0;
        int ac = 0;
        int br = matrix.length - 1;
        int bc = matrix[0].length - 1;
        StringBuilder sb = new StringBuilder();
        while (ar <= br && ac <= bc) {
            print(matrix, sb, ar++, ac++, br--, bc--);
        }
        return sb.toString();
    }

    private static void print(int[][] martix, StringBuilder sb,
                              int ar, int ac, int br, int bc) {
        if (ar == br) { //只有这一行
            for (int i = ac; i <= bc; i++) {
                sb.append(martix[ar][i]).append(" ");
            }
        } else if (ac == bc) { //只有这一列
            for (int i = ar; i <= br; i++) {
                sb.append(martix[i][ac]).append(" ");
            }
        } else { //既有行，也有列
            int i = ar;
            int j = ac;
            while (j < bc) {
                sb.append(martix[ar][j]).append(" ");
                j++;
            }
            while (i < br) {
                sb.append(martix[i][bc]).append(" ");
                i++;
            }
            while (j > ac) {
                sb.append(martix[br][j]).append(" ");
                j--;
            }
            while (i > ar) {
                sb.append(martix[i][ac]).append(" ");
                i--;
            }
        }
    }
}
