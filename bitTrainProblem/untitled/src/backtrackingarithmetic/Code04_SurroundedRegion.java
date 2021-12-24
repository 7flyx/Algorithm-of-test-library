package backtrackingarithmetic;

import java.util.Arrays;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:17
 * Description:被围绕的区域
 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 * LeetCode：https://leetcode-cn.com/problems/surrounded-regions/
 */
public class Code04_SurroundedRegion {

    public static void main(String[] args) {
        char[][] board = {{'X','X','X','X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}};
        char[][] board2 = {{'X','O','X'},
                {'X','O','X'},
                {'X','O','X'}};
        char[][] board3 = {
                {'O','X','X','O','X'},
                {'X','O','O','X','O'},
                {'X','O','X','O','X'},
                {'O','X','O','O','O'},
                {'X','X','O','X','O'}};
        solve2(board3);
        System.out.println(Arrays.toString(board3[0]));
        System.out.println(Arrays.toString(board3[1]));
        System.out.println(Arrays.toString(board3[2]));
        System.out.println(Arrays.toString(board3[3]));
        System.out.println(Arrays.toString(board3[4]));

    }

    public static void solve1(char[][] board) {
        if (board == null) {
            return;
        }

        // boolean[][] isVisit = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                boolean[][] isVisit = new boolean[board.length][board[0].length];
                if (process(board, isVisit, i, j)) {
                    changeCH(board, i, j);
                }
            }
        }
    }

    //遍历四个边，从边向里面扩展，将不能改定的点，存储在一张表里面
    public static void solve2(char[][] board) {
        boolean[][] isChange = new boolean[board.length][board[0].length];
        int row = 0;
        int col = 0;
        int size = board.length * 2 + board[0].length * 2 - 4;//边界所有的点
        while (size-- >= 0) {
            if (board[row][col] == 'O') {
                find(board, isChange, row, col);
            }
            //从左上角开始，绕边走一圈
            if (col == board[0].length - 1 && row != board.length - 1) {
                row++;
            } else if (row == 0 && col != board[0].length - 1) {
                col++;
            } else if (row == board.length - 1 && col != 0) {
                col--;
            } else {
                row--;
            }
        }
        //开始遍历整个矩阵，可以忽略四条边的遍历，省时间
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] == 'O' && !isChange[i][j]) {//可以改动，且当前字符是O
                    //changeCH(board, i, j); //改字符
                    board[i][j] = 'X'; //也可以直接改，不需要调用递归函数
                }
            }
        }
    }

    private static boolean process(char[][] board, boolean[][] isVisit, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || isVisit[i][j]) {
            return true;
        }
        if ((i == 0 || i == board.length - 1) && board[i][j] == 'O') {
            return false;
        }
        if ((j == 0 || j == board[0].length - 1) && board[i][j] == 'O') {
            return false;
        }
        isVisit[i][j] = true;
        //有一个是false，整体就是false
        if (!process(board, isVisit, i, j + 1)) return false;
        if (!process(board, isVisit, i, j - 1)) return false;
        if (!process(board, isVisit, i - 1, j)) return false;
        return process(board, isVisit, i + 1, j);
    }

    private static void changeCH(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] == 'X') {
            return;
        }
        board[i][j] = 'X';
        //changeCH(board, i, j - 1);
        changeCH(board, i, j + 1);
        //changeCH(board, i - 1, j);
        changeCH(board, i + 1, j);
    }

    private static void find(char[][] board, boolean[][] isChange, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] == 'X' || isChange[i][j]) {
            return;
        }
        isChange[i][j] = true;
        find(board, isChange, i + 1, j);
        find(board, isChange, i - 1, j);
        find(board, isChange, i, j + 1);
        find(board, isChange, i, j - 1);
    }


}
