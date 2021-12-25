package backtrackingarithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:35
 * Description:腐烂的橘子
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotting-oranges
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code13_BadOrange {
    class Solution {
        public int orangesRotting(int[][] grid) {
            if (grid == null) {
                return -1;
            }

            //首先统计有多少个橘子
            int orange = 0; //好的橘子数量
            List<Node> badOrange = new ArrayList<>();
            boolean[][] isBad = new boolean[grid.length][grid[0].length]; //坏橘子的位置
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) orange++;
                    if (grid[i][j] == 2) {
                        badOrange.add(new Node(i, j));
                        isBad[i][j] = true;
                    }
                }
            }

            if (orange == 0) {//一个好的橘子都没有的话
                return 0;
            }

            int option = 0;
            while (orange > 0) {
                int tmp = 0;
                List<Node> list = new ArrayList<>();
                for (Node node : badOrange) {
                    tmp += infection(grid, list, isBad, node.x, node.y);
                }
                if (tmp == 0) {//经过操作，一个橘子都没感染到
                    break;
                }
                badOrange.addAll(list); //将这一次感染的橘子，放入表中
                orange -= tmp;
                option++;
            }
            return orange == 0 ? option : -1;
        }

        //感染函数-对一个位置的橘子，向四处进行感染
        private int infection(int[][] grid, List<Node> list, boolean[][] isBad, int x, int y) {
            int res = 0; //感染到的橘子数量
            if (x - 1 >= 0 && grid[x - 1][y] == 1 && !isBad[x - 1][y]) {
                res++;
                isBad[x - 1][y] = true;
                list.add(new Node(x - 1, y)); //将新感染的橘子，放入表中
            }
            if (x + 1 < grid.length && grid[x + 1][y] == 1 && !isBad[x + 1][y]) {
                res++;
                isBad[x + 1][y] = true;
                list.add(new Node(x + 1, y));
            }
            if (y - 1 >= 0 && grid[x][y - 1] == 1 && !isBad[x][y - 1]) {
                res++;
                isBad[x][y - 1] = true;
                list.add(new Node(x, y - 1));
            }
            if (y + 1 < grid[0].length && grid[x][y + 1] == 1 && !isBad[x][y + 1]) {
                res++;
                isBad[x][y + 1] = true;
                list.add(new Node(x, y + 1));
            }
            return res;
        }

        private class Node {
            public int x;
            public int y;

            public Node(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }
}
