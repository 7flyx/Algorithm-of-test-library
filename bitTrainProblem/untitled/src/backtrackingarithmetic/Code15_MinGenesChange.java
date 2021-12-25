package backtrackingarithmetic;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:38
 * Description:最小基因变化-与上一道题几乎一模一样
 * LeetCode：https://leetcode-cn.com/problems/minimum-genetic-mutation/
 */
public class Code15_MinGenesChange {

    public int minMutation(String beginWord, String endWord, String[] wordList) {
        if (beginWord == null || endWord == null || beginWord.length() != endWord.length()) {
            return 0;
        }

        HashSet<String> set = new HashSet<>();
        int length = endWord.length();
        for (String str : wordList) {
            if (length == str.length()) {
                set.add(str); //将所有的单词放入set里面
            }
        }
        if (set.contains(endWord)) {
            set.remove(endWord);
        } else {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(endWord);
        int res = 0; //起始状态是0，进入队列，就加一次
        while (!queue.isEmpty()) {
            res++;//状态加1
            int size = queue.size();
            while (size-- > 0) {
                endWord = queue.poll();
                int count2 = 0; //记录与开始串的差距
                for (int i = 0; i < length; i++) {
                    if (beginWord.charAt(i) != endWord.charAt(i)) {
                        count2++;
                    }
                }
                if (count2 < 2) { //与起始串相差一个，直接返回答案了
                    return res;
                }
                List<String> list = new ArrayList<>();
                for (String str : set) {
                    int count = 0;
                    for (int i = 0; i < length; i++) {
                        if (endWord.charAt(i) != str.charAt(i)) {
                            count++;
                        }
                    }
                    if (count < 2) {
                        queue.add(str); //只相差一个字符，可以放入队里
                        list.add(str); //在set里删除str
                    }
                }
                set.removeAll(list);//在set里删除str
            }
        }
        return -1;
    }

}
