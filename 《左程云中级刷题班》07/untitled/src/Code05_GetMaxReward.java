import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-04
 * Time: 9:38
 * Description:
 * 输入描述：
 * 第一行输入数据N与D， 表示有N项活动， D表示给予的时长。 0＜N＜＝ 1000， 0＜D＜＝ 10000。
 * 从第二行开始到N+1行， 每行描述一个活动的信息， 其中第一项表示当前活动需要花费的时间t， 第二项表示可以获
 * 得的奖励a， 之后有N项数据， 表示当前活动与其他活动的依赖关系， 1表示有依赖， 0表示无依赖。 每项数据用空格
 * 分开。
 * 输出描述：
 * 输出两项数据A与T， 用空格分割。 A表示所获得的最大奖励， T表示所需要的时长。
 * 输入
 * 8 10
 * 3 2000 0 1 1 0 0 0 0 0
 * 3 4000 0 0 0 1 1 0 0 0
 * 2 2500 0 0 0 1 0 0 0 0
 * 1 1600 0 0 0 0 1 1 1 0
 * 4 3800 0 0 0 0 0 0 0 1
 * 2 2600 0 0 0 0 0 0 0 1
 * 4 4000 0 0 0 0 0 0 0 1
 * 3 3500 0 0 0 0 0 0 0 0
 * 输出
 * 11700 9
 */
public class Code05_GetMaxReward {
    public static void main(String[] args) {
        int allTime = 10;
        int[] revenue = {2000, 4000, 2500, 1600, 3800, 2600, 4000, 3500};
        int[] times = {3, 3, 2, 1, 4, 2, 4, 3};
        int[][] dependents = {
                {0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0}};

        int[] res = calcMaxReward(allTime, times, revenue, dependents);
        System.out.println(res[0] + " " + res[1]);
    }

    /**
     * @param allTime   你总共有多少时间
     * @param day       每一个任务所需要的时间
     * @param reward    每一个任务的奖励
     * @param dependent 依赖关系。类似于图。
     * @return 在allTime时间内，最高的奖励是多少
     */
    public static int[] calcMaxReward(int allTime, int[] day, int[] reward, int[][] dependent) {
        if (day == null || reward == null || day.length != reward.length) {
            return new int[]{};
        }
        //题意说的是，从任意一个任务出发，但是必须把最后一任务给做了，且类似于图一样，有这依赖关系
        //所以我们先根据依赖关系，将每一个节点做完之后，下一个任务可以做什么，用表记录好
        HashMap<Integer, List<Integer>> parents = new HashMap<>();
        //对于每一任务来说，可能会有多种.建一张表，记录每个任务能够拿到的报酬以及时间限制
        HashMap<Integer, TreeMap<Integer, Integer>> nodeReward = new HashMap<>();
        for (int i = 0; i < day.length; i++) {
            parents.put(i, new ArrayList<>());
            nodeReward.put(i, new TreeMap<>());
        }
        int end = 0;
        for (int i = 0; i < dependent.length; i++) {
            boolean flag = true;
            for (int j = 0; j < dependent[0].length; j++) {
                if (dependent[i][j] == 1) {
                    parents.get(j).add(i); //确定当前节点结束后，可以做下一个任务
                    flag = false;
                }
            }
            if (flag) {
                end = i; //最后一个任务的下标值
            }
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(day[end], reward[end]);
        nodeReward.put(end, map); //将最后一个节点的数值先放入表中
        Queue<Integer> queue = new LinkedList<>();
        queue.add(end); //将最后一个任务放入队列，然后就是图的宽度遍历
        while (!queue.isEmpty()) {
            end = queue.poll();
            //将该节点的下一节点全部遍历一遍
            for (Integer i : parents.get(end)) {
                //遍历当前节点的所有报酬，然后添加到i节点中去
                for (Map.Entry<Integer, Integer> entry : nodeReward.get(end).entrySet()) {
                    int times = entry.getKey() + day[i]; //所需要的时间
                    int money = entry.getValue() + reward[i]; //可以得到的钱
                    TreeMap<Integer, Integer> preMap = nodeReward.get(i); //上一节点的表
                    //表中，当前时间的奖励为空，或者这个时间的奖励最高，才放入进去。
                    //表彰严格控制，时间增加，奖励必须增加
                    if (preMap.floorKey(times) == null || preMap.get(preMap.floorKey(times)) < money) {
                        preMap.put(times, money);
                    }
                }
                queue.add(i);
            }
        }

        //将每一个节点的所有任务进行合并，再根据allTime进行返回
        TreeMap<Integer, Integer> allMap = new TreeMap<>();
        for (int i = 0; i < day.length; i++) {
            TreeMap<Integer, Integer> node = nodeReward.get(i);
            for (Map.Entry<Integer, Integer> entry : node.entrySet()) {
                int time = entry.getKey();
                int money = entry.getValue();
                //表中，依旧保持 时间增加，报酬增加
                if (allMap.floorKey(time) == null || allMap.get(allMap.floorKey(time)) < money) {
                    allMap.put(time, money);
                }
            }
        }
        //有多少时间，返回相应的报酬
        return new int[]{allMap.get(allMap.floorKey(allTime)), allMap.floorKey(allTime)};
    }
}
