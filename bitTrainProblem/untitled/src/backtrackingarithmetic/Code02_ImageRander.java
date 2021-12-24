package backtrackingarithmetic;

import java.util.Stack;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-23
 * Time: 22:09
 * Description:图像渲染
 * 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
 * 给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
 * 为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上像素值与初始坐标相同的相连像素点，
 * 接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。
 * 将所有有记录的像素点的颜色值改为新的颜色值。
 * 最后返回经过上色渲染后的图像。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flood-fill
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Code02_ImageRander {
    class Solution {
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            if (image == null || sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length) {
                return image;
            }

            //boolean[][] isVisit = new boolean[image.length][image[0].length];
            //process(image, isVisit, sr, sc,newColor, image[sr][sc]);
            process(image, sr, sc, newColor);
            return image;
        }

        //深度搜索
        private void process(int[][] image, boolean[][] isVisit, int i, int j, int newColor, int flag) {
            if (i < 0 || i >= image.length || j < 0 || j >= image[0].length || isVisit[i][j] || image[i][j] != flag) {
                return;
            }

            image[i][j] = newColor;
            isVisit[i][j] = true;
            process(image, isVisit, i + 1, j, newColor, flag);
            process(image, isVisit, i - 1, j, newColor, flag);
            process(image, isVisit, i, j + 1, newColor, flag);
            process(image, isVisit, i, j - 1, newColor, flag);
        }

        //广度搜索
        private void process(int[][] image, int sr, int sc, int newColor) {
            Stack<Integer> stack = new Stack<>();
            stack.push(sr);
            stack.push(sc);
            int oldColor = image[sr][sc];
            while (!stack.isEmpty()) {
                sc = stack.pop();//行
                sr = stack.pop(); //列
                if (sc >= 0 && sr >= 0 && sr < image.length && sc < image[0].length &&
                        image[sr][sc] == oldColor && oldColor != newColor) {
                    image[sr][sc] = newColor;
                    //左
                    stack.push(sr - 1);
                    stack.push(sc);
                    //右
                    stack.push(sr + 1);
                    stack.push(sc);
                    //上
                    stack.push(sr);
                    stack.push(sc + 1);
                    //下
                    stack.push(sr);
                    stack.push(sc - 1);
                }
            }
        }
    }
}
