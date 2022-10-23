package class42;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-10-22
 * Time: 10:10
 * Description: 给定两个字符串str1和str2，想把str2整体插入到str1中的某个位置，形成最大的字典序，
 * 返回字典序最大的结果
 */
public class Code02_InsertS2MakeMostAlphabeticalOrder {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "def";
        System.out.println(makeMostAlphabeticalOrder(s1, s2));
        System.out.println(makeMostAlphabeticalOrder2(s1, s2));
    }

    public static String makeMostAlphabeticalOrder(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return "";
        }
        if (s1 == null) {
            return s2;
        }
        if (s2 == null) {
            return s1;
        }
        // 枚举每一种插入的情况即可
        String ans = "";
        int N = s1.length();
        for (int i = 0; i < N; i++) {
            String tmp = s1.substring(0, i) + s2 + s1.substring(i); // substring是左闭右开区间
            if (ans.compareTo(tmp) < 0) {
                ans = tmp;
            }
        }
        if (ans.compareTo(s1 + s2) < 0) {
            ans = s1 + s2;
        }
        return ans;
    }

    public static String makeMostAlphabeticalOrder2(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2;
        }
        if (s2 == null || s2.length() == 0) {
            return s1;
        }
        // 将s1和s2合并起来，去调用DC3算法，生成后缀数组
        int N = s1.length();
        int M = s2.length();
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        int[] all = new int[N + M + 1];
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, ch1[i]);
            min = Math.min(min, ch1[i]);
        }
        for (int i = 0; i < M; i++) {
            max = Math.max(max, ch2[i]);
            min = Math.min(min, ch2[i]);
        }
        int index = 0;
        for (int i = 0; i < N; i++) {
            all[index++] = ch1[i] - min + 2; // +2的原因是，数组中的所有值都是必须>1的，因为要以1作为全局最小值
        }
        all[index++] = 1; // 以1作为全局最小值，用于生成后缀数组的，进行隔离两个字符串
        for (int i = 0; i < M; i++) {
            all[index++] = ch2[i] - min + 2;
        }

        DC3 dc3 = new DC3(all, max - min + 2);
        int[] rank = dc3.rank;
        int comp = N + 1; // s2字符串的开始位置
        for (int i = 0; i < N; i++) { // 遍历，找到某个后缀字符串的字典序 比 s2的字典序小，就插这里
            if (rank[i] < rank[comp]) {
                int best = bestSplit(s1, s2, i);
                return s1.substring(0, best) + s2 + s1.substring(best); // substring是左开右闭区间
            }
        }
        return s1 + s2;
    }

    // 在first开始往后的范围内，进行尝试，找到那个最好的分割点。
    public static int bestSplit(String s1, String s2, int first) {
        int N = s1.length();
        int M = s2.length();
        int end = N;
        for (int i = first, j = 0; i < N && j < M; i++, j++) {
            if (s1.charAt(i) < s2.charAt(j)) { // s1的某个字符小于s2的字符，就记录下标，并退出
                end = i;
                break;
            }
        }
        String bestPrefix = s2;
        int bestSplit = first;
        for (int i = first + 1, j = M - 1; i <= end; i++, j--) {
            String curPrefix = s1.substring(first, i) + s2.substring(0, j);
            if (curPrefix.compareTo(bestPrefix) >= 0) {
                bestPrefix = curPrefix;
                bestSplit = i;
            }
        }
        return bestSplit;
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
