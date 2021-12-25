package backtrackingarithmetic;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:37
 * Description:单词接龙
 * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列：
 * <p>
 * 序列中第一个单词是 beginWord 。
 * 序列中最后一个单词是 endWord 。
 * 每次转换只能改变一个字母。
 * 转换过程中的中间单词必须是字典 wordList 中的单词。
 * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，找到从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。
 * 如果不存在这样的转换序列，返回 0。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-ladder
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code14_WortPickUpDragon {

    public static void main(String[] args) {
        String begin = "hit";
        String end = "cog";
        List<String> list = new ArrayList<>();
        list.add("hot");
        list.add("dog");
        list.add("dot");
        list.add("lot");
        list.add("log");
        list.add("cog");
        System.out.println(ladderLength(begin, end, list));
    }


    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
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
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(endWord);
        int res = 1; //起始状态是1，进入队列，就加一次
        while (!queue.isEmpty()) {
            res++;//状态加1
            int size = queue.size();
            while (size-- > 0) {
                endWord = queue.poll();
                int count2 = 0; //记录与起始串的差距
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
        return 0;
    }

}
