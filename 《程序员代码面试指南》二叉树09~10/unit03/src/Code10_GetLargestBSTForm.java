import java.io.*;
import java.util.*;

/**
 * Created by flyx
 * Description: 返回一颗二叉树中符合搜索二叉树条件的最大拓扑结构，返回值是节点个数
 * User: 听风
 * Date: 2021-09-06
 * Time: 11:01
 */

public class Code10_GetLargestBSTForm {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = in.readLine().split(" ");
        int n = Integer.parseInt(nums[0]);
        TreeNode root = createTree(in);
        int res = bestTopoSize1(root);

        System.out.println(res);

        in.close();
    }

    private static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val){
            this.val = val;
        }
    }

    public static TreeNode createTree(BufferedReader in) throws IOException {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack<>();
        String[] values = in.readLine().split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        if (!values[1].equals("0")) {
            root.left = new TreeNode(Integer.parseInt(values[1]));
            stack.push(root.left);
        }
        if (!values[2].equals("0")) {
            root.right = new TreeNode(Integer.parseInt(values[2]));
            stack.push(root.right);
        }

        while (!stack.isEmpty()) {
            values = in.readLine().split(" ");
            while (!stack.isEmpty() && stack.peek().val != Integer.parseInt(values[0])) {
                help.push(stack.pop());
            }
            TreeNode cur = stack.pop();
            if (!values[1].equals("0")) {
                cur.left = new TreeNode(Integer.parseInt(values[1]));
                stack.push(cur.left);
            }
            if (!values[2].equals("0")) {
                cur.right = new TreeNode(Integer.parseInt(values[2]));
                stack.push(cur.right);
            }
            while (!help.isEmpty()) {
                stack.push(help.pop());
            }
        }
        return root;
    }

    //从每个节点开始，看做一棵树，向下做遍历
    public static int bestTopoSize1(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int max = maxTop(node,node); //获取当前节点，往下的最大节点数
        max = Math.max(max, bestTopoSize1(node.left)); //获取左子树的
        max = Math.max(max,  bestTopoSize1(node.right)); //获取右子树的
        return max;
    }

    public static int maxTop(TreeNode h, TreeNode n) {
        if (h != null && n != null && isBSTNode(h, n, n.val)) {
            return maxTop(h, n.left) + maxTop(h, n.right) + 1;
        }
        return 0;
    }

    public static boolean isBSTNode(TreeNode h, TreeNode n, int value) {
        if (h == null) {
            return false; //没找到当前这个节点
        }
        if (h == n) { //h暂时表示祖先节点，n表示当前节点，
            return true;
        }
        //比上一节点小，就进入左子树，反之就进入右子树---n不动，是用h去寻找n
        return isBSTNode(h.val > value? h.left : h.right, n, value);
    }

    //表示每个节点的拓扑结构贡献，也就是该节点向下满足条件的节点数
    private static class NodeInfo {
        public int l;
        public int r;
        public NodeInfo(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    //从底部开始，向上返回信息。向左子树要信息，向右子树要信息
    public static int bestTopoSize2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        HashMap<TreeNode, NodeInfo> map = new HashMap<>();
        return posOrder(node, map);
    }

    public static int posOrder(TreeNode node, HashMap<TreeNode, NodeInfo> map) {
        if (node == null) {
            return 0;
        }

        //后序遍历，左子树右子树根结点
        int leftSubTree = posOrder(node.left, map);
        int rightSubTree = posOrder(node.right, map);
        //向下遍历子树，更新新的拓扑信息
        modifyMap(node.left,node.val,  map, true);
        modifyMap(node.right,node.val,  map, false);
        //建立当前节点的拓扑信息
        NodeInfo leftRecord = map.get(node.left);
        NodeInfo rightRecord = map.get(node.right);
        int leftBest = leftRecord == null? 0 : leftRecord.l + leftRecord.r + 1;
        int rightBest = rightRecord == null? 0 : rightRecord.l + rightRecord.r + 1;
        map.put(node, new NodeInfo(leftBest, rightBest));
        //判断当前节点和左右子树的，谁多，返回谁
        return Math.max(leftBest + rightBest + 1 , Math.max(leftSubTree, rightSubTree));
    }

    /**
     *  调整哈希表的数据
     * @param node 当前节点
     * @param val 当前这棵树的根结点的值（固定）
     * @param map 哈希表存储的对应 拓扑节点的数据
     * @param isLeftTree 是不是左子树
     * @return 需要被删除的节点数量
     */
    public static int modifyMap(TreeNode node, int val, HashMap<TreeNode, NodeInfo> map, boolean isLeftTree) {
        if (node == null || (!map.containsKey(node))) {
            return 0;
        }

        NodeInfo record = map.get(node);
        //违背了搜索二叉树的性质，就删除
        if ((isLeftTree && node.val > val) || (!isLeftTree && node.val < val)) {
            map.remove(node);
            return record.l + record.r + 1; //删除左右子树和本身自己这个节点
        } else {
            //如果是左子树，则需要循环查看这颗左子树的右侧节点
            //如果是右子树，则需要循环查看这颗右子树的左侧节点
            int minus = modifyMap(isLeftTree? node.right : node.left, val, map, isLeftTree); //去查找需要被删除的节点
            if (isLeftTree) {
                record.l = record.l - minus;
            } else {
                record.r = record.r - minus;
            }
            map.put(node, record);
            return minus; //向上返回需要被删除的数据量，沿途的节点都需要删除
        }
    }

}


