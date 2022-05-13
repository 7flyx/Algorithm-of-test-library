package class14;

import java.util.*;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-13
 * Time: 19:45
 * Description: LeetCode547. 省份数量
 */
public class Code02_ProvinceSum {
    // 用哈希表实现的并查集
    class Solution {
        private HashMap<Integer, Integer> parent;
        private HashMap<Integer, Integer> sizeMap;

        public int findCircleNum(int[][] isConnected) {
            if (isConnected == null || isConnected.length == 0 || isConnected[0].length == 0) {
                return 0;
            }
            parent = new HashMap<>();
            sizeMap = new HashMap<>();
            for (int i = 0; i < isConnected.length; i++) {
                parent.put(i + 1, i + 1); // 每个城市以自己为中心
                sizeMap.put(i + 1, 1);
            }
            for (int i = 0; i < isConnected.length; i++) {
                for (int j = 0; j < isConnected[0].length; j++) {
                    if (isConnected[i][j] == 1 && !isSameSet(i + 1, j + 1)) {
                        union(i + 1, j + 1);
                    }
                }
            }
            return sizeMap.size();
        }

        private void union(int a, int b) {
            int parentA = findFather(a);
            int parentB = findFather(b);
            if (parentA != parentB) {
                int sizeA = sizeMap.get(parentA);
                int sizeB = sizeMap.get(parentB);
                int big = sizeA >= sizeB? parentA : parentB;
                int small = sizeA < sizeB? parentA : parentB;
                parent.put(small, big);
                sizeMap.put(big, sizeA + sizeB);
                sizeMap.remove(small);
            }
        }

        private boolean isSameSet(int a, int b) {
            return findFather(a) == findFather(b);
        }

        private int findFather(int num) {
            Stack<Integer> stack = new Stack<>();
            while (num != parent.get(num)) {
                stack.push(num);
                num = parent.get(num);
            }

            // 路径压缩
            while (!stack.isEmpty()) {
                parent.put(stack.pop(), num);
            }
            return num;
        }
    }

    // 用数组实现的并查集
    class Solution2 {
        public int findCircleNum(int[][] isConnected) {
            if (isConnected == null || isConnected.length == 0 || isConnected[0].length == 0) {
                return 0;
            }
            int[] block = new int[isConnected.length + 1]; // 下标表示城市，数值表示这个城市集群的代表节点
            for (int i = 1; i < block.length; i++) {
                block[i] = i; // 初始化，每个城市是独立的
            }

            for (int i = 0; i < isConnected.length; i++) {
                for (int j = 0; j < isConnected[0].length; j++) {
                    if (isConnected[i][j] == 1 && !isSameSet(block, i + 1, j + 1)) {
                        union(block, i + 1, j + 1);
                    }
                }
            }
            // 将全部的城市，重新跑一遍 findFather，全部要进行路径压缩
            // 使其父节点就是当前集合的代表节点
            // for (int i = 1; i < block.length; i++) {
            //     findFather(block, i);
            // }

            // 遍历数组，就能得出有多少 相连的城市
            HashSet<Integer> set = new HashSet<>();
            for (int i = 1; i < block.length; i++) {
                findFather(block, i);
                set.add(block[i]);
            }
            return set.size();
        }

        private void union(int[] block, int a, int b) {
            int parentA = findFather(block, a);
            int parentB = findFather(block, b);
            if (parentA != parentB) {
                // 按照原先的设计理念，应该是小的集合挂在大的集合上，减少一定的“链”的长度
                // 此处是用数组写的，并没有实现集合大小的功能，当然也可以加一个哈希表记录
                block[parentA] = parentB;
            }
        }

        private boolean isSameSet(int[] block, int a, int b) {
            return findFather(block, a) == findFather(block, b);
        }

        private int findFather(int[] block, int num) {
            Stack<Integer> stack = new Stack<>();
            while (num != block[num]) {
                stack.push(num);
                num = block[num];
            }
            // 路径压缩
            while (!stack.isEmpty()) {
                block[stack.pop()] = num;
            }
            return num;
        }
    }

    // 深搜或者广搜
    static class Solution3 {
        public int findCircleNum(int[][] isConnected) {
            int rows = isConnected.length;
            boolean[] visited = new boolean[rows];
            int count = 0;
            for (int i = 0; i < rows; i++) {
                if (!visited[i]) {
                    dfs(isConnected, visited, rows, i);
                    count++;
                }
            }
            return count;
        }

        private void dfs(int[][] isConnected, boolean[] visited, int rows, int i) {
            for (int j = 0; j < rows; j++) { // 遍历的是纵向，就是其他点走向当前点，也就是 入度
                if (isConnected[i][j] == 1 && !visited[j]) {
                    visited[j] = true;
                    dfs(isConnected, visited, rows, j);
                }
            }
        }

        // BFS
        public int findCircleNum2(int[][] isConnected) {
            int rows = isConnected.length;
            boolean[] visited = new boolean[rows];
            int count = 0;
            Queue<Integer> queue = new LinkedList<Integer>();
            for (int i = 0; i < rows; i++) {
                if (!visited[i]) {
                    queue.offer(i);
                    while (!queue.isEmpty()) {
                        int j = queue.poll();
                        visited[j] = true;
                        for (int k = 0; k < rows; k++) {
                            if (isConnected[j][k] == 1 && !visited[k]) {
                                queue.offer(k);
                            }
                        }
                    }
                    count++;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[][] arr = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        Solution3 solution3 = new Solution3();
        int circleNum = solution3.findCircleNum(arr);
        System.out.println(circleNum);
    }
}
