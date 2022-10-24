package class42;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-22
 * Time: 10:11
 * Description:
 * 测试链接: https://leetcode.cn/problems/create-maximum-number/
 * 给两个长度分别为M和N的整型数组nums1和nums2，其中每个值都不大于9，
 * 再给定一个正数K。 你可以在nums1和nums2中挑选数字，要求一共挑选K个，
 * 并且要从左到右挑。返回所有可能的结果中，代表最大数字的结果
 */
public class Code03_CreateMaximumNumber {
    public static void main(String[] args) {
        int[] nums1 = {3, 4, 6, 5};
        int[] nums2 = {9, 1, 2, 5, 8, 3};
        int k = 5;
        System.out.println(Arrays.toString(maxNumber1(nums1, nums2, k)));
    }

    public static int[] maxNumber1(int[] nums1, int[] nums2, int K) {
        if (nums1 == null || nums2 == null || K < 0 || nums1.length + nums2.length < K) {
            return new int[]{};
        }
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[][] dp1 = getDp(nums1);
        int[][] dp2 = getDp(nums2);
        int[] ans = new int[K];
        // 挑选K个数，这K个数可能全部来自nums1，又或许全部来自nums2
        // 又或许一部分来自nums1，另外一部分来自nums2
        // leftGet,指的是从nums1中拿取的数字个数。
        for (int leftGet = Math.max(0, K - len2); leftGet <= Math.min(len1, K); leftGet++) {
            int[] left = maxPick(nums1, dp1, leftGet);
            int[] right = maxPick(nums2, dp2, K - leftGet);
            // 将左右两个数组进行合并
//            int[] tmp = merge(left, right); // 不用DC3优化，O(N^2)
            int[] tmp = mergeByDC3(left, right); // 用DC3优化，O(N)
            if (judgeArray(ans, tmp)) {
                ans = tmp;
            }
        }
        return ans;
    }

    // O(N^2)的时间复杂度
    private static int[] merge(int[] left, int[] right) {
        int[] ans = new int[left.length + right.length];
        int leftStart = 0;
        int rightStart = 0;
        for (int i = 0; i < ans.length; i++) {
            int l = leftStart;
            int r = rightStart;
            int pick = -1;
            while (l < left.length && r < right.length) {
                if (left[l] > right[r]) {
                    pick = left[leftStart++];
                    break;
                } else if (left[l] < right[r]) {
                    pick = right[rightStart++];
                    break;
                } else {
                    l++;
                    r++;
                }
            }
            if (pick != -1) { // 已经分出字典序，谁的字典序大，就先拷贝谁
                ans[i] = pick;
            } else { // 要么是字典序相等，要么就是有一个已经越界了
                ans[i] = l < left.length ? left[leftStart++] : right[rightStart++];
            }
        }
        return ans;
    }

    // 获取dp表。dp[i][j]表示在i~N-1范围内，拿取j个数，如何使其结果最优。存储的是nums对应的下标值
    private static int[][] getDp(int[] nums) {
        int size = nums.length;
        int pick = size + 1; // 挑选数字的个数。第0列表示挑选0个数组，没有意义，不需要填写
        // dp[i][j] 表示 从i~N-1范围的数字，挑选j个数字。dp[i][j]的值就是 第一个数字
        // 比如想要从6~10范围，挑选2个数字，组合成最大的结果。就是(dp[6][2] = j) + dp[j+1][1]这样的组合结果。这里是数组下标
        int[][] dp = new int[size][pick];
        // 从左往右，从下往上填写。一列一列的填写
        for (int j = 1; j < pick; j++) { // 挑选的数字个数
            int maxIndex = size - j;
            for (int i = size - j; i >= 0; i--) { //
                if (nums[i] >= nums[maxIndex]) { // 更新最好的结果
                    maxIndex = i;
                }
                dp[i][j] = maxIndex; // 填写当前位置最好的结果
            }
        }
        return dp;
    }

    // 在nums中拿pick个数，返回最大的结果
    private static int[] maxPick(int[] nums, int[][] dp, int pick) {
        int[] ans = new int[pick];
        for (int resIndex = 0, dpRow = 0; pick > 0; pick--, resIndex++) {
            ans[resIndex] = nums[dp[dpRow][pick]];
            dpRow = dp[dpRow][pick] + 1;
        }
        return ans;
    }

    private static boolean judgeArray(int[] ans, int[] tmp) {
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == tmp[i]) {
                continue;
            }
            if (ans[i] < tmp[i]) {
                return true;
            } else { // ans[i] > tmp[i]
                return false;
            }
        }
        return false; // 二者相等的情况
    }

    private static int[] mergeByDC3(int[] left, int[] right) {
        int len1 = left.length;
        int len2 = right.length;
        int[] all = new int[len1 + len2 + 1]; // 多一个位置出来，进行隔离
        // 本身数组中的数据都是>=0的，所以此处就不需要统计max和min。只需让全部的数据+2即可
        // 将全部的数据填入all数组中
        for (int i = 0; i < len1; i++) {
            all[i] = left[i] + 2;
        }
        all[len1] = 1; // 以全局最小值进行隔断
        for (int i = 0; i < len2; i++) {
            all[i + len1 + 1] = right[i] + 2;
        }
        // 生成DC3的rank数组。rank[i] = j。指的是以下标i开始的后缀字符串 排第j小
        DC3 dc3 = new DC3(all, 11); // 本身全局最大值是9，后续又+2了
        int[] rank = dc3.rank;
        int[] ans = new int[len1 + len2];
        int i = 0; // 指向left数组
        int j = 0; // 指向right数组
        int index = 0;// 指向ans数组
        while (i < len1 && j < len2) {
            ans[index++] = rank[i] > rank[j + len1 + 1] ? left[i++] : right[j++];
        }
        while (i < len1) {
            ans[index++] = left[i++];
        }
        while (j < len2) {
            ans[index++] = right[j++];
        }
        return ans;
    }

    private static class DC3 {
        public int[] sa; // sa[i] = j。 表示第i小的字符串是从j位置开始往后的
        public int[] rank; // rank[i] = j。表示从i开始往后的字符串，第j小。
        public int[] height; // height[i] = j。表示 第i小的后缀字符串 和 第i-1小的后缀字符串，二者最长的公共前缀串的长度

        // 构造方法的约定:
        // 数组叫nums，如果你是字符串，请转成整型数组nums
        // 数组中，最小值>=1
        // 如果不满足，处理成满足的，也不会影响使用
        // max, nums里面最大值是多少
        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
//            height = height(nums);
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
            for (int i = 0, k = 0; i < n; ++i) {
                if (rank[i] != 0) {
                    if (k > 0) {
                        --k;
                    }
                    int j = sa[rank[i] - 1];
                    while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
                        ++k;
                    }
                    ans[rank[i]] = k;
                }
            }
            return ans;
        }
    }
}
