/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-08
 * Time: 19:49
 * Description:
 * 数据算加法密扩和展数据题压目缩二中常需要对特殊的字符串进行编码。
 * 给定的字母表A由26个小写英文字母组成， 即
 * A={a, b...z}。 该字母表产生的长序字符串是指定字符串中字母从左到右出现的次序与字母在字母表中出现
 * 的次序相同， 且每个字符最多出现1次。 例如， a， b， ab， bc， xyz等字符串是升序字符串。 对字母表A产生
 * 的所有长度不超过6的升序字符串按照字典排列编码如下： a(1)， b(2)， c(3)……， z(26)， ab(27)，
 * ac(28)……对于任意长度不超过16的升序字符串， 迅速计算出它在上述字典中的编码。
 * 输入描述：
 * 第1行是一个正整数N， 表示接下来共有N行， 在接下来的N行中， 每行给出一个字符串。 输出描述：
 * 输出N行， 每行对应于一个字符串编码。
 * 示例1:
 * 输入
 * 3
 * a b ab
 * 输出
 * 1 2 27
 */
public class Code05_CharCoding {
    public static void main(String[] args) {
        String str = "bc";
        System.out.println(calcCharCoding(str));
    }

    /**
     * 这道题以进制的思想去做的话，行不通，因为每一个字符开头，得出的字符数是不一样的
     * 只能通过递归去一个个计算
     * @param str 升序的，并且没有重复的字符
     * @return 返回最后的字符数
     */
    public static int calcCharCoding(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] ch = str.toCharArray();
        for (int i = 1; i < ch.length; i++) { //判断字符的合法性
            if (ch[i - 1] >= ch[i]) {
                return -1;
            }
        }

        int preSum = 0;
        int len = ch.length;
        for (int i = 1; i < len; i++) { //计算长度为1、2、3……一直到len-1的字符个数
            preSum += g(i); //此处的i指的就是字符的长度
        }

        int first = ch[0] - 'a' + 1; //第一个字符
        for (int i = 1; i < first; i++) {
            preSum += f(1, len); //计算第一个字符，到a字符中间的和
        }

        int pre = first;
        for (int i = 1; i < len; i++) {
            int cur = ch[i] - 'a' + 1; //第二个字符
            for (int j = pre + 1; j < cur; j++) {
                preSum += f(j, len - i); //计算i字符开头，len长度的字符
            }
            pre = cur; //更新为第一个字符
        }
        return preSum + 1;//最后的结果需要加上1
    }

    //计算以i字符开头，len为长度的全部字符个数
    public static int f(int i, int len) {
        if (len == 1) {
            return 1;
        }
        int sum = 0;
        for (int j = i + 1; j <= 26; j++) {
            sum += f(j, len - 1);
        }
        return sum;
    }

    //计算长度为len的所有字符个数
    public static int g(int len) {
        int sum = 0;
        for (int i = 1; i <= 26; i++) {
            sum += f(i, len);
        }
        return sum;
    }


}
