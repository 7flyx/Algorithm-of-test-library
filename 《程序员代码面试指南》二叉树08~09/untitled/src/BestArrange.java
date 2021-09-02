import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by flyx
 * Description: 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 *              给你每一个项目开始的时间和结束的时间(给你-个数组，里面是一一个个具体
 *              的项目)，你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
 *              返回这个最多的宣讲场次。
 * User: 听风
 * Date: 2021-09-01
 * Time: 20:28
 */
public class BestArrange {
    private static class Program {
        public int start; //开始时间
        public int end; //结束时间
        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class ProgramCompare implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end; //左减右，升序
        }
    }

    public int bestArrange(Program[] array) {
        if (array == null) {
            return 0;
        }

        //以每一场会议的结束时间，作为标志，进行排序。从最先结束的会议算着走，就能得到这一天最多的会议场数
        Arrays.sort(array, new ProgramCompare());
        int timePoint = 0; //上一场会议的结束时间
        int res = 0;
        for (Program program : array) {
            if (timePoint <= program.start) { //小于这一场会议的开始时间，我们就安排这一场
                res++;
                timePoint = program.end; //新的结束时间，就是这场会议的结束时间
            }
        }
        return res;
    }
}
