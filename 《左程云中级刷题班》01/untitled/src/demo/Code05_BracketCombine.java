package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-29
 * Time: 17:18
 * Description:括号匹配问题
 * 一个完整的括号字符串定义规则如下:
 * ①空字符串是完整的。
 * ②如果s是完整的字符串， 那么(s)也是完整的。
 * ③如果s和t是完整的字符串， 将它们连接起来形成的st也是完整的。
 * 例如， "(()())", ""和"(())()"是完整的括号字符串， "())(", "()(" 和 ")"
 * 是不完整的括号字符串。
 * 牛牛有一个括号字符串s,现在需要在其中任意位置尽量少地添加括号,将其转化
 * 为一个完整的括号字符串。 请问牛牛至少需要添加多少个括号。
 */

public class Code05_BracketCombine {
    public static void main(String[] args) {
        String str = "(()()())))((((((";
        System.out.println(countBrackets(str));
    }

    public static int countBrackets(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }
        char[] ch = str.toCharArray();
        int count = 0;//左括号++， 右括号--
        int response = 0; //需要添加的括号数
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '(') {
                count++;
            } else { //是右括号
                if (count == 0) {
                    response++;
                } else {
                    count--;
                }
            }
        }
        return response + count;
    }
}
