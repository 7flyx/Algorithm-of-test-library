package unit01;

import java.util.*;

public class Code06_HanoiProblemAdvance {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int count1 = hanoiProblem1(N); //递归版本
        System.out.println();
        int count2 = hanoiProblem2(N, "left", "mid", "right"); //递归版本
        System.out.println("It will move " + count1 + " steps.");
        System.out.println("It will move " + count2 + " steps.");
    }

    public static int hanoiProblem1(int N) {
        if (N < 1) {
            return 0;
        }
        return process(N, "left", "mid", "right", "left", "right");
    }

    public static int process(int N, String left, String mid, String right, String from, String to) {
        if (N == 1) {
            //分为两种情况，起点或终点在中间；  左到右 或者是右到左
            if (from.equals("mid") || to.equals("mid")) {
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            } else {
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }

        //还不止一个盘子的时候
        if (from.equals("mid") || to.equals("mid")) { //起点或终点在中间
            String another = from.equals("left") || to.equals("left") ? right : left; //左边还是右边做中转
            int part1 = process(N - 1, left, mid, right, from, another);
            int part2 = 1;
            System.out.println("Move " + N + " from " + from + " to " + to);
            int part3 = process(N - 1, left, mid, right, another, to);
            return part1 + part2 + part3;
        } else { //左到右 或者 右到左
            int part1 = process(N - 1, left, mid, right, from, to); //递归到N=1时，将1号盘先移动
            int part2 = 1;
            System.out.println("Move " + N + " from " + from + " to " + mid);
            int part3 = process(N - 1, left, mid, right, to, from);
            int part4 = 1;
            System.out.println("Move " + N + " from " + mid + " to " + to);
            int part5 = process(N - 1, left, mid, right, from, to);
            return part1 + part2 + part3 + part4 + part5;
        }
    }

    public enum Action {
        NO, LToM, MToL, MToR, RToM
    }

    public static int hanoiProblem2(int N, String from, String mid, String to) {
        if (N < 1) {
            return 0;
        }
        Stack<Integer> lS = new Stack<>();
        Stack<Integer> mS = new Stack<>();
        Stack<Integer> rS = new Stack<>();

        lS.push(Integer.MAX_VALUE);
        mS.push(Integer.MAX_VALUE);
        rS.push(Integer.MAX_VALUE);

        for (int i = N; i > 0; i--) {
            lS.push(i);
        }
        int steps = 0;
        Action[] record = {Action.NO};
        while (rS.size() != N + 1) {
                steps += fStackTotStack(record, Action.MToL, Action.LToM, lS,mS, from, mid);
                steps += fStackTotStack(record, Action.LToM, Action.MToL, mS,lS, mid, from);
                steps += fStackTotStack(record, Action.RToM, Action.MToR, mS,rS, mid, to);
                steps += fStackTotStack(record, Action.MToR, Action.RToM, rS,mS, to, mid);
        }
        return steps;
    }

    public static int fStackTotStack(Action[] record, Action preNoAct, Action nowAct,
                                     Stack<Integer> fStack, Stack<Integer> tStack, String from, String to) {
        if (record[0] != preNoAct && fStack.peek() < tStack.peek()) {
            tStack.push(fStack.pop());
            System.out.println("Move " + tStack.peek() + " from " + from + " to " + to);
            record[0] = nowAct;
            return 1;
        }
        return 0;
    }
}