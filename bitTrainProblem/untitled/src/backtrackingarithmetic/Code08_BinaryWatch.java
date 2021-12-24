package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:24
 * Description: 二进制手表
 * LeetCode：https://leetcode-cn.com/problems/binary-watch/
 */
public class Code08_BinaryWatch {

    public static void main(String[] args) {
        int turnedOn = 1;
        List<String> res = readBinaryWatch(turnedOn);
        for (String s : res) {
            System.out.print(s + " ");
        }
    }

    private static List<String> res = new ArrayList<>();
    private static int[] hour = {8, 4, 2, 1};
    private static int[] minute = {32, 16, 8, 4, 2, 1};

    public static List<String> readBinaryWatch(int turnedOn) {
        if (turnedOn <= 0) {
            res.add("0:00");
            return res;
        }

        for (int h = 0; h <= turnedOn; h++) {
            process(h, turnedOn - h, 0, 0, 0, 0);
        }
        return res;
    }

    private static void process(int h, int m, int indexOfH, int indexOfM, int hRes, int mRes) {
        if (h == 0 && m == 0 && hRes <= 11 && mRes <= 59) {
            if (mRes < 10) {
                res.add(hRes + ":0" + mRes);
            } else {
                res.add(hRes + ":" + mRes);
            }
            return;
        }
        if (h < 0 || m < 0 || h >= 4 || m >= 6 || hRes > 11 || mRes > 59 || indexOfM == 6) {
            return;
        }

        process(h, m, indexOfH + 1, indexOfM + 1, hRes, mRes); //什么都不要
        if (indexOfH < 4) {
            process(h - 1, m, indexOfH + 1, indexOfM + 1, hRes + hour[indexOfH], mRes); //要小时
            //两个都要
            process(h - 1, m - 1, indexOfH + 1, indexOfM + 1, hRes + hour[indexOfH], mRes + minute[indexOfM]);
        }
        process(h, m - 1, indexOfH + 1, indexOfM + 1, hRes, mRes + minute[indexOfM]); //要分钟
    }


}
