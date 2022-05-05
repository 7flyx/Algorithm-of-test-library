package class11;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-05-05
 * Time: 15:58
 * Description: 折纸条问题。给你一个纸条朝向自己进行对折，再给定一个N，表示对折次数。
 * 问：对折N次后，纸条从上到下，折痕的凹凸情况。
 */
public class Code07_IsDownOrUp {
    public static void main(String[] args) {
        int n = 3;
        process(0, n, true);
    }

    /**
     * @param level 当前层数
     * @param N 最大层数
     * @param isDown true表示凹，false 表示凸
     */
    public static void process(int level, int N, boolean isDown) {
        if (level == N) {
            return;
        }
        process(level + 1, N, true);
        System.out.print(isDown? "凹 ": "凸 ");
        process(level + 1, N, false);
    }
}
