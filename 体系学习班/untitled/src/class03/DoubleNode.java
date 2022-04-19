package class03;
/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 16:39
 * Description: 泛型的双链表节点
 */
public class DoubleNode<T> {
    T value;
    DoubleNode<T> last;
    DoubleNode<T> next;

    public DoubleNode(T value) {
        this.value = value;
    }
}