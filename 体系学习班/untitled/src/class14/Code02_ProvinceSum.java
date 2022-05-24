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

    // 左神的思路实现的数组的并查集
    static class Solution4 {
        private int[] parent;
        private int[] size;
        private int[] helpStack; // 辅助栈，比stack要快
        private int sets; // 表示集合数量

        public Solution4(int N) {
            this.parent = new int[N];
            this.helpStack = new int[N];
            this.size = new int[N];
            this.sets = N; // 先让每个元素自己单独成为一个集合，后续在union的时候，在减回来即可
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public void union(int i, int j) {
            int f1 = findFather(i);
            int f2 = findFather(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    parent[f2] = f1;
                    size[f1] += size[f2];
                    size[f2] = 0;
                } else {
                    parent[f1] = f2;
                    size[f2] += size[f1];
                    size[f1] = 0;
                }
                sets--;
            }
        }

        private int findFather(int i) {
            int hi = 0;
            while (i != parent[i]) {
                helpStack[hi++] = i;
                i = parent[i];
            }
            // 路径压缩
            while (hi-- > 0) {
                parent[helpStack[hi]] = i;
            }
            return i;
        }

        // 当前并查集的集合个数
        public int getSets() {
            return sets;
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
//        Solution3 solution3 = new Solution3();
//        int circleNum = solution3.findCircleNum(arr);
//        System.out.println(circleNum);

        Solution4 solution4 = new Solution4(arr.length);
        // 只需要遍历右上角部分的值，应该左下角和右上角部分是对应的
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i][j] == 1) {
                    solution4.union(i, j);
                }
            }
        }
    }

}
