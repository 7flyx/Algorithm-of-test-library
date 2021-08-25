import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by flyx
 * Description: 测试红黑树
 * User: 听风
 * Date: 2021-08-25
 * Time: 10:56
 */
public class Demo {
    public static void main(String[] args) throws FileNotFoundException {
        RBTree rbt = new RBTree();
        AVLTree avl = new AVLTree();

        int n = 20000000;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int tmp = random.nextInt(Integer.MAX_VALUE);
            list.add(tmp);
        }

        long startedTime = System.nanoTime();
        for (int i : list) {
            avl.add(i);
        }
        long endTime = System.nanoTime(); //单位是纳秒
        double time = (endTime - startedTime) / 1000000000.0;
        System.out.println("avl: " + time + "s");


        startedTime = System.nanoTime();
        for(int i : list) {
            rbt.add(i);
        }
        endTime = System.nanoTime(); //单位是纳秒
        time = (endTime - startedTime) / 1000000000.0;
        System.out.println("rbt: " + time + "s");
    }
}
