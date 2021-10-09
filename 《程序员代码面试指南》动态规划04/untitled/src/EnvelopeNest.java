import java.util.*;
import java.io.*;

/**
 * Created by IDEA
 * User: 听风
 * Date: 2021-10-09
 * Time: 18:06
 * Description: 信封嵌套问题
 *          给n个信封的长度和宽度。如果信封A的长和宽都小于信封B，
 *          那么信封A可以放到信封B里，请求出信封最多可以嵌套多少层
 */

public class EnvelopeNest {

    static int[] dp; //这些数据，需要静态的。在计算dp的时候，避免重复计算
    static int[] ends;
    static int right = 0;
    static int l, m, r, res = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        Node[] arr = new Node[n];
        for (int i = 0; i < n; i++) {
            String[] str = in.readLine().split(" ");
            Node node = new Node(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
            arr[i] = node;
        }
        Arrays.sort(arr, new LengthCompare()); //排序

        ends = new int[n];
        dp = new int[n];
        dp[0] = 1;
        ends[0] = arr[0].wid; //以宽度进行judge。最长递增子序列问题
        int max = -1;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, getMaxNum(arr, i));
        }
        System.out.println(max);
    }

    private static class Node {
        public int len;
        public int wid;

        public Node(int len, int wid) {
            this.len = len;
            this.wid = wid;
        }
    }

    private static class LengthCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            //以长度为第一要素；长度一样时，宽度要降序。
            //计算了宽度最大的时候，后面宽度小的就没必要计算
            return o1.len != o2.len ? o1.len - o2.len : o2.wid - o1.wid;
        }
    }

    public static int getMaxNum(Node[] nodes, int limit) {
        l = 0;
        r = right;
        while (l <= r) {
            m = (l + r) / 2;
            if (nodes[limit].wid > ends[m]) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        right = Math.max(right, l);
        dp[limit] = l + 1;
        ends[l] = nodes[limit].wid;
        res = Math.max(res, dp[limit]);
        return res;
    }

}
