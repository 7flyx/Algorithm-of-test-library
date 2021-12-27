package greedarithmetic;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-25
 * Time: 19:34
 * Description:最多可参与的会议数量
 * https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended/
 */
public class Code07_MaxJoinConference {
    public int maxEvents(int[][] events) {
        if (events == null || events.length == 0) {
            return 0;
        }

        //先对数组进行排序，第一列的数是会议的开始时间，让开始时间相等的在一堆
        Arrays.sort(events, (o1, o2)-> {
            return o1[0] - o2[0]; //升序
        });

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int curDay = 1;
        int res = 0;
        int i = 0;
        while (i < events.length || !minHeap.isEmpty()) {
            while (i < events.length && events[i][0] == curDay) {//当前数据的开始时间与cur相等
                minHeap.add(events[i][1]);//将结束时间放入小堆
                i++;
            }

            //当前堆中的结束时间小于当前之间，直接舍弃
            while (!minHeap.isEmpty() && minHeap.peek() < curDay) {
                minHeap.poll();
            }

            if (!minHeap.isEmpty()) { //每天只能开一个会议
                minHeap.poll();
                res++;
            }
            curDay++;
        }
        return res;
    }

}
