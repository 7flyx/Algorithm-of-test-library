package demo;

import java.util.HashMap;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-11-08
 * Time: 19:48
 * Description:
 * 给定一个字符串类型的数组arr， 求其中出现次数最多的前K个。升级版
 * 在此基础之上，还有一个方法能随时打印当前堆里的数据。printAll
 */

public class Code06_StrTopKPlus {
    public static void main(String[] args) {
        StringTopK heap = new StringTopK(3);
        heap.add("hello");
        heap.add("world");
        heap.add("hello");
        heap.add("bit");
        heap.add("june");
        heap.add("Tom");
        heap.add("Tom");
        heap.printAll();
    }

    //字符串包装成节点
    private static class Node {
        public String str;
        public int times; //频率
        public Node(String str, int times) {
            this.str = str;
            this.times = times;
        }
    }

    //此时不是一般的TopK问题，需要自己写一个堆，能够随时在添加元素的时候，进行上下调整
    private static class StringTopK {
        private Node[] heap;
        private HashMap<String, Node> strNodeMap; //对应的Node节点记录词频
        private HashMap<Node, Integer> indexOfNode; //字符串在数组中的下标
        private int usedSize;

        public StringTopK(int K) {
            heap = new Node[K];
            indexOfNode = new HashMap<>();
            strNodeMap = new HashMap<>();
        }

        public void add(String str) {
            Node cur = null;
            int preIndex = -1;
            if (!strNodeMap.containsKey(str)) {
                cur = new Node(str, 1);
                strNodeMap.put(str, cur);
                indexOfNode.put(cur, -1); //默认是-1的位置，还没在堆上
            } else {
                cur = strNodeMap.get(str);
                cur.times++; //词频++
                preIndex = indexOfNode.get(cur); //得到相应的下标
            }

            if (preIndex == -1) {//下标是-1，可能是新建的。也可能原先就没在堆上
                if (usedSize == heap.length) {
                     if (cur.times > heap[0].times) { //词频大于堆顶的数据，替换掉
                         indexOfNode.put(cur, 0);
                         indexOfNode.put(heap[0], -1);
                         heap[0] = cur; //新节点顶替上去
                         heapify(0, usedSize);
                     }
                } else {
                    indexOfNode.put(cur, usedSize);
                    heap[usedSize] = cur;
                    heapInsert(usedSize++); //向上调整
                }
            } else {//原本就已经在堆上，只需向下调整
                heapify(preIndex, usedSize);
            }
        }

        private void heapify(int index, int size) {
            int child = (index << 1) + 1;
            while (child < size) {
                int largest = child + 1 < size && heap[child].times > heap[child + 1].times?
                        child + 1 : child;
                if (heap[index].times < heap[largest].times) {
                    break;
                }

                swap(index, largest);
                index = largest;
                child = (index << 1) + 1;
            }
        }

        private void heapInsert(int index) {
            int parent = (index - 1) >> 1;
            while (parent >= 0 && heap[index].times < heap[parent].times) {
                swap(index, parent);
                index = parent;
                parent = (index - 1) >> 1;
            }
        }

        private void swap(int L, int R) {
            Node tmp = heap[L];
            heap[L] = heap[R];
            heap[R] = tmp;
            indexOfNode.put(heap[R], R);
            indexOfNode.put(heap[L], L); //下标的表中也应修改相应的值
        }

        public void printAll() {
            for (Node node : heap) {
                System.out.print(node.str + " ");
            }
        }
    }
}
