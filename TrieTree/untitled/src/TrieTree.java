import java.util.HashMap;

/**
 * Created by flyx
 * Description: 前缀树（字典树）的实现
 * User: 听风
 * Date: 2021-09-01
 * Time: 16:18
 */
public class TrieTree {
    private final TrieNode root;

    public TrieTree() {
        root = new TrieNode();
    }

    private static class TrieNode {
        public int pass; //途径的数量
        public int end;
        public HashMap<Character, TrieNode> map; //保存下一节点的地址

        public TrieNode() {
            map = new HashMap<>();
        }
    }

    public void add(String word) {
        if (word == null) {
            return;
        }
        char[] array = word.toCharArray();
        TrieNode node = root;
        node.pass++;
        for (char ch : array) {
            if (!node.map.containsKey(ch)) {
                node.map.put(ch, new TrieNode());
            }
            node = node.map.get(ch);
            node.pass++;
        }
        node.end++;
    }

    public int search(String word) {
        if (word == null) {
            return 0;
        }

        char[] array = word.toCharArray();
        TrieNode node = root;
        for (char ch : array) {
            if (!node.map.containsKey(ch)) {
                return 0;
            }
            node = node.map.get(ch); //拿到下一节点
        }
        return node.end; //返回最终的end值
    }

    /**
     *
     * @param word 以word为前缀的字符串
     * @return 返回以word为前缀的字符串的数量
     */
    public int prefixNumber(String word) {
        if (word == null) {
            return 0;
        }

        char[] array = word.toCharArray();
        TrieNode node = root;
        for (char ch : array) {
            if (!node.map.containsKey(ch)) {
                return 0;
            }
            node = node.map.get(ch); //拿到下一节点
        }
        return node.pass;
    }

    public boolean delete(String word) {
        if (word != null && search(word) != 0) {
            char[] array = word.toCharArray();
            TrieNode node = root;
            node.pass--;
            for (char ch : array) {
                if (--node.map.get(ch).pass == 0) {
                    //pass值为0，所以从该节点一下的所有子树，都将不存在，所以直接全部回收即可
                    //C++ 的,需要遍历所有子树，调用析构函数
                    node.map.remove(ch);
                    return true;
                }
                node = node.map.get(ch);
            }
            node.end--;
            return true;
        }
        return false;
    }
}
