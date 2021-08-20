import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by flyx
 * Description:
 * User: 听风
 * Date: 2021-08-20
 * Time: 10:39
 */
public class Demo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("abcdef");
        sb.length();
        Deque<String> queue = new LinkedList<>();

        sb.delete(0, 3);
        System.out.println(sb.toString());
    }
}
