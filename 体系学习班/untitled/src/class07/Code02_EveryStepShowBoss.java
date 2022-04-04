package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-03
 * Time: 18:12
 * Description: 第8节 加强堆的实现以及使用
 * 原题：给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op
 * 两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
 * arr= [3,3,1,2,1,2,5…
 * op = [T,T,T,T,F,T,F…
 * 依次表示：
 * 3用户购买了一件商品
 * 3用户购买了一件商品
 * 1用户购买了一件商品
 * 2用户购买了一件商品
 * 1用户退货了一件商品
 * 2用户购买了一件商品
 * 5用户退货了一件商品…
 * 一对arr[i]和op[i]就代表一个事件：
 * 用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品
 * op[i] == F就代表这个用户退货了一件商品
 * 现在你作为电商平台负责人，你想在每一个事件到来的时候，
 * 都给购买次数最多的前K名用户颁奖。
 * 所以每个事件发生后，你都需要一个得奖名单（得奖区）。
 * 得奖系统的规则：
 * 1，如果某个用户购买商品数为0，但是又发生了退货事件，
 * 则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
 * 2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
 * 3，每次都是最多K个用户得奖，K也为传入的参数
 * 如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
 * 4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，
 * 一定在这两个区域中的一个
 * 5，购买数最大的前K名用户进入得奖区，
 * 在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
 * 6，如果购买数不足以进入得奖区的用户，进入候选区
 * 7，如果候选区购买数最多的用户，已经足以进入得奖区，
 * 该用户就会替换得奖区中购买数最少的用户（大于才能替换），
 * 如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
 * 如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
 * 8，候选区和得奖区是两套时间，
 * 因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
 * 从得奖区出来进入候选区的用户，得奖区时间删除，
 * 进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 从候选区出来进入得奖区的用户，候选区时间删除，
 * 进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
 * 离开是指彻底离开，哪个区域也不会找到该用户
 * 如果下次该用户又发生购买行为，产生>0的购买数，
 * 会再次根据之前规则回到某个区域中，进入区域的时间重记
 * 请遍历arr数组和op数组，遍历每一步输出一个得奖名单
 * public List<List<Integer>>  topK (int[] arr, boolean[] op, int k)
 */
public class Code02_EveryStepShowBoss {

    public static class DaddyCompare implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            if (o1.buy == o2.buy) { // 买的东西都一样，先进入的排前面
                return o1.enterTime - o2.enterTime;
            }
            return o1.buy - o2.buy; // 买的东西少的排前面
        }
    }

    public static class CandidateCompare implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            if (o1.buy == o2.buy) { // 买的东西相同时，先买的人排在前面
                return o1.enterTime - o2.enterTime;
            }
            return o2.buy - o1.buy; // 买的东西多的人，排在前面
        }
    }

    // 加强堆的改版
    public static class WhoIsYourDaddy {
        private HashMap<Integer, Customer> map; // 存储每个id对用的Consumer对象
        private HeapGreater<Customer> daddy; // 得奖区
        private HeapGreater<Customer> candidate; // 候选区
        private final int limit; // 得奖区人数限制

        public WhoIsYourDaddy(int limit) {
            this.candidate = new HeapGreater<>(new CandidateCompare());
            this.daddy = new HeapGreater<>(new DaddyCompare());
            this.map = new HashMap<>();
            this.limit = limit;
        }

        public void operate(int id, boolean buyOrReturn, int time) {
            if (!buyOrReturn && !map.containsKey(id)) { // 当前是退货，但是map中并没有这个人的信息
                return;
            }
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            Customer c = map.get(id); // 拿到当前id的Consumer对象
            if (buyOrReturn) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) { // 进货数为0了，直接清空即可
                map.remove(id);
            }

            //  当前Consumer既不在候选区，也不在获奖区
            if (!candidate.contains(c) && !daddy.contains(c)) {
                c.enterTime = time;
                if (daddy.size() < limit) {
                    daddy.push(c);
                } else {
                    candidate.push(c);
                }
            } else if (candidate.contains(c)) { // 当前consumer在候选区
                if (c.buy == 0) {
                    candidate.remove(c);
                } else {
                    candidate.resign(c); // 调整当前对象在堆上的位置
                }
            } else { // 当前consumer在获奖区
                if (c.buy == 0) {
                    daddy.remove(c);
                } else {
                    daddy.resign(c); // 调整当前对象在堆上的位置
                }
            }
            moveDaddy(time);
        }

        private void moveDaddy(int time) {
            if (candidate.isEmpty()) {
                return;
            }
            if (daddy.size() < limit) { // 获奖区还没满的情况
                Customer c = candidate.pop();
                c.enterTime = time;
                daddy.push(c);
            } else {
                // 候选区买的东西 比 获奖区买的东西多
                if (candidate.peek().buy > daddy.peek().buy) {
                    Customer oldDad = daddy.pop();
                    oldDad.enterTime = time;
                    Customer newDad = candidate.pop();
                    newDad.enterTime = time;
                    candidate.push(oldDad); // 压入被挤出来的金主爸爸
                    daddy.push(newDad); // 压入新的金主爸爸
                }
            }
        }

        public List<Integer> getDaddies() {
            List<Integer> ans = new ArrayList<>();
            List<Customer> allElements = daddy.getAllElements();
            for (Customer c : allElements) {
                ans.add(c.id);
            }
            return ans;
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        if (arr.length != op.length) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new ArrayList<>();
        WhoIsYourDaddy whoIsYourDaddy = new WhoIsYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoIsYourDaddy.operate(arr[i], op[i], i);
            ans.add(whoIsYourDaddy.getDaddies());
        }
        return ans;
    }

    // 普通解
    public static List<List<Integer>> ordinary(int[] arr, boolean[] op, int k) {
        if (arr.length != op.length) {
            return new ArrayList<>();
        }
        HashMap<Integer, Customer> map = new HashMap<>(); // 存储每个id对用的Consumer对象
        ArrayList<Customer> daddy = new ArrayList<>(); // 得奖区
        ArrayList<Customer> candidate = new ArrayList<>(); // 候选区
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i]; // 用户ID
            boolean buyOrReturn = op[i]; // 买东西或者退东西
            if (!buyOrReturn && !map.containsKey(id)) { // 当前是退货，但是并没有这个人，直接忽略即可
                ans.add(getCurAns(daddy));
                continue;
            }

            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            Customer c = map.get(id);  // 取出当前id的Consumer对象
            if (buyOrReturn) {
                c.buy++;
            } else { // 退货
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id); // 退货后，删除当前Consumer对象
            }
            // 两个区域都没有当前Consumer对象时， 添加即可
            if (!daddy.contains(c) && !candidate.contains(c)) {
                c.enterTime = i; // 更新进去区域的时间
                if (daddy.size() < k) {
                    daddy.add(c);
                } else {
                    candidate.add(c);
                }
            }

            // 清楚两个区域中，buy为0的情况
            clearZeroBuy(daddy);
            clearZeroBuy(candidate);
            // 再次进行排序
            candidate.sort(new CandidateCompare());
            daddy.sort(new DaddyCompare());
            // 尝试将候选区的数据，移动到获奖区
            moveDaddy(candidate, daddy, i, k);
            ans.add(getCurAns(daddy)); // 抓取当前位置处理后的结果
        }
        return ans;
    }

    private static void moveDaddy(ArrayList<Customer> candidate, ArrayList<Customer> daddy, int time, int k) {
        if (candidate.isEmpty()) { // 候选区没人的情况，直接返回
            return;
        }
        if (daddy.size() < k) { // 获奖区还没有满的情况
            Customer newDad = candidate.get(0);
            newDad.enterTime = time;
            candidate.remove(0); // 删除在候选区的对象
            daddy.add(newDad);
        } else {
            // 候选区的人买的东西 比 获奖区的人买的东西多，他两交换
            if (candidate.get(0).buy > daddy.get(0).buy) {
                Customer newDad = candidate.get(0);
                Customer oldDad = daddy.get(0);
                newDad.enterTime = time;
                oldDad.enterTime = time; // 更新二者进入区域的时间
                candidate.set(0, oldDad);
                daddy.set(0, newDad);
            }
        }
    }

    private static void clearZeroBuy(ArrayList<Customer> customers) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer c : customers) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        customers.clear(); // 清空原数组
        for (Customer c : noZero) {
            customers.add(c);
        }
    }

    private static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> res = new ArrayList<>();
        for (Customer customer : daddy) {
            res.add(customer.id);
        }
        return res;
    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = ordinary(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

    public static void main1(String[] args) {
        int[] arr = {4, 4, 1, 8, 9, 6, 4, 8, 5, 5, 7, 4, 1, 8, 8, 2, 1, 3, 7, 8, 1, 3, 5, 6, 7, 8, 8, 1, 1, 6, 2, 5, 2, 6, 8, 6, 7, 6, 7, 7, 0, 0, 1, 7, 9, 6, 4, 5, 2, 7, 1, 7, 9, 0, 4, 0, 6, 0, 6, 1, 3, 0, 0, 3, 5, 6, 7, 0, 5, 4, 5, 4};
        boolean[] op = {true, false, true, true, true, true, false, false, true, false, true, true, true, true, false, true, true, false, false, true, false, false, false, false, true, true, false, true, false, true, true, true, true, true, false, true, true, true, false, false, true, true, true, true, false, true, false, false, false, false, false, true, false, true, true, true, false, true, false, false, true, false, false, false, true, true, false, false, true, false, true, true};
        int k = 3;
        List<List<Integer>> ans2 = ordinary(arr, op, k);
        List<List<Integer>> ans1 = topK(arr, op, k);
        if (!sameAnswer(ans1, ans2)) {
            System.out.println("测试失败");
            System.out.println(ans1);
            System.out.println(ans2);
        }
    }


}
