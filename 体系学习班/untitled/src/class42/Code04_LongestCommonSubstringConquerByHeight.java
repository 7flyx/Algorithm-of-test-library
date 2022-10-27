package class42;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-22
 * Time: 10:11
 * Description:
 * 最长公共子串问题是面试常见题目之一，假设str1长度N，str2长度M
 * 一般在面试场上回答出O(N*M)的解法已经是比较优秀了
 * 因为得到O(N*M)的解法，就已经需要用到动态规划了
 * 但其实这个问题的最优解是O(N+M)，需要用到后缀数组+height数组
 * 课上将对本题解法代码进行详解
 */
public class Code04_LongestCommonSubstringConquerByHeight {
    public static void main(String[] args) {
        String str1 = "bcd";
        String str2 = "abced";
        System.out.println(longestCommonSubstringLength1(str1, str2));
        System.out.println(longestCommonSubstringLength2(str1, str2));
        System.out.println(longestCommonSubstringLength3(str1, str2));
        System.out.println(longestCommonSubstringLength4(str1, str2)); // DC3算法优化，时间复杂度O(N + M)
    }

    // 普通版的dp。时间复杂度O(N^2)
    public static int longestCommonSubstringLength1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        int N = str1.length();
        int M = str2.length();
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int[][] dp = new int[N][M];
        int ans = 0;
        for (int i = 0; i < N; i++) {
            dp[i][0] = ch1[i] == ch2[0] ? 1 : 0; // 第一列
            ans = Math.max(ans, dp[i][0]);
        }
        for (int j = 0; j < M; j++) {
            dp[0][j] = ch1[0] == ch2[j] ? 1 : 0; // 第一行
            ans = Math.max(ans, dp[0][j]);
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                dp[i][j] = ch1[i] == ch2[j] ? 1 + dp[i - 1][j - 1] : 0;
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    // 一维空间压缩版本的dp。 时间复杂度O(N^2)
    public static int longestCommonSubstringLength2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int max = Math.max(ch1.length, ch2.length);
        int min = Math.min(ch1.length, ch2.length);
        char[] longStr = ch1.length == max ? ch1 : ch2;
        char[] shortStr = ch2.length == min ? ch2 : ch1;
        int[] dp = new int[min];
        int ans = 0;
        for (int j = 0; j < min; j++) {
            dp[j] = longStr[0] == shortStr[j] ? 1 : 0; // 第一行
            ans = Math.max(ans, dp[j]);
        }
        // 填写普遍位置
        for (int i = 1; i < max; i++) {
            int leftUp = dp[0];
            dp[0] = longStr[i] == shortStr[0] ? 1 : 0;
            for (int j = 1; j < min; j++) {
                int up = dp[j];
                dp[j] = longStr[i] == shortStr[j] ? leftUp + 1 : 0;
                leftUp = up;
                ans = Math.max(ans, dp[j]);
            }
        }
        return ans;
    }

    // 空间复杂度O(1)的dp
    public static int longestCommonSubstringLength3(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int row = 0;
        int col = ch2.length - 1;
        int ans = 0;
        // 整体从右上角的格子开始，以“斜线”的方式进行遍历
        while (row < ch1.length) {
            int i = row; // 指向str1
            int j = col; // 指向str2
            int length = 0;
            while (i < ch1.length && j < ch2.length) {
                if (ch1[i] == ch2[j]) {
                    length++;
                } else {
                    ans = Math.max(ans, length);
                    length = 0;
                }
                i++;
                j++;
            }
            ans = Math.max(ans, length);
            // 控制row和col的走向
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return ans;
    }

    public static int longestCommonSubstringLength4(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int[] all = new int[ch1.length + ch2.length + 1];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < ch1.length; i++) {
            max = Math.max(max, ch1[i]);
            min = Math.min(min, ch1[i]);
        }
        for (int j = 0; j < ch2.length; j++) {
            max = Math.max(max, ch2[j]);
            min = Math.min(min, ch2[j]);
        }
        int index = 0;
        for (int i = 0; i < ch1.length; i++) {
            all[index++] = ch1[i] - min + 2;
        }
        all[index++] = 1;
        for (int j = 0; j < ch2.length; j++) {
            all[index++] = ch2[j] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int[] rank = dc3.rank;
        int[] height = dc3.height;
        int ans = 0;
        for (int i = 1; i < all.length; i++) {
            int X = rank[i - 1];
            int Y = rank[i];
            if (Math.min(X, Y) < ch1.length && Math.max(X, Y) > ch1.length) { // X 和Y分别在ch1和ch2，就取height即可
                ans = Math.max(ans, height[i]); // height[i] 就表示i下标开始的后缀字符串 与 i-1下标开始的后缀字符串的 最长公共前缀
            }
        }
        return ans;
    }

    public static class DC3 {

        public int[] sa;

        public int[] rank;

        public int[] height;

        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
            height = height(nums);
        }

        private int[] sa(int[] nums, int max) {
            int n = nums.length;
            int[] arr = new int[n + 3];
            for (int i = 0; i < n; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, n, max);
        }

        private int[] skew(int[] nums, int n, int K) {
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
                if (0 != i % 3) {
                    s12[j++] = i;
                }
            }
            radixPass(nums, s12, sa12, 2, n02, K);
            radixPass(nums, sa12, s12, 1, n02, K);
            radixPass(nums, s12, sa12, 0, n02, K);
            int name = 0, c0 = -1, c1 = -1, c2 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                    name++;
                    c0 = nums[sa12[i]];
                    c1 = nums[sa12[i] + 1];
                    c2 = nums[sa12[i] + 2];
                }
                if (1 == sa12[i] % 3) {
                    s12[sa12[i] / 3] = name;
                } else {
                    s12[sa12[i] / 3 + n0] = name;
                }
            }
            if (name < n02) {
                sa12 = skew(s12, n02, name);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, j = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[j++] = 3 * sa12[i];
                }
            }
            radixPass(nums, s0, sa0, 0, n0, K);
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                        : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                    sa[k] = i;
                    t++;
                    if (t == n02) {
                        for (k++; p < n0; p++, k++) {
                            sa[k] = sa0[p];
                        }
                    }
                } else {
                    sa[k] = j;
                    p++;
                    if (p == n0) {
                        for (k++; t < n02; t++, k++) {
                            sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                        }
                    }
                }
            }
            return sa;
        }

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
            int[] cnt = new int[k + 1];
            for (int i = 0; i < n; ++i) {
                cnt[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < cnt.length; ++i) {
                int t = cnt[i];
                cnt[i] = sum;
                sum += t;
            }
            for (int i = 0; i < n; ++i) {
                output[cnt[nums[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

        private int[] rank() {
            int n = sa.length;
            int[] ans = new int[n];
            for (int i = 0; i < n; i++) {
                ans[sa[i]] = i;
            }
            return ans;
        }

        private int[] height(int[] s) {
            int n = s.length;
            int[] ans = new int[n];
            // 依次求h[i] , k = 0
            for (int i = 0, k = 0; i < n; ++i) {
                if (rank[i] != 0) {
                    if (k > 0) {
                        --k; // 回退一格
                    }
                    int j = sa[rank[i] - 1];
                    while (i + k < n && j + k < n && s[i + k] == s[j + k]) { // 这里的k指的是公共前缀
                        ++k;
                    }
                    // h[i] = k
                    ans[rank[i]] = k;
                }
            }
            return ans;
        }
    }
}
