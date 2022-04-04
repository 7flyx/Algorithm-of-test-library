package class07;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-04
 * Time: 11:06
 * Description:
 */
public class Customer {
    public int id;
    public int buy; // 买的商品数
    public int enterTime; // 时间点

    public Customer(int id, int buy, int time) {
        this.buy = buy;
        this.id = id;
        this.enterTime = 0;
    }
}
