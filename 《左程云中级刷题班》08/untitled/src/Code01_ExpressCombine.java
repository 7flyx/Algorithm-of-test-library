/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-12-04
 * Time: 16:40
 * Description:
 * 给定一个只由 0(假)、 1(真)、 &(逻辑与)、 |(逻辑或)和^(异或)五种字符组成
 * 的字符串express， 再给定一个布尔值 desired。 返回express能有多少种组合
 * 方式， 可以达到desired的结果。
 * 【 举例】
 * express="1^0|0|1"， desired=false
 * 只有 1^((0|0)|1)和 1^(0|(0|1))的组合可以得到 false， 返回 2。
 * express="1"， desired=false
 * 无组合则可以得到false， 返回0
 */
public class Code01_ExpressCombine {
    public static void main(String[] args) {
        String str = "1^0|0|1";
        boolean desired = false;
        System.out.println(calcExpressCombine(str, desired));
        System.out.println(calcExpressCombine2(str, desired));
    }

    public static int calcExpressCombine(String str, boolean desire) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (!isVaid(str.toCharArray())) {
            return 0;
        }

        return process(str.toCharArray(), desire, 0, str.length() - 1);
    }

    private static boolean isVaid(char[] str) {
        if ((str.length & 1) != 1) {
            return false;
        }
        for (int i = 0; i < str.length - 1; i += 2) {
            if (str[i] != '0' && str[i] != '1') {
                return false;
            }
            if (str[i + 1] != '&' && str[i + 1] != '|' && str[i + 1] != '^') {
                return false;
            }
        }
        return true;
    }

    /**
     * L 和R位置一定是数字
     *
     * @param ch
     * @param desire
     * @param L
     * @param R
     * @return
     */
    public static int process(char[] ch, boolean desire, int L, int R) {
        if (L == R) {
            if (ch[L] == '0') { //需要的是true，可当前字符是0，则返回0
                return desire ? 0 : 1;
            } else {
                return desire ? 1 : 0;
            }
        }

        int res = 0;
        for (int i = L; i < R; i += 2) {
            if (desire) { //当前需要的是true
                switch (ch[i + 1]) {
                    case '|':
                        res += process(ch, true, L, i) * process(ch, false, i + 2, R); //真 假
                        res += process(ch, false, L, i) * process(ch, true, i + 2, R); //假 假
                        res += process(ch, true, L, i) * process(ch, true, i + 2, R);//真 真
                        break;
                    case '&':
                        res += process(ch, true, L, i) * process(ch, true, i + 2, R);
                        break;
                    case '^':
                        res += process(ch, false, L, i) * process(ch, true, i + 2, R);
                        res += process(ch, true, L, i) * process(ch, false, i + 2, R);
                        break;
                }
            } else { //当前需要的是false
                switch (ch[i + 1]) {
                    case '|':
                        res += process(ch, false, L, i) * process(ch, false, i + 2, R);
                        break;
                    case '&':
                        res += process(ch, true, L, i) * process(ch, false, i + 2, R);
                        res += process(ch, false, L, i) * process(ch, true, i + 2, R);
                        res += process(ch, false, L, i) * process(ch, false, i + 2, R);
                        break;
                    case '^':
                        res += process(ch, false, L, i) * process(ch, false, i + 2, R);
                        res += process(ch, true, L, i) * process(ch, true, i + 2, R);
                        break;
                }
            }
        }
        return res;
    }

    public static int calcExpressCombine2(String str, boolean desire) {
        if (str == null || str.length() == 0 || !isVaid(str.toCharArray())) {
            return 0;
        }

        //根据尝试方法可知，有三个可变参数，L和R和desire
        //desire只有两个参数，true和false，所以只需要两张二维表即可
        int N = str.length();
        char[] ch = str.toCharArray();
        int[][] trueMap = new int[N][N];
        int[][] falseMap = new int[N][N];

        //根据L和R的范围，L一定是小于等于R的，所以两张二维表的左下角是没有数据的
        for (int i = 0; i < N; i += 2) { //base case
            trueMap[i][i] = ch[i] == '1'? 1 : 0; //ch[i] == '1'? 1 : 0;
            falseMap[i][i] = ch[i] == '1' ? 0 : 1;
        }

        for (int row = N - 3; row >= 0; row -= 2) { //从倒数第3行开始
            for (int col = row + 2; col < N; col += 2) {//从第5列开始
                for (int i = row + 1; i < col; i += 2) {
                    switch (ch[i]) {
                        case '&':
                            trueMap[row][col] += trueMap[row][i - 1] * trueMap[i + 1][col]; //左边和下边的值
                            falseMap[row][col] += falseMap[row][i - 1] * falseMap[i + 1][col]; //假 假
                            falseMap[row][col] += trueMap[row][i - 1] * falseMap[i + 1][col]; //真 假
                            falseMap[row][col] += falseMap[row][i - 1] * trueMap[i + 1][col]; //假 真
                            break;
                        case '|':
                            trueMap[row][col] += trueMap[row][i - 1] * trueMap[i + 1][col]; //真 真
                            trueMap[row][col] += trueMap[row][i - 1] * falseMap[i + 1][col]; //真 假
                            trueMap[row][col] += falseMap[row][i - 1] * trueMap[i + 1][col]; //假 真
                            falseMap[row][col] += falseMap[row][i - 1] * falseMap[i + 1][col];
                            break;
                        case '^':
                            trueMap[row][col] += falseMap[row][i - 1] * trueMap[i + 1][col];
                            trueMap[row][col] += trueMap[row][i - 1] * falseMap[i + 1][col];
                            falseMap[row][col] += falseMap[row][i - 1] * falseMap[i + 1][col];
                            falseMap[row][col] += trueMap[row][i - 1] * trueMap[i + 1][col];
                            break;
                    }
                }
            }
        }
        return desire?trueMap[0][N - 1] : falseMap[0][N - 1];
    }
}
