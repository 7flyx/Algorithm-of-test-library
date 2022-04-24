package class09;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-24
 * Time: 18:21
 * Description: 前缀树
 */

public class Code01_TrieTree {
    private Node root; // 根节点

    private static class Node {
        public int pass;
        public int end;
        // 数据规模小，比如小写字母等，可直接使用数组，否则就能用其他数据结构，比如哈希表
        public Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            nexts = new Node[26];
        }
    }

    public Code01_TrieTree() {
        root = new Node();
    }

    public void insert(String str) {
        if (str == null) {
            return;
        }

        char[] ch = str.toCharArray();
        Node node = root;
        node.pass++;
        for (int i = 0; i < ch.length; i++) {
            int index = ch[i] - 'a'; // 计算偏移量
            if (node.nexts[index] == null) {
                node.nexts[index] = new Node();
            }
            node = node.nexts[index]; //先跳转到下一个节点
            node.pass++; // 沿途的pass++
        }
        node.end++;
    }

    // 查找str在树上出现过几次
    public int search(String str) {
        if (str == null) {
            return 0;
        }

        Node node = root;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            int index = ch[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index]; // 向下跳转
        }
        return node.end; // 返回end值
    }

    // 返回以str为前缀字符串的字符串个数
    public int prefixString(String str ) {
        if (str == null) {
            return 0;
        }
        char[] ch = str.toCharArray();
        Node node = root;
        for (int i = 0; i < ch.length; i++) {
            int index = ch[i] - 'a';
            if (node.nexts[index] == null) {
                return 0;
            }
            node = node.nexts[index];
        }
        return node.pass; // 返回pass值即可
    }

    public void delete(String str) {
        if (search(str) != 0) { // 前缀树上有这个单词的时候，才能删除
            char[] ch = str.toCharArray();
            Node node = root;
            node.pass--;
            for (int i = 0; i < ch.length; i++) {
                int index = ch[i] - 'a';
                if (--node.nexts[index].pass == 0) {
                    node.nexts[index] = null; // 改为空，让JVM去回收内存资源
                    return;
                }
                node = node.nexts[index];
            }
            node.end--;
        }
    }

}
