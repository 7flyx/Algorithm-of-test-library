package class14;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-24
 * Time: 22:46
 * Description: LeetCode305 岛屿数量2
 * 假设你设计一个游戏，用一个 m 行 n 列的 2D 网格来存储你的游戏地图。
 * 起始的时候，每个格子的地形都被默认标记为「水」。我们可以通过使用 addLand 进行操作，将位置 (row, col) 的「水」变成「陆地」。
 * 你将会被给定一个列表，来记录所有需要被操作的位置，然后你需要返回计算出来 每次 addLand 操作后岛屿的数量。
 * 注意：一个岛的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。你可以假设地图网格的四边均被无边无际的「水」所包围。
 * 示例:
 * 输入: m = 3, n = 3,
 * positions = [[0,0], [0,1], [1,2], [2,1]]
 * 输出: [1,1,2,3]
 * 解析:
 * 起初，二维网格 grid 被全部注入「水」。（0 代表「水」，1 代表「陆地」）
 * 0 0 0
 * 0 0 0
 * 0 0 0
 * <p>
 * 操作 #1：addLand(0, 0) 将 grid[0][0] 的水变为陆地。
 * 1 0 0
 * 0 0 0 Number of islands = 1
 * 0 0 0
 * <p>
 * 操作 #2：addLand(0, 1) 将 grid[0][1] 的水变为陆地。
 * 1 1 0
 * 0 0 0 岛屿的数量为 1
 * 0 0 0
 * <p>
 * 操作 #3：addLand(1, 2) 将 grid[1][2] 的水变为陆地。
 * 1 1 0
 * 0 0 1 岛屿的数量为 2
 * 0 0 0
 * <p>
 * 操作 #4：addLand(2, 1) 将 grid[2][1] 的水变为陆地。
 * 1 1 0
 * 0 0 1 岛屿的数量为 3
 * 0 1 0
 * <p>
 * 拓展：
 * 你是否能在 O(k log mn) 的时间复杂度程度内完成每次的计算？
 * （k 表示 positions 的长度）
 */
public class Code04_NumberIsland2 {
    class Solution {
        public List<Integer> numIslands2(int m, int n, int[][] position) {
            List<Integer> ans = new ArrayList<>();
            UnionSet unionSet = new UnionSet(m, n);
            for (int[] arr : position) {
                unionSet.setIsland(arr[0], arr[1]); // 将当前位置设置成岛，然后去跑上下左右四个方向
                ans.add(unionSet.getSets());
            }
            return ans;
        }

        private static class UnionSet {
            private int[] parent;
            private int[] helpStack;
            private int[] size;
            private int sets;
            private final int col; // 总的列数
            private final int row; // 总的行数

            public UnionSet(int m, int n) {
                int length = m * n;
                col = n;
                row = m;
                parent = new int[length];
                helpStack = new int[length];
                size = new int[length];
                sets = 0;
            }

            private int getIndex(int i, int j) {
                return i * col + j;
            }

            private int findFather(int index) {
                int hi = 0;
                while (index != parent[index]) {
                    helpStack[hi++] = index;
                    index = parent[index];
                }
                // 路径压缩
                while (hi-- > 0) {
                    parent[helpStack[hi]] = index;
                }
                return index;
            }

            public void union(int r1, int c1, int r2, int c2) {
                if (r1 < 0 || r1 == row || c1 < 0 || c1 == col || r2 < 0 || r2 == row || c2 < 0 || c2 == col) {
                    return;
                }
                int index1 = getIndex(r1, c1);
                int index2 = getIndex(r2, c2);
                if (size[index1] == 0 || size[index2] == 0) {
                    return;
                }

                int f1 = findFather(index1);
                int f2 = findFather(index2);
                if (f1 != f2) {
                    if (size[f1] >= size[f2]) {
                        size[f1] += size[f2];
//                        size[f2] = 0; // 此处的size不用归0，代表着当前节点已经被划分到了某一个集合中
                        parent[f2] = f1;
                    } else {
                        size[f2] += size[f1];
//                        size[f1] = 0; // 此处的size不用归0，代表着当前节点已经被划分到了某一个集合中
                        parent[f1] = f2;
                    }
                    sets--; // 两个集合合并之后，总的集合数就要减-
                }

            }

            public void setIsland(int i, int j) {
                int index = getIndex(i, j);
                if (size[index] == 0) { // 说明还没有来到过这个点
                    size[index] = 1;
                    parent[index] = index;
                    sets++; // 总的集合数+1
                    // 去跑当前点的上下左右四个方向
                    union(i - 1, j, i, j); // 上
                    union(i + 1, j, i, j); // 下
                    union(i, j - 1, i, j); // 左
                    union(i, j + 1, i, j); // 右
                }
            }

            public int getSets() {
                return sets;
            }
        }
    }
}
