package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-29
 * Time: 17:15
 * Description: 给定一个整数N，表示节点数。返回能形成多少种不同的二叉树结构
 */
public class Code04_GenericTreeStruct {
    public static void main(String[] args) {
        int N = 5;
        System.out.println(genericTreeStruct1(N));
        System.out.println(genericTreeStruct2(N));
    }

    public static int genericTreeStruct1(int N) {
        if (N < 0) return 0;
        if (N == 0) {
            return 1; //空树
        }
        if (N == 1) {
            return 1; //只有一个节点的时候
        }
        if (N == 2) {
            return 2;
        }

        int response = 0;
        for (int leftNum = 0; leftNum <= N - 1; leftNum++) { //控制左子树的节点数
            int left = genericTreeStruct1(leftNum);
            int right = genericTreeStruct1(N - 1 - leftNum); //需要减去根节点这个数
            response += left * right;
        }
        return response;
    }

    public static int genericTreeStruct2(int N) {
        if (N < 0) {
            return -1;
        }
        int[] dp = new int[N + 1];
        dp[0] = 1; //空树
        for (int i = 1; i <= N; i++) { //当前节总点数
            for (int j = 0; j <= i - 1; j++) { //左子树节点数
                dp[i] += dp[j] * dp[i - 1 - j];
            }
        }
        return dp[N];
    }
}
