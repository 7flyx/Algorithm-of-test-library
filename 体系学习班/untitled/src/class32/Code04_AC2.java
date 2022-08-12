package class32;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-08-10
 * Time: 16:46
 * Description: AC自动机。
 * 返回的是 敏感词的数量。 不包含重复的
 */
public class Code04_AC2 {
    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("c");
        ac.build();
        System.out.println(ac.containNum("cdhe"));
    }

    private static class Node {
        public int end;
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    private static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String str) {
            if (str == null) {
                return;
            }
            Node cur = root;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end++; // 尾结点统计数量++
        }

        // 连接所有的fail指针
        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                // 遍历nexts数组
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) { // 下级节点不为null的情况
                        cur.nexts[i].fail = root; // 首先指向root，后续可能还会更新
                        Node curFail = cur.fail; // 父节点的fail指针
                        while (curFail != null) {
                            if (curFail.nexts[i] != null) {
                                cur.nexts[i].fail = curFail.nexts[i];
                                break;
                            }
                            curFail = curFail.fail;
                        }
                        queue.add(cur.nexts[i]); // 当前节点入队列
                    }
                }
            }

        }

        // 查询content中，有多少敏感词，不包括重复的
        public int containNum(String content) {
            if (content == null || content.length() == 0) {
                return 0;
            }
            int length = content.length();
            Node cur = root;
            int ans = 0;
            for (int i = 0; i < length; i++) {
                int index = content.charAt(i) - 'a';
                // 没有下级节点，但是cur还不是root，可以沿着fail指针走
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null? cur.nexts[index] : root;
                // 到这里，cur要么是root，要么就是下级节点
                // 以cur为起点，跑一遍fail指针，沿途搜集敏感词
                Node follow = cur;
                while (follow != root) {
                    if (follow.end == -1) {
                        break;
                    }
                    if (follow.end != 0) {
                        ans += follow.end;
                        follow.end = -1;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }
}
