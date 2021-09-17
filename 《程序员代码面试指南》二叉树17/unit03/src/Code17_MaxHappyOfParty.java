import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description: 派对的最大快乐值
 * User: 听风
 * Date: 2021-09-17
 * Time: 15:06
 */

public class Code17_MaxHappyOfParty {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        TreeNode root = createTree(in, n);

        System.out.println(calcPartyHappy(root));
        in.close();
    }

    private static class TreeNode {
        public int happy;
        public List<TreeNode> next; //存储直接下级节点

        public TreeNode(int happy) {
            this.happy = happy;
            next = new ArrayList<>();
        }
    }

    public static TreeNode createTree(BufferedReader in, int n) throws IOException {
        TreeNode[] array = new TreeNode[n];
        String[] happys = in.readLine().split(" "); //happy值数组
        for (int i = 0; i < n; i++) {
            array[i] = new TreeNode(Integer.parseInt(happys[i]));
        }

        for (int i = 0; i < n - 1; i++) {
            String[] nums = in.readLine().split(" ");
            int leader = Integer.parseInt(nums[0]);
            int staff = Integer.parseInt(nums[1]);
            array[leader - 1].next.add(array[staff - 1]);
        }
        return array[n - 1];
    }

    private static class ReturnHappy {
        public int yesX; //要当前happy的情况
        public int noX; //不要当前happy的情况

        public ReturnHappy(int yesX, int noX) {
            this.yesX = yesX;
            this.noX = noX;
        }
    }

    public static int calcPartyHappy(TreeNode node) {
        if (node == null) {
            return 0;
        }

        ReturnHappy data = process(node);
        return Math.max(data.yesX, data.noX);
    }

    private static ReturnHappy process(TreeNode node) {
        int yesX = node.happy;
        int noX = 0;
        if (node.next.isEmpty()) { //没有直接下级节点
            return new ReturnHappy(yesX, noX);
        }

        //遍历所有下级节点，返回数值，添加至相应的位置
        for (TreeNode next : node.next) {
            //向下级节点索要数据
            ReturnHappy data = process(next);
            yesX += data.noX; //加上下一节点不要的总和
            noX += Math.max(data.yesX, data.noX); //当前节点不要，下级节点可要可不要，取最大值
        }

        return new ReturnHappy(yesX, noX);
    }
}
