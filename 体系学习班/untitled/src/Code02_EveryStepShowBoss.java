import class07.HeapGenerate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code02_EveryStepShowBoss {

    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int v, int b, int o) {
            id = v;
            buy = b;
            enterTime = 0;
        }
    }

    public static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }

    }

    public static class DaddyComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }

    }

    public static class WhosYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGenerate<Customer> candHeap;
        private HeapGenerate<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosYourDaddy(int limit) {
            customers = new HashMap<Integer, Customer>();
            candHeap = new HeapGenerate<>(new CandidateComparator());
            daddyHeap = new HeapGenerate<>(new DaddyComparator());
            daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer c = customers.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                customers.remove(id);
            }
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.add(c);
                } else {
                    c.enterTime = time;
                    candHeap.add(c);
                }
            } else if (candHeap.contains(c)) {
                if (c.buy == 0) {
                    candHeap.remove(c);
                } else {
                    candHeap.resign(c);
                }
            } else {
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                } else {
                    daddyHeap.resign(c);
                }
            }
            daddyMove(time);
        }

        public List<Integer> getDaddies() {
            List<Customer> customers = daddyHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer p = candHeap.poll();
                p.enterTime = time;
                daddyHeap.add(p);
            } else {
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer oldDaddy = daddyHeap.poll();
                    Customer newDaddy = candHeap.poll();
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    daddyHeap.add(newDaddy);
                    candHeap.add(oldDaddy);
                }
            }
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(i, arr[i], op[i]);
            ans.add(whoDaddies.getDaddies());
        }
        return ans;
    }

    // 干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.enterTime = i;
                    daddy.add(c);
                } else {
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new CandidateComparator());
            daddy.sort(new DaddyComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
//                daddy.remove(0);
                Customer newDaddy = cands.get(0);
//                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
//                daddy.add(newDaddy);
//                cands.add(oldDaddy);
                daddy.set(0, newDaddy);
                cands.set(0, oldDaddy);
            }
        }
    }

    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }


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
            clearZeroBuy1(daddy);
            clearZeroBuy1(candidate);
            // 再次进行排序
            candidate.sort(new CandidateComparator());
            daddy.sort(new DaddyComparator());
            // 尝试将候选区的数据，移动到获奖区
            moveDaddy1(candidate, daddy, i, k);
            ans.add(getCurAns(daddy)); // 抓取当前位置处理后的结果
        }
        return ans;
    }

    private static void moveDaddy1(ArrayList<Customer> candidate, ArrayList<Customer> daddy, int time, int k) {
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

    private static void clearZeroBuy1(ArrayList<Customer> customers) {
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

    public static void main1(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
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

    public static void main(String[] args) {
        int[] arr = {4, 4, 1, 8, 9, 6, 4, 8, 5, 5, 7, 4, 1, 8, 8, 2, 1, 3, 7, 8, 1, 3, 5, 6, 7, 8, 8, 1, 1, 6, 2, 5, 2, 6, 8, 6, 7, 6, 7, 7, 0, 0, 1, 7, 9, 6, 4, 5, 2, 7, 1, 7, 9, 0, 4, 0, 6, 0, 6, 1, 3, 0, 0, 3, 5, 6, 7, 0, 5, 4, 5, 4};
        boolean[] op = {true, false, true, true, true, true, false, false, true, false, true, true, true, true, false, true, true, false, false, true, false, false, false, false, true, true, false, true, false, true, true, true, true, true, false, true, true, true, false, false, true, true, true, true, false, true, false, false, false, false, false, true, false, true, true, true, false, true, false, false, true, false, false, false, true, true, false, false, true, false, true, true};
        int k = 3;
        List<List<Integer>> ans1 = topK(arr, op, k);
        List<List<Integer>> ans2 = ordinary(arr, op, k);
        if (sameAnswer(ans1, ans2)) {
            System.out.println(ans1);
            System.out.println(ans2);
        }
    }

}
