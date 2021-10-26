import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-24
 * Time: 15:59
 * Description:
 */
public class demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        HashSet<Character> set = new HashSet<>();
        int N = str1.length();
        int M = str2.length();
        while (--M >= 0) {
            if (!set.contains(str2.charAt(M))) {
                set.add(str2.charAt(M));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (!set.contains(str1.charAt(i)) &&
                    sb.indexOf(str1.charAt(i) + "") == -1) {
                sb.append(str1.charAt(i));
            }
        }
        System.out.println(sb.toString().toUpperCase());
    }
}
