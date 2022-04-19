package class03;

/**
 * Created by Terry
 * User: Administrator
 * Date: 2022-04-19
 * Time: 16:40
 * Description: 泛型的单链表节点
 */
public class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}
