package class13;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-12
 * Time: 11:57
 * Description: 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲，给你每一个项目开始的时间和结束的时间
 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次
 */
public class Code02_BestArrange {
    public static void main(String[] args) {

    }

    public static int bestArrange(Node[] conference) {
        if (conference == null || conference.length == 0) {
            return 0;
        }

        // 先以会议结束时间进行排序，先结束的会议排在前面
        Arrays.sort(conference, new ConferenceCompare());
        int preEndTime = 0;
        int count = 0;
        for (Node node : conference) {
            if (node.start >= preEndTime) {
                count++;
                preEndTime = node.end; // 更新结束时间
            }
        }
        return count;
    }

    private static class Node {
        public int start;
        public int end;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }


    private static class ConferenceCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.end - o2.end; // 以会议的结束时间进行排序，先结束的排在前面
        }
    }
}
