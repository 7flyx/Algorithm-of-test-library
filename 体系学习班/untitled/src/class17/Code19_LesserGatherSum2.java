package class17;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-23
 * Time: 13:25
 * Description:
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 */
public class Code19_LesserGatherSum2 {
    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 100, 1, 1, 1};
        System.out.println(lesserGatherSum1(arr));
        System.out.println(lesserGatherSum2(arr));
        System.out.println(lesserGatherSum3(arr));
    }

    public static int lesserGatherSum1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if ((N & 1) == 0) { // 偶数个的情况
            return process(arr, 0, N / 2, sum / 2);
        } else { // 奇数个的情况
            // 例如总共7个数，分为3个一组和4个一组
            // 二者都是计算，并判断，取最接近中间值的那个。
            return Math.max(process(arr, 0, N / 2 + 1, sum / 2),
                    process(arr, 0, N / 2, sum / 2));
        }
    }

    /**
     * @param arr   原数组
     * @param index 指向arr的下标
     * @param picks 剩余可选的数量
     * @param rest  还剩的总和数
     * @return 返回挑选picks个数，最接近rest的数值
     */
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            return picks == 0 ? 0 : -1; // 没有挑选够picks个数时，返回无效状态
        }
        int p1 = process(arr, index + 1, picks, rest); // 当前位置的数不要的情况
        int p2 = -1;
        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, picks - 1, rest - arr[index]);
        }
        if (next != -1) { // 说明后续状态有效
            p1 = next + arr[index];
        }
        return Math.max(p1, p2);
    }

    // 经典dp版本
    public static int lesserGatherSum2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int picks = (N + 1) / 2;
        // 第一个参数：arr长度，第二个参数：奇偶数需要折半，第三个参数：rest
        int[][][] dp = new int[N + 1][picks + 1][sum + 1];
        for (int pick = 0; pick <= picks; pick++) {
            for (int rest = 0; rest <= sum; rest++) {
                if (pick == 0) {
                    dp[N][pick][rest] = 0; // pick ==0的时候
                } else {
                    dp[N][pick][rest] = -1; // pick不是0的时候，都是-1
                }
            }
        }
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            for (int pick = 0; pick <= picks; pick++) {
                for (int rest = 0; rest <= sum; rest++) {
                    // 1、当前位置的数据，不要的情况
                    dp[i][pick][rest] = dp[i + 1][pick][rest];
                    // 2、当前位置的数据，要的情况
                    if (pick >= 1 && arr[i] <= rest &&
                            dp[i + 1][pick - 1][rest - arr[i]] != -1) {
                        dp[i][pick][rest] = Math.max(dp[i][pick][rest],
                                arr[i] + dp[i + 1][pick - 1][rest - arr[i]]);
                    }
                }
            }
        }
        if ((N & 1) == 0) { // 偶数的情况
            return dp[0][N / 2][sum];
        }
        // 奇数的情况
        return Math.max(dp[0][N / 2][sum], dp[0][picks][sum]);
    }

    // dp空间压缩
    public static int lesserGatherSum3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int picks = (N + 1) / 2; // 此处是 假设是偶数，就是折半的效果。假设是奇数，就是偶数折半 再+1
        //  三维数组，根据经典dp版本可知，每个位置只依赖于他下一层的数据
        // 而具体依赖下一层哪些位置，不是很好写，就用两个 二维数组交替使用来代替
        int[][] curDp = new int[picks + 1][sum + 1];
        int[][] preDp = new int[picks + 1][sum + 1];
        for (int pick = 0; pick <= picks; pick++) {
            for (int rest = 0; rest <= sum; rest++) {
                if (pick == 0) {
                    preDp[pick][rest] = 0;
                } else {
                    preDp[pick][rest] = -1;
                }
            }   
        }
        // 填写普遍位置
        for (int i = N - 1; i >= 0; i--) {
            for (int pick = 0; pick <= picks; pick++) {
                for (int rest = 0; rest <= sum; rest++) {
                    curDp[pick][rest] = preDp[pick][rest];
                    if (pick >= 1 && arr[i] <= rest &&
                            preDp[pick - 1][rest - arr[i]] != -1) {
                        curDp[pick][rest] = Math.max(curDp[pick][rest], arr[i] + preDp[pick - 1][rest - arr[i]]);
                    }
                }
                // 两个数组交替
                int[][] tmp = curDp;
                curDp = preDp;
                preDp = tmp;
            }
        }
        if ((N & 1) == 0) { // 偶数的情况
            return preDp[picks][sum];
        }
        // 奇数的情况
        return Math.max(preDp[picks][sum], preDp[picks - 1][sum]);
    }
}
