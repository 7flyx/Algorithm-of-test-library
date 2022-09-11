package demo;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-09-08
 * Time: 20:01
 * Description:2022.9.8 腾讯音乐笔试 第3题
 * 给定一个只包含小写字母字符串，每次可以选择两个相同的字符删除，开在字符串结尾新增任惑一个小写字母.请问最少多少次操作后,
 * 所有的字母都不相同?
 */
public class Code03_MinOptionNum {
    public static void main(String[] args) {
        String str = "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzzzz";
        String str2 = "aabbccddeeffgghhiijjkkll";
        String str3 = "aabbccddee";
        String str4 = "hfilashflaisjflasflahsflhasadljfksjdaljsdjajsdlkjasqowiruqoiwruoihfebfjbwbfwopebfpwenp";
        String str5 = "abcdefghigklmnopqrstuvwxyzaaa";
        System.out.println(minOptionNum2(str4));
//        System.out.println(minOperations(str4));
    }

    public static int minOptionNum(String str) {
        if (str == null || str.length() <= 1) {
            return 0;
        }
        int[] counts = new int[26];
        Queue<Character> queue = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            counts[ch - 'a']++;
            if (counts[ch - 'a'] == 2) {
                queue.add(ch);
            }
        }
        int ans = 0; // 擦作次数
        while (!queue.isEmpty()) {
            ans++;
            char ch = queue.peek();
            counts[ch - 'a'] -= 2; // 删除字母
            if (counts[ch - 'a'] < 2) {
                queue.poll();
            }
            int offset = -1;
            boolean flag = false;
            for (int i = 0; i < 26; i++) {
                if (counts[i] == 0) {
                    counts[i]++;
                    flag = true;
                    break;
                }
                // 插入的时候，是挑的词频为0 的插入。有可能整个数组根本就没有词频为0的字母
                // 所以接下来要挑所有词频最小，且还是偶数次的。
                // 因为原先是偶数次，+1后，变为奇数次。并不会造成新的option
                if (counts[i] % 2 == 0 && (offset == -1 || counts[offset] > counts[i])) {
                    offset = i;
                }
            }
            // 没插入进去的情况
            // 有可能全是奇数次的词频，则挑词频最小的即可。也就是offset=-1的时候
            if (!flag) {
                if (offset == -1) {
                    offset = 0;
                    for (int i = 1; i < 26; i++) {
                        if (counts[offset] > counts[i]) {
                            offset = i;
                        }
                    }
                }
                counts[offset]++;
                if (counts[offset] == 2) {
                    queue.add((char) (offset + 'a'));
                }
            }
        }
        return ans;
    }

    public static int minOptionNum2(String str) {
        if (str == null || str.length() <= 1) {
            return 0;
        }

        int[] counts = new int[26];
        for (char ch : str.toCharArray()) {
            counts[ch - 'a']++;
        }
        process(counts, 0, 0);
        return ans;
    }

    private static int ans = Integer.MAX_VALUE;

    private static int process(int[] counts, int options, int index) {
        if (options >= ans) {
            return options;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (counts[i] >= 2) {
                counts[i] -= 2;
                for (int j = 0; j <= index; j++) { // 枚举累加 每一种字符的情况
                    counts[j] += 1;
                    res = Math.min(res, process(counts, options + 1, i));
                    counts[j] -= 1;
                }
                counts[i] += 2;
                break;
            }
        }
        if (res == Integer.MAX_VALUE) { // 说明counts数组都是不超过2的词频
            ans = Math.min(options, ans);
        }
        return res;
    }

}
