import java.io.*;
/**
 * Created by flyx
 * Description: CD140 字典树
 * User: 听风
 * Date: 2021-09-06
 * Time: 16:27
 */
public class Code11_TrieTree {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(in.readLine());
        TrieTree root = new TrieTree();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            String[] nums = in.readLine().split(" ");
            String option = nums[0];
            String word = nums[1];
            if (option.equals("1")) { //insert
                root.insert(word);
            } else if (option.equals("2")) { //delete
                root.delete(word);
            } else if (option.equals("3")) { //search
                boolean res = root.search(word);
                sb.append(res ? "YES":"NO").append("\n");
            } else if (option.equals("4")) { //prefixNumber
                sb.append(root.prefixNumber(word)).append("\n");
            } else {
                throw new RuntimeException("option is illegality.");
            }
        }
        System.out.println(sb.toString());
        in.close();
    }

    private static class TrieNode {
        public int pass;
        public int end;
        public TrieNode[] nexts; //存储数值的，因为只有26个小写字母，用数组快一点
        public TrieNode() {
            nexts = new TrieNode[26];
        }
    }

    public static class TrieTree {
        private final TrieNode root;

        public TrieTree() {
            root = new TrieNode();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            TrieNode node = root;
            char[] array = word.toCharArray();
            node.pass++;
            int length = array.length;
            for (int i = 0; i < length; i++) {
                int index = array[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new TrieNode();
                }
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (word != null && search(word)) {
                TrieNode node = root;
                root.pass--;
                char[] array = word.toCharArray();
                int length = array.length;
                for (int i = 0; i < length; i++) {
                    int index = array[i] - 'a';
                    if (node.nexts[index].pass-- == 1) {
                        node.nexts[index] = null;
                        return;
                    }
                    node = node.nexts[index];
                }
                node.end--;
            }
        }

        public boolean search(String word) {
            if (word == null) {
                return false;
            }
            TrieNode node = root;
            char[] array = word.toCharArray();
            int length = array.length;
            for (int i = 0; i < length; i++) {
                int index = array[i] - 'a';
                if (node.nexts[index] == null) {
                    return false;
                }
                node = node.nexts[index];
            }
            return node.end != 0; //判断是否为0
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            TrieNode node = root;
            char[] array = pre.toCharArray();
            int length = array.length;
            for (int i = 0; i < length; i++) {
                int index = array[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }
}
