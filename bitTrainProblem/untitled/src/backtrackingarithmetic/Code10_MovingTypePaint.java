package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:30
 * Description:活字印刷
 * 你有一套活字字模 tiles，其中每个字模上都刻有一个字母 tiles[i]。返回你可以印出的非空字母序列的数目。
 * 注意：本题中，每个活字字模只能使用一次。
 * LeetCode：https://leetcode-cn.com/problems/letter-tile-possibilities/
 */
public class Code10_MovingTypePaint {
    public static void main(String[] args) {
        String str = "AAABBC";
        System.out.println(numTilePossibilities(str));
    }

    //先对字符串进行一个子序列，筛选出一些字符串。然后对这些进行一个全排列。要去重
    public static int numTilePossibilities(String tiles) {
        if (tiles == null || tiles.length() == 0) {
            return 0;
        }

        HashSet<String> subOrderStr = new HashSet<>();
        getOrderStr(tiles.toCharArray(), subOrderStr, 0, new char[tiles.length()], 0);
        HashSet<String> set = new HashSet<>();
        int index = 0;
        for (String s : subOrderStr) {
            if (index++ != 0) {
                getArrangement(s.toCharArray(), set, 0);
            }
        }
        return set.size();
    }

    //全排列
    private static void getArrangement(char[] ch, HashSet<String> list, int index) {
        if (index >= ch.length) {
            list.add(new String(ch));
            return;
        }

        boolean[] isVisit = new boolean[26]; //分支限界，俗称剪枝
        for (int i = index; i < ch.length; i++) {
            if (!isVisit[ch[i] - 'A']) {
                isVisit[ch[i] - 'A'] = true;
                swap(ch, i, index);
                getArrangement(ch, list, index + 1);
                swap(ch, i, index);
            }
        }
    }

    //子序列
    private static void getOrderStr(char[] ch, HashSet<String> list, int index, char[] tmp, int indexOfTmp) {
        if (index == ch.length) {
            //特别注意，这里在保存tmp为字符串的时候，不能直接newString，因为tmp后续的位置还有空位置，会出现0
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while (i < indexOfTmp && tmp[i] != 0) {
                sb.append(tmp[i++]);
            }
            list.add(sb.toString());
            return;
        }
        getOrderStr(ch, list, index + 1, tmp, indexOfTmp); //不要当前字符
        tmp[indexOfTmp] = ch[index];
        getOrderStr(ch, list, index + 1, tmp, indexOfTmp + 1); //要当前字符
    }

    private static void swap(char[] ch, int l, int r) {
        char tmp = ch[l];
        ch[l] = ch[r];
        ch[r] = tmp;
    }
}
