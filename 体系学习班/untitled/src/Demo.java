import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-07-24
 * Time: 22:44
 * Description:
 */
public class Demo {
    public static void main1(String[] args) {
        Solution solution = new Solution();
        String s = solution.minWindow("a", "b");
        System.out.println(s);
//        int k = new String("a");
//        String s = String("1");
        long j = 8888;
        char c = 74;
    }

    static class Solution {
        public String minWindow(String s, String t) {
            if (s == null || t == null) {
                return "";
            }
            char[] ch1 = s.toCharArray();
            char[] ch2 = t.toCharArray();
            int length = ch2.length;
            int[] counts = new int[128];
            boolean[] flags = new boolean[128];
            for (int i = 0; i < length; i++) {
                counts[ch2[i]]++;
                flags[ch2[i]] = true;
            }
            int left = 0;
            int right = 0;
            String ans = "";
            while (right < ch1.length || length == 0) {
                if (length > 0) { // t字符串还有字符的情况
                    if (flags[ch1[right]]) {
                        if (counts[ch1[right]] > 0) { // 当前t有这个字符，总长度-1
                            length--;
                        }
                        counts[ch1[right]]--;
                    }
                    right++;
                } else if (length == 0) {
                    if (ans.length() == 0) { // ans还没答案的时候
                        ans = s.substring(left, right);
                    } else if (right - left < ans.length()) { // 发现了比ans更短的子串
                        ans = s.substring(left, right);
                    }
                    if (flags[ch1[left]]) {
                        counts[ch1[left]]++;
                        if (counts[ch1[left]] > 0) { // 当前恢复left位置字符，使得counts数组大于0了，则总长度+1
                            length++;
                        }
                    }
                    left++;
                }
            }
            return ans;
        }
    }

    public static void main2(String[] args) throws IOException {
        File file = new File("C:/Users/Administrator/Desktop/单词1.txt/");
        FileInputStream inputStream = new FileInputStream(file);

        File file1 = new File("C:/Users/Administrator/Desktop/单词.txt/");
        FileOutputStream outputStream = new FileOutputStream(file1);

        int read = 0;
        StringBuilder sb = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        boolean flag = true; // 是不是单词
        while ((read = inputStream.read()) != -1) {
            if (read == '\n' && flag) {
                if (!set.contains(sb.toString())) {
                    set.add(sb.toString());
                    outputStream.write(sb.append("\n").toString().getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
                sb.delete(0, sb.length());
            } else if (read == '\n' && !flag) {
                sb.delete(0, sb.length());
                flag = true;
            } else if (flag && read >= 'a' && read <= 'z') {
                sb.append((char) (read));
            } else if (read != '\r') { // 是中文的情况
                flag = false;
            }
        }
        outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        outputStream.close();
        inputStream.close();
    }


    public static void main(String[] args) {

    }

    static int method(person s) {
        s.name = "jjj";
        return 0;
    }


}
class person {
    String name = "jjj";
}
