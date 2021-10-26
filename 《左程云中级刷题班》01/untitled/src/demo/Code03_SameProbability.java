package demo;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2021-10-26
 * Time: 16:51
 * Description:
 */
public class  Code03_SameProbability {
    public static void main(String[] args) {

    }

    /**
     * 给定一个函数f， 可以1～5的数字等概率返回一个。 请加工出1～7的数字等概率
     * 返回一个的函数g。
     */
    public static int g1() {
        int res = 0;
        do{
            res = (process() << 2) + (process() << 1) + process();
        }while(res == 7); //等于7的时候，重新生成。
        return res;
    }
    //生成0、1
    public static int process() {
        int index = 0;
        do {
            index = (int)(Math.random() * 5) + 1;
        } while (index == 5);
        return index <= 2? 0 : 1; //生成0、1的生成器
    }

    /**
     * 给定一个函数f， 以p概率返回0， 以1-p概率返回1。 请加工出等概率返回0和1的
     * 函数g
     * @return response
     */
    public static int g2() {
        //p的概念生成0。1-p生成1.二者是相对的
        //则只需两个bit位。生成00、01、10、11,。如果生成00和11，则重新生成即可
        return 0;
    }
}
