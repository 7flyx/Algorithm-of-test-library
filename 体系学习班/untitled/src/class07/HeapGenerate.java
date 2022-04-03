package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-03
 * Time: 16:44
 * Description: 手动改堆--核心就在于有一张表，记录每个节点的下标。
 */
public class HeapGenerate<T> {
    private ArrayList<T> arr; // 存储节点的数组
    private HashMap<T, Integer> indexOfMap; // 存储每个节点在堆上的下标
    private int size; // 堆的大小
    private Comparator<? super T> comp; // 比较器

    public HeapGenerate(Comparator<? super T> comp) {
        this.arr = new ArrayList<>();
        this.indexOfMap = new HashMap<>();
        this.size = 0;
        this.comp = comp;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> getAllElements() {
        List<T> list = new ArrayList<>();
        for (T v : arr) {
            list.add(v);
        }
        return list;
    }

    public boolean contains(T obj) {
        return indexOfMap.containsKey(obj); // 查看当前堆中是否有该对象
    }

    public T peek() {
        return arr.get(0);
    }

    public void add(T value) {
        this.arr.add(value);
        this.indexOfMap.put(value, size); // 存储下标值
        heapInsert(size++); // 往上调整
    }

    // 弹出堆顶结果
    public T poll() {
        T res = arr.get(0);
        swap(0, size - 1); // 第一个数据和最后一个数据进行交换
        indexOfMap.remove(res); // 删除res对应的下标
        arr.remove(--size); // 删除在数组上的数据
        heapify(0); // 向下调整
        return res;
    }

    // 手改堆的核心，能删除非堆顶元素
    public void remove(T obj) {
        T replace = arr.get(size - 1); // 拿到最后一个元素
        int index = indexOfMap.get(obj);
        indexOfMap.remove(obj); // 删除在表中的下标
        arr.remove(--size); // 删除数组中的最后一个元素
        if (replace != obj) { // 被删除的元素并不是数组中的最后一个元素
            arr.set(index, replace);
            indexOfMap.put(replace, index); // 新的下标
            resign(replace);
        }
    }

    // 手改堆的核心方法
    public void resign(T value) { // 根据对象，获取对象在数组中的下下标，从而进行调整
        heapInsert(indexOfMap.get(value));
        heapify(indexOfMap.get(value)); // 二者，只可能有一个会调用，只可能向上或向下
    }

    private void heapify(int i) {
        int left = (i << 1) + 1;
        while (left < size) {
            int maxChild = left + 1 < size && comp.compare(arr.get(left + 1), arr.get(left)) < 0?
                    left + 1 : left;
            maxChild = comp.compare(arr.get(maxChild), arr.get(i)) < 0? maxChild : i; // 跟父节点做判断
            if (maxChild == i) {
                break;
            }
            swap(i, maxChild);
            i = maxChild;
            left = (i << 1) + 1; // 再次刷新左孩子
        }
    }

    // 往上走，调整堆结构
    private void heapInsert(int i) {
        // 根据自定义的比较器进行比较
        // 此处除以2，用位运算代替，要判断i是大于0才行
        while (i > 0 && comp.compare(arr.get(i), arr.get((i - 1) >> 1)) < 0) {
            swap(i, (i - 1) >> 1);
            i = (i - 1) >> 1;
        }
    }

    // 不仅要更新在数组上的值，还要更新indexOfMap中的值
    private void swap(int up, int down) {
        T o1 = arr.get(up);
        T o2 = arr.get(down);
        indexOfMap.put(o1, down);
        indexOfMap.put(o2, up);
        arr.set(up, o2); // 更新
        arr.set(down, o1); // 更新
    }

    public static void main(String[] args) {
        HeapGenerate<Node> heap = new HeapGenerate<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.value - o2.value;
            }
        });

        heap.add(new Node(5));
        heap.add(new Node(1));
        heap.add(new Node(2));
        heap.add(new Node(3));
        heap.add(new Node(7));
        heap.add(new Node(10));
        List<Node> list = heap.getAllElements();
        System.out.println(heap.peek().value);
        heap.poll(); // 弹出堆顶
        System.out.println(heap.peek().value);
    }
}
