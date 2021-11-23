package demo;


import java.util.Arrays;
import java.util.TreeMap;

/**
 * 找工作。牛牛帮其他伙伴找工作。
 * class Job{
 * public int money; //报酬
 * public int hard; //难度
 * public Job(int money, int hard){
 * this.money = money;
 * this.hard = hard;
 * }
 * }
 * <p>
 * 给定一个Job类型的数组，表示所有的工作。给定一个int类型的数组arr，表示所有小伙伴的能力。
 * 返回int类型的数组，表示每一个小伙伴按照牛牛的标准选工作后所能获得的最大报酬。
 */
public class Code03_FindJob {
    public static void main(String[] args) {

    }

    private static class Job {
        public int money; //报酬
        public int hard; //难度

        public Job(int money, int hard) {
            this.money = money;
            this.hard = hard;
        }

    }

    public static int[] findJob(Job[] jobs, int[] arr) {
        if (jobs == null || arr == null || jobs.length == 0 || arr.length == 0) {
            return new int[]{};
        }

        Arrays.sort(jobs,((o1, o2) -> {
            if (o1.hard == o2.hard) {
                return o2.money - o1.money; //难度相同的，报酬高的，排前面
            }
            return o1.hard - o2.hard; //难度升序排列
        }));
        int[] res = new int[arr.length];
        //现在的状况是难度相同的，报酬高的在前面
        //难度依次递增
        //将所有工作，难度相同的，报酬高的，放入TreeMap中
        TreeMap<Integer, Integer> map = new TreeMap<>(); //
        Job pre = jobs[0]; //上一组难度的组长
        map.put(pre.hard, pre.money);
        for (int j = 1; j < jobs.length; j++) {
            if (pre.hard != jobs[j].hard && pre.money < jobs[j].money) {
                map.put(jobs[j].hard, jobs[j].money); //将好的工作放入map中
                pre = jobs[j];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            Integer index = map.floorKey(i); //返回的是，<=i的最大值;难度
            res[i] = index == null? 0 : map.get(index);
        }
        return res;
    }
}
