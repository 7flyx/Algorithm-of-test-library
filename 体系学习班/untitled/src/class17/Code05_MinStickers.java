package class17;


import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-06-12
 * Time: 11:45
 * Description: 最少的贴纸数。 LeetCode 691题
 */
public class Code05_MinStickers {
    public static void main(String[] args) {
        String[] stickers = {"these", "guess", "about", "garden", "him"};
        String target = "atomher";
        System.out.println(minStickers2(stickers, target));
        System.out.println(minStickers3(stickers, target));
    }

    // 递归版本
    public int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return 0;
        }
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first); // 消除一定的字符
            if (rest.length() != target.length()) { // 确实是first消除了一些字符后，调用后续过程
                min = Math.min(min, process(stickers, rest));
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1; // 加上当前这一个位置的帖纸
    }

    // 根据str字符串，消除在target中的字符
    public static String minus(String target, String delete) {
        int[] count = new int[26];
        for (char ch : target.toCharArray()) {
            count[ch - 'a']++;
        }
        for (char ch : delete.toCharArray()) {
            count[ch - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            while (count[i]-- > 0) {
                sb.append((char) (i + 'a'));
            }
        }
        return sb.toString();
    }

    // 剪枝之后的递归版本
    public static int minStickers2(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return 0;
        }
        int N = stickers.length;
        int[][] stickers2 = new int[N][26]; // 用二维表存储所有的帖纸
        for (int i = 0; i < N; i++) {
            for (char ch : stickers[i].toCharArray()) {
                stickers2[i][ch - 'a']++;
            }
        }
        int ans = process2(stickers2, minus(target, ""));
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process2(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int[] count = new int[26];
        for (char ch : target.toCharArray()) {
            count[ch - 'a']++;
        }
        for (int i = 0; i < stickers.length; i++) {
            // 剪枝优化，当前递归，只需要解决target的第一个字符即可，后续的不用管，交给其他调用
            if (stickers[i][target.charAt(0) - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        int nums = count[j] - stickers[i][j]; // target 减掉 帖纸相应的字符
                        for (int k = 0; k < nums; k++) {
                            sb.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = sb.toString(); // 生成rest字符串
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1;
    }

    // 记忆化搜索版本--无法根据递归版本解出具体的dp表，因为递归函数的可变参数，无法计算出具体的参数范围
    // 又或者说，这个具体的参数范围大到已经没必要进行优化了，所有就用哈希表来搞记忆化搜索即可
    public static int minStickers3(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return 0;
        }
        int N = stickers.length;
        int[][] stickers2 = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char ch : stickers[i].toCharArray()) {
                stickers2[i][ch - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0); // base case
        int ans = process3(stickers2, target, dp);
        return ans == Integer.MAX_VALUE? -1 : ans;
    }

    public static int process3(int[][] stickers, String target, HashMap<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        int[] count = new int[26];
        for (char ch : target.toCharArray()) {
            count[ch - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            // 只递归调用 target字符串出现的第一个字符的情况
            if (stickers[i][target.charAt(0) - 'a'] > 0) { // 剪枝
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    int nums = count[j] - stickers[i][j]; // target 减去 帖纸的字符个数
                    for (int k = 0; k < nums; k++) {
                        sb.append((char)(j + 'a'));
                    }
                }
                String rest = sb.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        min = min == Integer.MAX_VALUE? min : min + 1;
        dp.put(target, min);
        return min;
    }
}
