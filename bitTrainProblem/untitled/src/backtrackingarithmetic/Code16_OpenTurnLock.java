package backtrackingarithmetic;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:40
 * Description:打开转盘锁
 * LeetCode；https://leetcode-cn.com/problems/open-the-lock/
 */
public class Code16_OpenTurnLock {
    public int openLock(String[] deadends, String target) {
        if (deadends == null || target == null) {
            return -1;
        }
        if (target.equals("0000")) {
            return 0;
        }
        HashSet<String> set = new HashSet<>();
        for (String str : deadends) {
            set.add(str);
        }
        if (set.contains("0000")) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        HashSet<String> isVisit = new HashSet<>(); //用一个表，记录当前字符是否已经遍历过
        isVisit.add("0000"); //这是初始化状态
        queue.offer("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            step++; //步数加1
            int size = queue.size();
            while (size-- != 0) {
                String str = queue.poll();
                List<String> list = getNextString(str); //得到当前字符串，转动一次的所有可能性
                for (String s : list) {
                    //当前字符的字符还没遍历过，且还等于target，返回即可
                    if (!isVisit.contains(s) && !set.contains(s)) {
                        if (s.equals(target)) {
                            return step;
                        }
                        isVisit.add(s); //遍历过了，直接放入
                        queue.offer(s); //队列加一个
                    }

                }
            }
        }
        return -1;
    }

    private List<String> getNextString(String str) {
        char[] ch = str.toCharArray();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            char tmp = ch[i];
            ch[i] = getPreNum(tmp);
            res.add(new String(ch)); //放入集合中
            ch[i] = getLastNum(tmp);
            res.add(new String(ch)); //放入集合中
            ch[i] = tmp; //改为原来的字符
        }
        return res;
    }

    //得到下一个字符
    private char getPreNum(char chs) {
        return chs == '9' ? '0' : (char) (chs + 1);
    }

    //得到上一个字符
    private char getLastNum(char chs) {
        return chs == '0' ? '9' : (char) (chs - 1);
    }

}
