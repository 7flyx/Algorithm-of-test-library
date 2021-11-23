package demo;

/**
 *  给定一个字符串，如果该字符串符合人们日常书写的一个整数的形式，返回int
 *  了类型的这个数，如果不符合，请抛出异常
 */
public class Code01_StringToNumber {
    public static void main(String[] args) {
        String str = "214.5";
        System.out.println(Integer.MAX_VALUE);
        System.out.println(strToNumber(str));
    }

    public static int strToNumber(String str) {
        if (str == null || str.length() < 1) {
            throw new RuntimeException("输入参数异常");
        }

        //首先判断这个参数是否合法，然后再去转
        char[] ch = str.toCharArray();
        if (!isValid(ch)) {
            throw new RuntimeException("输入参数异常");
        }

        int res = 0;
        int tmp = 0;
        //以下两个参数，用于判断是否会溢出的
        int numBorder1 = Integer.MIN_VALUE / 10; //负数的前N-1位数
        int numBorder2 = Integer.MIN_VALUE % 10; //负数的最后一位数
        boolean flag = ch[0] == '-';
        for (int i = flag? 1 : 0; i < ch.length; i++) {
            tmp = '0' - ch[i]; //拿到当前数字的负数形式

            //在归并到res时，应提前判断是否会溢出
            if (res < numBorder1 || (res == numBorder1 && tmp < numBorder2)) {
                throw new RuntimeException("参数将会溢出");
            }
            res = res * 10 + tmp;
        }
        return flag? res : -res;
    }

    public static boolean isValid(char[] ch) {
        if (ch[0] == '-' && 1 < ch.length && (ch[1] <= '0' || ch[1] > '9')) {
            return false;
        }
        if ((ch[0] < '0' || ch[0] > '9') && ch[0] != '-') {
            return false;
        }
        boolean flag = ch[0] == '-';
        for (int i = flag? 1 : 0; i < ch.length; i++) {
            if (ch[i] < '0' || ch[i] > '9') {
                return false;
            }
        }
        return true;
    }
}
