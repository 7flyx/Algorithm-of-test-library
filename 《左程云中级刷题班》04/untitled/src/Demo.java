/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-11
 * Time: 22:50
 * Description:
 */

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Demo {
    public static int charCount(char c,String str) {
        int count = 0;
        for(int i=0;i<str.length();i++) {
            if(c==str.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static String moveStr(String str,int m) {
        String strs1 = str.substring(0, m);
        String strs2 = str.substring(m, str.length());
        return strs2+strs1;
    }

    public static String sort(String str) {
        char[] arr = str.toCharArray();
        Arrays.sort(arr,1,str.length()-1);
        int tmp = 0;
        for(int i=1,j=str.length()-2; i<=j; i++,j--) {
            if(arr[i]<arr[j]) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = (char) tmp;
            }
        }
        String strs = new String(arr);
        return strs;
    }
    public static String delStar(String str) {
        int a = 0;
        int m = str.length();
        char[] arr = str.toCharArray();
        for(int i=arr.length-1;i>0;i--) {
            if((arr[i] - '*') == 0)
                a++;
        }
        String strs9 = str.substring(0,str.length()-a);

        return strs9;
    }

    public static void main1(String[] args) {

        String strs3 = "∗∗∗∗A∗BC∗DEF∗G******";
        String strs4 = delStar(strs3);
        System.out.println(strs4);

		/*String str = "asdf153asdee";
		char c = 'a';
		int ret = charCount(c, str);
		System.out.println(ret);

		String str1 = moveStr(str, 2);
		System.out.println(str1);*/

		/*String strs1 = "CEAedca";
		String str2 = sort(strs1);
		System.out.println(str2);*/

    }

    private static int index = 0;
    public static void main(String[] args) {
        int n = 6;

        System.out.println(process(1));
    }

    public static int process(int n) {
        if (n <= 2) {
            return n == 2 ? 5 : 1;
        }
        int count = 4; //计算第几层
        int res = 6;
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> help = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1);
        while (res < 10_0000_0000) {
            help.add(1); //第一个位置都是1
            for (int i = 0; i < count - 2; i++) {
                int tmp = list.get(i) + list.get(i + 1);
                if (tmp == n) {
                    res = res + i + 2;
                    return res;
                }
                help.add(tmp);
            }
            help.add(1);
            res += count;
            count++;
            ArrayList<Integer> list1 = list;
            list = help;
            help = list1;
            help.clear(); //清空
        }
        return res;
    }
}
