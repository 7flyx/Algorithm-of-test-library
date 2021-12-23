package demo;
import java.util.*;
import java.io.*;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 21:25
 * Description: 大楼轮廓问题
 * 给定一个N \times 3N×3的矩阵matrix，对于每一个长度为3的小数组arr，都表示一个大楼的三个数据。arr[0]表示大楼的左边界，
 * arr[1]表示大楼的右边界，arr[2]表示大楼的高度(一定大于0)。每座大楼的地基都在X轴上，大楼之间可能会有重叠，请返回整体的轮廓线数组
 * [要求]
 * 时间复杂度为O(n \log n)O(nlogn)
 */


public class Code04_FloorOutline {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine()); //数据的行数
        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            String[] str = in.readLine().split(" ");
            for (int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(str[j]);
            }
        }
        List<List<Integer>> res = getOutline(arr);
        StringBuilder sb = new StringBuilder();
        for (List<Integer> list : res) {
            for (Integer num : list) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        in.close();
    }

    private static class Node {
        public int x;
        public boolean isAdd; //true是add，false是del
        public int height; //当前节点的高度
        public Node(int x, boolean isAdd, int height) {
            this.x = x;
            this.isAdd = isAdd;
            this.height = height;
        }
    }

    private static class NodeCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x; //先以x排升序
            }
            if (o1.isAdd == o2.isAdd) { //相等时，都无所谓
                return 0;
            }
            return o1.isAdd? -1 : 1; //add在前面，false在后面
        }
    }

    public static List<List<Integer>> getOutline(int[][] arr) {
        if (arr == null) {
            return new ArrayList<List<Integer>>();
        }

        //先对传入的参数，进行一个包装。含有add和del参数的节点Node
        Node[] nodes = new Node[arr.length * 2]; //起点和终点都需要记录
        for (int i = 0; i < arr.length; i++) {
            nodes[i * 2] = new Node(arr[i][0], true, arr[i][2]);
            nodes[i * 2 + 1] = new Node(arr[i][1], false, arr[i][2]);
        }

        //再对nodes数组进行排序，以x排升序。x相等时，add在前面
        Arrays.sort(nodes, new NodeCompare());

        //开始遍历nodes数组，并且用TreeMap记录相应的数据
        //因为需要随时取当前范围内的最大值，所有用TreeMap
        TreeMap<Integer, Integer> curMaxHeight = new TreeMap<>();//第一参数是高度，第二参数是这个高度出现的次数
        TreeMap<Integer, Integer> heightOfX = new TreeMap<>(); //x点，所对应的高度
        for (int i = 0; i < nodes.length; i++) {
            //首先是调整高度
            if (nodes[i].isAdd) { //表示增加
                if (!curMaxHeight.containsKey(nodes[i].height)) {
                    curMaxHeight.put(nodes[i].height, 1); //添加新的高度
                } else { //已有当前高度，则次数加1
                    curMaxHeight.put(nodes[i].height, curMaxHeight.get(nodes[i].height) + 1);
                }
            } else {//表示删除
                if (curMaxHeight.get(nodes[i].height) == 1) {
                    curMaxHeight.remove(nodes[i].height); //直接删除这个高度
                } else { //已有当前高度，则次数减1
                    curMaxHeight.put(nodes[i].height, curMaxHeight.get(nodes[i].height) - 1);
                }
            }

            //高度调整好之后，就是填写当前x的高度
            if (curMaxHeight.isEmpty()) { //空表，则当前节点的高度就是0
                heightOfX.put(nodes[i].x, 0);
            } else {
                heightOfX.put(nodes[i].x, curMaxHeight.lastKey()); //当前表的最大高度值
            }
        }

        //至此，所有的x的高度就在heightOfX表中
        List<List<Integer>> res = new ArrayList<>();
        int start = 0; //起点
        int preHeight = 0;//上一个高度
        Set<Map.Entry<Integer, Integer>> entries = heightOfX.entrySet();
        for(Map.Entry<Integer, Integer> entry : entries) {
            int end = entry.getKey(); //x 点
            int height = entry.getValue(); //高度
            if (height != preHeight) { //与上一节点高度不等
                if (preHeight != 0) {
                    res.add(new ArrayList<Integer>(Arrays.asList(start, end, preHeight)));
                }
                //更新起点和高度值
                start = end;
                preHeight = height;
            }
        }
        return res;
    }
}
