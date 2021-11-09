package demo;

import javafx.scene.layout.Priority;

import java.util.*;
import java.util.function.DoubleToIntFunction;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 19:48
 * Description:
 * 给定一个字符串类型的数组arr， 求其中出现次数最多的前K个。
 */
public class Code05_StrTopK {
    public static void main(String[] args) {
        String[] array = {"hello", "hello", "world", "nihao", "enhance", "enhance", "encourage"};
        String[] res = calcStrNum(array, 3);
        for (String s : res) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static String[] calcStrNum(String[] array, int k) {
        if (array == null || array.length == 0 || k < 1) {
            return new String[] {""};
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String s : array) {
            if (!map.containsKey(s)) {
                map.put(s, 1);
            } else {
                map.put(s, map.get(s) + 1);
            }
        }


        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>((o1,o2) -> o1.getValue() - o2.getValue());
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> tmp = iterator.next();
            if (minHeap.size() < k) {
                minHeap.offer(tmp);
            } else {
                if(tmp.getValue() > minHeap.peek().getValue()) {
                    minHeap.poll();
                    minHeap.offer(tmp);
                }
            }
        }

        String[] res = new String[minHeap.size()];
        int index = 0;
        while (!minHeap.isEmpty()) {
            res[index++] = minHeap.poll().getKey();
        }
        return res;
    }
}
