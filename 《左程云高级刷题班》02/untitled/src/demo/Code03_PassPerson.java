package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-21
 * Time: 21:34
 * Description:
 * 某公司招聘， 有n个人入围， HR在黑板上依次写下m个正整数A1、 A2、 ……、 Am， 然后让这n个人围成一个
 * 圈， 并按照顺时针顺序为他们编号0、 1、 2、 ……、 n-1。 录取规则是：
 * 第一轮从0号的人开始， 取用黑板上的第1个数字， 也就是A1
 * 黑板上的数字按次序循环取用， 即如果某轮用了第m个， 则下一轮需要用第1个； 如果某轮用到第k个， 则
 * 下轮需要用第k+1个（k<m）
 * 每一轮按照黑板上的次序取用到一个数字Ax， 淘汰掉从当前轮到的人开始按照顺时针顺序数到的第Ax个
 * 人， 下一轮开始时轮到的人即为被淘汰掉的人的顺时针顺序下一个人
 * 被淘汰的人直接回家， 所以不会被后续轮次计数时数到
 * 经过n-1轮后， 剩下的最后1人被录取
 * 所以最后被录取的人的编号与（n， m， A1， A2， ……， Am） 相关。
 * 输入描述：
 * 第一行是一个正整数N， 表示有N组参数
 * 从第二行开始， 每行有若干个正整数， 依次存放n、 m、 A1、 ……、 Am， 一共有N行， 也就是上面的N组参
 * 数。
 * 输出描述：
 * 输出有N行， 每行对应相应的那组参数确定的录取之人的编号示例1:
 * 输入
 * 1 4
 * 2 3 1
 * 输出
 * 1
 * <p>
 * 简单点说，就是约瑟夫环问题的改版
 */
public class Code03_PassPerson {
    public static void main(String[] args) {
        int[] arr = {4, 2, 3, 1};
        System.out.println(getLiveNum(1, arr));
    }

    /**
     * @param N   共有多少人
     * @param arr 里面存储的是报数，表示从某一个人开始报数，
     * @return 返回最后活下来的那个人
     */
    public static int getLiveNum(int N, int[] arr) {
        if (N <= 1) {
            return N;
        }
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("数组为空");
        }

        return process(N, arr, 0);
    }

    public static int process(int i, int[] arr, int index) {
        if (i == 1) {
            return 1;
        }
        int nextIndex = getNextIndex(arr.length, index);
        return (process(i - 1, arr, nextIndex) //此时指的就是新编号
                + arr[index] - 1) % i //arr【index】指的就是原来的M
                + 1;
    }

    public static int getNextIndex(int length, int index) {
        return index == length - 1 ? 0 : index + 1; //返回的是报数数组相应的下标
    }
}
